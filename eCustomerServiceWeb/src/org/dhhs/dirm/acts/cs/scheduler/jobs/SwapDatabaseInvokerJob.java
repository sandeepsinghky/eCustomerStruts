/**
 * Created on Jul 20, 2004
 *
 * To change this generated comment edit the template variable "filecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of file
 * comments go to Window>Preferences>Java>Code Generation.
 */


package org.dhhs.dirm.acts.cs.scheduler.jobs;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;
import org.dhhs.dirm.acts.cs.PropertyManager;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * SwapDatabaseInvokerJob.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Jul 20, 2004 3:20:14 PM
 * 
 * @author RKodumagulla
 *
 */
public class SwapDatabaseInvokerJob implements Job
{

	private static final Logger log = Logger.getLogger(SwapDatabaseInvokerJob.class);

	/**
	 * Constructor for SwapDatabaseInvokerJob.
	 */
	public SwapDatabaseInvokerJob()
	{
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(JobExecutionContext)
	 */
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{

		log.info("SwapDatabaseInvoker Job Initiated");

		String host = PropertyManager.getPrimaryURL();

		String urlString = host + "/AutoRetryDBStatus";

		log.info("URL: " + urlString);

		try
		{
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();
			log.info("Connection " + connection);

			connection.setDoInput(true);
			connection.setDoOutput(true);

			connection.getContent();
			connection.getContentEncoding();

		} catch (MalformedURLException mfe)
		{
			log.fatal(mfe);
		} catch (IOException ioe)
		{
			log.fatal(ioe);
		}
		log.info("SwapDatabaseInvoker Job Completed Successfully");
	}

}
