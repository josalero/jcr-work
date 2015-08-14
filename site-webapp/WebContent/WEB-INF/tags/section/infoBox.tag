<%@ include file="/WEB-INF/jsp/common/include/include-taglibs.jsp"%>
<%@ attribute name="sectionUIData" type="com.bac.oee.model.ui.content.SectionUIData" required="true"%>
	<div class="commitmentBottomLinks">	
		<ul><c:forEach var="infobox" items="${sectionUIData.infoBoxList}" varStatus="infoboxStatus">
					<c:choose><c:when test="${infoboxStatus.count eq 1}"><!--
                        --><li class="consumers"><a href="#" data-hover="${infobox.infoText}">${infobox.anchor.text}</a></li></c:when><c:when test="${infoboxStatus.count eq 2}"><!--
                        --><li class="wealth"><a href="#" data-hover="${infobox.infoText}">${infobox.anchor.text}</a></li></c:when><c:when test="${infoboxStatus.count eq 3}"><!--
                        --><li class="corporate"><a href="#" data-hover="${infobox.infoText}">${infobox.anchor.text}</a></li></c:when><c:when test="${infoboxStatus.count eq 4}"><!--
                        --><li class="employees"><a href="#" data-hover="${infobox.infoText}">${infobox.anchor.text}</a></li><!--
                      --></c:when></c:choose></c:forEach></ul>
	</div>