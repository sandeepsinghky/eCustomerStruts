

package org.dhhs.dirm.acts.cs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
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
 * A HttpServlet Class that starts a thread which in turn invokes this class
 * after a specified time interval.
 * <p>
 * The time interval is defined in a properties file that is read whenever the
 * Servlet is loaded in the Application Server.
 * <p>
 * Note: Usage of this servlet is strictly within an Application Server and the
 * servlet must be set to load on start as it does not have any other means to
 * start the thread.
 * <p>
 * Creation date: (12/11/2001 10:47:42 AM)
 * 
 * @author: Ramakanth Kodumagulla
 */

public class AutoRetryDBStatus extends HttpServlet implements MaintainedServlet
{

	private DBConnectManager	connectManager;
	private DataSource			dataSource;
	private String				dbRegion;
	private String				userID;
	private String				password;
	private ServletMaintenance	maintenanceThread;

	private static final String	className	= "AutoRetryDBStatus";

	/**
	 * This method returns the Connection Object back to the connection pool.
	 * This method is called once the database related processing is completed
	 * by the servlet.
	 * <p>
	 * Creation date: (12/11/2001 10:47:42 AM)
	 * 
	 * @exception java.io.IOException
	 *                & javax.servlet.ServletException
	 */

	public void closeConnection(Connection connection, String methodName)
	{
		try
		{
			if (!connection.isClosed())
			{
				connection.commit();
				connection.close();
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
	 * A standard servlet method that allows other processes to shut down the
	 * Maintained servlet thread. Creation date: (12/11/2001 10:47:42 AM)
	 */
	public void destroy()
	{

		System.out.println("Shutting Down the Thread ... ");

		maintenanceThread.shutDown();
		maintenanceThread = null;

		System.out.println("Thread Shutdown");
	}
	/**
	 * This method is invoked as part of a get request from either a JSP Page or
	 * another servlet. Creation date: (12/11/2001 10:47:42 AM)
	 * 
	 * @param request
	 *            javax.servlet.http.HttpServletRequest
	 * @param response
	 *            javax.servlet.http.HttpServletResponse
	 * @exception javax.servlet.ServletException
	 *                The exception description.
	 * @exception java.io.IOException
	 *                The exception description.
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws javax.servlet.ServletException, java.io.IOException
	{

		performTask(req, res);
	}

	/**
	 * This method is invoked from the ServletMaintenance thread to perform
	 * timebased actions Creation date: (12/11/2001 10:47:42 AM)
	 */

	public void doMaintenance()
	{

		Connection connection = null;

		java.util.Date now = new java.util.Date();

		String methodName = "doMaintenance";

		System.out.println("Executing AutoRetryDBStatus Servlet....@" + now);

		try
		{
			connection = dataSource.getConnection(userID, password);
			connection.setAutoCommit(false);
		} catch (SQLException e)
		{
			maintenanceThread.shutDown();
			maintenanceThread = null;
			return;
		}

		try
		{
			// Check the Database Status
			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery(getDatabaseStatus());

			boolean databaseUp = false;

			// Check to see if database is up
			while (rs.next())
			{
				if (rs.getInt(1) > 0)
				{
					databaseUp = true;
				}
			}
			rs.close();
			statement.close();

			if (databaseUp)
			{

				// Release the connection back to the pool
				closeConnection(connection, methodName);

				// Swap the regions for ejb container
				try
				{
					System.out.println("Trying to Locate the EJB from JNDI");
					Object obj = HomeHelper.singleton().getHome("ecsts.CSDatabaseSwapLocalHome");
					System.out.println("Located the EJB from JNDI " + obj);
					CSDatabaseSwapLocalHome dbSwapLocalHome = (CSDatabaseSwapLocalHome) obj;
					CSDatabaseSwapLocal databaseSwap = dbSwapLocalHome.create();
					databaseSwap.swap();
					System.out.println("EJB Swap Completed");

					// Swap the regions for the web container
					String region = PropertyManager.getRegion();
					String backupRegion = PropertyManager.getBackupRegion();
					PropertyManager.setRegion(backupRegion);
					PropertyManager.setBackupRegion(region);

					// Send an Email to administrator
					sendMail();

				} catch (EJBException ex)
				{
					System.out.println("EJB Swap Failed");
					ex.printStackTrace();
					swapFailedMail();
				} catch (NamingException ne)
				{
					System.out.println("EJB Swap Failed");
					ne.printStackTrace();
					swapFailedMail();
				} catch (CreateException ce)
				{
					System.out.println("EJB Swap Failed");
					ce.printStackTrace();
					swapFailedMail();
				}

				// Destroy the servlet
				destroy();
			} else
			{
				// Database is not up, the servlet will be invoked later by the
				// thread
				System.out.println("Database is not up ....@" + now);
			}
		} catch (SQLException e)
		{

			// Create the ErrorDescriptor object and set the appropriate
			// properties
			// including notification, logging and error level
			ErrorDescriptor ed = new ErrorDescriptor(className, methodName);

			new ApplicationException(e.getMessage(), e, ed);

			System.out.println("SQLException in AutoRetryDBStatus " + e);
			System.out.println("Shutting Down the Thread ... ");

			maintenanceThread.shutDown();
			maintenanceThread = null;

			System.out.println("Thread Shutdown...OK");

			return;
		} finally
		{
			closeConnection(connection, methodName);
		}
	}

	/**
	 * This method is invoked as part of a post request from either a JSP Page
	 * or another servlet. Creation date: (12/11/2001 10:47:42 AM)
	 * 
	 * @param request
	 *            javax.servlet.http.HttpServletRequest
	 * @param response
	 *            javax.servlet.http.HttpServletResponse
	 * @exception javax.servlet.ServletException
	 *                The exception description.
	 * @exception java.io.IOException
	 *                The exception description.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws javax.servlet.ServletException, java.io.IOException
	{

		performTask(req, res);
	}

	/**
	 * Method to build the SQL Query to obtain the web user information.
	 * Creation date: (12/11/2001 10:47:42 AM)
	 * 
	 * @return java.lang.String
	 */

	public String getDatabaseStatus()
	{
		// Build the sql string using uid and password passed from the client
		String sql = "Select count(*) from " + PropertyManager.getWebRegion() + "FKKT_WEB_DB_STAT " + "where cd_qual = '" + PropertyManager.getWebRegion().toUpperCase().substring(0, 3) + "'" + "  and cd_stat = 'A'";
		return sql;
	}

	/**
	 * This method is called only once to instantiate the servlet. Creation
	 * date: (12/11/2001 10:47:42 AM)
	 * 
	 * @param config
	 *            javax.servlet.ServletConfig
	 * @exception javax.servlet.ServletException
	 *                The exception description.
	 */

	public void init(ServletConfig config) throws javax.servlet.ServletException
	{

		super.init(config);

		String methodName = "init";

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
	public void performTask(HttpServletRequest req, HttpServletResponse res)
	{

		java.util.Date now = new java.util.Date();

		String methodName = "performTask";

		System.out.println("Requested AutoRetryDBStatus Servlet....@" + now);

		if (maintenanceThread == null)
		{

			System.out.println("Starting Thread....@" + now);

			maintenanceThread = new ServletMaintenance(this, PropertyManager.getDbTimeToWait(), PropertyManager.getDbClassificationToWait());

			System.out.println("Started Thread....@" + now);
		} else
		{
			System.out.println("Thread already active....@" + now);
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
	public void sendMail()
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

	/**
	 * Method to Compose and send email message to the user Creation date:
	 * (4/16/2001 4:59:20 PM)
	 * 
	 * @exception javax.servlet.ServletException
	 *                The exception description.
	 * @exception java.io.IOException
	 *                The exception description.
	 */
	public void swapFailedMail()
	{

		try
		{
			EmailManager em = new EmailManager();
			em.addTo(PropertyManager.getEmailMsgNotify());
			em.setSubject("Swap Database Failed");

			String strBody = "NOTE: PLEASE DO NOT RESPOND DIRECTLY TO THIS E-MAIL MESSAGE. THIS ADDRESS IS NOT MONITORED.\n\n" + "To eCustomerService Administrator(s), " + "\n\n" + "This message has been sent as an indication that the application has " + "failed to swapped the database regions. \n\n" + "Primary Region in use is: " + PropertyManager.getRegion() + ".\n\n" + "Web Region in use is: " + PropertyManager.getWebRegion() + ".\n\n" + "Backup Region in use is: " + PropertyManager.getBackupRegion() + ".\n\n";

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
