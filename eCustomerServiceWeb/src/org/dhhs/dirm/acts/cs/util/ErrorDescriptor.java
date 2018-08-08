

package org.dhhs.dirm.acts.cs.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Vector;

/**
 * com.sysrad.util.ErrorDescriptor
 *
 * This is a utility class that must be instantiated whenever an exception
 * occurs in the system and must be included in the catch statement
 * 
 * It is recommended that the class or method that actually instantiates this
 * object pass the fully qualified classname, method name and object details
 * from the catch block.
 *
 * Notification and Logging is turned on by default. If for whatever reason, you
 * decide not to notify and/or log this error, call the appropriate
 * setErrLogRequired(false) and setErrNotify(false);
 *
 * errLevel is set to FATAL - 0 by default. You can change the severity of the
 * error by calling the setErrLevel(int) by passing in the appropriate value
 *
 * Possible values: 0 FATAL 1 ERROR 2 WARNING 3 INFO 4 DEBUG
 *
 * <pre>
 *		try {
 *			........
 *			........
 *		} catch (Exception e) {
 *			ErrorDescriptor errD = new ErrorDescriptor(fully qualified classname,method name);
 *          errD.addAppVariable(String1);
 *			errD.addAppVariable(String2);
 *		}
 *
 * </pre>
 *
 * @author: Ramakanth Kodumagulla
 */
public class ErrorDescriptor
{

	// The Class where the error occurred
	private String		errClass;

	// The Method within the Class that the error occurred
	private String		errMethod;

	// The Error Message String that was set
	private String		errMessage;

	// The Error Object that was passed from the Exception Object
	private Object		errObject;

	// The actual time the error descriptor object was created
	private Timestamp	timestamp;

	// Error Level of the exception
	private int			errLevel;

	// True or False to indicate if this error requires a notification
	private boolean		errNotify		= true;

	// True or False to indicate if this error must be logged
	private boolean		errLogRequired	= true;

	// Vector to hold supporting application variables that will
	// assist the administrator to identify the source of the abend
	private Vector		appVariables;

