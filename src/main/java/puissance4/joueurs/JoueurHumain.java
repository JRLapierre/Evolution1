package puissance4.joueurs;

import java.util.Scanner;

import puissance4.grille.Grille;

public class JoueurHumain extends Joueur{
	
	/**
	 * rien a rajouter au constructeur
	 * @param pion
	 */
	public JoueurHumain(char pion) {
		super(pion);
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
		return Integer.parseInt(reponse);
	}

}