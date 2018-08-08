

package org.dhhs.dirm.acts.cs.businesslogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.naming.Context;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.dhhs.dirm.acts.cs.Constants;
import org.dhhs.dirm.acts.cs.DBConnectManager;
import org.dhhs.dirm.acts.cs.PropertyManager;
import org.dhhs.dirm.acts.cs.beans.TaskBean;

/**
 * TaskManager
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Aug 18, 2003 9:59:00 AM
 * 
 * @author Rkodumagulla
 *
 */
public class TaskManager
{

	private static final Logger				log								= Logger.getLogger(TaskManager.class);

	private static TaskManager				taskManager						= null;
	private static Context					ctx								= null;
	private static DataSource				dataSource						= null;
	private static String					user							= null;
	private static String					password						= null;

	private static final java.sql.Timestamp	TS_DEFAULT						= new java.sql.Timestamp(0);

	// Get all tasks pending approval
	final String							SELECT_APPROVAL_SQL				= "SELECT A.ID_REFERENCE, A.CD_STAT, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, " + "      A.ID_WRKR_UPDATE, A.CD_RFRL, A.ID_PART, A.NB_CASE, A.NB_SSN, A.NB_DKT, " + "      A.ID_EMAIL, A.NB_TEL_ACD, A.NB_TEL_EXC, A.NB_TEL_LN, A.NB_TEL_EXT, " + " 	 A.NM_COUNTY, A.NB_CONTROL, A.NM_CP_L, A.NM_CP_F, A.NM_CP_M, " + "      A.NM_NCP_L, A.NM_NCP_F, A.NM_NCP_M, A.NM_CUSTOMER_L, " + "      A.NM_CUSTOMER_F, A.NM_CUSTOMER_M, A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, B.NM_WRKR_F, B.NM_WRKR_L, B.NM_WRKR_MI, C.ID_WRKR_ASSIGN, A.TS_ASSIGNED " + " FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " C, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " B " + " WHERE C.CD_STAT IN ('REQR','APRV')" + "   AND C.TS_WRKR_END = ?" + "   AND A.ID_REFERENCE = C.ID_REFERENCE " + "   AND A.CD_STAT = C.CD_STAT " + "   AND B.ID_WRKR = C.ID_WRKR_ASSIGN " + " ORDER BY A.TS_CREATE DESC";

	// "SELECT ID_REFERENCE FROM " + PropertyManager.getWebRegion() +
	// Constants.TABLE_CSESRV_FORMS + " WHERE CD_STAT IN ('REQR','APRV')";

	// Get all pending tasks for agent
	final String							SELECT_PENDWRKRTSKS_SQL			= "SELECT A.ID_REFERENCE, A.CD_STAT, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, " + "      A.ID_WRKR_UPDATE, A.CD_RFRL, A.ID_PART, A.NB_CASE, A.NB_SSN, A.NB_DKT, " + "      A.ID_EMAIL, A.NB_TEL_ACD, A.NB_TEL_EXC, A.NB_TEL_LN, A.NB_TEL_EXT, " + " 	 A.NM_COUNTY, A.NB_CONTROL, A.NM_CP_L, A.NM_CP_F, A.NM_CP_M, " + "      A.NM_NCP_L, A.NM_NCP_F, A.NM_NCP_M, A.NM_CUSTOMER_L, " + "      A.NM_CUSTOMER_F, A.NM_CUSTOMER_M, A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, B.NM_WRKR_F, B.NM_WRKR_L, B.NM_WRKR_MI, C.ID_WRKR_ASSIGN, A.TS_ASSIGNED " + " FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " C, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " B " + " WHERE C.CD_STAT IN ('REQR','APRV')" + "   AND C.ID_WRKR_ASSIGN = ? " + "   AND C.TS_WRKR_END = ?" + "   AND A.ID_REFERENCE = C.ID_REFERENCE " + "   AND A.CD_STAT = C.CD_STAT " + "   AND B.ID_WRKR = C.ID_WRKR_ASSIGN " + " ORDER BY A.TS_CREATE DESC";

	/*
	 * "SELECT A.ID_REFERENCE " + "	FROM " + PropertyManager.getWebRegion() +
	 * Constants.TABLE_CSESRV_FORMS + " A, " + "        " +
	 * PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " B " +
	 * "  WHERE A.CD_STAT IN ('APRV','REQR') " +
	 * "    AND B.ID_REFERENCE = A.ID_REFERENCE " +
	 * "    AND B.CD_STAT = A.CD_STAT " + "    AND B.ID_WRKR_ASSIGN = ? " +
	 * "    AND B.TS_WRKR_END = ?";
	 */

	// Select all pending tasks from forms table
	final String							SELECT_PENDALL_SQL				= "SELECT A.ID_REFERENCE, A.CD_STAT, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, " + "      A.ID_WRKR_UPDATE, A.CD_RFRL, A.ID_PART, A.NB_CASE, A.NB_SSN, A.NB_DKT, " + "      A.ID_EMAIL, A.NB_TEL_ACD, A.NB_TEL_EXC, A.NB_TEL_LN, A.NB_TEL_EXT, " + " 	 A.NM_COUNTY, A.NB_CONTROL, A.NM_CP_L, A.NM_CP_F, A.NM_CP_M, " + "      A.NM_NCP_L, A.NM_NCP_F, A.NM_NCP_M, A.NM_CUSTOMER_L, " + "      A.NM_CUSTOMER_F, A.NM_CUSTOMER_M, A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, B.NM_WRKR_F, B.NM_WRKR_L, B.NM_WRKR_MI, C.ID_WRKR_ASSIGN, A.TS_ASSIGNED " + " FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " C, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " B " + " WHERE C.CD_STAT IN ('REQR','APRV')" + "   AND C.TS_WRKR_END = ?" + "   AND A.ID_REFERENCE = C.ID_REFERENCE " + "   AND A.CD_STAT = C.CD_STAT " + "   AND B.ID_WRKR = C.ID_WRKR_ASSIGN " + " ORDER BY A.TS_CREATE DESC";

	// "SELECT ID_REFERENCE FROM " + PropertyManager.getWebRegion() +
	// Constants.TABLE_CSESRV_FORMS + " WHERE CD_STAT IN ('APRV','REQR')";

	// Select all tasks from forms table
	final String							SELECT_ALL_SQL					= "SELECT A.ID_REFERENCE, A.CD_STAT, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, " + "      A.ID_WRKR_UPDATE, A.CD_RFRL, A.ID_PART, A.NB_CASE, A.NB_SSN, A.NB_DKT, " + "      A.ID_EMAIL, A.NB_TEL_ACD, A.NB_TEL_EXC, A.NB_TEL_LN, A.NB_TEL_EXT, " + " 	 A.NM_COUNTY, A.NB_CONTROL, A.NM_CP_L, A.NM_CP_F, A.NM_CP_M, " + "      A.NM_NCP_L, A.NM_NCP_F, A.NM_NCP_M, A.NM_CUSTOMER_L, " + "      A.NM_CUSTOMER_F, A.NM_CUSTOMER_M, A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, B.NM_WRKR_F, B.NM_WRKR_L, B.NM_WRKR_MI, C.ID_WRKR_ASSIGN, A.TS_ASSIGNED " + " FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " C, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " B " + " WHERE C.CD_STAT <> 'DELT'" + "   AND (C.CD_STAT = 'CLSD' OR (C.CD_STAT NOT IN ('CLSD','TRAN') AND C.TS_WRKR_END = ?)) " + "   AND A.ID_REFERENCE = C.ID_REFERENCE " + "   AND A.CD_STAT = C.CD_STAT " + "   AND B.ID_WRKR = C.ID_WRKR_ASSIGN " + " ORDER BY A.TS_CREATE DESC";

	// "SELECT ID_REFERENCE FROM " + PropertyManager.getWebRegion() +
	// Constants.TABLE_CSESRV_FORMS + " WHERE CD_STAT <> 'DELT'";

	// Get all tasks outstanding
	final String							SELECT_OUTSTANDING_SQL			= "SELECT A.ID_REFERENCE, A.CD_STAT, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, " + "      A.ID_WRKR_UPDATE, A.CD_RFRL, A.ID_PART, A.NB_CASE, A.NB_SSN, A.NB_DKT, " + "      A.ID_EMAIL, A.NB_TEL_ACD, A.NB_TEL_EXC, A.NB_TEL_LN, A.NB_TEL_EXT, " + " 	 A.NM_COUNTY, A.NB_CONTROL, A.NM_CP_L, A.NM_CP_F, A.NM_CP_M, " + "      A.NM_NCP_L, A.NM_NCP_F, A.NM_NCP_M, A.NM_CUSTOMER_L, " + "      A.NM_CUSTOMER_F, A.NM_CUSTOMER_M, A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, B.NM_WRKR_F, B.NM_WRKR_L, B.NM_WRKR_MI, C.ID_WRKR_ASSIGN, A.TS_ASSIGNED " + " FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " C, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " B " + " WHERE C.CD_STAT NOT IN ('DELT','CLSD')" + "   AND C.TS_WRKR_END = ?" + "   AND A.ID_REFERENCE = C.ID_REFERENCE " + "   AND A.CD_STAT = C.CD_STAT " + "   AND B.ID_WRKR = C.ID_WRKR_ASSIGN " + " ORDER BY A.TS_CREATE DESC";

	// "SELECT ID_REFERENCE FROM " + PropertyManager.getWebRegion() +
	// Constants.TABLE_CSESRV_FORMS + " WHERE CD_STAT NOT IN ('DELT','CLSD')";

	// Get all tasks for agent
	final String							SELECT_WRKRTSKS_SQL				= "SELECT A.ID_REFERENCE, A.CD_STAT, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, " + "      A.ID_WRKR_UPDATE, A.CD_RFRL, A.ID_PART, A.NB_CASE, A.NB_SSN, A.NB_DKT, " + "      A.ID_EMAIL, A.NB_TEL_ACD, A.NB_TEL_EXC, A.NB_TEL_LN, A.NB_TEL_EXT, " + " 	 A.NM_COUNTY, A.NB_CONTROL, A.NM_CP_L, A.NM_CP_F, A.NM_CP_M, " + "      A.NM_NCP_L, A.NM_NCP_F, A.NM_NCP_M, A.NM_CUSTOMER_L, " + "      A.NM_CUSTOMER_F, A.NM_CUSTOMER_M, A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, B.NM_WRKR_F, B.NM_WRKR_L, B.NM_WRKR_MI, C.ID_WRKR_ASSIGN, A.TS_ASSIGNED " + " FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " C, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " B " + "  WHERE A.CD_STAT <> 'DELT' " + "   AND C.CD_STAT = A.CD_STAT " + "   AND C.ID_WRKR_ASSIGN = ? " + "   AND C.ID_REFERENCE = A.ID_REFERENCE " + "   AND (C.CD_STAT = 'CLSD' OR (C.CD_STAT NOT IN ('CLSD','TRAN') AND C.TS_WRKR_END = ?)) " + "   AND B.ID_WRKR = C.ID_WRKR_ASSIGN " + " ORDER BY A.TS_CREATE DESC";

