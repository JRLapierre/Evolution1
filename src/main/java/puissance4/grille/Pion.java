package puissance4.grille;

/**
 * une classe pion extremement simple.
 * Un pion a une couleur.
 * @author jrl
 *
 */
public abstract class Pion {

	/**
	 * la couleur du pion representee par un carractere.
	 */
	private char couleur;
	
	/**
	 * constructeur
	 * @param couleur la couleur a mettre
	 */
	protected Pion(char couleur) {
		this.couleur=couleur;
	}
	
	/**
	 * getteur pour la couleur
	 * @return couleur
	 */
	protected char getCouleur() {
		return this.couleur;
	}
	
	/**
	 * equals custom
	 * @param other l'autre pion a comparer
	 * @return true si les deux pions partagent la meme couleur
	 */
	protected boolean equalsPion(Pion other) {
		return this.couleur==other.couleur;
	}
	
	/**
	 * toString qui affiche la couleur.
	 */
	public String toString() {
		return "" + this.couleur;
	}
	
}
