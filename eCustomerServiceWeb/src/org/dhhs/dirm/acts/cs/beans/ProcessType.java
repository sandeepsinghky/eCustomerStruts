

package org.dhhs.dirm.acts.cs.beans;

/**
 * ProcessType.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Nov 3, 2003 9:36:19 AM
 * 
 * @author rkodumagulla
 *
 */
public class ProcessType
{

	private String	step;

	private String	description;

	private String	tsCreate;

	private String	tsUpdate;

	private String	wrkrCreate;

	private String	wrkrUpdate;
	/**
	 * Constructor for ProcessType.
	 */
	public ProcessType()
	{
		super();
	}

	/**
	 * Returns the description.
	 * 
	 * @return String
	 */
	public String getDescription()
	{
		return this.description;
	}

	/**
	 * Returns the step.
	 * 
	 * @return String
	 */
	public String getStep()
	{
		return this.step;
	}

	/**
	 * Sets the description.
	 * 
	 * @param description
	 *            The description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
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
	 * Returns the tsCreate.
	 * 
	 * @return String
	 */
	public String getTsCreate()
	{
		return this.tsCreate;
	}

	/**
	 * Returns the tsUpdate.
	 * 
	 * @return String
	 */
	public String getTsUpdate()
	{
		return this.tsUpdate;
	}

	/**
	 * Returns the wrkrCreate.
	 * 
	 * @return String
	 */
	public String getWrkrCreate()
	{
		return this.wrkrCreate;
	}

	/**
	 * Returns the wrkrUpdate.
	 * 
	 * @return String
	 */
	public String getWrkrUpdate()
	{
		return this.wrkrUpdate;
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
