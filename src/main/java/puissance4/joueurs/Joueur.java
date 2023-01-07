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
	 * le score du joueur
	 */
	private int score=0;
	
	/**
	 * un generateur de nombre aleatoires pour resoudre les indecisions
	 */
	protected static Aleatoire random=new Aleatoire(456);

	
	/**
	 * constructeur
	 */
	protected Joueur() {
	}
	
	/**
	 * fonction pour determiner le choix du joueur
	 * @param grille la grille actuelle du jeu
	 * @return le choix du joueur allant de 1 a 7
	 */
	public abstract int choix(Grille grille);
	
	
	public void updateScore(int points) {
		this.score+=points;
	}
	
	public int getScore() {
		return score;
	}
	
	/**
	 * setteur qui permet de renouveler la graine d'aleatoire.
	 * @param graine la graine d'aleatoire
	 */
	public static void setAleatoire(int graine) {
		random=new Aleatoire(graine);
	}
}
