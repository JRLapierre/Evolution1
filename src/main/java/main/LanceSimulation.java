package main;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import outils.Decode;
import simulation.Simulation;
import simulation.SimulationInitiale;

/**
 * cette classe permet de lancer une simulation.
 * @author jrl
 *
 */
public class LanceSimulation {

	//-----------------------------------------------------------------------------------------
	//objets
	
	//----------------------------------
	//elements affiches
	
	/**
	 * le bouton qui permet d'arreter la simulation
	 */
	private static JButton stop;
	
	/**
	 * le bouton qui permet de lancer une simulation
	 */
	private static JButton boutonSimulation;
	
	/**
	 * le bouton pour lancer le decodage d'un fichier
	 */
	private static JButton boutonDecodage;
	
	//-------------------------------------------
	//elements d'affichage
	
	/**
	 * le panel global qui arrange ses elements verticalements
	 */
	private static JPanel panel;
	
	/**
	 * le panel qui permet d'afficher les boutons des sous applications horisontalement
	 */
	private static JPanel panelOptions;
	
	/**
	 * le panel du programme
	 */
	private static JPanel panelProgramme;
	
	/**
	 * la fenetre affichant le tout
	 */
	private static JFrame fenetre=new JFrame();
	
	//-------------------------------------------
	//objets du programme
	
	/**
	 * l'objet programme lui-même
	 */
	private static Thread programme;
	
	//-----------------------------------------------------------------------------------------
	//fonction d'initialisation
	
	/**
	 * initialise les actions des bouttons
	 */
	private static void initActionsBoutons() {
		//bouton stop
        stop.addActionListener(e-> {
        	if(programme instanceof Simulation simulation) {
        		if(!simulation.estEnPause()) simulation.playPause();
        		simulation.finProgramme();
        	}
    		fenetre.dispose();
    		System.exit(0);
        });
        //bouton simulation
        boutonSimulation.addActionListener(e->{
        	programme = new SimulationInitiale(panelProgramme);
            panel.add(panelProgramme);
            panelOptions.setVisible(false);
        	if(programme instanceof Simulation simulation && !simulation.choix()) return;
        	programme.start();
        });
        //bouton decodage
        boutonDecodage.addActionListener(e->{
        	Decode.initialise(panelProgramme);
            panel.add(panelProgramme);
            panelOptions.setVisible(false);
        });
	}
	
	/**
	 * initialise la vue
	 */
	private static void init() {

    	//les elements a afficher
        stop = new JButton("arret");
        boutonSimulation=new JButton("simulation");
        boutonDecodage=new JButton("decodage");
        panelProgramme = new JPanel();
        
        //le panel des boutons des differents programmes
        panelOptions=new JPanel();
        panelOptions.setLayout(new BoxLayout(panelOptions, BoxLayout.X_AXIS));
        panelOptions.add(boutonSimulation);
        panelOptions.add(boutonDecodage);
        
        //le pannel rassemblant tous les elements
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(stop);
        panel.add(panelOptions);
        
        //les actions des boutons
        initActionsBoutons();
        
        //la fenetre permettant d'afficher tout
        fenetre.add(panel);
        fenetre.setSize(400, 400);
        fenetre.setVisible(true);

   	}
	
	/**
	 * fonction main qui permet de lancer le programme.
	 * @param args rien a mettre
	 */
	public static void main(String[] args) {
		init();

	}

}
