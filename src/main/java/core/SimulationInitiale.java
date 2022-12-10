package core;

import java.io.File;
import java.util.Scanner;

import core.generation.Epreuve;
import core.generation.Generation;
import core.generation.individus.Individu;
import core.generation.individus.Original;
import core.generation.individus.cerveau.Cerveau;
import core.generation.individus.mutations.Mutation;
import puissance4.jeu.Tournoi;
import puissance4.joueurs.Joueur;
import puissance4.joueurs.JoueurIndividu;

/**
 * cette classe permet de lancer une nouvelle simulation.
 * Il n'y a qu'a changer les parametres pour obtenir une simulation inedite.
 * @author jrl
 *
 */
public class SimulationInitiale {

	//-----------------------------------------------------------------------------------------
	//parametres
	/*
	 * ces parametres sont a changer selon la simulation qu'on veut faire.
	 * N'hesitez pas a les changer.
	 */
	
	/**
	 * le nom de la simulation
	 */
	private static String nomSimulation="2P4";
		
	//-----------------------------------------------------------------------------------------
	//population
	/*
	 * il s'agit de la repartition de la population selon le type d'individu.
	 * Ce qui distingue ces individus, c'est leur mode de reproduction.
	 * La population totale est constituee du cumul de ces trois types d'individus
	 */
	
	/**
	 * le nombre de clones parfaits dans la populations : 
	 * les clones parfaits ont une copie parfaite du cerveau de leurs parents.
	 */
	private static int nbClonesParfaits=25;
	
	/**
	 * le nombre de clones mutes dans la population : 
	 * les clones mutes ont une copie alteree du cerveau de leurs parents
	 */
	private static int nbClonesMutes=50;
	
	/**
	 * le nombre d'enfants de reproduction sexuee dans la population : 
	 * les enfantsSexe ont une fusion alteree des cerveaux de leurs deux parents.
	 */
	private static int nbEnfantsSexe=25;
	
	
	//-----------------------------------------------------------------------------------------
	//individus
	/*
	 * Dans cette section, les carracteristiques partagees par tous les individus sont 
	 * definis : leur graine d'aleatoire, leur cerveau et leurs mutations.
	 */
	
	/**
	 * la graine d'aleatoire des individus.
	 * cette graine permet de generer une suite de nombres aleatoire qui permettent de decider
	 * de la fusion de deux cerveaux lors de la reproduction sexuee.
	 */
	private static int graineAleatoire=645;
	
	//-----------------------------------
	//cerveau
	/*
	 * il s'agit des carracteristiques des cerveaux qu'on veut donner aux individus.
	 */
	
	/**
	 * le nombre de neurones d'entrees du cerveau : 
	 * ces neurones sont celles qui recoivent un signal qui est ensuite transmis a d'autres 
	 * parties du cerveau par les connexions.
	 */
	private static int nbInput=43;
	
	/**
	 * le nombre de neurones de sortie du cerveau : 
	 * ces neurones sont observes et servent de base a l'evaluation des individus.
	 */
	private static int nbOutput=7;
	
	/**
	 * le nombre de neurones internes : 
	 * ce sont des neurones internes au cerveau, permettant des shema de connexions complexes.
	 */
	private static int nbNeurones=50;
	
	/**
	 * le cerveau de base. Rien a modifier.
	 * Ce cerveau ne comporte aucune connexion entre les neurones. Les connexions vont arriver
	 * via les mutations.
	 */
	private static Cerveau cerveau=new Cerveau(nbInput, nbOutput, nbNeurones);
	
	//-----------------------------------
	//mutations
	/**
	 * les mutations agissent sur les connexions du cerveau. Elles permettent d'en creer, de 
	 * les modifier ou de les supprimer.
	 */
	
	/**
	 * la graine d'aleatoire de Mutation.
	 * Cette graine permet de determiner une suite de nombre pseudo-aleatoire permettant de 
	 * faire des mutations qui semblent aleatoires.
	 */
	private static int graineMutation=6843;
	
	/**
	 * le taux de chance de creation d'une connexion entre deux neurones.
	 */
	private static int tauxCreation=5;
	
