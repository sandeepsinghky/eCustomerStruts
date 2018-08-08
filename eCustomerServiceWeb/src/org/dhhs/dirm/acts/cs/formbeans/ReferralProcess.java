

package org.dhhs.dirm.acts.cs.formbeans;

import org.apache.struts.action.ActionForm;

/**
 * ReferralProcess.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Nov 4, 2003 9:50:10 AM
 * 
 * @author rkodumagulla
 *
 */
public class ReferralProcess extends ActionForm
{

	private String	type;

	private String	step;

	private int		seq;

	private short	duration;

	private String	tsCreate;

	private String	tsUpdate;

	private String	wrkrCreate;

	private String	wrkrUpdate;

	/**
	 * Constructor for ReferralProcess.
	 */
	public ReferralProcess()
	{
		super();
	}

	/*
	 * //struts validator public ActionErrors validate(ActionMapping mapping,
	 * HttpServletRequest request) {
	 * 
	 * ActionErrors errors = null;
	 * 
	 * if ((!request.getParameter(Constants.METHOD).equals(Constants.CREATE)) &&
	 * (!request.getParameter(Constants.METHOD).equals(Constants.EDIT)) &&
	 * (!request.getParameter(Constants.METHOD).equals(Constants.DELETE)) &&
	 * (!request.getParameter(Constants.METHOD).equals(Constants.VIEW))) {
	 * errors = super.validate(mapping, request); }
	 * 
	 * if (errors == null) { errors = new ActionErrors(); } else { if
	 * (request.getParameter(Constants.METHOD).equals(Constants.SAVE)) {
	 * request.setAttribute("formMode","1"); } if
	 * (request.getParameter(Constants.METHOD).equals(Constants.STORE)) {
	 * request.setAttribute("formMode","0"); } } return errors; }
	 */

	/**
	 * Returns the duration.
	 * 
	 * @return short
	 */
	public short getDuration()
	{
		return duration;
	}

	/**
	 * Returns the seq.
	 * 
	 * @return int
	 */
	public int getSeq()
	{
		return seq;
	}

	/**
	 * Returns the step.
	 * 
	 * @return String
	 */
	public String getStep()
	{
		return step;
	}

	/**
	 * Returns the tsCreate.
	 * 
	 * @return String
	 */
	public String getTsCreate()
	{
		return tsCreate;
	}

	/**
	 * Returns the tsUpdate.
	 * 
	 * @return String
	 */
	public String getTsUpdate()
	{
		return tsUpdate;
	}

	/**
	 * Returns the type.
	 * 
	 * @return String
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * Returns the wrkrCreate.
	 * 
	 * @return String
	 */
	public String getWrkrCreate()
	{
		return wrkrCreate;
	}

	/**
	 * Returns the wrkrUpdate.
	 * 
	 * @return String
	 */
	public String getWrkrUpdate()
	{
		return wrkrUpdate;
	}

	/**
	 * Sets the duration.
	 * 
	 * @param duration
	 *            The duration to set
	 */
	public void setDuration(short duration)
	{
		this.duration = duration;
	}

	/**
	 * Sets the seq.
	 * 
	 * @param seq
	 *            The seq to set
	 */
	public void setSeq(int seq)
	{
		this.seq = seq;
	}

	/**
	 * Sets the step.
	 * 
	 * @param step
	 *            The step to set
	 */
	public void setStep(String step)
	{
		this.step = step;
	}

	/**
	 * Sets the tsCreate.
	 * 
	 * @param tsCreate
	 *            The tsCreate to set
	 */
	public void setTsCreate(String tsCreate)
	{
		this.tsCreate = tsCreate;
	}

	/**
	 * Sets the tsUpdate.
	 * 
	 * @param tsUpdate
	 *            The tsUpdate to set
	 */
	public void setTsUpdate(String tsUpdate)
	{
		this.tsUpdate = tsUpdate;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type
	 *            The type to set
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * Sets the wrkrCreate.
	 * 
	 * @param wrkrCreate
	 *            The wrkrCreate to set
	 */
	public void setWrkrCreate(String wrkrCreate)
	{
		this.wrkrCreate = wrkrCreate;
	}

	/**
	 * Sets the wrkrUpdate.
	 * 
	 * @param wrkrUpdate
	 *            The wrkrUpdate to set
	 */
	public void setWrkrUpdate(String wrkrUpdate)
	{
		this.wrkrUpdate = wrkrUpdate;
	}

}
