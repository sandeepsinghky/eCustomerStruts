package org.dhhs.dirm.acts.cs.ejb;
import org.dhhs.dirm.acts.cs.beans.UserEntityBean;
/**
 * Local interface for Enterprise Bean: CSUser
 */
public interface CSUserLocal extends javax.ejb.EJBLocalObject {
	/**
	 * getEntityContext
	 */
	public javax.ejb.EntityContext getEntityContext();
	/**
	 * Returns the idPassword.
	 * @return String
	 */
	public String getIdPassword();
	/**
	 * Returns the idProfile.
	 * @return String
	 */
	public String getIdProfile();
	/**
	 * Returns the idProfileDesc.
	 * @return String
	 */
	public String getIdProfileDesc();
	/**
	 * Returns the idWrkr.
	 * @return String
	 */
	public String getIdWrkr();
	/**
	 * Returns the idWrkrCreate.
	 * @return String
	 */
	public String getIdWrkrCreate();
	/**
	 * Returns the idWrkrUpdate.
	 * @return String
	 */
	public String getIdWrkrUpdate();
	/**
	 * Returns the myEntityCtx.
	 * @return javax.ejb.EntityContext
	 */
	public javax.ejb.EntityContext getMyEntityCtx();
	/**
	 * Returns the tsCreate.
	 * @return java.sql.Timestamp
	 */
	public java.sql.Timestamp getTsCreate();
	/**
	 * Returns the tsUpdate.
	 * @return java.sql.Timestamp
	 */
	public java.sql.Timestamp getTsUpdate();
	/**
	 * Sets the idPassword.
	 * @param idPassword The idPassword to set
	 */
	public void setIdPassword(String idPassword);
	/**
	 * Sets the idProfile.
	 * @param idProfile The idProfile to set
	 */
	public void setIdProfile(String idProfile);
	/**
	 * Sets the idProfileDesc.
	 * @param idProfileDesc The idProfileDesc to set
	 */
	public void setIdProfileDesc(String idProfileDesc);
	/**
	 * Sets the idWrkrUpdate.
	 * @param idWrkrUpdate The idWrkrUpdate to set
	 */
	public void setIdWrkrUpdate(String idWrkrUpdate);
	/**
	 * Sets the tsUpdate.
	 * @param tsUpdate The tsUpdate to set
	 */
	public void setTsUpdate(java.sql.Timestamp tsUpdate);
	/**
	 * Returns the nmWrkr.
	 * @return String
	 */
	public String getNmWrkr();
	/**
	 * Sets the nmWrkr.
	 * @param nmWrkr The nmWrkr to set
	 */
	public void setNmWrkr(String nmWrkr);
	/**
	 * Returns the cdPasswordStatus.
	 * @return String
	 */
	public String getCdPasswordStatus();
	/**
	 * Sets the cdPasswordStatus.
	 * @param cdPasswordStatus The cdPasswordStatus to set
	 */
	public void setCdPasswordStatus(String cdPasswordStatus);
	/**
	 * Returns the cdAccptWrkld.
	 * @return String
	 */
	public String getCdAccptWrkld();
	/**
	 * Returns the nbApproval.
	 * @return int
	 */
	public int getNbApproval();
	/**
	 * Returns the nbCompleted.
	 * @return int
	 */
	public int getNbCompleted();
	/**
	 * Returns the nbOutstanding.
	 * @return int
	 */
	public int getNbOutstanding();
	/**
	 * Sets the cdAccptWrkld.
	 * @param cdAccptWrkld The cdAccptWrkld to set
	 */
	public void setCdAccptWrkld(String cdAccptWrkld);
	/**
	 * Sets the nbApproval.
	 * @param nbApproval The nbApproval to set
	 */
	public void setNbApproval(int nbApproval);
	/**
	 * Sets the nbCompleted.
	 * @param nbCompleted The nbCompleted to set
	 */
	public void setNbCompleted(int nbCompleted);
	/**
	 * Sets the nbOutstanding.
	 * @param nbOutstanding The nbOutstanding to set
	 */
	public void setNbOutstanding(int nbOutstanding);
	/**
	 * Returns the byAgent.
	 * @return boolean
	 */
	public boolean isByAgent();
	/**
	 * Returns the byControlNbr.
	 * @return boolean
	 */
	public boolean isByControlNbr();
	/**
	 * Returns the byCounty.
	 * @return boolean
	 */
	public boolean isByCounty();
	/**
	 * Returns the byCP.
	 * @return boolean
	 */
	public boolean isByCP();
	/**
	 * Returns the byCustomer.
	 * @return boolean
	 */
	public boolean isByCustomer();
	/**
	 * Returns the byDtCompleted.
	 * @return boolean
	 */
	public boolean isByDtCompleted();
	/**
	 * Returns the byDtDue.
	 * @return boolean
	 */
	public boolean isByDtDue();
	/**
	 * Returns the byDtReceived.
	 * @return boolean
	 */
	public boolean isByDtReceived();
	/**
	 * Returns the byEmail.
	 * @return boolean
	 */
	public boolean isByEmail();
	/**
	 * Returns the byIdPart.
	 * @return boolean
	 */
	public boolean isByIdPart();
	/**
	 * Returns the byNbCase.
	 * @return boolean
	 */
	public boolean isByNbCase();
	/**
	 * Returns the byNbDkt.
	 * @return boolean
	 */
	public boolean isByNbDkt();
	/**
	 * Returns the byNbSSN.
	 * @return boolean
	 */
	public boolean isByNbSSN();
	/**
	 * Returns the byNCP.
	 * @return boolean
	 */
	public boolean isByNCP();
	/**
	 * Returns the byReferralType.
	 * @return boolean
	 */
	public boolean isByReferralType();
	/**
	 * Returns the bySrc1.
	 * @return boolean
	 */
	public boolean isBySrc1();
	/**
	 * Returns the bySrc2.
	 * @return boolean
	 */
	public boolean isBySrc2();
	/**
	 * Returns the bySrc3.
	 * @return boolean
	 */
	public boolean isBySrc3();
	/**
	 * Returns the bySrc4.
	 * @return boolean
	 */
	public boolean isBySrc4();
	/**
	 * Returns the dirty.
	 * @return boolean
	 */
	public boolean isDirty();
	/**
	 * Sets the byAgent.
	 * @param byAgent The byAgent to set
	 */
	public void setByAgent(boolean byAgent);
	/**
	 * Sets the byControlNbr.
	 * @param byControlNbr The byControlNbr to set
	 */
	public void setByControlNbr(boolean byControlNbr);
	/**
	 * Sets the byCounty.
	 * @param byCounty The byCounty to set
	 */
	public void setByCounty(boolean byCounty);
	/**
	 * Sets the byCP.
	 * @param byCP The byCP to set
	 */
	public void setByCP(boolean byCP);
	/**
	 * Sets the byCustomer.
	 * @param byCustomer The byCustomer to set
	 */
	public void setByCustomer(boolean byCustomer);
	/**
	 * Sets the byDtCompleted.
	 * @param byDtCompleted The byDtCompleted to set
	 */
	public void setByDtCompleted(boolean byDtCompleted);
	/**
	 * Sets the byDtDue.
	 * @param byDtDue The byDtDue to set
	 */
	public void setByDtDue(boolean byDtDue);
	/**
	 * Sets the byDtReceived.
	 * @param byDtReceived The byDtReceived to set
	 */
	public void setByDtReceived(boolean byDtReceived);
	/**
	 * Sets the byEmail.
	 * @param byEmail The byEmail to set
	 */
	public void setByEmail(boolean byEmail);
	/**
	 * Sets the byIdPart.
	 * @param byIdPart The byIdPart to set
	 */
	public void setByIdPart(boolean byIdPart);
	/**
	 * Sets the byNbCase.
	 * @param byNbCase The byNbCase to set
	 */
	public void setByNbCase(boolean byNbCase);
	/**
	 * Sets the byNbDkt.
	 * @param byNbDkt The byNbDkt to set
	 */
	public void setByNbDkt(boolean byNbDkt);
	/**
	 * Sets the byNbSSN.
	 * @param byNbSSN The byNbSSN to set
	 */
	public void setByNbSSN(boolean byNbSSN);
	/**
	 * Sets the byNCP.
	 * @param byNCP The byNCP to set
	 */
	public void setByNCP(boolean byNCP);
	/**
	 * Sets the byReferralType.
	 * @param byReferralType The byReferralType to set
	 */
	public void setByReferralType(boolean byReferralType);
	/**
	 * Sets the bySrc1.
	 * @param bySrc1 The bySrc1 to set
	 */
	public void setBySrc1(boolean bySrc1);
	/**
	 * Sets the bySrc2.
	 * @param bySrc2 The bySrc2 to set
	 */
	public void setBySrc2(boolean bySrc2);
	/**
	 * Sets the bySrc3.
	 * @param bySrc3 The bySrc3 to set
	 */
	public void setBySrc3(boolean bySrc3);
	/**
	 * Sets the bySrc4.
	 * @param bySrc4 The bySrc4 to set
	 */
	public void setBySrc4(boolean bySrc4);
	/**
	 * Returns the cdApprovalRequired.
	 * @return String
	 */
	public String getCdApprovalRequired();
	/**
	 * Sets the cdApprovalRequired.
	 * @param cdApprovalRequired The cdApprovalRequired to set
	 */
	public void setCdApprovalRequired(String cdApprovalRequired);
	/**
	 * Returns the userEntityBean.
	 * @return UserEntityBean
	 */
	public UserEntityBean getUserEntityBean();
	/**
	 * Sets the userEntityBean.
	 * @param userEntityBean The userEntityBean to set
	 */
	public void setUserEntityBean(UserEntityBean userEntityBean);
}
