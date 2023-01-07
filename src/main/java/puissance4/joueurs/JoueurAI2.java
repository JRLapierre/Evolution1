package puissance4.joueurs;

import puissance4.grille.Grille;

/**
 * represente un joueur un peu plus intelligent que JoueurAI1 : ce joueur va toujours
 * jouer la colonne donnee dans le constructeur jusqu'a ce qu'elle soit pleine.
 * @author jrl
 *
 */
public class JoueurAI2 extends Joueur{

	private int colonne;
	
	/**
	 * constructeur
	 * @param colonne la colonne qui va être jouee a repetion
	 */
	public JoueurAI2(int colonne) {
		super();
		this.colonne=colonne;
	}

	@Override
	public int choix(Grille grille) {
		//choisis un nombre aleatoire parmi les possiblites
		int choix=colonne;
		while (grille.colonnePleine(choix)) {
			choix=(choix+1)%7;
			if(choix==0) choix=7;
		}
		return choix;
	}

	
}
