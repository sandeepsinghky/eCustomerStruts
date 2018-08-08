package org.dhhs.dirm.acts.cs.ejb;
import java.util.Collection;
/**
 * Local Home interface for Enterprise Bean: CSActsWorker
 */
public interface CSActsWorkerLocalHome extends javax.ejb.EJBLocalHome {
	/**
	 * Creates an instance from a key for Entity Bean: CSActsWorker
	 */
	public org.dhhs.dirm.acts.cs.ejb.CSActsWorkerLocal create() throws javax.ejb.CreateException;
	/**
	 * Finds an instance using a key for Entity Bean: CSActsWorker
	 */
	public org.dhhs.dirm.acts.cs.ejb.CSActsWorkerLocal findByPrimaryKey(java.lang.String primaryKey) throws javax.ejb.FinderException;
	public java.util.Collection findAllWorkers() throws javax.ejb.FinderException;
	public java.util.Collection findWorkersByName(String lastName, String firstName) throws javax.ejb.FinderException;
	public java.util.Collection findWorkersByLastName(String lastName) throws javax.ejb.FinderException;
}
