

package org.dhhs.dirm.acts.cs.jasper;

import java.util.Vector;

import org.dhhs.dirm.acts.cs.beans.LocalSupportBean;

import dori.jasper.engine.JRDataSource;
import dori.jasper.engine.JRException;
import dori.jasper.engine.JRField;

/**
 * SupportTotalDataSource.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: May 16, 2004 11:24:50 AM
 * 
 * @author rkodumagulla
 *
 */
public class SupportTotalDataSource implements JRDataSource
{

	private Vector	data;

	private int		index	= -1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see dori.jasper.engine.JRDataSource#getFieldValue(JRField)
	 */
	public Object getFieldValue(JRField field) throws JRException
	{

		Object value = null;

		String fieldName = field.getName();

		if (fieldName.equals("type"))
		{
			return ((LocalSupportBean) data.elementAt(index)).getType();
		} else if (fieldName.equals("total"))
		{
			return new Integer(((LocalSupportBean) data.elementAt(index)).getTotal());
		} else if (fieldName.equals("i_month"))
		{
			return new Integer(((LocalSupportBean) data.elementAt(index)).getMonth());
		} else if (fieldName.equals("i_year"))
		{
			return new Integer(((LocalSupportBean) data.elementAt(index)).getYear());
		} else if (fieldName.equals("month"))
		{
			int month = ((LocalSupportBean) data.elementAt(index)).getMonth();
			switch (month)
			{
				case 1 :
					return "January";
				case 2 :
					return "February";
				case 3 :
					return "March";
				case 4 :
					return "April";
				case 5 :
					return "May";
				case 6 :
					return "June";
				case 7 :
					return "July";
				case 8 :
					return "August";
				case 9 :
					return "September";
				case 10 :
					return "October";
				case 11 :
					return "November";
				case 12 :
					return "December";
				default :
					return "";
			}
		} else
		{
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dori.jasper.engine.JRDataSource#next()
	 */
	public boolean next() throws JRException
	{
		index++;

		return (index < data.size());
	}
	/**
	 * Returns the data.
	 * 
	 * @return Vector
	 */
	public Vector getData()
	{
		return data;
	}

	/**
	 * Sets the data.
	 * 
	 * @param data
	 *            The data to set
	 */
	public void setData(Vector data)
	{
		this.data = data;
	}

}
