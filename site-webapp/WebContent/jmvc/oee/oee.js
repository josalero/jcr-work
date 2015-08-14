steal(
	'./oee.css', 			// application CSS file
	'./models/models.js',		// steals all your models
	'./fixtures/fixtures.js'	// sets up fixtures for your models
).then(
	'./jquery-ui-1.9.2.min.js',	// jQueryUI
	'./jquery.mousewheel.js',	// mousewheel events
	'./jquery.cookie.js'	// cookie management
).then(
	'./parallax/parallax2',	// parallax controller
	'./flagscape/flagscape',	// flagscape navigation
	'./carousel/carousel',	// carousel controller
	'./commitment/hover/hover',	// commitment hover controller
	'./navigation/navigation',	// commitment hover controller

	'./brightcove/player/player',
	'./brightcove/player/style_players',

	'./tagging/tagging',

	function(){					// configure your application
	
		var brightcovePlayerId = '2065600358001',
		brightcovePlayerKey = 'IBjK_h84Q3o_ydDZmv5gjXIz1F4excvGlnt8PsyrdKPb2YW4F1lYnQ..',
		brightcovePublisherId = '2067658940001'

		steal.dev.log(devMode);	
		if (devMode == "true") {
			var brightcovePlayerId = '2061261571001',
				brightcovePlayerKey = 'u2ePYbm8Bui8juba17Mn_OFOVnEs_HGeP6LDJ_SqVMcVmz3SlC18RA..',
				brightcovePublisherId = '1082515149001'
		}

		// make video placeholders live
		$('a.videoPlaceholder').each(function(){
			if(typeof $(this).data('videoid') != 'undefined') {
				$(this).find('.playIcon').append(
					$(document.createElement('span')).addClass('accessibility-hidden').text('Play Video: ' + $(this).data('videotitle'))
				);
				$(this).on('click',function(ev){
					ev.preventDefault ? ev.preventDefault() : ev.returnValue = false;
					var el = this;

					$(document.createElement('div')).attr('id','videoPopupContainer').dialog({
						dialogClass: 'videoPopup',
						height: 340,
						width: 480,
						minHeight: 340,
						minWidth: 480,
						modal: false,
						draggable: false,
						resizable: false ,
						create: function(){
							$(document.createElement('div')).addClass('ui-widget-overlay').css({
								height: $(window).height(),
								width: $(window).width(),
								zIndex: 1000
							}).hide().appendTo('body').fadeIn();
							$(document.createElement('div')).addClass('videoBox').appendTo('.videoPopup .ui-dialog-content');


							// TODO: programatically pull headline and transcript.
							$('.videoPopup .videoBox').oee_brightcove_player({
								"videoId" : $(el).data('videoid'),
								"videoTileId" : "12121212", // Needs to pass videoTileId thru
								"playerType" : "defaultPlayer",
								"headline" : $(el).data('videotitle'),
								"transcript" : $('.tempTranscriptContainer[rel='+$(el).data('videoid')+']').html(),
								"autostart" : "true",
								"brand" : "bank-of-america",
								"color" : "black",
								"playerID" : brightcovePlayerId,
								"playerKey" : brightcovePlayerKey,
								"publisherID" : brightcovePublisherId
							});

							// update hash
							location.hash = '#!' + $(el).parents('.vPanel, .hPanel').attr('id') + '/video/' + $(el).data('videoid')
						},
						open: function(){
							$('a.ui-dialog-titlebar-close').focus();
						},
						close: function(){
							$('.ui-widget-overlay').fadeOut(400, function(){$(this).remove()});
							$('.ui-dialog #videoPopupContainer, .ui-dialog').remove();
							location.hash = location.hash.split('/')[0];
						}
					});
				});
			}
		});

		// setup parallax experience supporting parts
		$('#map').oee_navigation();
		$('#nav').oee_flagscape();
		$('.carousel').oee_carousel();
		$('.commitmentBottomLinks').oee_commitment_hover();

		// load this one last to give everything else a chance to load.
		$('body').oee_parallax();

		$("body").oee_tagging();

		/* ADA */
		$('#tableOfContents ul li a[href^=#\\!]').prepend(
			$(document.createElement('span')).addClass('accessibility-hidden').text('Jump to: ')
		);
	});