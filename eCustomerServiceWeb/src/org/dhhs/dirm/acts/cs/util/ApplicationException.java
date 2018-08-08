

package org.dhhs.dirm.acts.cs.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Vector;

// import log4j packages
import org.apache.log4j.Logger;
import org.dhhs.dirm.acts.cs.EmailException;
import org.dhhs.dirm.acts.cs.EmailManager;
import org.dhhs.dirm.acts.cs.PropertyManager;

public class ApplicationException extends Exception
{

	private String				message;
	private int					errorLevel;

	private int					errSQLCode;
	private String				errSQLState;
	private ErrorDescriptor		errorDescriptor;

	private Throwable			cause				= null;

	/**
	 * Variables that indicate if logging & email notification must be enabled
	 */
	private static boolean		logEnabled;
	private static boolean		emailEnabled;
	private static boolean		instantMsgEnabled;

	/*
	 * Variables that set the administrator group email id, application name and
	 * primary web url if any.
	 */
	private static String		adminEmail			= "";
	private static int			emailMsgLevel;

	private static String		instantMsgNotify	= "";
	private static int			instantMsgLevel;

	private static String		applicationName		= "";
	private static String		applicationURL		= "";

	private int					error_code			= -1;

	private final static Logger	log					= Logger.getLogger(ApplicationException.class);

	static
	{

		try
		{
			adminEmail = PropertyManager.getEmailMsgNotify();
			emailMsgLevel = PropertyManager.getEmailMsgLevel();

			emailEnabled = PropertyManager.isEmailMsg();

			instantMsgNotify = PropertyManager.getInstantMsgNotify();
			instantMsgLevel = PropertyManager.getInstantMsgLevel();

			applicationName = PropertyManager.getAppName();
			applicationURL = PropertyManager.getPrimaryURL();

			instantMsgEnabled = PropertyManager.isInstantMsg();

			logEnabled = PropertyManager.isLogging();

		} catch (Exception e)
		{
		}
	}

	public ApplicationException(String s, ErrorDescriptor errorDescriptor)
	{

		// Create the superclass
		super(s);

		// Set the throwable object
		this.cause = null;

		// Set the errorDescriptor object
		this.errorDescriptor = errorDescriptor;

		analyzeException();

	}

	public ApplicationException(String s, Throwable cause)
	{
		// Create the superclass
		super(s);

		// Set the throwable object
		this.cause = cause;

		analyzeException();

	}

	public ApplicationException(String s, Throwable cause, ErrorDescriptor errorDescriptor)
	{

		// Create the superclass
		super(s);

		// Set the throwable object
		this.cause = cause;

		// Set the errorDescriptor object
		this.errorDescriptor = errorDescriptor;

		analyzeException();
	}

