package be.ipl.tc.test;

import static org.junit.Assert.fail;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import be.ipl.tc.domaine.Joueur;
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
	
	private static int nomPartieUnique = 50;
	
	@BeforeClass
	public static void init() throws Exception {
		
		Context jndi = new InitialContext();
		uccVoitures = (GestionVoituresRemote) jndi.lookup("Touche_CreveEAR/GestionVoituresImpl/remote");
		uccParties = (GestionPartiesRemote) jndi.lookup("Touche_CreveEAR/GestionPartiesImpl/remote");
		uccTentatives = (GestionTentativesCrevaisonRemote) jndi.lookup("Touche_CreveEAR/GestionTentativesCrevaisonImpl/remote");
	}
	
	@Before
	public void setUp() throws Exception {
		
		// Partie en attente
		partieEnAttente = uccParties.creerPartie("Joueur de test en attente", "TentativeCrevaison_Test : Partie en attente " + nomPartieUnique++);
		
		// Partie en préparation
		partieEnPreparation = uccParties.creerPartie("Joueur de test rouge", "TentativeCrevaison_Test : Partie en préparation " + nomPartieUnique);
		
		// Partie en cours
		partieEnCours = uccParties.creerPartie("Joueur de test rouge", "TentativeCrevaison_Test : Partie en cours " + nomPartieUnique++);
		System.out.println("T 0");
		System.out.println("Partie etat 1" + partieEnCours.getEtat());
		partieEnCours = uccParties.rejoindrePartie(partieEnCours.getId(), "Joueur de test bleu");
		System.out.println("T 1");
		System.out.println("Partie etat 2" + partieEnCours.getEtat());
		uccVoitures.placerVoiture(partieEnCours.getId(), partieEnCours.getJoueurRouge().getIdJoueur(), "Citadine", 0, 0, Voiture.DIRECTION_HORIZONTAL);
		System.out.println("T 2");
		uccVoitures.placerVoiture(partieEnCours.getId(), partieEnCours.getJoueurRouge().getIdJoueur(), "Coupé", 1, 0, Voiture.DIRECTION_HORIZONTAL);
		uccVoitures.placerVoiture(partieEnCours.getId(), partieEnCours.getJoueurRouge().getIdJoueur(), "Berline", 2, 0, Voiture.DIRECTION_HORIZONTAL);
		uccVoitures.placerVoiture(partieEnCours.getId(), partieEnCours.getJoueurRouge().getIdJoueur(), "Break", 3, 0, Voiture.DIRECTION_HORIZONTAL);
		uccVoitures.placerVoiture(partieEnCours.getId(), partieEnCours.getJoueurRouge().getIdJoueur(), "Limousine", 4, 0, Voiture.DIRECTION_HORIZONTAL);
		uccVoitures.placerVoiture(partieEnCours.getId(), partieEnCours.getJoueurBleu().getIdJoueur(), "Citadine", 5, 0, Voiture.DIRECTION_HORIZONTAL);
		uccVoitures.placerVoiture(partieEnCours.getId(), partieEnCours.getJoueurBleu().getIdJoueur(), "Coupé", 6, 0, Voiture.DIRECTION_HORIZONTAL);
		uccVoitures.placerVoiture(partieEnCours.getId(), partieEnCours.getJoueurBleu().getIdJoueur(), "Berline", 7, 0, Voiture.DIRECTION_HORIZONTAL);
		uccVoitures.placerVoiture(partieEnCours.getId(), partieEnCours.getJoueurBleu().getIdJoueur(), "Break", 8, 0, Voiture.DIRECTION_HORIZONTAL);
		System.out.println("T 3");
		uccVoitures.placerVoiture(partieEnCours.getId(), partieEnCours.getJoueurBleu().getIdJoueur(), "Limousine", 9, 0, Voiture.DIRECTION_HORIZONTAL);
		System.out.println("T 4");
		// Partie terminée
	}
	
	/**
	 * Tentative de crevaison sans spécifier la partie
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testTenterCrevaison1() throws Exception {
		
		uccTentatives.tenterCrevaison(null, partieEnCours.getJoueurRouge(), 0, 0);
		
	}
	
	/**
	 * Tentative de crevaison sans spécifier le joueur
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testTenterCrevaison2() throws Exception {
		
		uccTentatives.tenterCrevaison(partieEnCours, null, 0, 0);
		
	}
	
	/**
	 * Tentative de crevaison à un indice de ligne négatif
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testTenterCrevaison3() throws Exception {
		
		uccTentatives.tenterCrevaison(partieEnCours, partieEnCours.getJoueurRouge(), -1, 0);
		
	}
	
	/**
	 * Tentative de crevaison à un indice de colonne négatif
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testTenterCrevaison4() throws Exception {
		
		uccTentatives.tenterCrevaison(partieEnCours, partieEnCours.getJoueurRouge(), 0, -1);
		
	}
	
	/**
	 * Tentative de crevaison à un indice de ligne au delà des limites 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testTenterCrevaison5() throws Exception {
		
		uccTentatives.tenterCrevaison(partieEnCours, partieEnCours.getJoueurRouge(), Partie.LIGNE_INDICE_MAX + 1, 0);
		
	}
	
	/**
	 * Tentative de crevaison à un indice de colonne au delà des limites
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testTenterCrevaison6() throws Exception {
		
		uccTentatives.tenterCrevaison(partieEnCours, partieEnCours.getJoueurRouge(), 0, partieEnCours.COLONNE_INDICE_MAX + 1);
		
	}
	
	/**
	 * Tentative de crevaison en spécifiant une partie à laquelle le joueur ne joue pas
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testTenterCrevaison7() throws Exception {
		
		try {
			Joueur autreJoueur = new Joueur("autreJoueur");
			uccTentatives.tenterCrevaison(partieEnCours, autreJoueur, 0, 0);
		} catch (Exception e) {
			System.out.println(e);
			throw e;
		}
		
	}
	
	/**
	 * Tentative de crevaison en spécifiant un joueur à qui le tour n'appartient pas
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testTenterCrevaison8() throws Exception {
		
		uccTentatives.tenterCrevaison(partieEnCours, partieEnCours.getJoueurBleu(), 0, 0);
		
	}
	
	/**
	 * Tentative de crevaison en spécifiant une coordonée déjà tentée par ce joueur
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testTenterCrevaison9() throws Exception {
		
		uccTentatives.tenterCrevaison(partieEnCours, partieEnCours.getJoueurRouge(), 0, 0);
		uccTentatives.tenterCrevaison(partieEnCours, partieEnCours.getJoueurBleu(), 0, 0);
		uccTentatives.tenterCrevaison(partieEnCours, partieEnCours.getJoueurRouge(), 0, 0);
		
	}
	
	/**
	 * Tentative de crevaison en spécifiant une partie en état EN_ATTENTE
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testTenterCrevaison10() throws Exception {
		
		uccTentatives.tenterCrevaison(partieEnAttente, partieEnAttente.getJoueurRouge(), 0, 0);
		
	}
	
	/**
	 * Tentative de crevaison en spécifiant une partie en état EN_PREPARATION et le joueur rouge de celle-ci
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testTenterCrevaison11() throws Exception {
		
		uccTentatives.tenterCrevaison(partieEnAttente, partieEnAttente.getJoueurRouge(), 0, 0);
		
	}
	
	/**
	 * Tentative de crevaison en spécifiant une partie en état EN_PREPARATION et le joueur bleu de celle-ci
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testTenterCrevaison12() throws Exception {
		
		uccTentatives.tenterCrevaison(partieEnAttente, partieEnAttente.getJoueurBleu(), 0, 0);
		
	}
	
	/**
	 * Tentative de crevaison en spécifiant une partie en état TERMINEE
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void testTenterCrevaison13() throws Exception {
		
		uccTentatives.tenterCrevaison(partieTerminee, partieTerminee.getJoueurRouge(), 0, 0);
		
	}
	
	/**
	 * Tentative de crevaison valide
	 * @throws Exception
	 */
	public void testTenterCrevaison14() throws Exception {
		
		try {
			uccTentatives.tenterCrevaison(partieEnCours, partieEnCours.getJoueurRouge(), 0, 0);
		} catch (Throwable t) {
			fail();
		}
		
	}
		
	
}
	