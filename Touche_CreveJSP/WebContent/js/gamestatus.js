 $(document).ready(function() {
   setInterval(function() {
	   $.post("PingPartie", {id: idPartie, action: "getTour"},
			   function(data) {
		   		if (monNom == data) {
		   			$("#tour_info").html("C'est à votre tour de jouer");
		   			$("#boutonCrever").removeAttr("disabled");
		   		} else {
		   			$("#tour_info").html("Au tour de " + data);
		   			$("#boutonCrever").removeAttr("disabled");
		   		}
	   			}, "text") 	
   	          }, 5000);
   $.ajaxSetup({ cache: false });
});