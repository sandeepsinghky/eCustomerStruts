

package org.dhhs.dirm.acts.cs;

import java.util.Vector;

/**
 * NavigationStack.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Oct 20, 2003 3:45:55 PM
 * 
 * @author rkodumagulla
 *
 */

public class NavigationStack
{

	Vector stack;

	/**
	 * Constructor for NavigationStack.
	 */
	public NavigationStack()
	{
		stack = new Vector();
	}

	public void resetStack()
	{
		stack.clear();
	}

	public String getPreviousStackItem()
	{
		if (stack.isEmpty())
		{
			System.out.println("NavigationStack getPreviousStackItem stack is empty");
			return null;
		} else if (stack.size() > 1)
		{
			System.out.println("NavigationStack getPreviousStackItem stack size >1 " + stack.elementAt(stack.size() - 2));
			return (String) stack.elementAt(stack.size() - 2);
		} else
		{
			System.out.println("NavigationStack getPreviousStackItem stack.elementAt(0) " + stack.elementAt(0));
			return (String) stack.elementAt(0);
		}
	}

	public boolean queryStack(String key)
	{
		if (stack.contains(key))
		{
			return true;
		} else
		{
			return false;
		}
	}

	public void removeFromStack(String key)
	{
		if (queryStack(key))
		{
			stack.remove(key);
		}
	}

	public void addToStack(String key)
	{

		if (!queryStack(key))
		{
			System.out.println("NavigationStack addToStack !queryStack(key)  " +key);
			stack.addElement(key);
		} else
		{
			System.out.println("NavigationStack addToStack else loop  " +key);
			int i = 0;
			loop : for (i = 0; i < stack.size(); i++)
			{
				if (key.equals((String) stack.elementAt(i)))
				{
					break loop;
				}
			}

			for (int j = i + 1; j < stack.size(); j++)
			{
				stack.removeElementAt(j);
			}
		}

		dumpStack();
	}

	public Vector obtainStack()
	{
		return stack;
	}

	public void dumpStack()
	{
		for (int i = 0; i < stack.size(); i++)
		{
			System.out.println("Page " + stack.elementAt(i) + " occurs at " + i + " in the stack");
		}
	}
}
