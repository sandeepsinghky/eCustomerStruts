

package org.dhhs.dirm.acts.cs;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * Controller Servlet Used as the application Startup Program that can read
 * application level properties and create a java bean that houses the
 * information with application level scope.
 *
 * This servlet must be set to run as soon as the application server starts Set
 * the Load on Startup to true
 *
 * Rama Kodumagulla 03/05/03
 * 
 * Modified the Controller Servlet Logic to read in parameters from the Servlet
 * Init Parms The Parameters at this point are the Drive Letter and Path
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
public class ControllerServlet extends GenericServlet
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
		/**
		 * 03/05/03 Using the ServletConfig, obtain the init parameters
		 */

		String driveLetter = config.getInitParameter("drive");
		String path = config.getInitParameter("path");

		if (driveLetter == null)
		{
			String fatalMsg = "Controller Servlet Fatal Error: Drive Letter not set in the Servlet Init Parameters";
			throw new ServletException(fatalMsg);
		} else if (driveLetter.length() < 2)
		{
			String fatalMsg = "Controller Servlet Fatal Error: Drive Letter must be a character followed by a colon. Example D:";
			throw new ServletException(fatalMsg);
		}

		if (path == null)
		{
			String fatalMsg = "Controller Servlet Fatal Error: Path not set in the Servlet Init Parameters";
			throw new ServletException(fatalMsg);
		} else if (path.indexOf("/") > 0)
		{
			String fatalMsg = "Controller Servlet Fatal Error: Path Name cannot contain a '/'. Replace '/' with '\' and retry ";
			throw new ServletException(fatalMsg);
		}

		String fullPath = driveLetter + System.getProperty("file.separator") + path;

		System.out.println("Controller Servlet: The WebApplication Path in use is " + fullPath);

		System.setProperty("workingDir", fullPath);

		try
		{
			PropertyManager.loadAppProperties(fullPath);
		} catch (Exception e)
		{
			throw new ServletException(e.getMessage());
		}

		try
		{
			PropertyManager.loadDBProperties(fullPath);
		} catch (Exception e)
		{
			throw new ServletException(e.getMessage());
		}

		/*
		 * System.out.println(
		 * "Controller Servlet: Loading County Codes from CountyCodes.txt File....."
		 * );
		 * 
		 * Vector countyCodes = new Vector();
		 * 
		 * try { File f = new File(fullPath, "CountyCodes.txt"); BufferedReader
		 * br = new BufferedReader(new FileReader(f)); while (true) { String
		 * line = br.readLine(); if (line == null) { break; }
		 * countyCodes.addElement(line); } br.close();
		 * getServletContext().setAttribute("countyCodes", countyCodes); } catch
		 * (IOException ioe) { ioe.printStackTrace(); }
		 * 
		 * System.out.println(
		 * "Controller Servlet: Successfully Loaded County Codes.");
		 * 
		 * System.out.println(
		 * "Controller Servlet: Loading Application Scope Variables\n");
		 * 
		 * try { FormTypeManager manager = FormTypeManager.getInstance();
		 * getServletContext().setAttribute("forms", manager.getAllFormTypes());
		 * } catch (BusinessLogicException ble) { System.out.println(
		 * "Controller Servlet: Failed to load forms " + ble.getMessage() +
		 * "\n"); }
		 * 
		 * try { UserProfileManager profileManager =
		 * UserProfileManager.getInstance();
		 * getServletContext().setAttribute("profiles",
		 * profileManager.getAllUserProfiles()); } catch (BusinessLogicException
		 * ble) { System.out.println(
		 * "Controller Servlet: Failed to load profiles " + ble.getMessage() +
		 * "\n"); }
		 * 
		 * try { ReferralSourceManager rfsManager =
		 * ReferralSourceManager.getInstance();
		 * getServletContext().setAttribute("referralSources",
		 * rfsManager.getAllReferralSources()); } catch (BusinessLogicException
		 * ble) { System.out.println(
		 * "Controller Servlet: Failed to load referral sources " +
		 * ble.getMessage() + "\n"); }
		 * 
		 * try { Vector users = new Vector();
		 * 
		 * Object obj = HomeHelper.singleton().getHome("ecsts.CSUserLocalHome");
		 * CSUserLocalHome userLocalHome = (CSUserLocalHome) obj;
		 * 
		 * Collection c = userLocalHome.findAllUsers(); Iterator i =
		 * c.iterator(); while (i.hasNext()) { CSUserLocal user = (CSUserLocal)
		 * i.next(); users.addElement(createUserBean(user)); }
		 * 
		 * getServletContext().setAttribute("agents", users); } catch
		 * (javax.ejb.FinderException e) { System.out.println(
		 * "CAUTION:   Controller Servlet Failed to Initialize." +
		 * e.getMessage()); } catch (javax.naming.NamingException e) {
		 * System.out.println(
		 * "CAUTION:   Controller Servlet Failed to Initialize. " +
		 * e.getMessage()); }
		 */

		System.out.println("Servlet Context : " + getServletContext());
		System.out.println("Controller Servlet: Initialized Successfully");

	}
	/*
	 * private org.dhhs.dirm.acts.cs.beans.UserBean createUserBean(CSUserLocal
	 * user) { org.dhhs.dirm.acts.cs.beans.UserBean userBean = new
	 * org.dhhs.dirm.acts.cs.beans.UserBean(); UserEntityBean userEntityBean =
	 * user.getUserEntityBean();
	 * 
	 * userBean.setNmWorker(userEntityBean.getNmWrkr());
	 * userBean.setCdAccptWrkld(userEntityBean.getCdAccptWrkld());
	 * userBean.setIdProfile(userEntityBean.getIdProfile());
	 * userBean.setIdWorker(userEntityBean.getIdWrkr().trim());
	 * userBean.setNbrOutstanding(userEntityBean.getNbOutstanding());
	 * userBean.setNbrCompleted(userEntityBean.getNbCompleted());
	 * userBean.setNbrApproval(userEntityBean.getNbApproval());
	 * 
	 * return userBean; }
	 */
	/**
	 * @see javax.servlet.Servlet#service(ServletRequest, ServletResponse)
	 */
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException
	{
	}

}
