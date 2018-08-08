package org.dhhs.dirm.acts.cs.ejb;

import java.sql.*;
import java.util.*;
import javax.ejb.*;
import org.dhhs.dirm.acts.cs.ejb.util.*;
import org.dhhs.dirm.acts.cs.persister.*;
import org.dhhs.dirm.acts.cs.beans.*;

/**
 * Bean implementation class for Enterprise Bean: CSProcessor
 */
public class CSProcessorBean implements javax.ejb.EntityBean {
	private javax.ejb.EntityContext myEntityCtx;
	
	private CSTaskPersister csTaskPersister = null;
	
	private String idReference;
	
	private String cdType;
	
	private Timestamp tsCreate;
	
	private Timestamp tsAssign;
	
	private Timestamp tsUpdate;
	
	private String idWrkrCreate;
	
	private String idWrkrUpdate;
	
	private String cdStatus;
	
	private String nbCase;
	
	private String idPart;
	
	private String nbSSN;
	
	private String nbDocket;
	
	private String idWorker;
	
	private String nmWorker;
	
	private String idEmail;
	
	private String nbTelACD;
	
	private String nbTelEXC;
	
	private String nbTelLN;
	
	private String nbTelEXT;

	private String nmCounty;
	
	private String nbControl;
	
	private String nmCustomerFirst;
	private String nmCustomerLast;
	private String nmCustomerMi;
	
	private String nmCustParFirst;
	private String nmCustParLast;
	private String nmCustParMi;
	
	private String nmNonCustParFirst;
	private String nmNonCustParLast;
	private String nmNonCustParMi;
	
	private String nmRefSource1;
	private String nmRefSource2;
	private String nmRefSource3;
	private String nmRefSource4;
	
	private Vector frmTrack;
	
	private boolean dirty;
	
	private TaskEntityBean taskEntityBean = new TaskEntityBean();
	/**
	 * ejbActivate
	 */
	public void ejbActivate() {
	}
	/**
	 * ejbLoad
	 */
	public void ejbLoad() {

		try {
			csTaskPersister.loadState(this);
			dirty = false;
		} catch (SQLException se) {
			se.printStackTrace();
			throw new EJBException(se.getSQLState() + " code:" + se.getErrorCode());
		}
		
	}
	/**
	 * ejbPassivate
	 */
	public void ejbPassivate() {
	}
	/**
	 * ejbRemove
	 */
	public void ejbRemove() throws javax.ejb.RemoveException {
	}
	
	/**
	 * ejbStore
	 */
	public void ejbStore() {
		if (dirty) {
			try {
				csTaskPersister.storeState(this);
			} catch (SQLException se) {
				se.printStackTrace();
				throw new EJBException(se.getSQLState() + " code:" + se.getErrorCode());
			}		
		}
		dirty = false;
	}
	
	/**
	 * getEntityContext
	 */
	public javax.ejb.EntityContext getEntityContext() {
		return myEntityCtx;
	}
	/**
	 * setEntityContext
	 */
	public void setEntityContext(javax.ejb.EntityContext ctx) {
		myEntityCtx = ctx;

		if (csTaskPersister == null) {
			csTaskPersister = new CSTaskPersister();
		}	
	}
	
	/**
	 * unsetEntityContext
	 */
	public void unsetEntityContext() {
		myEntityCtx = null;

		if (csTaskPersister != null) {
			csTaskPersister.freeResources();
			csTaskPersister = null;
		}	
	}
	
	/**
	 * ejbCreate
	 */
	public java.lang.String ejbCreate(TaskFormBean taskBean) throws javax.ejb.CreateException {

		try {
			idReference = csTaskPersister.createState(taskBean);
			dirty = false;
		} catch (SQLException se) {
			se.printStackTrace();
			throw new CreateException(se.getMessage());
		}

		return idReference;
	}
	/**
	 * ejbPostCreate
	 */
	public void ejbPostCreate(TaskFormBean taskBean) throws javax.ejb.CreateException {
	}
	
	/**
	 * ejbCreateTaskHistory
	 */
	public java.lang.String ejbCreateTaskHistory(TaskFormBean taskBean) throws javax.ejb.CreateException {

		try {
			csTaskPersister.createHistory(taskBean);
			dirty = false;
		} catch (SQLException se) {
			se.printStackTrace();
			throw new CreateException(se.getMessage());
		}

		return idReference;
	}

