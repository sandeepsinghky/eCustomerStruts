

package org.dhhs.dirm.acts.cs.beans;

/**
 * LocalSupportBean.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: May 17, 2004 10:50:31 AM
 * 
 * @author rkodumagulla
 *
 */
public class LocalSupportBean
{

	private String	type;

	private int		total;

	private int		month;

	private int		year;

	/**
	 * Constructor for LocalSupportBean.
	 */
	public LocalSupportBean()
	{
		super();
	}

	/**
	 * Returns the month.
	 * 
	 * @return int
	 */
	public int getMonth()
	{
		return this.month;
	}

	/**
	 * Returns the total.
	 * 
	 * @return int
	 */
	public int getTotal()
	{
		return this.total;
	}

	/**
	 * Returns the type.
	 * 
	 * @return String
	 */
	public String getType()
	{
		return this.type;
	}

	/**
	 * Returns the year.
	 * 
	 * @return int
	 */
	public int getYear()
	{
		return this.year;
	}

	/**
	 * Sets the month.
	 * 
	 * @param month
	 *            The month to set
	 */
	public void setMonth(int month)
	{
		this.month = month;
	}

	/**
	 * Sets the total.
	 * 
	 * @param total
	 *            The total to set
	 */
	public void setTotal(int total)
	{
		this.total = total;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type
	 *            The type to set
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * Sets the year.
	 * 
	 * @param year
	 *            The year to set
	 */
	public void setYear(int year)
	{
		this.year = year;
	}

}
