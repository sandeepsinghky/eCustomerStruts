

package org.dhhs.dirm.acts.cs;

import java.util.Calendar;

/**
 * TimeframeTest.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Jul 1, 2004 11:40:20 AM
 * 
 * @author rkodumagulla
 *
 */
public class TimeframeTest
{

	/**
	 * Constructor for TimeframeTest.
	 */
	public TimeframeTest()
	{

		/*
		 * Connection connection = null; Context ctx = null; DataSource
		 * dataSource = null;
		 * 
		 * try {
		 * 
		 * Class.forName("hit.db2.Db2Driver");
		 * 
		 * connection = DriverManager.getConnection("jdbc:db2://" +
		 * "sccw.sips.state.nc.us:5005" +
		 * ";rdbname=netsndb52;package_collection_id=fkj_acts_projects;commit_select=true;catalog_qualifier=FKJ;auto_type_conversion=yes"
		 * ,"TS64S54","rama0504");
		 * 
		 * } catch (Exception e) { e.printStackTrace(); System.exit(0); }
		 * 
		 * String SELECT_TASK_SQL =
		 * "Select ID_REFERENCE, TS_CREATE FROM FKJ.FKKT_CSESRV_FORMS ORDER BY ID_REFERENCE "
		 * ;
		 * 
		 * String UPDATE_TASK_SQL =
		 * "UPDATE FKJ.FKKT_CSESRV_FORMS SET TS_ASSIGNED = ? WHERE ID_REFERENCE = ?"
		 * ;
		 * 
		 * try { connection.setAutoCommit(false); PreparedStatement ps =
		 * connection.prepareStatement(SELECT_TASK_SQL); ResultSet rs =
		 * ps.executeQuery();
		 * 
		 * while (rs.next()) { String idReference = rs.getString(1);
		 * java.sql.Timestamp ts = rs.getTimestamp(2); System.out.println(
		 * "Reference ID: " + idReference); System.out.println("Create TS: " +
		 * ts); System.out.println("Assign TS: " + getAssignedTimeStamp(ts));
		 * PreparedStatement ps1 = connection.prepareStatement(UPDATE_TASK_SQL);
		 * ps1.setTimestamp(1,getAssignedTimeStamp(ts)); ps1.setString(2,
		 * idReference); ps1.executeUpdate(); ps1.close(); } rs.close();
		 * ps.close(); } catch (SQLException e) { e.printStackTrace(); }
		 * 
		 * try { if (!connection.isClosed()) { connection.commit();
		 * connection.close(); } } catch (SQLException e) { e.printStackTrace();
		 * }
		 */

		Calendar rightNow = Calendar.getInstance();
		rightNow.set(Calendar.DATE, 31);
		rightNow.set(Calendar.HOUR_OF_DAY, 4);
		rightNow.set(Calendar.MINUTE, 31);
		rightNow.set(Calendar.SECOND, 0);
		java.util.Date now = rightNow.getTime();

		System.out.println("After Conversion: " + getAssignedTimeStamp(new java.sql.Timestamp(now.getTime())));
	}

	private java.sql.Timestamp getAssignedTimeStamp(java.sql.Timestamp ts)
	{

		java.util.Date dt = new java.util.Date(ts.getTime());
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.getTime();

		int DAY_OF_WEEK = rightNow.get(Calendar.DAY_OF_WEEK);
		int HOUR_OF_DAY = rightNow.get(Calendar.HOUR_OF_DAY);
		int MINUTE_OF_HOUR = rightNow.get(Calendar.MINUTE);

		System.out.println("Before Conversion: " + ts);

		switch (DAY_OF_WEEK)
		{
			case Calendar.SATURDAY :
				rightNow.add(Calendar.DAY_OF_MONTH, 2);
				rightNow.set(Calendar.HOUR_OF_DAY, 7);
				rightNow.set(Calendar.MINUTE, 0);
				rightNow.set(Calendar.SECOND, 0);
				java.util.Date now = rightNow.getTime();
				ts = new java.sql.Timestamp(now.getTime());
				return ts;
			case Calendar.SUNDAY :
				rightNow.add(Calendar.DAY_OF_MONTH, 1);
				rightNow.set(Calendar.HOUR_OF_DAY, 7);
				rightNow.set(Calendar.MINUTE, 0);
				rightNow.set(Calendar.SECOND, 0);
				now = rightNow.getTime();
				ts = new java.sql.Timestamp(now.getTime());
				return ts;
			default :
				break;
		}

		if ((HOUR_OF_DAY > 16 || (HOUR_OF_DAY == 16 && MINUTE_OF_HOUR > 30)) && HOUR_OF_DAY <= 24)
		{
			switch (DAY_OF_WEEK)
			{
				case Calendar.MONDAY :
				case Calendar.TUESDAY :
				case Calendar.WEDNESDAY :
				case Calendar.THURSDAY :
					rightNow.add(Calendar.DAY_OF_MONTH, 1);
					rightNow.set(Calendar.HOUR_OF_DAY, 7);
					rightNow.set(Calendar.MINUTE, 0);
					rightNow.set(Calendar.SECOND, 0);
					java.util.Date now = rightNow.getTime();
					ts = new java.sql.Timestamp(now.getTime());
					return ts;
				case Calendar.FRIDAY :
					rightNow.add(Calendar.DAY_OF_MONTH, 3);
					rightNow.set(Calendar.HOUR_OF_DAY, 7);
					rightNow.set(Calendar.MINUTE, 0);
					rightNow.set(Calendar.SECOND, 0);
					now = rightNow.getTime();
					ts = new java.sql.Timestamp(now.getTime());
					return ts;
				default :
					break;
			}
		}

		if (HOUR_OF_DAY >= 1 && HOUR_OF_DAY < 7)
		{
			switch (DAY_OF_WEEK)
			{
				case Calendar.MONDAY :
				case Calendar.TUESDAY :
				case Calendar.WEDNESDAY :
				case Calendar.THURSDAY :
				case Calendar.FRIDAY :
					rightNow.set(Calendar.HOUR_OF_DAY, 7);
					rightNow.set(Calendar.MINUTE, 0);
					rightNow.set(Calendar.SECOND, 0);
					java.util.Date now = rightNow.getTime();
					ts = new java.sql.Timestamp(now.getTime());
					return ts;
				default :
					break;
			}
		}

		return ts;
	}

	public static void main(String[] args)
	{

		new TimeframeTest();
	}
}
