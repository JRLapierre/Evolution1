package core.generation.individus;

import java.nio.ByteBuffer;

import core.Enregistrable;
import core.generation.individus.cerveau.Cerveau;
import core.generation.individus.mutations.Mutation;
import outils.Aleatoire;

/**
 * cette classe représente un individu.
 * l'invidu peut etre de cinq types : 
 * - original, créé depuis la racine pour servir de base à tout le reste
 * - les clones parfait, copies parfaites de leur parents
 * - les clones mutés, copies avec quelques mutations de leur parents
 * - les enfants de reproduction sexuée, qui mélange les carractéristiques
 * de leurs parents et qui ont en plus quelques mutations
 * - les sauvegarde, qui sont des individus simplifies pour reprendre depuis une sauvegarde.
 * 
 * 
 * @author jrl
 *
 */
public abstract class Individu implements Enregistrable {
	
	//-------------------------------------------------------------------------------
	//variables
	
	/**
	 * le cerveau de l'individu
	 */
	protected Cerveau cerveau;
	
	/**
	 * le nombre de generations precedant l'individu
	 */
	protected int generation;
	
	/**
	 * l'identifiant de l'individu
	 */
	protected int id;
	
	/**
	 * le nombre d'individus
	 */
	private static int nbIndividus=0;
	
	/**
	 * le generateur de nombres aleatoires pour la reproduction sexuee
	 */
	protected static Aleatoire alea;
	
	/**
	 * le generateurs de mutation
	 */
	protected static Mutation mutation;
	
	/**
	 * le score pour evaluer la performance
	 */
	protected float score=0;
	
	//-------------------------------------------------------------------------------
	//constructeurs
	
	/**
	 * constructeur général pour un individu, sans précisions
	 * 
	 */
	protected Individu() {
		this.id=nbIndividus;
		nbIndividus++;
	}
	
	/**
	 * constructeur pour recreer des individus depuis une sauvegarde.
	 * @param sub le string qui decrit un individu
	 */
	protected Individu(String sub) {
		this.id=Integer.parseInt(sub.substring(
				sub.indexOf("\"id\":")+5, 
				sub.indexOf(",\"generation\"")));
		this.generation=Integer.parseInt(sub.substring(
				sub.indexOf("\"generation\":")+13, 
				sub.indexOf(",\"score\"")));
		this.score=Float.parseFloat(sub.substring(
				sub.indexOf("\"score\":")+8, 
				sub.indexOf(",\"cerveau\"")));
		this.cerveau=new Cerveau(sub);
		if(this.id+1>nbIndividus) {
			Individu.nbIndividus=id+1;
		}
	}
	
	//----------------------------------------------------------------------
	//"constructeur"
	
	//fonction statique qui genere un individu
	public static Individu regenereIndividu(ByteBuffer bb) {
		byte type=bb.get();
		switch(type) {
		case(0):
			return new Original(bb);
		case(1):
			return new CloneParfait(bb);
		case(2):
			return new CloneMute(bb);
		case(3):
			return new EnfantSexe(bb);
		case(4):
			return new Sauvegarde(bb);
		default:
			System.err.println("type inconu");
			return null;
		}
	}
	
	//----------------------------------------------------------------------
	//getteurs
	
	/**
	 * getteur pour l'id
	 * @return
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * getteur pour le score
	 * @return
	 */
	public float getScore() {
		return score;
	}
	
	/**
	 * getteur pour le cerveau
	 * @return
	 */
	public Cerveau getCerveau() {
		return cerveau;
	}
	
	/**
	 * getteur pour la generation de l'individu
	 * @return
	 */
	public int getGeneration() {
		return generation;
	}
	
	/**
	 * faux getteur qui renvoie le toStringJson de mutation
	 * @return mutation.toStringJson()
	 */
	public static String getMutationToStringJson() {
		return mutation.toStringJson();
	}
	
	//----------------------------------------------------------------------
	//fonctions lies au fonctionnement actif
	
	/**
	 * met à jour le score pour l'evaluation et la selection
	 * @param score
	 */
	public void updateScore(float score) {
		this.score +=score;
	}
	
	public static void setMutation(Mutation mutation) {
		Individu.mutation=mutation;
	}
	
	//-------------------------------------------------------------------------------
	//fonction de l'interface Enregistrable
	
	/**
	 * fonction toStringJson incomplete qui reclamme une sous classe
	 * pour être complété
	 */
	public String toStringJson() {
		//je laisse le début aux sous classes
		return ""
		+ "\"id\":" + id +","
		+ "\"generation\":" + generation + ","
		+ "\"score\":" + score + ","
		+ "\"cerveau\":"
		+ cerveau.toStringJson()
		//je ferme cette partie la pour l'instant
		+ "}}";
	}
	
	
}
