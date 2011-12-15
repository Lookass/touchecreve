package be.ipl.tc.test;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.security.auth.login.FailedLoginException;

import org.junit.BeforeClass;
import org.junit.Test;

import be.ipl.tc.domaine.Partie;
import be.ipl.tc.domaine.Voiture;
import be.ipl.tc.exceptions.PartieException;
import be.ipl.tc.usecases.GestionPartiesRemote;

public class TentativeCrevaison_Test {
	
	private static GestionTentativesCrevaisonRemote uccTentatives;
	
	private Partie partieEnAttente;
	private Partie partieEnPreparation;
	private Partie partieEnCours;
	private Partie partieTerminee;
	
	private int nomPartieUnique = 0;
	
	@BeforeClass
	public static void init() throws Exception {
		
		Context jndi = new InitialContext();
		uccTentatives = (GestionTentativesCrevaisonRemote) jndi.lookup("Touche_CreveEAR/GestionTentativesCrevaisonImpl/remote");
		
		partieEnAttente = new Partie(new Joueur("Joueur de test en attente"), "TentativeCrevaison_Test : Partie en attente");
		partieEnPreparation = new Partie(new Joueur("Joueur de test rouge"), "TentativeCrevaison_Test : Partie en pr�paration");
		partieEnPreparation.ajouterJoueurBleu(new Joueur("Joueur de test bleu"));
//		partieTerminee = new Partie()
	}
	
	@Before
	public void setUp() throws Exception {
		
		partieEnCours = new Partie(new Joueur("Joueur de test rouge"), "TentativeCrevaison_Test" + nomPartieUnique++);
		partieEnCours.ajouterJoueurBleu(new Joueur("Joueur de test bleu"));
		
		// Placer les voitures et commencer la partie..
	}
	
	/**
	 * Tentative de crevaison sans sp�cifier la partie
	 * @throws Exception
	 */
	@Test(expected = ArgumentInvalideException.class)
	public void testTenterCrevaison1() throws Exception {
		
		uccTentatives.tenterCrevaison(null, partieEnCours.getJoueurRouge(), 0, 0);
		
	}
	
	/**
	 * Tentative de crevaison sans sp�cifier le joueur
	 * @throws Exception
	 */
	@Test(expected = ArgumentInvalideException.class)
	public void testTenterCrevaison2() throws Exception {
		
		uccTentatives.tenterCrevaison(partieEnCours, null, 0, 0);
		
	}
	
	/**
	 * Tentative de crevaison � un indice de ligne n�gatif
	 * @throws Exception
	 */
	@Test(expected = ArgumentInvalideException.class)
	public void testTenterCrevaison3() throws Exception {
		
		uccTentatives.tenterCrevaison(partieEnCours, partieEnCours.getJoueurRouge(), -1, 0);
		
	}
	
	/**
	 * Tentative de crevaison � un indice de colonne n�gatif
	 * @throws Exception
	 */
	@Test(expected = ArgumentInvalideException.class)
	public void testTenterCrevaison4() throws Exception {
		
		uccTentatives.tenterCrevaison(partieEnCours, partieEnCours.getJoueurRouge(), 0, -1);
		
	}
	
	/**
	 * Tentative de crevaison � un indice de ligne au del� des limites 
	 * @throws Exception
	 */
	@Test(expected = ArgumentInvalideException.class)
	public void testTenterCrevaison5() throws Exception {
		
		uccTentatives.tenterCrevaison(partieEnCours, partieEnCours.getJoueurRouge(), partieEnCours.LIGNE_INDICE_MAX + 1, 0);
		
	}
	
	/**
	 * Tentative de crevaison � un indice de colonne au del� des limites
	 * @throws Exception
	 */
	@Test(expected = ArgumentInvalideException.class)
	public void testTenterCrevaison6() throws Exception {
		
		uccTentatives.tenterCrevaison(partieEnCours, partieEnCours.getJoueurRouge(), 0, partieEnCours.COLONNE_INDICE_MAX + 1);
		
	}
	
	/**
	 * Tentative de crevaison en sp�cifiant une partie � laquelle le joueur ne joue pas
	 * @throws Exception
	 */
	@Test(expected = PartieException.class)
	public void testTenterCrevaison7() throws Exception {
		
		Joueur autreJoueur = new Joueur("autreJoueur");
		uccTentatives.tenterCrevaison(partieEnCours, autreJoueur, 0, 0);
		
	}
	
	/**
	 * Tentative de crevaison en sp�cifiant un joueur � qui le tour n'appartient pas
	 * @throws Exception
	 */
	@Test(expected = PartieException.class)
	public void testTenterCrevaison8() throws Exception {
		
		uccTentatives.tenterCrevaison(partieEnCours, partieEnCours.getJoueurBleu(), 0, 0);
		
	}
	
	/**
	 * Tentative de crevaison en sp�cifiant une coordon�e d�j� tent�e par ce joueur
	 * @throws Exception
	 */
	@Test(expected = PartieException.class)
	public void testTenterCrevaison9() throws Exception {
		
		uccTentatives.tenterCrevaison(partieEnCours, partieEnCours.getJoueurRouge(), 0, 0);
		uccTentatives.tenterCrevaison(partieEnCours, partieEnCours.getJoueurBleu(), 0, 0);
		uccTentatives.tenterCrevaison(partieEnCours, partieEnCours.getJoueurRouge(), 0, 0);
		
	}
	
	/**
	 * Tentative de crevaison en sp�cifiant une partie en �tat EN_ATTENTE
	 * @throws Exception
	 */
	@Test(expected = PartieException.class)
	public void testTenterCrevaison10() throws Exception {
		
		uccTentatives.tenterCrevaison(partieEnAttente, partieEnAttente.getJoueurRouge(), 0, 0);
		
	}
	
	/**
	 * Tentative de crevaison en sp�cifiant une partie en �tat EN_PREPARATION et le joueur rouge de celle-ci
	 * @throws Exception
	 */
	@Test(expected = PartieException.class)
	public void testTenterCrevaison11() throws Exception {
		
		uccTentatives.tenterCrevaison(partieEnAttente, partieEnAttente.getJoueurRouge(), 0, 0);
		
	}
	
	/**
	 * Tentative de crevaison en sp�cifiant une partie en �tat EN_PREPARATION et le joueur bleu de celle-ci
	 * @throws Exception
	 */
	@Test(expected = PartieException.class)
	public void testTenterCrevaison12() throws Exception {
		
		uccTentatives.tenterCrevaison(partieEnAttente, partieEnAttente.getJoueurBleu(), 0, 0);
		
	}
	
	/**
	 * Tentative de crevaison en sp�cifiant une partie en �tat TERMINEE
	 * @throws Exception
	 */
	@Test(expected = PartieException.class)
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
	