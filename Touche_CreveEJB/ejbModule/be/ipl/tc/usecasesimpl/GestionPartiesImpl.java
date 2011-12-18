package be.ipl.tc.usecasesimpl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import be.ipl.tc.dao.JoueurDao;
import be.ipl.tc.dao.PartieDao;
import be.ipl.tc.domaine.Joueur;
import be.ipl.tc.domaine.Partie;
import be.ipl.tc.exceptions.PartieException;
import be.ipl.tc.usecases.GestionParties;
import be.ipl.tc.usecases.GestionPartiesRemote;

@Singleton
@Startup
@Remote(GestionPartiesRemote.class)
public class GestionPartiesImpl implements GestionParties {

	// Seules les parties terminées sont persistées
	@EJB PartieDao partieDao;
	@EJB JoueurDao joueurDao;

	// Les parties en attente d'adversaire sont gardées en mémoire
	private Map<Integer, Partie> partiesEnAttente = new HashMap<Integer, Partie>();

	@Override
	public List<Partie> listerPartiesEnAttente() {
		return new LinkedList<Partie>(partiesEnAttente.values());
	}

	@Override
	public List<Partie> listerPartiesTerminees() {

		List<Partie> parties = partieDao.lister();
		List<Partie> partiesTerminees = new LinkedList<Partie>();
		for (Partie p : parties) {
			if (p.getEtat() == Partie.Etat.TERMINEE)
				partiesTerminees.add(p);
		}

		return partiesTerminees;

	}

	@Override
	public Partie creerPartie(String nomJoueur, String nomPartie)
			throws PartieException {
		Partie partieRecherche=partieDao.rechercherPartieNonTerminée(nomPartie);
		if(partieRecherche!=null)throw new PartieException("Le nom est déja utilisé");
		Joueur joueurRouge = new Joueur(nomJoueur);
		Partie partie = new Partie(joueurRouge, nomPartie);
		// Persist de la partie pour générer son ID
		partie = partieDao.enregistrer(partie);
		
		partiesEnAttente.put(partie.getId(), partie);

		return partie;

	}

	@Override
	public Partie rejoindrePartie(int idPartie, String nomJoueur)
			throws PartieException {

		// Recherche de la partie
		Partie partie = partiesEnAttente.get(idPartie);

		// Vérifications
		if (partie == null)
			throw new PartieException(
					"La partie que vous tentez de joindre n'éxiste pas.");
		if (partie.getJoueurBleu() != null)
			throw new PartieException(
					"La partie que vous tentez de joindre est pleine.");

		// Le joueur rejoint la partie
		Joueur joueurBleu = new Joueur(nomJoueur);
		partie.ajouterJoueurBleu(joueurBleu);
		
		joueurDao.enregistrer(joueurBleu);

		// La partie n'est plus en attente
		partiesEnAttente.remove(idPartie);
		
		partieDao.mettreAJour(partie);

		return partie;

	}

	@Override
	public List<Partie> listerParties() {
		List<Partie> parties = partieDao.lister();
		return parties;
	}

}
