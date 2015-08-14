<%@ include file="/WEB-INF/jsp/common/include/include-taglibs.jsp"%>
<%@tag language="java" trimDirectiveWhitespaces="true" %>

<%@ attribute name="pageUIData" type="com.bac.oee.model.ui.content.PageUIData" required="true"%>
 	<!-- Panel 3: "We're better when we're connected" -->
   <c:forEach var="panel" items="${pageUIData.panelList}" varStatus="panelStatus">
	  	<c:if test="${panelStatus.count == 1}">
             	<c:forEach var="section" items="${panel.sectionList}" varStatus="sectionStatus">
      				<c:if test="${sectionStatus.count == 1}">
      				
		      				    <div class="wrapper">
		      				    
								      <div class="bgFakeNext color-sandStone message">
								        <div class="color-red"></div>
								        <div class="color-lightBlue"></div>
								        <div class="color-darkBlue"></div>
								      </div>
			
									      <div id="why-do-we-do-what-we-do" class="vPanel panel color-sandStone" data-galabel="${panel.name}"  data-rightnavtitle="${panel.name}">
									        <div class="backgroundOverlay"></div>
									        <div class="contents">
									          <div class="container">
									            <div class="paddingForHeader">									      
									              
									              	<c:forEach var="uiTile" items="${section.tileList}" varStatus="tileStatus">
									            		 <img src="${uiTile.path}"   alt="${uiTile.altText}" /> 
													</c:forEach>
									            </div>
									          </div>
									        </div>
									      </div>
							    
							    </div>
					  </c:if>
	  			</c:forEach>
	  	</c:if>
</c:forEach>
