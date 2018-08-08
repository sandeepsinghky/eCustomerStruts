package org.dhhs.dirm.acts.cs.persister;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.io.*;
import java.sql.*;
import javax.sql.*;

import org.apache.log4j.Logger;

import java.util.*;

/**
 * A Generic Database Connection Manager Class that helps all the
 * servlets to obtain a Datasource object for connection to the database.
 * <p>
 * This Class requires a properties file that maintains the database
 * connection parameters. The location of the properties must be referenced
 * in the static variable CONFIG_BUNDLE_NAME. 
 * <p>
 * Typically one database properties file exists for each application server
 * and preferably located in the directory of the package.
 * <p>
 * Note:
 * This Class assumes that the datasources have been defined prior to use in
 * the Application Server and adheres strictly to Hit's implementation of
 * Database Connection Pooling.
 * <p>
 * If a need arises to use this class for any other vendor specific Connection
 * Pooling, necessary adjustments must have to be made for the JNDI properties.
 * <p>
 * Creation date: (2/3/2001 4:02:19 PM)
 * @author: Ramakanth Kodumagulla
 */
public class DBConnectManager {

	private static final Logger log = Logger.getLogger(DBConnectManager.class);

	private DataSource dataSource = null;
	private String user = null;
	private String password = null;
	private String owner = null;
	private String source = null;
	private String region = null;

	/**
	 * This method is invoked from all the Servlets that plan to utilize the
	 * datasource object for db2 connection. 
	 * Creation date: (3/1/2001 2:11:16 PM)
	 */
	public DBConnectManager() {

		java.util.Date now = new java.util.Date();
		Context ctx = null;

		user = PropertyManager.getUserID();
		password = PropertyManager.getPassword();
		source = PropertyManager.getSource();
		region = PropertyManager.getRegion();
		System.out.println("In DB ConnectManger user= " + user+ " password " + password  +" source " + source + " region " + region);

		/**
		* Perform a JNDI Lookup to obtain a datasource object
		*/

		try {
			// Generate the JDBC TraceFile
			String driveLetter = System.getProperty("driveLetter");
			if (driveLetter == null) {
				driveLetter = "C:";
			}
			String path = System.getProperty("basePath");
			if (path == null) {
				path = "WorkingDirectory/ApplicationServers/ecsts";
			}
			
			String fullPath = driveLetter + System.getProperty("file.separator") + path;
			
			DriverManager.setLogWriter(new PrintWriter(new FileOutputStream(fullPath + System.getProperty("file.separator") + "EJBConnections.log")));

			Hashtable parms = new Hashtable();

			// Create the initial context using Hit's own jdbc context factory
			// Make sure that "java.naming.factory.initial" is set when running this application
			parms.put("java.naming.factory.initial", "hit.jndi.jdbccontext.JDBCNameContextFactory");

			ctx = new InitialContext(parms);
			System.out.println("In DB ConnectManger Context " + ctx);  
			// Look up the data source defined in the Properties File
			// The datasource must be bound by DataSourceAdmin.java
			log.info("Looking up data source name " + source);
			dataSource = (DataSource) ctx.lookup(source);
			log.info("Found data source name " + dataSource);
		} catch (Exception ex) {
			log.info("DBConnectManager Lookup Exception: " + ex.getMessage());
		}
	}
	/**
	 * Standard Get Method to get the datasource
	 * Creation date: (3/1/2001 2:14:40 PM)
	 * @return javax.sql.DataSource
	 */
	public DataSource getDataSource() {
		return dataSource;
	}
	/**
	 * Standard Get Method to get the database password
	 * Creation date: (3/1/2001 2:24:17 PM)
	 * @return java.lang.String
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Standard Get Method to get the database region
	 * Creation date: (6/3/2001 4:02:50 PM)
	 * @return java.lang.String
	 */
	public java.lang.String getRegion() {
		return region;
	}
	/**
	 * Standard Get Method to get the datasource
	 * Creation date: (4/10/2001 9:10:52 PM)
	 * @return java.lang.String
	 */
	public java.lang.String getSource() {
		return source;
	}
	/**
	 * Standard Get Method to get the database password
	 * Creation date: (3/1/2001 2:23:32 PM)
	 * @return java.lang.String
	 */
	public String getUserID() {
		return user;
	}
	/**
	 * Standard Set Method to set the database region
	 * Creation date: (6/3/2001 4:02:50 PM)
	 * @param newRegion java.lang.String
	 */
	public void setRegion(java.lang.String newRegion) {
		region = newRegion;
	}
	/**
	 * Standard Set Method to set the datasource
	 * Creation date: (4/10/2001 9:10:52 PM)
	 * @param newSource java.lang.String
	 */
	public void setSource(java.lang.String newSource) {
		source = newSource;
	}
}
