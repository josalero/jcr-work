<%@page import="java.util.Locale"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.bac.oee.model.PageData"%>

<%@ page trimDirectiveWhitespaces="true"%>

<%@ include file="/WEB-INF/jsp/common/include/include-taglibs.jsp"%>

			<!-- Parallax Panels Begin Here -->
			<div id="container">
			    <div id="cart">
					<c:if test="${pageData.indexPageUIData.template == 'index'}">
						<c:forEach var="pageUIData" items="${pageData.indexPageUIData.pageList}">
							<views:displayPage subPageUIData="${pageUIData}"/>
						</c:forEach>
					</c:if>
				</div>
			</div>			 
			<!--  End of container -->