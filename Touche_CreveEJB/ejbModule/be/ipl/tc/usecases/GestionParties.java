package be.ipl.tc.usecases;

import java.util.List;

import javax.ejb.Local;

import be.ipl.tc.domaine.Joueur;
import be.ipl.tc.domaine.Partie;
import be.ipl.tc.exceptions.PartieException;

@Local
public interface GestionParties {
	
	List<Partie> listerPartiesEnAttente();
	List<Partie> listerPartiesTerminees();
	List<Partie> listerParties();
	
	Joueur getTour(int idPartie);
	
	Partie creerPartie(String nomJoueur,String nom) throws PartieException;
	Partie rejoindrePartie(int idPartie, String nomJoueur) throws PartieException;

}
