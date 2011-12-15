package be.ipl.tc.daoimpl;

import javax.ejb.Stateless;

import be.ipl.tc.dao.JoueurDao;
import be.ipl.tc.dao.PartieDao;
import be.ipl.tc.domaine.Joueur;
import be.ipl.tc.domaine.Partie;

@Stateless
public class PartieDaoImpl extends DaoImpl<Integer, Partie> implements
		PartieDao {

}
