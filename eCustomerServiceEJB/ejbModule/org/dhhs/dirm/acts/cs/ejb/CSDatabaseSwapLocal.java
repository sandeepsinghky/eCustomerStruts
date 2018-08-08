package org.dhhs.dirm.acts.cs.ejb;
import javax.ejb.EJBException;
/**
 * Local interface for Enterprise Bean: CSDatabaseSwap
 */
public interface CSDatabaseSwapLocal extends javax.ejb.EJBLocalObject {
	public void swap() throws EJBException;
	public void forceSwap(String region, String bkRegion) throws EJBException;
}
