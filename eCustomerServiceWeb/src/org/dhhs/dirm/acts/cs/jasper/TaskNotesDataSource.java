

package org.dhhs.dirm.acts.cs.jasper;

import dori.jasper.engine.JRDataSource;
import dori.jasper.engine.JRException;
import dori.jasper.engine.JRField;

public class TaskNotesDataSource implements JRDataSource
{

	private Object[][]	data	= new Object[1][1];

	private int			index	= -1;

	public TaskNotesDataSource()
	{
	}

	public boolean next() throws JRException
	{
		index++;

		System.out.println("Length of Data Array" + data.length);
		return (index < data.length);
	}

	public Object getFieldValue(JRField field) throws JRException
	{
		Object value = null;
		return value;
	}
}
