steal( 
	'jquery/controller',
	'jquery/view/ejs' 
).then( 
	'./navigation.css',
	'./views/tooltip.ejs', 
	function($){

/**
 * @class Oee.Navigation
 */
$.Controller('Oee.Navigation',
/** @Static */
{
	defaults : {}
},
/** @Prototype */
{
	init : function(){
		console.log($('.vPanel, .hPanel').length);

		$('.vPanel, .hPanel').each(function(){
			
			console.log($(this).data('rightnavtitle'));
			
			var rightnavtitle = $(this).data('rightnavtitle');
			var galabel = $(this).data('galabel');
			
			$('#map ul').append(
				$(document.createElement('li')).attr({
					'class': 'stop ' + this.className,
					'rel': this.id
				}).append(	
					$(document.createElement('a')).data('tooltip', rightnavtitle).attr({
						'href': '#!'+this.id
					}).attr({
						'data-gacategory': 'Overview'
					}).attr({
						'data-gaaction': 'Select'
					}).attr({
						'data-galabel': 'Right-Nav-Bar-' + galabel
					}).append(
						$(document.createElement('span')).addClass('accessibility-hidden').text('Jump to: ' + rightnavtitle)
					)
				)
			);
		});

		$('#map ul li:first-child').addClass('first');
		$('#map ul li:last-child').addClass('last');
		$('#map ul li.hPanel').last().addClass('last');
		$('a[href^=#\\!]').click(function(event){
			event.preventDefault ? event.preventDefault() : event.returnValue = false; 
			Oee.Parallax.prototype.scrollTo($(this).attr('href').replace('!',''));
		});

		$('#map').css({
			'top': ($(window).height() - $('#map').height())/2
		});

		$('#map').hide();

		$('#map li').first().addClass('current');

		/* ADA Compliance */
		if($('#map li.current').length){
			var nextSectionName = '';
			if ($('#map li.current').next().length != 0) {
				nextSectionName = $('#map li.current').next().find('a span.accessibility-hidden').html().split(': ')[1];
			} else {
				nextSectionName = $('#map li').first().find('a span.accessibility-hidden').html().split(': ')[1];
			}
			$('#rightbottom a').prepend(
				$(document.createElement('span')).addClass('accessibility-hidden').text('Jump to Next Section: ' + nextSectionName)
			);
		}
	},
	
	updateMap : function(_id) {
		$("#nav li.at").removeClass('at');
		
		if ($("#nav li a[href='#\\!"+_id+"']").length > 0) {
			$("#nav li a[href='#\\!"+_id+"']").parent().addClass('at');
		} else if (_id.indexOf('commitment') > -1) {
			$("#nav li a[href='#\\!our-commitments']").parent().addClass('at');
		}

		$("#map .stop").removeClass('current');
		$("#map .stop[rel='"+_id+"']").addClass('current');

		if(_id == $("#map .stop").last().attr('rel')) {
			$('#rightbottom a').addClass('atBottom');
			$('#rightbottom a').attr('href','#!'+$("#map .stop").first().attr('rel'));
			$('#rightbottom a span.accessibility-hidden');

			/* ADA Compliance */
			var nextSectionName = $('#map li').first().find('a span.accessibility-hidden').html().split(': ')[1];
			$(document.createElement('span')).addClass('accessibility-hidden').text('Jump to First Section: ' + $('#map li.current a span.accessibility-hidden').html().split(': ')[1]);
		} 
		else {
			$('#rightbottom a').removeClass('atBottom');
			$('#rightbottom a').attr('href','#!'+$("#map .stop[rel='"+_id+"']").next().attr('rel'));

			/* ADA Compliance */
			if($('#map li.current').length) {
				console.log($('#map li.current').next(), $('#map li.current').next().find('a span.accessibility-hidden'));
				var nextSectionName = $('#map li.current').next().find('a span.accessibility-hidden').html().split(': ')[1];
				$(document.createElement('span')).addClass('accessibility-hidden').text('Jump to Next Section: ' + $('#map li.current a span.accessibility-hidden').html().split(': ')[1]);	
			}
		}
	},
	
	'li.stop a mouseenter' : function(el, ev){

		$('#rightnav-tooltip').stop(true,true).remove();
		$(document.createElement('div')).attr('id','rightnav-tooltip').addClass('text-darkSand').html(
			"//oee/navigation/views/tooltip.ejs",{
			tooltip: $(el).data('tooltip') + ' &#8250;'
		}).css({
			top: $(el).offset().top,
			right: '70px'
		}).hide().appendTo('body').fadeIn('fast');
	},
	'li.stop a mouseleave' : function(){
		$('#rightnav-tooltip').fadeOut('fast',function(){$(this).remove();});
	}

});

});