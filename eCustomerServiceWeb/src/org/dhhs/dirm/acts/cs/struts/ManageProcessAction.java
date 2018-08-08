

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
import org.dhhs.dirm.acts.cs.beans.ProcessType;
import org.dhhs.dirm.acts.cs.businesslogic.BusinessLogicException;
import org.dhhs.dirm.acts.cs.businesslogic.DuplicateException;
import org.dhhs.dirm.acts.cs.businesslogic.StepManager;
import org.dhhs.dirm.acts.cs.formbeans.ProcessTypeForm;
import org.dhhs.dirm.acts.cs.util.ApplicationException;
import org.dhhs.dirm.acts.cs.util.ErrorDescriptor;

/**
 * ManageProcessAction.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Nov 04, 2003 12:00:15 PM
 * 
 * @author rkodumagulla
 *
 */
public class ManageProcessAction extends DispatchAction
{

	private static final Logger log = Logger.getLogger(ManageProcessAction.class);

	/**
	 * Constructor for ManageProcessAction.
	 */
	public ManageProcessAction()
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

			form = new ProcessTypeForm();

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
			log.error("Manage Process Action Create Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageProcessAction", "create"));

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

			String step = (String) request.getParameter("step");

			if (step == null)
			{ // Go to the failure page
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("manageForm.error.typeRequired"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			if (form == null)
			{
				form = new ProcessTypeForm();

				((ProcessTypeForm) form).setStep(step);
			}

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				StepManager manager = StepManager.getInstance();
				ProcessType s = manager.getStep(step);
				((ProcessTypeForm) form).setDescription(s.getDescription());
				((ProcessTypeForm) form).setTsCreate(s.getTsCreate());
				((ProcessTypeForm) form).setWrkrCreate(s.getWrkrCreate());
				((ProcessTypeForm) form).setTsUpdate(s.getTsUpdate());
				((ProcessTypeForm) form).setWrkrUpdate(s.getWrkrUpdate());
			} catch (BusinessLogicException ble)
			{
				log.error("Manage Process Action Edit Failed. Database Exception non EJB");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageProcessAction", "edit"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			saveToken(request);

			if ("request".equals(mapping.getScope()))
				request.setAttribute(mapping.getAttribute(), form);
			else
			{
				session.setAttribute(mapping.getAttribute(), form);
			}
			request.setAttribute("formMode", "1");
			/**
			 * Logic for return functionality
			 */
			new ManageServletStack().addToStack(request, session);

			return (mapping.findForward(Constants.SUCCESS));
		} catch (Exception e)
		{
			log.error("Manage Process Action Edit Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageProcessAction", "edit"));

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

			String step = (String) request.getParameter("step");

			if (step == null)
			{ // Go to the failure page
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("manageForm.error.typeRequired"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			if (form == null)
			{
				form = new ProcessTypeForm();

				((ProcessTypeForm) form).setStep(step);
			}

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				StepManager manager = StepManager.getInstance();
				ProcessType s = manager.getStep(step);
				((ProcessTypeForm) form).setDescription(s.getDescription());
				((ProcessTypeForm) form).setTsCreate(s.getTsCreate());
				((ProcessTypeForm) form).setWrkrCreate(s.getWrkrCreate());
				((ProcessTypeForm) form).setTsUpdate(s.getTsUpdate());
				((ProcessTypeForm) form).setWrkrUpdate(s.getWrkrUpdate());
			} catch (BusinessLogicException ble)
			{
				log.error("Manage Process Action View Failed. Database Exception non EJB");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageProcessAction", "view"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			saveToken(request);

			if ("request".equals(mapping.getScope()))
				request.setAttribute(mapping.getAttribute(), form);
			else
			{
				session.setAttribute(mapping.getAttribute(), form);
			}
			request.setAttribute("formMode", "2");
			/**
			 * Logic for return functionality
			 */
			new ManageServletStack().addToStack(request, session);

			return (mapping.findForward(Constants.SUCCESS));
		} catch (Exception e)
		{
			log.error("Manage Process Action View Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageProcessAction", "view"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			return (new ActionForward(mapping.getInput()));

		}
	}

	/**
	 * @see org.apache.struts.action.Action#add(ActionMapping, ActionForm,
	 *      HttpServletRequest, HttpServletResponse)
	 */
	public ActionForward store(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		try
		{
			Locale locale = getLocale(request);
			MessageResources messages = getResources(request);
			HttpSession session = request.getSession();

			ProcessTypeForm processForm = (ProcessTypeForm) form;

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				org.dhhs.dirm.acts.cs.beans.UserBean loggedInUser = (org.dhhs.dirm.acts.cs.beans.UserBean) session.getAttribute(Constants.USERBEAN_KEY);

				StepManager manager = StepManager.getInstance();

				manager.createStepType(processForm.getStep().toUpperCase(), processForm.getDescription(), loggedInUser.getIdWorker());
			} catch (BusinessLogicException ble)
			{
				log.error("Manage Process Action Store Failed. Database Exception non EJB");
				request.setAttribute("formMode", "0");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageProcessAction", "store"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (DuplicateException de)
			{
				log.error("Manage Process Action Store returns duplicate record. Cannot Insert process " + processForm.getStep());

				new ApplicationException(de.getMessage(), de, new ErrorDescriptor("ManageProcessAction", "store"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.create.duplicate", "process type", processForm.getStep()));
				saveErrors(request, errors);
				request.setAttribute("formMode", "0");
				return (new ActionForward(mapping.getInput()));
			}

			return (mapping.findForward("store"));

		} catch (Exception e)
		{
			log.error("Manage Process Action Add Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageProcessAction", "add"));

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

			ProcessTypeForm processForm = (ProcessTypeForm) form;

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				org.dhhs.dirm.acts.cs.beans.UserBean loggedInUser = (org.dhhs.dirm.acts.cs.beans.UserBean) session.getAttribute(Constants.USERBEAN_KEY);

				StepManager manager = StepManager.getInstance();

				if (manager.getStep(processForm.getStep()) != null)
				{
					manager.updateStepType(processForm.getStep(), processForm.getDescription(), loggedInUser.getIdWorker());
				} else
				{
					log.error("Manage Process Action Save found no record. Cannot update process " + processForm.getStep());
					ActionErrors errors = new ActionErrors();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.notfound"));
					saveErrors(request, errors);
					return mapping.findForward("store");
				}
			} catch (BusinessLogicException ble)
			{
				log.error("Manage Process Action Save Failed. Database Exception non EJB");
				request.setAttribute("formMode", "1");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageProcessAction", "save"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}
			return (mapping.findForward("store"));

		} catch (Exception e)
		{
			log.error("Manage Process Action Add Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageProcessAction", "add"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			return (new ActionForward(mapping.getInput()));

		}

	}
}
