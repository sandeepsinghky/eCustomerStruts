

package org.dhhs.dirm.acts.cs.formbeans;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 * LogonForm.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Aug 15, 2003 12:18:02 PM
 * 
 * @author Rkodumagulla
 *
 */
public class UserSearchForm extends ValidatorForm
{

	private String	workerid	= null;

	private String	profileid	= null;

	private String	lastname	= null;

	private String	firstname	= null;

	/**
	 * Constructor for LogonForm.
	 */
	public UserSearchForm()
	{
		super();
	}

	/**
	 * @see org.apache.struts.action.ActionForm#validate(ActionMapping,
	 *      HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{

		ActionErrors errors = null;

		errors = super.validate(mapping, request);

		if (errors == null)
		{
			errors = new ActionErrors();
		}
		return errors;
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
	 * Returns the profileid.
	 * 
	 * @return String
	 */
	public String getProfileid()
	{
		return profileid;
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
	 * Sets the profileid.
	 * 
	 * @param profileid
	 *            The profileid to set
	 */
	public void setProfileid(String profileid)
	{
		this.profileid = profileid;
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

}
