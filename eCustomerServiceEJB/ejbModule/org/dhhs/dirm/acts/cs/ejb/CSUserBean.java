package org.dhhs.dirm.acts.cs.ejb;

import java.sql.*;
import java.util.*;
import javax.ejb.*;

import org.dhhs.dirm.acts.cs.beans.UserEntityBean;
import org.dhhs.dirm.acts.cs.persister.*;
/**
 * Bean implementation class for Enterprise Bean: CSUser
 */
public class CSUserBean implements javax.ejb.EntityBean {
	private javax.ejb.EntityContext myEntityCtx;

	private CSUserPersister csUserPersister = null;

	private String idWrkr;

	private String nmWrkr;

	private String idPassword;

	private String idProfile;

	private String idProfileDesc;

	private String cdAccptWrkld;
	
	private String cdApprovalRequired;

	private int nbOutstanding;

	private int nbCompleted;
	
	private int nbApproval;

	private java.sql.Timestamp tsCreate;

	private String idWrkrCreate;

	private java.sql.Timestamp tsUpdate;

	private String idWrkrUpdate;
	
	private String cdPasswordStatus;
	
	private boolean byDtReceived;
	
	private boolean byDtCompleted;
	
	private boolean byDtDue;
	
	private boolean byNbCase;
	
	private boolean byIdPart;
	
	private boolean byNbSSN;
	
	private boolean byAgent;
	
	private boolean byReferralType;
	
	private boolean byEmail;
	
	private boolean byCP;
	
	private boolean byNCP;
	
	private boolean byControlNbr;
	
	private boolean byCustomer;
	
	private boolean byNbDkt;
	
	private boolean bySrc1;
	
	private boolean bySrc2;
	
	private boolean bySrc3;
	
	private boolean bySrc4;
	
	private boolean byCounty;
	
	private boolean dirty;
	
