package be.ipl.tc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import be.ipl.tc.domaine.Partie;
import be.ipl.tc.domaine.Voiture;
import be.ipl.tc.exceptions.ArgumentInvalideException;
import be.ipl.tc.exceptions.VoitureException;
import be.ipl.tc.usecases.GestionPartiesRemote;
import be.ipl.tc.usecases.GestionVoituresRemote;

public class PlacementVoiture_Test {

	private static GestionPartiesRemote uccParties;
	private static GestionVoituresRemote uccPlacementVoiture;
	private Partie partie;
	private Voiture voiture;
	private static int nomPartieUnique = 0;

	@BeforeClass
	public static void init() throws Exception {

		Context jndi = new InitialContext();
		uccPlacementVoiture = (GestionVoituresRemote) jndi
				.lookup("Touche_CreveEAR/GestionVoituresImpl/remote");
		uccParties = (GestionPartiesRemote) jndi
				.lookup("Touche_CreveEAR/GestionPartiesImpl/remote");
	}

	@Before
	public void setUp() throws Exception {
		partie = uccParties.creerPartie("Joueur de test rouge",
				"Placement_Test" + nomPartieUnique++);
		partie = uccParties.rejoindrePartie(partie.getId(),
				"Joueur de test bleu");
	}

	/**
	 * Vérification que l'état de la partie est bien en placement
	 * 
	 */
	@Test
	public void testEtatPartie() {
		assertTrue(partie.getEtat().equals(Partie.Etat.EN_PLACEMENT));
	}

	/**
	 * Vérification qu'il y a bien 5 voitures que l'on doit placer.
	 * 
	 */
	@Test
	public void testListerVoituresAPlacer() {
		List<Voiture> listeVoiture = uccPlacementVoiture
				.listerVoituresAPlacer();
		assertEquals(5, listeVoiture.size());
	}

	/**
	 * Vérification que l'on a bien placer la voiture à la bonne position.
	 * 
	 */
	@Test
	public void testPlacerVoiture() throws ArgumentInvalideException,
			VoitureException {
		partie = uccPlacementVoiture.placerVoiture(partie.getId(), partie
				.getJoueurRouge().getIdJoueur(), "Break", 2, 2,
				Voiture.DIRECTION_HORIZONTAL);
		List<Voiture> voitures = partie.getJoueurRouge().getVoitures();
		for (Voiture v : voitures) {
			if (v.getNom().equals("Break")) {
				voiture = v;
				break;
			}
		}
		assertEquals(2, voiture.getColonne());
		assertEquals(2, voiture.getLigne());
		assertEquals(0, voiture.getDirection());
		assertEquals(4, voiture.getNbrPneusRestant());
	}

	/**
	 * Tentative de placement d'une voiture qui porte un nom différent des 5 de
	 * la liste.
	 * 
	 * @throws VoitureException
	 */
	@Test(expected = VoitureException.class)
	public void testPlacerVoitureMauvaisNom() throws ArgumentInvalideException,
			VoitureException {
		uccPlacementVoiture.placerVoiture(partie.getId(), partie
				.getJoueurRouge().getIdJoueur(), "Mauvais nom", 3, 2,
				Voiture.DIRECTION_HORIZONTAL);
	}

	/**
	 * Tentative de placement d'une voiture qui a déja été placé.
	 * 
	 * @throws VoitureException
	 */
	@Test(expected = VoitureException.class)
	public void testPlacerVoitureCollisionNom()
			throws ArgumentInvalideException, VoitureException {
		uccPlacementVoiture.placerVoiture(partie.getId(), partie
				.getJoueurRouge().getIdJoueur(), "Break", 3, 2,
				Voiture.DIRECTION_HORIZONTAL);
		uccPlacementVoiture.placerVoiture(partie.getId(), partie
				.getJoueurRouge().getIdJoueur(), "Break", 4, 2,
				Voiture.DIRECTION_HORIZONTAL);
	}

	/**
	 * Tentative de placement d'une voiture à la meme position qu'une autre.
	 * 
	 * @throws EJBException
	 */
	@Test(expected = EJBException.class)
	public void testPlacerVoitureCollisionPosition()
			throws ArgumentInvalideException, VoitureException {
		uccPlacementVoiture.placerVoiture(partie.getId(), partie
				.getJoueurRouge().getIdJoueur(), "Break", 3, 2,
				Voiture.DIRECTION_HORIZONTAL);
		uccPlacementVoiture.placerVoiture(partie.getId(), partie
				.getJoueurRouge().getIdJoueur(), "Coupe", 3, 2,
				Voiture.DIRECTION_HORIZONTAL);
	}

