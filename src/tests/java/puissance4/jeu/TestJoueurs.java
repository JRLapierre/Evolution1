package puissance4.jeu;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import core.generation.individus.cerveau.Cerveau;
import core.generation.individus.cerveau.Connexion;
import puissance4.joueurs.Joueur;
import puissance4.joueurs.JoueurAI1;
import puissance4.joueurs.JoueurHumain;
import puissance4.joueurs.JoueurIndividu;

class TestJoueurs {

	@Test
	@DisplayName("test d'un duel entre un joueur humain et un joueur aleatoire")
	void testJoueurHumainVsJoueurAleatoire() {
		Joueur j1=new JoueurAI1();
		Joueur j2=new JoueurAI1();
		Joueur j3=new JoueurHumain();
		
		try {
			System.out.println(Partie.jeu(j1, j2).pion);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	@DisplayName("test des joueurs Individu")
	void testJoueurIndividu() {
		Cerveau c=new Cerveau(43,7,0);
		for(int i=0; i<43; i++) {
			c.addConnexion(new Connexion(1, c.getListeInput()[i], c.getListeOutput()[i%7]));
		}
		Joueur j4=new JoueurIndividu(c, 2);
		Joueur j3=new JoueurHumain();
		Joueur j5=new JoueurAI1();

		
		try {
			Partie.jeu(j3, j5);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
	

}
