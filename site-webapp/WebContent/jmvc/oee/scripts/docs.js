//js oee/scripts/doc.js

load('steal/rhino/rhino.js');
steal("documentjs").then(function(){
	DocumentJS('oee/oee.html', {
		markdown : ['oee']
	});
});