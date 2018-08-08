

package org.dhhs.dirm.acts.cs;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * A Generic Property Manager Class that helps all the servlets to obtain email
 * and ip configurations
 * <p>
 * This Class requires a properties file that maintains the database connection
 * parameters. The location of the properties must be referenced in the static
 * variable CONFIG_BUNDLE_NAME.
 * <p>
 * Note:
 * 
 * Rama 03/05/03. Modified the Logic in the PropertyManager class. Always call
 * loadXXX method from the Caller passing the String full absolute file name.
 * <p>
 * Creation date: (11/30/2001 4:02:19 PM)
 * 
 * @author: Ramakanth Kodumagulla
 */
public class PropertyManager
{

	static String				mailServerHost			= null;
	static String				mailServerUser			= null;
	static String				mailServerPassword		= null;
	static String				mailSender				= null;

	static String				primaryURL				= null;
	static String				secondaryURL			= null;
	static String				otherURL				= null;
	static String				primaryEmail			= null;
	static String				secondaryEmail			= null;
	static String				otherEmail				= null;

	static boolean				logging;
	static boolean				emailMsg;
	static String				emailMsgNotify;
	static int					emailMsgLevel;

	static boolean				instantMsg;
	static String				instantMsgNotify;
	static int					instantMsgLevel;
	static String				appName;

	static int					dbTimeToWait			= 0;								// No
	                                                                                        // Wait
	static int					dbClassificationToWait	= 1;								// ServletMaintenance.MINUTES;

	// Properties pertaining to Background Thread Servlets
	static int					msTimeToWait			= 0;
	static int					msClassificationToWait	= 1;								// ServletMaintenance.MINUTES;

	// Database Properties
	static String				ipAddress;
	static int					portNumber;
	static String				rdbName;
	static String				commitSelect;
	static String				collectionID;
	static String				region;
	static String				webRegion;
	static String				backupRegion;
	static String				queryRegion;
	static String				userID;
	static String				password;
	static String				parent;
	static String				source;
	static String				trace;
	static int					maxConnections;
	static int					minConnections;

	static String				jobName;
	static int					connectionTimeout;

	static int					retryCount;
	static int					retryInterval;
	static String				autoCreatePkgs;
	static int					traceLevel;
	static String				remoteMonitorAddr;

	// Name of property file used to complete the URL, properties
	// and table owner information at runtime, plus other information.

	private static final String	APP_CONFIG_BUNDLE_NAME	= "ApplicationConfig.properties";
	private static final String	DB_CONFIG_BUNDLE_NAME	= "ConnPoolStrings.properties";

	/** When the class is first loaded it instanciate itself */
	static
	{
		PropertyManager p = new PropertyManager();
	}

	public PropertyManager()
	{
		System.out.println("Initializing PropertyManager Class");
	}

	/**
	 * The Caller must handle the Exception thrown in case of an error and take
	 * necessary action
	 */

