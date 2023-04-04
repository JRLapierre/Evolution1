package main;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
	
	private static JButton boutonSimulation;
	
	//-------------------------------------------
	//elements d'affichage
	
	/**
	 * le panel global qui arrange ses elements verticalements
	 */
	private static JPanel panel;
	
	/**
	 * le panel de la simulation
	 */
	private static JPanel panelSimulation;
	
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
        stop = new JButton("arret");
        boutonSimulation=new JButton("simulation");
        panelSimulation = new JPanel();
        
        //le pannel rassemblant tous les elements
        panel = new JPanel();
        boxLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxLayout);
        panel.add(stop);
        panel.add(boutonSimulation);
        
        //la fenetre permettant d'afficher tout
        JFrame fenetre = new JFrame();
        fenetre.add(panel);
        fenetre.setSize(400, 400);
        fenetre.setVisible(true);
        
        //le programme principal
        simulation = new SimulationInitiale(panelSimulation);

        //les actions des boutons
        stop.addActionListener(e-> {
        	if(!simulation.estEnPause()) simulation.playPause();
        	simulation.finProgramme();
    		fenetre.dispose();
        });
        
        boutonSimulation.addActionListener(e->{
            panel.add(panelSimulation);
            boutonSimulation.setVisible(false);
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
