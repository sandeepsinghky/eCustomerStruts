

package org.dhhs.dirm.acts.cs.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

/**
 * ScheduleUtil.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: May 28, 2004 10:46:34 AM
 * 
 * @author RKodumagulla
 *
 */
public class ScheduleUtil
{

	private static final Logger	log					= Logger.getLogger(ScheduleUtil.class);

	private static final int	FIRST_QUARTER_END	= 2;

	private static final int	SECOND_QUARTER_END	= 5;

	private static final int	THIRD_QUARTER_END	= 8;

	private static final int	FOURTH_QUARTER_END	= 11;

	private static final int	FIRST_HALF			= 5;

	private static final int	SECOND_HALF			= 11;

	private String				startDate;

	private String				endDate;

	private String				runDate;

	private String				month;

	private String				year;

	private Calendar			rightNow;

	/**
	 * Constructor for ScheduleUtil.
	 */
	public ScheduleUtil()
	{
		rightNow = Calendar.getInstance();
	}

	/**
	 * Constructor for overide on date
	 */
	public ScheduleUtil(int month, int year)
	{
		rightNow = Calendar.getInstance();
		rightNow.set(Calendar.MONTH, month);
		rightNow.set(Calendar.YEAR, year);
		rightNow.getTime();
	}

	public void computeMonth()
	{

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		java.util.Date now = rightNow.getTime();

		rightNow.add(Calendar.MONTH, -1);
		rightNow.getTime();
		log.debug("Right Now : " + rightNow);

		int daysInMonth = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);

		rightNow.set(Calendar.DATE, daysInMonth);

		java.util.Date to = rightNow.getTime();
		endDate = sdf.format(to);
		log.debug("Month End Date : " + endDate);

		rightNow.set(Calendar.DAY_OF_MONTH, 1);
		java.util.Date from = rightNow.getTime();
		startDate = sdf.format(from);
		log.debug("Month Start Date : " + startDate);

