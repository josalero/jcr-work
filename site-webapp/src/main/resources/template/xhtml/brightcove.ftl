
<!-- Brightcove Player -->

<div id="div${parameters.get('videoID')}" style="display:block">

</div>

<#if parameters.get('script')?exists>
	<script type="text/javascript" src="${parameters.get('script')}"></script>
</#if>

<script type="text/javascript" >
	var ${parameters.get('templateLoadHandler')?default('onTemplateLoaded')}Var = newInstance(${parameters.get('videoID')}, "${parameters.get('playerType')}");
	createPlayer(${parameters.get('templateLoadHandler')?default('onTemplateLoaded')}Var,"${parameters.get('templateLoadHandler')?default('onTemplateLoaded')}")
</script>


<!-- End of Brightcove Player -->