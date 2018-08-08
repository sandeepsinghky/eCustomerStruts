

package org.dhhs.dirm.acts.cs.jasper;

import java.text.SimpleDateFormat;
import java.util.Vector;

import org.quartz.Trigger;

import dori.jasper.engine.JRDataSource;
import dori.jasper.engine.JRException;
import dori.jasper.engine.JRField;

/**
 * ScheduleTasksDataSource.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Apr 15, 2004 1:24:50 PM
 * 
 * @author rkodumagulla
 *
 */
public class ScheduleTasksDataSource implements JRDataSource
{

	private Vector	data;

	private int		index	= -1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see dori.jasper.engine.JRDataSource#getFieldValue(JRField)
	 */
	public Object getFieldValue(JRField field) throws JRException
	{

		Object value = null;

		String fieldName = field.getName();

		if (fieldName.equals("triggerName"))
		{
			return ((Trigger) data.elementAt(index)).getName();
		} else if (fieldName.equals("jobDetails"))
		{
			return ((Trigger) data.elementAt(index)).getJobName();
		} else if (fieldName.equals("prevRun"))
		{
			if (((Trigger) data.elementAt(index)).getPreviousFireTime() == null)
			{
				return "N/A";
			}
			java.util.Date fireDate = ((Trigger) data.elementAt(index)).getPreviousFireTime();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

			String formattedDate = sdf.format(fireDate);
			return formattedDate;
		} else if (fieldName.equals("nextRun"))
		{
			if (((Trigger) data.elementAt(index)).getNextFireTime() == null)
			{
				return "N/A";
			}
			java.util.Date nextFireTime = ((Trigger) data.elementAt(index)).getNextFireTime();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

			String formattedDate = sdf.format(nextFireTime);
			return formattedDate;
		} else if (fieldName.equals("startTime"))
		{
			if (((Trigger) data.elementAt(index)).getStartTime() == null)
			{
				return "N/A";
			}
			java.util.Date startTime = ((Trigger) data.elementAt(index)).getStartTime();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

			String formattedDate = sdf.format(startTime);
			return formattedDate;
		} else if (fieldName.equals("misfireInstruction"))
		{
			return new Integer(((Trigger) data.elementAt(index)).getMisfireInstruction()).toString();
		} else if (fieldName.equals("isVolatile"))
		{
			return new Boolean(((Trigger) data.elementAt(index)).isVolatile()).toString();
		} else
		{
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dori.jasper.engine.JRDataSource#next()
	 */
	public boolean next() throws JRException
	{
		index++;

		return (index < data.size());
	}
	/**
	 * Returns the data.
	 * 
	 * @return Vector
	 */
	public Vector getData()
	{
		return data;
	}

	/**
	 * Sets the data.
	 * 
	 * @param data
	 *            The data to set
	 */
	public void setData(Vector data)
	{
		this.data = data;
	}

}
