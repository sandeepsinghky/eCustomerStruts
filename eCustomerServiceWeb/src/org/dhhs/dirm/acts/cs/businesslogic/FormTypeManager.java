

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
import org.dhhs.dirm.acts.cs.beans.ReferralType;
import org.dhhs.dirm.acts.cs.formbeans.ReferralProcess;
import org.dhhs.dirm.acts.cs.helpers.FormatTimestamp;

/**
 * FprmTypeManager
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Aug 18, 2003 9:59:00 AM
 * 
 * @author Rkodumagulla
 *
 */
public class FormTypeManager
{

	/**
	 *
	 */
	private static FormTypeManager	formTypeManager	= null;
	private static Context			ctx				= null;
	private static DataSource		dataSource		= null;
	private static String			user			= null;
	private static String			password		= null;

	/**
	 * Constructor for UserRegistry.
	 */
	public FormTypeManager() throws BusinessLogicException
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
	public static FormTypeManager getInstance() throws BusinessLogicException
	{

		if (null == formTypeManager)
		{
			formTypeManager = new FormTypeManager();
		}
		return formTypeManager;
	}

	public Vector getAllFormTypes() throws BusinessLogicException, SQLException
	{

		Connection connection = null;
		Statement getFormStatement = null;
		ResultSet getFormResultSet = null;
		ResultSet rs = null;

		Vector forms = new Vector();

		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);

			getFormStatement = connection.createStatement();
			String query = "SELECT CD_RFRL, CD_DESC, CD_CORRESPONDENCE, TS_CREATE, ID_WRKR_CREATE, TS_UPDATE, ID_WRKR_UPDATE " + "  FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_REFERRAL;
			getFormResultSet = getFormStatement.executeQuery(query);

			while (getFormResultSet.next())
			{
				ReferralType form = new ReferralType();
				form.setType(getFormResultSet.getString(1));
				form.setDescription(getFormResultSet.getString(2));
				form.setGenerateCorrespondence(getFormResultSet.getString(3).equals("Y") ? true : false);
				form.setTsCreate(new FormatTimestamp().format(getFormResultSet.getTimestamp(4)));
				form.setWrkrCreate(getFormResultSet.getString(5));
				form.setTsUpdate(new FormatTimestamp().format(getFormResultSet.getTimestamp(6)));
				form.setWrkrUpdate(getFormResultSet.getString(7));
				forms.addElement(form);
			}
			getFormResultSet.close();
			getFormStatement.close();

