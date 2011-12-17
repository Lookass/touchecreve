<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
   
       <title>Touché Crevé - Partie : ${partie.nom}</title>
	   <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	   <meta name="description" content="Plateau de jeu">
	   <link rel="stylesheet" media="screen" type="text/css" title="Design" href="touche_creve.css">
	   <link type="text/css" href="css/ui-darkness/jquery-ui-1.8.16.custom.css" rel="stylesheet" />	
	   <script type="text/javascript" src="js/jquery-1.6.2.min.js"></script>
	   <script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
	   <script type="text/javascript" src="js/placement.js"></script>
	   <script type="text/javascript">
	   $(document).ready(function() {
		   setIdPartie(<c:out value="${partie.id}" />);
		   setIdJoueur(<c:out value="${partie.joueurRouge.idJoueur}" />);
		   getNextVoiture();
		});
	   </script>
    </head>
	<body>
	
		<h2>${partie.nom}</h2>
		
		<div class="joueurs_info_container">
			<span class="joueur_rouge">${partie.joueurRouge.nom}</span> vs. <span class="joueur_bleu">${partie.joueurBleu.nom}</span>
		</div>
		
		<div class="tour_info">
			Placement des voitures
		</div>
		
		<div class="grilles_container">
		
			<div class="grille_voitures_container">
				<table id="grille_voitures">
					<thead>
						<tr>
							<th colspan="10">Vos voitures</th>
						</tr>
					</thead>
					<tbody>
						<tr id="G0">
							<td id="G00"></td>
							<td id="G01"></td>
							<td id="G02"></td>
							<td id="G03"></td>
							<td id="G04"></td>
							<td id="G05"></td>
							<td id="G06"></td>
							<td id="G07"></td>
							<td id="G08"></td>
							<td id="G09"></td>
						</tr>
						<tr id="G1">
							<td id="G10"></td>
							<td id="G11"></td>
							<td id="G12"></td>
							<td id="G13"></td>
							<td id="G14"></td>
							<td id="G15"></td>
							<td id="G16"></td>
							<td id="G17"></td>
							<td id="G18"></td>
							<td id="G19"></td>
						</tr>
						<tr id="G2">
							<td id="G20"></td>
							<td id="G21"></td>
							<td id="G22"></td>
							<td id="G23"></td>
							<td id="G24"></td>
							<td id="G25"></td>
							<td id="G26"></td>
							<td id="G27"></td>
							<td id="G28"></td>
							<td id="G29"></td>
						</tr>
						<tr id="G3">
							<td id="G30"></td>
							<td id="G31"></td>
							<td id="G32"></td>
							<td id="G33"></td>
							<td id="G34"></td>
							<td id="G35"></td>
							<td id="G36"></td>
							<td id="G37"></td>
							<td id="G38"></td>
							<td id="G39"></td>
						</tr>
						<tr id="G4">
							<td id="G40"></td>
							<td id="G41"></td>
							<td id="G42"></td>
							<td id="G43"></td>
							<td id="G44"></td>
							<td id="G45"></td>
							<td id="G46"></td>
							<td id="G47"></td>
							<td id="G48"></td>
							<td id="G49"></td>
						</tr>
						<tr id="G5">
							<td id="G50"></td>
							<td id="G51"></td>
							<td id="G52"></td>
							<td id="G53"></td>
							<td id="G54"></td>
							<td id="G55"></td>
							<td id="G56"></td>
							<td id="G57"></td>
							<td id="G58"></td>
							<td id="G59"></td>
						</tr>
						<tr id="G6">
							<td id="G60"></td>
							<td id="G61"></td>
							<td id="G62"></td>
							<td id="G63"></td>
							<td id="G64"></td>
							<td id="G65"></td>
							<td id="G66"></td>
							<td id="G67"></td>
							<td id="G68"></td>
							<td id="G69"></td>
						</tr>
						<tr id="G7">
							<td id="G70"></td>
							<td id="G71"></td>
							<td id="G72"></td>
							<td id="G73"></td>
							<td id="G74"></td>
							<td id="G75"></td>
							<td id="G76"></td>
							<td id="G77"></td>
							<td id="G78"></td>
							<td id="G79"></td>
						</tr>
						<tr id="G8">
							<td id="G80"></td>
							<td id="G81"></td>
							<td id="G82"></td>
							<td id="G83"></td>
							<td id="G84"></td>
							<td id="G85"></td>
							<td id="G86"></td>
							<td id="G87"></td>
							<td id="G88"></td>
							<td id="G89"></td>
						</tr>
						<tr id="G9">
							<td id="G90"></td>
							<td id="G91"></td>
							<td id="G92"></td>
							<td id="G93"></td>
							<td id="G94"></td>
							<td id="G95"></td>
							<td id="G96"></td>
							<td id="G97"></td>
							<td id="G98"></td>
							<td id="G99"></td>
						</tr>
					</tbody>
				</table>
			</div>
		
		</div>
		
		<div class="placement_voiture_container_1st_level">
		
			<div class="placement_voiture_caracteristiques_left">
		
				Voiture à placer : <div id="nomVoiture"></div><br/>
				
				<div class="voiture_selection_display">
					<table style="width:75%">
						<tr id="displayVoiturePreview">
						</tr>
					</table>
				</div>
				
			</div>
			
			<div class="placement_voiture_caracteristiques_right">
			
				<div class="selection_caracteristique_voiture">
					Direction : <select id="selectDirection" name="select_placement_voiture_direction">
									<option selected="selected" value="0">Horizontale</option>
									<option value="1">Verticale</option>
								</select>
				</div>
				
				<div class="selection_caracteristique_voiture">
					Ligne : <select id="selectLigne" name="select_placement_voiture_ligne">
								<option selected="selected" value="0">A</option>
								<option value="1">B</option>
								<option value="2">C</option>
								<option value="3">D</option>
								<option value="4">E</option>
								<option value="5">F</option>
								<option value="6">G</option>
								<option value="7">H</option>
								<option value="8">I</option>
								<option value="9">J</option>
							</select>
				</div>
				
				<div class="selection_caracteristique_voiture">
					Colonne :  <select id="selectColonne" name="select_placement_voiture_colonne">
									<option selected="selected" value="0">0</option>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
									<option value="6">6</option>
									<option value="7">7</option>
									<option value="8">8</option>
									<option value="9">9</option>
								</select>
				</div>
				<div>
					<button onClick="previewPlacement();">Aperçu</button>
					<button id="boutonPostePlacement" onClick="postPlacementVoiture();">Placer la voiture</button>
				</div>
			
			</div>
			
		</div>
		
	</body>
</html>