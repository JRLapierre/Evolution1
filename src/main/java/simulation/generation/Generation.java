package simulation.generation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import outils.Aleatoire;
import outils.Carracteristique;
import outils.ListeChaine;
import simulation.Enregistrable;
import simulation.generation.individus.CloneMute;
import simulation.generation.individus.CloneParfait;
import simulation.generation.individus.EnfantSexe;
import simulation.generation.individus.Individu;
import simulation.generation.individus.Sauvegarde;
import simulation.generation.individus.cerveau.Cerveau;
import simulation.generation.individus.cerveau.Mutation;

/**
 * cette classe représente une génération.
 * @author jrl
 *
 */
public class Generation implements Enregistrable {

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
	 * genere une nouvelle generation a partir d'un individu donné
	 * @param originel 			le premier individu de la simulation
	 * @param nbClonesParfaits 	le nombre de clones parfaits a chaque generation
	 * @param nbClonesMutes 	le nombre de clones mutes a chaque generation
	 * @param nbEnfantsSexe 	le nombre d'enfants de reproduction sexuee a chaque generation
	 * @param butoir 			la limite dans le classement pour pouvoir se reproduire
	 * @param epreuve 			l'epreuve pour evaluer les individus
	 * @param nomSimulation 	le nom de la simulation
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
		//creation d'une nouvelle population
		for (int i=0; i<nbIndividus; i++) {
			population[i]=new CloneMute(originel);
		}
	}

		
	
	/**
	 * constructeur pour recreer une generation a partir de fichiers
	 * @param nomSimulation le nom de la simulation a restaurer
	 * @param numero la generation d'ou reprendre la simulation
	 * @param format bin ou json
	 * @param epreuve l'epreuve
	 * @throws IOException si le fichier n'est pas trouve
	 */
	public Generation(String nomSimulation, int numero, String format, Epreuve epreuve) throws IOException {
		this.nomSimulation=nomSimulation;
		switch(format) {
		case("json"):
			decodeJson(nomSimulation, numero);
			break;
		case("bin"):
			decodeBin(nomSimulation, numero);
			break;
		default:
			System.err.println("le format n'existe pas");
		}
		this.epreuve=epreuve;
		triScore(population);
	}
	
	
	
	//----------------------------------------------------------------------------------------
	//decodeurs
	
	/**
	 * decodeur pour le format json
	 * @param nomSimulation
	 * @param numero le numero de la simulation
	 * @throws IOException si le fichier n'est pas trouve
	 */
	private void decodeJson(String nomSimulation, int numero) throws IOException {
		//recherche de la simulation et de la generation en accedant aux fichiers
		String path="enregistrements/simulation"+nomSimulation+"/";
		//fichier de la generation
		String sim = Files.readString(Paths.get(path+"infos.json"));
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
		//generation des individus sauvegardes
		String content;
		File[] fichiers=new File(path + "generation"+numero+"/").listFiles();
		//la population
		for(int i=0; i<fichiers.length; i++) {
			//chercher le fichier en question
			content=Files.readString(Paths.get(
					path + "generation" + numero + "/" + fichiers[i].getName()));
			this.population[i]=new Sauvegarde(content,
					new Mutation(sim.substring(sim.indexOf("\"mutations\":")+12)));
		}
	}
	
	
	/**
	 * decodeur pour le format bin
	 * @param nomSimulation
	 * @param numero le numero de la generation
	 * @throws IOException si le fichier n'est pas trouve
	 */
	private void decodeBin(String nomSimulation, int numero) throws IOException {
		//recherche de la simulation et de la generation en accedant aux fichiers
		String path="enregistrements/simulation"+nomSimulation+"/";
		//fichier de la generation
		byte[] sim = Files.readAllBytes(Paths.get(path+"infos.bin"));
		ByteBuffer bb;
		bb=ByteBuffer.allocate(sim.length);
		bb.put(sim);
		//recuperation des donnees de la generation
		bb.flip();
		this.nbClonesParfaits=bb.getInt();
		this.nbClonesMutes=bb.getInt();
		this.nbEnfantsSexe=bb.getInt();
		this.nbIndividus=nbClonesParfaits + nbClonesMutes + nbEnfantsSexe;
		this.butoir=bb.getInt();
		this.population=new Individu[nbIndividus];
		//regeneration des mutations
		Cerveau.mutation=new Mutation(bb);
		//generation des individus sauvegardes
		byte[] contenu;
		File[] fichiers=new File(path + "generation"+numero+"/").listFiles();
		//la population
		for(int i=0; i<fichiers.length; i++) {
			//chercher le fichier en question
			contenu=Files.readAllBytes(Paths.get(
					path + "generation" + numero + "/" + fichiers[i].getName()));
			bb=ByteBuffer.allocate(contenu.length);
			bb.put(contenu);
			bb.flip();
			this.population[i]=Individu.regenereIndividu(bb);
		}
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
			newPopulation[i]=new CloneParfait(population[(i % butoir) % population.length]);
		}
		//clones mutes
		for(int i=0; i<nbClonesMutes; i++) {
			newPopulation[nbClonesParfaits+i]=new CloneMute(population[(i % butoir) % population.length]);
		}
		//melange d'une partie de la liste
		Aleatoire alea=new Aleatoire(population[0].getId());
		int limite=butoir%nbIndividus;//si le butoir est trop eleve
		Individu indTemp;
		int intTemp1;
		int intTemp2;
		for(int i=0; i<limite*5; i++) {
			intTemp1=alea.aleatInt(limite-1);
			intTemp2=alea.aleatInt(limite-1);
			indTemp=population[intTemp1];
			population[intTemp1]=population[intTemp2];
			population[intTemp2]=indTemp;
		}
		//reproduction sexuee
		for (int i=0; i<nbEnfantsSexe*2; i+=2) {
			newPopulation[nbClonesParfaits + nbClonesMutes + i/2]=
					new EnfantSexe(
							population[((i % nbIndividus) % butoir) % population.length], 
							population[((i+1 % nbIndividus) % butoir) % population.length]);
		}
		this.population=newPopulation;
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
		for (int i=0; i<population.length; i++) {
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
		Cerveau.mutation=mutation;
	}
	
	
	//-------------------------------------------------------------------------------------------
	//fonctions d'evaluation
	
	
	/**
	 * fonction pour l'evaluation des individus
	 */
	public void evaluation() {
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
		+ "\"mutations\":" + Cerveau.mutation.toStringJson()
		+"}";
	}
	
	
	@Override
	public byte[] toByte() {
		ByteBuffer bb=ByteBuffer.allocate(toByteLongueur());
		bb.putInt(nbClonesParfaits);
		bb.putInt(nbClonesMutes);
		bb.putInt(nbEnfantsSexe);
		bb.putInt(butoir);
		bb.put(Cerveau.mutation.toByte());
		return bb.array();
	}
	
	@Override
	public int toByteLongueur() {
		return 28;//12 pour mutation, 16 pour les autres infos
	}
	
	//-------------------------------------------------------------------------------------------
	//fonctions d'enregistrements
	
	/**
	 * une fonction separee pour enregistrer des infos sur la simulation
	 * @param format bin ou json
	 */
	public void enregistreInfos(String format) {
		//creer un fichier generation info
        try {
        	//si le dossier n'existe pas on le créé
        	File f=new File("enregistrements\\simulation" + nomSimulation
        			+ "\\generation" + population[0].getGeneration() + "\\");
        	f.mkdirs();
        	String path="enregistrements\\simulation" + nomSimulation + "\\infos." + format;
            switch(format) {
            case("bin"):
            	FileOutputStream fos = new FileOutputStream(path);
            	fos.write(this.toByte());
            	fos.flush();
            	fos.close();
            	break;
            case("json"):
                PrintWriter writer = new PrintWriter(path);
            	writer.write(this.toStringJson());
                writer.flush();
                writer.close();
                break;
            default:
            	System.err.println("type de document inconnu");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

	}
	
	/**
	 * fonction pour enregistrer toute une generation dans des fichiers au format json.
	 * @param format bin ou json
	 */
	public void enregistreGeneration(String format) {
        String path="enregistrements/simulation" + nomSimulation
           		+ "/generation" + population[0].getGeneration() + "/";
        //si le dossier n'existe pas on le créé
        File f=new File(path);
        f.mkdirs();
        for(Individu individu : population) {
        	individu.enregistre(path, format);
        }
	}
}