	private UserEntityBean userEntityBean = new UserEntityBean();

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
			csUserPersister.loadState(this);
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
		try {
			boolean success = csUserPersister.deleteState(this);

			if (!success) {
				throw new RemoveException("Not able to delete state");
			}
		} catch (SQLException se) {
			se.printStackTrace();
			throw new RemoveException(se.getMessage());
		}
	}
	/**
	 * ejbStore
	 */
	public void ejbStore() {
		
		if (dirty) {
			try {
				csUserPersister.storeState(this);
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

		if (csUserPersister == null) {
			csUserPersister = new CSUserPersister();
		}
	}
	/**
	 * unsetEntityContext
	 */
	public void unsetEntityContext() {
		myEntityCtx = null;

		if (csUserPersister != null) {
			csUserPersister.freeResources();
			csUserPersister = null;
		}
	}
	/**
	 * ejbCreate
	 */
	public java.lang.String ejbCreate(String idWrkr, String idPassword, String idProfile, String idWrkrCreate) throws javax.ejb.CreateException {

		try {
			csUserPersister.createState(idWrkr, idPassword, idProfile, idWrkrCreate);
		} catch (SQLException se) {
			se.printStackTrace();
			throw new CreateException(se.getMessage());
		}
		dirty = false;
		return idWrkr;
	}
	/**
	 * ejbPostCreate
	 */
	public void ejbPostCreate(String idWrkr, String idPassword, String idProfile, String idWrkrCreate) throws javax.ejb.CreateException {
	}
	/**
	 * ejbFindByPrimaryKey
	 */
	public java.lang.String ejbFindByPrimaryKey(java.lang.String primaryKey) throws javax.ejb.FinderException {

		boolean found = false;

		try {
			found = csUserPersister.findPrimaryKey(primaryKey);
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

	public Collection ejbFindAllUsers() throws javax.ejb.FinderException {

		Collection result;

		try {
			result = csUserPersister.findAllUsers();
		} catch (SQLException ex) {
			throw new EJBException("ejbFindAllUsers " + ex.getMessage());
		}
		
		if (result.size() == 0) {
			throw new ObjectNotFoundException("ejbFindAllUsers returns no records");
		}
		
		return result;
	}

	public Collection ejbFindUsersForProfile(String profileID) throws javax.ejb.FinderException {

		Collection result;

		try {
			result = csUserPersister.findUsersForProfile(profileID);
		} catch (Exception ex) {
			throw new EJBException("ejbFindUsersForProfile " + ex.getMessage());
		}
		
		if (result.size() == 0) {
			throw new ObjectNotFoundException("ejbFindUsersForProfile returns no records");
		}
		
		return result;

	}

	public Collection ejbFindUsersByName(String lastName, String firstName) throws javax.ejb.FinderException {
		Collection result;

		try {
			result = csUserPersister.findUsersByName(lastName, firstName);
		} catch (Exception ex) {
			throw new EJBException("ejbFindUsersByName " + ex.getMessage());
		}
		
		if (result.size() == 0) {
			throw new ObjectNotFoundException("ejbFindUsersByName returns no records");
		}
		
		return result;

	}

	public Collection ejbFindUserByLastName(String lastName) throws javax.ejb.FinderException {
		Collection result;

		try {
			result = csUserPersister.findUsersByLastName(lastName);
		} catch (Exception ex) {
			throw new EJBException("ejbFindUsersByLastName " + ex.getMessage());
		}
		
		if (result.size() == 0) {
			throw new ObjectNotFoundException("ejbFindUsersByLastName returns no records");
		}
		return result;
	}

	/**
	 * Returns the idPassword.
	 * @return String
	 */
	public String getIdPassword() {
		return idPassword;
	}

	/**
	 * Returns the idProfile.
	 * @return String
	 */
	public String getIdProfile() {
		return idProfile;
	}

	/**
	 * Returns the idProfileDesc.
	 * @return String
	 */
	public String getIdProfileDesc() {
		return idProfileDesc;
	}

	/**
	 * Returns the idWrkr.
	 * @return String
	 */
	public String getIdWrkr() {
		return idWrkr;
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
	 * Returns the tsCreate.
	 * @return java.sql.Timestamp
	 */
	public java.sql.Timestamp getTsCreate() {
		return tsCreate;
	}

	/**
	 * Returns the tsUpdate.
	 * @return java.sql.Timestamp
	 */
	public java.sql.Timestamp getTsUpdate() {
		return tsUpdate;
	}


	/**
	 * Sets the idPassword.
	 * @param idPassword The idPassword to set
	 */
	public void setIdPassword(String idPassword) {
		this.idPassword = idPassword;
		dirty = true;
	}

	/**
	 * Sets the idProfile.
	 * @param idProfile The idProfile to set
	 */
	public void setIdProfile(String idProfile) {
		this.idProfile = idProfile;
		dirty = true;
	}

	/**
	 * Sets the idProfileDesc.
	 * @param idProfileDesc The idProfileDesc to set
	 */
	public void setIdProfileDesc(String idProfileDesc) {
		this.idProfileDesc = idProfileDesc;
		dirty = true;
	}

	/**
	 * Sets the idWrkr.
	 * @param idWrkr The idWrkr to set
	 */
	public void setIdWrkr(String idWrkr) {
		this.idWrkr = idWrkr;
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
	 * Sets the tsCreate.
	 * @param tsCreate The tsCreate to set
	 */
	public void setTsCreate(java.sql.Timestamp tsCreate) {
		this.tsCreate = tsCreate;
		dirty = true;
	}

	/**
	 * Sets the tsUpdate.
	 * @param tsUpdate The tsUpdate to set
	 */
	public void setTsUpdate(java.sql.Timestamp tsUpdate) {
		this.tsUpdate = tsUpdate;
		dirty = true;
	}

	/**
	 * Returns the nmWrkr.
	 * @return String
	 */
	public String getNmWrkr() {
		return nmWrkr;
	}

	/**
	 * Sets the nmWrkr.
	 * @param nmWrkr The nmWrkr to set
	 */
	public void setNmWrkr(String nmWrkr) {
		this.nmWrkr = nmWrkr;
		dirty = true;
	}

	/**
	 * Returns the cdPasswordStatus.
	 * @return String
	 */
	public String getCdPasswordStatus() {
		return cdPasswordStatus;
	}

	/**
	 * Sets the cdPasswordStatus.
	 * @param cdPasswordStatus The cdPasswordStatus to set
	 */
	public void setCdPasswordStatus(String cdPasswordStatus) {
		this.cdPasswordStatus = cdPasswordStatus;
		dirty = true;
	}

	/**
	 * Returns the cdAccptWrkld.
	 * @return String
	 */
	public String getCdAccptWrkld() {
		return cdAccptWrkld;
	}

	/**
	 * Returns the nbApproval.
	 * @return int
	 */
	public int getNbApproval() {
		return nbApproval;
	}

	/**
	 * Returns the nbCompleted.
	 * @return int
	 */
	public int getNbCompleted() {
		return nbCompleted;
	}

	/**
	 * Returns the nbOutstanding.
	 * @return int
	 */
	public int getNbOutstanding() {
		return nbOutstanding;
	}

	/**
	 * Sets the cdAccptWrkld.
	 * @param cdAccptWrkld The cdAccptWrkld to set
	 */
	public void setCdAccptWrkld(String cdAccptWrkld) {
		this.cdAccptWrkld = cdAccptWrkld;
		dirty = true;
	}

	/**
	 * Sets the nbApproval.
	 * @param nbApproval The nbApproval to set
	 */
	public void setNbApproval(int nbApproval) {
		this.nbApproval = nbApproval;
		dirty = true;
	}

	/**
	 * Sets the nbCompleted.
	 * @param nbCompleted The nbCompleted to set
	 */
	public void setNbCompleted(int nbCompleted) {
		this.nbCompleted = nbCompleted;
		dirty = true;
	}

	/**
	 * Sets the nbOutstanding.
	 * @param nbOutstanding The nbOutstanding to set
	 */
	public void setNbOutstanding(int nbOutstanding) {
		this.nbOutstanding = nbOutstanding;
		dirty = true;
	}

	/**
	 * Returns the byAgent.
	 * @return boolean
	 */
	public boolean isByAgent() {
		return byAgent;
	}

	/**
	 * Returns the byControlNbr.
	 * @return boolean
	 */
	public boolean isByControlNbr() {
		return byControlNbr;
	}

	/**
	 * Returns the byCounty.
	 * @return boolean
	 */
	public boolean isByCounty() {
		return byCounty;
	}

	/**
	 * Returns the byCP.
	 * @return boolean
	 */
	public boolean isByCP() {
		return byCP;
	}

	/**
	 * Returns the byCustomer.
	 * @return boolean
	 */
	public boolean isByCustomer() {
		return byCustomer;
	}

	/**
	 * Returns the byDtCompleted.
	 * @return boolean
	 */
	public boolean isByDtCompleted() {
		return byDtCompleted;
	}

	/**
	 * Returns the byDtDue.
	 * @return boolean
	 */
	public boolean isByDtDue() {
		return byDtDue;
	}

	/**
	 * Returns the byDtReceived.
	 * @return boolean
	 */
	public boolean isByDtReceived() {
		return byDtReceived;
	}

	/**
	 * Returns the byEmail.
	 * @return boolean
	 */
	public boolean isByEmail() {
		return byEmail;
	}

	/**
	 * Returns the byIdPart.
	 * @return boolean
	 */
	public boolean isByIdPart() {
		return byIdPart;
	}

	/**
	 * Returns the byNbCase.
	 * @return boolean
	 */
	public boolean isByNbCase() {
		return byNbCase;
	}

	/**
	 * Returns the byNbDkt.
	 * @return boolean
	 */
	public boolean isByNbDkt() {
		return byNbDkt;
	}

	/**
	 * Returns the byNbSSN.
	 * @return boolean
	 */
	public boolean isByNbSSN() {
		return byNbSSN;
	}

	/**
	 * Returns the byNCP.
	 * @return boolean
	 */
	public boolean isByNCP() {
		return byNCP;
	}

	/**
	 * Returns the byReferralType.
	 * @return boolean
	 */
	public boolean isByReferralType() {
		return byReferralType;
	}

	/**
	 * Returns the bySrc1.
	 * @return boolean
	 */
	public boolean isBySrc1() {
		return bySrc1;
	}

	/**
	 * Returns the bySrc2.
	 * @return boolean
	 */
	public boolean isBySrc2() {
		return bySrc2;
	}

	/**
	 * Returns the bySrc3.
	 * @return boolean
	 */
	public boolean isBySrc3() {
		return bySrc3;
	}

	/**
	 * Returns the bySrc4.
	 * @return boolean
	 */
	public boolean isBySrc4() {
		return bySrc4;
	}

	/**
	 * Returns the csUserPersister.
	 * @return CSUserPersister
	 */
	public CSUserPersister getCsUserPersister() {
		return csUserPersister;
	}

	/**
	 * Returns the dirty.
	 * @return boolean
	 */
	public boolean isDirty() {
		return dirty;
	}

	/**
	 * Sets the byAgent.
	 * @param byAgent The byAgent to set
	 */
	public void setByAgent(boolean byAgent) {
		this.byAgent = byAgent;
		dirty = true;
	}

	/**
	 * Sets the byControlNbr.
	 * @param byControlNbr The byControlNbr to set
	 */
	public void setByControlNbr(boolean byControlNbr) {
		this.byControlNbr = byControlNbr;
		dirty = true;
	}

	/**
	 * Sets the byCounty.
	 * @param byCounty The byCounty to set
	 */
	public void setByCounty(boolean byCounty) {
		this.byCounty = byCounty;
		dirty = true;
	}

	/**
	 * Sets the byCP.
	 * @param byCP The byCP to set
	 */
	public void setByCP(boolean byCP) {
		this.byCP = byCP;
		dirty = true;
	}

	/**
	 * Sets the byCustomer.
	 * @param byCustomer The byCustomer to set
	 */
	public void setByCustomer(boolean byCustomer) {
		this.byCustomer = byCustomer;
		dirty = true;
	}

	/**
	 * Sets the byDtCompleted.
	 * @param byDtCompleted The byDtCompleted to set
	 */
	public void setByDtCompleted(boolean byDtCompleted) {
		this.byDtCompleted = byDtCompleted;
		dirty = true;
	}

	/**
	 * Sets the byDtDue.
	 * @param byDtDue The byDtDue to set
	 */
	public void setByDtDue(boolean byDtDue) {
		this.byDtDue = byDtDue;
		dirty = true;
	}

	/**
	 * Sets the byDtReceived.
	 * @param byDtReceived The byDtReceived to set
	 */
	public void setByDtReceived(boolean byDtReceived) {
		this.byDtReceived = byDtReceived;
		dirty = true;
	}

	/**
	 * Sets the byEmail.
	 * @param byEmail The byEmail to set
	 */
	public void setByEmail(boolean byEmail) {
		this.byEmail = byEmail;
		dirty = true;
	}

	/**
	 * Sets the byIdPart.
	 * @param byIdPart The byIdPart to set
	 */
	public void setByIdPart(boolean byIdPart) {
		this.byIdPart = byIdPart;
		dirty = true;
	}

	/**
	 * Sets the byNbCase.
	 * @param byNbCase The byNbCase to set
	 */
	public void setByNbCase(boolean byNbCase) {
		this.byNbCase = byNbCase;
		dirty = true;
	}

	/**
	 * Sets the byNbDkt.
	 * @param byNbDkt The byNbDkt to set
	 */
	public void setByNbDkt(boolean byNbDkt) {
		this.byNbDkt = byNbDkt;
		dirty = true;
	}

	/**
	 * Sets the byNbSSN.
	 * @param byNbSSN The byNbSSN to set
	 */
	public void setByNbSSN(boolean byNbSSN) {
		this.byNbSSN = byNbSSN;
		dirty = true;
	}

	/**
	 * Sets the byNCP.
	 * @param byNCP The byNCP to set
	 */
	public void setByNCP(boolean byNCP) {
		this.byNCP = byNCP;
		dirty = true;
	}

	/**
	 * Sets the byReferralType.
	 * @param byReferralType The byReferralType to set
	 */
	public void setByReferralType(boolean byReferralType) {
		this.byReferralType = byReferralType;
		dirty = true;
	}

	/**
	 * Sets the bySrc1.
	 * @param bySrc1 The bySrc1 to set
	 */
	public void setBySrc1(boolean bySrc1) {
		this.bySrc1 = bySrc1;
		dirty = true;
	}

	/**
	 * Sets the bySrc2.
	 * @param bySrc2 The bySrc2 to set
	 */
	public void setBySrc2(boolean bySrc2) {
		this.bySrc2 = bySrc2;
		dirty = true;
	}

	/**
	 * Sets the bySrc3.
	 * @param bySrc3 The bySrc3 to set
	 */
	public void setBySrc3(boolean bySrc3) {
		this.bySrc3 = bySrc3;
		dirty = true;
	}

	/**
	 * Sets the bySrc4.
	 * @param bySrc4 The bySrc4 to set
	 */
	public void setBySrc4(boolean bySrc4) {
		this.bySrc4 = bySrc4;
		dirty = true;
	}

	/**
	 * Sets the csUserPersister.
	 * @param csUserPersister The csUserPersister to set
	 */
	public void setCsUserPersister(CSUserPersister csUserPersister) {
		this.csUserPersister = csUserPersister;
	}

	/**
	 * Sets the dirty.
	 * @param dirty The dirty to set
	 */
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	/**
	 * Returns the cdApprovalRequired.
	 * @return String
	 */
	public String getCdApprovalRequired() {
		return cdApprovalRequired;
	}

	/**
	 * Sets the cdApprovalRequired.
	 * @param cdApprovalRequired The cdApprovalRequired to set
	 */
	public void setCdApprovalRequired(String cdApprovalRequired) {
		this.cdApprovalRequired = cdApprovalRequired;
		this.dirty = true;
	}

	/**
	 * Returns the userEntityBean.
	 * @return UserEntityBean
	 */
	public UserEntityBean getUserEntityBean() {
		
		userEntityBean.setByAgent(byAgent);
		userEntityBean.setByControlNbr(byControlNbr);
		userEntityBean.setByCounty(byCounty);
		userEntityBean.setByCP(byCP);
		userEntityBean.setByCustomer(byCustomer);
		userEntityBean.setByDtCompleted(byDtCompleted);
		userEntityBean.setByDtDue(byDtDue);
		userEntityBean.setByDtReceived(byDtReceived);
		userEntityBean.setByEmail(byEmail);
		userEntityBean.setByIdPart(byIdPart);
		userEntityBean.setByNbCase(byNbCase);
		userEntityBean.setByNbDkt(byNbDkt);
		userEntityBean.setByNbSSN(byNbSSN);
		userEntityBean.setByNCP(byNCP);
		userEntityBean.setByReferralType(byReferralType);
		userEntityBean.setBySrc1(bySrc1);
		userEntityBean.setBySrc2(bySrc2);
		userEntityBean.setBySrc3(bySrc3);
		userEntityBean.setBySrc4(bySrc4);
		userEntityBean.setCdAccptWrkld(cdAccptWrkld);
		userEntityBean.setCdApprovalRequired(cdApprovalRequired);
		userEntityBean.setCdPasswordStatus(cdPasswordStatus);
		userEntityBean.setIdPassword(idPassword);
		userEntityBean.setIdProfile(idProfile);
		userEntityBean.setIdProfileDesc(idProfileDesc);
		userEntityBean.setIdWrkr(idWrkr);
		userEntityBean.setIdWrkrCreate(idWrkrCreate);
		userEntityBean.setIdWrkrUpdate(idWrkrUpdate);
		userEntityBean.setNbApproval(nbApproval);
		userEntityBean.setNbCompleted(nbCompleted);
		userEntityBean.setNbOutstanding(nbOutstanding);
		userEntityBean.setNmWrkr(nmWrkr);
		userEntityBean.setTsCreate(tsCreate);
		userEntityBean.setTsUpdate(tsUpdate);
		return userEntityBean;
	}

	/**
	 * Sets the userEntityBean.
	 * @param userEntityBean The userEntityBean to set
	 */
	public void setUserEntityBean(UserEntityBean userEntityBean) {
		this.userEntityBean = userEntityBean;

		this.byAgent = userEntityBean.isByAgent();
		this.byControlNbr = userEntityBean.isByControlNbr();
		this.byCounty = userEntityBean.isByCounty();
		this.byCP = userEntityBean.isByCP();
		this.byCustomer = userEntityBean.isByCustomer();
		this.byDtCompleted = userEntityBean.isByDtCompleted();
		this.byDtDue = userEntityBean.isByDtDue();
		this.byDtReceived = userEntityBean.isByDtReceived();
		this.byEmail = userEntityBean.isByEmail();
		this.byIdPart = userEntityBean.isByIdPart();
		this.byNbCase = userEntityBean.isByNbCase();
		this.byNbDkt = userEntityBean.isByNbDkt();
		this.byNbSSN = userEntityBean.isByNbSSN();
		this.byNCP = userEntityBean.isByNCP();
		this.byReferralType = userEntityBean.isByReferralType();
		this.bySrc1 = userEntityBean.isBySrc1();
		this.bySrc2 = userEntityBean.isBySrc2();
		this.bySrc3 = userEntityBean.isBySrc3();
		this.bySrc4 = userEntityBean.isBySrc4();
		this.cdAccptWrkld = userEntityBean.getCdAccptWrkld();
		this.cdApprovalRequired = userEntityBean.getCdApprovalRequired();
		this.cdPasswordStatus = userEntityBean.getCdPasswordStatus();
		this.idPassword = userEntityBean.getIdPassword();
		this.idProfile = userEntityBean.getIdProfile();
		this.idProfileDesc = userEntityBean.getIdProfileDesc();
		this.idWrkr = userEntityBean.getIdWrkr();
		this.idWrkrCreate = userEntityBean.getIdWrkrCreate();
		this.idWrkrUpdate = userEntityBean.getIdWrkrUpdate();
		this.nbApproval = userEntityBean.getNbApproval();
		this.nbCompleted = userEntityBean.getNbCompleted();
		this.nbOutstanding = userEntityBean.getNbOutstanding();
		this.nmWrkr = userEntityBean.getNmWrkr();
		this.tsCreate = userEntityBean.getTsCreate();
		this.tsUpdate = userEntityBean.getTsUpdate();
		this.dirty = true;
	}
}
