package com.baiqiz.project1.network;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baiqiz.project1.adapter.AutoServer;
import com.baiqiz.project1.adapter.BuildAuto;
import com.baiqiz.project1.exception.AutoException;
import com.baiqiz.project1.model.Automotive;

/**
 * Servlet implementation class ConfigServlet
 */
@WebServlet("/ConfigServlet")
public class ConfigServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("nothing posted");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter printWriter = response.getWriter();
		//Write title and header
		printWriter.append(ServletUtilities.DOCTYPE);
		printWriter.append(ServletUtilities.headWithTitle("Configure Model"));

		//Use forms
		String autoName = request.getParameter("name");
		AutoServer autoServer = new BuildAuto();
		Automotive automotive = autoServer.getAutoFromList(autoName);

		printWriter.append("<style> table { border-collapse: collapse; } "
				+ "table, td, th {padding: 3px;border: 1px solid black;}"
				+ "</style>");
		try {
			printWriter.append(autoName);
			printWriter.append("<br>");

			printWriter.append("<form action=\"ShowResult.jsp\" method=\"GET\">");
			// A hidden input field for sending extra parameter
			printWriter.append("<input type=\"hidden\" name=\"autoName\" value=\""); 
			printWriter.append(autoName);
			printWriter.append("\">");
			System.out.println(automotive);
			printWriter.append("<table>");
			int optionSetSize = automotive.getOptionSetSize();
			for (int optionSetIndex=0;optionSetIndex<optionSetSize;optionSetIndex++){
				String optionSetName = automotive.getOptionSetName(optionSetIndex);
				printWriter.append("<tr>");
				// 1st column
				printWriter.append("<td>");
				printWriter.append(optionSetName);
				printWriter.append("</td>");
				// 2nd column
				printWriter.append("<td>");
				printWriter.append("<select name=\"");
				printWriter.append(optionSetName);
				printWriter.append("\">");
				int optionSize = automotive.getOptionSize(optionSetIndex);
				for (int optionIndex=0;optionIndex<optionSize;optionIndex++){
					String optionName = automotive.getOptionName(optionSetIndex, optionIndex);
					printWriter.append("<option value=\"");
					printWriter.append(optionName);
					printWriter.append("\">");
					printWriter.append(optionName);
					printWriter.append("</option>");					
				}
				printWriter.append("</select>");
				printWriter.append("</td>");	
				printWriter.append("</tr>");
			}
			printWriter.append("<tr><td  colspan =\"2\">");
			printWriter.append("<input type=\"submit\" value=\"Configure\" />");
			printWriter.append("</td></tr>");
			printWriter.append("</table>");
			printWriter.append("</form>");
			
		} catch (AutoException e) {
			System.err.println("Corrupted!");
			System.err.println(automotive);
			e.printStackTrace();
		}
			
	}

}
