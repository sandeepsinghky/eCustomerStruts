

package org.dhhs.dirm.acts.cs.helpers;

import java.util.MissingResourceException;
import java.util.Properties;
import java.util.PropertyResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * NamingHelper.java
 * 
 * Provides access to JNDI properties for lookups. The properties are stored in
 * two resource files that are read when this class is instantiated. The files
 * contain the following:
 * <UL>
 * <LI>JNDI_LOOKUP_PROPERTIES:<BR>
 * <UL>
 * <LI>The name of the InitialContextFactory class to use</LI>
 * <LI>The IIOP URL for the Persistant name server</LI>
 * </UL>
 * </LI>
 * <LI>JNDI_NAMES:<BR>
 * <UL>
 * <LI>The JNDI names of any resources.</LI>
 * </UL>
 * </LI>
 * </UL>
 *
 * This class is implemented as a singleton.
 *
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Aug 28, 2003 10:08:37 AM
 * 
 * @author Rkodumagulla
 *
 */

public class NamingHelper
{

	// singleton instance variable
	private static NamingHelper		instance					= null;

	// InitialContext object
	private static InitialContext	ctx							= null;

	// default names which are used if the files are not present, or
	// cannot be read. These are for WAS 3.5 on the same server
	private static final String		defaultContextFactoryName	= "com.ibm.ejs.ns.jndi.CNInitialContextFactory";
	private static final String		defaultLookupURL			= "iiop:///";

	// names of the .properties files
	private static final String		JNDI_LOOKUP_PROPERTIES		= "org.dhhs.dirm.acts.cs.helpers.eCustomerServiceJNDILookup";
	private static final String		JNDI_NAMES					= "org.dhhs.dirm.acts.cs.helpers.eCustomerServiceJNDINames";

	// Resource bundles to hold the file contents
	private PropertyResourceBundle	JNDIProperties;
	private PropertyResourceBundle	JNDINames;
	/**
	 * NamingHelper default constructor.
	 */
	private NamingHelper()
	{
		super();
		try
		{
			JNDIProperties = (PropertyResourceBundle) PropertyResourceBundle.getBundle(JNDI_LOOKUP_PROPERTIES);
			JNDINames = (PropertyResourceBundle) PropertyResourceBundle.getBundle(JNDI_NAMES);
		} catch (Throwable t)
		{
			t.printStackTrace();
		}
	}
	/**
	 * Retrieves the InitialContextFactory name to use in EJB home lookups.
	 *
	 * @return The InitialContextFactory to use
	 */
	public String getContextFactoryName()
	{

		String dynamicContextFactoryName = JNDIProperties.getString("java.naming.factory.initial");

		if (dynamicContextFactoryName != null)
			return dynamicContextFactoryName;
		else
			return defaultContextFactoryName;
	}
	/**
	 * Retrieves the WebSphere InitialContext which can then be used for
	 * DataSource, EJB Home and JMS administered object lookup.
	 *
	 * @return The InitialContext object
	 *
	 * @exception javax.naming.NamingException
	 */
	public InitialContext getInitialContext() throws NamingException
	{

		if (ctx == null)
		{
			Properties props = new Properties();
			props.put(javax.naming.Context.PROVIDER_URL, this.getLookupURL());
			props.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, getContextFactoryName());
			ctx = new javax.naming.InitialContext(props);
		}
		return ctx;
	}
	/**
	 * Retrieves the JNDI name for a specified resource. If we cannot find the
	 * name, for instance if the property file is corrupt, then we return the
	 * name provided to us.
	 *
	 * @param String
	 *            Fully qualified class name of the EJB we want to look up
	 *
	 * @return The JNDI name to use
	 */
	public String getJNDIName(String name)
	{
		String dynamicJNDIName = null;

		try
		{
			dynamicJNDIName = JNDINames.getString(name);
			System.out.println("Found JNDI Name: " + dynamicJNDIName);
		} catch (MissingResourceException mre)
		{
			mre.printStackTrace();
		}

		if (dynamicJNDIName != null)
			return dynamicJNDIName;
		else
			return name;
	}
	/**
	 * Retrieves the provider URL to use in EJB home lookups.
	 *
	 * @return The URL to use
	 */
	public String getLookupURL()
	{

		String dynamicLookupURL = JNDIProperties.getString("java.naming.provider.url");

		if (dynamicLookupURL != null)
			return dynamicLookupURL;
		else
			return defaultLookupURL;
	}
	/**
	 * Singleton accessor method.
	 *
	 * @return Singleton instance of NamingHelper
	 */
	public static NamingHelper singleton()
	{
		if (instance == null)
		{
			instance = new NamingHelper();
		}
		return instance;
	}
}
