 $(document).ready(function() {
   setInterval(function() {
	   $.post("PingPartie", {id: idPartie, action: "getEtat"},
			   function(data) {
		   
		   if (data == "EN_PLACEMENT") {
			   alert("Un adversaire a rejoint la partie!");
			   $('#redirectPlacement').append('<input type="hidden" name="gameid" value="'+ idPartie +'" />');
			   $('#redirectPlacement').append('<input type="hidden" name="owner" value="1" />');
			   $('#redirectPlacement').submit();
		   }
	   }, "text")
   }, 5000);
   $.ajaxSetup({ cache: false });
});