	public static void loadAppProperties(String fileName) throws Exception
	{

		java.util.Date now = new java.util.Date();

		/**
		 * Get information at runtime from an external property file identified
		 * by fileName parameter.
		 * 
		 */

		File f = new File(fileName, APP_CONFIG_BUNDLE_NAME);
		System.out.println("Loading Application Properties from " + f.getName());

		FileInputStream fis = new FileInputStream(f);

		Properties properties = new Properties();
		properties.load(fis);

		mailServerHost = properties.getProperty("mailServer.host");
		mailServerUser = properties.getProperty("mailServer.user");
		mailServerPassword = properties.getProperty("mailServer.password");
		mailSender = properties.getProperty("mailServer.from");

		primaryURL = properties.getProperty("application.primaryURL");
		secondaryURL = properties.getProperty("application.secondaryURL");
		otherURL = properties.getProperty("application.otherURL");
		primaryEmail = properties.getProperty("application.primaryEmail");
		secondaryEmail = properties.getProperty("application.secondaryEmail");
		otherEmail = properties.getProperty("application.otherEmail");
		dbTimeToWait = Integer.parseInt(properties.getProperty("application.dbTimeToWait"));
		dbClassificationToWait = Integer.parseInt(properties.getProperty("application.dbClassificationToWait"));

		msTimeToWait = Integer.parseInt(properties.getProperty("application.msTimeToWait"));
		msClassificationToWait = Integer.parseInt(properties.getProperty("application.msClassificationToWait"));

		logging = Boolean.valueOf(properties.getProperty("application.logging")).booleanValue();

		emailMsg = Boolean.valueOf(properties.getProperty("application.emailMsg")).booleanValue();
		emailMsgNotify = properties.getProperty("application.emailMsgNotify");
		emailMsgLevel = Integer.parseInt(properties.getProperty("application.emailMsgLevel"));

		instantMsg = Boolean.valueOf(properties.getProperty("application.instantMsg")).booleanValue();
		instantMsgNotify = properties.getProperty("application.instantMsgNotify");
		instantMsgLevel = Integer.parseInt(properties.getProperty("application.instantMsgLevel"));
		appName = properties.getProperty("application.appName");

		System.out.println("PropertyManager Application Parameters - Start\n");
		System.out.println("Mail Server Host: " + mailServerHost);
		System.out.println("Mail Server User: " + mailServerUser);
		System.out.println("Mail Server Password: **********");
		System.out.println("Mail Server Sender: " + mailSender);

		System.out.println("Application Name: " + appName);
		System.out.println("Primary URL: " + primaryURL);
		System.out.println("Secondary URL: " + secondaryURL);
		System.out.println("Other URL: " + otherURL);
		System.out.println("Primary Email: " + primaryEmail);
		System.out.println("Secondary Email: " + secondaryEmail);
		System.out.println("Other Email: " + otherEmail);

		System.out.println("TechSupport Email Enabled: " + emailMsg);
		System.out.println("TechSupport Email: " + emailMsgNotify);

		System.out.println("TechSupport Page Enabled: " + instantMsg);
		System.out.println("TechSupport Page: " + instantMsgNotify);

		System.out.println("Database Time To Wait: " + dbTimeToWait);
		System.out.println("Database Classification To Wait: " + dbClassificationToWait);
		System.out.println("Maintanied Servlet Time To Wait: " + msTimeToWait);
		System.out.println("Maintained Servlet Classification To Wait: " + msClassificationToWait);
		System.out.println("PropertyManager Application Parameters - End\n");

		System.out.println("Loading Application Properties Completed.");

	}

	/**
	 * The Caller must handle the Exception thrown in case of an error and take
	 * necessary action
	 */

