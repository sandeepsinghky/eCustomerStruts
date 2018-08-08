package org.dhhs.dirm.acts.cs.ejb;
import org.dhhs.dirm.acts.cs.persister.CSTaskPersister;
import java.sql.Timestamp;
import java.util.Vector;
import org.dhhs.dirm.acts.cs.beans.TaskEntityBean;
/**
 * Remote interface for Enterprise Bean: CSProcessor
 */
public interface CSProcessor extends javax.ejb.EJBObject {
	/**
	 * Returns the cdStatus.
	 * @return String
	 */
	public String getCdStatus() throws java.rmi.RemoteException;
	/**
	 * Returns the cdType.
	 * @return String
	 */
	public String getCdType() throws java.rmi.RemoteException;
	/**
	 * Returns the csTaskPersister.
	 * @return CSTaskPersister
	 */
	public CSTaskPersister getCsTaskPersister() throws java.rmi.RemoteException;
	/**
	 * Returns the frmTrack.
	 * @return Vector
	 */
	public Vector getFrmTrack() throws java.rmi.RemoteException;
	/**
	 * Returns the idEmail.
	 * @return String
	 */
	public String getIdEmail() throws java.rmi.RemoteException;
	/**
	 * Returns the idPart.
	 * @return String
	 */
	public String getIdPart() throws java.rmi.RemoteException;
	/**
	 * Returns the idReference.
	 * @return String
	 */
	public String getIdReference() throws java.rmi.RemoteException;
	/**
	 * Returns the idWorker.
	 * @return String
	 */
	public String getIdWorker() throws java.rmi.RemoteException;
	/**
	 * Returns the idWrkrCreate.
	 * @return String
	 */
	public String getIdWrkrCreate() throws java.rmi.RemoteException;
	/**
	 * Returns the idWrkrUpdate.
	 * @return String
	 */
	public String getIdWrkrUpdate() throws java.rmi.RemoteException;
	/**
	 * Returns the myEntityCtx.
	 * @return javax.ejb.EntityContext
	 */
	public javax.ejb.EntityContext getMyEntityCtx() throws java.rmi.RemoteException;
	/**
	 * Returns the nbCase.
	 * @return String
	 */
	public String getNbCase() throws java.rmi.RemoteException;
	/**
	 * Returns the nbControl.
	 * @return String
	 */
	public String getNbControl() throws java.rmi.RemoteException;
	/**
	 * Returns the nbDocket.
	 * @return String
	 */
	public String getNbDocket() throws java.rmi.RemoteException;
	/**
	 * Returns the nbSSN.
	 * @return String
	 */
	public String getNbSSN() throws java.rmi.RemoteException;
	/**
	 * Returns the nbTelACD.
	 * @return String
	 */
	public String getNbTelACD() throws java.rmi.RemoteException;
	/**
	 * Returns the nbTelEXC.
	 * @return String
	 */
	public String getNbTelEXC() throws java.rmi.RemoteException;
	/**
	 * Returns the nbTelEXT.
	 * @return String
	 */
	public String getNbTelEXT() throws java.rmi.RemoteException;
	/**
	 * Returns the nbTelLN.
	 * @return String
	 */
	public String getNbTelLN() throws java.rmi.RemoteException;
	/**
	 * Returns the nmCounty.
	 * @return String
	 */
	public String getNmCounty() throws java.rmi.RemoteException;
	/**
	 * Returns the nmCustomerFirst.
	 * @return String
	 */
	public String getNmCustomerFirst() throws java.rmi.RemoteException;
	/**
	 * Returns the nmCustomerMi.
	 * @return String
	 */
	public String getNmCustomerMi() throws java.rmi.RemoteException;
	/**
	 * Returns the nmCustParFirst.
	 * @return String
	 */
	public String getNmCustParFirst() throws java.rmi.RemoteException;
	/**
	 * Returns the nmCustParLast.
	 * @return String
	 */
	public String getNmCustParLast() throws java.rmi.RemoteException;
	/**
	 * Returns the nmCustParMi.
	 * @return String
	 */
	public String getNmCustParMi() throws java.rmi.RemoteException;
	/**
	 * Returns the nmNonCustParFirst.
	 * @return String
	 */
	public String getNmNonCustParFirst() throws java.rmi.RemoteException;
	/**
	 * Returns the nmNonCustParLast.
	 * @return String
	 */
	public String getNmNonCustParLast() throws java.rmi.RemoteException;
	/**
	 * Returns the nmNonCustParMi.
	 * @return String
	 */
	public String getNmNonCustParMi() throws java.rmi.RemoteException;
	/**
	 * Returns the nmRefSource1.
	 * @return String
	 */
	public String getNmRefSource1() throws java.rmi.RemoteException;
	/**
	 * Returns the nmRefSource2.
	 * @return String
	 */
	public String getNmRefSource2() throws java.rmi.RemoteException;
	/**
	 * Returns the nmRefSource3.
	 * @return String
	 */
	public String getNmRefSource3() throws java.rmi.RemoteException;
	/**
	 * Returns the nmRefSource4.
	 * @return String
	 */
	public String getNmRefSource4() throws java.rmi.RemoteException;
	/**
	 * Returns the nmWorker.
	 * @return String
	 */
	public String getNmWorker() throws java.rmi.RemoteException;
	/**
	 * Returns the tsCreate.
	 * @return Timestamp
	 */
	public Timestamp getTsCreate() throws java.rmi.RemoteException;
	/**
	 * Returns the tsUpdate.
	 * @return Timestamp
	 */
	public Timestamp getTsUpdate() throws java.rmi.RemoteException;
	/**
	 * Sets the cdStatus.
	 * @param cdStatus The cdStatus to set
	 */
	public void setCdStatus(String cdStatus) throws java.rmi.RemoteException;
	/**
	 * Sets the cdType.
	 * @param cdType The cdType to set
	 */
	public void setCdType(String cdType) throws java.rmi.RemoteException;
	/**
	 * Sets the csTaskPersister.
	 * @param csTaskPersister The csTaskPersister to set
	 */
	public void setCsTaskPersister(CSTaskPersister csTaskPersister) throws java.rmi.RemoteException;
	/**
	 * Sets the frmTrack.
	 * @param frmTrack The frmTrack to set
	 */
	public void setFrmTrack(Vector frmTrack) throws java.rmi.RemoteException;
	/**
	 * Sets the idEmail.
	 * @param idEmail The idEmail to set
	 */
	public void setIdEmail(String idEmail) throws java.rmi.RemoteException;
	/**
	 * Sets the idPart.
	 * @param idPart The idPart to set
	 */
	public void setIdPart(String idPart) throws java.rmi.RemoteException;
	/**
	 * Sets the idReference.
	 * @param idReference The idReference to set
	 */
	public void setIdReference(String idReference) throws java.rmi.RemoteException;
	/**
	 * Sets the idWorker.
	 * @param idWorker The idWorker to set
	 */
	public void setIdWorker(String idWorker) throws java.rmi.RemoteException;
	/**
	 * Sets the idWrkrCreate.
	 * @param idWrkrCreate The idWrkrCreate to set
	 */
	public void setIdWrkrCreate(String idWrkrCreate) throws java.rmi.RemoteException;
	/**
	 * Sets the idWrkrUpdate.
	 * @param idWrkrUpdate The idWrkrUpdate to set
	 */
	public void setIdWrkrUpdate(String idWrkrUpdate) throws java.rmi.RemoteException;
	/**
	 * Sets the nbCase.
	 * @param nbCase The nbCase to set
	 */
	public void setNbCase(String nbCase) throws java.rmi.RemoteException;
	/**
	 * Sets the nbControl.
	 * @param nbControl The nbControl to set
	 */
	public void setNbControl(String nbControl) throws java.rmi.RemoteException;
	/**
	 * Sets the nbDocket.
	 * @param nbDocket The nbDocket to set
	 */
	public void setNbDocket(String nbDocket) throws java.rmi.RemoteException;
	/**
	 * Sets the nbSSN.
	 * @param nbSSN The nbSSN to set
	 */
	public void setNbSSN(String nbSSN) throws java.rmi.RemoteException;
	/**
	 * Sets the nbTelACD.
	 * @param nbTelACD The nbTelACD to set
	 */
	public void setNbTelACD(String nbTelACD) throws java.rmi.RemoteException;
	/**
	 * Sets the nbTelEXC.
	 * @param nbTelEXC The nbTelEXC to set
	 */
	public void setNbTelEXC(String nbTelEXC) throws java.rmi.RemoteException;
	/**
	 * Sets the nbTelEXT.
	 * @param nbTelEXT The nbTelEXT to set
	 */
	public void setNbTelEXT(String nbTelEXT) throws java.rmi.RemoteException;
	/**
	 * Sets the nbTelLN.
	 * @param nbTelLN The nbTelLN to set
	 */
	public void setNbTelLN(String nbTelLN) throws java.rmi.RemoteException;
	/**
	 * Sets the nmCounty.
	 * @param nmCounty The nmCounty to set
	 */
	public void setNmCounty(String nmCounty) throws java.rmi.RemoteException;
	/**
	 * Sets the nmCustomerFirst.
	 * @param nmCustomerFirst The nmCustomerFirst to set
	 */
	public void setNmCustomerFirst(String nmCustomerFirst) throws java.rmi.RemoteException;
	/**
	 * Sets the nmCustomerLast.
	 * @param nmCustomerLast The nmCustomerLast to set
	 */
	public void setNmCustomerLast(String nmCustomerLast) throws java.rmi.RemoteException;
	/**
	 * Sets the nmCustomerMi.
	 * @param nmCustomerMi The nmCustomerMi to set
	 */
	public void setNmCustomerMi(String nmCustomerMi) throws java.rmi.RemoteException;
	/**
	 * Sets the nmCustParFirst.
	 * @param nmCustParFirst The nmCustParFirst to set
	 */
	public void setNmCustParFirst(String nmCustParFirst) throws java.rmi.RemoteException;
	/**
	 * Sets the nmCustParLast.
	 * @param nmCustParLast The nmCustParLast to set
	 */
	public void setNmCustParLast(String nmCustParLast) throws java.rmi.RemoteException;
	/**
	 * Sets the nmCustParMi.
	 * @param nmCustParMi The nmCustParMi to set
	 */
	public void setNmCustParMi(String nmCustParMi) throws java.rmi.RemoteException;
	/**
	 * Sets the nmNonCustParFirst.
	 * @param nmNonCustParFirst The nmNonCustParFirst to set
	 */
	public void setNmNonCustParFirst(String nmNonCustParFirst) throws java.rmi.RemoteException;
	/**
	 * Sets the nmNonCustParLast.
	 * @param nmNonCustParLast The nmNonCustParLast to set
	 */
	public void setNmNonCustParLast(String nmNonCustParLast) throws java.rmi.RemoteException;
	/**
	 * Sets the nmNonCustParMi.
	 * @param nmNonCustParMi The nmNonCustParMi to set
	 */
	public void setNmNonCustParMi(String nmNonCustParMi) throws java.rmi.RemoteException;
	/**
	 * Sets the nmRefSource1.
	 * @param nmRefSource1 The nmRefSource1 to set
	 */
	public void setNmRefSource1(String nmRefSource1) throws java.rmi.RemoteException;
	/**
	 * Sets the nmRefSource2.
	 * @param nmRefSource2 The nmRefSource2 to set
	 */
	public void setNmRefSource2(String nmRefSource2) throws java.rmi.RemoteException;
	/**
	 * Sets the nmRefSource3.
	 * @param nmRefSource3 The nmRefSource3 to set
	 */
	public void setNmRefSource3(String nmRefSource3) throws java.rmi.RemoteException;
	/**
	 * Sets the nmRefSource4.
	 * @param nmRefSource4 The nmRefSource4 to set
	 */
	public void setNmRefSource4(String nmRefSource4) throws java.rmi.RemoteException;
	/**
	 * Sets the nmWorker.
	 * @param nmWorker The nmWorker to set
	 */
	public void setNmWorker(String nmWorker) throws java.rmi.RemoteException;
	/**
	 * Sets the tsCreate.
	 * @param tsCreate The tsCreate to set
	 */
	public void setTsCreate(Timestamp tsCreate) throws java.rmi.RemoteException;
	/**
	 * Sets the tsUpdate.
	 * @param tsUpdate The tsUpdate to set
	 */
	public void setTsUpdate(Timestamp tsUpdate) throws java.rmi.RemoteException;
	/**
	 * Returns the taskEntityBean.
	 * @return TaskEntityBean
	 */
	public TaskEntityBean getTaskEntityBean() throws java.rmi.RemoteException;
	/**
	 * Sets the taskEntityBean.
	 * @param taskEntityBean The taskEntityBean to set
	 */
	public void setTaskEntityBean(TaskEntityBean taskEntityBean) throws java.rmi.RemoteException;
}
