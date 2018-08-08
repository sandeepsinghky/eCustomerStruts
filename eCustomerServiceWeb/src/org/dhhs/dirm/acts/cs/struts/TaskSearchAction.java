

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
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dhhs.dirm.acts.cs.Constants;
import org.dhhs.dirm.acts.cs.beans.TaskBean;
import org.dhhs.dirm.acts.cs.beans.TaskEntityBean;
import org.dhhs.dirm.acts.cs.beans.TaskHistoryBean;
import org.dhhs.dirm.acts.cs.businesslogic.BusinessLogicException;
import org.dhhs.dirm.acts.cs.businesslogic.TaskManager;
import org.dhhs.dirm.acts.cs.ejb.CSProcessorLocal;
import org.dhhs.dirm.acts.cs.ejb.CSProcessorLocalHome;
import org.dhhs.dirm.acts.cs.formbeans.ManageTaskForm;
import org.dhhs.dirm.acts.cs.helpers.HomeHelper;
import org.dhhs.dirm.acts.cs.util.ApplicationException;
import org.dhhs.dirm.acts.cs.util.ErrorDescriptor;

/**
 * TaskSearchAction.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Nov 15, 2003 12:29:26 PM
 * 
 * @author Rkodumagulla
 *
 */
public class TaskSearchAction extends Action
{

	private final static Logger log = Logger.getLogger(TaskSearchAction.class);

