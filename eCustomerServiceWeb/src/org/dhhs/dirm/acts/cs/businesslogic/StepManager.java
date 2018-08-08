

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
import org.dhhs.dirm.acts.cs.beans.ProcessType;
import org.dhhs.dirm.acts.cs.helpers.FormatTimestamp;

/**
 * StepManager
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Aug 18, 2003 9:59:00 AM
 * 
 * @author Rkodumagulla
 *
 */
public class StepManager
{

	/**
	 *
	 */
	private static StepManager	stepManager	= null;
	private static Context		ctx			= null;
	private static DataSource	dataSource	= null;
	private static String		user		= null;
	private static String		password	= null;

	/**
	 * Constructor for UserRegistry.
	 */
	public StepManager() throws BusinessLogicException
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
	public static StepManager getInstance() throws BusinessLogicException
	{

		if (null == stepManager)
		{
			stepManager = new StepManager();
		}
		return stepManager;
	}

	public Vector getAllSteps() throws BusinessLogicException, SQLException
	{

		Connection connection = null;
		Statement getstepstatement = null;
		ResultSet getStepResultSet = null;

		Vector steps = new Vector();

		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);
			getstepstatement = connection.createStatement();
			String query = "SELECT CD_PRCS, CD_DESC, TS_CREATE, ID_WRKR_CREATE, TS_UPDATE, ID_WRKR_UPDATE " + "  FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_PROCESS + " ORDER BY CD_PRCS";
			getStepResultSet = getstepstatement.executeQuery(query);
			while (getStepResultSet.next())
			{
				ProcessType step = new ProcessType();
				step.setStep(getStepResultSet.getString(1));
				step.setDescription(getStepResultSet.getString(2));
				step.setTsCreate(new FormatTimestamp().format(getStepResultSet.getTimestamp(3)));
				step.setWrkrCreate(getStepResultSet.getString(4));
				step.setTsUpdate(new FormatTimestamp().format(getStepResultSet.getTimestamp(5)));
				step.setWrkrUpdate(getStepResultSet.getString(6));
				steps.addElement(step);
			}
		} catch (SQLException se)
		{
			throw new BusinessLogicException(se.getMessage());
		} finally
		{
			if (getStepResultSet != null)
				getStepResultSet.close();
			if (getstepstatement != null)
				getstepstatement.close();

			closeConnection(connection);
		}
		return steps;
	}

	public ProcessType getStep(String type) throws BusinessLogicException, SQLException
	{

		Connection connection = null;
		Statement getstepstatement = null;
		ResultSet getStepResultSet = null;

		ProcessType step = null;;

		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);
			getstepstatement = connection.createStatement();
			String query = "SELECT CD_PRCS, CD_DESC , TS_CREATE, ID_WRKR_CREATE, TS_UPDATE, ID_WRKR_UPDATE " + "  FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_PROCESS + " " + " WHERE CD_PRCS = '" + type + "'";
			getStepResultSet = getstepstatement.executeQuery(query);
			if (getStepResultSet.next())
			{
				step = new ProcessType();
				step.setStep(getStepResultSet.getString(1));
				step.setDescription(getStepResultSet.getString(2));
				step.setTsCreate(new FormatTimestamp().format(getStepResultSet.getTimestamp(3)));
				step.setWrkrCreate(getStepResultSet.getString(4));
				step.setTsUpdate(new FormatTimestamp().format(getStepResultSet.getTimestamp(5)));
				step.setWrkrUpdate(getStepResultSet.getString(6));
			}
		} catch (SQLException se)
		{
			throw new BusinessLogicException(se.getMessage());
		} finally
		{
			if (getStepResultSet != null)
				getStepResultSet.close();
			if (getstepstatement != null)
				getstepstatement.close();
			closeConnection(connection);
		}
		return step;
	}

	public void createStepType(String type, String desc, String idWorker) throws BusinessLogicException, DuplicateException, SQLException
	{

		Connection connection = null;

		String INSERT_SQL = "INSERT INTO " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_PROCESS + " (CD_PRCS, CD_DESC, TS_CREATE, ID_WRKR_CREATE, TS_UPDATE, ID_WRKR_UPDATE) VALUES (?, ?, ?, ?, ?, ?)";

		PreparedStatement ps = null;
		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(INSERT_SQL);

			java.sql.Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());

			ps.setString(1, type);
			ps.setString(2, desc);
			ps.setTimestamp(3, ts);
			ps.setString(4, idWorker);
			ps.setTimestamp(5, ts);
			ps.setString(6, idWorker);
			int result = ps.executeUpdate();
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

	public void updateStepType(String type, String desc, String idWorker) throws BusinessLogicException, SQLException
	{

		Connection connection = null;

		String UPDATE_STEP_SQL = "UPDATE " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_PROCESS + " SET CD_DESC = ?, TS_UPDATE = ?, ID_WRKR_UPDATE = ? WHERE CD_PRCS = ?";

		PreparedStatement ps = null;
		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(UPDATE_STEP_SQL);

			java.sql.Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());

			ps.setString(1, desc);
			ps.setTimestamp(2, ts);
			ps.setString(3, idWorker);
			ps.setString(4, type);
			int result = ps.executeUpdate();
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
