package puissance4.joueurs;

import java.util.Scanner;

import puissance4.grille.Grille;

/**
 * cette classe represente un joueur hummain, c'est 
 * a dire nous. Elle nous permet de jouer.
 * @author jrl
 *
 */
public class JoueurHumain extends Joueur{
	
	/**
	 * rien a rajouter au constructeur
	 */
	public JoueurHumain() {
		super();
	}

	@Override
	public int choix(Grille grille) {
		System.out.println(grille); //pour que le joueur voie la grille
		System.out.println("votre pion : " + this.pion);
		Scanner input = new Scanner(System.in);
	    String reponse="";
	    while(!(reponse.equals("1") || reponse.equals("2") || reponse.equals("3") || 
	    		reponse.equals("4") || reponse.equals("5") || reponse.equals("6") || 
	    		reponse.equals("7"))) {
	    	System.out.print("votre choix : ");
		    reponse = input.nextLine();
	    }
	    //input.close();
		return Integer.parseInt(reponse);
	}

}
