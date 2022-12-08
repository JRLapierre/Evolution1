package puissance4.jeu;

import puissance4.grille.Grille;
import puissance4.joueurs.Joueur;

/**
 * cette classe simule une partie entre deux joueurs.
 * @author jrl
 *
 */
public class Partie {
	
	/**
	 * simule le jeu principal
	 * @return le joueur qui gagne, null en cas d'egalite
	 * @throws Exception 
	 */
	public static Joueur jeu(Joueur joueur1, Joueur joueur2) throws Exception {
		joueur1.pion='X';
		joueur2.pion='O';
		Grille grille=new Grille();
		Joueur joueurActuel=joueur1;
		int dernierCoup=8; //on est sur d'avoir false au debut
		
		while(!grille.estPleine() && !grille.gagne(joueurActuel.pion, dernierCoup)) {
			//on change le joueur
			if(joueurActuel==joueur1) joueurActuel=joueur2;
			else joueurActuel=joueur1;
			//le joueur fait son choix
			dernierCoup=joueurActuel.choix(grille);
			if(!grille.ajoutePion(joueurActuel.pion, dernierCoup)) {
				//si le joueur choisit une colonne deja pleine ou joue hors du plateau
				throw new Exception("le joueur ne sait pas jouer");
			}
		}
		//on verifie quelle condition a brise la boucle
		if(grille.gagne(joueurActuel.pion, dernierCoup)) return joueurActuel;
		else return null;
	}
}
