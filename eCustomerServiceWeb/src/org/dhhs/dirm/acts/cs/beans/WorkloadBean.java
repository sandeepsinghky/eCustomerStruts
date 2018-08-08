

package org.dhhs.dirm.acts.cs.beans;

import org.apache.struts.action.ActionForm;

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
public class WorkloadBean extends ActionForm
{

	private String	filler;

	private String	idReference;

	private String	srcAgent;

	private String	srcAgentName;

	private String	trgAgent;

	private String	trgAgentName;

	private String	fromDate;

	private String	toDate;

	private String	assignedDate;

	private String	cdStatus;

	/**
	 * Constructor for WorkloadBean.
	 */
	public WorkloadBean()
	{
		super();
	}

	/**
	 * Returns the assignedDate.
	 * 
	 * @return String
	 */
	public String getAssignedDate()
	{
		return this.assignedDate;
	}

	/**
	 * Returns the cdStatus.
	 * 
	 * @return String
	 */
	public String getCdStatus()
	{
		return this.cdStatus;
	}

	/**
	 * Returns the fromDate.
	 * 
	 * @return String
	 */
	public String getFromDate()
	{
		return this.fromDate;
	}

	/**
	 * Returns the idReference.
	 * 
	 * @return String
	 */
	public String getIdReference()
	{
		return this.idReference;
	}

	/**
	 * Returns the srcAgent.
	 * 
	 * @return String
	 */
	public String getSrcAgent()
	{
		return this.srcAgent;
	}

	/**
	 * Returns the toDate.
	 * 
	 * @return String
	 */
	public String getToDate()
	{
		return this.toDate;
	}

	/**
	 * Returns the trgAgent.
	 * 
	 * @return String
	 */
	public String getTrgAgent()
	{
		return this.trgAgent;
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
	 * Returns the filler.
	 * 
	 * @return String
	 */
	public String getFiller()
	{
		return this.filler;
	}

	/**
	 * Returns the srcAgentName.
	 * 
	 * @return String
	 */
	public String getSrcAgentName()
	{
		return this.srcAgentName;
	}

	/**
	 * Returns the trgAgentName.
	 * 
	 * @return String
	 */
	public String getTrgAgentName()
	{
		return this.trgAgentName;
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

}
