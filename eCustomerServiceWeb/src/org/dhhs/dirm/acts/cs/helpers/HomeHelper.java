

package org.dhhs.dirm.acts.cs.helpers;

import java.util.Hashtable;

import javax.naming.NamingException;

/**
 * HomeHelper.java
 * 
 * Provides access to EJB home lookups.
 *
 * This class is implemented as a singleton.
 *
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Aug 28, 2003 10:07:47 AM
 * 
 * @author Rkodumagulla
 *
 */

public class HomeHelper
{

	// singleton instance variable
	private static HomeHelper	instance	= null;

	private Hashtable			homes		= new Hashtable();

	/**
	 * NamingHelper default constructor.
	 */
	private HomeHelper()
	{
		super();
	}
	/**
	 * Performs a JNDI lookup to find an EJB Home object. As this class is a
	 * singleton, the lookup only occurs the first time. After that the EJB home
	 * is simply retrieved from the Hashtable.
	 *
	 * @param lookupName
	 *            java.lang.String
	 */
	public Object getHome(String homeName) throws NamingException
	{

		Object obj = (Object) homes.get(homeName);

		if (obj == null)
		{
			// get InitialContext and perform lookup
			obj = NamingHelper.singleton().getInitialContext().lookup(new StringBuffer("java:comp/env/").append(NamingHelper.singleton().getJNDIName(homeName)).toString());
			homes.put(homeName, obj);
		}

		if (obj instanceof javax.ejb.EJBLocalHome)
		{
			System.out.println("ejbRef " + homeName + " is a local reference.");
			return obj;
		} else
		{
			System.out.println("ejbRef " + homeName + " is a remote reference.");
			javax.ejb.EJBHome ejbHome = (javax.ejb.EJBHome) javax.rmi.PortableRemoteObject.narrow((org.omg.CORBA.Object) obj, javax.ejb.EJBHome.class);
			return ejbHome;
		}
	}
	/**
	 * Singleton accessor method.
	 *
	 * @return Singleton instance of NamingHelper
	 */
	public static HomeHelper singleton()
	{
		if (instance == null)
		{
			instance = new HomeHelper();
		}
		return instance;
	}
}
