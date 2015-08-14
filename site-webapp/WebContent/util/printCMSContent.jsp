<%@page import="com.amex.srt.content.ContentManager"%>
<%@page import="com.amex.srt.content.ContentLoader"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>

<%

	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	Locale locale = request.getLocale();
	String localeString = locale.getLanguage() + '-' + locale.getCountry();
	
	ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());

	ContentManager contentManager = (ContentManager)applicationContext.getBean("contentManager");
	ContentLoader contentLoader = (ContentLoader)applicationContext.getBean("contentLoader");
	
	String message = "Loading completed successfully";
	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

    <base href="<%=basePath%>">
    
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
		try {
			
			contentManager.listFolder("/xml/en_US/our-story");
		}
		catch (Exception exception) {
			message = exception.getMessage();
		}
	%>  
	 	
	<%=message%>
	 	
  </body>

</html>
