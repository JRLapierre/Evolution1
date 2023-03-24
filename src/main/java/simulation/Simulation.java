package simulation;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import puissance4.jeu.Tournoi;
import puissance4.joueurs.Joueur;
import puissance4.joueurs.JoueurIndividu;
import simulation.generation.Epreuve;
import simulation.generation.Generation;

/**
 * cette classe abstraite represente une simulation.
 * @author jrl
 *
 */
public abstract class Simulation extends Thread {

	//-----------------------------------------------------------------------------------------
	//parametres lies a la simulation peu importe son type
	
	//parametres de la simulation
	
	/**
	 * expression lambda qui definit l'epreuve par laquelle les individus vont passer : 
	 * on envoie des signaux dans les neurones d'entree et on observe ceux qui sont en
	 * sortie. On leur fait accomplir une tache grace aux connexions de sortie et on 
	 * evalue leur performance afin de former un score.
	 * Les individus avec le plus grand score vont pouvoir se reproduire.
	 */
	protected static Epreuve epreuve=population -> {
		//creer une liste de joueurs
		Joueur[] participants=new Joueur[population.length];
		for(int i=0; i<population.length; i++) {
			participants[i]=new JoueurIndividu(population[i].getCerveau(), 25);
		}
		//lancer le tournoi
		Tournoi tournoi=new Tournoi(participants);
		tournoi.lancer();
		//recuperer les scores des joueurs
		for(int i=0; i<population.length; i++) {
			population[i].updateScore(participants[i].getScore());
		}
	};
	
	//----------------------------
	//parametres d'enregistrement
	
	/**
	 * le format des fichiers a enregistrer.
	 * Dans la situation actuelle, on a le choix entre json et bin.
	 */
	protected String typeEnregistrement="bin";
	
	/**
	 * limiteur d'enregistrement.
	 * Une generation va etre enregistree si son numero % 1 == 0.
	 * avec une valeur de 1, toutes les generations vont etre enregistrees.
	 */
	private int enregistre=1;
	
	//----------------------------
	//objets de la simulation
	
	/**
	 * l'objet porteur des generations sucessives
	 */
	protected Generation generation;
	
	//----------------------------
	//attributs pour la pause
	
	/**
	 * Ce booleen permet d'arreter la simulation.
	 * Si il passe à false, une derniere generation
	 * va s'effectuer avant de s'arreter.
	 * par defaut, la valeur est true.
	 */
    private boolean fonctionne = true;
	
    /**
     * Ce booleen permet de mettre en pause.
     * Si il est à true, la simulation est arrete
     * jusqu'a nouvel ordre.
     * Par defaut, il est a true.
     * Si il passe a false, la simulation reprends
     */
    private boolean pause = true;

    //-----------------------------
    //attributs pour l'affichage
    
	/**
	 * ce label permet d'afficher la generation 
	 * actuellement simulee.
	 */
	protected JLabel labelGeneration;
	
	/**
	 * ce label permet d'afficher la phase de 
	 * la simulation est actuellement effectuee
	 */
	protected JLabel labelPhase;
	
	/**
	 * permet d'afficher un paragraphe
	 */
	protected JTextArea zoneTexte;
	
	//-----------------------------------------------------------------------------------------
	//constructeur
	
	
	/**
	 * ce constructeur initialise une simulation. 
	 * Les parametres sont les JLabel qui vont afficher
	 * les informations relatives au fonctionnement 
	 * de la simulation
	 * @param generation le label qui affiche la generation
	 * @param phase le label qui affiche la phase de la simulation
	 */
	protected Simulation(JLabel labelGeneration, JLabel labelPhase, JTextArea zoneTexte) {
		this.labelGeneration=labelGeneration;
		this.labelPhase=labelPhase;
		this.zoneTexte=zoneTexte;
	}

	//-----------------------------------------------------------------------------------------
	//methodes permettant de controller la pause ou l'arret du programme
	
    public void playPause() {
        if(pause) reprends();
        else pause=true;
    }

    private synchronized void reprends() {
        pause = false;
        notifyAll();
    }

    public synchronized void finProgramme() {
        fonctionne = false;
        notifyAll();
    }
    
    public boolean estEnPause() {
    	return pause;
    }
    
    //-----------------------------------------------------------------------------------------
    //autres methodes
    
	/**
	 * demande a l'utilisateur si il est sur de ses choix
	 * @return true si l'utilisateur valide
	 */
	public abstract boolean choix();
	
	/**
	 * met en pause le programme si les conditions sont reunies
	 */
	protected synchronized void pause() {
        while (pause && fonctionne) {
        	try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
        }
	}
	
	/**
	 * simule une generation.
	 * @param generationActuelle le numero de la generation
	 */
	public void simuleGeneration(int generationActuelle) {
		synchronized (this) {
        	//si on a envoye l'instruction de pause
			pause();
            //si on a envoye l'instruction d'arret
            if (!fonctionne) System.exit(0);
            //simulation d'une generation
			labelGeneration.setText("===generation " + generationActuelle + "===");
			labelPhase.setText("creation de la prochaine generation...");
			generation.nextGen();
			labelPhase.setText("evaluation...");
			generation.evaluation();
			if(generationActuelle%enregistre==0) {
				labelPhase.setText("enregistrement...");
				generation.enregistreGeneration(typeEnregistrement);
			}
			labelPhase.setText("");
        }
	}
	
}
