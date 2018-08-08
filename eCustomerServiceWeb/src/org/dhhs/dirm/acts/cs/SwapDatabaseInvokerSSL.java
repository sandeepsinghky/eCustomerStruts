

package org.dhhs.dirm.acts.cs;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.Security;

import com.sun.net.ssl.HttpsURLConnection;

/**
 * Insert the type's description here. Creation date: (12/7/2001 11:38:58 AM)
 * 
 * @author: Ramakanth Kodumagulla
 */
public class SwapDatabaseInvokerSSL
{

	private String				to;
	private String				from;
	private String				subject;
	private String				body;
	private String				host;
	private boolean				debug;
	private String				user;
	private String				password;

	private static final String	CONFIG_BUNDLE_NAME	= "corp.sysrad.acts.ConPool.ApplicationConfig";

	/**
	 * Insert the method's description here. Creation date: (12/11/2001 10:59:13
	 * AM)
	 * 
	 * @param id
	 *            int
	 */
	public SwapDatabaseInvokerSSL(int id, String property_path)
	{

		/**
		 * Get information at runtime (from an external property file identified
		 * by CONFIG_BUNDLE_NAME) for the mail server properties
		 */
		System.out.println("SwapDatabaseInvoker Initiated");

		System.out.println("SwapDatabaseInvokerSSL Path to the properties File: " + property_path);

		try
		{
			PropertyManager.loadAppProperties(property_path);
			PropertyManager.loadDBProperties(property_path);
		} catch (Exception e)
		{
			e.printStackTrace();
			System.exit(16);
		}
		host = PropertyManager.getSecondaryURL();

		System.out.println("Attempting to connect to host: " + host);

		String urlString = "";

		switch (id)
		{
			case 0 :
				urlString = host + "/SwapDatabase?id=" + id;
				break;
			case 1 :
				urlString = host + "/SwapDatabase?id=" + id;
				break;
			case 2 :
				urlString = host + "/AutoRetryDBStatus";
				break;
		}

		System.out.println("Http URL to be invoked: " + urlString);

		System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");

		try
		{
			Class clsFactory = Class.forName("com.sun.net.ssl.internal.ssl.Provider");
			if ((null != clsFactory) && (null == java.security.Security.getProvider("SunJSSE")))
			{
				Security.addProvider((com.sun.net.ssl.internal.ssl.Provider) clsFactory.newInstance());
			}
		} catch (ClassNotFoundException cnfe)
		{
			System.out.println(cnfe);
		} catch (InstantiationException ie)
		{
			System.out.println(ie);
		} catch (IllegalAccessException iae)
		{
			System.out.println(iae);
		}

		try
		{
			URL url = new URL(urlString);

			URLConnection connection = url.openConnection();

			HttpsURLConnection secConn = (HttpsURLConnection) connection;

			System.out.println("Secure Connection Made to host using: " + secConn);

			secConn.setDoInput(true);
			secConn.setDoOutput(true);

			secConn.getContent();
			secConn.getContentEncoding();

			secConn.disconnect();

		} catch (MalformedURLException mfe)
		{
			mfe.printStackTrace();
		} catch (IOException ioe)
		{
			ioe.printStackTrace();
		}

		System.out.println("SwapDatabaseInvoker Completed Successfully");

	}
	/**
	 * Creation date: (12/12/2001 2:13:48 PM)
	 * 
	 * @author: Ramakanth Kodumagulla
	 * 
	 * 
	 * @param id
	 *            int
	 * @param primary_region
	 *            java.lang.String
	 * @param backup_region
	 *            java.lang.String
	 */
	public SwapDatabaseInvokerSSL(int id, String primary_region, String backup_region, String property_path)
	{

		/**
		 * Get information at runtime (from an external property file identified
		 * by CONFIG_BUNDLE_NAME) for the mail server properties
		 */
		System.out.println("SwapDatabaseInvoker Initiated");

		System.out.println("SwapDatabaseInvokerSSL Path to the properties File: " + property_path);

		try
		{
			PropertyManager.loadAppProperties(property_path);
			PropertyManager.loadDBProperties(property_path);
		} catch (Exception e)
		{
			e.printStackTrace();
			System.exit(16);
		}
		host = PropertyManager.getSecondaryURL();

		System.out.println("Attempting to connect to host: " + host);

		String urlString = "";

		switch (id)
		{
			case 0 :
				urlString = host + "/SwapDatabase?id=" + id;
				break;
			case 1 :
				urlString = host + "/SwapDatabase?id=" + id + "&primary=" + primary_region + "&backup=" + backup_region;
				break;
			case 2 :
				urlString = host + "/AutoRetryDBStatus";
				break;
		}

		System.out.println("Http URL to be invoked: " + urlString);

		System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");

		try
		{
			Class clsFactory = Class.forName("com.sun.net.ssl.internal.ssl.Provider");
			if ((null != clsFactory) && (null == java.security.Security.getProvider("SunJSSE")))
			{
				Security.addProvider((com.sun.net.ssl.internal.ssl.Provider) clsFactory.newInstance());
			}
		} catch (ClassNotFoundException cnfe)
		{
			System.out.println(cnfe);
		} catch (InstantiationException ie)
		{
			System.out.println(ie);
		} catch (IllegalAccessException iae)
		{
			System.out.println(iae);
		}

		try
		{
			URL url = new URL(urlString);

			URLConnection connection = url.openConnection();

			HttpsURLConnection secConn = (HttpsURLConnection) connection;

			System.out.println("Secure Connection Made to host using: " + secConn);

			secConn.setDoInput(true);
			secConn.setDoOutput(true);

			secConn.getContent();
			secConn.getContentEncoding();

			secConn.disconnect();

		} catch (MalformedURLException mfe)
		{
			mfe.printStackTrace();
		} catch (IOException ioe)
		{
			ioe.printStackTrace();
		}

		System.out.println("SwapDatabaseInvoker Completed Successfully");

	}
	/**
	 * Starts the application.
	 * 
	 * @param args
	 *            an array of command-line arguments
	 */
	public static void main(java.lang.String[] args)
	{

		if (args.length == 0)
		{
			System.out.println("Invalid Call, Missing Parameters");
			System.exit(16);
		} else
		{
			String strID = args[0];

			int id = 0;

			try
			{
				id = Integer.parseInt(strID);

				switch (id)
				{
					case 0 :
						String path = args[1];
						SwapDatabaseInvokerSSL sdi = new SwapDatabaseInvokerSSL(id, path);
						break;
					case 1 :
						// If the paramter is 1, then primary and backup regions
						// are required
						String primary_region = args[1];
						if (primary_region == null)
						{
							System.out.println("Primary Database region is required");
							return;
						}

						String backup_region = args[2];
						if (backup_region == null)
						{
							System.out.println("Backup Database region is required");
							return;
						}
						path = args[3];
						sdi = new SwapDatabaseInvokerSSL(id, primary_region, backup_region, path);
						break;
					case 2 :
						path = args[1];
						sdi = new SwapDatabaseInvokerSSL(id, path);
						break;
				}
			} catch (NumberFormatException nfe)
			{
				System.out.println("Invalid Parameter Entered, must be a number");
				return;
			}

		}
	}
}
