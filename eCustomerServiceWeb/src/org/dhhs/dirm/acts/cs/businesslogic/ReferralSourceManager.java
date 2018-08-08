

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
import org.dhhs.dirm.acts.cs.formbeans.ReferralOffice;
import org.dhhs.dirm.acts.cs.formbeans.ReferralSource;
import org.dhhs.dirm.acts.cs.helpers.FormatTimestamp;

/**
 * ReferralSourceManager
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Aug 18, 2003 9:59:00 AM
 * 
 * @author Rkodumagulla
 *
 */
public class ReferralSourceManager
{

	/**
	 *
	 */
	private static ReferralSourceManager	referralSourceManager	= null;
	private static Context					ctx						= null;
	private static DataSource				dataSource				= null;
	private static String					user					= null;
	private static String					password				= null;

	/**
	 * Constructor for UserRegistry.
	 */
	public ReferralSourceManager() throws BusinessLogicException
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
	public static ReferralSourceManager getInstance() throws BusinessLogicException
	{

		if (null == referralSourceManager)
		{
			referralSourceManager = new ReferralSourceManager();
		}
		return referralSourceManager;
	}

	public Vector getOffices() throws BusinessLogicException, SQLException
	{

		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		Vector offices = new Vector();

		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);

			statement = connection.createStatement();
			String query = "SELECT NB_SEQ, NM_OFFICE, NM_OFFICE_DESC, TS_CREATE, ID_WRKR_CREATE, TS_UPDATE, ID_WRKR_UPDATE " + "  FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_OFFICE + "";

			rs = statement.executeQuery(query);

