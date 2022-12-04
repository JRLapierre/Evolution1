package puissance4.joueurs;

import puissance4.grille.Grille;

/**
 * cette classe represente un joueur.
 * @author jrl
 *
 */
public abstract class Joueur {

	
	/**
	 * un attribut Pion
	 */
	protected char pion;
	
	/**
	 * constructeur
	 * @param pion le pion attribue au joueur
	 */
	public Joueur(char pion) {
		this.pion=pion;
	}
	
	/**
	 * fonction pour determiner le choix du joueur
	 * @param grille la grille actuelle du jeu
	 * @return le choix du joueur allant de 1 a 7
	 */
	public abstract int choix(Grille grille);
	
}
