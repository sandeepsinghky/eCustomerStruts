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
import org.dhhs.dirm.acts.cs.beans.UserEntityBean;
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
public class CSUserPersister {

	// sql datasource cached
	private DataSource ds = null;

	private static final Logger log = Logger.getLogger(CSUserPersister.class);

	// SQL statements
	final String SELECT_SQL = "SELECT A.ID_WRKR, A.ID_PASSWORD, A.ID_PROFILE, A.CD_ACCPT_WRKLD, " +
								"A.NB_OUTSTANDING, A.NB_COMPLETED, A.NB_APPROVAL, A.TS_CREATE, " + 
								"A.ID_WRKR_CREATE, A.TS_UPDATE, A.ID_WRKR_UPDATE, B.ID_PROFILE_DESC, " + 
								"C.NM_WRKR_F, C.NM_WRKR_MI, C.NM_WRKR_L, A.CD_PASSWORD_STATUS, " + 
								"A.CD_DT_RCVD, A.CD_DT_COMP, A.CD_DT_DUE, A.CD_NB_CASE, A.CD_ID_PART, " + 
								"A.CD_NB_SSN, A.CD_AGENT, A.CD_REFERRAL_TYPE, A.CD_EMAIL, " +
								"A.CD_CP, A.CD_NCP, A.CD_NB_CNTL, A.CD_NM_CUSTOMER, A.CD_NB_DKT, " + 
								"A.CD_RFRL_SRC1, A.CD_RFRL_SRC2, A.CD_RFRL_SRC3, A.CD_RFRL_SRC4, A.CD_NM_CNTY, A.CD_APPROVAL_REQ " + 
								"FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_USER + " A, " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_PROFILE + " B, " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " C WHERE A.ID_WRKR = ? AND B.ID_PROFILE = A.ID_PROFILE AND C.ID_WRKR = A.ID_WRKR";
	
	final String S_ALL_SQL = "SELECT A.ID_WRKR, A.ID_PASSWORD, A.ID_PROFILE, A.CD_ACCPT_WRKLD, A.NB_OUTSTANDING, A.NB_COMPLETED, A.NB_APPROVAL, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, A.ID_WRKR_UPDATE, B.ID_PROFILE_DESC, C.NM_WRKR_F, C.NM_WRKR_MI, C.NM_WRKR_L FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_USER + " A, " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_PROFILE + " B, " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " C WHERE B.ID_PROFILE = A.ID_PROFILE  AND C.ID_WRKR = A.ID_WRKR";
	final String S_PROFILE_SQL = "SELECT A.ID_WRKR, A.ID_PASSWORD, A.ID_PROFILE, A.CD_ACCPT_WRKLD, A.NB_OUTSTANDING, A.NB_COMPLETED, A.NB_APPROVAL, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, A.ID_WRKR_UPDATE, B.ID_PROFILE_DESC, C.NM_WRKR_F, C.NM_WRKR_MI, C.NM_WRKR_L FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_USER + " A, " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_PROFILE + " B, " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " C  WHERE A.ID_PROFILE = ? AND B.ID_PROFILE = A.ID_PROFILE  AND C.ID_WRKR = A.ID_WRKR";
	final String S_LASTNAME_SQL = "SELECT A.ID_WRKR, A.ID_PASSWORD, A.ID_PROFILE, A.CD_ACCPT_WRKLD, A.NB_OUTSTANDING, A.NB_COMPLETED, A.NB_APPROVAL, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, A.ID_WRKR_UPDATE, B.ID_PROFILE_DESC, C.NM_WRKR_F, C.NM_WRKR_MI, C.NM_WRKR_L FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_USER + " A, " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_PROFILE + " B, " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " C WHERE C.NM_WRKR_L LIKE ? AND A.ID_WRKR = C.ID_WRKR AND B.ID_PROFILE = A.ID_PROFILE";
	
	final String S_NAME_SQL = "SELECT A.ID_WRKR, A.ID_PASSWORD, A.ID_PROFILE, A.CD_ACCPT_WRKLD, A.NB_OUTSTANDING, A.NB_COMPLETED, A.NB_APPROVAL, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, A.ID_WRKR_UPDATE, B.ID_PROFILE_DESC, C.NM_WRKR_F, C.NM_WRKR_MI, C.NM_WRKR_L FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_USER + " A, " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_PROFILE + " B, " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " C WHERE C.NM_WRKR_L = ? AND C.NM_WRKR_F = ? AND A.ID_WRKR = C.ID_WRKR AND B.ID_PROFILE = A.ID_PROFILE";

