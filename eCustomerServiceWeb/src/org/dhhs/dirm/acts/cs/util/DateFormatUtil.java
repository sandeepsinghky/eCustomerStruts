

package org.dhhs.dirm.acts.cs.util;

/**
 * DateFormatUtil.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Jun 29, 2004 8:26:52 AM
 * 
 * @author rkodumagulla
 *
 */
public class DateFormatUtil
{

	/**
	 * Constructor for DateFormatUtil.
	 */
	public DateFormatUtil()
	{
		super();
	}

	/**
	 * Assuming the string length to be 10 characters long
	 */
	public String format(String s, int type)
	{

		if (s.length() == 10)
		{
			// Convert from ccyy-mm-dd to mm/dd/ccyy
			if (type == 1)
			{
				StringBuffer sb = new StringBuffer();
				sb.append(s.substring(5, 7));
				sb.append("/");
				sb.append(s.substring(8));
				sb.append("/");
				sb.append(s.substring(0, 4));
				return sb.toString();
				// Convert from mm/dd/ccyy to ccyy-mm-dd
			} else if (type == 2)
			{
				StringBuffer sb = new StringBuffer();
				sb.append(s.substring(6));
				sb.append("-");
				sb.append(s.substring(0, 2));
				sb.append("-");
				sb.append(s.substring(3, 5));
				return sb.toString();
			} else
			{
				return s;
			}
		} else if (s.length() == 8)
		{
			// Convert from ccyymmdd to mm/dd/ccyy
			if (type == 1)
			{
				StringBuffer sb = new StringBuffer();
				sb.append(s.substring(4, 6));
				sb.append("/");
				sb.append(s.substring(6));
				sb.append("/");
				sb.append(s.substring(0, 4));
				return sb.toString();
				// Convert from mmddccyy to ccyy-mm-dd
			} else if (type == 2)
			{
				StringBuffer sb = new StringBuffer();
				sb.append(s.substring(4));
				sb.append("-");
				sb.append(s.substring(0, 2));
				sb.append("-");
				sb.append(s.substring(2, 4));
				return sb.toString();
			} else
			{
				return s;
			}
		} else
		{
			return s;
		}
	}
}
