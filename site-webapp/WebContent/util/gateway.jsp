<%@ include file="/WEB-INF/jsp/include/imports-taglibs.jsp"%>
<%@page import="com.amex.srt.FeedService"%>
<%@page import="com.amex.srt.HttpUtils"%>
<%@page import="com.amex.srt.Feed"%>
<%@page import="com.bac.oee.struts.ViewConstants"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page
	import="org.fornax.cartridges.sculptor.framework.domain.PagedResult"%>
<%@page
	import="org.fornax.cartridges.sculptor.framework.domain.PagingParameter"%>
<%@page
	import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
	final String context = request.getContextPath();
	pageContext.setAttribute("context", context);
%>
<%
	ApplicationContext applicationContext = WebApplicationContextUtils
			.getRequiredWebApplicationContext(pageContext
					.getServletContext());
	FeedService feedService = (FeedService) applicationContext
			.getBean("feedService");

	String queryString = request.getQueryString();
	int currentPage = 1;
	String feedType = null;

	// if we don't have a null or empty querystring, split it up and try to acquire the page and the feed types
	if (StringUtils.isNotEmpty(queryString)) {
		Map<String, String> queryStringMap = HttpUtils
				.getQueryStringMap(queryString);

		if (queryStringMap
				.containsKey(ViewConstants.PAGE_QUERYSTRING_PARAMETER)) {
			String pageValue = queryStringMap
					.get(ViewConstants.PAGE_QUERYSTRING_PARAMETER);

			if (StringUtils.isNotEmpty(pageValue)) {
				try {
					currentPage = Integer.parseInt(pageValue);
				} catch (Exception e) {
				}
			}
		}

		if (queryStringMap
				.containsKey(ViewConstants.FEED_TYPE_QUERYSTRING_PARAMETER)) {
			feedType = queryStringMap
					.get(ViewConstants.FEED_TYPE_QUERYSTRING_PARAMETER);
		}
	}

	// if for some reason the page is less than 1, start at 1 again
	if (currentPage < 1) {
		currentPage = 1;
	}

	PagingParameter pagingParameter = PagingParameter.pageAccess(
			ViewConstants.DEFAULT_GATEWAY_DATAGRID_PAGE_SIZE,
			currentPage, true);
	List<Feed> feeds = null;
	PagedResult<Feed> pagedResult = null;
	
	try {
		pagedResult = feedService.findAll(null, pagingParameter);
	} catch (Exception e) {
		request.setAttribute(ViewConstants.GATEWAY_ERROR_MESSAGE,
				e.getMessage());
		request.setAttribute(
				ViewConstants.GATEWAY_ERROR_STACKTRACE,
				e.getStackTrace());
	}

	if (pagedResult != null) {
		feeds = pagedResult.getValues();
	}

	pageContext.setAttribute(ViewConstants.GATEWAY_FEED_DATA, feeds);
	pageContext.setAttribute(ViewConstants.GATEWAY_FEED_PAGE_NUMBER, currentPage);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<!-- HTML Header start -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><tiles:insertAttribute name="title" ignore="true" /></title>
<meta name="keywords" content="<tiles:insertAttribute name="keywords" ignore="true" />" />
<meta name="description" content="<tiles:insertAttribute name="description" ignore="true" />" />

<!-- Include all the CSS Files -->
<style type="text/css">
	body {
		font-family: Arial, Helvetica, sans-serif;
		font-size: 11px;
	}
	
	h1 {
		font-size: 16px;
	}
	
	h2 {
		font-size: 14px;
	}
	
	table, td {
		border: 1px solid black;
	}
	
	.headerRow {
		font-weight:bold;
	}
	
	.regularRow {
		background-color:#6495ed;
	}
	
	.alternatingRow {
		background-color:#e0ffff;
	}
</style>
</head>
<!-- HTML Header ends -->

<!--HTML body starts-->
<body>
	<div>
		<h1>Gateway Data Feeds Processed Page ${currentPageNumber}</h1>
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
					<tr class="headerRow">
						<td>ID</td>
						<td>Data Feed Type</td>
						<td>Data</td>
						<td>Handle (Facebook and Twitter Only)</td>
						<td>Gateway Processed Date</td>
					</tr>
					<c:forEach var="gatewayFeed" items="${gatewayFeedData}" varStatus="i">
						<c:choose>
							<c:when test="${i.index mod 2 == 0}">
								<tr class="regularRow">
							</c:when>
							<c:otherwise>
								<tr class="alternatingRow">
							</c:otherwise>
						</c:choose>						
						<td><c:out value="${gatewayFeed.id}" /></td>
						<td><c:out value="${gatewayFeed.dataFeedType}" /></td>
						<td><c:out value="${gatewayFeed.data}" /></td>
						<td><c:out value="${gatewayFeed.handle}" /></td>
						<td><c:out value="${gatewayFeed.createdDate}" /></td>
						</tr>
					</c:forEach>
					<tr>
						<c:choose>
							<c:when test="${currentPageNumber gt 1}">
								<td><a href="/util/gateway.jsp?page=${currentPageNumber - 1}">&lt;&lt; Previous</a></td>
							</c:when>
							<c:otherwise>
								<td>&lt;&lt; Previous</td>
							</c:otherwise>
						</c:choose>
						<td colspan="3">&nbsp;</td>
						<td><a
							href="/util/gateway.jsp?page=${currentPageNumber + 1}">Next
								&gt;&gt;</a></td>
					</tr>
				</table>
			</div>
		</c:otherwise>
	</c:choose>
</body>
<!--HTML body ends -->
</html>