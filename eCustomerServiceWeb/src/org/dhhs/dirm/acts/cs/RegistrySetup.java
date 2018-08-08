

package org.dhhs.dirm.acts.cs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.naming.Context;
import javax.sql.DataSource;

public class RegistrySetup
{

	/**
	 * Constructor for eCustomer Service Application Registry Setup
	 */
	public RegistrySetup() throws Exception
	{

		Connection connection = null;
		Context ctx = null;
		DataSource dataSource = null;

		try
		{

			Class.forName("hit.db2.Db2Driver");

			// connection = DriverManager.getConnection("jdbc:db2://" +
			// "149.168.32.233:50000" + ";rdbname=ecsts", "db2admin",
			// "Admindb2");
			connection = DriverManager.getConnection("jdbc:db2://" + "scca.sips.state.nc.us:5019" + ";rdbname=netsndb01;package_collection_id=fky_acts_eproject;commit_select=true;catalog_qualifier=FKY;auto_type_conversion=yes", "TS84CO4", "rama0704");

		} catch (Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}

		/*
		 * try {
		 * 
		 * String sql = "SELECT ID_PASSWORD FROM FKKT_CSESRV_USER ";
		 * 
		 * String query =
		 * "SELECT B.ID_WRKR_ASSIGN, B.ID_REFERENCE, B.CD_RESOLUTION, COUNT(*)"
		 * + "	FROM FKKT_CSESRV_FORMS A, " + "        FKKT_CSESRV_FRMTRK B " +
		 * "  WHERE DATE(A.TS_CREATE) BETWEEN ? AND ? " +
		 * "    AND B.ID_REFERENCE = A.ID_REFERENCE " +
		 * "    AND B.CD_STAT = A.CD_STAT " +
		 * "  ORDER BY B.ID_WRKR_ASSIGN, B.ID_REFERENCE " +
		 * "  GROUP BY B.ID_WRKR_ASSIGN, B.ID_REFERENCE, B.CD_RESOLUTION " +
		 * "  HAVING COUNT > 0 " ;
		 * 
		 * String query = "SELECT A.ID_REFERENCE, COUNT(*) " +
		 * "	FROM FKKT_CSESRV_FORMS A, " + "        FKKT_CSESRV_FRMTRK B " +
		 * "  WHERE DATE(A.TS_CREATE) BETWEEN ? AND ? " +
		 * "    AND B.ID_REFERENCE = A.ID_REFERENCE " +
		 * "    AND B.CD_STAT = A.CD_STAT " + "  ORDER BY A.ID_REFERENCE " +
		 * "  GROUP BY A.ID_REFERENCE " + " HAVING COUNT > 0 ";
		 */

		/*
		 * 
		 * String dateString = "05/01/2004"; java.util.Date parsedDate = null;
		 * 
		 * try { DateFormat df = new java.text.SimpleDateFormat("MM/dd/yyyy");
		 * parsedDate = df.parse(dateString); } catch (java.text.ParseException
		 * pe) { pe.printStackTrace(); }
		 * 
		 * Calendar cal = Calendar.getInstance(); cal.setTime(parsedDate);
		 * cal.set(Calendar.HOUR_OF_DAY,0); cal.set(Calendar.MINUTE,0);
		 * cal.set(Calendar.SECOND,0);
		 * 
		 * java.util.Date loDate = cal.getTime();
		 * 
		 * cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));
		 * cal.set(Calendar.HOUR_OF_DAY,23); cal.set(Calendar.MINUTE,59);
		 * cal.set(Calendar.SECOND,59); java.util.Date hiDate = cal.getTime();
		 * 
		 * java.sql.Timestamp startTS = new
		 * java.sql.Timestamp(((java.util.Date)loDate).getTime());
		 * java.sql.Timestamp endTS = new
		 * java.sql.Timestamp(((java.util.Date)hiDate).getTime());
		 * 
		 * 
		 * String sql = "SELECT A.ID_REFERENCE, A.TS_CREATE " +
		 * "	FROM FKKT_CSESRV_FORMS A " +
		 * "    WHERE A.TS_CREATE between ? and ?";
		 * 
		 * PreparedStatement s = connection.prepareStatement(query); //
		 * s.setTimestamp(1,startTS); // s.setTimestamp(2,endTS);
		 * s.setDate(1,new java.sql.Date(startTS.getTime())); s.setDate(2,new
		 * java.sql.Date(endTS.getTime()));
		 * 
		 * Statement s = connection.createStatement(); ResultSet rs =
		 * s.executeQuery(sql); while (rs.next()) {
		 * System.out.println(rs.getString(1) + "  - " +
		 * rs.getString(1).length()); } s.close(); connection.close();
		 * System.exit(0); } catch (SQLException e) {
		 * System.out.println(e.getMessage()); e.printStackTrace(); } finally {
		 * connection.close(); System.exit(0); }
		 */

		/**
		 * Perform a JNDI Lookup to obtain a datasource object
		 */

		java.util.Date now = new java.util.Date();

		try
		{

			connection.setAutoCommit(false);

			boolean dropTables = false;
			boolean createTables = false;
			boolean initializeTables = true;
			boolean deleteAll = false;

			if (dropTables)
			{
				Statement s = connection.createStatement();
				s.executeUpdate("DROP TABLE FKKT_CSESRV_USER");
				s.executeUpdate("DROP TABLE FKKT_CSESRV_PROF");
				s.executeUpdate("DROP TABLE FKKT_CSESRV_USRPRF");
				s.executeUpdate("DROP TABLE FKKT_CSESRV_FORMS");
				s.executeUpdate("DROP TABLE FKKT_CSESRV_FRMTRK");
				s.executeUpdate("DROP TABLE FKKT_CSESRV_RFRL");
				s.executeUpdate("DROP TABLE FKKT_CSESRV_PRCS");
				s.executeUpdate("DROP TABLE FKKT_CSESRV_RFRPRS ");
				s.executeUpdate("DROP TABLE FKKT_NOTES");
				s.executeUpdate("DROP TABLE FKKT_CSESRV_OFFICE");
				s.executeUpdate("DROP TABLE FKKT_CSESRV_STAFF");
				s.executeUpdate("DROP TABLE FKKT_CONTROL_NBR");
				s.close();
				System.out.println("Dropped successfully");
			}

			if (createTables)
			{

				/**
				 * Create Schema for FKKT_WORKER if not in the target database
				 */

				String worker = "CREATE TABLE FKKT_WORKER " + "(ID_WRKR                        CHAR(8) NOT NULL," + " CD_FIPS_WRKR                   CHAR(10) NOT NULL,      " + " NM_WRKR_L                      CHAR(17) NOT NULL,      " + " NM_WRKR_F                      CHAR(15) NOT NULL,      " + " NM_WRKR_MI                     CHAR(1) NOT NULL,       " + " ID_WRKR_LOGON                  CHAR(8) NOT NULL,       " + " CD_WRKR_TYPE                   CHAR(4) NOT NULL,       " + " NB_RFRL                        DECIMAL(10, 0) NOT NULL," + " NB_TEL_WRKR_ACD                CHAR(3) NOT NULL,       " + " NB_TEL_WRKR_EXC                CHAR(3) NOT NULL,       " + " NB_TEL_WRKR_LN                 CHAR(4) NOT NULL,       " + " ID_PRTR                        CHAR(8) NOT NULL,       " + " ID_TRML                        CHAR(8) NOT NULL,       " + " IN_MAIL_WRKLST                 CHAR(1) NOT NULL,       " + " DT_LST_UPD                     DATE NOT NULL,          " + " TM_LST_UPD                     TIME NOT NULL,          " + " ID_WRKR_LST_UPD                CHAR(8) NOT NULL,       " + " ID_TRML_LST_UPD                CHAR(8) NOT NULL,       " + " DT_HIRE                        DATE NOT NULL,          " + " TM_WRK_STRT                    TIME NOT NULL,          " + " TM_WRK_END                     TIME NOT NULL,          " + " TM_LUNCH_STRT                  TIME NOT NULL,          " + " TM_LUNCH_END                   TIME NOT NULL,          " + " DE_WRKR_TITLE                  CHAR(35) NOT NULL,      " + " ID_WRKR_SUP                    CHAR(8) NOT NULL,       " + " NB_TEL_WRKR_EXT                CHAR(6) NOT NULL,       " + " NB_FAX_WRKR_ACD                CHAR(3) NOT NULL,       " + " NB_FAX_WRKR_EXC                CHAR(3) NOT NULL,       " + " NB_FAX_WRKR_LN                 CHAR(4) NOT NULL,       " + " AD_INTERNET_WRKR               CHAR(35) NOT NULL,      " + " NB_RFRL_NIVD                   DECIMAL(10, 0) NOT NULL," + " IN_AOC_ORD                     CHAR(1) NOT NULL );";

				/**
				 * Create FKKT_CSESRV_USER table to hold users
				 */

				String user = "CREATE TABLE FKKT_CSESRV_USER ( " + "	ID_WRKR CHAR(8) NOT NULL," + "	ID_PROFILE CHAR(4) NOT NULL, " + "	CD_ACCPT_WRKLD CHAR(1) NOT NULL," + "	CD_APPROVAL_REQ CHAR(1) NOT NULL," + "	ID_PASSWORD VARCHAR(8) NOT NULL," + "	CD_PASSWORD_STATUS CHAR(1) NOT NULL, " + "	NB_OUTSTANDING INTEGER NOT NULL," + "	NB_COMPLETED INTEGER NOT NULL, " + "	NB_APPROVAL INTEGER NOT NULL, " + "	TS_CREATE TIMESTAMP NOT NULL, " + "	ID_WRKR_CREATE CHAR(8) NOT NULL, " + "	TS_UPDATE TIMESTAMP NOT NULL, " + "	ID_WRKR_UPDATE CHAR(8) NOT NULL, " + " CD_DT_RCVD CHAR(1) NOT NULL," + " CD_DT_COMP CHAR(1) NOT NULL," + " CD_DT_DUE CHAR(1) NOT NULL," + " CD_NB_CASE CHAR(1) NOT NULL," + " CD_ID_PART CHAR(1) NOT NULL," + " CD_NB_SSN CHAR(1) NOT NULL," + " CD_AGENT CHAR(1) NOT NULL," + " CD_REFERRAL_TYPE CHAR(1) NOT NULL," + " CD_EMAIL CHAR(1) NOT NULL," + " CD_CP CHAR(1) NOT NULL," + " CD_NCP CHAR(1) NOT NULL," + " CD_NB_CNTL CHAR(1) NOT NULL," + " CD_NM_CUSTOMER CHAR(1) NOT NULL," + " CD_NB_DKT CHAR(1) NOT NULL," + " CD_RFRL_SRC1 CHAR(1) NOT NULL," + " CD_RFRL_SRC2 CHAR(1) NOT NULL," + " CD_RFRL_SRC3 CHAR(1) NOT NULL," + " CD_RFRL_SRC4 CHAR(1) NOT NULL," + " CD_NM_CNTY CHAR(1) NOT NULL," + "	CONSTRAINT PK_USER PRIMARY KEY (ID_WRKR)" + ");";

				/**
				 * Create FKKT_CSESRV_PROF table to hold profiles
				 */

				String profile = "CREATE TABLE FKKT_CSESRV_PROF (" + "	ID_PROFILE CHAR(4) UNIQUE NOT NULL," + "	ID_PROFILE_DESC VARCHAR(30) NOT NULL," + " CD_MENU_ITEMS CHAR(25) NOT NULL, " + "	TS_CREATE TIMESTAMP NOT NULL, " + "	ID_WRKR_CREATE CHAR(8) NOT NULL, " + "	TS_UPDATE TIMESTAMP NOT NULL, " + "	ID_WRKR_UPDATE CHAR(8) NOT NULL, " + "	CONSTRAINT PK_GROUP PRIMARY KEY (ID_PROFILE)" + ");";

				/**
				 * Create FKKT_CSESRV_USRPRF table to hold users and profiles
				 */

				String userprof = "CREATE TABLE FKKT_CSESRV_USRPRF (" + "	ID_WRKR CHAR(8) NOT NULL," + "	ID_PROFILE CHAR(4) NOT NULL," + "	TS_CREATE TIMESTAMP NOT NULL, " + "	ID_WRKR_CREATE CHAR(8) NOT NULL, " + "	TS_UPDATE TIMESTAMP NOT NULL, " + "	ID_WRKR_UPDATE CHAR(8) NOT NULL, " + "	CONSTRAINT PK_MEMBER PRIMARY KEY (ID_WRKR, ID_PROFILE)" + ");";

				/**
				 * Create FKKT_CSESRV_FORMS table to hold feedback forms
				 * requests
				 */

				String forms = "CREATE TABLE FKKT_CSESRV_FORMS (" + "	ID_REFERENCE CHAR(10) NOT NULL," + "	CD_STAT CHAR(4) NOT NULL," + "	TS_CREATE TIMESTAMP NOT NULL, " + "	ID_WRKR_CREATE CHAR(8) NOT NULL, " + "	TS_UPDATE TIMESTAMP NOT NULL, " + "	ID_WRKR_UPDATE CHAR(8) NOT NULL, " + " CD_RFRL CHAR(4) NOT NULL," + " NM_COUNTY CHAR(25) NOT NULL, " + " ID_PART CHAR(10) NOT NULL," + " NB_CASE CHAR(10) NOT NULL," + " NB_SSN DECIMAL(9, 0) NOT NULL, " + " NB_DKT CHAR(17) NOT NULL, " + " NB_CONTROL CHAR(10) NOT NULL, " + " ID_EMAIL CHAR(50) NOT NULL," + " NB_TEL_ACD CHAR(3) NOT NULL," + " NB_TEL_EXC CHAR(3) NOT NULL," + " NB_TEL_LN CHAR(4) NOT NULL," + " NB_TEL_EXT CHAR(6) NOT NULL," + " NM_CP_L CHAR(17) NOT NULL, " + " NM_CP_F CHAR(15) NOT NULL, " + " NM_CP_M CHAR(1) NOT NULL, " + " NM_NCP_L CHAR(17) NOT NULL, " + " NM_NCP_F CHAR(15) NOT NULL, " + " NM_NCP_M CHAR(1) NOT NULL, " + " NM_CUSTOMER_L CHAR(17) NOT NULL, " + " NM_CUSTOMER_F CHAR(15) NOT NULL, " + " NM_CUSTOMER_M CHAR(1) NOT NULL, " + " ID_STAFF_1 CHAR(8) NOT NULL," + " ID_STAFF_2 CHAR(8) NOT NULL," + " ID_STAFF_3 CHAR(8) NOT NULL," + " ID_STAFF_4 CHAR(8) NOT NULL," + "	PRIMARY KEY (ID_REFERENCE)" + ");";

				/**
				 * Create FKKT_CSESRV_FRMTRK table to hold feedback forms
				 * requests
				 */

				String frmtrk = "CREATE TABLE FKKT_CSESRV_FRMTRK (" + "	ID_REFERENCE CHAR(10) NOT NULL," + "	TS_CREATE TIMESTAMP NOT NULL," + "	CD_STAT CHAR(4) NOT NULL, " + "	CD_RESOLUTION CHAR(2) NOT NULL, " + "	ID_WRKR_ASSIGN CHAR(8) NOT NULL, " + "	TS_WRKR_START TIMESTAMP NOT NULL, " + "	TS_WRKR_END TIMESTAMP NOT NULL, " + "	ID_NOTE DECIMAL(17, 0) NOT NULL, " + "	ID_WRKR_CREATE CHAR(8) NOT NULL, " + "	TS_UPDATE TIMESTAMP NOT NULL, " + "	ID_WRKR_UPDATE CHAR(8) NOT NULL, " + "	PRIMARY KEY (ID_REFERENCE,TS_CREATE)" + ");";

				/**
				 * Create FKKT_CSESRV_RFRL table to hold feedback forms requests
				 */

				String rfrl = "CREATE TABLE FKKT_CSESRV_RFRL (" + "	CD_RFRL CHAR(4) NOT NULL," + "	CD_DESC CHAR(30) NOT NULL," + "	TS_CREATE TIMESTAMP NOT NULL, " + "	ID_WRKR_CREATE CHAR(8) NOT NULL, " + "	TS_UPDATE TIMESTAMP NOT NULL, " + "	ID_WRKR_UPDATE CHAR(8) NOT NULL," + "	PRIMARY KEY (CD_RFRL)" + ");";

				/**
				 * Create FKKT_CSESRV_PRCS table to
				 */

				String prcs = "CREATE TABLE FKKT_CSESRV_PRCS (" + "	CD_PRCS CHAR(4) NOT NULL," + "	CD_DESC CHAR(30) NOT NULL," + "	TS_CREATE TIMESTAMP NOT NULL, " + "	ID_WRKR_CREATE CHAR(8) NOT NULL, " + "	TS_UPDATE TIMESTAMP NOT NULL, " + "	ID_WRKR_UPDATE CHAR(8) NOT NULL," + "	PRIMARY KEY (CD_PRCS)" + ");";

				/**
				 * Create FKKT_CSESRV_RFRPRS table to
				 */

				String rfrlprcs = "CREATE TABLE FKKT_CSESRV_RFRPRS  (" + "	CD_RFRL CHAR(4) NOT NULL," + "	CD_PRCS CHAR(4) NOT NULL," + "	NB_SEQ SMALLINT NOT NULL," + "	NB_DURATION SMALLINT NOT NULL," + "	TS_CREATE TIMESTAMP NOT NULL, " + "	ID_WRKR_CREATE CHAR(8) NOT NULL, " + "	TS_UPDATE TIMESTAMP NOT NULL, " + "	ID_WRKR_UPDATE CHAR(8) NOT NULL," + "	PRIMARY KEY (CD_RFRL,CD_PRCS)" + ");";

				/**
				 * Create FKKT_CONTROL_NBR table
				 */
				String cntlnbr = " CREATE TABLE FKKT_CONTROL_NBR (" + " ID_CNTL CHAR(4) NOT NULL, " + " NB_CNTL INTEGER NOT NULL)";

				String note = "CREATE TABLE FKKT_NOTES (" + " ID_NOTE DECIMAL(17, 0) NOT NULL," + " ID_NOTE_NXT DECIMAL(17, 0) NOT NULL," + " NM_NOTE_SEG_REF CHAR(35) NOT NULL," + " DT_LST_UPD DATE NOT NULL," + " TM_LST_UPD TIME NOT NULL, " + " ID_WRKR_LST_UPD CHAR(8) NOT NULL," + " ID_TRML_LST_UPD CHAR(8) NOT NULL," + " DE_NOTE_TXT VARCHAR(3000) NOT NULL, " + " PRIMARY KEY (ID_NOTE)" + ");";

				/**
				 * Create FKKT_CSESRV_OFFICE table
				 */
				String office = " CREATE TABLE FKKT_CSESRV_OFFICE (" + " NB_SEQ SMALLINT NOT NULL, " + " NM_OFFICE CHAR(6) NOT NULL, " + " NM_OFFICE_DESC CHAR(50) NOT NULL, " + " TS_CREATE TIMESTAMP NOT NULL, " + " ID_WRKR_CREATE CHAR(8) NOT NULL, " + " TS_UPDATE TIMESTAMP NOT NULL, " + " ID_WRKR_UPDATE CHAR(8) NOT NULL," + " PRIMARY KEY (NB_SEQ)" + ");";

				/**
				 * Create FKKT_CSESRV_STAFF table
				 */
				String staff = " CREATE TABLE FKKT_CSESRV_STAFF (" + " ID_STAFF CHAR(8) NOT NULL, " + " NB_SEQ SMALLINT NOT NULL, " + " NM_STAFF CHAR(35) NOT NULL, " + " NM_TITLE CHAR(50) NOT NULL, " + " CD_STATUS CHAR(1) NOT NULL, " + " TS_CREATE TIMESTAMP NOT NULL, " + " ID_WRKR_CREATE CHAR(8) NOT NULL, " + " TS_UPDATE TIMESTAMP NOT NULL, " + " ID_WRKR_UPDATE CHAR(8) NOT NULL," + " PRIMARY KEY (ID_STAFF)" + ");";

				/**
				 * Alter table FKKT_CSESRV_USRPRF to add constraint
				 */

				String altMember = "ALTER TABLE FKKT_CSESRV_USRPRF " + "ADD CONSTRAINT USERPROF_USER " + "FOREIGN KEY (ID_WRKR) " + "REFERENCES FKKT_CSESRV_USER (ID_WRKR);";

				/**
				 * Alter table FKKT_CSESRV_USRPRF to add constraint
				 */
				String altMember1 = "ALTER TABLE FKKT_CSESRV_USRPRF " + "ADD CONSTRAINT USERPROF_PROFILE " + "FOREIGN KEY (ID_PROFILE) " + "REFERENCES FKKT_CSESRV_PROF (ID_PROFILE);";

				Statement s = connection.createStatement();
				// int rc = s.executeUpdate(worker);
				// System.out.println("Return Code from Create Table " + worker
				// + " " + rc);
				// s.close();

				int rc = s.executeUpdate(user);
				System.out.println("Return Code from Create Table " + user + " " + rc);
				s.close();

				rc = s.executeUpdate(profile);
				System.out.println("Return Code from Create Table " + profile + " " + rc);
				s.close();

				rc = s.executeUpdate(userprof);
				System.out.println("Return Code from Create Table " + userprof + " " + rc);
				s.close();

				rc = s.executeUpdate(forms);
				System.out.println("Return Code from Create Table " + forms + " " + rc);
				s.close();

				rc = s.executeUpdate(frmtrk);
				System.out.println("Return Code from Create Table " + frmtrk + " " + rc);
				s.close();

				rc = s.executeUpdate(rfrl);
				System.out.println("Return Code from Create Table " + rfrl + " " + rc);
				s.close();

				rc = s.executeUpdate(prcs);
				System.out.println("Return Code from Create Table " + prcs + " " + rc);
				s.close();

				rc = s.executeUpdate(rfrlprcs);
				System.out.println("Return Code from Create Table " + rfrlprcs + " " + rc);
				s.close();

				rc = s.executeUpdate(cntlnbr);
				System.out.println("Return Code from Create Table " + cntlnbr + " " + rc);
				s.close();

				rc = s.executeUpdate(office);
				System.out.println("Return Code from Create Table " + office + " " + rc);
				s.close();

				rc = s.executeUpdate(staff);
				System.out.println("Return Code from Create Table " + staff + " " + rc);
				s.close();

				rc = s.executeUpdate(note);
				System.out.println("Return Code from Create Table " + note + " " + rc);
				s.close();

				rc = s.executeUpdate(altMember);
				System.out.println("Return Code from Alter Table " + altMember + " " + rc);
				s.close();

				rc = s.executeUpdate(altMember1);
				System.out.println("Return Code from Alter Table " + altMember + " " + rc);
				s.close();

			}

			String types[] = {"MAIL", "WEB", "PHN", "CFRM"};
			String desc[] = {"US Mail request", "Web request", "Phone request", "Complaint form request"};

			String steps[] = {"INIT", "OPEN", "CSWK", "APRV", "APCL", "APRT", "CORR", "TRAN", "DELT", "REQR", "CLSD"};

			String stepdesc[] = {"New Task", "Work in progress", "Case Worker", "Approval required", "Approved and closed", "Approved and returned", "Correction requested", "Transferred", "Deleted", "Approval requested", "Closed"};

			if (initializeTables)
			{

				Timestamp ts = new Timestamp(new java.util.Date().getTime());
				PreparedStatement pstmt = null;

				pstmt = connection.prepareStatement("INSERT INTO " + "FKY.FKKT_CSESRV_PROF (" + "	ID_PROFILE ," + "	ID_PROFILE_DESC ," + " CD_MENU_ITEMS, " + "	TS_CREATE , " + "	ID_WRKR_CREATE , " + "	TS_UPDATE , " + "	ID_WRKR_UPDATE)" + "	VALUES (" + " ? " + ",? " + ",? " + ",? " + ",? " + ",? " + ",?)");
				pstmt.clearParameters();
				pstmt.setString(1, "SSSM");
				pstmt.setString(2, "Security & System Maintenance");
				pstmt.setString(3, "TTTTTTTFFFFFFFFFFFFFFFFF");
				pstmt.setTimestamp(4, ts);
				pstmt.setString(5, "ACTS9999");
				pstmt.setTimestamp(6, ts);
				pstmt.setString(7, "ACTS9999");
				pstmt.executeUpdate();
				pstmt.close();

				System.out.println("Inserted a row in FKKT_CSESRV_PROF");

				pstmt = connection.prepareStatement("INSERT INTO " + "FKY.FKKT_CSESRV_USER (" + "	ID_WRKR," + "	CD_ACCPT_WRKLD," + "	CD_APPROVAL_REQ," + "	ID_PASSWORD," + "	CD_PASSWORD_STATUS," + "	NB_OUTSTANDING," + "	NB_COMPLETED, " + "	NB_APPROVAL, " + "	TS_CREATE, " + "	ID_WRKR_CREATE, " + "	TS_UPDATE, " + "	ID_WRKR_UPDATE," + "	ID_PROFILE," + " CD_DT_RCVD," + " CD_DT_COMP," + " CD_DT_DUE," + " CD_NB_CASE," + " CD_ID_PART," + " CD_NB_SSN," + " CD_AGENT," + " CD_REFERRAL_TYPE," + " CD_EMAIL," + " CD_CP," + " CD_NCP," + " CD_NB_CNTL," + " CD_NM_CUSTOMER," + " CD_NB_DKT," + " CD_RFRL_SRC1," + " CD_RFRL_SRC2," + " CD_RFRL_SRC3," + " CD_RFRL_SRC4," + " CD_NM_CNTY)" + "	VALUES (" + " ? " + ",? " + ",? " + ",? " + ",? " + ",? " + ",? " + ",? " + ",? " + ",? " + ",? " + ",? " + ",? " + ",? " + ",? " + ",? " + ",? " + ",? " + ",? " + ",? " + ",? " + ",? " + ",? " + ",? " + ",? " + ",? " + ",? " + ",? " + ",? " + ",? " + ",? " + ",?)");
				pstmt.clearParameters();
				pstmt.setString(1, "ACTS1004");
				pstmt.setString(2, "Y");
				pstmt.setString(3, "N");
				pstmt.setString(4, "password");
				pstmt.setString(5, "I");
				pstmt.setInt(6, 0);
				pstmt.setInt(7, 0);
				pstmt.setInt(8, 0);
				pstmt.setTimestamp(9, ts);
				pstmt.setString(10, "ACTS9999");
				pstmt.setTimestamp(11, ts);
				pstmt.setString(12, "ACTS9999");
				pstmt.setString(13, "SSSM");
				pstmt.setString(14, "Y");
				pstmt.setString(15, "Y");
				pstmt.setString(16, "Y");
				pstmt.setString(17, "Y");
				pstmt.setString(18, "Y");
				pstmt.setString(19, "Y");
				pstmt.setString(20, "Y");
				pstmt.setString(21, "Y");
				pstmt.setString(22, "Y");
				pstmt.setString(23, "Y");
				pstmt.setString(24, "Y");
				pstmt.setString(25, "Y");
				pstmt.setString(26, "Y");
				pstmt.setString(27, "Y");
				pstmt.setString(28, "Y");
				pstmt.setString(29, "Y");
				pstmt.setString(30, "Y");
				pstmt.setString(31, "Y");
				pstmt.setString(32, "");
				pstmt.executeUpdate();
				pstmt.close();

				System.out.println("Inserted a row in FKKT_CSESRV_USER");

				pstmt = connection.prepareStatement("INSERT INTO " + "FKY.FKKT_CSESRV_USRPRF (" + "	ID_WRKR," + "	ID_PROFILE," + "	TS_CREATE, " + "	ID_WRKR_CREATE, " + "	TS_UPDATE, " + "	ID_WRKR_UPDATE)" + "	VALUES (" + " ? " + ",? " + ",? " + ",? " + ",? " + ",?)");
				pstmt.clearParameters();
				pstmt.setString(1, "ACTS1004");
				pstmt.setString(2, "SSSM");
				pstmt.setTimestamp(3, ts);
				pstmt.setString(4, "ACTS9999");
				pstmt.setTimestamp(5, ts);
				pstmt.setString(6, "ACTS9999");
				pstmt.executeUpdate();
				pstmt.close();

				System.out.println("Inserted a row in FKKT_CSESRV_USRPRF");

				for (int i = 0; i < types.length; i++)
				{
					ts = new Timestamp(new java.util.Date().getTime());
					pstmt = connection.prepareStatement("INSERT INTO " + "FKY.FKKT_CSESRV_RFRL (" + "	CD_RFRL ," + "	CD_DESC ," + "	TS_CREATE , " + "	ID_WRKR_CREATE , " + "	TS_UPDATE , " + "	ID_WRKR_UPDATE)" + "	VALUES (" + " ? " + ",? " + ",? " + ",? " + ",? " + ",?)");
					pstmt.clearParameters();
					pstmt.setString(1, types[i]);
					pstmt.setString(2, desc[i]);
					pstmt.setTimestamp(3, ts);
					pstmt.setString(4, "ACTS9999");
					pstmt.setTimestamp(5, ts);
					pstmt.setString(6, "ACTS9999");
					pstmt.executeUpdate();
					pstmt.close();

					System.out.println("Inserted rows in FKKT_CSESRV_RFRL");

					int seq = 0;
					for (int j = 0; j < steps.length; j++)
					{
						seq++;
						pstmt = connection.prepareStatement("INSERT INTO " + "FKY.FKKT_CSESRV_RFRPRS  (" + "	CD_RFRL ," + "	CD_PRCS ," + " NB_SEQ," + " NB_DURATION, " + "	TS_CREATE , " + "	ID_WRKR_CREATE , " + "	TS_UPDATE , " + "	ID_WRKR_UPDATE)" + "	VALUES (" + " ? " + ",? " + ",? " + ",? " + ",? " + ",? " + ",? " + ",?)");
						pstmt.clearParameters();
						pstmt.setString(1, types[i]);
						pstmt.setString(2, steps[j]);
						pstmt.setShort(3, (short) seq);
						pstmt.setShort(4, (short) 0);
						pstmt.setTimestamp(5, ts);
						pstmt.setString(6, "ACTS9999");
						pstmt.setTimestamp(7, ts);
						pstmt.setString(8, "ACTS9999");
						pstmt.executeUpdate();
						pstmt.close();
						System.out.println("Inserted a row in FKKT_CSESRV_RFRPRS ");
					}

				}

				for (int i = 0; i < steps.length; i++)
				{
					ts = new Timestamp(new java.util.Date().getTime());
					pstmt = connection.prepareStatement("INSERT INTO " + "FKY.FKKT_CSESRV_PRCS (" + "	CD_PRCS ," + "	CD_DESC ," + "	TS_CREATE , " + "	ID_WRKR_CREATE , " + "	TS_UPDATE , " + "	ID_WRKR_UPDATE)" + "	VALUES (" + " ? " + ",? " + ",? " + ",? " + ",? " + ",?)");
					pstmt.clearParameters();
					pstmt.setString(1, steps[i]);
					pstmt.setString(2, stepdesc[i]);
					pstmt.setTimestamp(3, ts);
					pstmt.setString(4, "ACTS9999");
					pstmt.setTimestamp(5, ts);
					pstmt.setString(6, "ACTS9999");
					pstmt.executeUpdate();
					pstmt.close();
					System.out.println("Inserted a row in FKKT_CSESRV_PRCS");
				}

				pstmt = connection.prepareStatement("INSERT INTO " + "FKY.FKKT_CSESRV_CTLNBR (" + "	ID_CNTL ," + "	NB_CNTL)" + "	VALUES (" + "? " + ",?)");
				pstmt.clearParameters();
				pstmt.setString(1, "CSTS");
				pstmt.setInt(2, 0);
				pstmt.executeUpdate();
				System.out.println("Inserted a row in FKKT_CONTROL_NBR");
				pstmt.close();
			}

			if (deleteAll)
			{
				Statement s = connection.createStatement();
				s.executeUpdate("DELETE FROM FKKT_CSESRV_USRPRF");
				s.close();

				s = connection.createStatement();
				s.executeUpdate("DELETE FROM FKKT_CSESRV_PROF");
				s.close();

				s = connection.createStatement();
				s.executeUpdate("DELETE FROM FKKT_CSESRV_USER");
				s.close();

				s = connection.createStatement();
				s.executeUpdate("DELETE FROM FKKT_CSESRV_RFRL");
				s.close();

				s = connection.createStatement();
				s.executeUpdate("DELETE FROM FKKT_CSESRV_PRCS");
				s.close();

				s = connection.createStatement();
				s.executeUpdate("DELETE FROM FKKT_CSESRV_RFRPRS ");
				s.close();

				s = connection.createStatement();
				s.executeUpdate("DELETE FROM FKKT_CONTROL_NBR");
				s.close();

			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		try
		{
			if (!connection.isClosed())
			{
				connection.commit();
				connection.close();
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{

		try
		{
			new RegistrySetup();
		} catch (Exception e)
		{
		}
		System.exit(0);
	}
}
