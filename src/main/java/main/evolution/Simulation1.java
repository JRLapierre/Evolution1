package main.evolution;

import core.cerveau.Cerveau;
import core.individus.CloneMute;
import core.individus.CloneParfait;
import core.individus.EnfantSexe;
import core.individus.Individu;
import core.individus.Original;
import core.mutations.Mutation;
import outils.listeChaine.Compare;
import outils.listeChaine.ListeChaine;

/**
 * dans cette classe je vais faire tourner la simulation 1.
 * Cette simulation aura 1 entrée au cerveau, 1 sortie
 * et le parametre de selection est de se rapprocher au max
 * de 5 en sortie.
 * @author jrl
 *
 */
public class Simulation1 {
	
	//-------------------------------------------------------------------------------
	//attributs
	
	//je customise pour la simulation 1
	//je ferais peut être bien de faire une classe abstraite pour factoriser 
	// par la suite
	
	/**
	 * le numero de la simulation
	 */
	private static int numSimulation=1;
	
	
	//-----------------------------------------
	//population
	
	/**
	 * distribution de la population : le nombre de clones parfait
	 */
	private static int nbClonesParfaits=25;
	
	/**
	 * distribution de la population : le nombre de clones mutés
	 */
	private static int nbClonesMutes=50;
	
	/**
	 * distribution de la population : le nombre de clones parfaits
	 */
	private static int nbEnfantsSexe=25;
	
	
	//-------------------------------------------
	//cerveau
	
	
	/**
	 * cerveau : nombre de neurones d'entrées
	 */
	private static int nbInput=1;
	
	/**
	 * cerveau : nombre de neurones de sortie
	 */
	private static int nbOutput=1;
	
	/**
	 * cerveau : nombre de neurones internes
	 */
	private static int nbNeurones=5;
	
	//-------------------------------------------------
	//parametres de mutation
	
	/**
	 * mutation : graine determinant les nombres aleatoires
	 */
	private static int graine=46;
	
	/**
	 * mutation : taux de chance de creation d'une connexion pour une neurone
	 */
	private static int tauxCreation=5;
	
	/**
	 * mutation : taux de chance de suppresion de connexion par connexion
	 */
	private static int tauxSuppression=5;
	
	/**
	 * mutation : taux de chance de mutation du facteur d'une connexion
	 */
	private static int tauxMutationFacteur=5;
	
	/**
	 * mutation : nombre maximal du changement du facteur d'une connexion
	 */
	private static int maxChangementFacteur=2;
	
	/**
	 * mutation : taux de chance que les extremites d'une connexion changent
	 */
	private static int tauxMutationNeurone=5;
	
	/**
	 * le nombre de tic
	 */
	private static int nbTics=10;
	
	/**
	 * le nombre de generations
	 */
	private static int nbGens=100;
	
	/**
	 * les mutations
	 */
	private static Mutation mutation=new Mutation(
			graine, 
			tauxCreation, 
			tauxSuppression, 
			tauxMutationFacteur,
			maxChangementFacteur, 
			tauxMutationNeurone);
	
	/**
	 * une liste d'individus d'une generation
	 */
	private static ListeChaine<Individu> population=new ListeChaine<>();
	

	//-------------------------------------------------------------------------------
	//fonctions d'initialisation

	/**
	 * une fonction qui initialise le premier individu et qui remplis la premiere
	 * generation
	 */
	private static void initIndividus() {
		Individu original = new Original(new Cerveau(nbInput, nbOutput, nbNeurones), graine);
		for (int i=0; i<nbClonesParfaits + nbClonesMutes + nbEnfantsSexe; i++) {
			population.ajout(new CloneMute(original, mutation));
		}
	}
	
	//-------------------------------------------------------------------------------
	//fonctions de reproduction
	
	/**
	 * reproduction sexuee
	 * @param newGen la liste incomplete des individus de la nouvelle generation
	 */
	private static void newGenSexe(ListeChaine<Individu> newGen) {
		//reproduction sexuee
		//on selectionne 2n individus qu'on met dans une liste a 
		ListeChaine<Individu> sexe=new ListeChaine<>();
		Individu ind;
		for (int i=0; i<2*nbEnfantsSexe; i++) {
			ind=population.getPrecedent();
			if (ind==null) {//cas ou il n'y a pas assez d'individus
				ind=population.getPrecedent();
			}
			sexe.ajout(ind);
		}
		population.resetParcours();
		//on cree les nouveaux individus
		for (int i=0; i<nbEnfantsSexe; i++) {
			newGen.ajout(new EnfantSexe(sexe.getPrecedent(), sexe.getPrecedent(),
					mutation));
		}
		//on remplace l'ancienne generation par la nouvelle
		population=newGen;
	}
	
	/**
	 * une fonction qui genere la generation suivante
	 */
	private static void newGen() {
		//tri de la population selon le score
		Compare<Individu> egal = (a, b) -> a.getScore()==b.getScore();
		Compare<Individu> different = (a , b) -> a.getScore() < b.getScore();
		population.triRapide(egal, different); //ceux avec le plus grand score sont a la fin
		
		ListeChaine<Individu> newGen=new ListeChaine<>();
		population.resetParcours();
		//ajout des clones pafaits
		for (int i=0; i<nbClonesParfaits; i++) {
			newGen.ajout(new CloneParfait(population.getPrecedent()));
		}
		population.resetParcours();
		//ajout des clones mutes
		for (int i=0; i<nbClonesMutes; i++) {
			newGen.ajout(new CloneMute(population.getPrecedent(), mutation));
		}
		population.resetParcours();
		
		newGenSexe(newGen);

	}
	
	/**
	 * une methode pour evaluer la performance des individus
	 * @param individu l'invididu a evaluer
	 */
	protected static void performance(Individu individu) {
		//je vais selectionner ceux qui ont un output s'approchant le plus de 5
		float score=individu.getCerveau().getListeOutput()[0].getPuissance();
		individu.updateScore(5+score);
	}
	
	/**
	 * une fonction qui met à l'epreuve pour n tics
	 * @param individu l'individu a evaluer
	 */
	protected static void epreuve(Individu individu) {
		for (int i=0; i<nbTics; i++) {
			individu.getCerveau().getListeInput()[0].setPuissance(1);
			individu.getCerveau().next();
			performance(individu);
		}
	}
	
	//-------------------------------------------------------------------------------
	//main
	
	/**
	 * fonction main qui lance le tout
	 * @param args litteralement rien
	 */
	public static void main(String[] args) {
		initIndividus();
		
		//boucle pour le nombre de generations
		for (int i=0; i<nbGens; i++) {
			population.resetParcours();
			Individu individu=population.getSuivant();
			while (individu!=null) {
				//on fait l'epreuve
				epreuve(individu);
				//on enregistre l'individu
				individu.creeEnregistrementJson(numSimulation, "generation"
				+ individu.getGeneration(), "individu" + individu.getId());
				individu=population.getSuivant();
			}
			//on fait la reproduction
			newGen();
			
		}

		
	}
}
