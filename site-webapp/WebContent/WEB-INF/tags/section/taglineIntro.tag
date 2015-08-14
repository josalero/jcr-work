<%@ include file="/WEB-INF/jsp/common/include/include-taglibs.jsp"%>
<%@tag language="java" trimDirectiveWhitespaces="true" %>
<%@ attribute name="sectionUIData" type="com.bac.oee.model.ui.content.SectionUIData" required="true"%>
<%@ attribute name="introAnimationUIData" type="com.bac.oee.model.ui.content.IntroAnimationUIData" required="true"%>

<div id="tagline-intro" class="centered800">
	<c:forEach var="paragraph" items="${sectionUIData.paragraphList}"  varStatus="paragraphStatus">		
		<c:if test="${not empty paragraph}">
		          <div id="intro-part${paragraphStatus.count}">${paragraph.heading}</div>
		</c:if>								
	</c:forEach>
</div>