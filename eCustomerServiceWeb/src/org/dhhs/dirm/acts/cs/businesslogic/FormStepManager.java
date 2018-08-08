

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
import org.dhhs.dirm.acts.cs.formbeans.ReferralProcess;
import org.dhhs.dirm.acts.cs.helpers.FormatTimestamp;

/**
 * FormStepManager
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Nov 3, 2003 3:44:00 PM
 * 
 * @author Rkodumagulla
 *
 */
public class FormStepManager
{

	/**
	 *
	 */
	private static FormStepManager	formStepManager	= null;
	private static Context			ctx				= null;
	private static DataSource		dataSource		= null;
	private static String			user			= null;
	private static String			password		= null;

	/**
	 * Constructor for UserRegistry.
	 */
	public FormStepManager() throws BusinessLogicException
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
	public static FormStepManager getInstance() throws BusinessLogicException
	{

		if (null == formStepManager)
		{
			formStepManager = new FormStepManager();
		}
		return formStepManager;
	}

	public Vector getAllFormSteps(String type) throws BusinessLogicException, SQLException
	{

		Connection connection = null;
		Statement getFormStatement = null;
		ResultSet getFormResultSet = null;

		Vector forms = new Vector();

		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);
			getFormStatement = connection.createStatement();
			String query = "SELECT CD_RFRL, CD_PRCS, NB_SEQ, NB_DUCATION, TS_CREATE, ID_WRKR_CREATE, TS_UPDATE, ID_WRKR_UPDATE " + "  FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_REFERRAL_PROCESS + "";
			getFormResultSet = getFormStatement.executeQuery(query);
			while (getFormResultSet.next())
			{
				ReferralProcess form = new ReferralProcess();
				form.setType(getFormResultSet.getString(1));
				form.setStep(getFormResultSet.getString(2));
				form.setSeq(getFormResultSet.getShort(3));
				form.setDuration(getFormResultSet.getShort(4));
				form.setTsCreate(new FormatTimestamp().format(getFormResultSet.getTimestamp(5)));
				form.setWrkrCreate(getFormResultSet.getString(6));
				form.setTsUpdate(new FormatTimestamp().format(getFormResultSet.getTimestamp(7)));
				form.setWrkrUpdate(getFormResultSet.getString(8));
				forms.addElement(form);
			}
		} catch (SQLException se)
		{
			throw new BusinessLogicException(se.getMessage());
		} finally
		{
			if (getFormResultSet != null)
				getFormResultSet.close();
			if (getFormStatement != null)
				getFormStatement.close();

			closeConnection(connection);
		}
		return forms;
	}

	public ReferralProcess getStep(String type, String step) throws BusinessLogicException, SQLException
	{

		Connection connection = null;
		Statement getFormStatement = null;
		ResultSet getFormResultSet = null;

		ReferralProcess form = null;

		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);
			getFormStatement = connection.createStatement();

			String query = "SELECT CD_RFRL, CD_PRCS, NB_SEQ, NB_DURATION, TS_CREATE, ID_WRKR_CREATE, TS_UPDATE, ID_WRKR_UPDATE " + "  FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_REFERRAL_PROCESS + "" + " WHERE CD_RFRL = '" + type + "'" + "   AND CD_PRCS = '" + step + "'";

			getFormResultSet = getFormStatement.executeQuery(query);

			if (getFormResultSet.next())
			{
				form = new ReferralProcess();
				form.setType(getFormResultSet.getString(1));
				form.setStep(getFormResultSet.getString(2));
				form.setSeq(getFormResultSet.getShort(3));
				form.setDuration(getFormResultSet.getShort(4));
				form.setTsCreate(new FormatTimestamp().format(getFormResultSet.getTimestamp(5)));
				form.setWrkrCreate(getFormResultSet.getString(6));
				form.setTsUpdate(new FormatTimestamp().format(getFormResultSet.getTimestamp(7)));
				form.setWrkrUpdate(getFormResultSet.getString(8));
			}
		} catch (SQLException se)
		{
			throw new BusinessLogicException(se.getMessage());
		} finally
		{
			if (getFormResultSet != null)
				getFormResultSet.close();
			if (getFormStatement != null)
				getFormStatement.close();

			closeConnection(connection);
		}
		return form;
	}

	public void updateFormStep(ReferralProcess formStep, String idWorker) throws BusinessLogicException, SQLException
	{

		Connection connection = null;
		Profile profile = null;;

		String UPDATE_FORM_SQL = "UPDATE " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_REFERRAL_PROCESS + " SET NB_SEQ = ?, NB_DURATION = ?, TS_UPDATE = ?, ID_WRKR_UPDATE = ? WHERE CD_RFRL = ? AND CD_PRCS = ?";

		PreparedStatement ps = null;
		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(UPDATE_FORM_SQL);

			java.sql.Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());

			ps.setShort(1, (short) formStep.getSeq());
			ps.setShort(2, (short) formStep.getDuration());
			ps.setTimestamp(3, ts);
			ps.setString(4, idWorker);
			ps.setString(5, formStep.getType());
			ps.setString(6, formStep.getStep());
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

	public void deleteFormStep(String type, String step) throws BusinessLogicException, SQLException
	{

		Connection connection = null;

		String DELETE_FORMSTEPS_SQL = "DELETE FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_REFERRAL_PROCESS + " WHERE CD_RFRL = ? AND CD_PRCS = ?";

		PreparedStatement ps = null;
		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(DELETE_FORMSTEPS_SQL);
			ps.setString(1, type);
			ps.setString(2, step);
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

	public void deleteAllSteps(String type) throws BusinessLogicException, SQLException
	{

		Connection connection = null;

		String DELETE_FORMSTEPS_SQL = "DELETE FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_REFERRAL_PROCESS + " WHERE CD_RFRL = ?";

		PreparedStatement ps = null;
		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(DELETE_FORMSTEPS_SQL);
			ps.setString(1, type);
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
