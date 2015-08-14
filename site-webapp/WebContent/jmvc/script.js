if ( ! window.console ) console = { log: function(){} };

var 
	ltie9 = false,//$('html').hasClass('lt-ie9'),
	transitionGap = 50,
	bottomGap = 25,
	currPanelId = null,
	panelHeight = 800,
	hPanelHeight = 800,
	windowHeight = 800,
	totalHeight = 0,
	isIntroComplete = false, // should be false for launch
	panelWidth85 = 0,
	ourCommitmentsOffsetTop = 0,
	scrollDelta = 0,
	scrollAmount = (ltie9)?25:15,
	scrollBarWidth = 20;

$(function(){

	$('#tagline .contents, .hPanel').hide();
	$('#leadersInputForm .part2').hide();
	$('#leadersInputForm .part3').hide();

	$('#leadersInputForm .part1 button').click(function(){expandLeadersForm();});
	$('#leadersInputForm .part2 button').click(function(){leadersFormNext();});
	$('#leadersInputForm .part3 button').click(function(){leadersFormSubmit();});

	$('.vPanel, .hPanel').each(function(){
		$('#map ul').append(
			$(document.createElement('li')).attr({
				'class': 'stop ' + this.className,
				'rel': this.id
			}).append(
				$(document.createElement('a')).attr({
					'href': '#'+this.id
				}).append(
					$(document.createElement('span')).addClass('accessibility-hidden').text($(this).text())
				)
			)
		);
	});

	scrollPromptAnimation();

	repositionElements();
	$('#map ul li:last-child').addClass('last');
	$('#map ul li.hPanel').last().addClass('last');
	$('a[href^=#]').click(function(){
		scrollTo($(this).attr('href'));
		return false;
	});

	$('#map').hide();

	slidePanels();
	hPanelDefaultStates();

	/* Window Bindings */
	$(window).scroll(function() {
		slidePanels();
	})
	$(window).resize(function(){
		slidePanels();
		repositionElements();
	});

	$("#container").mousewheel(function(objEvent, intDelta){
		if (intDelta > 0){
			if (scrollDelta + scrollAmount > 0) {
				console.log(scrollDelta + scrollAmount);
				return false;
			}
			scrollDelta += scrollAmount;
		}
		else if (intDelta < 0){
			if (scrollDelta-scrollAmount<-1*(totalHeight-panelHeight-30)) {
				console.log(scrollDelta-scrollAmount);
				return false
			}
			scrollDelta -= scrollAmount;
		}
		
		$("#scrollHandle").css({'top': -1*((scrollDelta/(totalHeight))*windowHeight)});
		$("#cart").css({marginTop: scrollDelta});
		setTimeout(function(){slidePanels();},250);
	});

    $("#scrollHandle").draggable({
    	containment: "#scrollBar", 
    	scroll: false, 
    	axis: "y",
    	drag: function(){
    		var newScrollDelta = -1 * (parseInt($("#scrollHandle").css('top'))/(windowHeight-30)) * totalHeight;
    		if (newScrollDelta > -1*(totalHeight-panelHeight)) {
	    		scrollDelta = newScrollDelta;
				$('#container #cart').css({
					marginTop: scrollDelta
				});
				slidePanels();
    		}
    	}
    });

});

