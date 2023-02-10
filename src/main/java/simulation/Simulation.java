package simulation;

/**
 * cette classe abstraite represente une simulation.
 * @author jrl
 *
 */
public abstract class Simulation {

	/**
	 * demande a l'utilisateur si il est sur de ses choix
	 * @return true si l'utilisateur valide
	 */
	public abstract boolean choix();
	
	/**
	 * lance la simulation
	 */
	public abstract void run();
	
}
