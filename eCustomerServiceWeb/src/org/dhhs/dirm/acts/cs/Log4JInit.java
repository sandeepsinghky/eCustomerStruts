

package org.dhhs.dirm.acts.cs;

import org.apache.log4j.PropertyConfigurator;

public class Log4JInit extends javax.servlet.http.HttpServlet
{

	/**
	 * Process incoming HTTP GET requests
	 * 
	 * @param request
	 *            Object that encapsulates the request to the servlet
	 * @param response
	 *            Object that encapsulates the response from the servlet
	 */
	public void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException
	{

	}
	/**
	 * Process incoming HTTP POST requests
	 * 
	 * @param request
	 *            Object that encapsulates the request to the servlet
	 * @param response
	 *            Object that encapsulates the response from the servlet
	 */
	public void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException
	{

	}
	/**
	 * Returns the servlet info string.
	 */
	public String getServletInfo()
	{

		return super.getServletInfo();

	}
	/**
	 * Initializes the servlet.
	 */
	public void init()
	{

		String prefix = getServletContext().getRealPath("/");
		String file = getServletContext().getInitParameter("log4j-init-file");

		if (file != null)
		{
			PropertyConfigurator.configure(prefix + file);
		}
	}
}
