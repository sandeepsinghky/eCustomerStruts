

package org.dhhs.dirm.acts.cs.formbeans;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;
import org.dhhs.dirm.acts.cs.Constants;

/**
 * ReferralTypeForm.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Nov 04, 2003 09:29:17 AM
 * 
 * @author rkodumagulla
 *
 */
public class ReferralTypeForm extends ValidatorForm
{

	private String	type;

	private String	description;

	private String	tsCreate;

	private String	tsUpdate;

	private String	idWrkrCreate;

	private String	idWrkrUpdate;

	private boolean	generateCorrespondence;

	private Vector	formSteps;

	// struts validator
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{

		ActionErrors errors = null;

		if ((!request.getParameter(Constants.METHOD).equals(Constants.CREATE)) && (!request.getParameter(Constants.METHOD).equals(Constants.EDIT)) && (!request.getParameter(Constants.METHOD).equals(Constants.DELETE)) && (!request.getParameter(Constants.METHOD).equals(Constants.DELETEALL)) && (!request.getParameter(Constants.METHOD).equals(Constants.VIEW)))
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

		/*
		 * ActionErrors errors = new ActionErrors(); System.out.println(
		 * "In Validate method"); if
		 * ((!request.getParameter(Constants.METHOD).equals(Constants.CREATE))
		 * && (!request.getParameter(Constants.METHOD).equals(Constants.EDIT))
		 * && (!request.getParameter(Constants.METHOD).equals(Constants.DELETE))
		 * &&
		 * (!request.getParameter(Constants.METHOD).equals(Constants.DELETEALL))
		 * && (!request.getParameter(Constants.METHOD).equals(Constants.VIEW)))
		 * { System.out.println("Validating required fields");
		 * 
		 * if (type.trim().equals("")) { errors.add(ActionErrors.GLOBAL_ERROR,
		 * new ActionError("referralTypeForm.type.displayName")); }
		 * 
		 * if (description.trim().equals("")) {
		 * errors.add(ActionErrors.GLOBAL_ERROR, new
		 * ActionError("referralTypeForm.discription.displayName")); } } return
		 * errors;
		 */
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
	 * Returns the formSteps.
	 * 
	 * @return Vector
	 */
	public Vector getFormSteps()
	{
		return formSteps;
	}

	/**
	 * Returns the idWrkrCreate.
	 * 
	 * @return String
	 */
	public String getIdWrkrCreate()
	{
		return idWrkrCreate;
	}

	/**
	 * Returns the idWrkrUpdate.
	 * 
	 * @return String
	 */
	public String getIdWrkrUpdate()
	{
		return idWrkrUpdate;
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
	 * Sets the formSteps.
	 * 
	 * @param formSteps
	 *            The formSteps to set
	 */
	public void setFormSteps(Vector formSteps)
	{
		this.formSteps = formSteps;
	}

	/**
	 * Sets the idWrkrCreate.
	 * 
	 * @param idWrkrCreate
	 *            The idWrkrCreate to set
	 */
	public void setIdWrkrCreate(String idWrkrCreate)
	{
		this.idWrkrCreate = idWrkrCreate;
	}

	/**
	 * Sets the idWrkrUpdate.
	 * 
	 * @param idWrkrUpdate
	 *            The idWrkrUpdate to set
	 */
	public void setIdWrkrUpdate(String idWrkrUpdate)
	{
		this.idWrkrUpdate = idWrkrUpdate;
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
	 * Returns the generateCorrespondence.
	 * 
	 * @return boolean
	 */
	public boolean isGenerateCorrespondence()
	{
		return generateCorrespondence;
	}

	/**
	 * Sets the generateCorrespondence.
	 * 
	 * @param generateCorrespondence
	 *            The generateCorrespondence to set
	 */
	public void setGenerateCorrespondence(boolean generateCorrespondence)
	{
		this.generateCorrespondence = generateCorrespondence;
	}

}
