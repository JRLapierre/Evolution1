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
	private Pion[] liste=new Pion[6];
	
	/**
	 * constructeur
	 */
	protected Colonne() {
		//rien a mettre
	}
	
	/**
	 * 
	 * @return la liste de pions
	 */
	protected Pion[] getListe() {
		return liste;
	}
	
	/**
	 * methode d'ajout d'un pion.
	 * Je laisse la securite a la classe grille
	 * @param pion le pion a rajouter
	 * @return true si le pion a pu etre place
	 */
	protected boolean ajoutePion(Pion pion) {
		for(int i=0; i<6; i++) {
			if(liste[i]==null) {
				liste[i]=pion;
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * si une colonne est pleine
	 * @return true si la colonne est pleine
	 */
	protected boolean estPleine() {
		return liste[5]!=null;
	}
	
	//vider une colonne
	protected void vide() {
		for(int i=0; i<6; i++) {
			liste[i]=null;
		}
	}
}
