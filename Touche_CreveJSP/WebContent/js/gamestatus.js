 $(document).ready(function() {
   setInterval(function() {
	   $.post("PingPartie", {id: idPartie, action: "getTour"},
			   function(data) {
		   		if (monNom == data) {
		   			
		   		} else {
		   			
		   		}
	   			}, "text") 	
   	          }, 5000);
   $.ajaxSetup({ cache: false });
});