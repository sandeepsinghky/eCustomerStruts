

package org.dhhs.dirm.acts.cs.struts;

import java.io.IOException;
import java.util.Collection;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// import log4j packages
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.dhhs.dirm.acts.cs.Constants;
import org.dhhs.dirm.acts.cs.NavigationStack;
import org.dhhs.dirm.acts.cs.beans.Profile;
import org.dhhs.dirm.acts.cs.beans.UserEntityBean;
import org.dhhs.dirm.acts.cs.beans.WorkerCountBean;
import org.dhhs.dirm.acts.cs.beans.WorkerStatsBean;
import org.dhhs.dirm.acts.cs.businesslogic.BusinessLogicException;
import org.dhhs.dirm.acts.cs.businesslogic.UserProfileManager;
import org.dhhs.dirm.acts.cs.ejb.CSProcessorLocalHome;
import org.dhhs.dirm.acts.cs.ejb.CSUserLocal;
import org.dhhs.dirm.acts.cs.ejb.CSUserLocalHome;
import org.dhhs.dirm.acts.cs.formbeans.LogonForm;
import org.dhhs.dirm.acts.cs.helpers.HomeHelper;
import org.dhhs.dirm.acts.cs.util.ApplicationException;
import org.dhhs.dirm.acts.cs.util.ErrorDescriptor;

/**
 * LogonAction.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Aug 15, 2003 12:29:26 PM
 * 
 * @author Rkodumagulla
 *
 */
public class LogonAction extends DispatchAction
{

	private final static Logger log = Logger.getLogger(LogonAction.class);

