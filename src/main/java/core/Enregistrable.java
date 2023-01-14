package core;

/**
 * cette interface existe pour les objets qui vont figurer dans l'enregistrement.
 * @author jrl
 *
 */
public interface Enregistrable {
	
	/**
	 * produit un string correspondant au format Json des objet
	 * @return
	 */
	public String toStringJson();
	
	
}
