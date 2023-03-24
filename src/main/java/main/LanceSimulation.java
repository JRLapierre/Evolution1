package main;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

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
	 * le bouton qui permet de controller la pause
	 */
	private static JButton playPause;
	
	/**
	 * le bouton qui permet d'arreter la simulation
	 */
	private static JButton stop;
	
	/**
	 * JLabel qui affiche si la simulation tourne ou est en pause
	 */
	private static JLabel etatSimulation;
	
	/**
	 * JLabel qui affiche quelle generation est actuellement simulee
	 */
	private static JLabel labelGeneration;
	
	/**
	 * JLabel qui affiche la phase dans la simulation
	 */
	private static JLabel labelPhase;
	
	/**
	 * JTextArea qui affiche un paragraphe
	 */
	private static JTextArea zoneTexte; 
	
	//-------------------------------------------
	//elements d'affichage
	
	/**
	 * le panel qui permet d'avoir les boutons horisontalement
	 */
	private static JPanel boutonsPanel;
	
	/**
	 * le panel qui arrange ses elements verticalements
	 */
	private static JPanel panel;
	
	/**
	 * le boxLayout contenant les elements
	 */
	private static BoxLayout boxLayout;
	
	/**
	 * la fenetre affichant le tout
	 */
	private static JFrame fenetre;
	
	//-------------------------------------------
	//objets de la simulation
	
	/**
	 * l'objet simulation lui-même
	 */
	private static Simulation simulation;
	
	//-----------------------------------------------------------------------------------------
	//fonction d'initialisation
	
	/**
	 * initialise la vue
	 */
	private static void init() {
    	
    	//les elements a afficher
    	playPause = new JButton("play/pause");
        stop = new JButton("arret");
        etatSimulation = new JLabel();
        labelGeneration = new JLabel();
        labelPhase = new JLabel();
        zoneTexte = new JTextArea();
        zoneTexte.setEditable(false);
        
        //le pannel contenant les boutons
        boutonsPanel = new JPanel();
        boutonsPanel.add(playPause);
        boutonsPanel.add(stop);
        
        //le pannel rassemblant tous les elements
        panel = new JPanel();
        boxLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxLayout);
        panel.add(boutonsPanel);
        panel.add(etatSimulation);
        panel.add(labelGeneration);
        panel.add(labelPhase);
        panel.add(zoneTexte);
        
        //la fenetre permettant d'afficher tout
        JFrame fenetre = new JFrame();
        fenetre.add(panel);
        fenetre.setSize(400, 400);
        fenetre.setVisible(true);
        
        //le programme principal
        simulation = new SimulationInitiale(labelGeneration, labelPhase, zoneTexte);

        //les actions des boutons
        playPause.addActionListener(e -> {
        	simulation.playPause();
        	if(simulation.estEnPause()) {
        		while(simulation.getState() != Thread.State.WAITING);//attendre
        		etatSimulation.setText("programme mis en pause");
        	}else {
        		etatSimulation.setText("programme en cours ...");
        	}
        });
        
        stop.addActionListener(e-> {
        	if(!simulation.estEnPause()) simulation.playPause();
        	simulation.finProgramme();
    		fenetre.dispose();
        });
   	}
	
	/**
	 * fonction main qui permet de lancer le programme.
	 * @param args rien a mettre
	 */
	public static void main(String[] args) {
		init();
		if(!simulation.choix()) return; //arrete le programme si le choix n'est pas fait
		simulation.start();
	}

}
