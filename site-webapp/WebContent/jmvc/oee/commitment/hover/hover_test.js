steal('funcunit').then(function(){

module("Oee.Commitment.Hover", { 
	setup: function(){
		S.open("//oee/commitment/hover/hover.html");
	}
});

test("Text Test", function(){
	equals(S("h1").text(), "Oee.Commitment.Hover Demo","demo text");
});


});