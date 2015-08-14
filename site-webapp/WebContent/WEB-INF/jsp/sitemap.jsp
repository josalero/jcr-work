<%@ include file="/WEB-INF/jsp/include/imports-taglibs.jsp"%>

<style>
	#sitemap-container {
		padding: 2em;
		padding-bottom:90px;
		background: #FFF;
	}
	#sitemap-container .sitemap-col1 {
		width: 20%;
		float: left;
		padding-right: 85px;
	}
	#sitemap-container .sitemap-col2 {
		width: 40%;
		float: left;
		padding-right: 85px;
	}
	#sitemap-container .sitemap-col3 {
		width: 25%;
		float: left;
		padding-right: 0px;
	}
	.sitemap-header{
		color: #AD1A10;
		font-weight: bold;
		border-bottom: 1px solid #E1E1E1;
		padding-bottom: 10px;
	}
	.sitemap-header a{
		text-decoration: none;
		color: #AD1A10;
	}
	.sitemap-header a:visited{
		color: #AD1A10;
	}
	.sitemap-header2{
		color: #AD1A10;
		font-weight: bold;
		border-bottom: 1px solid #E1E1E1;
		padding-top: 20px;
		padding-bottom: 10px;
	}
	.sitemap-header2 a{
		text-decoration: none;
		color: #AD1A10;
	}
	.sitemap-ul{
		list-style-type: none;
		
	}
	.sitemap-ul li{
		list-style-type: none;
		padding-left: 10px;
		line-height: 160%;
	}
	.sitemap-ul li a{
		text-decoration: none;
		color:#515151;
		font-family:'FranklinGothicMedium';
	}
	.sitemap-ul li a:hover{
		text-decoration: none;
		color:#AD1A10;
		font-family:'FranklinGothicMedium';
		cursor: hand;
	}
	.sitemap-headline{
		margin-top: 10px;
		font-family:'FranklinGothicMedium';
		font-size: 110%;	
		color:#515151;
		font-weight: bold;
	}
	
	
</style>

