package puissance4.jeu;

/**
 * cette classe permet de faire un duel contre un individu enregistre.
 * NE PAS LANCER CETTE CLASSE QUAND LA SIMULATION PRINCIPALE EST EN MARCHE
 * @author jrl
 *
 */
public class Jouer {

	public static void main(String[] args) {
		try {
			DuelVsIndividu duel=new DuelVsIndividu("2P4", 400, 39905);
			duel.duel(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
