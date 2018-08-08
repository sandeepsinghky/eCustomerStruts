

package org.dhhs.dirm.acts.cs.struts;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.MessageResources;
import org.dhhs.dirm.acts.cs.Constants;
import org.dhhs.dirm.acts.cs.ManageServletStack;
import org.dhhs.dirm.acts.cs.beans.Profile;
import org.dhhs.dirm.acts.cs.businesslogic.BusinessLogicException;
import org.dhhs.dirm.acts.cs.businesslogic.DuplicateException;
import org.dhhs.dirm.acts.cs.businesslogic.UserProfileManager;
import org.dhhs.dirm.acts.cs.formbeans.ProfileForm;
import org.dhhs.dirm.acts.cs.util.ApplicationException;
import org.dhhs.dirm.acts.cs.util.ErrorDescriptor;

/**
 * ManageUserAction.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Sep 17, 2003 4:24:15 PM
 * 
 * @author rkodumagulla
 *
 */
public class ManageProfileAction extends DispatchAction
{

	private static final Logger log = Logger.getLogger(ManageProfileAction.class);

	/**
	 * Constructor for ManageUserAction.
	 */
	public ManageProfileAction()
	{
		super();
	}

	/**
	 * @see org.apache.struts.action.Action#create(ActionMapping, ActionForm,
	 *      HttpServletRequest, HttpServletResponse)
	 */
	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		try
		{
			Locale locale = getLocale(request);
			MessageResources messages = getResources(request);
			HttpSession session = request.getSession();

			form = new ProfileForm();

			request.setAttribute("formMode", "0");

			((ProfileForm) form).setAction("create");

			if ("request".equals(mapping.getScope()))
				request.setAttribute(mapping.getAttribute(), form);
			else
			{
				session.setAttribute(mapping.getAttribute(), form);
			}
			saveToken(request);
			/**
			 * Logic for return functionality
			 */
			new ManageServletStack().addToStack(request, session);

			return (mapping.findForward("create"));
		} catch (Exception e)
		{
			log.error("Manage Profile Action create Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageProfileAction", "create"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			return (new ActionForward(mapping.getInput()));
		}
	}

	/**
	 * @see org.apache.struts.action.Action#edit(ActionMapping, ActionForm,
	 *      HttpServletRequest, HttpServletResponse)
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		try
		{

			Locale locale = getLocale(request);
			MessageResources messages = getResources(request);
			HttpSession session = request.getSession();

			String idProfile = (String) request.getParameter("idProfile");

			if (idProfile == null)
			{ // Go to the failure page
				log.error("Profile ID is missing");
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("manageProfile.error.idRequired"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			// Populate the profile form
			if (form == null)
			{
				form = new ProfileForm();
				if ("request".equals(mapping.getScope()))
					request.setAttribute(mapping.getAttribute(), form);
				else
				{
					session.setAttribute(mapping.getAttribute(), form);
				}
			}

			ProfileForm profileForm = (ProfileForm) form;

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				UserProfileManager manager = UserProfileManager.getInstance();
				if (idProfile.length() > 0)
				{
					Profile profile = manager.getProfile(idProfile);
					profileForm.setIdProfile(profile.getIdProfile());
					profileForm.setIdProfileDesc(profile.getIdProfileDesc());
					profileForm.setIdWrkrCreate(profile.getIdWrkrCreate());
					profileForm.setIdWrkrUpdate(profile.getIdWrkrUpdate());
					profileForm.setTsCreate(profile.getTsCreate());
					profileForm.setTsUpdate(profile.getTsUpdate());

					String cdMenuItems = profile.getCdMenuItems();

					profileForm.setManageAll(cdMenuItems.charAt(0) == 'T' ? true : false);
					profileForm.setManageUsers(cdMenuItems.charAt(1) == 'T' ? true : false);
					profileForm.setManageProfiles(cdMenuItems.charAt(2) == 'T' ? true : false);
					profileForm.setManageWorkFlow(cdMenuItems.charAt(3) == 'T' ? true : false);
					profileForm.setManageWorkLoad(cdMenuItems.charAt(4) == 'T' ? true : false);
					profileForm.setManageApprovals(cdMenuItems.charAt(5) == 'T' ? true : false);
					profileForm.setManageReferralSources(cdMenuItems.charAt(6) == 'T' ? true : false);
					profileForm.setManageReports(cdMenuItems.charAt(7) == 'T' ? true : false);
				}
			} catch (BusinessLogicException ble)
			{
				log.fatal("ManageProfileAction Failed. " + ble.getMessage());

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageProfileAction", "edit"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			saveToken(request);

			request.setAttribute("formMode", "1");

			profileForm.setAction("update");
			/**
			 * Logic for return functionality
			 */
			new ManageServletStack().addToStack(request, session);

