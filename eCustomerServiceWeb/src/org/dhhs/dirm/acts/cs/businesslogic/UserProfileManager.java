

package org.dhhs.dirm.acts.cs.businesslogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.naming.Context;
import javax.sql.DataSource;

import org.dhhs.dirm.acts.cs.Constants;
import org.dhhs.dirm.acts.cs.DBConnectManager;
import org.dhhs.dirm.acts.cs.PropertyManager;
import org.dhhs.dirm.acts.cs.beans.Profile;
import org.dhhs.dirm.acts.cs.helpers.FormatTimestamp;

/**
 * UserProfileManager.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Aug 18, 2003 9:59:00 AM
 * 
 * @author Rkodumagulla
 *
 */
public class UserProfileManager
{

	/**
	 *
	 */
	private static UserProfileManager	userProfiles	= null;
	private static Context				ctx				= null;
	private static DataSource			dataSource		= null;
	private static String				user			= null;
	private static String				password		= null;

	/**
	 * Constructor for UserRegistry.
	 */
	public UserProfileManager() throws BusinessLogicException
	{

		DBConnectManager cm = new DBConnectManager();

		dataSource = cm.getDataSource();
		user = cm.getUserID();
		password = cm.getPassword();

		if (dataSource == null)
		{
			throw new BusinessLogicException("DataSource Initialization Exception");
		}
	}

	public void closeConnection(Connection connection) throws BusinessLogicException
	{
		try
		{
			connection.commit();
			connection.close();
		} catch (Exception e)
		{
			throw new BusinessLogicException(e.getMessage());
		}
	}

	/**
	 *
	 */
	public static UserProfileManager getInstance() throws BusinessLogicException
	{

		if (null == userProfiles)
		{
			userProfiles = new UserProfileManager();
		}
		return userProfiles;
	}

