package puissance4.jeu;

import puissance4.joueurs.Joueur;

/**
 * cette classe simule un tournoi.
 * Il s'agit d'organiser tous les duel au puissance 4 possibles.
 * @author jrl
 *
 */
public class Tournoi {
	
	
	/**
	 * une liste des parties
	 */
	private Partie[] parties;
	
	/**
	 * constructeur
	 * @param participants la liste de joueurs participants
	 */
	public Tournoi(Joueur[] participants) {
		//une liste de n*n-n parties
		parties=new Partie[participants.length*participants.length-participants.length];
		//remplissage de la liste de parties
		int compte=0;
		for(int i=0; i<participants.length; i++) {
			for(int j=0; j<participants.length; j++) {
				if(i!=j) {
					parties[compte]=new Partie(participants[i], participants[j]);
					compte++;
				}
			}
		}
	}
	
	/**
	 * lance le tournoi
	 */
	public void lancer() {
		for(int i=0; i<parties.length; i++) {
			while (Runtime.getRuntime().availableProcessors()==0) {
				//on attend qu'un processeur se libere
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}
			parties[i].start();
		}
	}
	

}
