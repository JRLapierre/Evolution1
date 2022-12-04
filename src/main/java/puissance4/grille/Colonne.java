package puissance4.grille;

/**
 * cette classe represente une colonne d'une grille.
 * @author jrl
 *
 */
public class Colonne {

	/**
	 * une colonne est une liste de pion
	 */
	private Pion[] colonne=new Pion[6];
	
	/**
	 * constructeur
	 */
	protected Colonne() {
		//rien a mettre
	}
	
	/**
	 * methode d'ajout d'un pion.
	 * Je laisse la securite a la classe grille
	 * @param pion le pion a rajouter
	 */
	protected void ajoutePion(Pion pion) {
		for(int i=0; i<6; i++) {
			if(colonne[i]==null) {
				colonne[i]=pion;
				return;
			}
		}
	}
	
	
	/**
	 * si une colonne est pleine
	 * @return true si la colonne est pleine
	 */
	public boolean estPleine() {
		return colonne[5]==null;
	}
	
	//vider une colonne
	protected void vide() {
		for(int i=0; i<6; i++) {
			colonne[i]=null;
		}
	}
}