function repositionElements() {
	panelHeight = hPanelHeight = ($(window).height() - bottomGap);

	$('#map').css({
		'top': ($(window).height() - $('#map').height())/2
	});
	$('#container, #scrollBar').css({
		height: $(window).height()
	});
	$('#container .top-shadow').css({
		top: panelHeight
	});
	$('#container .vPanel, #container .hPanels, #container .hPanel, #container .backgroundOverlay, #container .window, #leaders-on-the-ground .leadersContainer, .hPanelWindow, .hPanel .window').css({
		height: panelHeight
	});
	$('#container .wrapper').css({
		height: Math.round(($(window).height() * 1.1))
	});
	$('#container #our-commitments.wrapper').css({
		height: Math.round((panelHeight * .75) * $('#container .hPanel').length)
	});
	$('#container #commitment1 .contents').css({
		width: $(window).width() * 1
	});
	$('#container #commitment2 .contents').css({
		width: $(window).width() * .9
	});
	$('#container #commitment3 .contents').css({
		width: $(window).width() * .8
	});
	$('#container #commitment4 .contents').css({
		width: $(window).width() * .7
	})
	$('#container .hPanelWindow .color-darkBlue').css({
		width: $(window).width() * 1.1
	});
	$('#container, #container .panel').css({
		width: $(window).width() - scrollBarWidth
	});

	totalHeight = $('#cart').height();
	windowHeight = $(window).height();
	panelWidth85 = Math.round($(window).width() * .85);
	ourCommitmentsOffsetTop = $('#container #our-commitments').offset().top;
}

function updateMap(_id) {
	$("#map .stop").removeClass('current');
	$("#map .stop[rel='"+_id+"']").addClass('current');

	if(_id == $("#map .stop").last().attr('rel')) {
		$('#rightbottom a').addClass('atBottom');
		$('#rightbottom a').attr('href','#'+$("#map .stop").first().attr('rel'));
	} else {
		$('#rightbottom a').removeClass('atBottom');
		$('#rightbottom a').attr('href','#'+$("#map .stop[rel='"+_id+"']").next().attr('rel'));
	}
}

function slidePanels() {
	
	$("#scrollHandle").css({'top': -1*((scrollDelta/(totalHeight))*windowHeight)});

	var scrollTop = scrollDelta * -1;
	//console.log(scrollTop);
	panelWidth85 = Math.round($(window).width() * .85);

	$('#container .panel').each(function(){
        var $el = $(this),
            top = $el.parent().position().top,
            height = $el.parent().height(),
            targetId = $el.attr('id');

        if (scrollTop + panelHeight > top + height) {
        	// user is below this panel
			$el.removeClass('above below at').addClass('below');
        } else if (scrollTop >= top) {
        	//console.log($el, scrollTop, top, height, targetId)
			// user is at this panel
			$el.removeClass('above below at').addClass('at');
			//$el.parent('.wrapper').next().find('.panel').addClass('next').css({top: 'auto', bottom: (-1 * panelHeight) + 25 });
			if($el.hasClass('hPanels')) {

				var hPanelsScroll = scrollTop - ourCommitmentsOffsetTop,
					hScrollInterval = panelHeight * .5;

				if ( hPanelsScroll >= hScrollInterval * 3 ) {
					if($('#commitment4').is(':hidden')) {
						triggerPanel('commitment4');
						if(ltie9){
							$('.hPanelWindow .color-darkBlue').css({
								width: '85%'
							});
							$('.hPanelWindow').eq(0).find('.color-red, .color-lightBlue, .color-sandStone').css({
								width: '5%'
							});
						} else {
							$('.hPanelWindow .color-darkBlue').stop(true,true).animate({
								width: '85%'
							},{duration: 500, easing: 'easeOutSine', queue: false});
							$('.hPanelWindow').eq(0).find('.color-red, .color-lightBlue, .color-sandStone').stop(true,true).animate({
								width: '5%'
							},{duration: 500, easing: 'easeOutSine', queue: false});
						}
					}
					updateMap('commitment4');
				} else if ( hPanelsScroll >= hScrollInterval * 2 ) {
					if($('#commitment3').is(':hidden')) {
						triggerPanel('commitment3');
						if(ltie9){
							$('.hPanelWindow .color-lightBlue').css({
								width: '85%'
							});
							$('.hPanelWindow').eq(0).find('.color-red, .color-sandStone, .color-darkBlue').css({
								width: '5%'
							});
						} else {
							$('.hPanelWindow .color-lightBlue').stop(true,true).animate({
								width: '85%'
							},{duration: 500, easing: 'easeOutSine', queue: false});
							$('.hPanelWindow').eq(0).find('.color-red, .color-sandStone, .color-darkBlue').stop(true,true).animate({
								width: '5%'
							},{duration: 500, easing: 'easeOutSine', queue: false});
						}
					}
					updateMap('commitment3');
				} else if ( hPanelsScroll >= hScrollInterval * 1 ) {
					if($('#commitment2').is(':hidden')) {
						triggerPanel('commitment2');
						if(ltie9){
							$('.hPanelWindow .color-red').css({
								width: '85%'
							});
							$('.hPanelWindow').eq(0).find('.color-sandStone, .color-lightBlue, .color-darkBlue').css({
								width: '5%'
							});
						} else {
							$('.hPanelWindow .color-red').stop(true,true).animate({
								width: '85%'
							},{duration: 500, easing: 'easeOutSine', queue: false});
							$('.hPanelWindow').eq(0).find('.color-sandStone, .color-lightBlue, .color-darkBlue').stop(true,true).animate({
								width: '5%'
							},{duration: 500, easing: 'easeOutSine', queue: false});
						}
					}
					updateMap('commitment2');
				} else {
					if($('#commitment1').is(':hidden')) {
						triggerPanel('commitment1');
						if(ltie9){
							$('.hPanelWindow .color-sandStone').css({
								width: '85%'
							});
							$('.hPanelWindow').eq(0).find('.color-red, .color-lightBlue, .color-darkBlue').css({
								width: '5%'
							});
						} else {
							$('.hPanelWindow .color-sandStone').stop(true,true).animate({
								width: '85%'
							},{duration: 500, easing: 'easeOutSine', queue: false});
							$('.hPanelWindow').eq(0).find('.color-red, .color-lightBlue, .color-darkBlue').stop(true,true).animate({
								width: '5%'
							},{duration: 500, easing: 'easeOutSine', queue: false});
						}
					}
					updateMap('commitment1');
				}
			} else {
				triggerPanel(targetId);
			}


        } else {
			// user is above this panels
			$el.removeClass('above below at next').addClass('above');
        }
	});

}

