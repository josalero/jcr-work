<%@ page isErrorPage="true" %>
<%@page import="java.util.Locale"%>

<%@page import="java.util.Locale"%>

<%

	session.getServletContext().setAttribute("exception", exception);

	Locale locale = request.getLocale();
	String localeString = locale.getLanguage() + '-' + locale.getCountry();
	
	localeString = localeString.toLowerCase();
	
	Object codeObj = request.getAttribute("javax.servlet.error.status_code");
	Object messageObj = request.getAttribute("javax.servlet.error.message");
	Object typeObj = request.getAttribute("javax.servlet.error.exception_type");
	Throwable throwable = (Throwable)request.getAttribute("javax.servlet.error.exception");
	String uri = (String) request.getAttribute("javax.servlet.error.request_uri");
	
	if (codeObj != null) {
		session.setAttribute("ABOUT_SITE_ERROR_500_CODE", codeObj);
	}
	
	if (messageObj != null) {
		session.setAttribute("ABOUT_SITE_ERROR_500_MESSAGE", messageObj);
	}
	
	if (typeObj != null) {
		session.setAttribute("ABOUT_SITE_ERROR_500_TYPE", typeObj);
	}
	
	if (throwable != null) {
		session.setAttribute("ABOUT_SITE_ERROR_500_EXCEPTION", throwable);
	}
	
	if (uri != null) {
		session.setAttribute("ABOUT_SITE_ERROR_500_URI", uri);
	}
	
//	response.setStatus(301);
//	response.setHeader( "Location", "/" + localeString + "/server-error.html" );
//	response.setHeader( "Connection", "close" );	
	
	//response.sendRedirect("/" + localeString + "/server-error.html");
%>

<html>

	<head>
	
		<!-- Robots.txt Rules -->
		<meta name="ROBOTS" content="NOINDEX, NOFOLLOW" />
		
		<title>Bank of America | Server Error</title>
	</head>

	<body>	
	</body>

</html>