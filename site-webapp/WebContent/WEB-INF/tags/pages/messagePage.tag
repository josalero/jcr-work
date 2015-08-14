<%@ include file="/WEB-INF/jsp/common/include/include-taglibs.jsp"%>
<%@tag language="java" trimDirectiveWhitespaces="true" %>

<%@ attribute name="pageUIData" type="com.bac.oee.model.ui.content.PageUIData" required="true"%>
    		<!-- Panel 2: "We're Better When We're Connected" -->

				<c:forEach var="panel" items="${pageUIData.panelList}" varStatus="panelStatus">
					  	<c:if test="${panelStatus.count == 1}">
				             	<c:forEach var="section" items="${panel.sectionList}" varStatus="sectionStatus">
			      				<c:if test="${sectionStatus.count == 1}">
										  <div class="wrapper">
   											  <div class="bgFakeNext color-sandStone"></div>
										      <div id="were-better-when-were-connected" class="vPanel panel color-sandStone"  data-rightnavtitle="${panel.name}"  data-galabel="${panel.name}">
										       <div class="backgroundOverlay"></div>
										       <div class="contents">
										        <div id="messageContents" class="centered800 paddingForHeader">
								  						<c:forEach var="paragraph" items="${section.paragraphList}" varStatus="paragraphStatus">
      												      <paragraph:messageHeading paragraphUIData="${paragraph}"/>
								          				</c:forEach>							
								          								
														<c:forEach var="uiTile" items="${section.tileList}" varStatus="tileStatus">
															<views:displayTile uiTile="${uiTile}"/>
									              		</c:forEach>	
									              		
									              		<c:forEach var="paragraph" items="${section.paragraphList}" varStatus="paragraphStatus">
								          					<paragraph:messageContent paragraphUIData="${paragraph}"/>
								          				</c:forEach>	
									             		<div id="signature">
											              <div id="signatureContainer">
											                <div class="printed">
											                      <div class="name">Brian T. Moynihan, Chief Executive Officer</div>
											                </div>
											              </div>
											            </div>		
					              		          </div>
										        </div>
										      </div>
										    </div>
  								  </c:if>
					  			</c:forEach>
					  	</c:if>
				</c:forEach>	  	
				
				
 