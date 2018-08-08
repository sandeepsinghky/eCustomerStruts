

package org.dhhs.dirm.acts.cs.formbeans;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;
import org.dhhs.dirm.acts.cs.Constants;

/**
 * ProcessTypeForm.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Jan 7, 2004 11:04:51 AM
 * 
 * @author rkodumagulla
 *
 */
public class ProcessTypeForm extends ValidatorForm
{

	private String	step;

	private String	description;

	private String	tsCreate;

	private String	tsUpdate;

	private String	wrkrCreate;

	private String	wrkrUpdate;

	/**
	 * Constructor for ProcessTypeForm.
	 */
	public ProcessTypeForm()
	{
		super();
	}

	// struts validator
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{

		ActionErrors errors = null;

		if ((!request.getParameter(Constants.METHOD).equals(Constants.CREATE)) && (!request.getParameter(Constants.METHOD).equals(Constants.EDIT)) && (!request.getParameter(Constants.METHOD).equals(Constants.VIEW)))
		{
			errors = super.validate(mapping, request);
		}

		if (errors == null)
		{
			errors = new ActionErrors();
		} else
		{
			if (request.getParameter(Constants.METHOD).equals(Constants.SAVE))
			{
				request.setAttribute("formMode", "1");
			}
			if (request.getParameter(Constants.METHOD).equals(Constants.STORE))
			{
				request.setAttribute("formMode", "0");
			}
		}
		return errors;
	}

	/**
	 * Returns the description.
	 * 
	 * @return String
	 */
	public String getDescription()
	{
		return description;
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