	/**
	 * @see org.apache.struts.action.Action#authenticate(ActionMapping,
	 *      ActionForm, HttpServletRequest, HttpServletResponse)
	 */
	public ActionForward authenticate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{

		try
		{

			org.dhhs.dirm.acts.cs.beans.UserBean userBean = null;

			// HttpSession session = request.getSession();
			HttpSession session = request.getSession(true);

			// Obtain username and password from web tier
			String username = ((LogonForm) form).getUsername();
			String password = ((LogonForm) form).getPassword();

			CSUserLocal user = null;

			boolean getStats = false;

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				Object obj = HomeHelper.singleton().getHome("ecsts.CSUserLocalHome");
				CSUserLocalHome userLocalHome = (CSUserLocalHome) obj;
				user = userLocalHome.findByPrimaryKey(username);
				UserEntityBean userEntityBean = user.getUserEntityBean();

				if (userEntityBean.getIdPassword().equals(password))
				{
					log.debug("Valid Credentials. User Logging In");
					getStats = true;
					userBean = new org.dhhs.dirm.acts.cs.beans.UserBean();
					userBean.setApprovalRequired(userEntityBean.getCdApprovalRequired().equals("Y") ? true : false);
					userBean.setNmWorker(userEntityBean.getNmWrkr());
					userBean.setIdWorker(username);
					userBean.setCdAccptWrkld(userEntityBean.getCdAccptWrkld());
					userBean.setIdProfile(userEntityBean.getIdProfile());
					userBean.setNbrOutstanding(userEntityBean.getNbOutstanding());
					userBean.setNbrCompleted(userEntityBean.getNbCompleted());
					userBean.setNbrApproval(userEntityBean.getNbApproval());
					userBean.setByAgent(userEntityBean.isByAgent());
					userBean.setByControlNbr(userEntityBean.isByControlNbr());
					userBean.setByCounty(userEntityBean.isByCounty());
					userBean.setByCP(userEntityBean.isByCP());
					userBean.setByCustomer(userEntityBean.isByCustomer());
					userBean.setByDtCompleted(userEntityBean.isByDtCompleted());
					userBean.setByDtDue(userEntityBean.isByDtDue());
					userBean.setByDtReceived(userEntityBean.isByDtReceived());
					userBean.setByEmail(userEntityBean.isByEmail());
					userBean.setByIdPart(userEntityBean.isByIdPart());
					userBean.setByNbCase(userEntityBean.isByNbCase());
					userBean.setByNbDkt(userEntityBean.isByNbDkt());
					userBean.setByNbSSN(userEntityBean.isByNbSSN());
					userBean.setByNCP(userEntityBean.isByNbSSN());
					userBean.setByReferralType(userEntityBean.isByReferralType());
					userBean.setBySrc1(userEntityBean.isBySrc1());
					userBean.setBySrc2(userEntityBean.isBySrc2());
					userBean.setBySrc3(userEntityBean.isBySrc3());
					userBean.setBySrc4(userEntityBean.isBySrc4());

					if (userEntityBean.getCdPasswordStatus().equals("I"))
					{
						log.debug("Password has been reset. User needs to change password");
						ActionForward forward = mapping.findForward(Constants.PASSWORD_RESET);
						StringBuffer path = new StringBuffer(forward.getPath());
						path.append("?id=" + username);

						request.setAttribute("resetPassword", "True");

						ActionErrors errors = new ActionErrors();
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("msg.manageUser.resetPassword"));
						saveErrors(request, errors);
						return new ActionForward(path.toString());
					}
				} else
				{
					log.info("User Authentication Failed. Bad Password for " + username);

					// credentials don't match
					ActionErrors errors = new ActionErrors();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.logon.invalid"));
					saveErrors(request, errors);
					// return to input page
					return (new ActionForward(mapping.getInput()));
				}

				/**
				 * Obtain the User Profile from FKKT_CSESRV_PROFILE and examine
				 * the user's security profile settings
				 */
				boolean manageAll = false;
				UserProfileManager manager = UserProfileManager.getInstance();
				if (userEntityBean.getIdProfile().length() == 4)
				{
					Profile profile = manager.getProfile(userEntityBean.getIdProfile());
					userBean.setManageAll(profile.getCdMenuItems().charAt(0) == 'T' ? true : false);
					userBean.setManageUsers(profile.getCdMenuItems().charAt(1) == 'T' ? true : false);
					userBean.setManageProfiles(profile.getCdMenuItems().charAt(2) == 'T' ? true : false);
					userBean.setManageWorkFlow(profile.getCdMenuItems().charAt(3) == 'T' ? true : false);
					userBean.setManageWorkLoad(profile.getCdMenuItems().charAt(4) == 'T' ? true : false);
					userBean.setManageApprovals(profile.getCdMenuItems().charAt(5) == 'T' ? true : false);
					userBean.setManageReferralSources(profile.getCdMenuItems().charAt(6) == 'T' ? true : false);
					userBean.setManageReports(profile.getCdMenuItems().charAt(7) == 'T' ? true : false);

				}

				if (getStats)
				{
					obj = HomeHelper.singleton().getHome("ecsts.CSProcessorLocalHome");
					CSProcessorLocalHome processorLocalHome = (CSProcessorLocalHome) obj;

					// rk 04/15/2004 use the boolean isManageAll() method
					// instead of a hard coded value
					// if (userBean.getIdProfile().equals("SSSM")) {
					System.out.println("LogonAction isManageAll "+ userBean.isManageAll() );
					if (userBean.isManageAll())
					{
				
						java.util.Vector workerStats = new java.util.Vector();

						Collection c1 = processorLocalHome.groupAll();
						for (java.util.Iterator i = c1.iterator(); i.hasNext();)
						{
							WorkerCountBean wcb = (WorkerCountBean) i.next();
							WorkerStatsBean wsb = new WorkerStatsBean();
							wsb.setIdWorker(wcb.getIdWorker());
							wsb.setNbAll(wcb.getCount());
							workerStats.addElement(wsb);
						}
						Collection c2 = processorLocalHome.groupApproval();
						for (java.util.Iterator i = c2.iterator(); i.hasNext();)
						{
							WorkerCountBean wcb = (WorkerCountBean) i.next();
							loop : for (int j = 0; j <= workerStats.size(); j++)
							{
								WorkerStatsBean wsb = (WorkerStatsBean) workerStats.elementAt(j);
								if (wsb.getIdWorker().equals(wcb.getIdWorker()))
								{
									wsb.setNbApproval(wcb.getCount());
									break loop;
								}
							}
						}
						Collection c3 = processorLocalHome.groupOutstanding();
						for (java.util.Iterator i = c3.iterator(); i.hasNext();)
						{
							WorkerCountBean wcb = (WorkerCountBean) i.next();
							loop : for (int j = 0; j <= workerStats.size(); j++)
							{
								WorkerStatsBean wsb = (WorkerStatsBean) workerStats.elementAt(j);
								if (wsb.getIdWorker().equals(wcb.getIdWorker()))
								{
									wsb.setNbOutstanding(wcb.getCount());
									break loop;
								}
							}
						}
						Collection c4 = processorLocalHome.groupCompleted();
						for (java.util.Iterator i = c4.iterator(); i.hasNext();)
						{
							WorkerCountBean wcb = (WorkerCountBean) i.next();
							loop : for (int j = 0; j <= workerStats.size(); j++)
							{
								WorkerStatsBean wsb = (WorkerStatsBean) workerStats.elementAt(j);
								if (wsb.getIdWorker().equals(wcb.getIdWorker()))
								{
									wsb.setNbCompleted(wcb.getCount());
									break loop;
								}
							}
						}

						session.setAttribute("workerStats", workerStats);
					} else
					{
						int approvalCount = processorLocalHome.countApproval(userBean.getIdWorker());
						int allCount = processorLocalHome.countAll(userBean.getIdWorker());
						int outstandingCount = processorLocalHome.countOutstanding(userBean.getIdWorker());
						int completedCount = processorLocalHome.countCompleted(userBean.getIdWorker());

						session.setAttribute("approvalCount", new Integer(approvalCount).toString());
						session.setAttribute("completedCount", new Integer(completedCount).toString());
						session.setAttribute("allCount", new Integer(allCount).toString());
						session.setAttribute("outstandingCount", new Integer(outstandingCount).toString());
					}
				}

			} catch (javax.ejb.ObjectNotFoundException onfe)
			{ // findByPrimaryKey returned not found exception
				log.info("User Authentication Failed. User ID not found");
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.logon.invalid"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (javax.ejb.FinderException fe)
			{
				log.error("User Authentication Failed. Database Exception resulted from EJB");

				new ApplicationException(fe.getMessage(), fe, new ErrorDescriptor("LogonAction", "execute"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (NamingException ne)
			{
				log.fatal("User Authentication Failed. EJB Not Found");

				new ApplicationException(ne.getMessage(), ne, new ErrorDescriptor("LogonAction", "execute"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (BusinessLogicException ble)
			{
				log.error("User Authentication Failed. Database Exception non EJB");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("LogonAction", "execute"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			session.setAttribute(Constants.USER_KEY, form);
			session.setAttribute(Constants.STACK, new NavigationStack());

			if (userBean != null)
			{
				session.setAttribute(Constants.USERBEAN_KEY, userBean);
			}

			return (mapping.findForward(Constants.SUCCESS));
		} catch (Exception e)
		{
			log.error("User Authentication Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("LogonAction", "execute"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			log.debug("Supposed to go to input page.. but dosen't");
			return (new ActionForward(mapping.getInput()));
		}
	}
}
