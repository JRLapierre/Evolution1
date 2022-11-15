package outils.listeChaine;

/**
 * interface fonctionnelle permetant de cr�er des expressions
 * lambda de comparaison, notamment pour trier une listeChaine d'elements
 * selon les crit�res voulus.
 * @author jrl
 *
 * @param <T>
 */
@FunctionalInterface
public interface Compare<T> {
	
	/**
	 * 
	 * @param premier
	 * @param deuxieme
	 * @return true si premier est plus petit, false sinon
	 */
	Boolean compare(T premier, T deuxieme);

}