			while (rs.next())
			{
				ReferralOffice ro = new ReferralOffice();
				ro.setNbSeq(new Short(rs.getShort(1)).toString());
				ro.setNmOffice(rs.getString(2));
				ro.setNmOfficeDesc(rs.getString(3));
				ro.setTsCreate(new FormatTimestamp().format(rs.getTimestamp(4)));
				ro.setIdWrkrCreate(rs.getString(5));
				ro.setTsUpdate(new FormatTimestamp().format(rs.getTimestamp(6)));
				ro.setIdWrkrUpdate(rs.getString(7));
				offices.addElement(ro);

				short sequenceNbr = Short.parseShort(ro.getNbSeq());

				String sources = "SELECT ID_STAFF, NB_SEQ, NM_STAFF, NM_TITLE, CD_STATUS, TS_CREATE, ID_WRKR_CREATE, TS_UPDATE, ID_WRKR_UPDATE " + "  FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_STAFF + " WHERE NB_SEQ = " + sequenceNbr + " AND CD_STATUS = 'A' ORDER BY ID_STAFF";

				Statement s = connection.createStatement();
				ResultSet r = s.executeQuery(sources);
				while (r.next())
				{
					ReferralSource refs = new ReferralSource();
					refs.setIdStaff(r.getString(1));
					refs.setNbSeq(new Short(r.getShort(2)).toString());
					refs.setNmStaff(r.getString(3));
					refs.setTitle(r.getString(4));
					refs.setCdStatus(r.getString(5).equals("A") ? true : false);
					refs.setTsCreate(new FormatTimestamp().format(r.getTimestamp(6)));
					refs.setIdWrkrCreate(r.getString(7));
					refs.setTsUpdate(new FormatTimestamp().format(r.getTimestamp(8)));
					refs.setIdWrkrUpdate(r.getString(9));
					ro.addSource(refs);
				}
				r.close();
				s.close();
			}
		} catch (SQLException se)
		{
			throw new BusinessLogicException(se.getMessage());
		} finally
		{
			if (rs != null)
				rs.close();
			if (statement != null)
				statement.close();
			closeConnection(connection);
		}
		return offices;
	}

	public boolean getOfficeByName(String office) throws BusinessLogicException, SQLException
	{

		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		boolean exists = false;

		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);

			statement = connection.createStatement();
			String query = "SELECT NM_OFFICE " + "  FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_OFFICE + " WHERE NM_OFFICE = '" + office + "'";
			rs = statement.executeQuery(query);

			if (rs.next())
			{
				exists = true;
			}
		} catch (SQLException se)
		{
			throw new BusinessLogicException(se.getMessage());
		} finally
		{
			if (rs != null)
				rs.close();
			if (statement != null)
				statement.close();

			closeConnection(connection);
		}
		return exists;
	}

	public ReferralOffice getOffice(short key) throws BusinessLogicException, SQLException
	{

		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		ReferralOffice ro = null;

		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);

			statement = connection.createStatement();
			String query = "SELECT NB_SEQ, NM_OFFICE, NM_OFFICE_DESC, TS_CREATE, ID_WRKR_CREATE, TS_UPDATE, ID_WRKR_UPDATE " + "  FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_OFFICE + " WHERE NB_SEQ = " + key;

			rs = statement.executeQuery(query);

			while (rs.next())
			{
				ro = new ReferralOffice();
				ro.setNbSeq(new Short(rs.getShort(1)).toString());
				ro.setNmOffice(rs.getString(2));
				ro.setNmOfficeDesc(rs.getString(3));
				ro.setTsCreate(new FormatTimestamp().format(rs.getTimestamp(4)));
				ro.setIdWrkrCreate(rs.getString(5));
				ro.setTsUpdate(new FormatTimestamp().format(rs.getTimestamp(6)));
				ro.setIdWrkrUpdate(rs.getString(7));

				short sequenceNbr = Short.parseShort(ro.getNbSeq());

				String sources = "SELECT ID_STAFF, NB_SEQ, NM_STAFF, NM_TITLE, CD_STATUS, TS_CREATE, ID_WRKR_CREATE, TS_UPDATE, ID_WRKR_UPDATE " + "  FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_STAFF + " WHERE NB_SEQ = " + sequenceNbr + " AND CD_STATUS = 'A' ORDER BY ID_STAFF";

				Statement s = connection.createStatement();
				ResultSet r = s.executeQuery(sources);
				while (r.next())
				{
					ReferralSource refs = new ReferralSource();
					refs.setIdStaff(r.getString(1));
					refs.setNbSeq(new Short(r.getShort(2)).toString());
					refs.setNmStaff(r.getString(3));
					refs.setTitle(r.getString(4));
					refs.setCdStatus(r.getString(5).equals("A") ? true : false);
					refs.setTsCreate(new FormatTimestamp().format(r.getTimestamp(6)));
					refs.setIdWrkrCreate(r.getString(7));
					refs.setTsUpdate(new FormatTimestamp().format(r.getTimestamp(8)));
					refs.setIdWrkrUpdate(r.getString(9));
					ro.addSource(refs);
				}
				r.close();
				s.close();
			}
		} catch (SQLException se)
		{
			throw new BusinessLogicException(se.getMessage());
		} finally
		{
			if (rs != null)
				rs.close();
			if (statement != null)
				statement.close();

			closeConnection(connection);
		}
		return ro;
	}

	public Vector getAllReferralSources(String office) throws BusinessLogicException, SQLException
	{

		Connection connection = null;
		Statement statement = null;
		ResultSet r = null;

		Vector sources = new Vector();

		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);

			String query = "SELECT ID_STAFF, NB_SEQ, NM_STAFF, NM_TITLE, CD_STATUS, TS_CREATE, ID_WRKR_CREATE, TS_UPDATE, ID_WRKR_UPDATE " + "  FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_STAFF + " WHERE NB_SEQ = " + office + " AND CD_STATUS = 'A' ORDER BY ID_STAFF";

			statement = connection.createStatement();
			r = statement.executeQuery(query);
			while (r.next())
			{
				ReferralSource refs = new ReferralSource();
				refs.setIdStaff(r.getString(1));
				refs.setNbSeq(new Short(r.getShort(2)).toString());
				refs.setNmStaff(r.getString(3));
				refs.setTitle(r.getString(4));
				refs.setCdStatus(r.getString(5).equals("A") ? true : false);
				refs.setTsCreate(new FormatTimestamp().format(r.getTimestamp(6)));
				refs.setIdWrkrCreate(r.getString(7));
				refs.setTsUpdate(new FormatTimestamp().format(r.getTimestamp(8)));
				refs.setIdWrkrUpdate(r.getString(9));
				sources.addElement(refs);
			}
		} catch (SQLException se)
		{
			throw new BusinessLogicException(se.getMessage());
		} finally
		{
			if (r != null)
				r.close();
			if (statement != null)
				statement.close();

			closeConnection(connection);
		}
		return sources;
	}

	public Vector getAllReferralSources() throws BusinessLogicException, SQLException
	{

		Connection connection = null;
		Statement statement = null;
		ResultSet r = null;

		Vector sources = new Vector();

		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);

			String query = "SELECT ID_STAFF, NB_SEQ, NM_STAFF, NM_TITLE, CD_STATUS, TS_CREATE, ID_WRKR_CREATE, TS_UPDATE, ID_WRKR_UPDATE " + "  FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_STAFF + " WHERE CD_STATUS = 'A' ORDER BY ID_STAFF";

			statement = connection.createStatement();
			r = statement.executeQuery(query);
			while (r.next())
			{
				ReferralSource refs = new ReferralSource();
				refs.setIdStaff(r.getString(1));
				refs.setNbSeq(new Short(r.getShort(2)).toString());
				refs.setNmStaff(r.getString(3));
				refs.setTitle(r.getString(4));
				refs.setCdStatus(r.getString(5).equals("A") ? true : false);
				refs.setTsCreate(new FormatTimestamp().format(r.getTimestamp(6)));
				refs.setIdWrkrCreate(r.getString(7));
				refs.setTsUpdate(new FormatTimestamp().format(r.getTimestamp(8)));
				refs.setIdWrkrUpdate(r.getString(9));
				sources.addElement(refs);
			}
		} catch (SQLException se)
		{
			throw new BusinessLogicException(se.getMessage());
		} finally
		{
			if (r != null)
				r.close();
			if (statement != null)
				statement.close();

			closeConnection(connection);
		}
		return sources;
	}

	public ReferralSource getReferralSource(String idStaff) throws BusinessLogicException, SQLException
	{

		Connection connection = null;
		Statement statement = null;
		ResultSet r = null;

		ReferralSource refs = new ReferralSource();

		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);

			String query = "SELECT ID_STAFF, NB_SEQ, NM_STAFF, NM_TITLE, CD_STATUS, TS_CREATE, ID_WRKR_CREATE, TS_UPDATE, ID_WRKR_UPDATE " + "  FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_STAFF + " WHERE ID_STAFF = '" + idStaff + "'";

			statement = connection.createStatement();
			r = statement.executeQuery(query);
			while (r.next())
			{
				refs.setIdStaff(r.getString(1));
				refs.setNbSeq(new Short(r.getShort(2)).toString());
				refs.setNmStaff(r.getString(3));
				refs.setTitle(r.getString(4));
				refs.setCdStatus(r.getString(5).equals("A") ? true : false);
				refs.setTsCreate(new FormatTimestamp().format(r.getTimestamp(6)));
				refs.setIdWrkrCreate(r.getString(7));
				refs.setTsUpdate(new FormatTimestamp().format(r.getTimestamp(8)));
				refs.setIdWrkrUpdate(r.getString(9));
			}
		} catch (SQLException se)
		{
			throw new BusinessLogicException(se.getMessage());
		} finally
		{
			if (r != null)
				r.close();
			if (statement != null)
				statement.close();

			closeConnection(connection);
		}
		return refs;
	}

	public void addReferralSource(ReferralSource refs) throws BusinessLogicException, SQLException
	{

		Connection connection = null;
		String INSERT_SQL = "INSERT INTO " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_STAFF + " (ID_STAFF, NB_SEQ, NM_STAFF, NM_TITLE, CD_STATUS, TS_CREATE, ID_WRKR_CREATE, TS_UPDATE, ID_WRKR_UPDATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		String idStaff = getMaxSource();

		String newIdStaff = "";

		String zero = "0";

		idStaff = idStaff.substring(0, 5);

		try
		{
			int id = Integer.parseInt(idStaff);

			id++;

			String s = new Integer(id).toString();
			newIdStaff = s;

			for (int i = 0; i < (5 - s.length()); i++)
			{
				newIdStaff = zero + newIdStaff;
			}
			newIdStaff = newIdStaff + "000";

		} catch (NumberFormatException nfe)
		{
			throw new BusinessLogicException(nfe.getMessage());
		}

		PreparedStatement ps = null;
		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(INSERT_SQL);

			java.sql.Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());

			ps.setString(1, newIdStaff);
			ps.setShort(2, Short.parseShort(refs.getNbSeq()));
			ps.setString(3, refs.getNmStaff());
			ps.setString(4, refs.getTitle());
			ps.setString(5, refs.isCdStatus() ? "A" : "I");
			ps.setTimestamp(6, ts);
			ps.setString(7, refs.getIdWrkrCreate());
			ps.setTimestamp(8, ts);
			ps.setString(9, refs.getIdWrkrUpdate());
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

	public void updateReferralSource(ReferralSource refs, String idWorker) throws BusinessLogicException, SQLException
	{

		Connection connection = null;
		String UPDATE_SQL = "UPDATE " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_STAFF + " SET CD_STATUS = ?, TS_UPDATE = ?, ID_WRKR_UPDATE = ? WHERE ID_STAFF = ?";

		String INSERT_SQL = "INSERT INTO " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_STAFF + " (ID_STAFF, NB_SEQ, NM_STAFF, NM_TITLE, CD_STATUS, TS_CREATE, ID_WRKR_CREATE, TS_UPDATE, ID_WRKR_UPDATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		String idStaff = refs.getIdStaff();

		String newIdStaff = refs.getIdStaff().substring(0, 5);

		String zero = "0";

		PreparedStatement ps = null;
		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(UPDATE_SQL);

			java.sql.Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());

			ps.setString(1, "I");
			ps.setTimestamp(2, ts);
			ps.setString(3, idWorker);
			ps.setString(4, idStaff);
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

		idStaff = idStaff.substring(5);
		System.out.println("Last three digits " + idStaff);

		try
		{
			int id = Integer.parseInt(idStaff);

			id++;

			String s = new Integer(id).toString();
			int len = s.length();

			for (int i = 0; i < (3 - len); i++)
			{
				s = zero + s;
			}

			newIdStaff = newIdStaff + s;

			System.out.println("New Id staff " + newIdStaff);

		} catch (NumberFormatException nfe)
		{
			throw new BusinessLogicException(nfe.getMessage());
		}

		ps = null;

		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(INSERT_SQL);

			java.sql.Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());

			ps.setString(1, newIdStaff);
			ps.setShort(2, Short.parseShort(refs.getNbSeq()));
			ps.setString(3, refs.getNmStaff());
			ps.setString(4, refs.getTitle());
			ps.setString(5, "A");
			ps.setTimestamp(6, ts);
			ps.setString(7, idWorker);
			ps.setTimestamp(8, ts);
			ps.setString(9, idWorker);
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

	public short inactivateReferralSource(String key, String idWorker) throws BusinessLogicException, SQLException
	{

		Connection connection = null;
		Statement statement = null;
		ResultSet r = null;

		short nbSeq = -1;

		String SELECT_SQL = "SELECT NB_SEQ FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_STAFF + " WHERE ID_STAFF = '" + key + "'";

		String UPDATE_SQL = "UPDATE " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_STAFF + " SET CD_STATUS = ?, TS_UPDATE = ?, ID_WRKR_UPDATE = ? WHERE ID_STAFF = ?";

		PreparedStatement ps = null;

		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);

			statement = connection.createStatement();
			r = statement.executeQuery(SELECT_SQL);
			if (r.next())
			{
				nbSeq = r.getShort(1);
			}
			r.close();
			statement.close();

			ps = connection.prepareStatement(UPDATE_SQL);

			java.sql.Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());

			ps.setString(1, "I");
			ps.setTimestamp(2, ts);
			ps.setString(3, idWorker);
			ps.setString(4, key);
			ps.executeUpdate();
		} catch (SQLException se)
		{
			throw new BusinessLogicException(se.getMessage());
		} finally
		{
			if (r != null)
				r.close();
			if (statement != null)
				statement.close();
			if (ps != null)
				ps.close();

			closeConnection(connection);
		}

		return nbSeq;
	}

	public void createOffice(ReferralOffice ro) throws BusinessLogicException, DuplicateException, SQLException
	{

		Connection connection = null;
		String INSERT_SQL = "INSERT INTO " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_OFFICE + " (NB_SEQ, NM_OFFICE, NM_OFFICE_DESC, TS_CREATE, ID_WRKR_CREATE, TS_UPDATE, ID_WRKR_UPDATE) VALUES (?, ?, ?, ?, ?, ?, ?)";

		short nbSeq = getNextOffice();
		nbSeq++;

		PreparedStatement ps = null;
		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);

			ps = connection.prepareStatement(INSERT_SQL);

			java.sql.Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());

			ps.setShort(1, nbSeq);
			ps.setString(2, ro.getNmOffice());
			ps.setString(3, ro.getNmOfficeDesc());
			ps.setTimestamp(4, ts);
			ps.setString(5, ro.getIdWrkrCreate());
			ps.setTimestamp(6, ts);
			ps.setString(7, ro.getIdWrkrUpdate());
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

	public void updateOffice(ReferralOffice ro, String idWorker) throws BusinessLogicException, SQLException
	{

		Connection connection = null;
		String UPDATE_SQL = "UPDATE " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_OFFICE + " SET NM_OFFICE = ?, NM_OFFICE_DESC = ?, TS_UPDATE = ?, ID_WRKR_UPDATE = ? WHERE NB_SEQ = ?";

		PreparedStatement ps = null;
		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(UPDATE_SQL);

			java.sql.Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());

			ps.setString(1, ro.getNmOffice());
			ps.setString(2, ro.getNmOfficeDesc());
			ps.setTimestamp(3, ts);
			ps.setString(4, idWorker);
			ps.setShort(5, Short.parseShort(ro.getNbSeq()));
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

	public short getNextOffice() throws BusinessLogicException, SQLException
	{

		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		String MAX_SQL = "SELECT MAX(NB_SEQ) FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_OFFICE + "";

		short nbSeq = 0;

		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);

			statement = connection.createStatement();
			rs = statement.executeQuery(MAX_SQL);
			if (rs.next())
			{
				nbSeq = rs.getShort(1);
			}
		} catch (SQLException se)
		{
			throw new BusinessLogicException(se.getMessage());
		} finally
		{
			if (rs != null)
				rs.close();
			if (statement != null)
				statement.close();

			closeConnection(connection);
		}
		return nbSeq;
	}

	public String getMaxSource() throws BusinessLogicException, SQLException
	{

		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		String MAX_SQL = "SELECT MAX(ID_STAFF) FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_STAFF + "";

		String idStaff = "00000000";

		try
		{
			connection = dataSource.getConnection(user, password);
			connection.setAutoCommit(false);

			statement = connection.createStatement();
			rs = statement.executeQuery(MAX_SQL);
			if (rs.next())
			{
				idStaff = rs.getString(1);
			}
		} catch (SQLException se)
		{
			throw new BusinessLogicException(se.getMessage());
		} finally
		{
			if (rs != null)
				rs.close();
			if (statement != null)
				statement.close();

			closeConnection(connection);
		}

		return (idStaff == null ? "00000000" : idStaff);
	}
}
