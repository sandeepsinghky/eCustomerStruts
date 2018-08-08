

package org.dhhs.dirm.acts.cs.jasper;

import java.util.Vector;

import org.dhhs.dirm.acts.cs.beans.TimeframeTrackingBean;

import dori.jasper.engine.JRDataSource;
import dori.jasper.engine.JRException;
import dori.jasper.engine.JRField;

/**
 * TimeframeTrackingDataSource.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: May 14, 2004 11:24:50 AM
 * 
 * @author rkodumagulla
 *
 */
public class TimeframeTrackingDataSource implements JRDataSource
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
			return ((TimeframeTrackingBean) data.elementAt(index)).getIdReference();
		} else if (fieldName.equals("cd_type"))
		{
			return ((TimeframeTrackingBean) data.elementAt(index)).getCdType();
		} else if (fieldName.equals("dt_created"))
		{
			return ((TimeframeTrackingBean) data.elementAt(index)).getDtCreated();
		} else if (fieldName.equals("dt_assigned"))
		{
			return ((TimeframeTrackingBean) data.elementAt(index)).getDtAssigned();
		} else if (fieldName.equals("dt_completed"))
		{
			return ((TimeframeTrackingBean) data.elementAt(index)).getDtCompleted();
		} else if (fieldName.equals("nm_wrkr_l"))
		{
			return ((TimeframeTrackingBean) data.elementAt(index)).getWrkrNameLast();
		} else if (fieldName.equals("nm_wrkr_f"))
		{
			return ((TimeframeTrackingBean) data.elementAt(index)).getWrkrNameFirst();
		} else if (fieldName.equals("nm_wrkr_mi"))
		{
			return ((TimeframeTrackingBean) data.elementAt(index)).getWrkrNameMiddle();
		} else if (fieldName.equals("duration"))
		{
			return ((TimeframeTrackingBean) data.elementAt(index)).getDuration();
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
