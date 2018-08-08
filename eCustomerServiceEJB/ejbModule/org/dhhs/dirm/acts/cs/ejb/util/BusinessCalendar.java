package org.dhhs.dirm.acts.cs.ejb.util;

import java.util.*;

import org.apache.log4j.Logger;

/**
 * BusinessCalendar.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained for
 * North Carolina Child Support Enforcement - ACTS Project.
 * 
 * Creation Date: Aug 23, 2004 3:27:52 PM
 * 
 * @author rkodumagulla
 *
 */
public class BusinessCalendar {

	private static final Logger log = Logger.getLogger(BusinessCalendar.class);

	/**
	 * Constructor for BusinessCalendar.
	 */
	public BusinessCalendar() {
		super();
		log.debug("Business Calendar Instance Created.");
	}

	public Calendar addBussinessDays(int number, Calendar date) {
		
		log.debug("Calculate " + number + " Business Days from " + date.getTime().toString());
		
		int nrAdded = 0;
		while (nrAdded < number) {
			date.add(Calendar.DAY_OF_WEEK, 1);
			if (isBussinessDay(date)) {
				nrAdded++;
			}
		}
		log.debug("Calculated Due Date is " + date.getTime().toString());
		return date;
	}

	public boolean isBussinessDay(Calendar date) {
		if ((date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) || (date.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		
		Calendar c = Calendar.getInstance();
		int days = 5;
		
		Calendar before = new BusinessCalendar().addBussinessDays(days,c);
	}
}
