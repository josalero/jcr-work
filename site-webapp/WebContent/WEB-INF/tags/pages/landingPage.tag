<%@ include file="/WEB-INF/jsp/common/include/include-taglibs.jsp"%>
<%@tag language="java" trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageUIData" type="com.bac.oee.model.ui.content.PageUIData" required="true"%>

	    <c:forEach var="panel" items="${pageUIData.panelList}">		
		    <div class="wrapper">
		      <div class="bgFakeNext color-darkSandStone"></div>
		      <div id="tagline" class="vPanel panel color-sandStone"  data-rightnavtitle="${panel.name}"  data-galabel="${panel.name}">
		        <div class="color-red"></div>
		        <div class="color-lightBlue"></div>
		        <div class="color-darkBlue"></div>
		        <div id="tagline-flag-part1" class="backgroundOverlay"></div>
		        <div id="tagline-flag-part2" class="backgroundOverlay"></div>
		        <div id="tagline-flag-part3" class="backgroundOverlay"></div>
		        <div id="tagline-flag-part4" class="backgroundOverlay"></div>        
		       	<misc:introAnimation pageUIData="${pageData.indexPageUIData}"></misc:introAnimation>	
				<c:if test="${not empty panel.sectionList[0]}">
					<section:tableOfContents sectionUIData="${panel.sectionList[0]}"></section:tableOfContents>
				</c:if>
		      </div>
		    </div>
	   	</c:forEach>