steal('funcunit').then(function(){

module("Oee.Carousel", { 
	setup: function(){
		S.open("//oee/carousel/carousel.html");
	}
});

test("Text Test", function(){
	equals(S("h1").text(), "Oee.Carousel Demo","demo text");
});


});