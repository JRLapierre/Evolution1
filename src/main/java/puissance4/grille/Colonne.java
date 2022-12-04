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
	private char[] liste=new char[6];
	
	/**
	 * constructeur
	 */
	protected Colonne() {
		for(int i=0; i<6; i++) {
			liste[i]=' ';
		}
	}
	
	/**
	 * 
	 * @return la liste de pions
	 */
	protected char[] getListe() {
		return liste;
	}
	
	/**
	 * methode d'ajout d'un pion.
	 * Je laisse la securite a la classe grille
	 * @param pion le pion a rajouter
	 * @return true si le pion a pu etre place
	 */
	protected boolean ajoutePion(char pion) {
		for(int i=0; i<6; i++) {
			if(liste[i]==' ') {
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
		return liste[5]!=' ';
	}
	
	//vider une colonne
	protected void vide() {
		for(int i=0; i<6; i++) {
			liste[i]=' ';
		}
	}
}
