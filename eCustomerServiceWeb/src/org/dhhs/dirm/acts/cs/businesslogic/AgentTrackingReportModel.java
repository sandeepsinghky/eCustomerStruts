

package org.dhhs.dirm.acts.cs.businesslogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.naming.Context;
import javax.sql.DataSource;

import org.dhhs.dirm.acts.cs.Constants;
import org.dhhs.dirm.acts.cs.DBConnectManager;
import org.dhhs.dirm.acts.cs.PropertyManager;
import org.dhhs.dirm.acts.cs.beans.TimeframeTrackingBean;
import org.dhhs.dirm.acts.cs.helpers.FormatTimestamp;

/**
 * AgentTrackingReportModel.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: May 14, 2004 04:45:00 PM
 * 
 * @author Rkodumagulla
 *
 */

public class AgentTrackingReportModel
{

	private static AgentTrackingReportModel	reportModel	= null;
	private static Context					ctx			= null;
	private static DataSource				dataSource	= null;
	private static String					user		= null;
	private static String					password	= null;

	public AgentTrackingReportModel() throws BusinessLogicException
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

	public static AgentTrackingReportModel getInstance() throws BusinessLogicException
	{

		if (null == reportModel)
		{
			reportModel = new AgentTrackingReportModel();
		}
		return reportModel;
	}

	public Vector getData(String fromDt, String toDt) throws BusinessLogicException, SQLException
	{

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		Vector results = new Vector();

		java.util.Date fromDate = null;
		java.sql.Date frDT = null;

		java.util.Date toDate = null;
		java.sql.Date toDT = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if (fromDt != null)
		{
			try
			{
				fromDate = sdf.parse(fromDt);
				frDT = new java.sql.Date(fromDate.getTime());
			} catch (ParseException pe)
			{
				throw new BusinessLogicException(pe.getMessage());
			}

		} else
		{
			fromDate = java.util.Calendar.getInstance().getTime();
			frDT = new java.sql.Date(fromDate.getTime());
		}

		if (toDt != null)
		{
			try
			{
				toDate = sdf.parse(toDt);
				toDT = new java.sql.Date(toDate.getTime());
			} catch (ParseException pe)
			{
				throw new BusinessLogicException(pe.getMessage());
			}
		} else
		{
			toDate = java.util.Calendar.getInstance().getTime();
			toDT = new java.sql.Date(toDate.getTime());
		}

		String query = "SELECT B.ID_WRKR_ASSIGN, B.ID_REFERENCE, A.CD_RFRL, A.TS_CREATE, B.TS_CREATE, " + " (DATE(B.TS_CREATE) - DATE(A.TS_ASSIGNED)) AS NBR_DAYS, A.TS_ASSIGNED " + "	FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "        " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " B " + "  WHERE DATE(A.TS_CREATE) BETWEEN ? AND ? " + "    AND A.CD_STAT  = 'CLSD' " + "    AND B.ID_REFERENCE = A.ID_REFERENCE " + "    AND B.CD_STAT = A.CD_STAT " + "  ORDER BY ID_WRKR_ASSIGN, ID_REFERENCE ";

		String SELECT_WRKR_SQL = "SELECT NM_WRKR_F, NM_WRKR_MI, NM_WRKR_L " + "	FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " " + "  WHERE ID_WRKR = ? ";

		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(query);
			ps.setDate(1, frDT);
			ps.setDate(2, toDT);
			rs = ps.executeQuery();
			while (rs.next())
			{
				TimeframeTrackingBean tftb = new TimeframeTrackingBean();
				tftb.setIdWorker(rs.getString(1));
				tftb.setIdReference(rs.getString(2));
				tftb.setCdType(rs.getString(3));
				tftb.setDtCreated(new FormatTimestamp().format(rs.getTimestamp(4)));
				tftb.setDtCompleted(new FormatTimestamp().format(rs.getTimestamp(5)));

				int duration = new Double(rs.getDouble(6)).intValue();
				if (duration == 0)
				{
					duration = 1;
				}

				tftb.setDuration("" + duration + "");
				tftb.setDtAssigned(new FormatTimestamp().format(rs.getTimestamp(7)));

				PreparedStatement ps1 = connection.prepareStatement(SELECT_WRKR_SQL);
				ps1.setString(1, tftb.getIdWorker());
				ResultSet rs1 = ps1.executeQuery();
				if (rs1.next())
				{
					tftb.setWrkrNameFirst(rs1.getString(1));
					tftb.setWrkrNameMiddle(rs1.getString(2));
					tftb.setWrkrNameLast(rs1.getString(3));
				}
				rs1.close();
				ps1.close();
				results.addElement(tftb);
			}
		} catch (SQLException se)
		{
			throw se;
		} finally
		{
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();

			closeConnection(connection);
		}
		return results;
	}
}
