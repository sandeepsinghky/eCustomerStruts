

package org.dhhs.dirm.acts.cs.formbeans;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;
import org.dhhs.dirm.acts.cs.Constants;

/**
 * CorrespondenceReportForm.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Feb 18, 2004 10:16:41 AM
 * 
 * @author rkodumagulla
 *
 */
public class CorrespondenceReportForm extends ValidatorForm
{

	private boolean	allCorrespondence;

	private boolean	incompleteCorrespondence;

	private boolean	completeCorrespondence;

	private String	customerLast;

	private String	customerFirst;

	private String	referralSource1;

	private String	referralSource2;

	private String	referralSource3;

	private String	referralSource4;

	private String	idWorker;

	private String	nmWorker;

	private String	fromDate;

	private String	toDate;

	/**
	 * Constructor for CorrespondenceReportForm.
	 */
	public CorrespondenceReportForm()
	{
		super();
	}

	// struts validator
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{

		ActionErrors errors = null;

		if (!request.getParameter(Constants.METHOD).equals(Constants.CREATE))
		{
			errors = super.validate(mapping, request);
		}

		if (errors == null)
		{
			errors = new ActionErrors();
		}
		return errors;
	}

	/**
	 * Returns the allCorrespondence.
	 * 
	 * @return boolean
	 */
	public boolean isAllCorrespondence()
	{
		return allCorrespondence;
	}

	/**
	 * Returns the completeCorrespondence.
	 * 
	 * @return boolean
	 */
	public boolean isCompleteCorrespondence()
	{
		return completeCorrespondence;
	}

	/**
	 * Returns the idWorker.
	 * 
	 * @return String
	 */
	public String getIdWorker()
	{
		return idWorker;
	}

	/**
	 * Returns the incompleteCorrespondence.
	 * 
	 * @return boolean
	 */
	public boolean isIncompleteCorrespondence()
	{
		return incompleteCorrespondence;
	}

	/**
	 * Returns the nmWorker.
	 * 
	 * @return String
	 */
	public String getNmWorker()
	{
		return nmWorker;
	}

	/**
	 * Returns the referralSource1.
	 * 
	 * @return String
	 */
	public String getReferralSource1()
	{
		return referralSource1;
	}

	/**
	 * Returns the referralSource2.
	 * 
	 * @return String
	 */
	public String getReferralSource2()
	{
		return referralSource2;
	}

	/**
	 * Returns the referralSource3.
	 * 
	 * @return String
	 */
	public String getReferralSource3()
	{
		return referralSource3;
	}

	/**
	 * Returns the referralSource4.
	 * 
	 * @return String
	 */
	public String getReferralSource4()
	{
		return referralSource4;
	}

	/**
	 * Sets the allCorrespondence.
	 * 
	 * @param allCorrespondence
	 *            The allCorrespondence to set
	 */
	public void setAllCorrespondence(boolean allCorrespondence)
	{
		this.allCorrespondence = allCorrespondence;
	}

	/**
	 * Sets the completeCorrespondence.
	 * 
	 * @param completeCorrespondence
	 *            The completeCorrespondence to set
	 */
	public void setCompleteCorrespondence(boolean completeCorrespondence)
	{
		this.completeCorrespondence = completeCorrespondence;
	}

	/**
	 * Sets the idWorker.
	 * 
	 * @param idWorker
	 *            The idWorker to set
	 */
	public void setIdWorker(String idWorker)
	{
		this.idWorker = idWorker;
	}

	/**
	 * Sets the incompleteCorrespondence.
	 * 
	 * @param incompleteCorrespondence
	 *            The incompleteCorrespondence to set
	 */
	public void setIncompleteCorrespondence(boolean incompleteCorrespondence)
	{
		this.incompleteCorrespondence = incompleteCorrespondence;
	}

	/**
	 * Sets the nmWorker.
	 * 
	 * @param nmWorker
	 *            The nmWorker to set
	 */
	public void setNmWorker(String nmWorker)
	{
		this.nmWorker = nmWorker;
	}

	/**
	 * Sets the referralSource1.
	 * 
	 * @param referralSource1
	 *            The referralSource1 to set
	 */
	public void setReferralSource1(String referralSource1)
	{
		this.referralSource1 = referralSource1;
	}

	/**
	 * Sets the referralSource2.
	 * 
	 * @param referralSource2
	 *            The referralSource2 to set
	 */
	public void setReferralSource2(String referralSource2)
	{
		this.referralSource2 = referralSource2;
	}

	/**
	 * Sets the referralSource3.
	 * 
	 * @param referralSource3
	 *            The referralSource3 to set
	 */
	public void setReferralSource3(String referralSource3)
	{
		this.referralSource3 = referralSource3;
	}

	/**
	 * Sets the referralSource4.
	 * 
	 * @param referralSource4
	 *            The referralSource4 to set
	 */
	public void setReferralSource4(String referralSource4)
	{
		this.referralSource4 = referralSource4;
	}

	/**
	 * Returns the customerFirst.
	 * 
	 * @return String
	 */
	public String getCustomerFirst()
	{
		return customerFirst;
	}

	/**
	 * Returns the customerLast.
	 * 
	 * @return String
	 */
	public String getCustomerLast()
	{
		return customerLast;
	}

	/**
	 * Sets the customerFirst.
	 * 
	 * @param customerFirst
	 *            The customerFirst to set
	 */
	public void setCustomerFirst(String customerFirst)
	{
		this.customerFirst = customerFirst;
	}

	/**
	 * Sets the customerLast.
	 * 
	 * @param customerLast
	 *            The customerLast to set
	 */
	public void setCustomerLast(String customerLast)
	{
		this.customerLast = customerLast;
	}
	/**
	 * Returns the fromDate.
	 * 
	 * @return String
	 */
	public String getFromDate()
	{
		return fromDate;
	}

	/**
	 * Returns the toDate.
	 * 
	 * @return String
	 */
	public String getToDate()
	{
		return toDate;
	}

	/**
	 * Sets the fromDate.
	 * 
	 * @param fromDate
	 *            The fromDate to set
	 */
	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}

	/**
	 * Sets the toDate.
	 * 
	 * @param toDate
	 *            The toDate to set
	 */
	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}

}
