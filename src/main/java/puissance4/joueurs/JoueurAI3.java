package puissance4.joueurs;

import puissance4.grille.Grille;

/**
 * un joueur qui va choisir une colonne aleatoire puis la spammer
 * @author jrl
 *
 */
public class JoueurAI3 extends Joueur{

	/**
	 * la colonne qui va etre jouee a repetion
	 */
	private int colonne;
	
	/**
	 * constructeur
	 */
	public JoueurAI3() {
		super();
		this.colonne=random.aleatInt(1, 7);
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
