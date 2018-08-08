

package org.dhhs.dirm.acts.cs.jasper.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.quartz.Scheduler;

/**
 * InitializeScheduler.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Apr 13, 2004 11:35:35 AM
 * 
 * @author RKodumagulla
 *
 */
public class InitializeScheduler extends GenericServlet
{

	private final static Logger	log						= Logger.getLogger(InitializeScheduler.class);
	private static final String	SCHEDULER_CONFIG_NAME	= "Scheduler.properties";

	/**
	 * Constructor for InitializeScheduler.
	 */
	public InitializeScheduler()
	{
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Servlet#service(ServletRequest, ServletResponse)
	 */
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException
	{

		super.init(config);

		try
		{

			ServletContext context = this.getServletConfig().getServletContext();

			String reportFilePath = context.getRealPath("/reports/");

			System.setProperty("ReportFilePath", reportFilePath);

			String workingDir = System.getProperty("workingDir");

			File f = new File(workingDir, SCHEDULER_CONFIG_NAME);

			System.out.println("Loading Scheduler Properties from " + f.getName());

			FileInputStream fis = new FileInputStream(f);

			Properties properties = new Properties();
			properties.load(fis);

			System.out.println("Properties Successsfully Loaded...");

			org.quartz.impl.StdSchedulerFactory sf = new org.quartz.impl.StdSchedulerFactory(properties);

			Scheduler sched = sf.getScheduler();

			System.out.println("Initialization Complete" + sched);

			System.out.println("Not Scheduling any Jobs - relying on XML definitions");

			System.out.println("Starting Scheduler");

			sched.start();

			/*
			 * JobDetail jobDetail = new JobDetail("Sample", "Report",
			 * CorrectionReportJob.class);
			 * jobDetail.getJobDataMap().put("Type","FULL");
			 * 
			 * CronTrigger trigger = new CronTrigger("Sample", "Report");
			 * trigger.setCronExpression("0 * 15 * * ?");
			 * sched.scheduleJob(jobDetail,trigger);
			 */
			/*
			 * try { Thread.sleep(60L * 1000L); // wait five minutes to show
			 * jobs } catch (Exception e) { e.printStackTrace(); }
			 * System.out.println("Shutting Down");
			 * 
			 * sched.shutdown(true);
			 * 
			 * System.out.println("Shutdown Complete"); SchedulerMetaData
			 * metaData = sched.getMetaData();
			 * System.out.println(metaData.getSummary()); System.out.println(
			 * "Executed " + metaData.numJobsExecuted() + " jobs.");
			 * 
			 */
		} catch (org.quartz.SchedulerException se)
		{
			se.printStackTrace();
		} catch (IOException ioe)
		{
			ioe.printStackTrace();
			// } catch (java.text.ParseException pe) {
			// pe.printStackTrace();
		}

	}

}
