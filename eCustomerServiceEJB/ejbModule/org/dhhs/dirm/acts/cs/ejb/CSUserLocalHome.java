package org.dhhs.dirm.acts.cs.ejb;
import java.util.Collection;
/**
 * Local Home interface for Enterprise Bean: CSUser
 */
public interface CSUserLocalHome extends javax.ejb.EJBLocalHome {
	/**
	 * Finds an instance using a key for Entity Bean: CSUser
	 */
	public org.dhhs.dirm.acts.cs.ejb.CSUserLocal findByPrimaryKey(java.lang.String primaryKey) throws javax.ejb.FinderException;
	/**
	 * ejbCreate
	 */
	public org.dhhs.dirm.acts.cs.ejb.CSUserLocal create(String idWrkr, String idPassword, String idProfile, String idWrkrCreate)
		throws javax.ejb.CreateException;
		
	public java.util.Collection findUserByLastName(String lastName) throws javax.ejb.FinderException;
	public java.util.Collection findAllUsers() throws javax.ejb.FinderException;
	public java.util.Collection findUsersByName(String lastName, String firstName) throws javax.ejb.FinderException;
	public java.util.Collection findUsersForProfile(String profileID) throws javax.ejb.FinderException;
}
