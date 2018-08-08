

package org.dhhs.dirm.acts.cs.struts;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.naming.NamingException;
import javax.servlet.ServletException;
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
import org.dhhs.dirm.acts.cs.Constants;
import org.dhhs.dirm.acts.cs.beans.ReferralType;
import org.dhhs.dirm.acts.cs.beans.TaskBean;
import org.dhhs.dirm.acts.cs.beans.TaskEntityBean;
import org.dhhs.dirm.acts.cs.beans.TaskFormBean;
import org.dhhs.dirm.acts.cs.beans.TaskHistoryBean;
import org.dhhs.dirm.acts.cs.businesslogic.BusinessLogicException;
import org.dhhs.dirm.acts.cs.businesslogic.FormTypeManager;
import org.dhhs.dirm.acts.cs.businesslogic.TaskManager;
import org.dhhs.dirm.acts.cs.ejb.CSProcessorLocal;
import org.dhhs.dirm.acts.cs.ejb.CSProcessorLocalHome;
import org.dhhs.dirm.acts.cs.formbeans.ManageApprovalForm;
import org.dhhs.dirm.acts.cs.helpers.HomeHelper;
import org.dhhs.dirm.acts.cs.util.ApplicationException;
import org.dhhs.dirm.acts.cs.util.ErrorDescriptor;

/**
 * ApprovalSearchAction.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Jan 13, 2004 09:59:26 AM
 * 
 * @author Rkodumagulla
 *
 */
public class ApprovalSearchAction extends DispatchAction
{

	private static final Logger log = Logger.getLogger(ApprovalSearchAction.class);

	private TaskBean createTaskBean(CSProcessorLocal task)
	{
		TaskBean taskBean = new TaskBean();
		TaskEntityBean taskEntityBean = task.getTaskEntityBean();

		taskBean.setIdReference(taskEntityBean.getIdReference());
		taskBean.setNbCase(taskEntityBean.getNbCase());
		taskBean.setIdEmail(taskEntityBean.getIdEmail());
		taskBean.setIdPart(taskEntityBean.getIdPart());
		taskBean.setIdWorker(taskEntityBean.getIdWorker());
		taskBean.setNmWorker(taskEntityBean.getNmWorker());
		taskBean.setCdType(taskEntityBean.getCdType());
		taskBean.setNbTelACD(taskEntityBean.getNbTelACD());
		taskBean.setNbTelEXC(taskEntityBean.getNbTelEXC());
		taskBean.setNbTelLN(taskEntityBean.getNbTelLN());
		taskBean.setNbTelEXT(taskEntityBean.getNbTelEXT());
		taskBean.setNbDocket(taskEntityBean.getNbDocket());
		taskBean.setNmCustomerFirst(taskEntityBean.getNmCustomerFirst());
		taskBean.setNmCustomerLast(taskEntityBean.getNmCustomerLast());

		Vector v = new Vector();
		v.addElement(taskEntityBean.getFrmTrack().elementAt(0));
		taskBean.setFrmTrack(v);

		TaskHistoryBean thb = (TaskHistoryBean) taskEntityBean.getFrmTrack().elementAt(0);
		taskBean.setCdStatus(thb.getCdStatus());

		return taskBean;
	}

	private void manageTaskBean(TaskBean taskBean, String owner)
	{
		if (taskBean.getIdWorker().equals(owner))
		{
			taskBean.setOwner(true);
		} else
		{
			taskBean.setOwner(false);
		}
	}

