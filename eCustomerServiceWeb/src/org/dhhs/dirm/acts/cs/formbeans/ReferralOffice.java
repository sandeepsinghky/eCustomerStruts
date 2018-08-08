

package org.dhhs.dirm.acts.cs.formbeans;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;
import org.dhhs.dirm.acts.cs.Constants;

/**
 * ReferralOffice.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Jan 28, 2004 1:04:05 PM
 * 
 * @author rkodumagulla
 *
 */
public class ReferralOffice extends ValidatorForm
{

	private String	nbSeq;

	private String	nmOffice;

	private String	nmOfficeDesc;

	private String	tsCreate;

	private String	idWrkrCreate;

	private String	tsUpdate;

	private String	idWrkrUpdate;

	private Vector	sources;

	/**
	 * Constructor for ReferralOffice.
	 */
	public ReferralOffice()
	{
		super();

		sources = new Vector();
	}

	// struts validator
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{

		ActionErrors errors = null;

		if ((!request.getParameter(Constants.METHOD).equals(Constants.CREATE)) && (!request.getParameter(Constants.METHOD).equals(Constants.EDIT)) && (!request.getParameter(Constants.METHOD).equals(Constants.LOAD)) && (!request.getParameter(Constants.METHOD).equals(Constants.VIEW)))
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
	 * Returns the nmOffice.
	 * 
	 * @return String
	 */
	public String getNmOffice()
	{
		return nmOffice;
	}

	/**
	 * Returns the nmOfficeDesc.
	 * 
	 * @return String
	 */
	public String getNmOfficeDesc()
	{
		return nmOfficeDesc;
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
	 * Sets the nmOfficeDesc.
	 * 
	 * @param nmOfficeDesc
	 *            The nmOfficeDesc to set
	 */
	public void setNmOfficeDesc(String nmOfficeDesc)
	{
		this.nmOfficeDesc = nmOfficeDesc;
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
	 * Returns the sources.
	 * 
	 * @return Vector
	 */
	public Vector getSources()
	{
		return sources;
	}

	/**
	 * Sets the sources.
	 * 
	 * @param sources
	 *            The sources to set
	 */
	public void setSources(Vector sources)
	{
		this.sources = sources;
	}

	public void addSource(ReferralSource rs)
	{
		sources.addElement(rs);
	}

}
