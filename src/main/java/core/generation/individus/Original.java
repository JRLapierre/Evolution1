package core.generation.individus;

import core.generation.individus.cerveau.Cerveau;
import core.generation.individus.mutations.Mutation;
import outils.Aleatoire;

/**
 * cette classe existe pour initialiser l'individu original.
 * elle ne contient qu'un constructeur et initialise quelques donnees
 * @author jrl
 *
 */
public class Original extends Individu{

	/**
	 * constructeur pour l'individu initial de la simulation, ainsi
	 * que un peu d'initialisation
	 * @param cerveau
	 * @param graineAleatoire
	 */
	public Original(Cerveau cerveau, int graineAleatoire, Mutation mutation) {
		super();
		this.cerveau=cerveau;
		this.generation=0;
		Individu.mutation=mutation;
		Individu.alea=new Aleatoire(graineAleatoire);
	}
	
	/**
	 * fonction toStringJson pour les individus originaux
	 */
	@Override
	public String toStringJson() {
		return "{\"individu" + this.getId() + "\":{"
		+ "\"type\":\"original\","
		+ super.toStringJson();
	}

}
