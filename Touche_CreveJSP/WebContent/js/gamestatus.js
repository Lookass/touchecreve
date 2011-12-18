 $(document).ready(function() {
   setInterval(function() {
	   $.post("PingPartie", {id: idPartie, action: "getTour"},
			   function(data) {
		   alert(data);
	   			}, "text")
   	          }, 5000);
   $.ajaxSetup({ cache: false });
});