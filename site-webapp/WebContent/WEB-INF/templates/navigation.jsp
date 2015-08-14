<%@page trimDirectiveWhitespaces="true"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@page import="com.amex.srt.struts.ViewConstants"%>
<%@page import="com.amex.srt.AppConstants"%>
<%@page import="com.bac.oee.model.PageData"%>

<%
	String basePath = request.getContextPath();
	PageData pageData = (PageData)request.getAttribute(ViewConstants.PAGE_DATA);
	
	String siteCategoryId = "";
	
	String locale = (String)session.getAttribute(AppConstants.LOCALE);

	
%>

				<script>
					var nav_options = {
						"basePath" : "<%=basePath%>",
						"siteCategoryId" : "<%=siteCategoryId%>"
					};
				</script>
				
				  <!-- Map: Persistent Navigation along Right Edge of Screen -->
				  <div id="map">
				    <ul>
				    </ul>
				  </div>
		
				  <div id="rightbottom">
				    <a href="#!"></a>
				  </div>
				
				  <div id="scrollBar">
				    <a href="#" id="scrollHandle"></a>
				  </div>
						
				