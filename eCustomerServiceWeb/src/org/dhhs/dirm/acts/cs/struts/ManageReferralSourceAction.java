

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
import org.dhhs.dirm.acts.cs.businesslogic.BusinessLogicException;
import org.dhhs.dirm.acts.cs.businesslogic.ReferralSourceManager;
import org.dhhs.dirm.acts.cs.formbeans.ReferralOffice;
import org.dhhs.dirm.acts.cs.formbeans.ReferralSource;
import org.dhhs.dirm.acts.cs.util.ApplicationException;
import org.dhhs.dirm.acts.cs.util.ErrorDescriptor;

/**
 * ManageReferralSourceAction.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Jan 29, 2004 11:02:15 APM
 * 
 * @author rkodumagulla
 *
 */
public class ManageReferralSourceAction extends DispatchAction
{

	private final static Logger log = Logger.getLogger(ManageReferralSourceAction.class);

	/**
	 * Constructor for ManageReferralSourceAction.
	 */
	public ManageReferralSourceAction()
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

			form = new ReferralSource();

			// Get the Key to the Office Table to associate this record
			String key = request.getParameter("key");

			ReferralSource rs = (ReferralSource) form;
			rs.setNbSeq(key);
			rs.setCdStatus(true);

			request.setAttribute("formMode", "0");

			if ("request".equals(mapping.getScope()))
				request.setAttribute(mapping.getAttribute(), form);
			else
			{
				session.setAttribute(mapping.getAttribute(), form);
			}

