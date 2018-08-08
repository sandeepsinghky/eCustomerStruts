

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

import org.apache.log4j.Logger;
import org.dhhs.dirm.acts.cs.Constants;
import org.dhhs.dirm.acts.cs.DBConnectManager;
import org.dhhs.dirm.acts.cs.PropertyManager;
import org.dhhs.dirm.acts.cs.beans.AgentCorrectionBean;
import org.dhhs.dirm.acts.cs.util.DateFormatUtil;

/**
 * CorrectionReportModel.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Mar 31, 2004 04:45:00 PM
 * 
 * @author Rkodumagulla
 *
 */

public class CorrectionReportModel
{

	private static CorrectionReportModel	reportModel	= null;
	private static Context					ctx			= null;
	private static DataSource				dataSource	= null;
	private static String					user		= null;
	private static String					password	= null;

	private final static Logger				log			= Logger.getLogger(CorrectionReportModel.class);

	public CorrectionReportModel() throws BusinessLogicException
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

	public static CorrectionReportModel getInstance() throws BusinessLogicException
	{

		if (null == reportModel)
		{
			reportModel = new CorrectionReportModel();
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

		String query = "SELECT B.ID_WRKR_ASSIGN, B.ID_REFERENCE, B.CD_RESOLUTION, COUNT(*)" + "	FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "        " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " B " + "  WHERE DATE(A.TS_CREATE) BETWEEN ? AND ? " + "    AND B.CD_RESOLUTION <> '' " + "    AND B.ID_REFERENCE = A.ID_REFERENCE " + "  GROUP BY B.ID_WRKR_ASSIGN, B.ID_REFERENCE, B.CD_RESOLUTION " + "  HAVING COUNT(*) > 0 " + "  ORDER BY ID_WRKR_ASSIGN, ID_REFERENCE ";

		String SELECT_WRKR_SQL = "SELECT NM_WRKR_F, NM_WRKR_MI, NM_WRKR_L " + "	FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " " + "  WHERE ID_WRKR = ? ";

		String SELECT_STDATE_SQL = "SELECT DATE(TS_CREATE)" + "	FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " WHERE ID_REFERENCE = ? " + "   AND CD_STAT = 'INIT'";

		String SELECT_ENDDATE_SQL = "SELECT DATE(TS_WRKR_END)" + "	FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " WHERE ID_REFERENCE = ? " + "   AND CD_STAT = 'CLSD'";

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
				log.info("Correction Report Model selecting record with ID_REFERENCE " + rs.getString(2));
				AgentCorrectionBean acb = new AgentCorrectionBean();

				PreparedStatement ps2 = connection.prepareStatement(SELECT_STDATE_SQL);
				ps2.setString(1, rs.getString(2));
				ResultSet rs2 = ps2.executeQuery();
				if (rs2.next())
				{
					acb.setDtCreated(new DateFormatUtil().format(rs2.getDate(1).toString(), 1));
				}
				rs2.close();
				ps2.close();

				ps2 = connection.prepareStatement(SELECT_ENDDATE_SQL);
				ps2.setString(1, rs.getString(2));
				rs2 = ps2.executeQuery();
				if (rs2.next())
				{
					java.sql.Date dtComplete = rs2.getDate(1);

					if (dtComplete.toString().equals("1969-12-31"))
					{
						acb.setDtCompleted("");
					} else
					{
						acb.setDtCompleted(new DateFormatUtil().format(dtComplete.toString(), 1));
					}
				} else
				{
					acb.setDtCompleted("");
				}
				rs2.close();
				ps2.close();

				acb.setIdWorker(rs.getString(1));
				acb.setIdReference(rs.getString(2));
				acb.setCdResolution(rs.getString(3));
				acb.setCount("" + rs.getInt(4) + "");

				PreparedStatement ps1 = connection.prepareStatement(SELECT_WRKR_SQL);
				ps1.setString(1, acb.getIdWorker());
				ResultSet rs1 = ps1.executeQuery();
				if (rs1.next())
				{
					acb.setWrkrNameFirst(rs1.getString(1));
					acb.setWrkrNameMiddle(rs1.getString(2));
					acb.setWrkrNameLast(rs1.getString(3));
				}
				rs1.close();
				ps1.close();

				results.addElement(acb);
			}
		} catch (SQLException se)
		{
			throw new BusinessLogicException(se.getMessage());
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
