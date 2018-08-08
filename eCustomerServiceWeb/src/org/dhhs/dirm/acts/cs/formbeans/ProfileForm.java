

package org.dhhs.dirm.acts.cs.formbeans;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;
import org.dhhs.dirm.acts.cs.Constants;

/**
 * ActsWorkerSearchForm.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Aug 26, 2003 2:29:17 PM
 * 
 * @author rkodumagulla
 *
 */
public class ProfileForm extends ValidatorForm
{

	private String	idProfile;

	private String	idProfileDesc;

	private String	tsCreate;

	private String	tsUpdate;

	private String	idWrkrCreate;

	private String	idWrkrUpdate;

	private boolean	manageAll;

	private boolean	manageUsers;

	private boolean	manageProfiles;

	private boolean	manageWorkFlow;

	private boolean	manageWorkLoad;

	private boolean	manageApprovals;

	private boolean	manageReferralSources;

	private boolean	manageReports;

	private String	action	= "update";

	// struts validator
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{

		ActionErrors errors = null;

		if (request.getParameter(Constants.METHOD) != null)
		{
			if ((!request.getParameter(Constants.METHOD).equals(Constants.CREATE)) && (!request.getParameter(Constants.METHOD).equals(Constants.EDIT)) && (!request.getParameter(Constants.METHOD).equals(Constants.VIEW)))
			{
				errors = super.validate(mapping, request);
			}
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
	 * Returns the idProfile.
	 * 
	 * @return String
	 */
	public String getIdProfile()
	{
		return idProfile;
	}

	/**
	 * Returns the idProfileDesc.
	 * 
	 * @return String
	 */
	public String getIdProfileDesc()
	{
		return idProfileDesc;
	}

	/**
	 * Returns the idWrkrCreate.
	 * 
	 * @return String
	 */
	public String getIdWrkrCreate()
	{
		return idWrkrCreate;
	}

	/**
	 * Returns the idWrkrUpdate.
	 * 
	 * @return String
	 */
	public String getIdWrkrUpdate()
	{
		return idWrkrUpdate;
	}

	/**
	 * Returns the tsCreate.
	 * 
	 * @return String
	 */
	public String getTsCreate()
	{
		return tsCreate;
	}

	/**
	 * Returns the tsUpdate.
	 * 
	 * @return String
	 */
	public String getTsUpdate()
	{
		return tsUpdate;
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
	 * Sets the idProfileDesc.
	 * 
	 * @param idProfileDesc
	 *            The idProfileDesc to set
	 */
	public void setIdProfileDesc(String idProfileDesc)
	{
		this.idProfileDesc = idProfileDesc;
	}

	/**
	 * Sets the idWrkrCreate.
	 * 
	 * @param idWrkrCreate
	 *            The idWrkrCreate to set
	 */
	public void setIdWrkrCreate(String idWrkrCreate)
	{
		this.idWrkrCreate = idWrkrCreate;
	}

	/**
	 * Sets the idWrkrUpdate.
	 * 
	 * @param idWrkrUpdate
	 *            The idWrkrUpdate to set
	 */
	public void setIdWrkrUpdate(String idWrkrUpdate)
	{
		this.idWrkrUpdate = idWrkrUpdate;
	}

	/**
	 * Sets the tsCreate.
	 * 
	 * @param tsCreate
	 *            The tsCreate to set
	 */
	public void setTsCreate(String tsCreate)
	{
		this.tsCreate = tsCreate;
	}

	/**
	 * Sets the tsUpdate.
	 * 
	 * @param tsUpdate
	 *            The tsUpdate to set
	 */
	public void setTsUpdate(String tsUpdate)
	{
		this.tsUpdate = tsUpdate;
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
	 * Returns the manageAll.
	 * 
	 * @return boolean
	 */
	public boolean isManageAll()
	{
		return manageAll;
	}

	/**
	 * Returns the manageApprovals.
	 * 
	 * @return boolean
	 */
	public boolean isManageApprovals()
	{
		return manageApprovals;
	}

	/**
	 * Returns the manageProfiles.
	 * 
	 * @return boolean
	 */
	public boolean isManageProfiles()
	{
		return manageProfiles;
	}

	/**
	 * Returns the manageReferralSources.
	 * 
	 * @return boolean
	 */
	public boolean isManageReferralSources()
	{
		return manageReferralSources;
	}

	/**
	 * Returns the manageReports.
	 * 
	 * @return boolean
	 */
	public boolean isManageReports()
	{
		return manageReports;
	}

	/**
	 * Returns the manageUsers.
	 * 
	 * @return boolean
	 */
	public boolean isManageUsers()
	{
		return manageUsers;
	}

	/**
	 * Returns the manageWorkFlow.
	 * 
	 * @return boolean
	 */
	public boolean isManageWorkFlow()
	{
		return manageWorkFlow;
	}

	/**
	 * Returns the manageWorkLoad.
	 * 
	 * @return boolean
	 */
	public boolean isManageWorkLoad()
	{
		return manageWorkLoad;
	}

	/**
	 * Sets the manageAll.
	 * 
	 * @param manageAll
	 *            The manageAll to set
	 */
	public void setManageAll(boolean manageAll)
	{
		this.manageAll = manageAll;
	}

	/**
	 * Sets the manageApprovals.
	 * 
	 * @param manageApprovals
	 *            The manageApprovals to set
	 */
	public void setManageApprovals(boolean manageApprovals)
	{
		this.manageApprovals = manageApprovals;
	}

	/**
	 * Sets the manageProfiles.
	 * 
	 * @param manageProfiles
	 *            The manageProfiles to set
	 */
	public void setManageProfiles(boolean manageProfiles)
	{
		this.manageProfiles = manageProfiles;
	}

	/**
	 * Sets the manageReferralSources.
	 * 
	 * @param manageReferralSources
	 *            The manageReferralSources to set
	 */
	public void setManageReferralSources(boolean manageReferralSources)
	{
		this.manageReferralSources = manageReferralSources;
	}

	/**
	 * Sets the manageReports.
	 * 
	 * @param manageReports
	 *            The manageReports to set
	 */
	public void setManageReports(boolean manageReports)
	{
		this.manageReports = manageReports;
	}

	/**
	 * Sets the manageUsers.
	 * 
	 * @param manageUsers
	 *            The manageUsers to set
	 */
	public void setManageUsers(boolean manageUsers)
	{
		this.manageUsers = manageUsers;
	}

	/**
	 * Sets the manageWorkFlow.
	 * 
	 * @param manageWorkFlow
	 *            The manageWorkFlow to set
	 */
	public void setManageWorkFlow(boolean manageWorkFlow)
	{
		this.manageWorkFlow = manageWorkFlow;
	}

	/**
	 * Sets the manageWorkLoad.
	 * 
	 * @param manageWorkLoad
	 *            The manageWorkLoad to set
	 */
	public void setManageWorkLoad(boolean manageWorkLoad)
	{
		this.manageWorkLoad = manageWorkLoad;
	}

}
