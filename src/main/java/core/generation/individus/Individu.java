package core.generation.individus;

import core.generation.individus.cerveau.Cerveau;
import core.generation.individus.mutations.Mutation;
import outils.Aleatoire;

/**
 * cette classe repr?sente un individu.
 * l'invidu peut etre de quatre types : 
 * - original, cr?? depuis la racine pour servir de base ? tout le reste
 * - les clones parfait, copies parfaites de leur parents
 * - les clones mut?s, copies avec quelques mutations de leur parents
 * - les enfants de reproduction sexu?e, qui m?lange les carract?ristiques
 * de leurs parents et qui ont en plus quelques mutations
 * 
 * 
 * @author jrl
 *
 */
public abstract class Individu {	
	
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
	private int id;
	
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
	 * le score pour evaluer la
	 */
	private float score=0;
	
	//-------------------------------------------------------------------------------
	//constructeurs
	
	/**
	 * constructeur g?n?ral pour un individu, sans pr?cisions
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
	 * met ? jour le score pour l'evaluation et la selection
	 * @param score
	 */
	public void updateScore(float score) {
		this.score +=score;
	}
	
	public static void setMutation(Mutation mutation) {
		Individu.mutation=mutation;
	}
	
	//-------------------------------------------------------------------------------
	//fonction d'affichage
	
	/**
	 * fonction toStringJson incomplete qui reclamme une sous classe
	 * pour ?tre compl?t?
	 */
	public String toStringJson() {
		//je laisse le d?but aux sous classes
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
