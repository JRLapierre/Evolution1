package core.generation.individus;

import java.nio.ByteBuffer;

import core.generation.individus.cerveau.Cerveau;
import core.generation.individus.mutations.Mutation;
import outils.Aleatoire;

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
	
	
	/**
	 * constructeur pour creer un individu sauvegarde a partir de binaire
	 * @param bb
	 */
	public Sauvegarde(ByteBuffer bb) {
		this.id=bb.getInt();
		this.generation=bb.getInt();
		this.score=bb.getFloat();
		this.cerveau=new Cerveau(bb);
	}
	
	
	@Override
	public String toStringJson() {
		return "{\"individu" + this.getId() + "\":{"
		+ "\"type\":\"sauvegarde\","
		+ super.toStringJson();
	}


	@Override
	public byte[] toByte() {
		ByteBuffer bb=ByteBuffer.allocate(toByteLongueur());
		bb.put((byte) 4);//4 pour sauvegarde
		bb.putInt(this.id);
		bb.putInt(generation);
		bb.putFloat(score);
		bb.put(cerveau.toByte());
		return bb.array();
	}


	@Override
	public int toByteLongueur() {
		return 13 + cerveau.toByteLongueur();
	}
	
}
