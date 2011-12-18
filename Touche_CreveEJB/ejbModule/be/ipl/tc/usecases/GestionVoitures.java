package be.ipl.tc.usecases;

import java.util.List;

import javax.ejb.Local;

import be.ipl.tc.domaine.Partie;
import be.ipl.tc.domaine.Voiture;
import be.ipl.tc.exceptions.ArgumentInvalideException;
import be.ipl.tc.exceptions.VoitureException;

@Local
public interface GestionVoitures {
	
	List<Voiture> listerVoituresAPlacer();
	String getVoitures(int idPartie, String nomJoueur);
	
	/**
	 * @param partie La partie en cours pour l'utilisateur
	 * @param joueur Le joueur effectuant la requête de placement d'une voiture
	 * @param voiture La voiture que l'utilisateur souhaite placer
	 * @param ligne
	 * @param colonne
	 * @return
	 * @throws ArgumentInvalideException
	 * @throws VoitureException
	 */
	Partie placerVoiture(int partieId, int idJoueur, String nomVoiture, int ligne, int colonne, int direction) throws ArgumentInvalideException, VoitureException;

}
