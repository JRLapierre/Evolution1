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
 * cette classe repr�sente une g�n�ration.
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
	 * distribution de la population : le nombre de clones mut�s
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
	
	/**
	 * le rang maximal des individus pouvant se reproduire
	 */
	private int butoir;
	
	
	//-------------------------------------------------------------------------------------------
	//constructeurs
	
	/**
	 * genere une nouvelle generation a partir d'un individu donn�
	 * @param originel
	 * @param nbClonesParfaits
	 * @param nbClonesMutes
	 * @param nbEnfantsSexe
	 */
	public Generation(
			Individu originel,
			int nbClonesParfaits, 
			int nbClonesMutes, 
			int nbEnfantsSexe, 
			int butoir, 
			Epreuve epreuve, 
			String nomSimulation) {
		this.nbClonesParfaits=nbClonesParfaits;
		this.nbClonesMutes=nbClonesMutes;
		this.nbEnfantsSexe=nbEnfantsSexe;
		this.nbIndividus=nbClonesParfaits+nbClonesMutes+nbEnfantsSexe;
		this.nomSimulation=nomSimulation;
		this.population=new Individu[nbIndividus];
		this.epreuve=epreuve;
		this.butoir=butoir;
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
	public Generation(String nomSimulation, int numero, Epreuve epreuve) throws IOException {
		//recherche de la simulation et de la generation en accedant aux fichiers
		String path="enregistrements/simulation"+nomSimulation+"/";
		//fichiers de la generation
		String sim = Files.readString(Paths.get(path+"infos.json"));
		this.epreuve=epreuve;
		this.nomSimulation=sim.substring(18, sim.indexOf("\",\"nbClonesParfaits\""));
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
		this.butoir=Integer.parseInt(sim.substring(
				sim.indexOf("\"butoir\":")+9, 
				sim.indexOf(",\"mutations\"")));
		this.population=new Individu[nbIndividus];
		int graine=Integer.parseInt(sim.substring(
				sim.indexOf("\"graine\":")+9, 
				sim.indexOf(",\"tauxCreation\"")));
		String content;
		int premierId=nbIndividus*(numero-1);
		//la population
		for(int i=1; i<=nbIndividus; i++) {
			//chercher le fichier en question
			content=Files.readString(Paths.get(
					path + "generation"+numero+"/individu"+(premierId+i)+".json"));
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
			newPopulation[i]=new CloneParfait(population[i % butoir]);
		}
		//clones mutes
		for(int i=0; i<nbClonesMutes; i++) {
			newPopulation[nbClonesParfaits+i]=new CloneMute(population[i % butoir]);
		}
		//reproduction sexuee
		for (int i=0; i<nbEnfantsSexe*2; i+=2) {
			newPopulation[nbClonesParfaits + nbClonesMutes + i/2]=
					new EnfantSexe(population[(i % nbIndividus) % butoir], 
							population[(i+1 % nbIndividus) % butoir]);
		}
		this.population=newPopulation;
		//on evalue automatiquement la population a la fin
		evaluation();
	}
	
	//------------------------------------------------------------------------------------------
	//fonctions prives pratiques
	
	/**
	 * tri de la population en fonction du score
	 * @param population la population � trier
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
	
	
	//----------------------------------------------------------------------------------------
	//setteurs pour changer les parametres de la simulation en cours
	
	/**
	 * @param nbClonesParfaits the nbClonesParfaits to set
	 */
	public void setNbClonesParfaits(int nbClonesParfaits) {
		this.nbClonesParfaits = nbClonesParfaits;
		this.nbIndividus=nbClonesParfaits+nbClonesMutes+nbEnfantsSexe;
	}

	/**
	 * @param nbClonesMutes the nbClonesMutes to set
	 */
	public void setNbClonesMutes(int nbClonesMutes) {
		this.nbClonesMutes = nbClonesMutes;
		this.nbIndividus=nbClonesParfaits+nbClonesMutes+nbEnfantsSexe;
	}

	/**
	 * @param nbEnfantsSexe the nbEnfantsSexe to set
	 */
	public void setNbEnfantsSexe(int nbEnfantsSexe) {
		this.nbEnfantsSexe = nbEnfantsSexe;
		this.nbIndividus=nbClonesParfaits+nbClonesMutes+nbEnfantsSexe;
	}

	/**
	 * @param epreuve the epreuve to set
	 */
	public void setEpreuve(Epreuve epreuve) {
		this.epreuve = epreuve;
	}

	/**
	 * @param butoir the butoir to set
	 */
	public void setButoir(int butoir) {
		this.butoir = butoir;
	}
	
	/**
	 * setteur pour changer les mutations en pleine simulation
	 * @param mutation les mutations a changer
	 */
	public void setMutations(Mutation mutation) {
		Individu.setMutation(mutation);
	}
	
	
	//-------------------------------------------------------------------------------------------
	//fonctions d'evaluation
	
	
	/**
	 * fonction pour l'evaluation des individus
	 */
	private void evaluation() {
		epreuve.epreuve(population);
	}
	
	//------------------------------------------------------------------------------------------
	//fonction d'affichage



	/**
	 * fonction toStringJson qui genere un descriptif a la syntaxe Json
	 * @return un string compatible au json
	 */
	public String toStringJson() {
		return "{"
		+ "\"nomSimulation\":\"" + nomSimulation + "\","
		+ "\"nbClonesParfaits\":" + nbClonesParfaits + ","
		+ "\"nbClonesMutes\":" + nbClonesMutes + ","
		+ "\"nbEnfantsSexe\":" + nbEnfantsSexe + ","
		+ "\"nbIndividus\":" + nbIndividus + ","
		+ "\"butoir\":" + butoir + ","
		+ "\"mutations\":" + Individu.getMutationToStringJson()
		+"}";
	}
	
	//-------------------------------------------------------------------------------------------
	//fonctions d'enregistrements
	
	//une fonction separee pour enregistrer des infos sur la simulation
	public void enregistreInfos() {
		//creer un fichier generation info
        try {
        	//si le dossier n'existe pas on le cr��
        	File f=new File("enregistrements\\simulation" + nomSimulation
        			+ "\\generation" + population[0].getGeneration() + "\\");
        	f.mkdirs();
            PrintWriter writer = new PrintWriter(
            		"enregistrements\\simulation" + nomSimulation
            		+ "\\infos.json");
            writer.write(this.toStringJson());
            
            writer.flush();
            writer.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

	}
	
	/**
	 * fonction pour enregistrer toute une generation dans des fichiers au format json.
	 */
	public void enregistreGeneration() {
		//creer un fichier generation info
        try {
        	//si le dossier n'existe pas on le cr��
        	File f=new File("enregistrements\\simulation" + nomSimulation
        			+ "\\generation" + population[0].getGeneration() + "\\");
        	f.mkdirs();

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
