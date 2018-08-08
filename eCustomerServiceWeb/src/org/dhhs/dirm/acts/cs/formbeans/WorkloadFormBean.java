

package org.dhhs.dirm.acts.cs.formbeans;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.dhhs.dirm.acts.cs.beans.WorkloadBean;

/**
 * WorkloadBean.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by ACTS
 * Technical Team for North Carolina Child Support Enforcement - ACTS Project
 * 
 * Creation Date: Sep 8, 2004 10:17:01 AM
 * 
 * @author rkodumagulla
 *
 */
public class WorkloadFormBean extends ActionForm
{

	private String[]	selectedTasks	= new String[100];

	private String		button			= "";

	private String		filler;

	private String		idReference		= null;

	private String		srcAgent;

	private String		srcAgentName;

	private String		trgAgent;

	private String		trgAgentName;

	private String		fromDate;

	private String		toDate;

	private String		assignedDate;

	private String		cdStatus;

	private Vector		tasks;

	/**
	 * Constructor for WorkloadBean.
	 */
	public WorkloadFormBean()
	{
		super();
		tasks = new Vector();
	}

	/**
	 * Returns the assignedDate.
	 * 
	 * @return String
	 */
	public String getAssignedDate()
	{
		return assignedDate;
	}

	/**
	 * Returns the cdStatus.
	 * 
	 * @return String
	 */
	public String getCdStatus()
	{
		return cdStatus;
	}

	/**
	 * Returns the fromDate.
	 * 
	 * @return String
	 */
	public String getFromDate()
	{
		return fromDate;
	}

	/**
	 * Returns the idReference.
	 * 
	 * @return String
	 */
	public String getIdReference()
	{
		return idReference;
	}

	/**
	 * Returns the srcAgent.
	 * 
	 * @return String
	 */
	public String getSrcAgent()
	{
		return srcAgent;
	}

	/**
	 * Returns the toDate.
	 * 
	 * @return String
	 */
	public String getToDate()
	{
		return toDate;
	}

	/**
	 * Returns the trgAgent.
	 * 
	 * @return String
	 */
	public String getTrgAgent()
	{
		return trgAgent;
	}

	/**
	 * Sets the assignedDate.
	 * 
	 * @param assignedDate
	 *            The assignedDate to set
	 */
	public void setAssignedDate(String assignedDate)
	{
		this.assignedDate = assignedDate;
	}

	/**
	 * Sets the cdStatus.
	 * 
	 * @param cdStatus
	 *            The cdStatus to set
	 */
	public void setCdStatus(String cdStatus)
	{
		this.cdStatus = cdStatus;
	}

	/**
	 * Sets the fromDate.
	 * 
	 * @param fromDate
	 *            The fromDate to set
	 */
	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}

	/**
	 * Sets the idReference.
	 * 
	 * @param idReference
	 *            The idReference to set
	 */
	public void setIdReference(String idReference)
	{
		this.idReference = idReference;
	}

	/**
	 * Sets the srcAgent.
	 * 
	 * @param srcAgent
	 *            The srcAgent to set
	 */
	public void setSrcAgent(String srcAgent)
	{
		this.srcAgent = srcAgent;
	}

	/**
	 * Sets the toDate.
	 * 
	 * @param toDate
	 *            The toDate to set
	 */
	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}

	/**
	 * Sets the trgAgent.
	 * 
	 * @param trgAgent
	 *            The trgAgent to set
	 */
	public void setTrgAgent(String trgAgent)
	{
		this.trgAgent = trgAgent;
	}

	/**
	 * Returns the selectedTasks.
	 * 
	 * @return String[]
	 */
	public String[] getSelectedTasks()
	{
		return selectedTasks;
	}

	/**
	 * Sets the selectedTasks.
	 * 
	 * @param selectedTasks
	 *            The selectedTasks to set
	 */
	public void setSelectedTasks(String[] selectedTasks)
	{
		this.selectedTasks = selectedTasks;
	}

	public void setSelectedTasks(int i, String selectedTask)
	{
		System.out.println("Selected Task " + selectedTask + " @ line number" + i);
		this.selectedTasks[i] = selectedTask;
	}

	/**
	 * @see org.apache.struts.action.ActionForm#reset(ActionMapping,
	 *      HttpServletRequest)
	 */
	public void reset(ActionMapping arg0, HttpServletRequest arg1)
	{
		super.reset(arg0, arg1);

		System.out.println("WorkloadBean -> Size of selectedTasks :" + selectedTasks.length);

		for (int i = 0; i < selectedTasks.length; i++)
		{
			selectedTasks[i] = "";
		}
	}

	/**
	 * Returns the tasks.
	 * 
	 * @return Vector
	 */
	public Vector getTasks()
	{
		return tasks;
	}

	/**
	 * Sets the tasks.
	 * 
	 * @param tasks
	 *            The tasks to set
	 */
	public void setTasks(Vector tasks)
	{
		this.tasks = tasks;
	}

	public void addTask(WorkloadBean task)
	{
		tasks.addElement(task);
	}

	/**
	 * Returns the filler.
	 * 
	 * @return String
	 */
	public String getFiller()
	{
		return filler;
	}

	/**
	 * Returns the srcAgentName.
	 * 
	 * @return String
	 */
	public String getSrcAgentName()
	{
		return srcAgentName;
	}

	/**
	 * Returns the trgAgentName.
	 * 
	 * @return String
	 */
	public String getTrgAgentName()
	{
		return trgAgentName;
	}

	/**
	 * Sets the filler.
	 * 
	 * @param filler
	 *            The filler to set
	 */
	public void setFiller(String filler)
	{
		this.filler = filler;
	}

	/**
	 * Sets the srcAgentName.
	 * 
	 * @param srcAgentName
	 *            The srcAgentName to set
	 */
	public void setSrcAgentName(String srcAgentName)
	{
		this.srcAgentName = srcAgentName;
	}

	/**
	 * Sets the trgAgentName.
	 * 
	 * @param trgAgentName
	 *            The trgAgentName to set
	 */
	public void setTrgAgentName(String trgAgentName)
	{
		this.trgAgentName = trgAgentName;
	}

	/**
	 * Returns the button.
	 * 
	 * @return String
	 */
	public String getButton()
	{
		return button;
	}

	/**
	 * Sets the button.
	 * 
	 * @param button
	 *            The button to set
	 */
	public void setButton(String button)
	{
		this.button = button;
	}

}
