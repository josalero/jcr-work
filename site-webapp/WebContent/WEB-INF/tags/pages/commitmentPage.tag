<%@ include file="/WEB-INF/jsp/common/include/include-taglibs.jsp"%>
<%@tag language="java" trimDirectiveWhitespaces="true" %>

<%@ attribute name="pageUIData" type="com.bac.oee.model.ui.content.PageUIData" required="true"%>

    
  <!-- Panel 3: "Our Commitments" -->
    <div id="our-commitments" class="wrapper">
      <div class="bgFakeNext color-red"></div>
      <div id="commitments" class="hPanels panel">
        <div  id="commitmentBackgrounds"  class="hPanelWindow">
          <div class="color-sandStone"></div>
          <div class="color-red"></div>
          <div class="color-lightBlue"></div>
          <div class="color-darkBlue"></div>
        </div>
        <div class="backgroundOverlay"></div>

        <div class="hPanelWindow">
		        
			<c:forEach var="panel" items="${pageUIData.panelList}" varStatus="panelStatus">

				 <!--  Panel 2 displays heading /infobox/ video tile / video tiles -->
				 
					 <div id="commitment${panelStatus.count}" class="hPanel" data-rightnavtitle="${panel.name}" data-galabel="Commitments${panelStatus.count}">
			            <div class="window">
			              <div class="contents">
			              		
			              		<c:forEach var="section" items="${panel.sectionList}" varStatus="sectionStatus">
					             			<c:if test="${sectionStatus.count == 1}">
					             				<div class="commitmentContainer">
				             						<c:forEach var="uiTile" items="${section.tileList}" varStatus="tileStatus">
														<views:displayTile uiTile="${uiTile}"/>
								              		</c:forEach>									              										         
								                  <div class="commitmentContainerPadding">
				              						<c:forEach var="paragraph" items="${section.paragraphList}" varStatus="paragraphStatus">
											              		<paragraph:simpleHeadingWithContent paragraphUIData="${paragraph}"/>
								         			</c:forEach>
								                   <section:infoBox sectionUIData="${section}"></section:infoBox>
								                  </div>  
						            			</div>
						                	</c:if>
						                	
						            		<c:if test="${sectionStatus.count == 2}">
						            			<section:carousel sectionUIData="${section}" panelNumber="${panelStatus.count}"></section:carousel>
		            						</c:if>
						          </c:forEach>
			              </div>
			            </div>
			          </div>		
			</c:forEach>
			
        </div>

      </div>
    </div>

