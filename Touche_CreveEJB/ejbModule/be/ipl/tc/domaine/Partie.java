package be.ipl.tc.domaine;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import be.ipl.tc.exceptions.ArgumentInvalideException;
import be.ipl.tc.exceptions.PartieException;

@Entity
@Table(name = "PARTIES", schema = "TC")
public class Partie implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public final static int LIGNE_INDICE_MAX = 9;
	public final static int COLONNE_INDICE_MAX = 9;

	public enum Etat {

		EN_ATTENTE {

			public boolean ajouterJoueurBleu(Partie partie, Joueur joueur) {

				if (partie.getJoueurRouge().getNom().equals(joueur.getNom()))
					throw new PartieException("Le joueur rouge est déjà nommé "
							+ partie.getJoueurRouge().getNom());
				partie.joueurBleu = joueur;
				partie.etat = Etat.EN_PLACEMENT;
				return true;

			}

		},

		EN_PLACEMENT {

			public boolean placerVoiture(Partie partie, Joueur joueur, Voiture v) {
				if (!partie.contientJoueur(joueur))
					throw new PartieException(
							"Le joueur n'appartient pas à la partie.");
				if (v.getLigne() < 0 || v.getLigne() > Partie.LIGNE_INDICE_MAX)
					throw new ArgumentInvalideException(
							"Indice de la ligne incorrect : " + v.getLigne());
				if (v.getColonne() < 0
						|| v.getColonne() > Partie.COLONNE_INDICE_MAX)
					throw new ArgumentInvalideException(
							"Indice de la colonne incorrect : "
									+ v.getColonne());
				if (v.getDirection() == Voiture.DIRECTION_VERTICAL
						&& (v.getLigne() + v.getNbrPneus()) - 1 > LIGNE_INDICE_MAX)
					throw new ArgumentInvalideException(
							"Indice de la ligne incorrect : " + v.getLigne());
				if (v.getDirection() == Voiture.DIRECTION_HORIZONTAL
						&& (v.getColonne() + v.getNbrPneus()) - 1 > COLONNE_INDICE_MAX)
					throw new ArgumentInvalideException(
							"Indice de la colonne incorrect : "
									+ v.getColonne());
				// La voiture à placer ne doit pas occuper de case utilisée par
				// une autre voiture
				for (Voiture autre : joueur.getVoitures())
					if (autre.occupePlace(v))
						throw new PartieException(
								"L'emplacement [ "
										+ v.getLigne()
										+ ":"
										+ v.getColonne()
										+ (v.getDirection() == Voiture.DIRECTION_HORIZONTAL ? "(Horizontal)"
												: "(Vertical)")
										+ "] est déjà occupé.");
				joueur.ajouterVoiture(v);

				// On lance la partie si toutes les voitures ont été placées
				if (partie.getJoueurRouge().getVoitures().size() == 5
						&& partie.getJoueurBleu().getVoitures().size() == 5) {
					partie.etat = Etat.EN_COURS;
					partie.tour = partie.joueurRouge;
					partie.dateDebut = new GregorianCalendar().getTimeInMillis();
				}

				return false;
			}

		},

		EN_COURS {

			public TentativeCrevaison tenterCrevaison(Partie partie,
					Joueur joueur, int ligne, int colonne) {

				if (!partie.contientJoueur(joueur))
					throw new PartieException(
							"Le joueur n'appartient pas à la partie.");

				if (ligne < 0 || ligne > Partie.LIGNE_INDICE_MAX)
					throw new ArgumentInvalideException(
							"Indice de la ligne incorrect : " + ligne);

				if (colonne < 0 || colonne > Partie.COLONNE_INDICE_MAX)
					throw new ArgumentInvalideException(
							"Indice de la colonne incorrect : " + colonne);

				for (TentativeCrevaison tc : joueur.getTentativesCrevaison())
					if (tc.getLigne() == ligne && tc.getColonne() == colonne)
						throw new PartieException(
								"Tentative déjà effectuée en [" + ligne + ":"
										+ colonne + "].");

				if (!joueur.equals(partie.getTour()))
					throw new PartieException(
							"Ce n'est pas à votre tour de jouer.");

				TentativeCrevaison tentative = new TentativeCrevaison(ligne,
						colonne);
				tentative
						.setEtatTentative(TentativeCrevaison.TENTATIVE_ETAT_RATE);
				
				/*
				 * TEST
				 * 
				 */
				List<Voiture> listeV;
				if(joueur.getIdJoueur()==partie.getJoueurRouge().getIdJoueur()){
					listeV=partie.getJoueurBleu().getVoitures();
				}else{
					listeV=partie.getJoueurRouge().getVoitures();
				}
				for (Voiture v : listeV ){
					if (v.occupeCellule(ligne, colonne)){
						tentative = v.creverPneu(tentative);
					}
				}
				joueur.ajouterTentativeCrevaison(tentative);
				
				Joueur j = (joueur == partie.joueurRouge ? partie.joueurBleu : partie.joueurRouge);
				boolean terminee = true;
				for(Voiture v : j.getVoitures()) {
					if(!v.estCrevée()) {
						terminee = false;
						break;
					}
				}
				
				if(terminee)
					partie.etat = Etat.TERMINEE;	
				else
					partie.tourSuivant();

				return tentative;
			}

		},

		TERMINEE {

			public Joueur getVainqueur(Partie partie) {

				if (partie.getJoueurRouge().getNbVoituresRestantes() == 0)
					return partie.getJoueurBleu();
				return partie.getJoueurRouge();

			}

		};

		public boolean ajouterJoueurBleu(Partie partie, Joueur joueur) {
			throw new PartieException("Etat inccorect");
		}

		public boolean placerVoiture(Partie partie, Joueur joueur,
				Voiture voiture) {
			throw new PartieException("Etat inccorect");
		}

		public TentativeCrevaison tenterCrevaison(Partie partie, Joueur joueur,
				int ligne, int colonne) {
			throw new PartieException("Etat inccorect");
		}

		public Joueur getVainqueur(Partie partie) {
			throw new PartieException("Etat inccorect");
		}

	}

	@Id
	@GeneratedValue
	private int id;
	@Column(nullable = false, unique = true)
	private String nom;
	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "idJoueurRouge")
	private Joueur joueurRouge;
	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "idJoueurBleu")
	private Joueur joueurBleu;
	@Column
	private long dateDebut;
	@Enumerated
	private Partie.Etat etat = Partie.Etat.EN_ATTENTE;
	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "idJoueurTour")
	private Joueur tour;

	public Partie() {}

	public Partie(Joueur joueurRouge, String nom) {
		super();
		this.joueurRouge = joueurRouge;
		this.nom = nom;
		this.tour = joueurRouge;
		Calendar date = new GregorianCalendar();
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

	public boolean contientJoueur(Joueur joueur) {
		if (joueur == null)
			return false;
		if (joueurRouge.equals(joueur))
			return true;
		if (joueurBleu.equals(joueur))
			return true;
		return false;
	}

	private Joueur getTour() {
		return tour;
	}

	public Partie.Etat getEtat() {
		return etat;
	}

	private void tourSuivant() {
		if (tour == joueurRouge)
			tour = joueurBleu;
		else
			tour = joueurRouge;
	}

	public void ajouterJoueurBleu(Joueur joueurBleu) {
		this.etat.ajouterJoueurBleu(this, joueurBleu);
	}

	public void placerVoiture(Joueur joueur, Voiture voiture) {
		this.etat.placerVoiture(this, joueur, voiture);
	}

	public TentativeCrevaison tenterCrevaison(Joueur joueur, int ligne,
			int colonne) {
		return this.etat.tenterCrevaison(this, joueur, ligne, colonne);
	}

	public Joueur getVainqueur() {
		return this.etat.getVainqueur(this);
	}

	public long getDateDebut() {
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