	/**
	 * Vérifie que le nom de la voiture créée n'est pas nul
	 */
	@Test(expected = EJBException.class)
	public void testPlacerVoitureNomVoitureNull()
			throws ArgumentInvalideException, VoitureException {
		uccPlacementVoiture.placerVoiture(partie.getId(), partie
				.getJoueurRouge().getIdJoueur(), null, 3, 2,
				Voiture.DIRECTION_HORIZONTAL);
	}

	/**
	 * Vérifie qu'un joueur,qui n'appartient pas à la partie, ne puisse placer
	 * sa voiture.
	 */
	@Test(expected = EJBException.class)
	public void testPlacerVoitureParJoueurInconnuParLaPartie()
			throws ArgumentInvalideException, VoitureException {
		uccPlacementVoiture.placerVoiture(partie.getId(), -1, "Break", 3, 2,
				Voiture.DIRECTION_HORIZONTAL);
	}

	/**
	 * Vérifie qu'un joueur ne puisse placer sa voiture sur une partie
	 * inexistante.
	 */
	@Test(expected = EJBException.class)
	public void testPlacerVoitureDansUnePartieInexistante()
			throws ArgumentInvalideException, VoitureException {
		uccPlacementVoiture.placerVoiture(-1, partie.getJoueurRouge()
				.getIdJoueur(), "Break", 3, 2, Voiture.DIRECTION_HORIZONTAL);
	}

	/**
	 * Insertion d'une coordonnée négative sur l'axe horizontal
	 */
	@Test(expected = EJBException.class)
	public void testPlacerVoitureCoordonneesHorizontalesErronees()
			throws ArgumentInvalideException, VoitureException {
		uccPlacementVoiture.placerVoiture(partie.getId(), partie
				.getJoueurRouge().getIdJoueur(), "Berline", -1, 1, 1);
	}

	/**
	 * Insertion d'une coordonnée négative sur l'axe vertical
	 */
	@Test(expected = EJBException.class)
	public void testPlacerVoitureCoordonneesVerticalesErronees()
			throws ArgumentInvalideException, VoitureException {
		uccPlacementVoiture.placerVoiture(partie.getId(), partie
				.getJoueurRouge().getIdJoueur(), "Berline", 1, -1, 1);
	}

	/**
	 * Insertion d'une coordonnée négative pour la direction
	 */
	@Test(expected = EJBException.class)
	public void testPlacerVoitureCoordonneesDirectionErronees()
			throws ArgumentInvalideException, VoitureException {
		uccPlacementVoiture.placerVoiture(partie.getId(), partie
				.getJoueurRouge().getIdJoueur(), "Berline", 1, 1, -1);
	}

	/**
	 * Insertion d'une coordonnée supérieure à la limite sur l'axe horizontal
	 */
	@Test(expected = EJBException.class)
	public void testPlacerVoitureCoordonneesHorizontalesErronees2()
			throws ArgumentInvalideException, VoitureException {
		uccPlacementVoiture.placerVoiture(partie.getId(), partie
				.getJoueurRouge().getIdJoueur(), "Berline", 10, 1, 1);
	}

	/**
	 * Insertion d'une coordonnée supérieure à la limite sur l'axe vertical
	 */
	@Test(expected = EJBException.class)
	public void testPlacerVoitureCoordonneesVerticalesErronees2()
			throws ArgumentInvalideException, VoitureException {
		uccPlacementVoiture.placerVoiture(partie.getId(), partie
				.getJoueurRouge().getIdJoueur(), "Berline", 1, 10, 1);
	}

	/**
	 * Insertion d'une coordonnée supérieure à la limite pour la direction
	 */
	@Test(expected = EJBException.class)
	public void testPlacerVoitureCoordonneesDirectionErronees2()
			throws ArgumentInvalideException, VoitureException {
		uccPlacementVoiture.placerVoiture(partie.getId(), partie
				.getJoueurRouge().getIdJoueur(), "Berline", 1, 1, 10);
	}

	/**
	 * Vérifie que le joueur ne peut placer une voiture qui déborde du champ de
	 * jeu.
	 */
	@Test(expected = EJBException.class)
	public void testPlacerVoitureQuiDebordeDeLaTableCoinBasGaucheVertical()
			throws ArgumentInvalideException, VoitureException {
		uccPlacementVoiture.placerVoiture(partie.getId(), partie
				.getJoueurRouge().getIdJoueur(), "Berline", 9, 0, 1);
	}

	/**
	 * Vérifie que le joueur ne peut placer une voiture qui déborde du champ de
	 * jeu.
	 */
	@Test(expected = EJBException.class)
	public void testPlacerVoitureQuiDebordeDeLaTableCoinBasDroitVertical()
			throws ArgumentInvalideException, VoitureException {
		uccPlacementVoiture.placerVoiture(partie.getId(), partie
				.getJoueurRouge().getIdJoueur(), "Berline", 9, 9, 1);
	}