	public static void loadDBProperties(String fileName) throws Exception
	{

		/**
		 * Get information at runtime (from an external property file identified
		 * by fileName) about the server,user, password, and owner information.
		 * This information could be provided in other ways - perhaps in a
		 * somewhat more secure compiled resource bundle, or hardcoded within
		 * this application.
		 */
		File f = new File(fileName, DB_CONFIG_BUNDLE_NAME);

		System.out.println("Loading Database Properties from " + f.getName());

		FileInputStream fis = new FileInputStream(f);

		Properties properties = new Properties();
		properties.load(fis);

		ipAddress = properties.getProperty("poolServlet.ip");
		portNumber = Integer.parseInt(properties.getProperty("poolServlet.port"));
		collectionID = properties.getProperty("poolServlet.collid");
		rdbName = properties.getProperty("poolServlet.rdbname");
		commitSelect = properties.getProperty("poolServlet.commitSelect");

		userID = properties.getProperty("poolServlet.user");
		password = properties.getProperty("poolServlet.password");

		region = properties.getProperty("poolServlet.region");
		webRegion = properties.getProperty("poolServlet.webregion");
		backupRegion = properties.getProperty("poolServlet.bkregion");
		queryRegion = properties.getProperty("poolServlet.queryregion");

		// Get context and logical name information to use below in a
		// naming service lookup to create HitJDBC JNDI naming object.
		parent = properties.getProperty("poolServlet.parent");

		trace = properties.getProperty("poolServlet.trace");

		autoCreatePkgs = properties.getProperty("poolServlet.autoCreatePkgs");
		traceLevel = Integer.parseInt(properties.getProperty("poolServlet.traceLevel"));
		remoteMonitorAddr = properties.getProperty("poolServlet.remoteMonitorAddr");

		// Max number of connections in the pool
		maxConnections = Integer.parseInt(properties.getProperty("poolServlet.maxConnections"));

		// Min number of connections in the pool
		minConnections = Integer.parseInt(properties.getProperty("poolServlet.minConnections"));

		jobName = properties.getProperty("poolServlet.jobName");

		// Connection Timeout
		connectionTimeout = Integer.parseInt(properties.getProperty("poolServlet.connectionTimeout"));

		parent = "jdbc/" + parent;

		// Get context and logical name information to use below in a
		// naming service lookup to get a DataSource object.
		source = properties.getProperty("poolServlet.source");

		retryCount = Integer.parseInt(properties.getProperty("poolServlet.retryCount"));
		retryInterval = Integer.parseInt(properties.getProperty("poolServlet.retryCount"));

		System.out.println("PropertyManager Database Parameters - Start\n");
		System.out.println("IP Address: " + ipAddress);
		System.out.println("Port Number: " + portNumber);
		System.out.println("Collection ID: " + collectionID);
		System.out.println("RDBName: " + rdbName);
		System.out.println("CommitSelect: " + commitSelect);
		System.out.println("User ID: " + userID);
		System.out.println("Password: **********");
		System.out.println("Region: " + region);
		System.out.println("Backup Region: " + backupRegion);
		System.out.println("Web Region: " + webRegion);
		System.out.println("Query Region: " + queryRegion);
		System.out.println("Parent: " + parent);
		System.out.println("Trace: " + trace);
		System.out.println("Trace Level: " + traceLevel);
		System.out.println("Auto Create Packages: " + autoCreatePkgs);
		System.out.println("Remote Monitor Address: " + remoteMonitorAddr);
		System.out.println("Max Connections: " + maxConnections);
		System.out.println("Min Connections: " + minConnections);
		System.out.println("Source: " + source);
		System.out.println("Retry Count: " + retryCount);
		System.out.println("Retry Interval: " + retryInterval);
		System.out.println("PropertyManager Database Parameters - End\n");
		System.out.println("Loading Database Properties Completed.");

	}

