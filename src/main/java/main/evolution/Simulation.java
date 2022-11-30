package main.evolution;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import core.generation.FromSave;
import core.generation.Generation;
import core.generation.Type1;

/**
 * cette classe est minimaliste et sert à lancer la simulation.
 * La seule chose à faire dans cette classe est de décider du nombre de generations.
 * @author jrl
 *
 */
public class Simulation {

	/**
	 * le nombre de simulation à simuler.
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
		try {
			Generation g2=new FromSave("1", 100);
			g2=new Type1(g);
			for(int i=0; i<10; i++) {
				g.evaluation();
				g.enregistre();
				g=new Type1(g);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
