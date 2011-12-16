package be.ipl.tc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
		assertEquals(5,listeVoiture.size());
	}
	
	@Test
	public void testPlacerVoiture() throws ArgumentInvalideException, VoitureException{
		uccPlacementVoiture.placerVoiture(partie, partie.getJoueurRouge(), "Coupé", 2, 2, Voiture.DIRECTION_HORIZONTAL);
	}
	
	@Test
	public void testPlacerVoitureMauvaisNom() throws ArgumentInvalideException, VoitureException{
		uccPlacementVoiture.placerVoiture(partie, partie.getJoueurRouge(), "Mauvais nom", 3, 2, Voiture.DIRECTION_HORIZONTAL);
	}
	
	@Test
	public void testPlacerVoitureCollisionNom() throws ArgumentInvalideException, VoitureException{
		uccPlacementVoiture.placerVoiture(partie, partie.getJoueurRouge(), "Break", 3, 2, Voiture.DIRECTION_HORIZONTAL);
		uccPlacementVoiture.placerVoiture(partie, partie.getJoueurRouge(), "Break", 4, 2, Voiture.DIRECTION_HORIZONTAL);
		
	}
	
	@Test
	public void testPlacerVoitureCollisionPosition() throws ArgumentInvalideException, VoitureException{
		uccPlacementVoiture.placerVoiture(partie, partie.getJoueurRouge(), "Break", 3, 2, Voiture.DIRECTION_HORIZONTAL);
		uccPlacementVoiture.placerVoiture(partie, partie.getJoueurRouge(), "Coupé", 3, 2, Voiture.DIRECTION_HORIZONTAL);
	}
	
	
	
	
	
	
	
	

}
