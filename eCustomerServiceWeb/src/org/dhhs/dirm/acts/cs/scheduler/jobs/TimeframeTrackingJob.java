

package org.dhhs.dirm.acts.cs.scheduler.jobs;

import java.util.PropertyResourceBundle;

import org.apache.log4j.Logger;
import org.dhhs.dirm.acts.cs.EmailException;
import org.dhhs.dirm.acts.cs.EmailManager;
import org.dhhs.dirm.acts.cs.businesslogic.AgentTrackingReportModel;
import org.dhhs.dirm.acts.cs.jasper.TimeframeTrackingDataSource;
import org.dhhs.dirm.acts.cs.util.ApplicationException;
import org.dhhs.dirm.acts.cs.util.DateFormatUtil;
import org.dhhs.dirm.acts.cs.util.ErrorDescriptor;
import org.dhhs.dirm.acts.cs.util.ScheduleUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;

import dori.jasper.engine.JRException;
import dori.jasper.engine.JasperExportManager;
import dori.jasper.engine.JasperFillManager;

/**
 * TimeframeTrackingJob.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Apr 13, 2004 2:28:24 PM
 * 
 * @author RKodumagulla
 *
 */
public class TimeframeTrackingJob implements Job
{

	private static final Logger	log	= Logger.getLogger(TimeframeTrackingJob.class);
	private String				to	= "";
	private String				cc	= "";
	private String				bcc	= "";

	/**
	 * Constructor for TimeframeTrackingJob.
	 */
	public TimeframeTrackingJob()
	{
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(JobExecutionContext)
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException
	{

		try
		{
			log.info("Executing TimeframeTrackingJob");

			loadProperties();

			Scheduler sched = context.getScheduler();
			String instGroup = context.getJobDetail().getGroup();

			String reportFile;
			String title = "AGENT TIMEFRAME TRACKING REPORT";
			String startDate = "";
			String endDate = "";

			reportFile = System.getProperty("ReportFilePath");

			java.util.Map parameters = new java.util.HashMap();
			parameters.put("ReportTitle", title);
			parameters.put("BaseDir", new java.io.File(reportFile).getParentFile());
			// parameters.put("BaseDir", new java.io.File(reportFile));

			// Based on the job group name, calculate start and end dates
			if (instGroup.equals("MonthlyReportGroup"))
			{
				// Run the report for the previous month
				// ScheduleUtil su = new ScheduleUtil(8,2004);
				ScheduleUtil su = new ScheduleUtil();
				su.computeMonth();
				startDate = su.getStartDate();
				endDate = su.getEndDate();
				parameters.put("month", su.getMonth());
				parameters.put("year", su.getYear());
			} else if (instGroup.equals("QuarterlyReportGroup"))
			{
				// Run the report for the previous quarter
				ScheduleUtil su = new ScheduleUtil();
				su.computeQuarter();
				startDate = su.getStartDate();
				endDate = su.getEndDate();
				parameters.put("month", su.getMonth());
				parameters.put("year", su.getYear());
			} else if (instGroup.equals("SemiAnnualReportGroup"))
			{
				// Run the report for the prior half of the year
				ScheduleUtil su = new ScheduleUtil();
				su.computeSemiAnnual();
				startDate = su.getStartDate();
				endDate = su.getEndDate();
				parameters.put("month", su.getMonth());
				parameters.put("year", su.getYear());
			} else if (instGroup.equals("AnnualReportGroup"))
			{
				// Run the report for the prior year
				ScheduleUtil su = new ScheduleUtil();
				su.computeAnnual();
				startDate = su.getStartDate();
				endDate = su.getEndDate();
				parameters.put("month", su.getMonth());
				parameters.put("year", su.getYear());
			}

			TimeframeTrackingDataSource ttds = new TimeframeTrackingDataSource();
			AgentTrackingReportModel atrm = AgentTrackingReportModel.getInstance();

			parameters.put("FROMDATE", new DateFormatUtil().format(startDate, 1));
			parameters.put("TODATE", new DateFormatUtil().format(endDate, 1));

			ttds.setData(atrm.getData(startDate, endDate));

			try
			{
				JasperFillManager.fillReportToFile(reportFile + "AgentTracking.jasper", "AgentTrackingReport", parameters, ttds);

				String pdf = System.getProperty("workingDir") + System.getProperty("file.separator") + "AgentTrackingReport.pdf";
				JasperExportManager.exportReportToPdfFile("AgentTrackingReport", pdf);
				sendEmail(pdf);

				// Add Locic not to delete this file but instead add the
				// month-end date to make this file unique.
				// java.io.File f = new java.io.File(pdf);
				// f.delete();

			} catch (JRException je)
			{
				throw new Exception(je.getMessage());
			} catch (EmailException ex)
			{
				throw new Exception(ex.getMessage());
			}
		} catch (Exception e)
		{
			log.error("Timeframe Tracking Report Job Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("TimeframeTrackingJob", "execute"));
		}
	}

	public void sendEmail(String file) throws EmailException
	{

		EmailManager em = new EmailManager();

		em.setAttach(true);
		em.addTo(to);
		if (cc != null && !cc.equals(""))
		{
			em.addCc(cc);
		}
		if (bcc != null && !bcc.equals(""))
		{
			em.addBcc(bcc);
		}

		em.setSubject("Timeframe Tracking Report");

		em.addAttachments(file);

		StringBuffer buf = new StringBuffer();

		buf.append("NOTE: PLEASE DO NOT RESPOND DIRECTLY TO THIS E-MAIL MESSAGE. THIS ADDRESS IS NOT MONITORED.\n\n");

		buf.append("This message has been sent in response to automated scheduled reports. " + "If you did not request this service or believe this " + "message has been sent to you in error, please contact ACTS Help Desk." + ".\n\n");

		String emailMsg = buf.toString();

		em.setBody(emailMsg);

		em.sendMail();
	}

	/**
	 * 
	 */
	public void loadProperties() throws Exception
	{
		/**
		 * Get information at runtime (from an external property file identified
		 * by CONFIG_BUNDLE_NAME) for the JobClass.
		 */

		try
		{
			String CONFIG_BUNDLE_NAME = this.getClass().getName();

			PropertyResourceBundle configBundle = (PropertyResourceBundle) PropertyResourceBundle.getBundle(CONFIG_BUNDLE_NAME);
			to = configBundle.getString("QUARTZ_JOB_DETAIL.TO");
			cc = configBundle.getString("QUARTZ_JOB_DETAIL.CC");
			bcc = configBundle.getString("QUARTZ_JOB_DETAIL.BCC");
		} catch (Exception e)
		{
			log.error("Properties file exception: " + e.getMessage());
			throw e;
		}
	}

}
