package org.dhhs.dirm.acts.cs.persister;

import java.util.*;
import java.math.BigDecimal;
import java.sql.*;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.dhhs.dirm.acts.cs.ejb.*;
import org.apache.log4j.Logger;
import org.dhhs.dirm.acts.cs.beans.*;
import org.dhhs.dirm.acts.cs.ejb.util.*;

/**
 * CSTaskPersister.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by
 * SYSTEMS RESEARCH & DEVELOPMENT INC.,
 * 
 * This is a helper class for handling the JDBC jobs for the BMP CSUser Entity EJB
 * 
 * 
 * Creation Date: Nov 17, 2003 10:31:31 AM
 * 
 * @author Rkodumagulla
 *
 * ===================================================================================== *
 *  Call Ticket | Modified the read for SELECT_TASK_SQL to add leading zeros to the 
 *    520496    | social security number before displaying the value to the user.
 *              |  
 * ===================================================================================== *
 */
public class CSTaskPersister {

	private static final Logger log = Logger.getLogger(CSTaskPersister.class);

	// sql datasource cached
	private DataSource ds = null;

	private static final java.sql.Timestamp TS_DEFAULT = new java.sql.Timestamp(0);

	// SQL statements

	// Get the current timestamp from DB2
	final String SELECT_TS_SQL = "SELECT CURRENT TIMESTAMP FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_CNTLNBR;

	// Get the next control number
	final String SELECT_CNNB_SQL =
		"SELECT NB_CNTL, ID_CNTL FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_CNTLNBR + " WHERE ID_CNTL = 'CSTS'";

	// Update the control number
	final String UPDATE_CNNB_SQL = "UPDATE " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_CNTLNBR + " SET NB_CNTL = ? WHERE ID_CNTL = ?";

	// Get the worker that has the minimal tasks
	/**
	 * RK CT# 
	 */
	final String SELECT_MIN_WRKR_SQL =
		"SELECT ID_WRKR, NB_OUTSTANDING FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_USER
			+ " WHERE CD_ACCPT_WRKLD = 'Y' "
			+ "   AND NB_OUTSTANDING = (SELECT MIN(NB_OUTSTANDING) FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_USER
			+ " WHERE CD_ACCPT_WRKLD = 'Y')";

	// Get ALL agentS to load to the AgentDelegate list
	final String SELECT_ALL_WRKR_SQL =
		"SELECT ID_WRKR, CD_ACCPT_WRKLD FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_USER + " ORDER BY ID_WRKR";

	// Get agent who can accept workload
	final String SELECT_WRKR_SQL =
		"SELECT ID_WRKR, NB_OUTSTANDING FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_USER
			+ " WHERE ID_WRKR = ? AND CD_ACCPT_WRKLD = 'Y'";

	// get task by reference id
	/**
	 * RK CT# 521456 - Search on a specific task that has been deleted is displaying totally
	 * unrelated task. We must exclude tasks that are deleted
	 * 08/12/04
	 */
	final String SELECT_FORM_SQL =
		"SELECT ID_REFERENCE FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " WHERE ID_REFERENCE = ?"
			+ "   AND CD_STAT <> 'DELT'";

	// Insert a row in form
	final String INSERT_FORM_SQL =
		"INSERT INTO "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " (ID_REFERENCE, CD_STAT, TS_CREATE, ID_WRKR_CREATE, TS_UPDATE, ID_WRKR_UPDATE, CD_RFRL, ID_PART, NB_CASE, NB_SSN, NB_DKT, ID_EMAIL, NB_TEL_ACD, NB_TEL_EXC, NB_TEL_LN, NB_TEL_EXT, NM_COUNTY, NB_CONTROL, NM_CP_L, NM_CP_F, NM_CP_M, NM_NCP_L, NM_NCP_F, NM_NCP_M, NM_CUSTOMER_L, NM_CUSTOMER_F, NM_CUSTOMER_M, ID_STAFF_1, ID_STAFF_2, ID_STAFF_3, ID_STAFF_4, MO_CREATE, YR_CREATE, TS_ASSIGNED) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	// Insert a row in form tracking table
	final String INSERT_FRMTRK_SQL =
		"INSERT INTO "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " (ID_REFERENCE, TS_CREATE, CD_STAT, CD_RESOLUTION, ID_WRKR_ASSIGN, TS_WRKR_START, TS_WRKR_END, ID_NOTE, ID_WRKR_CREATE, TS_UPDATE, ID_WRKR_UPDATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	// Insert a row in notes table
	final String INSERT_NOTE_SQL =
		"INSERT INTO "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_NOTES
			+ " (ID_NOTE, ID_NOTE_NXT, NM_NOTE_SEG_REF, DT_LST_UPD, TM_LST_UPD, ID_WRKR_LST_UPD, ID_TRML_LST_UPD, DE_NOTE_TXT) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	// Update outstanding task count for worker
	final String UPDATE_OUTSTANDING_SQL =
		"UPDATE " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_USER + " SET NB_OUTSTANDING = ? WHERE ID_WRKR = ?";

	// Update completed task count for worker
	final String UPDATE_COMPLETED_SQL =
		"UPDATE " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_USER + " SET NB_COMPLETED = ? WHERE ID_WRKR = ?";

	// Update pending approval count for worker
	final String UPDATE_APPROVAL_SQL =
		"UPDATE " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_USER + " SET NB_APPROVAL = ? WHERE ID_WRKR = ?";

	// Select all pending tasks from forms table
	final String SELECT_PENDALL_SQL =
		"SELECT ID_REFERENCE FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " WHERE CD_STAT IN ('APRV','REQR')";

	// Select all tasks from forms table
	final String SELECT_ALL_SQL =
		"SELECT ID_REFERENCE FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " WHERE CD_STAT <> 'DELT'";

	// Process Usage Count
	final String COUNT_PRCS_USAGE_SQL =
		"SELECT COUNT(*) FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " A, "
			+ "  "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " B "
			+ " WHERE A.CD_RFRL = ? "
			+ "   AND A.CD_STAT = ? "
			+ "   AND B.ID_REFERENCE = A.ID_REFERENCE "
			+ "   AND B.CD_STAT = A.CD_STAT ";

	// Referral Usage count
	final String COUNT_FORM_USAGE_SQL =
		"SELECT COUNT(*) FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " WHERE CD_RFRL = ? ";

	// Total Tasks for agent
	final String COUNT_ALL_SQL =
		"SELECT COUNT(*) FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " A, "
			+ "  "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " B "
			+ " WHERE B.ID_WRKR_ASSIGN = ? "
			+ "   AND B.ID_REFERENCE = A.ID_REFERENCE "
			+ "   AND A.CD_STAT <> 'DELT' "
			+ "   AND B.CD_STAT = A.CD_STAT "
			+ "   AND (B.CD_STAT = 'CLSD' OR (B.CD_STAT NOT IN ('CLSD','TRAN') AND B.TS_WRKR_END = ?))";

	// Count of tasks grouped by agent
	final String GROUP_ALL_SQL =
		"SELECT COUNT(*), B.ID_WRKR_ASSIGN "
			+ "	FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " A, "
			+ "       "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " B "
			+ " WHERE B.ID_REFERENCE = A.ID_REFERENCE "
			+ "   AND A.CD_STAT <> 'DELT' "
			+ "   AND B.CD_STAT = A.CD_STAT "
			+ "   AND (B.CD_STAT = 'CLSD' OR (B.CD_STAT NOT IN ('CLSD','TRAN') AND B.TS_WRKR_END = ?))"
			+ " GROUP BY B.ID_WRKR_ASSIGN "
			+ " ORDER BY B.ID_WRKR_ASSIGN";

	// Count of tasks grouped by pending approval for agent
	final String GROUP_APPROVAL_SQL =
		"SELECT COUNT(*), B.ID_WRKR_ASSIGN "
			+ "	FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " A, "
			+ "       "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " B "
			+ " WHERE B.ID_REFERENCE = A.ID_REFERENCE "
			+ "   AND A.CD_STAT IN ('APRV','REQR') "
			+ "   AND B.CD_STAT = A.CD_STAT "
		// Added TS_WRKR_END as this will be default timestamp when it is still pending			
	+"   AND B.TS_WRKR_END = ? " + " GROUP BY B.ID_WRKR_ASSIGN " + " ORDER BY B.ID_WRKR_ASSIGN";

	// Count of tasks grouped by outstanding for agent
	final String GROUP_OUTSTANDING_SQL =
		"SELECT COUNT(*), B.ID_WRKR_ASSIGN "
			+ "	FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " A, "
			+ "       "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " B "
			+ " WHERE B.ID_REFERENCE = A.ID_REFERENCE "
			+ "   AND A.CD_STAT NOT IN ('DELT','CLSD') "
			+ "   AND B.CD_STAT = A.CD_STAT "
		// Added TS_WRKR_END as this will be default timestamp when it is still outstanding	
	+"   AND B.TS_WRKR_END = ? " + " GROUP BY B.ID_WRKR_ASSIGN " + " ORDER BY B.ID_WRKR_ASSIGN";

	// Count of tasks grouped by completed
	final String GROUP_COMPLETED_SQL =
		"SELECT COUNT(*), B.ID_WRKR_ASSIGN "
			+ "	FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " A, "
			+ "       "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " B "
			+ " WHERE B.ID_REFERENCE = A.ID_REFERENCE "
			+ "   AND A.CD_STAT = 'CLSD' "
			+ "   AND B.CD_STAT = A.CD_STAT "
			+ " GROUP BY B.ID_WRKR_ASSIGN "
			+ " ORDER BY B.ID_WRKR_ASSIGN";

