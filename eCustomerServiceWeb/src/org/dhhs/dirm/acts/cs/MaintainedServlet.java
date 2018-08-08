

package org.dhhs.dirm.acts.cs;

/**
 * An Interface that must be implemented by any class that intends to use the
 * ServletMaintenance Class to perform time based actions.
 * <p>
 * Note: Make sure to implement the doMaintenance method as this is the method
 * that will be called by the ServletMaintenance Class.
 * <p>
 * Creation date: (6/18/2001 8:47:42 AM)
 * 
 * @author: Ramakanth Kodumagulla
 */
public interface MaintainedServlet
{
	/**
	 * A method that must be implemented by the class so that the
	 * ServletMaintenance class can perform timebased functions Creation date:
	 * (6/18/2001 8:48:13 AM)
	 */
	public void doMaintenance();
}
