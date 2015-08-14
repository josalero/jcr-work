<%@ include file="/WEB-INF/jsp/include/imports-taglibs.jsp"%>


<fieldset style="display:block; float:left; border:2px solid; width:100%; height:auto; margin-bottom:20px; padding:10px;">
	<legend style="margin:10px; padding:5px;">Filter By Tile Parameters</legend>
	<div style="display:block; float:left; width:100%; height:auto; ">
		<s:form action="beehive.html" method="post">
			<s:hidden name="queryData.option" value="enterprise:Tile"/>
		    <s:textfield name="queryData.cmsId" label="Cms ID" cssStyle="border:2px solid;margin: 10px 0" value=""/>
	
		    <s:textfield name="queryData.site" label="Site" cssStyle="border:2px solid;margin: 10px 0"/>
	
		    <s:textfield name="queryData.pillar" label="Pillar" cssStyle="border:2px solid;margin: 10px 0"/>
		    
		    <s:select label="Type"
		     		headerKey="-1" headerValue="Select Content Type"	list="tileNameList" 
					name="queryData.type" cssStyle="border:2px solid;margin: 10px 0" />
		    
		    <s:select label="Placement"
		     		headerKey="-1" headerValue="Select Placement"	list="placementNameList" 
					name="queryData.placement" cssStyle="border:2px solid;margin: 10px 0" />
					
		    <s:submit value="Filter" align="center" cssStyle="border:1px solid; padding:3px"/>
		    
		</s:form>
	</div>
</fieldset>

<fieldset style="display:block; float:left; border:2px solid; width:100%; height:auto; margin-bottom:20px; padding:10px;">
	<legend style="margin:10px; padding:5px;">Filter By Page</legend>
	<div style="display:block; float:left; width:100%; height:auto; ">
		<s:form action="beehive.html" method="post">
			<s:hidden name="queryData.option" value="enterprise:Page"/>
		    <s:textfield name="queryData.category" label="Category" cssStyle="border:2px solid;margin: 10px 0" value="our-story"/> 
	
		    <s:textfield name="queryData.pagePath" label="Page Path" cssStyle="border:2px solid;margin: 10px 0" value="our-story/our-team"/> 
	
		    <s:submit value="Filter" align="center" cssStyle="border:1px solid; padding:3px"/>
		    
		</s:form>
	</div>
</fieldset>
<strong style="margin-bottom:10px; border-bottom:1px solid; width:100%; float:left; padding:10px 0" >RESULTS:</strong>
<c:forEach var="tile" items="${beehiveTileList}">
<c:set var="linkPreview">
	
	<c:forEach var="placement" items="${tile.content.placements.placementList}" varStatus="status">
		<c:if test="${placement eq 'RIGHTRAIL' }">
			 &nbsp;<a href="preview.html?idTile=${tile.cmsId}">Preview</a><br/>
		</c:if>
	</c:forEach>  
	
</c:set>
<div style="color:#000000">
	<strong>ID:</strong> &nbsp; ${tile.id}<br/><br/>
	
	<strong>CMSID:</strong> &nbsp; ${tile.cmsId} ${linkPreview}<br/><br/>  
	<strong>Template:</strong> &nbsp; ${tile.template} <br/><br/>  
	<strong>Primary-Headline:</strong> &nbsp; ${tile.content.primaryHeadline} <br/><br/>
	<strong>Headline:</strong> &nbsp; ${tile.content.headline} <br/><br/>
	<strong>Short-Headline:</strong> &nbsp; ${tile.content.shortHeadline} <br/><br/>
	<strong>Abstract:</strong> &nbsp; ${tile.content.address} <br/><br/>
	<strong>Published Date:</strong> &nbsp; ${tile.metadata.publishedDate} <br/><br/>
	<strong>Address:</strong> &nbsp; ${tile.content.address} <br/><br/>
	<strong>FeaturedFromDate:</strong> &nbsp; ${tile.content.featuredInfo.featuredFromDate} <br/><br/>  
	<strong>FeaturedToDate:</strong> &nbsp; ${tile.content.featuredInfo.featuredToDate} <br/><br/>  
	<strong>CTA:</strong> <br/><br/>
	<c:forEach var="cta" items="${tile.content.ctaItems.ctaItemList}" varStatus="status">
		<strong style="margin-left:30px;text-decoration:underline;">Image${status.count}</strong> <br/><br/>
		<strong style="margin-left:30px">Link-URL:</strong> &nbsp; ${cta.linkUrl} <a href="${cta.linkUrl}" target="_new">Go</a><br/><br/>
		<strong style="margin-left:30px">Link-Text:</strong> &nbsp; ${cta.linkText} <br/><br/>
		<strong style="margin-left:30px">Link-Target:</strong> &nbsp; ${cta.linkTarget} <br/><br/>
		<strong style="margin-left:30px">ScreenReaderText:</strong> &nbsp; ${cta.screenReaderText} <br/><br/>
		
	</c:forEach>  
	<strong>Images:</strong> <br/><br/>
	<c:forEach var="image" items="${tile.content.images.imageList}" varStatus="status">
		<strong style="margin-left:30px;text-decoration:underline;">Image${status.count}</strong> <br/><br/>
		<strong style="margin-left:30px">ImageType:</strong> &nbsp; ${image.imageType} <br/><br/>
		<strong style="margin-left:30px">Path:</strong> &nbsp; ${image.path} <a href="${image.path}" target="_new">Show</a><br/><br/>
		<strong style="margin-left:30px">Alt-Text:</strong> &nbsp; ${image.altText} <br/><br/>
	</c:forEach>  
	<strong>Placements:</strong> <br/><br/>
	<c:forEach var="placement" items="${tile.content.placements.placementList}" varStatus="status">
		<strong style="margin-left:30px">${placement}</strong> <br/><br/>
	</c:forEach>  	
</div>
<hr style="margin-bottom:10px; border:1px solid" />
</c:forEach>