

package org.dhhs.dirm.acts.cs.struts;

import java.util.Locale;

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
import org.dhhs.dirm.acts.cs.beans.ReferralType;
import org.dhhs.dirm.acts.cs.businesslogic.BusinessLogicException;
import org.dhhs.dirm.acts.cs.businesslogic.DuplicateException;
import org.dhhs.dirm.acts.cs.businesslogic.FormStepManager;
import org.dhhs.dirm.acts.cs.businesslogic.FormTypeManager;
import org.dhhs.dirm.acts.cs.businesslogic.StepManager;
import org.dhhs.dirm.acts.cs.ejb.CSProcessorLocalHome;
import org.dhhs.dirm.acts.cs.formbeans.ReferralProcess;
import org.dhhs.dirm.acts.cs.helpers.HomeHelper;
import org.dhhs.dirm.acts.cs.util.ApplicationException;
import org.dhhs.dirm.acts.cs.util.ErrorDescriptor;

/**
 * ManageReferralProcessAction.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Nov 04, 2003 12:00:15 PM
 * 
 * @author rkodumagulla
 *
 */
public class ManageReferralProcessAction extends DispatchAction
{

	private final static Logger log = Logger.getLogger(ManageReferralProcessAction.class);

	/**
	 * Constructor for ManageReferralProcessAction.
	 */
	public ManageReferralProcessAction()
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

			String type = request.getParameter("type");

			form = new ReferralProcess();

			((ReferralProcess) form).setType(type);

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				FormTypeManager formManager = FormTypeManager.getInstance();
				ReferralType frm = formManager.getForm(type);
				request.setAttribute("formSteps", frm.getFormSteps());

