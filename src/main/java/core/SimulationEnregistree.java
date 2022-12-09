package core;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import core.generation.Epreuve;
import core.generation.Generation;
import puissance4.jeu.Tournoi;
import puissance4.joueurs.Joueur;
import puissance4.joueurs.JoueurIndividu;

/**
 * cette classe permet de continuer une simulation a partir d'une sauvegarde.
 * @author jrl
 *
 */
public class SimulationEnregistree {


	//-----------------------------------------------------------------------------------------
	//parametres
	
	/**
	 * le nom de la simulation
	 */
	private static String nomSimulation="2P4";
	
	/**
	 * le numero de la generation a laquelle on va reprendre la simulation
	 */
	private static int generationInitiale=100;
	
	/**
	 * le nombre de generations a simuler.
	 */
	private static int nbGenerations=100;
	
	/**
	 * limiteur d'enregistrement.
	 * Une generation va etre enregistree si son numero % 1 == 0.
	 * avec une valeur de 1, toutes les generations vont etre enregistrees.
	 */
	private static int enregistre=1;
	
	/**
	 * fonction lambda. 
	 * Malheureusement, je ne suis pas parvenu a l'enregistrer ou a la recuperer depuis un 
	 * fichier. Il faut donc la redefinir ici.
	 */
	private static Epreuve epreuve=population -> {
		//creer une liste de joueurs
		Joueur[] participants=new Joueur[population.length];
		for(int i=0; i<population.length; i++) {
			participants[i]=new JoueurIndividu('O', population[i].getCerveau(), 25);
		}
		//lancer le tournoi
		Tournoi tournoi=new Tournoi(participants);
		try {
			tournoi.lancer();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//recuperer les scores des joueurs
		for(int i=0; i<population.length; i++) {
			population[i].updateScore(participants[i].getScore());
		}
	};
	
	/**
	 * fonction main qui permet de lancer le programme.
	 * @param args rien a mettre
	 */
	public static void main(String[] args) {
    	File f=new File("enregistrements\\simulation" + nomSimulation
    			+ "\\generation" + generationInitiale + "\\");
    	if(f.exists()) {
    		String reponse="";
		    Scanner input = new Scanner(System.in);
    		while(!reponse.equals("oui")) {
    		    System.out.println("Etes vous surs d'être a la derniere generation ?"
    		    		+ " Si ce n'est pas le cas, les fichiers des generations suivantes "
    		    		+ "vont etre ecrases.");
    		    System.out.print("Continuer ? (oui ou non) : ");
    		    reponse = input.nextLine();
    		    //on met fin a la simulation si la reponse est non
    		    if (reponse.equals("non")) {
    		    	return;
    		    }
    		}
		    input.close();
    	}
    	else {
    		System.out.println("le dossier cherche n'existe pas. verifiez le nom de la "
    				+ "simulation et le numero de genration.");
    		return;
    	}
    	Generation generation;
		try {
			generation = new Generation(nomSimulation, generationInitiale, epreuve);
			//on fait tourner la simulation pour nbGenerations
			for(int i=0; i<nbGenerations; i++) {
				System.out.println("generation " + (generationInitiale + i));
				if((generationInitiale + i)%enregistre==0) generation.enregistreGeneration();
				generation.nextGen();
			}
		} catch (IOException e) {
			System.out.println("Si vous lisez ce message, il existe des failles que je "
					+ "n'ai pas encore detectes");
			e.printStackTrace();
		}

	}

}
