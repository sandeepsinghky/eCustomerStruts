

package org.dhhs.dirm.acts.cs.util;

public class ExceptionStackItem
{

	private int		itemID;

	private int		itemCount		= 0;

	private int		supressCount	= 1;

	private String	itemDesc;

	private boolean	itemSupress;

	public ExceptionStackItem(String desc)
	{

		this.itemID = NextNumber.getNextStackID();

		this.itemCount++;

		this.itemDesc = desc;

		this.itemSupress = false;
	}

	public int getItemID()
	{
		return itemID;
	}

	public void supressItem(boolean b)
	{
		itemSupress = b;
	}

	public void enableItem(boolean b)
	{
		itemSupress = b;
	}

	public String getItemDesc()
	{
		return itemDesc;
	}

	public boolean getItemState()
	{
		return itemSupress;
	}

	public int getSupressCount()
	{
		return supressCount;
	}

	public int getItemCount()
	{
		return itemCount;
	}

	public void setSupressCount(int count)
	{
		supressCount = count;
		if (supressCount >= count)
		{
			itemSupress = false;
		}
	}

	public void incrementItem()
	{
		this.itemCount++;

		if (itemCount > supressCount)
		{
			itemSupress = true;
		}

	}

	public void decrementItem()
	{
		if (this.itemCount > 0)
		{
			this.itemCount--;
		} else
		{
			this.itemCount = 0;
		}

		if (itemCount < supressCount)
		{
			itemSupress = false;
		}
	}

	public void resetItem()
	{
		this.itemCount = 0;
	}

	public String toString()
	{
		return "ID: " + itemID + " Count: " + itemCount + "\nSupress Count: " + supressCount + "\nDesc:" + itemDesc + "\nStatus:" + itemSupress;
	}
}