	/**
	 * Insert the method's description here. Creation date: (4/16/2002 8:55:35
	 * AM)
	 * 
	 * @return int
	 */
	public static int getDbClassificationToWait()
	{
		return dbClassificationToWait;
	}
	/**
	 * Insert the method's description here. Creation date: (4/16/2002 8:55:35
	 * AM)
	 * 
	 * @return int
	 */
	public static int getDbTimeToWait()
	{
		return dbTimeToWait;
	}
	/**
	 * Insert the method's description here. Creation date: (4/16/2002 3:11:52
	 * PM)
	 * 
	 * @return int
	 */
	public static int getMsClassificationToWait()
	{
		return msClassificationToWait;
	}
	/**
	 * Insert the method's description here. Creation date: (4/16/2002 3:11:52
	 * PM)
	 * 
	 * @return int
	 */
	public static int getMsTimeToWait()
	{
		return msTimeToWait;
	}
	/**
	 * Insert the method's description here. Creation date: (11/30/2001 3:54:49
	 * PM)
	 * 
	 * @return java.lang.String
	 */
	public static java.lang.String getOtherEmail()
	{
		return otherEmail;
	}
	/**
	 * Insert the method's description here. Creation date: (11/30/2001 3:54:49
	 * PM)
	 * 
	 * @return java.lang.String
	 */
	public static java.lang.String getOtherURL()
	{
		return otherURL;
	}
	/**
	 * Insert the method's description here. Creation date: (11/30/2001 3:54:49
	 * PM)
	 * 
	 * @return java.lang.String
	 */
	public static java.lang.String getPrimaryEmail()
	{
		return primaryEmail;
	}
	/**
	 * Insert the method's description here. Creation date: (11/30/2001 3:54:49
	 * PM)
	 * 
	 * @return java.lang.String
	 */
	public static java.lang.String getPrimaryURL()
	{
		return primaryURL;
	}
	/**
	 * Insert the method's description here. Creation date: (11/30/2001 3:54:49
	 * PM)
	 * 
	 * @return java.lang.String
	 */
	public static java.lang.String getSecondaryEmail()
	{
		return secondaryEmail;
	}
	/**
	 * Insert the method's description here. Creation date: (11/30/2001 3:54:49
	 * PM)
	 * 
	 * @return java.lang.String
	 */
	public static java.lang.String getSecondaryURL()
	{
		return secondaryURL;
	}
	/**
	 * Insert the method's description here. Creation date: (4/16/2002 8:55:35
	 * AM)
	 * 
	 * @param newDbClassificationToWait
	 *            int
	 */
	public static void setDbClassificationToWait(int newDbClassificationToWait)
	{
		dbClassificationToWait = newDbClassificationToWait;
	}
	/**
	 * Insert the method's description here. Creation date: (4/16/2002 8:55:35
	 * AM)
	 * 
	 * @param newDbTimeToWait
	 *            int
	 */
	public static void setDbTimeToWait(int newDbTimeToWait)
	{
		dbTimeToWait = newDbTimeToWait;
	}
	/**
	 * Insert the method's description here. Creation date: (4/16/2002 3:11:52
	 * PM)
	 * 
	 * @param newMsClassificationToWait
	 *            int
	 */
	public static void setMsClassificationToWait(int newMsClassificationToWait)
	{
		msClassificationToWait = newMsClassificationToWait;
	}
	/**
	 * Insert the method's description here. Creation date: (4/16/2002 3:11:52
	 * PM)
	 * 
	 * @param newMsTimeToWait
	 *            int
	 */
	public static void setMsTimeToWait(int newMsTimeToWait)
	{
		msTimeToWait = newMsTimeToWait;
	}
	/**
	 * Insert the method's description here. Creation date: (11/30/2001 3:54:49
	 * PM)
	 * 
	 * @param newOtherEmail
	 *            java.lang.String
	 */
	public static void setOtherEmail(java.lang.String newOtherEmail)
	{
		otherEmail = newOtherEmail;
	}
	/**
	 * Insert the method's description here. Creation date: (11/30/2001 3:54:49
	 * PM)
	 * 
	 * @param newOtherURL
	 *            java.lang.String
	 */
	public static void setOtherURL(java.lang.String newOtherURL)
	{
		otherURL = newOtherURL;
	}
	/**
	 * Insert the method's description here. Creation date: (11/30/2001 3:54:49
	 * PM)
	 * 
	 * @param newPrimaryEmail
	 *            java.lang.String
	 */
	public static void setPrimaryEmail(java.lang.String newPrimaryEmail)
	{
		primaryEmail = newPrimaryEmail;
	}
	/**
	 * Insert the method's description here. Creation date: (11/30/2001 3:54:49
	 * PM)
	 * 
	 * @param newPrimaryURL
	 *            java.lang.String
	 */
	public static void setPrimaryURL(java.lang.String newPrimaryURL)
	{
		primaryURL = newPrimaryURL;
	}
	/**
	 * Insert the method's description here. Creation date: (11/30/2001 3:54:49
	 * PM)
	 * 
	 * @param newSecondaryEmail
	 *            java.lang.String
	 */
	public static void setSecondaryEmail(java.lang.String newSecondaryEmail)
	{
		secondaryEmail = newSecondaryEmail;
	}
	/**
	 * Insert the method's description here. Creation date: (11/30/2001 3:54:49
	 * PM)
	 * 
	 * @param newSecondaryURL
	 *            java.lang.String
	 */
	public static void setSecondaryURL(java.lang.String newSecondaryURL)
	{
		secondaryURL = newSecondaryURL;
	}

	public static boolean isLogging()
	{
		return logging;
	}

	public static String getEmailMsgNotify()
	{
		return emailMsgNotify;
	}

	public static boolean isInstantMsg()
	{
		return instantMsg;
	}

	public static String getInstantMsgNotify()
	{
		return instantMsgNotify;
	}