	/*
	 * "SELECT A.ID_REFERENCE " + "	FROM " + PropertyManager.getWebRegion() +
	 * Constants.TABLE_CSESRV_FORMS + " A, " + "        " +
	 * PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " B " +
	 * "  WHERE B.ID_REFERENCE = A.ID_REFERENCE " +
	 * "   AND A.CD_STAT <> 'DELT' " + "   AND B.CD_STAT = A.CD_STAT " +
	 * "   AND B.ID_WRKR_ASSIGN = ? " +
	 * "   AND (B.CD_STAT = 'CLSD' OR (B.CD_STAT NOT IN ('CLSD','TRAN') AND B.TS_WRKR_END = ?)) "
	 * + " ORDER BY A.TS_CREATE DESC";
	 */

	// Get all tasks that are created on a given date
	final String							SELECT_DT_CRE_SQL				= "SELECT A.ID_REFERENCE, A.CD_STAT, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, " + "      A.ID_WRKR_UPDATE, A.CD_RFRL, A.ID_PART, A.NB_CASE, A.NB_SSN, A.NB_DKT, " + "      A.ID_EMAIL, A.NB_TEL_ACD, A.NB_TEL_EXC, A.NB_TEL_LN, A.NB_TEL_EXT, " + " 	 A.NM_COUNTY, A.NB_CONTROL, A.NM_CP_L, A.NM_CP_F, A.NM_CP_M, " + "      A.NM_NCP_L, A.NM_NCP_F, A.NM_NCP_M, A.NM_CUSTOMER_L, " + "      A.NM_CUSTOMER_F, A.NM_CUSTOMER_M, A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, B.NM_WRKR_F, B.NM_WRKR_L, B.NM_WRKR_MI, C.ID_WRKR_ASSIGN, A.TS_ASSIGNED " + " FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " C, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " B " + " WHERE DATE(A.TS_CREATE) = ? " + "   AND A.CD_STAT <> 'DELT' " + "   AND C.ID_REFERENCE = A.ID_REFERENCE " + "   AND C.CD_STAT = A.CD_STAT " + "   AND (C.CD_STAT = 'CLSD' OR (C.CD_STAT NOT IN ('CLSD','TRAN') AND C.TS_WRKR_END = ?)) " + "   AND B.ID_WRKR = C.ID_WRKR_ASSIGN " + " ORDER BY A.TS_CREATE DESC";

	/*
	 * "SELECT A.ID_REFERENCE " + "	 FROM " + PropertyManager.getWebRegion() +
	 * Constants.TABLE_CSESRV_FORMS + " A" + " WHERE DATE(A.TS_CREATE) = ? " +
	 * "   AND A.CD_STAT <> 'DELT' ";
	 */

	// Get all tasks that are created on a given date for a worker
	final String							SELECT_DT_CRE_WRKR_SQL			= "SELECT A.ID_REFERENCE, A.CD_STAT, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, " + "      A.ID_WRKR_UPDATE, A.CD_RFRL, A.ID_PART, A.NB_CASE, A.NB_SSN, A.NB_DKT, " + "      A.ID_EMAIL, A.NB_TEL_ACD, A.NB_TEL_EXC, A.NB_TEL_LN, A.NB_TEL_EXT, " + " 	 A.NM_COUNTY, A.NB_CONTROL, A.NM_CP_L, A.NM_CP_F, A.NM_CP_M, " + "      A.NM_NCP_L, A.NM_NCP_F, A.NM_NCP_M, A.NM_CUSTOMER_L, " + "      A.NM_CUSTOMER_F, A.NM_CUSTOMER_M, A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, B.NM_WRKR_F, B.NM_WRKR_L, B.NM_WRKR_MI, C.ID_WRKR_ASSIGN, A.TS_ASSIGNED " + " FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " C, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " B " + " WHERE DATE(A.TS_CREATE) = ? " + "   AND A.CD_STAT <> 'DELT' " + "   AND C.ID_WRKR_ASSIGN = ? " + "   AND C.ID_REFERENCE = A.ID_REFERENCE " + "   AND C.CD_STAT = A.CD_STAT " + "   AND (C.CD_STAT = 'CLSD' OR (C.CD_STAT NOT IN ('CLSD','TRAN') AND C.TS_WRKR_END = ?)) " + "   AND B.ID_WRKR = C.ID_WRKR_ASSIGN " + " ORDER BY A.TS_CREATE DESC";
	                                                                        /*
	                                                                         * "SELECT A.ID_REFERENCE "
	                                                                         * +
	                                                                         * "	 FROM "
	                                                                         * +
	                                                                         * PropertyManager
	                                                                         * .
	                                                                         * getWebRegion
	                                                                         * (
	                                                                         * )
	                                                                         * +
	                                                                         * Constants
	                                                                         * .
	                                                                         * TABLE_CSESRV_FORMS
	                                                                         * +
	                                                                         * " A, "
	                                                                         * +
	                                                                         * "  "
	                                                                         * +
	                                                                         * PropertyManager
	                                                                         * .
	                                                                         * getWebRegion
	                                                                         * (
	                                                                         * )
	                                                                         * +
	                                                                         * Constants
	                                                                         * .
	                                                                         * TABLE_CSESRV_FRMTRK
	                                                                         * +
	                                                                         * " B "
	                                                                         * +
	                                                                         * " WHERE DATE(A.TS_CREATE) = ? "
	                                                                         * +
	                                                                         * "   AND B.ID_REFERENCE = A.ID_REFERENCE "
	                                                                         * +
	                                                                         * "   AND A.CD_STAT <> 'DELT' "
	                                                                         * +
	                                                                         * "   AND B.CD_STAT = A.CD_STAT "
	                                                                         * +
	                                                                         * "   AND B.ID_WRKR_ASSIGN = ? "
	                                                                         * +
	                                                                         * "   AND (B.CD_STAT = 'CLSD' OR (B.CD_STAT NOT IN ('CLSD','TRAN') AND B.TS_WRKR_END = ? ))"
	                                                                         * ;
	                                                                         */

	// Get all tasks that were created between a date range for a worker
	final String							SELECT_DT_RANGE_WRKR_SQL		= "SELECT A.ID_REFERENCE " + "	 FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "  " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " B " + " WHERE DATE(A.TS_CREATE)  between ?  and ? " + "   AND B.ID_REFERENCE = A.ID_REFERENCE " + "   AND A.CD_STAT <> 'DELT' " + "   AND B.CD_STAT = A.CD_STAT " + "   AND B.ID_WRKR_ASSIGN = ? " + "   AND (B.CD_STAT = 'CLSD' OR (B.CD_STAT NOT IN ('CLSD','TRAN') AND B.TS_WRKR_END = ? ))";

	// Get all tasks that are completed on a given date
	final String							SELECT_DT_COMPLETED_SQL			= "SELECT A.ID_REFERENCE, A.CD_STAT, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, " + "      A.ID_WRKR_UPDATE, A.CD_RFRL, A.ID_PART, A.NB_CASE, A.NB_SSN, A.NB_DKT, " + "      A.ID_EMAIL, A.NB_TEL_ACD, A.NB_TEL_EXC, A.NB_TEL_LN, A.NB_TEL_EXT, " + " 	 A.NM_COUNTY, A.NB_CONTROL, A.NM_CP_L, A.NM_CP_F, A.NM_CP_M, " + "      A.NM_NCP_L, A.NM_NCP_F, A.NM_NCP_M, A.NM_CUSTOMER_L, " + "      A.NM_CUSTOMER_F, A.NM_CUSTOMER_M, A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, B.NM_WRKR_F, B.NM_WRKR_L, B.NM_WRKR_MI, C.ID_WRKR_ASSIGN, A.TS_ASSIGNED " + " FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " C, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " B " + "  WHERE C.CD_STAT = 'CLSD'" + "   AND DATE(C.TS_CREATE) = ? " + "   AND A.ID_REFERENCE = C.ID_REFERENCE " + "   AND A.CD_STAT = C.CD_STAT " + "   AND B.ID_WRKR = C.ID_WRKR_ASSIGN " + " ORDER BY A.TS_CREATE DESC";

	/*
	 * "SELECT A.ID_REFERENCE FROM " + PropertyManager.getWebRegion() +
	 * Constants.TABLE_CSESRV_FORMS + " A, " + "  " +
	 * PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " B " +
	 * " WHERE A.CD_STAT = 'CLSD' " + "   AND B.ID_REFERENCE = A.ID_REFERENCE "
	 * + "   AND B.CD_STAT = A.CD_STAT " + "   AND DATE(B.TS_CREATE) = ? ";
	 */

	// Get all tasks that are completed on a given date for a worker
	final String							SELECT_DT_COMPLETED_WRKR_SQL	= "SELECT A.ID_REFERENCE, A.CD_STAT, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, " + "      A.ID_WRKR_UPDATE, A.CD_RFRL, A.ID_PART, A.NB_CASE, A.NB_SSN, A.NB_DKT, " + "      A.ID_EMAIL, A.NB_TEL_ACD, A.NB_TEL_EXC, A.NB_TEL_LN, A.NB_TEL_EXT, " + " 	 A.NM_COUNTY, A.NB_CONTROL, A.NM_CP_L, A.NM_CP_F, A.NM_CP_M, " + "      A.NM_NCP_L, A.NM_NCP_F, A.NM_NCP_M, A.NM_CUSTOMER_L, " + "      A.NM_CUSTOMER_F, A.NM_CUSTOMER_M, A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, B.NM_WRKR_F, B.NM_WRKR_L, B.NM_WRKR_MI, C.ID_WRKR_ASSIGN, A.TS_ASSIGNED " + " FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " C, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " B " + "  WHERE C.CD_STAT = 'CLSD'" + "   AND DATE(C.TS_CREATE) = ? " + "   AND C.ID_WRKR_ASSIGN = ? " + "   AND A.ID_REFERENCE = C.ID_REFERENCE " + "   AND A.CD_STAT = C.CD_STAT " + "   AND B.ID_WRKR = C.ID_WRKR_ASSIGN " + " ORDER BY A.TS_CREATE DESC";

	/*
	 * "SELECT A.ID_REFERENCE FROM " + PropertyManager.getWebRegion() +
	 * Constants.TABLE_CSESRV_FORMS + " A, " + "  " +
	 * PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " B " +
	 * " WHERE A.CD_STAT = 'CLSD' " + "   AND B.ID_REFERENCE = A.ID_REFERENCE "
	 * + "   AND B.CD_STAT = A.CD_STAT " + "   AND DATE(B.TS_CREATE) = ? " +
	 * "   AND B.ID_WRKR_ASSIGN = ? ";
	 */

	// Get all tasks that for a referral type
	final String							SELECT_RFRLTSKS_SQL				= "SELECT A.ID_REFERENCE, A.CD_STAT, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, " + "      A.ID_WRKR_UPDATE, A.CD_RFRL, A.ID_PART, A.NB_CASE, A.NB_SSN, A.NB_DKT, " + "      A.ID_EMAIL, A.NB_TEL_ACD, A.NB_TEL_EXC, A.NB_TEL_LN, A.NB_TEL_EXT, " + " 	 A.NM_COUNTY, A.NB_CONTROL, A.NM_CP_L, A.NM_CP_F, A.NM_CP_M, " + "      A.NM_NCP_L, A.NM_NCP_F, A.NM_NCP_M, A.NM_CUSTOMER_L, " + "      A.NM_CUSTOMER_F, A.NM_CUSTOMER_M, A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, B.NM_WRKR_F, B.NM_WRKR_L, B.NM_WRKR_MI, C.ID_WRKR_ASSIGN, A.TS_ASSIGNED " + " FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " C, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " B " + " WHERE A.CD_RFRL = ?" + "   AND A.CD_STAT <> 'DELT' " + "   AND C.ID_REFERENCE = A.ID_REFERENCE " + "   AND C.CD_STAT = A.CD_STAT " + "   AND (C.CD_STAT = 'CLSD' OR (C.CD_STAT NOT IN ('CLSD','TRAN') AND C.TS_WRKR_END = ? ))" + "   AND B.ID_WRKR = C.ID_WRKR_ASSIGN " + " ORDER BY A.TS_CREATE DESC";

