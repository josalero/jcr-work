<%@page import="java.util.Locale"%>

<%

	Locale locale = request.getLocale();
	String localeString = locale.getLanguage() + '-' + locale.getCountry();
	
	localeString = localeString.toLowerCase();

	//response.setStatus(301);
	//response.setHeader( "Location", "/" + localeString + "/page-not-found.html" );
	//response.setHeader( "Connection", "close" );	

	//response.sendRedirect("/" + localeString + "/page-not-found.html");
%>

<html>

	<head>
	
		<!-- Robots.txt Rules -->
		<meta name="ROBOTS" content="NOINDEX, NOFOLLOW" />
		
		<title>Bank of America | Page Not Found</title>
	</head>

	<body>	
	</body>

</html>