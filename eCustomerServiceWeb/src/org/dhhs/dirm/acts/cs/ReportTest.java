

package org.dhhs.dirm.acts.cs;

import java.util.Date;

import org.dhhs.dirm.acts.cs.scheduler.jobs.TimeframeTrackingJob;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;

/**
 * ReportTest.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Jun 14, 2004 9:42:58 AM
 * 
 * @author RKodumagulla
 *
 */
public class ReportTest
{

	/**
	 * Constructor for ReportTest.
	 */
	public ReportTest()
	{

		try
		{

			System.setProperty("workingDir", "C:/ApplicationServers/ecsts");
			String fullPath = System.getProperty("workingDir");

			try
			{
				PropertyManager.loadAppProperties(fullPath);
			} catch (Exception e)
			{
				e.printStackTrace();
			}

			try
			{
				PropertyManager.loadDBProperties(fullPath);
			} catch (Exception e)
			{
				e.printStackTrace();
			}

			SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();

			Scheduler sched = schedFact.getScheduler();

			sched.start();

			long startTime = System.currentTimeMillis() + 10000L;

			System.setProperty("ReportFilePath", "C:/workspace/eCustomerServiceWeb/Web Content/reports/");

			/*
			 * JobDetail jobDetail = new JobDetail("TestJob1",
			 * "MonthlyReportGroup", SupportTotalJob.class); SimpleTrigger
			 * trigger = new SimpleTrigger("myTrigger1", "MonthlyReportGroup",
			 * new Date(startTime), null, 0, 0L); sched.scheduleJob(jobDetail,
			 * trigger);
			 * 
			 * jobDetail = new JobDetail("TestJob2", "MonthlyReportGroup",
			 * CorrectionReportJob.class); trigger = new
			 * SimpleTrigger("myTrigger2", "MonthlyReportGroup", new
			 * Date(startTime), null, 0, 0L); sched.scheduleJob(jobDetail,
			 * trigger);
			 */

			JobDetail jobDetail = new JobDetail("TestJob3", "MonthlyReportGroup", TimeframeTrackingJob.class);
			SimpleTrigger trigger = new SimpleTrigger("myTrigger3", "MonthlyReportGroup", new Date(startTime), null, 0, 0L);
			sched.scheduleJob(jobDetail, trigger);

			// sched.shutdown();
			// System.exit(0);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{

		new ReportTest();
	}
}
