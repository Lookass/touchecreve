package be.ipl.tc.usecasesimpl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import be.ipl.tc.dao.VoitureDao;
import be.ipl.tc.domaine.Joueur;
import be.ipl.tc.domaine.Partie;
import be.ipl.tc.domaine.Voiture;
import be.ipl.tc.exceptions.ArgumentInvalideException;
import be.ipl.tc.exceptions.VoitureException;
import be.ipl.tc.usecases.GestionVoitures;

@Stateless
public class GestionVoituresImpl implements GestionVoitures {
	
	@EJB VoitureDao voitureDao;
	/**
	 * Liste des voitures à placer par le joueur
	 */
	private List<Voiture> voituresAPlacer = new ArrayList<Voiture>(5);
	
	{
		voituresAPlacer.add(new Voiture("Citadine", 2));
		voituresAPlacer.add(new Voiture("Coupé", 3));
		voituresAPlacer.add(new Voiture("Berline", 3));
		voituresAPlacer.add(new Voiture("Break", 4));
		voituresAPlacer.add(new Voiture("Limousine", 5));
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
	public void placerVoiture(Partie partie, Joueur joueur,
			String nomVoiture, int ligne, int colonne, int nbPneus, int direction)
			throws ArgumentInvalideException, VoitureException {
			
			Voiture v = new Voiture(nomVoiture, nbPneus);
			v.setDirection(direction);
			v.setLigne(ligne);
			v.setColonne(colonne);
			partie.placerVoiture(joueur, v);
			voitureDao.enregistrer(v);
	}

}
