<%@ include file="/WEB-INF/jsp/common/include/include-taglibs.jsp"%>
<%@tag language="java" trimDirectiveWhitespaces="true" %>
 <%@ attribute name="uiTile" type="com.bac.oee.model.ui.content.tiles.UITile" required="true"%>
 <%@ attribute name="tilePosition" type="java.lang.String" required="true"%>

<c:if test="${tilePosition == 'first'}">
 <li class="first"><a href="#">${uiTile.cmsId}</a></li>
</c:if>

<c:if test="${tilePosition == 'last'}">
 <li class="last"><a href="#">${uiTile.cmsId}</a></li>
</c:if>

<c:if test="${tilePosition == 'middle'}">
 <li><a href="#">${uiTile.cmsId}</a></li>
</c:if>

  