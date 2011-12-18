package be.ipl.tc.usecasesimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Singleton;

import be.ipl.tc.dao.JoueurDao;
import be.ipl.tc.dao.PartieDao;
import be.ipl.tc.dao.VoitureDao;
import be.ipl.tc.domaine.Joueur;
import be.ipl.tc.domaine.Partie;
import be.ipl.tc.domaine.Voiture;
import be.ipl.tc.exceptions.ArgumentInvalideException;
import be.ipl.tc.exceptions.PartieException;
import be.ipl.tc.exceptions.VoitureException;
import be.ipl.tc.usecases.GestionVoitures;
import be.ipl.tc.usecases.GestionVoituresRemote;
import be.ipl.tc.util.Util;

@Singleton
@Remote(GestionVoituresRemote.class)
public class GestionVoituresImpl implements GestionVoitures {
	
	@EJB VoitureDao voitureDao;
	@EJB PartieDao partieDao;
	@EJB JoueurDao joueurDao;
	
	/**
	 * Map des voitures à placer par chaque joueur
	 */
	private Map<String, Voiture> voituresAPlacer = new HashMap<String, Voiture>(5);
	
	{
		voituresAPlacer.put("Citadine", new Voiture("Citadine", 2));
		voituresAPlacer.put("Coupe", new Voiture("Coupe", 3));
		voituresAPlacer.put("Berline", new Voiture("Berline", 3));
		voituresAPlacer.put("Break", new Voiture("Break", 4));
		voituresAPlacer.put("Limousine", new Voiture("Limousine", 5));
	}

	@Override
	public List<Voiture> listerVoituresAPlacer() {
		List<Voiture> listeVoitures = new ArrayList<Voiture>(voituresAPlacer.size());
		Set<String> nomsVoituresAPlacer = voituresAPlacer.keySet();
		for(String nomVoiture : nomsVoituresAPlacer) {
			Voiture v = voituresAPlacer.get(nomVoiture);
			listeVoitures.add((Voiture) v.clone());
		}
		// On présente les voitures de façon triée selon leur grandeur (nbPneus)
		Collections.sort(listeVoitures, new Comparator<Voiture>() {

			@Override
			public int compare(Voiture arg0, Voiture arg1) {
				
				return arg0.getNbrPneus() - arg1.getNbrPneus();
			}
			
		});
		return listeVoitures;
		
	}

	@Override
	public Partie placerVoiture(int idPartie, int idJoueur,
			String nomVoiture, int ligne, int colonne, int direction)
			throws ArgumentInvalideException, VoitureException {
		
			Partie partie;
			Joueur joueur;
			
			try{
				partie = partieDao.recharger(idPartie);
			}catch (Exception e) {
				throw new PartieException("Partie inexistante");
			}
			try{
				joueur = joueurDao.recharger(idJoueur);
			}catch (Exception e) {
				throw new PartieException("Joueur inexistant");
			}
				
			//Vérifie que le nom passé en paramètre n'est pas null
			Util.checkObject(nomVoiture);
			// Le joueur doit être dans la partie spécifiée
			if(!partie.contientJoueur(joueur))
				throw new PartieException("La partie ne contient pas le joueur.");

			// Le nom en paramètre doit correspondre au nom d'une des voitures à placer

			Voiture newVoiture = voituresAPlacer.get(nomVoiture);
			if(newVoiture == null)
				throw new VoitureException();
			newVoiture = (Voiture) newVoiture.clone();
			
			// Le joueur ne peut placer deux fois la même voiture
			for(Voiture v : joueur.getVoitures())
				if(v.getNom().equals(nomVoiture))
					throw new VoitureException();

			newVoiture.setDirection(direction);
			newVoiture.setLigne(ligne);
			newVoiture.setColonne(colonne);
			partie.placerVoiture(joueur, newVoiture);
			
			voitureDao.enregistrer(newVoiture);
			
			return partie;
	}

	@Override
	public List<Voiture> getVoitures(int idJoueur) {
		
		Joueur j = joueurDao.rechercher(idJoueur);
		return j.getVoitures();
		
	}

}
