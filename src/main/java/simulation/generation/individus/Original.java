package simulation.generation.individus;

import java.nio.ByteBuffer;

import simulation.generation.individus.cerveau.Cerveau;
import simulation.generation.individus.mutations.Mutation;

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
	 * @param cerveau le cerveau de l'individu
	 * @param mutation l'objet mutation pour l'initialiser
	 */
	public Original(Cerveau cerveau, Mutation mutation) {
		super();
		this.cerveau=cerveau;
		this.generation=0;
		Individu.mutation=mutation;
	}
	
	/**
	 * constructeur pour un individu originan a partir de bin
	 * @param bb le ByteBuffer duquel on extrait les infos
	 */
	public Original(ByteBuffer bb) {
		this.id=bb.getInt();
		this.generation=bb.getInt();
		this.score=bb.getFloat();
		this.cerveau=new Cerveau(bb);
		updateId();
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

	@Override
	public byte[] toByte() {
		ByteBuffer bb=ByteBuffer.allocate(toByteLongueur());
		bb.put((byte) 0);//0 pour original
		bb.putInt(this.id);
		bb.putInt(generation);
		bb.putFloat(score);
		bb.put(cerveau.toByte());
		return bb.array();
	}

	@Override
	public int toByteLongueur() {
		return 13 + this.cerveau.toByteLongueur();
	}

}
