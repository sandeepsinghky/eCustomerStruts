

package org.dhhs.dirm.acts.cs.struts;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.dhhs.dirm.acts.cs.Constants;
import org.dhhs.dirm.acts.cs.ManageServletStack;
import org.dhhs.dirm.acts.cs.beans.TaskEntityBean;
import org.dhhs.dirm.acts.cs.beans.TaskFormBean;
import org.dhhs.dirm.acts.cs.beans.TaskHistoryBean;
import org.dhhs.dirm.acts.cs.beans.WorkloadBean;
import org.dhhs.dirm.acts.cs.ejb.CSProcessorLocal;
import org.dhhs.dirm.acts.cs.ejb.CSProcessorLocalHome;
import org.dhhs.dirm.acts.cs.formbeans.WorkloadFormBean;
import org.dhhs.dirm.acts.cs.helpers.HomeHelper;
import org.dhhs.dirm.acts.cs.util.ApplicationException;
import org.dhhs.dirm.acts.cs.util.ErrorDescriptor;

/**
 * WorkloadSearchAction.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by ACTS
 * Technical Team for North Carolina Child Support Enforcement - ACTS Project
 * 
 * Creation Date: Sep 8, 2004 10:21:22 AM
 * 
 * @author rkodumagulla
 *
 */
public class WorkloadSearchAction extends DispatchAction
{

