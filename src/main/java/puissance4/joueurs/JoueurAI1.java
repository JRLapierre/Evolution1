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
		Grille g=grille.copie();
		//balance un nombre aleatoire parmi les possiblites
		int choix=random.aleatInt(1, 7);
		while (!g.ajoutePion(this.pion, choix)) {
			choix=(choix+1)%7;
		}
		return choix;
	}

}
