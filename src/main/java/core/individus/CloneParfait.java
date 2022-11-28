package core.individus;

/**
 * classe repr�sentant un individu issu du clonage parfait
 * @author jrl
 *
 */
public class CloneParfait extends Individu{

	//------------------------------------------------------------------------------
	//variables
	
	/**
	 * le p�re de l'individu, de la g�n�ration pr�c�dente
	 */
	private Individu parent;

	//-------------------------------------------------------------------------------
	//constructeur
	
	/**
	 * constructeur pour un clone parfait,au cerveau identique � ses parents
	 * @param parent
	 */
	public CloneParfait(Individu parent) {
		super();
		this.parent = parent;
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
		+ "\"parent\":" + parent.getId() + ","
		+ super.toStringJson();
	}
	

}
