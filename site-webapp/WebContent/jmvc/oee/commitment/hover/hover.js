steal( 
	'jquery/controller',
	'jquery/view/ejs'
).then( 
	'./hover.css',
	'./views/init.ejs', 
	function($){

/**
 * @class Oee.Commitment.Hover
 */
$.Controller('Oee.Commitment.Hover',
/** @Static */
{
	defaults : {}
},
/** @Prototype */
{
	init : function(){
		// nothing to do on init...
	},
	'ul li a mouseenter' : function(el, ev) {
		//console.log('hover!', $(el).data('hover'));
		Oee.Commitment.Hover.prototype.activate($(el).data('hover'), $(el));
	},
	'ul li a mouseleave' : function(el) {
		$('#commitmentHover').fadeOut('fast',function(){$(this).remove();});
	},
	'ul li a click' : function(el) {
		/* ADA */
		$('#commitmentHover').remove();
		Oee.Commitment.Hover.prototype.activate($(el).data('hover'), $(el), true);
	},
	activate : function(_text, $el, _ada) {
		$('#commitmentHover').stop(true,true).remove();

		$(document.createElement('div')).attr('id','commitmentHover').append(
			$(document.createElement('div')).addClass('arrow').css({
				left: $el.offset().left + ($el.width()/2) - 10 - $el.parents('.commitmentContainer').offset().left
			}),
			$(document.createElement('span')).html(_text)
		).css({
			top: $el.offset().top + $el.outerHeight() + 15,
			left: $el.parents('.commitmentContainer').offset().left
		}).hide().appendTo('body').fadeIn('fast');

		if (typeof _ada != 'undefined') {
			$('#commitmentHover').append(
				$(document.createElement('a')).addClass('close').attr('href','#').text('close').on('click',function(e){
					e.preventDefault ? e.preventDefault() : e.returnValue = false; 
					$('#commitmentHover').fadeOut('fast',function(){$(this).remove();});
				})
			);

			$('#commitmentHover a.close').focus();
		}
	}

});

});