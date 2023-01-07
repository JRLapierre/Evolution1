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
	 * l'id du p�re de l'individu, de la g�n�ration pr�c�dente
	 */
	private int idParent;

	//-------------------------------------------------------------------------------
	//constructeur
	
	/**
	 * constructeur pour un clone mute
	 * @param parent
	 * @param mutation
	 */
	public CloneMute(Individu parent) {
		super();
		this.idParent = parent.getId();
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
		+ "\"parent\":" + idParent + ","
		+ super.toStringJson();
	}
	
	
}
