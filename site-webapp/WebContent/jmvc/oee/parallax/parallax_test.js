steal('funcunit').then(function(){

module("Oee.Parallax", { 
	setup: function(){
		S.open("//oee/parallax/parallax.html");
	}
});

test("Text Test", function(){
	equals(S("h1").text(), "Oee.Parallax Demo","demo text");
});


});