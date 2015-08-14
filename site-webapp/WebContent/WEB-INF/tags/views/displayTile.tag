<%@ include file="/WEB-INF/jsp/common/include/include-taglibs.jsp"%>
<%@tag language="java" trimDirectiveWhitespaces="true" %>
<%@ attribute name="uiTile" type="com.bac.oee.model.ui.content.tiles.UITile" required="true"%>
<%@ attribute name="tilePosition" type="java.lang.String"%>
<%@ attribute name="forCarousel" type="java.lang.String"%>
<%@ attribute name="panelNumber" type="java.lang.Integer" %>

		<c:if test="${uiTile.type == 'VIDEOTILE' }">
		
			<c:if test="${uiTile.parentPage.template == 'message_page' }">
				<tiles:messageVideoTile videoUITile="${uiTile}"/>
			</c:if>
		
			<c:if test="${uiTile.parentPage.template == 'commitment_page' }">
					<c:if test="${forCarousel != 'true' }">
						<tiles:commitmentPagevideoTile videoUITile="${uiTile}"/>
					</c:if>
					<c:if test="${forCarousel == 'true' }">
						<tiles:carouselVideoTile videoUITile="${uiTile}"/>
					</c:if>
			</c:if>
		
			<c:if test="${uiTile.parentPage.template == 'gallery_page' }">
				<tiles:galleryVideoTile uiTile="${uiTile}" tilePosition="${tilePosition}"/>
			</c:if>
			
		</c:if>
		        		
		 <c:if test="${uiTile.type ==  'IMAGETILE'}">
		 	<c:if test="${uiTile.parentPage.template != 'message_page' }">
				<tiles:imageTile uiTile="${uiTile}" />
			</c:if>
		 </c:if>
		                                 
		  <c:if test="${uiTile.type ==  'SEEITINACTIONTILE'}">
			<tiles:seeItInActionTile seeItInActionUITile="${uiTile}" panelNumber="${panelNumber}"/>
		 </c:if>