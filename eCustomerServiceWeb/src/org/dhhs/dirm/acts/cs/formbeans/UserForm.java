

package org.dhhs.dirm.acts.cs.formbeans;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.dhhs.dirm.acts.cs.Constants;

/**
 * UserForm.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Sep 17, 2003 2:10:04 PM
 * 
 * @author rkodumagulla
 *
 */
public class UserForm extends ActionForm
{

	private String	idWorker;

	private String	nmWorker;

	private String	idProfile;

	private String	nbPhone;

	private String	idPrinter;

	private String	nbWorkHourStart;

	private String	nbWorkHourEnd;

	private String	nbLunchStart;

	private String	nbLunchEnd;

	private String	idEmail;

	private String	idPassword;

	private String	idPassword2;

	private String	action	= "Create";

	private boolean	cdAccptWrkld;

	private boolean	cdResetPassword;

	private boolean	approvalRequired;

	private int		nbCompleted;

	private int		nbOutstanding;

	private int		nbApproval;

	/**
	 * Constructor for UserForm.
	 */
	public UserForm()
	{
		idWorker = "";
		nmWorker = "";
		idProfile = "";
		idPassword = "";
		idPassword2 = "";
		nbOutstanding = 0;
		nbCompleted = 0;
		nbApproval = 0;
	}

	/**
	 * Returns the idPassword.
	 * 
	 * @return String
	 */
	public String getIdPassword()
	{
		return idPassword;
	}

	/**
	 * Returns the idProfile.
	 * 
	 * @return String
	 */
	public String getIdProfile()
	{
		return idProfile;
	}

