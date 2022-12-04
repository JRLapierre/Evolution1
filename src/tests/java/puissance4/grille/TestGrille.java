package puissance4.grille;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TestGrille {

	@Test
	void test() {
		Grille g=new Grille();
		System.out.println(g);
		g.ajoutePion(new PionO(), 1);
		System.out.println(g);

	}
	
	@Test
	@DisplayName("test de victoire horisontale")
	void testVictoireHorisontal() {
		Grille g=new Grille();
		g.ajoutePion(new PionO(), 1);
		g.ajoutePion(new PionX(), 1);
		g.ajoutePion(new PionX(), 1);
		g.ajoutePion(new PionX(), 1);
		assertEquals(false, g.gagne(new PionX(), 1));
		g.ajoutePion(new PionX(), 1);
		assertEquals(true, g.gagne(new PionX(), 1));
	}
	
	@Test
	@DisplayName("Test de la victoire verticale")
	void testVictoireVerticale() {
		Grille g=new Grille();
		g.ajoutePion(new PionO(), 1);
		g.ajoutePion(new PionX(), 2);
		g.ajoutePion(new PionX(), 3);
		g.ajoutePion(new PionX(), 4);
		assertEquals(false, g.gagne(new PionX(), 4));
		g.ajoutePion(new PionX(), 5);
		assertEquals(true, g.gagne(new PionX(), 5));
	}
	
	@Test
	@DisplayName("test de la victoire diagonale montante")
	void testVictoireDiagonaleMontante() {
		Grille g=new Grille();
		g.ajoutePion(new PionO(), 2);
		g.ajoutePion(new PionX(), 3);
		g.ajoutePion(new PionO(), 3);
		g.ajoutePion(new PionX(), 4);
		g.ajoutePion(new PionX(), 4);
		g.ajoutePion(new PionO(), 4);
		g.ajoutePion(new PionX(), 5);
		g.ajoutePion(new PionX(), 5);
		g.ajoutePion(new PionX(), 5);
		assertEquals(false, g.gagne(new PionO(), 5));
		g.ajoutePion(new PionO(), 5);
		System.out.println(g);
		assertEquals(true, g.gagne(new PionO(), 5));
	}
	
	@Test
	@DisplayName("test de la victoire diagonale descendante")
	void testVictoireDiagonaleDescendante() {
		Grille g=new Grille();
		g.ajoutePion(new PionO(), 5);
		g.ajoutePion(new PionX(), 4);
		g.ajoutePion(new PionO(), 4);
		g.ajoutePion(new PionX(), 3);
		g.ajoutePion(new PionX(), 3);
		g.ajoutePion(new PionO(), 3);
		g.ajoutePion(new PionX(), 2);
		g.ajoutePion(new PionX(), 2);
		g.ajoutePion(new PionX(), 2);
		assertEquals(false, g.gagne(new PionO(), 2));
		g.ajoutePion(new PionO(), 2);
		System.out.println(g);
		assertEquals(true, g.gagne(new PionO(), 2));
	}

}
