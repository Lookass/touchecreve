package be.ipl.tc.dao;

import java.util.List;



public interface Dao<K,E> {
	E rechercher(K id);
	E enregistrer(E entité);
	E mettreAJour(E entité);
	E recharger(K id);
	void supprimer(K id);
	List<E> lister();
}