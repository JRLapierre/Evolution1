package core.generation.individus;

/**
 * classe repr�sentant un individu issu du clonage parfait
 * @author jrl
 *
 */
public class CloneParfait extends Individu{

	//------------------------------------------------------------------------------
	//variables
	
	/**
	 * l'id du p�re de l'individu, de la g�n�ration pr�c�dente
	 */
	private int idParent;

	//-------------------------------------------------------------------------------
	//constructeur
	
	/**
	 * constructeur pour un clone parfait,au cerveau identique � ses parents
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
