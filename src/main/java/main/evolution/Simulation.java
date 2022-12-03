package main.evolution;

import java.io.IOException;

import core.generation.Epreuve;
import core.generation.Generation;
import core.generation.individus.Original;
import core.generation.individus.cerveau.Cerveau;
import core.generation.individus.mutations.Mutation;

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
	 * l'expression lambda de l'evaluation
	 */
	private static Epreuve epreuve=individu -> {for(int i=0; i<10; i++) {
		individu.getCerveau().getListeInput()[0].setPuissance(1);
		individu.getCerveau().next();
		float score=individu.getCerveau().getListeOutput()[0].getPuissance();
		individu.updateScore(5+score);
	}};
	
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
		
		Generation g=new Generation(new Original(new Cerveau(1,1,5), 46, new Mutation(46, 5, 5, 5, 2, 5)), 25, 50, 25, 50, epreuve, "1");
		g.enregistre();
		for(int i=1; i<nbGenerations; i++) {
			g.nextGen();
			g.enregistre();
		}
		try {
			Generation g2=new Generation("1", 100, epreuve);
			g2.nextGen();
			g2.enregistre();
			for(int i=1; i<10; i++) {
				g2.nextGen();
				g2.enregistre();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
