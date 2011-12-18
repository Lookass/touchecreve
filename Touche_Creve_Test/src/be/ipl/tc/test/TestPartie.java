package be.ipl.tc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.BeforeClass;
import org.junit.Test;

import be.ipl.tc.domaine.Partie;
import be.ipl.tc.domaine.TentativeCrevaison;
import be.ipl.tc.domaine.Voiture;
import be.ipl.tc.exceptions.VoitureException;
import be.ipl.tc.usecases.GestionPartiesRemote;
import be.ipl.tc.usecases.GestionTentativesCrevaisonRemote;
import be.ipl.tc.usecases.GestionVoituresRemote;

public class TestPartie {

	private static GestionPartiesRemote uccPartie;
	private static GestionVoituresRemote uccVoitures;
	private static GestionTentativesCrevaisonRemote uccTentatives;

	@BeforeClass
	public static void init() throws Exception {

		Context jndi = new InitialContext();
		uccPartie = (GestionPartiesRemote) jndi.lookup("Touche_CreveEAR/GestionPartiesImpl/remote");
		uccVoitures = (GestionVoituresRemote) jndi.lookup("Touche_CreveEAR/GestionVoituresImpl/remote");
		uccTentatives = (GestionTentativesCrevaisonRemote) jndi.lookup("Touche_CreveEAR/GestionTentativesCrevaisonImpl/remote");

	}

	@Test
	public void testCreerPartie1() {

		uccPartie.creerPartie("Joueur 1", "Partie 1");
		uccPartie.creerPartie("Joueur 2", "Partie 2");
	}

	@Test(expected = EJBException.class)
	public void testCreerPartie2() {
		uccPartie.creerPartie("Joueur 2", "Partie 1");
	}

	@Test
	public void testListerPartiesEnAttente() {

		List<Partie> listePartiesEnAttente = uccPartie.listerPartiesEnAttente();
		assertEquals(2, listePartiesEnAttente.size());

	}

	@Test
	public void testListerPartiesTerminees() {

		List<Partie> listePartiesTerminees = uccPartie.listerPartiesTerminees();
		assertEquals(0, listePartiesTerminees.size());

	}

	@Test(expected = EJBException.class)
	public void testRejoindrePartieSiPartieNull() {
		uccPartie.rejoindrePartie(-1, "Joueur 1");
	}

	@Test
	public void testRejoindrePartie() {
		Partie partie = uccPartie.creerPartie("Joueur 1", "Partie 3");
		Partie partie2 = uccPartie.rejoindrePartie(partie.getId(), "Joueur2");
		assertTrue("Joueur2".equals(partie2.getJoueurBleu().getNom()));
	}

	@Test(expected = EJBException.class)
	public void testRejoindrePartieSiPleine() {
		Partie partie = uccPartie.creerPartie("Joueur 1", "Partie 4");
		uccPartie.rejoindrePartie(partie.getId(), "Joueur 2");
		uccPartie.rejoindrePartie(partie.getId(), "Joueur 3");
	}

	@Test
	public void testRejoindrePartieSuppressionListeAttente() {
		Partie partie = uccPartie.creerPartie("Joueur 1", "Partie 5");
		uccPartie.rejoindrePartie(partie.getId(), "Joueur 2");
		List<Partie> listePartiesEnAttente = uccPartie.listerPartiesEnAttente();
		for (Partie p : listePartiesEnAttente) {
			if (partie.getId() == p.getId()) {
				assertFalse(true);
			}
		}
		assertFalse(false);
	}
	
