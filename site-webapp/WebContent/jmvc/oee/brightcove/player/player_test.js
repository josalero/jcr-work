steal('funcunit').then(function(){

module("Flag.Brightcove.Player", { 
	setup: function(){
		S.open("//flag/brightcove/player/player.html");
	}
});

test("Text Test", function(){
	equals(S("h1").text(), "Flag.Brightcove.Player Demo","demo text");
});


});