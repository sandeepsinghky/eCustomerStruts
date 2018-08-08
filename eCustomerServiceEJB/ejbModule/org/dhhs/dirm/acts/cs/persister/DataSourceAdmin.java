package org.dhhs.dirm.acts.cs.persister;

import javax.naming.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import hit.jndi.jdbccontext.*;
import hit.db2.*;
import java.lang.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.sql.*;

/**
 * Hit JDBC's Implementation of Database Connection Pooling
 * This Class is configured to create a pool of datasource objects so that
 * the servlets can share datasource objects from the pool.
 * <p>
 * Creation date: (7/31/2001 9:49:44 AM)
 * @author: Ramakanth Kodumagulla
 */

public class DataSourceAdmin {

	private String ipAddress;
	private int portNumber;
	private String rdbName;
	private String commitSelect;
	private String collectionID;
	private String db2Region;
	private String userID;
	private String password;
	private String parent;
	private String source;
	private String trace;
	private int maxConnections;
	private int minConnections;
	private String jobName;
	private int connectionTimeout;
	private String autoCreatePkgs;
	private int traceLevel;
	private String remoteMonitorAddr;
	
	/**
	 * Constructor for the Pooled DataSource. This method obtains all the properties
	 * from the properties file placed in the classpath of the servlets. Make sure to
	 * modify the properties file if any changes are required for the parameters.
	 */
	public DataSourceAdmin() {

		ipAddress = PropertyManager.getIpAddress();
		portNumber = PropertyManager.getPortNumber();
		collectionID = PropertyManager.getCollectionID();
		rdbName = PropertyManager.getRdbName();
		commitSelect = PropertyManager.getCommitSelect();

		userID = PropertyManager.getUserID();
		password = PropertyManager.getPassword();

		// Get context and logical name information to use below in a
		// naming service lookup to create HitJDBC JNDI naming object.
		parent = PropertyManager.getParent();

		trace = PropertyManager.getTrace();
		
		traceLevel = PropertyManager.getTraceLevel();
		
		autoCreatePkgs = PropertyManager.getAutoCreatePkgs();
		
		remoteMonitorAddr = PropertyManager.getRemoteMonitorAddr();

		// Max number of connections in the pool
		maxConnections = PropertyManager.getMaxConnections();

		// Min number of connections in the pool
		minConnections = PropertyManager.getMinConnections();
		
		jobName = PropertyManager.getJobName();
		
		connectionTimeout = PropertyManager.getConnectionTimeout();

		// Get context and logical name information to use below in a
		// naming service lookup to get a DataSource object.
		source = PropertyManager.getSource();

		try {
			if (trace.equals("ON")) {
				System.out.print("Database Activity Trace is " + trace);
				// Generate JDBC trace for the Connection Pooling Administration
				DriverManager.setLogWriter(new PrintWriter(new FileOutputStream("HitConPool.log")));
			}

			Hashtable parms = new Hashtable();

			// Create the initial context using Hit's own jdbc context factory
			// Make sure that "java.naming.factory.initial" is set when running this application
			parms.put("java.naming.factory.initial", "hit.jndi.jdbccontext.JDBCNameContextFactory");
			Context ctx = new InitialContext(parms);

			// Using a connection pooling data source actually requires two data sources:
			// one that implements DataSource and the other that implements
			// Db2PooleConnectionDataSource which is connection (PooledConnection) factory.
			// The DataSource's implementation uses Db2PooledDataSource as a connection
			// factory while it manages the pool of connections.
			// An administrative application like this will create both type of classes
			// but a user application only use the DataSource implementation and does
			// not care and doesn't know anything about the Db2PooleConnectionDataSource
			// behind the scene.

			// Create and bind a Db2PooledDataSource and a Db2ConnectionPoolDataSource.
			// A Db2PooledDataSource is a DataSource that implements connection pooling management.
			// A Db2PooledDataSource does not create the actual physical connections,
			// rather it relies on Db2ConnectionPoolDataSource as the connection factory.

			// Create new instance of a pool connection factory Db2ConnectionPoolDataSource
			hit.db2.Db2ConnectionPoolDataSource cpds = new hit.db2.Db2ConnectionPoolDataSource();

			// Set connection properties for the data source (connection factory)
			cpds.setServerName(ipAddress); // Server name or address
			cpds.setPortNumber(portNumber); // Port Number
			cpds.setDatabaseName(rdbName); // Rdbname
			cpds.setUser(userID); // Username
			cpds.setPassword(password); // Password
			
			String connectionOptions = "";
			
			if (collectionID != null) {
				connectionOptions += "package_collection_id=" + collectionID + ";";
			}
			
			if (commitSelect != null) {
				connectionOptions += "commit_select=" + commitSelect + ";";
			}
			
			if (trace.equals("ON")) {
				connectionOptions += "trace_level=" + traceLevel + ";";
			}
			
			if (jobName != null) {
				connectionOptions += "job_name=" + jobName + ";";
			}

			if (connectionTimeout > 0) {
				connectionOptions += "connection_timeout=" + connectionTimeout + ";";
			}
			
			if (autoCreatePkgs != null) {
				connectionOptions += "auto_create_packages=" + autoCreatePkgs + ";";
			}
			
			if (remoteMonitorAddr != null) {
				connectionOptions += "remote_monitor_address=" + remoteMonitorAddr + ";";
			}
			
			connectionOptions += "auto_type_conversion=yes;remove_trailing_blanks=yes;remove_sql_white_spaces=yes";
			
//8/11/03	cpds.setConnectionOptions("package_collection_id=" + collectionID + ";commit_select=" + commitSelect);
			cpds.setConnectionOptions(connectionOptions);

			if (cpds.getServerName().equals("1.1.1.1")) {
				System.err.println("Please edit this application to specify your own database properties before running!!!");
				throw new Exception("No good database properties");
			}

			// Bind data source to name "jdbc/pool/sample"
			// This name will be used as data source for a Db2PooledDataSource later
			// This data source is not meant to be used by an end-user application
			try {
				// we call rebind() rather than bind() here
				// If we use bind() we would get an exception
				// if we run this application more than once
				// ctx.rebind("jdbc/pool/sample", cpds);
				ctx.rebind(parent, cpds);
			} catch (Exception be) {
				System.err.println(be);
			}

			System.out.println("jdbc/sample first rebind successful");

			// Create new instance of Db2PooledDataSource which implements connection pooling management

			hit.db2.Db2PooledDataSource pooledDS = new hit.db2.Db2PooledDataSource(parms);

			System.out.println("created hit.db2.Db2PooledDataSource Object" + pooledDS);

			// Set connection properties for the connection pooling data source
			pooledDS.setDataSourceName(parent);

			System.out.println("setDataSourceName successful");
			System.out.println("Max Number of Connections in the pool " + maxConnections);
			System.out.println("Min Number of Connections in the pool " + minConnections);

			// Data source name of PooledConnection object factory
			// rk 08/10/2004 - Use MaxPoolSize and MinPoolSize methods
			//pooledDS.setMaxConnections(maxConnections);
			pooledDS.setMaxPoolSize(maxConnections);
			// Maximum number of concurrent connection allowed
			
			//pooledDS.setOptimalConnections(minConnections);
			pooledDS.setMinPoolSize(minConnections);
			pooledDS.setInitialPoolSize(minConnections);
			// Option number of connection to remain opened
			
			// I have no clue as to what this method is
			pooledDS.setMaxIdleTime(20);

			System.out.println("jdbc/sample set datasource successful");
			System.out.println("DataSource Name to rebind is " + source);
			// Bind data source to name source so that it can be
			// looked up and used later by user applications.
			// See the sample application PooledDSConnection.java.
			try {
				ctx.rebind(source, pooledDS);
			} catch (Exception e) {
				System.err.println(e);
				e.printStackTrace();
			}

		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