	/**
	 * ejbPostCreate
	 */
	public void ejbPostCreateTaskHistory(TaskFormBean taskBean) throws javax.ejb.CreateException {
	}

	
	/**
	 * ejbFindByPrimaryKey
	 */
	public java.lang.String ejbFindByPrimaryKey(java.lang.String primaryKey) throws javax.ejb.FinderException {

		boolean found = false;

		try {
			found = csTaskPersister.findPrimaryKey(primaryKey);
		} catch (SQLException se) {
			se.printStackTrace();
			throw new javax.ejb.FinderException(se.getMessage());
		}
		if (found) {
			return primaryKey;
		} else {
			throw new ObjectNotFoundException();
		}
	}
	
	public Collection ejbFindAllTasks() throws javax.ejb.FinderException {

		Collection result;

		try {
			result = csTaskPersister.findAllTasks();
		} catch (Exception ex) {
			throw new EJBException("ejbFindAllTasks " + ex.getMessage());
		}
		return result;
	}	

	
	public Collection ejbFindAllPendingTasks() throws javax.ejb.FinderException {

		Collection result;

		try {
			result = csTaskPersister.findAllPendingTasks();
		} catch (Exception ex) {
			throw new EJBException("ejbFindAllPendingTasks " + ex.getMessage());
		}
		return result;
	}	
	
	
	public Collection ejbFindAllApproval() throws javax.ejb.FinderException {

		Collection result;

		try {
			result = csTaskPersister.findAllApproval();
		} catch (Exception ex) {
			throw new EJBException("ejbFindAllApproval " + ex.getMessage());
		}
		return result;
	}	

	
	public Collection ejbFindOutstandingTasks() throws javax.ejb.FinderException {

		Collection result;

		try {
			result = csTaskPersister.findAllOutstanding();
		} catch (Exception ex) {
			throw new EJBException("ejbFindOutstandingTasks " + ex.getMessage());
		}
		return result;
	}
	
	
	public Collection ejbFindTasksByCreateDate(java.sql.Date date) throws javax.ejb.FinderException {

		Collection result;

		try {
			result = csTaskPersister.findTasksByCreateDate(date);
		} catch (Exception ex) {
			throw new EJBException("ejbFindTasksByCreateDate " + ex.getMessage());
		}
		return result;
	}
	
	public Collection ejbFindTasksByCreateDate(java.sql.Date date, String idWorker) throws javax.ejb.FinderException {

		Collection result;

		try {
			result = csTaskPersister.findTasksByCreateDate(date, idWorker);
		} catch (Exception ex) {
			throw new EJBException("ejbFindTasksByCreateDate " + ex.getMessage());
		}
		return result;
	}

	
	public Collection ejbFindTasksForDateRange(java.sql.Date frDate, java.sql.Date toDate, String idWorker) throws javax.ejb.FinderException {

		Collection result;

		try {
			result = csTaskPersister.findTasksForDateRange(frDate, toDate, idWorker);
		} catch (Exception ex) {
			throw new EJBException("ejbFindTasksForDateRange " + ex.getMessage());
		}
		return result;
	}

	
	public Collection ejbFindTasksByCompleteDate(java.sql.Date date) throws javax.ejb.FinderException {

		Collection result;

		try {
			result = csTaskPersister.findTasksByCompleteDate(date);
		} catch (Exception ex) {
			throw new EJBException("ejbFindTasksByCompleteDate " + ex.getMessage());
		}
		return result;
	}
	
	public Collection ejbFindTasksByCompleteDate(java.sql.Date date, String idWorker) throws javax.ejb.FinderException {

		Collection result;

		try {
			result = csTaskPersister.findTasksByCompleteDate(date, idWorker);
		} catch (Exception ex) {
			throw new EJBException("ejbFindTasksByCompleteDate " + ex.getMessage());
		}
		return result;
	}

	
	public Collection ejbFindTasksByDueDate(java.sql.Date date) throws javax.ejb.FinderException {

		Collection result;

		try {
			result = csTaskPersister.findTasksByDueDate(date);
		} catch (Exception ex) {
			throw new EJBException("ejbFindTasksByDueDate " + ex.getMessage());
		}
		return result;
	}
	
