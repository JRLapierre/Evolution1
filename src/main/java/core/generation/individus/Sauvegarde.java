package core.generation.individus;

import core.generation.individus.mutations.Mutation;
import outils.aleatoire.Aleatoire;

/**
 * cette classe genere des individus a partir d'un ficher.
 * @author jrl
 *
 */
public class Sauvegarde extends Individu {

	/**
	 * booleen pour verifier si on a la graine.
	 * Par defaut, la valeur est a false. elle passe a true une fois qu'on a obtenu une graine.
	 */
	private static boolean hasSeed=false;
	
	/**
	 * constructeur 
	 * @param sub le string correspondant au contenu du ficher
	 * @param seed la graine d'aleatoire
	 */
	public Sauvegarde(String sub, int seed, Mutation mutation) {
		super(sub);
		if(!Sauvegarde.hasSeed) {
			alea=new Aleatoire(seed+10%2147483647);
			Sauvegarde.hasSeed=true;
			Individu.mutation=mutation;
		}
	}
	
	
	@Override
	public String toStringJson() {
		return "{\"individu" + this.getId() + "\":{"
		+ "\"type\":\"sauvegarde\","
		+ super.toStringJson();
	}
	
}
