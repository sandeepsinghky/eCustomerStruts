

package org.dhhs.dirm.acts.cs.formbeans;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;
import org.dhhs.dirm.acts.cs.Constants;

/**
 * ReferralSource.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Jan 28, 2004 1:06:05 PM
 * 
 * @author rkodumagulla
 *
 */
public class ReferralSource extends ValidatorForm
{

	private String	idStaff;

	private String	nbSeq;

	private String	nmOffice;

	private String	nmStaff;

	private String	title;

	private boolean	cdStatus;

	private String	tsCreate;

	private String	idWrkrCreate;

	private String	tsUpdate;

	private String	idWrkrUpdate;

	/**
	 * Constructor for ReferralSource.
	 */
	public ReferralSource()
	{
		super();
	}

	// struts validator
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{

		ActionErrors errors = null;

		if ((!request.getParameter(Constants.METHOD).equals(Constants.CREATE)) && (!request.getParameter(Constants.METHOD).equals(Constants.EDIT)) && (!request.getParameter(Constants.METHOD).equals(Constants.INACTIVATE)) && (!request.getParameter(Constants.METHOD).equals(Constants.VIEW)))
		{
			errors = super.validate(mapping, request);
		}
		if (errors == null)
		{
			errors = new ActionErrors();
		} else
		{
			if (request.getParameter(Constants.METHOD).equals(Constants.SAVE))
			{
				request.setAttribute("formMode", "1");
			}
			if (request.getParameter(Constants.METHOD).equals(Constants.STORE))
			{
				request.setAttribute("formMode", "0");
			}
		}
		return errors;
	}

	/**
	 * Returns the idStaff.
	 * 
	 * @return String
	 */
	public String getIdStaff()
	{
		return idStaff;
	}

	/**
	 * Returns the idWrkrCreate.
	 * 
	 * @return String
	 */
	public String getIdWrkrCreate()
	{
		return idWrkrCreate;
	}

	/**
	 * Returns the idWrkrUpdate.
	 * 
	 * @return String
	 */
	public String getIdWrkrUpdate()
	{
		return idWrkrUpdate;
	}

	/**
	 * Returns the nbSeq.
	 * 
	 * @return String
	 */
	public String getNbSeq()
	{
		return nbSeq;
	}

	/**
	 * Returns the nmStaff.
	 * 
	 * @return String
	 */
	public String getNmStaff()
	{
		return nmStaff;
	}

	/**
	 * Returns the title.
	 * 
	 * @return String
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * Returns the tsCreate.
	 * 
	 * @return String
	 */
	public String getTsCreate()
	{
		return tsCreate;
	}

	/**
	 * Returns the tsUpdate.
	 * 
	 * @return String
	 */
	public String getTsUpdate()
	{
		return tsUpdate;
	}

	/**
	 * Sets the idStaff.
	 * 
	 * @param idStaff
	 *            The idStaff to set
	 */
	public void setIdStaff(String idStaff)
	{
		this.idStaff = idStaff;
	}

	/**
	 * Sets the idWrkrCreate.
	 * 
	 * @param idWrkrCreate
	 *            The idWrkrCreate to set
	 */
	public void setIdWrkrCreate(String idWrkrCreate)
	{
		this.idWrkrCreate = idWrkrCreate;
	}

	/**
	 * Sets the idWrkrUpdate.
	 * 
	 * @param idWrkrUpdate
	 *            The idWrkrUpdate to set
	 */
	public void setIdWrkrUpdate(String idWrkrUpdate)
	{
		this.idWrkrUpdate = idWrkrUpdate;
	}

	/**
	 * Sets the nbSeq.
	 * 
	 * @param nbSeq
	 *            The nbSeq to set
	 */
	public void setNbSeq(String nbSeq)
	{
		this.nbSeq = nbSeq;
	}

	/**
	 * Sets the nmStaff.
	 * 
	 * @param nmStaff
	 *            The nmStaff to set
	 */
	public void setNmStaff(String nmStaff)
	{
		this.nmStaff = nmStaff;
	}

	/**
	 * Sets the title.
	 * 
	 * @param title
	 *            The title to set
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * Sets the tsCreate.
	 * 
	 * @param tsCreate
	 *            The tsCreate to set
	 */
	public void setTsCreate(String tsCreate)
	{
		this.tsCreate = tsCreate;
	}

	/**
	 * Sets the tsUpdate.
	 * 
	 * @param tsUpdate
	 *            The tsUpdate to set
	 */
	public void setTsUpdate(String tsUpdate)
	{
		this.tsUpdate = tsUpdate;
	}

	/**
	 * Returns the nmOffice.
	 * 
	 * @return String
	 */
	public String getNmOffice()
	{
		return nmOffice;
	}

	/**
	 * Sets the nmOffice.
	 * 
	 * @param nmOffice
	 *            The nmOffice to set
	 */
	public void setNmOffice(String nmOffice)
	{
		this.nmOffice = nmOffice;
	}

	/**
	 * Returns the cdStatus.
	 * 
	 * @return boolean
	 */
	public boolean isCdStatus()
	{
		return cdStatus;
	}

	/**
	 * Sets the cdStatus.
	 * 
	 * @param cdStatus
	 *            The cdStatus to set
	 */
	public void setCdStatus(boolean cdStatus)
	{
		this.cdStatus = cdStatus;
	}

}
