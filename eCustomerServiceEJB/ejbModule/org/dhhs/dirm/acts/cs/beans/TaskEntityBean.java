package org.dhhs.dirm.acts.cs.beans;

import java.sql.Timestamp;
import java.util.Vector;

/**
 * TaskEntityBean.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by
 * SYSTEMS RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Jun 7, 2004 9:47:42 AM
 * 
 * @author RKodumagulla
 *
 */
public class TaskEntityBean {

	private String idReference;
	
	private String cdType;
	
	private Timestamp tsCreate;
	
	private Timestamp tsAssign;
	
	private Timestamp tsUpdate;
	
	private String idWrkrCreate;
	
	private String idWrkrUpdate;
	
	private String cdStatus;
	
	private String nbCase;
	
	private String idPart;
	
	private String nbSSN;
	
	private String nbDocket;
	
	private String idWorker;
	
	private String nmWorker;
	
	private String idEmail;
	
	private String nbTelACD;
	
	private String nbTelEXC;
	
	private String nbTelLN;
	
	private String nbTelEXT;

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
	
	private Vector frmTrack;

	/**
	 * Constructor for TaskEntityBean.
	 */
	public TaskEntityBean() {
		super();
	}

	/**
	 * Returns the cdStatus.
	 * @return String
	 */
	public String getCdStatus() {
		return cdStatus;
	}

	/**
	 * Returns the cdType.
	 * @return String
	 */
	public String getCdType() {
		return cdType;
	}

	/**
	 * Returns the frmTrack.
	 * @return Vector
	 */
	public Vector getFrmTrack() {
		return frmTrack;
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
	 * Returns the nbCase.
	 * @return String
	 */
	public String getNbCase() {
		return nbCase;
	}

	/**
	 * Returns the nbControl.
	 * @return String
	 */
	public String getNbControl() {
		return nbControl;
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
	 * Returns the nbTelACD.
	 * @return String
	 */
	public String getNbTelACD() {
		return nbTelACD;
	}

	/**
	 * Returns the nbTelEXC.
	 * @return String
	 */
	public String getNbTelEXC() {
		return nbTelEXC;
	}

	/**
	 * Returns the nbTelEXT.
	 * @return String
	 */
	public String getNbTelEXT() {
		return nbTelEXT;
	}

	/**
	 * Returns the nbTelLN.
	 * @return String
	 */
	public String getNbTelLN() {
		return nbTelLN;
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
	 * Returns the nmRefSource4.
	 * @return String
	 */
	public String getNmRefSource4() {
		return nmRefSource4;
	}

	/**
	 * Returns the nmWorker.
	 * @return String
	 */
	public String getNmWorker() {
		return nmWorker;
	}

	/**
	 * Returns the tsCreate.
	 * @return Timestamp
	 */
	public Timestamp getTsCreate() {
		return tsCreate;
	}

	/**
	 * Returns the tsUpdate.
	 * @return Timestamp
	 */
	public Timestamp getTsUpdate() {
		return tsUpdate;
	}

	/**
	 * Sets the cdStatus.
	 * @param cdStatus The cdStatus to set
	 */
	public void setCdStatus(String cdStatus) {
		this.cdStatus = cdStatus;
	}

	/**
	 * Sets the cdType.
	 * @param cdType The cdType to set
	 */
	public void setCdType(String cdType) {
		this.cdType = cdType;
	}

	/**
	 * Sets the frmTrack.
	 * @param frmTrack The frmTrack to set
	 */
	public void setFrmTrack(Vector frmTrack) {
		this.frmTrack = frmTrack;
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
	 * Sets the nbCase.
	 * @param nbCase The nbCase to set
	 */
	public void setNbCase(String nbCase) {
		this.nbCase = nbCase;
	}

	/**
	 * Sets the nbControl.
	 * @param nbControl The nbControl to set
	 */
	public void setNbControl(String nbControl) {
		this.nbControl = nbControl;
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
	 * Sets the nbTelACD.
	 * @param nbTelACD The nbTelACD to set
	 */
	public void setNbTelACD(String nbTelACD) {
		this.nbTelACD = nbTelACD;
	}

	/**
	 * Sets the nbTelEXC.
	 * @param nbTelEXC The nbTelEXC to set
	 */
	public void setNbTelEXC(String nbTelEXC) {
		this.nbTelEXC = nbTelEXC;
	}

	/**
	 * Sets the nbTelEXT.
	 * @param nbTelEXT The nbTelEXT to set
	 */
	public void setNbTelEXT(String nbTelEXT) {
		this.nbTelEXT = nbTelEXT;
	}

	/**
	 * Sets the nbTelLN.
	 * @param nbTelLN The nbTelLN to set
	 */
	public void setNbTelLN(String nbTelLN) {
		this.nbTelLN = nbTelLN;
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
	 * Sets the nmRefSource4.
	 * @param nmRefSource4 The nmRefSource4 to set
	 */
	public void setNmRefSource4(String nmRefSource4) {
		this.nmRefSource4 = nmRefSource4;
	}

	/**
	 * Sets the nmWorker.
	 * @param nmWorker The nmWorker to set
	 */
	public void setNmWorker(String nmWorker) {
		this.nmWorker = nmWorker;
	}

	/**
	 * Sets the tsCreate.
	 * @param tsCreate The tsCreate to set
	 */
	public void setTsCreate(Timestamp tsCreate) {
		this.tsCreate = tsCreate;
	}

	/**
	 * Sets the tsUpdate.
	 * @param tsUpdate The tsUpdate to set
	 */
	public void setTsUpdate(Timestamp tsUpdate) {
		this.tsUpdate = tsUpdate;
	}

	/**
	 * Returns the tsAssign.
	 * @return Timestamp
	 */
	public Timestamp getTsAssign() {
		return tsAssign;
	}

	/**
	 * Sets the tsAssign.
	 * @param tsAssign The tsAssign to set
	 */
	public void setTsAssign(Timestamp tsAssign) {
		this.tsAssign = tsAssign;
	}

}