	// Get all tasks pending approval	
	final String SELECT_APPROVAL_SQL =
		"SELECT ID_REFERENCE FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " WHERE CD_STAT IN ('REQR','APRV')";

	// Count of tasks pending approval for worker
	final String COUNT_APPROVAL_SQL =
		"SELECT COUNT(*) FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " A, "
			+ "  "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " B "
			+ " WHERE B.ID_WRKR_ASSIGN = ? "
			+ "   AND B.ID_REFERENCE = A.ID_REFERENCE "
			+ "   AND A.CD_STAT IN ('REQR','APRV') "
			+ "   AND B.CD_STAT = A.CD_STAT "
		// Added TS_WRKR_END as this will be default timestamp when it is still pending
	+"   AND B.TS_WRKR_END = ?";

	// Get all tasks outstanding
	final String SELECT_OUTSTANDING_SQL =
		"SELECT ID_REFERENCE FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " WHERE CD_STAT NOT IN ('DELT','CLSD')";

	// Count of tasks that are outstanding for agent
	final String COUNT_OUTSTANDING_SQL =
		"SELECT COUNT(*) FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " A, "
			+ "  "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " B "
			+ " WHERE B.ID_WRKR_ASSIGN = ? "
			+ "   AND B.ID_REFERENCE = A.ID_REFERENCE "
			+ "   AND A.CD_STAT NOT IN ('DELT','CLSD')"
			+ "   AND B.CD_STAT = A.CD_STAT "
		// Added TS_WRKR_END as this will be default timestamp when it is still pending or outstanding
	+"   AND B.TS_WRKR_END = ? ";

	// Count of tasks that are closed for agent
	final String COUNT_COMPLETED_SQL =
		"SELECT COUNT(*) FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " A, "
			+ "  "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " B "
			+ " WHERE B.ID_WRKR_ASSIGN = ? "
			+ "   AND B.ID_REFERENCE = A.ID_REFERENCE "
			+ "   AND B.CD_STAT = A.CD_STAT "
			+ "   AND A.CD_STAT = 'CLSD'";

	// Get all pending tasks for agent			
	final String SELECT_PENDWRKRTSKS_SQL =
		"SELECT A.ID_REFERENCE "
			+ "	FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " A, "
			+ "        "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " B "
			+ "  WHERE A.CD_STAT IN ('APRV','REQR') "
			+ "    AND B.ID_REFERENCE = A.ID_REFERENCE "
			+ "    AND B.CD_STAT = A.CD_STAT "
			+ "    AND B.ID_WRKR_ASSIGN = ? "
			+ "    AND B.TS_WRKR_END = ?";

	// Get all tasks for agent			
	final String SELECT_WRKRTSKS_SQL =
		"SELECT A.ID_REFERENCE "
			+ "	FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " A, "
			+ "        "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " B "
			+ "  WHERE B.ID_REFERENCE = A.ID_REFERENCE "
			+ "   AND A.CD_STAT <> 'DELT' "
			+ "   AND B.CD_STAT = A.CD_STAT "
			+ "   AND B.ID_WRKR_ASSIGN = ? "
			+ "   AND (B.CD_STAT = 'CLSD' OR (B.CD_STAT NOT IN ('CLSD','TRAN') AND B.TS_WRKR_END = ?)) "
			+ " ORDER BY A.TS_CREATE DESC";

	// Get all tasks that are created on a given date
	final String SELECT_DT_CRE_SQL =
		"SELECT A.ID_REFERENCE "
			+ "	 FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " A"
			+ " WHERE DATE(A.TS_CREATE) = ? "
			+ "   AND A.CD_STAT <> 'DELT' ";

	// Get all tasks that are created on a given date for a worker
	final String SELECT_DT_CRE_WRKR_SQL =
		"SELECT A.ID_REFERENCE "
			+ "	 FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " A, "
			+ "  "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " B "
			+ " WHERE DATE(A.TS_CREATE) = ? "
			+ "   AND B.ID_REFERENCE = A.ID_REFERENCE "
			+ "   AND A.CD_STAT <> 'DELT' "
			+ "   AND B.CD_STAT = A.CD_STAT "
			+ "   AND B.ID_WRKR_ASSIGN = ? "
			+ "   AND (B.CD_STAT = 'CLSD' OR (B.CD_STAT NOT IN ('CLSD','TRAN') AND B.TS_WRKR_END = ? ))";

	// Get all tasks that were created between a date range for a worker
	final String SELECT_DT_RANGE_WRKR_SQL =
		"SELECT A.ID_REFERENCE "
			+ "	 FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " A, "
			+ "  "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " B "
			+ " WHERE DATE(A.TS_CREATE)  between ?  and ? "
			+ "   AND B.ID_REFERENCE = A.ID_REFERENCE "
			+ "   AND A.CD_STAT <> 'DELT' "
			+ "   AND B.CD_STAT = A.CD_STAT "
			+ "   AND B.ID_WRKR_ASSIGN = ? "
			+ "   AND (B.CD_STAT = 'CLSD' OR (B.CD_STAT NOT IN ('CLSD','TRAN') AND B.TS_WRKR_END = ? ))";

	// Get all tasks that are completed on a given date
	final String SELECT_DT_COMPLETED_SQL =
		"SELECT A.ID_REFERENCE FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " A, "
			+ "  "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " B "
			+ " WHERE A.CD_STAT = 'CLSD' "
			+ "   AND B.ID_REFERENCE = A.ID_REFERENCE "
			+ "   AND B.CD_STAT = A.CD_STAT "
			+ "   AND DATE(B.TS_CREATE) = ? ";

	// Get all tasks that are completed on a given date for a worker
	final String SELECT_DT_COMPLETED_WRKR_SQL =
		"SELECT A.ID_REFERENCE FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " A, "
			+ "  "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " B "
			+ " WHERE A.CD_STAT = 'CLSD' "
			+ "   AND B.ID_REFERENCE = A.ID_REFERENCE "
			+ "   AND B.CD_STAT = A.CD_STAT "
			+ "   AND DATE(B.TS_CREATE) = ? "
			+ "   AND B.ID_WRKR_ASSIGN = ? ";

	// Get all tasks that for a referral type
	final String SELECT_RFRLTSKS_SQL =
		"SELECT ID_REFERENCE FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " WHERE CD_RFRL = ?"
			+ "   AND CD_STAT <> 'DELT' ";

	// Get all tasks pending approval that for a referral type
	final String SELECT_PENDRFRLTSKS_SQL =
		"SELECT ID_REFERENCE FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " WHERE CD_RFRL = ?"
			+ "   AND CD_STAT IN ('APRV','REQR') ";

	// Get all tasks that for a referral type for a worker
	final String SELECT_RFRLTSKS_WRKR_SQL =
		"SELECT A.ID_REFERENCE "
			+ "  FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " A, "
			+ "  "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " B "
			+ " WHERE A.CD_RFRL = ?"
			+ "   AND B.ID_REFERENCE = A.ID_REFERENCE "
			+ "   AND A.CD_STAT <> 'DELT' "
			+ "   AND B.CD_STAT = A.CD_STAT "
			+ "   AND B.ID_WRKR_ASSIGN = ? "
			+ "   AND (B.CD_STAT = 'CLSD' OR (B.CD_STAT NOT IN ('CLSD','TRAN') AND B.TS_WRKR_END = ? ))";

	// Get all tasks that have a case number
	final String SELECT_CASETSKS_SQL =
		"SELECT ID_REFERENCE FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " WHERE NB_CASE = ?"
			+ "   AND CD_STAT <> 'DELT' ";

	// Get all tasks that have a case number for a worker
	final String SELECT_CASETSKS_WRKR_SQL =
		"SELECT A.ID_REFERENCE "
			+ "  FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " A, "
			+ "  "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " B "
			+ " WHERE NB_CASE = ?"
			+ "   AND B.ID_REFERENCE = A.ID_REFERENCE "
			+ "   AND A.CD_STAT <> 'DELT' "
			+ "   AND B.CD_STAT = A.CD_STAT "
			+ "   AND B.ID_WRKR_ASSIGN = ? "
			+ "   AND (B.CD_STAT = 'CLSD' OR (B.CD_STAT NOT IN ('CLSD','TRAN') AND B.TS_WRKR_END = ?))";

	// Get all tasks that have a mpi number
	final String SELECT_MPITSKS_SQL =
		"SELECT ID_REFERENCE FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " WHERE ID_PART = ?"
			+ "   AND CD_STAT <> 'DELT' ";

	// Get all tasks that have a mpi number for a worker
	final String SELECT_MPITSKS_WRKR_SQL =
		"SELECT A.ID_REFERENCE "
			+ "  FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " A, "
			+ "  "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " B "
			+ " WHERE ID_PART = ?"
			+ "   AND B.ID_REFERENCE = A.ID_REFERENCE "
			+ "   AND A.CD_STAT <> 'DELT' "
			+ "   AND B.CD_STAT = A.CD_STAT "
			+ "   AND B.ID_WRKR_ASSIGN = ? "
			+ "   AND (B.CD_STAT = 'CLSD' OR (B.CD_STAT NOT IN ('CLSD','TRAN') AND B.TS_WRKR_END = ?))";

