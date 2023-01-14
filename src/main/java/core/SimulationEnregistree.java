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
	private static int generationInitiale=40000;
	
	/**
	 * le nombre de generations a simuler.
	 */
	private static int nbGenerations=10000;
	
	/**
	 * limiteur d'enregistrement.
	 * Une generation va etre enregistree si son numero % enregistre == 0.
	 * avec une valeur de 1, toutes les generations vont etre enregistrees.
	 */
	private static int enregistre=100;
	
	/**
	 * fonction lambda. 
	 * Malheureusement, je ne suis pas parvenu a l'enregistrer ou a la recuperer depuis un 
	 * fichier. Il faut donc la redefinir ici.
	 */
	private static Epreuve epreuve=population -> {
		//creer une liste de joueurs
		Joueur[] participants=new Joueur[population.length];
		for(int i=0; i<population.length; i++) {
			participants[i]=new JoueurIndividu(population[i].getCerveau(), 25);
		}
		//on lance le tournoi
		Tournoi tournoi=new Tournoi(participants);
		tournoi.lancer();
		//recuperer les scores des joueurs
		for(int i=0; i<population.length; i++) {
			population[i].updateScore(participants[i].getScore());
		}
		
	};
	
	//----------------------------------------------------------------------------------------
	//fonction de changement
	
	/**
	 * fonction permettant de changer les regles de la simulation
	 * @param g la generation a changer
	 */
	private static void changeParametres(Generation g) {
		//si vous ne voulez pas changer les parametres, mettez les en commentaires.
		//g.setButoir(50);
		//g.setEpreuve(epreuve);
		//g.setMutations(null);
		//g.setNbClonesMutes(50);
		//g.setNbClonesParfaits(25);
		//g.setNbEnfantsSexe(25);
		g.enregistreInfos();
	}
	
	
	//----------------------------------------------------------------------------------------
	//fonction main
	
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
    		    System.out.println("Etes vous surs que la generation " + generationInitiale
    		    		+ " est la derniere generation ? \r"
    		    		+ "Si ce n'est pas le cas, les fichiers des generations suivantes "
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
			changeParametres(generation);
			//on fait tourner la simulation pour nbGenerations
			for(int i=0; i<=nbGenerations; i++) {
				System.out.println("generation " + (generationInitiale + i));
				if((generationInitiale + i)%enregistre==0 && i!=0) generation.enregistreGeneration();
				generation.nextGen();
			}
			System.out.println("fait");
		} catch (IOException e) {
			System.out.println("Si vous lisez ce message, il existe des failles que je "
					+ "n'ai pas encore detectes");
			e.printStackTrace();
		}

	}

}