	final String INSERT_SQL = 
		"INSERT INTO " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_USER + " " +
		"(ID_WRKR, ID_PASSWORD, ID_PROFILE, CD_ACCPT_WRKLD, CD_APPROVAL_REQ, NB_OUTSTANDING, NB_COMPLETED, " + 
		"NB_APPROVAL, TS_CREATE, ID_WRKR_CREATE, TS_UPDATE, ID_WRKR_UPDATE, CD_PASSWORD_STATUS, " + 
		"CD_DT_RCVD, CD_DT_COMP, CD_DT_DUE, CD_NB_CASE, CD_ID_PART, CD_NB_SSN, CD_AGENT, CD_REFERRAL_TYPE, CD_EMAIL, " +
		"CD_CP, CD_NCP, CD_NB_CNTL, CD_NM_CUSTOMER, CD_NB_DKT, CD_RFRL_SRC1, CD_RFRL_SRC2, CD_RFRL_SRC3, CD_RFRL_SRC4, CD_NM_CNTY)" + 
		"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
	final String UPDATE_SQL = "UPDATE " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_USER + " SET ID_PASSWORD = ?, ID_PROFILE = ?, CD_ACCPT_WRKLD = ?, " + 
								" NB_OUTSTANDING = ?, NB_COMPLETED = ?, NB_APPROVAL = ?, TS_UPDATE = ?, " + 
								"ID_WRKR_UPDATE = ?, CD_PASSWORD_STATUS = ?,  " + 
								"CD_DT_RCVD = ?, CD_DT_COMP = ?, CD_DT_DUE = ?, CD_NB_CASE = ?, CD_ID_PART = ?, " + 
								"CD_NB_SSN = ?, CD_AGENT = ?, CD_REFERRAL_TYPE = ?, CD_EMAIL = ?, " +
								"CD_CP = ?, CD_NCP = ?, CD_NB_CNTL = ?, CD_NM_CUSTOMER = ?, CD_NB_DKT = ?, " + 
								"CD_RFRL_SRC1 = ?, CD_RFRL_SRC2 = ?, CD_RFRL_SRC3 = ?, CD_RFRL_SRC4 = ?, CD_NM_CNTY = ?, CD_APPROVAL_REQ = ? " + 
								"WHERE ID_WRKR = ?";
								
	final String UPDATE_STATUS_SQL = "UPDATE " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_USER + " SET CD_ACCPT_WRKLD = ? WHERE ID_WRKR = ?";
	final String UPDATE_PROFILE_SQL = "UPDATE " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_USER + " SET ID_PROFILE = ? WHERE ID_WRKR = ?";
	final String UPDATE_OUTSTANDING_SQL = "UPDATE " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_USER + " SET NB_OUTSTANDING = ? WHERE ID_WRKR = ?";
	final String UPDATE_COMPLETED_SQL = "UPDATE " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_USER + " SET NB_COMPLETED = ? WHERE ID_WRKR = ?";
	final String UPDATE_APPROVAL_SQL = "UPDATE " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_USER + " SET NB_APPROVAL = ? WHERE ID_WRKR = ?";
	final String DELETE_SQL = "DELETE FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_USER + " WHERE ID_WRKR = ?";

	public CSUserPersister() {
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
		log.info("BMP persister free resources");
		ds = null;
	}

	public void createState(String idWrkr, String idPassword, String idProfile, String idWrkrCreate) throws SQLException {
		log.info("BMP persister createState");
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(INSERT_SQL);
			
			java.sql.Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());
			
			ps.setString(1, idWrkr);
			ps.setString(2, idPassword);
			ps.setString(3, idProfile);
			ps.setString(4, "Y");
			ps.setString(5, "Y");
			ps.setInt(6,0);
			ps.setInt(7,0);
			ps.setInt(8,0);
			ps.setTimestamp(9,ts);
			ps.setString(10,idWrkrCreate);
			ps.setTimestamp(11,ts);
			ps.setString(12,idWrkrCreate);
			ps.setString(13,"I");
			ps.setString(14,"Y");
			ps.setString(15,"Y");
			ps.setString(16,"Y");
			ps.setString(17,"Y");
			ps.setString(18,"Y");
			ps.setString(19,"Y");
			ps.setString(20,"Y");
			ps.setString(21,"Y");
			ps.setString(22,"Y");
			ps.setString(23,"Y");
			ps.setString(24,"Y");
			ps.setString(25,"Y");
			ps.setString(26,"Y");
			ps.setString(27,"Y");
			ps.setString(28,"Y");
			ps.setString(29,"Y");
			ps.setString(30,"Y");
			ps.setString(31,"Y");
			ps.setString(32,"Y");
			int result = ps.executeUpdate();
			
			UserEntityBean ueb = new UserEntityBean();
			ueb.setIdWrkr(idWrkr);
			ueb.setCdAccptWrkld("Y");
			
