

package org.dhhs.dirm.acts.cs.beans;

/**
 * CorrespondenceBean.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Mar 29, 2004 1:41:46 PM
 * 
 * @author rkodumagulla
 *
 */
public class CorrespondenceBean
{

	private String	idReference;

	private String	customerName;

	private String	customerNameLast;

	private String	customerNameFirst;

	private String	cdReferral;

	private String	cdProcess;

	private String	dtCreated;

	private String	dtDue;

	private String	dtCompleted;

	private String	wrkrName;

	private String	idWorker;

	private String	referralSource1;

	private String	referralSource2;

	private String	referralSource3;

	private String	referralSource4;

	/**
	 * Constructor for CorrespondenceBean.
	 */
	public CorrespondenceBean()
	{
		super();
	}

	/**
	 * Returns the cdReferral.
	 * 
	 * @return String
	 */
	public String getCdReferral()
	{
		return this.cdReferral;
	}

	/**
	 * Returns the customerName.
	 * 
	 * @return String
	 */
	public String getCustomerName()
	{
		return this.customerName;
	}

	/**
	 * Returns the dtCompleted.
	 * 
	 * @return String
	 */
	public String getDtCompleted()
	{
		return this.dtCompleted;
	}

	/**
	 * Returns the dtCreated.
	 * 
	 * @return String
	 */
	public String getDtCreated()
	{
		return this.dtCreated;
	}

	/**
	 * Returns the dtDue.
	 * 
	 * @return String
	 */
	public String getDtDue()
	{
		return this.dtDue;
	}

	/**
	 * Returns the idReference.
	 * 
	 * @return String
	 */
	public String getIdReference()
	{
		return this.idReference;
	}

	/**
	 * Returns the wrkrName.
	 * 
	 * @return String
	 */
	public String getWrkrName()
	{
		return this.wrkrName;
	}

	/**
	 * Sets the cdReferral.
	 * 
	 * @param cdReferral
	 *            The cdReferral to set
	 */
	public void setCdReferral(String cdReferral)
	{
		this.cdReferral = cdReferral;
	}

	/**
	 * Sets the customerName.
	 * 
	 * @param customerName
	 *            The customerName to set
	 */
	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}

	/**
	 * Sets the dtCompleted.
	 * 
	 * @param dtCompleted
	 *            The dtCompleted to set
	 */
	public void setDtCompleted(String dtCompleted)
	{
		this.dtCompleted = dtCompleted;
	}

	/**
	 * Sets the dtCreated.
	 * 
	 * @param dtCreated
	 *            The dtCreated to set
	 */
	public void setDtCreated(String dtCreated)
	{
		this.dtCreated = dtCreated;
	}

	/**
	 * Sets the dtDue.
	 * 
	 * @param dtDue
	 *            The dtDue to set
	 */
	public void setDtDue(String dtDue)
	{
		this.dtDue = dtDue;
	}

	/**
	 * Sets the idReference.
	 * 
	 * @param idReference
	 *            The idReference to set
	 */
	public void setIdReference(String idReference)
	{
		this.idReference = idReference;
	}

	/**
	 * Sets the wrkrName.
	 * 
	 * @param wrkrName
	 *            The wrkrName to set
	 */
	public void setWrkrName(String wrkrName)
	{
		this.wrkrName = wrkrName;
	}

	/**
	 * Returns the cdProcess.
	 * 
	 * @return String
	 */
	public String getCdProcess()
	{
		return this.cdProcess;
	}

	/**
	 * Sets the cdProcess.
	 * 
	 * @param cdProcess
	 *            The cdProcess to set
	 */
	public void setCdProcess(String cdProcess)
	{
		this.cdProcess = cdProcess;
	}

	/**
	 * Returns the referralSource1.
	 * 
	 * @return String
	 */
	public String getReferralSource1()
	{
		return this.referralSource1;
	}

	/**
	 * Returns the referralSource2.
	 * 
	 * @return String
	 */
	public String getReferralSource2()
	{
		return this.referralSource2;
	}

	/**
	 * Returns the referralSource3.
	 * 
	 * @return String
	 */
	public String getReferralSource3()
	{
		return this.referralSource3;
	}

	/**
	 * Returns the referralSource4.
	 * 
	 * @return String
	 */
	public String getReferralSource4()
	{
		return this.referralSource4;
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
	 * Returns the idWorker.
	 * 
	 * @return String
	 */
	public String getIdWorker()
	{
		return this.idWorker;
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

	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Reference ID:" + this.idReference);
		sb.append("Customer:" + this.customerName);
		sb.append("Referral Type:" + this.cdReferral);
		sb.append("Process Stat:" + this.cdProcess);
		sb.append("Date Cre:" + this.dtCreated);
		sb.append("Date Due:" + this.dtDue);
		sb.append("Date Comp:" + this.dtCompleted);
		sb.append("Worker:" + this.wrkrName);
		sb.append("Worker ID:" + this.idWorker);
		sb.append("Ref Src1:" + this.referralSource1);
		sb.append("Ref Src2:" + this.referralSource2);
		sb.append("Ref Src3:" + this.referralSource3);
		sb.append("Ref Src4:" + this.referralSource4);
		return sb.toString();
	}
	/**
	 * Returns the customerNameFirst.
	 * 
	 * @return String
	 */
	public String getCustomerNameFirst()
	{
		return this.customerNameFirst;
	}

	/**
	 * Returns the customerNameLast.
	 * 
	 * @return String
	 */
	public String getCustomerNameLast()
	{
		return this.customerNameLast;
	}

	/**
	 * Sets the customerNameFirst.
	 * 
	 * @param customerNameFirst
	 *            The customerNameFirst to set
	 */
	public void setCustomerNameFirst(String customerNameFirst)
	{
		this.customerNameFirst = customerNameFirst;
	}

	/**
	 * Sets the customerNameLast.
	 * 
	 * @param customerNameLast
	 *            The customerNameLast to set
	 */
	public void setCustomerNameLast(String customerNameLast)
	{
		this.customerNameLast = customerNameLast;
	}

}