	public Collection ejbFindTasksByDueDate(java.sql.Date date, String idWorker) throws javax.ejb.FinderException {

		Collection result;

		try {
			result = csTaskPersister.findTasksByDueDate(date,idWorker);
		} catch (Exception ex) {
			throw new EJBException("ejbFindTasksByDueDate " + ex.getMessage());
		}
		return result;
	}
	
	
	public int ejbHomeCountApproval(String idWrkr) {
		int count = 0;
		try {
			count = csTaskPersister.findApprovalCount(idWrkr);
		} catch (Exception ex) {
			throw new EJBException("ejbFindApprovalCount " + ex.getMessage());
		}
		return count;
	}

	public Collection ejbHomeGroupAll() {
		Collection c;
		try {
			c = csTaskPersister.groupAllCount();
		} catch (Exception ex) {
			throw new EJBException("ejbgroupAllCount " + ex.getMessage());
		}
		return c;
	}

	public Collection ejbHomeGroupApproval() {
		Collection c;
		try {
			c = csTaskPersister.groupApprovalCount();
		} catch (Exception ex) {
			throw new EJBException("ejbgroupApprovalCount " + ex.getMessage());
		}
		return c;
	}

	public Collection ejbHomeGroupOutstanding() {
		Collection c;
		try {
			c = csTaskPersister.groupOutstandingCount();
		} catch (Exception ex) {
			throw new EJBException("ejbgroupOutstandingCount " + ex.getMessage());
		}
		return c;
	}

	public Collection ejbHomeGroupCompleted() {
		Collection c;
		try {
			c = csTaskPersister.groupCompletedCount();
		} catch (Exception ex) {
			throw new EJBException("ejbgroupCompletedCount " + ex.getMessage());
		}
		return c;
	}

	
	public int ejbHomeCountFormUsage(String cdFormType) {
		int count = 0;
		try {
			count = csTaskPersister.findFormUsageCount(cdFormType);
		} catch (Exception ex) {
			throw new EJBException("ejbHomeCountFormUsage " + ex.getMessage());
		}
		return count;
	}
	
	public int ejbHomeCountProcessUsage(String cdFormType, String cdProcess) {
		int count = 0;
		try {
			count = csTaskPersister.findProcessUsageCount(cdFormType,cdProcess);
		} catch (Exception ex) {
			throw new EJBException("ejbHomeCountProcessUsage " + ex.getMessage());
		}
		return count;
	}	
	
	
	public int ejbHomeCountAll(String idWrkr) {
		int count = 0;
		try {
			count = csTaskPersister.findAllCount(idWrkr);
		} catch (Exception ex) {
			throw new EJBException("ejbFindAllCount " + ex.getMessage());
		}
		return count;
	}
	
	public int ejbHomeCountCompleted(String idWrkr) {
		int count = 0;
		try {
			count = csTaskPersister.findCompletedCount(idWrkr);
		} catch (Exception ex) {
			throw new EJBException("ejbFindCompletedCount " + ex.getMessage());
		}
		return count;
	}
	
	public int ejbHomeCountOutstanding(String idWrkr) {
		int count = 0;
		try {
			count = csTaskPersister.findOutstandingCount(idWrkr);
		} catch (Exception ex) {
			throw new EJBException("ejbFindOutstandingCount " + ex.getMessage());
		}
		return count;
	}

	public Collection ejbFindPendingWorkerTasks(String idWorker) throws javax.ejb.FinderException {

		Collection result;

		try {
			result = csTaskPersister.findPendingWorkerTasks(idWorker);
		} catch (Exception ex) {
			throw new EJBException("ejbFindPendingWorkerTasks " + ex.getMessage());
		}
		return result;
	}	
	
	public Collection ejbFindWorkerTasks(String idWorker) throws javax.ejb.FinderException {

		Collection result;

		try {
			result = csTaskPersister.findWorkerTasks(idWorker);
		} catch (Exception ex) {
			throw new EJBException("ejbFindWorkerTasks " + ex.getMessage());
		}
		return result;
	}	

	
	public Collection ejbFindPendingTasksByRfrl(String cdRfrl) throws javax.ejb.FinderException {

		Collection result;

		try {
			result = csTaskPersister.findPendingTasksByRfrl(cdRfrl);
		} catch (Exception ex) {
			throw new EJBException("ejbFindPendingTasksByRfrl " + ex.getMessage());
		}
		return result;
	}	

	
	public Collection ejbFindTasksByRfrl(String cdRfrl) throws javax.ejb.FinderException {

		Collection result;

		try {
			result = csTaskPersister.findTasksByRfrl(cdRfrl);
		} catch (Exception ex) {
			throw new EJBException("ejbFindTasksByRfrl " + ex.getMessage());
		}
		return result;
	}	
	
