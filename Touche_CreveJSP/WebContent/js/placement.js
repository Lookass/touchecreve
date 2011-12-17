var voituresAPlacer = new Array("Citadine", "Coup�", "Berline", "Break", "Limousine");
var nbRoue = 1;

function getNextVoiture() {
	nbRoue++;
	return voituresAPlacer.shift();
}

//Fonction permet de verifier si les coordonn�es sont OK et affiche un preview dans le tableau.
function previewPlacement() {
	var direction = $('#selectDirection').val();
	var ligne = $('#selectLigne').val()*1;
	var colonne = $('#selectColonne').val()*1;
	
	if (direction == 0) { //Si horizontal
		if (ligne+nbRoue > 10) {
			alert("D�bordement ligne")
		}
	} else  { //Si vertical
		if (colonne+nbRoue > 10) {
			alert("D�bordement colonne")
		}
	}
	
}

//Une fois le preview plac�, on envoit dans un POST -avec ajax- le placement de la voiture en cours.
function postPlacementVoiture() {
	if (nbRoue < 5) {
		$('#nomVoiture').html(getNextVoiture());
		$('#displayVoiturePreview').append("<td class=\"cellule_voiture_pneu_normal\"></td>");
	}
}