		month = new Integer(rightNow.get(Calendar.MONTH) + 1).toString();
		year = new Integer(rightNow.get(Calendar.YEAR)).toString();
	}

	public void computeQuarter()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		java.util.Date now = rightNow.getTime();

		int mnth = rightNow.get(Calendar.MONTH);
		log.debug("Right Now : " + rightNow);

		if (mnth <= FIRST_QUARTER_END)
		{
			rightNow.add(Calendar.YEAR, -1);
			rightNow.set(Calendar.MONTH, FOURTH_QUARTER_END);
			rightNow.getTime();
		} else if (mnth > FIRST_QUARTER_END && mnth <= SECOND_QUARTER_END)
		{
			rightNow.set(Calendar.MONTH, FIRST_QUARTER_END);
			rightNow.getTime();
		} else if (mnth > SECOND_QUARTER_END && mnth <= THIRD_QUARTER_END)
		{
			rightNow.set(Calendar.MONTH, SECOND_QUARTER_END);
			rightNow.getTime();
		} else if (mnth > THIRD_QUARTER_END && mnth <= FOURTH_QUARTER_END)
		{
			rightNow.set(Calendar.MONTH, THIRD_QUARTER_END);
			rightNow.getTime();
		}
		int daysInMonth = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
		rightNow.set(Calendar.DATE, daysInMonth);
		rightNow.getTime();

		java.util.Date to = rightNow.getTime();
		endDate = sdf.format(to);
		log.debug("Quarter End Date : " + endDate);

		rightNow.set(Calendar.DAY_OF_MONTH, 1);
		rightNow.add(Calendar.MONTH, -2);
		java.util.Date from = rightNow.getTime();
		startDate = sdf.format(from);
		log.debug("Quarter Start Date : " + startDate);

		month = new Integer(rightNow.get(Calendar.MONTH) + 1).toString();
		year = new Integer(rightNow.get(Calendar.YEAR)).toString();

	}

	public void computeSemiAnnual()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		java.util.Date now = rightNow.getTime();

		int mnth = rightNow.get(Calendar.MONTH);
		log.debug("Right Now : " + rightNow);

		if (mnth <= FIRST_HALF)
		{
			rightNow.add(Calendar.YEAR, -1);
			rightNow.set(Calendar.MONTH, SECOND_HALF);
			rightNow.getTime();
		} else if (mnth > FIRST_HALF)
		{
			rightNow.set(Calendar.MONTH, FIRST_HALF);
			rightNow.getTime();
		}

		int daysInMonth = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
		rightNow.set(Calendar.DATE, daysInMonth);
		rightNow.getTime();

		java.util.Date to = rightNow.getTime();
		endDate = sdf.format(to);
		log.debug("SemiAnnual : " + endDate);

		rightNow.set(Calendar.DAY_OF_MONTH, 1);
		rightNow.add(Calendar.MONTH, -5);
		java.util.Date from = rightNow.getTime();
		startDate = sdf.format(from);
		log.debug("SemiAnnual : " + startDate);

		month = new Integer(rightNow.get(Calendar.MONTH) + 1).toString();
		year = new Integer(rightNow.get(Calendar.YEAR)).toString();

	}

	public void computeAnnual()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		java.util.Date now = rightNow.getTime();

		int mnth = rightNow.get(Calendar.MONTH);
		log.debug("Right Now : " + rightNow);

		rightNow.add(Calendar.YEAR, -1);
		rightNow.set(Calendar.MONTH, 11);
		rightNow.getTime();

		int daysInMonth = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
		rightNow.set(Calendar.DATE, daysInMonth);
		rightNow.getTime();

		java.util.Date to = rightNow.getTime();
		endDate = sdf.format(to);
		log.debug("Annual : " + endDate);

		rightNow.set(Calendar.DAY_OF_MONTH, 1);
		rightNow.add(Calendar.MONTH, -11);
		java.util.Date from = rightNow.getTime();
		startDate = sdf.format(from);
		log.debug("Annual : " + startDate);

		month = new Integer(rightNow.get(Calendar.MONTH) + 1).toString();
		year = new Integer(rightNow.get(Calendar.YEAR)).toString();

	}

	/**
	 * Returns the endDate.
	 * 
	 * @return String
	 */
	public String getEndDate()
	{
		return endDate;
	}

	/**
	 * Returns the runDate.
	 * 
	 * @return String
	 */
	public String getRunDate()
	{
		return runDate;
	}

	/**
	 * Returns the startDate.
	 * 
	 * @return String
	 */
	public String getStartDate()
	{
		return startDate;
	}

	/**
	 * Sets the endDate.
	 * 
	 * @param endDate
	 *            The endDate to set
	 */
	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}

	/**
	 * Sets the runDate.
	 * 
	 * @param runDate
	 *            The runDate to set
	 */
	public void setRunDate(String runDate)
	{
		this.runDate = runDate;
	}

	/**
	 * Sets the startDate.
	 * 
	 * @param startDate
	 *            The startDate to set
	 */
	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}

	public static void main(String[] args)
	{
		ScheduleUtil su = new ScheduleUtil();
		su.computeMonth();
		su.computeQuarter();
		su.computeSemiAnnual();
		su.computeAnnual();
	}
	/**
	 * Returns the month.
	 * 
	 * @return String
	 */
	public String getMonth()
	{
		return month;
	}

	/**
	 * Returns the year.
	 * 
	 * @return String
	 */
	public String getYear()
	{
		return year;
	}

	/**
	 * Sets the month.
	 * 
	 * @param month
	 *            The month to set
	 */
	public void setMonth(String month)
	{
		this.month = month;
	}

	/**
	 * Sets the year.
	 * 
	 * @param year
	 *            The year to set
	 */
	public void setYear(String year)
	{
		this.year = year;
	}

}
