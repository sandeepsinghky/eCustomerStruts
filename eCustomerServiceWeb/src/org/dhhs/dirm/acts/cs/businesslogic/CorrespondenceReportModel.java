

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
import org.dhhs.dirm.acts.cs.beans.CorrespondenceBean;
import org.dhhs.dirm.acts.cs.util.BusinessCalendar;
import org.dhhs.dirm.acts.cs.util.DateFormatUtil;

/**
 * CorrespondenceReportModel.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Mar 29, 2004 11:45:00 AM
 * 
 * @author Rkodumagulla
 *
 */

public class CorrespondenceReportModel
{

	/**
	 *
	 */
	private static CorrespondenceReportModel	reportModel			= null;
	private static Context						ctx					= null;
	private static DataSource					dataSource			= null;
	private static String						user				= null;
	private static String						password			= null;

	final String								SELECT_DUEDATE_SQL	=
	                                                                                                                                                                                                            /*
	                                                                                                                                                                                                             * "SELECT SUM(NB_DURATION) FROM "
	                                                                                                                                                                                                             * +
	                                                                                                                                                                                                             * PropertyManager
	                                                                                                                                                                                                             * .
	                                                                                                                                                                                                             * getWebRegion
	                                                                                                                                                                                                             * (
	                                                                                                                                                                                                             * )
	                                                                                                                                                                                                             * +
	                                                                                                                                                                                                             * Constants
	                                                                                                                                                                                                             * .
	                                                                                                                                                                                                             * TABLE_CSESRV_REFERRAL_PROCESS
	                                                                                                                                                                                                             * +
	                                                                                                                                                                                                             * " "
	                                                                                                                                                                                                             * +
	                                                                                                                                                                                                             * " WHERE CD_RFRL = ? "
	                                                                                                                                                                                                             * +
	                                                                                                                                                                                                             * "   AND CD_PRCS = ? "
	                                                                                                                                                                                                             * ;
	                                                                                                                                                                                                             */
	                                                                "SELECT SUM(NB_DURATION) FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_REFERRAL_PROCESS + " " + " WHERE CD_RFRL = ? ";

	/**
	 * Constructor for UserRegistry.
	 */
	public CorrespondenceReportModel() throws BusinessLogicException
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
	public static CorrespondenceReportModel getInstance() throws BusinessLogicException
	{

		if (null == reportModel)
		{
			reportModel = new CorrespondenceReportModel();
		}
		return reportModel;
	}

