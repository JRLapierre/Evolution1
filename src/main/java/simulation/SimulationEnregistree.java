package simulation;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import puissance4.jeu.Tournoi;
import puissance4.joueurs.Joueur;

import puissance4.joueurs.JoueurIndividu;
import simulation.generation.Epreuve;
import simulation.generation.Generation;

/**
 * cette classe permet de continuer une simulation a partir d'une sauvegarde.
 * @author jrl
 *
 */
public class SimulationEnregistree extends Simulation{


	//-----------------------------------------------------------------------------------------
	//parametres
	
	/**
	 * le nom de la simulation
	 */
	private static String nomSimulation="3P4";
	
	/**
	 * le format des fichiers a recuperer.
	 * Dans la situation actuelle, on a le choix entre json et bin.
	 */
	private static String typeFichiers="bin";
	
	
	/**
	 * le format des fichiers a enregistrer.
	 * Dans la situation actuelle, on a le choix entre json et bin.
	 */
	private static String typeEnregistrements="bin";
	
	/**
	 * le numero de la generation a laquelle on va reprendre la simulation
	 */
	private static int generationInitiale=110;
	
	/**
	 * le nombre de generations a simuler.
	 */
	private static int nbGenerations=90;
	
	/**
	 * limiteur d'enregistrement.
	 * Une generation va etre enregistree si son numero % enregistre == 0.
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
	
	/**
	 * la generation porteuse
	 */
	private static Generation generation;

	
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
		g.enregistreInfos(typeEnregistrements);
	}
	
	
	//----------------------------------------------------------------------------------------
	//fonctions de code
	//ne pas toucher a cette partie du code
	
	/**
	 * demande a l'utilisateur si il est sur de ses actions
	 * @return true si l'utilisateur fait son choix
	 */
	public boolean choix() {
	   	File f=new File("enregistrements\\simulation" + nomSimulation
    			+ "\\generation" + generationInitiale + "\\");
	   	if(!f.exists()) {
    		System.out.println("le dossier cherche n'existe pas. verifiez le nom de la "
    				+ "simulation et le numero de genration.");
    		return false;
	   	}
	   	else {
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
    		    	return false;
    		    }
    		}
    		input.close();
    		return true;
    	}
	}
	
	/**
	 * fonction run qui fait tourner la simulation
	 */
	public void run() {
		try {
			System.out.println("recuperation des donnees...");
			generation = new Generation(nomSimulation, generationInitiale, typeFichiers, epreuve);
			changeParametres(generation);
			//on fait tourner la simulation pour nbGenerations
			for(int i=1; i<=nbGenerations; i++) {
				System.out.println("===generation " + (generationInitiale + i) + "===");
				System.out.println("creation de la prochaine generation...");
				generation.nextGen();
				System.out.println("evaluation...");
				generation.evaluation();
				if((generationInitiale + i) % enregistre==0) {
		        	System.out.println("enregistrement...");
					generation.enregistreGeneration(typeEnregistrements);
				}
			}
			System.out.println("fait");
		} catch (IOException e) {
			System.out.println("Si vous lisez ce message, il existe des failles que je "
					+ "n'ai pas encore detectes");
			e.printStackTrace();
		}
	}
	

}
