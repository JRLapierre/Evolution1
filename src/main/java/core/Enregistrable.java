package core;

/**
 * cette interface existe pour les objets qui vont figurer dans l'enregistrement.
 * @author jrl
 *
 */
public interface Enregistrable {
	
	/**
	 * produit un string correspondant au format Json des objet
	 * @return un string au format json
	 */
	public String toStringJson();
	
	
	
	/**
	 * cree une representation de l'objet en binaire
	 * format d'enregistrement : 
	 * 
	 * pour les individus : 
	 * id : int
	 * type d'individu : byte
	 * parent (0, 1 ou 2 selon le type) : int
	 * generation : int
	 * score : float
	 * dans le cerveau :
	 * nombre d'entrees : short
	 * nombre d'interne : short
	 * nombre de sortie : short 
	 * type de neurone (input 1, interne 2, output 3) : byte
	 * 	numero de neurone x fois : short
	 * 	nombre de connexions partant de cette neurone : short
	 * 		connexion x fois : 
	 * 			id : int
	 * 			facteur : float
	 * 			cible : 
	 * 				type : byte
	 * 				numero : short 
	 * 
	 * Pour les mutations :
	 * graine : int
	 * tauxCreation : byte
	 * tauxSuppression : byte
	 * tauxMutationFacteur : byte
	 * maxChangementFacteur : float
	 * tauxMutationNeurone : byte
	 * 
	 * @return un tableau de byte correspondant a l'objet
	 */
	public byte[] toByte();
	
	/**
	 * une fonction qui dit la longueur de toByte
	 * @return la longueur
	 */
	public int toByteLongueur();
}
