/**
 * Created on May 18, 2004
 *
 * To change this generated comment edit the template variable "filecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of file
 * comments go to Window>Preferences>Java>Code Generation.
 */


package org.dhhs.dirm.acts.cs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * ManageServletStack.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: May 18, 2004 1:27:48 PM
 * 
 * @author RKodumagulla
 *
 */
public class ManageServletStack
{

	private static final Logger log = Logger.getLogger(ManageServletStack.class);

	/**
	 * Constructor for ManageServletStack.
	 */
	public ManageServletStack()
	{
		super();
	}

	public void addToStack(HttpServletRequest request, HttpSession session)
	{

		NavigationStack stack = (NavigationStack) session.getAttribute(Constants.STACK);

		String uri = request.getRequestURI();
		String queryString = request.getQueryString();

		log.info("ManageServletStack: uri " + uri);
		log.info("ManageServletStack: queryString " + queryString);

		boolean isQuery = (uri.indexOf("?") >= 0);

		if (isQuery)
		{
			stack.addToStack(uri);
		} else
		{
			stack.addToStack(uri + "?" + queryString);
		}
	}
}