	public Collection ejbFindTasksByRfrl(String cdRfrl, String idWorker) throws javax.ejb.FinderException {

		Collection result;

		try {
			result = csTaskPersister.findTasksByRfrl(cdRfrl, idWorker);
		} catch (Exception ex) {
			throw new EJBException("ejbFindTasksByRfrl " + ex.getMessage());
		}
		return result;
	}	
	
	public Collection ejbFindTasksByCase(String nbCase) throws javax.ejb.FinderException {

		Collection result;

		try {
			result = csTaskPersister.findTasksByCase(nbCase);
		} catch (Exception ex) {
			throw new EJBException("ejbFindTasksByCase " + ex.getMessage());
		}
		return result;
	}	
	
	public Collection ejbFindTasksByCase(String nbCase, String idWorker) throws javax.ejb.FinderException {

		Collection result;

		try {
			result = csTaskPersister.findTasksByCase(nbCase, idWorker);
		} catch (Exception ex) {
			throw new EJBException("ejbFindTasksByCase " + ex.getMessage());
		}
		return result;
	}	
	
	public Collection ejbFindTasksByMPI(String idPart) throws javax.ejb.FinderException {

		Collection result;

		try {
			result = csTaskPersister.findTasksByMPI(idPart);
		} catch (Exception ex) {
			throw new EJBException("ejbFindTasksByMPI " + ex.getMessage());
		}
		return result;
	}	
	
	public Collection ejbFindTasksByMPI(String idPart, String idWorker) throws javax.ejb.FinderException {

		Collection result;

		try {
			result = csTaskPersister.findTasksByMPI(idPart, idWorker);
		} catch (Exception ex) {
			throw new EJBException("ejbFindTasksByMPI " + ex.getMessage());
		}
		return result;
	}	
	
	public Collection ejbFindTasksByDkt(String nbDocket) throws javax.ejb.FinderException {

		Collection result;

		try {
			result = csTaskPersister.findTasksByDocket(nbDocket);
		} catch (Exception ex) {
			throw new EJBException("ejbFindTasksByDkt " + ex.getMessage());
		}
		return result;
	}	
	
	public Collection ejbFindTasksByDkt(String nbDocket, String idWorker) throws javax.ejb.FinderException {

		Collection result;

		try {
			result = csTaskPersister.findTasksByDocket(nbDocket, idWorker);
		} catch (Exception ex) {
			throw new EJBException("ejbFindTasksByDkt " + ex.getMessage());
		}
		return result;
	}	

	public Collection ejbFindTasksByEmail(String idEmail) throws javax.ejb.FinderException {

		Collection result;

		try {
			result = csTaskPersister.findTasksByEmail(idEmail);
		} catch (Exception ex) {
			throw new EJBException("ejbFindTasksByEmail " + ex.getMessage());
		}
		return result;
	}	

	public Collection ejbFindTasksByEmail(String idEmail, String idWorker) throws javax.ejb.FinderException {

		Collection result;

		try {
			result = csTaskPersister.findTasksByEmail(idEmail, idWorker);
		} catch (Exception ex) {
			throw new EJBException("ejbFindTasksByEmail " + ex.getMessage());
		}
		return result;
	}	

	public Collection ejbFindTasksByCustomerLName(String lname) throws javax.ejb.FinderException {

		Collection result;

		try {
			result = csTaskPersister.findTasksByCustomerLName(lname);
		} catch (Exception ex) {
			throw new EJBException("ejbFindTasksByCustomerLName " + ex.getMessage());
		}
		return result;
	}	

	public Collection ejbFindTasksByCustomerLName(String lname, String idWorker) throws javax.ejb.FinderException {

		Collection result;

		try {
			result = csTaskPersister.findTasksByCustomerLName(lname, idWorker);
		} catch (Exception ex) {
			throw new EJBException("findTasksByCustomerLName " + ex.getMessage());
		}
		return result;
	}	


	public Collection ejbFindTasksByCustomerName(String lname, String fname) throws javax.ejb.FinderException {

		Collection result;

		try {
			result = csTaskPersister.findTasksByCustomerName(lname, fname);
		} catch (Exception ex) {
			throw new EJBException("ejbFindTasksByCustomerName " + ex.getMessage());
		}
		return result;
	}	

