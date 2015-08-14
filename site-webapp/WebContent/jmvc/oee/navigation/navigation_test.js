steal('funcunit').then(function(){

module("Oee.Navigation", { 
	setup: function(){
		S.open("//oee/navigation/navigation.html");
	}
});

test("Text Test", function(){
	equals(S("h1").text(), "Oee.Navigation Demo","demo text");
});


});