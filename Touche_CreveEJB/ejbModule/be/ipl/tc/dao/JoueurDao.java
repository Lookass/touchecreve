package be.ipl.tc.dao;

import javax.ejb.Local;
import be.ipl.tc.domaine.Joueur;

@Local
public interface JoueurDao extends Dao<Integer,Joueur>{

}