	/**
	 * @see org.apache.struts.action.Action#search(ActionMapping, ActionForm,
	 *      HttpServletRequest, HttpServletResponse)
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{

		try
		{

			ManageApprovalForm taskForm = (ManageApprovalForm) form;

			log.info("Start Time for Approval Search " + new Date());

			HttpSession session = request.getSession();

			// This means that the Approve button was clicked on the input form
			String button = taskForm.getButton();
			if (button.equals("approveAll"))
			{
				return approveAll(mapping, form, request, response);
			}

			int searchType = 0;

			boolean blnFilterIdRef = false;
			boolean blnFilterWrkr = false;
			boolean blnFilterForm = false;

			if (taskForm.getIdReference() != null)
			{
				if (!taskForm.getIdReference().equals(""))
				{
					blnFilterIdRef = true;
				}
			}

			if (taskForm.getIdWorker() != null)
			{
				if (!taskForm.getIdWorker().equals(""))
				{
					blnFilterWrkr = true;
				}
			}

			if (taskForm.getCdType() != null && (!taskForm.getCdType().equals("")))
			{
				blnFilterForm = true;
			}

			Vector tasks = new Vector();
			org.dhhs.dirm.acts.cs.beans.UserBean loggedInUser = (org.dhhs.dirm.acts.cs.beans.UserBean) session.getAttribute(Constants.USERBEAN_KEY);
			String loggedInWorkerID = loggedInUser.getIdWorker();

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				Object obj = HomeHelper.singleton().getHome("ecsts.CSProcessorLocalHome");
				CSProcessorLocalHome processorLocalHome = (CSProcessorLocalHome) obj;

				TaskManager taskManager = TaskManager.getInstance();

				if (blnFilterIdRef)
				{
					log.debug("Search by Reference ID " + taskForm.getIdReference());
					CSProcessorLocal task = processorLocalHome.findByPrimaryKey(taskForm.getIdReference());
					TaskBean taskBean = createTaskBean(task);
					if (applyFilters(form, taskBean, false, blnFilterWrkr, blnFilterForm))
					{
						tasks.addElement(taskBean);
					}
				} else if (blnFilterWrkr)
				{
					log.debug("Search by Worker ID " + taskForm.getIdWorker());
					// 11/22/04 Collection c =
					// processorLocalHome.findPendingWorkerTasks(taskForm.getIdWorker());
					Collection c = taskManager.findPendingWorkerTasks(taskForm.getIdWorker());
					Iterator i = c.iterator();
					while (i.hasNext())
					{
						TaskBean taskBean = (TaskBean) i.next();
						manageTaskBean(taskBean, loggedInWorkerID);

						// 11/22/04 CSProcessorLocal task = (CSProcessorLocal)
						// i.next();
						// 11/22/04 TaskBean taskBean = createTaskBean(task);
						if (applyFilters(form, taskBean, false, false, blnFilterForm))
						{
							tasks.addElement(taskBean);
						}
					}
				} else if (blnFilterForm)
				{
					log.debug("Search by ReferralType Type " + taskForm.getCdType());
					Collection c = null;
					// c =
					// processorLocalHome.findPendingTasksByRfrl(taskForm.getCdType());
					c = taskManager.findPendingTasksByRfrl(taskForm.getCdType());
					Iterator i = c.iterator();
					while (i.hasNext())
					{
						TaskBean taskBean = (TaskBean) i.next();
						manageTaskBean(taskBean, loggedInWorkerID);

						// CSProcessorLocal task = (CSProcessorLocal) i.next();
						// TaskBean taskBean = createTaskBean(task);
						if (applyFilters(form, taskBean, false, false, false))
						{
							tasks.addElement(taskBean);
						}
					}
					log.info("Successfully Found TaskManager using Referral Type " + taskForm.getCdType());
				} else
				{
					log.debug("Search all");
					// Collection c = processorLocalHome.findAllPendingTasks();
					Collection c = taskManager.findAllPendingTasks();
					Iterator i = c.iterator();
					while (i.hasNext())
					{
						TaskBean taskBean = (TaskBean) i.next();
						manageTaskBean(taskBean, loggedInWorkerID);

						// CSProcessorLocal task = (CSProcessorLocal) i.next();
						// TaskBean taskBean = createTaskBean(task);
						if (applyFilters(form, taskBean, blnFilterIdRef, blnFilterWrkr, blnFilterForm))
						{
							tasks.addElement(taskBean);
						}
					}
				}
			} catch (javax.ejb.ObjectNotFoundException onfe)
			{ // findByPrimaryKey returned not found exception
				log.info("Approval Search Failed. Task(s) not found");
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.notfound"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (javax.ejb.FinderException fe)
			{
				log.fatal("Approval Search Failed. Database Exception resulted from EJB");

				new ApplicationException(fe.getMessage(), fe, new ErrorDescriptor("ApprovalSearchAction", "search"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));

			} catch (NamingException ne)
			{
				log.fatal("Approval Search Failed. EJB Not Found");

				new ApplicationException(ne.getMessage(), ne, new ErrorDescriptor("ApprovalSearchAction", "search"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (BusinessLogicException ble)
			{
				log.fatal("Approval Search Failed in TaskManager ");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ApprovalSearchAction", "search"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			taskForm.setApprovalList(tasks);
			if (tasks.size() == 0)
			{
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.notfound"));
				saveErrors(request, errors);
			}

			String[] selections = new String[tasks.size()];

			for (int j = 0; j < selections.length; j++)
			{
				selections[j] = "";
			}
			taskForm.setSelection(selections);

			if ("request".equals(mapping.getScope()))
				request.setAttribute(mapping.getAttribute(), form);
			else
			{
				session.setAttribute(mapping.getAttribute(), form);
			}

			log.info("End Time for Task Search " + new Date());

			// Return success
			return (mapping.findForward(Constants.SUCCESS));
		} catch (Exception e)
		{
			log.error("Manage Task View Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageTaskAction", "view"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			return (new ActionForward(mapping.getInput()));
		}

	}

	private boolean applyFilters(ActionForm form, TaskBean taskBean, boolean blnFilterIdRef, boolean blnFilterWrkr, boolean blnFilterForm)
	{

		ManageApprovalForm taskForm = (ManageApprovalForm) form;

		boolean select = true;

		log.debug("Applying Filter Criteria for Task # " + taskBean.getIdReference());

		log.debug("ApprovalSearch applying status filter");
		if (!taskBean.getCdStatus().equals("APRV") && !taskBean.getCdStatus().equals("REQR"))
		{
			log.debug("ApprovalSearch applying status filter - reject record with " + taskBean.getCdStatus());
			return false;
		}

		log.debug("ApprovalSearch applying reference id filter");
		if (blnFilterIdRef)
		{
			if (!taskForm.getIdReference().trim().equals(taskBean.getIdReference().trim()))
			{
				log.debug("ApprovalSearch applying reference id filter - reject record with " + taskForm.getIdReference());
				return false;
			}
		}

		log.debug("ApprovalSearch applying worker filter");
		if (blnFilterWrkr)
		{
			if (!taskForm.getIdWorker().trim().equals(taskBean.getIdWorker().trim()))
			{
				log.debug("ApprovalSearch applying worker filter - reject record with " + taskForm.getIdWorker());
				return false;
			}
		}

		log.debug("ApprovalSearch applying referral type filter");
		if (blnFilterForm)
		{
			if (!taskForm.getCdType().trim().equals(taskBean.getCdType().trim()))
			{
				log.debug("ApprovalSearch applying referral filter - reject record with " + taskForm.getCdType());
				return false;
			}
		}

		return select;
	}

	/**
	 * @see org.apache.struts.action.Action#approveAll(ActionMapping,
	 *      ActionForm, HttpServletRequest, HttpServletResponse)
	 */
	public ActionForward approveAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{

		try
		{

			ManageApprovalForm taskForm = (ManageApprovalForm) form;

			Vector tasks = taskForm.getApprovalList();
			String[] selection = taskForm.getSelection();

			Object obj = HomeHelper.singleton().getHome("ecsts.CSProcessorLocalHome");
			CSProcessorLocalHome processorLocalHome = (CSProcessorLocalHome) obj;

			for (int i = 0; i < selection.length; i++)
			{
				if (!selection[i].equals(""))
				{
					String idReference = selection[i];
					log.info("mass approval: approve task with reference id : " + selection[i]);

					/**
					 * Invoke the Business Process and return results
					 */
					try
					{
						log.debug("Search by Reference ID");
						CSProcessorLocal task = processorLocalHome.findByPrimaryKey(idReference);
						TaskEntityBean taskEntityBean = task.getTaskEntityBean();

						TaskFormBean tb = new TaskFormBean();
						tb.setIdReference(taskEntityBean.getIdReference());
						tb.setCdType(taskEntityBean.getCdType());

						/**
						 * MOD# 2873 - rkodumagulla 09/14/04. If the referral
						 * type does not require correspondence generation, then
						 * Approve and Close the task
						 */

						FormTypeManager ftm = FormTypeManager.getInstance();
						ReferralType rt = ftm.getForm(taskEntityBean.getCdType());
						if (rt.isGenerateCorrespondence())
						{
							tb.setCdStatus("APRT");
						} else
						{
							tb.setCdStatus("APCL");
						}
						tb.setIdWorker(((TaskHistoryBean) taskEntityBean.getFrmTrack().elementAt(0)).getIdWrkrAssign());
						tb.setNbTelAcd(taskEntityBean.getNbTelACD());
						tb.setNbTelExc(taskEntityBean.getNbTelEXC());
						tb.setNbTelLn(taskEntityBean.getNbTelLN());
						tb.setNbTelExt(taskEntityBean.getNbTelEXT());
						tb.setNbCase(taskEntityBean.getNbCase());
						tb.setIdEmail(taskEntityBean.getIdEmail());
						tb.setIdPart(taskEntityBean.getIdPart());
						tb.setNbSSN(taskEntityBean.getNbSSN());
						tb.setIdWorker(taskEntityBean.getIdWorker());
						tb.setNbDocket(taskEntityBean.getNbDocket());
						tb.setNbControl(taskEntityBean.getNbControl());
						tb.setNmCounty(taskEntityBean.getNmCounty());
						tb.setNmCustomerFirst(taskEntityBean.getNmCustomerFirst());
						tb.setNmCustomerLast(taskEntityBean.getNmCustomerLast());
						tb.setNmCustomerMi(taskEntityBean.getNmCustomerMi());
						tb.setNmCustParFirst(taskEntityBean.getNmCustParFirst());
						tb.setNmCustParLast(taskEntityBean.getNmCustParLast());
						tb.setNmCustParMi(taskEntityBean.getNmCustParMi());
						tb.setNmNonCustParFirst(taskEntityBean.getNmNonCustParFirst());
						tb.setNmNonCustParLast(taskEntityBean.getNmNonCustParLast());
						tb.setNmNonCustParMi(taskEntityBean.getNmNonCustParMi());
						tb.setNmRefSource1(taskEntityBean.getNmRefSource1());
						tb.setNmRefSource2(taskEntityBean.getNmRefSource2());
						tb.setNmRefSource3(taskEntityBean.getNmRefSource3());
						tb.setNmRefSource4(taskEntityBean.getNmRefSource4());
						tb.setDtCompleted(((TaskHistoryBean) taskEntityBean.getFrmTrack().elementAt(0)).getFormattedEnd());
						tb.setDtReceived(((TaskHistoryBean) taskEntityBean.getFrmTrack().elementAt(0)).getFormattedStart());
						tb.setDtDue(((TaskHistoryBean) taskEntityBean.getFrmTrack().elementAt(0)).getFormattedDue());
						tb.setNtResolution(((TaskHistoryBean) taskEntityBean.getFrmTrack().elementAt(0)).getNotes());

						processorLocalHome.createTaskHistory(tb);

						// MOD 2873 - rkodumagulla 09/14/05. If the referral
						// type does not require
						// correspondence, then simply create a CLSD transaction
						// also.
						if (!rt.isGenerateCorrespondence())
						{
							tb.setCdStatus("CLSD");
							processorLocalHome.createTaskHistory(tb);
						}
					} catch (javax.ejb.ObjectNotFoundException onfe)
					{ // findByPrimaryKey returned not found exception
						log.info("Approval Search Failed. Task not found");
						ActionErrors errors = new ActionErrors();
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.notfound"));
						saveErrors(request, errors);
						return (new ActionForward(mapping.getInput()));
					} catch (javax.ejb.FinderException fe)
					{
						log.fatal("Approval Search Failed. Database Exception resulted from EJB");

						new ApplicationException(fe.getMessage(), fe, new ErrorDescriptor("ApprovalSearchAction", "approveAll"));

						ActionErrors errors = new ActionErrors();
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
						saveErrors(request, errors);
						return (new ActionForward(mapping.getInput()));
					}
				}
			}

			taskForm.setButton("");
			return (new ActionForward(mapping.getInput()));

		} catch (Exception e)
		{
			log.error("Approval Search Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ApprovalSearchAction", "approveAll"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			return (new ActionForward(mapping.getInput()));
		}
	}
}
