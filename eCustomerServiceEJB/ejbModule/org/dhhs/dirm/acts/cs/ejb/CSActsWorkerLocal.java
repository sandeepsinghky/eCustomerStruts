package org.dhhs.dirm.acts.cs.ejb;
import org.dhhs.dirm.acts.cs.persister.CSActsWorkerInquiry;
import org.dhhs.dirm.acts.cs.beans.ActsWorkerEntityBean;
/**
 * Local interface for Enterprise Bean: CSActsWorker
 */
public interface CSActsWorkerLocal extends javax.ejb.EJBLocalObject {
	/**
	 * Returns the cdFipsWrkr.
	 * @return String
	 */
	public String getCdFipsWrkr();
	/**
	 * Returns the cdWrkrType.
	 * @return String
	 */
	public String getCdWrkrType();
	/**
	 * Returns the csActsWorkerInquiry.
	 * @return CSActsWorkerInquiry
	 */
	public CSActsWorkerInquiry getCsActsWorkerInquiry();
	/**
	 * getEntityContext
	 */
	public javax.ejb.EntityContext getEntityContext();
	/**
	 * Returns the idEmail.
	 * @return String
	 */
	public String getIdEmail();
	/**
	 * Returns the idWrkr.
	 * @return String
	 */
	public String getIdWrkr();
	/**
	 * Returns the idWrkrLogon.
	 * @return String
	 */
	public String getIdWrkrLogon();
	/**
	 * Returns the myEntityCtx.
	 * @return javax.ejb.EntityContext
	 */
	public javax.ejb.EntityContext getMyEntityCtx();
	/**
	 * Returns the nbFaxWorker.
	 * @return String
	 */
	public String getNbFaxWorker();
	/**
	 * Returns the nbTelWorker.
	 * @return String
	 */
	public String getNbTelWorker();
	/**
	 * Returns the nmWrkr.
	 * @return String
	 */
	public String getNmWrkr();
	/**
	 * Returns the tmLunchEnd.
	 * @return String
	 */
	public String getTmLunchEnd();
	/**
	 * Returns the tmLunchStart.
	 * @return String
	 */
	public String getTmLunchStart();
	/**
	 * Returns the tmWorkEnd.
	 * @return String
	 */
	public String getTmWorkEnd();
	/**
	 * Returns the tmWorkStart.
	 * @return String
	 */
	public String getTmWorkStart();
	/**
	 * Sets the cdFipsWrkr.
	 * @param cdFipsWrkr The cdFipsWrkr to set
	 */
	public void setCdFipsWrkr(String cdFipsWrkr);
	/**
	 * Sets the cdWrkrType.
	 * @param cdWrkrType The cdWrkrType to set
	 */
	public void setCdWrkrType(String cdWrkrType);
	/**
	 * Sets the csActsWorkerInquiry.
	 * @param csActsWorkerInquiry The csActsWorkerInquiry to set
	 */
	public void setCsActsWorkerInquiry(CSActsWorkerInquiry csActsWorkerInquiry);
	/**
	 * setEntityContext
	 */
	public void setEntityContext(javax.ejb.EntityContext ctx);
	/**
	 * Sets the idEmail.
	 * @param idEmail The idEmail to set
	 */
	public void setIdEmail(String idEmail);
	/**
	 * Sets the idWrkr.
	 * @param idWrkr The idWrkr to set
	 */
	public void setIdWrkr(String idWrkr);
	/**
	 * Sets the idWrkrLogon.
	 * @param idWrkrLogon The idWrkrLogon to set
	 */
	public void setIdWrkrLogon(String idWrkrLogon);
	/**
	 * Sets the myEntityCtx.
	 * @param myEntityCtx The myEntityCtx to set
	 */
	public void setMyEntityCtx(javax.ejb.EntityContext myEntityCtx);
	/**
	 * Sets the nbFaxWorker.
	 * @param nbFaxWorker The nbFaxWorker to set
	 */
	public void setNbFaxWorker(String nbFaxWorker);
	/**
	 * Sets the nbTelWorker.
	 * @param nbTelWorker The nbTelWorker to set
	 */
	public void setNbTelWorker(String nbTelWorker);
	/**
	 * Sets the nmWrkr.
	 * @param nmWrkr The nmWrkr to set
	 */
	public void setNmWrkr(String nmWrkr);
	/**
	 * Sets the tmLunchEnd.
	 * @param tmLunchEnd The tmLunchEnd to set
	 */
	public void setTmLunchEnd(String tmLunchEnd);
	/**
	 * Sets the tmLunchStart.
	 * @param tmLunchStart The tmLunchStart to set
	 */
	public void setTmLunchStart(String tmLunchStart);
	/**
	 * Sets the tmWorkEnd.
	 * @param tmWorkEnd The tmWorkEnd to set
	 */
	public void setTmWorkEnd(String tmWorkEnd);
	/**
	 * Sets the tmWorkStart.
	 * @param tmWorkStart The tmWorkStart to set
	 */
	public void setTmWorkStart(String tmWorkStart);
	/**
	 * Returns the actsWorkerEntityBean.
	 * @return ActsWorkerEntityBean
	 */
	public ActsWorkerEntityBean getActsWorkerEntityBean();
	/**
	 * Sets the actsWorkerEntityBean.
	 * @param actsWorkerEntityBean The actsWorkerEntityBean to set
	 */
	public void setActsWorkerEntityBean(ActsWorkerEntityBean actsWorkerEntityBean);
}
