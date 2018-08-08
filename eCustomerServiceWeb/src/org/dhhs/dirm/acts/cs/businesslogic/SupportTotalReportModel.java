

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
import org.dhhs.dirm.acts.cs.beans.LocalSupportBean;

/**
 * SupportTotalReportModel.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: May 16, 2004 11:45:00 AM
 * 
 * @author Rkodumagulla
 *
 */

public class SupportTotalReportModel
{

	private static SupportTotalReportModel	reportModel	= null;
	private static Context					ctx			= null;
	private static DataSource				dataSource	= null;
	private static String					user		= null;
	private static String					password	= null;

	public SupportTotalReportModel() throws BusinessLogicException
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

	public static SupportTotalReportModel getInstance() throws BusinessLogicException
	{

		if (null == reportModel)
		{
			reportModel = new SupportTotalReportModel();
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
		/*
		 * String query =
		 * "SELECT MONTH(A.TS_CREATE) AS C_MONTH, YEAR(A.TS_CREATE) AS C_YEAR, A.CD_RFRL, COUNT(*) "
		 * + "	FROM " + PropertyManager.getWebRegion() +
		 * Constants.TABLE_CSESRV_FORMS + " A, " + "        " +
		 * PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK +
		 * " B " + "  WHERE DATE(A.TS_CREATE) BETWEEN ? AND ? " +
		 * "    AND B.ID_REFERENCE = A.ID_REFERENCE " +
		 * "    AND B.CD_STAT = A.CD_STAT " +
		 * " GROUP BY MONTH(A.TS_CREATE), YEAR(A.TS_CREATE), A.CD_RFRL" +
		 * "  ORDER BY C_MONTH, C_YEAR, A.CD_RFRL " ;
		 */
		String query = "SELECT MO_CREATE AS C_MONTH, YR_CREATE AS C_YEAR, CD_RFRL, COUNT(*) " + "	FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + "  WHERE DATE(TS_CREATE) BETWEEN ? AND ? " + " GROUP BY MO_CREATE, YR_CREATE, CD_RFRL" + "  ORDER BY C_MONTH, C_YEAR, CD_RFRL ";

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
				LocalSupportBean lsb = new LocalSupportBean();
				lsb.setMonth(rs.getInt(1));
				lsb.setYear(rs.getInt(2));
				lsb.setType(rs.getString(3));
				lsb.setTotal(rs.getInt(4));
				results.addElement(lsb);
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
