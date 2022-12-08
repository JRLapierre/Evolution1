package puissance4.joueurs;

import outils.Aleatoire;
import puissance4.grille.Grille;
import puissance4.jeu.Partie;

/**
 * cette classe represente un joueur.
 * @author jrl
 *
 */
public abstract class Joueur {

	
	/**
	 * un attribut Pion
	 */
	public char pion;
	
	
	/**
	 * un generateur de nombre aleatoires pour resoudre les indecisions
	 */
	protected static Aleatoire random=new Aleatoire(456);

	
	/**
	 * constructeur
	 * @param pion le pion attribue au joueur
	 */
	protected Joueur(char pion) {
		this.pion=pion;
	}
	
	/**
	 * fonction pour determiner le choix du joueur
	 * @param grille la grille actuelle du jeu
	 * @return le choix du joueur allant de 1 a 7
	 */
	public abstract int choix(Grille grille);
	
}