<%@page import="com.baiqiz.project1.network.ServletUtilities"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="com.baiqiz.project1.model.Automotive"%>
<%@page import="com.baiqiz.project1.adapter.BuildAuto"%>
<%@page import="com.baiqiz.project1.adapter.AutoServer"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Configure Model</title>
</head>
<style>
	table {
	    border-collapse: collapse;
	}
	
	table, td, th {
		padding: 3px;
	    border: 1px solid black;
	}
</style>
<body>
	<h2> Here is what you seletcted:</h2>
	<table>
	<% 
		AutoServer autoServer = new BuildAuto();
		Automotive automotive=autoServer.getAutoFromList(request.getParameter("autoName"));
	%>
		<tr>
			<td><%= request.getParameter("autoName") %></td>
			<td>base price</td>
			<td><%= automotive.getBasePrice() %></td>
		</tr>
	<%
		int optionSetSize = automotive.getOptionSetSize();
		for (int optionSetIndex=0;optionSetIndex<optionSetSize;optionSetIndex++){
			String optionSetName = automotive.getOptionSetName(optionSetIndex);
			String selection = request.getParameter(optionSetName);
			automotive.setOptionChoice(optionSetName, selection);
	%>
		<tr>
			<td><%= optionSetName %></td>
			<td><%= selection %></td>
			<td style="text-align:right"><%= automotive.getOptionChoicePrice(optionSetName) %></td>
		</tr>
	<%
		}
 	%>
 	<tr style="font-weight:bold">
 		<td>Total Price:</td>
 		<td></td>
 		<td><%= automotive.getTotalPrice() %></td>
 	</tr>
 	</table>
 	<a href="http://localhost:8080/Project1_U5/ModelServlet">Go back</a>
</body>
</html>