			// Validate the transactional control token
			ActionErrors errors = new ActionErrors();

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				ReferralSourceManager manager = ReferralSourceManager.getInstance();
				ReferralOffice ro = manager.getOffice(Short.parseShort(key));
				rs.setNmOffice(ro.getNmOffice());
				request.setAttribute("referralOffice", ro);
			} catch (BusinessLogicException ble)
			{
				log.fatal("Create Referral Source Action Failed. Database Exception");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageReferralSourceAction", "create"));

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
			log.error("Manage Referral Source Action  Create Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageReferralSourceAction", "create"));

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

			ReferralSource source = (ReferralSource) form;

			ActionErrors errors = new ActionErrors();

			request.setAttribute("formMode", "0");

			org.dhhs.dirm.acts.cs.beans.UserBean loggedInUser = (org.dhhs.dirm.acts.cs.beans.UserBean) session.getAttribute(Constants.USERBEAN_KEY);

			ReferralSource rs = (ReferralSource) form;
			rs.setIdWrkrCreate(loggedInUser.getIdWorker());
			rs.setIdWrkrUpdate(loggedInUser.getIdWorker());

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				ReferralSourceManager manager = ReferralSourceManager.getInstance();
				manager.addReferralSource((ReferralSource) form);

				/**
				 * Refresh the application scope variable referralSources
				 */
				getServlet().getServletContext().setAttribute("referralSources", manager.getAllReferralSources());
			} catch (BusinessLogicException ble)
			{
				log.fatal("Store Referral Source Action Failed. Database Exception");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageReferralSourceAction", "store"));

				errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}
			saveToken(request);

			ActionForward forward = mapping.findForward("store");
			StringBuffer path = new StringBuffer(forward.getPath());
			boolean isQuery = (path.toString().indexOf("?") >= 0);

			if (isQuery)
			{
				path.append("&reqCode=edit&key=" + rs.getNbSeq());
			} else
			{
				path.append("?reqCode=edit&key=" + rs.getNbSeq());
			}
			return new ActionForward(path.toString());
		} catch (Exception e)
		{
			log.error("Manage Referral Source Action Store Failed. Unknown Exception");

			request.setAttribute("formMode", "0");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageReferralSourceAction", "store"));

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

			String key = (String) request.getParameter("idStaff");

			log.debug("Referral Source ID_STAFF= " + key);

			if (key == null)
			{ // Go to the failure page
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.generic", "Invalid key passed"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			if (form == null)
			{
				form = new ReferralSource();
				if ("request".equals(mapping.getScope()))
					request.setAttribute(mapping.getAttribute(), form);
				else
				{
					session.setAttribute(mapping.getAttribute(), form);
				}
			}

			ActionErrors errors = new ActionErrors();

			ReferralSource rs = (ReferralSource) form;

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				ReferralSourceManager manager = ReferralSourceManager.getInstance();
				ReferralSource refo = manager.getReferralSource(key);

				rs.setIdWrkrCreate(refo.getIdWrkrCreate());
				rs.setIdWrkrUpdate(refo.getIdWrkrUpdate());
				rs.setNbSeq(refo.getNbSeq());
				rs.setCdStatus(refo.isCdStatus());
				rs.setIdStaff(refo.getIdStaff());
				rs.setTsCreate(refo.getTsCreate());
				rs.setTsUpdate(refo.getTsUpdate());
				rs.setNmStaff(refo.getNmStaff());
				rs.setTitle(refo.getTitle());

				ReferralOffice ro = manager.getOffice(Short.parseShort(refo.getNbSeq()));
				request.setAttribute("referralOffice", ro);

				rs.setNmOffice(ro.getNmOffice());
			} catch (BusinessLogicException ble)
			{
				log.fatal("Referral Source Action Edit Failed. Database Exception");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageReferralSourceAction", "edit"));

				errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			saveToken(request);

			System.out.println("Business Logic completed");

			request.setAttribute("formMode", "1");
			/**
			 * Logic for return functionality
			 */
			new ManageServletStack().addToStack(request, session);

			return (mapping.findForward(Constants.SUCCESS));
		} catch (Exception e)
		{
			log.error("Manage Referral Source Action Edit Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageReferralSourceAction", "edit"));

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

			ReferralSource rs = (ReferralSource) form;

			ActionErrors errors = new ActionErrors();

			resetToken(request);

			org.dhhs.dirm.acts.cs.beans.UserBean loggedInUser = (org.dhhs.dirm.acts.cs.beans.UserBean) session.getAttribute(Constants.USERBEAN_KEY);

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				ReferralSourceManager manager = ReferralSourceManager.getInstance();
				manager.updateReferralSource(rs, loggedInUser.getIdWorker());
			} catch (BusinessLogicException ble)
			{
				log.fatal("Manage Referral Source Action Failed. Database Exception");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageReferralSourceAction", "save"));

				errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			saveToken(request);

			ActionForward forward = mapping.findForward("save");
			StringBuffer path = new StringBuffer(forward.getPath());
			boolean isQuery = (path.toString().indexOf("?") >= 0);

			if (isQuery)
			{
				path.append("&reqCode=edit&key=" + rs.getNbSeq());
			} else
			{
				path.append("?reqCode=edit&key=" + rs.getNbSeq());
			}
			return new ActionForward(path.toString());

		} catch (Exception e)
		{
			log.error("Manage Referral Source Action Save Failed. Unknown Exception");

			request.setAttribute("formMode", "1");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageReferralSourceAction", "save"));

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
	public ActionForward inactivate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		try
		{
			Locale locale = getLocale(request);
			MessageResources messages = getResources(request);
			HttpSession session = request.getSession();

			short nbSeq = -1;

			String key = (String) request.getParameter("idStaff");

			if (key == null)
			{ // Go to the failure page
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.generic", "Invalid key passed </p>"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			if (form == null)
			{
				form = new ReferralSource();
				if ("request".equals(mapping.getScope()))
					request.setAttribute(mapping.getAttribute(), form);
				else
				{
					session.setAttribute(mapping.getAttribute(), form);
				}
			}

			ActionErrors errors = new ActionErrors();

			org.dhhs.dirm.acts.cs.beans.UserBean loggedInUser = (org.dhhs.dirm.acts.cs.beans.UserBean) session.getAttribute(Constants.USERBEAN_KEY);

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				ReferralSourceManager manager = ReferralSourceManager.getInstance();
				nbSeq = manager.inactivateReferralSource(key, loggedInUser.getIdWorker());
			} catch (BusinessLogicException ble)
			{
				log.fatal("Manage Referral Source Action Failed. Database Exception");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageReferralSourceAction", "inactivate"));

				errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			saveToken(request);

			ActionForward forward = mapping.findForward("save");
			StringBuffer path = new StringBuffer(forward.getPath());
			boolean isQuery = (path.toString().indexOf("?") >= 0);
			if (isQuery)
			{
				path.append("&reqCode=edit&key=" + new Short(nbSeq).toString());
			} else
			{
				path.append("?reqCode=edit&key=" + new Short(nbSeq).toString());
			}
			return new ActionForward(path.toString());
		} catch (Exception e)
		{
			log.error("Manage Referral Source Action Inactivate Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageReferralSourceAction", "inactivate"));

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

			String key = (String) request.getParameter("idStaff");

			log.debug("Referral Source ID_STAFF= " + key);

			if (key == null)
			{ // Go to the failure page
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.generic", "Invalid key passed"));
				saveErrors(request, errors);
				return (new ActionForward(mapping.getInput()));
			}

			if (form == null)
			{
				form = new ReferralSource();
				if ("request".equals(mapping.getScope()))
					request.setAttribute(mapping.getAttribute(), form);
				else
				{
					session.setAttribute(mapping.getAttribute(), form);
				}
			}

			ActionErrors errors = new ActionErrors();

			ReferralSource rs = (ReferralSource) form;

			/**
			 * Invoke the Business Process and return results
			 */
			try
			{
				ReferralSourceManager manager = ReferralSourceManager.getInstance();
				ReferralSource refo = manager.getReferralSource(key);

				rs.setIdWrkrCreate(refo.getIdWrkrCreate());
				rs.setIdWrkrUpdate(refo.getIdWrkrUpdate());
				rs.setNbSeq(refo.getNbSeq());
				rs.setCdStatus(refo.isCdStatus());
				rs.setIdStaff(refo.getIdStaff());
				rs.setTsCreate(refo.getTsCreate());
				rs.setTsUpdate(refo.getTsUpdate());
				rs.setNmStaff(refo.getNmStaff());
				rs.setTitle(refo.getTitle());

				ReferralOffice ro = manager.getOffice(Short.parseShort(refo.getNbSeq()));
				request.setAttribute("referralOffice", ro);

				rs.setNmOffice(ro.getNmOffice());
			} catch (BusinessLogicException ble)
			{
				log.fatal("Referral Source Action View Failed. Database Exception");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ManageReferralSourceAction", "view"));

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
			log.error("Manage Referral Source Action View Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("ManageReferralSourceAction", "view"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			return (new ActionForward(mapping.getInput()));
		}
	}
}
