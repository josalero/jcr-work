steal('funcunit').then(function(){

module("Oee.Flagscape", { 
	setup: function(){
		S.open("//oee/flagscape/flagscape.html");
	}
});

test("Text Test", function(){
	equals(S("h1").text(), "Oee.Flagscape Demo","demo text");
});


});