

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
public class LogonForm extends ValidatorForm
{

	private String	password		= null;

	private String	username		= null;

	private String	newPassword		= null;

	private String	confirmPassword	= null;

	private boolean	resetPassword	= false;

	/**
	 * Constructor for LogonForm.
	 */
	public LogonForm()
	{
		super();
	}

	/**
	 * Returns the password.
	 * 
	 * @return String
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * Returns the username.
	 * 
	 * @return String
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * Sets the password.
	 * 
	 * @param password
	 *            The password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * Sets the username.
	 * 
	 * @param username
	 *            The username to set
	 */
	public void setUsername(String username)
	{
		this.username = username.toUpperCase();
	}

	/**
	 * @see org.apache.struts.action.ActionForm#validate(ActionMapping,
	 *      HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{

		ActionErrors errors = super.validate(mapping, request);

		/*
		 * if (newPassword != null) { if (newPassword.length() < 8) {
		 * errors.add("newPassword", new
		 * ActionError("error.password.required")); }
		 * 
		 * if (confirmPassword == null) { errors.add("confirmPassword", new
		 * ActionError("error.password.required")); } else if
		 * (!confirmPassword.equals(newPassword)) {
		 * errors.add("confirmPassword", new
		 * ActionError("error.password.verify")); } }
		 */

		return errors;
	}

	/**
	 * Returns the confirmPassword.
	 * 
	 * @return String
	 */
	public String getConfirmPassword()
	{
		return confirmPassword;
	}

	/**
	 * Returns the newPassword.
	 * 
	 * @return String
	 */
	public String getNewPassword()
	{
		return newPassword;
	}

	/**
	 * Sets the confirmPassword.
	 * 
	 * @param confirmPassword
	 *            The confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword)
	{
		this.confirmPassword = confirmPassword;
	}

	/**
	 * Sets the newPassword.
	 * 
	 * @param newPassword
	 *            The newPassword to set
	 */
	public void setNewPassword(String newPassword)
	{
		this.newPassword = newPassword;
	}

	/**
	 * Returns the resetPassword.
	 * 
	 * @return boolean
	 */
	public boolean isResetPassword()
	{
		return resetPassword;
	}

	/**
	 * Sets the resetPassword.
	 * 
	 * @param resetPassword
	 *            The resetPassword to set
	 */
	public void setResetPassword(boolean resetPassword)
	{
		this.resetPassword = resetPassword;
	}

}
