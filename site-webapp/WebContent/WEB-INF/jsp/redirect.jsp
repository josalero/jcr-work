<%@page import="java.util.Locale"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.bac.oee.struts.ViewConstants"%>

<%

	String url = (String)request.getAttribute(ViewConstants.REDIRECT_URL);

	if (url != null){
		response.sendRedirect(url);
	}
	
%>

<html>

	<head>
	
		<title>Bank of America | Redirect Page</title>
	</head>

	<body>	
	</body>

</html>