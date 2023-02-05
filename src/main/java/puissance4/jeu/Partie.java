package puissance4.jeu;

import puissance4.grille.Grille;
import puissance4.joueurs.Joueur;

/**
 * cette classe simule une partie entre deux joueurs.
 * @author jrl
 *
 */
public class Partie extends Thread{
	
	/**
	 * le premier joueur
	 */
	private Joueur joueur1;
	
	/**
	 * le deuxieme joueur
	 */
	private Joueur joueur2;
	
	/**
	 * constructeur pour une nouvelle partie
	 * @param joueur1 le joueur qui commencera
	 * @param joueur2 le joueur qui jouera en 2e
	 */
	public Partie(Joueur joueur1, Joueur joueur2) {
		this.joueur1=joueur1;
		this.joueur2=joueur2;
	}
	
	/**
	 * simule le jeu principal
	 * @throws Exception si le joueur choisit une colonne deja pleine ou joue hors du plateau
	 */
	public void jeu() throws Exception {
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
		if(grille.gagne(joueurActuel.pion, dernierCoup)) {
			joueurActuel.updateScore(2);
		}
		else {
			joueur1.updateScore(1);
			joueur2.updateScore(1);
		}
	}

	/**
	 * run : lance un jeu
	 */
	@Override
	public void run() {
		try {
			jeu();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
