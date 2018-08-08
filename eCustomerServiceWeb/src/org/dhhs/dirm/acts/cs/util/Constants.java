

package org.dhhs.dirm.acts.cs.util;

/**
 * A Simple Interface used as a global variable holder to store application
 * specific messages.
 * <p>
 * Typically used whenever a need arises for using the error messages.
 * <p>
 * Note: Create a new series of variables whenever new error messages are
 * required for process that are not defined.
 * <p>
 * Creation date: (3/10/2001 4:04:39 PM)
 * 
 * @author: Ramakanth Kodumagulla
 */
public interface Constants
{

	public static final int		FATAL					= 0;
	public static final int		ERROR					= 1;
	public static final int		WARNING					= 2;
	public static final int		INFO					= 3;
	public static final int		DEBUG					= 4;

	public static final int		EXCEPTION				= 0;
	public static final int		IOEXCEPTION				= 1;
	public static final int		NAMINGEXCEPTION			= 2;
	public static final int		SQLEXCEPTION			= 3;
	public static final int		SOCKETEXCEPTION			= 4;
	public static final int		MESSAGINGEXCEPTION		= 5;
	public static final int		APPLICATIONEXCEPTION	= 6;
	public static final int		SERVLETEXCEPTION		= 7;
	public static final int		JDBCDRIVEREXCEPTION		= 8;
	public static final int		OTHEREXCEPTION			= 9;

	public static final String	DATABASE_EXCEPTION		= "A Severe Database Exception has occurred. Your login has been invalidated. Please try to Login after a few minutes.";

	public static final String	OTHER_EXCEPTION			= "Our WebSite is experiencing some technical difficulties. Please try to Login after a few minutes.";

	public static final String	APPLICATION_ERROR_PAGE	= "ApplicationError.jsp";

}
