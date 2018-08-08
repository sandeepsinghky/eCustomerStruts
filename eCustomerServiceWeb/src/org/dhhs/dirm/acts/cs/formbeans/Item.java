

package org.dhhs.dirm.acts.cs.formbeans;

/**
 * Item.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Feb 27, 2004 11:49:03 AM
 * 
 * @author rkodumagulla
 *
 */
public class Item
{

	private int		selectedItemNumber;
	private String	displayLabel;

	/**
	 * Constructor for Item.
	 */
	public Item(int option, String label)
	{
		super();
		this.selectedItemNumber = option;
		this.displayLabel = label;
	}

	/**
	 * Returns the selectedItemNumber.
	 * 
	 * @return int
	 */
	public int getSelectedItemNumber()
	{
		return selectedItemNumber;
	}

	/**
	 * Sets the selectedItemNumber.
	 * 
	 * @param selectedItemNumber
	 *            The selectedItemNumber to set
	 */
	public void setSelectedItemNumber(int selectedItemNumber)
	{
		this.selectedItemNumber = selectedItemNumber;
	}

	/**
	 * Returns the displayLabel.
	 * 
	 * @return String
	 */
	public String getDisplayLabel()
	{
		return displayLabel;
	}

	/**
	 * Sets the displayLabel.
	 * 
	 * @param displayLabel
	 *            The displayLabel to set
	 */
	public void setDisplayLabel(String displayLabel)
	{
		this.displayLabel = displayLabel;
	}

}
