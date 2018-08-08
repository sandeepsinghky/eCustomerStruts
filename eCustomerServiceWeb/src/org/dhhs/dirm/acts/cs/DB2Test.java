

package org.dhhs.dirm.acts.cs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.sql.DataSource;

public class DB2Test
{

	/**
	 * Constructor for eCustomer Service Application Registry Setup
	 */
	public DB2Test() throws Exception
	{

		Connection connection = null;
		Context ctx = null;
		DataSource dataSource = null;

		try
		{

			Class.forName("hit.db2.Db2Driver");

			connection = DriverManager.getConnection("jdbc:db2://" + "scca.sips.state.nc.us:5019" + ";rdbname=netsndb01;package_collection_id=fky_acts_eproject;commit_select=true;catalog_qualifier=FKY;auto_type_conversion=yes", "HVU1143", "z829acts");

		} catch (Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}

		try
		{
			String sql = "SELECT * FROM FKY.FKKT_CSESRV_WORKER ";

			Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next())
			{
				System.out.println(rs.getString(1));
			}
			rs.close();
			s.close();
		} catch (SQLException e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally
		{
			connection.commit();
			connection.close();
			System.exit(0);
		}

	}

	public static void main(String[] args)
	{
		try
		{
			new DB2Test();
		} catch (Exception e)
		{
		}
	}
}
