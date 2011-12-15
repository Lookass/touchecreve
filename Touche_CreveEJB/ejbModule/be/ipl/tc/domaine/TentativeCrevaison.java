package be.ipl.tc.domaine;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Tentatives", schema = "TC")
public class TentativeCrevaison implements Serializable {
	@Id
	@GeneratedValue
	private int id;
	@Column(nullable=false)
	private int ligne;
	@Column(nullable=false)
	private int colonne;
	private int etatTentative;

	public TentativeCrevaison() {
		super();
	}

	public TentativeCrevaison(int ligne, int colonne) {
		super();
		this.ligne = ligne;
		this.colonne = colonne;
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

	public int getEtatTentative() {
		return etatTentative;
	}

	public void setEtatTentative(int etatTentative) {
		this.etatTentative = etatTentative;
	}

	public int getIdTentatives() {
		return id;
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
		TentativeCrevaison other = (TentativeCrevaison) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
