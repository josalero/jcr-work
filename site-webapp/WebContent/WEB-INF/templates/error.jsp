<%@page import="java.util.Locale"%>
<%@page import="com.amex.srt.struts.ViewConstants"%>
<%@page import="com.amex.srt.AppConstants"%>
<%@page import="com.bac.oee.model.PageData"%>
<%@ include file="/WEB-INF/jsp/include/imports-taglibs.jsp" %>
<%
	Boolean devMode = Boolean.TRUE;
	
	if (request.getParameter("PROD") != null) {		
		pageContext.setAttribute("devMode", Boolean.FALSE);
	}
	else {
		pageContext.setAttribute("devMode", devMode);
	}
	

%>
<!DOCTYPE html>
	<head>
			
		<tiles:insertAttribute name="common-page-head" />
	

		<c:choose>
			<c:when test="${not empty pageData.htmlTitle}">
				<title>${pageData.htmlTitle}</title>
			</c:when>
			<c:otherwise>
				<title><tiles:insertAttribute name='title' ignore='true'/></title>  
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
		
		<script type="text/javascript">
		
			function onPageLoad() {
				alert("Page Loaded");
			}

		    var page_options = {
		    	
		    	"additionalCSS" : "<tiles:getAsString name='additionalCSS'/>"
		    };
			
		</script>
		
		<link rel="stylesheet"
			href="/css/custom-fonts.css" type="text/css" />

		<link rel="stylesheet"
			href="/jmvc/flag/navigation/navigation.css"
			type="text/css" />
			
		<NOSCRIPT>
		
			<link rel="stylesheet"
				href="/jmvc/flag/css/noscript.css"
				type="text/css" />
				
		</NOSCRIPT>

		<c:if test="${devMode}">					
			<link rel="stylesheet"
	            href="/jmvc/flag/css/main.css"
	            type="text/css" />
		</c:if>	

		<c:if test="${!devMode}">					
			<link rel="stylesheet"
	            href="/jmvc/flag/production.css"
	            type="text/css" />
		</c:if>	
		
		<!-- Start Webmaster Tools Meta Tags Verification Code, DO NOT REMOVE meta tags below this line --> 
		<meta name="google-site-verification" content="w_aPC7FY1aXXQNDVNWnsN5fw6TKKB9XIUhqNJ76oAdE" /> 
		<meta name="msvalidate.01" content="DCFA7922C7EDFBFFCAEE5E3C7ECA5C8C" /> 
		<!-- End Webmaster Tools Meta Tags Verification Code, DO NOT REMOVE meta tags above this line -->
	</head>
	
	<!--HTML body starts-->
	<body>
		<div class="error-main">
			<!-- place holder for including the page header -->
			<tiles:insertAttribute name="header" />
			<!-- place holder for including the page navigation menu -->
			<tiles:insertAttribute name="navigation" />
			<!-- place holder for including the page body -->
			<tiles:insertAttribute name="body" />
			<!-- place holder for including the page footer -->
			<tiles:insertAttribute name="footer" />
		</div>

		
		<c:if test="${!devMode}">					
			<script type='text/javascript' src='/jmvc/steal/steal.production.js?flag'></script>
		</c:if>
	
		<c:if test="${devMode}">
			<script type='text/javascript' src='/jmvc/steal/steal.js?flag'></script>
		</c:if>		
		

	</body> 
	
	<!--HTML body ends -->
</html>