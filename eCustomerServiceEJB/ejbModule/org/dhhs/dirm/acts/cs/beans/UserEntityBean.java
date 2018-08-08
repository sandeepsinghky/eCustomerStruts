package org.dhhs.dirm.acts.cs.beans;

/**
 * UserEntityBean.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by
 * SYSTEMS RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Jun 7, 2004 8:56:24 AM
 * 
 * @author RKodumagulla
 *
 */
public class UserEntityBean {

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

	/**
	 * Constructor for UserEntityBean.
	 */
	public UserEntityBean() {
		super();
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
	 * Returns the cdAccptWrkld.
	 * @return String
	 */
	public String getCdAccptWrkld() {
		return cdAccptWrkld;
	}

	/**
	 * Returns the cdApprovalRequired.
	 * @return String
	 */
	public String getCdApprovalRequired() {
		return cdApprovalRequired;
	}

	/**
	 * Returns the cdPasswordStatus.
	 * @return String
	 */
	public String getCdPasswordStatus() {
		return cdPasswordStatus;
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
	 * Returns the nmWrkr.
	 * @return String
	 */
	public String getNmWrkr() {
		return nmWrkr;
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
	 * Sets the byAgent.
	 * @param byAgent The byAgent to set
	 */
	public void setByAgent(boolean byAgent) {
		this.byAgent = byAgent;
	}

	/**
	 * Sets the byControlNbr.
	 * @param byControlNbr The byControlNbr to set
	 */
	public void setByControlNbr(boolean byControlNbr) {
		this.byControlNbr = byControlNbr;
	}

	/**
	 * Sets the byCounty.
	 * @param byCounty The byCounty to set
	 */
	public void setByCounty(boolean byCounty) {
		this.byCounty = byCounty;
	}

	/**
	 * Sets the byCP.
	 * @param byCP The byCP to set
	 */
	public void setByCP(boolean byCP) {
		this.byCP = byCP;
	}

	/**
	 * Sets the byCustomer.
	 * @param byCustomer The byCustomer to set
	 */
	public void setByCustomer(boolean byCustomer) {
		this.byCustomer = byCustomer;
	}

	/**
	 * Sets the byDtCompleted.
	 * @param byDtCompleted The byDtCompleted to set
	 */
	public void setByDtCompleted(boolean byDtCompleted) {
		this.byDtCompleted = byDtCompleted;
	}

	/**
	 * Sets the byDtDue.
	 * @param byDtDue The byDtDue to set
	 */
	public void setByDtDue(boolean byDtDue) {
		this.byDtDue = byDtDue;
	}

	/**
	 * Sets the byDtReceived.
	 * @param byDtReceived The byDtReceived to set
	 */
	public void setByDtReceived(boolean byDtReceived) {
		this.byDtReceived = byDtReceived;
	}

	/**
	 * Sets the byEmail.
	 * @param byEmail The byEmail to set
	 */
	public void setByEmail(boolean byEmail) {
		this.byEmail = byEmail;
	}

	/**
	 * Sets the byIdPart.
	 * @param byIdPart The byIdPart to set
	 */
	public void setByIdPart(boolean byIdPart) {
		this.byIdPart = byIdPart;
	}

	/**
	 * Sets the byNbCase.
	 * @param byNbCase The byNbCase to set
	 */
	public void setByNbCase(boolean byNbCase) {
		this.byNbCase = byNbCase;
	}

	/**
	 * Sets the byNbDkt.
	 * @param byNbDkt The byNbDkt to set
	 */
	public void setByNbDkt(boolean byNbDkt) {
		this.byNbDkt = byNbDkt;
	}

	/**
	 * Sets the byNbSSN.
	 * @param byNbSSN The byNbSSN to set
	 */
	public void setByNbSSN(boolean byNbSSN) {
		this.byNbSSN = byNbSSN;
	}

	/**
	 * Sets the byNCP.
	 * @param byNCP The byNCP to set
	 */
	public void setByNCP(boolean byNCP) {
		this.byNCP = byNCP;
	}

	/**
	 * Sets the byReferralType.
	 * @param byReferralType The byReferralType to set
	 */
	public void setByReferralType(boolean byReferralType) {
		this.byReferralType = byReferralType;
	}

	/**
	 * Sets the bySrc1.
	 * @param bySrc1 The bySrc1 to set
	 */
	public void setBySrc1(boolean bySrc1) {
		this.bySrc1 = bySrc1;
	}

	/**
	 * Sets the bySrc2.
	 * @param bySrc2 The bySrc2 to set
	 */
	public void setBySrc2(boolean bySrc2) {
		this.bySrc2 = bySrc2;
	}

	/**
	 * Sets the bySrc3.
	 * @param bySrc3 The bySrc3 to set
	 */
	public void setBySrc3(boolean bySrc3) {
		this.bySrc3 = bySrc3;
	}

	/**
	 * Sets the bySrc4.
	 * @param bySrc4 The bySrc4 to set
	 */
	public void setBySrc4(boolean bySrc4) {
		this.bySrc4 = bySrc4;
	}

	/**
	 * Sets the cdAccptWrkld.
	 * @param cdAccptWrkld The cdAccptWrkld to set
	 */
	public void setCdAccptWrkld(String cdAccptWrkld) {
		this.cdAccptWrkld = cdAccptWrkld;
	}

	/**
	 * Sets the cdApprovalRequired.
	 * @param cdApprovalRequired The cdApprovalRequired to set
	 */
	public void setCdApprovalRequired(String cdApprovalRequired) {
		this.cdApprovalRequired = cdApprovalRequired;
	}

	/**
	 * Sets the cdPasswordStatus.
	 * @param cdPasswordStatus The cdPasswordStatus to set
	 */
	public void setCdPasswordStatus(String cdPasswordStatus) {
		this.cdPasswordStatus = cdPasswordStatus;
	}

	/**
	 * Sets the idPassword.
	 * @param idPassword The idPassword to set
	 */
	public void setIdPassword(String idPassword) {
		this.idPassword = idPassword;
	}

	/**
	 * Sets the idProfile.
	 * @param idProfile The idProfile to set
	 */
	public void setIdProfile(String idProfile) {
		this.idProfile = idProfile;
	}

	/**
	 * Sets the idProfileDesc.
	 * @param idProfileDesc The idProfileDesc to set
	 */
	public void setIdProfileDesc(String idProfileDesc) {
		this.idProfileDesc = idProfileDesc;
	}

	/**
	 * Sets the idWrkr.
	 * @param idWrkr The idWrkr to set
	 */
	public void setIdWrkr(String idWrkr) {
		this.idWrkr = idWrkr;
	}

	/**
	 * Sets the idWrkrCreate.
	 * @param idWrkrCreate The idWrkrCreate to set
	 */
	public void setIdWrkrCreate(String idWrkrCreate) {
		this.idWrkrCreate = idWrkrCreate;
	}

	/**
	 * Sets the idWrkrUpdate.
	 * @param idWrkrUpdate The idWrkrUpdate to set
	 */
	public void setIdWrkrUpdate(String idWrkrUpdate) {
		this.idWrkrUpdate = idWrkrUpdate;
	}

	/**
	 * Sets the nbApproval.
	 * @param nbApproval The nbApproval to set
	 */
	public void setNbApproval(int nbApproval) {
		this.nbApproval = nbApproval;
	}

	/**
	 * Sets the nbCompleted.
	 * @param nbCompleted The nbCompleted to set
	 */
	public void setNbCompleted(int nbCompleted) {
		this.nbCompleted = nbCompleted;
	}

	/**
	 * Sets the nbOutstanding.
	 * @param nbOutstanding The nbOutstanding to set
	 */
	public void setNbOutstanding(int nbOutstanding) {
		this.nbOutstanding = nbOutstanding;
	}

	/**
	 * Sets the nmWrkr.
	 * @param nmWrkr The nmWrkr to set
	 */
	public void setNmWrkr(String nmWrkr) {
		this.nmWrkr = nmWrkr;
	}

	/**
	 * Sets the tsCreate.
	 * @param tsCreate The tsCreate to set
	 */
	public void setTsCreate(java.sql.Timestamp tsCreate) {
		this.tsCreate = tsCreate;
	}

	/**
	 * Sets the tsUpdate.
	 * @param tsUpdate The tsUpdate to set
	 */
	public void setTsUpdate(java.sql.Timestamp tsUpdate) {
		this.tsUpdate = tsUpdate;
	}

}
