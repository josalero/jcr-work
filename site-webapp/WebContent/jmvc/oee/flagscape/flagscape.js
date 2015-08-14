steal( 'jquery/controller','jquery/view/ejs' )
	.then( './flagscape.css','./views/init.ejs', function($){

/**
 * @class Oee.Flagscape
 */
$.Controller('Oee.Flagscape',
/** @Static */
{
	defaults : {}
},
/** @Prototype */
{
	init : function(){
		$('#nav li a[href^=#\\!]').each(function(){
			$(this).prepend(
				$(document.createElement('span')).addClass('accessibility-hidden').text('Jump to: ')
			);
		});
	}
});

});