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

	  

    </head>

	<body>

	

		<h2>${partie.nom}</h2>

		

		<div class="joueurs_info_container">

			<span class="joueur_rouge">${partie.joueurRouge.nom}</span> vs. <span class="joueur_bleu">${partie.joueurBleu.nom}</span>

		</div>

		

		<div class="tour_info">

			À votre tour de jouer

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

							<td id="G32" class="cellule_voiture_pneu_normal"></td>

							<td id="G33" class="cellule_voiture_pneu_normal"></td>

							<td id="G34" class="cellule_voiture_pneu_touche"></td>

							<td id="G35" class="cellule_voiture_pneu_normal"></td>

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

							<td id="G67" class="cellule_voiture_pneu_creve"></td>

							<td id="G68" class="cellule_voiture_pneu_creve"></td>

							<td id="G69" class="cellule_voiture_pneu_creve"></td>

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

							<td id="G86" class="cellule_voiture_rate"></td>

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

							<td id="G96" class="cellule_voiture_rate"></td>

							<td id="G97" class="cellule_voiture_rate"></td>

							<td id="G98"></td>

							<td id="G99"></td>

						</tr>

					</tbody>

				</table>

			</div>

		

			<div id="tentative_info_container">

				<form>

					<div>Colonne : <select name="colonne" size="1">

									<option selected="selected" value="1">A</option>

									<option value="2">B</option>

									<option value="3">C</option>

									<option value="4">D</option>

									<option value="5">E</option>

									<option value="6">F</option>

									<option value="7">G</option>

									<option value="8">H</option>

									<option value="9">I</option>

									<option value="10">J</option>

								   </select>

					</div>

					<div>Ligne : <select name="ligne" size="1">

									<option selected="selected" value="1">1</option>

									<option value="2">2</option>

									<option value="3">3</option>

									<option value="4">4</option value="5">5</option>

									<option value="6">6</option>

									<option value="7">7</option>

									<option value="8">8</option>

									<option value="9">9</option>

									<option value="10">10</option>

								</select>

					</div>

					<div><input type="submit" value="Crever !"/></div>

				</form>

			</div>

		

			<div class="grille_tentatives_container">

				<table id="grille_tentatives">

					<thead>

						<th colspan="10">Tentatives</td>

					</thead>

					<tbody>

						<tr id="D0">

							<td id="D00"></td>

							<td id="D01"></td>

							<td id="D02"></td>

							<td id="D03"></td>

							<td id="D04"></td>

							<td id="D05"></td>

							<td id="D06"></td>

							<td id="D07"></td>

							<td id="D08"></td>

							<td id="D09"></td>

						</tr>

						<tr id="D1">

							<td id="D10"></td>

							<td id="D11"></td>

							<td id="D12"></td>

							<td id="D13"></td>

							<td id="D14"></td>

							<td id="D15"></td>

							<td id="D16"></td>

							<td id="D17"></td>

							<td id="D18"></td>

							<td id="D19"></td>

						</tr>

						<tr id="D2">

							<td id="D20"></td>

							<td id="D21"></td>

							<td id="D22"></td>

							<td id="D23"></td>

							<td id="D24"></td>

							<td id="D25"></td>

							<td id="D26"></td>

							<td id="D27"></td>

							<td id="D28"></td>

							<td id="D29"></td>

						</tr>

						<tr id="D3">

							<td id="D30"></td>

							<td id="D31"></td>

							<td id="D32"></td>

							<td id="D33"></td>

							<td id="D34"></td>

							<td id="D35"></td>

							<td id="D36"></td>

							<td id="D37"></td>

							<td id="D38"></td>

							<td id="D39"></td>

						</tr>

						<tr id="D4">

							<td id="D40"></td>

							<td id="D41"></td>

							<td id="D42" class="cellule_tentative_rate"></td>

							<td id="D43" class="cellule_tentative_rate"></td>

							<td id="D44"></td>

							<td id="D45"></td>

							<td id="D46"></td>

							<td id="D47"></td>

							<td id="D48"></td>

							<td id="D49"></td>

						</tr>

						<tr id="D5">

							<td id="D50"></td>

							<td id="D51"></td>

							<td id="D52"></td>

							<td id="D53"></td>

							<td id="D54"></td>

							<td id="D55"></td>

							<td id="D56"></td>

							<td id="D57"></td>

							<td id="D58"></td>

							<td id="D59"></td>

						</tr>

						<tr id="D6">

							<td id="D60"></td>

							<td id="D61"></td>

							<td id="D62"></td>

							<td id="D63"></td>

							<td id="D64" class="cellule_tentative_rate"></td>

							<td id="D65"></td>

							<td id="D66"></td>

							<td id="D67"></td>

							<td id="D68"></td>

							<td id="D69"></td>

						</tr>

						<tr id="D7">

							<td id="D70"></td>

							<td id="D71"></td>

							<td id="D72"></td>

							<td id="D73"></td>

							<td id="D74" class="cellule_tentative_touche"></td>

							<td id="D75"></td>

							<td id="D76"></td>

							<td id="D77"></td>

							<td id="D78"></td>

							<td id="D79"></td>

						</tr>

						<tr id="D8">

							<td id="D80"></td>

							<td id="D81"></td>

							<td id="D82"></td>

							<td id="D83"></td>

							<td id="D84" class="cellule_tentative_rate"></td>

							<td id="D85"></td>

							<td id="D86"></td>

							<td id="D87"></td>

							<td id="D88"></td>

							<td id="D89"></td>

						</tr>

						<tr id="D9">

							<td id="D90"></td>

							<td id="D91"></td>

							<td id="D92"></td>

							<td id="D93"></td>

							<td id="D94"></td>

							<td id="D95" class="cellule_tentative_rate"></td>

							<td id="D96" class="cellule_tentative_creve"></td>

							<td id="D97" class="cellule_tentative_creve"></td>

							<td id="D98" class="cellule_tentative_creve"></td>

							<td id="D99"></td>

						</tr>

					</tbody>

				</table>

			</div>

			

		</div>

		

	</body>

</html>