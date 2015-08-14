steal('funcunit').then(function(){

module("Oee.Tagging", { 
	setup: function(){
		S.open("//oee/tagging/tagging.html");
	}
});

test("Text Test", function(){
	equals(S("h1").text(), "Oee.Tagging Demo","demo text");
});


});