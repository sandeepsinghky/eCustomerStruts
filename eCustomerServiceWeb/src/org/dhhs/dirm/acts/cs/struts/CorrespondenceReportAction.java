

package org.dhhs.dirm.acts.cs.struts;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
import org.dhhs.dirm.acts.cs.beans.CorrespondenceBean;
import org.dhhs.dirm.acts.cs.businesslogic.BusinessLogicException;
import org.dhhs.dirm.acts.cs.businesslogic.CorrespondenceReportModel;
import org.dhhs.dirm.acts.cs.formbeans.CorrespondenceReportForm;
import org.dhhs.dirm.acts.cs.jasper.CorrespondenceDataSource;
import org.dhhs.dirm.acts.cs.util.ApplicationException;
import org.dhhs.dirm.acts.cs.util.ErrorDescriptor;

import dori.jasper.engine.JRException;
import dori.jasper.engine.JasperRunManager;

/**
 * CorrespondenceReportAction.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Feb 18, 2004 10:23:51 AM
 * 
 * @author rkodumagulla
 *
 */
public class CorrespondenceReportAction extends DispatchAction
{

	private static final Logger log = Logger.getLogger(CorrespondenceReportAction.class);

	/**
	 * Constructor for CorrespondenceReportAction.
	 */
	public CorrespondenceReportAction()
	{
		super();
	}

	/**
	 * @see org.apache.struts.action.Action#create(ActionMapping, ActionForm,
	 *      HttpServletRequest, HttpServletResponse)
	 */

	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		// Extract attributes we will need
		Locale locale = getLocale(request);
		MessageResources messages = getResources(request);
		HttpSession session = request.getSession();

		form = new CorrespondenceReportForm();

