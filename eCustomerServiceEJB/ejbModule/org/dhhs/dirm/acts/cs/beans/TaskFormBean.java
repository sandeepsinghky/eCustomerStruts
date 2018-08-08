package org.dhhs.dirm.acts.cs.beans;


/**
 * TaskBean.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by
 * SYSTEMS RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Nov 18, 2003 9:29:17 AM
 * 
 * @author rkodumagulla
 *
 */
public class TaskFormBean implements java.io.Serializable {

	private String idReference;
	
	private String nbCase;
	private String idPart;
	private String nbDocket;
	private String idWorker;
	private String nbSSN;
	private String idEmail;
	private String cdType;
	
	private String cdStatus;
	
	private String nbTelAcd;
	private String nbTelExc;
	private String nbTelLn;
	private String nbTelExt;
	
	private String dtReceived;
	private String dtDue;
	private String dtCompleted;
	
	private String nmAuthor;
	
	private String nmCounty;
	private String nbControl;
	
	private String nmCustomerFirst;
	private String nmCustomerLast;
	private String nmCustomerMi;
	
	private String nmCustParFirst;
	private String nmCustParLast;
	private String nmCustParMi;
	
	private String nmNonCustParFirst;
	private String nmNonCustParLast;
	private String nmNonCustParMi;
	
	private String nmRefSource1;
	private String nmRefSource2;
	private String nmRefSource3;
	private String nmRefSource4;
	
	private String ntResolution;
	
	private boolean chkOutstanding;
	private boolean chkApproval;
	private boolean chkCompleted;

	private boolean selfAssigned;
	
	public TaskFormBean() {

		super();

	}

	/**
	 * Returns the cdType.
	 * @return String
	 */
	public String getCdType() {
		return cdType;
	}

	/**
	 * Returns the chkApproval.
	 * @return boolean
	 */
	public boolean isChkApproval() {
		return chkApproval;
	}

	/**
	 * Returns the chkCompleted.
	 * @return boolean
	 */
	public boolean isChkCompleted() {
		return chkCompleted;
	}

	/**
	 * Returns the chkOutstanding.
	 * @return boolean
	 */
	public boolean isChkOutstanding() {
		return chkOutstanding;
	}

	/**
	 * Returns the dtCompleted.
	 * @return String
	 */
	public String getDtCompleted() {
		return dtCompleted;
	}

	/**
	 * Returns the dtDue.
	 * @return String
	 */
	public String getDtDue() {
		return dtDue;
	}

	/**
	 * Returns the dtReceived.
	 * @return String
	 */
	public String getDtReceived() {
		return dtReceived;
	}

	/**
	 * Returns the idEmail.
	 * @return String
	 */
	public String getIdEmail() {
		return idEmail;
	}

	/**
	 * Returns the idPart.
	 * @return String
	 */
	public String getIdPart() {
		return idPart;
	}

	/**
	 * Returns the idReference.
	 * @return String
	 */
	public String getIdReference() {
		return idReference;
	}

	/**
	 * Returns the idWorker.
	 * @return String
	 */
	public String getIdWorker() {
		return idWorker;
	}

	/**
	 * Returns the nbCase.
	 * @return String
	 */
	public String getNbCase() {
		return nbCase;
	}

	/**
	 * Returns the nbDocket.
	 * @return String
	 */
	public String getNbDocket() {
		return nbDocket;
	}

	/**
	 * Returns the nbSSN.
	 * @return String
	 */
	public String getNbSSN() {
		return nbSSN;
	}

	/**
	 * Returns the nbTelAcd.
	 * @return String
	 */
	public String getNbTelAcd() {
		return nbTelAcd;
	}

	/**
	 * Returns the nbTelExc.
	 * @return String
	 */
	public String getNbTelExc() {
		return nbTelExc;
	}

	/**
	 * Returns the nbTelExt.
	 * @return String
	 */
	public String getNbTelExt() {
		return nbTelExt;
	}

	/**
	 * Returns the nbTelLn.
	 * @return String
	 */
	public String getNbTelLn() {
		return nbTelLn;
	}

	/**
	 * Returns the nmAuthor.
	 * @return String
	 */
	public String getNmAuthor() {
		return nmAuthor;
	}

