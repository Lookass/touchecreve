package be.ipl.tc.domaine;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import be.ipl.tc.exceptions.ArgumentInvalideException;
import be.ipl.tc.exceptions.PartieException;

@Entity
@Table(name = "VOITURES", schema = "TC")
public class Voiture implements Serializable,Cloneable {
	@Id
	@GeneratedValue
	private int id;
	private String nom;
	private int ligne;
	private int colonne;
	private int direction;
	private int nbrPneus;
	private int nbrPneusRestant;

	public static int DIRECTION_HORIZONTAL = 0;
	public static int DIRECTION_VERTICAL = 1;
	
	public Voiture(){
		
	}
	public Voiture(String nom, int nbrPneus) {
		super();
		this.nom = nom;
		this.nbrPneus = nbrPneus;
		this.nbrPneusRestant = nbrPneus;
	}

	public int getLigne() {
		return ligne;
	}

	public void setLigne(int ligne) {
		this.ligne = ligne;
	}

	public int getColonne() {
		return colonne;
	}

	public void setColonne(int colonne) {
		this.colonne = colonne;
	}

	public int getNbrPneusRestant() {
		return nbrPneusRestant;
	}
	
	public void creverPneu(TentativeCrevaison tc) {
		if(nbrPneusRestant == 0)
			throw new PartieException("Tous les pneus ont déjà été crevés");
		--nbrPneusRestant;
		if(nbrPneusRestant == 0)
			tc.setEtatTentative(TentativeCrevaison.TENTATIVE_ETAT_CREVE);
		else
			tc.setEtatTentative(TentativeCrevaison.TENTATIVE_ETAT_TOUCHE);
	}

	public int getId() {
		return id;
	}
	
	public String getNom() {
		return nom;
	}

	public int getDirection() {
		return direction;
	}
	
	public void setDirection(int direction) throws ArgumentInvalideException {
		if(direction != DIRECTION_HORIZONTAL && direction != DIRECTION_VERTICAL)
			throw new ArgumentInvalideException("Direction invalide");
		this.direction = direction;
	}

	public int getNbrPneus() {
		return nbrPneus;
	}
	
	public boolean occupePlace(Voiture autre) {
		
		if(autre.direction == DIRECTION_HORIZONTAL) {
			
			if(this.direction == DIRECTION_HORIZONTAL) {
				if(this.ligne != autre.ligne)
					return false;
				if(this.colonne < autre.colonne)
					if(this.colonne + this.nbrPneus < autre.colonne)
					return false;
				else
					if(autre.colonne + autre.nbrPneus < this.colonne)
						return false;
				return true;
			} else {
				if(this.ligne > autre.ligne)
					return false;
				if(this.colonne < autre.colonne && this.colonne > autre.colonne + autre.nbrPneus)
					return false;
				if(this.ligne + this.nbrPneus < autre.ligne)
					return false;
				return true;
			}
		
		} else { // autre.direction == DIRECTION_VERTICAL
			
			if(this.direction == DIRECTION_HORIZONTAL) {
				if(autre.ligne > this.ligne)
					return false;
				if(autre.colonne < this.colonne && autre.colonne > this.colonne + this.nbrPneus)
					return false;
				if(autre.ligne + autre.nbrPneus < this.ligne)
					return false;
				return true;
			} else {
				if(autre.ligne != this.ligne)
					return false;
				if(autre.colonne < this.colonne)
					if(autre.colonne + autre.nbrPneus < this.colonne)
					return false;
				else
					if(this.colonne + this.nbrPneus < autre.colonne)
						return false;
				return true;
			}
			
		}
	}
	
    /*
	public boolean occupePlace(Voiture autre) {
		
		for(int col = autre.colonne; col < autre.colonne + autre.nbrPneus; ++col)
			if(occupeCellule(autre.ligne, col))
				return true;
			return false;
		} else {
			for(int row = autre.ligne; row < autre.ligne + autre.nbrPneus; ++row)
				if(occupeCellule(row, autre.colonne))
					return true;
			return false;
		}
			
	}*/

	public boolean occupeCellule(int ligne, int colonne) {

		if (direction == DIRECTION_HORIZONTAL) {

			if (ligne != this.ligne)
				return false;
			if (colonne < this.colonne
					|| colonne > this.colonne + this.nbrPneus)
				return false;
			return true;

		} else {

			if (colonne != this.colonne)
				return false;
			if (ligne < this.ligne || ligne > this.ligne + this.nbrPneus)
				return false;
			return true;

		}

	}
	
	public Object clone() {
		Object clone;
		try {
			clone = super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
		Voiture v = (Voiture) clone;
		v.nom = this.nom;
		return v;
	}
}
