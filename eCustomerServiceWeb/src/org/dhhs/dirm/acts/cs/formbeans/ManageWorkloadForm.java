

package org.dhhs.dirm.acts.cs.formbeans;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.dhhs.dirm.acts.cs.beans.UserBean;

/**
 * ManageWorkloadForm.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Dec 15, 2003 10:49:05 AM
 * 
 * @author rkodumagulla
 *
 */
public class ManageWorkloadForm extends ActionForm
{

	private String		workerid;

	private String		nmWorker;

	private int			nbrOutstanding;

	private int			nbrApproval;

	private int			nbrCompleted;

	private int			calculate;

	private String[]	selectedWorkers	= new String[100];

	private Vector		targetUsers;

	/**
	 * Constructor for ManageWorkloadForm.
	 */
	public ManageWorkloadForm()
	{
		super();

		targetUsers = new Vector();
	}

	/*
	 * //struts validator public ActionErrors validate(ActionMapping mapping,
	 * HttpServletRequest request) {
	 * 
	 * ActionErrors errors = null;
	 * 
	 * if ((!request.getParameter(Constants.METHOD).equals(Constants.LOAD)) &&
	 * (!request.getParameter(Constants.METHOD).equals(Constants.LOADALL))) {
	 * errors = super.validate(mapping, request); }
	 * 
	 * if (errors == null) { errors = new ActionErrors(); } return errors; }
	 */

	public void addUser(UserBean user)
	{
		targetUsers.addElement(user);
	}

	/**
	 * Returns the nbrApproval.
	 * 
	 * @return int
	 */
	public int getNbrApproval()
	{
		return nbrApproval;
	}

	/**
	 * Returns the nbrCompleted.
	 * 
	 * @return int
	 */
	public int getNbrCompleted()
	{
		return nbrCompleted;
	}

	/**
	 * Returns the nbrOutstanding.
	 * 
	 * @return int
	 */
	public int getNbrOutstanding()
	{
		return nbrOutstanding;
	}

	/**
	 * Returns the targetUsers.
	 * 
	 * @return Vector
	 */
	public Vector getTargetUsers()
	{
		return targetUsers;
	}

	/**
	 * Returns the workerid.
	 * 
	 * @return String
	 */
	public String getWorkerid()
	{
		return workerid;
	}

	/**
	 * Sets the nbrApproval.
	 * 
	 * @param nbrApproval
	 *            The nbrApproval to set
	 */
	public void setNbrApproval(int nbrApproval)
	{
		this.nbrApproval = nbrApproval;
	}

	/**
	 * Sets the nbrCompleted.
	 * 
	 * @param nbrCompleted
	 *            The nbrCompleted to set
	 */
	public void setNbrCompleted(int nbrCompleted)
	{
		this.nbrCompleted = nbrCompleted;
	}

	/**
	 * Sets the nbrOutstanding.
	 * 
	 * @param nbrOutstanding
	 *            The nbrOutstanding to set
	 */
	public void setNbrOutstanding(int nbrOutstanding)
	{
		this.nbrOutstanding = nbrOutstanding;
	}

	/**
	 * Sets the targetUsers.
	 * 
	 * @param targetUsers
	 *            The targetUsers to set
	 */
	public void setTargetUsers(Vector targetUsers)
	{
		this.targetUsers = targetUsers;
	}

	/**
	 * Sets the workerid.
	 * 
	 * @param workerid
	 *            The workerid to set
	 */
	public void setWorkerid(String workerid)
	{
		this.workerid = workerid;
	}

	/**
	 * Returns the nmWorker.
	 * 
	 * @return String
	 */
	public String getNmWorker()
	{
		return nmWorker;
	}

	/**
	 * Sets the nmWorker.
	 * 
	 * @param nmWorker
	 *            The nmWorker to set
	 */
	public void setNmWorker(String nmWorker)
	{
		this.nmWorker = nmWorker;
	}

	/**
	 * Returns the selectedWorkers.
	 * 
	 * @return String[]
	 */
	public String[] getSelectedWorkers()
	{
		return selectedWorkers;
	}

	/**
	 * Sets the selectedWorkers.
	 * 
	 * @param selectedWorkers
	 *            The selectedWorkers to set
	 */
	public void setSelectedWorkers(String[] selectedWorkers)
	{
		this.selectedWorkers = selectedWorkers;
	}

	public void setSelectedWorkers(int i, String selectedWorker)
	{
		System.out.println("Selected Worker " + selectedWorker + " @ line number" + i);
		this.selectedWorkers[i] = selectedWorker;
	}

	/**
	 * @see org.apache.struts.action.ActionForm#reset(ActionMapping,
	 *      HttpServletRequest)
	 */
	public void reset(ActionMapping arg0, HttpServletRequest arg1)
	{
		super.reset(arg0, arg1);

		System.out.println("ManageWorkloadForm -> Size of selectedWorkers :" + selectedWorkers.length);

		for (int i = 0; i < selectedWorkers.length; i++)
		{
			selectedWorkers[i] = "";
		}
	}

	/**
	 * Returns the calculate.
	 * 
	 * @return int
	 */
	public int getCalculate()
	{
		return calculate;
	}

	/**
	 * Sets the calculate.
	 * 
	 * @param calculate
	 *            The calculate to set
	 */
	public void setCalculate(int calculate)
	{
		this.calculate = calculate;
	}

}
