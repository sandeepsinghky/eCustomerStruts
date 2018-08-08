

package org.dhhs.dirm.acts.cs.scheduler.jobs;

import org.dhhs.dirm.acts.cs.EmailException;
import org.dhhs.dirm.acts.cs.EmailManager;
import org.dhhs.dirm.acts.cs.PropertyManager;
import org.dhhs.dirm.acts.cs.jasper.ScheduleTasksDataSource;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import dori.jasper.engine.JRException;
import dori.jasper.engine.JasperExportManager;
import dori.jasper.engine.JasperFillManager;

/**
 * ScheduleTasks.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Apr 14, 2004 4:51:00 PM
 * 
 * @author RKodumagulla
 *
 */

public class ScheduleTasks implements Job
{

	/**
	 * Constructor for ScheduleTasks.
	 */
	public ScheduleTasks()
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

		Scheduler sched = context.getScheduler();

		String reportFile;
		java.util.Vector data = new java.util.Vector();
		String title = "Schedule Tasks Report";

		reportFile = System.getProperty("ReportFilePath");

		java.util.Map parameters = new java.util.HashMap();
		parameters.put("ReportTitle", title);
		parameters.put("BaseDir", new java.io.File(reportFile).getParentFile());

		ScheduleTasksDataSource st = new ScheduleTasksDataSource();

		try
		{
			String[] groups = sched.getTriggerGroupNames();

			for (int i = 0; i < groups.length; i++)
			{
				String[] names = sched.getTriggerNames(groups[i]);
				for (int j = 0; j < names.length; j++)
				{
					Trigger trigger = sched.getTrigger(names[j], groups[i]);
					if (trigger != null)
					{
						data.addElement(trigger);
					}
				}
			}
		} catch (SchedulerException se)
		{
			se.printStackTrace();
		}

		st.setData(data);

		try
		{
			String temp = System.getProperty("workingDir") + System.getProperty("file.separator") + "ScheduleTasksReport";
			JasperFillManager.fillReportToFile(reportFile + "ScheduleTasks.jasper", temp, parameters, st);

			String pdf = System.getProperty("workingDir") + System.getProperty("file.separator") + "ScheduleTasksReport.pdf";
			JasperExportManager.exportReportToPdfFile(temp, pdf);
			sendEmail(pdf);
			java.io.File f = new java.io.File(pdf);
			f.delete();
		} catch (JRException je)
		{
			je.printStackTrace();
		} catch (EmailException ex)
		{
			ex.printStackTrace();
		}

	}

	public void sendEmail(String file) throws EmailException
	{

		EmailManager em = new EmailManager();

		em.setAttach(true);
		em.addTo(PropertyManager.getEmailMsgNotify());

		em.setSubject("Schedule Tasks Report");

		em.addAttachments(file);

		StringBuffer buf = new StringBuffer();

		buf.append("NOTE: PLEASE DO NOT RESPOND DIRECTLY TO THIS E-MAIL MESSAGE. THIS ADDRESS IS NOT MONITORED.\n\n");

		buf.append("This message has been sent in response to the daily schedule tasks. " + "If you did not request this service or believe this " + "message has been sent to you in error, please contact ACTS Help Desk." + ".\n\n");

		String emailMsg = buf.toString();

		em.setBody(emailMsg);

		em.sendMail();
		return;
	}

}
