package org.dhhs.dirm.acts.cs.persister;

/**
 * EmptyListException.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained for
 * North Carolina Child Support Enforcement - ACTS Project.
 * 
 * Creation Date: Aug 12, 2004 9:28:03 AM
 * 
 * @author RKodumagulla
 *
 */
public class EmptyListException extends Exception {

	/**
	 * Constructor for EmptyListException.
	 */
	public EmptyListException() {
		super();
	}

	/**
	 * Constructor for EmptyListException.
	 * @param s
	 */
	public EmptyListException(String s) {
		super(s);
	}

}