	/*
	 * "SELECT ID_REFERENCE FROM " + PropertyManager.getWebRegion() +
	 * Constants.TABLE_CSESRV_FORMS + " WHERE CD_RFRL = ?" +
	 * "   AND CD_STAT <> 'DELT' ";
	 */

	// Get all tasks pending approval that for a referral type
	final String							SELECT_PENDRFRLTSKS_SQL			= "SELECT A.ID_REFERENCE, A.CD_STAT, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, " + "      A.ID_WRKR_UPDATE, A.CD_RFRL, A.ID_PART, A.NB_CASE, A.NB_SSN, A.NB_DKT, " + "      A.ID_EMAIL, A.NB_TEL_ACD, A.NB_TEL_EXC, A.NB_TEL_LN, A.NB_TEL_EXT, " + " 	 A.NM_COUNTY, A.NB_CONTROL, A.NM_CP_L, A.NM_CP_F, A.NM_CP_M, " + "      A.NM_NCP_L, A.NM_NCP_F, A.NM_NCP_M, A.NM_CUSTOMER_L, " + "      A.NM_CUSTOMER_F, A.NM_CUSTOMER_M, A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, B.NM_WRKR_F, B.NM_WRKR_L, B.NM_WRKR_MI, C.ID_WRKR_ASSIGN, A.TS_ASSIGNED " + " FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " C, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " B " + " WHERE A.CD_RFRL = ?" + "   AND C.CD_STAT IN ('REQR','APRV')" + "   AND C.TS_WRKR_END = ?" + "   AND A.ID_REFERENCE = C.ID_REFERENCE " + "   AND A.CD_STAT = C.CD_STAT " + "   AND B.ID_WRKR = C.ID_WRKR_ASSIGN " + " ORDER BY A.TS_CREATE DESC";

	/*
	 * "SELECT ID_REFERENCE FROM " + PropertyManager.getWebRegion() +
	 * Constants.TABLE_CSESRV_FORMS + " WHERE CD_RFRL = ?" +
	 * "   AND CD_STAT IN ('APRV','REQR') ";
	 */

	// Get all tasks that for a referral type for a worker
	final String							SELECT_RFRLTSKS_WRKR_SQL		= "SELECT A.ID_REFERENCE, A.CD_STAT, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, " + "      A.ID_WRKR_UPDATE, A.CD_RFRL, A.ID_PART, A.NB_CASE, A.NB_SSN, A.NB_DKT, " + "      A.ID_EMAIL, A.NB_TEL_ACD, A.NB_TEL_EXC, A.NB_TEL_LN, A.NB_TEL_EXT, " + " 	 A.NM_COUNTY, A.NB_CONTROL, A.NM_CP_L, A.NM_CP_F, A.NM_CP_M, " + "      A.NM_NCP_L, A.NM_NCP_F, A.NM_NCP_M, A.NM_CUSTOMER_L, " + "      A.NM_CUSTOMER_F, A.NM_CUSTOMER_M, A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, B.NM_WRKR_F, B.NM_WRKR_L, B.NM_WRKR_MI, C.ID_WRKR_ASSIGN, A.TS_ASSIGNED " + " FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " C, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " B " + " WHERE A.CD_RFRL = ?" + "   AND A.CD_STAT <> 'DELT' " + "   AND C.ID_REFERENCE = A.ID_REFERENCE " + "   AND C.CD_STAT = A.CD_STAT " + "   AND C.ID_WRKR_ASSIGN = ? " + "   AND (C.CD_STAT = 'CLSD' OR (C.CD_STAT NOT IN ('CLSD','TRAN') AND C.TS_WRKR_END = ? ))" + "   AND B.ID_WRKR = C.ID_WRKR_ASSIGN " + " ORDER BY A.TS_CREATE DESC";

	/*
	 * "SELECT A.ID_REFERENCE " + "  FROM " + PropertyManager.getWebRegion() +
	 * Constants.TABLE_CSESRV_FORMS + " A, " + "  " +
	 * PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " B " +
	 * " WHERE A.CD_RFRL = ?" + "   AND B.ID_REFERENCE = A.ID_REFERENCE " +
	 * "   AND A.CD_STAT <> 'DELT' " + "   AND B.CD_STAT = A.CD_STAT " +
	 * "   AND B.ID_WRKR_ASSIGN = ? " +
	 * "   AND (B.CD_STAT = 'CLSD' OR (B.CD_STAT NOT IN ('CLSD','TRAN') AND B.TS_WRKR_END = ? ))"
	 * ;
	 */

	// Get all tasks that have a case number
	final String							SELECT_CASETSKS_SQL				= "SELECT A.ID_REFERENCE, A.CD_STAT, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, " + "      A.ID_WRKR_UPDATE, A.CD_RFRL, A.ID_PART, A.NB_CASE, A.NB_SSN, A.NB_DKT, " + "      A.ID_EMAIL, A.NB_TEL_ACD, A.NB_TEL_EXC, A.NB_TEL_LN, A.NB_TEL_EXT, " + " 	 A.NM_COUNTY, A.NB_CONTROL, A.NM_CP_L, A.NM_CP_F, A.NM_CP_M, " + "      A.NM_NCP_L, A.NM_NCP_F, A.NM_NCP_M, A.NM_CUSTOMER_L, " + "      A.NM_CUSTOMER_F, A.NM_CUSTOMER_M, A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, B.NM_WRKR_F, B.NM_WRKR_L, B.NM_WRKR_MI, C.ID_WRKR_ASSIGN, A.TS_ASSIGNED " + " FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " C, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " B " + " WHERE A.NB_CASE = ?" + "   AND A.CD_STAT <> 'DELT' " + "   AND C.ID_REFERENCE = A.ID_REFERENCE " + "   AND C.CD_STAT = A.CD_STAT " + "   AND (C.CD_STAT = 'CLSD' OR (C.CD_STAT NOT IN ('CLSD','TRAN') AND C.TS_WRKR_END = ? ))" + "   AND B.ID_WRKR = C.ID_WRKR_ASSIGN " + " ORDER BY A.TS_CREATE DESC";

	/*
	 * "SELECT ID_REFERENCE FROM " + PropertyManager.getWebRegion() +
	 * Constants.TABLE_CSESRV_FORMS + " WHERE NB_CASE = ?" +
	 * "   AND CD_STAT <> 'DELT' ";
	 */

	// Get all tasks that have a case number for a worker
	final String							SELECT_CASETSKS_WRKR_SQL		= "SELECT A.ID_REFERENCE, A.CD_STAT, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, " + "      A.ID_WRKR_UPDATE, A.CD_RFRL, A.ID_PART, A.NB_CASE, A.NB_SSN, A.NB_DKT, " + "      A.ID_EMAIL, A.NB_TEL_ACD, A.NB_TEL_EXC, A.NB_TEL_LN, A.NB_TEL_EXT, " + " 	 A.NM_COUNTY, A.NB_CONTROL, A.NM_CP_L, A.NM_CP_F, A.NM_CP_M, " + "      A.NM_NCP_L, A.NM_NCP_F, A.NM_NCP_M, A.NM_CUSTOMER_L, " + "      A.NM_CUSTOMER_F, A.NM_CUSTOMER_M, A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, B.NM_WRKR_F, B.NM_WRKR_L, B.NM_WRKR_MI, C.ID_WRKR_ASSIGN, A.TS_ASSIGNED " + " FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " C, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " B " + " WHERE A.NB_CASE = ?" + "   AND A.CD_STAT <> 'DELT' " + "   AND C.ID_REFERENCE = A.ID_REFERENCE " + "   AND C.CD_STAT = A.CD_STAT " + "   AND C.ID_WRKR_ASSIGN = ? " + "   AND (C.CD_STAT = 'CLSD' OR (C.CD_STAT NOT IN ('CLSD','TRAN') AND C.TS_WRKR_END = ? ))" + "   AND B.ID_WRKR = C.ID_WRKR_ASSIGN " + " ORDER BY A.TS_CREATE DESC";

	/*
	 * "SELECT A.ID_REFERENCE " + "  FROM " + PropertyManager.getWebRegion() +
	 * Constants.TABLE_CSESRV_FORMS + " A, " + "  " +
	 * PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " B " +
	 * " WHERE NB_CASE = ?" + "   AND B.ID_REFERENCE = A.ID_REFERENCE " +
	 * "   AND A.CD_STAT <> 'DELT' " + "   AND B.CD_STAT = A.CD_STAT " +
	 * "   AND B.ID_WRKR_ASSIGN = ? " +
	 * "   AND (B.CD_STAT = 'CLSD' OR (B.CD_STAT NOT IN ('CLSD','TRAN') AND B.TS_WRKR_END = ?))"
	 * ;
	 */

	// Get all tasks that have a mpi number
	final String							SELECT_MPITSKS_SQL				= "SELECT A.ID_REFERENCE, A.CD_STAT, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, " + "      A.ID_WRKR_UPDATE, A.CD_RFRL, A.ID_PART, A.NB_CASE, A.NB_SSN, A.NB_DKT, " + "      A.ID_EMAIL, A.NB_TEL_ACD, A.NB_TEL_EXC, A.NB_TEL_LN, A.NB_TEL_EXT, " + " 	 A.NM_COUNTY, A.NB_CONTROL, A.NM_CP_L, A.NM_CP_F, A.NM_CP_M, " + "      A.NM_NCP_L, A.NM_NCP_F, A.NM_NCP_M, A.NM_CUSTOMER_L, " + "      A.NM_CUSTOMER_F, A.NM_CUSTOMER_M, A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, B.NM_WRKR_F, B.NM_WRKR_L, B.NM_WRKR_MI, C.ID_WRKR_ASSIGN, A.TS_ASSIGNED " + " FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " C, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " B " + " WHERE A.ID_PART = ?" + "   AND A.CD_STAT <> 'DELT' " + "   AND C.ID_REFERENCE = A.ID_REFERENCE " + "   AND C.CD_STAT = A.CD_STAT " + "   AND (C.CD_STAT = 'CLSD' OR (C.CD_STAT NOT IN ('CLSD','TRAN') AND C.TS_WRKR_END = ? ))" + "   AND B.ID_WRKR = C.ID_WRKR_ASSIGN " + " ORDER BY A.TS_CREATE DESC";

	/*
	 * "SELECT ID_REFERENCE FROM " + PropertyManager.getWebRegion() +
	 * Constants.TABLE_CSESRV_FORMS + " WHERE ID_PART = ?" +
	 * "   AND CD_STAT <> 'DELT' ";
	 */

