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
		if (ligne+nbRoue > 10) {
			alert("Débordement ligne")
		}
	} else  { //Si vertical
		if (colonne+nbRoue > 10) {
			alert("Débordement colonne")
		}
	}
	
}

//Une fois le preview placé, on envoit dans un POST -avec ajax- le placement de la voiture en cours.
function postPlacementVoiture() {
	if (nbRoue < 5) {
		$('#nomVoiture').html(getNextVoiture());
		$('#displayVoiturePreview').html("");
		for(var i = 0;i < nbRoue;i++) {
			$('#displayVoiturePreview').append("<td class=\"cellule_voiture_pneu_normal\"></td>");
		}
	}
}