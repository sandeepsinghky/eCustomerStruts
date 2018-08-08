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
import java.security.Security;

import org.apache.log4j.Logger;
import org.dhhs.dirm.acts.cs.PropertyManager;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.sun.net.ssl.HttpsURLConnection;

/**
 * SwapDatabaseInvokerSSLJob.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Jul 20, 2004 3:20:38 PM
 * 
 * @author RKodumagulla
 *
 */
public class SwapDatabaseInvokerSSLJob implements Job
{

	private static final Logger log = Logger.getLogger(SwapDatabaseInvokerSSLJob.class);

	/**
	 * Constructor for SwapDatabaseInvokerSSLJob.
	 */
	public SwapDatabaseInvokerSSLJob()
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
		log.info("SwapDatabaseInvokerSSL Job Initiated");

		String host = PropertyManager.getPrimaryURL();

		String urlString = host + "/AutoRetryDBStatus";

		log.info("URL: " + urlString);

		System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");

		try
		{
			Class clsFactory = Class.forName("com.sun.net.ssl.internal.ssl.Provider");
			if ((null != clsFactory) && (null == java.security.Security.getProvider("SunJSSE")))
			{
				Security.addProvider((com.sun.net.ssl.internal.ssl.Provider) clsFactory.newInstance());
			}
		} catch (ClassNotFoundException cnfe)
		{
			log.fatal(cnfe);
		} catch (InstantiationException ie)
		{
			log.fatal(ie);
		} catch (IllegalAccessException iae)
		{
			log.fatal(iae);
		}

		try
		{
			URL url = new URL(urlString);

			URLConnection connection = url.openConnection();

			HttpsURLConnection secConn = (HttpsURLConnection) connection;

			log.fatal("Secure Connection Made to host using: " + secConn);

			secConn.setDoInput(true);
			secConn.setDoOutput(true);

			secConn.getContent();
			secConn.getContentEncoding();

			secConn.disconnect();

		} catch (MalformedURLException mfe)
		{
			log.fatal(mfe);
		} catch (IOException ioe)
		{
			log.fatal(ioe);
		}
		log.info("SwapDatabaseInvokerSSL Job Completed Successfully");
	}
}
