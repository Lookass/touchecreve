package be.ipl.tc.usecases;

import java.util.List;

import javax.ejb.Local;

import be.ipl.tc.domaine.TentativeCrevaison;
import be.ipl.tc.exceptions.ArgumentInvalideException;
import be.ipl.tc.exceptions.PartieException;
import be.ipl.tc.exceptions.TentativeCrevaisonException;

@Local
public interface GestionTentativesCrevaison {
	
	/**
	 * 
	 * @param partie La partie en cours
	 * @param joueur Le joueur qui effectue la requête de tentative de crevaison
	 * @param ligne L'indice [0, 9] de la ligne sélectionnée par l'utilisateur
	 * @param colonne L'indice [0,9] de la colonne sélectionnée par l'utilisateur
	 * @return La tentative de crevaison effectuée
	 * @throws ArgumentInvalideException
	 * @throws TentativeCrevaisonException
	 */
	TentativeCrevaison tenterCrevaison(int idPartie, int idJoueur, int ligne, int colonne) throws ArgumentInvalideException, TentativeCrevaisonException;
	
	/**
	 * @param idPartie L'id de la partie
	 * @return La liste des tentatives de crevaison de la partie correspondant à idPartie
	 */
	List<TentativeCrevaison> listerTentatives(int idPartie) throws PartieException;
	
	/**
	 * @param idPartie L'id de la partie
	 * @param idJoueur L'id du joueur
	 * @return La liste des tentatives de crevaison du joueur spécifié au sein de la partie correspondant à idPartie
	 */
	List<TentativeCrevaison> listerTentativesJoueur(int idPartie, int idJoueur) throws PartieException;

}
