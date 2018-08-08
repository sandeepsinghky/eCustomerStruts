

package org.dhhs.dirm.acts.cs.struts;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dhhs.dirm.acts.cs.Constants;
import org.dhhs.dirm.acts.cs.businesslogic.BusinessLogicException;
import org.dhhs.dirm.acts.cs.businesslogic.UserProfileManager;
import org.dhhs.dirm.acts.cs.formbeans.ProfileForm;
import org.dhhs.dirm.acts.cs.util.ApplicationException;
import org.dhhs.dirm.acts.cs.util.ErrorDescriptor;

/**
 * ActsWorkerSearchAction.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Aug 26, 2003 2:21:46 PM
 * 
 * @author rkodumagulla
 *
 */
public class ProfileSearchAction extends Action
{

	private final static Logger log = Logger.getLogger(ProfileSearchAction.class);

	/**
	 * @see org.apache.struts.action.Action#execute(ActionMapping, ActionForm,
	 *      HttpServletRequest, HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		try
		{
			String idProfile = ((ProfileForm) form).getIdProfile();

			if (idProfile != null)
			{
				if (idProfile.length() == 0)
				{
					idProfile = "";

				}
			} else
			{
				idProfile = "";
			}

			Vector profiles = new Vector();

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				if (idProfile != null)
				{

					UserProfileManager manager = UserProfileManager.getInstance();
					if (idProfile.length() > 0 && idProfile.length() < 4)
					{
						idProfile += "%";
						profiles = manager.getUserProfiles(idProfile);
					} else if (idProfile.length() == 0)
					{
						profiles = manager.getAllUserProfiles();
					} else
					{
						profiles.addElement(manager.getProfile(idProfile));
					}
				}
			} catch (BusinessLogicException ble)
			{
				log.error("Profile Search Action Failed. Database Exception non EJB");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ProfileSearchAction", "execute"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			request.setAttribute(Constants.PROFILELIST, profiles);

			// Return success
			return (mapping.findForward(Constants.SUCCESS));
		} catch (Exception e)
		{
			log.error("Profile Search Action Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ProfileSearchAction", "execute"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			return (new ActionForward(mapping.getInput()));
		}
	}

}
