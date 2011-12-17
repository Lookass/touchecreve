 $(document).ready(function() {
	 alert('lol');
	 alert(idPartie);
   setInterval(function() {
	   $.post("PingPartie", {id: idPartie},
			   function(data) {
		   alert(data);
	   })
   }, 5000);
   $.ajaxSetup({ cache: false });
});