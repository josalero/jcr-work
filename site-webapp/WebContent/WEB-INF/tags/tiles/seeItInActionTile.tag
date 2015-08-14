<%@ include file="/WEB-INF/jsp/common/include/include-taglibs.jsp"%>
<%@tag language="java" trimDirectiveWhitespaces="true" %>

 <%@ attribute name="seeItInActionUITile" type="com.bac.oee.model.ui.content.tiles.UITile" required="true"%>
  <%@ attribute name="panelNumber" type="java.lang.Integer" required="yes"%>
 
 	 <li>
         <div class="textContainer" data-tilecmsid="${seeItInActionUITile.cmsId}" >
                   <img src="${seeItInActionUITile.logo.path}" alt="${seeItInActionUITile.logo.altText}"/>
                   	<c:if test="${panelNumber == 1 or panelNumber == 3 }">
	                   <div class="textBlock color-red">
	                 </c:if>
	                  <c:if test="${panelNumber == 2 or panelNumber == 4 }">
	                   <div class="textBlock color-darkBlue">
	                 </c:if>
	                     <img src="/images/see_it_in_action.png" class="seeitinaction" />
	                     <strong>${seeItInActionUITile.name}</strong>
	                     <div class="textBody">
	                     ${seeItInActionUITile.text}
	                     </div>
                   </div>
                   <div class="caption">
                     <p>${seeItInActionUITile.caption}</p>
                     <div class="pageCounter" text-darkSand></div>
                     <a href="#" class="expand" data-gacategory="Commitments${panelNumber}" data-gaaction="Expand" data-galabel="${seeItInActionUITile.cmsId}">
                     	<span class="accessibility-hidden">Expand Tile</span></a>
                   </div>
           </div>
       </li>