	// Get all tasks that have a mpi number for a worker
	final String							SELECT_MPITSKS_WRKR_SQL			= "SELECT A.ID_REFERENCE, A.CD_STAT, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, " + "      A.ID_WRKR_UPDATE, A.CD_RFRL, A.ID_PART, A.NB_CASE, A.NB_SSN, A.NB_DKT, " + "      A.ID_EMAIL, A.NB_TEL_ACD, A.NB_TEL_EXC, A.NB_TEL_LN, A.NB_TEL_EXT, " + " 	 A.NM_COUNTY, A.NB_CONTROL, A.NM_CP_L, A.NM_CP_F, A.NM_CP_M, " + "      A.NM_NCP_L, A.NM_NCP_F, A.NM_NCP_M, A.NM_CUSTOMER_L, " + "      A.NM_CUSTOMER_F, A.NM_CUSTOMER_M, A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, B.NM_WRKR_F, B.NM_WRKR_L, B.NM_WRKR_MI, C.ID_WRKR_ASSIGN, A.TS_ASSIGNED " + " FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " C, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " B " + " WHERE A.ID_PART = ?" + "   AND A.CD_STAT <> 'DELT' " + "   AND C.ID_REFERENCE = A.ID_REFERENCE " + "   AND C.CD_STAT = A.CD_STAT " + "   AND C.ID_WRKR_ASSIGN = ? " + "   AND (C.CD_STAT = 'CLSD' OR (C.CD_STAT NOT IN ('CLSD','TRAN') AND C.TS_WRKR_END = ? ))" + "   AND B.ID_WRKR = C.ID_WRKR_ASSIGN " + " ORDER BY A.TS_CREATE DESC";

	/*
	 * "SELECT A.ID_REFERENCE " + "  FROM " + PropertyManager.getWebRegion() +
	 * Constants.TABLE_CSESRV_FORMS + " A, " + "  " +
	 * PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " B " +
	 * " WHERE ID_PART = ?" + "   AND B.ID_REFERENCE = A.ID_REFERENCE " +
	 * "   AND A.CD_STAT <> 'DELT' " + "   AND B.CD_STAT = A.CD_STAT " +
	 * "   AND B.ID_WRKR_ASSIGN = ? " +
	 * "   AND (B.CD_STAT = 'CLSD' OR (B.CD_STAT NOT IN ('CLSD','TRAN') AND B.TS_WRKR_END = ?))"
	 * ;
	 */

	// Get all tasks that have a docket number
	final String							SELECT_DKTTSKS_SQL				= "SELECT A.ID_REFERENCE, A.CD_STAT, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, " + "      A.ID_WRKR_UPDATE, A.CD_RFRL, A.ID_PART, A.NB_CASE, A.NB_SSN, A.NB_DKT, " + "      A.ID_EMAIL, A.NB_TEL_ACD, A.NB_TEL_EXC, A.NB_TEL_LN, A.NB_TEL_EXT, " + " 	 A.NM_COUNTY, A.NB_CONTROL, A.NM_CP_L, A.NM_CP_F, A.NM_CP_M, " + "      A.NM_NCP_L, A.NM_NCP_F, A.NM_NCP_M, A.NM_CUSTOMER_L, " + "      A.NM_CUSTOMER_F, A.NM_CUSTOMER_M, A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, B.NM_WRKR_F, B.NM_WRKR_L, B.NM_WRKR_MI, C.ID_WRKR_ASSIGN, A.TS_ASSIGNED " + " FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " C, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " B " + " WHERE A.NB_DKT = ?" + "   AND A.CD_STAT <> 'DELT' " + "   AND C.ID_REFERENCE = A.ID_REFERENCE " + "   AND C.CD_STAT = A.CD_STAT " + "   AND (C.CD_STAT = 'CLSD' OR (C.CD_STAT NOT IN ('CLSD','TRAN') AND C.TS_WRKR_END = ? ))" + "   AND B.ID_WRKR = C.ID_WRKR_ASSIGN " + " ORDER BY A.TS_CREATE DESC";

	/*
	 * "SELECT ID_REFERENCE FROM " + PropertyManager.getWebRegion() +
	 * Constants.TABLE_CSESRV_FORMS + " WHERE NB_DKT = ?" +
	 * "   AND CD_STAT <> 'DELT' ";
	 */

	// Get all tasks that have a docket number for a worker
	final String							SELECT_DKTTSKS_WRKR_SQL			= "SELECT A.ID_REFERENCE, A.CD_STAT, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, " + "      A.ID_WRKR_UPDATE, A.CD_RFRL, A.ID_PART, A.NB_CASE, A.NB_SSN, A.NB_DKT, " + "      A.ID_EMAIL, A.NB_TEL_ACD, A.NB_TEL_EXC, A.NB_TEL_LN, A.NB_TEL_EXT, " + " 	 A.NM_COUNTY, A.NB_CONTROL, A.NM_CP_L, A.NM_CP_F, A.NM_CP_M, " + "      A.NM_NCP_L, A.NM_NCP_F, A.NM_NCP_M, A.NM_CUSTOMER_L, " + "      A.NM_CUSTOMER_F, A.NM_CUSTOMER_M, A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, B.NM_WRKR_F, B.NM_WRKR_L, B.NM_WRKR_MI, C.ID_WRKR_ASSIGN, A.TS_ASSIGNED " + " FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " C, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " B " + " WHERE A.NB_DKT = ?" + "   AND A.CD_STAT <> 'DELT' " + "   AND C.ID_REFERENCE = A.ID_REFERENCE " + "   AND C.CD_STAT = A.CD_STAT " + "   AND C.ID_WRKR_ASSIGN = ? " + "   AND (C.CD_STAT = 'CLSD' OR (C.CD_STAT NOT IN ('CLSD','TRAN') AND C.TS_WRKR_END = ? ))" + "   AND B.ID_WRKR = C.ID_WRKR_ASSIGN " + " ORDER BY A.TS_CREATE DESC";

	/*
	 * "SELECT A.ID_REFERENCE " + "  FROM " + PropertyManager.getWebRegion() +
	 * Constants.TABLE_CSESRV_FORMS + " A, " + "  " +
	 * PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " B " +
	 * " WHERE NB_DKT = ?" + "   AND B.ID_REFERENCE = A.ID_REFERENCE " +
	 * "   AND A.CD_STAT <> 'DELT' " + "   AND B.CD_STAT = A.CD_STAT " +
	 * "   AND B.ID_WRKR_ASSIGN = ? " +
	 * "   AND (B.CD_STAT = 'CLSD' OR (B.CD_STAT NOT IN ('CLSD','TRAN') AND B.TS_WRKR_END = ?))"
	 * ;
	 */

	// Get all tasks that have an email
	final String							SELECT_EMAILTSKS_SQL			= "SELECT A.ID_REFERENCE, A.CD_STAT, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, " + "      A.ID_WRKR_UPDATE, A.CD_RFRL, A.ID_PART, A.NB_CASE, A.NB_SSN, A.NB_DKT, " + "      A.ID_EMAIL, A.NB_TEL_ACD, A.NB_TEL_EXC, A.NB_TEL_LN, A.NB_TEL_EXT, " + " 	 A.NM_COUNTY, A.NB_CONTROL, A.NM_CP_L, A.NM_CP_F, A.NM_CP_M, " + "      A.NM_NCP_L, A.NM_NCP_F, A.NM_NCP_M, A.NM_CUSTOMER_L, " + "      A.NM_CUSTOMER_F, A.NM_CUSTOMER_M, A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, B.NM_WRKR_F, B.NM_WRKR_L, B.NM_WRKR_MI, C.ID_WRKR_ASSIGN, A.TS_ASSIGNED " + " FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " C, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " B " + " WHERE A.ID_EMAIL = ?" + "   AND A.CD_STAT <> 'DELT' " + "   AND C.ID_REFERENCE = A.ID_REFERENCE " + "   AND C.CD_STAT = A.CD_STAT " + "   AND (C.CD_STAT = 'CLSD' OR (C.CD_STAT NOT IN ('CLSD','TRAN') AND C.TS_WRKR_END = ? ))" + "   AND B.ID_WRKR = C.ID_WRKR_ASSIGN " + " ORDER BY A.TS_CREATE DESC";

	/*
	 * "SELECT ID_REFERENCE FROM " + PropertyManager.getWebRegion() +
	 * Constants.TABLE_CSESRV_FORMS + " WHERE ID_EMAIL = ?" +
	 * "   AND CD_STAT <> 'DELT' ";
	 */

	// Get all tasks that have an email for a worker
	final String							SELECT_EMAILTSKS_WRKR_SQL		= "SELECT A.ID_REFERENCE, A.CD_STAT, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, " + "      A.ID_WRKR_UPDATE, A.CD_RFRL, A.ID_PART, A.NB_CASE, A.NB_SSN, A.NB_DKT, " + "      A.ID_EMAIL, A.NB_TEL_ACD, A.NB_TEL_EXC, A.NB_TEL_LN, A.NB_TEL_EXT, " + " 	 A.NM_COUNTY, A.NB_CONTROL, A.NM_CP_L, A.NM_CP_F, A.NM_CP_M, " + "      A.NM_NCP_L, A.NM_NCP_F, A.NM_NCP_M, A.NM_CUSTOMER_L, " + "      A.NM_CUSTOMER_F, A.NM_CUSTOMER_M, A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, B.NM_WRKR_F, B.NM_WRKR_L, B.NM_WRKR_MI, C.ID_WRKR_ASSIGN, A.TS_ASSIGNED " + " FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " C, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " B " + " WHERE A.ID_EMAIL = ?" + "   AND A.CD_STAT <> 'DELT' " + "   AND C.ID_REFERENCE = A.ID_REFERENCE " + "   AND C.CD_STAT = A.CD_STAT " + "   AND C.ID_WRKR_ASSIGN = ? " + "   AND (C.CD_STAT = 'CLSD' OR (C.CD_STAT NOT IN ('CLSD','TRAN') AND C.TS_WRKR_END = ? ))" + "   AND B.ID_WRKR = C.ID_WRKR_ASSIGN " + " ORDER BY A.TS_CREATE DESC";

	/*
	 * "SELECT A.ID_REFERENCE " + "  FROM " + PropertyManager.getWebRegion() +
	 * Constants.TABLE_CSESRV_FORMS + " A, " + "  " +
	 * PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " B " +
	 * " WHERE ID_EMAIL = ?" + "   AND B.ID_REFERENCE = A.ID_REFERENCE " +
	 * "   AND A.CD_STAT <> 'DELT' " + "   AND B.CD_STAT = A.CD_STAT " +
	 * "   AND B.ID_WRKR_ASSIGN = ? " +
	 * "   AND (B.CD_STAT = 'CLSD' OR (B.CD_STAT NOT IN ('CLSD','TRAN') AND B.TS_WRKR_END = ?))"
	 * ;
	 */

