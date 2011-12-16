package be.ipl.tc.usecasesimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		voituresAPlacer.put("Coupé", new Voiture("Coupé", 3));
		voituresAPlacer.put("Berline", new Voiture("Berline", 3));
		voituresAPlacer.put("Break", new Voiture("Break", 4));
		voituresAPlacer.put("Limousine", new Voiture("Limousine", 5));
	}

	@Override
	public List<Voiture> listerVoituresAPlacer() {
		
		List<Voiture> listeVoitures = new ArrayList<Voiture>(voituresAPlacer.size());
		for(int i = 0; i < voituresAPlacer.size(); ++i) {
			listeVoitures.add((Voiture) voituresAPlacer.get(i).clone());
		}
		return listeVoitures;
		
	}

	@Override
	public void placerVoiture(int idPartie, int idJoueur,
			String nomVoiture, int ligne, int colonne, int direction)
			throws ArgumentInvalideException, VoitureException {
			Partie partie = partieDao.rechercher(idPartie);
			Joueur joueur = joueurDao.rechercher(idJoueur);
			//Vérifie que le nom passé en paramètre n'est pas null
			Util.checkObject(nomVoiture);
			// Le joueur doit être dans la partie spécifiée
			if(!partie.contientJoueur(joueur))
				throw new PartieException();
			//La voiture n'est pas présente dans la liste des voitures pouvant être ajoutées
//			if(!voituresAPlacer.containsKey(nomVoiture))
//				throw new ArgumentInvalideException("La voiture que vous avez choisi n'est pas un choix possible");
//			// Le nom en paramètre doit correspondre au nom d'une des voitures à placer
			Voiture newVoiture = voituresAPlacer.get(nomVoiture);
			if(newVoiture == null)
				throw new VoitureException();
			newVoiture = (Voiture) newVoiture.clone();
			
			
			// Le joueur ne peut placer deux fois la même voiture
			for(Voiture v : joueur.getVoitures())
				if(v.equals(newVoiture))
					throw new VoitureException();
			
		
			newVoiture.setDirection(direction);
			newVoiture.setLigne(ligne);
			newVoiture.setColonne(colonne);
			partie.placerVoiture(joueur, newVoiture);
			
			
			voitureDao.enregistrer(newVoiture);
			
	}

}
