 $(document).ready(function() {
   setInterval(function() {
	   $.post("PingPartie", {id: idPartie, action: "getTour"},
			   function(data) {
		   		if (monNom == data) {
		   			$("#tour_info").html("C'est � votre tour de jouer");
		   			$("#boutonCrever").disabled = true;
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
						if (data == "0") {
							alert("Rat�!");
						} else if (data == "1") {
							alert("Touch�!");
						} else if (data == "2") {
							alert("Crev�!!!");
						} else {
							alert("Erreur : " + data);
						}
						$("#boutonCrever").removeAttr("disabled");
				   }, "text");
		
 }