

package org.dhhs.dirm.acts.cs;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.ejb.EJBException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.dhhs.dirm.acts.cs.ejb.CSDatabaseSwapLocal;
import org.dhhs.dirm.acts.cs.ejb.CSDatabaseSwapLocalHome;
import org.dhhs.dirm.acts.cs.helpers.HomeHelper;
import org.dhhs.dirm.acts.cs.util.ApplicationException;
import org.dhhs.dirm.acts.cs.util.ErrorDescriptor;

/**
 * This is a HttpServet that can be invoked either from a command line or from
 * the Administrative webpage to swap the database that the Acts Application
 * must use without actually bringing the server down. Creation date: (12/7/2001
 * 9:19:47 AM)
 * 
 * @author: Ramakanth Kodumagulla
 */

public class SwapDatabase extends HttpServlet
{

	private DBConnectManager	connectManager;
	private DataSource			dataSource;
	private String				dbRegion;
	private String				userID;
	private String				password;
	private String				className	= "SwapDatabase";

	/**
	 * The closeConnection method is performed within all classes making
	 * database requests. closeConnection allows for standard error processing
	 * within all all classes. Creation date: (2/27/2001 4:48:31 PM)
	 * 
	 * @exception java.lang.Exception
	 *                The exception description.
	 */
	public void closeConnection(Connection connection, String methodName, HttpServletRequest request, HttpServletResponse response) throws java.io.IOException, javax.servlet.ServletException
	{
		try
		{
			if (!connection.isClosed())
			{
				connection.commit();
				connection.close();

				System.out.println("SwapDatabase Servlet returned connection back to pool.");
			}

		} catch (Exception e)
		{

			// Create the ErrorDescriptor object and set the appropriate
			// properties
			// including notification, logging and error level
			ErrorDescriptor ed = new ErrorDescriptor(className, methodName);
			new ApplicationException(e.getMessage(), e, ed);
			return;
		}
	}

	/**
	 * Process incoming HTTP GET requests
	 * 
	 * @param request
	 *            Object that encapsulates the request to the servlet
	 * @param response
	 *            Object that encapsulates the response from the servlet
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
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
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
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
	public void init(ServletConfig config) throws javax.servlet.ServletException
	{

		super.init(config);

		/*
		 * Create DBConnectManager Object.
		 */

		connectManager = new DBConnectManager();

		dataSource = connectManager.getDataSource();
		userID = connectManager.getUserID();
		password = connectManager.getPassword();
		dbRegion = connectManager.getRegion();
	}
	/**
	 * Process incoming requests for information
	 * 
	 * @param request
	 *            Object that encapsulates the request to the servlet
	 * @param response
	 *            Object that encapsulates the response from the servlet
	 */
	public void performTask(HttpServletRequest request, HttpServletResponse response)
	{

		Connection connection = null;
		String methodName = "performTask";

		try
		{

			try
			{
				connection = dataSource.getConnection(userID, password);
				System.out.println("SwapDatabase Servlet obtained a new connection.");
				connection.setAutoCommit(false);

			} catch (SQLException e)
			{
				ErrorDescriptor ed = new ErrorDescriptor(className, methodName);
				new ApplicationException(e.getMessage(), e, ed);
				return;
			}

			java.util.Date now = new java.util.Date();

			/*
			 * The parameter that must be passed to this servlet must be either
			 * 0 - to swap the database or 1 - to override the current database
			 */
			String strRequestID = (String) request.getParameter("id");
			int requestID = 0;

			System.out.println("SwapDatabase Servlet Invoked @ " + now);

			try
			{
				requestID = Integer.parseInt(strRequestID);
			} catch (NumberFormatException nfe)
			{

				/**
				 * rk 01/21/04 - CT# 432912 - Close the connection. - start
				 */
				closeConnection(connection, methodName, request, response);
				// rk 01/21/04 - end

				// Create the ErrorDescriptor object and set the appropriate
				// properties
				// including notification, logging and error level
				ErrorDescriptor ed = new ErrorDescriptor(this.getClass().getName(), "performTask");
				new ApplicationException(nfe.getMessage(), nfe, ed);
			}

			switch (requestID)
			{
				case 0 : // Request from any application to swap database
					System.out.println("SwapDatabase Invoked to swap @ " + now);

					// Swap the regions for servlet container
					String region = PropertyManager.getRegion();
					String backupRegion = PropertyManager.getBackupRegion();
					PropertyManager.setRegion(backupRegion);
					PropertyManager.setBackupRegion(region);

					// Swap the regions for ejb container
					try
					{
						Object obj = HomeHelper.singleton().getHome("ecsts.CSDatabaseSwapLocalHome");
						CSDatabaseSwapLocalHome dbSwapLocalHome = (CSDatabaseSwapLocalHome) obj;
						CSDatabaseSwapLocal databaseSwap = dbSwapLocalHome.create();
						databaseSwap.swap();
					} catch (EJBException ex)
					{
						ex.printStackTrace();
					}

					break;
				case 1 : // Override database
					System.out.println("SwapDatabase Invoked to Override @ " + now);

					// Swap the regions for servlet container
					String primary_region = (String) request.getParameter("primary");
					String backup_region = (String) request.getParameter("backup");
					PropertyManager.setRegion(primary_region);
					PropertyManager.setBackupRegion(backup_region);

					// Swap the regions for ejb container
					try
					{
						Object obj = HomeHelper.singleton().getHome("ecsts.CSSwapDatabaseLocalHome");
						CSDatabaseSwapLocalHome dbSwapLocalHome = (CSDatabaseSwapLocalHome) obj;
						CSDatabaseSwapLocal databaseSwap = dbSwapLocalHome.create();
						databaseSwap.forceSwap(primary_region, backup_region);
					} catch (EJBException ex)
					{
						ex.printStackTrace();
					}

					break;
			}

			closeConnection(connection, methodName, request, response);

			sendMail();
		} catch (Throwable e)
		{
			e.printStackTrace();
			System.err.println("Swap Database Exception");
		}
	}

	/**
	 * Method to Compose and send email message to the user Creation date:
	 * (4/16/2001 4:59:20 PM)
	 * 
	 * @exception javax.servlet.ServletException
	 *                The exception description.
	 * @exception java.io.IOException
	 *                The exception description.
	 */
	public void sendMail() throws javax.servlet.ServletException, java.io.IOException
	{

		try
		{
			EmailManager em = new EmailManager();
			em.addTo(PropertyManager.getEmailMsgNotify());
			em.setSubject("Swap Database Completed Successfully");

			String strBody = "NOTE: PLEASE DO NOT RESPOND DIRECTLY TO THIS E-MAIL MESSAGE. THIS ADDRESS IS NOT MONITORED.\n\n" + "To eCustomerService Administrator(s), " + "\n\n" + "This message has been sent as an indication that the application has " + "successfully swapped the database regions. \n\n" + "Primary Region in use is: " + PropertyManager.getRegion() + ".\n\n" + "Web Region in use is: " + PropertyManager.getWebRegion() + ".\n\n" + "Backup Region in use is: " + PropertyManager.getBackupRegion() + ".\n\n";

			em.setBody(strBody);
			em.sendMail();
		} catch (EmailException ex)
		{
			String errMsg = "Send Mail Exception";

			// Create the ErrorDescriptor object and set the appropriate
			// properties
			// including notification, logging and error level
			ErrorDescriptor ed = new ErrorDescriptor(this.getClass().getName(), "sendMail");

			new ApplicationException(ex.getMessage(), ex, ed);
		}
	}

}
