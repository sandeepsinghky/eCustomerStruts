

package org.dhhs.dirm.acts.cs.struts;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import javax.ejb.CreateException;
import javax.naming.NamingException;
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
import org.dhhs.dirm.acts.cs.NavigationStack;
import org.dhhs.dirm.acts.cs.beans.ActsWorkerEntityBean;
import org.dhhs.dirm.acts.cs.beans.UserEntityBean;
import org.dhhs.dirm.acts.cs.ejb.CSActsWorkerLocal;
import org.dhhs.dirm.acts.cs.ejb.CSActsWorkerLocalHome;
import org.dhhs.dirm.acts.cs.ejb.CSProcessorLocalHome;
import org.dhhs.dirm.acts.cs.ejb.CSUserLocal;
import org.dhhs.dirm.acts.cs.ejb.CSUserLocalHome;
import org.dhhs.dirm.acts.cs.formbeans.UserForm;
import org.dhhs.dirm.acts.cs.helpers.HomeHelper;
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
public class ManageUserAction extends DispatchAction
{

	private final static Logger log = Logger.getLogger(ManageUserAction.class);

	/**
	 * Constructor for ManageUserAction.
	 */
	public ManageUserAction()
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

			String idWorker = (String) request.getParameter("idWorker");

			log.debug("create method invoked" + request.getParameter("reqCode"));

