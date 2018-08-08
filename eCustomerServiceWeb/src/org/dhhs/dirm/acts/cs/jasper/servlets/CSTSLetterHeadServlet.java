

package org.dhhs.dirm.acts.cs.jasper.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.dhhs.dirm.acts.cs.DBConnectManager;
import org.dhhs.dirm.acts.cs.beans.TaskHistoryBean;
import org.dhhs.dirm.acts.cs.ejb.CSProcessorLocal;
import org.dhhs.dirm.acts.cs.ejb.CSProcessorLocalHome;
import org.dhhs.dirm.acts.cs.helpers.HomeHelper;
import org.dhhs.dirm.acts.cs.jasper.TaskNotesDataSource;

import dori.jasper.engine.JRException;
import dori.jasper.engine.JasperRunManager;

public class CSTSLetterHeadServlet extends HttpServlet
{

	private DataSource	dataSource;
	private String		user;
	private String		password;

	/**
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest,
	 *      HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException
	{
		performTask(arg0, arg1);
	}

	/**
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest,
	 *      HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException
	{
		performTask(arg0, arg1);
	}

	/**
	 * @see javax.servlet.Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig arg0) throws ServletException
	{
		super.init(arg0);

		DBConnectManager cm = new DBConnectManager();

		dataSource = cm.getDataSource();

		user = cm.getUserID();

		password = cm.getPassword();
	}

	public void performTask(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{

		Connection connection = null;

		try
		{
			connection = dataSource.getConnection(user, password);
		} catch (SQLException e)
		{
			e.printStackTrace();
			return;
		}

		ServletContext context = this.getServletConfig().getServletContext();

		String report = request.getParameter("report");

		String referenceID = request.getParameter("referenceID");
		String note = "";

		File reportFile = new File(context.getRealPath("/reports/" + report + ".jasper"));

		/**
		 * Invoke the Business Process and return results
		 */
		try
		{
			Object obj = HomeHelper.singleton().getHome("ecsts.CSProcessorLocalHome");
			CSProcessorLocalHome processorLocalHome = (CSProcessorLocalHome) obj;

			System.out.println("Search by Reference ID");
			CSProcessorLocal task = processorLocalHome.findByPrimaryKey(referenceID);
			System.out.println(task.getCdStatus());

			int taskHistoryItem = 0;

			for (int i = 0; i < task.getFrmTrack().size(); i++)
			{
				TaskHistoryBean thb = (TaskHistoryBean) task.getFrmTrack().elementAt(i);
				if (thb.getCdStatus().trim().equals(task.getCdStatus().trim()))
				{
					taskHistoryItem = i;
					note = thb.getNotes().trim();
					break;
				}
			}

		} catch (javax.ejb.FinderException fe)
		{
		} catch (NamingException ne)
		{
		}

		Map parameters = new HashMap();
		parameters.put("ReportTitle", "Address Report");
		parameters.put("BaseDir", reportFile.getParentFile());
		parameters.put("txtLine", note);

		byte[] bytes = null;

		try
		{
			bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parameters, new TaskNotesDataSource());

		} catch (JRException e)
		{
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("<title>JasperReports - Web Application Sample</title>");
			out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"../stylesheet.css\" title=\"Style\">");
			out.println("</head>");

			out.println("<body bgcolor=\"white\">");

			out.println("<span class=\"bnew\">JasperReports encountered this error :</span>");
			out.println("<pre>");

			e.printStackTrace(out);

			out.println("</pre>");

			out.println("</body>");
			out.println("</html>");

			return;
		} finally
		{
			try
			{
				connection.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

		if (bytes != null && bytes.length > 0)
		{
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			ServletOutputStream ouputStream = response.getOutputStream();
			ouputStream.write(bytes, 0, bytes.length);
			ouputStream.flush();
			ouputStream.close();
		} else
		{
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("<title>JasperReports - Web Application Sample</title>");
			out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"../stylesheet.css\" title=\"Style\">");
			out.println("</head>");

			out.println("<body bgcolor=\"white\">");

			out.println("<span class=\"bold\">Empty response.</span>");

			out.println("</body>");
			out.println("</html>");
		}
	}
}
