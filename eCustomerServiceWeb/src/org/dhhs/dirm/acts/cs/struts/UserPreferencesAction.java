

package org.dhhs.dirm.acts.cs.struts;

import java.util.Locale;

import javax.naming.NamingException;
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
import org.apache.struts.util.MessageResources;
import org.dhhs.dirm.acts.cs.Constants;
import org.dhhs.dirm.acts.cs.ManageServletStack;
import org.dhhs.dirm.acts.cs.beans.UserEntityBean;
import org.dhhs.dirm.acts.cs.ejb.CSUserLocal;
import org.dhhs.dirm.acts.cs.ejb.CSUserLocalHome;
import org.dhhs.dirm.acts.cs.formbeans.PreferencesFormBean;
import org.dhhs.dirm.acts.cs.helpers.HomeHelper;
import org.dhhs.dirm.acts.cs.util.ApplicationException;
import org.dhhs.dirm.acts.cs.util.ErrorDescriptor;

/**
 * UserPreferencesAction.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Nov 13, 2003 4:24:15 PM
 * 
 * @author rkodumagulla
 *
 */
public class UserPreferencesAction extends DispatchAction
{

	private final static Logger log = Logger.getLogger(UserPreferencesAction.class);

	/**
	 * Constructor for UserPreferencesAction.
	 */
	public UserPreferencesAction()
	{
		super();
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

			CSUserLocal user = null;

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
				form = new PreferencesFormBean();
				if ("request".equals(mapping.getScope()))
					request.setAttribute(mapping.getAttribute(), form);
				else
				{
					session.setAttribute(mapping.getAttribute(), form);
				}
			}

			PreferencesFormBean preferencesFormBean = (PreferencesFormBean) form;

			request.setAttribute("formMode", "2");

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				Object obj = HomeHelper.singleton().getHome("ecsts.CSUserLocalHome");
				CSUserLocalHome userLocalHome = (CSUserLocalHome) obj;
				user = userLocalHome.findByPrimaryKey(idWorker);
				UserEntityBean userEntityBean = user.getUserEntityBean();