<div id="sitemap-container">
	<h1 style="padding-bottom: 1em; color: #AD1A10;">SITE MAP</h1>
	<c:if test="${!empty siteNavigationData && !empty siteNavigationData.primaryNav && !empty siteNavigationData.primaryNav.navItems && !empty siteNavigationData.primaryNav.navItems.navItemList}">
		<c:forEach var="column" items="${siteNavigationData.primaryNav.navItems.navItemList}" varStatus="index">
			<c:set var="columnValue" value="${fn:toLowerCase(column.name)}" />
			
			<c:if test="${columnValue eq 'our story'}">
				<div class="sitemap-col1">
					<p class="sitemap-header"><a href="${column.link.linkUrl}">Our Story &#8250;</a><p>
					<ul class="sitemap-ul">
						<c:forEach var="columnContainer" items="${column.navItemColumnList}">
							<c:forEach var="columnItem" items="${columnContainer.navItemList}">
								<li style="padding-top: 10px;">
									<a href="${columnItem.link.linkUrl}" class="sitemap-headline">${columnItem.name}&nbsp;&#8250;</a>
									<ul>
										<c:forEach var="subColumnItem" items="${columnItem.navItemList}">
											<li><a href="${subColumnItem.link.linkUrl}" >${subColumnItem.name}</a></li>
										</c:forEach>
									</ul>
								</li>
							</c:forEach>
						</c:forEach>
					</ul>
					<div style="margin-top: 20px;">
						<p class="sitemap-header"><a href="${column.link.linkUrl}">Global Impact &#8250;</a><p>
						<ul class="sitemap-ul">
							<c:forEach var="column2" items="${siteNavigationData.primaryNav.navItems.navItemList}" varStatus="index2">
								<c:set var="columnValue2" value="${fn:toLowerCase(column2.name)}" />
								<c:if test="${columnValue2 eq 'global impact'}">
									<c:forEach var="columnContainer2" items="${column2.navItemColumnList}">
										<c:forEach var="columnItem2" items="${columnContainer2.navItemList}">
											<li style="padding-top: 10px;">
												<a href="${columnItem2.link.linkUrl}" class="sitemap-headline">${columnItem2.name}&nbsp;&#8250;</a>
												<ul>
													<c:forEach var="subColumnItem" items="${columnItem2.navItemList}">
														<li><a href="${subColumnItem.link.linkUrl}">${subColumnItem.name}</a></li>
													</c:forEach>
												</ul>
											</li>
										</c:forEach>
									</c:forEach>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</div>
			</c:if>
			
			<c:if test="${columnValue eq 'partnering locally'}">
				<div class="sitemap-col2">
					<p class="sitemap-header"><a href="${column.link.linkUrl}">Partnering Locally &#8250;</a><p>
					<div style="float: left; width: 300px; border: 0px solid black;">
						<p class="sitemap-headline">By Location:</p>
						<p class="sitemap-headline">Featured U.S. Metro Markets</p>
						<div style="float: left;">
							<c:if test="${!empty geoTaxonomy}">
								<ul class="sitemap-ul">	
									<c:if test="${!empty geoTaxonomy.geoAreaList}">
										<c:forEach var="geoArea" items="${geoTaxonomy.geoAreaList}">
											<c:if test="${!empty geoArea && geoArea.id eq 'us'}">
												<c:forEach var="country" items="${geoArea.countryList}">
													<c:if test="${!empty country && country.id eq 'us' && !empty country.metroAreas}">
														<c:set var="selectedCountry" value="${country}" scope="request"/>
													</c:if>
												</c:forEach>
											</c:if>
										</c:forEach>
									</c:if>
								</ul>
							</c:if>
						</div>
						<div style="float: left; margin-left: 20px;">
							<ul class="sitemap-ul">	
								<c:if test="${!empty geoTaxonomy.geoAreaList}">
									<c:forEach var="geoArea" items="${geoTaxonomy.geoAreaList}">
										<c:if test="${!empty geoArea && geoArea.id eq 'us'}">
											<c:forEach var="country" items="${geoArea.countryList}">
												<c:if test="${!empty country && country.id eq 'us' && !empty country.metroAreas}">
													<c:set var="selectedCountry" value="${country}" scope="request"/>
													<c:forEach var="metroArea" items="${country.metroAreas.metroAreaList}" varStatus="metroAreaIdx">
														<jsp:setProperty name="lptModelUIData" property="metroArea" value="${metroArea}"/>
														<c:if test="${metroAreaIdx.index >5}">
															<li><a href="${lptModelUIData.metroAreaUrl}">${metroArea.name}</a></li>
														</c:if>
													</c:forEach>
												</c:if>
											</c:forEach>
										</c:if>
									</c:forEach>
								</c:if>
							</ul>
						</div>
					</div>
					<div style="float: right; width: 180px; margin-left: 30px;  border: 0px solid black;">
						<p class="sitemap-headline">By Topic:</p>
						<ul class="sitemap-ul">
							<c:if test="${!empty siteNavigationData.topicTaxonomy && !empty siteNavigationData.topicTaxonomy.siteTaxonomyList}">
								<c:forEach var="topicSiteTaxonomy" items="${siteNavigationData.topicTaxonomy.siteTaxonomyList}">
									<c:if test="${!empty topicSiteTaxonomy && !empty topicSiteTaxonomy.topicList && topicSiteTaxonomy.id eq 'partnering-locally'}">
										<c:forEach var="topic" items="${topicSiteTaxonomy.topicList}">
											<jsp:setProperty name="lptModelUIData" property="topic" value="${topic}"/>
											<li><a href="${lptModelUIData.topicUrl}">${topic.name}</a></li>
										</c:forEach>
									</c:if>
								</c:forEach>
							</c:if>
						</ul>
					</div>
					<div style="float: left; width: 276px; border: 0px solid black;">
						<p class="sitemap-headline">U.S. States</p>
						<div style="float: left; border: 0px solid black;">
							<ul class="sitemap-ul">
								<c:if test="${!empty selectedCountry && !empty selectedCountry.states && !empty selectedCountry.states.stateList}">
									<c:forEach var="state" items="${selectedCountry.states.stateList}" varStatus="stateIdx">
										<jsp:setProperty name="lptModelUIData" property="state" value="${state}"/>
										<c:if test="${stateIdx.index <=25}">
											<li><a href="${lptModelUIData.stateUrl}">${state.name}</a></li>
										</c:if>
									</c:forEach>
								</c:if>
							</ul>
						</div>
						<div style="float: left; width: 100px; margin-left: 70px;  border: 0px solid black;">
							<ul class="sitemap-ul">
								<c:if test="${!empty selectedCountry && !empty selectedCountry.states && !empty selectedCountry.states.stateList}">
									<c:forEach var="state" items="${selectedCountry.states.stateList}" varStatus="stateIdx">
										<jsp:setProperty name="lptModelUIData" property="state" value="${state}"/>
										<c:if test="${stateIdx.index > 25}">
											<li><a href="${lptModelUIData.stateUrl}">${state.name}</a></li>
										</c:if>
									</c:forEach>
								</c:if>
							</ul>
						</div>
						
					</div>
					<div style="float: right; width: 180px; margin-left: 30px;  border: 0px solid black;">
						<p class="sitemap-headline">By Program</p>
						<ul class="sitemap-ul">								
							<c:if test="${!empty  siteNavigationData.programTaxonomy && !empty  siteNavigationData.programTaxonomy.siteTaxonomyList}">
								<c:forEach var="programSiteTaxonomy" items="${ siteNavigationData.programTaxonomy.siteTaxonomyList}">
									<c:if test="${!empty programSiteTaxonomy && !empty programSiteTaxonomy.programList && programSiteTaxonomy.id eq 'partnering-locally'}">
										<c:forEach var="program" items="${programSiteTaxonomy.programList}">
											<jsp:setProperty name="lptModelUIData" property="program" value="${program}"/>
											<li><a href="${lptModelUIData.programUrl}">${program.name}</a></li>
										</c:forEach>
									</c:if>
								</c:forEach>
							</c:if>
						</ul>
					</div>
					<div style="float: left; width: 300px; border: 0px solid black;">
						<p class="sitemap-headline">Global</p>
						<c:forEach var="columnContainer" items="${column.navItemColumnList}">
							<c:forEach var="columnItem" items="${columnContainer.navItemList}">
								<c:if test="${columnItem.name eq 'Global' }">
									<ul class="sitemap-ul">
										<c:forEach var="subColumnItem" items="${columnItem.navItemList}">
											<li><a href="${subColumnItem.link.linkUrl}" target="_self">${subColumnItem.name}</a></li>
										</c:forEach>
									</ul>
								</c:if>
							</c:forEach>
						</c:forEach>
					</div>
					
				</div>
			</c:if>
			
			<c:if test="${columnValue eq 'newsroom'}">
				<div class="sitemap-col3">
					<p class="sitemap-header"><a href="${column.link.linkUrl}" target="_self">Newsroom &#8250;</a><p>	
					<ul class="sitemap-ul">
						<c:forEach var="columnContainer" items="${column.navItemColumnList}" varStatus="newsroomIndex">
							<c:forEach var="columnItem" items="${columnContainer.navItemList}">
								<li>
									<a  href="${columnItem.link.linkUrl}" target="${columnItem.link.linkTarget.xmlValue()}">${columnItem.name}</a>
								</li>
							</c:forEach>
						</c:forEach>
					</ul>
					
					<div>
						<ul class="sitemap-ul">
							<c:forEach var="column2" items="${siteNavigationData.primaryNav.navItems.navItemList}" varStatus="index2">
								<c:if test="${column2.name eq 'Investor Relations'}">
									<p class="sitemap-header2"><a href="${column2.link.linkUrl}" target="_self">Investor Relations &#8250;</a><p>
									<c:forEach var="columnContainer2" items="${column2.navItemColumnList}">
										<c:forEach var="columnItem2" items="${columnContainer2.navItemList}">
											<li style="padding-top: 10px;">
												<a href="${columnItem2.link.linkUrl}" target="${columnItem2.link.linkTarget.xmlValue()}">${columnItem2.name}</a>
											</li>
										</c:forEach>
									</c:forEach>
								</c:if>
							</c:forEach>
						</ul>
					</div>
					<div>
						<p class="sitemap-header2"><a href="/en-us/global-impact/report-center.html" >Report Center &#8250;</a><p>
					</div>
					<div style="margin-top: 20px;">
						<p class="sitemap-header2"><a href="/en-us/global-impact/find-grants-sponsorships.html" >Find Grants & Sponsorships &#8250;</a><p>
					</div>
				</div>
			</c:if>
			
		</c:forEach>
	</c:if>
	<div class="clear"></div>
</div>

