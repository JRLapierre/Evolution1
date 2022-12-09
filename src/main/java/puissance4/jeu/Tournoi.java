package puissance4.jeu;

import puissance4.grille.Grille;
import puissance4.joueurs.Joueur;

public class Tournoi {
	
	/**
	 * une liste de joueurs
	 */
	private Joueur[] participants;
	
	/**
	 * constructeur
	 * @param participants la liste de joueurs participants
	 */
	public Tournoi(Joueur[] participants) {
		this.participants=participants;
	}
	
	/**
	 * lance le tournoi
	 * @throws Exception si les joueurs sont defectueux
	 */
	public void lancer() throws Exception {
		for(int i=0; i<participants.length; i++) {
			for(int j=0; j<participants.length; j++) {
				jeu(participants[i], participants[j]);
			}
		}
	}
	 
	/**
	 * le jeu (repis de Partie avec quelques modifs)
	 * @param joueur1
	 * @param joueur2
	 * @return le joueur gagnant
	 * @throws Exception si les joueurs sont defectueux
	 */
	public Joueur jeu(Joueur joueur1, Joueur joueur2) throws Exception {
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
			return joueurActuel;
		}
		else {
			joueur1.updateScore(1);
			joueur2.updateScore(1);
			return null;
		}
	}

}
