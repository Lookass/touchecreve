 $(document).ready(function() {
	 loadVoiture();
   setInterval(function() {
	   $.post("PingPartie", {id: idPartie, action: "getTour"},
			   function(data) {
		   		if (monNom == data) {
		   			$("#tour_info").html("C'est à votre tour de jouer");
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
 
 function loadVoiture() {
	   $.post("PingPartie", {id: idPartie, action: "getVoiture"},
			   function(data) {
		   			alert(data);
			   		var mesVoitures = data.split(";");
			   		while (mesVoitures.size > 0) {
			   			alert(mesVoitures.shift() + " " + mesVoitures.shift() + " " + mesVoitures.shift() + " " + mesVoitures.shift() + " " + mesVoitures.shift());
			   		}
	   			}, "text"); 	
 }
 function crever() {
		var ligne = $('#selectLigne').val()*1;
		var colonne = $('#selectColonne').val()*1;
		$.post("gameaction.html", { idpartie: idPartie, l: ligne, c: colonne},
				   function(data) {
						if (data == "0") {
							alert("Raté!");
						} else if (data == "1") {
							alert("Touché!");
						} else if (data == "2") {
							alert("Crevé!!!");
						} else {
							alert("Erreur : " + data);
						}
						$("#boutonCrever").removeAttr("disabled");
				   }, "text");
		
 }