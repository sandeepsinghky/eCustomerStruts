

package org.dhhs.dirm.acts.cs;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Insert the type's description here. Creation date: (12/7/2001 11:38:58 AM)
 * 
 * @author: Ramakanth Kodumagulla
 */
public class SwapDatabaseInvoker
{

	private String	to;
	private String	from;
	private String	subject;
	private String	body;
	private String	host;
	private boolean	debug;
	private String	user;
	private String	password;

	/**
	 * SwapDatabaseInvoker constructor comment.
	 */

	public SwapDatabaseInvoker()
	{

		/**
		 * Get information at runtime (from an external property file identified
		 * by CONFIG_BUNDLE_NAME) for the mail server properties
		 */
		System.out.println("SwapDatabaseInvoker Initiated");

		host = PropertyManager.getPrimaryURL();

		System.out.println("host: " + host);

		String urlString = host + "/SwapDatabase?id=0";

		System.out.println("URL: " + urlString);

		try
		{
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();

			System.out.println("Connection " + connection);

			connection.setDoInput(true);
			connection.setDoOutput(true);

			System.out.println(connection.getContent());
			System.out.println(connection.getContentEncoding());

		} catch (MalformedURLException mfe)
		{
			System.out.println(mfe);
		} catch (IOException ioe)
		{
			System.out.println(ioe);
		}
		System.out.println("SwapDatabaseInvoker Completed Successfully");
	}

	public SwapDatabaseInvoker(String strURL)
	{

		/**
		 * Get information at runtime (from an external property file identified
		 * by CONFIG_BUNDLE_NAME) for the mail server properties
		 */
		System.out.println("SwapDatabaseInvoker Initiated for url: " + strURL);

		host = strURL;

		System.out.println("host: " + host);

		String urlString = host;

		System.out.println("URL: " + urlString);

		try
		{
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();

			System.out.println("Connection " + connection);

			connection.setDoInput(true);
			connection.setDoOutput(true);

			System.out.println(connection.getContent());
			System.out.println(connection.getContentEncoding());

		} catch (MalformedURLException mfe)
		{
			System.out.println(mfe);
		} catch (IOException ioe)
		{
			System.out.println(ioe);
		}
		System.out.println("SwapDatabaseInvoker Completed Successfully");
	}

	/**
	 * Insert the method's description here. Creation date: (12/11/2001 10:59:13
	 * AM)
	 * 
	 * @param id
	 *            int
	 */
	public SwapDatabaseInvoker(int id, int port)
	{

		/**
		 * Get information at runtime (from an external property file identified
		 * by CONFIG_BUNDLE_NAME) for the mail server properties
		 */
		System.out.println("SwapDatabaseInvoker Initiated");

		host = PropertyManager.getPrimaryURL();

		System.out.println("host: " + host);

		String urlString = "";

		switch (id)
		{
			case 0 :
				if (port > 0)
				{
					urlString = host + ":" + port + "/SwapDatabase?id=" + id;
				} else
				{
					urlString = host + "/SwapDatabase?id=" + id;
				}
				break;
			case 1 :
				if (port > 0)
				{
					urlString = host + ":" + port + "/SwapDatabase?id=" + id;
				} else
				{
					urlString = host + "/SwapDatabase?id=" + id;
				}
				break;
			case 2 :
				if (port > 0)
				{
					urlString = host + ":" + port + "/AutoRetryDBStatus";
				} else
				{
					urlString = host + "/AutoRetryDBStatus";
				}
				break;
		}

		System.out.println("URL: " + urlString);

		try
		{
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();

			System.out.println("Connection " + connection);

			connection.setDoInput(true);
			connection.setDoOutput(true);

			System.out.println(connection.getContent());
			System.out.println(connection.getContentEncoding());

		} catch (MalformedURLException mfe)
		{
			System.out.println(mfe);
		} catch (IOException ioe)
		{
			System.out.println(ioe);
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

			SwapDatabaseInvoker sdi = new SwapDatabaseInvoker();

		} else if (args.length == 1)
		{
			SwapDatabaseInvoker sdi = new SwapDatabaseInvoker(args[0]);
		} else
		{
			String strID = args[0];
			String strPort = args[1];

			int id = 0;
			int port = 0;

			try
			{
				id = Integer.parseInt(strID);
			} catch (NumberFormatException nfe)
			{
				System.out.println("Invalid Parameter Entered, must be a number");
				return;
			}

			if (strPort != null || strPort != "")
			{
				try
				{
					port = Integer.parseInt(strPort);
				} catch (NumberFormatException nfe)
				{
					System.out.println("Invalid Parameter Entered, must be a number");
					return;
				}
			}

			SwapDatabaseInvoker sdi = new SwapDatabaseInvoker(id, port);
		}
	}
}