		if ("request".equals(mapping.getScope()))
			request.setAttribute(mapping.getAttribute(), form);
		else
		{
			session.setAttribute(mapping.getAttribute(), form);
		}
		saveToken(request);
		return (mapping.findForward(Constants.SUCCESS));
	}

	/**
	 * @see org.apache.struts.action.Action#generate(ActionMapping, ActionForm,
	 *      HttpServletRequest, HttpServletResponse)
	 */
	public ActionForward generate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		try
		{

			Locale locale = getLocale(request);
			MessageResources messages = getResources(request);
			HttpSession session = request.getSession();

			CorrespondenceReportForm crf = (CorrespondenceReportForm) form;

			ActionErrors errors = new ActionErrors();

			resetToken(request);

			org.dhhs.dirm.acts.cs.beans.UserBean loggedInUser = (org.dhhs.dirm.acts.cs.beans.UserBean) session.getAttribute(Constants.USERBEAN_KEY);

			/**
			 * Invoke the Business Process and return results
			 */

			javax.servlet.ServletContext context = this.getServlet().getServletConfig().getServletContext();

			java.io.File reportFile = null;;
			java.util.Vector data = null;
			String title = "";
			int type = 0;

			try
			{

				if (crf.isAllCorrespondence())
				{
					data = CorrespondenceReportModel.getInstance().getAllCorrespondence(crf.getFromDate(), crf.getToDate());
					title = "ALL CORRESPONDENCE";
					type = 0;
				} else if (crf.isCompleteCorrespondence())
				{
					data = CorrespondenceReportModel.getInstance().getCompleteCorrespondence(crf.getFromDate(), crf.getToDate());
					title = "COMPLETE CORRESPONDENCE";
					type = 1;
				} else if (crf.isIncompleteCorrespondence())
				{
					data = CorrespondenceReportModel.getInstance().getIncompleteCorrespondence(crf.getFromDate(), crf.getToDate());
					title = "INCOMPLETE CORRESPONDENCE";
					type = 2;
				} else
				{
					throw new BusinessLogicException("Invalid Report Requested");
				}

				data = filterData(crf, data);
				log.debug("Size of data source " + data.size());

				switch (type)
				{
					case 0 :
						reportFile = new java.io.File(context.getRealPath("/reports/AllCorrespondence.jasper"));
						break;
					case 1 :
						reportFile = new java.io.File(context.getRealPath("/reports/CompleteCorrespondence.jasper"));
						break;
					case 2 :
						reportFile = new java.io.File(context.getRealPath("/reports/IncompleteCorrespondence.jasper"));
						break;
				}

				Map parameters = new HashMap();
				parameters.put("ReportTitle", title);
				parameters.put("BaseDir", reportFile.getParentFile());
				parameters.put("FROMDATE", crf.getFromDate());
				parameters.put("TODATE", crf.getToDate());

				CorrespondenceDataSource cds = new CorrespondenceDataSource();
				cds.setData(data);

				byte[] bytes = null;

				try
				{
					bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parameters, cds);
				} catch (JRException e)
				{
					log.error("Correspondence Report Failed. Jasper Exception");

					new ApplicationException(e.getMessage(), e, new ErrorDescriptor("CorrespondenceReportAction", "generate"));

					errors = new ActionErrors();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
					saveErrors(request, errors);

					return (new ActionForward(mapping.getInput()));
				}

				if (bytes != null && bytes.length > 0)
				{
					response.setContentType("application/pdf");
					// response.setHeader("Content-disposition","attachment;
					// filename=correspondence.pdf");
					response.setContentLength(bytes.length);
					javax.servlet.ServletOutputStream ouputStream = response.getOutputStream();
					ouputStream.write(bytes, 0, bytes.length);
					ouputStream.flush();
					ouputStream.close();
				} else
				{
					log.error("Correspondence Report Failed. Unknown Exception");

					new ApplicationException("Failed to generate report", new BusinessLogicException("Failed to generate report."), new ErrorDescriptor("CorrespondenceReportAction", "generate"));

					errors = new ActionErrors();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
					saveErrors(request, errors);

					return (new ActionForward(mapping.getInput()));
				}
			} catch (BusinessLogicException ble)
			{
				log.error("Correspondence Report Failed. Unknown Exception");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("CorrespondenceReportAction", "generate"));

				errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
				saveErrors(request, errors);

				return (new ActionForward(mapping.getInput()));
			}

			// return (mapping.findForward(Constants.SUCCESS));
			return null;
		} catch (Exception e)
		{
			log.error("Correspondence Report Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("CorrespondenceReportAction", "generate"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			return (new ActionForward(mapping.getInput()));
		}
	}

	private java.util.Vector filterData(CorrespondenceReportForm crf, java.util.Vector data) throws BusinessLogicException
	{

		java.util.Vector filteredData = new java.util.Vector();

		log.debug("Correspondence Report Filters");
		log.debug("Customer: " + crf.getCustomerFirst() + " " + crf.getCustomerLast());
		log.debug("Referral Source1: " + crf.getReferralSource1());
		log.debug("Referral Source2: " + crf.getReferralSource2());
		log.debug("Referral Source3: " + crf.getReferralSource3());
		log.debug("Referral Source4: " + crf.getReferralSource4());
		log.debug("ID Worker: " + crf.getIdWorker());

		for (int i = 0; i < data.size(); i++)
		{

			boolean disQualified = false;

			CorrespondenceBean cb = (CorrespondenceBean) data.elementAt(i);
			log.debug(cb.toString());

			log.debug("Processing Record with Reference ID: " + cb.getIdReference());

			if (crf.getCustomerFirst() != null && (!crf.getCustomerFirst().equals("")))
			{
				if (!cb.getCustomerNameFirst().equalsIgnoreCase(crf.getCustomerFirst().trim()))
				{
					log.debug("Record Failed Filter Criteria : Name Mismatch" + cb.getIdReference());
					disQualified = true;
				}
			}

			if (crf.getCustomerLast() != null && (!crf.getCustomerLast().equals("")))
			{
				if (!cb.getCustomerNameLast().equalsIgnoreCase(crf.getCustomerLast().trim()))
				{
					log.debug("Record Failed Filter Criteria : Name Mismatch" + cb.getIdReference());
					disQualified = true;
				}
			}

			if (crf.getReferralSource1() != null && (!crf.getReferralSource1().equals("")))
			{
				if (!cb.getReferralSource1().equals(crf.getReferralSource1()))
				{
					log.debug("Record Failed Filter Criteria : Referral Source 1 Mismatch" + cb.getIdReference());
					disQualified = true;
				}
			}

			if (crf.getReferralSource2() != null && (!crf.getReferralSource2().equals("")))
			{
				if (!cb.getReferralSource2().equals(crf.getReferralSource2()))
				{
					log.debug("Record Failed Filter Criteria : Referral Source 2 Mismatch" + cb.getIdReference());
					disQualified = true;
				}
			}

			if (crf.getReferralSource3() != null && (!crf.getReferralSource3().equals("")))
			{
				if (!cb.getReferralSource3().equals(crf.getReferralSource3()))
				{
					log.debug("Record Failed Filter Criteria : Referral Source 3 Mismatch" + cb.getIdReference());
					disQualified = true;
				}
			}

			if (crf.getReferralSource4() != null && (!crf.getReferralSource4().equals("")))
			{
				if (!cb.getReferralSource4().equals(crf.getReferralSource4()))
				{
					log.debug("Record Failed Filter Criteria : Referral Source 4 Mismatch" + cb.getIdReference());
					disQualified = true;
				}
			}

			if (crf.getIdWorker() != null && (!crf.getIdWorker().equals("")))
			{
				if (!cb.getIdWorker().equals(crf.getIdWorker()))
				{
					log.debug("Record Failed Filter Criteria : Worker ID Mismatch" + cb.getIdReference());
					disQualified = true;
				}
			}

			if (!disQualified)
			{
				log.debug("Record passed the filer criteria " + cb.getIdReference());
				filteredData.addElement(cb);
			}

		}

		return filteredData;

	}
}
