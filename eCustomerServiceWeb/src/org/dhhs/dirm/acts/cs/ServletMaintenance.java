

package org.dhhs.dirm.acts.cs;

/**
 * A thread that helps a servlet perform time based actions. This system is
 * useful for doing things such as saving the servlet's state or performing
 * other periodic tasks.
 * <p>
 * The user of this class specifies a number of minutes and hours to wait before
 * invoking the doMaintenance() function of the passed in MaintainedServlet
 * object.
 * <p>
 * For example, a servlet should "implement ServletMaintenace" and then have a
 * doMainenance() method that contains actions that should be performed
 * periodically. Then, the servlet should define a ServletMaintenance object as
 * something like:
 * <p>
 * 
 * <pre>
 * ServletMaintenance timer = new ServletMaintenance(this, 30, ServletMaintenance.MINUTES);
 * </pre>
 * <p>
 * This starts up a timer that will call doMaintenance() every thirty minutes.
 * When the thread needs to be stopped, the shutDown() method should be called.
 *
 * Creation date: (6/18/2001 8:48:59 AM)
 * 
 * @author: Ramakanth Kodumagulla
 */
public class ServletMaintenance extends Thread
{
	/**
	 * Constant representing seconds.
	 */
	public static final int		SECONDS	= 0;
	/**
	 * Constant representing minutes.
	 */
	public static final int		MINUTES	= 1;
	/**
	 * Constant representing hours.
	 */
	public static final int		HOURS	= 2;
	/**
	 * Constant representing days.
	 */
	public static final int		DAYS	= 3;

	private MaintainedServlet	servletReference;
	private boolean				done	= false;
	private int					secondsToWait;
	private int					minutesToWait;
	private int					hoursToWait;
	private int					daysToWait;
	// A series of time constants to make the source more readable.
	// They are measured in milleseconds.
	private final long			SECOND	= 1000;
	private final long			MINUTE	= SECOND * 60;
	private final long			HOUR	= MINUTE * 60;
	private final long			DAY		= HOUR * 24;

	/**
	 * Class users must pass in a servlet that implements the MaintainedServlet
	 * interface as well as a length of time that this thread will wait during
	 * each interval. Note, after calling the constructor, the thread will
	 * automatically start running.
	 * <p>
	 * The third argument to the constructor should be one of the following:
	 * <ul>
	 * <li>ServletMaintenance.SECONDS
	 * <li>ServletMaintenance.MINUTES
	 * <li>ServletMaintenance.HOURS
	 * <li>ServletMaintenance.DAYS
	 * </ul>
	 *
	 * @param reference
	 *            some object that implements MaintainedServlet
	 * @param lengthTime
	 *            a length of time to wait
	 * @param timeType
	 *            the time unit that should be used
	 */
	public ServletMaintenance(MaintainedServlet reference, int lengthTime, int timeType)
	{
		servletReference = reference;
		secondsToWait = 0;
		minutesToWait = 0;
		hoursToWait = 0;
		daysToWait = 0;

		if (timeType == ServletMaintenance.SECONDS)
			secondsToWait = lengthTime;
		else if (timeType == ServletMaintenance.MINUTES)
			minutesToWait = lengthTime;
		else if (timeType == ServletMaintenance.HOURS)
			hoursToWait = lengthTime;
		else if (timeType == ServletMaintenance.DAYS)
			daysToWait = lengthTime;

		// Start this thread
		start();
	}
	/**
	 * Insert the method's description here. Creation date: (6/18/2001 8:53:09
	 * AM)
	 * 
	 * @return int
	 */
	public int getDaysToWait()
	{
		return daysToWait;
	}
	/**
	 * Insert the method's description here. Creation date: (6/18/2001 8:53:09
	 * AM)
	 * 
	 * @return int
	 */
	public int getHoursToWait()
	{
		return hoursToWait;
	}
	/**
	 * Insert the method's description here. Creation date: (6/18/2001 8:53:09
	 * AM)
	 * 
	 * @return int
	 */
	public int getMinutesToWait()
	{
		return minutesToWait;
	}
	/**
	 * Insert the method's description here. Creation date: (6/18/2001 8:53:09
	 * AM)
	 * 
	 * @return int
	 */
	public int getSecondsToWait()
	{
		return secondsToWait;
	}
	/**
	 * Insert the method's description here. Creation date: (6/18/2001 8:53:09
	 * AM)
	 * 
	 * @return boolean
	 */
	public boolean isDone()
	{
		return done;
	}
	/**
	 * Calls the doMaintenance() method of the MaintainedServlet object and then
	 * sleeps for the desired interval. This process will continue until the
	 * shutDown() method is called. This method will be automatically started
	 * when the object is constructed. You should not call the run method or the
	 * start method in your code.
	 */
	public void run()
	{
		// While the user has not called the shutDown() method.
		while (!done)
		{
			try
			{
				// Wait for the specified amount of time before continuing.
				long timeToWait = secondsToWait * SECOND;
				timeToWait += minutesToWait * MINUTE;
				timeToWait += hoursToWait * HOUR;
				timeToWait += daysToWait * DAY;
				sleep(timeToWait);
				// Tell the servlet to perform the maintenance function
				if (!done)
				{
					servletReference.doMaintenance();
				}
			} catch (Exception e)
			{
				// we'll ignore errors...
			}
		}
	}
	/**
	 * Insert the method's description here. Creation date: (6/18/2001 8:53:09
	 * AM)
	 * 
	 * @param newDaysToWait
	 *            int
	 */
	public void setDaysToWait(int newDaysToWait)
	{
		daysToWait = newDaysToWait;
	}
	/**
	 * Insert the method's description here. Creation date: (6/18/2001 8:53:09
	 * AM)
	 * 
	 * @param newDone
	 *            boolean
	 */
	public void setDone(boolean newDone)
	{
		done = newDone;
	}
	/**
	 * Insert the method's description here. Creation date: (6/18/2001 8:53:09
	 * AM)
	 * 
	 * @param newHoursToWait
	 *            int
	 */
	public void setHoursToWait(int newHoursToWait)
	{
		hoursToWait = newHoursToWait;
	}
	/**
	 * Insert the method's description here. Creation date: (6/18/2001 8:53:09
	 * AM)
	 * 
	 * @param newMinutesToWait
	 *            int
	 */
	public void setMinutesToWait(int newMinutesToWait)
	{
		minutesToWait = newMinutesToWait;
	}
	/**
	 * Insert the method's description here. Creation date: (6/18/2001 8:53:09
	 * AM)
	 * 
	 * @param newSecondsToWait
	 *            int
	 */
	public void setSecondsToWait(int newSecondsToWait)
	{
		secondsToWait = newSecondsToWait;
	}
	/**
	 * This should be called when the thread needs to be shut-down.
	 */
	public void shutDown()
	{
		done = true;
	}
}