	/**
	 * Constructor that requires class & method to debug the exception
	 * 
	 * @param errClass
	 *            java.lang.String
	 * @param errMethod
	 *            java.lang.String
	 */
	public ErrorDescriptor(String errClass, String errMethod)
	{

		this.errClass = errClass;

		this.errMethod = errMethod;

		buildTimestamp();
	}
	/**
	 * Constructor that requires class, method, message & error object to debug
	 * the exception
	 *
	 * It is recommended that the classname be fully qualified example:
	 * com.foo.bar if bar the class in the package com.foo.bar
	 *
	 * @param errClass
	 *            java.lang.String
	 * @param errMethod
	 *            java.lang.String
	 * @param errMessage
	 *            java.lang.String
	 * @param errObject
	 *            java.lang.Object
	 */
	public ErrorDescriptor(String errClass, String errMethod, String errMessage, Object errObject)
	{

		this.errClass = errClass;

		this.errMethod = errMethod;

		this.errMessage = errMessage;

		this.errObject = errObject;

		buildTimestamp();
	}
	/**
	 * This method provides the ability to add any supporting debug data that
	 * helps the application programmer or administrator identify the source of
	 * an exception.
	 *
	 * String that is passed to this method may contain any JavaBeans, or any
	 * other information but preferable in a formatted way with each name value
	 * pair followed with a newline character
	 *
	 * Creation date: (4/8/2002 12:25:05 PM)
	 * 
	 * @param var
	 *            java.lang.String
	 */
	public void addAppVariable(String var)
	{
		appVariables.addElement(var);
	}
	/**
	 * This method creates a timestamp that may identify the time of
	 * constructing this object.It also initializes all the other variables.
	 */
	private void buildTimestamp()
	{

		// Obtain the current timestamp using java date objects

		Calendar c = Calendar.getInstance();

		java.util.Date now = c.getTime();

		java.sql.Date date = new java.sql.Date(now.getTime());

		timestamp = new Timestamp(date.getTime());

		// Error Logging is true by default
		errLogRequired = true;

		// Error Notification is true by default
		errNotify = true;

		// Error Level is ERROR by default
		errLevel = Constants.ERROR;

		// initialize appVariables
		appVariables = new Vector();
	}
	/**
	 * Compares two objects for equality. Returns a boolean that indicates
	 * whether this object is equivalent to the specified object. This method is
	 * used when an object is stored in a hashtable.
	 * 
	 * @param obj
	 *            the Object to compare with
	 * @return true if these Objects are equal; false otherwise.
	 * @see java.util.Hashtable
	 */
	public boolean equals(Object obj)
	{
		// Insert code to compare the receiver and obj here.
		// This implementation forwards the message to super. You may replace or
		// supplement this.
		// NOTE: obj might be an instance of any class
		return super.equals(obj);
	}
	/**
	 * Returns the vector of debug strings that have been passed by the class
	 * that instantiated the object
	 *
	 * Creation date: (4/8/2002 1:02:05 PM)
	 * 
	 * @return java.util.Vector
	 */
	public java.util.Vector getAppVariables()
	{
		return appVariables;
	}
	/**
	 * Returns the fully qualified classname that has been passed by the class
	 * that instantiated the object
	 *
	 * Creation date: (3/28/2002 11:33:11 PM)
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getErrClass()
	{
		return errClass;
	}
	/**
	 * Returns the error Level that has been passed by the class that
	 * instantiated the object
	 *
	 * Creation date: (3/28/2002 11:33:11 PM)
	 * 
	 * @return int
	 */
	public int getErrLevel()
	{
		return errLevel;
	}
	/**
	 * Returns the error message that has been passed by the class that
	 * instantiated the object
	 *
	 * Creation date: (3/28/2002 11:33:11 PM)
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getErrMessage()
	{
		return errMessage;
	}
	/**
	 * Returns the error method that has been passed by the class that
	 * instantiated the object
	 *
	 * Creation date: (3/28/2002 11:33:11 PM)
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getErrMethod()
	{
		return errMethod;
	}
	/**
	 * Returns the error object details that has been passed by the class that
	 * instantiated the object
	 *
	 * Creation date: (3/28/2002 11:33:11 PM)
	 * 
	 * @return java.lang.Object
	 */
	public java.lang.Object getErrObject()
	{
		return errObject;
	}
	/**
	 * Returns the timestamp when the error occurred
	 *
	 * Creation date: (3/28/2002 11:33:11 PM)
	 * 
	 * @return java.sql.Timestamp
	 */
	public java.sql.Timestamp getTimestamp()
	{
		return timestamp;
	}
	/**
	 * Generates a hash code for the receiver. This method is supported
	 * primarily for hash tables, such as those provided in java.util.
	 * 
	 * @return an integer hash code for the receiver
	 * @see java.util.Hashtable
	 */
	public int hashCode()
	{
		// Insert code to generate a hash code for the receiver here.
		// This implementation forwards the message to super. You may replace or
		// supplement this.
		// NOTE: if two objects are equal (equals(Object) returns true) they
		// must have the same hash code
		return super.hashCode();
	}
	/**
	 * Method that is used to test if logging is enabled for this exception
	 *
	 * Creation date: (3/28/2002 11:33:11 PM)
	 * 
	 * @return boolean
	 */
	public boolean isErrLogRequired()
	{
		return errLogRequired;
	}
	/**
	 * Method that is used to test if notification is enabled for this exception
	 * Creation date: (3/28/2002 11:33:11 PM)
	 * 
	 * @return boolean
	 */
	public boolean isErrNotify()
	{
		return errNotify;
	}
	/**
	 * Insert the method's description here. Creation date: (4/8/2002 1:02:05
	 * PM)
	 * 
	 * @param newAppVariables
	 *            java.util.Vector
	 */
	public void setAppVariables(java.util.Vector newAppVariables)
	{
		appVariables = newAppVariables;
	}
	/**
	 * Method that is used to set the classname where the exception occurred
	 *
	 * Creation date: (3/28/2002 11:33:11 PM)
	 * 
	 * @param newErrClass
	 *            java.lang.String
	 */
	public void setErrClass(java.lang.String newErrClass)
	{
		errClass = newErrClass;
	}
	/**
	 * Method that is used to set the error level of the exception.
	 *
	 * <pre>
	 *	0	FATAL
	 *	1	ERROR
	 *	2	WARNING
	 *	3	INFO
	 *	4	DEBUG
	 * </pre>
	 * 
	 * Creation date: (3/28/2002 11:33:11 PM)
	 * 
	 * @param newErrLevel
	 *            int
	 */
	public void setErrLevel(int newErrLevel)
	{
		errLevel = newErrLevel;
	}
	/**
	 * Method that is used to set if logging is required for the exception.
	 *
	 * Creation date: (3/28/2002 11:33:11 PM)
	 * 
	 * @param newErrLogRequired
	 *            boolean
	 */
	public void setErrLogRequired(boolean newErrLogRequired)
	{
		errLogRequired = newErrLogRequired;
	}
	/**
	 * Method that is used to set message for the exception.
	 *
	 * Creation date: (3/28/2002 11:33:11 PM)
	 * 
	 * @param newErrMessage
	 *            java.lang.String
	 */
	public void setErrMessage(java.lang.String newErrMessage)
	{
		errMessage = newErrMessage;
	}
	/**
	 * Method that is used to set method of the class where the exception
	 * occurred.
	 *
	 * Creation date: (3/28/2002 11:33:11 PM)
	 * 
	 * @param newErrMethod
	 *            java.lang.String
	 */
	public void setErrMethod(java.lang.String newErrMethod)
	{
		errMethod = newErrMethod;
	}
	/**
	 * Method that is used to set if notification is required for the exception.
	 *
	 * Creation date: (3/28/2002 11:33:11 PM)
	 * 
	 * @param newErrNotify
	 *            boolean
	 */
	public void setErrNotify(boolean newErrNotify)
	{
		errNotify = newErrNotify;
	}
	/**
	 * Method that is used to set object root cause for the exception.
	 *
	 * Creation date: (3/28/2002 11:33:11 PM)
	 * 
	 * @param newErrObject
	 *            java.lang.Object
	 */
	public void setErrObject(java.lang.Object newErrObject)
	{
		errObject = newErrObject;
	}
}