			for (int i = 0; i < forms.size(); i++)
			{
				Vector vector = new Vector();
				ReferralType form = (ReferralType) forms.elementAt(i);
				getFormStatement = connection.createStatement();
				String frmsteps = "SELECT CD_RFRL, CD_PRCS, NB_SEQ, NB_DURATION, TS_CREATE, ID_WRKR_CREATE, TS_UPDATE, ID_WRKR_UPDATE " + "  FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_REFERRAL_PROCESS + " WHERE CD_RFRL = '" + form.getType() + "'" + " ORDER BY NB_SEQ";
				rs = getFormStatement.executeQuery(frmsteps);
				while (rs.next())
				{
					ReferralProcess formStep = new ReferralProcess();
					formStep.setType(rs.getString(1));
					formStep.setStep(rs.getString(2));
					formStep.setSeq(rs.getShort(3));
					formStep.setDuration(rs.getShort(4));
					formStep.setTsCreate(new FormatTimestamp().format(rs.getTimestamp(5)));
					formStep.setWrkrCreate(rs.getString(6));
					formStep.setTsUpdate(new FormatTimestamp().format(rs.getTimestamp(7)));
					formStep.setWrkrUpdate(rs.getString(8));
					vector.addElement(formStep);
				}
				form.setFormSteps(vector);
			}
		} catch (SQLException se)
		{
			throw new BusinessLogicException(se.getMessage());
		} finally
		{
			if (rs != null)
				rs.close();
			if (getFormResultSet != null)
				getFormResultSet.close();
			if (getFormStatement != null)
				getFormStatement.close();

			closeConnection(connection);
		}
		return forms;
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
			String query = "SELECT CD_RFRL, CD_PRCS, NB_SEQ, NB_DURPropertyManager.getWebRegion()ATION, TS_CREATE, ID_WRKR_CREATE, TS_UPDATE, ID_WRKR_UPDATE " + "  FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_REFERRAL_PROCESS + " WHERE CD_RFRL = '" + type + "'";
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

	public ReferralType getForm(String type) throws BusinessLogicException, SQLException
	{

		Connection connection = null;
		Statement getFormStatement = null;
		ResultSet getFormResultSet = null;

		ReferralType form = null;

		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);
			getFormStatement = connection.createStatement();
			String query = "SELECT CD_RFRL, CD_DESC , CD_CORRESPONDENCE, TS_CREATE, ID_WRKR_CREATE, TS_UPDATE, ID_WRKR_UPDATE " + "  FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_REFERRAL + " " + " WHERE CD_RFRL = '" + type + "'";
			getFormResultSet = getFormStatement.executeQuery(query);
			if (getFormResultSet.next())
			{
				form = new ReferralType();
				form.setType(getFormResultSet.getString(1));
				form.setDescription(getFormResultSet.getString(2));
				form.setGenerateCorrespondence(getFormResultSet.getString(3).equals("Y") ? true : false);
				form.setTsCreate(new FormatTimestamp().format(getFormResultSet.getTimestamp(4)));
				form.setWrkrCreate(getFormResultSet.getString(5));
				form.setTsUpdate(new FormatTimestamp().format(getFormResultSet.getTimestamp(6)));
				form.setWrkrUpdate(getFormResultSet.getString(7));

				Vector vector = new Vector();

				String frmsteps = "SELECT CD_RFRL, CD_PRCS, NB_SEQ, NB_DURATION, TS_CREATE, ID_WRKR_CREATE, TS_UPDATE, ID_WRKR_UPDATE " + "  FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_REFERRAL_PROCESS + " WHERE CD_RFRL = '" + type + "'"
				/**
				 * rk 04/30/04. We do not need to load the INIT & APRV process
				 * status as it is unassignable by the user
				 */
				/*
				 * UAT #35. We need to display all
				 */
				// + " AND CD_PRCS NOT IN ('INIT','OPEN','APRV') "
				+ " ORDER BY NB_SEQ";
				Statement s2 = connection.createStatement();
				ResultSet rs2 = s2.executeQuery(frmsteps);
				while (rs2.next())
				{
					ReferralProcess formStep = new ReferralProcess();
					formStep.setType(type);
					formStep.setStep(rs2.getString(2));
					formStep.setSeq(rs2.getShort(3));
					formStep.setDuration(rs2.getShort(4));
					formStep.setTsCreate(new FormatTimestamp().format(rs2.getTimestamp(5)));
					formStep.setWrkrCreate(rs2.getString(6));
					formStep.setTsUpdate(new FormatTimestamp().format(rs2.getTimestamp(7)));
					formStep.setWrkrUpdate(rs2.getString(8));
					vector.addElement(formStep);
				}
				rs2.close();
				s2.close();
				form.setFormSteps(vector);

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

	public void addStep(ReferralProcess formStep, String idWorker) throws BusinessLogicException, DuplicateException, SQLException
	{

		Connection connection = null;
		String UPDATE_CLSD_SQL = "SELECT NB_SEQ FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_REFERRAL_PROCESS + " WHERE CD_RFRL = '" + formStep.getType() + "' AND CD_PRCS =  'CLSD'";
		String INSERT_SQL = "INSERT INTO " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_REFERRAL_PROCESS + " (CD_RFRL, CD_PRCS, NB_SEQ, NB_DURATION, TS_CREATE, ID_WRKR_CREATE, TS_UPDATE, ID_WRKR_UPDATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement ps = null;
		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);

			ps = connection.prepareStatement(INSERT_SQL);

			java.sql.Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());

			ps.setString(1, formStep.getType());
			ps.setString(2, formStep.getStep());
			ps.setShort(3, (short) formStep.getSeq());
			ps.setShort(4, (short) formStep.getDuration());
			ps.setTimestamp(5, ts);
			ps.setString(6, idWorker);
			ps.setTimestamp(7, ts);
			ps.setString(8, idWorker);
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

	public void addCustomStep(ReferralProcess formStep, String idWorker) throws BusinessLogicException, SQLException
	{

		Connection connection = null;
		String UPDATE_CLSD_SQL = "SELECT NB_SEQ FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_REFERRAL_PROCESS + " WHERE CD_RFRL = '" + formStep.getType() + "' AND CD_PRCS =  'CLSD'";
		String INSERT_SQL = "INSERT INTO " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_REFERRAL_PROCESS + " (CD_RFRL, CD_PRCS, NB_SEQ, NB_DURATION, TS_CREATE, ID_WRKR_CREATE, TS_UPDATE, ID_WRKR_UPDATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement ps = null;
		Statement s = null;
		ResultSet r = null;

		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);

			// Whenever we insert a process type other than init and clsd, we
			// got to push closed down
			if ((!formStep.getStep().equals("INIT")) && (!formStep.getStep().equals("CLSD")))
			{
				s = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				r = s.executeQuery(UPDATE_CLSD_SQL);
				if (r.next())
				{
					short seq = r.getShort(1);
					seq++;
					r.updateShort("NB_SEQ", seq);
					r.updateRow();
				}
				r.close();
				s.close();
			}

			ps = connection.prepareStatement(INSERT_SQL);

			java.sql.Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());

			ps.setString(1, formStep.getType());
			ps.setString(2, formStep.getStep());
			ps.setShort(3, (short) formStep.getSeq());
			ps.setShort(4, (short) formStep.getDuration());
			ps.setTimestamp(5, ts);
			ps.setString(6, idWorker);
			ps.setTimestamp(7, ts);
			ps.setString(8, idWorker);
			ps.executeUpdate();
		} catch (SQLException se)
		{
			throw new BusinessLogicException(se.getMessage());
		} finally
		{
			if (r != null)
				r.close();
			if (ps != null)
				ps.close();
			if (s != null)
				s.close();

			closeConnection(connection);
		}
	}

	public void createFormType(String type, String desc, boolean createCorrespondence, String idWorker) throws BusinessLogicException, DuplicateException, SQLException
	{

		Connection connection = null;

		String INSERT_SQL = "INSERT INTO " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_REFERRAL + " (CD_RFRL, CD_DESC, CD_CORRESPONDENCE, TS_CREATE, ID_WRKR_CREATE, TS_UPDATE, ID_WRKR_UPDATE) VALUES (?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement ps = null;
		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(INSERT_SQL);

			java.sql.Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());

			ps.setString(1, type);
			ps.setString(2, desc);
			ps.setString(3, createCorrespondence ? "Y" : "N");
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

	public void updateFormType(String type, String desc, boolean createCorrespondence, String idWorker) throws BusinessLogicException, SQLException
	{

		Connection connection = null;

		String UPDATE_FORM_SQL = "UPDATE " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_REFERRAL + " SET CD_DESC = ?, TS_UPDATE = ?, CD_CORRESPONDENCE = ?, ID_WRKR_UPDATE = ? WHERE CD_RFRL = ?";

		PreparedStatement ps = null;
		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(UPDATE_FORM_SQL);

			java.sql.Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());

			ps.setString(1, desc);
			ps.setTimestamp(2, ts);
			ps.setString(3, createCorrespondence ? "Y" : "N");
			ps.setString(4, idWorker);
			ps.setString(5, type);
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

	public void deleteFormType(String type) throws BusinessLogicException, SQLException
	{

		Connection connection = null;

		String DELETE_FORM_SQL = "DELETE FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_REFERRAL + " WHERE CD_RFRL = ?";
		String DELETE_FORMSTEPS_SQL = "DELETE FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_REFERRAL_PROCESS + " WHERE CD_RFRL = ?";

		PreparedStatement ps = null;
		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(DELETE_FORM_SQL);
			ps.setString(1, type);
			ps.executeUpdate();
			ps.close();

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
