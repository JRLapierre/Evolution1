package puissance4.joueurs;

import outils.Aleatoire;
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
	
	
	/**
	 * modifie le score du joueur de x points
	 * @param points les points a ajouter ou enlever au joueur
	 */
	public void updateScore(int points) {
		this.score+=points;
	}
	
	/**
	 * getteur pour obtenir le score du joueur
	 * @return le score du joueur
	 */
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
