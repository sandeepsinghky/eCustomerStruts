package org.dhhs.dirm.acts.cs.ejb;

import javax.ejb.EJBException;
import org.dhhs.dirm.acts.cs.persister.PropertyManager;

/**
 * Bean implementation class for Enterprise Bean: CSDatabaseSwap
 */
public class CSDatabaseSwapBean implements javax.ejb.SessionBean {
	private javax.ejb.SessionContext mySessionCtx = null;
	/**
	 * getSessionContext
	 */
	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}
	/**
	 * setSessionContext
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) {
		mySessionCtx = ctx;
	}
	/**
	 * ejbCreate
	 */
	public void ejbCreate() throws javax.ejb.CreateException {
	}
	/**
	 * ejbActivate
	 */
	public void ejbActivate() {
	}
	/**
	 * ejbPassivate
	 */
	public void ejbPassivate() {
	}
	/**
	 * ejbRemove
	 */
	public void ejbRemove() {
	}
	
	public void swap() throws EJBException {
		System.out.println("CSDatabaseSwapBean swap method invoked.");
		
		String primaryRegion = PropertyManager.getRegion();
		System.out.println("Before Swap Primary Region: " + primaryRegion);
		
		String backupRegion = PropertyManager.getBackupRegion();
		System.out.println("Before Swap Backup Region: " + backupRegion);
		
		PropertyManager.setRegion(backupRegion);
		System.out.println("After Swap Primary Region: " + PropertyManager.getRegion());
		
		PropertyManager.setBackupRegion(primaryRegion);
		System.out.println("After Swap Backup Region: " + PropertyManager.getBackupRegion());

		System.out.println("CSDatabaseSwapBean swap method completed.");
		
	}
	
	public void forceSwap(String region, String bkRegion) throws EJBException {
		System.out.println("CSDatabaseSwapBean forceSwap method invoked.");
		
		System.out.println("Force Swap to use region : " + region);
		
		String primaryRegion = PropertyManager.getRegion();
		System.out.println("Before Swap Primary Region: " + primaryRegion);
		
		String backupRegion = PropertyManager.getBackupRegion();
		System.out.println("Before Swap Backup Region: " + backupRegion);
		
		PropertyManager.setRegion(region);
		System.out.println("After Swap Primary Region: " + PropertyManager.getRegion());
		
		PropertyManager.setBackupRegion(bkRegion);
		System.out.println("After Swap Backup Region: " + PropertyManager.getBackupRegion());

		System.out.println("CSDatabaseSwapBean forceSwap method completed.");
		
	}

}