function triggerPanel(_panelId) {
	console.log('triggerPanel',_panelId, currPanelId);
	if (_panelId != currPanelId) {
		
		//console.log('firing open scripts for panel:',_panelId);
		updateMap(_panelId);

		switch(_panelId) {
			case "tagline":
				$('#rightbottom a').attr('href','#message');
				if (!isIntroComplete) {
					/* intro animation that does not re-set */
					$('body').addClass('intro');

					// setup intro words
					$('#container #tagline #tagline-intro div').each(function(){
						var output = $(document.createElement('div'));
						$($(this).text().split(' ')).each(function(){
							$(output).append($(document.createElement('span')).text(' '+this).hide());
						});

						$(this).html($(output).html());
					});
					$('#container #tagline #tagline-intro div#intro-part1').show();
					$('#container #tagline #tagline-intro div#intro-part2').hide();
					$('#container #tagline #tagline-intro div#intro-part3').hide();
					$('#container #tagline #tagline-intro div#intro-part4').hide();

					// animate intro words
					var t1 = setTimeout(function(){
						daisyChainFadeIn('#tagline-intro div#intro-part1 span:hidden', 750);
					}, 500);
					var t2 = setTimeout(function(){
						$('#container #tagline #tagline-intro div#intro-part1').fadeOut({duration: 250, queue: false, complete: function(){
							$('#container #tagline #tagline-intro div#intro-part2').show();
							daisyChainFadeIn('#tagline-intro div#intro-part2 span:hidden', 750);
						}});
					}, 8000);
					var t3 = setTimeout(function(){
						$('#container #tagline #tagline-intro div#intro-part2').fadeOut({duration: 250, queue: false, complete: function(){
							$('#container #tagline #tagline-intro div#intro-part3').show();
							daisyChainFadeIn('#tagline-intro div#intro-part3 span:hidden', 750);
						}});
					}, 13750);
					var t4 = setTimeout(function(){
						$('#container #tagline #tagline-intro div#intro-part3').fadeOut({duration: 250, queue: false, complete: function(){
							$('#container #tagline #tagline-intro div#intro-part4').show();
							daisyChainFadeIn('#tagline-intro div#intro-part4 span:hidden', 750);
						}});
					}, 18500);

					$('#container #tagline #tagline-flag-part1.backgroundOverlay').animate({top: 0, right: 0}, {duration: 20500, easing: 'easeInOutSine', queue: false});
					$('#container #tagline #tagline-flag-part2.backgroundOverlay').animate({top: 0, left: 0}, {duration: 20500, easing: 'easeInOutSine', queue: false});
					$('#container #tagline #tagline-flag-part3.backgroundOverlay').animate({top: 0, left: 0}, {duration: 20500, easing: 'easeInOutSine', queue: false});
					$('#container #tagline #tagline-flag-part4.backgroundOverlay').animate({top: 0, left: 0}, {duration: 20500, easing: 'easeInOutSine', queue: false});

					var t5 = setTimeout(function(){
						$('#container #tagline #tagline-intro div#intro-part4').fadeOut({duration: 1000, queue: false, complete: function(){
							$('#container #tagline #tagline-intro').remove();
						}});
						$('#tagline .color-lightBlue, #tagline .color-darkBlue').animate({top: 0}, {duration: 2000, easing: 'easeInExpo', complete: function(){
							$('#map').fadeIn('slow');
						}})

						isIntroComplete = true;
						$('body').removeClass('intro');
						triggerPanel('tagline');
						setTimeout(function(){$('#tagline .contents').fadeIn('slow');},1000);
					}, 21750);

				} else {
					/* resettable behaviors */
					$('#container #tagline #tagline-flag-part1.backgroundOverlay').stop().css({top: 0, right: 0});
					$('#container #tagline #tagline-flag-part2.backgroundOverlay').stop().css({top: 0, left: 0});
					$('#container #tagline #tagline-flag-part3.backgroundOverlay').stop().css({top: 0, left: 0});
					$('#container #tagline #tagline-flag-part4.backgroundOverlay').stop().css({top: 0, left: 0});

					$('#map').fadeIn('fast');

					$('#tagline .color-red').stop().animate({top:'0'},{duration: 700, easing: 'easeOutSine', queue: false});
					$('#tagline .color-lightBlue').stop().animate({top:'0'},{duration: 500, easing: 'easeOutSine', queue: false});
					$('#tagline .color-darkBlue').stop().animate({top:'0'},{duration: 900, easing: 'easeOutSine', queue: false, complete: function(){
						$('#message .top-shadow').fadeIn('slow');
					}});
					$('#tagline .contents').fadeIn('slow');
				}

				break;
			case "message":
				//$('#message .contents').fadeIn('slow');
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
			case "leaders-on-the-ground":
				//$('#leaders-on-the-ground .contents').fadeIn({queue: false});
				break;
			case "gallery":
				//$('#gallery .contents').fadeIn({queue: false});
				break;
			case "download-center":
				//$('#download-center .contents').fadeIn({queue: false});
				break;
		}
		if (!isIntroComplete && _panelId != 'tagline') {
			// if you've come into the site not at the top:
			$('#container #tagline #tagline-flag-part1.backgroundOverlay').stop().css({top: 0, right: 0});
			$('#container #tagline #tagline-flag-part2.backgroundOverlay').stop().css({top: 0, left: 0});
			$('#container #tagline #tagline-flag-part3.backgroundOverlay').stop().css({top: 0, left: 0});
			$('#container #tagline #tagline-flag-part4.backgroundOverlay').stop().css({top: 0, left: 0});
			$('#map').fadeIn('fast');
			isIntroComplete = true;
		}
		currPanelId = _panelId;
	}
}

