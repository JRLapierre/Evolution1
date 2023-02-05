package simulation.generation.individus;

import java.nio.ByteBuffer;

import simulation.generation.individus.cerveau.Cerveau;

/**
 * classe qui représente un clone avec quelques mutations
 * @author jrl
 *
 */
public class CloneMute extends Individu{

	//------------------------------------------------------------------------------
	//variables
	
	/**
	 * l'id du père de l'individu, de la génération précédente
	 */
	private int idParent;

	//-------------------------------------------------------------------------------
	//constructeur
	
	/**
	 * constructeur pour un clone mute
	 * @param parent le parent de l'individu
	 */
	public CloneMute(Individu parent) {
		super();
		this.idParent = parent.getId();
		this.generation=parent.generation+1;
		this.cerveau=mutation.evolution(parent.cerveau.replique());
	}
	
	
	/**
	 * constructeur pour un cloneMute a partir d'un ByteBuffer binaire
	 * @param bb le ByteBuffer contenant les infos
	 */
	public CloneMute(ByteBuffer bb) {
		this.id=bb.getInt();
		this.idParent=bb.getInt();
		this.generation=bb.getInt();
		this.score=bb.getFloat();
		this.cerveau=new Cerveau(bb);
	}
	
	//-------------------------------------------------------------------------------
	//fonctions d'affichage
	
	/**
	 * toString decrivant un clone mute
	 */
	@Override
	public String toStringJson() {
		return "{\"individu" + this.getId() + "\":{"
		+ "\"type\":\"CloneMute\","
		+ "\"parent\":" + idParent + ","
		+ super.toStringJson();
	}

	@Override
	public byte[] toByte() {
		ByteBuffer bb=ByteBuffer.allocate(toByteLongueur());
		bb.put((byte) 2);//2 pour cloneMute
		bb.putInt(id);
		bb.putInt(idParent);
		bb.putInt(generation);
		bb.putFloat(score);
		bb.put(cerveau.toByte());
		return bb.array();
	}

	@Override
	public int toByteLongueur() {
		return 17 + cerveau.toByteLongueur();
	}
	
	
}
