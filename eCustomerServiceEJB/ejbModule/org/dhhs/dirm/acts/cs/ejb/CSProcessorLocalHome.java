package org.dhhs.dirm.acts.cs.ejb;
import org.dhhs.dirm.acts.cs.beans.TaskFormBean;
import java.util.Collection;
import javax.ejb.FinderException;
/**
 * Local Home interface for Enterprise Bean: CSProcessor
 */
public interface CSProcessorLocalHome extends javax.ejb.EJBLocalHome {
	/**
	 * Finds an instance using a key for Entity Bean: CSProcessor
	 */
	public org.dhhs.dirm.acts.cs.ejb.CSProcessorLocal findByPrimaryKey(java.lang.String primaryKey) throws javax.ejb.FinderException;
	/**
	 * ejbCreate
	 */
	public org.dhhs.dirm.acts.cs.ejb.CSProcessorLocal create(TaskFormBean taskBean) throws javax.ejb.CreateException;
	public java.util.Collection findAllApproval() throws javax.ejb.FinderException;
	public java.util.Collection findAllTasks() throws javax.ejb.FinderException;
	public java.util.Collection findOutstandingTasks() throws javax.ejb.FinderException;
	public java.util.Collection findTasksByCase(String nbCase) throws javax.ejb.FinderException;
	public java.util.Collection findTasksByDkt(String nbDocket) throws javax.ejb.FinderException;
	public java.util.Collection findTasksByEmail(String idEmail) throws javax.ejb.FinderException;
	public java.util.Collection findTasksByMPI(String idPart) throws javax.ejb.FinderException;
	public java.util.Collection findWorkerTasks(String idWorker) throws javax.ejb.FinderException;
	
	/**
	 * Home Methods
	 */
	public int countAll(String idWrkr);
	public int countApproval(String idWrkr);
	public int countOutstanding(String idWrkr);
	public int countCompleted(String idWrkr);
	public int countFormUsage(String cdFormType);
	
	public java.util.Collection groupAll();
	public java.util.Collection groupApproval();
	public java.util.Collection groupOutstanding();
	public java.util.Collection groupCompleted();
	
	/**
	 * ejbCreateTaskHistory
	 */
	public org.dhhs.dirm.acts.cs.ejb.CSProcessorLocal createTaskHistory(TaskFormBean taskBean) throws javax.ejb.CreateException;

	public int countProcessUsage(String cdFormType, String cdProcess);
	public java.util.Collection findTasksByCompleteDate(java.sql.Date date) throws javax.ejb.FinderException;
	public java.util.Collection findTasksByCreateDate(java.sql.Date date) throws javax.ejb.FinderException;
	public java.util.Collection findTasksByDueDate(java.sql.Date date) throws javax.ejb.FinderException;
	public java.util.Collection findTasksByCase(String nbCase, String idWorker) throws javax.ejb.FinderException;
	public java.util.Collection findTasksByCompleteDate(java.sql.Date date, String idWorker) throws javax.ejb.FinderException;
	public java.util.Collection findTasksByCreateDate(java.sql.Date date, String idWorker) throws javax.ejb.FinderException;
	public java.util.Collection findTasksByDkt(String nbDocket, String idWorker) throws javax.ejb.FinderException;
	public java.util.Collection findTasksByDueDate(java.sql.Date date, String idWorker) throws javax.ejb.FinderException;
	public java.util.Collection findTasksByEmail(String idEmail, String idWorker) throws javax.ejb.FinderException;
	public java.util.Collection findTasksByMPI(String idPart, String idWorker) throws javax.ejb.FinderException;
	public java.util.Collection findTasksByRfrl(String cdRfrl) throws javax.ejb.FinderException;
	public java.util.Collection findTasksByRfrl(String cdRfrl, String idWorker) throws javax.ejb.FinderException;
	public java.util.Collection findPendingTasksByRfrl(String cdRfrl) throws javax.ejb.FinderException;
	public java.util.Collection findPendingWorkerTasks(String idWorker) throws javax.ejb.FinderException;
	public java.util.Collection findAllPendingTasks() throws javax.ejb.FinderException;
	public java.util.Collection findTasksForDateRange(java.sql.Date frDate, java.sql.Date toDate, String idWorker) throws javax.ejb.FinderException;
	public java.util.Collection findTasksByCustomerLName(String lname) throws javax.ejb.FinderException;
	public java.util.Collection findTasksByCustomerLName(String lname, String idWorker) throws javax.ejb.FinderException;
	public java.util.Collection findTasksByCustomerName(String lname, String fname) throws javax.ejb.FinderException;
	public java.util.Collection findTasksByCustomerName(String lname, String fname, String idWorker) throws javax.ejb.FinderException;
}
