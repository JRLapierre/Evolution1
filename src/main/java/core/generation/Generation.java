package core.generation;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import core.generation.individus.CloneMute;
import core.generation.individus.CloneParfait;
import core.generation.individus.EnfantSexe;
import core.generation.individus.Individu;
import core.generation.individus.Sauvegarde;
import core.generation.individus.mutations.Mutation;
import outils.Carracteristique;
import outils.ListeChaine;

/**
 * cette classe représente une génération.
 * @author jrl
 *
 */
public class Generation {

	//------------------------------------------------------------------------------------------
	//variables : distribution de la population
	
	/**
	 * distribution de la population : le nombre de clones parfait
	 */
	private int nbClonesParfaits;
	
	/**
	 * distribution de la population : le nombre de clones mutés
	 */
	private int nbClonesMutes;
	
	/**
	 * distribution de la population : le nombre de clones parfaits
	 */
	private int nbEnfantsSexe;
	
	/**
	 * le nombre d'individus
	 */
	private int nbIndividus;
	
	//-------------------------------------------------------------------------------------------
	//autres variables
	
	/**
	 * le numero de la generation
	 */
	private int id;
	
	/**
	 * le nombre de tics d'une evaluation
	 */
	protected int nbTics;
	
	/**
	 * le nom de la simulation
	 */
	private String nomSimulation;
	
	/**
	 * la liste d'individus d'une generation
	 */
	private Individu[] population;
	
	/**
	 * expression lambda pour l'epreuve des individus.
	 */
	private Epreuve epreuve;
	
	
	//-------------------------------------------------------------------------------------------
	//constructeurs
	
	/**
	 * genere une nouvelle generation a partir d'un individu donné
	 * @param originel
	 * @param nbClonesParfaits
	 * @param nbClonesMutes
	 * @param nbEnfantsSexe
	 */
	public Generation(Individu originel,
			int nbClonesParfaits, int nbClonesMutes, int nbEnfantsSexe, 
			int nbTics, Epreuve epreuve, String nomSimulation) {
		this.nbClonesParfaits=nbClonesParfaits;
		this.nbClonesMutes=nbClonesMutes;
		this.nbEnfantsSexe=nbEnfantsSexe;
		this.nbIndividus=nbClonesParfaits+nbClonesMutes+nbEnfantsSexe;
		this.id=1;
		this.nbTics=nbTics;
		this.nomSimulation=nomSimulation;
		this.population=new Individu[nbIndividus];
		this.epreuve=epreuve;
		//creation d'une nouvelle generation
		for (int i=0; i<nbIndividus; i++) {
			population[i]=new CloneMute(originel);
		}
		evaluation();
	}

		
	
	/**
	 * constructeur pour recreer une generation a partir de fichiers
	 * @param nomSimulation le nom de la simulation a restaurer
	 * @param generation la generation d'ou reprendre la simulation
	 * @throws IOException 
	 */
	public Generation(String nomSimulation, int idGeneration, Epreuve epreuve) throws IOException {
		//recherche de la simulation et de la generation en accedant aux fichiers
		String path="enregistrements/simulation"+nomSimulation+"/generation"+idGeneration+"/";
		//fichiers de la generation
		String sim = Files.readString(Paths.get(path+"infos_gen"+idGeneration+".json"));
		this.epreuve=epreuve;
		this.nomSimulation=sim.substring(17, sim.indexOf(",\"nbTics\""));
		this.nbTics=Integer.parseInt(sim.substring(
				sim.indexOf("\"nbTics\":")+9, 
				sim.indexOf(",\"generation\"")));
		this.id=Integer.parseInt(sim.substring(
				sim.indexOf("\"generation\":")+13, 
				sim.indexOf(",\"nbClonesParfaits\"")));
		this.nbClonesParfaits=Integer.parseInt(sim.substring(
				sim.indexOf("\"nbClonesParfaits\":")+19, 
				sim.indexOf(",\"nbClonesMutes\"")));
		this.nbClonesMutes=Integer.parseInt(sim.substring(
				sim.indexOf("\"nbClonesMutes\":")+16, 
				sim.indexOf(",\"nbEnfantsSexe\"")));
		this.nbEnfantsSexe=Integer.parseInt(sim.substring(
				sim.indexOf("\"nbEnfantsSexe\":")+16, 
				sim.indexOf(",\"nbIndividus\"")));
		this.nbIndividus=nbClonesParfaits+nbClonesMutes+nbEnfantsSexe;
		this.population=new Individu[nbIndividus];
		int graine=Integer.parseInt(sim.substring(
				sim.indexOf("\"graine\":")+9, 
				sim.indexOf(",\"tauxCreation\"")));
		String content;
		int premierId=nbIndividus*(id-1);
		//la population
		for(int i=1; i<=nbIndividus; i++) {
			//chercher le fichier en question
			content=Files.readString(Paths.get(path+"individu"+(premierId+i)+".json"));
			this.population[i-1]=new Sauvegarde(content, graine,
					new Mutation(sim.substring(sim.indexOf("\"mutations\":")+12)));
		}
		evaluation();
		triScore(population);
	}
	
