package puissance4.jeu;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import core.generation.individus.Sauvegarde;
import puissance4.joueurs.Joueur;
import puissance4.joueurs.JoueurHumain;
import puissance4.joueurs.JoueurIndividu;

/**
 * cette classe contient tous les elements necessaires pour lancer un duel contre 
 * un individu enregistre.
 * @author jrl
 *
 */
public class DuelVsIndividu {

	/**
	 * l'individu reconstitue
	 */
	Joueur individu;
	
	public DuelVsIndividu(String nomSimulation, int generation, int id) throws IOException {
		//A NE PAS LANCER DURANT UNE SIMULATION DEJA EXISTANTE
		individu=new JoueurIndividu('O',
		new Sauvegarde(Files.readString(Paths.get(
				"enregistrements/simulation" + nomSimulation + 
				"/generation"+generation + 
				"/individu" + id + ".json")),
		0,
		null
		).getCerveau(),
		25);
	}
	
	/**
	 * lance un duel entre un joueur et une AI
	 * @param commencer mettre true si on veut commencer
	 * @throws Exception si un des joueurs ne sais pas jouer
	 */
	public void duel(boolean commencer) throws Exception {
		Joueur j;
		if(commencer) {
			j=Partie.jeu(individu, new JoueurHumain('X'));
		}
		else {
			j=Partie.jeu( new JoueurHumain('X'), individu);
		}
		if(j==individu) System.out.println("l'individu a gagné");
		else System.out.println("vous avez gagné");
	}
	
}