	/**
	 * Returns the appName.
	 * 
	 * @return String
	 */
	public static String getAppName()
	{
		return appName;
	}

	/**
	 * Returns the collectionID.
	 * 
	 * @return String
	 */
	public static String getCollectionID()
	{
		return collectionID;
	}

	/**
	 * Returns the commitSelect.
	 * 
	 * @return String
	 */
	public static String getCommitSelect()
	{
		return commitSelect;
	}

	/**
	 * Returns the emailMsg.
	 * 
	 * @return boolean
	 */
	public static boolean isEmailMsg()
	{
		return emailMsg;
	}

	/**
	 * Returns the emailMsgLevel.
	 * 
	 * @return int
	 */
	public static int getEmailMsgLevel()
	{
		return emailMsgLevel;
	}

	/**
	 * Returns the instantMsgLevel.
	 * 
	 * @return int
	 */
	public static int getInstantMsgLevel()
	{
		return instantMsgLevel;
	}

	/**
	 * Returns the ipAddress.
	 * 
	 * @return String
	 */
	public static String getIpAddress()
	{
		return ipAddress;
	}

	/**
	 * Returns the mailSender.
	 * 
	 * @return String
	 */
	public static String getMailSender()
	{
		return mailSender;
	}

	/**
	 * Returns the mailServerHost.
	 * 
	 * @return String
	 */
	public static String getMailServerHost()
	{
		return mailServerHost;
	}

	/**
	 * Returns the mailServerPassword.
	 * 
	 * @return String
	 */
	public static String getMailServerPassword()
	{
		return mailServerPassword;
	}

	/**
	 * Returns the mailServerUser.
	 * 
	 * @return String
	 */
	public static String getMailServerUser()
	{
		return mailServerUser;
	}

	/**
	 * Returns the maxConnections.
	 * 
	 * @return int
	 */
	public static int getMaxConnections()
	{
		return maxConnections;
	}

	/**
	 * Returns the minConnections.
	 * 
	 * @return int
	 */
	public static int getMinConnections()
	{
		return minConnections;
	}

	/**
	 * Returns the parent.
	 * 
	 * @return String
	 */
	public static String getParent()
	{
		return parent;
	}

	/**
	 * Returns the password.
	 * 
	 * @return String
	 */
	public static String getPassword()
	{
		return password;
	}

	/**
	 * Returns the portNumber.
	 * 
	 * @return int
	 */
	public static int getPortNumber()
	{
		return portNumber;
	}

	/**
	 * Returns the rdbName.
	 * 
	 * @return String
	 */
	public static String getRdbName()
	{
		return rdbName;
	}

	/**
	 * Returns the source.
	 * 
	 * @return String
	 */
	public static String getSource()
	{
		return source;
	}

	/**
	 * Returns the trace.
	 * 
	 * @return String
	 */
	public static String getTrace()
	{
		return trace;
	}

	/**
	 * Returns the userID.
	 * 
	 * @return String
	 */
	public static String getUserID()
	{
		return userID;
	}

	/**
	 * Sets the appName.
	 * 
	 * @param appName
	 *            The appName to set
	 */
	public static void setAppName(String appName)
	{
		PropertyManager.appName = appName;
	}

	/**
	 * Sets the collectionID.
	 * 
	 * @param collectionID
	 *            The collectionID to set
	 */
	public static void setCollectionID(String collectionID)
	{
		PropertyManager.collectionID = collectionID;
	}

	/**
	 * Sets the commitSelect.
	 * 
	 * @param commitSelect
	 *            The commitSelect to set
	 */
	public static void setCommitSelect(String commitSelect)
	{
		PropertyManager.commitSelect = commitSelect;
	}

	/**
	 * Sets the emailMsg.
	 * 
	 * @param emailMsg
	 *            The emailMsg to set
	 */
	public static void setEmailMsg(boolean emailMsg)
	{
		PropertyManager.emailMsg = emailMsg;
	}

	/**
	 * Sets the emailMsgLevel.
	 * 
	 * @param emailMsgLevel
	 *            The emailMsgLevel to set
	 */
	public static void setEmailMsgLevel(int emailMsgLevel)
	{
		PropertyManager.emailMsgLevel = emailMsgLevel;
	}