			/**
			 * RK CT# 523481  - When ever a new agent is added to the system, simply add
			 * the agent to the assignment table
			 * 08/12/04
			 */
			AgentDelegate.addAgent(ueb);
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			if (con != null) {
				con.commit();
				con.close();
			}
		}
	}

	public boolean findPrimaryKey(String key) throws SQLException {
		log.info("BMP persister findByPrimaryKey");
		System.out.println("CSUserPersister findPrimaryKey key: " + key + " SELECT_SQL " +SELECT_SQL);
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


	public Collection findAllUsers() throws SQLException {
		log.info("BMP persister findAllUsers");
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
		
		log.info("BMP persister findAllUsers will return :" + a);
		
		return a;
	}


	public Collection findUsersForProfile(String profileID) throws SQLException {
		log.info("BMP persister findUsersForProfile");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		ArrayList a = new ArrayList();
		
		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(S_PROFILE_SQL);
			ps.setString(1,profileID);
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
		
		log.info("BMP persister findUsersForProfile will return :" + a);
		
		return a;
	}
	
	public Collection findUsersByName(String lastName, String firstName) throws SQLException {
		log.info("BMP persister findUsersByName");
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
		
		log.info("BMP persister findUsersByName will return :" + a);
		
		return a;
	}	

	public Collection findUsersByLastName(String lastName) throws SQLException {
		log.info("BMP persister findUsersByLastName");
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
		
		log.info("BMP persister findUsersByLastName will return :" + a);
		
		return a;
	}	


	public void loadState(CSUserBean bean) throws SQLException {
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
			bean.setIdPassword(rs.getString(2));
			bean.setIdProfile(rs.getString(3));
			bean.setCdAccptWrkld(rs.getString(4));
			bean.setNbOutstanding(rs.getInt(5));
			bean.setNbCompleted(rs.getInt(6));
			bean.setNbApproval(rs.getInt(7));
			bean.setTsCreate(rs.getTimestamp(8));
			bean.setIdWrkrCreate(rs.getString(9));
			bean.setTsUpdate(rs.getTimestamp(10));
			bean.setIdWrkrUpdate(rs.getString(11));
			bean.setIdProfileDesc(rs.getString(12));
			bean.setNmWrkr(rs.getString(13) + " " + rs.getString(14) + " " + rs.getString(15));
			bean.setCdPasswordStatus(rs.getString(16));
			bean.setByDtReceived(rs.getString(17).equals("Y") ? true : false);
			bean.setByDtCompleted(rs.getString(18).equals("Y") ? true : false);
			bean.setByDtDue(rs.getString(19).equals("Y") ? true : false);
			bean.setByNbCase(rs.getString(20).equals("Y") ? true : false);
			bean.setByIdPart(rs.getString(21).equals("Y") ? true : false);
			bean.setByNbSSN(rs.getString(22).equals("Y") ? true : false);
			bean.setByAgent(rs.getString(23).equals("Y") ? true : false);
			bean.setByReferralType(rs.getString(24).equals("Y") ? true : false);
			bean.setByEmail(rs.getString(25).equals("Y") ? true : false);
			bean.setByCP(rs.getString(26).equals("Y") ? true : false);
			bean.setByNCP(rs.getString(27).equals("Y") ? true : false);
			bean.setByControlNbr(rs.getString(28).equals("Y") ? true : false);
			bean.setByCustomer(rs.getString(29).equals("Y") ? true : false);
			bean.setByNbDkt(rs.getString(30).equals("Y") ? true : false);
			bean.setBySrc1(rs.getString(31).equals("Y") ? true : false);
			bean.setBySrc2(rs.getString(32).equals("Y") ? true : false);
			bean.setBySrc3(rs.getString(33).equals("Y") ? true : false);
			bean.setBySrc4(rs.getString(34).equals("Y") ? true : false);
			bean.setByCounty(rs.getString(35).equals("Y") ? true : false);
			bean.setCdApprovalRequired(rs.getString(36));
			
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

	public void storeState(CSUserBean bean) throws SQLException {
		log.info("BMP persister storeState");
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(UPDATE_SQL);
			
			java.sql.Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());
			
			ps.setString(1, bean.getIdPassword());
			ps.setString(2, bean.getIdProfile());
			ps.setString(3, bean.getCdAccptWrkld());
			ps.setInt(4,bean.getNbOutstanding());
			ps.setInt(5,bean.getNbCompleted());
			ps.setInt(6,bean.getNbApproval());
			ps.setTimestamp(7,ts);
			ps.setString(8,bean.getIdWrkrUpdate());
			ps.setString(9,bean.getCdPasswordStatus());
			ps.setString(10,bean.isByDtReceived() == true ? "Y" : "N");
			ps.setString(11,bean.isByDtCompleted() == true ? "Y" : "N");
			ps.setString(12,bean.isByDtDue() == true ? "Y" : "N");
			ps.setString(13,bean.isByNbCase() == true ? "Y" : "N");
			ps.setString(14,bean.isByIdPart() == true ? "Y" : "N");
			ps.setString(15,bean.isByNbSSN() == true ? "Y" : "N");
			ps.setString(16,bean.isByAgent() == true ? "Y" : "N");
			ps.setString(17,bean.isByReferralType() == true ? "Y" : "N");
			ps.setString(18,bean.isByEmail() == true ? "Y" : "N");
			ps.setString(19,bean.isByCP() == true ? "Y" : "N");
			ps.setString(20,bean.isByNCP() == true ? "Y" : "N");
			ps.setString(21,bean.isByControlNbr() == true ? "Y" : "N");
			ps.setString(22,bean.isByCustomer() == true ? "Y" : "N");
			ps.setString(23,bean.isByNbDkt() == true ? "Y" : "N");
			ps.setString(24,bean.isBySrc1() == true ? "Y" : "N");
			ps.setString(25,bean.isBySrc2() == true ? "Y" : "N");
			ps.setString(26,bean.isBySrc3() == true ? "Y" : "N");
			ps.setString(27,bean.isBySrc4() == true ? "Y" : "N");
			ps.setString(28,bean.isByCounty() == true ? "Y" : "N");
			ps.setString(29,bean.getCdApprovalRequired());
			ps.setString(30,bean.getIdWrkr());
			
			int result = ps.executeUpdate();

			/**
			 * RK CT# 523481  - When ever an agent is updated to the system, simply update
			 * the agent in the assignment table
			 * 08/12/04
			 */
			try {
				AgentDelegate.updateAgentStatus(bean.getIdWrkr(),bean.getCdAccptWrkld());
			} catch (EmptyListException ele) {
				log.info("EJB CSUserPersister: " + ele.getMessage());
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			if (con != null) {
				con.commit();
				con.close();
			}
		}
	}

	public boolean deleteState(CSUserBean bean) throws SQLException {
		log.info("BMP persister deleteState");
		String key = (String) bean.getEntityContext().getPrimaryKey();
		Connection con = null;
		PreparedStatement ps = null;
		int result;
		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(DELETE_SQL);
			ps.setString(1, key);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			if (con != null) {
				con.commit();
				con.close();
			}
		}
		return (result != 0);
	}
	

	public boolean updateStatus(String idWorker, String cdStatus) throws SQLException {
		log.info("BMP persister updateStatus");
		Connection con = null;
		PreparedStatement ps = null;
		int result;
		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(UPDATE_STATUS_SQL);
			ps.setString(1, cdStatus);
			ps.setString(2, idWorker);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			if (con != null) {
				con.commit();
				con.close();
			}
		}
		return (result != 0);
	}

	public boolean updateProfile(String idWorker, String idProfile) throws SQLException {
		log.info("BMP persister updateProfile");
		Connection con = null;
		PreparedStatement ps = null;
		int result;
		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(UPDATE_PROFILE_SQL);
			ps.setString(1, idProfile);
			ps.setString(2, idWorker);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			if (con != null) {
				con.commit();
				con.close();
			}
		}
		return (result != 0);
	}


	public boolean updateOutstanding(String idWorker, int nbOutstanding) throws SQLException {
		log.info("BMP persister updateOutstanding");
		Connection con = null;
		PreparedStatement ps = null;
		int result;
		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(UPDATE_OUTSTANDING_SQL);
			ps.setInt(1, nbOutstanding);
			ps.setString(2, idWorker);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			if (con != null) {
				con.commit();
				con.close();
			}
		}
		return (result != 0);
	}


	public boolean updateCompleted(String idWorker, int nbCompleted) throws SQLException {
		log.info("BMP persister updateCompleted");
		Connection con = null;
		PreparedStatement ps = null;
		int result;
		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(UPDATE_COMPLETED_SQL);
			ps.setInt(1, nbCompleted);
			ps.setString(2, idWorker);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			if (con != null) {
				con.commit();
				con.close();
			}
		}
		return (result != 0);
	}

	public boolean updateApproval(String idWorker, int nbApproval) throws SQLException {
		log.info("BMP persister updateApproval");
		Connection con = null;
		PreparedStatement ps = null;
		int result;
		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(UPDATE_APPROVAL_SQL);
			ps.setInt(1, nbApproval);
			ps.setString(2, idWorker);
			result = ps.executeUpdate(); 
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			if (con != null) {
				con.commit();
				con.close();
			}
		}
		return (result != 0);
	}
	
}

