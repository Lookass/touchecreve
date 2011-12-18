package be.ipl.tc.usecasesimpl;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import be.ipl.tc.dao.JoueurDao;
import be.ipl.tc.dao.PartieDao;
import be.ipl.tc.dao.TentativeCrevaisonDao;
import be.ipl.tc.domaine.Joueur;
import be.ipl.tc.domaine.Partie;
import be.ipl.tc.domaine.TentativeCrevaison;
import be.ipl.tc.exceptions.ArgumentInvalideException;
import be.ipl.tc.exceptions.PartieException;
import be.ipl.tc.usecases.GestionTentativesCrevaison;
import be.ipl.tc.usecases.GestionTentativesCrevaisonRemote;

@Stateless
@Remote(GestionTentativesCrevaisonRemote.class)
public class GestionTentativesCrevaisonImpl implements
		GestionTentativesCrevaison {
	
	@EJB PartieDao partieDao;
	@EJB JoueurDao joueurDao;
	@EJB TentativeCrevaisonDao tentativeCrevaisonDao;

	@Override
	public TentativeCrevaison tenterCrevaison(int idPartie,
			int idJoueur, int ligne, int colonne)
			throws ArgumentInvalideException, PartieException {
		
		Partie partie = partieDao.recharger(idPartie);
		Joueur joueur = joueurDao.recharger(idJoueur);
		
		if(!partie.contientJoueur(joueur))
			throw new PartieException("Le joueur n'appartient pas à cette partie.");
		
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
			if(i != tentativesJoueurRouge.size()-1 || tentativesJoueurBleu.size() == tentativesJoueurRouge.size())
				listeTentatives.add(tentativesJoueurBleu.get(i));
		}
		
		return listeTentatives;
	}

}
