<%@ include file="/WEB-INF/jsp/include/imports-taglibs.jsp"%>
<%
	final String context = request.getContextPath();
	pageContext.setAttribute("context", context);
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

<!-- Include all the CSS Files -->
<link rel="stylesheet" href="/css/gateway.css" type="text/css" />
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
		<script type='text/javascript'
			src='/jmvc/steal/steal.production.js?flag'></script>
	</c:otherwise>
</c:choose>
</head>
<!-- HTML Header ends -->

<!--HTML body starts-->
<body>
	<div>
		<h1>Gateway Data Feeds Processed</h1>
	</div>
	<c:choose>
		<c:when test="${empty gatewayFeedData}">
			<div class="error">Sorry, I had trouble fetching the gateway
				feed data that was processed.</div>
			<c:if test="${!empty gatewayErrorMessage}">
				<div class="error">
					<c:out value="${gatewayErrorMessage}" />
				</div>
			</c:if>
			<c:if test="${!empty gatewayErrorStackTrace}">
				<div class="error">
					<c:out value="${gatewayErrorStackTrace}" />
				</div>
			</c:if>
		</c:when>
		<c:otherwise>
			<div>
				<table>
					<th>
						<td>ID</td>
						<td>Data Feed Type</td>
						<td>Data</td>
						<td>Handle (Facebook and Twitter Only)</td>
						<td>Gateway Processed Date</td>
					</th>
					<c:forEach var="gatewayFeed" items="${gatewayFeedData}" varStatus="i">
						<c:when test="${i.index mod 2 == 0}">
							<tr class="regularRow">
						</c:when>
						<c:otherwise>
							<tr class="alternatingRow">
						</c:otherwise>
							<td><c:out value="${gatewayfeed.Id}" /></td>
							<td><c:out value="${gatewayfeed.DataFeedType}" /></td>
							<td><c:out value="${gatewayfeed.Data}" /></td>
							<td><c:out value="${gatewayfeed.Handle}" /></td>
							<td><c:out value="${gatewayfeed.CreatedDate}" /></td>
						</tr>
					</c:forEach>
					<tr>
						<c:choose>
							<c:when test="${currentPageNumber > 1}">
								<td><a href="${context}/admin/gateway.action?page=${currentPageNumber - 1}"></a>&lt;&lt; Previous</td>
							</c:when>
							<c:otherwise>
								<td>&lt;&lt; Previous</td>
							</c:otherwise>
						</c:choose>
						<td colspan="3">&nbsp;</td>
						<td><a href="${context}/admin/gateway.action?page=${currentPageNumber + 1}">Next &gt;&gt;</a></td>
					</tr>
				</table>
			</div>
		</c:otherwise>
	</c:choose>
</body>
<!--HTML body ends -->
</html>