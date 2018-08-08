

package org.dhhs.dirm.acts.cs.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionStackServlet extends HttpServlet
{

	private Hashtable hashtable;
	/**
	 * 
	 * Process incoming HTTP GET requests
	 * 
	 * @param request
	 *            Object that encapsulates the request to the servlet
	 * @param response
	 *            Object that encapsulates the response from the servlet
	 */
	public void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException
	{

		performTask(request, response);
	}
	/**
	 * Process incoming HTTP POST requests
	 * 
	 * @param request
	 *            Object that encapsulates the request to the servlet
	 * @param response
	 *            Object that encapsulates the response from the servlet
	 */
	public void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException
	{
		performTask(request, response);

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

	}

	/**
	 * Process incoming requests for information
	 * 
	 * @param request
	 *            Object that encapsulates the request to the servlet
	 * @param response
	 *            Object that encapsulates the response from the servlet
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res)
	{

		try
		{
			hashtable = ExceptionStack.obtainStack();
			// Get en enumeration of all the elements from the stack table
			Enumeration enumeration = hashtable.elements();

			// Initialize a ExceptionStackItem object to null
			ExceptionStackItem item = null;

			// Get the Item Supress Count
			int supressCount = Integer.parseInt((String) req.getParameter("supressCount"));

			// Get the value of the radio button for Item Supress
			String supress = ((String) req.getParameter("itemSupress").trim());

			boolean itemSupress = false;

			if (supress.equalsIgnoreCase("true"))
			{
				itemSupress = true;
			} else
			{
				itemSupress = false;
			}

			// Get the Error Message Item Description
			int itemID = Integer.parseInt((String) req.getParameter("itemID"));

			while (enumeration.hasMoreElements())
			{
				ExceptionStackItem itm = (ExceptionStackItem) enumeration.nextElement();
				if (itm.getItemID() == itemID)
				{
					item = itm;
					break;
				}
			}

			if (item == null)
			{
				String loginURL = "ExceptionStackTable.jsp" + "?errorMsg=" + URLEncoder.encode("Update unsuccessful, Item not found in the stack.");
				getServletConfig().getServletContext().getRequestDispatcher(loginURL).forward(req, res);
				return;
			} else
			{
				item.setSupressCount(supressCount);
				item.supressItem(itemSupress);
				String loginURL = "ExceptionStackTable.jsp" + "?errorMsg=" + URLEncoder.encode("Update successful, Stack Item Modified");
				getServletConfig().getServletContext().getRequestDispatcher(loginURL).forward(req, res);
				return;
			}
		} catch (Throwable e)
		{
			// Create the ErrorDescriptor object and set the appropriate
			// properties
			// including notification, logging and error level
			ErrorDescriptor ed = new ErrorDescriptor("ExceptionStackServlet", "performTask");

			new ApplicationException(e.getMessage(), e, ed);

		}
	}
}
