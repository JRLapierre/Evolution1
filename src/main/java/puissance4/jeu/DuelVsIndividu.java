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
	
	/**
	 * nous
	 */
	Joueur humain=new JoueurHumain();
	
	public DuelVsIndividu(String nomSimulation, int generation, int id) throws IOException {
		//A NE PAS LANCER DURANT UNE SIMULATION DEJA EXISTANTE
		individu=new JoueurIndividu(
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
		Partie partie;
		int score=humain.getScore();
		if(commencer) {
			partie=new Partie(individu, humain);
			partie.jeu();
		}
		else {
			partie=new Partie(humain, individu);
			partie.jeu();
		}
		if(score==humain.getScore()) System.out.println("l'individu a gagné");
		else System.out.println("vous avez gagné");
		System.out.println("scores :"
				+ " \r individu : " + individu.getScore()
				+ " \r vous : " + humain.getScore());
	}
	
}