	/**
	 * le taux de chance de suppression d'une connexion existante.
	 */
	private static int tauxSuppression=5;
	
	/**
	 * le taux de chance de mutation du facteur multiplicateur du signal d'une connexion.
	 */
	private static int tauxMutationFacteur=5;
	
	/**
	 * le facteur maximal de changement du facteur multiplicateur du signal d'une connexion.
	 * Cette variable intervient quand il y a mutation du facteur.
	 * Le changement de facteur sera entre - maxChangementFacteur et + maxChangementFacteur.
	 */
	private static float maxChangementFacteur=2;
	
	/**
	 * taux de chance de mutation d'une des extremites d'une connexion :
	 * la mutation d'une des extremites d'une connexion provoque le changement de source ou 
	 * de cible d'une connexion.
	 */
	private static int tauxMutationNeurone=5;
	
	/**
	 * les mutations. Rien a modifier.
	 */
	private static Mutation mutation=new Mutation(
			graineMutation, 
			tauxCreation, 
			tauxSuppression, 
			tauxMutationFacteur, 
			maxChangementFacteur, 
			tauxMutationNeurone);
	
	/**
	 * l'individu de base. Rien a modifier.
	 * Cet individu va etre le pere de tous les individus de la premiere generation.
	 */
	private static Individu original=new Original(cerveau, graineAleatoire, mutation);
	
	//-----------------------------------------------------------------------------------------
	//selection
	/*
	 * Cette section permet de definir les contraintes faces auquelles les individus vont 
	 * faire face a chaque generation.
	 * Si il n'y a aucune pression selective, la simulation ne va pas produire d'individus 
	 * performants : la plupart des mutations sont defavorables ou innutiles.
	 */
	
	/**
	 * ce butoir limite la capacite d'un individu a se reproduire en fonction de son rang : 
	 * les individus classes par leur score au dela de cette valeur ne vont pas pouvoir se 
	 * reproduire.
	 * Pour ne pas avoir de butoir, il suffit de lui attribuer une valeur superieure au 
	 * nombre d'individus d'une generation.
	 */
	private static int butoir=50;
	
	/**
	 * expression lambda qui definit l'epreuve par laquelle les individus vont passer : 
	 * on envoie des signaux dans les neurones d'entree et on observe ceux qui sont en
	 * sortie. On leur fait accomplir une tache grace aux connexions de sortie et on 
	 * evalue leur performance afin de former un score.
	 * Les individus avec le plus grand score vont pouvoir se reproduire.
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
			return;
		}
		//recuperer les scores des joueurs
		for(int i=0; i<population.length; i++) {
			population[i].updateScore(participants[i].getScore());
		}
	};
	
	/**
	 * la generation initiale. Rien a modifier.
	 * Elle sera composee de clones mutes de l'individu original.
	 */
	private static Generation generation=new Generation(original, nbClonesParfaits, nbClonesMutes, nbEnfantsSexe, butoir, epreuve, nomSimulation);
	
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
	
	//-----------------------------------------------------------------------------------------
	//programme principal
	
	/**
	 * fonction main qui permet de lancer le programme.
	 * @param args rien a mettre
	 */
	public static void main(String[] args) {
    	File f=new File("enregistrements\\simulation" + nomSimulation + "\\");
    	if(f.exists()) {
    		String reponse="";
		    Scanner input = new Scanner(System.in);
    		while(!reponse.equals("oui")) {
    		    System.out.println("Une simulation portant ce nom existe deja : "
    		    		+ "Si vous continuez, vous allez remplacer les fichiers de "
    		    		+ "cette simulation. ");
    		    System.out.println("Continuer ? (oui ou non) : ");
    		    reponse = input.nextLine();
    		    //on met fin a la simulation si la reponse est non
    		    if (reponse.equals("non")) {
    		    	return;
    		    }
    		}
		    input.close();
    	}
		//on fait tourner la simulation pour nbGenerations
    	generation.enregistreInfos();
		for(int i=0; i<nbGenerations; i++) {
			System.out.println("generation " + i);
			if(i%enregistre==0) generation.enregistreGeneration();
			generation.nextGen();
		}
	}

}
