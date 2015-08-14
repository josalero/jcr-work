<%@ include file="/WEB-INF/jsp/common/include/include-taglibs.jsp"%>
<%@tag language="java" trimDirectiveWhitespaces="true" %>
<%@ attribute name="sectionUIData" type="com.bac.oee.model.ui.content.SectionUIData" required="true"%>
 <%@ attribute name="panelNumber" type="java.lang.Integer" required="yes"%>		
<div class="carousel">
          <ul>			            
          		<c:forEach var="uiTile" items="${sectionUIData.tileList}" varStatus="tileStatus">
					<views:displayTile uiTile="${uiTile}" forCarousel="true" panelNumber="${panelNumber}" />
				</c:forEach>
          </ul>
</div>