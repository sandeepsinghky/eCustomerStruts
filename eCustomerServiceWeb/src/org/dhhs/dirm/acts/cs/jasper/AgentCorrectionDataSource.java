

package org.dhhs.dirm.acts.cs.jasper;

import java.util.Vector;

import org.dhhs.dirm.acts.cs.beans.AgentCorrectionBean;

import dori.jasper.engine.JRDataSource;
import dori.jasper.engine.JRException;
import dori.jasper.engine.JRField;

/**
 * AgentCorrectionDataSource.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: May 14, 2004 11:24:50 AM
 * 
 * @author rkodumagulla
 *
 */
public class AgentCorrectionDataSource implements JRDataSource
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

		if (fieldName.equals("id_reference"))
		{
			return ((AgentCorrectionBean) data.elementAt(index)).getIdReference();
		} else if (fieldName.equals("cdResolution"))
		{
			return ((AgentCorrectionBean) data.elementAt(index)).getCdResolution();
		} else if (fieldName.equals("nbResolution"))
		{
			return ((AgentCorrectionBean) data.elementAt(index)).getCount();
		} else if (fieldName.equals("dt_created"))
		{
			return ((AgentCorrectionBean) data.elementAt(index)).getDtCreated();
		} else if (fieldName.equals("dt_completed"))
		{
			return ((AgentCorrectionBean) data.elementAt(index)).getDtCompleted();
		} else if (fieldName.equals("nm_wrkr_l"))
		{
			return ((AgentCorrectionBean) data.elementAt(index)).getWrkrNameLast();
		} else if (fieldName.equals("nm_wrkr_f"))
		{
			return ((AgentCorrectionBean) data.elementAt(index)).getWrkrNameFirst();
		} else if (fieldName.equals("nm_wrkr_mi"))
		{
			return ((AgentCorrectionBean) data.elementAt(index)).getWrkrNameMiddle();
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
