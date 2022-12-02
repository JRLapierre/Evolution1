package core.generation;

import core.generation.individus.Individu;

/**
 * cette interface fonctionnelle sert à mettre a l'epreuve les individus
 * dans la simulation afin de determiner leur score.
 * 
 * @author jrl
 *
 */
@FunctionalInterface
public interface Epreuve {
	
	/**
	 * l'epreuve que chaque individu devra affronter.
	 * @param individu l'individu a mettre a l'epreuve
	 */
	void epreuve(Individu individu);

}
