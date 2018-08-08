package org.dhhs.dirm.acts.cs.ejb;
import org.dhhs.dirm.acts.cs.persister.CSTaskPersister;
import java.sql.Timestamp;
import java.util.Vector;
import org.dhhs.dirm.acts.cs.beans.TaskEntityBean;
/**
 * Local interface for Enterprise Bean: CSProcessor
 */
public interface CSProcessorLocal extends javax.ejb.EJBLocalObject {
	/**
	 * Returns the cdStatus.
	 * @return String
	 */
	public String getCdStatus();
	/**
	 * Returns the cdType.
	 * @return String
	 */
	public String getCdType();
	/**
	 * Returns the csTaskPersister.
	 * @return CSTaskPersister
	 */
	public CSTaskPersister getCsTaskPersister();
	/**
	 * Returns the idEmail.
	 * @return String
	 */
	public String getIdEmail();
	/**
	 * Returns the idPart.
	 * @return String
	 */
	public String getIdPart();
	/**
	 * Returns the idReference.
	 * @return String
	 */
	public String getIdReference();
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
	 * Returns the nbCase.
	 * @return String
	 */
	public String getNbCase();
	/**
	 * Returns the nbDocket.
	 * @return String
	 */
	public String getNbDocket();
	/**
	 * Returns the nbSSN.
	 * @return String
	 */
	public String getNbSSN();
	/**
	 * Returns the nbTelACD.
	 * @return String
	 */
	public String getNbTelACD();
	/**
	 * Returns the nbTelEXC.
	 * @return String
	 */
	public String getNbTelEXC();
	/**
	 * Returns the nbTelEXT.
	 * @return String
	 */
	public String getNbTelEXT();
	/**
	 * Returns the nbTelLN.
	 * @return String
	 */
	public String getNbTelLN();
	/**
	 * Returns the tsCreate.
	 * @return Timestamp
	 */
	public Timestamp getTsCreate();
	/**
	 * Returns the tsUpdate.
	 * @return Timestamp
	 */
	public Timestamp getTsUpdate();
	/**
	 * Sets the cdStatus.
	 * @param cdStatus The cdStatus to set
	 */
	public void setCdStatus(String cdStatus);
	/**
	 * Sets the cdType.
	 * @param cdType The cdType to set
	 */
	public void setCdType(String cdType);
	/**
	 * Sets the csTaskPersister.
	 * @param csTaskPersister The csTaskPersister to set
	 */
	public void setCsTaskPersister(CSTaskPersister csTaskPersister);
	/**
	 * Sets the idEmail.
	 * @param idEmail The idEmail to set
	 */
	public void setIdEmail(String idEmail);
	/**
	 * Sets the idPart.
	 * @param idPart The idPart to set
	 */
	public void setIdPart(String idPart);
	/**
	 * Sets the idReference.
	 * @param idReference The idReference to set
	 */
	public void setIdReference(String idReference);
	/**
	 * Sets the idWrkrCreate.
	 * @param idWrkrCreate The idWrkrCreate to set
	 */
	public void setIdWrkrCreate(String idWrkrCreate);
	/**
	 * Sets the idWrkrUpdate.
	 * @param idWrkrUpdate The idWrkrUpdate to set
	 */
	public void setIdWrkrUpdate(String idWrkrUpdate);
	/**
	 * Sets the nbCase.
	 * @param nbCase The nbCase to set
	 */
	public void setNbCase(String nbCase);
	/**
	 * Sets the nbDocket.
	 * @param nbDocket The nbDocket to set
	 */
	public void setNbDocket(String nbDocket);
	/**
	 * Sets the nbSSN.
	 * @param nbSSN The nbSSN to set
	 */
	public void setNbSSN(String nbSSN);
	/**
	 * Sets the nbTelACD.
	 * @param nbTelACD The nbTelACD to set
	 */
	public void setNbTelACD(String nbTelACD);
	/**
	 * Sets the nbTelEXC.
	 * @param nbTelEXC The nbTelEXC to set
	 */
	public void setNbTelEXC(String nbTelEXC);
	/**
	 * Sets the nbTelEXT.
	 * @param nbTelEXT The nbTelEXT to set
	 */
	public void setNbTelEXT(String nbTelEXT);
	/**
	 * Sets the nbTelLN.
	 * @param nbTelLN The nbTelLN to set
	 */
	public void setNbTelLN(String nbTelLN);
	/**
	 * Sets the tsCreate.
	 * @param tsCreate The tsCreate to set
	 */
	public void setTsCreate(Timestamp tsCreate);
	/**
	 * Sets the tsUpdate.
	 * @param tsUpdate The tsUpdate to set
	 */
	public void setTsUpdate(Timestamp tsUpdate);
	/**
	 * Returns the idWorker.
	 * @return String
	 */
	public String getIdWorker();
	/**
	 * Sets the idWorker.
	 * @param idWorker The idWorker to set
	 */
	public void setIdWorker(String idWorker);
	/**
	 * Returns the frmTrack.
	 * @return Vector
	 */
	public Vector getFrmTrack();
	/**
	 * Sets the frmTrack.
	 * @param frmTrack The frmTrack to set
	 */
	public void setFrmTrack(Vector frmTrack);
	/**
	 * Returns the nbControl.
	 * @return String
	 */
	public String getNbControl();
	/**
	 * Returns the nmCounty.
	 * @return String
	 */
	public String getNmCounty();
	/**
	 * Returns the nmCustomerFirst.
	 * @return String
	 */
	public String getNmCustomerFirst();
	/**
	 * Returns the nmCustomerLast.
	 * @return String
	 */
	public String getNmCustomerLast();
	/**
	 * Returns the nmCustomerMi.
	 * @return String
	 */
	public String getNmCustomerMi();
	/**
	 * Returns the nmCustParFirst.
	 * @return String
	 */
	public String getNmCustParFirst();
	/**
	 * Returns the nmCustParLast.
	 * @return String
	 */
	public String getNmCustParLast();
	/**
	 * Returns the nmCustParMi.
	 * @return String
	 */
	public String getNmCustParMi();
	/**
	 * Returns the nmNonCustParFirst.
	 * @return String
	 */
	public String getNmNonCustParFirst();
	/**
	 * Returns the nmNonCustParLast.
	 * @return String
	 */
	public String getNmNonCustParLast();
	/**
	 * Returns the nmNonCustParMi.
	 * @return String
	 */
	public String getNmNonCustParMi();
	/**
	 * Returns the nmRefSource1.
	 * @return String
	 */
	public String getNmRefSource1();
	/**
	 * Returns the nmRefSource2.
	 * @return String
	 */
	public String getNmRefSource2();
	/**
	 * Returns the nmRefSource3.
	 * @return String
	 */
	public String getNmRefSource3();
	/**
	 * Returns the nmRefSource4.
	 * @return String
	 */
	public String getNmRefSource4();
	/**
	 * Returns the nmWorker.
	 * @return String
	 */
	public String getNmWorker();
	/**
	 * Sets the nbControl.
	 * @param nbControl The nbControl to set
	 */
	public void setNbControl(String nbControl);
	/**
	 * Sets the nmCounty.
	 * @param nmCounty The nmCounty to set
	 */
	public void setNmCounty(String nmCounty);
	/**
	 * Sets the nmCustomerFirst.
	 * @param nmCustomerFirst The nmCustomerFirst to set
	 */
	public void setNmCustomerFirst(String nmCustomerFirst);
	/**
	 * Sets the nmCustomerLast.
	 * @param nmCustomerLast The nmCustomerLast to set
	 */
	public void setNmCustomerLast(String nmCustomerLast);
	/**
	 * Sets the nmCustomerMi.
	 * @param nmCustomerMi The nmCustomerMi to set
	 */
	public void setNmCustomerMi(String nmCustomerMi);
	/**
	 * Sets the nmCustParFirst.
	 * @param nmCustParFirst The nmCustParFirst to set
	 */
	public void setNmCustParFirst(String nmCustParFirst);
	/**
	 * Sets the nmCustParLast.
	 * @param nmCustParLast The nmCustParLast to set
	 */
	public void setNmCustParLast(String nmCustParLast);
	/**
	 * Sets the nmCustParMi.
	 * @param nmCustParMi The nmCustParMi to set
	 */
	public void setNmCustParMi(String nmCustParMi);
	/**
	 * Sets the nmNonCustParFirst.
	 * @param nmNonCustParFirst The nmNonCustParFirst to set
	 */
	public void setNmNonCustParFirst(String nmNonCustParFirst);
	/**
	 * Sets the nmNonCustParLast.
	 * @param nmNonCustParLast The nmNonCustParLast to set
	 */
	public void setNmNonCustParLast(String nmNonCustParLast);
	/**
	 * Sets the nmNonCustParMi.
	 * @param nmNonCustParMi The nmNonCustParMi to set
	 */
	public void setNmNonCustParMi(String nmNonCustParMi);
	/**
	 * Sets the nmRefSource1.
	 * @param nmRefSource1 The nmRefSource1 to set
	 */
	public void setNmRefSource1(String nmRefSource1);
	/**
	 * Sets the nmRefSource2.
	 * @param nmRefSource2 The nmRefSource2 to set
	 */
	public void setNmRefSource2(String nmRefSource2);
	/**
	 * Sets the nmRefSource3.
	 * @param nmRefSource3 The nmRefSource3 to set
	 */
	public void setNmRefSource3(String nmRefSource3);
	/**
	 * Sets the nmRefSource4.
	 * @param nmRefSource4 The nmRefSource4 to set
	 */
	public void setNmRefSource4(String nmRefSource4);
	/**
	 * Sets the nmWorker.
	 * @param nmWorker The nmWorker to set
	 */
	public void setNmWorker(String nmWorker);
	/**
	 * Returns the taskEntityBean.
	 * @return TaskEntityBean
	 */
	public TaskEntityBean getTaskEntityBean();
	/**
	 * Sets the taskEntityBean.
	 * @param taskEntityBean The taskEntityBean to set
	 */
	public void setTaskEntityBean(TaskEntityBean taskEntityBean);
}