	/**
	 * Returns the nmCustParFirst.
	 * @return String
	 */
	public String getNmCustParFirst() {
		return nmCustParFirst;
	}

	/**
	 * Returns the nmCustParLast.
	 * @return String
	 */
	public String getNmCustParLast() {
		return nmCustParLast;
	}

	/**
	 * Returns the nmCustParMi.
	 * @return String
	 */
	public String getNmCustParMi() {
		return nmCustParMi;
	}

	/**
	 * Returns the nmNonCustParFirst.
	 * @return String
	 */
	public String getNmNonCustParFirst() {
		return nmNonCustParFirst;
	}

	/**
	 * Returns the nmNonCustParLast.
	 * @return String
	 */
	public String getNmNonCustParLast() {
		return nmNonCustParLast;
	}

	/**
	 * Returns the nmNonCustParMi.
	 * @return String
	 */
	public String getNmNonCustParMi() {
		return nmNonCustParMi;
	}

	/**
	 * Returns the nmRefSource1.
	 * @return String
	 */
	public String getNmRefSource1() {
		return nmRefSource1;
	}

	/**
	 * Returns the nmRefSource2.
	 * @return String
	 */
	public String getNmRefSource2() {
		return nmRefSource2;
	}

	/**
	 * Returns the nmRefSource3.
	 * @return String
	 */
	public String getNmRefSource3() {
		return nmRefSource3;
	}

	/**
	 * Returns the ntResolution.
	 * @return String
	 */
	public String getNtResolution() {
		return ntResolution;
	}

	/**
	 * Sets the cdType.
	 * @param cdType The cdType to set
	 */
	public void setCdType(String cdType) {
		this.cdType = cdType;
	}

	/**
	 * Sets the chkApproval.
	 * @param chkApproval The chkApproval to set
	 */
	public void setChkApproval(boolean chkApproval) {
		this.chkApproval = chkApproval;
	}

	/**
	 * Sets the chkCompleted.
	 * @param chkCompleted The chkCompleted to set
	 */
	public void setChkCompleted(boolean chkCompleted) {
		this.chkCompleted = chkCompleted;
	}

	/**
	 * Sets the chkOutstanding.
	 * @param chkOutstanding The chkOutstanding to set
	 */
	public void setChkOutstanding(boolean chkOutstanding) {
		this.chkOutstanding = chkOutstanding;
	}

	/**
	 * Sets the dtCompleted.
	 * @param dtCompleted The dtCompleted to set
	 */
	public void setDtCompleted(String dtCompleted) {
		this.dtCompleted = dtCompleted;
	}

	/**
	 * Sets the dtDue.
	 * @param dtDue The dtDue to set
	 */
	public void setDtDue(String dtDue) {
		this.dtDue = dtDue;
	}

	/**
	 * Sets the dtReceived.
	 * @param dtReceived The dtReceived to set
	 */
	public void setDtReceived(String dtReceived) {
		this.dtReceived = dtReceived;
	}

	/**
	 * Sets the idEmail.
	 * @param idEmail The idEmail to set
	 */
	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

	/**
	 * Sets the idPart.
	 * @param idPart The idPart to set
	 */
	public void setIdPart(String idPart) {
		this.idPart = idPart;
	}

	/**
	 * Sets the idReference.
	 * @param idReference The idReference to set
	 */
	public void setIdReference(String idReference) {
		this.idReference = idReference;
	}

	/**
	 * Sets the idWorker.
	 * @param idWorker The idWorker to set
	 */
	public void setIdWorker(String idWorker) {
		this.idWorker = idWorker;
	}

	/**
	 * Sets the nbCase.
	 * @param nbCase The nbCase to set
	 */
	public void setNbCase(String nbCase) {
		this.nbCase = nbCase;
	}

	/**
	 * Sets the nbDocket.
	 * @param nbDocket The nbDocket to set
	 */
	public void setNbDocket(String nbDocket) {
		this.nbDocket = nbDocket;
	}

	/**
	 * Sets the nbSSN.
	 * @param nbSSN The nbSSN to set
	 */
	public void setNbSSN(String nbSSN) {
		this.nbSSN = nbSSN;
	}

