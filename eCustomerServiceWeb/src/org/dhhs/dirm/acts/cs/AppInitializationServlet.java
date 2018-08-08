

package org.dhhs.dirm.acts.cs;

/*
 * import java.io.*; import java.sql.SQLException; import java.util.*; import
 * javax.servlet.*; import javax.servlet.http.*;
 * 
 * import org.dhhs.dirm.acts.cs.beans.*; import
 * org.dhhs.dirm.acts.cs.businesslogic.*; import org.dhhs.dirm.acts.cs.ejb.*;
 * import org.dhhs.dirm.acts.cs.formbeans.*; import
 * org.dhhs.dirm.acts.cs.helpers.*; import
 * org.dhhs.dirm.acts.cs.persister.AgentDelegate;
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.dhhs.dirm.acts.cs.beans.UserBean;
import org.dhhs.dirm.acts.cs.beans.UserEntityBean;
import org.dhhs.dirm.acts.cs.businesslogic.BusinessLogicException;
import org.dhhs.dirm.acts.cs.businesslogic.FormTypeManager;
import org.dhhs.dirm.acts.cs.businesslogic.ReferralSourceManager;
import org.dhhs.dirm.acts.cs.businesslogic.UserProfileManager;
import org.dhhs.dirm.acts.cs.ejb.CSUserLocal;
import org.dhhs.dirm.acts.cs.ejb.CSUserLocalHome;
import org.dhhs.dirm.acts.cs.helpers.HomeHelper;
import org.dhhs.dirm.acts.cs.persister.AgentDelegate;

/**
 *
 * AppInitialization Servlet Used as the application Startup Program that can
 * read application level properties and create a java bean that houses the
 * information with application level scope.
 *
 * This servlet must be set to run as soon as the application server starts Set
 * the Load on Startup to true
 *
 * Rama Kodumagulla 03/05/03
 * 
 * Modified the AppInitialization Servlet Logic to read in parameters from the
 * Servlet Init Parms The Parameters at this point are the Drive Letter and Path
 * 
 * These parms are used to load the properties for the entire webapplication.
 * Failure to set the path correctly or missing properties within the path will
 * result in application failure
 * 
 * 
 * End Changes 03/05/03
 * 
 * 
 * Creation date: (4/19/2002 10:28:56 AM)
 * 
 * @author: Ramakanth Kodumagulla
 */
public class AppInitializationServlet extends GenericServlet
{

