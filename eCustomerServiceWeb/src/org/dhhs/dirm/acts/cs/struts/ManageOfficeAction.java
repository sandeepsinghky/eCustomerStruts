

package org.dhhs.dirm.acts.cs.struts;

import java.util.Locale;
import java.util.Vector;

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
import org.dhhs.dirm.acts.cs.businesslogic.BusinessLogicException;
import org.dhhs.dirm.acts.cs.businesslogic.ReferralSourceManager;
import org.dhhs.dirm.acts.cs.formbeans.ReferralOffice;
import org.dhhs.dirm.acts.cs.util.ApplicationException;
import org.dhhs.dirm.acts.cs.util.ErrorDescriptor;

/**
 * ManageOfficeAction.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Jan 28, 2004 2:02:15 PM
 * 
 * @author rkodumagulla
 *
 */

public class ManageOfficeAction extends DispatchAction
{

	private static final Logger log = Logger.getLogger(ManageOfficeAction.class);

	/**
	 * Constructor for ManageOfficeAction.
	 */
	public ManageOfficeAction()
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

			form = new ReferralOffice();

			request.setAttribute("formMode", "0");

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

			return (mapping.findForward(Constants.SUCCESS));
		} catch (Exception e)
		{
			log.error("Manage Office Action Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageOfficeAction", "create"));

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

			ReferralOffice office = (ReferralOffice) form;

			ActionErrors errors = new ActionErrors();
			request.setAttribute("formMode", "0");

			org.dhhs.dirm.acts.cs.beans.UserBean loggedInUser = (org.dhhs.dirm.acts.cs.beans.UserBean) session.getAttribute(Constants.USERBEAN_KEY);

			ReferralOffice ro = (ReferralOffice) form;
			ro.setIdWrkrCreate(loggedInUser.getIdWorker());
			ro.setIdWrkrUpdate(loggedInUser.getIdWorker());

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				ReferralSourceManager manager = ReferralSourceManager.getInstance();
				if (manager.getOfficeByName(((ReferralOffice) form).getNmOffice().toUpperCase()))
				{
					errors = new ActionErrors();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.create.duplicate", "referral office", ((ReferralOffice) form).getNmOffice()));
					saveErrors(request, errors);
					return (new ActionForward(mapping.getInput()));
				}
				manager.createOffice((ReferralOffice) form);
			} catch (BusinessLogicException ble)
			{
				log.error("Manage Referral Office Action Failed. Database Exception non EJB");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageReferralAction", "store"));

				errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}
			saveToken(request);

			return mapping.findForward("store");
		} catch (Exception e)
		{
			log.error("Manage Office Action Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageOfficeAction", "store"));

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

			if (form == null)
			{
				form = new ReferralOffice();
				if ("request".equals(mapping.getScope()))
					request.setAttribute(mapping.getAttribute(), form);
				else
				{
					session.setAttribute(mapping.getAttribute(), form);
				}
			}

			String key = (String) request.getParameter("key");

			request.setAttribute("formMode", "1");

			if (key == null)
			{

				key = ((ReferralOffice) form).getNbSeq();

				if (key == null || key.equals(""))
				{
					ActionErrors errors = new ActionErrors();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.generic", "Invalid key passed"));
					saveErrors(request, errors);
					return (new ActionForward(mapping.getInput()));
				}
			}

			ActionErrors errors = new ActionErrors();

			ReferralOffice ro = (ReferralOffice) form;

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				ReferralSourceManager manager = ReferralSourceManager.getInstance();
				ReferralOffice refo = manager.getOffice(Short.parseShort(key));

				ro.setIdWrkrCreate(refo.getIdWrkrCreate());
				ro.setIdWrkrUpdate(refo.getIdWrkrUpdate());
				ro.setNbSeq(refo.getNbSeq());
				ro.setNmOffice(refo.getNmOffice());
				ro.setNmOfficeDesc(refo.getNmOfficeDesc());
				ro.setTsCreate(refo.getTsCreate());
				ro.setTsUpdate(refo.getTsUpdate());
				ro.setSources(refo.getSources());
			} catch (BusinessLogicException ble)
			{
				log.error("Manage Referral Office Action Failed. Database Exception non EJB");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageReferralAction", "edit"));

				errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
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
			log.error("Manage Office Action Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageOfficeAction", "edit"));

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

			ReferralOffice ro = (ReferralOffice) form;

			ActionErrors errors = new ActionErrors();

			/**
			 * Invoke the Business Process to get the Referral Sources Again as
			 * they are not stored by the struts framework
			 */
			try
			{
				ReferralSourceManager manager = ReferralSourceManager.getInstance();
				ReferralOffice refo = manager.getOffice(Short.parseShort(ro.getNbSeq()));
				ro.setSources(refo.getSources());
			} catch (BusinessLogicException ble)
			{
				log.error("Manage Referral Office Action Failed. Database Exception non EJB");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageReferralAction", "save"));

				errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			if (!errors.isEmpty())
			{
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			org.dhhs.dirm.acts.cs.beans.UserBean loggedInUser = (org.dhhs.dirm.acts.cs.beans.UserBean) session.getAttribute(Constants.USERBEAN_KEY);

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				ReferralSourceManager manager = ReferralSourceManager.getInstance();
				manager.updateOffice(ro, loggedInUser.getIdWorker());
			} catch (BusinessLogicException ble)
			{
				log.error("Manage Referral Office Action Failed. Database Exception non EJB");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageReferralAction", "save"));

				errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}
			return load(mapping, form, request, response);
		} catch (Exception e)
		{
			log.error("Manage Office Action Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageOfficeAction", "save"));

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

			String key = (String) request.getParameter("key");

			if (key == null)
			{ // Go to the failure page
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.generic", "Invalid key passed </p>"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			if (form == null)
			{
				form = new ReferralOffice();
				if ("request".equals(mapping.getScope()))
					request.setAttribute(mapping.getAttribute(), form);
				else
				{
					session.setAttribute(mapping.getAttribute(), form);
				}
			}

			ActionErrors errors = new ActionErrors();

			ReferralOffice ro = (ReferralOffice) form;

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				ReferralSourceManager manager = ReferralSourceManager.getInstance();
				ReferralOffice refo = manager.getOffice(Short.parseShort(key));

				ro.setIdWrkrCreate(refo.getIdWrkrCreate());
				ro.setIdWrkrUpdate(refo.getIdWrkrUpdate());
				ro.setNbSeq(refo.getNbSeq());
				ro.setNmOffice(refo.getNmOffice());
				ro.setNmOfficeDesc(refo.getNmOfficeDesc());
				ro.setTsCreate(refo.getTsCreate());
				ro.setTsUpdate(refo.getTsUpdate());
				ro.setSources(refo.getSources());
			} catch (BusinessLogicException ble)
			{
				log.error("Manage Referral Office Action Failed. Database Exception non EJB");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageReferralAction", "view"));

				errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}
			saveToken(request);

			request.setAttribute("formMode", "2");
			/**
			 * Logic for return functionality
			 */
			new ManageServletStack().addToStack(request, session);

			return (mapping.findForward(Constants.SUCCESS));
		} catch (Exception e)
		{
			log.error("Manage Office Action Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageOfficeAction", "view"));

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
	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		try
		{
			Locale locale = getLocale(request);
			MessageResources messages = getResources(request);
			HttpSession session = request.getSession();

			ActionErrors errors = new ActionErrors();

			resetToken(request);

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				ReferralSourceManager manager = ReferralSourceManager.getInstance();
				Vector offices = manager.getOffices();

				request.setAttribute(Constants.OFFICELIST, offices);

			} catch (BusinessLogicException ble)
			{
				log.error("Manage Referral Office Action Failed. Database Exception non EJB");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageReferralAction", "load"));

				errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}
			saveToken(request);

			/**
			 * Logic for return functionality
			 */
			new ManageServletStack().addToStack(request, session);

			return mapping.findForward("load");
		} catch (Exception e)
		{
			log.error("Manage Office Action Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageOfficeAction", "load"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			return (new ActionForward(mapping.getInput()));
		}
	}
}
