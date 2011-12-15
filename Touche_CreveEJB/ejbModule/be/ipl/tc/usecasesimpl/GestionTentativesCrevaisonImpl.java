package be.ipl.tc.usecasesimpl;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import be.ipl.tc.dao.PartieDao;
import be.ipl.tc.dao.TentativeCrevaisonDao;
import be.ipl.tc.domaine.Joueur;
import be.ipl.tc.domaine.Partie;
import be.ipl.tc.domaine.TentativeCrevaison;
import be.ipl.tc.exceptions.ArgumentInvalideException;
import be.ipl.tc.exceptions.PartieException;
import be.ipl.tc.exceptions.TentativeCrevaisonException;
import be.ipl.tc.usecases.GestionTentativesCrevaison;

@Stateless
public class GestionTentativesCrevaisonImpl implements
		GestionTentativesCrevaison {
	
	@EJB PartieDao partieDao;
	@EJB TentativeCrevaisonDao tentativeCrevaisonDao;

	@Override
	public TentativeCrevaison tenterCrevaison(Partie partie,
			Joueur joueur, int ligne, int colonne)
			throws ArgumentInvalideException, PartieException {
		
		TentativeCrevaison tentative = partie.tenterCrevaison(joueur, ligne, colonne);
		
		tentativeCrevaisonDao.enregistrer(tentative);
		
		return tentative;
	}

	@Override
	public List<TentativeCrevaison> listerTentatives(int idPartie) throws PartieException {
		
		Partie p = partieDao.rechercher(idPartie);
		if(p == null)
			throw new PartieException("La partie n'éxiste pas.");
		
		List<TentativeCrevaison> tentativesJoueurRouge = p.getJoueurRouge().getTentativesCrevaison();
		List<TentativeCrevaison> tentativesJoueurBleu = p.getJoueurBleu().getTentativesCrevaison();
		
		List<TentativeCrevaison> listeTentatives = new LinkedList<TentativeCrevaison>();
		for(int i = 0; i < tentativesJoueurRouge.size(); ++i) {
			listeTentatives.add(tentativesJoueurRouge.get(i));
			// Ne pas ajouter null si le joueur rouge a effectué la dernière tentative
			if(i != tentativesJoueurRouge.size() || tentativesJoueurBleu.get(i) != null)
				listeTentatives.add(tentativesJoueurBleu.get(i));
		}
		
		return listeTentatives;
	}

}