	public Collection ejbFindTasksByCustomerName(String lname, String fname, String idWorker) throws javax.ejb.FinderException {

		Collection result;

		try {
			result = csTaskPersister.findTasksByCustomerName(lname, fname, idWorker);
		} catch (Exception ex) {
			throw new EJBException("findTasksByCustomerName " + ex.getMessage());
		}
		return result;
	}	

	
	/**
	 * Returns the cdStatus.
	 * @return String
	 */
	public String getCdStatus() {
		return cdStatus;
	}

	/**
	 * Returns the cdType.
	 * @return String
	 */
	public String getCdType() {
		return cdType;
	}

	/**
	 * Returns the csTaskPersister.
	 * @return CSTaskPersister
	 */
	public CSTaskPersister getCsTaskPersister() {
		return csTaskPersister;
	}

	/**
	 * Returns the idEmail.
	 * @return String
	 */
	public String getIdEmail() {
		return idEmail;
	}

	/**
	 * Returns the idPart.
	 * @return String
	 */
	public String getIdPart() {
		return idPart;
	}

	/**
	 * Returns the idReference.
	 * @return String
	 */
	public String getIdReference() {
		return idReference;
	}

	/**
	 * Returns the idWrkrCreate.
	 * @return String
	 */
	public String getIdWrkrCreate() {
		return idWrkrCreate;
	}

	/**
	 * Returns the idWrkrUpdate.
	 * @return String
	 */
	public String getIdWrkrUpdate() {
		return idWrkrUpdate;
	}

	/**
	 * Returns the myEntityCtx.
	 * @return javax.ejb.EntityContext
	 */
	public javax.ejb.EntityContext getMyEntityCtx() {
		return myEntityCtx;
	}

	/**
	 * Returns the nbCase.
	 * @return String
	 */
	public String getNbCase() {
		return nbCase;
	}

	/**
	 * Returns the nbDocket.
	 * @return String
	 */
	public String getNbDocket() {
		return nbDocket;
	}

	/**
	 * Returns the nbSSN.
	 * @return String
	 */
	public String getNbSSN() {
		return nbSSN;
	}

	/**
	 * Returns the nbTelACD.
	 * @return String
	 */
	public String getNbTelACD() {
		return nbTelACD;
	}

	/**
	 * Returns the nbTelEXC.
	 * @return String
	 */
	public String getNbTelEXC() {
		return nbTelEXC;
	}

	/**
	 * Returns the nbTelEXT.
	 * @return String
	 */
	public String getNbTelEXT() {
		return nbTelEXT;
	}

	/**
	 * Returns the nbTelLN.
	 * @return String
	 */
	public String getNbTelLN() {
		return nbTelLN;
	}

	/**
	 * Returns the tsCreate.
	 * @return Timestamp
	 */
	public Timestamp getTsCreate() {
		return tsCreate;
	}

	/**
	 * Returns the tsUpdate.
	 * @return Timestamp
	 */
	public Timestamp getTsUpdate() {
		return tsUpdate;
	}

	/**
	 * Sets the cdStatus.
	 * @param cdStatus The cdStatus to set
	 */
	public void setCdStatus(String cdStatus) {
		this.cdStatus = cdStatus;
		dirty = true;
	}

	/**
	 * Sets the cdType.
	 * @param cdType The cdType to set
	 */
	public void setCdType(String cdType) {
		this.cdType = cdType;
		dirty = true;
	}

	/**
	 * Sets the csTaskPersister.
	 * @param csTaskPersister The csTaskPersister to set
	 */
	public void setCsTaskPersister(CSTaskPersister csTaskPersister) {
		this.csTaskPersister = csTaskPersister;
		dirty = true;
	}

