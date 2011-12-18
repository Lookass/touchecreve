package be.ipl.tc.daoimpl;

import javax.ejb.Stateless;

import be.ipl.tc.dao.VoitureDao;
import be.ipl.tc.domaine.Voiture;

@Stateless
public class VoitureDaoImpl extends DaoImpl<Integer, Voiture> implements
		VoitureDao {

}
