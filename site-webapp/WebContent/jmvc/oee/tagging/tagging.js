steal( 'jquery/controller','jquery/view/ejs' )
	.then( './views/init.ejs', function($){

/**
 * @class Oee.Tagging
 */
$.Controller('Oee.Tagging',
/** @Static */
{
	defaults : {}
},

/** @Prototype */
{
	init : function(){
		
		$('a').on('click', function(event){
		    
			var el = this;
			var href = $(el).attr('href');
			
			if (href.charAt(0) == '#') {				
				event.preventDefault ? event.preventDefault() : event.returnValue = false;
			}
			
			Oee.Tagging.prototype.tag(this);
		});
	},
	
	tag : function(el, ev) {
		
		var ga_category = $(el).data('gacategory');
		var ga_action = $(el).data('gaaction');
		var ga_label = $(el).data('galabel');
		
		if (ga_label != undefined && ga_action != undefined && ga_category != undefined) {
			
			// Push the event to GA
			
			// Example: _gaq.push(['_trackEvent', 'Overview', 'Select', 'Nav-bar-Overview']);
			
			if ($(el).is('.expand')) {

				// Skip expand/collapse link on the See-It-In-Action tile
			}
			else {

				steal.dev.log("tag: ga_category = " + ga_category + ", ga_action = " + ga_action + ", ga_label = " + ga_label);				
				_gaq.push(['_trackEvent', ga_category, ga_action, ga_label]);
			}
		}
	},

	push_ga_tag : function(category, action, label) {
		
		var ga_category = category;
		var ga_action = action;
		var ga_label = label;
		
		if (ga_label != undefined && ga_action != undefined && ga_category != undefined) {
			
			steal.dev.log("push_ga_tag: ga_category = " + ga_category + ", ga_action = " + ga_action + ", ga_label = " + ga_label);

			// Push the event to GA
			
			// Example: _gaq.push(['_trackEvent', 'Overview', 'Select', 'Nav-bar-Overview']);
			
			_gaq.push(['_trackEvent', ga_category, ga_action, ga_label]);
		}
	}
	
});

});