				preferencesFormBean.setIdWorker(idWorker);
				preferencesFormBean.setAgent(userEntityBean.isByAgent());
				preferencesFormBean.setNbControl(userEntityBean.isByControlNbr());
				preferencesFormBean.setNmCounty(userEntityBean.isByCounty());
				preferencesFormBean.setCustodial(userEntityBean.isByCP());
				preferencesFormBean.setWriter(userEntityBean.isByCustomer());
				preferencesFormBean.setDtCompleted(userEntityBean.isByDtCompleted());
				preferencesFormBean.setDtDue(userEntityBean.isByDtDue());
				preferencesFormBean.setDtReceived(userEntityBean.isByDtReceived());
				preferencesFormBean.setIdEmail(userEntityBean.isByEmail());
				preferencesFormBean.setIdPart(userEntityBean.isByIdPart());
				preferencesFormBean.setNbCase(userEntityBean.isByNbCase());
				preferencesFormBean.setNbDocket(userEntityBean.isByNbDkt());
				preferencesFormBean.setNbSSN(userEntityBean.isByNbSSN());
				preferencesFormBean.setNonCustodial(userEntityBean.isByNCP());
				preferencesFormBean.setCdFormType(userEntityBean.isByReferralType());
				preferencesFormBean.setSource1(userEntityBean.isBySrc1());
				preferencesFormBean.setSource2(userEntityBean.isBySrc2());
				preferencesFormBean.setSource3(userEntityBean.isBySrc3());
				preferencesFormBean.setSource4(userEntityBean.isBySrc4());
			} catch (javax.ejb.ObjectNotFoundException onfe)
			{ // findByPrimaryKey returned not found exception
				log.info("User Preferences Failed. User ID not found");
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.logon.invalid"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (javax.ejb.FinderException fe)
			{
				log.error("User Preferences Failed. Database Exception resulted from EJB");

				new ApplicationException(fe.getMessage(), fe, new ErrorDescriptor("UserPreferencesAction", "view"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (NamingException ne)
			{
				log.fatal("User Preferences Failed. EJB Not Found");

				new ApplicationException(ne.getMessage(), ne, new ErrorDescriptor("UserPreferencesAction", "view"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			saveToken(request);

			/**
			 * Logic for return functionality
			 */
			new ManageServletStack().addToStack(request, session);

			return (mapping.findForward(Constants.SUCCESS));
		} catch (Exception e)
		{
			log.error("User Preferences Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("UserPreferencesAction", "view"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			log.debug("Supposed to go to input page.. but dosen't");
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

			CSUserLocal user = null;

			String idWorker = null;
			idWorker = (String) request.getParameter("idWorker");

			if (idWorker == null)
			{ // Go to the failure page
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("manageUser.error.workerRequired"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}
			form = new PreferencesFormBean();

			if ("request".equals(mapping.getScope()))
				request.setAttribute(mapping.getAttribute(), form);
			else
			{
				session.setAttribute(mapping.getAttribute(), form);
			}

			PreferencesFormBean preferencesFormBean = (PreferencesFormBean) form;

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				Object obj = HomeHelper.singleton().getHome("ecsts.CSUserLocalHome");
				CSUserLocalHome userLocalHome = (CSUserLocalHome) obj;
				user = userLocalHome.findByPrimaryKey(idWorker);
				UserEntityBean userEntityBean = user.getUserEntityBean();

				preferencesFormBean.setIdWorker(idWorker);
				preferencesFormBean.setAgent(userEntityBean.isByAgent());
				preferencesFormBean.setNbControl(userEntityBean.isByControlNbr());
				preferencesFormBean.setNmCounty(userEntityBean.isByCounty());
				preferencesFormBean.setCustodial(userEntityBean.isByCP());
				preferencesFormBean.setWriter(userEntityBean.isByCustomer());
				preferencesFormBean.setDtCompleted(userEntityBean.isByDtCompleted());
				preferencesFormBean.setDtDue(userEntityBean.isByDtDue());
				preferencesFormBean.setDtReceived(userEntityBean.isByDtReceived());
				preferencesFormBean.setIdEmail(userEntityBean.isByEmail());
				preferencesFormBean.setIdPart(userEntityBean.isByIdPart());
				preferencesFormBean.setNbCase(userEntityBean.isByNbCase());
				preferencesFormBean.setNbDocket(userEntityBean.isByNbDkt());
				preferencesFormBean.setNbSSN(userEntityBean.isByNbSSN());
				preferencesFormBean.setNonCustodial(userEntityBean.isByNCP());
				preferencesFormBean.setCdFormType(userEntityBean.isByReferralType());
				preferencesFormBean.setSource1(userEntityBean.isBySrc1());
				preferencesFormBean.setSource2(userEntityBean.isBySrc2());
				preferencesFormBean.setSource3(userEntityBean.isBySrc3());
				preferencesFormBean.setSource4(userEntityBean.isBySrc4());
			} catch (javax.ejb.ObjectNotFoundException onfe)
			{ // findByPrimaryKey returned not found exception
				log.info("User Preferences Failed. User ID not found");
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.logon.invalid"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (javax.ejb.FinderException fe)
			{
				log.error("User Preferences Failed. Database Exception resulted from EJB");

				new ApplicationException(fe.getMessage(), fe, new ErrorDescriptor("UserPreferencesAction", "edit"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (NamingException ne)
			{
				log.fatal("User Preferences Failed. EJB Not Found");

				new ApplicationException(ne.getMessage(), ne, new ErrorDescriptor("UserPreferencesAction", "edit"));

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
			log.error("User Preferences Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("UserPreferencesAction", "edit"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			log.debug("Supposed to go to input page.. but dosen't");
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

			CSUserLocal user = null;

			ActionErrors errors = new ActionErrors();
			org.dhhs.dirm.acts.cs.beans.UserBean loggedInUser = (org.dhhs.dirm.acts.cs.beans.UserBean) session.getAttribute(Constants.USERBEAN_KEY);

			PreferencesFormBean preferencesFormBean = (PreferencesFormBean) form;

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				Object obj = HomeHelper.singleton().getHome("ecsts.CSUserLocalHome");
				CSUserLocalHome userLocalHome = (CSUserLocalHome) obj;
				user = userLocalHome.findByPrimaryKey(preferencesFormBean.getIdWorker());
				UserEntityBean userEntityBean = user.getUserEntityBean();

				userEntityBean.setByAgent(preferencesFormBean.isAgent());
				userEntityBean.setByControlNbr(preferencesFormBean.isNbControl());
				userEntityBean.setByCounty(preferencesFormBean.isNmCounty());
				userEntityBean.setByCP(preferencesFormBean.isCustodial());
				userEntityBean.setByCustomer(preferencesFormBean.isWriter());
				userEntityBean.setByDtCompleted(preferencesFormBean.isDtCompleted());
				userEntityBean.setByDtDue(preferencesFormBean.isDtDue());
				userEntityBean.setByDtReceived(preferencesFormBean.isDtReceived());
				userEntityBean.setByEmail(preferencesFormBean.isIdEmail());
				userEntityBean.setByIdPart(preferencesFormBean.isIdPart());
				userEntityBean.setByNbCase(preferencesFormBean.isNbCase());
				userEntityBean.setByNbDkt(preferencesFormBean.isNbDocket());
				userEntityBean.setByNbSSN(preferencesFormBean.isNbSSN());
				userEntityBean.setByNCP(preferencesFormBean.isNonCustodial());
				userEntityBean.setByReferralType(preferencesFormBean.isCdFormType());
				userEntityBean.setBySrc1(preferencesFormBean.isSource1());
				userEntityBean.setBySrc2(preferencesFormBean.isSource2());
				userEntityBean.setBySrc3(preferencesFormBean.isSource3());
				userEntityBean.setBySrc4(preferencesFormBean.isSource4());
				userEntityBean.setIdWrkrUpdate(loggedInUser.getIdWorker());
				user.setUserEntityBean(userEntityBean);

				loggedInUser.setByAgent(preferencesFormBean.isAgent());
				loggedInUser.setByControlNbr(preferencesFormBean.isNbControl());
				loggedInUser.setByCounty(preferencesFormBean.isNmCounty());
				loggedInUser.setByCP(preferencesFormBean.isCustodial());
				loggedInUser.setByCustomer(preferencesFormBean.isWriter());
				loggedInUser.setByDtCompleted(preferencesFormBean.isDtCompleted());
				loggedInUser.setByDtDue(preferencesFormBean.isDtDue());
				loggedInUser.setByDtReceived(preferencesFormBean.isDtReceived());
				loggedInUser.setByEmail(preferencesFormBean.isIdEmail());
				loggedInUser.setByIdPart(preferencesFormBean.isIdPart());
				loggedInUser.setByNbCase(preferencesFormBean.isNbCase());
				loggedInUser.setByNbDkt(preferencesFormBean.isNbDocket());
				loggedInUser.setByNbSSN(preferencesFormBean.isNbSSN());
				loggedInUser.setByNCP(preferencesFormBean.isNonCustodial());
				loggedInUser.setByReferralType(preferencesFormBean.isCdFormType());
				loggedInUser.setBySrc1(preferencesFormBean.isSource1());
				loggedInUser.setBySrc2(preferencesFormBean.isSource2());
				loggedInUser.setBySrc3(preferencesFormBean.isSource3());
				loggedInUser.setBySrc4(preferencesFormBean.isSource4());
			} catch (javax.ejb.ObjectNotFoundException onfe)
			{ // findByPrimaryKey returned not found exception
				log.info("User Preferences Failed. User ID not found");
				errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.logon.invalid"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (javax.ejb.FinderException fe)
			{
				log.error("User Preferences Failed. Database Exception resulted from EJB");

				new ApplicationException(fe.getMessage(), fe, new ErrorDescriptor("UserPreferencesAction", "save"));

				errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (NamingException ne)
			{
				log.fatal("User Preferences Failed. EJB Not Found");

				new ApplicationException(ne.getMessage(), ne, new ErrorDescriptor("UserPreferencesAction", "save"));

				errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			saveToken(request);

			request.setAttribute("formMode", "2");

			return (mapping.findForward(Constants.SUCCESS));
		} catch (Exception e)
		{
			log.error("User Preferences Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("UserPreferencesAction", "save"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			request.setAttribute("formMode", "1");

			return (new ActionForward(mapping.getInput()));
		}
	}
}
