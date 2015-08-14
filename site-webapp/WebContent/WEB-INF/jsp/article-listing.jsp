<%@ include file="/WEB-INF/jsp/include/imports-taglibs.jsp"%>

<html>
	<head>
	
		<!-- Robots.txt Rules -->
		<meta name="ROBOTS" content="NOINDEX, FOLLOW" />
		
		<title>Bank of America | Page Not Found</title>
	</head>

	<body>	
		<div id="article-listing">
		<c:forEach	var="article" items="${articleListing}">
			<a href="${article.moreLink.linkUrl}">${article.headline}</a><br/>
		</c:forEach>
		</div>
	</body>
</html>	
