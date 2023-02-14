package simulation.generation.individus;

import java.nio.ByteBuffer;

import simulation.generation.individus.cerveau.Cerveau;
import simulation.generation.individus.cerveau.Mutation;

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
	private static boolean aMutation=false;
	
	/**
	 * constructeur 
	 * @param sub le string correspondant au contenu du ficher
	 * @param mutation les mutations
	 */
	public Sauvegarde(String sub, Mutation mutation) {
		super(sub);
		if(!Sauvegarde.aMutation) {
			Sauvegarde.aMutation=true;
			Cerveau.mutation=mutation;
		}
	}
	
	
	/**
	 * constructeur pour creer un individu sauvegarde a partir de binaire
	 * @param bb le bytebuffer contenant les inforamtions
	 */
	public Sauvegarde(ByteBuffer bb) {
		this.id=bb.getInt();
		this.generation=bb.getInt();
		this.score=bb.getFloat();
		this.cerveau=new Cerveau(bb);
		updateId();
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