				StepManager manager = StepManager.getInstance();
				request.setAttribute("steps", manager.getAllSteps());
			} catch (BusinessLogicException ble)
			{
				log.error("Manage Referral Process Action Create Failed. Database Exception non EJB");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageReferralProcessAction", "create"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
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

			saveToken(request);
			/**
			 * Logic for return functionality
			 */
			new ManageServletStack().addToStack(request, session);

			return (mapping.findForward(Constants.SUCCESS));
		} catch (Exception e)
		{
			log.error("Manage Referral Process Action Create Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageReferralProcessAction", "create"));

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

			String type = (String) request.getParameter("type");
			String step = (String) request.getParameter("step");

			if (type == null || step == null)
			{ // Go to the failure page
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("manageForm.error.typeRequired"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			if (form == null)
			{
				form = new ReferralProcess();

				((ReferralProcess) form).setType(type);
				((ReferralProcess) form).setStep(step);
			}

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				FormStepManager manager = FormStepManager.getInstance();
				ReferralProcess frm = manager.getStep(type, step);
				form = frm;

				FormTypeManager formManager = FormTypeManager.getInstance();
				ReferralType formTypeForm = formManager.getForm(type);
				request.setAttribute("formSteps", formTypeForm.getFormSteps());

			} catch (BusinessLogicException ble)
			{
				log.error("Manage Referral Process Action Edit Failed. Database Exception non EJB");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageReferralProcessAction", "edit"));

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
			log.error("Manage Referral Process Action Edit Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageReferralProcessAction", "edit"));

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

			String type = (String) request.getParameter("type");
			String step = (String) request.getParameter("step");

			if (type == null || step == null)
			{ // Go to the failure page
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("manageForm.error.typeRequired"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			if (form == null)
			{
				form = new ReferralProcess();

				((ReferralProcess) form).setType(type);
				((ReferralProcess) form).setStep(step);
			}

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				FormStepManager manager = FormStepManager.getInstance();
				ReferralProcess frm = manager.getStep(type, step);
				form = frm;

				FormTypeManager formManager = FormTypeManager.getInstance();
				ReferralType formTypeForm = formManager.getForm(type);
				request.setAttribute("formSteps", formTypeForm.getFormSteps());

			} catch (BusinessLogicException ble)
			{
				log.error("Manage Referral Process Action View Failed. Database Exception non EJB");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageReferralAction", "view"));

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
			log.error("Manage Referral Process Action View Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageReferralProcessAction", "view"));

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
	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		try
		{
			Locale locale = getLocale(request);
			MessageResources messages = getResources(request);
			HttpSession session = request.getSession();

			ReferralProcess formStep = (ReferralProcess) form;
			String type = formStep.getType();

			request.setAttribute("formMode", "0");

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				org.dhhs.dirm.acts.cs.beans.UserBean loggedInUser = (org.dhhs.dirm.acts.cs.beans.UserBean) session.getAttribute(Constants.USERBEAN_KEY);

				FormTypeManager manager = FormTypeManager.getInstance();
				FormStepManager formStepManager = FormStepManager.getInstance();

				// Create a new referral process. if duplicate,
				// DuplicateException will handle
				manager.addStep(formStep, loggedInUser.getIdWorker());

				ReferralType formType = manager.getForm(type);

				request.setAttribute("formSteps", formType.getFormSteps());

				StepManager stepManager = StepManager.getInstance();
				request.setAttribute("steps", stepManager.getAllSteps());

			} catch (BusinessLogicException ble)
			{
				log.error("Manage Referral Process Action Add Failed. Database Exception non EJB");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageReferralProcessAction", "add"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (DuplicateException de)
			{
				FormTypeManager manager = FormTypeManager.getInstance();
				ReferralType formType = manager.getForm(type);

				request.setAttribute("formSteps", formType.getFormSteps());

				StepManager stepManager = StepManager.getInstance();
				request.setAttribute("steps", stepManager.getAllSteps());

				log.error("Manage Referral Process Action add returns duplicate record. Cannot Insert Process " + formStep.getStep());
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.create.duplicate", "referral process", formStep.getStep()));
				saveErrors(request, errors);
				request.setAttribute("formMode", "0");

				/* return new ActionForward(mapping.getInput()); */
				return mapping.findForward("duplicate");
			}

			form = new ReferralProcess();
			((ReferralProcess) form).setType(type);

			if ("request".equals(mapping.getScope()))
				request.setAttribute(mapping.getAttribute(), form);
			else
			{
				session.setAttribute(mapping.getAttribute(), form);
			}
			saveToken(request);
			return (mapping.findForward(Constants.SUCCESS));
		} catch (Exception e)
		{
			log.error("Manage Referral Process Action Add Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageReferralProcessAction", "add"));

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

			ReferralProcess formStep = (ReferralProcess) form;

			ActionErrors errors = new ActionErrors();
			org.dhhs.dirm.acts.cs.beans.UserBean loggedInUser = (org.dhhs.dirm.acts.cs.beans.UserBean) session.getAttribute(Constants.USERBEAN_KEY);

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				FormStepManager manager = FormStepManager.getInstance();
				manager.updateFormStep(formStep, loggedInUser.getIdWorker());

				FormTypeManager formManager = FormTypeManager.getInstance();
				ReferralType formTypeForm = formManager.getForm(formStep.getType());
				request.setAttribute("formSteps", formTypeForm.getFormSteps());

			} catch (BusinessLogicException ble)
			{
				log.error("Manage Referral Process Action Edit Failed. Database Exception non EJB");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageReferralProcessAction", "save"));

				errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);

				request.setAttribute("formMode", "1");

				return (new ActionForward(mapping.getInput()));
			}

			request.setAttribute("formMode", "2");

			saveToken(request);

			ActionForward forward = mapping.findForward("save");
			StringBuffer path = new StringBuffer(forward.getPath());
			boolean isQuery = (path.toString().indexOf("?") >= 0);
			if (isQuery)
			{
				path.append("&reqCode=edit&type=" + formStep.getType());
			} else
			{
				path.append("?reqCode=edit&type=" + formStep.getType());
			}

			return new ActionForward(path.toString());
		} catch (Exception e)
		{
			log.error("Manage Referral Process Action Save Failed. Unknown Exception");

			request.setAttribute("formMode", "1");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageReferralProcessAction", "save"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			return (new ActionForward(mapping.getInput()));

		}
	}

	/**
	 * @see org.apache.struts.action.Action#delete(ActionMapping, ActionForm,
	 *      HttpServletRequest, HttpServletResponse)
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		try
		{
			Locale locale = getLocale(request);
			MessageResources messages = getResources(request);
			HttpSession session = request.getSession();

			String type = (String) request.getParameter("type");
			String step = (String) request.getParameter("step");

			if (type == null || step == null)
			{ // Go to the failure page
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("manageForm.error.typeRequired"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				// Check to see if the form to be deleted is being used and if
				// so throw error
				Object obj = HomeHelper.singleton().getHome("ecsts.CSProcessorLocalHome");
				CSProcessorLocalHome processorLocalHome = (CSProcessorLocalHome) obj;
				int count = processorLocalHome.countProcessUsage(type, step);

				if (count > 0)
				{
					log.error("Manage Referral Process Action Delete returns atleast one usable process. Cannot Delete Process " + step);
					ActionErrors errors = new ActionErrors();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.cannot.delete"));
					saveErrors(request, errors);
					return (mapping.findForward("delete"));
				}

				FormStepManager manager = FormStepManager.getInstance();
				manager.deleteFormStep(type, step);
			} catch (javax.ejb.EJBException fe)
			{
				log.error("Manage Referral Process Action Failed. Database Exception resulted from EJB");

				new ApplicationException(fe.getMessage(), fe, new ErrorDescriptor("ManageReferralProcessAction", "delete"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (NamingException ne)
			{
				log.fatal("Manage Referral Process Action Failed. EJB Not Found");

				new ApplicationException(ne.getMessage(), ne, new ErrorDescriptor("ManageReferralAction", "delete"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (BusinessLogicException ble)
			{
				log.error("Manage Referral Process Action Delete Failed. Database Exception non EJB");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageReferralProcessAction", "delete"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);

				return (new ActionForward(mapping.getInput()));
			}

			saveToken(request);
			ActionForward forward = mapping.findForward("delete");
			StringBuffer path = new StringBuffer(forward.getPath());
			boolean isQuery = (path.toString().indexOf("?") >= 0);

			if (isQuery)
			{
				path.append("&reqCode=edit&type=" + type);
			} else
			{
				path.append("?reqCode=edit&type=" + type);
			}

			return new ActionForward(path.toString());

		} catch (Exception e)
		{
			log.error("Manage Referral Process Action Delete Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageReferralProcessAction", "delete"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			return (new ActionForward(mapping.getInput()));
		}
	}

	/**
	 * @see org.apache.struts.action.Action#delete(ActionMapping, ActionForm,
	 *      HttpServletRequest, HttpServletResponse)
	 */
	public ActionForward deleteAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		try
		{
			Locale locale = getLocale(request);
			MessageResources messages = getResources(request);
			HttpSession session = request.getSession();

			String type = (String) request.getParameter("type");

			if (type == null)
			{ // Go to the failure page
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("manageForm.error.typeRequired"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				// Check to see if the form to be deleted is being used and if
				// so throw error
				Object obj = HomeHelper.singleton().getHome("ecsts.CSProcessorLocalHome");
				CSProcessorLocalHome processorLocalHome = (CSProcessorLocalHome) obj;
				int count = processorLocalHome.countFormUsage(type);

				if (count > 0)
				{
					log.error("Manage Referral Process Action Delete returns atleast one usable process.");
					ActionErrors errors = new ActionErrors();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.cannot.delete"));
					saveErrors(request, errors);
					return (mapping.findForward("delete"));
				}

				FormStepManager manager = FormStepManager.getInstance();
				manager.deleteAllSteps(type);
			} catch (javax.ejb.EJBException fe)
			{
				log.error("Manage Referral Process Action Failed. Database Exception resulted from EJB");

				new ApplicationException(fe.getMessage(), fe, new ErrorDescriptor("ManageReferralProcessAction", "deleteAll"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (NamingException ne)
			{
				log.fatal("Manage Referral Process Action Failed. EJB Not Found");

				new ApplicationException(ne.getMessage(), ne, new ErrorDescriptor("ManageReferralAction", "deleteAll"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (BusinessLogicException ble)
			{
				log.error("Manage Referral Process Action Delete Failed. Database Exception non EJB");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageReferralProcessAction", "deleteAll"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);

				return (new ActionForward(mapping.getInput()));
			}

			saveToken(request);

			ActionForward forward = mapping.findForward("deleteAll");
			StringBuffer path = new StringBuffer(forward.getPath());
			boolean isQuery = (path.toString().indexOf("?") >= 0);
			if (isQuery)
			{
				path.append("&reqCode=edit&type=" + type);
			} else
			{
				path.append("?reqCode=edit&type=" + type);
			}
			return new ActionForward(path.toString());

		} catch (Exception e)
		{
			log.error("Manage Referral Process Action DeleteAll Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageReferralProcessAction", "deleteAll"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			return (new ActionForward(mapping.getInput()));
		}
	}
}
