<%@ include file="/WEB-INF/jsp/include/imports-taglibs.jsp"%>

<%
	String hostAndContextName = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

	<!-- HTML Header start -->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><tiles:insertAttribute name="title" ignore="true" />
		</title>
		<meta name="keywords"
			content="<tiles:insertAttribute name="keywords" ignore="true" />" />
		<meta name="description"
			content="<tiles:insertAttribute name="description" ignore="true" />" />

		<!-- iPad Setup -->
		<meta name="viewport"
			content="minimum-scale=1.0, maximum-scale=1.0,width=device-width, user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<meta name="apple-mobile-web-app-status-bar-style"
			content="black-translucent" />

		<!-- Include all the CSS Files -->
		<link rel="stylesheet"
			href="/css/main.css"
			type="text/css" />
		<tiles:insertAttribute name="additionalCSS" ignore="true" />
		<!--[if IE 6]>
	    	<link rel="stylesheet" href="/css/ie6.css" type="text/css"  />
	    <![endif]-->
		<!--[if IE 7]>
	    	<link rel="stylesheet" href="/css/ie7.css" type="text/css"  />
	    <![endif]-->
		<!--[if IE 8]>
	    	<link rel="stylesheet" href="/css/ie8.css" type="text/css"  />
	    <![endif]-->

		<c:choose>
		    <c:when test="${empty param.PROD}">
				<script type='text/javascript' src='/jmvc/steal/steal.js?flag'></script>
		    </c:when>
		    <c:otherwise>
				<!--  The following is used for production deployment -->
				<script type='text/javascript' src='/jmvc/steal/steal.production.js?flag'></script>
		    </c:otherwise>
		</c:choose>

	    <script type="text/javascript">
			var hostAndContextName = "<%=hostAndContextName%>";
			var pageID = "struts2IndexPage";
			
			var sessionVars = {
				"brand" : "<%=brand%>",
				"sessionID" : "<%=sessionID%>",
				"locale" : "<%=locale%>"
			};
		</script>

	</head>
	<!-- HTML Header ends -->

	<!--HTML body starts-->
	<body>
		<div>

			<!-- place holder for including the page header -->
			<tiles:insertAttribute name="header" />
			<!-- place holder for including the page navigation menu -->
			<tiles:insertAttribute name="navigation" />
		</div>
	</body>
	<!--HTML body ends -->
</html>