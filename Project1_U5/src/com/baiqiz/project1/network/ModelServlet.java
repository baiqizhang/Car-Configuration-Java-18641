package com.baiqiz.project1.network;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baiqiz.project1.adapter.AutoServer;
import com.baiqiz.project1.adapter.BuildAuto;
import com.baiqiz.project1.exception.AutoException;
import com.baiqiz.project1.model.Automotive;
import com.baiqiz.project1.util.FileIO;

/**
 * Servlet implementation class AutoServlet
 */
@WebServlet("/ModelServlet")
public class ModelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;     

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		FileIO fileIO = new FileIO();
    	try {
    		String webInfPath = getServletConfig().getServletContext().getRealPath("/WEB-INF");
			Automotive automotive = fileIO.buildAutoObjectFromPropertyFile(webInfPath+"/input");
    		AutoServer autoServer = new BuildAuto();
			autoServer.insertAutoIntoHashMap(automotive);
			automotive=fileIO.buildAutoObjectFromPropertyFile(webInfPath+"/input2");
			autoServer.insertAutoIntoHashMap(automotive);
			System.out.println(webInfPath);			
		} catch (AutoException e) {
			e.printStackTrace();
		}    	
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter printWriter = response.getWriter();
		printWriter.append(ServletUtilities.DOCTYPE);
		printWriter.append(ServletUtilities.headWithTitle("Select Model"));
		AutoServer autoServer = new BuildAuto();

		printWriter.append("<form action=\"ConfigServlet\" method=\"POST\">");
		printWriter.append("Pick a model:");
		printWriter.append("<select name=\"name\">");
		for (String retval: autoServer.getAutoList().split("\n")){
			printWriter.append("<option value=\"");
			printWriter.append(retval);
			printWriter.append("\">");
			printWriter.append(retval);
			printWriter.append("</option");
			printWriter.append("<br>");
	    }
		printWriter.append("</select>");
		printWriter.append("<input type=\"submit\" value=\"Configure\" />");
		printWriter.append("</form>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