function daisyChainFadeIn(_selector, _delay) {
	if($(_selector).length > 0) {
		$(_selector).eq(0).fadeIn(_delay, function(){
			daisyChainFadeIn(_selector, _delay);
		});
	}
}

function scrollableElement(els) {
	for (var i = 0, argLength = arguments.length; i <argLength; i++) {
		var el = arguments[i],
			$scrollElement = $(el);
		if ($scrollElement.scrollTop()> 0) {
			return el;
		} else {
			$scrollElement.scrollTop(1);
			var isScrollable = $scrollElement.scrollTop()> 0;
			$scrollElement.scrollTop(0);
			if (isScrollable) {
				return el;
			}
		}
	}
	return [];
}

function scrollTo(_panelId) {
	if($(_panelId).length > 0) {
		isSliding = true;
		if ( $(_panelId).attr('class') == 'hPanel' ) {
			var hPanelIndex = $('#map ul li.hPanel').index($('a[href="'+_panelId+'"]').parent());

			scrollDelta = -1*($('#container #our-commitments.wrapper').position().top + (panelHeight * .5 * hPanelIndex));
		} else {
			scrollDelta = ($(_panelId).hasClass('wrapper'))?(-1*$(_panelId).position().top):(-1*$(_panelId).parent('.wrapper').position().top);
		}
		console.log(scrollDelta);
		$('#container #cart').animate({
			marginTop: scrollDelta
		}, {duration: 500, easing: 'easeInExpo', complete: function(){
			slidePanels();
		}});
	} else {
		console.log(_panelId, 'does not exist');
	}
}

