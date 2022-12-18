package puissance4.joueurs;

import puissance4.grille.Grille;

/**
 * cette classe represente un joueur assez bête : il joue aleatoirement.
 * @author jrl
 *
 */
public class JoueurAI1 extends Joueur{
	

	/**
	 * aucun ajout a la superclasse
	 */
	public JoueurAI1() {
		super();
	}

	@Override
	public int choix(Grille grille) {
		//choisis un nombre aleatoire parmi les possiblites
		int choix=random.aleatInt(1, 7);
		while (grille.colonnePleine(choix)) {
			choix=(choix+1)%7;
			if(choix==0) choix=7;
		}
		return choix;
	}

}
