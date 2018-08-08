

package org.dhhs.dirm.acts.cs.formbeans;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 * ActsWorkerSearchForm.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Aug 26, 2003 2:29:17 PM
 * 
 * @author rkodumagulla
 *
 */
public class ActsWorkerSearchForm extends ValidatorForm
{

	// private String racfid;

	private String	workerid;

	private String	lastname;

	private String	firstname;

	public ActsWorkerSearchForm()
	{
		super();
	}

	/**
	 * Returns the firstname.
	 * 
	 * @return String
	 */
	public String getFirstname()
	{
		return firstname;
	}

	/**
	 * Returns the lastname.
	 * 
	 * @return String
	 */
	public String getLastname()
	{
		return lastname;
	}

	/**
	 * Returns the workerid.
	 * 
	 * @return String
	 */
	public String getWorkerid()
	{
		return workerid;
	}

	/**
	 * Sets the firstname.
	 * 
	 * @param firstname
	 *            The firstname to set
	 */
	public void setFirstname(String firstname)
	{
		this.firstname = firstname.toUpperCase();
	}

	/**
	 * Sets the lastname.
	 * 
	 * @param lastname
	 *            The lastname to set
	 */
	public void setLastname(String lastname)
	{
		this.lastname = lastname.toUpperCase();
	}

	/**
	 * Sets the workerid.
	 * 
	 * @param workerid
	 *            The workerid to set
	 */
	public void setWorkerid(String workerid)
	{
		this.workerid = workerid.toUpperCase();
	}

	/**
	 * @see org.apache.struts.action.ActionForm#validate(ActionMapping,
	 *      HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{

		ActionErrors errors = super.validate(mapping, request);
		// ActionErrors errors = new ActionErrors();

		/*
		 * if ((racfid == null) || (racfid.length() < 1)) { if ((workerid ==
		 * null) || (workerid.length() < 1)) {
		 * 
		 * errors.add("username", new ActionError("error.username.required")); }
		 * if ((password == null) || (password.length() < 8)) {
		 * errors.add("password", new ActionError("error.password.required")); }
		 */

		return errors;
	}

}
