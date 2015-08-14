<%@ include file="/WEB-INF/jsp/common/include/include-taglibs.jsp"%>
<%@tag language="java" trimDirectiveWhitespaces="true" %>
<%@ attribute name="sectionUIData" type="com.bac.oee.model.ui.content.SectionUIData" required="true"%>

	<c:if test="${not empty sectionUIData.paragraphList[0]}">
	        <div class="contents">
	          <div id="tableOfContents" class="centered800 paddingForHeader">
	            <div id="scrollPrompt"></div>
	          
	          	<c:if test="${not empty sectionUIData.paragraphList[0].heading}">
	            	<h2>${sectionUIData.paragraphList[0].heading}</h2>
	            </c:if>
	            
	            <ul><c:forEach var="imageUITile" items="${sectionUIData.tileList}" varStatus="uiTileStatus"><c:choose><c:when test="${uiTileStatus.count eq 1}"><!--
	
	              --><li id="toc-commitments" class="left">
	              	<a href="#!${imageUITile.linkUrl}" data-gacategory="Overview" data-gaaction="Select" data-galabel="Tile-Our-Story"
	              		><img src="${imageUITile.path}" alt="${imageUITile.altText}"/><span>${imageUITile.linkText}</span></a></li></c:when><c:when test="${uiTileStatus.count eq 2}"><!--
	
	              --><li id="toc-werebetterwhenwereconnected" class="middle">
	              	<a href="#!${imageUITile.linkUrl}" data-gacategory="Overview" data-gaaction="Select" data-galabel="Tile-We-Are-Better-When-We-Are-Connected"
	              		><img src="${imageUITile.path}"  alt="${imageUITile.altText}"/><span>${imageUITile.linkText}</span></a></li></c:when><c:when test="${uiTileStatus.count eq 3}"><!--
	
	              --><li id="toc-gallery" class="right">
	              	<a href="#!${imageUITile.linkUrl}" data-gacategory="Overview" data-gaaction="Select" data-galabel="Tile-Gallery"
	              		><img src="${imageUITile.path}"  alt="${imageUITile.altText}"/><span>${imageUITile.linkText}</span></a></li><!--
	
	            --></c:when></c:choose></c:forEach></ul>			     
	          </div>
			</div>
	</c:if>