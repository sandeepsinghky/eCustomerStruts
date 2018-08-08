package org.dhhs.dirm.acts.cs.beans;

/**
 * WorkerCountBean.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by
 * SYSTEMS RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Dec 17, 2003 2:24:04 PM
 * 
 * @author rkodumagulla
 *
 */
public class WorkerCountBean {
	
	private String idWorker;
	
	private String nmWorker;
	
	private int count;
	
	/**
	 * 0 - Outstanding
	 * 1 - Approval
	 * 2 - Completed
	 * 3 - All
	 * 
	 */
	private int type;

	/**
	 * Constructor for WorkerCountBean.
	 */
	public WorkerCountBean() {
		super();
	}

	/**
	 * Returns the count.
	 * @return int
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Returns the idWorker.
	 * @return String
	 */
	public String getIdWorker() {
		return idWorker;
	}

	/**
	 * Returns the type.
	 * @return int
	 */
	public int getType() {
		return type;
	}

	/**
	 * Sets the count.
	 * @param count The count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * Sets the idWorker.
	 * @param idWorker The idWorker to set
	 */
	public void setIdWorker(String idWorker) {
		this.idWorker = idWorker;
	}

	/**
	 * Sets the type.
	 * @param type The type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Returns the nmWorker.
	 * @return String
	 */
	public String getNmWorker() {
		return nmWorker;
	}

	/**
	 * Sets the nmWorker.
	 * @param nmWorker The nmWorker to set
	 */
	public void setNmWorker(String nmWorker) {
		this.nmWorker = nmWorker;
	}

}