	public Vector getUserProfiles(String idProfile) throws BusinessLogicException, SQLException
	{
		
		Connection connection = null;
		Statement getProfileStatement = null;
		ResultSet getProfileResultSet = null;

		Vector profiles = new Vector();

		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);
			getProfileStatement = connection.createStatement();
			String query = "SELECT ID_PROFILE, ID_PROFILE_DESC, CD_MENU_ITEMS, TS_CREATE, ID_WRKR_CREATE, TS_UPDATE, ID_WRKR_UPDATE " + "  FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_PROFILE + " " + " WHERE ID_PROFILE LIKE '" + idProfile + "'";
			System.out.println("UserProfileManager.getUserProfiles " + query);
			getProfileResultSet = getProfileStatement.executeQuery(query);
			if (getProfileResultSet.next())
			{
				Profile profile = null;
				profile = new Profile();
				profile.setIdProfile(getProfileResultSet.getString(1));
				profile.setIdProfileDesc(getProfileResultSet.getString(2));
				profile.setCdMenuItems(getProfileResultSet.getString(3));

				profile.setTsCreate(new FormatTimestamp().format(getProfileResultSet.getTimestamp(4)));
				profile.setIdWrkrCreate(getProfileResultSet.getString(5));
				profile.setTsUpdate(new FormatTimestamp().format(getProfileResultSet.getTimestamp(6)));
				profile.setIdWrkrUpdate(getProfileResultSet.getString(7));
				profiles.addElement(profile);
			}
		} catch (SQLException se)
		{
			throw new BusinessLogicException(se.getMessage());
		} finally
		{
			if (getProfileResultSet != null)
				getProfileResultSet.close();
			if (getProfileStatement != null)
				getProfileStatement.close();
			closeConnection(connection);
		}
		return profiles;
	}

	public Vector getAllUserProfiles() throws BusinessLogicException, SQLException
	{

		Connection connection = null;
		Statement getProfileStatement = null;
		ResultSet getProfileResultSet = null;
		Vector profiles = new Vector();

		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);
			getProfileStatement = connection.createStatement();
			String query = "SELECT ID_PROFILE, ID_PROFILE_DESC , CD_MENU_ITEMS, TS_CREATE, ID_WRKR_CREATE, TS_UPDATE, ID_WRKR_UPDATE " + "  FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_PROFILE + " ";
			System.out.println("UserProfileManager.getAllUserProfiles " + query);
			getProfileResultSet = getProfileStatement.executeQuery(query);
			while (getProfileResultSet.next())
			{
				Profile profile = null;
				profile = new Profile();
				profile.setIdProfile(getProfileResultSet.getString(1));
				profile.setIdProfileDesc(getProfileResultSet.getString(2));
				profile.setCdMenuItems(getProfileResultSet.getString(3));
				profile.setTsCreate(new FormatTimestamp().format(getProfileResultSet.getTimestamp(4)));
				profile.setIdWrkrCreate(getProfileResultSet.getString(5));
				profile.setTsUpdate(new FormatTimestamp().format(getProfileResultSet.getTimestamp(6)));
				profile.setIdWrkrUpdate(getProfileResultSet.getString(7));
				profiles.addElement(profile);
			}
		} catch (SQLException se)
		{
			throw new BusinessLogicException(se.getMessage());
		} finally
		{
			if (getProfileResultSet != null)
				getProfileResultSet.close();
			if (getProfileStatement != null)
				getProfileStatement.close();
			closeConnection(connection);
		}
		return profiles;
	}

	public Profile getProfile(String idProfile) throws BusinessLogicException, SQLException
	{

		Connection connection = null;
		Statement getProfileStatement = null;
		ResultSet getProfileResultSet = null;
		Profile profile = null;;

		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);
			getProfileStatement = connection.createStatement();
			String query = "SELECT ID_PROFILE, ID_PROFILE_DESC , CD_MENU_ITEMS, TS_CREATE, ID_WRKR_CREATE, TS_UPDATE, ID_WRKR_UPDATE " + "  FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_PROFILE + " " + " WHERE ID_PROFILE = '" + idProfile + "'";
			System.out.println("UserProfileManager.getProfile " + query);
			getProfileResultSet = getProfileStatement.executeQuery(query);
			if (getProfileResultSet.next())
			{
				profile = new Profile();
				profile.setIdProfile(getProfileResultSet.getString(1));
				profile.setIdProfileDesc(getProfileResultSet.getString(2));
				profile.setCdMenuItems(getProfileResultSet.getString(3));
				profile.setTsCreate(new FormatTimestamp().format(getProfileResultSet.getTimestamp(4)));
				profile.setIdWrkrCreate(getProfileResultSet.getString(5));
				profile.setTsUpdate(new FormatTimestamp().format(getProfileResultSet.getTimestamp(6)));
				profile.setIdWrkrUpdate(getProfileResultSet.getString(7));
			}
		} catch (SQLException se)
		{
			throw new BusinessLogicException(se.getMessage());
		} finally
		{
			if (getProfileResultSet != null)
				getProfileResultSet.close();
			if (getProfileStatement != null)
				getProfileStatement.close();
			closeConnection(connection);
		}
		return profile;
	}

	public void createProfile(String idProfile, String idProfileDesc, String cdMenuItems, String idWorker) throws BusinessLogicException, DuplicateException, SQLException
	{

		Connection connection = null;
		Profile profile = null;;

		String INSERT_SQL = "INSERT INTO " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_PROFILE + " (ID_PROFILE, ID_PROFILE_DESC, CD_MENU_ITEMS, TS_CREATE, ID_WRKR_CREATE, TS_UPDATE, ID_WRKR_UPDATE) VALUES (?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement ps = null;
		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(INSERT_SQL);

			java.sql.Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());

			ps.setString(1, idProfile);
			ps.setString(2, idProfileDesc);
			ps.setString(3, cdMenuItems);
			ps.setTimestamp(4, ts);
			ps.setString(5, idWorker);
			ps.setTimestamp(6, ts);
			ps.setString(7, idWorker);
			ps.executeUpdate();
		} catch (SQLException se)
		{
			int sqlcode = se.getErrorCode();
			if (sqlcode == -803)
			{
				throw new DuplicateException(se.getMessage());
			}
			throw new BusinessLogicException(se.getMessage());
		} finally
		{
			if (ps != null)
				ps.close();

			closeConnection(connection);
		}
	}

	public void updateProfile(String idProfile, String idProfileDesc, String cdMenuItems, String idWorker) throws BusinessLogicException, SQLException
	{

		Connection connection = null;
		Profile profile = null;;

		String UPDATE_PROFILE_SQL = "UPDATE " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_PROFILE + " SET ID_PROFILE_DESC = ?, CD_MENU_ITEMS = ?, TS_UPDATE = ?, ID_WRKR_UPDATE = ? WHERE ID_PROFILE = ?";

		PreparedStatement ps = null;
		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(UPDATE_PROFILE_SQL);

			java.sql.Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());

			ps.setString(1, idProfileDesc);
			ps.setString(2, cdMenuItems);
			ps.setTimestamp(3, ts);
			ps.setString(4, idWorker);
			ps.setString(5, idProfile);
			ps.executeUpdate();
		} catch (SQLException se)
		{
			throw new BusinessLogicException(se.getMessage());
		} finally
		{
			if (ps != null)
				ps.close();

			closeConnection(connection);
		}
	}

}
