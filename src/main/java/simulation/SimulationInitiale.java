package simulation;

import java.io.File;

import javax.swing.JPanel;

import simulation.generation.Generation;
import simulation.generation.individus.Individu;
import simulation.generation.individus.Original;
import simulation.generation.individus.cerveau.Cerveau;
import simulation.generation.individus.cerveau.Mutation;

/**
 * cette classe permet de lancer une nouvelle simulation.
 * Il n'y a qu'a changer les parametres pour obtenir une simulation inedite.
 * @author jrl
 *
 */
public class SimulationInitiale extends Simulation{

	//-----------------------------------------------------------------------------------------
	//parametres
	/*
	 * ces parametres sont a changer selon la simulation qu'on veut faire.
	 * N'hesitez pas a les changer.
	 */
	
	/**
	 * le nom de la simulation
	 */
	private static String nomSimulation="3P4";
		
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
	private static int graineMutation=46587;
	
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
	private static Mutation mutation=new Mutation(graineMutation)
			.setTauxCreationSuppression(tauxCreation, tauxSuppression)
			.setMutationFacteur(tauxMutationFacteur, maxChangementFacteur)
			.setTauxMutationNeurone(tauxMutationNeurone); 
	
	/**
	 * l'individu de base. Rien a modifier.
	 * Cet individu va etre le pere de tous les individus de la premiere generation.
	 */
	private static Individu original=new Original(cerveau, mutation);
	
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
	 * le nombre de generations a simuler.
	 */
	private static int nbGenerations=100;
	
	
	//----------------------------------------------------------------------------------------
	//ne pas toucher la suite du code

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
	public SimulationInitiale(JPanel panel) {
		super(panel);
	}
	
	
	//----------------------------------------------------------------------------------------
	//fonctions de code
	
	/**
	 * demande a l'utilisateur si il est sur de ses actions
	 * @return true si l'utilisateur fait son choix
	 */
	public boolean choix() {
    	File f=new File("enregistrements\\simulation" + nomSimulation + "\\");
    	if(f.exists()) {
		    zoneTexte.setText("""
				Une simulation portant ce nom existe deja : 
				Si vous continuez, vous allez remplacer les fichiers de cette simulation. 
				Cliquez sur play/pause pour lancer 
				Cliquez sur arreter pour stopper
				""");
    	}
    	return true;
	}
	
	/**
	 * fonction run qui fait tourner la simulation
	 */
	@Override
	public void run() {
		pause();
		zoneTexte.setText("");
		generation=new Generation(original, nbClonesParfaits, nbClonesMutes, nbEnfantsSexe, butoir, epreuve, nomSimulation);
		//on fait tourner la simulation pour nbGenerations
    	generation.enregistreInfos(typeEnregistrement);
    	generation.enregistreGeneration(typeEnregistrement);
		for(int i=0; i<nbGenerations; i++) {
            simuleGeneration(i);
		}
	}
	
	

}
