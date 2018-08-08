package org.dhhs.dirm.acts.cs.beans;

/**
 * ActsWorkerEntityBean.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by
 * SYSTEMS RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Jun 7, 2004 9:31:39 AM
 * 
 * @author RKodumagulla
 *
 */
public class ActsWorkerEntityBean {

	private String idWrkr;
	
	private String cdFipsWrkr;

	/**
	 * String Last Name, First Name and Middle Initial
	 */	
	private String nmWrkr;
	
	private String idWrkrLogon;
	
	private String cdWrkrType;
	
	/**
	 * String Area Code, Exchange, Line and Extension
	 */
	private String nbTelWorker;
	
	/**
	 * String Area Code, Exchange, Line and Extension
	 */
	private String nbFaxWorker;
	
	private String tmLunchStart;
	
	private String tmLunchEnd;
	
	private String tmWorkStart;
	
	private String tmWorkEnd;
	
	private String idEmail;


	/**
	 * Constructor for ActsWorkerEntityBean.
	 */
	public ActsWorkerEntityBean() {
		super();
	}

	/**
	 * Returns the cdFipsWrkr.
	 * @return String
	 */
	public String getCdFipsWrkr() {
		return cdFipsWrkr;
	}

	/**
	 * Returns the cdWrkrType.
	 * @return String
	 */
	public String getCdWrkrType() {
		return cdWrkrType;
	}

	/**
	 * Returns the idEmail.
	 * @return String
	 */
	public String getIdEmail() {
		return idEmail;
	}

	/**
	 * Returns the idWrkr.
	 * @return String
	 */
	public String getIdWrkr() {
		return idWrkr;
	}

	/**
	 * Returns the idWrkrLogon.
	 * @return String
	 */
	public String getIdWrkrLogon() {
		return idWrkrLogon;
	}

	/**
	 * Returns the nbFaxWorker.
	 * @return String
	 */
	public String getNbFaxWorker() {
		return nbFaxWorker;
	}

	/**
	 * Returns the nbTelWorker.
	 * @return String
	 */
	public String getNbTelWorker() {
		return nbTelWorker;
	}

	/**
	 * Returns the nmWrkr.
	 * @return String
	 */
	public String getNmWrkr() {
		return nmWrkr;
	}

	/**
	 * Returns the tmLunchEnd.
	 * @return String
	 */
	public String getTmLunchEnd() {
		return tmLunchEnd;
	}

	/**
	 * Returns the tmLunchStart.
	 * @return String
	 */
	public String getTmLunchStart() {
		return tmLunchStart;
	}

	/**
	 * Returns the tmWorkEnd.
	 * @return String
	 */
	public String getTmWorkEnd() {
		return tmWorkEnd;
	}

	/**
	 * Returns the tmWorkStart.
	 * @return String
	 */
	public String getTmWorkStart() {
		return tmWorkStart;
	}

	/**
	 * Sets the cdFipsWrkr.
	 * @param cdFipsWrkr The cdFipsWrkr to set
	 */
	public void setCdFipsWrkr(String cdFipsWrkr) {
		this.cdFipsWrkr = cdFipsWrkr;
	}

	/**
	 * Sets the cdWrkrType.
	 * @param cdWrkrType The cdWrkrType to set
	 */
	public void setCdWrkrType(String cdWrkrType) {
		this.cdWrkrType = cdWrkrType;
	}

	/**
	 * Sets the idEmail.
	 * @param idEmail The idEmail to set
	 */
	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

	/**
	 * Sets the idWrkr.
	 * @param idWrkr The idWrkr to set
	 */
	public void setIdWrkr(String idWrkr) {
		this.idWrkr = idWrkr;
	}

	/**
	 * Sets the idWrkrLogon.
	 * @param idWrkrLogon The idWrkrLogon to set
	 */
	public void setIdWrkrLogon(String idWrkrLogon) {
		this.idWrkrLogon = idWrkrLogon;
	}

	/**
	 * Sets the nbFaxWorker.
	 * @param nbFaxWorker The nbFaxWorker to set
	 */
	public void setNbFaxWorker(String nbFaxWorker) {
		this.nbFaxWorker = nbFaxWorker;
	}

	/**
	 * Sets the nbTelWorker.
	 * @param nbTelWorker The nbTelWorker to set
	 */
	public void setNbTelWorker(String nbTelWorker) {
		this.nbTelWorker = nbTelWorker;
	}

	/**
	 * Sets the nmWrkr.
	 * @param nmWrkr The nmWrkr to set
	 */
	public void setNmWrkr(String nmWrkr) {
		this.nmWrkr = nmWrkr;
	}

	/**
	 * Sets the tmLunchEnd.
	 * @param tmLunchEnd The tmLunchEnd to set
	 */
	public void setTmLunchEnd(String tmLunchEnd) {
		this.tmLunchEnd = tmLunchEnd;
	}

	/**
	 * Sets the tmLunchStart.
	 * @param tmLunchStart The tmLunchStart to set
	 */
	public void setTmLunchStart(String tmLunchStart) {
		this.tmLunchStart = tmLunchStart;
	}

	/**
	 * Sets the tmWorkEnd.
	 * @param tmWorkEnd The tmWorkEnd to set
	 */
	public void setTmWorkEnd(String tmWorkEnd) {
		this.tmWorkEnd = tmWorkEnd;
	}

	/**
	 * Sets the tmWorkStart.
	 * @param tmWorkStart The tmWorkStart to set
	 */
	public void setTmWorkStart(String tmWorkStart) {
		this.tmWorkStart = tmWorkStart;
	}

}
