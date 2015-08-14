<%@ include file="/WEB-INF/jsp/common/include/include-taglibs.jsp"%>
<%@tag language="java" trimDirectiveWhitespaces="true" %>
 <%@ attribute name="videoUITile" type="com.bac.oee.model.ui.content.tiles.UITile" required="true"%>
				  <div class="videoContainer big">
				          <a href="#" class="videoPlaceholder" data-videoid="${videoUITile.brightcoveId}"    data-videotitle="${videoUITile.headline}"   rel="${videoUITile.brightcoveId}">
				          <img src="${videoUITile.imageList[0].path}"  alt="${videoUITile.imageList[0].altText}" />
				          <div class="playIcon"></div></a>
				          <div class="videoBrandBar"></div>
				          <div class="tempTranscriptContainer" rel="${videoUITile.brightcoveId}">${videoUITile.transcript}</div>
				  </div>