package be.ipl.tc.domaine;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import be.ipl.tc.exceptions.ArgumentInvalideException;
import be.ipl.tc.exceptions.PartieException;

@Entity
@Table(name="PARTIES", schema="TC")
public class Partie implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	private final static int LIGNE_INDICE_MAX = 9;
	private final static int COLONNE_INDICE_MAX = 9;
	
	public enum Etat {
		
		EN_ATTENTE {
			
			public boolean ajouterJoueurBleu(Partie partie, Joueur joueur) {
				
				if(partie.getJoueurRouge().getNom().equals(joueur.getNom()))
					throw new PartieException("Le joueur rouge est d�j� nomm� " + partie.getJoueurRouge().getNom());
				partie.joueurBleu = joueur;
				partie.etat = Etat.EN_PLACEMENT;
				return true;
				
			}
			
		},
		
		EN_PLACEMENT {
			
			public boolean placerVoiture(Partie partie, Joueur joueur, Voiture v) throws ArgumentInvalideException {
				
				if(!joueur.equals(partie.getJoueurBleu()) && !joueur.equals(partie.getJoueurRouge()))
					throw new PartieException("Le joueur n'appartient pas � la partie.");
				
				if(v.getLigne() < 0 || v.getLigne() > Partie.LIGNE_INDICE_MAX)
					throw new ArgumentInvalideException("Indice de la ligne incorrect : " + v.getLigne());
				
				if(v.getColonne() < 0 || v.getColonne() > Partie.COLONNE_INDICE_MAX)
					throw new ArgumentInvalideException("Indice de la colonne incorrect : " + v.getColonne());
				
				if(v.getDirection() == Voiture.DIRECTION_VERTICAL && v.getLigne() + v.getNbrPneus() > LIGNE_INDICE_MAX)
					throw new ArgumentInvalideException("Indice de la ligne incorrect : " + v.getLigne());
				
				if(v.getDirection() == Voiture.DIRECTION_HORIZONTAL && v.getColonne() + v.getNbrPneus() > COLONNE_INDICE_MAX)
					throw new ArgumentInvalideException("Indice de la colonne incorrect : " + v.getColonne());
				
				// La voiture � placer ne doit pas occuper de case utilis�e par une autre voiture
				for(Voiture autre : joueur.getVoitures())
					if(autre.occupePlace(v.getLigne(), v.getColonne()))
						throw new PartieException("L'emplacement [ " + v.getLigne() + ":" + v.getColonne() + "] est d�j� occup�.");
				
				
				
				joueur.ajouterVoiture(v);
				
				return false;
			}
			
		},
		
		EN_COURS {
			
			public TentativeCrevaison tenterCrevaison(Partie partie, Joueur joueur, int ligne, int colonne) throws ArgumentInvalideException {
				
				if(!joueur.equals(partie.getJoueurBleu()) && !joueur.equals(partie.getJoueurRouge()))
					throw new PartieException("Le joueur n'appartient pas � la partie.");
				
				if(ligne < 0 || ligne > Partie.LIGNE_INDICE_MAX)
					throw new ArgumentInvalideException("Indice de la ligne incorrect : " + ligne);
				
				if(colonne < 0 || colonne > Partie.COLONNE_INDICE_MAX)
					throw new ArgumentInvalideException("Indice de la colonne incorrect : " + colonne);
				
				for(TentativeCrevaison tc : joueur.getTentativesCrevaison())
					if(tc.getLigne() == ligne && tc.getColonne() == colonne)
						throw new PartieException("Tentative d�j� effectu�e en [" + ligne + ":" + colonne + "].");
				
				if(!joueur.equals(partie.getTour()))
					throw new PartieException("Ce n'est pas � votre tour de jouer.");
				
				TentativeCrevaison tentative = new TentativeCrevaison(ligne, colonne);
				
				for(Voiture v : joueur.getVoitures()) {
					if(v.occupePlace(ligne, colonne)) {
						v.creverPneu();
					}
				}
				
				joueur.ajouterTentativeCrevaison(tentative);
				
				partie.tourSuivant();
				
				return tentative;
			}
			
		},
		
		TERMINEE {
			
			public Joueur getVainqueur(Partie partie) {
				
				if(partie.getJoueurRouge().getNbVoituresRestantes() == 0)
					return partie.getJoueurBleu();
				return partie.getJoueurRouge();
				
			}
			
		};
		
		public boolean ajouterJoueurBleu(Partie partie, Joueur joueur) {
			throw new IllegalStateException();
		}
		
		public boolean placerVoiture(Partie partie, Joueur joueur, Voiture voiture) throws ArgumentInvalideException {
			throw new IllegalStateException();
		}
		
		public TentativeCrevaison tenterCrevaison(Partie partie, Joueur joueur, int ligne, int colonne) throws ArgumentInvalideException {
			throw new IllegalStateException();
		}
		
		public Joueur getVainqueur(Partie partie) {
			throw new IllegalStateException();
		}
		
	}	
	
	@Id @GeneratedValue
	private int id;
	@Column(nullable = false, unique = true)
	private String nom;
	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "idJoueurRouge")
	private Joueur joueurRouge;
	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "idJoueurBleu")
	private Joueur joueurBleu;
	@Temporal(TemporalType.DATE)
	private Calendar dateDebut;
	@Enumerated
	private Partie.Etat etat = Partie.Etat.EN_ATTENTE;
	@Transient
	private Joueur tour;

	
	public Partie(){}
	
	public Partie(Joueur joueurRouge, String nom) {
		super();
		this.joueurRouge = joueurRouge;
		this.nom = nom;
		this.tour = joueurRouge;
	}
	
	public int getId() {
		return id;
	}
	
	public String getNom() {
		return nom;
	}
	
	public Joueur getJoueurRouge() {
		return joueurRouge;
	}

	public Joueur getJoueurBleu() {
		return joueurBleu;
	}
	
	private Joueur getTour() {
		return tour;
	}
	
	public Partie.Etat getEtat() {
		return etat;
	}
	
	private void tourSuivant() {
		if(tour == joueurRouge)
			tour = joueurBleu;
		else
			tour = joueurRouge;
	}
	
	public void ajouterJoueurBleu(Joueur joueurBleu) {
		this.etat.ajouterJoueurBleu(this, joueurBleu);
	}
	
	public void placerVoiture(Joueur joueur, Voiture voiture) throws ArgumentInvalideException {
		this.etat.placerVoiture(this, joueur, voiture);
	}
	
	public TentativeCrevaison tenterCrevaison(Joueur joueur, int ligne, int colonne) throws ArgumentInvalideException {
		return this.etat.tenterCrevaison(this, joueur, ligne, colonne);
	}
	
	public Joueur getVainqueur() {
		return this.etat.getVainqueur(this);
	}
	
	public Calendar getDateDebut() {
		return dateDebut;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Partie other = (Partie) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
}