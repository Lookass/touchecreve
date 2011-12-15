package be.ipl.tc.daoimpl;

import javax.ejb.Stateless;

import be.ipl.tc.dao.JoueurDao;
import be.ipl.tc.domaine.Joueur;

@Stateless
public class JoueurDaoImpl extends DaoImpl<Integer,Joueur> implements JoueurDao{


}
