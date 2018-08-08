

package org.dhhs.dirm.acts.cs.struts;

import java.util.Calendar;
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
import org.dhhs.dirm.acts.cs.businesslogic.BusinessLogicException;
import org.dhhs.dirm.acts.cs.businesslogic.SupportTotalReportModel;
import org.dhhs.dirm.acts.cs.formbeans.LocalSupportForm;
import org.dhhs.dirm.acts.cs.jasper.SupportTotalDataSource;
import org.dhhs.dirm.acts.cs.util.ApplicationException;
import org.dhhs.dirm.acts.cs.util.DateFormatUtil;
import org.dhhs.dirm.acts.cs.util.ErrorDescriptor;

import dori.jasper.engine.JRException;
import dori.jasper.engine.JasperRunManager;

/**
 * LocalSupportReportAction.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Feb 27, 2004 10:37:14 AM
 * 
 * @author rkodumagulla
 *
 */
public class LocalSupportReportAction extends DispatchAction
{

	private final static Logger log = Logger.getLogger(LocalSupportReportAction.class);

	/**
	 * Constructor for LocalSupportReportAction.
	 */
	public LocalSupportReportAction()
	{
		super();
	}

	public ActionForward generate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{

			Locale locale = getLocale(request);
			MessageResources messages = getResources(request);
			HttpSession session = request.getSession();

			LocalSupportForm lsf = (LocalSupportForm) form;

			log.info("Local Support Report Form : " + lsf.toString());

			ActionErrors errors = new ActionErrors();

			org.dhhs.dirm.acts.cs.beans.UserBean loggedInUser = (org.dhhs.dirm.acts.cs.beans.UserBean) session.getAttribute(Constants.USERBEAN_KEY);

			/**
			 * Invoke the Business Process and return results
			 */

			javax.servlet.ServletContext context = this.getServlet().getServletConfig().getServletContext();

			java.io.File reportFile = null;
			java.util.Vector data = null;
			String title = "LOCAL REFERRAL REPORT";
			reportFile = new java.io.File(context.getRealPath("/reports/SupportTotal.jasper"));

			try
			{

				String startDate = buildStartDate(lsf);
				String endDate = buildEndDate(lsf);

				log.info("start Date: " + startDate);
				log.info("end Date: " + endDate);

				Map parameters = new HashMap();
				parameters.put("ReportTitle", title);
				parameters.put("BaseDir", reportFile.getParentFile());
				parameters.put("FROMDATE", new DateFormatUtil().format(startDate, 1));
				parameters.put("TODATE", new DateFormatUtil().format(endDate, 1));

				SupportTotalDataSource stds = new SupportTotalDataSource();

				SupportTotalReportModel strm = SupportTotalReportModel.getInstance();
				stds.setData(strm.getData(startDate, endDate));

				byte[] bytes = null;

				try
				{
					bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parameters, stds);
				} catch (JRException e)
				{
					log.error("Support Total Report Failed. Jasper Exception");

					new ApplicationException(e.getMessage(), e, new ErrorDescriptor("SupportReportAction", "generate"));

					errors = new ActionErrors();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
					saveErrors(request, errors);

					return (new ActionForward(mapping.getInput()));
				}

				if (bytes != null && bytes.length > 0)
				{
					response.setContentType("application/pdf");
					response.setContentLength(bytes.length);
					javax.servlet.ServletOutputStream ouputStream = response.getOutputStream();
					ouputStream.write(bytes, 0, bytes.length);
					ouputStream.flush();
					ouputStream.close();
				} else
				{
					log.error("Support Total Report Failed. Unknown Exception");

					new ApplicationException("Failed to generate report", new BusinessLogicException("Failed to generate report."), new ErrorDescriptor("LocalSupportReportAction", "generate"));

					errors = new ActionErrors();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
					saveErrors(request, errors);

					return (new ActionForward(mapping.getInput()));
				}
			} catch (BusinessLogicException ble)
			{
				log.error("Local Support Report Failed. Unknown Exception");

				new ApplicationException(ble.getMessage(), ble, new ErrorDescriptor("LocalSupportReportAction", "generate"));

				errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
				saveErrors(request, errors);

				return (new ActionForward(mapping.getInput()));
			}
			return (mapping.findForward(Constants.SUCCESS));
		} catch (Exception e)
		{
			log.error("Local Support Report Failed. Unknown Exception");

			new ApplicationException(e.getMessage(), e, new ErrorDescriptor("LocalSupportReportAction", "generate"));

			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.application"));
			saveErrors(request, errors);

			return (new ActionForward(mapping.getInput()));
		}
	}

	private String buildStartDate(LocalSupportForm lsf)
	{

		Calendar now = Calendar.getInstance();
		java.util.Vector monthList = new java.util.Vector();
		java.util.Date dt = null;

		monthList.addElement("January");
		monthList.addElement("February");
		monthList.addElement("March");
		monthList.addElement("April");
		monthList.addElement("May");
		monthList.addElement("June");
		monthList.addElement("July");
		monthList.addElement("August");
		monthList.addElement("September");
		monthList.addElement("October");
		monthList.addElement("November");
		monthList.addElement("December");

		switch (lsf.getItemNumber())
		{
			case 0 :
				now.set(Calendar.YEAR, lsf.getYear1());
				now.getTime();
				loop : for (int i = 0; i < monthList.size(); i++)
				{
					if (monthList.elementAt(i).equals(lsf.getMonth()))
					{
						now.set(Calendar.MONTH, i);
						now.getTime();
						break loop;
					}
				}
				now.set(Calendar.DAY_OF_MONTH, 1);
				dt = now.getTime();
				log.info("Start Date Being Selected: " + dt);
				break;
			case 1 :
				now.set(Calendar.YEAR, lsf.getYear2());
				now.getTime();

				switchstmt : switch (lsf.getQuarter())
				{
					case 1 :
						now.set(Calendar.MONTH, 0);
						now.getTime();
						break switchstmt;
					case 2 :
						now.set(Calendar.MONTH, 3);
						now.getTime();
						break switchstmt;
					case 3 :
						now.set(Calendar.MONTH, 6);
						now.getTime();
						break switchstmt;
					case 4 :
						now.set(Calendar.MONTH, 9);
						now.getTime();
						break switchstmt;
				}
				now.set(Calendar.DAY_OF_MONTH, 1);
				dt = now.getTime();
				log.info("Start Date Being Selected: " + dt);
				break;
			case 2 :
				now.set(Calendar.YEAR, lsf.getYear3());
				now.getTime();

				switchstmt : switch (lsf.getSemiannual())
				{
					case 1 :
						now.set(Calendar.MONTH, 0);
						now.getTime();
						break switchstmt;
					case 2 :
						now.set(Calendar.MONTH, 6);
						now.getTime();
						break switchstmt;
				}
				now.set(Calendar.DAY_OF_MONTH, 1);
				dt = now.getTime();
				log.info("Start Date Being Selected: " + dt);
				break;
			case 3 :
				now.set(Calendar.YEAR, lsf.getYear4());
				now.getTime();

				now.set(Calendar.MONTH, 0);
				now.getTime();

				now.set(Calendar.DAY_OF_MONTH, 1);
				dt = now.getTime();
				log.info("Start Date Being Selected: " + dt);
				break;
		}

		return (new java.sql.Date(dt.getTime()).toString());

	}

	private String buildEndDate(LocalSupportForm lsf)
	{

		Calendar now = Calendar.getInstance();
		java.util.Vector monthList = new java.util.Vector();
		java.util.Date dt = null;

		monthList.addElement("January");
		monthList.addElement("February");
		monthList.addElement("March");
		monthList.addElement("April");
		monthList.addElement("May");
		monthList.addElement("June");
		monthList.addElement("July");
		monthList.addElement("August");
		monthList.addElement("September");
		monthList.addElement("October");
		monthList.addElement("November");
		monthList.addElement("December");

		switch (lsf.getItemNumber())
		{
			case 0 :
				now.set(Calendar.YEAR, lsf.getYear1());
				now.getTime();
				loop : for (int i = 0; i < monthList.size(); i++)
				{
					if (monthList.elementAt(i).equals(lsf.getMonth()))
					{
						now.set(Calendar.MONTH, i);
						now.getTime();
						break loop;
					}
				}

				int daysInMonth = now.getActualMaximum(Calendar.DAY_OF_MONTH);
				now.set(Calendar.DATE, daysInMonth);
				dt = now.getTime();
				break;
			case 1 :
				now.set(Calendar.YEAR, lsf.getYear2());
				now.getTime();

				switchstmt : switch (lsf.getQuarter())
				{
					case 1 :
						now.set(Calendar.MONTH, 2);
						now.getTime();
						break switchstmt;
					case 2 :
						now.set(Calendar.MONTH, 5);
						now.getTime();
						break switchstmt;
					case 3 :
						now.set(Calendar.MONTH, 8);
						now.getTime();
						break switchstmt;
					case 4 :
						now.set(Calendar.MONTH, 11);
						now.getTime();
						break switchstmt;
				}
				daysInMonth = now.getActualMaximum(Calendar.DAY_OF_MONTH);
				now.set(Calendar.DATE, daysInMonth);
				dt = now.getTime();
				break;
			case 2 :
				now.set(Calendar.YEAR, lsf.getYear3());
				now.getTime();

				switchstmt : switch (lsf.getSemiannual())
				{
					case 1 :
						now.set(Calendar.MONTH, 5);
						now.getTime();
						break switchstmt;
					case 2 :
						now.set(Calendar.MONTH, 11);
						now.getTime();
						break switchstmt;
				}
				daysInMonth = now.getActualMaximum(Calendar.DAY_OF_MONTH);
				now.set(Calendar.DATE, daysInMonth);
				dt = now.getTime();
				break;
			case 3 :
				now.set(Calendar.YEAR, lsf.getYear4());
				now.getTime();

				now.set(Calendar.MONTH, 11);
				now.getTime();

				daysInMonth = now.getActualMaximum(Calendar.DAY_OF_MONTH);
				now.set(Calendar.DATE, daysInMonth);
				dt = now.getTime();

				break;
		}

		return (new java.sql.Date(dt.getTime()).toString());

	}
}
