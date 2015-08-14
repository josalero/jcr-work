<%@ include file="/WEB-INF/jsp/common/include/include-taglibs.jsp"%>
<%@tag language="java" trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageUIData" type="com.bac.oee.model.ui.content.PageUIData" required="true"%>

	<!-- Panel 5: "Gallery" -->
	<c:forEach var="panel" items="${pageUIData.panelList}" varStatus="panelStatus">				  
	    <div class="wrapper">
	      <div class="bgFakeNext color-lightBlue"></div>
	      <div id="gallery" class="vPanel panel color-red"  data-rightnavtitle="${panel.name}"  data-galabel="${panel.name}">
	        <div class="backgroundOverlay"></div>
	        <div class="contents">
	          <div class="bottom">
	            <div class="centered800">
		              	<c:forEach var="section" items="${panel.sectionList}" varStatus="sectionStatus">
		              		<c:if test="${sectionStatus.count eq 1}">
					              <ul class="thumbnails"><c:forEach var="videoUITile" items="${section.tileList}" varStatus="tileStatus">
					                <c:choose><c:when test="${tileStatus.count eq 1}"><li class="first"><a  href="#" class="videoPlaceholder" data-videoid="${videoUITile.brightcoveId}"   data-videotitle="${videoUITile.headline}"  ><img src="${videoUITile.imageList[0].path}"   alt="${videoUITile.imageList[0].altText}" /><div class="playIcon"></div></a><div class="tempTranscriptContainer" rel="${videoUITile.brightcoveId}">${videoUITile.transcript}</div></li><!--
					                --></c:when><c:when test="${tileStatus.count eq 2}"><li><a  href="#" class="videoPlaceholder" data-videoid="${videoUITile.brightcoveId}"   data-videotitle="${videoUITile.headline}"  ><img src="${videoUITile.imageList[0].path}"   alt="${videoUITile.imageList[0].altText}" /><div class="playIcon"></div></a><div class="tempTranscriptContainer" rel="${videoUITile.brightcoveId}">${videoUITile.transcript}</div></li><!--
					                --></c:when><c:when test="${tileStatus.count eq 3}"><li class="last"><a  href="#" class="videoPlaceholder" data-videoid="${videoUITile.brightcoveId}"   data-videotitle="${videoUITile.headline}" ><img src="${videoUITile.imageList[0].path}"   alt="${videoUITile.imageList[0].altText}" /><div class="playIcon"></div></a><div class="tempTranscriptContainer" rel="${videoUITile.brightcoveId}">${videoUITile.transcript}</div></li><!--
					                --></c:when><c:when test="${tileStatus.count eq 4}"><li class="first"><a  href="#" class="videoPlaceholder" data-videoid="${videoUITile.brightcoveId}"   data-videotitle="${videoUITile.headline}" ><img src="${videoUITile.imageList[0].path}"   alt="${videoUITile.imageList[0].altText}" /><div class="playIcon"></div></a><div class="tempTranscriptContainer" rel="${videoUITile.brightcoveId}">${videoUITile.transcript}</div></li><!--
					                --></c:when><c:when test="${tileStatus.count eq 5}"><li><a  href="#" class="videoPlaceholder" data-videoid="${videoUITile.brightcoveId}"   data-videotitle="${videoUITile.headline}"  ><img src="${videoUITile.imageList[0].path}"   alt="${videoUITile.imageList[0].altText}" /><div class="playIcon"></div></a><div class="tempTranscriptContainer" rel="${videoUITile.brightcoveId}">${videoUITile.transcript}</div></li><!--
					                --></c:when><c:when test="${tileStatus.count eq 6}"><li class="last"><a  href="#" class="videoPlaceholder" data-videoid="${videoUITile.brightcoveId}"   data-videotitle="${videoUITile.headline}" ><img src="${videoUITile.imageList[0].path}"   alt="${videoUITile.imageList[0].altText}" /><div class="playIcon"></div></a><div class="tempTranscriptContainer" rel="${videoUITile.brightcoveId}">${videoUITile.transcript}</div></li></c:when></c:choose>
					              </c:forEach></ul>
				            </c:if>
				        </c:forEach>
	            </div>
	          </div>
	        </div>
	      </div>
	    </div>
  </c:forEach>