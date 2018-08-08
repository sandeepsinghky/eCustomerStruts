package org.dhhs.dirm.acts.cs.persister;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.dhhs.dirm.acts.cs.beans.UserEntityBean;
import org.dhhs.dirm.acts.cs.ejb.util.Constants;

/**
 * AgentDelegate.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained for
 * North Carolina Child Support Enforcement - ACTS Project.
 * 
 * Creation Date: Aug 11, 2004 4:14:10 PM
 * 
 * @author RKodumagulla
 *
 */
public class AgentDelegate {
	
	private static final Logger log = Logger.getLogger(AgentDelegate.class);
	
	private static Vector agents = null;
	
	private static UserEntityBean lastAgent = null;
	
	/**
	 * Static Initializer for AgentDelegate.
	 */
	static {
		try {
			AgentDelegate delegate = new AgentDelegate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public AgentDelegate() throws SQLException {
		agents = new Vector();
	}
	
	public static void addAgents(Vector v) {
		agents = v;
	}
	
	public static void addAgent(UserEntityBean ueb) {
		if (agents == null) {
			agents = new Vector();
		} else {
			log.info("AgentDelegate: Adding Agent " + ueb.getIdWrkr() + " Accept Workload? " + ueb.getCdAccptWrkld());
			agents.addElement(ueb);
		}
	}
	
	public static void updateAgentStatus(String idWorker, String cdStatus) throws EmptyListException {
		
		log.info("AgentDelegate: Updating Agent " + idWorker + " Accept Workload? : " + cdStatus);
		
		if (agents == null || agents.size() == 0) {
			throw new EmptyListException("Agent Collection not loaded.");
		} else {
			loop:
			for (int i = 0; i < agents.size(); i++) {
				UserEntityBean ueb = (UserEntityBean)agents.elementAt(i);
				if (ueb.getIdWrkr().equalsIgnoreCase(idWorker)) {
					ueb.setCdAccptWrkld(cdStatus);
					break loop;
				}
			}
		}
	}
	
	public static synchronized UserEntityBean getNextAvailableAgent() throws EmptyListException {
		/**
		 * If the agents vector is null or contains no agents
		 * return null
		 */
		UserEntityBean nextAgent = null;
		int counter = 0;
		
		if (agents == null || agents.size() == 0) {
			throw new EmptyListException("Agent Collection not loaded.");
		} else {
			if (lastAgent == null) {
				firstLoop:
				for (int i = 0; i < agents.size(); i++) {
					UserEntityBean ueb = (UserEntityBean)agents.elementAt(i);
					if (ueb.getCdAccptWrkld().equalsIgnoreCase("Y")) {
						lastAgent = ueb;
						break firstLoop;
					}
				}
				log.info("AgentDelegate: This is the first agent being selected " + lastAgent.getIdWrkr());
				return lastAgent;
			} else {
				log.info("AgentDelegate: Last Agent selected was " + lastAgent.getIdWrkr());
			}
			loop:
			for (counter = 0; counter < agents.size(); counter++) {
				UserEntityBean ueb = (UserEntityBean)agents.elementAt(counter);
				if (ueb.getIdWrkr().equals(lastAgent.getIdWrkr())) {
					break loop;
				}
			}
		}
		
		/** We found the agent that was the last used agent and the index where the
		 * occurs in the list. increment the counter and find an agent in the list below
		 * the currrent agent. If found, return the agent, else start from the beginning
		 */
		counter++;
		
		upperLoop:
		for (int i = counter; i < agents.size(); i++) {
			UserEntityBean ueb = (UserEntityBean)agents.elementAt(i);
			if (ueb.getCdAccptWrkld().equalsIgnoreCase("Y")) {
				nextAgent = ueb;
				break upperLoop;
			}
		}
		
		/**
		 * If agent is not found from the upperLoop logic, continue searching 
		 * from the top until an agent is found. if no agent is found, then assign 
		 * the task to the last agent
		 */
		if (nextAgent == null) {
			lowerLoop:
			for (int i = 0; i < agents.size(); i++) {
				UserEntityBean ueb = (UserEntityBean)agents.elementAt(i);
				if (ueb.getCdAccptWrkld().equalsIgnoreCase("Y")) {
					nextAgent = ueb;
					break lowerLoop;
				}
			}
		}
		
		if (nextAgent != null) {
			lastAgent = nextAgent;
			log.info("AgentDelegate: Next Agent Selected is " + nextAgent.getIdWrkr());
		} else {
			log.info("AgentDelegate: No other agent found. Selecting the last agent " + lastAgent.getIdWrkr());
		}
		
		return ((nextAgent == null) ? lastAgent : nextAgent);
	}
}