	// Get all tasks that have the customer last name
	final String							SELECT_CUSTOMERLTSKS_SQL		= "SELECT A.ID_REFERENCE, A.CD_STAT, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, " + "      A.ID_WRKR_UPDATE, A.CD_RFRL, A.ID_PART, A.NB_CASE, A.NB_SSN, A.NB_DKT, " + "      A.ID_EMAIL, A.NB_TEL_ACD, A.NB_TEL_EXC, A.NB_TEL_LN, A.NB_TEL_EXT, " + " 	 A.NM_COUNTY, A.NB_CONTROL, A.NM_CP_L, A.NM_CP_F, A.NM_CP_M, " + "      A.NM_NCP_L, A.NM_NCP_F, A.NM_NCP_M, A.NM_CUSTOMER_L, " + "      A.NM_CUSTOMER_F, A.NM_CUSTOMER_M, A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, B.NM_WRKR_F, B.NM_WRKR_L, B.NM_WRKR_MI, C.ID_WRKR_ASSIGN, A.TS_ASSIGNED " + " FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " C, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " B " + " WHERE UCASE(A.NM_CUSTOMER_L) = ?" + "   AND A.CD_STAT <> 'DELT' " + "   AND C.ID_REFERENCE = A.ID_REFERENCE " + "   AND C.CD_STAT = A.CD_STAT " + "   AND (C.CD_STAT = 'CLSD' OR (C.CD_STAT NOT IN ('CLSD','TRAN') AND C.TS_WRKR_END = ? ))" + "   AND B.ID_WRKR = C.ID_WRKR_ASSIGN " + " ORDER BY A.TS_CREATE DESC";
	                                                                        /*
	                                                                         * "SELECT ID_REFERENCE FROM "
	                                                                         * +
	                                                                         * PropertyManager
	                                                                         * .
	                                                                         * getWebRegion
	                                                                         * (
	                                                                         * )
	                                                                         * +
	                                                                         * Constants
	                                                                         * .
	                                                                         * TABLE_CSESRV_FORMS
	                                                                         * +
	                                                                         * " WHERE UCASE(NM_CUSTOMER_L) = ?"
	                                                                         * +
	                                                                         * "   AND CD_STAT <> 'DELT' "
	                                                                         * ;
	                                                                         */

	// Get all tasks that have the customer last name for a worker
	final String							SELECT_CUSTOMERLTSKS_WRKR_SQL	= "SELECT A.ID_REFERENCE, A.CD_STAT, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, " + "      A.ID_WRKR_UPDATE, A.CD_RFRL, A.ID_PART, A.NB_CASE, A.NB_SSN, A.NB_DKT, " + "      A.ID_EMAIL, A.NB_TEL_ACD, A.NB_TEL_EXC, A.NB_TEL_LN, A.NB_TEL_EXT, " + " 	 A.NM_COUNTY, A.NB_CONTROL, A.NM_CP_L, A.NM_CP_F, A.NM_CP_M, " + "      A.NM_NCP_L, A.NM_NCP_F, A.NM_NCP_M, A.NM_CUSTOMER_L, " + "      A.NM_CUSTOMER_F, A.NM_CUSTOMER_M, A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, B.NM_WRKR_F, B.NM_WRKR_L, B.NM_WRKR_MI, C.ID_WRKR_ASSIGN, A.TS_ASSIGNED " + " FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " C, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " B " + " WHERE UCASE(A.NM_CUSTOMER_L) = ?" + "   AND A.CD_STAT <> 'DELT' " + "   AND C.ID_REFERENCE = A.ID_REFERENCE " + "   AND C.CD_STAT = A.CD_STAT " + "   AND C.ID_WRKR_ASSIGN = ? " + "   AND (C.CD_STAT = 'CLSD' OR (C.CD_STAT NOT IN ('CLSD','TRAN') AND C.TS_WRKR_END = ? ))" + "   AND B.ID_WRKR = C.ID_WRKR_ASSIGN " + " ORDER BY A.TS_CREATE DESC";

	/*
	 * "SELECT A.ID_REFERENCE " + "  FROM " + PropertyManager.getWebRegion() +
	 * Constants.TABLE_CSESRV_FORMS + " A, " + "  " +
	 * PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " B " +
	 * " WHERE UCASE(NM_CUSTOMER_L) = ?" +
	 * "   AND B.ID_REFERENCE = A.ID_REFERENCE " + "   AND A.CD_STAT <> 'DELT' "
	 * + "   AND B.CD_STAT = A.CD_STAT " + "   AND B.ID_WRKR_ASSIGN = ? " +
	 * "   AND (B.CD_STAT = 'CLSD' OR (B.CD_STAT NOT IN ('CLSD','TRAN') AND B.TS_WRKR_END = ?))"
	 * ;
	 */

	// Get all tasks that have the customer last name
	final String							SELECT_CUSTOMERLFTSKS_SQL		= "SELECT A.ID_REFERENCE, A.CD_STAT, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, " + "      A.ID_WRKR_UPDATE, A.CD_RFRL, A.ID_PART, A.NB_CASE, A.NB_SSN, A.NB_DKT, " + "      A.ID_EMAIL, A.NB_TEL_ACD, A.NB_TEL_EXC, A.NB_TEL_LN, A.NB_TEL_EXT, " + " 	 A.NM_COUNTY, A.NB_CONTROL, A.NM_CP_L, A.NM_CP_F, A.NM_CP_M, " + "      A.NM_NCP_L, A.NM_NCP_F, A.NM_NCP_M, A.NM_CUSTOMER_L, " + "      A.NM_CUSTOMER_F, A.NM_CUSTOMER_M, A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, B.NM_WRKR_F, B.NM_WRKR_L, B.NM_WRKR_MI, C.ID_WRKR_ASSIGN, A.TS_ASSIGNED " + " FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " C, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " B " + " WHERE UCASE(A.NM_CUSTOMER_L) = ?" + "   AND UCASE(A.NM_CUSTOMER_F) = ?" + "   AND A.CD_STAT <> 'DELT' " + "   AND C.ID_REFERENCE = A.ID_REFERENCE " + "   AND C.CD_STAT = A.CD_STAT " + "   AND (C.CD_STAT = 'CLSD' OR (C.CD_STAT NOT IN ('CLSD','TRAN') AND C.TS_WRKR_END = ? ))" + "   AND B.ID_WRKR = C.ID_WRKR_ASSIGN " + " ORDER BY A.TS_CREATE DESC";

	/*
	 * "SELECT ID_REFERENCE FROM " + PropertyManager.getWebRegion() +
	 * Constants.TABLE_CSESRV_FORMS + " WHERE UCASE(NM_CUSTOMER_L) = ?" +
	 * "   AND UCASE(NM_CUSTOMER_F) = ?" + "   AND CD_STAT <> 'DELT' ";
	 */

	// Get all tasks that have the customer last name for a worker
	final String							SELECT_CUSTOMERLFTSKS_WRKR_SQL	= "SELECT A.ID_REFERENCE, A.CD_STAT, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, " + "      A.ID_WRKR_UPDATE, A.CD_RFRL, A.ID_PART, A.NB_CASE, A.NB_SSN, A.NB_DKT, " + "      A.ID_EMAIL, A.NB_TEL_ACD, A.NB_TEL_EXC, A.NB_TEL_LN, A.NB_TEL_EXT, " + " 	 A.NM_COUNTY, A.NB_CONTROL, A.NM_CP_L, A.NM_CP_F, A.NM_CP_M, " + "      A.NM_NCP_L, A.NM_NCP_F, A.NM_NCP_M, A.NM_CUSTOMER_L, " + "      A.NM_CUSTOMER_F, A.NM_CUSTOMER_M, A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, B.NM_WRKR_F, B.NM_WRKR_L, B.NM_WRKR_MI, C.ID_WRKR_ASSIGN, A.TS_ASSIGNED " + " FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " C, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " B " + " WHERE UCASE(A.NM_CUSTOMER_L) = ?" + "   AND UCASE(A.NM_CUSTOMER_F) = ?" + "   AND A.CD_STAT <> 'DELT' " + "   AND C.ID_REFERENCE = A.ID_REFERENCE " + "   AND C.CD_STAT = A.CD_STAT " + "   AND C.ID_WRKR_ASSIGN = ? " + "   AND (C.CD_STAT = 'CLSD' OR (C.CD_STAT NOT IN ('CLSD','TRAN') AND C.TS_WRKR_END = ? ))" + "   AND B.ID_WRKR = C.ID_WRKR_ASSIGN " + " ORDER BY A.TS_CREATE DESC";

	/*
	 * "SELECT A.ID_REFERENCE " + "  FROM " + PropertyManager.getWebRegion() +
	 * Constants.TABLE_CSESRV_FORMS + " A, " + "  " +
	 * PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " B " +
	 * " WHERE UCASE(NM_CUSTOMER_L) = ?" + "   AND UCASE(NM_CUSTOMER_F) = ?" +
	 * "   AND B.ID_REFERENCE = A.ID_REFERENCE " + "   AND A.CD_STAT <> 'DELT' "
	 * + "   AND B.CD_STAT = A.CD_STAT " + "   AND B.ID_WRKR_ASSIGN = ? " +
	 * "   AND (B.CD_STAT = 'CLSD' OR (B.CD_STAT NOT IN ('CLSD','TRAN') AND B.TS_WRKR_END = ?))"
	 * ;
	 */

	// get task detail
	final String							SELECT_TASK_SQL					= "SELECT A.ID_REFERENCE, A.CD_STAT, A.TS_CREATE, A.ID_WRKR_CREATE, A.TS_UPDATE, " + "      A.ID_WRKR_UPDATE, A.CD_RFRL, A.ID_PART, A.NB_CASE, A.NB_SSN, A.NB_DKT, " + "      A.ID_EMAIL, A.NB_TEL_ACD, A.NB_TEL_EXC, A.NB_TEL_LN, A.NB_TEL_EXT, " + " 	 A.NM_COUNTY, A.NB_CONTROL, A.NM_CP_L, A.NM_CP_F, A.NM_CP_M, " + "      A.NM_NCP_L, A.NM_NCP_F, A.NM_NCP_M, A.NM_CUSTOMER_L, " + "      A.NM_CUSTOMER_F, A.NM_CUSTOMER_M, A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, B.NM_WRKR_F, B.NM_WRKR_L, B.NM_WRKR_MI, C.ID_WRKR_ASSIGN, A.TS_ASSIGNED " + " FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " C, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " B " + " WHERE A.ID_REFERENCE = ? " + "   AND C.ID_REFERENCE = A.ID_REFERENCE " + "   AND A.CD_STAT <> 'DELT' " + "   AND C.CD_STAT = A.CD_STAT " + "   AND B.ID_WRKR = C.ID_WRKR_ASSIGN " + "   AND (C.CD_STAT = 'CLSD' OR (C.CD_STAT NOT IN ('CLSD','TRAN') AND C.TS_WRKR_END = ?))";

	// get task history detail
	final String							SELECT_TRKHIST_SQL				= "SELECT A.ID_REFERENCE, A.TS_CREATE, A.CD_STAT, A.ID_WRKR_ASSIGN, " + "       A.TS_WRKR_START, A.TS_WRKR_END, A.ID_WRKR_CREATE, " + "       A.TS_UPDATE, A.ID_WRKR_UPDATE, B.DE_NOTE_TXT, C.NM_WRKR_L, C.NM_WRKR_F, C.NM_WRKR_MI " + "  FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " A, " + "       " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_NOTES + " B, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " C " + " WHERE A.ID_REFERENCE = ? " + "   AND B.ID_NOTE = A.ID_NOTE " + "   AND C.ID_WRKR = A.ID_WRKR_ASSIGN " + " ORDER BY A.TS_CREATE DESC ";

