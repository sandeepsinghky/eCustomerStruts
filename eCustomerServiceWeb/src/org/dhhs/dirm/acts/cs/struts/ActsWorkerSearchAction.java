

package org.dhhs.dirm.acts.cs.struts;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.naming.NamingException;
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
import org.dhhs.dirm.acts.cs.beans.ActsWorkerBean;
import org.dhhs.dirm.acts.cs.beans.ActsWorkerEntityBean;
import org.dhhs.dirm.acts.cs.ejb.CSActsWorkerLocal;
import org.dhhs.dirm.acts.cs.ejb.CSActsWorkerLocalHome;
import org.dhhs.dirm.acts.cs.formbeans.ActsWorkerSearchForm;
import org.dhhs.dirm.acts.cs.helpers.HomeHelper;

/**
 * ActsWorkerSearchAction.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Aug 26, 2003 2:21:46 PM
 * 
 * @author rkodumagulla
 *
 */
public class ActsWorkerSearchAction extends Action
{

	private static final Logger log = Logger.getLogger(ActsWorkerSearchAction.class);

	private ActsWorkerBean createActsWorkerBean(CSActsWorkerLocal worker)
	{
		// Create the Java Bean and pass it to the list screen
		ActsWorkerBean workerBean = new ActsWorkerBean();
		ActsWorkerEntityBean actsWorkerEntityBean = worker.getActsWorkerEntityBean();

		workerBean.setCdFipsWrkr(actsWorkerEntityBean.getCdFipsWrkr());
		workerBean.setCdWrkrType(actsWorkerEntityBean.getCdWrkrType());
		workerBean.setIdEmail(actsWorkerEntityBean.getIdEmail());
		workerBean.setIdWrkr(actsWorkerEntityBean.getIdWrkr());
		workerBean.setIdWrkrLogon(actsWorkerEntityBean.getIdWrkr());
		workerBean.setNbFaxWorker(actsWorkerEntityBean.getNbFaxWorker());
		workerBean.setNbTelWorker(actsWorkerEntityBean.getNbTelWorker());
		workerBean.setNmWrkr(actsWorkerEntityBean.getNmWrkr());
		workerBean.setTmLunchStart(actsWorkerEntityBean.getTmLunchStart());
		workerBean.setTmLunchEnd(actsWorkerEntityBean.getTmLunchEnd());
		workerBean.setTmWorkStart(actsWorkerEntityBean.getTmWorkStart());
		workerBean.setTmWorkEnd(actsWorkerEntityBean.getTmWorkEnd());
		return workerBean;
	}

	/**
	 * @see org.apache.struts.action.Action#execute(ActionMapping, ActionForm,
	 *      HttpServletRequest, HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		// Obtain username and password frm web tier
		String firstName = ((ActsWorkerSearchForm) form).getFirstname();

		if (firstName.length() == 0)
		{
			firstName = null;
		}

		String lastName = ((ActsWorkerSearchForm) form).getLastname();
		if (lastName.length() == 0)
		{
			lastName = null;
		}

		String workerId = ((ActsWorkerSearchForm) form).getWorkerid();
		if (workerId.length() == 0)
		{
			workerId = null;
		}

		Vector workers = new Vector();

		/**
		 * Invoke the Business Process and return results
		 */
		try
		{
			Object obj = HomeHelper.singleton().getHome("ecsts.CSActsWorkerLocalHome");
			CSActsWorkerLocalHome actsWorkerLocalHome = (CSActsWorkerLocalHome) obj;

			if (workerId != null)
			{
				CSActsWorkerLocal worker = actsWorkerLocalHome.findByPrimaryKey(workerId);
				workers.add(createActsWorkerBean(worker));
				log.debug("Search by Worker ID " + workerId);
			} else if (lastName != null)
			{
				if (firstName != null)
				{
					Collection c = actsWorkerLocalHome.findWorkersByName(lastName, firstName);
					Iterator i = c.iterator();
					while (i.hasNext())
					{
						CSActsWorkerLocal worker = (CSActsWorkerLocal) i.next();
						workers.add(createActsWorkerBean(worker));
					}
					log.debug("Search by First & Last Name " + firstName + " " + lastName);
				} else
				{
					Collection c = actsWorkerLocalHome.findWorkersByLastName(lastName + "%");
					Iterator i = c.iterator();
					while (i.hasNext())
					{
						CSActsWorkerLocal worker = (CSActsWorkerLocal) i.next();
						workers.add(createActsWorkerBean(worker));
					}
					log.debug("Search by Last Name " + lastName);
				}
			}
		} catch (javax.ejb.FinderException fe)
		{
			log.info(fe.getMessage());
			// credentials don't match
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.userSearch.notfound"));
			saveErrors(request, errors);
			return (new ActionForward(mapping.getInput()));
		} catch (NamingException ne)
		{
			log.fatal(ne.getMessage());
			// credentials don't match
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.logon.connect"));
			saveErrors(request, errors);
			return (new ActionForward(mapping.getInput()));
		}

		// Save our logged-in user in the session,
		// because we use it again later.
		HttpSession session = request.getSession();

		request.setAttribute(Constants.ACTSWORKERSLIST_KEY, workers);

		// Return success
		return (mapping.findForward(Constants.SUCCESS));
	}
}
