package org.dhhs.dirm.acts.cs.ejb;
import java.util.Collection;
import org.dhhs.dirm.acts.cs.beans.TaskFormBean;
/**
 * Home interface for Enterprise Bean: CSProcessor
 */
public interface CSProcessorHome extends javax.ejb.EJBHome {
	/**
	 * Finds an instance using a key for Entity Bean: CSProcessor
	 */
	public org.dhhs.dirm.acts.cs.ejb.CSProcessor findByPrimaryKey(java.lang.String primaryKey)
		throws javax.ejb.FinderException, java.rmi.RemoteException;
		
	public int countAll(String idWrkr) throws java.rmi.RemoteException;
	public int countApproval(String idWrkr) throws java.rmi.RemoteException;
	public int countCompleted(String idWrkr) throws java.rmi.RemoteException;
	public int countOutstanding(String idWrkr) throws java.rmi.RemoteException;
	public int countFormUsage(String cdFormType) throws java.rmi.RemoteException;
	
	public java.util.Collection groupAll() throws java.rmi.RemoteException;
	public java.util.Collection groupApproval() throws java.rmi.RemoteException;
	public java.util.Collection groupCompleted() throws java.rmi.RemoteException;
	public java.util.Collection groupOutstanding() throws java.rmi.RemoteException;
	/**
	 * ejbCreate
	 */
	public org.dhhs.dirm.acts.cs.ejb.CSProcessor create(TaskFormBean taskBean) throws javax.ejb.CreateException, java.rmi.RemoteException;
	/**
	 * ejbCreateTaskHistory
	 */
	public org.dhhs.dirm.acts.cs.ejb.CSProcessor createTaskHistory(TaskFormBean taskBean) throws javax.ejb.CreateException, java.rmi.RemoteException;
	public java.util.Collection findAllApproval() throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Collection findAllTasks() throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Collection findOutstandingTasks() throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Collection findTasksByCase(String nbCase) throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Collection findTasksByDkt(String nbDocket) throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Collection findTasksByEmail(String idEmail) throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Collection findTasksByMPI(String idPart) throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Collection findWorkerTasks(String idWorker) throws javax.ejb.FinderException, java.rmi.RemoteException;
	public int countProcessUsage(String cdFormType, String cdProcess) throws java.rmi.RemoteException;
	public java.util.Collection findTasksByCompleteDate(java.sql.Date date) throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Collection findTasksByCreateDate(java.sql.Date date) throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Collection findTasksByDueDate(java.sql.Date date) throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Collection findTasksByCase(String nbCase, String idWorker) throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Collection findTasksByCompleteDate(java.sql.Date date, String idWorker)
		throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Collection findTasksByCreateDate(java.sql.Date date, String idWorker)
		throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Collection findTasksByDkt(String nbDocket, String idWorker) throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Collection findTasksByDueDate(java.sql.Date date, String idWorker) throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Collection findTasksByEmail(String idEmail, String idWorker) throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Collection findTasksByMPI(String idPart, String idWorker) throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Collection findTasksByRfrl(String cdRfrl) throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Collection findTasksByRfrl(String cdRfrl, String idWorker) throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Collection findPendingTasksByRfrl(String cdRfrl) throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Collection findPendingWorkerTasks(String idWorker) throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Collection findAllPendingTasks() throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Collection findTasksForDateRange(java.sql.Date frDate, java.sql.Date toDate, String idWorker)
		throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Collection findTasksByCustomerLName(String lname) throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Collection findTasksByCustomerLName(String lname, String idWorker) throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Collection findTasksByCustomerName(String lname, String fname) throws javax.ejb.FinderException, java.rmi.RemoteException;
	public java.util.Collection findTasksByCustomerName(String lname, String fname, String idWorker)
		throws javax.ejb.FinderException, java.rmi.RemoteException;
}