	private void analyzeException()
	{

		boolean notify = true;

		errorLevel = Constants.DEBUG;

		// Check to see if the cause object is not null
		if (cause != null)
		{
			/**
			 * Check to see if the object is instance of java.sql.SQLException
			 * and if it is, then evaluate if the SQLException object is
			 * actually a wrapper object that is wrapped around another object
			 * by the JDBC driver
			 */
			if (cause instanceof java.sql.SQLException)
			{
				AnalyzeSQLException ae = new AnalyzeSQLException((java.sql.SQLException) cause);
				if (ae.sqlException())
				{
					error_code = Constants.SQLEXCEPTION;
				} else if (ae.socketException())
				{
					error_code = Constants.SOCKETEXCEPTION;
				} else if (ae.driverException())
				{
					error_code = Constants.JDBCDRIVEREXCEPTION;
				} else
				{
					error_code = Constants.OTHEREXCEPTION;
				}
			} else if (cause instanceof javax.mail.MessagingException)
			{
				notify = false;
			} else
			{
				error_code = Constants.OTHEREXCEPTION;
				cause.printStackTrace();
			}
		} else if (errorDescriptor != null)
		{
			error_code = Constants.APPLICATIONEXCEPTION;
		}

		if (errorDescriptor != null)
		{
			errorLevel = errorDescriptor.getErrLevel();
		}

		/*
		 * Set the error message based on the error_code value
		 */
		switch (error_code)
		{
			case Constants.APPLICATIONEXCEPTION :
				message = errorDescriptor.getErrMessage();
				break;
			case Constants.SQLEXCEPTION :
				message = (cause != null ? cause.toString() : errorDescriptor.getErrMessage());
				if (cause != null)
				{
					errSQLCode = ((java.sql.SQLException) cause).getErrorCode();
					errSQLState = ((java.sql.SQLException) cause).getSQLState();
				}
				break;
			default :
				message = (cause != null ? cause.toString() : errorDescriptor.getErrMessage());
				break;
		}

		/*
		 * Add this exception object to the stack
		 */

		ExceptionStack.addToStack(message);

		/**
		 * If the exception qualifies for notification, send an email/instant
		 * message notification
		 */
		if (notify)
		{

			if (errorLevel <= emailMsgLevel)
			{
				ExceptionStackItem item = ExceptionStack.getItem(message);

				if (item != null)
				{
					if (!item.getItemState())
					{
						initiateEmailNotification();
					}
				}
				// initiateEmailNotification();
			}

			if (errorLevel <= instantMsgLevel)
			{
				ExceptionStackItem item = ExceptionStack.getItem(message);

				if (item != null)
				{
					if (!item.getItemState())
					{
						initiateInstantMsg();
					}
				}
				// initiateInstantMsg();
			}
		}

		logError();
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
	 * Code to perform when this object is garbage collected.
	 * 
	 * Any exception thrown by a finalize method causes the finalization to
	 * halt. But otherwise, it is ignored.
	 */
	protected void finalize() throws Throwable
	{
		// Insert code to finalize the receiver here.
		// This implementation simply forwards the message to super. You may
		// replace or supplement this.
		super.finalize();
	}

	private String getStackTrace1()
	{

		// Create a StringWriter Object
		StringWriter sw = new StringWriter();

		// Create a PrintWriter Object
		PrintWriter pw = new PrintWriter(sw);

		// use the cause and print the stack trace to the PrintWriter Object

		cause.printStackTrace(pw);

		// return StackTrace String
		return sw.toString();

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

	public void initiateEmailNotification()
	{

		boolean sendMail = false;
		String emailTitle = "";

		if (emailEnabled)
		{
			if (errorDescriptor != null)
			{
				if (errorDescriptor.isErrNotify())
				{
					sendMail = true;
				}
			} else
			{
				sendMail = true;
			}
		}

		/*
		 * Set the email subject line based on the message
		 */
		String strSubjectLine = message;

		String errStackTrace = getStackTrace1();

		String errDetails = applicationName + "@" + applicationURL;
		String errClass = errorDescriptor.getErrClass();
		String errMethod = errorDescriptor.getErrMethod();
		String errMessage = strSubjectLine;

		String emailHeader = "<html>                                                                                                          \n" + "<head>                                                                                                          \n" + "<title>" + emailTitle + "</title>                                                                                \n" + "<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'>                                        \n" + "</head>                                                                                                         \n" + "                                                                                                                \n" + "<body bgcolor='#FFFFFF' text='#000000'>                                                                         \n";

		String emailBody = "<table width='75%' border='1' cellpadding='0' cellspacing='0' align='center'>                                   \n" + "  <tr bgcolor='#CCCCCC'>                                                                                        \n" + "    <td colspan='2'>                                                                                            \n" + "      <div align='center'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'><b>\n " + "        eCustomer Service Application Error Description</b></font></div>                                                  \n" + "    </td>                                                                                                       \n" + "  </tr>                                                                                                         \n" + "  <tr>                                                                                                          \n" + "    <td width='35%'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'>&nbsp</font></td>                    \n" + "    <td width='65%'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'>&nbsp</font></td>                    \n" + "  </tr>                                                                                                         \n" + "  <tr>                                                                                                          \n" + "    <td width='35%' bgcolor='#CCCCCC'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'>Application   \n" + "      Details: </font></td>                                                                                     \n" + "    <td width='65%'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'> " + errDetails + "</font></td>  \n" + "  </tr>                                                                                                         \n" + "  <tr>                                                                                                          \n" + "    <td width='35%'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'>&nbsp</font></td>                    \n" + "    <td width='65%'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'>&nbsp</font></td>                    \n" + "  </tr>                                                                                                         \n" + "  <tr>                                                                                                          \n" + "    <td width='35%' bgcolor='#CCCCCC'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'>Error         \n" + "      Class Name:</font></td>                                                                                   \n" + "    <td width='65%'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'>" + errClass + "</font></td>    \n" + "  </tr>                                                                                                         \n" + "  <tr>                                                                                                          \n" + "    <td width='35%'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'>&nbsp</font></td>                    \n" + "    <td width='65%'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'>&nbsp</font></td>                    \n" + "  </tr>                                                                                                         \n" + "  <tr>                                                                                                          \n" + "    <td width='35%' bgcolor='#CCCCCC'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'>Error         \n" + "      Method Name:</font></td>                                                                                  \n" + "    <td width='65%'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'>" + errMethod + "</font></td>   \n" + "  </tr>                                                                                                         \n" + "  <tr>                                                                                                          \n" + "    <td width='35%'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'>&nbsp</font></td>                    \n" + "    <td width='65%'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'>&nbsp</font></td>                    \n" + "  </tr>                                                                                                         \n" + "  <tr>                                                                                                          \n" + "    <td width='35%' bgcolor='#CCCCCC'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'>Error         \n" + "      Message: </font></td>                                                                                     \n" + "    <td width='65%'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'>" + errMessage + "</font></td>  \n" + "  </tr>                                                                                                         \n" + "  <tr>                                                                                                          \n" + "    <td width='35%'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'>&nbsp</font></td>                    \n" + "    <td width='65%'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'>&nbsp</font></td>                    \n" + "  </tr>                                                                                                         \n" + "  <tr>                                                                                                          \n" + "    <td width='35%' bgcolor='#CCCCCC'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'>SQL           \n" + "      Code: </font></td>                                                                                        \n" + "    <td width='65%'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'>" + errSQLCode + "</font></td>  \n" + "  </tr>                                                                                                         \n" + "  <tr>                                                                                                          \n" + "    <td width='35%'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'>&nbsp</font></td>                    \n" + "    <td width='65%'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'>&nbsp</font></td>                    \n" + "  </tr>                                                                                                         \n" + "  <tr>                                                                                                          \n" + "    <td width='35%' bgcolor='#CCCCCC'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'>SQL           \n" + "      State: </font></td>                                                                                       \n" + "    <td width='65%'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'>" + errSQLState + "</font></td> \n" + "  </tr>                                                                                                         \n" + "  <tr>                                                                                                          \n" + "    <td width='35%'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'>&nbsp</font></td>                    \n" + "    <td width='65%'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'>&nbsp</font></td>                    \n" + "  </tr>                                                                                                         \n" + "  <tr>                                                                                                          \n" + "    <td width='35%' bgcolor='#CCCCCC'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'>Stack         \n" + "      Trace: </font></td>                                                                                       \n" + "    <td width='65%'>&nbsp;</td>                                                                                       \n" + "  </tr>                                                                                                         \n" + "  <tr>                                                                                                          \n" + "    <td colspan='2'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'> " + errStackTrace + "</font></td> \n" + "  </tr>                                                                                                         \n" + "</table>                                                                                                        \n" + "<br>                                                                                                            \n";

		String emailFooter = "</body>                                                                                                         \n" + "</html>                                                                                                         \n";

		String appVariableHeader = "<table width='75%' border='1' cellpadding='0' cellspacing='0' align='center'>                                   \n" + "  <tr bgcolor='#CCCCCC'>                                                                                        \n" + "    <td colspan='2'>                                                                                            \n" + "      <div align='center'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'><b>eChild                 \n" + "        Support Application Variables</b></font></div>                                                          \n" + "    </td>                                                                                                       \n" + "  </tr>                                                                                                         \n";

		String appVariableFooter = "</table>  	                                                                                                   \n";

		if (sendMail)
		{
			try
			{
				EmailManager em = new EmailManager();

				/**
				 * Must use Administrator Email from some properties file
				 */
				em.addTo(adminEmail);

				em.setSubject(strSubjectLine);

				/**
				 * Logic to actually convert stackTrace from the exception to a
				 * String and send it via email
				 */

				// Set the HTML Header
				String body = emailHeader;

				// Add the HTML body
				body += emailBody;

				Vector appVariables = errorDescriptor.getAppVariables();

				// Set the appVariables HTML Footer
				if (appVariables.size() > 0)
				{
					body += appVariableHeader;
				}

				// Add the HTML variables

				for (int i = 0; i < appVariables.size(); i++)
				{
					String appVariable = (String) appVariables.elementAt(i);
					String appVariableBody = "  <tr>                                                                                                          \n" + "    <td width='35%'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'>&nbsp</font></td>               \n" + "    <td width='65%'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'>&nbsp</font></td>               \n" + "  </tr>                                                                                                         \n" + "  <tr>                                                                                                          \n" + "    <td width='35%' bgcolor='#CCCCCC'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'>Application   \n" + "      Trace: </font></td>                                                                                       \n" + "    <td width='65%'>&nbsp;</td>                                                                                 \n" + "  </tr>                                                                                                         \n" + "  <tr>                                                                                                          \n" + "    <td colspan='2'><font face='Verdana, Arial, Helvetica, sans-serif' size='2'> " + appVariable + "</font></td>\n" + "  </tr>                                                                                                         \n";
					body += appVariableBody;
				}

				// Set the appVariable HTML Footer
				if (appVariables.size() > 0)
				{
					body += appVariableFooter;
				}

				// Set the HTML Footer
				body += emailFooter;

				em.setBody(body);
				em.sendMailHTML();

			} catch (Exception e)
			{
			}
		}
	}

	public void initiateInstantMsg()
	{

		boolean sendInstantMsg = false;

		if (instantMsgEnabled)
		{
			if (errorDescriptor != null)
			{
				if (errorDescriptor.isErrNotify())
				{
					sendInstantMsg = true;
				}
			} else
			{
				sendInstantMsg = true;
			}
		}

		String errDetails = applicationURL;
		String errClass = errorDescriptor.getErrClass();
		String errMethod = errorDescriptor.getErrMethod();

		StringBuffer instantMsg = new StringBuffer();
		instantMsg.append("App:" + errDetails + "\n");
		instantMsg.append("SQLCode:" + errSQLCode + "\n");
		instantMsg.append("SQLState:" + errSQLState + "\n");

		if (sendInstantMsg)
		{
			try
			{
				EmailManager em = new EmailManager();

				/**
				 * Must use Administrator Email from some properties file
				 */
				em.addTo(instantMsgNotify);

				em.setSubject(message);

				em.setBody(instantMsg.toString());
				em.sendMail();

			} catch (EmailException ae)
			{
			}
		}
	}

	private void logError()
	{

		boolean log = false;

		/**
		 * If logging is enabled at the application level, simply check to see
		 * if the class that caught the exception passed the error descriptor
		 * object and if so, get the class name, log level and log override
		 * indicator
		 */

		if (logEnabled)
		{
			if (errorDescriptor != null)
			{
				if (errorDescriptor.isErrLogRequired())
				{
					log = true;
				}
			} else
			{
				log = true;
			}
		} else
		{
			log = false;
		}
	}

	public void printStackTrace()
	{
		super.printStackTrace();

		if (cause != null)
		{
			cause.printStackTrace();
		}
	}

	public void printStackTrace(java.io.PrintStream ps)
	{
		super.printStackTrace();

		if (cause != null)
		{
			cause.printStackTrace();
		}

	}

	public void printStackTrace(java.io.PrintWriter pw)
	{
		super.printStackTrace();

		if (cause != null)
		{
			cause.printStackTrace();
		}

	}

	/**
	 * Returns a String that represents the value of this object.
	 * 
	 * @return a string representation of the receiver
	 */
	public String toString()
	{
		// Insert code to print the receiver here.
		// This implementation forwards the message to super. You may replace or
		// supplement this.
		return super.toString();
	}
}
