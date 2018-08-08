

package org.dhhs.dirm.acts.cs;

/**
 * Constants.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Aug 18, 2003 11:04:41 AM
 * 
 * @author Rkodumagulla
 *
 */
public final class Constants
{

	/**
	 * The session scope attribute under which the Username for the currently
	 * logged in user is stored.
	 */
	public static final String		USER_KEY						= "user";

	/**
	 * The session scope attribute under which the User Information for the
	 * currently logged in user is stored.
	 */
	public static final String		USERBEAN_KEY					= "userbean";

	/**
	 * The session scope attribute under which the List of Acts Workers as a
	 * result of a search criteria
	 */
	public static final String		ACTSWORKERSLIST_KEY				= "actsworkerlist";

	public static final String		PROFILELIST						= "profilelist";
	public static final String		FORMSLIST						= "formslist";
	public static final String		STEPSLIST						= "stepslist";
	public static final String		TASKLIST						= "tasklist";
	public static final String		OFFICELIST						= "officelist";

	/**
	 * The token that represents a nominal outcome in an ActionForward.
	 */
	public static final String		SUCCESS							= "success";

	/**
	 * The token that represents the logon activity in an ActionForward.
	 */
	public static final String		LOGON							= "logon";

	/**
	 * The token that represents the welcome activity in an ActionForward.
	 */
	public static final String		WELCOME							= "welcome";

	/**
	 * The token that indicates if the password is to be changed
	 */
	public static final String		PASSWORD_RESET					= "password_reset";

	public static final String		STACK							= "stack";

	public static final String		SOURCE							= "source";

	public static final String		TARGET							= "target";

	/**
	 * The value to indicate debug logging.
	 */
	public static final int			DEBUG							= 1;

	/**
	 * The value to indicate normal logging.
	 */
	public static final int			NORMAL							= 0;

	public static final String[]	PRCS_STATUS						= {"INIT", "OPEN", "CSWK", "REQR", "APRV", "APCL", "APRT", "CORR", "TRAN", "DELT", "CLSD"};

	/**
	 * Methods used in the DispatchAction subclasses must be defined as static
	 * final variables and must be excluded or included in the ValidatorForm
	 * subclasses for the validation to work correctly.
	 */
	public static final String		METHOD							= "reqCode";

	public static final String		CREATE							= "create";
	public static final String		EDIT							= "edit";
	public static final String		VIEW							= "view";
	public static final String		STORE							= "store";
	public static final String		SAVE							= "save";
	public static final String		APPROVEALL						= "approveAll";
	public static final String		SEARCH							= "search";
	public static final String		GENERATE						= "generate";
	public static final String		AUTHENTICATE					= "authenticate";
	public static final String		MANAGE							= "manage";
	public static final String		LOAD							= "load";
	public static final String		DELETE							= "delete";
	public static final String		DELETEALL						= "deleteAll";
	public static final String		INACTIVATE						= "inactivate";
	public static final String		BACK							= "back";
	public static final String		LOADALL							= "loadAll";
	public static final String		RECALCULATE						= "recalculate";
	public static final String		TRANSFER						= "transfer";
	public static final String		SELECT							= "select";
	public static final String		RESETPASSWORD					= "resetPassword";
	public static final String		PRINT							= "print";
	public static final String		VALIDATIONERROR					= "validationError";

	/**
	 * Database Table Definitions
	 */
	public static final String		TABLE_CSESRV_USER				= "FKKT_CSESRV_USER";

	public static final String		TABLE_CSESRV_PROFILE			= "FKKT_CSESRV_PROF";

	public static final String		TABLE_CSESRV_USERPROFILE		= "FKKT_CSESRV_USRPRF";

	public static final String		TABLE_CSESRV_FORMS				= "FKKT_CSESRV_FORMS";

	public static final String		TABLE_CSESRV_FRMTRK				= "FKKT_CSESRV_FRMTRK";

	public static final String		TABLE_CSESRV_CNTLNBR			= "FKKT_CSESRV_CTLNBR ";

	public static final String		TABLE_CSESRV_NOTES				= "FKKT_CSESRV_NOTES ";

	public static final String		TABLE_CSESRV_REFERRAL			= "FKKT_CSESRV_RFRL";

	public static final String		TABLE_CSESRV_PROCESS			= "FKKT_CSESRV_PRCS";

	public static final String		TABLE_CSESRV_REFERRAL_PROCESS	= "FKKT_CSESRV_RFRPRS";

	public static final String		TABLE_CSESRV_OFFICE				= "FKKT_CSESRV_OFFICE";

	public static final String		TABLE_CSESRV_STAFF				= "FKKT_CSESRV_STAFF";

	// CT# 527057 - USE A NEW WORKER TABLE THAT IS REFRESHED EVERY NIGHT
	// public static final String TABLE_CSESRV_WORKER = "FKKT_WORKER";
	public static final String		TABLE_CSESRV_WORKER				= "FKKT_CSESRV_WORKER";
}
