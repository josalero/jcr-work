steal("funcunit", function(){
	module("oee test", { 
		setup: function(){
			S.open("//oee/oee.html");
		}
	});
	
	test("Copy Test", function(){
		equals(S("h1").text(), "Welcome to JavaScriptMVC 3.2!","welcome text");
	});
})