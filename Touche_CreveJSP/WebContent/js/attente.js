 $(document).ready(function() {
   setInterval(function() {
	   $.post("PingPartie", {id: idPartie},
			   function(data) {
		   alert(data);
	   })
   }, 5000);
   $.ajaxSetup({ cache: false });
});