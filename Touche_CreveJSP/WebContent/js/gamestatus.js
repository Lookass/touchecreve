 $(document).ready(function() {
   setInterval(function() {
	   $.post("PingPartie", {id: idPartie, action: "getTour"},
			   function(data) {
		   		if (monNom == data) {
		   			$("#tour_info").html("C'est à votre tour de jouer");
		   			$("#boutonCrever").removeAttr("disabled");
		   		} else {
		   			$("#tour_info").html("Au tour de " + data);
		   			$("#boutonCrever").disabled = true;
		   		}
	   			}, "text") 	
   	          }, 1000);
   $.ajaxSetup({ cache: false });
});
 
 function crever() {
		var ligne = $('#selectLigne').val()*1;
		var colonne = $('#selectColonne').val()*1;
		$.post("gameaction.html", { idpartie: idPartie, l: ligne, c: colonne},
				   function(data) {
						alert(data);
						$("#boutonCrever").removeAttr("disabled");
				   }, "text");
		
 }