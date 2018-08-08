

package org.dhhs.dirm.acts.cs.jasper;

import java.util.Vector;

import org.dhhs.dirm.acts.cs.beans.CorrespondenceBean;

import dori.jasper.engine.JRDataSource;
import dori.jasper.engine.JRException;
import dori.jasper.engine.JRField;

/**
 * CorrespondenceDataSource.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Mar 29, 2004 1:24:50 PM
 * 
 * @author rkodumagulla
 *
 */
public class CorrespondenceDataSource implements JRDataSource
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

		if (fieldName.equals("idReference"))
		{
			return ((CorrespondenceBean) data.elementAt(index)).getIdReference();
		} else if (fieldName.equals("customerName"))
		{
			return ((CorrespondenceBean) data.elementAt(index)).getCustomerName();
		} else if (fieldName.equals("wrkrName"))
		{
			return ((CorrespondenceBean) data.elementAt(index)).getWrkrName();
		} else if (fieldName.equals("cdReferral"))
		{
			return ((CorrespondenceBean) data.elementAt(index)).getCdReferral();
		} else if (fieldName.equals("dtAssigned"))
		{
			return ((CorrespondenceBean) data.elementAt(index)).getDtCreated();
		} else if (fieldName.equals("dtDue"))
		{
			return ((CorrespondenceBean) data.elementAt(index)).getDtDue();
		} else if (fieldName.equals("dtCompleted"))
		{
			return ((CorrespondenceBean) data.elementAt(index)).getDtCompleted();
		} else
		{
			System.out.println("Asking for Something Else");
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
