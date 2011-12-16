package be.ipl.tc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.BeforeClass;
import org.junit.Test;

import be.ipl.tc.domaine.Partie;
import be.ipl.tc.exceptions.PartieException;
import be.ipl.tc.usecases.GestionPartiesRemote;

public class TestPartie {

	private static GestionPartiesRemote uccPartie;
	private static Partie partie;

	@BeforeClass
	public static void init() throws Exception {

		Context jndi = new InitialContext();
		uccPartie = (GestionPartiesRemote) jndi
				.lookup("Touche_CreveEAR/GestionPartiesImpl/remote");

	}

	@Test
	public void testCreerPartie1() {

		uccPartie.creerPartie("Joueur 1", "Partie 1");
		uccPartie.creerPartie("Joueur 2", "Partie 2");
	}

	@Test(expected = PartieException.class)
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

	@Test(expected = PartieException.class)
	public void testRejoindrePartieSiPartieNull() {
		uccPartie.rejoindrePartie(-1, "Joueur 1");
	}

	@Test
	public void testRejoindrePartie() {
		Partie partie = uccPartie.creerPartie("Joueur 1", "Partie 3");
		Partie partie2 = uccPartie.rejoindrePartie(partie.getId(), "Joueur2");
		assertTrue("Joueur2".equals(partie2.getJoueurBleu().getNom()));
	}

	@Test(expected = PartieException.class)
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
}
