

package org.dhhs.dirm.acts.cs.jasper.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.GenericServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import dori.jasper.engine.JRException;
import dori.jasper.engine.JasperCompileManager;

public class ReportCompilerServlet extends GenericServlet
{

	private String workingDir;

	/**
	 * @see javax.servlet.Servlet#service(ServletRequest, ServletResponse)
	 */
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException
	{
	}

	/**
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException
	{
		super.init();

		workingDir = System.getProperty("workingDir");

		ServletContext context = this.getServletConfig().getServletContext();

		System.setProperty("jasper.reports.compile.class.path", context.getRealPath("/WEB-INF/lib/jasperreports-0.5.1.jar") + System.getProperty("path.separator") + context.getRealPath("/WEB-INF/classes/"));

		System.setProperty("jasper.reports.compile.temp", context.getRealPath("/reports/"));

		/**
		 * Load the Reports Property File to compile all the reports within the
		 * file
		 */

		try
		{
			File f = new File(workingDir, "CustomerServiceReports.properties");
			System.out.println("Loading Customer Service Reports to Compile from " + f.getName());

			FileInputStream fis = new FileInputStream(f);

			Properties properties = new Properties();
			properties.load(fis);

			int nReports = Integer.parseInt(properties.getProperty("report.count"));

			String key = "report";

			for (int i = 1; i <= nReports; i++)
			{
				System.out.println(key + i);
				String file = properties.getProperty(key + i);

				try
				{
					System.out.println(this.getClass().getName() + ": Compiling Report File " + file);
					JasperCompileManager.compileReportToFile(context.getRealPath("/reports/" + file));
				} catch (JRException e)
				{
					e.printStackTrace();
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("The XML report design was successfully compiled.");
	}
}
