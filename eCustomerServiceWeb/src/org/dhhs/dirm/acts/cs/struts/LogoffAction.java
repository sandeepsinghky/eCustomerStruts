

package org.dhhs.dirm.acts.cs.struts;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dhhs.dirm.acts.cs.Constants;
import org.dhhs.dirm.acts.cs.formbeans.LogonForm;

/**
 * LogoffAction.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Aug 18, 2003 11:06:43 AM
 * 
 * @author Rkodumagulla
 *
 */
public class LogoffAction extends Action
{

	private static final Logger log = Logger.getLogger(LogoffAction.class);

	/**
	 * @see org.apache.struts.action.Action#execute(ActionMapping, ActionForm,
	 *      HttpServletRequest, HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{

		// Extract attributes we will need
		HttpSession session = request.getSession();
		LogonForm user = (LogonForm) session.getAttribute(Constants.USER_KEY);

		boolean timeout = Boolean.valueOf(request.getParameter("timeout")).booleanValue();

		if (timeout)
		{
			log.debug("User Session timed out due to inactivity");
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.timeout"));
			saveErrors(request, errors);
		}

		// Log this user logoff
		if (user != null)
		{
			StringBuffer message = new StringBuffer("LogoffAction: User '");
			message.append(user.getUsername());
			message.append("' logged off in session ");
			message.append(session.getId());
			log.debug(message.toString());
		}

		// Remove user login
		session.removeAttribute(Constants.USER_KEY);

		// Remove userbean from the session
		session.removeAttribute(Constants.USERBEAN_KEY);

		session.invalidate();

		// Return success
		return (mapping.findForward(Constants.SUCCESS));
	}
}
