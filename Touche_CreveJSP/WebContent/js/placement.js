var voituresAPlacer = new Array(2, "Citadine", 3, "Coupe", 3, "Berline", 4, "Break", 5, "Limousine");
var nbRoue = 1;
var idPartie = 0;
var idJoueur = 0;


function setIdPartie(id) {
	idPartie = id;
}

function setIdJoueur(id) {
	idJoueur = id;
}
	
function getNextVoiture() {
	nbRoue = voituresAPlacer.shift();
	$('#nomVoiture').html(voituresAPlacer.shift());
	$('#displayVoiturePreview').html("");
	for(var i = 0;i < nbRoue;i++) {
		$('#displayVoiturePreview').append("<td class=\"cellule_voiture_pneu_normal\"></td>");
	}
}

//Fonction permet de verifier si les coordonn�es sont OK et affiche un preview dans le tableau.
function previewPlacement() {
	var direction = $('#selectDirection').val();
	var ligne = $('#selectLigne').val()*1;
	var colonne = $('#selectColonne').val()*1;
	
	if (direction == 0) { //Si horizontal
		if (colonne+nbRoue > 10) {
			alert("D�bordement colonne")
			return 0;
		}

	} else  { //Si vertical
		if (ligne+nbRoue > 10) {
			alert("D�bordement ligne")
			return 0;
		}
	}
	
	//On supprime les anciens previews
	for(var i = 0;i<10;i++) {
		for(var j = 0;j<10;j++) {
			if ($('#G'+i+''+j).hasClass("cellule_voiture_placement")) {
				$('#G'+i+''+j).removeClass("cellule_voiture_placement");
			}
		}
	}

	
	//On verifie si il n'y a pas d�j� de voitures sur ces positions

	if (direction == 0) { //Si horizontal
		for(var i = colonne;i < colonne+nbRoue;i++) {
			if ($('#G'+ligne+''+i).hasClass("cellule_voiture_pneu_normal")) {
				alert('Collision avec une autre voiture');
				return 0;
			}
		}
		for(var i = colonne;i < colonne+nbRoue;i++) {
			$('#G'+ligne+''+i).addClass("cellule_voiture_placement");
		}
		
	} else  { //Si vertical
		for(var i = ligne;i < ligne+nbRoue;i++) {
			if ($('#G'+i+''+colonne).hasClass("cellule_voiture_pneu_normal")) {
				alert('Collision avec une autre voiture');
				return 0;
			}
		}
		for(var i = ligne;i < ligne+nbRoue;i++) {
			$('#G'+i+''+colonne).addClass("cellule_voiture_placement");
		}
	}
	return 1;
	
}

//Une fois le preview plac�, on envoit dans un POST -avec ajax- le placement de la voiture en cours.
function postPlacementVoiture() {
	if (nbRoue <= 5) { 
		if (previewPlacement() == 1) {
			var direction = $('#selectDirection').val();
			var ligne = $('#selectLigne').val()*1;
			var colonne = $('#selectColonne').val()*1;
			$.post("placer", { idpartie: idPartie, idjoueur: idJoueur, voiture: $('#nomVoiture').html(), l: ligne, c: colonne, d: direction},
					   function(data) {
						 alert("Data Loaded: " + data);
	
						 if (data == "1") { //La requ�te � r�ussie
							if (direction == 0) { //Si horizontal
								for(var i = colonne;i < colonne+nbRoue;i++) {
									$('#G'+ligne+''+i).removeClass("cellule_voiture_placement");
									$('#G'+ligne+''+i).addClass("cellule_voiture_pneu_normal");
								}
								
							} else  { //Si vertical
								for(var i = ligne;i < ligne+nbRoue;i++) {
									
									$('#G'+i+''+colonne).removeClass("cellule_voiture_placement");
									$('#G'+i+''+colonne).addClass("cellule_voiture_pneu_normal");
								}
							}
							getNextVoiture();
	
						 } else { //On affiche l'exception
							 alert("Data Loaded: " + data);
						 }
					   }, "text");
			
			
		}
	}
}