	/**
	 * Returns the idWorker.
	 * 
	 * @return String
	 */
	public String getIdWorker()
	{
		return idWorker;
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
	 * Sets the idPassword.
	 * 
	 * @param idPassword
	 *            The idPassword to set
	 */
	public void setIdPassword(String idPassword)
	{
		this.idPassword = idPassword;
	}

	/**
	 * Sets the idProfile.
	 * 
	 * @param idProfile
	 *            The idProfile to set
	 */
	public void setIdProfile(String idProfile)
	{
		this.idProfile = idProfile;
	}

	/**
	 * Sets the idWorker.
	 * 
	 * @param idWorker
	 *            The idWorker to set
	 */
	public void setIdWorker(String idWorker)
	{
		this.idWorker = idWorker;
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
	 * Returns the action.
	 * 
	 * @return String
	 */
	public String getAction()
	{
		return action;
	}

	/**
	 * Returns the idPassword2.
	 * 
	 * @return String
	 */
	public String getIdPassword2()
	{
		return idPassword2;
	}

	/**
	 * Sets the action.
	 * 
	 * @param action
	 *            The action to set
	 */
	public void setAction(String action)
	{
		this.action = action;
	}

	/**
	 * Sets the idPassword2.
	 * 
	 * @param idPassword2
	 *            The idPassword2 to set
	 */
	public void setIdPassword2(String idPassword2)
	{
		this.idPassword2 = idPassword2;
	}

	/**
	 * @see org.apache.struts.action.ActionForm#validate(ActionMapping,
	 *      HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{

		ActionErrors errors = null;

		if ((!request.getParameter(Constants.METHOD).equals(Constants.CREATE)) && (!request.getParameter(Constants.METHOD).equals(Constants.EDIT)) && (!request.getParameter(Constants.METHOD).equals(Constants.BACK)) && (!request.getParameter(Constants.METHOD).equals(Constants.VIEW)))
		{
			errors = super.validate(mapping, request);
		}

		if (errors == null)
		{
			errors = new ActionErrors();
		} else
		{
			if (request.getParameter(Constants.METHOD).equals(Constants.SAVE))
			{
				request.setAttribute("formMode", "1");
			}
			if (request.getParameter(Constants.METHOD).equals(Constants.STORE))
			{
				request.setAttribute("formMode", "0");
			}
		}
		return errors;

	}

	/**
	 * Returns the cdAccptWrkld.
	 * 
	 * @return boolean
	 */
	public boolean getCdAccptWrkld()
	{
		return cdAccptWrkld;
	}

	/**
	 * Returns the nbApproval.
	 * 
	 * @return int
	 */
	public int getNbApproval()
	{
		return nbApproval;
	}

	/**
	 * Returns the nbCompleted.
	 * 
	 * @return int
	 */
	public int getNbCompleted()
	{
		return nbCompleted;
	}

	/**
	 * Returns the nbOutstanding.
	 * 
	 * @return int
	 */
	public int getNbOutstanding()
	{
		return nbOutstanding;
	}

	/**
	 * Sets the cdAccptWrkld.
	 * 
	 * @param cdAccptWrkld
	 *            The cdAccptWrkld to set
	 */
	public void setCdAccptWrkld(boolean cdAccptWrkld)
	{
		this.cdAccptWrkld = cdAccptWrkld;
	}

	/**
	 * Sets the nbApproval.
	 * 
	 * @param nbApproval
	 *            The nbApproval to set
	 */
	public void setNbApproval(int nbApproval)
	{
		this.nbApproval = nbApproval;
	}

	/**
	 * Sets the nbCompleted.
	 * 
	 * @param nbCompleted
	 *            The nbCompleted to set
	 */
	public void setNbCompleted(int nbCompleted)
	{
		this.nbCompleted = nbCompleted;
	}

	/**
	 * Sets the nbOutstanding.
	 * 
	 * @param nbOutstanding
	 *            The nbOutstanding to set
	 */
	public void setNbOutstanding(int nbOutstanding)
	{
		this.nbOutstanding = nbOutstanding;
	}

	/**
	 * Returns the cdResetPassword.
	 * 
	 * @return boolean
	 */
	public boolean isCdResetPassword()
	{
		return cdResetPassword;
	}

	/**
	 * Sets the cdResetPassword.
	 * 
	 * @param cdResetPassword
	 *            The cdResetPassword to set
	 */
	public void setCdResetPassword(boolean cdResetPassword)
	{
		this.cdResetPassword = cdResetPassword;
	}

	/**
	 * Returns the cdAccptWrkld.
	 * 
	 * @return boolean
	 */
	public boolean isCdAccptWrkld()
	{
		return cdAccptWrkld;
	}

	/**
	 * Returns the idEmail.
	 * 
	 * @return String
	 */
	public String getIdEmail()
	{
		return idEmail;
	}

	/**
	 * Returns the idPrinter.
	 * 
	 * @return String
	 */
	public String getIdPrinter()
	{
		return idPrinter;
	}

	/**
	 * Returns the nbLunchEnd.
	 * 
	 * @return String
	 */
	public String getNbLunchEnd()
	{
		return nbLunchEnd;
	}

	/**
	 * Returns the nbLunchStart.
	 * 
	 * @return String
	 */
	public String getNbLunchStart()
	{
		return nbLunchStart;
	}

	/**
	 * Returns the nbPhone.
	 * 
	 * @return String
	 */
	public String getNbPhone()
	{
		return nbPhone;
	}

	/**
	 * Returns the nbWorkHourEnd.
	 * 
	 * @return String
	 */
	public String getNbWorkHourEnd()
	{
		return nbWorkHourEnd;
	}

	/**
	 * Returns the nbWorkHourStart.
	 * 
	 * @return String
	 */
	public String getNbWorkHourStart()
	{
		return nbWorkHourStart;
	}

	/**
	 * Sets the idEmail.
	 * 
	 * @param idEmail
	 *            The idEmail to set
	 */
	public void setIdEmail(String idEmail)
	{
		this.idEmail = idEmail;
	}

	/**
	 * Sets the idPrinter.
	 * 
	 * @param idPrinter
	 *            The idPrinter to set
	 */
	public void setIdPrinter(String idPrinter)
	{
		this.idPrinter = idPrinter;
	}

	/**
	 * Sets the nbLunchEnd.
	 * 
	 * @param nbLunchEnd
	 *            The nbLunchEnd to set
	 */
	public void setNbLunchEnd(String nbLunchEnd)
	{
		this.nbLunchEnd = nbLunchEnd;
	}

	/**
	 * Sets the nbLunchStart.
	 * 
	 * @param nbLunchStart
	 *            The nbLunchStart to set
	 */
	public void setNbLunchStart(String nbLunchStart)
	{
		this.nbLunchStart = nbLunchStart;
	}

	/**
	 * Sets the nbPhone.
	 * 
	 * @param nbPhone
	 *            The nbPhone to set
	 */
	public void setNbPhone(String nbPhone)
	{
		this.nbPhone = nbPhone;
	}

	/**
	 * Sets the nbWorkHourEnd.
	 * 
	 * @param nbWorkHourEnd
	 *            The nbWorkHourEnd to set
	 */
	public void setNbWorkHourEnd(String nbWorkHourEnd)
	{
		this.nbWorkHourEnd = nbWorkHourEnd;
	}

	/**
	 * Sets the nbWorkHourStart.
	 * 
	 * @param nbWorkHourStart
	 *            The nbWorkHourStart to set
	 */
	public void setNbWorkHourStart(String nbWorkHourStart)
	{
		this.nbWorkHourStart = nbWorkHourStart;
	}

	/**
	 * Returns the approvalRequired.
	 * 
	 * @return boolean
	 */
	public boolean isApprovalRequired()
	{
		return approvalRequired;
	}

	/**
	 * Sets the approvalRequired.
	 * 
	 * @param approvalRequired
	 *            The approvalRequired to set
	 */
	public void setApprovalRequired(boolean approvalRequired)
	{
		this.approvalRequired = approvalRequired;
	}

}