	public Vector getAllCorrespondence(String fromDt, String toDt) throws BusinessLogicException, SQLException
	{
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		Vector results = new Vector();

		java.util.Date fromDate = null;
		java.sql.Date frDT = null;

		java.util.Date toDate = null;
		java.sql.Date toDT = null;

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat sdf1 = new SimpleDateFormat("MMddyyyy");

		if (fromDt != null)
		{
			if (fromDt.length() == 10)
			{
				try
				{
					fromDate = sdf.parse(fromDt);
					frDT = new java.sql.Date(fromDate.getTime());
				} catch (ParseException pe)
				{
					throw new BusinessLogicException(pe.getMessage());
				}
			} else if (fromDt.length() == 8)
			{
				try
				{
					fromDate = sdf1.parse(fromDt);
					frDT = new java.sql.Date(fromDate.getTime());
				} catch (ParseException pe)
				{
					throw new BusinessLogicException(pe.getMessage());
				}
			}
		} else
		{
			fromDate = java.util.Calendar.getInstance().getTime();
			frDT = new java.sql.Date(fromDate.getTime());
		}

		if (toDt != null)
		{
			if (toDt.length() == 10)
			{
				try
				{
					toDate = sdf.parse(toDt);
					toDT = new java.sql.Date(toDate.getTime());
				} catch (ParseException pe)
				{
					throw new BusinessLogicException(pe.getMessage());
				}
			} else if (toDt.length() == 8)
			{
				try
				{
					toDate = sdf1.parse(toDt);
					toDT = new java.sql.Date(toDate.getTime());
				} catch (ParseException pe)
				{
					throw new BusinessLogicException(pe.getMessage());
				}
			}
		} else
		{
			toDate = java.util.Calendar.getInstance().getTime();
			toDT = new java.sql.Date(toDate.getTime());
		}

		String query = "SELECT A.ID_REFERENCE, B.ID_WRKR_ASSIGN, A.CD_STAT, DATE(A.TS_CREATE), A.CD_RFRL, A.NM_CUSTOMER_L, " + "A.NM_CUSTOMER_F, A.NM_CUSTOMER_M , A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, " + "C.NM_WRKR_L, C.NM_WRKR_F , C.NM_WRKR_MI, DATE(B.TS_WRKR_START), DATE(B.TS_WRKR_END), DATE(A.TS_ASSIGNED) " + "	FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "        " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " B, " + "        " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " C " + "  WHERE DATE(A.TS_CREATE) BETWEEN ? AND ? " + "    AND B.ID_REFERENCE = A.ID_REFERENCE " + "    AND B.CD_STAT = A.CD_STAT " + "    AND C.ID_WRKR = B.ID_WRKR_ASSIGN " + "    AND B.TS_CREATE = (SELECT MAX(D.TS_CREATE) " + "                         FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " D " + "                        WHERE D.ID_REFERENCE = A.ID_REFERENCE " + "                          AND D.CD_STAT = A.CD_STAT )" +
		// " ORDER BY ID_WRKR_ASSIGN, ID_REFERENCE " ;
		"  ORDER BY C.NM_WRKR_L, C.NM_WRKR_F , C.NM_WRKR_MI, ID_REFERENCE ";

		String TASK_CLSD_SQL = "SELECT DATE(B.TS_WRKR_END) " + "	FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " B " + "  WHERE B.ID_REFERENCE = ? " + "    AND B.CD_STAT = 'CLSD' ";

		try
		{
			System.out.println("CorrespondenceReportModel getAllCorrespondence query " +query);
			System.out.println("CorrespondenceReportModel getAllCorrespondence TASK_CLSD_SQL " +TASK_CLSD_SQL);
			System.out.println("CorrespondenceReportModel getAllCorrespondence frDT " +frDT + "toDT " +toDT);
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(query);
			ps.setDate(1, frDT);
			ps.setDate(2, toDT);
			rs = ps.executeQuery();
			while (rs.next())
			{
				CorrespondenceBean cb = new CorrespondenceBean();
				cb.setIdReference(rs.getString(1));
				cb.setIdWorker(rs.getString(2));
				cb.setCdReferral(rs.getString(5));
				cb.setCdProcess(rs.getString(3));
				cb.setCustomerNameFirst(rs.getString(7).trim().toUpperCase());
				cb.setCustomerNameLast(rs.getString(6).trim().toUpperCase());
				cb.setCustomerName(rs.getString(7).trim().toUpperCase() + " " + rs.getString(8).trim().toUpperCase() + " " + rs.getString(6).trim().toUpperCase());
				cb.setReferralSource1(rs.getString(9).trim());
				cb.setReferralSource2(rs.getString(10).trim());
				cb.setReferralSource3(rs.getString(11).trim());
				cb.setReferralSource4(rs.getString(12).trim());
				cb.setWrkrName(rs.getString(14).trim() + " " + rs.getString(15).trim() + " " + rs.getString(13).trim());

				cb.setDtCreated(new DateFormatUtil().format(rs.getDate(18).toString(), 1));

				// Get the task completed date

				PreparedStatement ps2 = connection.prepareStatement(TASK_CLSD_SQL);
				ps2.setString(1, rs.getString(1));
				ResultSet rs2 = ps2.executeQuery();
				if (rs2.next())
				{
					java.sql.Date dtComplete = rs2.getDate(1);
					if (dtComplete.toString().equals("1969-12-31"))
					{
						cb.setDtCompleted("");
					} else
					{
						cb.setDtCompleted(new DateFormatUtil().format(dtComplete.toString(), 1));
					}
				} else
				{
					cb.setDtCompleted("");
				}
				rs2.close();
				ps2.close();

				// Get the task due date

				PreparedStatement ps1 = connection.prepareStatement(SELECT_DUEDATE_SQL);
				ps1.setString(1, cb.getCdReferral());
				ResultSet rs1 = ps1.executeQuery();
				if (rs1.next())
				{
					short duration = rs1.getShort(1);
					/*
					 * CT# 527783 - RK 08/24/04 Due date must be computed using
					 * BusinessCalendar class and not just by adding the
					 * duration to create date
					 */

					/*
					 * java.util.Date startDt = new
					 * java.util.Date(rs.getDate(4).getTime());
					 * java.util.Calendar c = java.util.Calendar.getInstance();
					 * c.setTime(startDt); c.add(Calendar.DATE,duration);
					 * 
					 * java.util.Date dueDt = c.getTime(); java.sql.Date dtDue =
					 * new java.sql.Date(dueDt.getTime());
					 * 
					 * if (dtDue.toString().equals("1969-12-31")) {
					 * cb.setDtDue(""); } else { cb.setDtDue(new
					 * DateFormatUtil().format(dtDue.toString(),1)); }
					 */
					java.util.Date startDt = new java.util.Date(rs.getDate(18).getTime());

					java.util.Calendar c = java.util.Calendar.getInstance();
					c.setTime(startDt);

					java.util.Date dueDt = new BusinessCalendar().addBussinessDays(duration, c).getTime();
					java.sql.Date dtDue = new java.sql.Date(dueDt.getTime());

					if (dtDue.toString().equals("1969-12-31"))
					{
						cb.setDtDue("");
					} else
					{
						cb.setDtDue(new DateFormatUtil().format(dtDue.toString(), 1));
					}

				} else
				{
					cb.setDtDue("");
				}
				rs1.close();
				ps1.close();

				results.addElement(cb);
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

	public Vector getIncompleteCorrespondence(String fromDt, String toDt) throws BusinessLogicException, SQLException
	{

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		Vector results = new Vector();

		java.util.Date fromDate = null;
		java.sql.Date frDT = null;

		java.util.Date toDate = null;
		java.sql.Date toDT = null;

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat sdf1 = new SimpleDateFormat("MMddyyyy");

		if (fromDt != null)
		{
			if (fromDt.length() == 10)
			{
				try
				{
					fromDate = sdf.parse(fromDt);
					frDT = new java.sql.Date(fromDate.getTime());
				} catch (ParseException pe)
				{
					throw new BusinessLogicException(pe.getMessage());
				}
			} else if (fromDt.length() == 8)
			{
				try
				{
					fromDate = sdf1.parse(fromDt);
					frDT = new java.sql.Date(fromDate.getTime());
				} catch (ParseException pe)
				{
					throw new BusinessLogicException(pe.getMessage());
				}
			}
		} else
		{
			fromDate = java.util.Calendar.getInstance().getTime();
			frDT = new java.sql.Date(fromDate.getTime());
		}

		if (toDt != null)
		{
			if (toDt.length() == 10)
			{
				try
				{
					toDate = sdf.parse(toDt);
					toDT = new java.sql.Date(toDate.getTime());
				} catch (ParseException pe)
				{
					throw new BusinessLogicException(pe.getMessage());
				}
			} else if (toDt.length() == 8)
			{
				try
				{
					toDate = sdf1.parse(toDt);
					toDT = new java.sql.Date(toDate.getTime());
				} catch (ParseException pe)
				{
					throw new BusinessLogicException(pe.getMessage());
				}
			}
		} else
		{
			toDate = java.util.Calendar.getInstance().getTime();
			toDT = new java.sql.Date(toDate.getTime());
		}

		String query = "SELECT A.ID_REFERENCE, B.ID_WRKR_ASSIGN, A.CD_STAT, DATE(A.TS_CREATE), A.CD_RFRL, A.NM_CUSTOMER_L, " + "A.NM_CUSTOMER_F, A.NM_CUSTOMER_M , A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, " + "C.NM_WRKR_L, C.NM_WRKR_F , C.NM_WRKR_MI, DATE(B.TS_WRKR_START), DATE(B.TS_WRKR_END), DATE(A.TS_ASSIGNED) " + "	FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "        " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " B, " + "        " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " C " + "  WHERE DATE(A.TS_CREATE) BETWEEN ? AND ? " + "    AND B.ID_REFERENCE = A.ID_REFERENCE " + "    AND A.CD_STAT NOT IN ('CLSD','DELT') " + "    AND B.CD_STAT = A.CD_STAT " + "    AND C.ID_WRKR = B.ID_WRKR_ASSIGN " + "    AND B.TS_CREATE = (SELECT MAX(D.TS_CREATE) " + "                         FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " D " + "                        WHERE D.ID_REFERENCE = A.ID_REFERENCE " + "                          AND D.CD_STAT = A.CD_STAT )" +
		// " ORDER BY ID_WRKR_ASSIGN, ID_REFERENCE " ;
		"  ORDER BY C.NM_WRKR_L, C.NM_WRKR_F , C.NM_WRKR_MI, ID_REFERENCE ";

		String TASK_CLSD_SQL = "SELECT DATE(B.TS_WRKR_END) " + "	FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " B " + "  WHERE B.ID_REFERENCE = ? " + "    AND B.CD_STAT = 'CLSD' ";

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
				System.out.println("CorrespondenceReportModel getIncompleteCorrespondence query " +query + "TASK_CLSD_SQL " +TASK_CLSD_SQL);
				CorrespondenceBean cb = new CorrespondenceBean();
				cb.setIdReference(rs.getString(1));
				cb.setIdWorker(rs.getString(2));
				cb.setCdReferral(rs.getString(5));
				cb.setCdProcess(rs.getString(3));
				// cb.setCustomerName(rs.getString(7).trim() + " " +
				// rs.getString(8).trim() + " " + rs.getString(6).trim());
				cb.setCustomerNameFirst(rs.getString(7).trim().toUpperCase());
				cb.setCustomerNameLast(rs.getString(6).trim().toUpperCase());
				cb.setCustomerName(rs.getString(7).trim().toUpperCase() + " " + rs.getString(8).trim().toUpperCase() + " " + rs.getString(6).trim().toUpperCase());
				cb.setReferralSource1(rs.getString(9).trim());
				cb.setReferralSource2(rs.getString(10).trim());
				cb.setReferralSource3(rs.getString(11).trim());
				cb.setReferralSource4(rs.getString(12).trim());
				cb.setWrkrName(rs.getString(14).trim() + " " + rs.getString(15).trim() + " " + rs.getString(13).trim());

				cb.setDtCreated(new DateFormatUtil().format(rs.getDate(18).toString(), 1));

				// Get the task completed date

				PreparedStatement ps2 = connection.prepareStatement(TASK_CLSD_SQL);
				ps2.setString(1, rs.getString(1));
				ResultSet rs2 = ps2.executeQuery();
				if (rs2.next())
				{
					java.sql.Date dtComplete = rs2.getDate(1);
					if (dtComplete.toString().equals("1969-12-31"))
					{
						cb.setDtCompleted("");
					} else
					{
						cb.setDtCompleted(new DateFormatUtil().format(dtComplete.toString(), 1));
					}
				} else
				{
					cb.setDtCompleted("");
				}
				rs2.close();
				ps2.close();

				// Get the task due date

				PreparedStatement ps1 = connection.prepareStatement(SELECT_DUEDATE_SQL);
				ps1.setString(1, cb.getCdReferral());
				ResultSet rs1 = ps1.executeQuery();
				if (rs1.next())
				{
					short duration = rs1.getShort(1);
					/*
					 * CT# 527783 - RK 08/24/04 Due date must be computed using
					 * BusinessCalendar class and not just by adding the
					 * duration to create date
					 */
					/*
					 * java.util.Date startDt = new
					 * java.util.Date(rs.getDate(4).getTime());
					 * 
					 * java.util.Calendar c = java.util.Calendar.getInstance();
					 * c.setTime(startDt); c.add(Calendar.DATE,duration);
					 * 
					 * java.util.Date dueDt = c.getTime(); java.sql.Date dtDue =
					 * new java.sql.Date(dueDt.getTime());
					 * 
					 * if (dtDue.toString().equals("1969-12-31")) {
					 * cb.setDtDue(""); } else { cb.setDtDue(new
					 * DateFormatUtil().format(dtDue.toString(),1)); }
					 */
					java.util.Date startDt = new java.util.Date(rs.getDate(18).getTime());

					java.util.Calendar c = java.util.Calendar.getInstance();
					c.setTime(startDt);

					java.util.Date dueDt = new BusinessCalendar().addBussinessDays(duration, c).getTime();
					java.sql.Date dtDue = new java.sql.Date(dueDt.getTime());

					if (dtDue.toString().equals("1969-12-31"))
					{
						cb.setDtDue("");
					} else
					{
						cb.setDtDue(new DateFormatUtil().format(dtDue.toString(), 1));
					}
				} else
				{
					cb.setDtDue("");
				}
				rs1.close();
				ps1.close();

				results.addElement(cb);
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

	public Vector getCompleteCorrespondence(String fromDt, String toDt) throws BusinessLogicException, SQLException
	{

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		Vector results = new Vector();

		java.util.Date fromDate = null;
		java.sql.Date frDT = null;

		java.util.Date toDate = null;
		java.sql.Date toDT = null;

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat sdf1 = new SimpleDateFormat("MMddyyyy");

		if (fromDt != null)
		{
			if (fromDt.length() == 10)
			{
				try
				{
					fromDate = sdf.parse(fromDt);
					frDT = new java.sql.Date(fromDate.getTime());
				} catch (ParseException pe)
				{
					throw new BusinessLogicException(pe.getMessage());
				}
			} else if (fromDt.length() == 8)
			{
				try
				{
					fromDate = sdf1.parse(fromDt);
					frDT = new java.sql.Date(fromDate.getTime());
				} catch (ParseException pe)
				{
					throw new BusinessLogicException(pe.getMessage());
				}
			}
		} else
		{
			fromDate = java.util.Calendar.getInstance().getTime();
			frDT = new java.sql.Date(fromDate.getTime());
		}

		if (toDt != null)
		{
			if (toDt.length() == 10)
			{
				try
				{
					toDate = sdf.parse(toDt);
					toDT = new java.sql.Date(toDate.getTime());
				} catch (ParseException pe)
				{
					throw new BusinessLogicException(pe.getMessage());
				}
			} else if (toDt.length() == 8)
			{
				try
				{
					toDate = sdf1.parse(toDt);
					toDT = new java.sql.Date(toDate.getTime());
				} catch (ParseException pe)
				{
					throw new BusinessLogicException(pe.getMessage());
				}
			}
		} else
		{
			toDate = java.util.Calendar.getInstance().getTime();
			toDT = new java.sql.Date(toDate.getTime());
		}

		String query = "SELECT A.ID_REFERENCE, B.ID_WRKR_ASSIGN, A.CD_STAT, DATE(A.TS_CREATE), A.CD_RFRL, A.NM_CUSTOMER_L, " + "A.NM_CUSTOMER_F, A.NM_CUSTOMER_M , A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, " + "C.NM_WRKR_L, C.NM_WRKR_F , C.NM_WRKR_MI, DATE(B.TS_WRKR_START), DATE(B.TS_WRKR_END), DATE(A.TS_ASSIGNED) " + "	FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "        " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " B, " + "        " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " C " + "  WHERE DATE(A.TS_CREATE) BETWEEN ? AND ? " + "    AND B.ID_REFERENCE = A.ID_REFERENCE " + "    AND A.CD_STAT = 'CLSD' " + "    AND B.CD_STAT = A.CD_STAT " + "    AND C.ID_WRKR = B.ID_WRKR_ASSIGN " +
		// " ORDER BY ID_WRKR_ASSIGN, ID_REFERENCE " ;
		"  ORDER BY C.NM_WRKR_L, C.NM_WRKR_F , C.NM_WRKR_MI, ID_REFERENCE ";

		try
		{
			System.out.println("CorrespondenceReportModel getCompleteCorrespondence frdate " + frDT + "toDT " +toDT +" sql " +query  );
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(query);
			ps.setDate(1, frDT);
			ps.setDate(2, toDT);
			rs = ps.executeQuery();
			while (rs.next())
			{
				CorrespondenceBean cb = new CorrespondenceBean();
				cb.setIdReference(rs.getString(1));
				cb.setIdWorker(rs.getString(2));
				cb.setCdReferral(rs.getString(5));
				cb.setCdProcess(rs.getString(3));
				cb.setCustomerNameFirst(rs.getString(7).trim().toUpperCase());
				cb.setCustomerNameLast(rs.getString(6).trim().toUpperCase());
				cb.setCustomerName(rs.getString(7).trim().toUpperCase() + " " + rs.getString(8).trim().toUpperCase() + " " + rs.getString(6).trim().toUpperCase());
				cb.setWrkrName(rs.getString(14).trim() + " " + rs.getString(15).trim() + " " + rs.getString(13).trim());
				cb.setReferralSource1(rs.getString(9).trim());
				cb.setReferralSource2(rs.getString(10).trim());
				cb.setReferralSource3(rs.getString(11).trim());
				cb.setReferralSource4(rs.getString(12).trim());

				cb.setDtCreated(new DateFormatUtil().format(rs.getDate(18).toString(), 1));

				java.sql.Date dtComplete = rs.getDate(17);
				java.sql.Date dtDefault = new java.sql.Date(0);

				if (dtComplete.toString().equals("1969-12-31"))
				{
					cb.setDtCompleted("");
				} else
				{
					cb.setDtCompleted(new DateFormatUtil().format(dtComplete.toString(), 1));
				}
				System.out.println("CorrespondenceReportModel getCompleteCorrespondence cb.getCdReferral() " +cb.getCdReferral() + " SELECT_DUEDATE_SQL " +SELECT_DUEDATE_SQL  );
				PreparedStatement ps1 = connection.prepareStatement(SELECT_DUEDATE_SQL);
				ps1.setString(1, cb.getCdReferral());
				ResultSet rs1 = ps1.executeQuery();
				if (rs1.next())
				{
					short duration = rs1.getShort(1);
					/*
					 * CT# 527783 - RK 08/24/04 Due date must be computed using
					 * BusinessCalendar class and not just by adding the
					 * duration to create date
					 */
					/*
					 * java.util.Date startDt = new
					 * java.util.Date(rs.getDate(4).getTime());
					 * 
					 * java.util.Calendar c = java.util.Calendar.getInstance();
					 * c.setTime(startDt); c.add(Calendar.DATE,duration);
					 * 
					 * java.util.Date dueDt = c.getTime(); java.sql.Date dtDue =
					 * new java.sql.Date(dueDt.getTime());
					 * 
					 * if (dtDue.toString().equals("1969-12-31")) {
					 * cb.setDtDue(""); } else { cb.setDtDue(new
					 * DateFormatUtil().format(dtDue.toString(),1)); }
					 */
					java.util.Date startDt = new java.util.Date(rs.getDate(18).getTime());

					java.util.Calendar c = java.util.Calendar.getInstance();
					c.setTime(startDt);

					java.util.Date dueDt = new BusinessCalendar().addBussinessDays(duration, c).getTime();
					java.sql.Date dtDue = new java.sql.Date(dueDt.getTime());

					if (dtDue.toString().equals("1969-12-31"))
					{
						cb.setDtDue("");
					} else
					{
						cb.setDtDue(new DateFormatUtil().format(dtDue.toString(), 1));
					}
				} else
				{
					cb.setDtDue("");
				}
				rs1.close();
				ps1.close();

				results.addElement(cb);
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
