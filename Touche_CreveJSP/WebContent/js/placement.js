var voituresAPlacer = new Array("Citadine", "Coup�", "Berline", "Break", "Limousine");
var nbRoue = 1;

function getNextVoiture() {
	nbRoue++;
	return voituresAPlacer.shift();
}

//Fonction permet de verifier si les coordonn�es sont OK et affiche un preview dans le tableau.
function previewPlacement() {
	var direction = $('#selectDirection').val();
	var ligne = parseInt($('#selectLigne').val());
	var colonne = parseInt($('#selectColonne').val());
	
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
	$('#nomVoiture').html(getNextVoiture());
}