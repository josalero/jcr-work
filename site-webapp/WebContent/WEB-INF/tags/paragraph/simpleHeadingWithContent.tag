<%@ include file="/WEB-INF/jsp/common/include/include-taglibs.jsp"%>
<%@tag language="java" trimDirectiveWhitespaces="true" %>

<%@ attribute name="paragraphUIData" type="com.bac.oee.model.ui.content.ParagraphUIData" required="true"%>

		<c:if test="${not empty paragraphUIData.heading}">
		<h2 class="text-red">${paragraphUIData.heading}</h2><p>${paragraphUIData.content}</p>
		</c:if>							
      