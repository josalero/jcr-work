steal( 
	'jquery/controller',
	'jquery/view/ejs' 
).then( 
	'./views/init.ejs', 
	'./jquery-ui-1.9.2.min.js',
	'./jquery.easing.js',
	function($){

/**
 * @class Oee.Parallax
 */
$.Controller('Oee.Parallax',
/** @Static */
{
	defaults : {},
	ltie9 : $('html').hasClass('lt-ie9'),
	transitionGap : ($('html').hasClass('lt-ie9'))?25:50,
	bottomGap : 25,
	currPanelId : null,
	panelHeight : 800,
	hPanelHeight : 800,
	windowHeight : 800,
	totalHeight : 0,
	isIntroComplete : false, // should be false for launch
	panelWidth85 : 0,
	ourCommitmentsOffsetTop : 0,
	scrollDelta : 0,
	scrollAmount : ($('html').hasClass('lt-ie9'))?40:15,
	scrollBarWidth : 20,
	scrollPromptAnimationIndex : 0
},
/** @Prototype */
{
	init : function(){

		// main parallax controller

		$('#tagline .contents, .hPanelWindow .hPanel').hide();
		$('#leadersInputForm .part2').hide();
		$('#leadersInputForm .part3').hide();

		$('#leadersInputForm .part1 button').click(function(){expandLeadersForm();});
		$('#leadersInputForm .part2 button').click(function(){leadersFormNext();});
		$('#leadersInputForm .part3 button').click(function(){leadersFormSubmit();});

		Oee.Parallax.prototype.scrollPromptAnimation();
		Oee.Parallax.prototype.repositionElements();

		// deeplink
		try {
			// 1. validate and scrollTo().
			var locationHashParts = location.hash.replace('!','').split('/');
			var potentialPanelFromHash = locationHashParts[0];
			console.log('trying deep link', potentialPanelFromHash);
			if ($(potentialPanelFromHash).length > 0) {
				console.log('hashId found', potentialPanelFromHash)
				Oee.Parallax.prototype.scrollTo(potentialPanelFromHash);
			}
			// 2. is there a valid video? if so, launch.
			if (location.hash.replace('!','').split('/').length > 1 && locationHashParts[1] == 'video') {
				$(potentialPanelFromHash+' a[rel='+locationHashParts[2]+']').first().trigger('click');
			}
		} catch(e) {
			// hash location is not valid, try-catch so we don't get any errors.
			console.log('deeplink did not work');
			// do nothing.
		}

		Oee.Parallax.prototype.slidePanels();
		Oee.Parallax.prototype.hPanelDefaultStates();

		/* Window Bindings */
		$(window).scroll(function() {
			Oee.Parallax.prototype.slidePanels();
		});
		$(window).resize(function(){
			Oee.Parallax.prototype.slidePanels();
			Oee.Parallax.prototype.repositionElements();
		});

		$("#scrollHandle").draggable({
			containment: "#scrollBar", 
			scroll: false, 
			axis: "y",
			drag: function(){
				var newScrollDelta = -1 * (parseInt($("#scrollHandle").css('top'))/(Oee.Parallax.windowHeight-30)) * Oee.Parallax.totalHeight;
				if (newScrollDelta > -1*(Oee.Parallax.totalHeight-Oee.Parallax.panelHeight)) {
					Oee.Parallax.scrollDelta = newScrollDelta;
					$('#container #cart').css({
						marginTop: Oee.Parallax.scrollDelta
					});
					Oee.Parallax.prototype.slidePanels();
				}
			}
		});

	},
	repositionElements : function() {
		Oee.Parallax.panelHeight = Oee.Parallax.hPanelHeight = ($(window).height() - Oee.Parallax.bottomGap);

		$('#map').css({
			'top': ($(window).height() - $('#map').height())/2
		});
		$('#container, #scrollBar').css({
			height: $(window).height()
		});
		$('#container .top-shadow').css({
			top: Oee.Parallax.panelHeight
		});
		$('#container .vPanel, #container .hPanels, #container .hPanel, #container .backgroundOverlay, #container .window, #why-do-we-do-what-we-do .container, .hPanelWindow, .hPanel .window').css({
			height: Oee.Parallax.panelHeight
		});
		$('#container .wrapper').css({
			height: Math.round(($(window).height() * 1.1))
		});
		$('#container #our-commitments.wrapper').css({
			height: Math.round((Oee.Parallax.panelHeight * .5) * $('#container .hPanel').length)
		});
		$('#container #commitmentBackgrounds.hPanelWindow .color-darkBlue').css({
			width: $(window).width() * 1.1
		});
		$('#container, #container .panel').css({
			width: $(window).width() - Oee.Parallax.scrollBarWidth
		});

		Oee.Parallax.totalHeight = $('#cart').height();
		Oee.Parallax.windowHeight = $(window).height();
		//Oee.Parallax.panelWidth85 = Math.round($(window).width() * .85);
		
		var offsetVar = $('#container #our-commitments').position(); // needs to use position() relative to parent not offset() relative to screen.
		
		if (offsetVar != null) {			
			Oee.Parallax.ourCommitmentsOffsetTop = offsetVar.top;
		}
	},
	slidePanels : function() {
		// IMPORTANT: this function is run with everytime the scroll event is fired,
		//            and loops through all panels to determine position relative to eachother.
		//            the fewer computations done the faster/smoother/better the experience.
		
		$("#scrollHandle").css({'top': -1*((Oee.Parallax.scrollDelta/(Oee.Parallax.totalHeight))*Oee.Parallax.windowHeight)});

		var scrollTop = Oee.Parallax.scrollDelta * -1;
		//console.log(scrollTop);
		//Oee.Parallax.panelWidth85 = Math.round($(window).width() * .85);

		$('#container .panel').each(function(){
	        var $el = $(this),
	            top = $el.parent().position().top,
	            height = $el.parent().height(),
	            targetId = $el.attr('id');

	        if (scrollTop + Oee.Parallax.panelHeight > top + height) {
	        	// user is below this panel
				$el.removeClass('above below at').addClass('below');
	        } else if (scrollTop >= top) {
	        	// user is at this panel
				$el.removeClass('above below at').addClass('at');
				
				if($el.hasClass('hPanels')) {
					// if user is at the horizontal panels, do something different
					var hPanelsScroll = scrollTop - Oee.Parallax.ourCommitmentsOffsetTop,
						hScrollInterval = Oee.Parallax.panelHeight * .25;

					if(Oee.Parallax.ltie9 && $('#commitmentBackgrounds .color-red').css('display') != 'inline') {
						// bug with IE7, when the site loads on a commitment, the inline-block hack does not work, fixing it with JS for this specific case.
						$('#commitmentBackgrounds .color-red, #commitmentBackgrounds .color-lightBlue, #commitmentBackgrounds .color-darkBlue, #commitmentBackgrounds .color-sandStone').css('display','inline');
					}

					if ( hPanelsScroll >= hScrollInterval * 3 ) {
						if($('#commitment4').is(':hidden')) {
							Oee.Parallax.prototype.triggerPanel('commitment4');
							$('#commitmentBackgrounds.hPanelWindow .color-darkBlue').stop(true,true).animate({
								width: '85%'
							},{duration: 500, easing: 'easeOutSine', queue: false});
							$('#commitmentBackgrounds.hPanelWindow').eq(0).find('.color-red, .color-lightBlue, .color-sandStone').stop(true,true).animate({
								width: '5%'
							},{duration: 500, easing: 'easeOutSine', queue: false});
						}
						Oee.Navigation.prototype.updateMap('commitment4');
					} else if ( hPanelsScroll >= hScrollInterval * 2 ) {
						if($('#commitment3').is(':hidden')) {
							Oee.Parallax.prototype.triggerPanel('commitment3');
							$('#commitmentBackgrounds.hPanelWindow .color-lightBlue').stop(true,true).animate({
								width: '85%'
							},{duration: 500, easing: 'easeOutSine', queue: false});
							$('#commitmentBackgrounds.hPanelWindow').eq(0).find('.color-red, .color-sandStone, .color-darkBlue').stop(true,true).animate({
								width: '5%'
							},{duration: 500, easing: 'easeOutSine', queue: false});
						}
						Oee.Navigation.prototype.updateMap('commitment3');
					} else if ( hPanelsScroll >= hScrollInterval * 1 ) {
						if($('#commitment2').is(':hidden')) {
							Oee.Parallax.prototype.triggerPanel('commitment2');
							$('#commitmentBackgrounds.hPanelWindow .color-red').stop(true,true).animate({
								width: '85%'
							},{duration: 500, easing: 'easeOutSine', queue: false});
							$('#commitmentBackgrounds.hPanelWindow').eq(0).find('.color-sandStone, .color-lightBlue, .color-darkBlue').stop(true,true).animate({
								width: '5%'
							},{duration: 500, easing: 'easeOutSine', queue: false});
						}
						Oee.Navigation.prototype.updateMap('commitment2');
					} else {
						if($('#commitment1').is(':hidden')) {
							Oee.Parallax.prototype.triggerPanel('commitment1');
							$('#commitmentBackgrounds.hPanelWindow .color-sandStone').stop(true,true).animate({
								width: '85%'
							},{duration: 500, easing: 'easeOutSine', queue: false});
							$('#commitmentBackgrounds.hPanelWindow').eq(0).find('.color-red, .color-lightBlue, .color-darkBlue').stop(true,true).animate({
								width: '5%'
							},{duration: 500, easing: 'easeOutSine', queue: false});
						}
						Oee.Navigation.prototype.updateMap('commitment1');
					}
				} else {
					Oee.Parallax.prototype.triggerPanel(targetId);
				}

			} else {
				// user is above this panels
				$el.removeClass('above below at next').addClass('above');
			}
		});

	},
	triggerPanel : function(_panelId) {
		if (_panelId != Oee.Parallax.currPanelId) {
			
			//console.log('firing open scripts for panel:',_panelId);
			Oee.Navigation.prototype.updateMap(_panelId);

			switch(_panelId) {
				case "tagline":
					$('#rightbottom a').attr('href','#!were-better-when-were-connected');
					if (!Oee.Parallax.isIntroComplete) {
						/* intro animation that does not re-set */
						$('body').addClass('intro');

						// setup intro words
						$('#container #tagline #tagline-intro div').each(function(){
							var output = $(document.createElement('div'));
							$($(this).html().split(' ')).each(function(){
								$(output).append($(document.createElement('span')).html(' '+this).hide());
							});

							$(this).html($(output).html());
						});
						$('#container #tagline #tagline-intro div#intro-part1').show();
						$('#container #tagline #tagline-intro div#intro-part2').hide();
						$('#container #tagline #tagline-intro div#intro-part3').hide();
						$('#container #tagline #tagline-intro div#intro-part4').hide();

						// animate intro words
						Oee.Parallax.prototype.daisyChainFadeIn('#tagline-intro div#intro-part1 span:hidden',function(){
							$('#container #tagline #tagline-intro div#intro-part1').fadeOut();
							$('#container #tagline #tagline-intro div#intro-part2').show();
							Oee.Parallax.prototype.daisyChainFadeIn('#tagline-intro div#intro-part2 span:hidden', function(){
								$('#container #tagline #tagline-intro div#intro-part2').fadeOut();
								$('#container #tagline #tagline-intro div#intro-part3').show();
								Oee.Parallax.prototype.daisyChainFadeIn('#tagline-intro div#intro-part3 span:hidden', function(){
									$('#container #tagline #tagline-intro div#intro-part3').fadeOut();
									$('#container #tagline #tagline-intro div#intro-part4').show();
									Oee.Parallax.prototype.daisyChainFadeIn('#tagline-intro div#intro-part4 span:hidden', function(){
										$('#container #tagline #tagline-intro div#intro-part4').fadeOut({duration: 1000, queue: false, complete: function(){
											$('#container #tagline #tagline-intro').remove();
										}});
										$('#tagline .color-lightBlue, #tagline .color-darkBlue').animate({top: '0%'}, {duration: 2000, easing: 'easeInExpo', complete: function(){
											Oee.Navigation.prototype.updateMap('tagline');
											$('#map').fadeIn('slow');
										}});

										Oee.Parallax.isIntroComplete = true;
										$('body').removeClass('intro');
										Oee.Parallax.prototype.triggerPanel('tagline');
										setTimeout(function(){$('#tagline .contents').fadeIn('slow');},1000);
									});
								});
							});
						});

						$('#container #tagline #tagline-flag-part1.backgroundOverlay').animate({top: '0%', right: '0%'}, {duration: 20500, easing: 'easeInOutSine', queue: false});
						$('#container #tagline #tagline-flag-part2.backgroundOverlay').animate({top: '0%', left: '0%'}, {duration: 20500, easing: 'easeInOutSine', queue: false});
						$('#container #tagline #tagline-flag-part3.backgroundOverlay').animate({top: '0%', left: '0%'}, {duration: 20500, easing: 'easeInOutSine', queue: false});
						$('#container #tagline #tagline-flag-part4.backgroundOverlay').animate({top: '0%', left: '0%'}, {duration: 20500, easing: 'easeInOutSine', queue: false});


					} else {
						/* resettable behaviors */
						$('#container #tagline #tagline-flag-part1.backgroundOverlay').stop().css({top: '0%', right: '0%'});
						$('#container #tagline #tagline-flag-part2.backgroundOverlay').stop().css({top: '0%', left: '0%'});
						$('#container #tagline #tagline-flag-part3.backgroundOverlay').stop().css({top: '0%', left: '0%'});
						$('#container #tagline #tagline-flag-part4.backgroundOverlay').stop().css({top: '0%', left: '0%'});

						$('#container #tagline #tagline-intro div').fadeOut({duration: 250, queue: false, complete: function(){
							$('#container #tagline #tagline-intro').remove();
						}});
						$('#map').fadeIn('fast');

						$('#tagline .color-red').stop().css({top:'0%'},{duration: 700, easing: 'easeOutSine', queue: false});
						$('#tagline .color-lightBlue').stop().css({top:'0%'},{duration: 500, easing: 'easeOutSine', queue: false});
						$('#tagline .color-darkBlue').stop().css({top:'0%'},{duration: 900, easing: 'easeOutSine', queue: false});
						$('#tagline .contents').fadeIn('slow');
					}

					break;
				case "were-better-when-were-connected":
					//$('#message .contents').fadeIn('slow');
					break;
				case "why-do-we-do-what-we-do":
					//$('#were-better-when-were-connected .contents').fadeIn({queue: false});
					break;
				case "commitment1":
					$('#commitment1').show();
					$('#commitment2, #commitment3, #commitment4').hide();
					break;
				case "commitment2":
					$('#commitment2').show();
					$('#commitment1, #commitment3, #commitment4').hide();
					break;
				case "commitment3":
					$('#commitment3').show();
					$('#commitment1, #commitment2, #commitment4').hide();
					break;
				case "commitment4":
					$('#commitment4').show();
					$('#commitment1, #commitment2, #commitment3').hide();
					break;
				case "gallery":
					//$('#gallery .contents').fadeIn({queue: false});
					break;
				case "download-center":
					//$('#download-center .contents').fadeIn({queue: false});
					break;
			}
			if (!Oee.Parallax.isIntroComplete && _panelId != 'tagline') {
				// if you've come into the site not at the top:
				$('#container #tagline #tagline-flag-part1.backgroundOverlay').stop().css({top: '0%', right: '0%'});
				$('#container #tagline #tagline-flag-part2.backgroundOverlay').stop().css({top: '0%', left: '0%'});
				$('#container #tagline #tagline-flag-part3.backgroundOverlay').stop().css({top: '0%', left: '0%'});
				$('#container #tagline #tagline-flag-part4.backgroundOverlay').stop().css({top: '0%', left: '0%'});
				$('#map').fadeIn('fast');
				Oee.Parallax.isIntroComplete = true;
			}
			Oee.Parallax.currPanelId = _panelId;
		}

		// update the location hash, only top level hashes. only update if a deeper one isn't already set
		// edgecase: when user comes in with a deep link, doesn't happen with normal use.
		var newHashPart = "#!" + _panelId;
		if (location.hash.indexOf(newHashPart) < 0) {
			location.hash = "#!" + _panelId;
		}
	},
	daisyChainFadeIn : function(_selector, _callback) {
		if($(_selector).length > 0) {
			var wordCount = $(_selector).eq(0).html().split("&nbsp;").length;
			_delay = wordCount * 300 + 100;
			//console.log(wordCount);
			$(_selector).eq(0).fadeIn(_delay, function(){
				Oee.Parallax.prototype.daisyChainFadeIn(_selector, _callback);
			});
		} else {
			if(typeof _callback == 'function'){
				_callback.call(this);
			}
		}
	},
	scrollTo : function(_panelId) {
		Oee.Navigation.prototype.updateMap(_panelId.replace('#',''));
		var scrollToTiming = 750;

		if($(_panelId).length > 0) {
			if ( $(_panelId).hasClass('hPanel')) {
				console.log($('.panel.at').hasClass('hPanel'));
				if ($('.panel.at').hasClass('hPanels')) {
					// reduce timing inside for hPanel to hPanel Scrolling.
					scrollToTiming = 0;
				}

				var hPanelIndex = $('#map ul li.hPanel').index($('a[href="'+_panelId.replace('#','#\\!')+'"]').parent());

				Oee.Parallax.scrollDelta = -1*($('#container #our-commitments.wrapper').position().top + (Oee.Parallax.panelHeight * .25 * hPanelIndex));
			} else {
				Oee.Parallax.scrollDelta = ($(_panelId).hasClass('wrapper'))?(-1*$(_panelId).position().top):(-1*$(_panelId).parent('.wrapper').position().top);
				
				// 'release' from fixed into absolute for better scrolling effect, but only for non-hPanel scrolls.
				if ($('.panel.at').length && $('.panel.at').parent().length) {
					console.log($('.panel.at').length, $('.panel.at').parent().length);
					$('.panel.at').css({top: $('.panel.at').parent().offset().top * -1}).removeClass('at').addClass('release');
				}
			}
			
			$('#container #cart').animate({
				marginTop: Oee.Parallax.scrollDelta
			}, {queue: false, duration: scrollToTiming, easing: 'easeInExpo', complete: function(){
				Oee.Parallax.prototype.slidePanels();
				$('.panel.release').removeClass('release').css({top: ''});
			}});
		} else {
			console.log(_panelId, 'does not exist');
		}
	},
	hPanelDefaultStates : function() {
		if( $('#commitments').hasClass('above') ) {
			//console.log('hPanelDefaultState trigger Commitment1')
			$('#commitment1').show();
			$('#commitmentBackgrounds.hPanelWindow .color-sandStone').stop(true,true).css({
				width: '85%'
			},{duration: 500, easing: 'easeOutSine', queue: false});
			$('#commitmentBackgrounds.hPanelWindow .color-red, #commitmentBackgrounds.hPanelWindow .color-lightBlue, #commitmentBackgrounds.hPanelWindow .color-darkBlue').stop(true,true).css({
				width: '5%'
			},{duration: 500, easing: 'easeOutSine', queue: false});
		} else if ( $('#commitments').hasClass('below') ) {
			//console.log('hPanelDefaultState trigger Commitment4')
			$('#commitment4').show();
			$('#commitmentBackgrounds.hPanelWindow .color-darkBlue').stop(true,true).css({
				width: '85%'
			},{duration: 500, easing: 'easeOutSine', queue: false});
			$('#commitmentBackgrounds.hPanelWindow .color-red, #commitmentBackgrounds.hPanelWindow .color-lightBlue, #commitmentBackgrounds.hPanelWindow .color-sandStone').stop(true,true).css({
				width: '5%'
			},{duration: 500, easing: 'easeOutSine', queue: false});
		} else if ( $('#commitments').hasClass('at') ) {
			// do nothing, let the scroll funciton handle it.
		}
	},
	scrollPromptAnimation : function() {
		// no alpha blending w/ animated gifs, using css sprite animations.
		var wait = (Oee.Parallax.scrollPromptAnimationIndex == 0) ? 2500:100;
		
		if($('#tagline').hasClass('at')) {
			
			$('#scrollPrompt').css('background-position','-'+47*Oee.Parallax.scrollPromptAnimationIndex+'px 0px');
			if (Oee.Parallax.scrollPromptAnimationIndex < 3) {
				Oee.Parallax.scrollPromptAnimationIndex++;
			} else {
				Oee.Parallax.scrollPromptAnimationIndex = 0;
			}
		}
	
		setTimeout(Oee.Parallax.prototype.scrollPromptAnimation, wait);
	}
});

});