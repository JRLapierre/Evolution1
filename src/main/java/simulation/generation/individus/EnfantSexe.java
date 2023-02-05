package simulation.generation.individus;

import java.nio.ByteBuffer;

import simulation.generation.individus.cerveau.Cerveau;

/**
 * classe qui représente un individu issu de reproduction sexuée
 * @author jrl
 *
 */
public class EnfantSexe extends Individu{

	//------------------------------------------------------------------------------
	//variables
	
	/**
	 * l'id du père de l'individu, de la génération précédente
	 */
	private int idParent1;
	
	/**
	 * l'id de la mere de l'individu, de la generation precedente
	 */
	private int idParent2;

	//-------------------------------------------------------------------------------
	//constructeur
	
	/**
	 * constructeur pour un enfant issu de reproduciton sexuee, 
	 * avec des mutations par rapport a ses parent
	 * @param parent1 un des parents de l'individu
	 * @param parent2 l'autre parent de l'individu
	 */
	public EnfantSexe(Individu parent1, Individu parent2) {
		super();
		this.idParent1 = parent1.getId();
		this.idParent2 = parent2.getId();
		this.generation=parent1.generation+1;
		this.cerveau=mutation.evolution(parent1.cerveau, parent2.cerveau);
	}
	
	
	/**
	 * constructeur pour un enfant a partir d'un fichier binaire
	 * @param bb le ByteBuffer contenant les infos
	 */
	public EnfantSexe(ByteBuffer bb) {
		this.id=bb.getInt();
		this.idParent1=bb.getInt();
		this.idParent2=bb.getInt();
		this.generation=bb.getInt();
		this.score=bb.getFloat();
		this.cerveau=new Cerveau(bb);
	}
	
	//-------------------------------------------------------------------------------
	//fonctions d'affichage
	
	/**
	 * toStringJson decrivant un enfant de repodution sexuée
	 */
	@Override
	public String toStringJson() {
		return "{\"individu" + this.getId() + "\":{"
		+ "\"type\":\"EnfantSexe\"" + ","
		+ "\"parent1\":" + idParent1 + ","
		+ "\"parent2\":" + idParent2 + ","
		+ super.toStringJson();
	}

	@Override
	public byte[] toByte() {
		ByteBuffer bb=ByteBuffer.allocate(toByteLongueur());
		bb.put((byte) 3);//3 pour cloneMute
		bb.putInt(id);
		bb.putInt(idParent1);
		bb.putInt(idParent2);
		bb.putInt(generation);
		bb.putFloat(score);
		bb.put(cerveau.toByte());
		return bb.array();
	}

	@Override
	public int toByteLongueur() {
		return 21 + cerveau.toByteLongueur();
	}
	
}
