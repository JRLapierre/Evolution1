package core.generation.individus;

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
	 * @param parent
	 */
	public CloneParfait(Individu parent) {
		super();
		this.idParent = parent.getId();
		this.generation=parent.generation+1;
		this.cerveau=parent.cerveau.replique();
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
	

}
