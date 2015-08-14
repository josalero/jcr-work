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
	
	String message = "Cache cleaned up successfully";
	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

    <title>Enterprise Site: Utilities</title>
    
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

	<h2>Utility Links:</h2>
	
	<ul>
		<li><a href="/util/loadCMSContent.jsp">Reload Content</a></li>
		<li><a href="/util/cleanRepoAndLoadCMSContent.jsp">Clean Repo and Reload Content</a></li>
		<li><a href="/util/cleanupContentCache.jsp">Clean Up Cache</a></li>
		<%-- <li><a href="/util/gateway.jsp">Gateway Data Feed Utility</a></li> --%>
		<!-- li><a href="/util/loadCMSMilitaryContent.jsp">Reload Military Content</a></li-->

	</ul>
		 	
  </body>

</html>
