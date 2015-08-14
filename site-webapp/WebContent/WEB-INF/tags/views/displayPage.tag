<%@ include file="/WEB-INF/jsp/common/include/include-taglibs.jsp"%>
<%@tag language="java" trimDirectiveWhitespaces="true" %>

<%@ attribute name="subPageUIData" type="com.bac.oee.model.ui.content.PageUIData" required="true"%>

		<c:if test="${subPageUIData.template == 'landing_page'}">
			<pages:landingPage pageUIData="${subPageUIData}"/>
	    </c:if>
	    
	    <c:if test="${subPageUIData.template == 'message_page'}">
			<pages:messagePage pageUIData="${subPageUIData}"/>
	    </c:if>
	    
	    <c:if test="${subPageUIData.template == 'connected_page'}">
			<pages:connectedPage pageUIData="${subPageUIData}"/>
	    </c:if>
	    
	
		<c:if test="${subPageUIData.template == 'commitment_page'}">
			<pages:commitmentPage pageUIData="${subPageUIData}"/>
	    </c:if>
	
		<c:if test="${subPageUIData.template == 'gallery_page'}">
			<pages:galleryPage pageUIData="${subPageUIData}"/>		
	    </c:if>