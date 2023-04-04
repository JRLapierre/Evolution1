package outils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import simulation.generation.individus.Individu;

/**
 * permet de decoder le fichier binaire d'un individu.
 * affiche dans le terminal sa representation json.
 * @author jrl
 *
 */
public class Decode {
	
	/**
	 * constructeur prive
	 */
	private Decode() {
		throw new IllegalStateException("classe utilitaire");
	}
	
	//-----------------------------------------------------------------------------------------
	//des zones de texte pour retrouver le fichier
	
	/**
	 * zone de texte pour le nom de la simulation
	 */
	private static JTextField fieldNomSimulation=new JTextField("nom de la simulation");
	
	/**
	 * zone de texte pour le numero de la generation
	 */
	private static JTextField fieldNumeroGeneration=new JTextField("numero de la generation");
	
	/**
	 * zone de texte pour l'id de l'individu
	 */
	private static JTextField fieldIdIndividu=new JTextField("id de l'individu");
	
	//-----------------------------------------------------------------------------------------
	//autres elements
	
	/**
	 * un bouton pour valider une demande
	 */
	private static JButton valider=new JButton("rechercher");
	
	//un panel qui aligne les elements de la recherche
	private static JPanel panelRecherche=new JPanel();
	
	/**
	 * une zone de texte pour afficher le resultat json
	 */
	private static JTextArea zoneTexte = new JTextArea();

	
	
	/**
	 * une methode statique pour initialiser
	 * @param panel le panel sur lequel tout va etre affiche
	 */
	public static void initialise(JPanel panel) {
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panelRecherche.setLayout(new BoxLayout(panelRecherche, BoxLayout.X_AXIS));
		panelRecherche.add(fieldNomSimulation);
		panelRecherche.add(fieldNumeroGeneration);
		panelRecherche.add(fieldIdIndividu);
		panelRecherche.add(valider);
		panel.add(panelRecherche);
		panel.add(zoneTexte);
        zoneTexte.setEditable(false);
        zoneTexte.setLineWrap(true);
        //l'action du bouton
		valider.addActionListener(e->
			trouveIndividu(fieldNomSimulation.getText(), 
					fieldNumeroGeneration.getText(), 
					fieldIdIndividu.getText())
		);
	}
	
	
	/**
	 * une methode qui se declence quand on clique sur le boutton
	 * @param nomSimulation le nom de la simulation
	 * @param numeroGeneration le numero de la generation
	 * @param idIndividu l'id de l'individu
	 */
	private static void trouveIndividu(String nomSimulation, String numeroGeneration, String idIndividu) {
		String path="enregistrements/simulation" + nomSimulation
				+ "/generation" + numeroGeneration + 
				"/individu" + idIndividu + ".bin";
		//fichier de la generation
		byte[] sim;
		try {
			sim = Files.readAllBytes(Paths.get(path));
			ByteBuffer b = ByteBuffer.allocate(sim.length);
			b.put(sim);
			b.flip();
			//recreer l'individu
			Individu i=Individu.regenereIndividu(b);
			zoneTexte.setText(i.toStringJson());
		} catch (IOException e) {
			zoneTexte.setText("le fichier "+path+" n'a pas été trouvé !");
		}
	}
	
}
