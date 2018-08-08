

package org.dhhs.dirm.acts.cs.struts;

import java.util.Locale;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.MessageResources;
import org.dhhs.dirm.acts.cs.Constants;
import org.dhhs.dirm.acts.cs.EmailException;
import org.dhhs.dirm.acts.cs.EmailManager;
import org.dhhs.dirm.acts.cs.ManageServletStack;
import org.dhhs.dirm.acts.cs.beans.TaskEntityBean;
import org.dhhs.dirm.acts.cs.beans.TaskFormBean;
import org.dhhs.dirm.acts.cs.beans.TaskHistoryBean;
import org.dhhs.dirm.acts.cs.businesslogic.BusinessLogicException;
import org.dhhs.dirm.acts.cs.businesslogic.ReferralSourceManager;
import org.dhhs.dirm.acts.cs.ejb.CSProcessorLocal;
import org.dhhs.dirm.acts.cs.ejb.CSProcessorLocalHome;
import org.dhhs.dirm.acts.cs.formbeans.ManageTaskForm;
import org.dhhs.dirm.acts.cs.formbeans.ReferralSource;
import org.dhhs.dirm.acts.cs.helpers.HomeHelper;
import org.dhhs.dirm.acts.cs.util.ApplicationException;
import org.dhhs.dirm.acts.cs.util.ErrorDescriptor;

/**
 * ManageApprovalAction.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: November 04, 2003 4:24:15 PM
 * 
 * @author Rkodumagulla
 *
 */

public class ManageApprovalAction extends DispatchAction
{

	private static final Logger log = Logger.getLogger(ManageApprovalAction.class);

