package be.ipl.tc.test;

import static org.junit.Assert.fail;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import be.ipl.tc.domaine.Partie;
import be.ipl.tc.domaine.Voiture;
import be.ipl.tc.usecases.GestionPartiesRemote;
import be.ipl.tc.usecases.GestionTentativesCrevaisonRemote;
import be.ipl.tc.usecases.GestionVoituresRemote;

public class TentativeCrevaison_Test {

	private static GestionVoituresRemote uccVoitures;
	private static GestionPartiesRemote uccParties;
	private static GestionTentativesCrevaisonRemote uccTentatives;

	private static Partie partieEnAttente;
	private static Partie partieEnPreparation;
	private static Partie partieEnCours;
	private static Partie partieTerminee;

	private static int nomPartieUnique = 2000;

	@BeforeClass
	public static void init() throws Exception {

		System.out.println("BeforeClass 0");

		Context jndi = new InitialContext();
		uccVoitures = (GestionVoituresRemote) jndi
				.lookup("Touche_CreveEAR/GestionVoituresImpl/remote");
		uccParties = (GestionPartiesRemote) jndi
				.lookup("Touche_CreveEAR/GestionPartiesImpl/remote");
		uccTentatives = (GestionTentativesCrevaisonRemote) jndi
				.lookup("Touche_CreveEAR/GestionTentativesCrevaisonImpl/remote");

		System.out.println("BeforeClass 1");
	}

	@Before
	public void setUp() throws Exception {

		try {

			System.out.println("setUp 0");

			// Partie en attente
			partieEnAttente = uccParties.creerPartie(
					"Joueur de test en attente",
					"TentativeCrevaison_Test : Partie en attente "
							+ nomPartieUnique++);

			// Partie en pr�paration
			partieEnPreparation = uccParties.creerPartie(
					"Joueur de test rouge",
					"TentativeCrevaison_Test : Partie en pr�paration "
							+ nomPartieUnique);
			uccParties.rejoindrePartie(partieEnPreparation.getId(),
					"Joueur de test bleu");

			// Partie en cours
			partieEnCours = uccParties.creerPartie("Joueur de test rouge",
					"TentativeCrevaison_Test : Partie en cours "
							+ nomPartieUnique++);
			partieEnCours = uccParties.rejoindrePartie(partieEnCours.getId(),
					"Joueur de test bleu");
			uccVoitures.placerVoiture(partieEnCours.getId(), partieEnCours
					.getJoueurRouge().getIdJoueur(), "Citadine", 0, 0,
					Voiture.DIRECTION_HORIZONTAL);
			uccVoitures.placerVoiture(partieEnCours.getId(), partieEnCours
					.getJoueurRouge().getIdJoueur(), "Coupe", 1, 0,
					Voiture.DIRECTION_HORIZONTAL);
			uccVoitures.placerVoiture(partieEnCours.getId(), partieEnCours
					.getJoueurRouge().getIdJoueur(), "Berline", 2, 0,
					Voiture.DIRECTION_HORIZONTAL);
			uccVoitures.placerVoiture(partieEnCours.getId(), partieEnCours
					.getJoueurRouge().getIdJoueur(), "Break", 3, 0,
					Voiture.DIRECTION_HORIZONTAL);
			uccVoitures.placerVoiture(partieEnCours.getId(), partieEnCours
					.getJoueurRouge().getIdJoueur(), "Limousine", 4, 0,
					Voiture.DIRECTION_HORIZONTAL);
			uccVoitures.placerVoiture(partieEnCours.getId(), partieEnCours
					.getJoueurBleu().getIdJoueur(), "Citadine", 5, 0,
					Voiture.DIRECTION_HORIZONTAL);
			uccVoitures.placerVoiture(partieEnCours.getId(), partieEnCours
					.getJoueurBleu().getIdJoueur(), "Coupe", 6, 0,
					Voiture.DIRECTION_HORIZONTAL);
			uccVoitures.placerVoiture(partieEnCours.getId(), partieEnCours
					.getJoueurBleu().getIdJoueur(), "Berline", 7, 0,
					Voiture.DIRECTION_HORIZONTAL);
			uccVoitures.placerVoiture(partieEnCours.getId(), partieEnCours
					.getJoueurBleu().getIdJoueur(), "Break", 8, 0,
					Voiture.DIRECTION_HORIZONTAL);
			uccVoitures.placerVoiture(partieEnCours.getId(), partieEnCours
					.getJoueurBleu().getIdJoueur(), "Limousine", 9, 0,
					Voiture.DIRECTION_HORIZONTAL);

			// Partie termin�e

			System.out.println("setUp 1");

		} catch (Throwable t) {
			System.out.println(t);
		}

	}

