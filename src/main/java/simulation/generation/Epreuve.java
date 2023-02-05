package simulation.generation;

import simulation.generation.individus.Individu;

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
	 * @param population la liste d'individus
	 */
	void epreuve(Individu[] population);

}
