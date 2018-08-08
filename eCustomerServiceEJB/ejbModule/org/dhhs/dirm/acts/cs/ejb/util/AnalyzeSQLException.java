package org.dhhs.dirm.acts.cs.ejb.util;

import java.io.*;
import java.net.*;
import java.sql.*;
/**
 * An Exception Class that can handle exceptions that occur during
 * communication of JDBC Driver with DB2 on OS390
 * Creation date: (10/30/2001 5:57:59 PM)
 * @author: Ramakanth Kodumagulla
 */
public class AnalyzeSQLException {

	private SQLException exception;
	private int sqlcode;
	private String sqlstate;
	private String reason;

	private static final String SOCKET_EXCEPTION = "java.net.SocketException";
	private static final String HITDB2_EXCEPTION = "hit.db2";
	private static final String HITLICENSE_EXCEPTION = "hit.license";
	private static final String HIT_EXCEPTION = "hit";

	private static final int HITDB2 = 0;
	private static final int HITLICENSE = 1;
	private static final int HIT = 2;

	private int errorcode;
	
/**
 * AnalyzeSQLException constructor comment.
 */
public AnalyzeSQLException() {
	super();
}
/**
 * Constructor that accepts the SQLException Object
 * Creation date: (10/30/2001 5:58:38 PM)
 * @param e java.sql.SQLException
 */
public AnalyzeSQLException(SQLException e) {

	this.exception = e;
	sqlcode = e.getErrorCode();
	sqlstate = e.getSQLState();
	reason = e.toString();
}
/**
 * A method that decides if the exception is a driver exception
 * Creation date: (10/30/2001 6:13:31 PM)
 * @return boolean
 */
public boolean driverException() {
	
	boolean rtn = false;
	
	if (reason != null) {
		if (reason.length() > 0) {
			if (reason.indexOf(HITDB2_EXCEPTION) > 0) {
				errorcode = HITDB2;
				return true;
			} else if (reason.indexOf(HITLICENSE_EXCEPTION) > 0) {
				errorcode = HITLICENSE;
				return true;
			} else if (reason.indexOf(HIT_EXCEPTION) > 0) {
				errorcode = HIT;
				return true;
			}
		}
	}

	return rtn;
}
/**
 * A method that decides if the exception is a socket exception
 * Creation date: (10/30/2001 6:08:21 PM)
 * @return boolean
 */
public boolean socketException() {

	boolean rtn = false;
	
	if (reason != null) {
		if (reason.length() > 0) {
			if (reason.indexOf(SOCKET_EXCEPTION) > 0) {
				return true;
			}
		}
	}

	return rtn;
}
/**
 * A method that decides if an exception is a generic sql exception
 * Creation date: (10/30/2001 6:03:45 PM)
 * @return boolean
 */
public boolean sqlException() {

	boolean rtn = true;
	
	switch (sqlcode) {
		case 0:
			if (sqlstate == null) {
				rtn = false;
			}
			break;
		default:
			break;
	}

	return rtn;
}
}
