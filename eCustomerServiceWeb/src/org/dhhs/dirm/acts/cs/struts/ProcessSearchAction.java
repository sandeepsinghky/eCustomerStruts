

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
import org.dhhs.dirm.acts.cs.businesslogic.StepManager;
import org.dhhs.dirm.acts.cs.util.ApplicationException;
import org.dhhs.dirm.acts.cs.util.ErrorDescriptor;

/**
 * ProcessSearchAction.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Aug 26, 2003 2:21:46 PM
 * 
 * @author rkodumagulla
 *
 */
public class ProcessSearchAction extends Action
{

	private final static Logger log = Logger.getLogger(ProcessSearchAction.class);

	/**
	 * @see org.apache.struts.action.Action#execute(ActionMapping, ActionForm,
	 *      HttpServletRequest, HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		Vector steps = null;

		/**
		 * Invoke the Business Process and return results
		 */

		try
		{
			StepManager manager = StepManager.getInstance();
			steps = manager.getAllSteps();
		} catch (BusinessLogicException ble)
		{
			log.error("Process Search Action Failed. Database Exception non EJB");

			new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("ProcessSearchAction", "execute"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.database"));
			saveErrors(request, errors);
			return (new ActionForward(mapping.getInput()));
		}

		request.setAttribute(Constants.STEPSLIST, steps);

		return (mapping.findForward(Constants.SUCCESS));

	}
}