	@Test
	public void testPartieComplete() {
		
		Partie p = uccPartie.creerPartie("Joueur rouge", "Partie complète");
		
		assertEquals(Partie.Etat.EN_ATTENTE, p.getEtat());
		
		p = uccPartie.rejoindrePartie(p.getId(), "Joueur bleu");
		
		assertEquals(Partie.Etat.EN_PLACEMENT, p.getEtat());
		
		try {
		
			uccVoitures.placerVoiture(p.getId(), p.getJoueurRouge().getIdJoueur(), "Citadine", 0, 0, Voiture.DIRECTION_HORIZONTAL);
			uccVoitures.placerVoiture(p.getId(), p.getJoueurRouge().getIdJoueur(), "Coupe", 1, 0, Voiture.DIRECTION_HORIZONTAL);
			uccVoitures.placerVoiture(p.getId(), p.getJoueurRouge().getIdJoueur(), "Berline", 2, 0, Voiture.DIRECTION_HORIZONTAL);
			uccVoitures.placerVoiture(p.getId(), p.getJoueurRouge().getIdJoueur(), "Break", 3, 0, Voiture.DIRECTION_HORIZONTAL);
			uccVoitures.placerVoiture(p.getId(), p.getJoueurRouge().getIdJoueur(), "Limousine", 4, 0, Voiture.DIRECTION_HORIZONTAL);
			
			uccVoitures.placerVoiture(p.getId(), p.getJoueurBleu().getIdJoueur(), "Citadine", 5, 0, Voiture.DIRECTION_HORIZONTAL);
			uccVoitures.placerVoiture(p.getId(), p.getJoueurBleu().getIdJoueur(), "Coupe", 6, 0, Voiture.DIRECTION_HORIZONTAL);
			uccVoitures.placerVoiture(p.getId(), p.getJoueurBleu().getIdJoueur(), "Berline", 7, 0, Voiture.DIRECTION_HORIZONTAL);
			uccVoitures.placerVoiture(p.getId(), p.getJoueurBleu().getIdJoueur(), "Break", 8, 0, Voiture.DIRECTION_HORIZONTAL);
			p = uccVoitures.placerVoiture(p.getId(), p.getJoueurBleu().getIdJoueur(), "Limousine", 9, 0, Voiture.DIRECTION_HORIZONTAL);
		
		} catch (EJBException e) {
			fail();
		} catch (VoitureException e) {
			fail();
		}
		
		assertEquals(Partie.Etat.EN_COURS, p.getEtat());
		
		try {
		
			// Citadines
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_TOUCHE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurRouge().getIdJoueur(), 5, 0).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_TOUCHE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurBleu().getIdJoueur(), 0, 0).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_CREVE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurRouge().getIdJoueur(), 5, 1).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_CREVE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurBleu().getIdJoueur(), 0, 1).getEtatTentative());
			
			// Coupés
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_TOUCHE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurRouge().getIdJoueur(), 6, 0).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_TOUCHE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurBleu().getIdJoueur(), 1, 0).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_TOUCHE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurRouge().getIdJoueur(), 6, 1).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_TOUCHE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurBleu().getIdJoueur(), 1, 1).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_CREVE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurRouge().getIdJoueur(), 6, 2).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_CREVE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurBleu().getIdJoueur(), 1, 2).getEtatTentative());
			
			// Berlines
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_TOUCHE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurRouge().getIdJoueur(), 7, 0).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_TOUCHE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurBleu().getIdJoueur(), 2, 0).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_TOUCHE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurRouge().getIdJoueur(), 7, 1).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_TOUCHE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurBleu().getIdJoueur(), 2, 1).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_CREVE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurRouge().getIdJoueur(), 7, 2).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_CREVE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurBleu().getIdJoueur(), 2, 2).getEtatTentative());
			
			// Breaks
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_TOUCHE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurRouge().getIdJoueur(), 8, 0).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_TOUCHE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurBleu().getIdJoueur(), 3, 0).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_TOUCHE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurRouge().getIdJoueur(), 8, 1).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_TOUCHE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurBleu().getIdJoueur(), 3, 1).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_TOUCHE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurRouge().getIdJoueur(), 8, 2).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_TOUCHE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurBleu().getIdJoueur(), 3, 2).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_CREVE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurRouge().getIdJoueur(), 8, 3).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_CREVE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurBleu().getIdJoueur(), 3, 3).getEtatTentative());
			
			// Quelques ratés
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_RATE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurRouge().getIdJoueur(), 0, 0).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_RATE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurBleu().getIdJoueur(), 5, 0).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_RATE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurRouge().getIdJoueur(), 0, 8).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_RATE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurBleu().getIdJoueur(), 5, 8).getEtatTentative());
			
			// Une tentative au mauvais tour
			try {
				uccTentatives.tenterCrevaison(p.getId(), p.getJoueurBleu().getIdJoueur(), 0, 8);
				fail();
			} catch (EJBException e) {
				assertTrue(true);
			}
			
			// Limousines
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_TOUCHE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurRouge().getIdJoueur(), 9, 0).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_TOUCHE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurBleu().getIdJoueur(), 4, 0).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_TOUCHE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurRouge().getIdJoueur(), 9, 1).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_TOUCHE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurBleu().getIdJoueur(), 4, 1).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_TOUCHE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurRouge().getIdJoueur(), 9, 2).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_TOUCHE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurBleu().getIdJoueur(), 4, 2).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_TOUCHE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurRouge().getIdJoueur(), 9, 3).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_TOUCHE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurBleu().getIdJoueur(), 4, 3).getEtatTentative());
			assertEquals(TentativeCrevaison.TENTATIVE_ETAT_CREVE, uccTentatives.tenterCrevaison(p.getId(), p.getJoueurRouge().getIdJoueur(), 9, 4).getEtatTentative());

			// Partie finie
		//  En remote, pas moyen de récupérer l'objet partie mis à jour
		//	assertEquals(Partie.Etat.TERMINEE, actual)
			
			// On ne peut plus rien faire sur une partie terminée
			try {
				uccPartie.rejoindrePartie(p.getId(), "Joueur");
				fail();
			} catch (EJBException e) {
				assertTrue(true);
			}
			
			try {
				uccVoitures.placerVoiture(p.getId(), p.getJoueurRouge().getIdJoueur(), "Citadine", 6, 6, Voiture.DIRECTION_HORIZONTAL);
				fail();
			} catch (EJBException e) {
				assertTrue(true);
			} catch (VoitureException e) {
				assertTrue(true);
			}
			
			try {
				uccTentatives.tenterCrevaison(p.getId(), p.getJoueurRouge().getIdJoueur(), 9, 9);
				fail();
			} catch (EJBException e) {
				assertTrue(true);
			}
			
			// Idem on ne peut pas tester getVainqueur en remote car l'objet partie n'est pas mis à jour
			// À tester dans les servlets
			
		} catch (EJBException e) {
			fail();
		}
	}
}
