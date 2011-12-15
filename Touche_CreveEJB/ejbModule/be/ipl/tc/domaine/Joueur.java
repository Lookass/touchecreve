package be.ipl.tc.domaine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import be.ipl.tc.exceptions.ArgumentInvalideException;
import be.ipl.tc.util.Util;

@Entity
@Table(name = "JOUEURS", schema = "TC")
public class Joueur implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private int idJoueur;
	@Column(nullable = false)
	private String nom;
	@OneToMany
	@JoinColumn(name="idJoueur")
	private List<TentativeCrevaison> tentativesCrevaison = new ArrayList<TentativeCrevaison>();
	@OneToMany
	@JoinColumn(name = "idJoueur")
	private List<Voiture> voitures = new ArrayList<Voiture>();
	
	public Joueur(){
		
	}
	
	public Joueur(String nom) {
		this.nom = nom;
	}

	public int getIdJoueur() {
		return idJoueur;
	}

	public String getNom() {
		return nom;
	}
	
	public void ajouterTentativeCrevaison(TentativeCrevaison tentative) throws ArgumentInvalideException {
		Util.checkObject(tentative);
		tentativesCrevaison.add(tentative);
	}
	
	public void ajouterVoiture(Voiture v) throws ArgumentInvalideException {
		Util.checkObject(v);
		voitures.add(v);
	}

	public List<TentativeCrevaison> getTentativesCrevaison() {
		return tentativesCrevaison;
	}
	
	public int getNbVoituresRestantes() {
		int nbVoituresRestantes = 0;
		for(Voiture v : voitures) {
			if(v.getNbrPneusRestant() > 0)
				++nbVoituresRestantes;
		}
		return nbVoituresRestantes;
	}
	public List<Voiture> getVoitures() {
		return Collections.unmodifiableList(voitures);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idJoueur;
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
		Joueur other = (Joueur) obj;
		if (idJoueur != other.idJoueur)
			return false;
		return true;
	}
}
