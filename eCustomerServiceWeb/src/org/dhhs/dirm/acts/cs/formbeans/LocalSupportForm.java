

package org.dhhs.dirm.acts.cs.formbeans;

import java.util.Vector;

import org.apache.struts.action.ActionForm;

/**
 * LocalSupportForm.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Feb 27, 2004 10:35:36 AM
 * 
 * @author rkodumagulla
 *
 */
public class LocalSupportForm extends ActionForm
{

	private Vector	itemChoices;

	private String	month;
	private int		quarter;
	private int		semiannual;
	private int		year1;
	private int		year2;
	private int		year3;
	private int		year4;
	private boolean	detailed;

	private int		itemNumber;

	/**
	 * Constructor for LocalSupportForm.
	 */
	public LocalSupportForm()
	{
		super();

		itemChoices = new Vector();
		itemChoices.addElement(new Item(0, "Monthly"));
		itemChoices.addElement(new Item(1, "Quarterly"));
		itemChoices.addElement(new Item(2, "Semi-Annual"));
		itemChoices.addElement(new Item(3, "Annual"));
	}

	/**
	 * Returns the detailed.
	 * 
	 * @return boolean
	 */
	public boolean isDetailed()
	{
		return detailed;
	}

	/**
	 * Returns the month.
	 * 
	 * @return String
	 */
	public String getMonth()
	{
		return month;
	}

	/**
	 * Returns the quarter.
	 * 
	 * @return int
	 */
	public int getQuarter()
	{
		return quarter;
	}

	/**
	 * Returns the year1.
	 * 
	 * @return int
	 */
	public int getYear1()
	{
		return year1;
	}

	/**
	 * Returns the year2.
	 * 
	 * @return int
	 */
	public int getYear2()
	{
		return year2;
	}

	/**
	 * Sets the detailed.
	 * 
	 * @param detailed
	 *            The detailed to set
	 */
	public void setDetailed(boolean detailed)
	{
		this.detailed = detailed;
	}

	/**
	 * Sets the month.
	 * 
	 * @param month
	 *            The month to set
	 */
	public void setMonth(String month)
	{
		this.month = month;
	}

	/**
	 * Sets the quarter.
	 * 
	 * @param quarter
	 *            The quarter to set
	 */
	public void setQuarter(int quarter)
	{
		this.quarter = quarter;
	}

	/**
	 * Sets the year1.
	 * 
	 * @param year1
	 *            The year1 to set
	 */
	public void setYear1(int year1)
	{
		this.year1 = year1;
	}

	/**
	 * Sets the year2.
	 * 
	 * @param year2
	 *            The year2 to set
	 */
	public void setYear2(int year2)
	{
		this.year2 = year2;
	}

	/**
	 * Returns the itemChoices.
	 * 
	 * @return Vector
	 */
	public Vector getItemChoices()
	{
		return itemChoices;
	}

	/**
	 * Sets the itemChoices.
	 * 
	 * @param itemChoices
	 *            The itemChoices to set
	 */
	public void setItemChoices(Vector itemChoices)
	{
		this.itemChoices = itemChoices;
	}

	/**
	 * Returns the itemNumber.
	 * 
	 * @return int
	 */
	public int getItemNumber()
	{
		return itemNumber;
	}

	/**
	 * Sets the itemNumber.
	 * 
	 * @param itemNumber
	 *            The itemNumber to set
	 */
	public void setItemNumber(int itemNumber)
	{
		this.itemNumber = itemNumber;
	}

	/**
	 * Returns the semiannual.
	 * 
	 * @return int
	 */
	public int getSemiannual()
	{
		return semiannual;
	}

	/**
	 * Returns the year3.
	 * 
	 * @return int
	 */
	public int getYear3()
	{
		return year3;
	}

	/**
	 * Returns the year4.
	 * 
	 * @return int
	 */
	public int getYear4()
	{
		return year4;
	}

	/**
	 * Sets the semiannual.
	 * 
	 * @param semiannual
	 *            The semiannual to set
	 */
	public void setSemiannual(int semiannual)
	{
		this.semiannual = semiannual;
	}

	/**
	 * Sets the year3.
	 * 
	 * @param year3
	 *            The year3 to set
	 */
	public void setYear3(int year3)
	{
		this.year3 = year3;
	}

	/**
	 * Sets the year4.
	 * 
	 * @param year4
	 *            The year4 to set
	 */
	public void setYear4(int year4)
	{
		this.year4 = year4;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("month:" + month);
		sb.append("quarter:" + quarter);
		sb.append("semiannual:" + semiannual);
		sb.append("year1:" + year1);
		sb.append("year2:" + year2);
		sb.append("year3:" + year3);
		sb.append("year4:" + year4);
		sb.append("detailed:" + detailed);
		sb.append("itemNumber:" + itemNumber);
		return sb.toString();

	}
}