	/**
	 * Sets the emailMsgNotify.
	 * 
	 * @param emailMsgNotify
	 *            The emailMsgNotify to set
	 */
	public static void setEmailMsgNotify(String emailMsgNotify)
	{
		PropertyManager.emailMsgNotify = emailMsgNotify;
	}

	/**
	 * Sets the instantMsg.
	 * 
	 * @param instantMsg
	 *            The instantMsg to set
	 */
	public static void setInstantMsg(boolean instantMsg)
	{
		PropertyManager.instantMsg = instantMsg;
	}

	/**
	 * Sets the instantMsgLevel.
	 * 
	 * @param instantMsgLevel
	 *            The instantMsgLevel to set
	 */
	public static void setInstantMsgLevel(int instantMsgLevel)
	{
		PropertyManager.instantMsgLevel = instantMsgLevel;
	}

	/**
	 * Sets the instantMsgNotify.
	 * 
	 * @param instantMsgNotify
	 *            The instantMsgNotify to set
	 */
	public static void setInstantMsgNotify(String instantMsgNotify)
	{
		PropertyManager.instantMsgNotify = instantMsgNotify;
	}

	/**
	 * Sets the ipAddress.
	 * 
	 * @param ipAddress
	 *            The ipAddress to set
	 */
	public static void setIpAddress(String ipAddress)
	{
		PropertyManager.ipAddress = ipAddress;
	}

	/**
	 * Sets the logging.
	 * 
	 * @param logging
	 *            The logging to set
	 */
	public static void setLogging(boolean logging)
	{
		PropertyManager.logging = logging;
	}

	/**
	 * Sets the mailSender.
	 * 
	 * @param mailSender
	 *            The mailSender to set
	 */
	public static void setMailSender(String mailSender)
	{
		PropertyManager.mailSender = mailSender;
	}

	/**
	 * Sets the mailServerHost.
	 * 
	 * @param mailServerHost
	 *            The mailServerHost to set
	 */
	public static void setMailServerHost(String mailServerHost)
	{
		PropertyManager.mailServerHost = mailServerHost;
	}

	/**
	 * Sets the maxConnections.
	 * 
	 * @param maxConnections
	 *            The maxConnections to set
	 */
	public static void setMaxConnections(int maxConnections)
	{
		PropertyManager.maxConnections = maxConnections;
	}

	/**
	 * Sets the minConnections.
	 * 
	 * @param minConnections
	 *            The minConnections to set
	 */
	public static void setMinConnections(int minConnections)
	{
		PropertyManager.minConnections = minConnections;
	}

	/**
	 * Sets the parent.
	 * 
	 * @param parent
	 *            The parent to set
	 */
	public static void setParent(String parent)
	{
		PropertyManager.parent = parent;
	}

	/**
	 * Sets the portNumber.
	 * 
	 * @param portNumber
	 *            The portNumber to set
	 */
	public static void setPortNumber(int portNumber)
	{
		PropertyManager.portNumber = portNumber;
	}

	/**
	 * Sets the rdbName.
	 * 
	 * @param rdbName
	 *            The rdbName to set
	 */
	public static void setRdbName(String rdbName)
	{
		PropertyManager.rdbName = rdbName;
	}

	/**
	 * Sets the source.
	 * 
	 * @param source
	 *            The source to set
	 */
	public static void setSource(String source)
	{
		PropertyManager.source = source;
	}

	/**
	 * Sets the trace.
	 * 
	 * @param trace
	 *            The trace to set
	 */
	public static void setTrace(String trace)
	{
		PropertyManager.trace = trace;
	}

	/**
	 * Returns the backupRegion.
	 * 
	 * @return String
	 */
	public static String getBackupRegion()
	{
		return backupRegion;
	}

	/**
	 * Returns the queryRegion.
	 * 
	 * @return String
	 */
	public static String getQueryRegion()
	{
		return queryRegion;
	}

	/**
	 * Returns the region.
	 * 
	 * @return String
	 */
	public static String getRegion()
	{
		return region;
	}

