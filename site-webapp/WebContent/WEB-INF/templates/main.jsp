<%@page trimDirectiveWhitespaces="true"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%><%@ taglib prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib
	uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%><%@ taglib
	prefix="s" uri="/struts-tags"%>
<!doctype html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]>    <html class="no-js lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang="en"> <!--<![endif]-->
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>Bank of America | Employee Experience</title>	
	<meta name="keywords" content="about bank of america employee experience" />
	<meta name="description" content="description goes here." />
</head> 	
 
<!--HTML body starts-->
<body id="home" >

	<div>
		<!-- place holder for including the page header -->
		<tiles:insertAttribute name="header" />
		<!-- place holder for including the page body -->
		<tiles:insertAttribute name="body" />
		<!-- place holder for including the page footer -->
		<tiles:insertAttribute name="footer" />
	</div>
	
</body>
<!--HTML body ends -->

</html>