package core.individus;

import core.cerveau.Cerveau;
import core.trace.GenereTrace;
import outils.aleatoire.Aleatoire;

/**
 * cette classe représente un individu.
 * l'invidu peut etre de quatre types : 
 * - original, créé depuis la racine pour servir de base à tout le reste
 * - les clones parfait, copies parfaites de leur parents
 * - les clones mutés, copies avec quelques mutations de leur parents
 * - les enfants de reproduction sexuée, qui mélange les carractéristiques
 * de leurs parents et qui ont en plus quelques mutations
 * 
 * 
 * @author jrl
 *
 */
public abstract class Individu extends GenereTrace{	
	
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
	 * le score pour evaluer la
	 */
	private float score=0;
	
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
	
	//----------------------------------------------------------------------
	//fonctions lies au fonctionnement actif
	
	/**
	 * met à jour le score pour l'evaluation et la selection
	 * @param score
	 */
	public void updateScore(float score) {
		this.score +=score;
	}
	
	//-------------------------------------------------------------------------------
	//fonction d'affichage
	
	/**
	 * fonction toStringJson incomplete qui reclamme une sous classe
	 * pour être complété
	 */
	public String toStringJson() {
		//je laisse le début aux sous classes
		String str="";
		str += "\"id\":" + id +",";
		str += "\"generation\":" + generation + ",";
		str += "\"score\":" + score + ",";
		str += "\"cerveau\":";
		str += cerveau.toStringJson();
		//je ferme cette partie la pour l'instant
		str += "}}";
		return str;
	}

	
	
	
	
}
