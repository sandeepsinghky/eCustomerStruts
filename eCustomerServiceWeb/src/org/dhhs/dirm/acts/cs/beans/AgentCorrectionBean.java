

package org.dhhs.dirm.acts.cs.beans;

/**
 * AgentCorrectionBean.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Apr 13, 2004 10:06:10 AM
 * 
 * @author RKodumagulla
 *
 */
public class AgentCorrectionBean
{

	private String	idWorker;

	private String	wrkrName;

	private String	wrkrNameLast;

	private String	wrkrNameFirst;

	private String	wrkrNameMiddle;

	private String	idReference;

	private String	cdResolution;

	private String	count;

	private String	dtCreated;

	private String	dtCompleted;

	/**
	 * Constructor for AgentCorrectionBean.
	 */
	public AgentCorrectionBean()
	{
		super();
	}

	/**
	 * Returns the cdResolution.
	 * 
	 * @return String
	 */
	public String getCdResolution()
	{
		return this.cdResolution;
	}

	/**
	 * Returns the count.
	 * 
	 * @return int
	 */
	public String getCount()
	{
		return this.count;
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
	 * Sets the cdResolution.
	 * 
	 * @param cdResolution
	 *            The cdResolution to set
	 */
	public void setCdResolution(String cdResolution)
	{
		this.cdResolution = cdResolution;
	}

	/**
	 * Sets the count.
	 * 
	 * @param count
	 *            The count to set
	 */
	public void setCount(String count)
	{
		this.count = count;
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

	/**
	 * Returns the wrkrNameFirst.
	 * 
	 * @return String
	 */
	public String getWrkrNameFirst()
	{
		return this.wrkrNameFirst;
	}

	/**
	 * Returns the wrkrNameLast.
	 * 
	 * @return String
	 */
	public String getWrkrNameLast()
	{
		return this.wrkrNameLast;
	}

	/**
	 * Returns the wrkrNameMiddle.
	 * 
	 * @return String
	 */
	public String getWrkrNameMiddle()
	{
		return this.wrkrNameMiddle;
	}

	/**
	 * Sets the wrkrNameFirst.
	 * 
	 * @param wrkrNameFirst
	 *            The wrkrNameFirst to set
	 */
	public void setWrkrNameFirst(String wrkrNameFirst)
	{
		this.wrkrNameFirst = wrkrNameFirst;
	}

	/**
	 * Sets the wrkrNameLast.
	 * 
	 * @param wrkrNameLast
	 *            The wrkrNameLast to set
	 */
	public void setWrkrNameLast(String wrkrNameLast)
	{
		this.wrkrNameLast = wrkrNameLast;
	}

	/**
	 * Sets the wrkrNameMiddle.
	 * 
	 * @param wrkrNameMiddle
	 *            The wrkrNameMiddle to set
	 */
	public void setWrkrNameMiddle(String wrkrNameMiddle)
	{
		this.wrkrNameMiddle = wrkrNameMiddle;
	}

}