	/**
	 * Sets the idEmail.
	 * @param idEmail The idEmail to set
	 */
	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
		dirty = true;
	}

	/**
	 * Sets the idPart.
	 * @param idPart The idPart to set
	 */
	public void setIdPart(String idPart) {
		this.idPart = idPart;
		dirty = true;
	}

	/**
	 * Sets the idReference.
	 * @param idReference The idReference to set
	 */
	public void setIdReference(String idReference) {
		this.idReference = idReference;
		dirty = true;
	}

	/**
	 * Sets the idWrkrCreate.
	 * @param idWrkrCreate The idWrkrCreate to set
	 */
	public void setIdWrkrCreate(String idWrkrCreate) {
		this.idWrkrCreate = idWrkrCreate;
		dirty = true;
	}

	/**
	 * Sets the idWrkrUpdate.
	 * @param idWrkrUpdate The idWrkrUpdate to set
	 */
	public void setIdWrkrUpdate(String idWrkrUpdate) {
		this.idWrkrUpdate = idWrkrUpdate;
		dirty = true;
	}

	/**
	 * Sets the myEntityCtx.
	 * @param myEntityCtx The myEntityCtx to set
	 */
	public void setMyEntityCtx(javax.ejb.EntityContext myEntityCtx) {
		this.myEntityCtx = myEntityCtx;
	}

	/**
	 * Sets the nbCase.
	 * @param nbCase The nbCase to set
	 */
	public void setNbCase(String nbCase) {
		this.nbCase = nbCase;
		dirty = true;
	}

	/**
	 * Sets the nbDocket.
	 * @param nbDocket The nbDocket to set
	 */
	public void setNbDocket(String nbDocket) {
		this.nbDocket = nbDocket;
		dirty = true;
	}

	/**
	 * Sets the nbSSN.
	 * @param nbSSN The nbSSN to set
	 */
	public void setNbSSN(String nbSSN) {
		this.nbSSN = nbSSN;
		dirty = true;
	}

	/**
	 * Sets the nbTelACD.
	 * @param nbTelACD The nbTelACD to set
	 */
	public void setNbTelACD(String nbTelACD) {
		this.nbTelACD = nbTelACD;
		dirty = true;
	}

	/**
	 * Sets the nbTelEXC.
	 * @param nbTelEXC The nbTelEXC to set
	 */
	public void setNbTelEXC(String nbTelEXC) {
		this.nbTelEXC = nbTelEXC;
		dirty = true;
	}

	/**
	 * Sets the nbTelEXT.
	 * @param nbTelEXT The nbTelEXT to set
	 */
	public void setNbTelEXT(String nbTelEXT) {
		this.nbTelEXT = nbTelEXT;
		dirty = true;
	}

	/**
	 * Sets the nbTelLN.
	 * @param nbTelLN The nbTelLN to set
	 */
	public void setNbTelLN(String nbTelLN) {
		this.nbTelLN = nbTelLN;
		dirty = true;
	}

	/**
	 * Sets the tsCreate.
	 * @param tsCreate The tsCreate to set
	 */
	public void setTsCreate(Timestamp tsCreate) {
		this.tsCreate = tsCreate;
		dirty = true;
	}

	/**
	 * Sets the tsUpdate.
	 * @param tsUpdate The tsUpdate to set
	 */
	public void setTsUpdate(Timestamp tsUpdate) {
		this.tsUpdate = tsUpdate;
		dirty = true;
	}

	/**
	 * Returns the idWorker.
	 * @return String
	 */
	public String getIdWorker() {
		return idWorker;
	}

	/**
	 * Sets the idWorker.
	 * @param idWorker The idWorker to set
	 */
	public void setIdWorker(String idWorker) {
		this.idWorker = idWorker;
		dirty = true;
	}

	/**
	 * Returns the frmTrack.
	 * @return Vector
	 */
	public Vector getFrmTrack() {
		return frmTrack;
	}

	/**
	 * Sets the frmTrack.
	 * @param frmTrack The frmTrack to set
	 */
	public void setFrmTrack(Vector frmTrack) {
		this.frmTrack = frmTrack;
		dirty = true;
	}

	/**
	 * Returns the nbControl.
	 * @return String
	 */
	public String getNbControl() {
		return nbControl;
	}

	/**
	 * Returns the nmCounty.
	 * @return String
	 */
	public String getNmCounty() {
		return nmCounty;
	}

	/**
	 * Returns the nmCustomerFirst.
	 * @return String
	 */
	public String getNmCustomerFirst() {
		return nmCustomerFirst;
	}

	/**
	 * Returns the nmCustomerLast.
	 * @return String
	 */
	public String getNmCustomerLast() {
		return nmCustomerLast;
	}

	/**
	 * Returns the nmCustomerMi.
	 * @return String
	 */
	public String getNmCustomerMi() {
		return nmCustomerMi;
	}

	/**
	 * Returns the nmCustParFirst.
	 * @return String
	 */
	public String getNmCustParFirst() {
		return nmCustParFirst;
	}

	/**
	 * Returns the nmCustParLast.
	 * @return String
	 */
	public String getNmCustParLast() {
		return nmCustParLast;
	}

	/**
	 * Returns the nmCustParMi.
	 * @return String
	 */
	public String getNmCustParMi() {
		return nmCustParMi;
	}

	/**
	 * Returns the nmNonCustParFirst.
	 * @return String
	 */
	public String getNmNonCustParFirst() {
		return nmNonCustParFirst;
	}

	/**
	 * Returns the nmNonCustParLast.
	 * @return String
	 */
	public String getNmNonCustParLast() {
		return nmNonCustParLast;
	}

	/**
	 * Returns the nmNonCustParMi.
	 * @return String
	 */
	public String getNmNonCustParMi() {
		return nmNonCustParMi;
	}

	/**
	 * Returns the nmRefSource1.
	 * @return String
	 */
	public String getNmRefSource1() {
		return nmRefSource1;
	}

	/**
	 * Returns the nmRefSource2.
	 * @return String
	 */
	public String getNmRefSource2() {
		return nmRefSource2;
	}

	/**
	 * Returns the nmRefSource3.
	 * @return String
	 */
	public String getNmRefSource3() {
		return nmRefSource3;
	}

	/**
	 * Returns the nmRefSource4.
	 * @return String
	 */
	public String getNmRefSource4() {
		return nmRefSource4;
	}

	/**
	 * Returns the nmWorker.
	 * @return String
	 */
	public String getNmWorker() {
		return nmWorker;
	}

	/**
	 * Sets the nbControl.
	 * @param nbControl The nbControl to set
	 */
	public void setNbControl(String nbControl) {
		this.nbControl = nbControl;
		dirty = true;
	}

	/**
	 * Sets the nmCounty.
	 * @param nmCounty The nmCounty to set
	 */
	public void setNmCounty(String nmCounty) {
		this.nmCounty = nmCounty;
		dirty = true;
	}

	/**
	 * Sets the nmCustomerFirst.
	 * @param nmCustomerFirst The nmCustomerFirst to set
	 */
	public void setNmCustomerFirst(String nmCustomerFirst) {
		this.nmCustomerFirst = nmCustomerFirst;
		dirty = true;
	}

	/**
	 * Sets the nmCustomerLast.
	 * @param nmCustomerLast The nmCustomerLast to set
	 */
	public void setNmCustomerLast(String nmCustomerLast) {
		this.nmCustomerLast = nmCustomerLast;
		dirty = true;
	}

	/**
	 * Sets the nmCustomerMi.
	 * @param nmCustomerMi The nmCustomerMi to set
	 */
	public void setNmCustomerMi(String nmCustomerMi) {
		this.nmCustomerMi = nmCustomerMi;
		dirty = true;
	}

	/**
	 * Sets the nmCustParFirst.
	 * @param nmCustParFirst The nmCustParFirst to set
	 */
	public void setNmCustParFirst(String nmCustParFirst) {
		this.nmCustParFirst = nmCustParFirst;
		dirty = true;
	}

	/**
	 * Sets the nmCustParLast.
	 * @param nmCustParLast The nmCustParLast to set
	 */
	public void setNmCustParLast(String nmCustParLast) {
		this.nmCustParLast = nmCustParLast;
		dirty = true;
	}

	/**
	 * Sets the nmCustParMi.
	 * @param nmCustParMi The nmCustParMi to set
	 */
	public void setNmCustParMi(String nmCustParMi) {
		this.nmCustParMi = nmCustParMi;
		dirty = true;
	}

	/**
	 * Sets the nmNonCustParFirst.
	 * @param nmNonCustParFirst The nmNonCustParFirst to set
	 */
	public void setNmNonCustParFirst(String nmNonCustParFirst) {
		this.nmNonCustParFirst = nmNonCustParFirst;
		dirty = true;
	}

	/**
	 * Sets the nmNonCustParLast.
	 * @param nmNonCustParLast The nmNonCustParLast to set
	 */
	public void setNmNonCustParLast(String nmNonCustParLast) {
		this.nmNonCustParLast = nmNonCustParLast;
		dirty = true;
	}

	/**
	 * Sets the nmNonCustParMi.
	 * @param nmNonCustParMi The nmNonCustParMi to set
	 */
	public void setNmNonCustParMi(String nmNonCustParMi) {
		this.nmNonCustParMi = nmNonCustParMi;
		dirty = true;
	}

	/**
	 * Sets the nmRefSource1.
	 * @param nmRefSource1 The nmRefSource1 to set
	 */
	public void setNmRefSource1(String nmRefSource1) {
		this.nmRefSource1 = nmRefSource1;
		dirty = true;
	}

	/**
	 * Sets the nmRefSource2.
	 * @param nmRefSource2 The nmRefSource2 to set
	 */
	public void setNmRefSource2(String nmRefSource2) {
		this.nmRefSource2 = nmRefSource2;
		dirty = true;
	}

	/**
	 * Sets the nmRefSource3.
	 * @param nmRefSource3 The nmRefSource3 to set
	 */
	public void setNmRefSource3(String nmRefSource3) {
		this.nmRefSource3 = nmRefSource3;
		dirty = true;
	}

	/**
	 * Sets the nmRefSource4.
	 * @param nmRefSource4 The nmRefSource4 to set
	 */
	public void setNmRefSource4(String nmRefSource4) {
		this.nmRefSource4 = nmRefSource4;
		dirty = true;
	}

	/**
	 * Sets the nmWorker.
	 * @param nmWorker The nmWorker to set
	 */
	public void setNmWorker(String nmWorker) {
		this.nmWorker = nmWorker;
		dirty = true;
	}

	/**
	 * Returns the taskEntityBean.
	 * @return TaskEntityBean
	 */
	public TaskEntityBean getTaskEntityBean() {
		
		taskEntityBean.setCdStatus(cdStatus);
		taskEntityBean.setCdType(cdType);
		taskEntityBean.setFrmTrack(frmTrack);
		taskEntityBean.setIdEmail(idEmail);
		taskEntityBean.setIdPart(idPart);
		taskEntityBean.setIdReference(idReference);
		taskEntityBean.setIdWorker(idWorker);
		taskEntityBean.setIdWrkrCreate(idWrkrCreate);
		taskEntityBean.setIdWrkrUpdate(idWrkrUpdate);
		taskEntityBean.setNbCase(nbCase);
		taskEntityBean.setNbControl(nbControl);
		taskEntityBean.setNbDocket(nbDocket);
		taskEntityBean.setNbSSN(nbSSN);
		taskEntityBean.setNbTelACD(nbTelACD);
		taskEntityBean.setNbTelEXC(nbTelEXC);
		taskEntityBean.setNbTelEXT(nbTelEXT);
		taskEntityBean.setNbTelLN(nbTelLN);
		taskEntityBean.setNmCounty(nmCounty);
		taskEntityBean.setNmCustomerFirst(nmCustomerFirst);
		taskEntityBean.setNmCustomerLast(nmCustomerLast);
		taskEntityBean.setNmCustomerMi(nmCustomerMi);
		taskEntityBean.setNmCustParFirst(nmCustParFirst);
		taskEntityBean.setNmCustParLast(nmCustParLast);
		taskEntityBean.setNmCustParMi(nmCustParMi);
		taskEntityBean.setNmNonCustParFirst(nmNonCustParFirst);
		taskEntityBean.setNmNonCustParLast(nmNonCustParLast);
		taskEntityBean.setNmNonCustParMi(nmNonCustParMi);
		taskEntityBean.setNmRefSource1(nmRefSource1);
		taskEntityBean.setNmRefSource2(nmRefSource2);
		taskEntityBean.setNmRefSource3(nmRefSource3);
		taskEntityBean.setNmRefSource4(nmRefSource4);
		taskEntityBean.setNmWorker(nmWorker);
		taskEntityBean.setTsCreate(tsCreate);
		taskEntityBean.setTsUpdate(tsUpdate);
		taskEntityBean.setTsAssign(tsAssign);
						
		return taskEntityBean;
	}

	/**
	 * Sets the taskEntityBean.
	 * @param taskEntityBean The taskEntityBean to set
	 */
	public void setTaskEntityBean(TaskEntityBean taskEntityBean) {
		this.taskEntityBean = taskEntityBean;
	}

	/**
	 * Returns the tsAssign.
	 * @return Timestamp
	 */
	public Timestamp getTsAssign() {
		return tsAssign;
	}

	/**
	 * Sets the tsAssign.
	 * @param tsAssign The tsAssign to set
	 */
	public void setTsAssign(Timestamp tsAssign) {
		this.tsAssign = tsAssign;
	}

}

