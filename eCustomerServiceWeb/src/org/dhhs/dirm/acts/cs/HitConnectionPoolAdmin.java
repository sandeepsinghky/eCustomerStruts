

package org.dhhs.dirm.acts.cs;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * A GenericServlet Class that is used by the WebSphere Application Server
 * Instance create a pool of database connections
 * <p>
 * Note: Usage of this servlet is strictly within an Application Server and the
 * servlet must be set to load on start as it does not have any other means to
 * start the thread.
 * <p>
 * A restart of the Application Server is required whenever the database
 * connections are not released correctly.
 * <p>
 * This Servlet instantiates the DataSourceAdmin Class that actually creates the
 * connection pooled datasources.
 * <p>
 * Creation date: (7/27/2001 10:47:42 AM)
 * 
 * @author: Ramakanth Kodumagulla
 */

public class HitConnectionPoolAdmin extends GenericServlet
{

	/**
	 * This method initializes the servlet and creates the pool of database
	 * connections that will be used by the servlets Creation date: (7/27/01
	 * 09:15:09 AM)
	 * 
	 * @param config
	 *            javax.servlet.ServletConfig
	 * @exception javax.servlet.ServletException
	 *                The exception description.
	 */
	public void init(ServletConfig config) throws javax.servlet.ServletException
	{

		super.init(config);

		DataSourceAdmin ds = new DataSourceAdmin();
	}
	/**
	 * This method is not used as this servlet is not designed to serve
	 * additional requests Please do not remove this method either, since we are
	 * subclassing GenericServlet.
	 */
	public void service(ServletRequest arg1, ServletResponse arg2) throws ServletException, IOException
	{

	}
}
