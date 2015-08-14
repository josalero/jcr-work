steal('jquery',	'jquery/controller', 'jquery/view/ejs').
			then('http://admin.brightcove.com/js/BrightcoveExperiences.js', 
				 'http://admin.brightcove.com/js/APIModules_all.js').
			then('//oee/brightcove/player/style_players').
			then('./player.css','./views/player.ejs', './views/jaws.ejs' ).
			then('./views/transcript.ejs', function( $ ) {

	var createParameter = function( player, key, value ) {
		var parameter = brightcove.createElement("param");
		parameter.name = key;
		parameter.value = value;
		player.appendChild(parameter);
	};
	
	var hostAndContextName = hostAndContextName || document.location.protocol + "//" + document.location.host;

	/**
	 * @class Oee.Masthead
	 *
	 */
	$.Controller('Oee.Video',
	/** @Static */
	{
		defaults : {
			player : "defaultPlayer"
		}
	},
	/** @Prototype */
	{
		init : function() {

			steal.dev.log("Init new Oee.Video");
			if (!this.element.hasClass("text-with-tile")){
				this.element.css("cursor", "pointer");
			}else{
				this.element.find("img").css("cursor", "pointer");
			}

			steal.dev.log("Init(): Before -  this.options.playerType" + this.options.playerType);
			this.options.playerType = this.options.playerType || Oee.Masthead.defaults.player;
			steal.dev.log("Init():  After - this.options.playerType" + this.options.playerType);

		},
		
		"click" : function(el, ev) {
			$('#video-module-player').clone().attr('id','videoPlayerPopup').dialog({
				dialogClass: 'selectedCell', // selectedCell because we are borrowing classes from parallax beehive video cells
				height: 518,
				width: 877,
				minHeight: 518,
				minWidth: 877,
				//modal: true,
				draggable: false,
				resizable: false,
				create: function(){
					$(document.createElement('div')).addClass('whiteOut').css({'height': $('body').height() + 'px', 'filter': 'alpha(opacity=50)', 'width': $('body').width()}).hide().appendTo($('body'));
		        	
					if($.browser.msie && $.browser.version < 9) {
						$('.whiteOut').addClass('ie').show().bind('click', function(){$('.ui-dialog-titlebar-close').click()});
					} else {
						$('.whiteOut').fadeIn().bind('click', function(){$('.ui-dialog-titlebar-close').click()});
					}
					$('.whiteOut').show().bind('click', function(){$('.ui-dialog-titlebar-close').click()});
					
					//init video player
					$('#videoPlayerPopup').Oee_brightcove_player(this.options);
					$(".ada-transcript").click(function(e){
						e.preventDefault();						
					});
		        },
		        close: function(){
					$(this).remove();
					
					$('.whiteOut').fadeIn('fast',function(){$('.whiteOut').remove()});
		        }
			});

			// activate shareing
			/* social widget is not initializing in IE7 the Oee.SocialWidget is dependent 
			   on IDs and when using $.dialog() the IDs end up duplicated and IE7 chokes, 
			   other browsers are more forgiving, reproduce widget here */
/*
			if($('.share a').length == 0) {
				var share_fb = $(displayIcon(1,document.title,this.url,1,this.shortDomain,this.mmcIndicator,this.mmcValue,null,"Bank of America",null,this.cmIndicator,this.cmDynamicPageVariable,0,null)).addClass('share-fb').click(function(){meteor.tracking.track_conversion(meteorCode,{'name':'facebook-via-sharewidget'})}),
					share_tw = $(displayIcon(2,document.title,this.url,1,this.shortDomain,this.mmcIndicator,this.mmcValue,document.title,"Bank of America",null,this.cmIndicator,this.cmDynamicPageVariable,0,null)).addClass('share-tw').click(function(){meteor.tracking.track_conversion(meteorCode,{'name':'twitter-via-sharewidget'})}),
					share_li = $(displayIcon(3,document.title,this.url,1,this.shortDomain,this.mmcIndicator,this.mmcValue,null,"Bank of America",document.title,this.cmIndicator,this.cmDynamicPageVariable,0,null)).addClass('share-li').click(function(){meteor.tracking.track_conversion(meteorCode,{'name':'linkedin-via-sharewidget'})}),
					share_dg = $(displayIcon(5,document.title,this.url,1,this.shortDomain,this.mmcIndicator,this.mmcValue,null,"Bank of America",null,this.cmIndicator,this.cmDynamicPageVariable,0,null)).addClass('share-dg').click(function(){meteor.tracking.track_conversion(meteorCode,{'name':'digg-via-sharewidget'})}),
					share_de = $(displayIcon(4,document.title,this.url,1,this.shortDomain,this.mmcIndicator,this.mmcValue,null,"Bank of America",null,this.cmIndicator,this.cmDynamicPageVariable,0,null)).addClass('share-de').click(function(){meteor.tracking.track_conversion(meteorCode,{'name':'delicious-via-sharewidget'})});
				$('.share').append(share_fb, share_tw, share_li, share_dg, share_de);
			}
			$('.cellContents').append($('.share'));
			return false;
*/
		},
		
		"keypress" : function() {
			steal.dev.log("Oee.Video keypress");
		}		

	});	
	/**
	 * @class Oee.Video.Transcript
	 * 
	 * 	 */
	$.Controller('Oee.Video.Transcript',
	/** @Static */
	{
	
		defaults: {
			
		}
	},
	/** @Prototype */
	{
		init: function() {
			steal.dev.log("Oee.Video.Transcript : init");
			this.videoId = this.options.videoId;
			this.pdfURL = "/images/facebook.jpg";
			steal.dev.log("Oee.Video.Transcript : videoId " + this.videoId);
			this.element.html("//oee/brightcove/player/views/transcript.ejs", {
				title: this.videoId,
				pdfURL: this.pdfURL
			});
		}

	});		
	/**
	 * @class Oee.Media.Controls
	 * 
	 * 	 */
	$.Controller('Oee.Media.Controls',
	/** @Static */
	{
	
		defaults: {
			
		}
	},
	/** @Prototype */
	{
		init: function() {
			this.playerObject = this.options.actionObject;
			this.videoPlayer = this.playerObject.video;
			var obj = this;
			this.isEmbedOpen = false;
			this.isLinkToOpen = false;
			this.element.find(".replay").click(function(e){
				e.preventDefault();
				obj.onReplayClick(e);
			});
			this.element.find(".embed").click(function(e){
				e.preventDefault();
				obj.onEmbedClick(e);
			});
			this.element.find(".link-to").click(function(e){
				e.preventDefault();
				obj.onLinktoClick(e);
			});

		},

		onReplayClick:function(e){
			e.preventDefault();
			steal.dev.log("Oee.Media.Controls : replay");
	        this.playerObject.clearAndPlay();
	        //this.playerObject.play(this.playerObject.player.id);
		},
		onEmbedClick:function(e){
			e.preventDefault();
			steal.dev.log("Oee.Media.Controls : embed");
			this.isLinkToOpen = false;
			if (!this.isEmbedOpen){
				$(".container textarea").css("display", "block").css("height","200px");
				$(".container textarea").text(this.element.find(".embed-text").html());
				$(".video-end-screen").css("height", "325px");
			}else{
				$(".container textarea").css("display", "none");
				$(".video-end-screen").css("height", "150px");
			}
			this.isEmbedOpen = !this.isEmbedOpen;
	        //this.playerObject.clear();
		},
		onLinktoClick:function(e){
			e.preventDefault();
			steal.dev.log("Oee.Media.Controls : link-to");
			this.isEmbedOpen = false;
			if (!this.isLinkToOpen){
				$(".container textarea").css("display", "block").css("height","20px");
				$(".video-end-screen").css("height", "160px");				
				if (document.location.href.indexOf("index.html") > -1){
					$(".container textarea").text(document.location.href);
				}else{
					$(".container textarea").text(this.playerObject.link);
				}
				var linkToText = $(".container textarea").html();
				if(linkToText.length > 35){
					$(".container textarea").css("display", "block").css("height","35px");
				}
				
			}else{
				$(".container textarea").css("display", "none");
				$(".video-end-screen").css("height", "150px");		
			}
			this.isLinkToOpen = !this.isLinkToOpen;
			
		},		
		'a[class="main-image-content"] click':function(e){
			steal.dev.log("Oee.Media.Controls : main-image-content");
	        this.playerObject.keepPlaying();
	        //this.playerObject.play(this.playerObject.player.id);
		}
	});	
	
	/**
	 * @class Oee.Brightcove.Player
	 */
	$.Controller('Oee.Brightcove.Player',
	/** @Static */
	{

		init: function() {
			
		},

		uniqueId:0,
		isTranscriptOpen: false,
		
		playerMap: {

		},
		
		isFlashEnabled:	function(){
		    var hasFlash = false;
		    try
		    {
		        var fo = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');
		        if(fo) hasFlash = true;
		    }
		    catch(e)
		    {
		        if(navigator.mimeTypes ["application/x-shockwave-flash"] != undefined) hasFlash = true;
		    }
		    if (hasFlash)
		    	hasFlash = document.location.href.indexOf("forceHTML=true") == -1;
		    return hasFlash;
		},
		
		registerPlayerObject : function(playerID, playerObject) {
			steal.dev.log("registerPlayerObject(): Entry ... playerID = " + playerID + ", playerObject = " + playerObject);
			steal.dev.log("registerPlayerObject(): playerMap = " + Oee.Brightcove.Player.playerMap);
			Oee.Brightcove.Player.playerMap[playerID] = playerObject;
		},
		
		getPlayerObject : function(playerID) {
			steal.dev.log("getPlayerObject(): Entry ... playerID = " + playerID);
			return Oee.Brightcove.Player.playerMap[playerID];
		},

		testStaticFunction : function(instance) {
			steal.dev.log("testStaticFunction(): Entry ... instance = " + instance);	
		},
		
		defaults: {
			logo : hostAndContextName + "/images/common/video_logo_boa.png",
			replay: hostAndContextName + "/images/common/vid_replay.png",
			linkTo: hostAndContextName + "/images/common/vid_linkto.png",
			embed: hostAndContextName + "/images/common/vid_embed.png"
		},

		assets : {
			"chrome" :{
				background : {
						img: "/images/brightcove/highsize/control-background.png",
						width:"100%",
						height:"50"
				},
				more: {
						on:"/images/brightcove/player/more.png",
						off:"/images/brightcove/player/more.png",
						width:"43",
						height:"18"
				},
				
				/*cc: {
						on:"/images/brightcove/highsize/cc-on.png",
						off:"/images/brightcove/highsize/cc-off.png",
						width:"26",
						height:"21"
				},*/
				
				transcript: {
						on:"/images/brightcove/player/transcript.png",
						off:"/images/brightcove/player/transcript.png",
						width:"81",
						height:"18"
				},
				
				share: {
						on:"/images/brightcove/player/share.png",
						off:"/images/brightcove/player/share.png",
						width:"57",
						height:"18"
				},
				email: {
						on:"/images/brightcove/player/mail.png",
						off:"/images/brightcove/player/mail.png",
						width:"23",
						height:"18"
				},
				/*addcomment: {
						on:"/images/brightcove/player/add_comment.png",
						off:"/images/brightcove/player/add_comment.png",
						label:"add comment",
						width:"98",
						height:"18"
				},*/
				like: {
						on:"/images/brightcove/player/like.png",
						off:"/images/brightcove/player/like.png",
						label:"like",
						width:"43",
						height:"18"
				},
				fullscreen: {
						on:"/images/brightcove/highsize/fullScreen.png",
						off:"/images/brightcove/highsize/fullScreen.png",
						width:"24",
						height:"24"
				}
			},			
			"chromeless" :{
				background : {
						img: "/images/brightcove/highsize/control-background.png",
						width:"100%",
						height:"50"
				},
				more: {
						on:"/images/brightcove/player/more.png",
						off:"/images/brightcove/player/more.png",
						width:"43",
						height:"18"
				},
				
				cc: {
						on:"/images/brightcove/highsize/cc-on.png",
						off:"/images/brightcove/highsize/cc-off.png",
						width:"26",
						height:"21"
				},
				
				transcript: {
						on:"/images/brightcove/player/transcript.png",
						off:"/images/brightcove/player/transcript.png",
						width:"81",
						height:"18"
				},
				
				share: {
						on:"/images/brightcove/player/share.png",
						off:"/images/brightcove/player/share.png",
						width:"57",
						height:"18"
				},
				email: {
						on:"/images/brightcove/player/mail.png",
						off:"/images/brightcove/player/mail.png",
						width:"23",
						height:"18"
				},
				addcomment: {
						on:"/images/brightcove/player/add_comment.png",
						off:"/images/brightcove/player/add_comment.png",
						label:"add comment",
						width:"98",
						height:"18"
				},
				like: {
						on:"/images/brightcove/player/like.png",
						off:"/images/brightcove/player/like.png",
						label:"like",
						width:"43",
						height:"18"
				},
				fullscreen: {
						on:"/images/brightcove/highsize/fullScreen.png",
						off:"/images/brightcove/highsize/fullScreen.png",
						width:"24",
						height:"24"
				}
			}
		},
		
		playerTypeDefinitions: {

			"defaultPlayer": {
				type:"chrome",
				width: "480",
				height: "300",
				playerID: "2061261571001",
				playerKey: "u2ePYbm8Bui8juba17Mn_OFOVnEs_HGeP6LDJ_SqVMcVmz3SlC18RA..",
				bgcolor: "#000000",
				publisherID: "1082515149001",
				isVid: "true",
				isUI: "true",
				dynamicStreaming: "true",
				cacheAMFURL : "https://share.brightcove.com/services/messagebroker/amf",
				styleMethod:defaultPlayer
			}
		}
	},
	/** @Prototype */
	{
		init: function() {

			var containerId = this.element;
			this.isFlashDetected = Oee.Brightcove.Player.isFlashEnabled();
			this.player = new Object();
			this.playerExp = new Object();
			this.video = new Object();
			this.content = new Object();
			this.experience = new Object();
			var t = document.title;

			/** This hack for the ie7 switching title issue */
			   if($.browser.msie && $.browser.version < 8){
			          window.setInterval(function () {
			               document.title = t;
			          }, 1);
			   }
			   /**end hack*/			

			this.autostart = this.options.autostart || this.element.find(".param-autostart").text() || "false";
			//this.autostart = "true";
			this.playerColor = this.options.color || this.element.find(".param-color").text() || "black";
			this.playerType = this.options.playerType || this.element.find(".param-playerType").text() || "defaultPlayer";
			this.videoId = this.options.videoId || this.element.find(".param-videoId").text() || "kipp-dc";
			this.id = this.playerType + "ID" + this.videoId;
			/*** Extra parameters ***/
			this.mainImage = this.options.mainImage || this.element.find(".param-mainImage").text() || "";
			this.relatedVideo = this.options.relatedVideo || this.element.find(".param-relatedVideo").text() || "";
			this.relatedContent = this.options.relatedContent || this.element.find(".param-relatedContent").text() || "";
			this.featuredComments = this.options.featuredComments || this.element.find(".param-featuredComments").text() || "";
			this.transcript = this.options.transcript || this.element.find(".param-transcript").text() || "";
			this.pdfPath = this.options.pdfPath || this.element.find(".param-pdf").text() || "/assets/pdf/Solar.pdf";
			this.headline = this.options.headline || this.element.find(".param-headline").text() || this.videoId;
			this.link = this.options.link || this.element.find(".param-link").text() || "";
			this.brand = this.options.brand || this.element.find(".param-brand").text() || "";
			this.darttags = this.options.darttags || this.element.find(".param-darttags").text() || "";

			this.videoTileId = this.options.videoTileId || this.element.find(".param-video-tile-id").text() || "";
			
			this.darttagItems = this.darttags.split("||").join('').split("|");
			
			if(this.link.indexOf("index.html") > -1){
				this.link = document.location.href;
			}
			this.player = brightcove.createElement("object");

			this.player.className = "BrightcoveExperience";
			this.type = Oee.Brightcove.Player.playerTypeDefinitions[this.playerType];
			//this.chrome = this.type.chrome;
			Oee.Brightcove.Player.uniqueId = Oee.Brightcove.Player.uniqueId + 1; 
			this.uniqueId = Oee.Brightcove.Player.uniqueId;
			steal.dev.log("init(): this.uniqueId = " + this.uniqueId);			
			this.player.id = "player-" + this.videoId + this.uniqueId + this.playerType;
			this.videoPlayerType = this.type["type"];
			this.isPause = false;
			this.showEndScreen = false;
			this.completed = false;

			//0: image path
			//1: alt text
			// The setting of this parameter is required
			var mainImageValues = this.mainImage.split("||");

			//0: comment text
			//1: author
			// The setting of this parameter is required
			var featuredCommentValues = this.featuredComments.split("||");
			
			//Optional Parameters
			
			this.playerID =  	this.options.playerID || this.element.find(".param-playerID").text() || this.type["playerID"],
			this.playerKey = 	this.options.playerKey || this.element.find(".param-playerKey").text() || this.type["playerKey"],
			this.publisherID =	this.options.publisherID || this.element.find(".param-publisherID").text() || this.type["publisherID"],
		  
			this.element.html("//oee/brightcove/player/views/player.ejs", {
				videoId: this.videoId + this.uniqueId,
				imgSrc : hostAndContextName + this.type["onMediaStop"],
				logo : Oee.Brightcove.Player.defaults["logo"],
				embed : Oee.Brightcove.Player.defaults["embed"],
				linkTo : Oee.Brightcove.Player.defaults["linkTo"],
				replay : Oee.Brightcove.Player.defaults["replay"],
				imageAlt : mainImageValues[1],
				imagePath : mainImageValues[0],
				headline: mainImageValues[2],
				comment: featuredCommentValues[0],
				author: featuredCommentValues[1],
				transcript : this.transcript,
				pdfURL : this.pdfPath,
				width: this.type["width"],
			    height: this.type["height"],
				playerID: this.playerID,
				playerKey: this.playerKey,
				publisherID:this.publisherID,
				link : this.link
			});
			this.element.append("//oee/brightcove/player/views/jaws.ejs", {});

			//$('div.size a.share').Oee_sharebar();
			
			//alert("#player-" + this.videoId + this.uniqueId);

			// Related Content
			// The setting of this parameter is optional
			var relatedContentValues =  this.relatedContent.split("||");
			
			if (relatedContentValues.length > 0){
				/*				
				<ul>
					<li><a href="/#">&#155; dummy</a></li>
					<li><a href="/#">&#155; dummy</a></li>
					<li><a href="/#">&#155; dummy</a></li>
					<li><a href="/#">&#155; dummy</a></li>
				</ul>
				kipp-dc-1::http://bosintdev71.digitas.com/enterprise/en-US/partnering-locally/kipp-dc.html::_SELF	
				*/
				var ulTag = $(document.createElement('ul'));
				for (i=0; i < relatedContentValues.length; i++){
					var items = relatedContentValues[i].split("|");
					//ul
					//li
					if (items.length > 1){
						var liTag = $(document.createElement('li'));
						var aTag = $(document.createElement('a'));
						aTag.html(items[0]);
						aTag.attr("href", items[1]);
						aTag.attr("target", items[2]);
						aTag.appendTo(liTag);
						liTag.appendTo(ulTag);
					}
				}
				
				ulTag.appendTo(this.element.find('.learn-more-content'));
			}
			
			// Videos
			// The setting of this parameter is optional
			var relatedVideotem = this.relatedVideo;
			var relatedVideoValues = this.relatedVideo.split("||");
			
			/*
			 * 	<div class="next-video">
					<a href="/tests/related-video.html?videoId=23" rel="id"  style="text-decoration:none">
						<img src="<%= this.nextvideo1%>"  rel="next-video-1"/>
					</a>	
					<p>Headline</p>
				</div>
				kipp-dc:/assets/images/partnering-locally/local-markets/LM_001_127x87.jpg:$10 MM Grant for Charlotte Mecklenburg:[Thomas Kirkley]
				next-steps
			 * 
			 */
			var resultDiv = $("#result");
		/*	if (relatedVideoValues.length > 1){
				for (i=0; i < relatedVideoValues.length; i++){
					var divTag = $(document.createElement('div'));
					var items = relatedVideoValues[i].split("|");
					divTag.attr("class", "next-video " + items[5]);
					if (items.length > 1){
						var aTag = $(document.createElement('a'));
						aTag.attr("href", "/ajax/related-video.html?videoId=" + items[0] + "&locale=" + items[4]);
						//aTag.attr("rel", "#" + containerId);
						aTag.attr("style", "text-decoration:none");
						
						aTag.click(function(e){
							e.preventDefault();
							resultDiv.load(this.href , function() {
								steal.dev.log(" before load  : --> " + containerId);
								var newTag = containerId.replaceWith(resultDiv.find('#video-module-player'));
								steal.dev.log(" after load  : --> " + containerId);
								$("#video-module-player").Oee_brightcove_player();
							});
						});
						var imgTag = $(document.createElement('img'));
						imgTag.attr("src", items[1]);
						imgTag.attr("alt", items[2]);
						imgTag.attr("rel", "next-video-" + i);
						imgTag.appendTo(aTag);
						var pTag = $(document.createElement('p'));
						pTag.html(items[3]);
						pTag.appendTo(aTag);
						pTag.attr("class", "next-video-caption") ;
						aTag.appendTo(divTag);
						var imgTag1 = $(document.createElement('img'));
						imgTag1.attr("src", "/assets/images/common/SmallVideoIcon.png");
						imgTag1.attr("class", "play-button");
						imgTag1.appendTo(aTag);
						divTag.appendTo(this.element.find('.next-steps'));
					}
				}
			}*/
			this.element.css("position", "relative");
			if (this.videoPlayerType == "chrome"){
				this.styles = $(document.createElement('div'));
				//this.playerColor = this.options.color;
				this.styles.load(hostAndContextName + "/jmvc/oee/players/" + this.playerColor +"-chrome-player.css");
			}	

			this.width = this.type["width"];
			this.height = this.type["height"];
			
			//Setting dimensions for player
			var playerLayer = this.element.find('.player'); 
			playerLayer.css("width", this.width).css("height", this.height).css("display", "block");
			
			//Setting dimensions for canvas
			this.element.width(this.width);
			var totalHeight= this.element.find(".size").height();
			var partialHeight = parseInt(this.height) ;
			//HTML5 player
			if (!this.isFlashDetected){
				var html5 = this.element.find(".html5-player-canvas");
				var obj = this;
				html5.css({"display":"block"});
				//totalHeight = totalHeight + html5.height();
				/*html5.find(".more-button").click(function(e){
					obj.more(e, obj.player.id);
				});*/
				html5.find(".transcript-button").click(function(e){
					obj.transcriptClick(e, obj.player.id);
				});		
				html5.find(".send-button").click(function(e){
					obj.emailClick(e, obj.player.id);
				});		
				html5.find(".share-button").click(function(e){
					obj.share(e, obj.player.id);
				});			
				//partialHeight = partialHeight + 35; // magic number
				totalHeight = totalHeight - 1; 
			}else{
				this.element.find(".size").addClass("notvisible");
			}
			//this.element.find(".on-player").css({position:"absolute", top: partialHeight + "px"})
			
			this.element.height(parseInt(this.height) + totalHeight);
			//this.element.css("overflow", "hidden");
			
			//Hide the transcript at the beginning
			this.isTranscriptOpen = false;
			//Hide the share window at the beginning
			this.isShareOpen = false;
			//Set this variable to false
			this.isFullScreen = false;
			this.onMediaBeginPlay = false;
			
			this.element.find('.transcript-container').hide();
			//this.element.find(".share").css("height", "0px").css("padding-top","0px");
			//this.element.find(".share").css("display", "block");
			//this.element.find(".share").Oee_social_widget();
			//this.element.find('textarea').width(parseInt(this.width) - 2);

			// activate shareing
			/* social widget is not initializing in IE7 the Oee.SocialWidget is dependent 
			   on IDs and when using $.dialog() the IDs end up duplicated and IE7 chokes, 
			   other browsers are more forgiving, reproduce widget here */
			/*
			if($('.share a').length == 0) {
				var share_fb = $(displayIcon(1,document.title,this.url,1,this.shortDomain,this.mmcIndicator,this.mmcValue,null,"Bank of America",null,this.cmIndicator,this.cmDynamicPageVariable,0,null)).addClass('share-fb').click(function(){meteor.tracking.track_conversion(meteorCode,{'name':'facebook-via-sharewidget'})}),
					share_tw = $(displayIcon(2,document.title,this.url,1,this.shortDomain,this.mmcIndicator,this.mmcValue,document.title,"Bank of America",null,this.cmIndicator,this.cmDynamicPageVariable,0,null)).addClass('share-tw').click(function(){meteor.tracking.track_conversion(meteorCode,{'name':'twitter-via-sharewidget'})}),
					share_li = $(displayIcon(3,document.title,this.url,1,this.shortDomain,this.mmcIndicator,this.mmcValue,null,"Bank of America",document.title,this.cmIndicator,this.cmDynamicPageVariable,0,null)).addClass('share-li').click(function(){meteor.tracking.track_conversion(meteorCode,{'name':'linkedin-via-sharewidget'})}),
					share_dg = $(displayIcon(5,document.title,this.url,1,this.shortDomain,this.mmcIndicator,this.mmcValue,null,"Bank of America",null,this.cmIndicator,this.cmDynamicPageVariable,0,null)).addClass('share-dg').click(function(){meteor.tracking.track_conversion(meteorCode,{'name':'digg-via-sharewidget'})}),
					share_de = $(displayIcon(4,document.title,this.url,1,this.shortDomain,this.mmcIndicator,this.mmcValue,null,"Bank of America",null,this.cmIndicator,this.cmDynamicPageVariable,0,null)).addClass('share-de').click(function(){meteor.tracking.track_conversion(meteorCode,{'name':'delicious-via-sharewidget'})});
				$('.share').append(share_fb, share_tw, share_li, share_dg, share_de);
			}
			*/
			
			if ( this.player ) {
				createParameter(this.player, "width", this.type["width"]);
				createParameter(this.player, "height", this.type["height"]);
				createParameter(this.player, "playerID", this.playerID);
				createParameter(this.player, "bgcolor", this.type["bgcolor"]);
				createParameter(this.player, "playerKey", this.playerKey);
				createParameter(this.player, "publisherID", this.publisherID);
				createParameter(this.player, "wmode", "transparent");
				createParameter(this.player, "videoID", this.videoId);
				createParameter(this.player, "isVid", this.type["isVid"]);
				createParameter(this.player, "isUI", this.type["isUI"]);
				createParameter(this.player, "dynamicStreaming", this.type["dynamicStreaming"]);
				createParameter(this.player, "cacheAMFURL", this.type["cacheAMFURL"]);
				createParameter(this.player, "autoStart", this.autostart);
				createParameter(this.player, "templateLoadHandler", this.onTemplateLoaded);
				//createParameter(this.player, "templateReadyHandler", this.onTemplateReady);
				this.playerContainer = this.element.find(".video-player-class").get(0);

				steal.dev.log("player = " + this.player);
				steal.dev.log("playerContainer = " + this.playerContainer);
				
				brightcove.createExperience(this.player, this.playerContainer, true);

				steal.dev.log("init() completed: this = " + this);
				steal.dev.log("init() completed: player = " + this.player);
				
				Oee.Brightcove.Player.registerPlayerObject(this.player.id, this);
				
				
			}

			// move the transcript outside the scope of $(this.element) for Firefox bug
			$('.transcript-container').clone().insertAfter($('.Oee_brightcove_player'));
			$('.Oee_brightcove_player .transcript-container').remove();
		},
		
		onTemplateLoaded : function(playerID){
			
			steal.dev.log("onTemplateLoaded(): playerID = " + playerID);			
			var playerObject = Oee.Brightcove.Player.getPlayerObject(playerID);
			steal.dev.log("onTemplateLoaded(): playerObject = " + playerObject);
			playerObject.reset(playerID);

			playerObject.experience.addEventListener(BCExperienceEvent.TEMPLATE_READY, function(templateReady){playerObject.onTemplateReady(templateReady,playerID, playerObject.playerType)});
			
			steal.dev.log("exit onTemplateLoaded" );			
		},
		
		onTemplateReady : function(templateReady, playerID, playerType){
			//Hacks 
			this.element.find(".size").removeClass("notvisible");
			this.element.css("background", "#FFFFFF");
			this.element.find(".close").html("Close this window");
			
			//Code needs review cause the player must have the focus once is loaded 
			$("#video-player").focus();
			$("#" + this.player.id).focus();
			this.element.find(this.player.id).keypress(function(e){
				steal.dev.log("Init keypress");
			});
			

			steal.dev.log("onTemplateReady(): [playerType, playerID] = " + playerType + "," + playerID);
			var playerObject = Oee.Brightcove.Player.getPlayerObject(playerID);
			steal.dev.log("onTemplateReady(): playerObject = " + playerObject);
			//playerObject.video.setSize(parseInt(playerObject.width) + 70,parseInt(playerObject.height) + 10);
			//playerObject.experience.setSize(parseInt(playerObject.width)+ 70,parseInt(playerObject.height) + 10);

			steal.dev.log("brightcove.video.size  = " + playerObject.video.getWidth() + "," + playerObject.video.getHeight());
			steal.dev.log("brightcove.experience.size  = " + playerObject.experience.getWidth() + "," + playerObject.experience.getHeight());
			playerObject.experience.addEventListener(BCExperienceEvent.CONTENT_LOAD, function(e){ playerObject.onContentLoad(e, playerID, playerObject.playerType)});
			playerObject.content.addEventListener(BCContentEvent.VIDEO_LOAD, function(e){ playerObject.onVideoLoad(e, playerID, playerObject.playerType)});
			playerObject.video.addEventListener(BCMediaEvent.CHANGE, function(e){ playerObject.onMediaChange(e, playerID, playerObject.playerType)});
			playerObject.video.addEventListener(BCMediaEvent.BEGIN, function(e){ playerObject.onMediaBegin(e, playerID, playerObject.playerType)});
			playerObject.video.addEventListener(BCMediaEvent.PLAY, function(e){ playerObject.onMediaPlay(e, playerID, playerObject.playerType)});
			playerObject.video.addEventListener(BCMediaEvent.PROGRESS, function(e){ playerObject.onMediaProgress(e, playerID, playerObject.playerType)});
			playerObject.video.addEventListener(BCMediaEvent.STOP, function(e){ playerObject.onMediaStop(e, playerID, playerObject.playerType)});
			playerObject.video.addEventListener(BCMediaEvent.COMPLETE, function(e){ playerObject.onMediaComplete(e, playerID, playerObject.playerType)});
			
			/**More Button**/
			/*var more = playerObject.experience.getElementByID("moreButton");
			more.addEventListener("click", function(e){ playerObject.more(e, playerID)});
			more.addEventListener("propertyChange", function(e){ playerObject.more(e, playerID)});*/

			
			/**Transcript Button**/
			var transcriptButton = playerObject.experience.getElementByID("transcriptButton");
			//transcriptButton.addEventListener("click", function(e){ playerObject.transcriptClick(e, playerID)});
			transcriptButton.addEventListener("propertyChange", function(e){ playerObject.transcriptClick(e, playerID)});
			
			/**Email Button**/
			var emailButton = playerObject.experience.getElementByID("emailButton");
			emailButton.addEventListener("propertyChange", function(e){ playerObject.emailClick(e, playerID)});

			Oee.Brightcove.Player.playerTypeDefinitions[this.playerType].styleMethod(playerObject);

			this.element.find('.media-controls').oee_media_controls({"actionObject":playerObject});

			playerObject.addJAWSSupport();
			steal.dev.log("onTemplateReady(): exit");
		},
		
		addJAWSSupport : function (){
			/****The following code is meant to offer JAWS support ****/
			var destinationMore = this.element.find(".jaws-more-content");
			var moreContent = this.element.find(".more");
			var obj = this;
			
			/*this.element.find(".jaws-more-button").click(function(e){
				if (obj.isFlashDetected){
					obj.more(e, obj.player.id);
					
				}	
				return false;
			});*/
			
			var obj = this;
			//PLAY OR PAUSE
			var playOrPause = this.element.find(".jaws-play-button");
			playOrPause.click(function(){
				if (obj.isFlashDetected){			
					obj.pause();
					if ($(this).html() == "Play this video"){
						$(this).html("Pause this video");
					}else{
						$(this).html("Play this video");
					}
					//return false;
				}
				return false;
			});
			
			//VOLUME UP
			
			var volumeUp = this.element.find(".jaws-volume-up");
			volumeUp.click(function(){
				if (obj.isFlashDetected){
					var volume = obj.video.getVolume();
					
					if (volume < 1){
						volume = (volume*100) + 10;
						obj.video.setVolume(volume / 100);
					}
				}	
				return false;
			});
			
			//VOLUME DOWN
			var volumeDown = this.element.find(".jaws-volume-down");
			volumeDown.click(function(){
				if (obj.isFlashDetected){					
					var volume = obj.video.getVolume();
					
					if (volume > 0){
						volume = (volume * 100) - 10;
						obj.video.setVolume(volume / 100);
					}
				}
				return false;
			});
			
			
			//Transcript
			var transcript = this.element.find(".jaws-transcript-button");
			
			transcript.click(function(e){
				obj.transcriptClick(e, obj.player.id);
				if (Oee.Brightcove.Player.isTranscriptOpen){
					$(this).html("Close the transcript");
				}else{
					$(this).html("Open the transcript");
				}
				return false;
			});

			
			//Share
			var share = this.element.find(".jaws-share-button");
			var shareContainer = this.element.find(".share");
			//var shareContainerDestination = this.element.find(".jaws-share-content");

			share.click(function(e){
				obj.share(e, obj.player.id);
				if (Oee.Brightcove.Player.isTranscriptOpen){
					$(this).html("Close the Share this video window");
				}else{
					$(this).html("Open the Share this video window");
				}
				return false;
			});
			
			/****END JAWS support ****/
			
		},
		
		onContentLoad: function(templateReady, playerID, playerType){
			//alert("onContentLoad(): playerID = " + playerID);
			steal.dev.log("onContentLoad(): playerID = " + templateReady );
		},
		
		onVideoLoad: function(templateReady, playerID, playerType){
			//alert("onVideoLoad(): playerID = " + playerID);
			steal.dev.log("onVideoLoad(): playerID = " + playerID );
		},

		onMediaBegin: function(templateReady, playerID, playerType){
			//alert("onVideoLoad(): playerID = " + playerID);
			steal.dev.log("onMediaBegin(): playerID = " + playerID );
			
			var playerObject = Oee.Brightcove.Player.getPlayerObject(playerID);
			playerObject.reset(playerID);
			
			if (playerObject.completed == true){
				playerObject.video.stop();
				playerObject.onMediaBeginPlay = true;
				playerObject.completed = false;
			}else{
				this.element.find('.video-end-screen').removeClass("visible");
				this.element.find('.video-end-screen').addClass("notvisible");
			}			
		},

		onMediaChange: function(templateReady, playerID, playerType){
			//alert("onVideoLoad(): playerID = " + playerID);
			steal.dev.log("onMediaChange(): playerID = " + playerID );
		},

		onMediaPlay: function(templateReady, playerID, playerType){
			//alert("onVideoLoad(): playerID = " + playerID);
			steal.dev.log("onMediaPlay(): playerID = " + playerID );


			var playerObject = Oee.Brightcove.Player.getPlayerObject(playerID);
			playerObject.reset(playerID);
			
			if (playerObject.completed == true && playerObject.onMediaBeginPlay){
				playerObject.video.pause(true);
			}else{
				if (playerObject.darttagItems.length > 1){
					var src = playerObject.darttagItems[0];
					var type = playerObject.darttagItems[1];
					var category = playerObject.darttagItems[2];
					//alert("");
					Oee.Tagging.prototype.displayDartTag(src,category,type);

					//FIXME:Add Correct Parameters
					Oee.Tagging.prototype.fireMeteorTag('video');
				}
				playerObject.onMediaBeginPlay = false;
			}
			if(!playerObject.completed){
				this.element.find('.video-end-screen').removeClass("visible");
				this.element.find('.video-end-screen').addClass("notvisible");				
				
			}
		},

		onMediaProgress: function(progress, playerID, playerType){
			//alert("onVideoLoad(): playerID = " + playerID);
			//  steal.dev.log("onMediaProgress(): playerID = " + progress.position );
		},

		pause : function(){
			if (this.isFlashDetected){
				/*if (playerObject.darttagItems.length > 1){
					var src = playerObject.darttagItems[3];
					var type = playerObject.darttagItems[4];
					var category = playerObject.darttagItems[5];
					Oee.Tagging.prototype.clickTag(src,category,type);
				}*/				
				if (this.video.isPlaying()){
					this.isPause = true;
					this.video.pause(obj.isPause);
				}else{
					this.isPause = false;
					if (this.isPause){
						this.video.pause(obj.isPause);
					}else{
						this.video.play();
					}
				}
			}	
		},

		play : function(){
			if (this.isFlashDetected){
				this.video.play();
			}	
		},
		
		stop : function(){
			if (this.isFlashDetected){
				this.video.stop();
			}			
		},
		
		fullScreen: function(){
			this.isFullScreen = !this.isFullScreen;
			//this.video.goFullScreen(this.isFullScreen);
			/*var fullscreenButton = this.experience.getElementByID("fullscreenButton");
			fullscreenButton.click();*/
			
		},
		
		clearAndPlay : function(){
			this.element.find('.video-end-screen').removeClass("visible");
			this.element.find('.video-end-screen').addClass("notvisible");
			this.element.find('.more').removeClass("visible");
			this.element.find('.more').addClass("notvisible");
			this.element.find('.overlay').removeClass("notvisible");
			this.element.find('.overlay').addClass("visible");
			if (this.isFlashDetected){
				if (this.video.isPlaying())
					this.video.stop();
				this.video.play();
			}
			
		},		
		keepPlaying : function(){
			this.element.find('.video-end-screen').removeClass("visible");
			this.element.find('.video-end-screen').addClass("notvisible");
			this.element.find('.more').removeClass("visible");
			this.element.find('.more').addClass("notvisible");
			/*this.element.find('.overlay').removeClass("notvisible");
			this.element.find('.overlay').addClass("visible");*/
			if (this.isFlashDetected){
		
				if (this.video.isPlaying()){
					this.isPause = false;
					this.video.pause(this.isPause);
				}else{
					this.video.play();
				}
			}	
		},	
		more : function(e, playerId){
			steal.dev.log("more(): event called");
			var playerObject = Oee.Brightcove.Player.getPlayerObject(playerId);
			this.element.find(".main-image-content a").click(function (e){
				e.preventDefault();
				steal.dev.log("Oee.Media.Controls : main-image-content");
		        playerObject.keepPlaying();
				
			});
			if (this.isFlashDetected){
				playerObject.reset(playerId);
				if (this.video.isPlaying()){
					this.isPause = true;
					this.video.pause(this.isPause);
				}
			}
			this.element.find('.video-end-screen').removeClass("visible");
			this.element.find('.video-end-screen').addClass("notvisible");			
			this.element.find('.more').removeClass("notvisible");
			this.element.find('.more').addClass("visible");
			this.element.find('.more').css("height", parseInt(this.type["height"]) + this.element.find('.more').height() );
			this.element.find('.more').css("width", this.type["width"]);
			this.element.find('.more').focus();
			this.element.find(".main-image-content a").focus();
			
		},
		
		transcriptClick : function(e, playerId){
			steal.dev.log("transcript(): event called");
			
			//var playerObject = Oee.Brightcove.Player.getPlayerObject(playerId);
			var size = 238;
			if (Oee.Brightcove.Player.isTranscriptOpen){
				// hide transcript
				$("#" + this.player.id).focus();

				Oee.Brightcove.Player.prototype.closeTranscript();
			}else{
				// show transcript
				$('.transcript-container .transcript .scroller').html(this.transcript);

				$('.transcript-button-container a.closeTranscript').click(function(){
					Oee.Brightcove.Player.prototype.closeTranscript();
					return false;
				});

				$('#videoPopupContainer .transcript-container').slideDown();

				$('.transcript-container a.closeTranscript').focus();

				// set var that we're open.
				Oee.Brightcove.Player.isTranscriptOpen = true;
			}
			
		},
		closeTranscript: function() {
			// hide transcript
			$('#videoPopupContainer .transcript-container').slideUp();

			Oee.Brightcove.Player.isTranscriptOpen = false;
		},
		emailClick : function(e, playerId){
			steal.dev.log("emailClick(): event called");
			//FIXME: This is tricky ... needs to be reviewed.
			
			var url = document.location.href;

			steal.dev.log("emailClick(): videoTileId = " + this.videoTileId);
			if (url.indexOf(this.videoTileId) == -1){
				//ADD code to support video tile id
			}
			steal.dev.log("emailClick() url: " + "mailto:?subject=" + encodeURI(this.headline).replace(' ','%20').replace('#','%23').replace('&','%26').replace('\'','%27') + "&body=" + url);
			document.location.href = "mailto:?subject=" + encodeURI(this.headline).replace(' ','%20').replace('#','%23').replace('&','%26').replace('\'','%27') + "&body=" + url.replace('#','%23');
			
		},
		share : function(e, playerId){
			steal.dev.log("share(): event called");
			$('div.size a.share').trigger('click');
			$('#sharebar-'+$('div.size a.share').data('sharebarid')).addClass('forVideo');
		},	
		reset : function(playerID){
			steal.dev.log("reset = " + playerID);
			var playerObject = Oee.Brightcove.Player.getPlayerObject(playerID);
			playerObject.playerExp = brightcove.getPlayer(playerID);
			playerObject.video 	= playerObject.playerExp.getModule(APIModules.VIDEO_PLAYER);
			playerObject.content = playerObject.playerExp.getModule(APIModules.CONTENT);
			playerObject.experience = playerObject.playerExp.getModule(APIModules.EXPERIENCE);

			steal.dev.log("exit reset " );
		},
		
		onMediaStop : function(stop, playerId, playerType){
			steal.dev.log("onMediaStop(): init " );
			var playerObject = Oee.Brightcove.Player.getPlayerObject(playerId);
			playerObject.reset(playerId);
			
			
			if (  stop.position >= stop.duration  ){
				this.element.find(".player").css("position", "relative");
				this.element.find('.video-end-screen').removeClass("notvisible");
				this.element.find('.video-end-screen').addClass("visible")
				this.element.find('.video-end-screen').css("top", 40);
				this.element.find('.video-end-screen').css("left",20 );
				this.element.find('.container textarea').css("display","none" );
				playerObject.completed = true;
			}
			/*$('.video-end-screen').removeClass("notvisible");
			$('.video-end-screen').addClass("visible");
			$('.video-end-screen').css("top", this.type["height"] / 4);
			$('.video-end-screen').css("left", this.type["width"] / 3);
			var options = {"actionObject":playerObject};
			$('.video-end-screen img').Oee_media_controls(options);*/
			/*if (playerObject.darttagItems.length > 1 && playerObject.video.isPlaying()){
				var src = playerObject.darttagItems[3];
				var type = playerObject.darttagItems[4];
				var category = playerObject.darttagItems[5];
				Oee.Tagging.prototype.clickTag(src,category,type);
			}	*/			
					
		},
		
		onMediaComplete : function(stop, playerId, playerType){
			steal.dev.log("onMediaComplete(): init " );
			this.element.find(".player").css("position", "relative");
			this.element.find('.video-end-screen').removeClass("notvisible");
			this.element.find('.video-end-screen').addClass("visible")
			this.element.find('.video-end-screen').css("top", 40);
			this.element.find('.video-end-screen').css("left",20 );
			this.element.find('.container textarea').css("display","none" );
			
			var playerObject = Oee.Brightcove.Player.getPlayerObject(playerId);
			playerObject.completed = true;
			/*if (playerObject.darttagItems.length > 1){
				var src = playerObject.darttagItems[6];
				var type = playerObject.darttagItems[7];
				var category = playerObject.darttagItems[8];
				Oee.Tagging.prototype.clickTag(src,category,type);
			}*/				

		},
		
		translate: function (css) {
			
			//|	Take out any HTML that firebug might add, and remove all whitespace to make parsing easier
			css = css.replace(/<.*?>/ig, '');
			css = css.replace(/\s{1,}?/ig, '');
			
			//|	Find each class-level chunk
			var p = css.split('}');
			var s = '';
			
			for (var i in p) {
			
				//|	Get the classname for use in the atribute names below
				var n = p[i].substr(0, p[i].indexOf('{'));
				var r = p[i].substr(p[i].indexOf('{') + 1).split(';');
				
				//|	Step through the key/value pairs within this selector chunk
				for (var j in r) {
				
					//|	Some browsers will give us some blank lines after the splits, so we take them out here
					if (r[j].length < 1) { continue; }
					var t = r[j].substr(0, r[j].indexOf(':'));
					var v = r[j].substr(r[j].indexOf(':') + 1);
					
					//|	Combine selector name with property name for our new key, assign the old value
					s += (n.replace('.', '')+'-'+t+':'+v+';');
				
				}
				
			}
	
			return s;
		}		
		
	})

});