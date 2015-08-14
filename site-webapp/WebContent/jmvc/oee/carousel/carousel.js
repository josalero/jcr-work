steal( 
	'jquery/controller',
	'jquery/view/ejs' 
).then( 
	'./carousel.css',
	function($){

/**
 * @class Oee.Carousel
 */
$.Controller('Oee.Carousel',
/** @Static */
{
	defaults : {}
},
/** @Prototype */
{
	init : function(){
		var $carousel = $(this.element).find('ul');

		// carousel setup
		$carousel.find('li').each(function(index){
			$(this).append(
				$(document.createElement('a')).attr({href: '#'}).addClass('move').append(
					$(document.createElement('span')).addClass('accessibility-hidden')
				)
			);
			$(this).find('.pageCounter').text((index+1) + '/' + $carousel.find('li').length);
		});

		$carousel.find('li .textContainer').each(function(){
			$(this).addClass('closed');
		});

		// initial layout of carousel
		$carousel.find('li').first().addClass('at').next().addClass('next');
		if($carousel.find('li').length > 2) {
			$carousel.find('li').last().addClass('prev');
			$carousel.find('.prev').prependTo($carousel);
		}
		$carousel.find('li.prev .move .accessibility-hidden').text('Show Previous Tile in "' + $('.carousel').parents('.hPanel').data('rightnavtitle') + '" Carousel');
		$carousel.find('li.next .move .accessibility-hidden').text('Show Next Tile in "' + $('.carousel').parents('.hPanel').data('rightnavtitle') + '" Carousel');
	},
	'li.next a.move click' : function(el, ev){
		ev.preventDefault ? ev.preventDefault() : ev.returnValue = false; 
		Oee.Carousel.prototype.move($(el).parents('.carousel').find('ul'), 1);
		
		var category = $(el).parents('.vPanel, .hPanel').data('galabel');
		var action = "Select";
		var label = "Arrow-Right";
		
		Oee.Tagging.prototype.push_ga_tag(category, action, label);
	},
	'li.prev a.move click' : function(el, ev){
		ev.preventDefault ? ev.preventDefault() : ev.returnValue = false; 
		Oee.Carousel.prototype.move($(el).parents('.carousel').find('ul'), -1);
		
		var category = $(el).parents('.vPanel, .hPanel').data('galabel');
		var action = "Select";
		var label = "Arrow-Left";
		
		Oee.Tagging.prototype.push_ga_tag(category, action, label);
	},
	move : function($carousel, _direction) {
		var elementsTotal = $carousel.find('li').length;
		var currIndex = $carousel.find('li').index($carousel.find('li.at'));
		var newIndex = (currIndex + _direction > elementsTotal - 1)?0:(currIndex + _direction);
		var nextIndex = (newIndex == elementsTotal - 1)?0:(newIndex + 1);
		var prevIndex = (newIndex == 0)?(elementsTotal - 1):(newIndex - 1);

		console.log(currIndex, newIndex);

		$carousel.find('li').removeClass('at prev next');
		//$carousel.find('li .textContainer.open').removeClass('open').css('height', '');
		Oee.Carousel.prototype.expand($carousel.find('li .textContainer.open'), 'close');

		$carousel.find('li').eq(newIndex).addClass('at');
		$carousel.find('li').eq(nextIndex).addClass('next');
		$carousel.find('li').eq(prevIndex).addClass('prev');

		// ada compliance
		$carousel.find('li.prev .move .accessibility-hidden').text('Show Previous Tile in "' + $('.carousel').parents('.hPanel').data('rightnavtitle') + '" Carousel');
		$carousel.find('li.next .move .accessibility-hidden').text('Show Next Tile in "' + $('.carousel').parents('.hPanel').data('rightnavtitle') + '" Carousel');

		// reposition tiles so they show up right
		$carousel.find('li.prev').insertBefore($carousel.find('li.at'));
		$carousel.find('li.next').insertAfter($carousel.find('li.at'));
		
		var category = $carousel.parents('.vPanel, .hPanel').data('galabel');
		var action = "Show";
		var label = $carousel.find('li.at').find('div.textContainer').data('tilecmsid');
		
		if (label == undefined) {
			
			label = $carousel.find('li.at').find('div.videoContainer').data('tilecmsid');
		}
		
		label = "CMS_ID_" + label;
		
		Oee.Tagging.prototype.push_ga_tag(category, action, label);
	},
	'li.at .textContainer:not(.open) click' : function(el, ev){
		ev.preventDefault ? ev.preventDefault() : ev.returnValue = false; 
		Oee.Carousel.prototype.expand($(el), 'open');
	},
	'li.at .textContainer.open click' : function(el, ev){
		ev.preventDefault ? ev.preventDefault() : ev.returnValue = false; 
		Oee.Carousel.prototype.expand($(el), 'close');
	},
	
	'li.at .textContainer:not(.open) .caption .expand click' : function(el, ev){	
		ev.preventDefault ? ev.preventDefault() : ev.returnValue = false; 
		Oee.Carousel.prototype.expand($(el).parents('.textContainer'), 'open');	
	},
	
	'li.at .textContainer.open .caption .expand click' : function(el, ev){
		ev.preventDefault ? ev.preventDefault() : ev.returnValue = false; 
		Oee.Carousel.prototype.expand($(el).parents('.textContainer'), 'close');
	},
	
	expand : function($tile, _direction){

		if(_direction == 'open') {
			
			$tile.removeClass('closed').addClass('open');
			$tile.animate({height: '490px'},{queue: false});
			$tile.find('.textBlock').animate({width: '100%'},{queue: false});
			$tile.find('.textBody').fadeIn({queue: false});
			$tile.find('.caption .expand .accessibility-hidden').text('Open "' + $tile.find('.caption p').text() + '" Tile');
			
			var ga_category = $tile.find('.caption .expand').data('gacategory');
			var ga_action = $tile.find('.caption .expand').data('gaaction');
			var ga_label = "CMS_ID_" + $tile.find('.caption .expand').data('galabel');
			
			Oee.Tagging.prototype.push_ga_tag(ga_category, ga_action, ga_label);
		} 
		else {
			
			$tile.removeClass('open').addClass('closed');
			if($tile.parent('li').hasClass('at')) {
				$tile.animate({height: '290px'},{queue: false, complete: function(){$(this).css('height', '');}});
			} else {
				$tile.css('height', '');
			}
			$tile.find('.textBlock').animate({width: '50%'},{queue: false});
			$tile.find('.textBody').fadeOut({queue: false});
			$tile.find('.caption .expand .accessibility-hidden').text('Close "' + $tile.find('.caption p').text() + '" Tile');
		}
	}
});

});