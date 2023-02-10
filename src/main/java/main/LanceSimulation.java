package main;

import simulation.Simulation;
import simulation.SimulationEnregistree;

/**
 * cette classe permet de lancer une simulation.
 * @author jrl
 *
 */
public class LanceSimulation {

	/**
	 * la simulation.
	 * On choisis si on veut une simulation partant de 0
	 * (SimulationInitiale) ou une simulation partant d'
	 * enregistrements (SimulationEnregistree)
	 */
	static Simulation simulation=new SimulationEnregistree();
	
	/**
	 * fonction main qui permet de lancer le programme.
	 * @param args rien a mettre
	 */
	public static void main(String[] args) {
		if(!simulation.choix()) return; //arrete le programme si le choix n'est pas fait
		simulation.run();
	}

}
