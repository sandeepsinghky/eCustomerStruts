package org.dhhs.dirm.acts.cs.persister;

import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.dhhs.dirm.acts.cs.ejb.*;
import org.dhhs.dirm.acts.cs.ejb.util.Constants;

/**
 * CSUserPersister.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by
 * SYSTEMS RESEARCH & DEVELOPMENT INC.,
 * 
 * This is a helper class for handling the JDBC jobs for the BMP CSUser Entity EJB
 * 
 * 
 * Creation Date: Sep 5, 2003 10:31:31 AM
 * 
 * @author Rkodumagulla
 *
 */
public class CSActsWorkerInquiry {

	// sql datasource cached
	private DataSource ds = null;

	private static final Logger log = Logger.getLogger(CSActsWorkerInquiry.class);

	// SQL statements
	final String SELECT_SQL = "SELECT ID_WRKR, CD_FIPS_WRKR, NM_WRKR_L, NM_WRKR_F, NM_WRKR_MI, ID_WRKR_LOGON, CD_WRKR_TYPE, NB_TEL_WRKR_ACD, NB_TEL_WRKR_EXC, NB_TEL_WRKR_LN, TM_WRK_STRT, TM_WRK_END, TM_LUNCH_STRT, TM_LUNCH_END, DE_WRKR_TITLE, ID_WRKR_SUP, NB_TEL_WRKR_EXT, NB_FAX_WRKR_ACD, NB_FAX_WRKR_EXC, NB_FAX_WRKR_LN, AD_INTERNET_WRKR FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " WHERE ID_WRKR = ?";
	final String S_ALL_SQL = "SELECT ID_WRKR, CD_FIPS_WRKR, NM_WRKR_L, NM_WRKR_F, NM_WRKR_MI, ID_WRKR_LOGON, CD_WRKR_TYPE, NB_TEL_WRKR_ACD, NB_TEL_WRKR_EXC, NB_TEL_WRKR_LN, TM_WRK_STRT, TM_WRK_END, TM_LUNCH_STRT, TM_LUNCH_END, DE_WRKR_TITLE, ID_WRKR_SUP, NB_TEL_WRKR_EXT, NB_FAX_WRKR_ACD, NB_FAX_WRKR_EXC, NB_FAX_WRKR_LN, AD_INTERNET_WRKR  FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " ORDER BY NM_WRKR_L, NM_WRKR_F";
	final String S_LASTNAME_SQL = "SELECT ID_WRKR, CD_FIPS_WRKR, NM_WRKR_L, NM_WRKR_F, NM_WRKR_MI, ID_WRKR_LOGON, CD_WRKR_TYPE, NB_TEL_WRKR_ACD, NB_TEL_WRKR_EXC, NB_TEL_WRKR_LN, TM_WRK_STRT, TM_WRK_END, TM_LUNCH_STRT, TM_LUNCH_END, DE_WRKR_TITLE, ID_WRKR_SUP, NB_TEL_WRKR_EXT, NB_FAX_WRKR_ACD, NB_FAX_WRKR_EXC, NB_FAX_WRKR_LN, AD_INTERNET_WRKR  FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " WHERE NM_WRKR_L LIKE ?";
	final String S_NAME_SQL = "SELECT ID_WRKR, CD_FIPS_WRKR, NM_WRKR_L, NM_WRKR_F, NM_WRKR_MI, ID_WRKR_LOGON, CD_WRKR_TYPE, NB_TEL_WRKR_ACD, NB_TEL_WRKR_EXC, NB_TEL_WRKR_LN, TM_WRK_STRT, TM_WRK_END, TM_LUNCH_STRT, TM_LUNCH_END, DE_WRKR_TITLE, ID_WRKR_SUP, NB_TEL_WRKR_EXT, NB_FAX_WRKR_ACD, NB_FAX_WRKR_EXC, NB_FAX_WRKR_LN, AD_INTERNET_WRKR  FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " WHERE NM_WRKR_L = ? AND NM_WRKR_F = ?";

