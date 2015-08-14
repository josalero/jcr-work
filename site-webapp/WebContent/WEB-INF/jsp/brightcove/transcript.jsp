<%@ include file="/WEB-INF/jsp/include/imports-taglibs.jsp"%>

<%
	String hostAndContextName = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<!-- HTML Header start -->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Video Transcript</title>

		<!-- iPad Setup -->
		<meta name="viewport"
			content="minimum-scale=1.0, maximum-scale=1.0,width=device-width, user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<meta name="apple-mobile-web-app-status-bar-style"
			content="black-translucent" />

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
		</script>

	</head>
	<!-- HTML Header ends -->

	<!--HTML body starts-->
	<body>
		<div id="video-transcript">
		</div>
		<script type='text/javascript'>
				
			var transcriptOptions = {
				"videoId" : "${videoTranscript}"
			};			
				
		</script>
	</body>
	<!--HTML body ends -->
</html>