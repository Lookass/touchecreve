package be.ipl.tc.daoimpl;

import javax.ejb.Stateless;

import be.ipl.tc.dao.JoueurDao;
import be.ipl.tc.dao.TentativeCrevaisonDao;
import be.ipl.tc.domaine.Joueur;
import be.ipl.tc.domaine.TentativeCrevaison;

@Stateless
public class TentativeCrevaisonDaoImpl extends DaoImpl<Integer, TentativeCrevaison>
		implements TentativeCrevaisonDao {

}