	public CSActsWorkerInquiry() {
		initializeResources();
	}

	public void initializeResources() {
		
		DBConnectManager manager = new DBConnectManager();
		ds = manager.getDataSource();
	}

	private DataSource getDatasource() throws SQLException {
		if (ds == null)
			throw new SQLException("Data source is null");
		else
			return ds;
	}

	public void freeResources() {
//		System.out.println("BMP persister free resources");
		ds = null;
	}

	public void createState() throws SQLException {
//		System.out.println("BMP persister createState not required");
	}

	public boolean findPrimaryKey(String key) throws SQLException {
		log.info("BMP persister findByPrimaryKey");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean result = false;
		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_SQL);
			ps.setString(1, key);
			rs = ps.executeQuery();
			result = rs.next();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (con != null) {
				con.commit();
				con.close();
			}
		}
		
		log.info("BMP persister findByPrimaryKey will return :" + result);
		
		return result;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection findAllWorkers() throws SQLException {
		log.info("BMP persister findAllWorkers");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		ArrayList a = new ArrayList();
		
		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(S_ALL_SQL);
			rs = ps.executeQuery();


			while (rs.next()) {
				String id = rs.getString(1);
				a.add(id);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (con != null) {
				con.commit();
				con.close();
			}
		}
		
		log.info("BMP persister findAllWorkers will return :" + a);
		
		return a;
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection findWorkersByName(String lastName, String firstName) throws SQLException {
		log.info("BMP persister findWorkersByName");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		ArrayList a = new ArrayList();
		
		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(S_NAME_SQL);
			ps.setString(1,lastName);
			ps.setString(2,firstName);
			rs = ps.executeQuery();

			while (rs.next()) {
				String id = rs.getString(1);
				a.add(id);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (con != null) {
				con.commit();
				con.close();
			}
		}
		
		log.info("BMP persister findWorkersByName will return :" + a);
		
		return a;
	}	

	public Collection findWorkersByLastName(String lastName) throws SQLException {
		log.info("BMP persister findWorkersByLastName");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		ArrayList a = new ArrayList();
		
		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(S_LASTNAME_SQL);
			ps.setString(1,lastName);
			rs = ps.executeQuery();

			while (rs.next()) {
				String id = rs.getString(1);
				a.add(id);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (con != null) {
				con.commit();
				con.close();
			}
		}
		
		log.info("BMP persister findWorkersByLastName will return :" + a);
		
		return a;
	}	


	public void loadState(CSActsWorkerBean bean) throws SQLException {
		log.info("BMP persister loadState");
		String key = (String) bean.getEntityContext().getPrimaryKey();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_SQL);
			ps.setString(1, key);
			rs = ps.executeQuery();
			rs.next();

			bean.setIdWrkr(rs.getString(1));
			bean.setCdFipsWrkr(rs.getString(2));
			bean.setNmWrkr(rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(3));
			bean.setCdWrkrType(rs.getString(7));
			bean.setTmLunchStart(rs.getTime(13).toString());
			bean.setTmLunchEnd(rs.getTime(14).toString());
			bean.setTmWorkStart(rs.getTime(11).toString());
			bean.setTmWorkEnd(rs.getTime(12).toString());
			bean.setIdEmail(rs.getString(21));
			bean.setIdWrkrLogon(rs.getString(6));
			bean.setNbTelWorker(rs.getString(8) + rs.getString(9) + rs.getString(10));
			bean.setNbFaxWorker(rs.getString(18) + rs.getString(19) + rs.getString(20));

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (con != null) {
				con.commit();
				con.close();
			}
		}
	}

	public void storeState(CSActsWorkerBean bean) throws SQLException {
		log.info("BMP persister storeState not required");
	}

	public boolean deleteState(CSActsWorkerBean bean) throws SQLException {
		log.info("BMP persister deleteState not required");
		return false;
	}
}