function expandLeadersForm() {
	$('#leadersInputForm').animate({height: panelHeight - ($('#leaders-on-the-ground h2').height() + 130)});
	$('#leadersInputForm .part1').fadeOut();
	$('#leadersInputForm .part2').fadeIn();
}
function leadersFormNext() {
	$('#leadersInputForm .part2').fadeOut();
	$('#leadersInputForm .part3').fadeIn();	
}
function leadersFormSubmit() {
	$('#leadersInputForm').animate({height: $('#leadersInputForm .part1').height()});
	$('#leadersInputForm .part3').fadeOut();
	$('#leadersInputForm .part1').fadeIn();		
}

function hPanelDefaultStates() {
	if( $('#commitments').hasClass('above') ) {
		//console.log('hPanelDefaultState trigger Commitment1')
		$('#commitment1').show();
		$('.hPanelWindow .color-sandStone').stop(true,true).css({
			width: '85%'
		},{duration: 500, easing: 'easeOutSine', queue: false});
		$('.hPanelWindow .color-red, .hPanelWindow .color-lightBlue, .hPanelWindow .color-darkBlue').stop(true,true).css({
			width: '5%'
		},{duration: 500, easing: 'easeOutSine', queue: false});

	} else if ( $('#commitments').hasClass('below') ) {
		//console.log('hPanelDefaultState trigger Commitment4')
		$('#commitment4').show();
		$('.hPanelWindow .color-darkBlue').stop(true,true).css({
			width: '85%'
		},{duration: 500, easing: 'easeOutSine', queue: false});
		$('.hPanelWindow .color-red, .hPanelWindow .color-lightBlue, .hPanelWindow .color-sandStone').stop(true,true).css({
			width: '5%'
		},{duration: 500, easing: 'easeOutSine', queue: false});

	} else if ( $('#commitments').hasClass('at') ) {

		// do nothing, let the scroll funciton handle it.

	}
}

var scrollPromptAnimationIndex = 0;
function scrollPromptAnimation() {
	var wait = (scrollPromptAnimationIndex == 0)?2500:100;
	if(document.getElementById('tagline').className.indexOf('at') > -1) {
		$('#scrollPrompt').css('background-position','-'+47*scrollPromptAnimationIndex+'px 0px');
		if (scrollPromptAnimationIndex < 3) {
			scrollPromptAnimationIndex++;
		} else {
			scrollPromptAnimationIndex = 0;
		}
	}
	setTimeout(scrollPromptAnimation, wait);
}