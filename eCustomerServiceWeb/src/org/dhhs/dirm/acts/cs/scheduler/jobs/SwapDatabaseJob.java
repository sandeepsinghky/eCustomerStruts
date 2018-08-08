

package org.dhhs.dirm.acts.cs.scheduler.jobs;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;
import org.dhhs.dirm.acts.cs.EmailException;
import org.dhhs.dirm.acts.cs.EmailManager;
import org.dhhs.dirm.acts.cs.PropertyManager;
import org.dhhs.dirm.acts.cs.util.ApplicationException;
import org.dhhs.dirm.acts.cs.util.ErrorDescriptor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * SwapDatabaseJob.java
 * 
 * Property of State of North Carolina DHHS.
 * 
 * Creation Date: Jul 20, 2004 2:09:01 PM
 * 
 * @author RKodumagulla
 *
 */
public class SwapDatabaseJob implements Job
{

	private static final Logger log = Logger.getLogger(SwapDatabaseJob.class);

	/**
	 * Constructor for SwapDatabaseJob.
	 */
	public SwapDatabaseJob()
	{
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(JobExecutionContext)
	 */
	/*
	 * public void execute(JobExecutionContext arg0) throws
	 * JobExecutionException {
	 * 
	 * log.info("SwapDatabase Job Initiated");
	 * 
	 * // Swap the regions for servlet container String region =
	 * PropertyManager.getRegion(); String backupRegion =
	 * PropertyManager.getBackupRegion();
	 * PropertyManager.setRegion(backupRegion);
	 * PropertyManager.setBackupRegion(region);
	 * 
	 * // Swap the regions for ejb container try { Object obj =
	 * HomeHelper.singleton().getHome("ecsts.CSDatabaseSwapLocalHome");
	 * CSDatabaseSwapLocalHome dbSwapLocalHome = (CSDatabaseSwapLocalHome) obj;
	 * CSDatabaseSwapLocal databaseSwap = dbSwapLocalHome.create();
	 * databaseSwap.swap(); } catch (EJBException ex) { log.fatal(ex); } catch
	 * (NamingException ne) { log.fatal(ne); } catch (CreateException ce) {
	 * log.fatal(ce); }
	 * 
	 * sendMail();
	 * 
	 * log.info("SwapDatabase Job Completed Successfully"); }
	 */

	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{

		log.info("SwapDatabaseInvoker Job Initiated");

		String host = PropertyManager.getPrimaryURL();

		String urlString = host + "/SwapDatabase?id=0";

		log.info("URL: " + urlString);

		try
		{
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();

			log.info("Connection " + connection);

			connection.setDoInput(true);
			connection.setDoOutput(true);

			log.info(connection.getContent());
			log.info(connection.getContentEncoding());

		} catch (MalformedURLException mfe)
		{
			log.fatal(mfe);
		} catch (IOException ioe)
		{
			log.fatal(ioe);
		}
		log.info("SwapDatabaseInvoker Job Completed Successfully");
	}

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

}
