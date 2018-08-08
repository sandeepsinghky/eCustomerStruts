

package org.dhhs.dirm.acts.cs.struts;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

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
import org.dhhs.dirm.acts.cs.ManageServletStack;
import org.dhhs.dirm.acts.cs.beans.TaskEntityBean;
import org.dhhs.dirm.acts.cs.beans.TaskFormBean;
import org.dhhs.dirm.acts.cs.beans.TaskHistoryBean;
import org.dhhs.dirm.acts.cs.beans.UserBean;
import org.dhhs.dirm.acts.cs.beans.UserEntityBean;
import org.dhhs.dirm.acts.cs.ejb.CSProcessorLocal;
import org.dhhs.dirm.acts.cs.ejb.CSProcessorLocalHome;
import org.dhhs.dirm.acts.cs.ejb.CSUserLocal;
import org.dhhs.dirm.acts.cs.ejb.CSUserLocalHome;
import org.dhhs.dirm.acts.cs.formbeans.ManageWorkloadForm;
import org.dhhs.dirm.acts.cs.helpers.HomeHelper;
import org.dhhs.dirm.acts.cs.util.ApplicationException;
import org.dhhs.dirm.acts.cs.util.ErrorDescriptor;

import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * ManageWorkloadAction.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Dec 15, 2003 10:58:32 AM
 * 
 * @author rkodumagulla
 *
 */
public class ManageWorkloadAction extends DispatchAction
{

	private final static Logger log = Logger.getLogger(ManageWorkloadAction.class);
	/**
	 * Constructor for ManageWorkloadAction.
	 */
	public ManageWorkloadAction()
	{
		super();
	}