			if (idWorker == null)
			{ // Go to the failure page
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("manageUser.error.workerRequired"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			UserForm userForm = (UserForm) form;

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				Object obj = HomeHelper.singleton().getHome("ecsts.CSUserLocalHome");
				CSUserLocalHome userLocalHome = (CSUserLocalHome) obj;
				CSUserLocal user = userLocalHome.findByPrimaryKey(idWorker);

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("manageUser.error.workerRegistered"));
				saveErrors(request, errors);

				NavigationStack stack = (NavigationStack) session.getAttribute(Constants.STACK);

				String target = stack.getPreviousStackItem();

				return mapping.findForward(target);
			} catch (javax.ejb.ObjectNotFoundException onfe)
			{ // finder methods returned no records
				log.info("Manage User Action. ACTS Worker: " + idWorker + " not registered. OK to register");
			} catch (javax.ejb.FinderException fe)
			{
				log.fatal("Manage User Action Failed. Database Exception resulted from EJB");

				new ApplicationException(fe.getMessage(), fe, new ErrorDescriptor("ManageUserAction", "create"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (NamingException ne)
			{
				log.fatal("Manage User Action Failed. EJB Not Found");

				new ApplicationException(ne.getMessage(), ne, new ErrorDescriptor("ManageUserAction", "create"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			try
			{
				Object obj = HomeHelper.singleton().getHome("ecsts.CSActsWorkerLocalHome");
				CSActsWorkerLocalHome workerLocalHome = (CSActsWorkerLocalHome) obj;
				CSActsWorkerLocal worker = workerLocalHome.findByPrimaryKey(idWorker);
				ActsWorkerEntityBean actsWorkerEntityBean = worker.getActsWorkerEntityBean();

				userForm.setApprovalRequired(true);
				userForm.setCdAccptWrkld(true);
				userForm.setCdResetPassword(true);
				userForm.setIdEmail(actsWorkerEntityBean.getIdEmail());
				userForm.setNbLunchStart(actsWorkerEntityBean.getTmLunchStart());
				userForm.setNbLunchEnd(actsWorkerEntityBean.getTmLunchEnd());
				userForm.setNbWorkHourStart(actsWorkerEntityBean.getTmWorkStart());
				userForm.setNbWorkHourEnd(actsWorkerEntityBean.getTmWorkEnd());
				userForm.setNbPhone(actsWorkerEntityBean.getNbTelWorker());
				userForm.setNmWorker(actsWorkerEntityBean.getNmWrkr());
			} catch (javax.ejb.ObjectNotFoundException onfe1)
			{
				log.info("Acts Worker not found in worker table " + idWorker);

				new ApplicationException(onfe1.getMessage(), onfe1, new ErrorDescriptor("ManageUserAction", "create"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (javax.ejb.FinderException fe)
			{
				log.fatal("Manage User Action Failed. Database Exception resulted from EJB");

				new ApplicationException(fe.getMessage(), fe, new ErrorDescriptor("ManageUserAction", "create"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (NamingException ne)
			{
				log.fatal("Manage User Action Failed. EJB Not Found");

				new ApplicationException(ne.getMessage(), ne, new ErrorDescriptor("ManageUserAction", "create"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			request.setAttribute("formMode", "0");

			if ("request".equals(mapping.getScope()))
				request.setAttribute(mapping.getAttribute(), form);
			else
			{
				session.setAttribute(mapping.getAttribute(), form);
			}

			((UserForm) form).setAction("Create");
			((UserForm) form).setIdWorker(idWorker);

			saveToken(request);

			/**
			 * Logic for return functionality
			 */
			new ManageServletStack().addToStack(request, session);

			return (mapping.findForward(Constants.SUCCESS));
		} catch (Exception e)
		{
			log.error("Manage User Action Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageUserAction", "create"));

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

			String idWorker = null;
			idWorker = (String) request.getParameter("idWorker");

			if (idWorker == null)
			{ // Go to the failure page
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("manageUser.error.workerRequired"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			// Populate the user registration form
			if (form == null)
			{
				form = new UserForm();
				if ("request".equals(mapping.getScope()))
					request.setAttribute(mapping.getAttribute(), form);
				else
				{
					session.setAttribute(mapping.getAttribute(), form);
				}
			}

			UserForm userForm = (UserForm) form;
			userForm.setAction("Edit");

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				Object obj = HomeHelper.singleton().getHome("ecsts.CSUserLocalHome");
				CSUserLocalHome userLocalHome = (CSUserLocalHome) obj;
				CSUserLocal user = userLocalHome.findByPrimaryKey(idWorker);
				UserEntityBean userEntityBean = user.getUserEntityBean();

				userForm.setIdWorker(userEntityBean.getIdWrkr());
				userForm.setCdAccptWrkld(userEntityBean.getCdAccptWrkld().equals("Y") ? true : false);
				userForm.setApprovalRequired(userEntityBean.getCdApprovalRequired().equals("Y") ? true : false);
				userForm.setCdResetPassword(userEntityBean.getCdPasswordStatus().equals("I") ? true : false);
				userForm.setIdProfile(userEntityBean.getIdProfile());
				userForm.setNmWorker(userEntityBean.getNmWrkr());

				Object obj1 = HomeHelper.singleton().getHome("ecsts.CSActsWorkerLocalHome");
				CSActsWorkerLocalHome actsWorkerLocalHome = (CSActsWorkerLocalHome) obj1;
				CSActsWorkerLocal worker = actsWorkerLocalHome.findByPrimaryKey(idWorker);
				ActsWorkerEntityBean actsWorkerEntityBean = worker.getActsWorkerEntityBean();

				userForm.setIdEmail(actsWorkerEntityBean.getIdEmail());
				userForm.setNbLunchStart(actsWorkerEntityBean.getTmLunchStart());
				userForm.setNbLunchEnd(actsWorkerEntityBean.getTmLunchEnd());
				userForm.setNbWorkHourEnd(actsWorkerEntityBean.getTmWorkEnd());
				userForm.setNbWorkHourStart(actsWorkerEntityBean.getTmWorkStart());
				userForm.setNbPhone(actsWorkerEntityBean.getNbTelWorker());

				obj = HomeHelper.singleton().getHome("ecsts.CSProcessorLocalHome");
				CSProcessorLocalHome processorLocalHome = (CSProcessorLocalHome) obj;

				/*
				 * 07/29/04 - RK Fixed code so that all the agents display the
				 * exact count of Tasks pending approval, completed and
				 * outstanding
				 */
				userForm.setNbApproval(processorLocalHome.countApproval(idWorker));
				userForm.setNbCompleted(processorLocalHome.countCompleted(idWorker));
				userForm.setNbOutstanding(processorLocalHome.countOutstanding(idWorker));

			} catch (javax.ejb.ObjectNotFoundException onfe)
			{ // finder methods returned no records
				log.fatal("Manage User Action Failed. Agent not found");

				new ApplicationException(onfe.getMessage(), onfe, new ErrorDescriptor("ManageUserAction", "edit"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.notfound"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (javax.ejb.FinderException fe)
			{
				log.fatal("Manage User Action Failed. Database Exception resulted from EJB");

				new ApplicationException(fe.getMessage(), fe, new ErrorDescriptor("ManageUserAction", "edit"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (NamingException ne)
			{
				log.fatal("Manage User Action Failed. EJB Not Found");

				new ApplicationException(ne.getMessage(), ne, new ErrorDescriptor("ManageUserAction", "edit"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}
			saveToken(request);

			request.setAttribute("formMode", "1");

			/**
			 * Logic for return functionality
			 */
			new ManageServletStack().addToStack(request, session);

			return (mapping.findForward(Constants.SUCCESS));

		} catch (Exception e)
		{
			log.error("Manage User Action Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageUserAction", "edit"));

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
			// Extract attributes we will need
			Locale locale = getLocale(request);
			MessageResources messages = getResources(request);
			HttpSession session = request.getSession();

			UserForm userForm = (UserForm) form;

			log.debug("Accept Workload? : " + userForm.getCdAccptWrkld());
			log.debug("Profile ID: " + userForm.getIdProfile());
			log.debug("Password Reset? : " + userForm.isCdResetPassword());

			org.dhhs.dirm.acts.cs.beans.UserBean loggedInUser = (org.dhhs.dirm.acts.cs.beans.UserBean) session.getAttribute(Constants.USERBEAN_KEY);

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				Object obj = HomeHelper.singleton().getHome("ecsts.CSUserLocalHome");
				CSUserLocalHome userLocalHome = (CSUserLocalHome) obj;

				CSUserLocal user = userLocalHome.findByPrimaryKey(userForm.getIdWorker());
				UserEntityBean userEntityBean = user.getUserEntityBean();

				userEntityBean.setCdAccptWrkld(userForm.getCdAccptWrkld() == true ? "Y" : "N");
				userEntityBean.setIdProfile(userForm.getIdProfile());
				userEntityBean.setIdWrkrUpdate(loggedInUser.getIdWorker());
				userEntityBean.setCdPasswordStatus(userForm.isCdResetPassword() == true ? "I" : "A");
				if (userForm.isCdResetPassword())
				{
					userEntityBean.setIdPassword("password");
				}
				userEntityBean.setCdApprovalRequired(userForm.isApprovalRequired() == true ? "Y" : "N");
				user.setUserEntityBean(userEntityBean);

			} catch (javax.ejb.ObjectNotFoundException onfe)
			{ // finder methods returned no records
				log.fatal("Manage User Action Failed. Agent not found");

				new ApplicationException(onfe.getMessage(), onfe, new ErrorDescriptor("ManageUserAction", "save"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.notfound"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (javax.ejb.FinderException fe)
			{
				log.fatal("Manage User Action Failed. Database Exception resulted from EJB");

				new ApplicationException(fe.getMessage(), fe, new ErrorDescriptor("ManageUserAction", "save"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (NamingException ne)
			{
				log.fatal("Manage User Action Failed. EJB Not Found");

				new ApplicationException(ne.getMessage(), ne, new ErrorDescriptor("ManageUserAction", "save"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			saveToken(request);

			request.setAttribute("formMode", "2");
			return (mapping.findForward(Constants.SUCCESS));
		} catch (Exception e)
		{
			log.error("Manage User Action Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageUserAction", "save"));

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

			// Extract attributes we will need
			Locale locale = getLocale(request);
			MessageResources messages = getResources(request);
			HttpSession session = request.getSession();

			UserForm userForm = (UserForm) form;

			org.dhhs.dirm.acts.cs.beans.UserBean loggedInUser = (org.dhhs.dirm.acts.cs.beans.UserBean) session.getAttribute(Constants.USERBEAN_KEY);

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				Object obj = HomeHelper.singleton().getHome("ecsts.CSUserLocalHome");
				CSUserLocalHome userLocalHome = (CSUserLocalHome) obj;

				CSUserLocal user = userLocalHome.create(userForm.getIdWorker(), "password", userForm.getIdProfile(), loggedInUser.getIdWorker());

				user = userLocalHome.findByPrimaryKey(userForm.getIdWorker());
				UserEntityBean userEntityBean = user.getUserEntityBean();

				userEntityBean.setCdAccptWrkld(userForm.getCdAccptWrkld() ? "Y" : "N");
				userEntityBean.setCdApprovalRequired(userForm.isApprovalRequired() ? "Y" : "N");
				user.setUserEntityBean(userEntityBean);

				/**
				 * Refresh the Application Scope variable agents
				 */
				Collection c = userLocalHome.findAllUsers();
				Iterator i = c.iterator();
				Vector users = new Vector();
				while (i.hasNext())
				{
					user = (CSUserLocal) i.next();
					users.addElement(createUserBean(user));
				}

				getServlet().getServletContext().setAttribute("agents", users);
			} catch (CreateException ce)
			{
				log.fatal("Manage User Action Create Failed. Database Exception resulted from EJB");

				new ApplicationException(ce.getMessage(), ce, new ErrorDescriptor("ManageUserAction", "store"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (NamingException ne)
			{
				log.fatal("Manage User Action Create Failed. EJB Not Found");

				new ApplicationException(ne.getMessage(), ne, new ErrorDescriptor("ManageUserAction", "store"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			request.setAttribute("formMode", "2");

			saveToken(request);

			return (mapping.findForward("createsuccess"));

		} catch (Exception e)
		{
			log.error("Manage User Action Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageUserAction", "store"));

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

			String idWorker = (String) request.getParameter("idWorker");

			if (idWorker == null)
			{ // Go to the failure page
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("manageUser.error.workerRequired"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			if (form == null)
			{
				form = new UserForm();
				if ("request".equals(mapping.getScope()))
					request.setAttribute(mapping.getAttribute(), form);
				else
				{
					session.setAttribute(mapping.getAttribute(), form);
				}
			}

			UserForm userForm = (UserForm) form;
			userForm.setAction("View");

			request.setAttribute("formMode", "2");

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				Object obj = HomeHelper.singleton().getHome("ecsts.CSUserLocalHome");
				CSUserLocalHome userLocalHome = (CSUserLocalHome) obj;
				CSUserLocal user = userLocalHome.findByPrimaryKey(idWorker);
				UserEntityBean userEntityBean = user.getUserEntityBean();

				log.debug("Successfully Found CSUserLocal EJB " + user.getIdPassword());

				userForm.setIdWorker(userEntityBean.getIdWrkr());
				userForm.setCdAccptWrkld(userEntityBean.getCdAccptWrkld().equals("Y") ? true : false);
				userForm.setApprovalRequired(userEntityBean.getCdApprovalRequired().equals("Y") ? true : false);
				userForm.setCdResetPassword(userEntityBean.getCdPasswordStatus().equals("I") ? true : false);
				userForm.setIdProfile(userEntityBean.getIdProfile());
				userForm.setNmWorker(userEntityBean.getNmWrkr());

				Object obj1 = HomeHelper.singleton().getHome("ecsts.CSActsWorkerLocalHome");
				CSActsWorkerLocalHome actsWorkerLocalHome = (CSActsWorkerLocalHome) obj1;
				CSActsWorkerLocal worker = actsWorkerLocalHome.findByPrimaryKey(idWorker);
				ActsWorkerEntityBean actsWorkerEntityBean = worker.getActsWorkerEntityBean();

				userForm.setIdEmail(actsWorkerEntityBean.getIdEmail());
				userForm.setNbLunchStart(actsWorkerEntityBean.getTmLunchStart());
				userForm.setNbLunchEnd(actsWorkerEntityBean.getTmLunchEnd());
				userForm.setNbWorkHourEnd(actsWorkerEntityBean.getTmWorkEnd());
				userForm.setNbWorkHourStart(actsWorkerEntityBean.getTmWorkStart());
				userForm.setNbPhone(actsWorkerEntityBean.getNbTelWorker());

				obj = HomeHelper.singleton().getHome("ecsts.CSProcessorLocalHome");
				CSProcessorLocalHome processorLocalHome = (CSProcessorLocalHome) obj;

				/*
				 * 07/29/04 - RK Fixed code so that all the agents display the
				 * exact count of Tasks pending approval, completed and
				 * outstanding
				 */
				userForm.setNbApproval(processorLocalHome.countApproval(idWorker));
				userForm.setNbCompleted(processorLocalHome.countCompleted(idWorker));
				userForm.setNbOutstanding(processorLocalHome.countOutstanding(idWorker));

			} catch (javax.ejb.ObjectNotFoundException onfe)
			{ // finder methods returned no records
				log.fatal("Manage User Action Failed. Agent not found");

				new ApplicationException(onfe.getMessage(), onfe, new ErrorDescriptor("ManageUserAction", "view"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("manageUser.error.workerNotFound"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (javax.ejb.FinderException fe)
			{
				log.fatal("Manage User Action Failed. Database Exception resulted from EJB");

				new ApplicationException(fe.getMessage(), fe, new ErrorDescriptor("ManageUserAction", "view"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (NamingException ne)
			{
				log.fatal("Manage User Action Failed. EJB Not Found");

				new ApplicationException(ne.getMessage(), ne, new ErrorDescriptor("ManageUserAction", "view"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			/**
			 * Logic for return functionality
			 */
			new ManageServletStack().addToStack(request, session);

			return (mapping.findForward(Constants.SUCCESS));
		} catch (Exception e)
		{
			log.error("Manage User Action Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageUserAction", "view"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			return (new ActionForward(mapping.getInput()));
		}
	}

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
	 * @see org.apache.struts.action.Action#back(ActionMapping, ActionForm,
	 *      HttpServletRequest, HttpServletResponse)
	 */
	public ActionForward back(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		HttpSession session = request.getSession();
		NavigationStack stack = (NavigationStack) session.getAttribute(Constants.STACK);

		String target = stack.getPreviousStackItem();

		return mapping.findForward(target);
	}
}