	/**
	 * Sets the nbTelAcd.
	 * @param nbTelAcd The nbTelAcd to set
	 */
	public void setNbTelAcd(String nbTelAcd) {
		this.nbTelAcd = nbTelAcd;
	}

	/**
	 * Sets the nbTelExc.
	 * @param nbTelExc The nbTelExc to set
	 */
	public void setNbTelExc(String nbTelExc) {
		this.nbTelExc = nbTelExc;
	}

	/**
	 * Sets the nbTelExt.
	 * @param nbTelExt The nbTelExt to set
	 */
	public void setNbTelExt(String nbTelExt) {
		this.nbTelExt = nbTelExt;
	}

	/**
	 * Sets the nbTelLn.
	 * @param nbTelLn The nbTelLn to set
	 */
	public void setNbTelLn(String nbTelLn) {
		this.nbTelLn = nbTelLn;
	}

	/**
	 * Sets the nmAuthor.
	 * @param nmAuthor The nmAuthor to set
	 */
	public void setNmAuthor(String nmAuthor) {
		this.nmAuthor = nmAuthor;
	}

	/**
	 * Sets the nmCustParFirst.
	 * @param nmCustParFirst The nmCustParFirst to set
	 */
	public void setNmCustParFirst(String nmCustParFirst) {
		this.nmCustParFirst = nmCustParFirst;
	}

	/**
	 * Sets the nmCustParLast.
	 * @param nmCustParLast The nmCustParLast to set
	 */
	public void setNmCustParLast(String nmCustParLast) {
		this.nmCustParLast = nmCustParLast;
	}

	/**
	 * Sets the nmCustParMi.
	 * @param nmCustParMi The nmCustParMi to set
	 */
	public void setNmCustParMi(String nmCustParMi) {
		this.nmCustParMi = nmCustParMi;
	}

	/**
	 * Sets the nmNonCustParFirst.
	 * @param nmNonCustParFirst The nmNonCustParFirst to set
	 */
	public void setNmNonCustParFirst(String nmNonCustParFirst) {
		this.nmNonCustParFirst = nmNonCustParFirst;
	}

	/**
	 * Sets the nmNonCustParLast.
	 * @param nmNonCustParLast The nmNonCustParLast to set
	 */
	public void setNmNonCustParLast(String nmNonCustParLast) {
		this.nmNonCustParLast = nmNonCustParLast;
	}

	/**
	 * Sets the nmNonCustParMi.
	 * @param nmNonCustParMi The nmNonCustParMi to set
	 */
	public void setNmNonCustParMi(String nmNonCustParMi) {
		this.nmNonCustParMi = nmNonCustParMi;
	}

	/**
	 * Sets the nmRefSource1.
	 * @param nmRefSource1 The nmRefSource1 to set
	 */
	public void setNmRefSource1(String nmRefSource1) {
		this.nmRefSource1 = nmRefSource1;
	}

	/**
	 * Sets the nmRefSource2.
	 * @param nmRefSource2 The nmRefSource2 to set
	 */
	public void setNmRefSource2(String nmRefSource2) {
		this.nmRefSource2 = nmRefSource2;
	}

	/**
	 * Sets the nmRefSource3.
	 * @param nmRefSource3 The nmRefSource3 to set
	 */
	public void setNmRefSource3(String nmRefSource3) {
		this.nmRefSource3 = nmRefSource3;
	}

	/**
	 * Sets the ntResolution.
	 * @param ntResolution The ntResolution to set
	 */
	public void setNtResolution(String ntResolution) {
		this.ntResolution = ntResolution;
	}

	/**
	 * Returns the cdStatus.
	 * @return String
	 */
	public String getCdStatus() {
		return cdStatus;
	}