	private org.dhhs.dirm.acts.cs.beans.UserBean createUserBean(CSUserLocal user)
	{
		org.dhhs.dirm.acts.cs.beans.UserBean userBean = new org.dhhs.dirm.acts.cs.beans.UserBean();
		UserEntityBean userEntityBean = user.getUserEntityBean();

		userBean.setNmWorker(userEntityBean.getNmWrkr());
		userBean.setCdAccptWrkld(userEntityBean.getCdAccptWrkld());
		userBean.setIdProfile(userEntityBean.getIdProfile());
		userBean.setIdWorker(userEntityBean.getIdWrkr());
		userBean.setNbrOutstanding(userEntityBean.getNbOutstanding());
		userBean.setNbrCompleted(userEntityBean.getNbCompleted());
		userBean.setNbrApproval(userEntityBean.getNbApproval());

		return userBean;
	}

	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		try
		{
			Locale locale = getLocale(request);
			MessageResources messages = getResources(request);
			HttpSession session = request.getSession();

			ManageWorkloadForm wkForm = (ManageWorkloadForm) form;

			if (wkForm == null)
			{
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("manageUser.error.workerRequired"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			Vector selectList = new Vector();
			Vector totalList = new Vector();
			String[] selection = wkForm.getSelectedWorkers();

			/**
			 * Some Return navigation problem 06/21/04 - UAT PR# 51
			 */
			log.info(wkForm.getWorkerid());

			if (wkForm.getWorkerid() == null)
			{
				log.info("Navigation Failure for Workload Management Detail. Load data from session");
				wkForm.setWorkerid((String) session.getAttribute("srcWKUID"));
				log.info(wkForm.getWorkerid());
				selection = (String[]) session.getAttribute("trgWKUIDLIST");
				log.info(selection);
				wkForm.setSelectedWorkers(selection);
			}
			session.setAttribute("srcWKUID", wkForm.getWorkerid());
			session.setAttribute("trgWKUIDLIST", selection);

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				Object obj = HomeHelper.singleton().getHome("ecsts.CSUserLocalHome");
				CSUserLocalHome userLocalHome = (CSUserLocalHome) obj;

				Object obj1 = HomeHelper.singleton().getHome("ecsts.CSProcessorLocalHome");
				CSProcessorLocalHome processorLocalHome = (CSProcessorLocalHome) obj1;
				int approvalCount = processorLocalHome.countApproval(wkForm.getWorkerid());
				int outstandingCount = processorLocalHome.countOutstanding(wkForm.getWorkerid());
				int completedCount = processorLocalHome.countCompleted(wkForm.getWorkerid());

				CSUserLocal srcUser = userLocalHome.findByPrimaryKey(wkForm.getWorkerid());
				UserEntityBean userEntityBean = srcUser.getUserEntityBean();

				wkForm.setNmWorker(userEntityBean.getNmWrkr());
				wkForm.setNbrApproval(approvalCount);
				wkForm.setNbrCompleted(completedCount);
				wkForm.setNbrOutstanding(outstandingCount);

				/*
				 * Collection c = userLocalHome.findAllUsers(); Iterator i =
				 * c.iterator(); while (i.hasNext()) { CSUserLocal user =
				 * (CSUserLocal) i.next(); UserBean ub = createUserBean(user);
				 * approvalCount =
				 * processorLocalHome.countApproval(ub.getIdWorker());
				 * outstandingCount =
				 * processorLocalHome.countOutstanding(ub.getIdWorker());
				 * completedCount =
				 * processorLocalHome.countCompleted(ub.getIdWorker());
				 * ub.setNbrApproval(approvalCount);
				 * ub.setNbrCompleted(completedCount);
				 * ub.setNbrOutstanding(outstandingCount);
				 * totalList.addElement(ub); }
				 */

				selectedWorkerLoop : for (int j = 0; j < selection.length; j++)
				{
					if (selection[j].equals(""))
					{
						continue;
					}

					CSUserLocal trgUser = userLocalHome.findByPrimaryKey(selection[j]);
					UserBean ub = createUserBean(trgUser);
					approvalCount = processorLocalHome.countApproval(ub.getIdWorker());
					outstandingCount = processorLocalHome.countOutstanding(ub.getIdWorker());
					completedCount = processorLocalHome.countCompleted(ub.getIdWorker());
					ub.setNbrApproval(approvalCount);
					ub.setNbrCompleted(completedCount);
					ub.setNbrOutstanding(outstandingCount);
					selectList.addElement(ub);

					/*
					 * org.dhhs.dirm.acts.cs.beans.UserBean ub =
					 * (org.dhhs.dirm.acts.cs.beans.UserBean)
					 * totalList.elementAt(j);
					 * 
					 * if (ub.getIdWorker().equals(selection[j])) {
					 * selectList.addElement(ub); }
					 */
				}
				wkForm.setTargetUsers(selectList);
			} catch (javax.ejb.FinderException fe)
			{
				log.fatal("Manage Workload Action Failed. Database Exception resulted from EJB");
				new ApplicationException(fe.getMessage(), fe, new ErrorDescriptor("ManageWorkloadAction", "load"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (NamingException ne)
			{
				log.fatal("Manage Workload Action Failed. EJB not found");

				new ApplicationException(ne.getMessage(), ne, new ErrorDescriptor("ManageWorkloadAction", "load"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			if ("request".equals(mapping.getScope()))
				request.setAttribute(mapping.getAttribute(), form);
			else
			{
				session.setAttribute(mapping.getAttribute(), form);
			}

			saveToken(request);

			FormUtils.setFormDisplayMode(request, form, FormUtils.EDIT_MODE);

			/**
			 * Logic for return functionality
			 */
			new ManageServletStack().addToStack(request, session);

			return (mapping.findForward(Constants.TARGET));
		} catch (Exception e)
		{
			log.error("Manage Workload Action Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageWorkloadAction", "load"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			return (new ActionForward(mapping.getInput()));
		}
	}

	public ActionForward loadAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		try
		{
			// Obtain the idWorker from the request object as this becomes the
			// source
			// agent.
			String idWorker = (String) request.getParameter("idWorker");

			Locale locale = getLocale(request);
			MessageResources messages = getResources(request);
			HttpSession session = request.getSession();

			if (idWorker == null)
			{ // Go to the failure page
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("manageUser.error.workerRequired"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				Object obj = HomeHelper.singleton().getHome("ecsts.CSUserLocalHome");
				CSUserLocalHome userLocalHome = (CSUserLocalHome) obj;

				Object obj1 = HomeHelper.singleton().getHome("ecsts.CSProcessorLocalHome");
				CSProcessorLocalHome processorLocalHome = (CSProcessorLocalHome) obj1;
				int approvalCount = processorLocalHome.countApproval(idWorker);
				int outstandingCount = processorLocalHome.countOutstanding(idWorker);
				int completedCount = processorLocalHome.countCompleted(idWorker);

				CSUserLocal user = userLocalHome.findByPrimaryKey(idWorker);
				UserEntityBean userEntityBean = user.getUserEntityBean();

				ManageWorkloadForm wkForm = new ManageWorkloadForm();
				wkForm.setWorkerid(userEntityBean.getIdWrkr());
				wkForm.setNmWorker(userEntityBean.getNmWrkr());
				wkForm.setNbrApproval(approvalCount);
				wkForm.setNbrCompleted(completedCount);
				wkForm.setNbrOutstanding(outstandingCount);

				/*
				 * RK CT# 527158 08/19/04 - Remove agent must switch the accept
				 * workload indicator so that the agent can no longer accept
				 * workload.
				 */
				log.info("Agent " + idWorker + " is being inactivated. ");
				userEntityBean.setCdAccptWrkld("N");
				user.setUserEntityBean(userEntityBean);
				log.info("Agent " + idWorker + " can no longer accept workload.");

				// Load all the available agents who can accept workload

				Collection c = userLocalHome.findAllUsers();
				Iterator i = c.iterator();
				while (i.hasNext())
				{
					user = (CSUserLocal) i.next();
					UserBean ub = createUserBean(user);
					approvalCount = processorLocalHome.countApproval(ub.getIdWorker());
					outstandingCount = processorLocalHome.countOutstanding(ub.getIdWorker());
					completedCount = processorLocalHome.countCompleted(ub.getIdWorker());
					ub.setNbrApproval(approvalCount);
					ub.setNbrCompleted(completedCount);
					ub.setNbrOutstanding(outstandingCount);
					if (ub.getCdAccptWrkld().equals("Y"))
					{
						wkForm.addUser(ub);
					}
					System.out.println("Successfully Found all CSUserLocal EJB ");
				}
				form = wkForm;
			} catch (javax.ejb.FinderException fe)
			{
				log.fatal("Manage Workload Action Failed. Database Exception resulted from EJB");
				new ApplicationException(fe.getMessage(), fe, new ErrorDescriptor("ManageWorkloadAction", "loadAll"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (NamingException ne)
			{
				log.fatal("Manage Workload Action Failed. EJB not found");

				new ApplicationException(ne.getMessage(), ne, new ErrorDescriptor("ManageWorkloadAction", "loadAll"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			if ("request".equals(mapping.getScope()))
				request.setAttribute(mapping.getAttribute(), form);
			else
			{
				session.setAttribute(mapping.getAttribute(), form);
			}

			saveToken(request);

			/**
			 * Logic for return functionality
			 */
			new ManageServletStack().addToStack(request, session);

			return (mapping.findForward(Constants.TARGET));
		} catch (Exception e)
		{
			log.error("Manage Workload Action Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageWorkloadAction", "loadAll"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			return (new ActionForward(mapping.getInput()));
		}
	}

	public ActionForward select(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		System.out.println("ManageWorkloadAction select");
		try
		{
			Locale locale = getLocale(request);
			MessageResources messages = getResources(request);
			HttpSession session = request.getSession();

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				Object obj = HomeHelper.singleton().getHome("ecsts.CSUserLocalHome");
				CSUserLocalHome userLocalHome = (CSUserLocalHome) obj;

				Object obj1 = HomeHelper.singleton().getHome("ecsts.CSProcessorLocalHome");
				CSProcessorLocalHome processorLocalHome = (CSProcessorLocalHome) obj1;

				ManageWorkloadForm wkForm = new ManageWorkloadForm();

				Collection c = userLocalHome.findAllUsers();
				Iterator i = c.iterator();
				while (i.hasNext())
				{
					CSUserLocal user = (CSUserLocal) i.next();
					UserBean ub = createUserBean(user);
					int approvalCount = processorLocalHome.countApproval(ub.getIdWorker());
					int outstandingCount = processorLocalHome.countOutstanding(ub.getIdWorker());
					int completedCount = processorLocalHome.countCompleted(ub.getIdWorker());
					ub.setNbrApproval(approvalCount);
					ub.setNbrCompleted(completedCount);
					ub.setNbrOutstanding(outstandingCount);
					wkForm.addUser(ub);
				}

				form = wkForm;
				String[] selections = new String[c.size()];

				for (int j = 0; j < selections.length; j++)
				{
					selections[j] = "";
				}

				wkForm.setSelectedWorkers(selections);
			} catch (javax.ejb.FinderException fe)
			{
				log.fatal("Manage Workload Action Failed. Database Exception in EJB");
				new ApplicationException(fe.getMessage(), fe, new ErrorDescriptor("ManageWorkloadAction", "select"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (NamingException ne)
			{
				log.fatal("Manage Workload Action Failed. EJB Not Found");

				new ApplicationException(ne.getMessage(), ne, new ErrorDescriptor("ManageWorkloadAction", "select"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			if ("request".equals(mapping.getScope()))
				request.setAttribute(mapping.getAttribute(), form);
			else
			{
				session.setAttribute(mapping.getAttribute(), form);
			}

			saveToken(request);

			/**
			 * Logic for return functionality
			 */
			new ManageServletStack().addToStack(request, session);

			return (mapping.findForward(Constants.SOURCE));
		} catch (Exception e)
		{
			log.error("Manage Workload Action Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageWorkloadAction", "select"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			return (new ActionForward(mapping.getInput()));
		}
	}

	public ActionForward transfer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		try
		{
			Locale locale = getLocale(request);
			MessageResources messages = getResources(request);
			HttpSession session = request.getSession();

			ManageWorkloadForm wkForm = (ManageWorkloadForm) form;

			if (wkForm.getCalculate() == 1)
			{
				return recalculate(mapping, form, request, response);
			}

			if (wkForm == null)
			{
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("manageUser.error.workerRequired"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			String[] selection = wkForm.getSelectedWorkers();

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				Object obj = HomeHelper.singleton().getHome("ecsts.CSUserLocalHome");
				CSUserLocalHome userLocalHome = (CSUserLocalHome) obj;

				Object obj1 = HomeHelper.singleton().getHome("ecsts.CSProcessorLocalHome");
				CSProcessorLocalHome processorLocalHome = (CSProcessorLocalHome) obj1;
				int srcApprovalCount = processorLocalHome.countApproval(wkForm.getWorkerid());
				int srcOutstandingCount = processorLocalHome.countOutstanding(wkForm.getWorkerid());
				// int completedCount =
				// processorLocalHome.countCompleted(wkForm.getWorkerid());

				// Find the Source User EJB
				CSUserLocal srcUser = userLocalHome.findByPrimaryKey(wkForm.getWorkerid());
				UserEntityBean srcUserEntityBean = srcUser.getUserEntityBean();

				// int srcOutstandingCount =
				// srcUserEntityBean.getNbOutstanding();
				// int srcApprovalCount = srcUserEntityBean.getNbApproval();

				int totalOutstanding = 0;
				int totalApproval = 0;

				/**
				 * Validate the Source User Counts with sum of all selected
				 * target user counts Error Checking
				 */
				for (int j = 0; j < selection.length; j++)
				{

					log.debug("Checking Totals...");

					// If the selection is blank, get the next selected worker
					if (selection[j].equals(""))
					{
						continue;
					}

					// Get the target user
					String idWorker = selection[j];
					CSUserLocal targetUser = userLocalHome.findByPrimaryKey(idWorker);

					wkForm.addUser(createUserBean(targetUser));

					log.debug("Adding transfer total for agent: " + idWorker);

					int trgOutstandingCount = Integer.parseInt(request.getParameter("workloadForm.pctOutstanding[" + j + "]"));
					log.debug("User is requesting transfer of " + trgOutstandingCount + " outstanding tasks to " + idWorker);
					if (trgOutstandingCount > 0)
					{
						totalOutstanding += trgOutstandingCount;
					}
					int trgApprovalCount = Integer.parseInt(request.getParameter("workloadForm.pctPending[" + j + "]"));
					log.debug("User is requesting transfer of " + trgApprovalCount + " pending tasks to " + idWorker);

					if (trgApprovalCount > 0)
					{
						totalApproval += trgApprovalCount;
					}
					Vector v = wkForm.getTargetUsers();
					for (int k = 0; k < v.size(); k++)
					{
						org.dhhs.dirm.acts.cs.beans.UserBean user = (org.dhhs.dirm.acts.cs.beans.UserBean) v.elementAt(k);
						if (user.getIdWorker().equals(idWorker))
						{
							user.setPctPending(trgApprovalCount);
							user.setPctOutstanding(trgOutstandingCount);
						}
					}
				}

				ActionErrors errors = new ActionErrors();

				// If total outstanding count is greater than source worker
				// outstanding count
				if (totalOutstanding > srcOutstandingCount)
				{
					log.debug("Outstanding Count greater than source ");
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.workload.outstandingcount"));
				}

				// If total pending count is greater than source worker pending
				// count
				if (totalApproval > srcApprovalCount)
				{
					log.debug("Pending Count greater than source ");
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.workload.approvalcount"));
				}

				// If errors, return to input
				if (!errors.isEmpty())
				{
					log.debug("Found Errors, returning to input");
					saveErrors(request, errors);
					return (mapping.findForward(Constants.VALIDATIONERROR));
				}

				/**
				 * For each selection of target user(s), obtain the user EJB and
				 * update the counts Also, obtain all the tasks for each user
				 * and change the target user id
				 */
				log.info("Validation Successful. Proceeding with transfer...");
				log.info("Source User: " + srcUser.getIdWrkr());

				for (int j = 0; j < selection.length; j++)
				{
					// If the selection is blank, get the next selected worker
					if (selection[j].equals(""))
					{
						continue;
					}

					// Get the target user
					String idWorker = selection[j];

					log.info("Target User: " + idWorker);

					CSUserLocal targetUser = userLocalHome.findByPrimaryKey(idWorker);

					// Update the target user count and also the source count
					int trgOutstandingCount = Integer.parseInt(request.getParameter("workloadForm.pctOutstanding[" + j + "]"));
					int trgApprovalCount = Integer.parseInt(request.getParameter("workloadForm.pctPending[" + j + "]"));

					log.info("Source User Outstanding Count: " + srcOutstandingCount);
					log.info("Target User Outstanding Count: " + trgOutstandingCount);

					targetUser.setNbOutstanding(trgOutstandingCount + targetUser.getNbOutstanding());
					srcUser.setNbOutstanding(srcOutstandingCount - trgOutstandingCount);

					targetUser.setNbApproval(trgApprovalCount + targetUser.getNbApproval());
					srcUser.setNbApproval(srcApprovalCount - trgApprovalCount);

					// Get the source user tasks
					Collection c = processorLocalHome.findWorkerTasks(srcUser.getIdWrkr());
					Iterator it = c.iterator();
					while (it.hasNext())
					{
						// Get the task and if it is outstanding, reassign it to
						// the next agent
						CSProcessorLocal srcTask = (CSProcessorLocal) it.next();
						TaskEntityBean srcTaskEntityBean = srcTask.getTaskEntityBean();

						log.info("Selected Src User Task #: " + srcTaskEntityBean.getIdReference());
						if (trgOutstandingCount > 0 && !srcTaskEntityBean.getCdStatus().equals("CLSD") && !srcTaskEntityBean.getCdStatus().equals("REQR") && !srcTaskEntityBean.getCdStatus().equals("APRV"))
						{

							TaskFormBean tb = new TaskFormBean();
							tb.setIdReference(srcTaskEntityBean.getIdReference());
							tb.setCdType(srcTaskEntityBean.getCdType());
							tb.setCdStatus("TRAN");
							tb.setNbTelAcd(srcTaskEntityBean.getNbTelACD());
							tb.setNbTelExc(srcTaskEntityBean.getNbTelEXC());
							tb.setNbTelLn(srcTaskEntityBean.getNbTelLN());
							tb.setNbTelExt(srcTaskEntityBean.getNbTelEXT());
							tb.setNbCase(srcTaskEntityBean.getNbCase());
							tb.setIdEmail(srcTaskEntityBean.getIdEmail());
							tb.setIdPart(srcTaskEntityBean.getIdPart());
							tb.setNbSSN(srcTaskEntityBean.getNbSSN());
							tb.setIdWorker(idWorker);
							tb.setNbDocket(srcTaskEntityBean.getNbDocket());
							tb.setNbControl(srcTaskEntityBean.getNbControl());
							tb.setNmCounty(srcTaskEntityBean.getNmCounty());
							tb.setNmCustomerFirst(srcTaskEntityBean.getNmCustomerFirst());
							tb.setNmCustomerLast(srcTaskEntityBean.getNmCustomerLast());
							tb.setNmCustomerMi(srcTaskEntityBean.getNmCustomerMi());
							tb.setNmCustParFirst(srcTaskEntityBean.getNmCustParFirst());
							tb.setNmCustParLast(srcTaskEntityBean.getNmCustParLast());
							tb.setNmCustParMi(srcTaskEntityBean.getNmCustParMi());
							tb.setNmNonCustParFirst(srcTaskEntityBean.getNmNonCustParFirst());
							tb.setNmNonCustParLast(srcTaskEntityBean.getNmNonCustParLast());
							tb.setNmNonCustParMi(srcTaskEntityBean.getNmNonCustParMi());
							tb.setNmRefSource1(srcTaskEntityBean.getNmRefSource1());
							tb.setNmRefSource2(srcTaskEntityBean.getNmRefSource2());
							tb.setNmRefSource3(srcTaskEntityBean.getNmRefSource3());
							Vector frmtrk = srcTaskEntityBean.getFrmTrack();
							TaskHistoryBean thb = (TaskHistoryBean) frmtrk.elementAt(0);
							tb.setNtResolution(thb.getNotes());
							processorLocalHome.createTaskHistory(tb);

							tb.setCdStatus("OPEN");
							processorLocalHome.createTaskHistory(tb);

							log.info("Transferred Src User Task #: " + srcTaskEntityBean.getIdReference() + " to " + idWorker);
							trgOutstandingCount--;

							/*
							 * Vector frmtrk = srcTask.getFrmTrack();
							 * TaskHistoryBean thb =
							 * (TaskHistoryBean)frmtrk.elementAt(0);
							 * thb.setIdWrkrAssign(idWorker);
							 * srcTask.setFrmTrack(frmtrk); log.info(
							 * "Transferred Src User Task #: " +
							 * srcTask.getIdReference() + " to " + idWorker);
							 * trgOutstandingCount--;
							 */
						}

						if (trgApprovalCount > 0 && (srcTaskEntityBean.getCdStatus().equals("REQR") || (srcTaskEntityBean.getCdStatus().equals("APRV"))))
						{
							TaskFormBean tb = new TaskFormBean();
							tb.setIdReference(srcTaskEntityBean.getIdReference());
							tb.setCdType(srcTaskEntityBean.getCdType());
							tb.setCdStatus("TRAN");
							tb.setNbTelAcd(srcTaskEntityBean.getNbTelACD());
							tb.setNbTelExc(srcTaskEntityBean.getNbTelEXC());
							tb.setNbTelLn(srcTaskEntityBean.getNbTelLN());
							tb.setNbTelExt(srcTaskEntityBean.getNbTelEXT());
							tb.setNbCase(srcTaskEntityBean.getNbCase());
							tb.setIdEmail(srcTaskEntityBean.getIdEmail());
							tb.setIdPart(srcTaskEntityBean.getIdPart());
							tb.setIdWorker(idWorker);
							tb.setNbDocket(srcTaskEntityBean.getNbDocket());
							tb.setNbControl(srcTaskEntityBean.getNbControl());
							tb.setNmCounty(srcTaskEntityBean.getNmCounty());
							tb.setNmCustomerFirst(srcTaskEntityBean.getNmCustomerFirst());
							tb.setNmCustomerLast(srcTaskEntityBean.getNmCustomerLast());
							tb.setNmCustomerMi(srcTaskEntityBean.getNmCustomerMi());
							tb.setNmCustParFirst(srcTaskEntityBean.getNmCustParFirst());
							tb.setNmCustParLast(srcTaskEntityBean.getNmCustParLast());
							tb.setNmCustParMi(srcTaskEntityBean.getNmCustParMi());
							tb.setNmNonCustParFirst(srcTaskEntityBean.getNmNonCustParFirst());
							tb.setNmNonCustParLast(srcTaskEntityBean.getNmNonCustParLast());
							tb.setNmNonCustParMi(srcTaskEntityBean.getNmNonCustParMi());
							tb.setNmRefSource1(srcTaskEntityBean.getNmRefSource1());
							tb.setNmRefSource2(srcTaskEntityBean.getNmRefSource2());
							tb.setNmRefSource3(srcTaskEntityBean.getNmRefSource3());
							Vector frmtrk = srcTaskEntityBean.getFrmTrack();
							TaskHistoryBean thb = (TaskHistoryBean) frmtrk.elementAt(0);
							tb.setNtResolution(thb.getNotes());
							processorLocalHome.createTaskHistory(tb);

							tb.setCdStatus(srcTaskEntityBean.getCdStatus());
							processorLocalHome.createTaskHistory(tb);

							log.info("Transferred Src User Task #: " + srcTaskEntityBean.getIdReference() + " to " + idWorker);
							trgApprovalCount--;

							/*
							 * Vector frmtrk = srcTask.getFrmTrack();
							 * TaskHistoryBean thb = (TaskHistoryBean)
							 * frmtrk.elementAt(0);
							 * thb.setIdWrkrAssign(idWorker);
							 * srcTask.setFrmTrack(frmtrk); log.info(
							 * "Transferred Src User Task #: " +
							 * srcTask.getIdReference() + " to " + idWorker);
							 * trgApprovalCount--;
							 */
						}
					}
				}

				// wkForm.setTargetUsers(totalList);
				wkForm.setNbrApproval(srcUserEntityBean.getNbApproval());
				wkForm.setNbrCompleted(srcUserEntityBean.getNbCompleted());
				wkForm.setNmWorker(srcUserEntityBean.getNmWrkr());
				wkForm.setNbrOutstanding(srcUserEntityBean.getNbOutstanding());

			} catch (javax.ejb.FinderException fe)
			{
				log.fatal("Manage Workload Action Failed. Database Exception resulted from EJB");
				new ApplicationException(fe.getMessage(), fe, new ErrorDescriptor("ManageWorkloadAction", "transfer"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (NamingException ne)
			{
				log.fatal("Manage Workload Action Failed. EJB not found");

				new ApplicationException(ne.getMessage(), ne, new ErrorDescriptor("ManageWorkloadAction", "transfer"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			if ("request".equals(mapping.getScope()))
				request.setAttribute(mapping.getAttribute(), form);
			else
			{
				session.setAttribute(mapping.getAttribute(), form);
			}

			saveToken(request);

			return (mapping.findForward(Constants.TRANSFER));
		} catch (Exception e)
		{
			log.error("Manage Workload Action Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageWorkloadAction", "transfer"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			return (new ActionForward(mapping.getInput()));
		}
	}

	public ActionForward recalculate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		try
		{
			Locale locale = getLocale(request);
			MessageResources messages = getResources(request);
			HttpSession session = request.getSession();

			ManageWorkloadForm wkForm = (ManageWorkloadForm) form;

			if (wkForm == null)
			{
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("manageUser.error.workerRequired"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			String[] selection = wkForm.getSelectedWorkers();
			Vector totalList = new Vector();

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				Object obj = HomeHelper.singleton().getHome("ecsts.CSUserLocalHome");
				CSUserLocalHome userLocalHome = (CSUserLocalHome) obj;

				Object obj1 = HomeHelper.singleton().getHome("ecsts.CSProcessorLocalHome");
				CSProcessorLocalHome processorLocalHome = (CSProcessorLocalHome) obj1;

				int srcApprovalCount = processorLocalHome.countApproval(wkForm.getWorkerid());
				int srcOutstandingCount = processorLocalHome.countOutstanding(wkForm.getWorkerid());
				int srcCompletedCount = processorLocalHome.countCompleted(wkForm.getWorkerid());

				CSUserLocal srcUser = userLocalHome.findByPrimaryKey(wkForm.getWorkerid());
				UserEntityBean srcUserEntityBean = srcUser.getUserEntityBean();

				UserBean srcUserBean = createUserBean(srcUser);
				srcUserBean.setNbrApproval(srcApprovalCount);
				srcUserBean.setNbrOutstanding(srcOutstandingCount);
				srcUserBean.setNbrCompleted(srcCompletedCount);

				for (int i = 0; i < selection.length; i++)
				{
					if (selection[i].equals(""))
					{
						continue;
					}

					String idWorker = selection[i];
					CSUserLocal targetUser = userLocalHome.findByPrimaryKey(idWorker);
					UserEntityBean targetUserEntityBean = targetUser.getUserEntityBean();
					org.dhhs.dirm.acts.cs.beans.UserBean targetUserBean = createUserBean(targetUser);

					targetUserBean.setNbrApproval(processorLocalHome.countApproval(targetUserBean.getIdWorker()));
					targetUserBean.setNbrOutstanding(processorLocalHome.countOutstanding(targetUserBean.getIdWorker()));
					targetUserBean.setNbrCompleted(processorLocalHome.countCompleted(targetUserBean.getIdWorker()));

					int trgOutstandingCount = Integer.parseInt(request.getParameter("workloadForm.pctOutstanding[" + i + "]"));
					targetUserBean.setNbrOutstanding(trgOutstandingCount + targetUserBean.getNbrOutstanding());
					srcUserBean.setNbrOutstanding(srcOutstandingCount - trgOutstandingCount);
					totalList.addElement(targetUserBean);
				}
				wkForm.setTargetUsers(totalList);
				wkForm.setNbrApproval(srcUserBean.getNbrApproval());
				wkForm.setNbrCompleted(srcUserBean.getNbrApproval());
				wkForm.setNmWorker(srcUserBean.getNmWorker());
				wkForm.setNbrOutstanding(srcUserBean.getNbrOutstanding());

			} catch (javax.ejb.FinderException fe)
			{
				log.fatal("Manage Workload Action Failed. Database Exception resulted from EJB");
				new ApplicationException(fe.getMessage(), fe, new ErrorDescriptor("ManageWorkloadAction", "recalculate"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (NamingException ne)
			{
				log.fatal("Manage Workload Action Failed. EJB not found");

				new ApplicationException(ne.getMessage(), ne, new ErrorDescriptor("ManageWorkloadAction", "recalculate"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			if ("request".equals(mapping.getScope()))
				request.setAttribute(mapping.getAttribute(), form);
			else
			{
				session.setAttribute(mapping.getAttribute(), form);
			}

			saveToken(request);

			return (mapping.findForward(Constants.TARGET));
		} catch (Exception e)
		{
			log.error("Manage Workload Action Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageWorkloadAction", "recalculate"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			return (new ActionForward(mapping.getInput()));
		}
	}
}