	// Get all tasks that have a docket number
	final String SELECT_DKTTSKS_SQL =
		"SELECT ID_REFERENCE FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " WHERE NB_DKT = ?"
			+ "   AND CD_STAT <> 'DELT' ";

	// Get all tasks that have a docket number for a worker
	final String SELECT_DKTTSKS_WRKR_SQL =
		"SELECT A.ID_REFERENCE "
			+ "  FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " A, "
			+ "  "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " B "
			+ " WHERE NB_DKT = ?"
			+ "   AND B.ID_REFERENCE = A.ID_REFERENCE "
			+ "   AND A.CD_STAT <> 'DELT' "
			+ "   AND B.CD_STAT = A.CD_STAT "
			+ "   AND B.ID_WRKR_ASSIGN = ? "
			+ "   AND (B.CD_STAT = 'CLSD' OR (B.CD_STAT NOT IN ('CLSD','TRAN') AND B.TS_WRKR_END = ?))";

	// Get all tasks that have an email
	final String SELECT_EMAILTSKS_SQL =
		"SELECT ID_REFERENCE FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " WHERE ID_EMAIL = ?"
			+ "   AND CD_STAT <> 'DELT' ";

	// Get all tasks that have an email for a worker
	final String SELECT_EMAILTSKS_WRKR_SQL =
		"SELECT A.ID_REFERENCE "
			+ "  FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " A, "
			+ "  "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " B "
			+ " WHERE ID_EMAIL = ?"
			+ "   AND B.ID_REFERENCE = A.ID_REFERENCE "
			+ "   AND A.CD_STAT <> 'DELT' "
			+ "   AND B.CD_STAT = A.CD_STAT "
			+ "   AND B.ID_WRKR_ASSIGN = ? "
			+ "   AND (B.CD_STAT = 'CLSD' OR (B.CD_STAT NOT IN ('CLSD','TRAN') AND B.TS_WRKR_END = ?))";

	// Get all tasks that have the customer last name
	final String SELECT_CUSTOMERLTSKS_SQL =
		"SELECT ID_REFERENCE FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " WHERE UCASE(NM_CUSTOMER_L) = ?"
			+ "   AND CD_STAT <> 'DELT' ";

	// Get all tasks that have the customer last name for a worker
	final String SELECT_CUSTOMERLTSKS_WRKR_SQL =
		"SELECT A.ID_REFERENCE "
			+ "  FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " A, "
			+ "  "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " B "
			+ " WHERE UCASE(NM_CUSTOMER_L) = ?"
			+ "   AND B.ID_REFERENCE = A.ID_REFERENCE "
			+ "   AND A.CD_STAT <> 'DELT' "
			+ "   AND B.CD_STAT = A.CD_STAT "
			+ "   AND B.ID_WRKR_ASSIGN = ? "
			+ "   AND (B.CD_STAT = 'CLSD' OR (B.CD_STAT NOT IN ('CLSD','TRAN') AND B.TS_WRKR_END = ?))";

	// Get all tasks that have the customer last name
	final String SELECT_CUSTOMERLFTSKS_SQL =
		"SELECT ID_REFERENCE FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " WHERE UCASE(NM_CUSTOMER_L) = ?"
			+ "   AND UCASE(NM_CUSTOMER_F) = ?"
			+ "   AND CD_STAT <> 'DELT' ";

	// Get all tasks that have the customer last name for a worker
	final String SELECT_CUSTOMERLFTSKS_WRKR_SQL =
		"SELECT A.ID_REFERENCE "
			+ "  FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " A, "
			+ "  "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " B "
			+ " WHERE UCASE(NM_CUSTOMER_L) = ?"
			+ "   AND UCASE(NM_CUSTOMER_F) = ?"
			+ "   AND B.ID_REFERENCE = A.ID_REFERENCE "
			+ "   AND A.CD_STAT <> 'DELT' "
			+ "   AND B.CD_STAT = A.CD_STAT "
			+ "   AND B.ID_WRKR_ASSIGN = ? "
			+ "   AND (B.CD_STAT = 'CLSD' OR (B.CD_STAT NOT IN ('CLSD','TRAN') AND B.TS_WRKR_END = ?))";

	// Update a task status
	final String UPDATE_TASKSTAT_SQL =
		"  UPDATE "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ "     SET CD_STAT = ?, TS_UPDATE = ?, ID_WRKR_UPDATE = ?, "
			+ "         ID_PART = ?, NB_CASE = ?, NB_SSN = ?, NB_DKT = ?, "
			+ "         ID_EMAIL = ?, NB_TEL_ACD = ?, NB_TEL_EXC = ?, NB_TEL_LN = ?, "
			+ "         NB_TEL_EXT = ?, NM_COUNTY = ?, NB_CONTROL = ?, NM_CP_L = ?, "
			+ "         NM_CP_F = ?, NM_CP_M = ?, NM_NCP_L = ?, NM_NCP_F = ?, NM_NCP_M = ?, "
			+ "         NM_CUSTOMER_L = ?, NM_CUSTOMER_F = ?, NM_CUSTOMER_M = ?"
			+ "   WHERE ID_REFERENCE = ? ";

	// Update a task
	final String UPDATE_TASK_SQL =
		"UPDATE "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " "
			+ " SET CD_STAT = ?, TS_CREATE = ?, ID_WRKR_CREATE = ?, "
			+ " TS_UPDATE = ?, ID_WRKR_UPDATE = ?, CD_RFRL = ?, "
			+ " ID_PART = ?, NB_CASE = ?, NB_DKT = ?, "
			+ " ID_EMAIL = ?, NB_TEL_ACD = ?, NB_TEL_EXC = ?, NB_TEL_LN = ?, "
			+ " NB_TEL_EXT = ? , NM_COUNTY = ? , NB_CONTROL = ? , NM_CP_L = ? , NM_CP_F = ?, NM_CP_M = ? , "
			+ " NM_NCP_L = ?, NM_NCP_F = ?, NM_NCP_M = ?, NM_CUSTOMER_L = ?, "
			+ " NM_CUSTOMER_F = ?, NM_CUSTOMER_M = ?, ID_STAFF_1 = ?, ID_STAFF_2 = ?, ID_STAFF_3 = ?, ID_STAFF_4 = ? "
			+ " WHERE ID_REFERENCE = ? ";

	// Update a task HISTORY (Used in StoreState)
	final String UPDATE_TASK_HIST_SQL =
		"UPDATE "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " "
			+ " SET CD_STAT = ?, CD_RESOLUTION = ?, ID_WRKR_ASSIGN = ?, "
			+ " TS_WRKR_START = ?, TS_WRKR_END = ?, ID_WRKR_CREATE = ?, "
			+ " TS_UPDATE = ?, ID_WRKR_UPDATE = ? "
			+ " WHERE ID_REFERENCE = ? "
			+ "   AND TS_CREATE = ? ";

