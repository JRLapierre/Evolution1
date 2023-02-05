package puissance4.jeu;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import puissance4.joueurs.Joueur;
import puissance4.joueurs.JoueurHumain;
import puissance4.joueurs.JoueurIndividu;
import simulation.generation.individus.Individu;
import simulation.generation.individus.Sauvegarde;
import simulation.generation.individus.cerveau.Cerveau;

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
	
	/**
	 * cree une partie de puissance4 entre nous et un individu enregistre
	 * @param nomSimulation la simulation de laquelle l'individu est sorti
	 * @param generation la generation de l'individu
	 * @param id l'id de l'individu
	 * @param format bin ou json
	 * @throws IOException si le fichier n'existe pas
	 */
	public DuelVsIndividu(String nomSimulation, int generation, int id, String format) throws IOException {
		//A NE PAS LANCER DURANT UNE SIMULATION DEJA EXISTANTE
		Cerveau c;
		String chemin="enregistrements/simulation" + nomSimulation + 
				"/generation"+generation + 
				"/individu" + id + "." + format;
		switch(format) {
		case("json"):
			c=new Sauvegarde(Files.readString(Paths.get(chemin)), null).getCerveau();
			break;
		case("bin"):
			//recuperation du contenu du fichier
			byte[] sim = Files.readAllBytes(Paths.get(chemin));
			ByteBuffer bb;
			bb=ByteBuffer.allocate(sim.length);
			bb.put(sim);
			bb.flip();
			c=Individu.regenereIndividu(bb).getCerveau();
			break;
		default:
			throw new FileNotFoundException("le fichier " + chemin + 
					" n'existe pas ou n'est pas du bon format");
		}
		individu=new JoueurIndividu(c, 25);
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
