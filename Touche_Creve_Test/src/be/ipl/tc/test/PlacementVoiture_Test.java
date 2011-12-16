package be.ipl.tc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import be.ipl.tc.domaine.Joueur;
import be.ipl.tc.domaine.Partie;
import be.ipl.tc.domaine.Voiture;
import be.ipl.tc.exceptions.ArgumentInvalideException;
import be.ipl.tc.exceptions.VoitureException;
import be.ipl.tc.usecases.GestionVoituresRemote;

public class PlacementVoiture_Test {
	
	private static GestionVoituresRemote uccPlacementVoiture;
	private Partie partie;
	private int nomPartieUnique = 0;
	@BeforeClass
	public static void init() throws Exception {

		Context jndi = new InitialContext();
		uccPlacementVoiture = (GestionVoituresRemote) jndi
				.lookup("Touche_CreveEAR/GestionVoituresImpl/remote");
	}
	@Before
	public void setUp() throws Exception {
		
		partie = new Partie(new Joueur("Joueur de test rouge"), "Placement_Test" + nomPartieUnique++);
		partie.ajouterJoueurBleu(new Joueur("Joueur de test bleu"));
	}
	
	@Test
	public void testEtatPartie(){
		assertTrue(partie.getEtat().equals(Partie.Etat.EN_PLACEMENT));		
	}
	
	@Test
	public void testListerVoituresAPlacer(){
		List<Voiture> listeVoiture=uccPlacementVoiture.listerVoituresAPlacer();
		System.out.println(listeVoiture.size());
		assertEquals(5,listeVoiture.size());
	}
	
	@Test(expected = Exception.class)
	public void testPlacerVoiture() throws ArgumentInvalideException, VoitureException{
		uccPlacementVoiture.placerVoiture(partie.getId(), partie.getJoueurRouge().getIdJoueur(), "Coupé", 2, 2, Voiture.DIRECTION_HORIZONTAL);
	}
	
	@Test (expected = Exception.class)
	public void testPlacerVoitureMauvaisNom() throws ArgumentInvalideException, VoitureException{
		uccPlacementVoiture.placerVoiture(partie.getId(), partie.getJoueurRouge().getIdJoueur(), "Mauvais nom", 3, 2, Voiture.DIRECTION_HORIZONTAL);
	}
	
	@Test(expected = Exception.class)
	public void testPlacerVoitureCollisionNom() throws ArgumentInvalideException, VoitureException{
		uccPlacementVoiture.placerVoiture(partie.getId(), partie.getJoueurRouge().getIdJoueur(), "Break", 3, 2, Voiture.DIRECTION_HORIZONTAL);
		uccPlacementVoiture.placerVoiture(partie.getId(), partie.getJoueurRouge().getIdJoueur(), "Break", 4, 2, Voiture.DIRECTION_HORIZONTAL);
		
	}
	
	@Test(expected = Exception.class)
	public void testPlacerVoitureCollisionPosition() throws ArgumentInvalideException, VoitureException{
		uccPlacementVoiture.placerVoiture(partie.getId(), partie.getJoueurRouge().getIdJoueur(), "Break", 3, 2, Voiture.DIRECTION_HORIZONTAL);
		uccPlacementVoiture.placerVoiture(partie.getId(), partie.getJoueurRouge().getIdJoueur(), "Coupé", 3, 2, Voiture.DIRECTION_HORIZONTAL);
	}
	

	/*
	 * Vérifie que le nom de la voiture créée n'est pas nul
	 */	
	@Test (expected = Exception.class)
	public void testPlacerVoitureNomVoitureNull() throws ArgumentInvalideException, VoitureException{
		uccPlacementVoiture.placerVoiture(partie.getId(), partie.getJoueurRouge().getIdJoueur(),null,1,1,1);
	}
	
	/*
	 * Insertion d'une coordonnée négative sur l'axe horizontal
	 */	
	@Test (expected = Exception.class)
	public void testPlacerVoitureCoordonneesHorizontalesErronees() throws ArgumentInvalideException, VoitureException{
		uccPlacementVoiture.placerVoiture(partie.getId(), partie.getJoueurRouge().getIdJoueur(),"Berline",-1,1,1);
	}
	
	
	/*
	 * Insertion d'une coordonnée négative sur l'axe vertical
	 */
	@Test (expected = Exception.class)
	public void testPlacerVoitureCoordonneesVerticalesErronees() throws ArgumentInvalideException, VoitureException{
		uccPlacementVoiture.placerVoiture(partie.getId(), partie.getJoueurRouge().getIdJoueur(),"Berline",1,-1,1);
	}
	
	/*
	 * Insertion d'une coordonnée négative pour la direction
	 */
	@Test (expected = Exception.class)
	public void testPlacerVoitureCoordonneesDirectionErronees() throws ArgumentInvalideException, VoitureException{
		uccPlacementVoiture.placerVoiture(partie.getId(), partie.getJoueurRouge().getIdJoueur(),"Berline",1,1,-1);
	}

	/*
	 * Insertion d'une coordonnée supérieure à la limite sur l'axe horizontal
	 */	
	@Test (expected = Exception.class)
	public void testPlacerVoitureCoordonneesHorizontalesErronees2() throws ArgumentInvalideException, VoitureException{
		uccPlacementVoiture.placerVoiture(partie.getId(), partie.getJoueurRouge().getIdJoueur(),"Berline",10,1,1);
	}
	
	
	/*
	 * Insertion d'une coordonnée supérieure à la limite sur l'axe vertical
	 */
	@Test (expected = Exception.class)
	public void testPlacerVoitureCoordonneesVerticalesErronees2() throws ArgumentInvalideException, VoitureException{
		uccPlacementVoiture.placerVoiture(partie.getId(), partie.getJoueurRouge().getIdJoueur(),"Berline",1,10,1);
	}
	
	/*
	 * Insertion d'une coordonnée supérieure à la limite pour la direction
	 */
	@Test (expected = Exception.class)
	public void testPlacerVoitureCoordonneesDirectionErronees2() throws ArgumentInvalideException, VoitureException{
		uccPlacementVoiture.placerVoiture(partie.getId(), partie.getJoueurRouge().getIdJoueur(),"Berline",1,1,10);
	}
	
	
	
}
