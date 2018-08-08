

package org.dhhs.dirm.acts.cs.struts;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dhhs.dirm.acts.cs.Constants;
import org.dhhs.dirm.acts.cs.NavigationStack;

/**
 * Navigation.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Aug 15, 2003 12:29:26 PM
 * 
 * @author Rkodumagulla
 *
 */
public class Navigation extends Action
{

	private static final Logger log = Logger.getLogger(Navigation.class);
	/**
	 * @see org.apache.struts.action.Action#execute(ActionMapping, ActionForm,
	 *      HttpServletRequest, HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{

		HttpSession session = request.getSession();
		NavigationStack stack = (NavigationStack) session.getAttribute(Constants.STACK);

		String target = stack.getPreviousStackItem();

		log.info("System is navigating to : " + target);
		System.out.println("System is navigating to : " + target);
		ActionForward af = mapping.findForward(target);

		if (af == null)
		{
			target=target.replaceFirst(request.getContextPath(),"");
			System.out.println(request.getContextPath());
			System.out.println("Trying ActionForward : " + target);
			log.info("Trying ActionForward");
			af = new ActionForward(target, true);
			
		}

		return af;
	}
}