	// get duration for a referral process
	final String							SELECT_DUEDATE_SQL				= "SELECT NB_DURATION FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_REFERRAL_PROCESS + " WHERE CD_RFRL = ? " + "   AND CD_PRCS = ? ";

	// get task by due date
	final String							SELECT_DT_DUE_SQL				= "SELECT A.ID_REFERENCE, A.CD_STAT, DATE(A.TS_CREATE), A.ID_WRKR_CREATE, A.TS_UPDATE, " + "      A.ID_WRKR_UPDATE, A.CD_RFRL, A.ID_PART, A.NB_CASE, A.NB_SSN, A.NB_DKT, " + "      A.ID_EMAIL, A.NB_TEL_ACD, A.NB_TEL_EXC, A.NB_TEL_LN, A.NB_TEL_EXT, " + " 	 A.NM_COUNTY, A.NB_CONTROL, A.NM_CP_L, A.NM_CP_F, A.NM_CP_M, " + "      A.NM_NCP_L, A.NM_NCP_F, A.NM_NCP_M, A.NM_CUSTOMER_L, " + "      A.NM_CUSTOMER_F, A.NM_CUSTOMER_M, A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, B.NM_WRKR_F, B.NM_WRKR_L, B.NM_WRKR_MI, C.ID_WRKR_ASSIGN, A.TS_ASSIGNED, A.TS_CREATE " + " FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " C, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " B " + " WHERE A.CD_STAT <> 'DELT' " + "   AND C.ID_REFERENCE = A.ID_REFERENCE " + "   AND C.CD_STAT = A.CD_STAT " + "   AND (C.CD_STAT = 'CLSD' OR (C.CD_STAT NOT IN ('CLSD','TRAN') AND C.TS_WRKR_END = ?))" + "   AND B.ID_WRKR = C.ID_WRKR_ASSIGN " + "  ORDER BY A.TS_CREATE";

	// get task by due date for a worker
	final String							SELECT_DT_DUE_WRKR_SQL			= "SELECT A.ID_REFERENCE, A.CD_STAT, DATE(A.TS_CREATE), A.ID_WRKR_CREATE, A.TS_UPDATE, " + "      A.ID_WRKR_UPDATE, A.CD_RFRL, A.ID_PART, A.NB_CASE, A.NB_SSN, A.NB_DKT, " + "      A.ID_EMAIL, A.NB_TEL_ACD, A.NB_TEL_EXC, A.NB_TEL_LN, A.NB_TEL_EXT, " + " 	 A.NM_COUNTY, A.NB_CONTROL, A.NM_CP_L, A.NM_CP_F, A.NM_CP_M, " + "      A.NM_NCP_L, A.NM_NCP_F, A.NM_NCP_M, A.NM_CUSTOMER_L, " + "      A.NM_CUSTOMER_F, A.NM_CUSTOMER_M, A.ID_STAFF_1, A.ID_STAFF_2, A.ID_STAFF_3, A.ID_STAFF_4, B.NM_WRKR_F, B.NM_WRKR_L, B.NM_WRKR_MI, C.ID_WRKR_ASSIGN, A.TS_ASSIGNED, A.TS_CREATE " + " FROM " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FORMS + " A, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_FRMTRK + " C, " + "      " + PropertyManager.getWebRegion() + Constants.TABLE_CSESRV_WORKER + " B " + " WHERE A.CD_STAT <> 'DELT' " + "   AND C.ID_REFERENCE = A.ID_REFERENCE " + "   AND C.CD_STAT = A.CD_STAT " + "   AND C.ID_WRKR_ASSIGN = ? " + "   AND (C.CD_STAT = 'CLSD' OR (C.CD_STAT NOT IN ('CLSD','TRAN') AND C.TS_WRKR_END = ?))" + "   AND B.ID_WRKR = C.ID_WRKR_ASSIGN " + "  ORDER BY A.TS_CREATE";
	/**
	 * Constructor for UserRegistry.
	 */
	public TaskManager() throws BusinessLogicException
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

	public Collection findAllTasks() throws SQLException
	{
		log.info("Task Manager findAllTasks");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_ALL_SQL);
			ps.setTimestamp(1, TS_DEFAULT);

			rs = ps.executeQuery();

			while (rs.next())
			{
				TaskBean bean = new TaskBean();
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
				if (dSSN == 0)
				{

					bean.setNbSSN("");
				} else
				{
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
				a.add(bean);
			}
			rs.close();
			ps.close();

		} catch (SQLException e)
		{
			throw e;

		} finally
		{
			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();

			if (con != null)
			{
				con.commit();
				con.close();
			}
		}

		log.info("TaskManager findAllTasks will return :" + a.size());

