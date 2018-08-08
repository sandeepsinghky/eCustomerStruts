package org.dhhs.dirm.acts.cs.ejb.util;

/**
 * A Simple Interface used as a global variable holder to store
 * application specific messages.
 * <p>
 * Typically used whenever a need arises for using the error messages.
 * <p>
 * Note:
 * Create a new series of variables whenever new error messages are
 * required for process that are not defined.
 * <p>
 * Creation date: (3/10/2001 4:04:39 PM)
 * @author: Ramakanth Kodumagulla
 */
public interface Constants {

    public static final int FATAL = 0;
    public static final int ERROR = 1;
    public static final int WARNING = 2;
    public static final int INFO = 3;
    public static final int DEBUG = 4;

    public static final int EXCEPTION = 0;
    public static final int IOEXCEPTION = 1;
    public static final int NAMINGEXCEPTION = 2;
    public static final int SQLEXCEPTION = 3;
    public static final int SOCKETEXCEPTION = 4;
    public static final int MESSAGINGEXCEPTION = 5;
    public static final int APPLICATIONEXCEPTION = 6;
    public static final int SERVLETEXCEPTION = 7;
    public static final int JDBCDRIVEREXCEPTION = 8;
    public static final int OTHEREXCEPTION = 9;

    public static final String DATABASE_EXCEPTION = "A Severe Database Exception has occurred. Your login has been invalidated. Please try to Login after a few minutes.";
								
	public static final String OTHER_EXCEPTION = "Our WebSite is experiencing some technical difficulties. Please try to Login after a few minutes.";

	public static final String APPLICATION_ERROR_PAGE = "ApplicationError.jsp";
	
	/**
	 * Database Table Definitions
	 */
	public static final String TABLE_CSESRV_USER = "FKKT_CSESRV_USER";
	
	public static final String TABLE_CSESRV_PROFILE = "FKKT_CSESRV_PROF";
	
	public static final String TABLE_CSESRV_USERPROFILE = "FKKT_CSESRV_USRPRF";
	
	public static final String TABLE_CSESRV_FORMS = "FKKT_CSESRV_FORMS";
	
	public static final String TABLE_CSESRV_FRMTRK = "FKKT_CSESRV_FRMTRK";
	
	public static final String TABLE_CSESRV_CNTLNBR = "FKKT_CSESRV_CTLNBR ";
	
	public static final String TABLE_CSESRV_NOTES = "FKKT_CSESRV_NOTES ";
	
	public static final String TABLE_CSESRV_REFERRAL = "FKKT_CSESRV_RFRL";
	
	public static final String TABLE_CSESRV_PROCESS = "FKKT_CSESRV_PRCS";
	
	public static final String TABLE_CSESRV_REFERRAL_PROCESS = "FKKT_CSESRV_RFRPRS";
	
	public static final String TABLE_CSESRV_OFFICE = "FKKT_CSESRV_OFFICE";
	
	public static final String TABLE_CSESRV_STAFF = "FKKT_CSESRV_STAFF";

//	CT# 527057 - USE A NEW WORKER TABLE THAT GETS REFRESHED EVERY NIGHT	
//	public static final String TABLE_CSESRV_WORKER = "FKKT_WORKER";
	public static final String TABLE_CSESRV_WORKER = "FKKT_CSESRV_WORKER";
	

}
