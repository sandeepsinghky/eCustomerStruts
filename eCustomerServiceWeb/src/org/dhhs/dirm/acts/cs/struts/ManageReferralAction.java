

package org.dhhs.dirm.acts.cs.struts;

import java.util.Locale;
import java.util.Vector;

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
import org.dhhs.dirm.acts.cs.businesslogic.FormTypeManager;
import org.dhhs.dirm.acts.cs.ejb.CSProcessorLocalHome;
import org.dhhs.dirm.acts.cs.formbeans.ReferralProcess;
import org.dhhs.dirm.acts.cs.formbeans.ReferralTypeForm;
import org.dhhs.dirm.acts.cs.helpers.HomeHelper;
import org.dhhs.dirm.acts.cs.util.ApplicationException;
import org.dhhs.dirm.acts.cs.util.ErrorDescriptor;

/**
 * ManageReferralAction.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Sep 17, 2003 4:24:15 PM
 * 
 * @author rkodumagulla
 *
 */
public class ManageReferralAction extends DispatchAction
{

	private final static Logger log = Logger.getLogger(ManageReferralAction.class);

	/**
	 * Constructor for ManageReferralAction.
	 */
	public ManageReferralAction()
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

			form = new ReferralTypeForm();
			((ReferralTypeForm) form).setGenerateCorrespondence(true);

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
			log.error("Manage Referral Action Create Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageReferralAction", "create"));

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

			if (type == null)
			{ // Go to the failure page
				System.out.println("Referral Type not passed");
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("manageForm.error.typeRequired"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			// Populate the profile form
			if (form == null)
			{

				form = new ReferralTypeForm();
				if ("request".equals(mapping.getScope()))
					request.setAttribute(mapping.getAttribute(), form);
				else
				{
					session.setAttribute(mapping.getAttribute(), form);
				}
			}

			ReferralTypeForm typeForm = (ReferralTypeForm) form;

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				FormTypeManager manager = FormTypeManager.getInstance();
				ReferralType frm = manager.getForm(type);
				typeForm.setDescription(frm.getDescription());
				typeForm.setGenerateCorrespondence(frm.isGenerateCorrespondence());
				typeForm.setIdWrkrCreate(frm.getWrkrCreate());
				typeForm.setIdWrkrUpdate(frm.getWrkrUpdate());
				typeForm.setTsCreate(frm.getTsCreate());
				typeForm.setTsUpdate(frm.getTsUpdate());
				typeForm.setFormSteps(frm.getFormSteps());
			} catch (BusinessLogicException ble)
			{
				log.error("Manage Referral Action Edit Failed. Database Exception non EJB");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageReferralAction", "edit"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
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
			log.error("Manage Referral Action Edit Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageReferralAction", "edit"));

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

			if (type == null)
			{ // Go to the failure page
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("manageForm.error.typeRequired"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			// Populate the user registration form
			if (form == null)
			{

				form = new ReferralTypeForm();
				if ("request".equals(mapping.getScope()))
					request.setAttribute(mapping.getAttribute(), form);
				else
				{
					session.setAttribute(mapping.getAttribute(), form);
				}
			}

			ReferralTypeForm typeForm = (ReferralTypeForm) form;

			request.setAttribute("formMode", "2");

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				FormTypeManager manager = FormTypeManager.getInstance();
				ReferralType frm = manager.getForm(type);
				typeForm.setDescription(frm.getDescription());
				typeForm.setGenerateCorrespondence(frm.isGenerateCorrespondence());
				typeForm.setIdWrkrCreate(frm.getWrkrCreate());
				typeForm.setIdWrkrUpdate(frm.getWrkrUpdate());
				typeForm.setTsCreate(frm.getTsCreate());
				typeForm.setTsUpdate(frm.getTsUpdate());
				typeForm.setFormSteps(frm.getFormSteps());
			} catch (BusinessLogicException ble)
			{
				log.error("Manage Referral Action View Failed. Database Exception non EJB");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageReferralAction", "view"));

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

			return (mapping.findForward(Constants.SUCCESS));
		} catch (Exception e)
		{
			log.error("Manage Referral View Create Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageReferralAction", "view"));

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

			ReferralTypeForm formType = (ReferralTypeForm) form;

			ActionErrors errors = new ActionErrors();

			errors = formType.validate(mapping, request);

			/**
			 * Invoke the Business Process to get the Referral Processes Again
			 * as they are not stored by the struts framework
			 */

			try
			{
				FormTypeManager manager = FormTypeManager.getInstance();
				ReferralType frm = manager.getForm(formType.getType());
				formType.setFormSteps(frm.getFormSteps());

			} catch (BusinessLogicException ble)
			{
				log.error("Manage Referral Action Save Failed. Database Exception non EJB");

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
				FormTypeManager manager = FormTypeManager.getInstance();
				manager.updateFormType(formType.getType(), formType.getDescription(), formType.isGenerateCorrespondence(), loggedInUser.getIdWorker());
			} catch (BusinessLogicException ble)
			{
				log.error("Manage Referral Action Save Failed. Database Exception non EJB");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageReferralAction", "save"));

				errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			saveToken(request);

			return (mapping.findForward("save"));
		} catch (Exception e)
		{
			log.error("Manage Referral Action Save Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageReferralAction", "save"));

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

			ReferralTypeForm formType = (ReferralTypeForm) form;

			ActionErrors errors = new ActionErrors();

			errors = formType.validate(mapping, request);
			if (!errors.isEmpty())
			{
				saveErrors(request, errors);

				return (new ActionForward(mapping.getInput()));
			}

			request.setAttribute("formMode", "2");

			org.dhhs.dirm.acts.cs.beans.UserBean loggedInUser = (org.dhhs.dirm.acts.cs.beans.UserBean) session.getAttribute(Constants.USERBEAN_KEY);

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				FormTypeManager manager = FormTypeManager.getInstance();

				manager.createFormType(formType.getType().toUpperCase(), formType.getDescription(), formType.isGenerateCorrespondence(), loggedInUser.getIdWorker());

				// Now automatically create the required processes for the
				// referral type
				ReferralProcess rp = new ReferralProcess();
				for (int i = 0; i < Constants.PRCS_STATUS.length; i++)
				{
					rp.setType(formType.getType().toUpperCase());
					rp.setStep(Constants.PRCS_STATUS[i]);
					rp.setSeq(i + 1);
					rp.setDuration((short) 0);
					manager.addStep(rp, loggedInUser.getIdWorker());
				}

				/**
				 * Refresh the application scope variable forms
				 */
				getServlet().getServletContext().setAttribute("forms", manager.getAllFormTypes());
			} catch (BusinessLogicException ble)
			{
				log.error("Manage Referral Action Failed. Database Exception non EJB");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageReferralAction", "store"));

				errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				request.setAttribute("formMode", "0");
				return (new ActionForward(mapping.getInput()));
			} catch (DuplicateException de)
			{
				log.fatal("Manage Referral Action Failed. " + de.getMessage());

				new ApplicationException(de.getMessage(), de, new ErrorDescriptor("ManageReferralAction", "store"));

				errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.create.duplicate", "referral type", formType.getType()));
				saveErrors(request, errors);
				request.setAttribute("formMode", "0");
				return (new ActionForward(mapping.getInput()));
			}
			saveToken(request);

			return mapping.findForward("store");
		} catch (Exception e)
		{
			log.error("Manage Referral Action Store Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageReferralAction", "store"));

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
					Vector forms = null;
					FormTypeManager manager = FormTypeManager.getInstance();
					forms = manager.getAllFormTypes();

					log.error("Manage Referral Action Delete returns atleast one usable form. Cannot Delete Form " + type);
					ActionErrors errors = new ActionErrors();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.cannot.delete"));
					saveErrors(request, errors);

					request.setAttribute(Constants.FORMSLIST, forms);

					return (mapping.findForward("delete"));
				}

				FormTypeManager manager = FormTypeManager.getInstance();
				manager.deleteFormType(type);
			} catch (javax.ejb.EJBException fe)
			{
				log.error("Manage Referral Action Failed. Database Exception resulted from EJB");

				new ApplicationException(fe.getMessage(), fe, new ErrorDescriptor("ManageReferralAction", "execute"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (NamingException ne)
			{
				log.fatal("Manage Referral Action Failed. EJB Not Found");

				new ApplicationException(ne.getMessage(), ne, new ErrorDescriptor("ManageReferralAction", "execute"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			} catch (BusinessLogicException ble)
			{
				log.error("Manage Referral Action Delete Failed. Database Exception non EJB");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageReferralAction", "delete"));

				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			saveToken(request);

			return (mapping.findForward("delete"));
		} catch (Exception e)
		{
			log.error("Manage Referral Action Delete Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageReferralAction", "delete"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			return (new ActionForward(mapping.getInput()));
		}
	}
}