	// update task history
	final String UPDATE_TASKHIST_SQL =
		"UPDATE "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " "
			+ " SET TS_WRKR_END = ?, TS_UPDATE = ?, ID_WRKR_UPDATE = ? "
			+ " WHERE ID_REFERENCE = ? "
			+ "   AND TS_CREATE = (SELECT MAX(TS_CREATE) FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " WHERE ID_REFERENCE = ?)";

	// get task detail
	final String SELECT_TASK_SQL =
		"SELECT A.ID_REFERENCE, A.CD_STAT, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, "
			+ "      A.ID_WRKR_UPDATE, A.CD_RFRL, A.ID_PART, A.NB_CASE, A.NB_SSN, A.NB_DKT, "
			+ "      A.ID_EMAIL, A.NB_TEL_ACD, A.NB_TEL_EXC, A.NB_TEL_LN, A.NB_TEL_EXT, "
			+ " 	 A.NM_COUNTY, A.NB_CONTROL, A.NM_CP_L, A.NM_CP_F, A.NM_CP_M, "
			+ "      A.NM_NCP_L, A.NM_NCP_F, A.NM_NCP_M, A.NM_CUSTOMER_L, "
			+ "      A.NM_CUSTOMER_F, A.NM_CUSTOMER_M, A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, B.NM_WRKR_F, B.NM_WRKR_L, B.NM_WRKR_MI, C.ID_WRKR_ASSIGN, A.TS_ASSIGNED "
			+ " FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " A, "
			+ "      "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " C, "
			+ "      "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_WORKER
			+ " B "
			+ " WHERE A.ID_REFERENCE = ? "
			+ "   AND C.ID_REFERENCE = A.ID_REFERENCE "
			+ "   AND A.CD_STAT <> 'DELT' "
			+ "   AND C.CD_STAT = A.CD_STAT "
			+ "   AND B.ID_WRKR = C.ID_WRKR_ASSIGN "
			+ "   AND (C.CD_STAT = 'CLSD' OR (C.CD_STAT NOT IN ('CLSD','TRAN') AND C.TS_WRKR_END = ?))";

	// get task history detail
	final String SELECT_TRKHIST_SQL =
		"SELECT A.ID_REFERENCE, A.TS_CREATE, A.CD_STAT, A.ID_WRKR_ASSIGN, "
			+ "       A.TS_WRKR_START, A.TS_WRKR_END, A.ID_WRKR_CREATE, "
			+ "       A.TS_UPDATE, A.ID_WRKR_UPDATE, B.DE_NOTE_TXT, C.NM_WRKR_L, C.NM_WRKR_F, C.NM_WRKR_MI "
			+ "  FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " A, "
			+ "       "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_NOTES
			+ " B, "
			+ "      "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_WORKER
			+ " C "
			+ " WHERE A.ID_REFERENCE = ? "
			+ "   AND B.ID_NOTE = A.ID_NOTE "
			+ "   AND C.ID_WRKR = A.ID_WRKR_ASSIGN "
			+ " ORDER BY A.TS_CREATE DESC ";

	// get duration for a referral process
	final String SELECT_DUEDATE_SQL =
		"SELECT NB_DURATION FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_REFERRAL_PROCESS
			+ " WHERE CD_RFRL = ? "
			+ "   AND CD_PRCS = ? ";

	// get task	by due date
	final String SELECT_DT_DUE_SQL =
		"SELECT A.ID_REFERENCE, DATE(A.TS_CREATE), A.CD_RFRL, A.CD_STAT FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " A "
			+ " WHERE A.CD_STAT <> 'DELT' "
			+ " ORDER BY TS_CREATE";

	// get task	by due date for a worker
	final String SELECT_DT_DUE_WRKR_SQL =
		"SELECT A.ID_REFERENCE, DATE(A.TS_CREATE), A.CD_RFRL, A.CD_STAT "
			+ "  FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " A, "
			+ "  "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " B "
			+ " WHERE B.ID_REFERENCE = A.ID_REFERENCE "
			+ "   AND A.CD_STAT <> 'DELT' "
			+ "   AND B.CD_STAT = A.CD_STAT "
			+ "   AND B.ID_WRKR_ASSIGN = ? "
			+ "   AND (B.CD_STAT <> 'CLSD' AND B.TS_WRKR_END = ?)"
			+ "  ORDER BY A.TS_CREATE";

	/**
	 * MOD# 2870 - 09/02/2004 rkodumagulla - Begin
	 * In order to prevent duplicate tasks from the same customer being assigned to
	 * different workers, the system should use matching criteria such as last name
	 * and email address over a 4 day period and assign the task to the same worker
	 */
	final String MATCH_AGNT_4DAY_TASK =
		"SELECT A.ID_REFERENCE, B.ID_WRKR_ASSIGN , A.CD_STAT "
			+ "  FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FORMS
			+ " A, "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " B "
			+ " WHERE DATE(A.TS_CREATE)  BETWEEN CURRENT DATE - 4 DAYS AND CURRENT DATE "
			+ "   AND UCASE(NM_CUSTOMER_L) = ? "
			+ "   AND UCASE(ID_EMAIL)      = ? "
			+ "   AND B.ID_REFERENCE = A.ID_REFERENCE "
			+ "   AND B.CD_STAT      = A.CD_STAT "
			+ "   AND B.TS_CREATE    = (SELECT MAX(C.TS_CREATE) FROM "
			+ PropertyManager.getWebRegion()
			+ Constants.TABLE_CSESRV_FRMTRK
			+ " C "
			+ "                          WHERE C.ID_REFERENCE = A.ID_REFERENCE)";
	/**
	 * MOD# 2870 - 09/02/2004 rkodumagulla - End
	 */

	public CSTaskPersister() {
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
		System.out.println("BMP persister free resources");
		ds = null;
	}

	public String createState(TaskFormBean taskBean) throws SQLException {
		System.out.println("BMP persister createState select " + SELECT_CNNB_SQL);

		String key = "";
		Connection con = null;
		PreparedStatement ps = null;
		Statement s = null;
		boolean rollback = false;

		int controlNumber = 0;
		int nbOutstanding = 0;
		String selectedWorker = null;

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);

			/**
			 * Execute a query to get the next control number
			 */
			s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = s.executeQuery(SELECT_CNNB_SQL);

			if (rs.next()) {
				controlNumber = rs.getInt(1);
				controlNumber++;
			}
			System.out.println("BMP persister createState update " + UPDATE_CNNB_SQL);	
			// Get concurrency of the result set
			int concurrency = rs.getConcurrency();

			if (concurrency == ResultSet.CONCUR_UPDATABLE) {
				// Result set is updatable
				rs.updateInt(1, controlNumber);
				rs.updateRow();
			} else {
				//result set is not updatable
				PreparedStatement ps1 = con.prepareStatement(UPDATE_CNNB_SQL);
				ps1.setInt(1, controlNumber);
				ps1.setString(2, "CSTS");
				ps1.executeUpdate();
				ps1.close();
			}
			rs.close();
			s.close();

			if (!taskBean.isSelfAssigned()) {
				/**
				 * MOD# 2869 - 09/07/2004 - rkodumagulla
				 * If the task is not self assigned, if supervisor is trying to assign this task
				 * to this specific agent, assign the task to that agent
				 */
				if (taskBean.getIdWorker() == null || taskBean.getIdWorker().equals("")) {
				// MOD# 2869 - 09/07/2004 - rkodumagulla - End

					/**
					 * MOD# 2870 - 09/02/2004 - rkodumagulla
					 * If the task is not self assigned, then check to see if the same customer
					 * has requested a task within the last 4 days and if true, assign this new task
					 * to the same agent to avoid duplicate tasks
					 */
					PreparedStatement ps1 = con.prepareStatement(MATCH_AGNT_4DAY_TASK);
					ps1.setString(1, taskBean.getNmCustomerLast().toUpperCase());
					ps1.setString(2, taskBean.getIdEmail().toUpperCase());
					rs = ps1.executeQuery();
					if (rs.next()) {
						selectedWorker = rs.getString(2);
					} else {
						/**
						 * RK CT # 523481 - Use the AgentDelegate Class to get the next available agent
						 * If an EmptyListException is thrown, the program must abend.
						 * 08/12/04
						 */
						try {
							selectedWorker = AgentDelegate.getNextAvailableAgent().getIdWrkr();
						} catch (EmptyListException ele) {
							System.out.println("EJB CSTaskPersister Create State : " + ele.getMessage());
						}
					}
					rs.close();
					ps1.close();

					/**
					 * MOD# 2870 - 09/02/2004 - rkodumagulla - End
					 */
				// MOD# 2869 - 09/07/2004 - rkodumagulla - Start
				} else {
					selectedWorker = taskBean.getIdWorker();
				// MOD# 2869 - 09/07/2004 - rkodumagulla - End
				}
			} else {
				
				/**
				 * Agents who cannot accept workload cannot even create a task. So , if this task must be
				 * self-assigned, simply assign this task to this agent
				 */
				selectedWorker = taskBean.getIdWorker();
				
				/**
				 * Execute a query to get the current worker that accepts workload.
				 */
				
				/*
				PreparedStatement ps1 = con.prepareStatement(SELECT_WRKR_SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps1.setString(1, taskBean.getIdWorker());
				rs = ps1.executeQuery();

				while (rs.next()) {
					selectedWorker = rs.getString(1);
					nbOutstanding = rs.getInt(2);
				}
				rs.close();
				ps1.close();
				*/
			}

			if (selectedWorker == null) {
				throw new SQLException("No worker available to accept workload.");
			}

			java.sql.Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());
			System.out.println("Timestamp is + " + ts.getTime());

			Calendar now = Calendar.getInstance();
			int month = now.get(Calendar.MONTH) + 1;
			int year = now.get(Calendar.YEAR);
			System.out.println("INSERT_NOTE_SQL" + INSERT_NOTE_SQL);
			ps = con.prepareStatement(INSERT_NOTE_SQL);

			/**
			 * Insert a record in " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_NOTES + " table
			 */

			BigDecimal idNote = new BigDecimal(ts.getTime());
			ps.setBigDecimal(1, idNote);
			ps.setBigDecimal(2, new BigDecimal(0));
			ps.setString(3, "");
			ps.setDate(4, new java.sql.Date(ts.getTime()));
			ps.setTime(5, new java.sql.Time(ts.getTime()));
			ps.setString(6, "ACTS9999");
			ps.setString(7, "ACTS9999");
			ps.setString(8, taskBean.getNtResolution());
			int result = ps.executeUpdate();
			ps.close();
			System.out.println("Created a Notes record" + " INSERT_FRMTRK_SQL " + INSERT_FRMTRK_SQL);

			/**
			 * Insert a record in " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " table with INIT
			 */

			key = Integer.toString(controlNumber);

			ps = con.prepareStatement(INSERT_FRMTRK_SQL);
			ps.setString(1, Integer.toString(controlNumber));
			ps.setTimestamp(2, ts);
			ps.setString(3, "INIT");
			ps.setString(4, "");
			ps.setString(5, selectedWorker);
			ps.setTimestamp(6, ts);
			ps.setTimestamp(7, ts);
			ps.setBigDecimal(8, idNote);
			ps.setString(9, "ACTS9999");
			ps.setTimestamp(10, ts);
			ps.setString(11, "ACTS9999");
			result = ps.executeUpdate();
			ps.close();
			System.out.println("Created a INIT record in FRMTRK table");

			/**
			 * Obtain the latest time
			 */
			ts = new java.sql.Timestamp(System.currentTimeMillis());
			System.out.println("Task Creation Timestamp: " + ts);

			java.sql.Timestamp tsAssigned = getAssignedTimeStamp(ts);
			System.out.println("Task Assignment Timestamp: " + tsAssigned);

			// Increment outstanding count
			nbOutstanding++;

			/**
			 * Now insert a record in FKKT_CSESRV_FORM table
			 */
			System.out.println("INSERT_FORM_SQL " + INSERT_FORM_SQL);
			ps = con.prepareStatement(INSERT_FORM_SQL);

			ps.setString(1, Integer.toString(controlNumber));
			ps.setString(2, "OPEN");
			ps.setTimestamp(3, ts);
			ps.setString(4, "ACTS9999");
			ps.setTimestamp(5, ts);
			ps.setString(6, "ACTS9999");
			ps.setString(7, taskBean.getCdType());
			ps.setString(8, taskBean.getIdPart());
			ps.setString(9, taskBean.getNbCase());

			String ssn = taskBean.getNbSSN();
			if (ssn.length() == 11) {
				ssn = ssn.substring(0, 3) + ssn.substring(4, 6) + ssn.substring(7);
			}

			double dblSSN = 0;
			try {
				dblSSN = Double.parseDouble(ssn);
			} catch (NumberFormatException nfe) {
			}

			ps.setBigDecimal(10, new BigDecimal(dblSSN));
			ps.setString(11, taskBean.getNbDocket());
			ps.setString(12, taskBean.getIdEmail());
			ps.setString(13, taskBean.getNbTelAcd());
			ps.setString(14, taskBean.getNbTelExc());
			ps.setString(15, taskBean.getNbTelLn());
			ps.setString(16, taskBean.getNbTelExt());
			ps.setString(17, taskBean.getNmCounty());
			ps.setString(18, taskBean.getNbControl());
			ps.setString(19, taskBean.getNmCustParLast());
			ps.setString(20, taskBean.getNmCustParFirst());
			ps.setString(21, taskBean.getNmCustParMi());
			ps.setString(22, taskBean.getNmNonCustParLast());
			ps.setString(23, taskBean.getNmNonCustParFirst());
			ps.setString(24, taskBean.getNmNonCustParMi());
			ps.setString(25, taskBean.getNmCustomerLast());
			ps.setString(26, taskBean.getNmCustomerFirst());
			ps.setString(27, taskBean.getNmCustomerMi());
			ps.setString(28, taskBean.getNmRefSource1());
			ps.setString(29, taskBean.getNmRefSource2());
			ps.setString(30, taskBean.getNmRefSource3());
			ps.setString(31, taskBean.getNmRefSource4());
			ps.setInt(32, month);
			ps.setInt(33, year);
			ps.setTimestamp(34, tsAssigned);

			result = ps.executeUpdate();
			ps.close();

			System.out.println("Created a record in FKKT_CSESRV_FORM table");

			/**
			 * Now insert a record in " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " table with an OPEN entry
			 */

			ps = con.prepareStatement(INSERT_FRMTRK_SQL);
			ps.setString(1, Integer.toString(controlNumber));
			ps.setTimestamp(2, ts);
			ps.setString(3, "OPEN");
			ps.setString(4, "");
			ps.setString(5, selectedWorker);
			ps.setTimestamp(6, ts);
			ps.setTimestamp(7, new java.sql.Timestamp(0));
			ps.setBigDecimal(8, idNote);
			ps.setString(9, "ACTS9999");
			ps.setTimestamp(10, ts);
			ps.setString(11, "ACTS9999");
			result = ps.executeUpdate();
			ps.close();

			System.out.println("Created a " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " record with OPEN status");

			/**
			 * Update the worker count of outstanding tasks
			 */
			ps = con.prepareStatement(UPDATE_OUTSTANDING_SQL);
			ps.setInt(1, nbOutstanding);
			ps.setString(2, selectedWorker);
			result = ps.executeUpdate();

			//			updateOutstanding(selectedWorker, nbOutstanding);

		} catch (SQLException e) {
			rollback = true;
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			if (con != null) {
				if (rollback) {
					con.rollback();
				} else {
					con.commit();
				}
				con.close();
			}
		}
		return key;
	}

	public void createHistory(TaskFormBean taskBean) throws SQLException {
		log.info("BMP persister createHistory");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean rollback = false;

		int controlNumber = 0;
		int nbOutstanding = 0;
		String selectedWorker = null;

		java.sql.Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());
		log.debug("Server Timestamp: " + ts);

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);

			/**
			 * RK - CT# 542487 09/24/04. - Do not use two different timestamps. Use single timestamp
			 * from the server and not DB2.
			 */
			/*
			ps = con.prepareStatement(SELECT_TS_SQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				ts = rs.getTimestamp(1);
				log.info("Override Server Timestamp with DB2 Timestamp: " + ts);
			}
			rs.close();
			ps.close();
			* RK - end 
			*/

			ps = con.prepareStatement(UPDATE_TASKSTAT_SQL);

			ps.setString(1, taskBean.getCdStatus());
			ps.setTimestamp(2, ts);
			ps.setString(3, "ACTS9999");
			ps.setString(4, taskBean.getIdPart());
			ps.setString(5, taskBean.getNbCase());

			// UAT PR# 115 - SSN is not being passed from some of the screens. As a safety measure,
			// if ssn is null, just set it to zero

			String ssn = (taskBean.getNbSSN() == null ? "0" : taskBean.getNbSSN());

			if (ssn.length() == 11) {
				ssn = ssn.substring(0, 3) + ssn.substring(4, 6) + ssn.substring(7);
			}

			double dblSSN = 0;
			try {
				dblSSN = Double.parseDouble(ssn);
			} catch (NumberFormatException nfe) {
				log.debug("ejb Create History: SSN not passed. default to 0");
			}

			ps.setBigDecimal(6, new BigDecimal(dblSSN));

			ps.setString(7, taskBean.getNbDocket());
			ps.setString(8, taskBean.getIdEmail());
			ps.setString(9, taskBean.getNbTelAcd());
			ps.setString(10, taskBean.getNbTelExc());
			ps.setString(11, taskBean.getNbTelLn());
			ps.setString(12, taskBean.getNbTelExt());
			ps.setString(13, taskBean.getNmCounty());
			ps.setString(14, taskBean.getNbControl());
			ps.setString(15, taskBean.getNmCustParLast());
			ps.setString(16, taskBean.getNmCustParFirst());
			ps.setString(17, taskBean.getNmCustParMi());
			ps.setString(18, taskBean.getNmNonCustParLast());
			ps.setString(19, taskBean.getNmNonCustParFirst());
			ps.setString(20, taskBean.getNmNonCustParMi());
			ps.setString(21, taskBean.getNmCustomerLast());
			ps.setString(22, taskBean.getNmCustomerFirst());
			ps.setString(23, taskBean.getNmCustomerMi());
			ps.setString(24, taskBean.getIdReference());
			int result = ps.executeUpdate();
			ps.close();

			log.info("TaskPersister - Update Form Successful");

			ps = con.prepareStatement(UPDATE_TASKHIST_SQL);
			ps.setTimestamp(1, ts);
			ps.setTimestamp(2, ts);
			ps.setString(3, "ACTS9999");
			ps.setString(4, taskBean.getIdReference());
			ps.setString(5, taskBean.getIdReference());
			result = ps.executeUpdate();
			ps.close();
			System.out.println("INSERT_NOTE_SQL" + INSERT_NOTE_SQL);
			ps = con.prepareStatement(INSERT_NOTE_SQL);

			log.debug("Long Value of Timestamp : " + ts.getTime());
			BigDecimal idNote = new BigDecimal(ts.getTime() + ts.getNanos());

			log.debug("BigDecimal built from Long: " + idNote);
			ps.setBigDecimal(1, idNote);
			ps.setBigDecimal(2, new BigDecimal(0));
			ps.setString(3, "");
			ps.setDate(4, new java.sql.Date(ts.getTime()));
			ps.setTime(5, new java.sql.Time(ts.getTime()));
			ps.setString(6, "ACTS9999");
			ps.setString(7, "ACTS9999");
			ps.setString(8, taskBean.getNtResolution());
			result = ps.executeUpdate();
			ps.close();

			log.info("TaskPersister - Insert Notes Successful");

			ps = con.prepareStatement(INSERT_FRMTRK_SQL);
			ps.setString(1, taskBean.getIdReference());
			ps.setTimestamp(2, ts);
			ps.setString(3, taskBean.getCdStatus());
			/**
			 * If the Task History being created is CORR (Correction), just set the 
			 * cd_resolution code to 01.
			 */
			if (taskBean.getCdStatus().equals("CORR")) {
				ps.setString(4, "01");
			} else {
				ps.setString(4, "");
			}
			ps.setString(5, taskBean.getIdWorker());
			ps.setTimestamp(6, ts);
			if (taskBean.getCdStatus().equals("CLSD")) {
				ps.setTimestamp(7, ts);
			} else {
				ps.setTimestamp(7, new java.sql.Timestamp(0));
			}
			ps.setBigDecimal(8, idNote);
			ps.setString(9, "ACTS9999");
			ps.setTimestamp(10, ts);
			ps.setString(11, "ACTS9999");
			result = ps.executeUpdate();
			ps.close();

			log.info("TaskPersister - Insert Frmtrk Successful");

		} catch (SQLException e) {
			rollback = true;
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (con != null) {
				if (rollback) {
					con.rollback();
				} else {
					con.commit();
				}
				con.close();
			}
		}
	}

	public void loadState(CSProcessorBean bean) throws SQLException {
		String key = (String) bean.getEntityContext().getPrimaryKey();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;

		log.info("BMP persister loadState with ID Reference: " + key + " SELECT_TASK_SQL " + SELECT_TASK_SQL);

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_TASK_SQL);
			ps.setString(1, key);
			ps.setTimestamp(2, TS_DEFAULT);
			rs = ps.executeQuery();
			if (rs.next()) {
				bean.setIdReference(rs.getString(1).trim());
				bean.setCdStatus(rs.getString(2).trim());
				bean.setTsCreate(rs.getTimestamp(3));
				bean.setIdWrkrCreate(rs.getString(4).trim());
				bean.setTsUpdate(rs.getTimestamp(5));
				bean.setIdWrkrUpdate(rs.getString(6).trim());
				bean.setCdType(rs.getString(7).trim());
				bean.setIdPart(rs.getString(8).trim());
				bean.setNbCase(rs.getString(9).trim());
				double dSSN = rs.getBigDecimal(10).doubleValue();
				if (dSSN == 0) {
					bean.setNbSSN("");
				} else {
					String ssn = rs.getBigDecimal(10).toString().trim();
					/** CT 520496 Begin **/
					ssn = zeroPadNumber(9, ssn);
					bean.setNbSSN(ssn);
					/** CT 520496 End **/
				}
				bean.setNbDocket(rs.getString(11).trim());
				bean.setIdEmail(rs.getString(12).trim());
				bean.setNbTelACD(rs.getString(13).trim());
				bean.setNbTelEXC(rs.getString(14).trim());
				bean.setNbTelLN(rs.getString(15).trim());
				bean.setNbTelEXT(rs.getString(16).trim());
				bean.setNmCounty(rs.getString(17).trim());
				bean.setNbControl(rs.getString(18).trim());
				bean.setNmCustParLast(rs.getString(19).trim());
				bean.setNmCustParFirst(rs.getString(20).trim());
				bean.setNmCustParMi(rs.getString(21).trim());
				bean.setNmNonCustParLast(rs.getString(22).trim());
				bean.setNmNonCustParFirst(rs.getString(23).trim());
				bean.setNmNonCustParMi(rs.getString(24).trim());
				bean.setNmCustomerLast(rs.getString(25).trim());
				bean.setNmCustomerFirst(rs.getString(26).trim());
				bean.setNmCustomerMi(rs.getString(27).trim());
				bean.setNmRefSource1(rs.getString(28).trim());
				bean.setNmRefSource2(rs.getString(29).trim());
				bean.setNmRefSource3(rs.getString(30).trim());
				bean.setNmRefSource4(rs.getString(31).trim());
				String nmWorker = rs.getString(32).trim() + " " + rs.getString(34).trim() + " " + rs.getString(33).trim();
				bean.setNmWorker(nmWorker);
				bean.setIdWorker(rs.getString(35));
				bean.setTsAssign(rs.getTimestamp(36));
			}
			rs.close();
			ps.close();

			Vector frmTrack = new Vector();

			ps = con.prepareStatement(SELECT_TRKHIST_SQL);
			ps.setString(1, key);
			rs = ps.executeQuery();
			while (rs.next()) {
				TaskHistoryBean thb = new TaskHistoryBean();
				thb.setIdReference(rs.getString(1).trim());
				thb.setTsCreate(rs.getTimestamp(2));
				thb.setFormattedCreate(new FormatTimestamp().format(rs.getTimestamp(2)));
				thb.setCdStatus(rs.getString(3).trim());
				thb.setIdWrkrAssign(rs.getString(4).trim());
				thb.setTsWrkrStart(rs.getTimestamp(5));
				thb.setFormattedStart(new FormatTimestamp().format(rs.getTimestamp(5)));
				thb.setTsWrkrEnd(rs.getTimestamp(6));
				if (thb.getTsWrkrEnd().getTime() == 0) {
					thb.setFormattedEnd("");
				} else {
					thb.setFormattedEnd(new FormatTimestamp().format(rs.getTimestamp(6)));
				}
				thb.setIdWrkrCreate(rs.getString(7).trim());
				thb.setTsUpdate(rs.getTimestamp(8));
				thb.setFormattedUpdate(new FormatTimestamp().format(rs.getTimestamp(8)));
				thb.setIdWrkrUpdate(rs.getString(9).trim());
				thb.setNotes(rs.getString(10).trim());
				thb.setNmWorkerAssign(rs.getString(12).trim() + " " + rs.getString(13).trim() + " " + rs.getString(11).trim());

				ps1 = con.prepareStatement(SELECT_DUEDATE_SQL);
				ps1.setString(1, bean.getCdType());
				ps1.setString(2, thb.getCdStatus());
				rs1 = ps1.executeQuery();
				if (rs1.next()) {
					short duration = rs1.getShort(1);
					java.util.Date startDt = new java.util.Date(thb.getTsWrkrStart().getTime());

					java.util.Calendar c = java.util.Calendar.getInstance();
					c.setTime(startDt);

					/*
					 * CT# 527783 - RK 08/24/04
					 * Due date must be computed using BusinessCalendar class and
					 * not just by adding the duration to create date
					 */
					java.util.Date dueDt = new BusinessCalendar().addBussinessDays(duration, c).getTime();
					Timestamp tsDue = new Timestamp(dueDt.getTime());
					thb.setTsDue(tsDue);
					thb.setFormattedDue(new FormatTimestamp().format(tsDue));

					/*
					c.add(Calendar.DATE, duration);
					
					java.util.Date dueDt = c.getTime();
					Timestamp tsDue = new Timestamp(dueDt.getTime());
					thb.setTsDue(tsDue);
					thb.setFormattedDue(new FormatTimestamp().format(tsDue));
					* CT# 527783 - END
					*/
				} else {
					thb.setFormattedDue("n/a");
				}
				rs1.close();
				ps1.close();

				frmTrack.addElement(thb);
			}

			bean.setFrmTrack(frmTrack);
			log.info(
				"successfully loaded BMP persister loadState with ID Reference: " + key + " & contains " + frmTrack.size() + " history items");

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs1 != null)
				rs1.close();

			if (ps1 != null)
				ps1.close();

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

	public void storeState(CSProcessorBean bean) throws SQLException {
		log.info("BMP persister storeState");
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(UPDATE_TASK_SQL);

			java.sql.Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());

			ps.setString(1, bean.getCdStatus());
			ps.setTimestamp(2, bean.getTsCreate());
			ps.setString(3, bean.getIdWrkrCreate());
			ps.setTimestamp(4, bean.getTsUpdate());
			ps.setString(5, bean.getIdWrkrUpdate());
			ps.setString(6, bean.getCdType());
			ps.setString(7, bean.getIdPart());
			ps.setString(8, bean.getNbCase());
			ps.setString(9, bean.getNbDocket());
			ps.setString(10, bean.getIdEmail());
			ps.setString(11, bean.getNbTelACD());
			ps.setString(12, bean.getNbTelEXC());
			ps.setString(13, bean.getNbTelLN());
			ps.setString(14, bean.getNbTelEXT());
			ps.setString(15, bean.getNmCounty());
			ps.setString(16, bean.getNbControl());
			ps.setString(17, bean.getNmCustParLast());
			ps.setString(18, bean.getNmCustParFirst());
			ps.setString(19, bean.getNmCustParMi());
			ps.setString(20, bean.getNmNonCustParLast());
			ps.setString(21, bean.getNmNonCustParFirst());
			ps.setString(22, bean.getNmNonCustParMi());
			ps.setString(23, bean.getNmCustomerLast());
			ps.setString(24, bean.getNmCustomerFirst());
			ps.setString(25, bean.getNmCustomerMi());
			ps.setString(26, bean.getNmRefSource1());
			ps.setString(27, bean.getNmRefSource2());
			ps.setString(28, bean.getNmRefSource3());
			ps.setString(29, bean.getNmRefSource4());
			ps.setString(30, bean.getIdReference());

			ps.executeUpdate();

			Vector frmtrk = bean.getFrmTrack();
			for (int i = 0; i < frmtrk.size(); i++) {
				TaskHistoryBean thb = (TaskHistoryBean) frmtrk.elementAt(i);
				ps1 = con.prepareStatement(UPDATE_TASK_HIST_SQL);
				ps1.setString(1, thb.getCdStatus());
				ps1.setString(2, "");
				ps1.setString(3, thb.getIdWrkrAssign());
				ps1.setTimestamp(4, thb.getTsWrkrStart());
				ps1.setTimestamp(5, thb.getTsWrkrEnd());
				ps1.setString(6, thb.getIdWrkrCreate());
				ps1.setTimestamp(7, thb.getTsUpdate());
				ps1.setString(8, thb.getIdWrkrUpdate());
				ps1.setString(9, thb.getIdReference());
				ps1.setTimestamp(10, thb.getTsCreate());
				ps1.executeUpdate();
				ps1.close();
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps1 != null)
				ps1.close();
			if (ps != null)
				ps.close();
			if (con != null) {
				con.commit();
				con.close();
			}
		}
	}

	public boolean findPrimaryKey(String key) throws SQLException {
		log.info("BMP persister findByPrimaryKey " + SELECT_FORM_SQL);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean result = false;

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_FORM_SQL);
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

	public boolean updateStatus(String idWorker, String cdStatus) throws SQLException {
		log.info("BMP persister updateStatus");
		return true;
	}

	public Collection groupAllCount() throws SQLException {

		log.info("BMP persister groupAllCount");
		System.out.println("CSTaskPersister groupAllCount SQL "+ GROUP_ALL_SQL);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(GROUP_ALL_SQL);
			ps.setTimestamp(1, TS_DEFAULT);
			rs = ps.executeQuery();

			while (rs.next()) {
				WorkerCountBean wcb = new WorkerCountBean();
				wcb.setType(3);
				wcb.setCount(rs.getInt(1));
				wcb.setIdWorker(rs.getString(2));
				a.add(wcb);
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

		log.info("BMP persister groupAllCount will return :" + a);

		return a;
	}

	public Collection groupApprovalCount() throws SQLException {

		log.info("BMP persister groupApprovalCount");
		System.out.println("CSTaskPersister groupApprovalCount SQL "+ GROUP_APPROVAL_SQL);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(GROUP_APPROVAL_SQL);
			ps.setTimestamp(1, TS_DEFAULT);
			rs = ps.executeQuery();

			while (rs.next()) {
				WorkerCountBean wcb = new WorkerCountBean();
				wcb.setType(1);
				wcb.setCount(rs.getInt(1));
				wcb.setIdWorker(rs.getString(2));
				a.add(wcb);
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

		log.info("BMP persister groupApprovalCount will return :" + a);

		return a;
	}

	public Collection groupOutstandingCount() throws SQLException {

		log.info("BMP persister groupOutstandingCount");
		System.out.println("CSTaskPersister groupOutstandingCount SQL "+ GROUP_OUTSTANDING_SQL);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(GROUP_OUTSTANDING_SQL);
			ps.setTimestamp(1, TS_DEFAULT);
			rs = ps.executeQuery();

			while (rs.next()) {
				WorkerCountBean wcb = new WorkerCountBean();
				wcb.setType(0);
				wcb.setCount(rs.getInt(1));
				wcb.setIdWorker(rs.getString(2));
				a.add(wcb);
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

		log.info("BMP persister groupOutstandingCount will return :" + a);

		return a;
	}

	public Collection groupCompletedCount() throws SQLException {

		log.info("BMP persister groupCompletedCount");
		System.out.println("CSTaskPersister groupCompletedCount SQL "+ GROUP_COMPLETED_SQL);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(GROUP_COMPLETED_SQL);
			rs = ps.executeQuery();

			while (rs.next()) {
				WorkerCountBean wcb = new WorkerCountBean();
				wcb.setType(2);
				wcb.setCount(rs.getInt(1));
				wcb.setIdWorker(rs.getString(2));
				a.add(wcb);
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

		log.info("BMP persister groupCompletedCount will return :" + a);

		return a;
	}

	public int findAllCount(String idWrkr) throws SQLException {

		log.info("BMP persister findAllCount");
		System.out.println("CSTaskPersister findAllCount SQL "+ COUNT_ALL_SQL);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int count = 0;

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(COUNT_ALL_SQL);
			ps.setString(1, idWrkr);
			ps.setTimestamp(2, TS_DEFAULT);
			rs = ps.executeQuery();

			while (rs.next()) {
				count = rs.getInt(1);
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

		log.info("BMP persister findAllCount will return :" + count);

		return count;
	}

	public int findApprovalCount(String idWrkr) throws SQLException {

		log.info("BMP persister findApprovalCount");
		System.out.println("CSTaskPersister findApprovalCount SQL "+ COUNT_APPROVAL_SQL);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int count = 0;

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(COUNT_APPROVAL_SQL);
			ps.setString(1, idWrkr);
			ps.setTimestamp(2, TS_DEFAULT);
			rs = ps.executeQuery();

			while (rs.next()) {
				count = rs.getInt(1);
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

		log.info("BMP persister findApprovalCount will return :" + count);

		return count;
	}

	public int findOutstandingCount(String idWrkr) throws SQLException {

		log.info("BMP persister findOutstandingCount");
		System.out.println("CSTaskPersister findOutstandingCount SQL "+ COUNT_OUTSTANDING_SQL);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int count = 0;

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(COUNT_OUTSTANDING_SQL);
			ps.setString(1, idWrkr);
			ps.setTimestamp(2, TS_DEFAULT);
			rs = ps.executeQuery();

			while (rs.next()) {
				count = rs.getInt(1);
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

		log.info("BMP persister findOutstandingCount will return :" + count);

		return count;
	}

	public int findCompletedCount(String idWrkr) throws SQLException {

		log.info("BMP persister findCompletedCount");
		System.out.println("CSTaskPersister findCompletedCount SQL "+ COUNT_COMPLETED_SQL);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int count = 0;

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(COUNT_COMPLETED_SQL);
			ps.setString(1, idWrkr);
			rs = ps.executeQuery();

			while (rs.next()) {
				count = rs.getInt(1);
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

		log.info("BMP persister findCompletedCount will return :" + count);

		return count;
	}

	public Collection findAllTasks() throws SQLException {
		log.info("BMP persister findAllTasks");
		System.out.println("CSTaskPersister findAllTasks SQL "+ SELECT_ALL_SQL);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_ALL_SQL);
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

		log.info("BMP persister findAllTasks will return :" + a);

		return a;
	}

	public Collection findAllPendingTasks() throws SQLException {
		log.info("BMP persister findAllPendingTasks");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_PENDALL_SQL);
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

		log.info("BMP persister findAllPendingTasks will return :" + a);

		return a;
	}

	public Collection findAllApproval() throws SQLException {
		log.info("BMP persister findAllApproval");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_APPROVAL_SQL);
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

		log.info("BMP persister findAllApproval will return :" + a);

		return a;
	}

	public Collection findAllOutstanding() throws SQLException {
		log.info("BMP persister findAllOutstanding");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_OUTSTANDING_SQL);
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

		log.info("BMP persister findAllOutstanding will return :" + a);

		return a;
	}

	public Collection findPendingTasksByRfrl(String cdRfrl) throws SQLException {
		log.info("BMP persister findPendingTasksByRfrl");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_PENDRFRLTSKS_SQL);
			ps.setString(1, cdRfrl);
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

		log.info("BMP persister findPendingTasksByRfrl will return :" + a);

		return a;
	}

	public Collection findWorkerTasks(String idWorker) throws SQLException {
		log.info("BMP persister findWorkerTasks");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_WRKRTSKS_SQL);
			ps.setString(1, idWorker);
			ps.setTimestamp(2, TS_DEFAULT);
			rs = ps.executeQuery();
			while (rs.next()) {
				String id = rs.getString(1);
				a.add(id);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null)
				ps.close();
			if (con != null) {
				con.commit();
				con.close();
			}
		}

		log.info("BMP persister findWorkerTasks will return :" + a);

		return a;
	}

	public Collection findPendingWorkerTasks(String idWorker) throws SQLException {
		log.info("BMP persister findPendingWorkerTasks");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_PENDWRKRTSKS_SQL);
			ps.setString(1, idWorker);
			ps.setTimestamp(2, TS_DEFAULT);
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

		log.info("BMP persister findPendingWorkerTasks will return :" + a);

		return a;
	}

	public Collection findTasksByRfrl(String cdRfrl) throws SQLException {
		log.info("BMP persister findTasksByRfrl");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_RFRLTSKS_SQL);
			ps.setString(1, cdRfrl);
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

		log.info("BMP persister findTasksByRfrl will return :" + a);

		return a;
	}

	public Collection findTasksByRfrl(String cdRfrl, String idWorker) throws SQLException {
		log.info("BMP persister findTasksByRfrl");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_RFRLTSKS_WRKR_SQL);
			ps.setString(1, cdRfrl);
			ps.setString(2, idWorker);
			ps.setTimestamp(3, TS_DEFAULT);
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

		log.info("BMP persister findTasksByRfrl will return :" + a);

		return a;
	}

	public Collection findTasksByCase(String nbCase) throws SQLException {
		log.info("BMP persister findTasksByCase");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_CASETSKS_SQL);
			ps.setString(1, nbCase);
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

		log.info("BMP persister findTasksByCase will return :" + a);

		return a;
	}

	public Collection findTasksByCase(String nbCase, String idWorker) throws SQLException {
		log.info("BMP persister findTasksByCase");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_CASETSKS_WRKR_SQL);
			ps.setString(1, nbCase);
			ps.setString(2, idWorker);
			ps.setTimestamp(3, TS_DEFAULT);
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

		log.info("BMP persister findTasksByCase will return :" + a);

		return a;
	}

	public Collection findTasksByMPI(String idPart) throws SQLException {
		log.info("BMP persister findTasksByMPI");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_MPITSKS_SQL);
			ps.setString(1, idPart);
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

		log.info("BMP persister findTasksByMPI will return :" + a);

		return a;
	}

	public Collection findTasksByMPI(String idPart, String idWorker) throws SQLException {
		log.info("BMP persister findTasksByMPI");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_MPITSKS_WRKR_SQL);
			ps.setString(1, idPart);
			ps.setString(2, idWorker);
			ps.setTimestamp(3, TS_DEFAULT);
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

		log.info("BMP persister findTasksByMPI will return :" + a);

		return a;
	}

	public Collection findTasksByDocket(String nbDkt) throws SQLException {
		log.info("BMP persister findTasksByDocket");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_DKTTSKS_SQL);
			ps.setString(1, nbDkt);
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

		log.info("BMP persister findTasksByDocket will return :" + a);

		return a;
	}

	public Collection findTasksByDocket(String nbDkt, String idWorker) throws SQLException {
		log.info("BMP persister findTasksByDocket");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_DKTTSKS_WRKR_SQL);
			ps.setString(1, nbDkt);
			ps.setString(2, idWorker);
			ps.setTimestamp(3, TS_DEFAULT);
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

		log.info("BMP persister findTasksByDocket will return :" + a);

		return a;
	}

	public Collection findTasksByEmail(String idEmail) throws SQLException {
		log.info("BMP persister findTasksByEmail");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_EMAILTSKS_SQL);
			ps.setString(1, idEmail);
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

		log.info("BMP persister findTasksByEmail will return :" + a);

		return a;
	}

	public Collection findTasksByEmail(String idEmail, String idWorker) throws SQLException {
		log.info("BMP persister findTasksByEmail");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_EMAILTSKS_WRKR_SQL);
			ps.setString(1, idEmail);
			ps.setString(2, idWorker);
			ps.setTimestamp(3, TS_DEFAULT);
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

		log.info("BMP persister findTasksByEmail will return :" + a);

		return a;
	}


	public Collection findTasksByCustomerLName(String lname) throws SQLException {
		log.info("BMP persister findTasksByCustomerLName");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_CUSTOMERLTSKS_SQL);
			ps.setString(1, lname);
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

		log.info("BMP persister findTasksByCustomerLName will return :" + a);

		return a;
	}

	public Collection findTasksByCustomerLName(String lname, String idWorker) throws SQLException {
		log.info("BMP persister findTasksByCustomerLName");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_CUSTOMERLTSKS_WRKR_SQL);
			ps.setString(1, lname);
			ps.setString(2, idWorker);
			ps.setTimestamp(3, TS_DEFAULT);
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

		log.info("BMP persister findTasksByCustomerLName will return :" + a);

		return a;
	}


	public Collection findTasksByCustomerName(String lname, String fname) throws SQLException {
		log.info("BMP persister findTasksByCustomerName");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_CUSTOMERLFTSKS_SQL);
			ps.setString(1, lname);
			ps.setString(2, fname);
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

		log.info("BMP persister findTasksByCustomerName will return :" + a);

		return a;
	}

	public Collection findTasksByCustomerName(String lname, String fname, String idWorker) throws SQLException {
		log.info("BMP persister findTasksByCustomerName");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_CUSTOMERLFTSKS_WRKR_SQL);
			ps.setString(1, lname);
			ps.setString(2, lname);
			ps.setString(3, idWorker);
			ps.setTimestamp(4, TS_DEFAULT);
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

		log.info("BMP persister findTasksByCustomerName will return :" + a);

		return a;
	}

	public Collection findTasksByCreateDate(java.sql.Date dt) throws SQLException {
		log.info("BMP persister findTasksByCreateDate");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_DT_CRE_SQL);
			ps.setDate(1, dt);
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

		log.info("BMP persister findTasksByCreateDate will return :" + a);

		return a;
	}

	public Collection findTasksByCreateDate(java.sql.Date dt, String idWorker) throws SQLException {
		log.info("BMP persister findTasksByCreateDate");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_DT_CRE_WRKR_SQL);
			ps.setDate(1, dt);
			ps.setString(2, idWorker);
			ps.setTimestamp(3, TS_DEFAULT);
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

		log.info("BMP persister findTasksByCreateDate will return :" + a);

		return a;
	}


	public Collection findTasksForDateRange(java.sql.Date frdt, java.sql.Date todt,String idWorker) throws SQLException {
		log.info("BMP persister findTasksForDateRange");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_DT_RANGE_WRKR_SQL);
			ps.setDate(1, frdt);
			ps.setDate(2, todt);
			ps.setString(3, idWorker);
			ps.setTimestamp(4, TS_DEFAULT);
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

		log.info("BMP persister findTasksForDateRange will return :" + a);

		return a;
	}

	public Collection findTasksByCompleteDate(java.sql.Date dt) throws SQLException {
		log.info("BMP persister findTasksByCompleteDate");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_DT_COMPLETED_SQL);
			ps.setDate(1, dt);
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

		log.info("BMP persister findTasksByCompleteDate will return :" + a);

		return a;
	}

	public Collection findTasksByCompleteDate(java.sql.Date dt, String idWorker) throws SQLException {
		log.info("BMP persister findTasksByCompleteDate");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_DT_COMPLETED_WRKR_SQL);
			ps.setDate(1, dt);
			ps.setString(2, idWorker);
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

		log.info("BMP persister findTasksByCompleteDate will return :" + a);

		return a;
	}

	public Collection findTasksByDueDate(java.sql.Date dt) throws SQLException {
		log.info("BMP persister findTasksByDueDate");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_DT_DUE_SQL);
			rs = ps.executeQuery();

			while (rs.next()) {
				String id = rs.getString(1);

				ps1 = con.prepareStatement(SELECT_DUEDATE_SQL);
				ps1.setString(1, rs.getString(3));
				ps1.setString(2, rs.getString(4));
				rs1 = ps1.executeQuery();
				if (rs1.next()) {
					short duration = rs1.getShort(1);
					java.util.Date startDt = new java.util.Date(rs.getDate(2).getTime());

					java.util.Calendar c = java.util.Calendar.getInstance();
					c.setTime(startDt);
					c.add(Calendar.DATE, duration);

					java.util.Date dueDt = new java.util.Date(dt.getTime());

					if ((c.getTime()).getTime() == dueDt.getTime()) {
						a.add(id);
					}
				}
				rs1.close();
				ps1.close();
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs1 != null)
				rs1.close();
			if (ps1 != null)
				ps1.close();
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (con != null) {
				con.commit();
				con.close();
			}
		}

		log.info("BMP persister findTasksByDueDate will return :" + a);

		return a;
	}

	public Collection findTasksByDueDate(java.sql.Date dt, String idWorker) throws SQLException {
		log.info("BMP persister findTasksByDueDate");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;

		ArrayList a = new ArrayList();

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_DT_DUE_WRKR_SQL);
			ps.setString(1, idWorker);
			ps.setTimestamp(2, TS_DEFAULT);
			rs = ps.executeQuery();

			while (rs.next()) {
				String id = rs.getString(1);

				ps1 = con.prepareStatement(SELECT_DUEDATE_SQL);
				ps1.setString(1, rs.getString(3));
				ps1.setString(2, rs.getString(4));
				rs1 = ps1.executeQuery();
				if (rs1.next()) {
					short duration = rs1.getShort(1);
					java.util.Date startDt = new java.util.Date(rs.getDate(2).getTime());

					java.util.Calendar c = java.util.Calendar.getInstance();
					c.setTime(startDt);
					c.add(Calendar.DATE, duration);

					java.util.Date dueDt = new java.util.Date(dt.getTime());

					if ((c.getTime()).getTime() == dueDt.getTime()) {
						a.add(id);
					}
				}
				rs1.close();
				ps1.close();
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs1 != null)
				rs1.close();
			if (ps1 != null)
				ps1.close();
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (con != null) {
				con.commit();
				con.close();
			}
		}

		log.info("BMP persister findTasksByDueDate will return :" + a);

		return a;
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
			result = ps.executeUpdate(); // is 0 for successful delete
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
			result = ps.executeUpdate(); // is 0 for successful delete
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
			result = ps.executeUpdate(); // is 0 for successful delete
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

	public int findFormUsageCount(String cdFormType) throws SQLException {

		log.info("BMP persister findFormUsageCount");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int count = 0;

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(COUNT_FORM_USAGE_SQL);
			ps.setString(1, cdFormType);
			rs = ps.executeQuery();

			while (rs.next()) {
				count = rs.getInt(1);
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

		log.info("BMP persister findFormUsageCount will return :" + count);

		return count;
	}

	public int findProcessUsageCount(String cdFormType, String cdProcess) throws SQLException {

		log.info("BMP persister findProcessUsageCount");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int count = 0;

		try {
			con = getDatasource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(COUNT_PRCS_USAGE_SQL);
			ps.setString(1, cdFormType);
			ps.setString(2, cdProcess);
			rs = ps.executeQuery();

			while (rs.next()) {
				count = rs.getInt(1);
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

		log.info("BMP persister findProcessUsageCount will return :" + count);

		return count;
	}

	/** CT 520496 Begin **/
	private String zeroPadNumber(int fieldlen, String number) {
		StringBuffer sb = new StringBuffer(number);
		int numZeros = fieldlen - number.length();

		for (int i = 0; i < numZeros; i++) {
			sb.insert(0, "0");
		}
		return sb.toString();
	}
	/** CT 520496 End **/

	private java.sql.Timestamp getAssignedTimeStamp(java.sql.Timestamp ts) {

		java.util.Date dt = new java.util.Date(ts.getTime());
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.getTime();

		int DAY_OF_WEEK = rightNow.get(Calendar.DAY_OF_WEEK);
		int HOUR_OF_DAY = rightNow.get(Calendar.HOUR_OF_DAY);
		int MINUTE_OF_HOUR = rightNow.get(Calendar.MINUTE);

		switch (DAY_OF_WEEK) {
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

		//		if (HOUR_OF_DAY >= 17 && HOUR_OF_DAY <= 24) {
		if ((HOUR_OF_DAY > 16 || (HOUR_OF_DAY == 16 && MINUTE_OF_HOUR > 30)) && HOUR_OF_DAY <= 24) {
			switch (DAY_OF_WEEK) {
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

		if (HOUR_OF_DAY >= 1 && HOUR_OF_DAY < 7) {
			switch (DAY_OF_WEEK) {
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
}
