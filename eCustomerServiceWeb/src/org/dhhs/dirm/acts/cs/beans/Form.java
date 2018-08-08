

package org.dhhs.dirm.acts.cs.beans;

import java.util.Vector;

/**
 * Form.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Nov 3, 2003 9:33:46 AM
 * 
 * @author rkodumagulla
 *
 */
public class Form
{

	private String	type;

	private String	description;

	private String	tsCreate;

	private String	tsUpdate;

	private String	wrkrCreate;

	private String	wrkrUpdate;

	private Vector	formSteps;

	/**
	 * Constructor for Form.
	 */
	public Form()
	{
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
	 * Returns the type.
	 * 
	 * @return String
	 */
	public String getType()
	{
		return this.type;
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

	/**
	 * Returns the formSteps.
	 * 
	 * @return Vector
	 */
	public Vector getFormSteps()
	{
		return this.formSteps;
	}

	/**
	 * Sets the formSteps.
	 * 
	 * @param formSteps
	 *            The formSteps to set
	 */
	public void setFormSteps(Vector formSteps)
	{
		this.formSteps = formSteps;
	}

}
