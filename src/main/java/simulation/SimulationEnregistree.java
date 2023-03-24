package simulation;

import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JTextArea;

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
	 * le numero de la generation a laquelle on va reprendre la simulation
	 */
	private static int generationInitiale=110;
	
	/**
	 * le nombre de generations a simuler.
	 */
	private static int nbGenerations=90;

	
	//----------------------------------------------------------------------------------------
	//fonction de changement
	
	/**
	 * fonction permettant de changer les regles de la simulation
	 * @param g la generation a changer
	 *//*
	private static void changeParametres(Generation g) {
		//si vous ne voulez pas changer les parametres, mettez les en commentaires.
		//g.setButoir(50);
		//g.setEpreuve(epreuve);
		//g.setMutations(null);
		//g.setNbClonesMutes(50);
		//g.setNbClonesParfaits(25);
		//g.setNbEnfantsSexe(25);
		g.enregistreInfos(typeEnregistrements);
	}*/
	
	//----------------------------------------------------------------------------------------
	//ne pas toucher a cette partie du code

	//----------------------------------------------------------------------------------------
	//constructeur
	
	/**
	 * ce constructeur initialise une simulation. 
	 * Les parametres sont les JLabel qui vont afficher
	 * les informations relatives au fonctionnement 
	 * de la simulation
	 * @param generation le label qui affiche la generation
	 * @param phase le label qui affiche la phase de la simulation
	 */
	public SimulationEnregistree(JLabel generation, JLabel phase, JTextArea zoneTexte) {
		super(generation, phase, zoneTexte);
	}
	
	//----------------------------------------------------------------------------------------
	//fonctions de code
	
	/**
	 * demande a l'utilisateur si il est sur de ses actions
	 * @return true si l'utilisateur fait son choix
	 */
	public boolean choix() {
	   	File f=new File("enregistrements\\simulation" + nomSimulation
    			+ "\\generation" + generationInitiale + "\\");
	   	if(!f.exists()) {
	   		zoneTexte.setText("le dossier cherche n'existe pas. verifiez le nom de la "
    				+ "simulation et le numero de genration.");
    		return false;
	   	}
	   	else zoneTexte.setText(
	   			"Etes vous surs que la generation " + generationInitiale + """
				est la derniere generation ?
				Si ce n'est pas le cas, les fichiers des generations suivantes
				vont etre ecrases.
				Cliquez sur play/pause pour lancer 
				Cliquez sur arreter pour stopper
				""");
    	return true;
	}
	
	/**
	 * fonction run qui fait tourner la simulation
	 */
	@Override
	public void run() {
		try {
			pause();
			zoneTexte.setText("");
			labelPhase.setText("recuperation des donnees...");
			generation = new Generation(nomSimulation, generationInitiale, typeFichiers, epreuve);
			//changeParametres(generation);
			//on fait tourner la simulation pour nbGenerations
			for(int i=1; i<=nbGenerations; i++) {
	            simuleGeneration(i);
			}
			labelGeneration.setText("fait");
		} catch (IOException e) {
			zoneTexte.setText("Si vous lisez ce message, il existe des failles que je "
					+ "n'ai pas encore detectes");
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
	

}
