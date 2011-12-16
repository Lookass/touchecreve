package be.ipl.tc.dao;

import javax.ejb.Local;

import be.ipl.tc.domaine.Partie;

@Local
public interface PartieDao extends Dao<Integer,Partie>{
	Partie rechercherPartieNonTerminée(String nom);
}
