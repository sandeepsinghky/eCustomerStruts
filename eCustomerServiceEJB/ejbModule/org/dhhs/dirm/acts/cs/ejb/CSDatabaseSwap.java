package org.dhhs.dirm.acts.cs.ejb;
import javax.ejb.EJBException;
/**
 * Remote interface for Enterprise Bean: CSDatabaseSwap
 */
public interface CSDatabaseSwap extends javax.ejb.EJBObject {
	public void swap() throws EJBException, java.rmi.RemoteException;
	public void forceSwap(String region, String bkRegion) throws EJBException, java.rmi.RemoteException;
}
