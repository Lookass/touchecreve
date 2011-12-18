package be.ipl.tc.daoimpl;

import javax.ejb.Stateless;

import be.ipl.tc.dao.PartieDao;
import be.ipl.tc.domaine.Partie;

@Stateless
public class PartieDaoImpl extends DaoImpl<Integer, Partie> implements
		PartieDao {
	
	
	public Partie rechercherPartieNonTermin�e(String nom) {
		String query="select p from Partie p where p.nom like ?1";
		return recherche(query, nom);
	}

}
