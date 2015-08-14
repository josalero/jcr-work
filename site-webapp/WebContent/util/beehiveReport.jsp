<%@page import="com.amex.srt.TileModel"%>
<%@page import="com.amex.srt.BeehiveModel"%>
<%@page import="com.amex.srt.ContentRulesManager"%>
<%@page import="com.amex.srt.ContentManager"%>
<%@page import="com.amex.srt.ContentLoader"%>
<%@page
	import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	Locale locale = request.getLocale();
	String localeString = locale.getLanguage() + '-' + locale.getCountry();

	ApplicationContext applicationContext = WebApplicationContextUtils
			.getRequiredWebApplicationContext(pageContext.getServletContext());

	ContentManager contentManager = (ContentManager) applicationContext.getBean("contentManager");
	ContentRulesManager contentRulesManager = (ContentRulesManager) applicationContext
			.getBean("contentRulesManager");

	String sessionID = session.getId();
	String brand = request.getParameter("brand");

	if (brand == null) {
		brand = "bank-of-america";
	}

	List<TileModel> queryResults = contentRulesManager.generateBeehiveTileQueryResults(localeString, sessionID,
			"cluster1", brand, "1024x768");

	BeehiveModel beehiveModel = contentRulesManager.generateBeehiveModel(localeString, sessionID, "cluster1",
			brand, "1024x768");

	pageContext.setAttribute("queryResults", queryResults);
	pageContext.setAttribute("beehiveModel", beehiveModel);

	pageContext.setAttribute("cluster", beehiveModel.getClusters().get(0));
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>Enterprise Site: Beehive Report</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta name="ROBOTS" content="NOINDEX, NOFOLLOW"/>

<style type="text/css">
body {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 10px;
}

h1 {
	font-size: 16px;
}

h2 {
	font-size: 14px;
}

table,td {
	font-size: 11px;
	border: 1px solid black;
}
</style>

</head>

<body>

	<h2>Beehive Report:</h2>

	<table>
		<tr>
			<td><b>Session ID:</b>
			</td>
			<td><%=session.getId()%></td>
		</tr>
		<tr>
			<td><b>Brand:</b>
			</td>
			<td><%=brand%></td>
		</tr>
	</table>

	<hr />

	<h3>Raw Query Execution:</h3>

	<table border="1" cellpadding="5">
		<tr>
			<th>CMS ID</th>
			<th>ID</th>
			<th>Type</th>
			<th>Published Date</th>
			<th>Featured</th>
			<th>Featured in Period</th>
			<th>Featured From</th>
			<th>Featured To</th>
			<th>Brand</th>
			<th>Primary Headline</th>
			<th>Short Headline</th>
		</tr>

		<c:forEach var="tileModel" items="${queryResults}">
			<tr>
				<td>${tileModel.cmsId}&nbsp;</td>
				<td>${tileModel.id}&nbsp;</td>
				<td>${tileModel.contentType}&nbsp;</td>
				<td>${tileModel.publishedDate}&nbsp;</td>
				<td>${tileModel.featured}&nbsp;</td>
				<td>${tileModel.featuredInPeriod}&nbsp;</td>
				<td>${tileModel.featuredFromDate}&nbsp;</td>
				<td>${tileModel.featuredToDate}&nbsp;</td>
				<td>${tileModel.brand}&nbsp;</td>
				<td>${tileModel.primaryHeadline}&nbsp;</td>
				<td>${tileModel.shortHeadline}&nbsp;</td>
			</tr>
		</c:forEach>
	</table>


	<hr />

	<h3>Resulting Beehive:</h3>

	<table border="1" cellpadding="5">
		<tr>
			<th>Cell ID</th>
			<th>CMS ID</th>
			<th>Type</th>
			<th>Subtype</th>
			<th>Brand</th>
			<th>X</th>
			<th>Y</th>
			<th>Z</th>
			<th>Small</th>
			<th>Width</th>
			<th>Height</th>
			<th>Medium</th>
			<th>Width</th>
			<th>Height</th>
		</tr>

		<c:forEach var="cell" items="${cluster.cells}">
			<tr>
				<td>${cell.id}&nbsp;</td>
				<td>${cell.cmsId}&nbsp;</td>
				<td>${cell.type}&nbsp;</td>
				<td>${cell.subtype}&nbsp;</td>
				<td>${cell.brand}&nbsp;</td>
				<td>${cell.position.x}&nbsp;</td>
				<td>${cell.position.y}&nbsp;</td>
				<td>${cell.position.z}&nbsp;</td>
				<td>${cell.thumbnails.small.image}&nbsp;</td>
				<td>${cell.thumbnails.small.width}&nbsp;</td>
				<td>${cell.thumbnails.small.height}&nbsp;</td>
				<td>${cell.thumbnails.medium.image}&nbsp;</td>
				<td>${cell.thumbnails.medium.width}&nbsp;</td>
				<td>${cell.thumbnails.medium.height}&nbsp;</td>
			</tr>
		</c:forEach>


	</table>

</body>

</html>
