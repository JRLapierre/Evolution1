package core.generation.individus;

/**
 * classe qui repr�sente un clone avec quelques mutations
 * @author jrl
 *
 */
public class CloneMute extends Individu{

	//------------------------------------------------------------------------------
	//variables
	
	/**
	 * le p�re de l'individu, de la g�n�ration pr�c�dente
	 */
	private Individu parent;

	//-------------------------------------------------------------------------------
	//constructeur
	
	/**
	 * constructeur pour un clone mute
	 * @param parent
	 * @param mutation
	 */
	public CloneMute(Individu parent) {
		super();
		this.parent = parent;
		this.generation=parent.generation+1;
		this.cerveau=mutation.evolution(parent.cerveau.replique());
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
		+ "\"parent\":" + parent.getId() + ","
		+ super.toStringJson();
	}
	
	
}
