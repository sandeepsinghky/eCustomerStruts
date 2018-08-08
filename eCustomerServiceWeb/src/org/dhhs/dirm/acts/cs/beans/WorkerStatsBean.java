

package org.dhhs.dirm.acts.cs.beans;

/**
 * WorkerStatsBean.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Dec 18, 2003 2:12:14 PM
 * 
 * @author rkodumagulla
 *
 */
public class WorkerStatsBean
{

	private String	idWorker;

	private String	nmWorker;

	private int		nbCompleted;

	private int		nbOutstanding;

	private int		nbApproval;

	private int		nbAll;

	/**
	 * Constructor for WorkerStatsBean.
	 */
	public WorkerStatsBean()
	{
		super();
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
	 * Returns the nbAll.
	 * 
	 * @return int
	 */
	public int getNbAll()
	{
		return this.nbAll;
	}

	/**
	 * Returns the nbApproval.
	 * 
	 * @return int
	 */
	public int getNbApproval()
	{
		return this.nbApproval;
	}

	/**
	 * Returns the nbCompleted.
	 * 
	 * @return int
	 */
	public int getNbCompleted()
	{
		return this.nbCompleted;
	}

	/**
	 * Returns the nbOutstanding.
	 * 
	 * @return int
	 */
	public int getNbOutstanding()
	{
		return this.nbOutstanding;
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
	 * Sets the nbAll.
	 * 
	 * @param nbAll
	 *            The nbAll to set
	 */
	public void setNbAll(int nbAll)
	{
		this.nbAll = nbAll;
	}

	/**
	 * Sets the nbApproval.
	 * 
	 * @param nbApproval
	 *            The nbApproval to set
	 */
	public void setNbApproval(int nbApproval)
	{
		this.nbApproval = nbApproval;
	}

	/**
	 * Sets the nbCompleted.
	 * 
	 * @param nbCompleted
	 *            The nbCompleted to set
	 */
	public void setNbCompleted(int nbCompleted)
	{
		this.nbCompleted = nbCompleted;
	}

	/**
	 * Sets the nbOutstanding.
	 * 
	 * @param nbOutstanding
	 *            The nbOutstanding to set
	 */
	public void setNbOutstanding(int nbOutstanding)
	{
		this.nbOutstanding = nbOutstanding;
	}

	/**
	 * Returns the nmWorker.
	 * 
	 * @return String
	 */
	public String getNmWorker()
	{
		return this.nmWorker;
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

}
