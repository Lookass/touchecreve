var voituresAPlacer = new Array(2, "Citadine", 3, "Coupé", 3, "Berline", 4, "Break", 5, "Limousine");
var nbRoue = 1;

function getNextVoiture() {
	nbRoue = voituresAPlacer.shift();
	return voituresAPlacer.shift();
}

//Fonction permet de verifier si les coordonnées sont OK et affiche un preview dans le tableau.
function previewPlacement() {
	var direction = $('#selectDirection').val();
	var ligne = $('#selectLigne').val()*1;
	var colonne = $('#selectColonne').val()*1;
	
	if (direction == 0) { //Si horizontal
		if (colonne+nbRoue > 10) {
			alert("Débordement colonne")
			return 0;
		}

	} else  { //Si vertical
		if (ligne+nbRoue > 10) {
			alert("Débordement ligne")
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

	
	//On verifie si il n'y a pas déjà de voitures sur ces positions

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

//Une fois le preview placé, on envoit dans un POST -avec ajax- le placement de la voiture en cours.
function postPlacementVoiture() {
	if (previewPlacement() == 1) {
		var direction = $('#selectDirection').val();
		var ligne = $('#selectLigne').val()*1;
		var colonne = $('#selectColonne').val()*1;
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
		if (nbRoue < 5) {
			$('#nomVoiture').html(getNextVoiture());
			$('#displayVoiturePreview').html("");
			for(var i = 0;i < nbRoue;i++) {
				$('#displayVoiturePreview').append("<td class=\"cellule_voiture_pneu_normal\"></td>");
			}
		}
	}
	
}