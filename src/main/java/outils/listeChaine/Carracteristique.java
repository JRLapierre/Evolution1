package outils.listeChaine;

/**
 * Interface fonctionnelle pour isoler une carracteristique d'un element.
 * Par exemple, isoler le score d'un individu et trier selon cette
 * carracteristique.
 * @author jrl
 *
 * @param <T>
 */
@FunctionalInterface
public interface Carracteristique<T> {

	/**
	 * 
	 * @param elt l'element dont on veut isoler une partie
	 * @return un float qui va servir
	 */
	float carracteristique(T elt);
	
}