	/**
	 * Sets the cdStatus.
	 * @param cdStatus The cdStatus to set
	 */
	public void setCdStatus(String cdStatus) {
		this.cdStatus = cdStatus;
	}
	public String toString() {

		StringBuffer sb = new StringBuffer();
		sb.append("Date Recieved: " + dtReceived + "\n");
		sb.append("Case: " + nbCase + "\n");
		sb.append("MPI: " + idPart + "\n");
		sb.append("Worker: " + idWorker + "\n");
		sb.append("Author: " + nmAuthor + "\n");
		sb.append("CP First Name: " + nmCustParFirst + "\n");
		sb.append("CP Last Name: " + nmCustParLast + "\n");
		sb.append("CP Middle Init: " + nmCustParMi + "\n");
		sb.append("NCP First Name: " + nmNonCustParFirst + "\n");
		sb.append("NCP Last Name: " + nmNonCustParLast + "\n");
		sb.append("NCP Middle Init: " + nmNonCustParMi + "\n");
		sb.append("Email: " + idEmail + "\n");
		sb.append("Area Code: " + nbTelAcd + "\n");
		sb.append("Exchange: " + nbTelExc + "\n");
		sb.append("Line: " + nbTelLn + "\n");
		sb.append("Ext: " + nbTelExt + "\n");
		sb.append("Due Date: " + dtDue + "\n");
		sb.append("Date Completed: " + dtCompleted + "\n");
		sb.append("Referral Source 1: " + nmRefSource1 + "\n");
		sb.append("Referral Source 1: " + nmRefSource2 + "\n");
		sb.append("Referral Source 3: " + nmRefSource3 + "\n");
		sb.append("Type: " + cdType + "\n");
		sb.append("Status: " + cdStatus + "\n");
		sb.append("Resolution: " + ntResolution + "\n");
		return sb.toString();
	}
	/**
	 * Returns the nbControl.
	 * @return String
	 */
	public String getNbControl() {
		return nbControl;
	}

	/**
	 * Returns the nmCounty.
	 * @return String
	 */
	public String getNmCounty() {
		return nmCounty;
	}

	/**
	 * Returns the nmCustomerFirst.
	 * @return String
	 */
	public String getNmCustomerFirst() {
		return nmCustomerFirst;
	}

	/**
	 * Returns the nmCustomerLast.
	 * @return String
	 */
	public String getNmCustomerLast() {
		return nmCustomerLast;
	}

	/**
	 * Returns the nmCustomerMi.
	 * @return String
	 */
	public String getNmCustomerMi() {
		return nmCustomerMi;
	}

	/**
	 * Returns the nmRefSource4.
	 * @return String
	 */
	public String getNmRefSource4() {
		return nmRefSource4;
	}

	/**
	 * Sets the nbControl.
	 * @param nbControl The nbControl to set
	 */
	public void setNbControl(String nbControl) {
		this.nbControl = nbControl;
	}

	/**
	 * Sets the nmCounty.
	 * @param nmCounty The nmCounty to set
	 */
	public void setNmCounty(String nmCounty) {
		this.nmCounty = nmCounty;
	}

	/**
	 * Sets the nmCustomerFirst.
	 * @param nmCustomerFirst The nmCustomerFirst to set
	 */
	public void setNmCustomerFirst(String nmCustomerFirst) {
		this.nmCustomerFirst = nmCustomerFirst;
	}

	/**
	 * Sets the nmCustomerLast.
	 * @param nmCustomerLast The nmCustomerLast to set
	 */
	public void setNmCustomerLast(String nmCustomerLast) {
		this.nmCustomerLast = nmCustomerLast;
	}

	/**
	 * Sets the nmCustomerMi.
	 * @param nmCustomerMi The nmCustomerMi to set
	 */
	public void setNmCustomerMi(String nmCustomerMi) {
		this.nmCustomerMi = nmCustomerMi;
	}

	/**
	 * Sets the nmRefSource4.
	 * @param nmRefSource4 The nmRefSource4 to set
	 */
	public void setNmRefSource4(String nmRefSource4) {
		this.nmRefSource4 = nmRefSource4;
	}

	/**
	 * Returns the selfAssigned.
	 * @return boolean
	 */
	public boolean isSelfAssigned() {
		return selfAssigned;
	}

	/**
	 * Sets the selfAssigned.
	 * @param selfAssigned The selfAssigned to set
	 */
	public void setSelfAssigned(boolean selfAssigned) {
		this.selfAssigned = selfAssigned;
	}

}