		return a;
	}

	public Collection findAllPendingTasks() throws SQLException
	{
		log.info("Task Manager findAllPendingTasks");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_PENDALL_SQL);
			ps.setTimestamp(1, TS_DEFAULT);

			rs = ps.executeQuery();

			while (rs.next())
			{
				TaskBean bean = new TaskBean();
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
				if (dSSN == 0)
				{

					bean.setNbSSN("");

				} else
				{
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
				a.add(bean);
			}
			rs.close();
			ps.close();

		} catch (SQLException e)
		{
			throw e;

		} finally
		{
			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();

			if (con != null)
			{
				con.commit();
				con.close();
			}
		}

		log.info("Task Manager findAllPendingTasks will return :" + a.size());

		return a;
	}

	public TaskBean loadState(String idReference) throws SQLException
	{

		String key = idReference;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;

		TaskBean bean = new TaskBean();

		// log.info("TaskManager loadState with ID Reference: " + key);

		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_TASK_SQL);
			ps.setString(1, key);
			ps.setTimestamp(2, TS_DEFAULT);
			rs = ps.executeQuery();

			if (rs.next())
			{
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
				if (dSSN == 0)
				{

					bean.setNbSSN("");

				} else
				{
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

			/*
			 * Vector frmTrack = new Vector();
			 * 
			 * ps = con.prepareStatement(SELECT_TRKHIST_SQL); ps.setString(1,
			 * key); rs = ps.executeQuery(); while (rs.next()) { TaskHistoryBean
			 * thb = new TaskHistoryBean();
			 * thb.setIdReference(rs.getString(1).trim());
			 * thb.setTsCreate(rs.getTimestamp(2)); thb.setFormattedCreate(new
			 * FormatTimestamp().format(rs.getTimestamp(2)));
			 * thb.setCdStatus(rs.getString(3).trim());
			 * thb.setIdWrkrAssign(rs.getString(4).trim());
			 * thb.setTsWrkrStart(rs.getTimestamp(5)); thb.setFormattedStart(new
			 * FormatTimestamp().format(rs.getTimestamp(5)));
			 * thb.setTsWrkrEnd(rs.getTimestamp(6)); if
			 * (thb.getTsWrkrEnd().getTime() == 0) { thb.setFormattedEnd(""); }
			 * else { thb.setFormattedEnd(new
			 * FormatTimestamp().format(rs.getTimestamp(6))); }
			 * thb.setIdWrkrCreate(rs.getString(7).trim());
			 * thb.setTsUpdate(rs.getTimestamp(8)); thb.setFormattedUpdate(new
			 * FormatTimestamp().format(rs.getTimestamp(8)));
			 * thb.setIdWrkrUpdate(rs.getString(9).trim());
			 * thb.setNotes(rs.getString(10).trim());
			 * thb.setNmWorkerAssign(rs.getString(12).trim() + " " +
			 * rs.getString(13).trim() + " " + rs.getString(11).trim());
			 * 
			 * ps1 = con.prepareStatement(SELECT_DUEDATE_SQL); ps1.setString(1,
			 * bean.getCdType()); ps1.setString(2, thb.getCdStatus()); rs1 =
			 * ps1.executeQuery(); if (rs1.next()) { short duration =
			 * rs1.getShort(1); java.util.Date startDt = new
			 * java.util.Date(thb.getTsWrkrStart().getTime());
			 * 
			 * java.util.Calendar c = java.util.Calendar.getInstance();
			 * c.setTime(startDt);
			 */

			/**
			 * CT# 527783 - RK 08/24/04 Due date must be computed using
			 * BusinessCalendar class and not just by adding the duration to
			 * create date
			 **/
			/*
			 * java.util.Date dueDt = new
			 * BusinessCalendar().addBussinessDays(duration, c).getTime();
			 * Timestamp tsDue = new Timestamp(dueDt.getTime());
			 * thb.setTsDue(tsDue); thb.setFormattedDue(new
			 * FormatTimestamp().format(tsDue));
			 */

			/*
			 * c.add(Calendar.DATE, duration);
			 * 
			 * java.util.Date dueDt = c.getTime(); Timestamp tsDue = new
			 * Timestamp(dueDt.getTime()); thb.setTsDue(tsDue);
			 * thb.setFormattedDue(new FormatTimestamp().format(tsDue)); CT#
			 * 527783 - END
			 */
			/*
			 * } else { thb.setFormattedDue("n/a"); } rs1.close(); ps1.close();
			 * 
			 * frmTrack.addElement(thb); }
			 * 
			 * bean.setFrmTrack(frmTrack);
			 */

			// log.info("successfully loaded TaskManager loadState with ID
			// Reference: " + key );

			return bean;

		} catch (SQLException e)
		{
			throw e;

		} finally
		{
			if (rs1 != null)
				rs1.close();

			if (ps1 != null)
				ps1.close();

			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();

			if (con != null)
			{
				con.commit();
				con.close();
			}
		}
	}

	public Collection findAllApproval() throws SQLException
	{
		log.info("TaskManager findAllApproval");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_APPROVAL_SQL);
			ps.setTimestamp(1, TS_DEFAULT);

			rs = ps.executeQuery();

			while (rs.next())
			{
				TaskBean bean = new TaskBean();
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
				if (dSSN == 0)
				{

					bean.setNbSSN("");

				} else
				{
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
				a.add(bean);
			}
			rs.close();
			ps.close();

		} catch (SQLException e)
		{
			throw e;

		} finally
		{
			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();

			if (con != null)
			{
				con.commit();
				con.close();
			}
		}

		log.info("TaskManager findAllApproval will return :" + a.size());

		return a;
	}

	public Collection findAllOutstanding() throws SQLException
	{
		log.info("TaskManager findAllOutstanding");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_OUTSTANDING_SQL);
			ps.setTimestamp(1, TS_DEFAULT);

			rs = ps.executeQuery();

			while (rs.next())
			{
				TaskBean bean = new TaskBean();
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
				if (dSSN == 0)
				{

					bean.setNbSSN("");

				} else
				{
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
				a.add(bean);
			}
			rs.close();
			ps.close();

		} catch (SQLException e)
		{
			throw e;

		} finally
		{
			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();

			if (con != null)
			{
				con.commit();
				con.close();
			}
		}

		log.info("TaskManager findAllOutstanding will return :" + a.size());

		return a;
	}

	public Collection findPendingTasksByRfrl(String cdRfrl) throws SQLException
	{
		log.info("TaskManager findPendingTasksByRfrl");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_PENDRFRLTSKS_SQL);
			ps.setString(1, cdRfrl);
			ps.setTimestamp(2, TS_DEFAULT);

			rs = ps.executeQuery();

			while (rs.next())
			{
				TaskBean bean = new TaskBean();
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
				if (dSSN == 0)
				{

					bean.setNbSSN("");

				} else
				{
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
				a.add(bean);
			}
			rs.close();
			ps.close();

		} catch (SQLException e)
		{
			throw e;

		} finally
		{
			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();

			if (con != null)
			{
				con.commit();
				con.close();
			}
		}

		log.info("TaskManager findPendingTasksByRfrl will return :" + a.size());

		return a;
	}

	public Collection findWorkerTasks(String idWorker) throws SQLException
	{
		log.info("TaskManager findWorkerTasks");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_WRKRTSKS_SQL);
			ps.setString(1, idWorker);
			ps.setTimestamp(2, TS_DEFAULT);

			rs = ps.executeQuery();

			while (rs.next())
			{
				TaskBean bean = new TaskBean();
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
				if (dSSN == 0)
				{

					bean.setNbSSN("");

				} else
				{
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
				a.add(bean);
			}
			rs.close();
			ps.close();

		} catch (SQLException e)
		{
			throw e;

		} finally
		{
			if (rs != null)
			{
				rs.close();
			}
			if (ps != null)
				ps.close();

			if (con != null)
			{
				con.commit();
				con.close();
			}
		}

		log.info("TaskManager findWorkerTasks will return :" + a.size());

		return a;
	}

	public Collection findPendingWorkerTasks(String idWorker) throws SQLException
	{
		log.info("TaskManager findPendingWorkerTasks");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_PENDWRKRTSKS_SQL);
			ps.setString(1, idWorker);
			ps.setTimestamp(2, TS_DEFAULT);

			rs = ps.executeQuery();

			while (rs.next())
			{
				TaskBean bean = new TaskBean();
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
				if (dSSN == 0)
				{

					bean.setNbSSN("");

				} else
				{
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
				a.add(bean);
			}
			rs.close();
			ps.close();

		} catch (SQLException e)
		{
			throw e;

		} finally
		{
			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();

			if (con != null)
			{
				con.commit();
				con.close();
			}
		}

		log.info("TaskManager findPendingWorkerTasks will return :" + a.size());

		return a;
	}

	public Collection findTasksByRfrl(String cdRfrl) throws SQLException
	{
		log.info("TaskManager findTasksByRfrl");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_RFRLTSKS_SQL);
			ps.setString(1, cdRfrl);
			ps.setTimestamp(2, TS_DEFAULT);

			rs = ps.executeQuery();

			while (rs.next())
			{
				TaskBean bean = new TaskBean();
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
				if (dSSN == 0)
				{

					bean.setNbSSN("");

				} else
				{
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
				a.add(bean);
			}
			rs.close();
			ps.close();

		} catch (SQLException e)
		{
			throw e;

		} finally
		{
			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();

			if (con != null)
			{
				con.commit();
				con.close();
			}
		}

		log.info("TaskManager findTasksByRfrl will return :" + a.size());

		return a;
	}

	public Collection findTasksByRfrl(String cdRfrl, String idWorker) throws SQLException
	{
		log.info("TaskManager findTasksByRfrl");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_RFRLTSKS_WRKR_SQL);
			ps.setString(1, cdRfrl);
			ps.setString(2, idWorker);
			ps.setTimestamp(3, TS_DEFAULT);

			rs = ps.executeQuery();

			while (rs.next())
			{
				TaskBean bean = new TaskBean();
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
				if (dSSN == 0)
				{

					bean.setNbSSN("");

				} else
				{
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
				a.add(bean);
			}
			rs.close();
			ps.close();

		} catch (SQLException e)
		{
			throw e;

		} finally
		{
			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();

			if (con != null)
			{
				con.commit();
				con.close();
			}
		}

		log.info("TaskManager findTasksByRfrl will return :" + a.size());

		return a;
	}

	public Collection findTasksByCase(String nbCase) throws SQLException
	{
		log.info("TaskManager findTasksByCase");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_CASETSKS_SQL);
			ps.setString(1, nbCase);
			ps.setTimestamp(2, TS_DEFAULT);

			rs = ps.executeQuery();

			while (rs.next())
			{
				TaskBean bean = new TaskBean();
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
				if (dSSN == 0)
				{

					bean.setNbSSN("");

				} else
				{
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
				a.add(bean);
			}
			rs.close();
			ps.close();

		} catch (SQLException e)
		{
			throw e;

		} finally
		{
			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();

			if (con != null)
			{
				con.commit();
				con.close();
			}
		}

		log.info("TaskManager findTasksByCase will return :" + a.size());

		return a;
	}

	public Collection findTasksByCase(String nbCase, String idWorker) throws SQLException
	{
		log.info("TaskManager findTasksByCase");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_CASETSKS_WRKR_SQL);
			ps.setString(1, nbCase);
			ps.setString(2, idWorker);
			ps.setTimestamp(3, TS_DEFAULT);

			rs = ps.executeQuery();

			while (rs.next())
			{
				TaskBean bean = new TaskBean();
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
				if (dSSN == 0)
				{

					bean.setNbSSN("");

				} else
				{
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
				a.add(bean);
			}
			rs.close();
			ps.close();

		} catch (SQLException e)
		{
			throw e;

		} finally
		{
			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();

			if (con != null)
			{
				con.commit();
				con.close();
			}
		}

		log.info("TaskManager findTasksByCase will return :" + a.size());

		return a;
	}

	public Collection findTasksByMPI(String idPart) throws SQLException
	{
		log.info("TaskManager findTasksByMPI");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_MPITSKS_SQL);
			ps.setString(1, idPart);
			ps.setTimestamp(2, TS_DEFAULT);

			rs = ps.executeQuery();

			while (rs.next())
			{
				TaskBean bean = new TaskBean();
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
				if (dSSN == 0)
				{

					bean.setNbSSN("");

				} else
				{
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
				a.add(bean);
			}
			rs.close();
			ps.close();

		} catch (SQLException e)
		{
			throw e;

		} finally
		{
			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();

			if (con != null)
			{
				con.commit();
				con.close();
			}
		}

		log.info("TaskManager findTasksByMPI will return :" + a.size());

		return a;
	}

	public Collection findTasksByMPI(String idPart, String idWorker) throws SQLException
	{
		log.info("TaskManager findTasksByMPI");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_MPITSKS_WRKR_SQL);
			ps.setString(1, idPart);
			ps.setString(2, idWorker);
			ps.setTimestamp(3, TS_DEFAULT);

			rs = ps.executeQuery();

			while (rs.next())
			{
				TaskBean bean = new TaskBean();
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
				if (dSSN == 0)
				{

					bean.setNbSSN("");

				} else
				{
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
				a.add(bean);
			}
			rs.close();
			ps.close();

		} catch (SQLException e)
		{
			throw e;

		} finally
		{
			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();

			if (con != null)
			{
				con.commit();
				con.close();
			}
		}

		log.info("TaskManager findTasksByMPI will return :" + a.size());

		return a;
	}

	public Collection findTasksByDocket(String nbDkt) throws SQLException
	{
		log.info("TaskManager findTasksByDocket");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_DKTTSKS_SQL);
			ps.setString(1, nbDkt);
			ps.setTimestamp(2, TS_DEFAULT);

			rs = ps.executeQuery();

			while (rs.next())
			{
				TaskBean bean = new TaskBean();
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
				if (dSSN == 0)
				{

					bean.setNbSSN("");

				} else
				{
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
				a.add(bean);
			}
			rs.close();
			ps.close();

		} catch (SQLException e)
		{
			throw e;

		} finally
		{
			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();

			if (con != null)
			{
				con.commit();
				con.close();
			}
		}

		log.info("TaskManager findTasksByDocket will return :" + a.size());

		return a;
	}

	public Collection findTasksByDocket(String nbDkt, String idWorker) throws SQLException
	{
		log.info("TaskManager findTasksByDocket");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_DKTTSKS_WRKR_SQL);
			ps.setString(1, nbDkt);
			ps.setString(2, idWorker);
			ps.setTimestamp(3, TS_DEFAULT);

			rs = ps.executeQuery();

			while (rs.next())
			{
				TaskBean bean = new TaskBean();
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
				if (dSSN == 0)
				{

					bean.setNbSSN("");

				} else
				{
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
				a.add(bean);
			}
			rs.close();
			ps.close();

		} catch (SQLException e)
		{
			throw e;

		} finally
		{
			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();

			if (con != null)
			{
				con.commit();
				con.close();
			}
		}

		log.info("TaskManager findTasksByDocket will return :" + a.size());

		return a;
	}

	public Collection findTasksByEmail(String idEmail) throws SQLException
	{
		log.info("TaskManager findTasksByEmail");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_EMAILTSKS_SQL);
			ps.setString(1, idEmail);
			ps.setTimestamp(2, TS_DEFAULT);

			rs = ps.executeQuery();

			while (rs.next())
			{
				TaskBean bean = new TaskBean();
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
				if (dSSN == 0)
				{

					bean.setNbSSN("");

				} else
				{
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
				a.add(bean);
			}
			rs.close();
			ps.close();

		} catch (SQLException e)
		{
			throw e;

		} finally
		{
			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();

			if (con != null)
			{
				con.commit();
				con.close();
			}
		}

		log.info("TaskManager findTasksByEmail will return :" + a.size());

		return a;
	}

	public Collection findTasksByEmail(String idEmail, String idWorker) throws SQLException
	{
		log.info("TaskManager findTasksByEmail");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_EMAILTSKS_WRKR_SQL);
			ps.setString(1, idEmail);
			ps.setString(2, idWorker);
			ps.setTimestamp(3, TS_DEFAULT);

			rs = ps.executeQuery();

			while (rs.next())
			{
				TaskBean bean = new TaskBean();
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
				if (dSSN == 0)
				{

					bean.setNbSSN("");

				} else
				{
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
				a.add(bean);
			}
			rs.close();
			ps.close();

		} catch (SQLException e)
		{
			throw e;

		} finally
		{
			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();

			if (con != null)
			{
				con.commit();
				con.close();
			}
		}

		log.info("TaskManager findTasksByEmail will return :" + a.size());

		return a;
	}

	public Collection findTasksByCustomerLName(String lname) throws SQLException
	{
		log.info("TaskManager findTasksByCustomerLName");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_CUSTOMERLTSKS_SQL);
			ps.setString(1, lname);
			ps.setTimestamp(2, TS_DEFAULT);

			rs = ps.executeQuery();

			while (rs.next())
			{
				TaskBean bean = new TaskBean();
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
				if (dSSN == 0)
				{

					bean.setNbSSN("");

				} else
				{
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
				a.add(bean);
			}
			rs.close();
			ps.close();

		} catch (SQLException e)
		{
			throw e;

		} finally
		{
			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();

			if (con != null)
			{
				con.commit();
				con.close();
			}
		}

		log.info("TaskManager findTasksByCustomerLName will return :" + a.size());

		return a;
	}

	public Collection findTasksByCustomerLName(String lname, String idWorker) throws SQLException
	{
		log.info("TaskManager findTasksByCustomerLName");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_CUSTOMERLTSKS_WRKR_SQL);
			ps.setString(1, lname);
			ps.setString(2, idWorker);
			ps.setTimestamp(3, TS_DEFAULT);

			rs = ps.executeQuery();

			while (rs.next())
			{
				TaskBean bean = new TaskBean();
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
				if (dSSN == 0)
				{

					bean.setNbSSN("");

				} else
				{
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
				a.add(bean);
			}
			rs.close();
			ps.close();

		} catch (SQLException e)
		{
			throw e;

		} finally
		{
			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();

			if (con != null)
			{
				con.commit();
				con.close();
			}
		}

		log.info("TaskManager findTasksByCustomerLName will return :" + a.size());

		return a;
	}

	public Collection findTasksByCustomerName(String lname, String fname) throws SQLException
	{
		log.info("TaskManager findTasksByCustomerName");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_CUSTOMERLFTSKS_SQL);
			ps.setString(1, lname);
			ps.setString(2, fname);
			ps.setTimestamp(3, TS_DEFAULT);

			rs = ps.executeQuery();

			while (rs.next())
			{
				TaskBean bean = new TaskBean();
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
				if (dSSN == 0)
				{

					bean.setNbSSN("");

				} else
				{
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
				a.add(bean);
			}
			rs.close();
			ps.close();

		} catch (SQLException e)
		{
			throw e;

		} finally
		{
			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();

			if (con != null)
			{
				con.commit();
				con.close();
			}
		}

		log.info("TaskManager findTasksByCustomerName will return :" + a.size());

		return a;
	}

	public Collection findTasksByCustomerName(String lname, String fname, String idWorker) throws SQLException
	{
		log.info("TaskManager findTasksByCustomerName");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_CUSTOMERLFTSKS_WRKR_SQL);
			ps.setString(1, lname);
			ps.setString(2, fname);
			ps.setString(3, idWorker);
			ps.setTimestamp(4, TS_DEFAULT);

			rs = ps.executeQuery();

			while (rs.next())
			{
				TaskBean bean = new TaskBean();
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
				if (dSSN == 0)
				{

					bean.setNbSSN("");

				} else
				{
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
				a.add(bean);
			}
			rs.close();
			ps.close();

		} catch (SQLException e)
		{
			throw e;

		} finally
		{
			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();

			if (con != null)
			{
				con.commit();
				con.close();
			}
		}

		log.info("TaskManager findTasksByCustomerName will return :" + a.size());

		return a;
	}

	public Collection findTasksByCreateDate(java.sql.Date dt) throws SQLException
	{
		log.info("TaskManager findTasksByCreateDate");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_DT_CRE_SQL);
			ps.setDate(1, dt);
			ps.setTimestamp(2, TS_DEFAULT);

			rs = ps.executeQuery();

			while (rs.next())
			{
				TaskBean bean = new TaskBean();
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
				if (dSSN == 0)
				{

					bean.setNbSSN("");

				} else
				{
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
				a.add(bean);
			}
			rs.close();
			ps.close();

		} catch (SQLException e)
		{
			throw e;

		} finally
		{
			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();

			if (con != null)
			{
				con.commit();
				con.close();
			}
		}

		log.info("TaskManager findTasksByCreateDate will return :" + a.size());

		return a;
	}

	public Collection findTasksByCreateDate(java.sql.Date dt, String idWorker) throws SQLException
	{
		log.info("TaskManager findTasksByCreateDate");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_DT_CRE_WRKR_SQL);
			ps.setDate(1, dt);
			ps.setString(2, idWorker);
			ps.setTimestamp(3, TS_DEFAULT);

			rs = ps.executeQuery();

			while (rs.next())
			{
				TaskBean bean = new TaskBean();
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
				if (dSSN == 0)
				{

					bean.setNbSSN("");

				} else
				{
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
				a.add(bean);
			}
			rs.close();
			ps.close();

		} catch (SQLException e)
		{
			throw e;

		} finally
		{
			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();

			if (con != null)
			{
				con.commit();
				con.close();
			}
		}

		log.info("TaskManager findTasksByCreateDate will return :" + a.size());

		return a;
	}

	public Collection findTasksForDateRange(java.sql.Date frdt, java.sql.Date todt, String idWorker) throws SQLException
	{
		log.info("TaskManager findTasksForDateRange");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_DT_RANGE_WRKR_SQL);
			ps.setDate(1, frdt);
			ps.setDate(2, todt);
			ps.setString(3, idWorker);
			ps.setTimestamp(4, TS_DEFAULT);

			rs = ps.executeQuery();

			while (rs.next())
			{
				TaskBean bean = new TaskBean();
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
				if (dSSN == 0)
				{

					bean.setNbSSN("");

				} else
				{
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
				a.add(bean);
			}
			rs.close();
			ps.close();

		} catch (SQLException e)
		{
			throw e;

		} finally
		{
			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();

			if (con != null)
			{
				con.commit();
				con.close();
			}
		}

		log.info("TaskManager findTasksForDateRange will return :" + a.size());

		return a;
	}

	public Collection findTasksByCompleteDate(java.sql.Date dt) throws SQLException
	{
		log.info("TaskManager findTasksByCompleteDate");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_DT_COMPLETED_SQL);
			ps.setDate(1, dt);

			rs = ps.executeQuery();

			while (rs.next())
			{
				TaskBean bean = new TaskBean();
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
				if (dSSN == 0)
				{

					bean.setNbSSN("");

				} else
				{
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
				a.add(bean);
			}
			rs.close();
			ps.close();

		} catch (SQLException e)
		{
			throw e;

		} finally
		{
			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();

			if (con != null)
			{
				con.commit();
				con.close();
			}
		}

		log.info("TaskManager findTasksByCompleteDate will return :" + a.size());

		return a;
	}

	public Collection findTasksByCompleteDate(java.sql.Date dt, String idWorker) throws SQLException
	{
		log.info("TaskManager findTasksByCompleteDate");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList a = new ArrayList();

		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_DT_COMPLETED_WRKR_SQL);
			ps.setDate(1, dt);
			ps.setString(2, idWorker);

			rs = ps.executeQuery();

			while (rs.next())
			{
				TaskBean bean = new TaskBean();
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
				if (dSSN == 0)
				{

					bean.setNbSSN("");

				} else
				{
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
				a.add(bean);
			}
			rs.close();
			ps.close();

		} catch (SQLException e)
		{
			throw e;

		} finally
		{
			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();

			if (con != null)
			{
				con.commit();
				con.close();
			}
		}

		log.info("TaskManager findTasksByCompleteDate will return :" + a.size());

		return a;
	}

	public Collection findTasksByDueDate(java.sql.Date dt) throws SQLException
	{
		log.info("TaskManager findTasksByDueDate");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;

		ArrayList a = new ArrayList();

		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_DT_DUE_SQL);
			ps.setTimestamp(1, TS_DEFAULT);
			rs = ps.executeQuery();

			while (rs.next())
			{
				ps1 = con.prepareStatement(SELECT_DUEDATE_SQL);
				ps1.setString(1, rs.getString(7));
				ps1.setString(2, rs.getString(2));
				rs1 = ps1.executeQuery();

				if (rs1.next())
				{
					short duration = rs1.getShort(1);
					java.util.Date startDt = new java.util.Date(rs.getDate(3).getTime());

					java.util.Calendar c = java.util.Calendar.getInstance();
					c.setTime(startDt);
					c.add(Calendar.DATE, duration);

					java.util.Date dueDt = new java.util.Date(dt.getTime());

					if ((c.getTime()).getTime() == dueDt.getTime())
					{

						TaskBean bean = new TaskBean();
						bean.setIdReference(rs.getString(1).trim());
						bean.setCdStatus(rs.getString(2).trim());
						bean.setTsCreate(rs.getTimestamp(37));
						bean.setIdWrkrCreate(rs.getString(4).trim());
						bean.setTsUpdate(rs.getTimestamp(5));
						bean.setIdWrkrUpdate(rs.getString(6).trim());
						bean.setCdType(rs.getString(7).trim());
						bean.setIdPart(rs.getString(8).trim());
						bean.setNbCase(rs.getString(9).trim());
						double dSSN = rs.getBigDecimal(10).doubleValue();
						if (dSSN == 0)
						{

							bean.setNbSSN("");

						} else
						{
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
						a.add(bean);
					}
				}
				rs1.close();
				ps1.close();
			}

		} catch (SQLException e)
		{
			throw e;

		} finally
		{
			if (rs1 != null)
				rs1.close();

			if (ps1 != null)
				ps1.close();

			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();

			if (con != null)
			{
				con.commit();
				con.close();
			}
		}

		log.info("TaskManager findTasksByDueDate will return :" + a.size());

		return a;
	}

	public Collection findTasksByDueDate(java.sql.Date dt, String idWorker) throws SQLException
	{
		log.info("TaskManager findTasksByDueDate");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;

		ArrayList a = new ArrayList();

		try
		{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(SELECT_DT_DUE_WRKR_SQL);
			ps.setString(1, idWorker);
			ps.setTimestamp(2, TS_DEFAULT);
			rs = ps.executeQuery();

			while (rs.next())
			{
				ps1 = con.prepareStatement(SELECT_DUEDATE_SQL);
				ps1.setString(1, rs.getString(7));
				ps1.setString(2, rs.getString(2));
				rs1 = ps1.executeQuery();

				if (rs1.next())
				{
					short duration = rs1.getShort(1);
					java.util.Date startDt = new java.util.Date(rs.getDate(3).getTime());

					java.util.Calendar c = java.util.Calendar.getInstance();
					c.setTime(startDt);
					c.add(Calendar.DATE, duration);

					java.util.Date dueDt = new java.util.Date(dt.getTime());

					if ((c.getTime()).getTime() == dueDt.getTime())
					{

						TaskBean bean = new TaskBean();
						bean.setIdReference(rs.getString(1).trim());
						bean.setCdStatus(rs.getString(2).trim());
						bean.setTsCreate(rs.getTimestamp(37));
						bean.setIdWrkrCreate(rs.getString(4).trim());
						bean.setTsUpdate(rs.getTimestamp(5));
						bean.setIdWrkrUpdate(rs.getString(6).trim());
						bean.setCdType(rs.getString(7).trim());
						bean.setIdPart(rs.getString(8).trim());
						bean.setNbCase(rs.getString(9).trim());
						double dSSN = rs.getBigDecimal(10).doubleValue();
						if (dSSN == 0)
						{

							bean.setNbSSN("");

						} else
						{
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
						a.add(bean);
					}
				}
				rs1.close();
				ps1.close();
			}

		} catch (SQLException e)
		{
			throw e;

		} finally
		{
			if (rs1 != null)
				rs1.close();

			if (ps1 != null)
				ps1.close();

			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();

			if (con != null)
			{
				con.commit();
				con.close();
			}
		}

		log.info("TaskManager findTasksByDueDate will return :" + a.size());

		return a;
	}

	/**
	 *
	 */
	public static TaskManager getInstance() throws BusinessLogicException
	{

		if (null == taskManager)
		{
			taskManager = new TaskManager();
		}
		return taskManager;
	}

	/** CT 520496 Begin **/
	private String zeroPadNumber(int fieldlen, String number)
	{
		StringBuffer sb = new StringBuffer(number);
		int numZeros = fieldlen - number.length();

		for (int i = 0; i < numZeros; i++)
		{
			sb.insert(0, "0");
		}
		return sb.toString();
	}

}
