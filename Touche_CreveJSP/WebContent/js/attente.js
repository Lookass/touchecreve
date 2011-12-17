<script>
 $(document).ready(function() {
   var refreshId = setInterval(function() {
	   $.post("", { idpartie: idPartie, idjoueur: idJoueur, voiture: $('#nomVoiture').html(), l: ligne, c: colonne, d: direction},
			   function(data) {
		   
	   })
   }, 5000);
   $.ajaxSetup({ cache: false });
});