<%@page import="com.amex.srt.ContentLoadReportEntry"%>
<%@page import="com.amex.srt.ContentLoadReport"%>
<%@page import="com.amex.srt.MilitaryContentLoader"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>

<%

	
	ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());

	MilitaryContentLoader contentLoader = (MilitaryContentLoader)applicationContext.getBean("militaryContentLoader");
	
	String message = "<b>Content Load Report [" + new Date() + "]</b>";
	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>CMS Content Loader</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>

	<%
		StringBuilder reportBuilder = new StringBuilder();
	
		try {
			
			ContentLoadReport contentLoadReport = contentLoader.refreshContent();
	
			reportBuilder.append("<b>LOAD STATUS:</b> " + contentLoadReport.getStatus() + "<br/>");
			
			reportBuilder.append("<br/><b>ADDS:</b><br/></b>").append('\n');
			for (ContentLoadReportEntry reportEntry : contentLoadReport.getSuccessfulAddedEntries()) {				
				reportBuilder.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; => " + reportEntry.getContentItemPath()).append("<br/>");
			}

			reportBuilder.append("<br/><b>UPDATES:</b><br/></b>").append('\n');
			for (ContentLoadReportEntry reportEntry : contentLoadReport.getSuccessfulUpdatedEntries()) {				
				reportBuilder.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; => " + reportEntry.getContentItemPath()).append("<br/>");
			}

			reportBuilder.append("<br/><b>DELETES:</b><br/></b>").append('\n');
			for (ContentLoadReportEntry reportEntry : contentLoadReport.getSuccessfulDeletedEntries()) {				
				reportBuilder.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; => " + reportEntry.getContentItemPath()).append("<br/>");
			}
			
			reportBuilder.append("<br/><b>FAILED ENTRIES:</b><br/></b>").append('\n');
			for (ContentLoadReportEntry reportEntry : contentLoadReport.getFailedEntries()) {				
				reportBuilder.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; => " + reportEntry.getContentItemPath()).append("<br/>");
				reportBuilder.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; outcome = " + reportEntry.getOutcome()).append("<br/>");
			}
		}
		catch (Exception exception) {
			message = exception.getMessage();
		}
	%>  
	 	
	<%=message%>
	
	<br/>
	<br/>
	
	<%=reportBuilder.toString()%>
	 	
  </body>

</html>
