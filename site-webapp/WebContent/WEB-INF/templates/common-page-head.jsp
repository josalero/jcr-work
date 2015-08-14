<%@page import="com.bac.oee.struts.ViewConstants"%>
<%@page import="com.amex.srt.AppConstants"%>
<%@page import="com.bac.oee.model.PageData"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page trimDirectiveWhitespaces="true"%>

<c:choose>
	<c:when test="${not empty pageData.htmlTitle}">
		<title>${pageData.htmlTitle}</title>
	</c:when>
	<c:otherwise>
		<title>About Bank of America | Make Opportunity Possible</title>  
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${not empty pageData.metaKeywords}">
		<meta name="keywords" content="${pageData.metaKeywords}" />
	</c:when>
	<c:otherwise>
		<meta name="keywords" content="<tiles:insertAttribute name='keywords' ignore='true'/>" />
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${not empty pageData.metaDescription}">
		<meta name="description" content="${pageData.metaDescription}" />
	</c:when>
	<c:otherwise>
		<meta name="description" content="<tiles:insertAttribute name='description' ignore='true' />" />
	</c:otherwise>
</c:choose>

		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		
	<meta name="viewport" content="width=1275,user-scalable=yes" />
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-mobile-web-app-status-bar-style"
			content="black-translucent" />
			
<c:if test="${noFollowFlag}">
			<!-- Robots.txt Rules -->
		<meta name="ROBOTS" content="INDEX, NOFOLLOW" />
</c:if>

<c:if test="${noIndexFlag}">
			<!-- Robots.txt Rules -->
		<meta name="ROBOTS" content="NOINDEX, NOFOLLOW" />
</c:if>
		
	<!--  Favicon.ico inclusion -->
	<link rel="icon" href="/images/favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="/images/favicon.ico" type="image/x-icon" />
