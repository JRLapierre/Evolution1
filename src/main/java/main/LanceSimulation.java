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
	
	private static JButton playPause;
	private static JButton stop;
	private static JLabel etatSimulation;
	private static JLabel labelGeneration;
	private static JLabel labelPhase;
	private static JPanel boutonsPanel;
	private static JPanel panel;
	private static BoxLayout boxLayout;
	private static JFrame fenetre;
	private static  Simulation simulation;
	
	//-----------------------------------------------------------------------------------------
	//fonction d'initialisation
	
	private static void init() {
    	
    	//les elements a afficher
    	playPause = new JButton("play/pause");
        stop = new JButton("arret");
        etatSimulation = new JLabel(" ");
        labelGeneration = new JLabel(" ");
        labelPhase = new JLabel(" ");
        
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
        
        //la fenetre permettant d'afficher tout
        JFrame fenetre = new JFrame();
        fenetre.add(panel);
        fenetre.pack();
        fenetre.setVisible(true);
        
        //le programme principal
        simulation = new SimulationInitiale(labelGeneration, labelPhase);

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
