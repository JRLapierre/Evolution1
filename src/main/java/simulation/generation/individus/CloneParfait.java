package simulation.generation.individus;

import java.nio.ByteBuffer;

import simulation.generation.individus.cerveau.Cerveau;

/**
 * classe représentant un individu issu du clonage parfait
 * @author jrl
 *
 */
public class CloneParfait extends Individu{

	//------------------------------------------------------------------------------
	//variables
	
	/**
	 * l'id du père de l'individu, de la génération précédente
	 */
	private int idParent;

	//-------------------------------------------------------------------------------
	//constructeur
	
	/**
	 * constructeur pour un clone parfait,au cerveau identique à ses parents
	 * @param parent le parent de l'individu
	 */
	public CloneParfait(Individu parent) {
		super();
		this.idParent = parent.getId();
		this.generation=parent.generation+1;
		this.cerveau=parent.cerveau.replique();
	}
	
	/**
	 * constructeur pour un clone parfait a partir de binaire
	 * @param bb le ByteBuffer contenant les informations
	 */
	public CloneParfait(ByteBuffer bb) {
		this.id=bb.getInt();
		this.idParent=bb.getInt();
		this.generation=bb.getInt();
		this.score=bb.getFloat();
		this.cerveau=new Cerveau(bb);
		updateId();
	}
	
	//-------------------------------------------------------------------------------
	//fonctions d'affichage
	
	/**
	 * toStringJson decrivant un clone parfait
	 */
	@Override
	public String toStringJson() {
		return "{\"individu" + this.getId() + "\":{"
		+ "\"type\":\"CloneParfait\","
		+ "\"parent\":" + idParent + ","
		+ super.toStringJson();
	}

	@Override
	public byte[] toByte() {
		ByteBuffer bb=ByteBuffer.allocate(toByteLongueur());
		bb.put((byte) 1);//1 pour cloneParfait
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