	/**
	 * Constructor
	 */
	public ManageApprovalAction()
	{
		super();
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		try
		{
			Locale locale = getLocale(request);
			MessageResources messages = getResources(request);
			HttpSession session = request.getSession();
			boolean taskClosed = false;

			org.dhhs.dirm.acts.cs.beans.UserBean loggedInUser = (org.dhhs.dirm.acts.cs.beans.UserBean) session.getAttribute(Constants.USERBEAN_KEY);

			ManageTaskForm tmf = (ManageTaskForm) form;

			/**
			 * Invoke the Business Process to get task history as the struts
			 * framework does not support it
			 */
			try
			{
				Object obj = HomeHelper.singleton().getHome("ecsts.CSProcessorLocalHome");
				CSProcessorLocalHome processorLocalHome = (CSProcessorLocalHome) obj;

				CSProcessorLocal task = processorLocalHome.findByPrimaryKey(tmf.getIdReference());
				request.setAttribute("taskHistory", task.getFrmTrack());

			} catch (javax.ejb.FinderException fe)
			{
				log.fatal("Manage Approval save Failed. Database Exception resulted from EJB");

				new ApplicationException(fe.getMessage(), fe, new ErrorDescriptor("ManageApprovalAction", "save"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (NamingException ne)
			{
				log.fatal("Manage Approval save Failed. EJB not found");

				new ApplicationException(ne.getMessage(), ne, new ErrorDescriptor("ManageApprovalAction", "save"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			ActionErrors errors = new ActionErrors();

			errors = tmf.validate(mapping, request);

			if (!errors.isEmpty())
			{
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			if (tmf.getCdStatus().equals("CLSD"))
			{
				taskClosed = true;
			}

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				Object obj = HomeHelper.singleton().getHome("ecsts.CSProcessorLocalHome");
				CSProcessorLocalHome processorLocalHome = (CSProcessorLocalHome) obj;

				TaskFormBean tb = new TaskFormBean();
				tb.setIdReference(tmf.getIdReference());
				tb.setCdType(tmf.getCdType());
				tb.setCdStatus(tmf.getCdStatus());
				tb.setNbTelAcd(tmf.getNbTelAcd());
				tb.setNbTelExc(tmf.getNbTelExc());
				tb.setNbTelLn(tmf.getNbTelLn());
				tb.setNbTelExt(tmf.getNbTelExt());
				tb.setDtCompleted(tmf.getDtCompleted());
				tb.setDtDue(tmf.getDtDue());
				tb.setDtReceived(tmf.getDtReceived());
				tb.setNbCase(tmf.getNbCase());
				tb.setIdEmail(tmf.getIdEmail());
				tb.setIdPart(tmf.getIdPart());
				tb.setNbSSN(tmf.getNbSSN());
				tb.setIdWorker(tmf.getIdWorker());
				tb.setNbDocket(tmf.getNbDocket());
				tb.setNbControl(tmf.getControlNumber());
				tb.setNmCounty(tmf.getNmCounty());
				tb.setNmAuthor(tmf.getNmAuthor());
				tb.setNmCustomerFirst(tmf.getNmAuthorFirst());
				tb.setNmCustomerLast(tmf.getNmAuthorLast());
				tb.setNmCustomerMi(tmf.getNmAuthorMi());
				tb.setNmCustParFirst(tmf.getNmCustParFirst());
				tb.setNmCustParLast(tmf.getNmCustParLast());
				tb.setNmCustParMi(tmf.getNmCustParMi());
				tb.setNmNonCustParFirst(tmf.getNmNonCustParFirst());
				tb.setNmNonCustParLast(tmf.getNmNonCustParLast());
				tb.setNmNonCustParMi(tmf.getNmNonCustParMi());
				tb.setNmRefSource1(tmf.getNmRefSource1());
				tb.setNmRefSource2(tmf.getNmRefSource2());
				tb.setNmRefSource3(tmf.getNmRefSource3());
				tb.setNtResolution(tmf.getNtResolution());

				processorLocalHome.createTaskHistory(tb);

				/**
				 * If the supervisor approved and closed, then also create a
				 * CLSD transaction and generate the appropriate documentation
				 */
				if (tmf.getCdStatus().equals("APCL"))
				{
					tb.setCdStatus("CLSD");
					taskClosed = true;
					processorLocalHome.createTaskHistory(tb);
				}

				if (taskClosed)
				{
					/*
					 * If the task has an email address associated, then simply
					 * generate an email to the customer and update the record,
					 * else generate the pdf for print.
					 */
					if (!tmf.getIdEmail().trim().equals(""))
					{
						StringBuffer sb = new StringBuffer();
						sb.append("NOTE: PLEASE DO NOT RESPOND DIRECTLY TO THIS E-MAIL MESSAGE. THIS ADDRESS IS NOT MONITORED.\n\n");
						sb.append("If a response is required, please use the following link to correspond with a customer service representative.");
						sb.append("https://nddhacts01.dhhs.state.nc.us/InternalFeedbackForm.jsp?dest=ecse");
						sb.append("\n\n");
						sb.append(tmf.getNtResolution());

						try
						{
							EmailManager em = new EmailManager();
							em.setSubject("N.C. Child Support Customer Service Center - Resolution for Request # " + tmf.getIdReference());
							em.addTo(tmf.getIdEmail());
							em.setBody(sb.toString());
							em.sendMail();
						} catch (EmailException e)
						{
							log.fatal("Manage Task Failed to send email to Client");

							new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageApprovalAction", "save"));

							errors = new ActionErrors();
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
							saveErrors(request, errors);
							return (new ActionForward(mapping.getInput()));
						}
					} else
					{
						response.sendRedirect("CSTSLetter?report=CSTSLetterHead&referenceID=" + tmf.getIdReference());
					}
				}
			} catch (NamingException ne)
			{
				log.fatal("Manage Approval Failed. EJB Not Found");

				new ApplicationException(ne.getMessage(), ne, new ErrorDescriptor("ManageApprovalAction", "save"));

				errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (javax.ejb.CreateException ce)
			{
				log.fatal("Create Task History Failed. EJB Database Exception");

				new ApplicationException(ce.getMessage(), ce, new ErrorDescriptor("ManageApprovalAction", "save"));

				errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}
			request.setAttribute("idReference", tmf.getIdReference());

			ActionForward forward = mapping.findForward("save");
			StringBuffer path = new StringBuffer(forward.getPath());
			boolean isQuery = (path.toString().indexOf("?") >= 0);

			if (isQuery)
			{
				path.append("&reqCode=view&idReference=" + tmf.getIdReference() + "&status=" + tmf.getCdStatus());
			} else
			{
				path.append("?reqCode=view&idReference=" + tmf.getIdReference() + "&status=" + tmf.getCdStatus());
			}
			return new ActionForward(path.toString());

			// return view(mapping, form, request, response);

		} catch (Exception e)
		{
			log.error("Save Approval Task Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageApprovalAction", "save"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			return (new ActionForward(mapping.getInput()));
		}
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		try
		{

			Locale locale = getLocale(request);
			MessageResources messages = getResources(request);
			HttpSession session = request.getSession();

			String idReference = (String) request.getParameter("idReference");
			String status = (String) request.getParameter("status");

			if (idReference == null)
			{
				log.error("Reference ID Missing.");
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("Reference ID missing"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			if (form == null)
			{

				form = new ManageTaskForm();
				if ("request".equals(mapping.getScope()))
					request.setAttribute(mapping.getAttribute(), form);
				else
				{
					session.setAttribute(mapping.getAttribute(), form);
				}
			}

			ManageTaskForm taskForm = (ManageTaskForm) form;

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				Object obj = HomeHelper.singleton().getHome("ecsts.CSProcessorLocalHome");
				CSProcessorLocalHome processorLocalHome = (CSProcessorLocalHome) obj;

				log.debug("Search by Reference ID");
				CSProcessorLocal task = processorLocalHome.findByPrimaryKey(idReference);
				TaskEntityBean taskEntityBean = task.getTaskEntityBean();

				int taskHistoryItem = 0;

				if (status != null)
				{
					for (int i = 0; i < taskEntityBean.getFrmTrack().size(); i++)
					{
						TaskHistoryBean thb = (TaskHistoryBean) taskEntityBean.getFrmTrack().elementAt(i);
						if (thb.getCdStatus().trim().equals(status.trim()))
						{
							taskHistoryItem = i;
							break;
						}
					}
				}

				taskForm.setIdReference(taskEntityBean.getIdReference());
				taskForm.setNbCase(taskEntityBean.getNbCase());
				taskForm.setIdEmail(taskEntityBean.getIdEmail());
				taskForm.setIdPart(taskEntityBean.getIdPart());
				taskForm.setCdType(taskEntityBean.getCdType());
				taskForm.setNbSSN(taskEntityBean.getNbSSN());
				taskForm.setNbTelAcd(taskEntityBean.getNbTelACD());
				taskForm.setNbTelExc(taskEntityBean.getNbTelEXC());
				taskForm.setNbTelLn(taskEntityBean.getNbTelLN());
				taskForm.setNbTelExt(taskEntityBean.getNbTelEXT());
				taskForm.setNbDocket(taskEntityBean.getNbDocket());
				taskForm.setControlNumber(taskEntityBean.getNbControl());
				taskForm.setNmCounty(taskEntityBean.getNmCounty());
				taskForm.setNmAuthorFirst(taskEntityBean.getNmCustomerFirst());
				taskForm.setNmAuthorLast(taskEntityBean.getNmCustomerLast());
				taskForm.setNmAuthorMi(taskEntityBean.getNmCustomerMi());
				taskForm.setNmCustParFirst(taskEntityBean.getNmCustParFirst());
				taskForm.setNmCustParLast(taskEntityBean.getNmCustParLast());
				taskForm.setNmCustParMi(taskEntityBean.getNmCustParMi());
				taskForm.setNmNonCustParFirst(taskEntityBean.getNmNonCustParFirst());
				taskForm.setNmNonCustParLast(taskEntityBean.getNmNonCustParLast());
				taskForm.setNmNonCustParMi(taskEntityBean.getNmNonCustParMi());

				ReferralSourceManager rfsManager = ReferralSourceManager.getInstance();

				ReferralSource rs = null;

				if (!taskEntityBean.getNmRefSource1().equals(""))
				{
					rs = rfsManager.getReferralSource(taskEntityBean.getNmRefSource1());
					taskForm.setNmRefSource1(rs.getNmStaff());
				}
				if (!taskEntityBean.getNmRefSource2().equals(""))
				{
					rs = rfsManager.getReferralSource(taskEntityBean.getNmRefSource2());
					taskForm.setNmRefSource2(rs.getNmStaff());
				}
				if (!taskEntityBean.getNmRefSource3().equals(""))
				{
					rs = rfsManager.getReferralSource(taskEntityBean.getNmRefSource3());
					taskForm.setNmRefSource3(rs.getNmStaff());
				}

				if (!taskEntityBean.getNmRefSource4().equals(""))
				{
					rs = rfsManager.getReferralSource(taskEntityBean.getNmRefSource4());
					taskForm.setNmRefSource4(rs.getNmStaff());
				}
				taskForm.setCdStatus(((TaskHistoryBean) taskEntityBean.getFrmTrack().elementAt(taskHistoryItem)).getCdStatus());
				taskForm.setIdWorker(((TaskHistoryBean) taskEntityBean.getFrmTrack().elementAt(taskHistoryItem)).getIdWrkrAssign());
				taskForm.setNtResolution(((TaskHistoryBean) taskEntityBean.getFrmTrack().elementAt(taskHistoryItem)).getNotes());
				taskForm.setDtCompleted(((TaskHistoryBean) taskEntityBean.getFrmTrack().elementAt(taskHistoryItem)).getFormattedEnd());
				taskForm.setDtReceived(((TaskHistoryBean) taskEntityBean.getFrmTrack().elementAt(taskHistoryItem)).getFormattedStart());
				taskForm.setDtDue(((TaskHistoryBean) taskEntityBean.getFrmTrack().elementAt(taskHistoryItem)).getFormattedDue());

				request.setAttribute("taskHistory", taskEntityBean.getFrmTrack());

				log.debug("Successfully Found CSProcessorLocal EJB using Reference ID:" + idReference);
			} catch (javax.ejb.ObjectNotFoundException onfe)
			{ // findByPrimaryKey returned not found exception
				log.info("Manage Approval View Failed. Task not found");
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.notfound"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (javax.ejb.FinderException fe)
			{
				log.fatal("Manage Approval View Failed. Database Exception resulted from EJB");

				new ApplicationException(fe.getMessage(), fe, new ErrorDescriptor("ManageApprovalAction", "view"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (NamingException ne)
			{
				log.fatal("Manage Approval View Failed. EJB not found");

				new ApplicationException(ne.getMessage(), ne, new ErrorDescriptor("ManageApprovalAction", "view"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (BusinessLogicException ble)
			{
				log.fatal("Manage Approval View Failed. Database Exception resulted from ReferralSourceManager");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageApprovalAction", "view"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			request.setAttribute("formMode", "2");

			/**
			 * Logic for return functionality
			 */
			new ManageServletStack().addToStack(request, session);

			return (mapping.findForward(Constants.SUCCESS));
		} catch (Exception e)
		{
			log.error("Manage Approval View Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageApprovalAction", "view"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			return (new ActionForward(mapping.getInput()));
		}
	}

	public ActionForward manage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		try
		{
			Locale locale = getLocale(request);
			MessageResources messages = getResources(request);
			HttpSession session = request.getSession();

			String idReference = (String) request.getParameter("idReference");

			if (idReference == null)
			{
				log.error("Reference ID Missing.");
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("Reference ID missing"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			if (form == null)
			{
				form = new ManageTaskForm();
				if ("request".equals(mapping.getScope()))
					request.setAttribute(mapping.getAttribute(), form);
				else
				{
					session.setAttribute(mapping.getAttribute(), form);
				}
			}

			ManageTaskForm taskForm = (ManageTaskForm) form;

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				Object obj = HomeHelper.singleton().getHome("ecsts.CSProcessorLocalHome");
				CSProcessorLocalHome processorLocalHome = (CSProcessorLocalHome) obj;

				CSProcessorLocal task = processorLocalHome.findByPrimaryKey(idReference);
				TaskEntityBean taskEntityBean = task.getTaskEntityBean();

				taskForm.setIdReference(idReference);
				taskForm.setNbCase(taskEntityBean.getNbCase());
				taskForm.setIdEmail(taskEntityBean.getIdEmail());
				taskForm.setIdPart(taskEntityBean.getIdPart());
				taskForm.setNbSSN(taskEntityBean.getNbSSN());
				taskForm.setCdStatus(taskEntityBean.getCdStatus());
				taskForm.setIdWorker(((TaskHistoryBean) taskEntityBean.getFrmTrack().elementAt(0)).getIdWrkrAssign());
				taskForm.setCdType(taskEntityBean.getCdType());
				taskForm.setNbTelAcd(taskEntityBean.getNbTelACD());
				taskForm.setNbTelExc(taskEntityBean.getNbTelEXC());
				taskForm.setNbTelLn(taskEntityBean.getNbTelLN());
				taskForm.setNbTelExt(taskEntityBean.getNbTelEXT());
				taskForm.setNbDocket(taskEntityBean.getNbDocket());
				taskForm.setNmCounty(taskEntityBean.getNmCounty());
				taskForm.setNmAuthorFirst(taskEntityBean.getNmCustomerFirst());
				taskForm.setNmAuthorLast(taskEntityBean.getNmCustomerLast());
				taskForm.setNmAuthorMi(taskEntityBean.getNmCustomerMi());
				taskForm.setNmCustParFirst(taskEntityBean.getNmCustParFirst());
				taskForm.setNmCustParLast(taskEntityBean.getNmCustParLast());
				taskForm.setNmCustParMi(taskEntityBean.getNmCustParMi());
				taskForm.setNmNonCustParFirst(taskEntityBean.getNmNonCustParFirst());
				taskForm.setNmNonCustParLast(taskEntityBean.getNmNonCustParLast());
				taskForm.setNmNonCustParMi(taskEntityBean.getNmNonCustParMi());

				ReferralSourceManager rfsManager = ReferralSourceManager.getInstance();

				ReferralSource rs = null;

				if (!taskEntityBean.getNmRefSource1().equals(""))
				{
					rs = rfsManager.getReferralSource(taskEntityBean.getNmRefSource1());
					taskForm.setNmRefSource1(rs.getNmStaff());
				}
				if (!taskEntityBean.getNmRefSource2().equals(""))
				{
					rs = rfsManager.getReferralSource(taskEntityBean.getNmRefSource2());
					taskForm.setNmRefSource2(rs.getNmStaff());
				}
				if (!taskEntityBean.getNmRefSource3().equals(""))
				{
					rs = rfsManager.getReferralSource(taskEntityBean.getNmRefSource3());
					taskForm.setNmRefSource3(rs.getNmStaff());
				}

				if (!taskEntityBean.getNmRefSource4().equals(""))
				{
					rs = rfsManager.getReferralSource(taskEntityBean.getNmRefSource4());
					taskForm.setNmRefSource4(rs.getNmStaff());
				}

				taskForm.setDtCompleted(((TaskHistoryBean) taskEntityBean.getFrmTrack().elementAt(0)).getFormattedEnd());
				taskForm.setDtReceived(((TaskHistoryBean) taskEntityBean.getFrmTrack().elementAt(0)).getFormattedStart());
				taskForm.setDtDue(((TaskHistoryBean) taskEntityBean.getFrmTrack().elementAt(0)).getFormattedDue());
				taskForm.setNtResolution(((TaskHistoryBean) taskEntityBean.getFrmTrack().elementAt(0)).getNotes());

				request.setAttribute("taskHistory", taskEntityBean.getFrmTrack());

			} catch (javax.ejb.FinderException fe)
			{
				log.fatal("Manage Approval manage Failed. Database Exception resulted from EJB");

				new ApplicationException(fe.getMessage(), fe, new ErrorDescriptor("ManageApprovalAction", "manage"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (NamingException ne)
			{
				log.fatal("Manage Approval manage Failed. EJB not found");

				new ApplicationException(ne.getMessage(), ne, new ErrorDescriptor("ManageApprovalAction", "manage"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (BusinessLogicException ble)
			{
				log.fatal("Manage Approval manage Failed. Database Exception resulted from ReferralSourceManager");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageApprovalAction", "manage"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			request.setAttribute("formMode", "1");

			/**
			 * Logic for return functionality
			 */
			// new ManageServletStack().addToStack(request,session);

			return (mapping.findForward(Constants.SUCCESS));
		} catch (Exception e)
		{
			log.error("Manage Approval Manage Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageApprovalAction", "manage"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			return (new ActionForward(mapping.getInput()));
		}
	}
}
