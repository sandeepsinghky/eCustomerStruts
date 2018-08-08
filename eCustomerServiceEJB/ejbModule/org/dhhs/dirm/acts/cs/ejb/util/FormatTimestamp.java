package org.dhhs.dirm.acts.cs.ejb.util;

import java.util.Date;
import java.sql.Timestamp;
import java.text.*;

/**
 * FormatTimestamp.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by
 * SYSTEMS RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Feb 3, 2004 9:57:33 AM
 * 
 * @author rkodumagulla
 *
 */
public class FormatTimestamp {
	
	private Timestamp ts;

	/**
	 * Constructor for FormatTimestamp.
	 */
	public FormatTimestamp() {
	}
	
	public String format(Timestamp ts){
		
		this.ts = ts;
		
		if (ts == null) {
			return "";
		} 
		
		Date dt = new Date(ts.getTime());
		
		return DateFormat.getInstance().format(dt);
	}
}
