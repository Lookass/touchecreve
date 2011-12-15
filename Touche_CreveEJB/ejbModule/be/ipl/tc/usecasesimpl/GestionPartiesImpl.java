package be.ipl.tc.usecasesimpl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;

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
	
	// Seules les parties termin�es sont persist�es
	@EJB PartieDao partieDao; 
	
	// Les parties en attente d'adversaire sont gard�es en m�moire
	private Map<Integer, Partie> partiesEnAttente = new HashMap<Integer, Partie>();

	@Override
	public List<Partie> listerPartiesEnAttente() {
		System.out.println("Taille de la liste " + partiesEnAttente.values().size());
		return new LinkedList<Partie>(partiesEnAttente.values());
	}

	@Override
	public List<Partie> listerPartiesTerminees() {
		
		List<Partie> parties = partieDao.lister();
		List<Partie> partiesTerminees = new LinkedList<Partie>();
		for(Partie p : parties) {
			if(p.getEtat() == Partie.Etat.TERMINEE)
				partiesTerminees.add(p);
		}
		
		return partiesTerminees;
		
	}

	@Override
	public Partie creerPartie(String nomJoueur, String nomPartie) throws PartieException {
		
		Joueur joueurRouge = new Joueur(nomJoueur);
		Partie partie = new Partie(joueurRouge, nomPartie);
		// Persist de la partie pour g�n�rer son ID
		try {
			partie = partieDao.enregistrer(partie);
		} catch (Throwable t) {
			System.out.println("Tets clef");
			throw new PartieException();
		}
		
		System.out.println("ID partie = " + partie.getId());
		System.out.println("avant Taille 2 : " + partiesEnAttente.keySet().size()+" id partie : "+partie.getId());
		partiesEnAttente.put(partie.getId(), partie);
		
		System.out.println("apres Taille 2 : " + partiesEnAttente.keySet().size()+" id partie : "+partie.getId());
		
		return partie;
		
	}

	@Override
	public Partie rejoindrePartie(int idPartie, String nomJoueur) throws PartieException {
		
		// Recherche de la partie
		Partie partie = partiesEnAttente.get(idPartie);
		
		// V�rifications
		if(partie == null)
			throw new PartieException("La partie que vous tentez de joindre n'�xiste pas.");
		if(partie.getJoueurBleu() != null)
			throw new PartieException("La partie que vous tentez de joindre est pleine.");
		
		// Le joueur rejoint la partie
		Joueur joueurBleu = new Joueur(nomJoueur);
		partie.ajouterJoueurBleu(joueurBleu);
		
		// La partie n'est plus en attente
		partiesEnAttente.remove(idPartie);
		
		partieDao.enregistrer(partie);
		
		return partie;
		
	}

}
