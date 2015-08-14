<%@ include file="/WEB-INF/jsp/include/imports-taglibs.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags/modules/paragraphItems" prefix="pi"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<!-- HTML Header start -->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Related Video</title>

	</head>
	<!-- HTML Header ends -->

	<!--HTML body starts-->
	<body>
		<div id="related-video-player ${modelUIData.brand}">
		
			<pi:playerModule modelUIData="${relatedVideo}"/>
		</div>
	</body>
	<!--HTML body ends -->
</html>
