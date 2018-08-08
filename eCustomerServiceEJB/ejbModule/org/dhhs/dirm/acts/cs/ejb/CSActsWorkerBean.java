package org.dhhs.dirm.acts.cs.ejb;

import java.sql.*;
import javax.ejb.*;
import java.util.*;

import org.dhhs.dirm.acts.cs.beans.ActsWorkerEntityBean;
import org.dhhs.dirm.acts.cs.persister.*;

/**
 * Bean implementation class for Enterprise Bean: CSActsWorker
 */
public class CSActsWorkerBean implements javax.ejb.EntityBean {
	private javax.ejb.EntityContext myEntityCtx;
	
	private CSActsWorkerInquiry csActsWorkerInquiry = null;	
	
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
	
	private boolean dirty;
	
	private ActsWorkerEntityBean actsWorkerEntityBean = new ActsWorkerEntityBean();
	
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
			csActsWorkerInquiry.loadState(this);
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
			csActsWorkerInquiry.deleteState(this);
		} catch (SQLException se) {
			se.printStackTrace();
			throw new EJBException(se.getSQLState() + " code:" + se.getErrorCode());
		}		
	}
	/**
	 * ejbStore
	 */
	public void ejbStore() {
		if (dirty) {
			try {
				csActsWorkerInquiry.storeState(this);
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
		
		if (csActsWorkerInquiry == null) {
			csActsWorkerInquiry = new CSActsWorkerInquiry();
		}
	}
	/**
	 * unsetEntityContext
	 */
	public void unsetEntityContext() {
		myEntityCtx = null;
		
		if (csActsWorkerInquiry != null) {
			csActsWorkerInquiry.freeResources();
			csActsWorkerInquiry = null;
		}
	}
	/**
	 * ejbCreate
	 */
	public java.lang.String ejbCreate() throws javax.ejb.CreateException {
		try {
			
			csActsWorkerInquiry.createState();
			dirty = false;
		} catch (SQLException se) {
			se.printStackTrace();
			throw new CreateException(se.getMessage());
		}

		return idWrkr;
	}
	/**
	 * ejbPostCreate
	 */
	public void ejbPostCreate() throws javax.ejb.CreateException {
	}
	/**
	 * ejbFindByPrimaryKey
	 */
	public java.lang.String ejbFindByPrimaryKey(java.lang.String primaryKey) throws javax.ejb.FinderException {
		
		boolean found = false;
		
		try {
			found = csActsWorkerInquiry.findPrimaryKey(primaryKey);
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
	
	public Collection ejbFindAllWorkers() throws javax.ejb.FinderException {
		
      Collection result;

      try {
         result = csActsWorkerInquiry.findAllWorkers();
       } catch (Exception ex) {
           throw new EJBException("findAllWorkers " + ex.getMessage());
       }
       return result;
		
	}
	
	
	public Collection ejbFindWorkersByName(String lastName, String firstName) throws javax.ejb.FinderException {
     Collection result;

      try {
         result = csActsWorkerInquiry.findWorkersByName(lastName, firstName);
       } catch (Exception ex) {
           throw new EJBException("ejbFindWorkersByName " + ex.getMessage());
       }
       return result;
		
	}
	
	public Collection ejbFindWorkersByLastName(String lastName) throws javax.ejb.FinderException {
     Collection result;

      try {
         result = csActsWorkerInquiry.findWorkersByLastName(lastName);
       } catch (Exception ex) {
           throw new EJBException("ejbFindWorkersByLastName " + ex.getMessage());
       }
       return result;
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
	 * Returns the csActsWorkerInquiry.
	 * @return CSActsWorkerInquiry
	 */
	public CSActsWorkerInquiry getCsActsWorkerInquiry() {
		return csActsWorkerInquiry;
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
	 * Returns the myEntityCtx.
	 * @return javax.ejb.EntityContext
	 */
	public javax.ejb.EntityContext getMyEntityCtx() {
		return myEntityCtx;
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
		dirty = true;
	}

	/**
	 * Sets the cdWrkrType.
	 * @param cdWrkrType The cdWrkrType to set
	 */
	public void setCdWrkrType(String cdWrkrType) {
		this.cdWrkrType = cdWrkrType;
		dirty = true;
	}

	/**
	 * Sets the csActsWorkerInquiry.
	 * @param csActsWorkerInquiry The csActsWorkerInquiry to set
	 */
	public void setCsActsWorkerInquiry(CSActsWorkerInquiry csActsWorkerInquiry) {
		this.csActsWorkerInquiry = csActsWorkerInquiry;
		dirty = true;
	}

	/**
	 * Sets the idEmail.
	 * @param idEmail The idEmail to set
	 */
	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
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
	 * Sets the idWrkrLogon.
	 * @param idWrkrLogon The idWrkrLogon to set
	 */
	public void setIdWrkrLogon(String idWrkrLogon) {
		this.idWrkrLogon = idWrkrLogon;
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
	 * Sets the nbFaxWorker.
	 * @param nbFaxWorker The nbFaxWorker to set
	 */
	public void setNbFaxWorker(String nbFaxWorker) {
		this.nbFaxWorker = nbFaxWorker;
		dirty = true;
	}

	/**
	 * Sets the nbTelWorker.
	 * @param nbTelWorker The nbTelWorker to set
	 */
	public void setNbTelWorker(String nbTelWorker) {
		this.nbTelWorker = nbTelWorker;
		dirty = true;
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
	 * Sets the tmWorkEnd.
	 * @param tmWorkEnd The tmWorkEnd to set
	 */
	public void setTmWorkEnd(String tmWorkEnd) {
		this.tmWorkEnd = tmWorkEnd;
		dirty = true;
	}

	/**
	 * Sets the tmWorkStart.
	 * @param tmWorkStart The tmWorkStart to set
	 */
	public void setTmWorkStart(String tmWorkStart) {
		this.tmWorkStart = tmWorkStart;
		dirty = true;
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
	 * Sets the tmLunchEnd.
	 * @param tmLunchEnd The tmLunchEnd to set
	 */
	public void setTmLunchEnd(String tmLunchEnd) {
		this.tmLunchEnd = tmLunchEnd;
		dirty = true;
	}

	/**
	 * Sets the tmLunchStart.
	 * @param tmLunchStart The tmLunchStart to set
	 */
	public void setTmLunchStart(String tmLunchStart) {
		this.tmLunchStart = tmLunchStart;
		dirty = true;
	}

	/**
	 * Returns the actsWorkerEntityBean.
	 * @return ActsWorkerEntityBean
	 */
	public ActsWorkerEntityBean getActsWorkerEntityBean() {
		
		actsWorkerEntityBean.setCdFipsWrkr(cdFipsWrkr);
		actsWorkerEntityBean.setCdWrkrType(cdWrkrType);
		actsWorkerEntityBean.setIdEmail(idEmail);
		actsWorkerEntityBean.setIdWrkr(idWrkr);
		actsWorkerEntityBean.setIdWrkrLogon(idWrkrLogon);
		actsWorkerEntityBean.setNbFaxWorker(nbFaxWorker);
		actsWorkerEntityBean.setNbTelWorker(nbTelWorker);
		actsWorkerEntityBean.setNmWrkr(nmWrkr);
		actsWorkerEntityBean.setTmLunchEnd(tmLunchEnd);
		actsWorkerEntityBean.setTmLunchStart(tmLunchStart);
		actsWorkerEntityBean.setTmWorkEnd(tmWorkEnd);
		actsWorkerEntityBean.setTmWorkStart(tmWorkStart);
		
		return actsWorkerEntityBean;
	}

	/**
	 * Sets the actsWorkerEntityBean.
	 * @param actsWorkerEntityBean The actsWorkerEntityBean to set
	 */
	public void setActsWorkerEntityBean(ActsWorkerEntityBean actsWorkerEntityBean) {
		this.actsWorkerEntityBean = actsWorkerEntityBean;
	}

}
