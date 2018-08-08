

package org.dhhs.dirm.acts.cs.util;

public class NextNumber
{

	// A unique counter for Exception Stack Process
	private static int stackID = 0;

	// Static initializer
	static
	{
		NextNumber n = new NextNumber();
	}

	public NextNumber()
	{
		stackID = 0;
	}

	public static int getNextStackID()
	{
		stackID++;
		return stackID;
	}

}
