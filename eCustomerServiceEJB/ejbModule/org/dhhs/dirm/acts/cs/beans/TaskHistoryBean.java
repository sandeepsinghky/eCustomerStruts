package org.dhhs.dirm.acts.cs.beans;

import java.sql.*;

/**
 * TaskHistoryBean.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by
 * SYSTEMS RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Nov 20, 2003 9:58:16 AM
 * 
 * @author rkodumagulla
 *
 */
public class TaskHistoryBean {
	
	private String idReference;
	
	private Timestamp tsCreate;
	
	private String formattedCreate;
	
	private String cdStatus;
	
	private String idWrkrAssign;
	
	private String nmWorkerAssign;
	
	private Timestamp tsWrkrStart;
	
	private String formattedStart;
	
	private Timestamp tsWrkrEnd;
	
	private String formattedEnd;
	
	private String idWrkrCreate;
	
	private Timestamp tsUpdate;
	
	private String formattedUpdate;
	
	private String idWrkrUpdate;
	
	private String notes;
	
	private Timestamp tsDue;
	
	private String formattedDue;

	/**
	 * Constructor for TaskHistoryBean.
	 */
	public TaskHistoryBean() {
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
	 * Returns the idReference.
	 * @return String
	 */
	public String getIdReference() {
		return idReference;
	}

	/**
	 * Returns the idWrkrAssign.
	 * @return String
	 */
	public String getIdWrkrAssign() {
		return idWrkrAssign;
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
	 * Returns the notes.
	 * @return String
	 */
	public String getNotes() {
		return notes;
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
	 * Returns the tsWrkrEnd.
	 * @return Timestamp
	 */
	public Timestamp getTsWrkrEnd() {
		return tsWrkrEnd;
	}

	/**
	 * Returns the tsWrkrStart.
	 * @return Timestamp
	 */
	public Timestamp getTsWrkrStart() {
		return tsWrkrStart;
	}

	/**
	 * Sets the cdStatus.
	 * @param cdStatus The cdStatus to set
	 */
	public void setCdStatus(String cdStatus) {
		this.cdStatus = cdStatus;
	}

	/**
	 * Sets the idReference.
	 * @param idReference The idReference to set
	 */
	public void setIdReference(String idReference) {
		this.idReference = idReference;
	}

	/**
	 * Sets the idWrkrAssign.
	 * @param idWrkrAssign The idWrkrAssign to set
	 */
	public void setIdWrkrAssign(String idWrkrAssign) {
		this.idWrkrAssign = idWrkrAssign;
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
	 * Sets the notes.
	 * @param notes The notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
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
	 * Sets the tsWrkrEnd.
	 * @param tsWrkrEnd The tsWrkrEnd to set
	 */
	public void setTsWrkrEnd(Timestamp tsWrkrEnd) {
		this.tsWrkrEnd = tsWrkrEnd;
	}

	/**
	 * Sets the tsWrkrStart.
	 * @param tsWrkrStart The tsWrkrStart to set
	 */
	public void setTsWrkrStart(Timestamp tsWrkrStart) {
		this.tsWrkrStart = tsWrkrStart;
	}

	/**
	 * Returns the formattedCreate.
	 * @return String
	 */
	public String getFormattedCreate() {
		return formattedCreate;
	}

	/**
	 * Returns the formattedDue.
	 * @return String
	 */
	public String getFormattedDue() {
		return formattedDue;
	}

	/**
	 * Returns the formattedEnd.
	 * @return String
	 */
	public String getFormattedEnd() {
		return formattedEnd;
	}

	/**
	 * Returns the formattedStart.
	 * @return String
	 */
	public String getFormattedStart() {
		return formattedStart;
	}

	/**
	 * Returns the formattedUpdate.
	 * @return String
	 */
	public String getFormattedUpdate() {
		return formattedUpdate;
	}

	/**
	 * Returns the tsDue.
	 * @return Timestamp
	 */
	public Timestamp getTsDue() {
		return tsDue;
	}

	/**
	 * Sets the formattedCreate.
	 * @param formattedCreate The formattedCreate to set
	 */
	public void setFormattedCreate(String formattedCreate) {
		this.formattedCreate = formattedCreate;
	}

	/**
	 * Sets the formattedDue.
	 * @param formattedDue The formattedDue to set
	 */
	public void setFormattedDue(String formattedDue) {
		this.formattedDue = formattedDue;
	}

	/**
	 * Sets the formattedEnd.
	 * @param formattedEnd The formattedEnd to set
	 */
	public void setFormattedEnd(String formattedEnd) {
		this.formattedEnd = formattedEnd;
	}

	/**
	 * Sets the formattedStart.
	 * @param formattedStart The formattedStart to set
	 */
	public void setFormattedStart(String formattedStart) {
		this.formattedStart = formattedStart;
	}

	/**
	 * Sets the formattedUpdate.
	 * @param formattedUpdate The formattedUpdate to set
	 */
	public void setFormattedUpdate(String formattedUpdate) {
		this.formattedUpdate = formattedUpdate;
	}

	/**
	 * Sets the tsDue.
	 * @param tsDue The tsDue to set
	 */
	public void setTsDue(Timestamp tsDue) {
		this.tsDue = tsDue;
	}

	/**
	 * Returns the nmWorkerAssign.
	 * @return String
	 */
	public String getNmWorkerAssign() {
		return nmWorkerAssign;
	}

	/**
	 * Sets the nmWorkerAssign.
	 * @param nmWorkerAssign The nmWorkerAssign to set
	 */
	public void setNmWorkerAssign(String nmWorkerAssign) {
		this.nmWorkerAssign = nmWorkerAssign;
	}

}
