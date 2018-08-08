

package org.dhhs.dirm.acts.cs.struts;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dhhs.dirm.acts.cs.Constants;
import org.dhhs.dirm.acts.cs.beans.UserBean;
import org.dhhs.dirm.acts.cs.beans.UserEntityBean;
import org.dhhs.dirm.acts.cs.ejb.CSProcessorLocalHome;
import org.dhhs.dirm.acts.cs.ejb.CSUserLocal;
import org.dhhs.dirm.acts.cs.ejb.CSUserLocalHome;
import org.dhhs.dirm.acts.cs.formbeans.UserSearchForm;
import org.dhhs.dirm.acts.cs.helpers.HomeHelper;
import org.dhhs.dirm.acts.cs.util.ApplicationException;
import org.dhhs.dirm.acts.cs.util.ErrorDescriptor;

/**
 * UserSearchAction.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Aug 15, 2003 12:29:26 PM
 * 
 * @author Rkodumagulla
 *
 */
public class UserSearchAction extends Action
{

	private final static Logger log = Logger.getLogger(UserSearchAction.class);

	private org.dhhs.dirm.acts.cs.beans.UserBean createUserBean(CSUserLocal user)
	{
		org.dhhs.dirm.acts.cs.beans.UserBean userBean = new org.dhhs.dirm.acts.cs.beans.UserBean();
		UserEntityBean userEntityBean = user.getUserEntityBean();
		userBean.setNmWorker(userEntityBean.getNmWrkr());
		userBean.setCdAccptWrkld(userEntityBean.getCdAccptWrkld());
		userBean.setIdProfile(userEntityBean.getIdProfile());
		userBean.setIdWorker(userEntityBean.getIdWrkr());
		userBean.setNbrOutstanding(userEntityBean.getNbOutstanding());
		userBean.setNbrCompleted(userEntityBean.getNbCompleted());
		userBean.setNbrApproval(userEntityBean.getNbApproval());

		return userBean;
	}

	/**
	 * @see org.apache.struts.action.Action#execute(ActionMapping, ActionForm,
	 *      HttpServletRequest, HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{

		try
		{

			org.dhhs.dirm.acts.cs.beans.UserBean userBean = null;

			String firstName = ((UserSearchForm) form).getFirstname().trim();
			if (firstName.length() == 0)
			{
				firstName = null;
			}

			String lastName = ((UserSearchForm) form).getLastname().trim();
			if (lastName.length() == 0)
			{
				lastName = null;
			}

			String workerId = ((UserSearchForm) form).getWorkerid().trim();
			if (workerId.length() == 0)
			{
				workerId = null;
			}

			String profileId = ((UserSearchForm) form).getProfileid().trim();
			if (profileId.length() == 0)
			{
				profileId = null;
			}

			Vector users = new Vector();

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				Object obj = HomeHelper.singleton().getHome("ecsts.CSUserLocalHome");
				CSUserLocalHome userLocalHome = (CSUserLocalHome) obj;

				if (workerId != null)
				{
					CSUserLocal user = userLocalHome.findByPrimaryKey(workerId);
					users.addElement(createUserBean(user));
					log.debug("Successfully Found CSUserLocal EJB using worker id ");
				} else if (profileId != null)
				{
					Collection c = userLocalHome.findUsersForProfile(profileId);
					Iterator i = c.iterator();
					while (i.hasNext())
					{
						CSUserLocal user = (CSUserLocal) i.next();
						users.addElement(createUserBean(user));
						log.debug("Successfully Found CSUserLocal EJB using profile id");
					}
				} else if (lastName != null)
				{
					if (firstName != null)
					{
						Collection c = userLocalHome.findUsersByName(lastName, firstName);
						Iterator i = c.iterator();
						while (i.hasNext())
						{
							CSUserLocal user = (CSUserLocal) i.next();
							users.addElement(createUserBean(user));
							log.debug("Successfully Found CSUserLocal EJB using first name & last name");
						}
					} else
					{
						Collection c = userLocalHome.findUserByLastName(lastName + "%");
						Iterator i = c.iterator();
						while (i.hasNext())
						{
							CSUserLocal user = (CSUserLocal) i.next();
							users.addElement(createUserBean(user));
							log.debug("Successfully Found CSUserLocal EJB using last name");
						}
					}
				} else
				{
					Collection c = userLocalHome.findAllUsers();
					Iterator i = c.iterator();
					while (i.hasNext())
					{
						CSUserLocal user = (CSUserLocal) i.next();
						users.addElement(createUserBean(user));
						log.debug("Successfully Found CSUserLocal EJB using no filters");
					}
				}

				obj = HomeHelper.singleton().getHome("ecsts.CSProcessorLocalHome");
				CSProcessorLocalHome processorLocalHome = (CSProcessorLocalHome) obj;

				/*
				 * 07/29/04 - RK Fixed code so that all the agents display the
				 * exact count of Tasks pending approval, completed and
				 * outstanding
				 */

				for (int i = 0; i < users.size(); i++)
				{
					UserBean ub = (UserBean) users.elementAt(i);
					ub.setNbrApproval(processorLocalHome.countApproval(ub.getIdWorker()));
					ub.setNbrCompleted(processorLocalHome.countCompleted(ub.getIdWorker()));
					ub.setNbrOutstanding(processorLocalHome.countOutstanding(ub.getIdWorker()));
				}

			} catch (javax.ejb.ObjectNotFoundException onfe)
			{ // finder methods returned no records
				log.info("User Search Action Failed. Record(s) not found based on search");
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.notfound"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (javax.ejb.FinderException fe)
			{
				log.error("User Search Action Failed. Database Exception resulted from EJB");

				new ApplicationException(fe.getMessage(), fe, new ErrorDescriptor("UserSearchAction", "execute"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (NamingException ne)
			{
				log.fatal("User Search Action Failed. EJB Not Found");

				new ApplicationException(ne.getMessage(), ne, new ErrorDescriptor("UserSearchAction", "execute"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			request.setAttribute(Constants.ACTSWORKERSLIST_KEY, users);

			HttpSession session = request.getSession();

			// Return success
			return (mapping.findForward(Constants.SUCCESS));
		} catch (Exception e)
		{
			log.error("User Search Action Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("UserSearchAction", "execute"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			return (new ActionForward(mapping.getInput()));
		}
	}
}