	/**
	 * Vérifie que le joueur ne peut placer une voiture qui déborde du champ de
	 * jeu.
	 */
	@Test(expected = EJBException.class)
	public void testPlacerVoitureQuiDebordeDeLaTableCoinBasDroitHorizontal()
			throws ArgumentInvalideException, VoitureException {
		uccPlacementVoiture.placerVoiture(partie.getId(), partie
				.getJoueurRouge().getIdJoueur(), "Berline", 9, 9, 0);
	}

	/**
	 * Vérifie que le joueur ne peut placer une voiture qui déborde du champ de
	 * jeu.
	 */
	@Test(expected = EJBException.class)
	public void testPlacerVoitureQuiDebordeDeLaTableCoinHautDroitHorizontal()
			throws ArgumentInvalideException, VoitureException {
		uccPlacementVoiture.placerVoiture(partie.getId(), partie
				.getJoueurRouge().getIdJoueur(), "Berline", 0, 9, 0);
	}

	/**
	 * Placement d'une voiture juste derrière une autre.(Les 2 en horizontale)
	 */
	@Test
	public void testPlacerVoitureCroiseAutreVoiture()
			throws ArgumentInvalideException, VoitureException {
		uccPlacementVoiture.placerVoiture(partie.getId(), partie
				.getJoueurRouge().getIdJoueur(), "Berline", 2, 0, 0);
		uccPlacementVoiture.placerVoiture(partie.getId(), partie
				.getJoueurRouge().getIdJoueur(), "Break", 2, 3, 0);
	}

	/**
	 * Placement d'une voiture juste en dessous d'une autre(Les 2 en verticale)
	 */
	@Test
	public void testPlacerVoitureCroiseAutreVoitureB()
			throws ArgumentInvalideException, VoitureException {
		uccPlacementVoiture.placerVoiture(partie.getId(), partie
				.getJoueurRouge().getIdJoueur(), "Citadine", 0, 0, 1);
		uccPlacementVoiture.placerVoiture(partie.getId(), partie
				.getJoueurRouge().getIdJoueur(), "Limousine", 2, 0, 1);

	}

	/**
	 * Placement d'une voiture juste avant une autre.(Les 2 en horizontale)
	 * 
	 */
	@Test
	public void testPlacerVoitureCroiseAutreVoitureC()
			throws ArgumentInvalideException, VoitureException {
		uccPlacementVoiture.placerVoiture(partie.getId(), partie
				.getJoueurRouge().getIdJoueur(), "Limousine", 0, 2, 0);
		uccPlacementVoiture.placerVoiture(partie.getId(), partie
				.getJoueurRouge().getIdJoueur(), "Citadine", 0, 0, 0);
	}
	/**
	 * Tentative de placement d'une voiture sur une autre.
	 * @throws EJBException
	 */
	@Test(expected = EJBException.class)
	public void testPlacerVoitureCroiseAutreVoitureD()
			throws ArgumentInvalideException, VoitureException {
		uccPlacementVoiture.placerVoiture(partie.getId(), partie
				.getJoueurRouge().getIdJoueur(), "Limousine", 0, 1, 1);
		uccPlacementVoiture.placerVoiture(partie.getId(), partie
				.getJoueurRouge().getIdJoueur(), "Citadine", 2, 0, 0);
	}
	/**
	 * Placement d'une voiture en fin de ligne. (0.8)
	 */
	@Test
	public void testPlacerVoitureCroiseAutreVoitureE()
			throws ArgumentInvalideException, VoitureException {
		uccPlacementVoiture.placerVoiture(partie.getId(), partie
				.getJoueurRouge().getIdJoueur(), "Citadine", 0, 8, 0);
	}
	/**
	 * Placement d'une voiture en fin de colonne. (8.0)
	 */
	@Test
	public void testPlacerVoitureCroiseAutreVoitureF()
			throws ArgumentInvalideException, VoitureException {
		uccPlacementVoiture.placerVoiture(partie.getId(), partie
				.getJoueurRouge().getIdJoueur(), "Citadine", 8, 0, 1);
	}
	/**
	 * Placement d'une voiture dans le coin bas à droite. (9.8)
	 */
	@Test
	public void testPlacerVoitureCroiseAutreVoitureG()
			throws ArgumentInvalideException, VoitureException {
		uccPlacementVoiture.placerVoiture(partie.getId(), partie
				.getJoueurRouge().getIdJoueur(), "Citadine", 9, 8, 0);
	}

}
