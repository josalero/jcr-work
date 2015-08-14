<%@ include file="/WEB-INF/jsp/common/include/include-taglibs.jsp"%>
<%@tag language="java" trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageUIData" type="com.bac.oee.model.ui.content.PageUIData" required="true"%>

		  <div id="tagline-intro" class="centered800" >
		      <c:forEach var="introAnimation" items="${pageUIData.introAnimationList}" varStatus="introAnimationStatus">			        	
				 	<div id="intro-part${introAnimationStatus.count}">${introAnimation.segmentText}</div>
		      </c:forEach>
		  </div>