var etatFini = false;

$(document).ready(function() {
	 loadVoiture();
   setInterval(function() {
	   if (!etatFini) {
	   $.post("PingPartie", {id: idPartie, action: "getTour"},
			   function(data) {
		   		if (monNom == data) {
		   			$("#tour_info").html("C'est à votre tour de jouer");
		   			$("#boutonCrever").attr("disabled", "disabled");
		   			$("#boutonCrever").removeAttr("disabled");
		   		} else {
		   			$("#tour_info").html("Au tour de " + data);
		   			$("#boutonCrever").attr("disabled", "disabled");
		   		}
	   			}, "text");
	   }
   	          }, 3000);
   setInterval(function() {   
	  if (!etatFini) {
      $.post("PingPartie", {id: idPartie, action: "getTentative"},
		   function(data) {
    	  var mesVoitures = data.split(';');
		   		while (mesVoitures.length > 0) {
		   			var classe = mesVoitures.shift();
		   			var colonne = mesVoitures.shift();
		   			var ligne = mesVoitures.shift();
		   			var resultat = mesVoitures.shift();
		   			var nomClasse = "";
		   			
		   			if (resultat == "0") {
		   				nomClasse = "cellule_tentative_rate";
		   			} else if (resultat == "1") {
		   				nomClasse = "cellule_tentative_touche";
		   			} else if(resultat == "2") {
		   				nomClasse = "cellule_tentative_creve";
		   			}
		   		
				    $('#'+ classe + '' + ligne + '' + colonne).addClass(nomClasse);
		   		}
   			}, "text"); 	
	  }
	          }, 3000);
   setInterval(function() {
	   if (!etatFini) {
	   $.post("PingPartie", {id: idPartie, action: "getEtat"},
			   function(data) {
		   
		   if (data == "TERMINEE") {
			   alert("Partie terminée");
			   $("#boutonCrever").attr("disabled", "disabled");
			   etatFini = true;
		   }
	   }, "text");
	  }
   }, 5000);
   $.ajaxSetup({ cache: false });
});
 
 function loadVoiture() {
	   $.post("PingPartie", {id: idPartie, action: "getVoiture"},
			   function(data) {
			   		var mesVoitures = data.split(';');
			   		while (mesVoitures.length > 0) {
			   			var creve = mesVoitures.shift();
			   			var ligne = mesVoitures.shift()*1;
			   			var colonne = mesVoitures.shift()*1;
			   			var nbRoue = mesVoitures.shift()*1;
			   			var direction = mesVoitures.shift();
			   			var nomClasse;
			   			if (creve == "false") {
			   				nomClasse = "cellule_voiture_pneu_normal";
			   			} else {
			   				nomClasse = "cellule_voiture_pneu_creve";
			   			}
			   			if (direction == 0) { //Si horizontal
							for(var i = colonne;i < colonne+nbRoue;i++) {
								$('#G'+ligne+''+i).addClass(nomClasse);
							}
							
						} else  { //Si vertical
							for(var i = ligne;i < ligne+nbRoue;i++) {
								$('#G'+i+''+colonne).addClass(nomClasse);
							}
						}
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
						$("#boutonCrever").attr("disabled", "disabled");
				   }, "text");
		
 }