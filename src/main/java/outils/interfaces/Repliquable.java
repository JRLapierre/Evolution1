package outils.interfaces;

/**
 * Cette interface permet de reproduire une copie 
 * identique des objets.
 * Dans un certain sens, cette interface est un equivalent 
 * a Clonable.
 * 
 * @author jrl
 *
 */
public interface Repliquable {

	/**
	 * permet de creer une copie identique de l'objet.
	 * @return l'objet a repliquer
	 */
	public Repliquable replique();
	
}