	/**
	 * Tentative de crevaison sans sp�cifier la partie
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testTenterCrevaison1() throws Exception {

		uccTentatives.tenterCrevaison(-1, partieEnCours.getJoueurRouge()
				.getIdJoueur(), 0, 0);

	}

	/**
	 * Tentative de crevaison sans sp�cifier le joueur
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testTenterCrevaison2() throws Exception {

		uccTentatives.tenterCrevaison(partieEnCours.getId(), -1, 0, 0);

	}

	/**
	 * Tentative de crevaison � un indice de ligne n�gatif
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testTenterCrevaison3() throws Exception {

		uccTentatives.tenterCrevaison(partieEnCours.getId(), partieEnCours
				.getJoueurRouge().getIdJoueur(), -1, 0);

	}

	/**
	 * Tentative de crevaison � un indice de colonne n�gatif
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testTenterCrevaison4() throws Exception {

		uccTentatives.tenterCrevaison(partieEnCours.getId(), partieEnCours
				.getJoueurRouge().getIdJoueur(), 0, -1);

	}

	/**
	 * Tentative de crevaison � un indice de ligne au del� des limites
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testTenterCrevaison5() throws Exception {

		uccTentatives
				.tenterCrevaison(partieEnCours.getId(), partieEnCours
						.getJoueurRouge().getIdJoueur(),
						Partie.LIGNE_INDICE_MAX + 1, 0);

	}

	/**
	 * Tentative de crevaison � un indice de colonne au del� des limites
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testTenterCrevaison6() throws Exception {

		uccTentatives.tenterCrevaison(partieEnCours.getId(), partieEnCours
				.getJoueurRouge().getIdJoueur(), 0,
				Partie.COLONNE_INDICE_MAX + 1);

	}

	/**
	 * Tentative de crevaison en sp�cifiant une partie � laquelle le joueur ne
	 * joue pas
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testTenterCrevaison7() throws Exception {

		uccTentatives.tenterCrevaison(partieEnCours.getId(), 555555, 0, 0);

	}

	/**
	 * Tentative de crevaison en sp�cifiant un joueur � qui le tour n'appartient
	 * pas
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testTenterCrevaison8() throws Exception {

		uccTentatives.tenterCrevaison(partieEnCours.getId(), partieEnCours
				.getJoueurBleu().getIdJoueur(), 0, 0);

	}

	/**
	 * Tentative de crevaison en sp�cifiant une coordon�e d�j� tent�e par ce
	 * joueur
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testTenterCrevaison9() throws Exception {

		uccTentatives.tenterCrevaison(partieEnCours.getId(), partieEnCours
				.getJoueurRouge().getIdJoueur(), 0, 0);
		uccTentatives.tenterCrevaison(partieEnCours.getId(), partieEnCours
				.getJoueurBleu().getIdJoueur(), 0, 0);
		uccTentatives.tenterCrevaison(partieEnCours.getId(), partieEnCours
				.getJoueurRouge().getIdJoueur(), 0, 0);

	}

	/**
	 * Tentative de crevaison en sp�cifiant une partie en �tat EN_ATTENTE
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testTenterCrevaison10() throws Exception {

		uccTentatives.tenterCrevaison(partieEnAttente.getId(), partieEnAttente
				.getJoueurRouge().getIdJoueur(), 0, 0);

	}

	/**
	 * Tentative de crevaison en sp�cifiant une partie en �tat EN_PREPARATION et
	 * le joueur rouge de celle-ci
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testTenterCrevaison11() throws Exception {

		uccTentatives.tenterCrevaison(partieEnAttente.getId(), partieEnAttente
				.getJoueurRouge().getIdJoueur(), 0, 0);

	}

	/**
	 * Tentative de crevaison en sp�cifiant une partie en �tat EN_PREPARATION et
	 * le joueur bleu de celle-ci
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testTenterCrevaison12() throws Exception {

		uccTentatives.tenterCrevaison(partieEnAttente.getId(), partieEnAttente
				.getJoueurBleu().getIdJoueur(), 0, 0);

	}

	/**
	 * Tentative de crevaison en sp�cifiant une partie en �tat TERMINEE
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testTenterCrevaison13() throws Exception {

		uccTentatives.tenterCrevaison(partieTerminee.getId(), partieTerminee
				.getJoueurRouge().getIdJoueur(), 0, 0);

	}

	/**
	 * Tentative de crevaison pour une partie en pr�paration
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testTenterCrevaison14() throws Exception {

		uccTentatives.tenterCrevaison(partieEnPreparation.getId(),
				partieEnPreparation.getJoueurRouge().getIdJoueur(), 0, 0);

	}

	/**
	 * Tentative de crevaison valide
	 * 
	 * @throws Exception
	 */
	@Test
	public void testTenterCrevaison15() throws Exception {

		try {
			uccTentatives.tenterCrevaison(partieEnCours.getId(), partieEnCours
					.getJoueurRouge().getIdJoueur(), 0, 0);
		} catch (Throwable t) {
			System.out.println(t);
			fail();
		}

	}

}
