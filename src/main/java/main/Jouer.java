package main;

import puissance4.jeu.DuelVsIndividu;

/**
 * cette classe permet de faire un duel contre un individu enregistre.
 * NE PAS LANCER CETTE CLASSE QUAND LA SIMULATION PRINCIPALE EST EN MARCHE
 * @author jrl
 *
 */
public class Jouer {

	/**
	 * methode main qui lance un duel
	 * @param args rien a mettre
	 */
	public static void main(String[] args) {
		try {
			DuelVsIndividu duel=new DuelVsIndividu("2P4", 51016, 100500, "bin");
			duel.duel(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