			return (mapping.findForward("edit"));
		} catch (Exception e)
		{
			log.error("Manage Profile Action edit Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageProfileAction", "edit"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			return (new ActionForward(mapping.getInput()));
		}
	}

	/**
	 * @see org.apache.struts.action.Action#view(ActionMapping, ActionForm,
	 *      HttpServletRequest, HttpServletResponse)
	 */
	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		try
		{
			Locale locale = getLocale(request);
			MessageResources messages = getResources(request);
			HttpSession session = request.getSession();

			String idProfile = (String) request.getParameter("idProfile");

			if (idProfile == null)
			{ // Go to the failure page
				log.error("Profile ID is missing");
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("manageProfile.error.idRequired"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}
			if (form == null)
			{
				form = new ProfileForm();
				if ("request".equals(mapping.getScope()))
					request.setAttribute(mapping.getAttribute(), form);
				else
				{
					session.setAttribute(mapping.getAttribute(), form);
				}
			}

			ProfileForm profileForm = (ProfileForm) form;
			profileForm.setAction("view");

			request.setAttribute("formMode", "2");

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				UserProfileManager manager = UserProfileManager.getInstance();
				if (idProfile.length() > 0)
				{
					Profile profile = manager.getProfile(idProfile);
					profileForm.setIdProfile(profile.getIdProfile());
					profileForm.setIdProfileDesc(profile.getIdProfileDesc());

					String cdMenuItems = profile.getCdMenuItems();

					profileForm.setManageAll(cdMenuItems.charAt(0) == 'T' ? true : false);
					profileForm.setManageUsers(cdMenuItems.charAt(1) == 'T' ? true : false);
					profileForm.setManageProfiles(cdMenuItems.charAt(2) == 'T' ? true : false);
					profileForm.setManageWorkFlow(cdMenuItems.charAt(3) == 'T' ? true : false);
					profileForm.setManageWorkLoad(cdMenuItems.charAt(4) == 'T' ? true : false);
					profileForm.setManageApprovals(cdMenuItems.charAt(5) == 'T' ? true : false);
					profileForm.setManageReferralSources(cdMenuItems.charAt(6) == 'T' ? true : false);
					profileForm.setManageReports(cdMenuItems.charAt(7) == 'T' ? true : false);

					profileForm.setIdWrkrCreate(profile.getIdWrkrCreate());
					profileForm.setIdWrkrUpdate(profile.getIdWrkrUpdate());
					profileForm.setTsCreate(profile.getTsCreate());
					profileForm.setTsUpdate(profile.getTsUpdate());
				}
			} catch (BusinessLogicException ble)
			{
				log.fatal("ManageProfileAction Failed. " + ble.getMessage());

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageProfileAction", "view"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			saveToken(request);
			/**
			 * Logic for return functionality
			 */
			new ManageServletStack().addToStack(request, session);

			return (mapping.findForward("view"));
		} catch (Exception e)
		{
			log.error("Manage Profile Action view Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageProfileAction", "view"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			return (new ActionForward(mapping.getInput()));
		}
	}

	/**
	 * @see org.apache.struts.action.Action#save(ActionMapping, ActionForm,
	 *      HttpServletRequest, HttpServletResponse)
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		try
		{
			Locale locale = getLocale(request);
			MessageResources messages = getResources(request);
			HttpSession session = request.getSession();

			ProfileForm profileForm = (ProfileForm) form;

			if (isCancelled(request))
			{
				if (mapping.getAttribute() != null)
					session.removeAttribute(mapping.getAttribute());
				return (mapping.findForward(Constants.SUCCESS));
			}

			org.dhhs.dirm.acts.cs.beans.UserBean loggedInUser = (org.dhhs.dirm.acts.cs.beans.UserBean) session.getAttribute(Constants.USERBEAN_KEY);

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				UserProfileManager manager = UserProfileManager.getInstance();

				String cdMenuItems = "";

				if (profileForm.isManageAll())
				{
					cdMenuItems += "TTTTTTTT";
				} else
				{
					cdMenuItems += "F";
				}
				if (!profileForm.isManageAll())
				{
					if (profileForm.isManageUsers())
					{
						cdMenuItems += "T";
					} else
					{
						cdMenuItems += "F";
					}

					if (profileForm.isManageProfiles())
					{
						cdMenuItems += "T";
					} else
					{
						cdMenuItems += "F";
					}

					if (profileForm.isManageWorkFlow())
					{
						cdMenuItems += "T";
					} else
					{
						cdMenuItems += "F";
					}

					if (profileForm.isManageWorkLoad())
					{
						cdMenuItems += "T";
					} else
					{
						cdMenuItems += "F";
					}

					if (profileForm.isManageApprovals())
					{
						cdMenuItems += "T";
					} else
					{
						cdMenuItems += "F";
					}

					if (profileForm.isManageReferralSources())
					{
						cdMenuItems += "T";
					} else
					{
						cdMenuItems += "F";
					}

					if (profileForm.isManageReports())
					{
						cdMenuItems += "T";
					} else
					{
						cdMenuItems += "F";
					}
				}
				manager.updateProfile(profileForm.getIdProfile(), profileForm.getIdProfileDesc(), cdMenuItems, loggedInUser.getIdWorker());
			} catch (BusinessLogicException ble)
			{
				log.fatal("ManageProfileAction Failed. " + ble.getMessage());

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageProfileAction", "save"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}
			saveToken(request);
			return (mapping.findForward("save"));
		} catch (Exception e)
		{
			log.error("Manage Profile Action view Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageProfileAction", "view"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			return (new ActionForward(mapping.getInput()));
		}
	}

	/**
	 * @see org.apache.struts.action.Action#store(ActionMapping, ActionForm,
	 *      HttpServletRequest, HttpServletResponse)
	 */
	public ActionForward store(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		try
		{
			Locale locale = getLocale(request);
			MessageResources messages = getResources(request);
			HttpSession session = request.getSession();

			ProfileForm profileForm = (ProfileForm) form;

			if (isCancelled(request))
			{
				if (mapping.getAttribute() != null)
					session.removeAttribute(mapping.getAttribute());
				return (mapping.findForward(Constants.SUCCESS));
			}

			org.dhhs.dirm.acts.cs.beans.UserBean loggedInUser = (org.dhhs.dirm.acts.cs.beans.UserBean) session.getAttribute(Constants.USERBEAN_KEY);

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				UserProfileManager manager = UserProfileManager.getInstance();

				String cdMenuItems = "";

				// If manageAll is checked, all other items must be checked by
				// default
				if (profileForm.isManageAll())
				{
					cdMenuItems = "TTTTTTTT";
				} else
				{
					cdMenuItems += "F";
				}

				if (!profileForm.isManageAll())
				{
					if (profileForm.isManageUsers())
					{
						cdMenuItems += "T";
					} else
					{
						cdMenuItems += "F";
					}

					if (profileForm.isManageProfiles())
					{
						cdMenuItems += "T";
					} else
					{
						cdMenuItems += "F";
					}

					if (profileForm.isManageWorkFlow())
					{
						cdMenuItems += "T";
					} else
					{
						cdMenuItems += "F";
					}

					if (profileForm.isManageWorkLoad())
					{
						cdMenuItems += "T";
					} else
					{
						cdMenuItems += "F";
					}

					if (profileForm.isManageApprovals())
					{
						cdMenuItems += "T";
					} else
					{
						cdMenuItems += "F";
					}

					if (profileForm.isManageReferralSources())
					{
						cdMenuItems += "T";
					} else
					{
						cdMenuItems += "F";
					}

					if (profileForm.isManageReports())
					{
						cdMenuItems += "T";
					} else
					{
						cdMenuItems += "F";
					}
				}

				manager.createProfile(profileForm.getIdProfile().toUpperCase(), profileForm.getIdProfileDesc(), cdMenuItems, loggedInUser.getIdWorker());

				/**
				 * Refresh the application scope variable profiles
				 */
				getServlet().getServletContext().setAttribute("profiles", manager.getAllUserProfiles());
			} catch (BusinessLogicException ble)
			{
				log.fatal("ManageProfileAction Failed. " + ble.getMessage());

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageProfileAction", "store"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (DuplicateException de)
			{
				log.fatal("ManageProfileAction Failed. " + de.getMessage());

				new ApplicationException(de.getMessage(), de, new ErrorDescriptor("ManageProfileAction", "store"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.create.duplicate", "profile", profileForm.getIdProfile()));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			request.setAttribute("formMode", "2");

			saveToken(request);
			return (mapping.findForward("store"));
		} catch (Exception e)
		{
			log.error("Manage Profile Action view Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageProfileAction", "store"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			return (new ActionForward(mapping.getInput()));
		}
	}
}