	/**
	 * Constructor for WorkloadSearchAction.
	 */
	public WorkloadSearchAction()
	{
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.Action#execute(ActionMapping, ActionForm,
	 * HttpServletRequest, HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		try
		{
			int searchType = -1;
			java.util.Date fromDate = null;
			java.util.Date toDate = null;

			HttpSession session = request.getSession();

			WorkloadFormBean workloadForm = (WorkloadFormBean) form;

			// This means that the Approve button was clicked on the input form
			String button = workloadForm.getButton();
			System.out.println("WorkloadSearchAction execute button is " +button);
			if (button != null && button.equals("transferAll"))
			{
				return transferAll(mapping, form, request, response);
			}

			if (workloadForm.getIdReference() != null && !workloadForm.getIdReference().equals(""))
			{
				log.info("Reference ID passed " + workloadForm.getIdReference());
				searchType = 0;
			} else
			{
				if (workloadForm.getFromDate() != null && !workloadForm.getFromDate().equals(""))
				{
					if (workloadForm.getFromDate().length() == 10)
					{
						try
						{
							java.text.DateFormat df = new java.text.SimpleDateFormat("MM/dd/yyyy");
							fromDate = df.parse(workloadForm.getFromDate());
							log.info("Parsed User entered Date " + fromDate);
						} catch (java.text.ParseException pe)
						{
							log.info("Workload Search Failed. Invalid From Date Format Entered");
							ActionErrors errors = new ActionErrors();
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.date", "From Date"));
							saveErrors(request, errors);
							return (new ActionForward(mapping.getInput()));
						}
					} else if (workloadForm.getFromDate().length() == 8)
					{
						try
						{
							java.text.DateFormat df = new java.text.SimpleDateFormat("MMddyyyy");
							fromDate = df.parse(workloadForm.getFromDate());
							log.info("Parsed User entered Date " + fromDate);
						} catch (java.text.ParseException pe)
						{
							log.info("Workload Search Failed. Invalid From Date Format Entered");
							ActionErrors errors = new ActionErrors();
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.date", "From Date"));
							saveErrors(request, errors);
							return (new ActionForward(mapping.getInput()));
						}
					} else
					{
						log.info("Workload Search Failed. Invalid From Date Format Entered");
						ActionErrors errors = new ActionErrors();
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.date", "From Date"));
						saveErrors(request, errors);
						return (new ActionForward(mapping.getInput()));
					}
				} else
				{
					log.info("Workload Search Failed. From Date Missing");
					ActionErrors errors = new ActionErrors();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.required", "From Date"));
					saveErrors(request, errors);
					return (new ActionForward(mapping.getInput()));
				}

				if (workloadForm.getToDate() != null && !workloadForm.getToDate().equals(""))
				{
					if (workloadForm.getToDate().length() == 10)
					{
						try
						{
							java.text.DateFormat df = new java.text.SimpleDateFormat("MM/dd/yyyy");
							toDate = df.parse(workloadForm.getToDate());
							log.info("Parsed User entered Date " + toDate);
						} catch (java.text.ParseException pe)
						{
							log.info("Workload Search Failed. Invalid To Date Format Entered");
							ActionErrors errors = new ActionErrors();
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.date", "To Date"));
							saveErrors(request, errors);
							return (new ActionForward(mapping.getInput()));
						}
					} else if (workloadForm.getToDate().length() == 8)
					{
						try
						{
							java.text.DateFormat df = new java.text.SimpleDateFormat("MMddyyyy");
							toDate = df.parse(workloadForm.getToDate());
							log.info("Parsed User entered Date " + toDate);
						} catch (java.text.ParseException pe)
						{
							log.info("Workload Search Failed. Invalid To Date Format Entered");
							ActionErrors errors = new ActionErrors();
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.date", "To Date"));
							saveErrors(request, errors);
							return (new ActionForward(mapping.getInput()));
						}
					} else
					{
						log.info("Workload Search Failed. Invalid To Date Format Entered");
						ActionErrors errors = new ActionErrors();
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.date", "To Date"));
						saveErrors(request, errors);
						return (new ActionForward(mapping.getInput()));
					}
				} else
				{
					log.info("Workload Search Failed. To Date Missing");
					ActionErrors errors = new ActionErrors();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.required", "To Date"));
					saveErrors(request, errors);
					return (new ActionForward(mapping.getInput()));
				}

				if (workloadForm.getSrcAgent() != null && workloadForm.getSrcAgent() != "")
				{
				} else
				{
					log.info("Workload Search Failed. From Agent Missing");
					ActionErrors errors = new ActionErrors();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.required", "From Agent"));
					saveErrors(request, errors);
					return (new ActionForward(mapping.getInput()));
				}

				if (workloadForm.getTrgAgent() != null && workloadForm.getTrgAgent() != "")
				{
				} else
				{
					log.info("Workload Search Failed. To Agent Missing");
					ActionErrors errors = new ActionErrors();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.required", "To Agent"));
					saveErrors(request, errors);
					return (new ActionForward(mapping.getInput()));
				}
				searchType = 1;
			}

			if (searchType == -1)
			{ // Invalid Search Type
				log.info("Workload Search Failed. Invalid Search Criteria Specified");
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.invalid", "Search criteria specified"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} else if (searchType == 0)
			{ // Search by reference id, transfer control to View the task in
			  // detail

				/**
				 * Invoke the Business Process and return results
				 */
				try
				{
					Object obj = HomeHelper.singleton().getHome("ecsts.CSProcessorLocalHome");
					CSProcessorLocalHome processorLocalHome = (CSProcessorLocalHome) obj;

					CSProcessorLocal task = processorLocalHome.findByPrimaryKey(workloadForm.getIdReference());
					TaskEntityBean taskEntityBean = task.getTaskEntityBean();

					if (taskEntityBean.getCdStatus().equals("CLSD"))
					{
						ActionErrors errors = new ActionErrors();
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.generic", "Selected task has already been closed."));
						saveErrors(request, errors);
						return (new ActionForward(mapping.getInput()));
					}

					ActionForward forward = mapping.findForward(Constants.TRANSFER);
					StringBuffer path = new StringBuffer(forward.getPath());
					boolean isQuery = (path.toString().indexOf("?") >= 0);

					if (isQuery)
					{
						path.append("&reqCode=viewToTransfer&idReference=" + taskEntityBean.getIdReference() + "&status=" + taskEntityBean.getCdStatus());
					} else
					{
						path.append("?reqCode=viewToTransfer&idReference=" + taskEntityBean.getIdReference() + "&status=" + taskEntityBean.getCdStatus());
					}
					return new ActionForward(path.toString());

				} catch (javax.ejb.ObjectNotFoundException onfe)
				{ // findByPrimaryKey returned not found exception
					log.info("Workload Search by reference failed. Task(s) not found");
					ActionErrors errors = new ActionErrors();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.notfound"));
					saveErrors(request, errors);
					return (new ActionForward(mapping.getInput()));
				} catch (javax.ejb.FinderException fe)
				{
					log.fatal("Workload Search by reference failed. Database Exception resulted from EJB");

					new ApplicationException(fe.getMessage(), fe, new ErrorDescriptor("WorkloadSearchAction", "execute"));

					ActionErrors errors = new ActionErrors();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
					saveErrors(request, errors);
					return (new ActionForward(mapping.getInput()));

				} catch (NamingException ne)
				{
					log.fatal("Workload Search by reference failed. EJB Not Found");

					new ApplicationException(ne.getMessage(), ne, new ErrorDescriptor("WorkloadSearchAction", "execute"));

					ActionErrors errors = new ActionErrors();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
					saveErrors(request, errors);
					return (new ActionForward(mapping.getInput()));
				}

			} else if (searchType == 1)
			{

				Vector taskList = new Vector();

				try
				{
					Object obj = HomeHelper.singleton().getHome("ecsts.CSProcessorLocalHome");
					CSProcessorLocalHome processorLocalHome = (CSProcessorLocalHome) obj;

					log.info("Search by Date Range");
					Collection c = null;

					c = processorLocalHome.findTasksForDateRange(new java.sql.Date(fromDate.getTime()), new java.sql.Date(toDate.getTime()), workloadForm.getSrcAgent());
					Iterator i = c.iterator();
					while (i.hasNext())
					{
						CSProcessorLocal task = (CSProcessorLocal) i.next();
						if (!task.getTaskEntityBean().getCdStatus().equals("CLSD"))
						{
							workloadForm.addTask(createTaskBean(task, workloadForm.getSrcAgent(), workloadForm.getTrgAgent()));
						}
					}
					String[] selections = new String[c.size()];

					for (int j = 0; j < selections.length; j++)
					{
						selections[j] = "";
					}

					workloadForm.setSelectedTasks(selections);

				} catch (javax.ejb.FinderException fe)
				{
					log.fatal("Workload Search by range failed. Database Exception resulted from EJB");

					new ApplicationException(fe.getMessage(), fe, new ErrorDescriptor("WorkloadSearchAction", "execute"));

					ActionErrors errors = new ActionErrors();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
					saveErrors(request, errors);
					return (new ActionForward(mapping.getInput()));

				} catch (NamingException ne)
				{
					log.fatal("Workload Search by range failed. EJB Not Found");

					new ApplicationException(ne.getMessage(), ne, new ErrorDescriptor("WorkloadSearchAction", "execute"));

					ActionErrors errors = new ActionErrors();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
					saveErrors(request, errors);
					return (new ActionForward(mapping.getInput()));
				}
			}

			if ("request".equals(mapping.getScope()))
				request.setAttribute(mapping.getAttribute(), form);
			else
			{
				session.setAttribute(mapping.getAttribute(), form);
			}

			if (workloadForm.getTasks().size() == 0)
			{
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.notfound"));
				saveErrors(request, errors);
			}

			saveToken(request);

			/**
			 * Logic for return functionality
			 */
			new ManageServletStack().addToStack(request, session);

			return mapping.findForward(Constants.SUCCESS);

		} catch (Exception e)
		{
			log.error("Workload Search Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("WorkloadSearchAction", "execute"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			return (new ActionForward(mapping.getInput()));
		}
	}

	/**
	 * @see org.apache.struts.action.Action#approveAll(ActionMapping,
	 *      ActionForm, HttpServletRequest, HttpServletResponse)
	 */
	public ActionForward transferAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{

		try
		{

			WorkloadFormBean wkForm = (WorkloadFormBean) form;

			Vector tasks = wkForm.getTasks();
			String[] selection = wkForm.getSelectedTasks();

			for (int i = 0; i < selection.length; i++)
			{
				if (!selection[i].equals(""))
				{
					String idReference = selection[i];
					log.info("mass transfer: transfer task with reference id : " + selection[i]);

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

						TaskFormBean tb = new TaskFormBean();
						tb.setIdReference(taskEntityBean.getIdReference());
						tb.setCdType(taskEntityBean.getCdType());
						tb.setCdStatus("TRAN");
						tb.setIdWorker(wkForm.getTrgAgent());
						tb.setNbTelAcd(taskEntityBean.getNbTelACD());
						tb.setNbTelExc(taskEntityBean.getNbTelEXC());
						tb.setNbTelLn(taskEntityBean.getNbTelLN());
						tb.setNbTelExt(taskEntityBean.getNbTelEXT());
						tb.setNbCase(taskEntityBean.getNbCase());
						tb.setIdEmail(taskEntityBean.getIdEmail());
						tb.setIdPart(taskEntityBean.getIdPart());
						tb.setNbSSN(taskEntityBean.getNbSSN());
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

						if (taskEntityBean.getCdStatus().equals("REQR") || (taskEntityBean.getCdStatus().equals("APRV")))
						{
							tb.setCdStatus(taskEntityBean.getCdStatus());
							processorLocalHome.createTaskHistory(tb);
						} else
						{
							tb.setCdStatus("OPEN");
							processorLocalHome.createTaskHistory(tb);
						}

					} catch (javax.ejb.ObjectNotFoundException onfe)
					{ // findByPrimaryKey returned not found exception
						log.info("Workload Search Failed. Task not found");
						ActionErrors errors = new ActionErrors();
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.notfound"));
						saveErrors(request, errors);
						return (new ActionForward(mapping.getInput()));
					} catch (javax.ejb.FinderException fe)
					{
						log.fatal("Workload Search Failed. Database Exception resulted from EJB");

						new ApplicationException(fe.getMessage(), fe, new ErrorDescriptor("WorkloadSearchAction", "transferAll"));

						ActionErrors errors = new ActionErrors();
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
						saveErrors(request, errors);
						return (new ActionForward(mapping.getInput()));
					} catch (NamingException ne)
					{
						log.fatal("Workload Search Failed. EJB not found");

						new ApplicationException(ne.getMessage(), ne, new ErrorDescriptor("WorkloadSearchAction", "transferAll"));

						ActionErrors errors = new ActionErrors();
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
						saveErrors(request, errors);
						return (new ActionForward(mapping.getInput()));
					}
				}
			}
			wkForm.setButton("");
			return (new ActionForward(mapping.getInput()));
		} catch (Exception e)
		{
			log.error("Workload Search Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("WorkloadSearchAction", "transferAll"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			return (new ActionForward(mapping.getInput()));
		}
	}

	private WorkloadBean createTaskBean(CSProcessorLocal task, String fromAgent, String toAgent)
	{

		WorkloadBean wkBean = new WorkloadBean();

		TaskEntityBean taskEntityBean = task.getTaskEntityBean();
		wkBean.setIdReference(taskEntityBean.getIdReference());
		wkBean.setAssignedDate(taskEntityBean.getTsAssign().toString());
		wkBean.setCdStatus(taskEntityBean.getCdStatus());
		wkBean.setTrgAgent(toAgent);
		wkBean.setSrcAgent(fromAgent);

		return wkBean;
	}

}
