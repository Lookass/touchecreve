package be.ipl.tc.test;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import be.ipl.tc.domaine.Partie;
import be.ipl.tc.exceptions.PartieException;
import be.ipl.tc.usecases.GestionPartiesRemote;

public class TestPartie {
	
	private static GestionPartiesRemote uccPartie;
	private static Partie partie;
	
	@BeforeClass
	public static void init() throws Exception {
		
		Context jndi = new InitialContext();
		uccPartie = (GestionPartiesRemote) jndi.lookup("Touche_CreveEAR/GestionPartiesImpl/remote");
		
	}
	
	@Test
	public void testCreerPartie1() {
		
		uccPartie.creerPartie("Joueur 1", "Partie 4");
		uccPartie.creerPartie("Joueur 1", "Partie 5");
		uccPartie.creerPartie("Joueur 1", "Partie 6");
		
		uccPartie.creerPartie("Joueur 1", "Partie 7");
		uccPartie.creerPartie("Joueur 1", "Partie 8");
		uccPartie.creerPartie("Joueur 1", "Partie 9");
		
	}
	
	@Test(expected = PartieException.class)
	public void testCreerPartie2() {
		
		uccPartie.creerPartie("Joueur 2", "Partie 1");
		
	}
		
	
	@Test
	public void testListerPartiesEnAttente() {
		
		List<Partie> listePartiesEnAttente = uccPartie.listerPartiesEnAttente();
		assertEquals(3, listePartiesEnAttente.size());
		
	}
	
	@Test
	public void testListerPartiesTerminees() {
		
		List<Partie> listePartiesTerminees = uccPartie.listerPartiesTerminees();
		assertEquals(0, listePartiesTerminees.size());
		
	}

}