	/**
	 * Returns the retryCount.
	 * 
	 * @return int
	 */
	public static int getRetryCount()
	{
		return retryCount;
	}

	/**
	 * Returns the retryInterval.
	 * 
	 * @return int
	 */
	public static int getRetryInterval()
	{
		return retryInterval;
	}

	/**
	 * Returns the webRegion.
	 * 
	 * @return String
	 */
	public static String getWebRegion()
	{
		return webRegion;
	}

	/**
	 * Sets the backupRegion.
	 * 
	 * @param backupRegion
	 *            The backupRegion to set
	 */
	public static void setBackupRegion(String backupRegion)
	{
		PropertyManager.backupRegion = backupRegion;
	}

	/**
	 * Sets the queryRegion.
	 * 
	 * @param queryRegion
	 *            The queryRegion to set
	 */
	public static void setQueryRegion(String queryRegion)
	{
		PropertyManager.queryRegion = queryRegion;
	}

	/**
	 * Sets the region.
	 * 
	 * @param region
	 *            The region to set
	 */
	public static void setRegion(String region)
	{
		PropertyManager.region = region;
	}

	/**
	 * Sets the retryCount.
	 * 
	 * @param retryCount
	 *            The retryCount to set
	 */
	public static void setRetryCount(int retryCount)
	{
		PropertyManager.retryCount = retryCount;
	}

	/**
	 * Sets the retryInterval.
	 * 
	 * @param retryInterval
	 *            The retryInterval to set
	 */
	public static void setRetryInterval(int retryInterval)
	{
		PropertyManager.retryInterval = retryInterval;
	}

	/**
	 * Sets the webRegion.
	 * 
	 * @param webRegion
	 *            The webRegion to set
	 */
	public static void setWebRegion(String webRegion)
	{
		PropertyManager.webRegion = webRegion;
	}

	/**
	 * Returns the autoCreatePkgs.
	 * 
	 * @return String
	 */
	public static String getAutoCreatePkgs()
	{
		return autoCreatePkgs;
	}

	/**
	 * Returns the remoteMonitorAddr.
	 * 
	 * @return String
	 */
	public static String getRemoteMonitorAddr()
	{
		return remoteMonitorAddr;
	}

	/**
	 * Returns the traceLevel.
	 * 
	 * @return int
	 */
	public static int getTraceLevel()
	{
		return traceLevel;
	}

	/**
	 * Sets the autoCreatePkgs.
	 * 
	 * @param autoCreatePkgs
	 *            The autoCreatePkgs to set
	 */
	public static void setAutoCreatePkgs(String autoCreatePkgs)
	{
		PropertyManager.autoCreatePkgs = autoCreatePkgs;
	}

	/**
	 * Sets the remoteMonitorAddr.
	 * 
	 * @param remoteMonitorAddr
	 *            The remoteMonitorAddr to set
	 */
	public static void setRemoteMonitorAddr(String remoteMonitorAddr)
	{
		PropertyManager.remoteMonitorAddr = remoteMonitorAddr;
	}

	/**
	 * Sets the traceLevel.
	 * 
	 * @param traceLevel
	 *            The traceLevel to set
	 */
	public static void setTraceLevel(int traceLevel)
	{
		PropertyManager.traceLevel = traceLevel;
	}

	/**
	 * Returns the connectionTimeout.
	 * 
	 * @return int
	 */
	public static int getConnectionTimeout()
	{
		return connectionTimeout;
	}

	/**
	 * Returns the jobName.
	 * 
	 * @return String
	 */
	public static String getJobName()
	{
		return jobName;
	}

	/**
	 * Sets the connectionTimeout.
	 * 
	 * @param connectionTimeout
	 *            The connectionTimeout to set
	 */
	public static void setConnectionTimeout(int connectionTimeout)
	{
		PropertyManager.connectionTimeout = connectionTimeout;
	}

	/**
	 * Sets the jobName.
	 * 
	 * @param jobName
	 *            The jobName to set
	 */
	public static void setJobName(String jobName)
	{
		PropertyManager.jobName = jobName;
	}

}