	private TaskBean createTaskBean(CSProcessorLocal task, String owner)
	{

		log.debug("Creating task bean");
		TaskBean taskBean = new TaskBean();
		TaskEntityBean taskEntityBean = task.getTaskEntityBean();
		/**
		 * MOD# 2872 - rkodumagulla 09/07/2004. If the task belongs to the
		 * logged in agent, then the owner flag must be true, else, the agent
		 * must be only given the view option on the list screen
		 */
		if (taskEntityBean.getIdWorker().equals(owner))
		{
			taskBean.setOwner(true);
		} else
		{
			taskBean.setOwner(false);
		}
		taskBean.setIdReference(taskEntityBean.getIdReference());
		taskBean.setTsCreate(taskEntityBean.getTsCreate());
		taskBean.setTsUpdate(taskEntityBean.getTsUpdate());
		taskBean.setNbCase(taskEntityBean.getNbCase());
		taskBean.setIdEmail(taskEntityBean.getIdEmail());
		taskBean.setIdPart(taskEntityBean.getIdPart());
		taskBean.setNbSSN(taskEntityBean.getNbSSN());
		taskBean.setIdWorker(taskEntityBean.getIdWorker());
		taskBean.setNmWorker(taskEntityBean.getNmWorker());
		taskBean.setCdType(taskEntityBean.getCdType());
		taskBean.setNbTelACD(taskEntityBean.getNbTelACD());
		taskBean.setNbTelEXC(taskEntityBean.getNbTelEXC());
		taskBean.setNbTelLN(taskEntityBean.getNbTelLN());
		taskBean.setNbTelEXT(taskEntityBean.getNbTelEXT());
		taskBean.setNbDocket(taskEntityBean.getNbDocket());
		taskBean.setNmCounty(taskEntityBean.getNmCounty());
		taskBean.setNbControl(taskEntityBean.getNbControl());
		taskBean.setNmCustomerLast(taskEntityBean.getNmCustomerLast());
		taskBean.setNmCustomerFirst(taskEntityBean.getNmCustomerFirst());
		taskBean.setNmCustomerMi(taskEntityBean.getNmCustomerMi());
		taskBean.setNmCustParFirst(taskEntityBean.getNmCustParFirst());
		taskBean.setNmCustParLast(taskEntityBean.getNmCustParLast());
		taskBean.setNmCustParMi(taskEntityBean.getNmCustParMi());
		taskBean.setNmNonCustParFirst(taskEntityBean.getNmNonCustParFirst());
		taskBean.setNmNonCustParLast(taskEntityBean.getNmNonCustParLast());
		taskBean.setNmNonCustParMi(taskEntityBean.getNmNonCustParMi());
		taskBean.setNmRefSource1(taskEntityBean.getNmRefSource1());
		taskBean.setNmRefSource2(taskEntityBean.getNmRefSource2());
		taskBean.setNmRefSource3(taskEntityBean.getNmRefSource3());
		taskBean.setNmRefSource4(taskEntityBean.getNmRefSource4());

		Vector v = new Vector();

		log.debug(" Size of Vector returned from taskEntityBean : " + taskEntityBean.getFrmTrack().size() + " with id reference " + taskEntityBean.getIdReference());

		v.addElement(taskEntityBean.getFrmTrack().elementAt(0));
		taskBean.setFrmTrack(v);

		TaskHistoryBean thb = (TaskHistoryBean) taskEntityBean.getFrmTrack().elementAt(0);
		taskBean.setCdStatus(thb.getCdStatus());
		log.debug("Created task bean");

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
	 * @see org.apache.struts.action.Action#execute(ActionMapping, ActionForm,
	 *      HttpServletRequest, HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{

		try
		{

			ManageTaskForm taskForm = (ManageTaskForm) form;
			int searchType = 0;

			boolean blnFilterIdRef = false;
			boolean blnFilterWrkr = false;
			boolean blnFilterCase = false;
			boolean blnFilterPart = false;
			boolean blnFilterDocket = false;
			boolean blnFilterEmail = false;
			boolean blnFilterSSN = false;
			boolean blnFilterForm = false;
			boolean blnOutstanding = false;
			boolean blnApproval = false;
			boolean blnCompleted = false;

			boolean blnCreateDt = false;
			boolean blnCompleteDt = false;
			boolean blnDueDt = false;

			boolean blnFilterCustomerLname = false;
			boolean blnFilterCustomerFname = false;

			boolean blnViewOtherAgentTasks = false;
			boolean blnAgentSearchOverride = false;

			HttpSession session = request.getSession();

			/**
			 * Get the UserBean from the Session and check for profile of the
			 * user to limit the task records only to the current logged in
			 * worker if the user profile does not have manageAll or manageUser
			 * options
			 */
			org.dhhs.dirm.acts.cs.beans.UserBean loggedInUser = (org.dhhs.dirm.acts.cs.beans.UserBean) session.getAttribute(Constants.USERBEAN_KEY);

			String loggedInWorkerID = loggedInUser.getIdWorker();

			if (loggedInUser.isManageAll() || loggedInUser.isManageUsers())
			{
				blnViewOtherAgentTasks = true;
			}

			log.info("Start Time for Task Search " + new Date());

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

			if (taskForm.getNbCase() != null)
			{
				if (!taskForm.getNbCase().equals(""))
				{
					blnFilterCase = true;
				}
			}

			if (taskForm.getIdPart() != null)
			{
				if (!taskForm.getIdPart().equals(""))
				{
					blnFilterPart = true;
				}
			}

			if (taskForm.getIdEmail() != null)
			{
				if (!taskForm.getIdEmail().equals(""))
				{
					blnFilterEmail = true;
				}
			}

			if (taskForm.getNmAuthorLast() != null)
			{
				if (!taskForm.getNmAuthorLast().equals(""))
				{
					blnFilterCustomerLname = true;
				}
			}

			if (taskForm.getNmAuthorFirst() != null)
			{
				if (!taskForm.getNmAuthorFirst().equals(""))
				{
					blnFilterCustomerFname = true;
				}
			}

			if (taskForm.getNbDocket() != null)
			{
				if (!taskForm.getNbDocket().equals(""))
				{
					blnFilterDocket = true;
				}
			}

			if (taskForm.getCdType() != null && (!taskForm.getCdType().equals("")))
			{
				blnFilterForm = true;
			}

			if (taskForm.getDtReceived() != null)
			{
				if (!taskForm.getDtReceived().equals(""))
				{
					blnCreateDt = true;
				}
			}

			if (taskForm.getDtCompleted() != null)
			{
				if (!taskForm.getDtCompleted().equals(""))
				{
					blnCompleteDt = true;
				}
			}

			if (taskForm.getDtDue() != null)
			{
				if (!taskForm.getDtDue().equals(""))
				{
					blnDueDt = true;
				}
			}

			if (taskForm.isChkApproval())
			{
				blnApproval = true;
			}

			if (taskForm.isChkOutstanding())
			{
				blnOutstanding = true;
			}

			if (taskForm.isChkCompleted())
			{
				blnCompleted = true;
			}

			if (taskForm.isAgentSearchOverride())
			{
				blnAgentSearchOverride = true;
			}

			Vector tasks = new Vector();

			/**
			 * Performance Changes. Use TaskManager instead of the EJB for
			 * search
			 */
			TaskManager taskManager = TaskManager.getInstance();

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				Object obj = HomeHelper.singleton().getHome("ecsts.CSProcessorLocalHome");
				CSProcessorLocalHome processorLocalHome = (CSProcessorLocalHome) obj;

				if (blnFilterIdRef)
				{
					log.info("Search by Reference ID ");
					CSProcessorLocal task = processorLocalHome.findByPrimaryKey(taskForm.getIdReference());
					TaskEntityBean taskEntityBean = task.getTaskEntityBean();

					log.debug("Found task from ejb: " + task);

					if (blnViewOtherAgentTasks)
					{
						tasks.addElement(createTaskBean(task, loggedInWorkerID));
					} else
					{
						if (task != null)
						{
							if (blnAgentSearchOverride)
							{
								tasks.addElement(createTaskBean(task, loggedInWorkerID));
							} else
							{
								if (taskEntityBean.getIdWorker().trim().equals(loggedInWorkerID))
								{
									tasks.addElement(createTaskBean(task, loggedInWorkerID));
								}
							}
							/*
							 * MOD# 2872 - rkodumagulla 09/07/2004. Allow agents
							 * to view other agents tasks, but not modify them
							 * if (taskEntityBean.getIdWorker().trim().equals(
							 * loggedInWorkerID)) { log.debug(
							 * "Compared the logged in worker with task worker id"
							 * ); tasks.addElement(createTaskBean(task,
							 * loggedInWorkerID)); log.debug(
							 * "Added task to list"); } else { log.debug(
							 * "Task found by reference id " +
							 * taskForm.getIdReference() +
							 * " but belongs to another user"); ActionErrors
							 * errors = new ActionErrors();
							 * errors.add(ActionErrors.GLOBAL_ERROR, new
							 * ActionError("error.task.notauthorized"));
							 * saveErrors(request, errors); return
							 * (mapping.findForward(Constants.SUCCESS)); }
							 */
						}
					}
					log.info("Successfully Found CSProcessorLocal EJB using Reference ID:" + taskForm.getIdReference());
				} else if (blnCreateDt)
				{
					log.info("Search by Create Date");
					java.util.Date parsedDate = null;
					if (taskForm.getDtReceived().length() == 10)
					{
						try
						{
							java.text.DateFormat df = new java.text.SimpleDateFormat("MM/dd/yyyy");
							parsedDate = df.parse(taskForm.getDtReceived());
							log.info("Parsed User entered Date " + parsedDate);
						} catch (java.text.ParseException pe)
						{
							log.info("Task Search Failed. Invalid Create Date Format Entered");
							ActionErrors errors = new ActionErrors();
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.date", "Date Received"));
							saveErrors(request, errors);
							return (new ActionForward(mapping.getInput()));
						}
					} else if (taskForm.getDtReceived().length() == 8)
					{
						try
						{
							java.text.DateFormat df = new java.text.SimpleDateFormat("MMddyyyy");
							parsedDate = df.parse(taskForm.getDtReceived());
							log.info("Parsed User entered Date " + parsedDate);
						} catch (java.text.ParseException pe)
						{
							log.info("Task Search Failed. Invalid Create Date Format Entered");
							ActionErrors errors = new ActionErrors();
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.date", "Date Received"));
							saveErrors(request, errors);
							return (new ActionForward(mapping.getInput()));
						}
					} else
					{
						log.info("Task Search Failed. Invalid Create Date Format Entered");
						ActionErrors errors = new ActionErrors();
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.date", "Date Received"));
						saveErrors(request, errors);
						return (new ActionForward(mapping.getInput()));
					}
					Collection c = null;
					if (blnViewOtherAgentTasks)
					{
						if (blnFilterWrkr)
						{
							// 11/22/04 c =
							// processorLocalHome.findTasksByCreateDate(new
							// java.sql.Date(parsedDate.getTime()),
							// taskForm.getIdWorker());
							c = taskManager.findTasksByCreateDate(new java.sql.Date(parsedDate.getTime()), taskForm.getIdWorker());
						} else
						{
							// 11/22/04 c =
							// processorLocalHome.findTasksByCreateDate(new
							// java.sql.Date(parsedDate.getTime()));
							c = taskManager.findTasksByCreateDate(new java.sql.Date(parsedDate.getTime()));
						}
					} else
					{
						// MOD # 2872 - rkodumagulla 09/07/2004.
						if (blnAgentSearchOverride)
						{
							// 11/22/04 c =
							// processorLocalHome.findTasksByCreateDate(new
							// java.sql.Date(parsedDate.getTime()));
							c = taskManager.findTasksByCreateDate(new java.sql.Date(parsedDate.getTime()));
						} else
						{
							// 11/22/04 c =
							// processorLocalHome.findTasksByCreateDate(new
							// java.sql.Date(parsedDate.getTime()),
							// loggedInWorkerID);
							c = taskManager.findTasksByCreateDate(new java.sql.Date(parsedDate.getTime()), loggedInWorkerID);
						}
					}
					Iterator i = c.iterator();
					while (i.hasNext())
					{
						TaskBean taskBean = (TaskBean) i.next();
						manageTaskBean(taskBean, loggedInWorkerID);

						// rk 11/22/04 CSProcessorLocal task =
						// (CSProcessorLocal) i.next();
						// rk 11/22/04 TaskBean taskBean = createTaskBean(task,
						// loggedInWorkerID);
						// RK CT# 522370 - Apply the referral type filter while
						// searching by dates
						if (blnFilterForm)
						{
							if (!taskForm.getCdType().trim().equals(taskBean.getCdType().trim()))
							{
								continue;
							}
						}
						// RK CT# 522370 - END
						if (applyFilters(form, taskBean, blnFilterCase, blnFilterPart, blnFilterSSN, blnFilterDocket, blnFilterEmail, blnApproval, blnOutstanding, blnCompleted))
						{
							tasks.addElement(taskBean);
						}
					}
					log.info("Successfully Found TaskManager using Create Date :" + taskForm.getDtReceived());
				} else if (blnCompleteDt)
				{
					log.info("Search by Complete Date");
					java.util.Date parsedDate = null;
					if (taskForm.getDtCompleted().length() == 10)
					{
						try
						{
							java.text.DateFormat df = new java.text.SimpleDateFormat("MM/dd/yyyy");
							parsedDate = df.parse(taskForm.getDtCompleted());
							log.info("Parsed User entered Date " + parsedDate);
						} catch (java.text.ParseException pe)
						{
							log.info("Task Search Failed. Invalid Complete Date Format Entered");
							ActionErrors errors = new ActionErrors();
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.date", "Date Complete"));
							saveErrors(request, errors);
							return (new ActionForward(mapping.getInput()));
						}
					} else if (taskForm.getDtCompleted().length() == 8)
					{
						try
						{
							java.text.DateFormat df = new java.text.SimpleDateFormat("MMddyyyy");
							parsedDate = df.parse(taskForm.getDtCompleted());
							log.info("Parsed User entered Date " + parsedDate);
						} catch (java.text.ParseException pe)
						{
							log.info("Task Search Failed. Invalid Complete Date Format Entered");
							ActionErrors errors = new ActionErrors();
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.date", "Date Complete"));
							saveErrors(request, errors);
							return (new ActionForward(mapping.getInput()));
						}
					} else
					{
						log.info("Task Search Failed. Invalid Complete Date Format Entered");
						ActionErrors errors = new ActionErrors();
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.date", "Date Complete"));
						saveErrors(request, errors);
						return (new ActionForward(mapping.getInput()));
					}

					Collection c = null;
					if (blnViewOtherAgentTasks)
					{
						if (blnFilterWrkr)
						{
							// c =
							// processorLocalHome.findTasksByCompleteDate(new
							// java.sql.Date(parsedDate.getTime()),
							// taskForm.getIdWorker());
							c = taskManager.findTasksByCompleteDate(new java.sql.Date(parsedDate.getTime()), taskForm.getIdWorker());
						} else
						{
							// c =
							// processorLocalHome.findTasksByCompleteDate(new
							// java.sql.Date(parsedDate.getTime()));
							c = taskManager.findTasksByCompleteDate(new java.sql.Date(parsedDate.getTime()));
						}
					} else
					{
						// MOD # 2872 - rkodumagulla 09/07/2004.
						if (blnAgentSearchOverride)
						{
							// c =
							// processorLocalHome.findTasksByCompleteDate(new
							// java.sql.Date(parsedDate.getTime()));
							c = taskManager.findTasksByCompleteDate(new java.sql.Date(parsedDate.getTime()));
						} else
						{
							// c =
							// processorLocalHome.findTasksByCompleteDate(new
							// java.sql.Date(parsedDate.getTime()),
							// loggedInWorkerID);
							c = taskManager.findTasksByCompleteDate(new java.sql.Date(parsedDate.getTime()), loggedInWorkerID);
						}
					}
					Iterator i = c.iterator();
					while (i.hasNext())
					{
						TaskBean taskBean = (TaskBean) i.next();
						manageTaskBean(taskBean, loggedInWorkerID);

						// CSProcessorLocal task = (CSProcessorLocal) i.next();
						// TaskBean taskBean = createTaskBean(task,
						// loggedInWorkerID);
						// RK CT# 522370 - Apply the referral type filter while
						// searching by dates
						if (blnFilterForm)
						{
							if (!taskForm.getCdType().trim().equals(taskBean.getCdType().trim()))
							{
								continue;
							}
						}
						// RK CT# 522370 - END
						if (applyFilters(form, taskBean, blnFilterCase, blnFilterPart, blnFilterSSN, blnFilterDocket, blnFilterEmail, blnApproval, blnOutstanding, blnCompleted))
						{
							tasks.addElement(taskBean);
						}
					}
					log.info("Successfully Found TaskManager using Complete Date :" + taskForm.getDtCompleted());
				} else if (blnDueDt)
				{
					log.info("Search by Due Date");
					java.util.Date parsedDate = null;
					if (taskForm.getDtDue().length() == 10)
					{
						try
						{
							java.text.DateFormat df = new java.text.SimpleDateFormat("MM/dd/yyyy");
							parsedDate = df.parse(taskForm.getDtDue());
							log.info("Parsed User entered Date " + parsedDate);
						} catch (java.text.ParseException pe)
						{
							log.info("Task Search Failed. Invalid Due Date Format Entered");
							ActionErrors errors = new ActionErrors();
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.date", "Date Due"));
							saveErrors(request, errors);
							return (new ActionForward(mapping.getInput()));
						}
					} else if (taskForm.getDtDue().length() == 8)
					{
						try
						{
							java.text.DateFormat df = new java.text.SimpleDateFormat("MMddyyyy");
							parsedDate = df.parse(taskForm.getDtDue());
							log.info("Parsed User entered Date " + parsedDate);
						} catch (java.text.ParseException pe)
						{
							log.info("Task Search Failed. Invalid Due Date Format Entered");
							ActionErrors errors = new ActionErrors();
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.date", "Date Due"));
							saveErrors(request, errors);
							return (new ActionForward(mapping.getInput()));
						}
					} else
					{
						log.info("Task Search Failed. Invalid Due Date Format Entered");
						ActionErrors errors = new ActionErrors();
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.date", "Date Due"));
						saveErrors(request, errors);
						return (new ActionForward(mapping.getInput()));
					}
					Collection c = null;
					if (blnViewOtherAgentTasks)
					{
						if (blnFilterWrkr)
						{
							// c = processorLocalHome.findTasksByDueDate(new
							// java.sql.Date(parsedDate.getTime()),
							// taskForm.getIdWorker());
							c = taskManager.findTasksByDueDate(new java.sql.Date(parsedDate.getTime()), taskForm.getIdWorker());
						} else
						{
							// c = processorLocalHome.findTasksByDueDate(new
							// java.sql.Date(parsedDate.getTime()));
							c = taskManager.findTasksByDueDate(new java.sql.Date(parsedDate.getTime()));
						}
					} else
					{
						// MOD # 2872 - rkodumagulla 09/07/2004.
						if (blnAgentSearchOverride)
						{
							// c = processorLocalHome.findTasksByDueDate(new
							// java.sql.Date(parsedDate.getTime()));
							c = taskManager.findTasksByDueDate(new java.sql.Date(parsedDate.getTime()));
						} else
						{
							// c = processorLocalHome.findTasksByDueDate(new
							// java.sql.Date(parsedDate.getTime()),
							// loggedInWorkerID);
							c = taskManager.findTasksByDueDate(new java.sql.Date(parsedDate.getTime()), loggedInWorkerID);
						}
					}
					Iterator i = c.iterator();
					while (i.hasNext())
					{
						TaskBean taskBean = (TaskBean) i.next();
						manageTaskBean(taskBean, loggedInWorkerID);

						// CSProcessorLocal task = (CSProcessorLocal) i.next();
						// TaskBean taskBean = createTaskBean(task,
						// loggedInWorkerID);
						// RK CT# 522370 - Apply the referral type filter while
						// searching by dates
						if (blnFilterForm)
						{
							if (!taskForm.getCdType().trim().equals(taskBean.getCdType().trim()))
							{
								continue;
							}
						}
						// RK CT# 522370 - END
						if (applyFilters(form, taskBean, blnFilterCase, blnFilterPart, blnFilterSSN, blnFilterDocket, blnFilterEmail, blnApproval, blnOutstanding, blnCompleted))
						{
							tasks.addElement(taskBean);
						}
					}
					log.info("Successfully Found CSProcessorLocal EJB using Due Date :" + taskForm.getDtDue());
				} else if (blnFilterForm)
				{
					log.info("Search by ReferralType Type");
					Collection c = null;
					if (blnViewOtherAgentTasks)
					{
						if (blnFilterWrkr)
						{
							// c =
							// processorLocalHome.findTasksByRfrl(taskForm.getCdType(),
							// taskForm.getIdWorker());
							c = taskManager.findTasksByRfrl(taskForm.getCdType(), taskForm.getIdWorker());
						} else
						{
							// c =
							// processorLocalHome.findTasksByRfrl(taskForm.getCdType());
							c = taskManager.findTasksByRfrl(taskForm.getCdType());
						}
					} else
					{
						// MOD # 2872 - rkodumagulla 09/07/2004.
						if (blnAgentSearchOverride)
						{
							// c =
							// processorLocalHome.findTasksByRfrl(taskForm.getCdType());
							c = taskManager.findTasksByRfrl(taskForm.getCdType());
						} else
						{
							// c =
							// processorLocalHome.findTasksByRfrl(taskForm.getCdType(),
							// loggedInWorkerID);
							c = taskManager.findTasksByRfrl(taskForm.getCdType(), loggedInWorkerID);
						}
					}
					Iterator i = c.iterator();
					while (i.hasNext())
					{
						TaskBean taskBean = (TaskBean) i.next();
						manageTaskBean(taskBean, loggedInWorkerID);

						// CSProcessorLocal task = (CSProcessorLocal) i.next();
						// TaskBean taskBean = createTaskBean(task,
						// loggedInWorkerID);
						if (applyFilters(form, taskBean, blnFilterCase, blnFilterPart, blnFilterSSN, blnFilterDocket, blnFilterEmail, blnApproval, blnOutstanding, blnCompleted))
						{
							tasks.addElement(taskBean);
						}
					}
					log.info("Successfully Found TaskManager using Referral Type L" + taskForm.getCdType());
				} else if (blnFilterWrkr)
				{
					log.info("Search by Worker ID");
					TaskManager tm = TaskManager.getInstance();

					Collection c = tm.findWorkerTasks(taskForm.getIdWorker());
					Iterator i = c.iterator();
					while (i.hasNext())
					{
						TaskBean taskBean = (TaskBean) i.next();
						manageTaskBean(taskBean, loggedInWorkerID);
						if (applyFilters(form, taskBean, blnFilterCase, blnFilterPart, blnFilterSSN, blnFilterDocket, blnFilterEmail, blnApproval, blnOutstanding, blnCompleted))
						{
							tasks.addElement(taskBean);
						}
					}
					log.info("Successfully Found TaskManager using Worker ID:" + taskForm.getIdWorker());
				} else if (blnFilterCustomerLname)
				{
					if (blnFilterCustomerFname)
					{
						log.info("Search by Customer Last & First Name");
						Collection c = null;
						if (blnViewOtherAgentTasks)
						{
							if (blnFilterWrkr)
							{
								// c =
								// processorLocalHome.findTasksByCustomerName(taskForm.getNmAuthorLast().toUpperCase(),
								// taskForm.getNmAuthorFirst().toUpperCase(),
								// taskForm.getIdWorker());
								c = taskManager.findTasksByCustomerName(taskForm.getNmAuthorLast().toUpperCase(), taskForm.getNmAuthorFirst().toUpperCase(), taskForm.getIdWorker());
							} else
							{
								// c =
								// processorLocalHome.findTasksByCustomerName(taskForm.getNmAuthorLast().toUpperCase(),
								// taskForm.getNmAuthorFirst().toUpperCase());
								c = taskManager.findTasksByCustomerName(taskForm.getNmAuthorLast().toUpperCase(), taskForm.getNmAuthorFirst().toUpperCase());
							}
						} else
						{
							// MOD # 2872 - rkodumagulla 09/07/2004.
							if (blnAgentSearchOverride)
							{
								// c =
								// processorLocalHome.findTasksByCustomerName(taskForm.getNmAuthorLast().toUpperCase(),
								// taskForm.getNmAuthorFirst().toUpperCase());
								c = taskManager.findTasksByCustomerName(taskForm.getNmAuthorLast().toUpperCase(), taskForm.getNmAuthorFirst().toUpperCase());
							} else
							{
								// c =
								// processorLocalHome.findTasksByCustomerName(taskForm.getNmAuthorLast().toUpperCase(),
								// taskForm.getNmAuthorFirst().toUpperCase(),
								// loggedInWorkerID);
								c = taskManager.findTasksByCustomerName(taskForm.getNmAuthorLast().toUpperCase(), taskForm.getNmAuthorFirst().toUpperCase(), loggedInWorkerID);
							}
						}
						Iterator i = c.iterator();
						while (i.hasNext())
						{
							TaskBean taskBean = (TaskBean) i.next();
							manageTaskBean(taskBean, loggedInWorkerID);
							// CSProcessorLocal task = (CSProcessorLocal)
							// i.next();
							// TaskBean taskBean = createTaskBean(task,
							// loggedInWorkerID);
							if (applyFilters(form, taskBean, false, false, blnFilterSSN, false, false, blnApproval, blnOutstanding, blnCompleted))
							{
								tasks.addElement(taskBean);
							}
						}
						log.info("Successfully Found TaskManager using Customer Name :" + taskForm.getNmAuthor());
					} else
					{
						log.info("Search by Customer Last Name");
						Collection c = null;
						if (blnViewOtherAgentTasks)
						{
							if (blnFilterWrkr)
							{
								// c =
								// processorLocalHome.findTasksByCustomerLName(taskForm.getNmAuthorLast().toUpperCase(),
								// taskForm.getIdWorker());
								c = taskManager.findTasksByCustomerLName(taskForm.getNmAuthorLast().toUpperCase(), taskForm.getIdWorker());
							} else
							{
								// c =
								// processorLocalHome.findTasksByCustomerLName(taskForm.getNmAuthorLast().toUpperCase());
								c = taskManager.findTasksByCustomerLName(taskForm.getNmAuthorLast().toUpperCase());
							}
						} else
						{
							// MOD # 2872 - rkodumagulla 09/07/2004.
							if (blnAgentSearchOverride)
							{
								// c =
								// processorLocalHome.findTasksByCustomerLName(taskForm.getNmAuthorLast().toUpperCase());
								c = taskManager.findTasksByCustomerLName(taskForm.getNmAuthorLast().toUpperCase());
							} else
							{
								// c =
								// processorLocalHome.findTasksByCustomerLName(taskForm.getNmAuthorLast().toUpperCase(),
								// loggedInWorkerID);
								c = taskManager.findTasksByCustomerLName(taskForm.getNmAuthorLast().toUpperCase(), loggedInWorkerID);
							}
						}
						Iterator i = c.iterator();
						while (i.hasNext())
						{
							TaskBean taskBean = (TaskBean) i.next();
							manageTaskBean(taskBean, loggedInWorkerID);

							// CSProcessorLocal task = (CSProcessorLocal)
							// i.next();
							// TaskBean taskBean = createTaskBean(task,
							// loggedInWorkerID);
							if (applyFilters(form, taskBean, false, false, blnFilterSSN, false, false, blnApproval, blnOutstanding, blnCompleted))
							{
								tasks.addElement(taskBean);
							}
						}
						log.info("Successfully Found TaskManager using Customer Last Name :" + taskForm.getNmAuthorLast());
					}
				} else if (blnFilterCase)
				{
					log.info("Search by Case");
					Collection c = null;
					if (blnViewOtherAgentTasks)
					{
						if (blnFilterWrkr)
						{
							// c =
							// processorLocalHome.findTasksByCase(taskForm.getNbCase(),
							// taskForm.getIdWorker());
							c = taskManager.findTasksByCase(taskForm.getNbCase(), taskForm.getIdWorker());
						} else
						{
							// c =
							// processorLocalHome.findTasksByCase(taskForm.getNbCase());
							c = taskManager.findTasksByCase(taskForm.getNbCase());
						}
					} else
					{
						// MOD # 2872 - rkodumagulla 09/07/2004.
						if (blnAgentSearchOverride)
						{
							// c =
							// processorLocalHome.findTasksByCase(taskForm.getNbCase());
							c = taskManager.findTasksByCase(taskForm.getNbCase());
						} else
						{
							// c =
							// processorLocalHome.findTasksByCase(taskForm.getNbCase(),
							// loggedInWorkerID);
							c = taskManager.findTasksByCase(taskForm.getNbCase(), loggedInWorkerID);
						}
					}
					Iterator i = c.iterator();
					while (i.hasNext())
					{
						TaskBean taskBean = (TaskBean) i.next();
						manageTaskBean(taskBean, loggedInWorkerID);

						// CSProcessorLocal task = (CSProcessorLocal) i.next();
						// TaskBean taskBean = createTaskBean(task,
						// loggedInWorkerID);
						if (applyFilters(form, taskBean, false, blnFilterPart, blnFilterSSN, blnFilterDocket, blnFilterEmail, blnApproval, blnOutstanding, blnCompleted))
						{
							tasks.addElement(taskBean);
						}
					}
					log.info("Successfully Found TaskManager using Case #:" + taskForm.getNbCase());
				} else if (blnFilterPart)
				{
					log.info("Search by Part");
					Collection c = null;
					if (blnViewOtherAgentTasks)
					{
						if (blnFilterWrkr)
						{
							// c =
							// processorLocalHome.findTasksByMPI(taskForm.getIdPart(),
							// taskForm.getIdWorker());
							c = taskManager.findTasksByMPI(taskForm.getIdPart(), taskForm.getIdWorker());
						} else
						{
							// c =
							// processorLocalHome.findTasksByMPI(taskForm.getIdPart());
							c = taskManager.findTasksByMPI(taskForm.getIdPart());
						}
					} else
					{
						// MOD # 2872 - rkodumagulla 09/07/2004.
						if (blnAgentSearchOverride)
						{
							// c =
							// processorLocalHome.findTasksByMPI(taskForm.getIdPart());
							c = taskManager.findTasksByMPI(taskForm.getIdPart());
						} else
						{
							// c =
							// processorLocalHome.findTasksByMPI(taskForm.getIdPart(),
							// loggedInWorkerID);
							c = taskManager.findTasksByMPI(taskForm.getIdPart(), loggedInWorkerID);
						}
					}
					Iterator i = c.iterator();
					while (i.hasNext())
					{
						TaskBean taskBean = (TaskBean) i.next();
						manageTaskBean(taskBean, loggedInWorkerID);

						// CSProcessorLocal task = (CSProcessorLocal) i.next();
						// TaskBean taskBean = createTaskBean(task,
						// loggedInWorkerID);
						if (applyFilters(form, taskBean, false, false, blnFilterSSN, blnFilterDocket, blnFilterEmail, blnApproval, blnOutstanding, blnCompleted))
						{
							tasks.addElement(taskBean);
						}
					}
					log.info("Successfully Found TaskManager using MPI #:" + taskForm.getIdPart());
				} else if (blnFilterDocket)
				{
					log.info("Search by Docket");
					Collection c = null;
					if (blnViewOtherAgentTasks)
					{
						if (blnFilterWrkr)
						{
							// c =
							// processorLocalHome.findTasksByDkt(taskForm.getNbDocket(),
							// taskForm.getIdWorker());
							c = taskManager.findTasksByDocket(taskForm.getNbDocket(), taskForm.getIdWorker());
						} else
						{
							// c =
							// processorLocalHome.findTasksByDkt(taskForm.getNbDocket());
							c = taskManager.findTasksByDocket(taskForm.getNbDocket());
						}
					} else
					{
						// MOD # 2872 - rkodumagulla 09/07/2004.
						if (blnAgentSearchOverride)
						{
							// c =
							// processorLocalHome.findTasksByDkt(taskForm.getNbDocket());
							c = taskManager.findTasksByDocket(taskForm.getNbDocket());
						} else
						{
							// c =
							// processorLocalHome.findTasksByDkt(taskForm.getNbDocket(),
							// loggedInWorkerID);
							c = taskManager.findTasksByDocket(taskForm.getNbDocket(), loggedInWorkerID);
						}
					}
					Iterator i = c.iterator();
					while (i.hasNext())
					{
						TaskBean taskBean = (TaskBean) i.next();
						manageTaskBean(taskBean, loggedInWorkerID);

						// CSProcessorLocal task = (CSProcessorLocal) i.next();
						// TaskBean taskBean = createTaskBean(task,
						// loggedInWorkerID);
						if (applyFilters(form, taskBean, false, false, false, false, blnFilterEmail, blnApproval, blnOutstanding, blnCompleted))
						{
							tasks.addElement(taskBean);
						}
					}
					log.info("Successfully Found TaskManager using Docket:" + taskForm.getNbDocket());
				} else if (blnFilterEmail)
				{
					log.info("Search by Email ID");
					Collection c = null;
					if (blnViewOtherAgentTasks)
					{
						if (blnFilterWrkr)
						{
							// c =
							// processorLocalHome.findTasksByEmail(taskForm.getIdEmail(),
							// taskForm.getIdWorker());
							c = taskManager.findTasksByEmail(taskForm.getIdEmail(), taskForm.getIdWorker());
						} else
						{
							// c =
							// processorLocalHome.findTasksByEmail(taskForm.getIdEmail());
							c = taskManager.findTasksByEmail(taskForm.getIdEmail());
						}
					} else
					{
						// MOD # 2872 - rkodumagulla 09/07/2004.
						if (blnAgentSearchOverride)
						{
							// c =
							// processorLocalHome.findTasksByEmail(taskForm.getIdEmail());
							c = taskManager.findTasksByEmail(taskForm.getIdEmail());
						} else
						{
							// c =
							// processorLocalHome.findTasksByEmail(taskForm.getIdEmail(),
							// loggedInWorkerID);
							c = taskManager.findTasksByEmail(taskForm.getIdEmail(), loggedInWorkerID);
						}
					}
					Iterator i = c.iterator();
					while (i.hasNext())
					{
						TaskBean taskBean = (TaskBean) i.next();
						manageTaskBean(taskBean, loggedInWorkerID);

						// CSProcessorLocal task = (CSProcessorLocal) i.next();
						// TaskBean taskBean = createTaskBean(task,
						// loggedInWorkerID);
						if (applyFilters(form, taskBean, false, false, blnFilterSSN, false, blnFilterEmail, blnApproval, blnOutstanding, blnCompleted))
						{
							tasks.addElement(taskBean);
						}
					}
					log.info("Successfully Found TaskManager using Email :" + taskForm.getIdEmail());
				} else
				{
					log.info("Search all");
					Collection c = null;
					if (blnViewOtherAgentTasks)
					{
						if (blnFilterWrkr)
						{
							// c =
							// processorLocalHome.findWorkerTasks(taskForm.getIdWorker());
							c = taskManager.findWorkerTasks(taskForm.getIdWorker());
						} else
						{
							// c = processorLocalHome.findAllTasks();
							c = taskManager.findAllTasks();
						}
					} else
					{
						if (blnAgentSearchOverride)
						{
							// c = processorLocalHome.findAllTasks();
							c = taskManager.findAllTasks();
						} else
						{
							// c =
							// processorLocalHome.findWorkerTasks(loggedInWorkerID);
							c = taskManager.findWorkerTasks(loggedInWorkerID);
						}
					}
					Iterator i = c.iterator();
					while (i.hasNext())
					{
						TaskBean taskBean = (TaskBean) i.next();
						manageTaskBean(taskBean, loggedInWorkerID);

						// CSProcessorLocal task = (CSProcessorLocal) i.next();
						// TaskBean taskBean = createTaskBean(task,
						// loggedInWorkerID);
						if (applyFilters(form, taskBean, false, false, false, false, false, blnApproval, blnOutstanding, blnCompleted))
						{
							tasks.addElement(taskBean);
						}
					}
					log.info("Successfully Found TaskManager ");
				}
			} catch (javax.ejb.ObjectNotFoundException onfe)
			{ // findByPrimaryKey returned not found exception
				log.info("Task Search Failed. Task(s) not found");
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.notfound"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (javax.ejb.FinderException fe)
			{
				log.fatal("Task Search Failed. Database Exception resulted from EJB");

				new ApplicationException(fe.getMessage(), fe, new ErrorDescriptor("TaskSearchAction", "execute"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));

			} catch (NamingException ne)
			{
				log.fatal("Task Search Failed. EJB Not Found");

				new ApplicationException(ne.getMessage(), ne, new ErrorDescriptor("TaskSearchAction", "execute"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (BusinessLogicException ble)
			{
				log.fatal("Task Search Failed in TaskManager ");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("TaskSearchAction", "execute"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			request.setAttribute(Constants.TASKLIST, tasks);

			if (tasks.size() == 0)
			{
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.notfound"));
				saveErrors(request, errors);
			}

			log.info("End Time for Task Search " + new Date());

			// Return success
			return (mapping.findForward(Constants.SUCCESS));
		} catch (Exception e)
		{
			log.error("Task Search Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("TaskSearchAction", "execute"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			log.info("Supposed to go to input page.. but dosen't");
			return (new ActionForward(mapping.getInput()));

		}

	}

	private boolean applyFilters(ActionForm form, TaskBean taskBean, boolean blnFilterCase, boolean blnFilterPart, boolean blnFilterSSN, boolean blnFilterDocket, boolean blnFilterEmail, boolean blnApproval, boolean blnOutstanding, boolean blnCompleted)
	{

		ManageTaskForm taskForm = (ManageTaskForm) form;

		boolean select = true;

		if (blnFilterCase)
		{
			if (!taskForm.getNbCase().equals(taskBean.getNbCase().trim()))
			{
				// select = false;
				// No further check required, just reject record
				return false;
			}
		}

		if (blnFilterPart)
		{
			if (!taskForm.getIdPart().equals(taskBean.getIdPart().trim()))
			{
				// select = false;
				// No further check required, just reject record
				return false;
			}
		}

		if (blnFilterDocket)
		{
			if (!taskForm.getNbDocket().equalsIgnoreCase(taskBean.getNbDocket().trim()))
			{
				// select = false;
				// No further check required, just reject record
				return false;
			}
		}

		if (blnFilterEmail)
		{
			if (!taskForm.getIdEmail().equalsIgnoreCase(taskBean.getIdEmail().trim()))
			{
				// select = false;
				// No further check required, just reject record
				return false;
			}
		}

		if (blnCompleted && blnApproval && blnOutstanding)
		{
			select = true;
		} else if (blnCompleted)
		{
			if (taskBean.getCdStatus().equals("CLSD"))
			{
				select = true;
			} else
			{
				// select = false;
				// No further check required, just reject record
				return false;
			}
		} else
		{
			if (blnApproval && blnOutstanding)
			{
				if ((!taskBean.getCdStatus().equals("DELT")) && (!taskBean.getCdStatus().equals("CLSD")))
				{
					select = true;
				} else
				{
					// select = false;
					// No further check required, just reject record
					return false;
				}
			} else if (blnApproval)
			{
				if ((taskBean.getCdStatus().equals("APRV")) || (taskBean.getCdStatus().equals("REQR")))
				{
					select = true;
				} else
				{
					// select = false;
					// No further check required, just reject record
					return false;
				}
			} else if (blnOutstanding)
			{
				if ((!taskBean.getCdStatus().equals("DELT")) && (!taskBean.getCdStatus().equals("CLSD")) && (!taskBean.getCdStatus().equals("REQR")) && (!taskBean.getCdStatus().equals("APRV")))
				{
					select = true;
				} else
				{
					// select = false;
					// No further check required, just reject record
					return false;
				}
			}
		}

		select = applyAdditionalFilters(form, taskBean);

		return select;
	}

	private boolean applyAdditionalFilters(ActionForm form, TaskBean taskBean)
	{

		ManageTaskForm taskForm = (ManageTaskForm) form;
		boolean select = true;

		// Filter SSN
		if (!taskForm.getNbSSN().trim().equals(""))
		{
			if (taskForm.getNbSSN().trim().equals(taskBean.getNbSSN().trim()))
			{
				select = true;
			} else
			{
				// select = false;
				// No further check required, just reject record
				return false;
			}
		}

		// Filter Control Number
		if (!taskForm.getControlNumber().trim().equals(""))
		{
			if (taskForm.getControlNumber().trim().equalsIgnoreCase(taskBean.getNbControl().trim()))
			{
				select = true;
			} else
			{
				// select = false;
				// No further check required, just reject record
				return false;
			}
		}

		// Filter County Name
		if (!taskForm.getNmCounty().trim().equals(""))
		{
			if (taskForm.getNmCounty().trim().equals(taskBean.getNmCounty().trim()))
			{
				select = true;
			} else
			{
				// select = false;
				// No further check required, just reject record
				return false;
			}
		}

		// Filter Customer First Name
		if (!taskForm.getNmAuthorFirst().trim().equals(""))
		{
			if (taskForm.getNmAuthorFirst().trim().equalsIgnoreCase(taskBean.getNmCustomerFirst().trim()))
			{
				select = true;
			} else
			{
				// select = false;
				// No further check required, just reject record
				return false;
			}
		}

		if (!taskForm.getNmAuthorLast().trim().equals(""))
		{
			if (taskForm.getNmAuthorLast().trim().equalsIgnoreCase(taskBean.getNmCustomerLast().trim()))
			{
				select = true;
			} else
			{
				// select = false;
				// No further check required, just reject record
				return false;
			}
		}

		if (!taskForm.getNmAuthorMi().trim().equals(""))
		{
			if (taskForm.getNmAuthorMi().trim().equalsIgnoreCase(taskBean.getNmCustomerMi().trim()))
			{
				select = true;
			} else
			{
				// select = false;
				// No further check required, just reject record
				return false;
			}
		}

		// Filter Custodial Parent
		if (!taskForm.getNmCustParFirst().trim().equals(""))
		{
			if (taskForm.getNmCustParFirst().trim().equalsIgnoreCase(taskBean.getNmCustParFirst().trim()))
			{
				select = true;
			} else
			{
				// select = false;
				// No further check required, just reject record
				return false;
			}
		}

		if (!taskForm.getNmCustParLast().trim().equals(""))
		{
			if (taskForm.getNmCustParLast().trim().equalsIgnoreCase(taskBean.getNmCustParLast().trim()))
			{
				select = true;
			} else
			{
				// select = false;
				// No further check required, just reject record
				return false;
			}
		}

		if (!taskForm.getNmCustParMi().trim().equals(""))
		{
			if (taskForm.getNmCustParMi().trim().equalsIgnoreCase(taskBean.getNmCustParMi().trim()))
			{
				select = true;
			} else
			{
				// select = false;
				// No further check required, just reject record
				return false;
			}
		}

		// Filter Non-Custodial Parent
		if (!taskForm.getNmNonCustParFirst().trim().equals(""))
		{
			if (taskForm.getNmNonCustParFirst().trim().equalsIgnoreCase(taskBean.getNmNonCustParFirst().trim()))
			{
				select = true;
			} else
			{
				// select = false;
				// No further check required, just reject record
				return false;
			}
		}

		if (!taskForm.getNmNonCustParLast().trim().equals(""))
		{
			if (taskForm.getNmNonCustParLast().trim().equalsIgnoreCase(taskBean.getNmNonCustParLast().trim()))
			{
				select = true;
			} else
			{
				// select = false;
				// No further check required, just reject record
				return false;
			}
		}

		if (!taskForm.getNmNonCustParMi().trim().equals(""))
		{
			if (taskForm.getNmNonCustParMi().trim().equalsIgnoreCase(taskBean.getNmNonCustParMi().trim()))
			{
				select = true;
			} else
			{
				// select = false;
				// No further check required, just reject record
				return false;
			}
		}

		// Filter Referral Source 1
		if (!taskForm.getNmRefSource1().trim().equals(""))
		{
			if (taskForm.getNmRefSource1().trim().equals(taskBean.getNmRefSource1().trim()))
			{
				select = true;
			} else
			{
				// select = false;
				// No further check required, just reject record
				return false;
			}
		}

		// Filter Referral Source 2
		if (!taskForm.getNmRefSource2().trim().equals(""))
		{
			if (taskForm.getNmRefSource2().trim().equals(taskBean.getNmRefSource2().trim()))
			{
				select = true;
			} else
			{
				// select = false;
				// No further check required, just reject record
				return false;
			}
		}

		// Filter Referral Source 3
		if (!taskForm.getNmRefSource3().trim().equals(""))
		{
			if (taskForm.getNmRefSource3().trim().equals(taskBean.getNmRefSource3().trim()))
			{
				select = true;
			} else
			{
				// select = false;
				// No further check required, just reject record
				return false;
			}
		}

		// Filter Referral Source 4
		if (!taskForm.getNmRefSource4().trim().equals(""))
		{
			if (taskForm.getNmRefSource4().trim().equals(taskBean.getNmRefSource4().trim()))
			{
				select = true;
			} else
			{
				// select = false;
				// No further check required, just reject record
				return false;
			}
		}

		return select;
	}

}