	//----------------------------------------------------------------------------------------
	//fonction de changement de generation
	
	/**
	 * fonction qui genere la generation suivante
	 */
	public void nextGen() {
		triScore(population);
		
		Individu[] newPopulation=new Individu[nbIndividus];
		
		for(int i=0; i<nbClonesParfaits; i++) {
			newPopulation[i]=new CloneParfait(population[i]);
		}
		//clones mutes
		for(int i=0; i<nbClonesMutes; i++) {
			newPopulation[nbClonesParfaits+i]=new CloneMute(population[i]);
		}
		//reproduction sexuee
		for (int i=0; i<nbEnfantsSexe*2; i+=2) {
			newPopulation[nbClonesParfaits + nbClonesMutes + i/2]=
					new EnfantSexe(population[i % nbIndividus], 
							population[i+1 % nbIndividus]);
		}
		this.population=newPopulation;
		this.id++;
		//on evalue automatiquement la population a la fin
		evaluation();
	}
	
	//------------------------------------------------------------------------------------------
	//fonctions prives pratiques
	
	/**
	 * tri de la population en fonction du score
	 * @param population la population à trier
	 */
	private void triScore(Individu[] population) {
		//creation d'une liste chainee d'individus
		ListeChaine<Individu> liste=new ListeChaine<>();
		for (int i=0; i<nbIndividus; i++) {
			liste.ajout(population[i]);
		}
		//tri de la population selon le score
		Carracteristique<Individu> car = elt -> elt.getScore()*(-1);
		//pour avoir les meilleurs au debut
		liste.triRapide(car); //ceux avec le plus grand score sont au debut
		//application du tri a la liste originale
		Individu ind=liste.getSuivant();
		int i=0;
		while(ind!=null) {
			population[i]=ind;
			i++;
			ind=liste.getSuivant();
		}
	}
	
	
	//-----------------------------------------------------------------------------------------
	//getteurs
	
	/**
	 * getteur pour la population
	 * @return population
	 */
	public Individu[] getPopulation() {
		return population;
	}
	
	//-------------------------------------------------------------------------------------------
	//fonctions d'evaluation
	
	
	/**
	 * fonction pour l'evaluation des individus
	 */
	private void evaluation() {
		for (int i=0; i<nbIndividus; i++) {
			epreuve.epreuve(population[i]);
		}
	}
	
	//------------------------------------------------------------------------------------------
	//fonction d'affichage
	
	/**
	 * fonction toStringJson qui genere un descriptif a la syntaxe Json
	 * @return un string compatible au json
	 */
	public String toStringJson() {
		return "{"
		+ "\"nomSimulation\":" + nomSimulation + ","
		+ "\"nbTics\":" + nbTics + ","
		+ "\"generation\":" + id + ","
		+ "\"nbClonesParfaits\":" + nbClonesParfaits + ","
		+ "\"nbClonesMutes\":" + nbClonesMutes + ","
		+ "\"nbEnfantsSexe\":" + nbEnfantsSexe + ","
		+ "\"nbIndividus\":" + nbIndividus + ","
		+ "\"mutations\":" + Individu.getMutationToStringJson()
		+"}";
	}
	
	//-------------------------------------------------------------------------------------------
	//fonctions d'enregistrements
	
	/**
	 * fonction pour enregistrer toute une generation dans des fichiers au format json.
	 */
	public void enregistre() {
		//creer un fichier generation info
        try {
        	//si le dossier n'existe pas on le créé
        	File f=new File("enregistrements\\simulation" + nomSimulation
        			+ "\\generation" + id + "\\");
        	f.mkdirs();
            PrintWriter writer = new PrintWriter(
            		"enregistrements\\simulation" + nomSimulation
            		+ "\\generation" + id
            		+ "\\infos_gen" + id + ".json");
            writer.write(this.toStringJson());
            
            writer.flush();
            writer.close();

            for (int i=0; i<nbIndividus; i++) {
            	PrintWriter writer2 = new PrintWriter(
                		"enregistrements\\simulation" + nomSimulation
                		+ "\\generation" + population[i].getGeneration()
                		+ "\\individu" + population[i].getId() + ".json");
                writer2.write(population[i].toStringJson());
                writer2.flush();
                writer2.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

	}
}