	private static final String CONFIG_BUNDLE_NAME = "corp.sysrad.acts.admin.ApplicationConfig";
	/**
	 * Returns the servlet info string.
	 */
	public String getServletInfo()
	{

		return super.getServletInfo();

	}
	/**
	 * Initializes the servlet.
	 */
	public void init(ServletConfig config) throws ServletException
	{

		super.init(config);

		String fullPath = System.getProperty("workingDir");

		System.out.println("AppInitialization Servlet: The WebApplication Path in use is " + fullPath);
		System.out.println("AppInitialization Servlet: Loading County Codes from CountyCodes.txt File.....");

		Vector countyCodes = new Vector();

		try
		{
			File f = new File(fullPath, "CountyCodes.txt");
			BufferedReader br = new BufferedReader(new FileReader(f));
			while (true)
			{
				String line = br.readLine();
				if (line == null)
				{
					break;
				}
				countyCodes.addElement(line);
			}
			br.close();
			getServletContext().setAttribute("countyCodes", countyCodes);
		} catch (IOException ioe)
		{
			ioe.printStackTrace();
		}

		System.out.println("AppInitialization Servlet: Successfully Loaded County Codes.");

		System.out.println("AppInitialization Servlet: Loading Application Scope Variables\n");

		try
		{
			FormTypeManager manager = FormTypeManager.getInstance();
			getServletContext().setAttribute("forms", manager.getAllFormTypes());
		} catch (BusinessLogicException ble)
		{
			System.out.println("AppInitialization Servlet: Failed to load forms " + ble.getMessage() + "\n");
		} catch (SQLException ble)
		{
			System.out.println("AppInitialization Servlet: Failed to load forms " + ble.getMessage() + "\n");
		}

		try
		{
			UserProfileManager profileManager = UserProfileManager.getInstance();
			getServletContext().setAttribute("profiles", profileManager.getAllUserProfiles());
		} catch (BusinessLogicException ble)
		{
			System.out.println("AppInitialization Servlet: Failed to load profiles " + ble.getMessage() + "\n");
		} catch (SQLException ble)
		{
			System.out.println("AppInitialization Servlet: Failed to load profiles " + ble.getMessage() + "\n");
		}

		try
		{
			ReferralSourceManager rfsManager = ReferralSourceManager.getInstance();
			getServletContext().setAttribute("referralSources", rfsManager.getAllReferralSources());
		} catch (BusinessLogicException ble)
		{
			System.out.println("AppInitialization Servlet: Failed to load referral sources " + ble.getMessage() + "\n");
		} catch (SQLException ble)
		{
			System.out.println("AppInitialization Servlet: Failed to load referral sources " + ble.getMessage() + "\n");
		}

		/**
		 * Load all the active users and save the list in application scope
		 * variable.
		 */

		try
		{
			Vector users = new Vector();

			/**
			 * Invoke the Business Process and return results
			 */
			Object obj = HomeHelper.singleton().getHome("ecsts.CSUserLocalHome");
			CSUserLocalHome userLocalHome = (CSUserLocalHome) obj;

			Collection c = userLocalHome.findAllUsers();
			Iterator i = c.iterator();
			while (i.hasNext())
			{
				CSUserLocal user = (CSUserLocal) i.next();
				users.addElement(createUserBean(user));
			}

			/**
			 * RK CT# 523481 For each agent that has been read from the
			 * database, load them to the Agent Assignment Table 08/12/04
			 */
			for (int j = 0; j < users.size(); j++)
			{
				UserEntityBean ueb = new UserEntityBean();
				ueb.setIdWrkr(((UserBean) users.elementAt(j)).getIdWorker());
				ueb.setCdAccptWrkld(((UserBean) users.elementAt(j)).getCdAccptWrkld());
				AgentDelegate.addAgent(ueb);
			}
			// RK CT# 523481 - END

			getServletContext().setAttribute("agents", users);
		} catch (javax.ejb.FinderException e)
		{
			System.out.println("CAUTION:   AppInitialization Servlet Failed to Initialize." + e.getMessage());
		} catch (javax.naming.NamingException e)
		{
			System.out.println("CAUTION:   AppInitialization Servlet Failed to Initialize. " + e.getMessage());
		}

		System.out.println("Servlet Context : " + getServletContext());
		System.out.println("AppInitialization Servlet: Initialized Successfully");

	}

	private org.dhhs.dirm.acts.cs.beans.UserBean createUserBean(CSUserLocal user)
	{
		org.dhhs.dirm.acts.cs.beans.UserBean userBean = new org.dhhs.dirm.acts.cs.beans.UserBean();
		UserEntityBean userEntityBean = user.getUserEntityBean();

		userBean.setNmWorker(userEntityBean.getNmWrkr());
		userBean.setCdAccptWrkld(userEntityBean.getCdAccptWrkld());
		userBean.setIdProfile(userEntityBean.getIdProfile());
		userBean.setIdWorker(userEntityBean.getIdWrkr().trim());
		userBean.setNbrOutstanding(userEntityBean.getNbOutstanding());
		userBean.setNbrCompleted(userEntityBean.getNbCompleted());
		userBean.setNbrApproval(userEntityBean.getNbApproval());

		return userBean;
	}
	/**
	 * @see javax.servlet.Servlet#service(ServletRequest, ServletResponse)
	 */
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException
	{
	}

}
