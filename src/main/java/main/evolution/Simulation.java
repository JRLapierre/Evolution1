package main.evolution;

import core.generation.Generation;
import core.generation.Type1;

/**
 * cette classe est minimaliste et sert � lancer la simulation.
 * La seule chose � faire dans cette classe est de d�cider du nombre de generations.
 * @author jrl
 *
 */
public class Simulation {

	/**
	 * le nombre de simulation � simuler.
	 */
	private static int nbGenerations=100;
	
	/**
	 * fonction main qui contient une boucle de nbGeneration iteration.
	 * A chaque tour de boucle, on genere la generation suivante et on enregistre.
	 * 
	 * Ici, il s'agit d'une generation Type1. Pour changer les regles de la simulation, 
	 * il suffira de modifier cela.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		Generation g=new Type1();
		for(int i=0; i<nbGenerations; i++) {
			g.evaluation();
			g.enregistre();
			g=new Type1(g);
		}
		
	}
}
