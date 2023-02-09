package puissance4.grille;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TestGrille {

	@Test
	void test() {
		Grille g=new Grille();
		System.out.println(g);
		g.ajoutePion('O', 1);
		System.out.println(g);

	}
	
	@Test
	@DisplayName("test de victoire horisontale")
	void testVictoireHorisontal() {
		Grille g=new Grille();
		g.ajoutePion('O', 1);
		g.ajoutePion('X', 1);
		g.ajoutePion('X', 1);
		g.ajoutePion('X', 1);
		assertEquals(false, g.gagne('X', 1));
		g.ajoutePion('X', 1);
		assertEquals(true, g.gagne('X', 1));
	}
	
	@Test
	@DisplayName("Test de la victoire verticale")
	void testVictoireVerticale() {
		Grille g=new Grille();
		g.ajoutePion('O', 1);
		g.ajoutePion('X', 2);
		g.ajoutePion('X', 3);
		g.ajoutePion('X', 4);
		assertEquals(false, g.gagne('X', 4));
		g.ajoutePion('X', 5);
		assertEquals(true, g.gagne('X', 5));
	}
	
	@Test
	@DisplayName("test de la victoire diagonale montante")
	void testVictoireDiagonaleMontante() {
		Grille g=new Grille();
		g.ajoutePion('O', 2);
		g.ajoutePion('X', 3);
		g.ajoutePion('O', 3);
		g.ajoutePion('X', 4);
		g.ajoutePion('X', 4);
		g.ajoutePion('O', 4);
		g.ajoutePion('X', 5);
		g.ajoutePion('X', 5);
		g.ajoutePion('X', 5);
		assertEquals(false, g.gagne('O', 5));
		g.ajoutePion('O', 5);
		System.out.println(g);
		assertEquals(true, g.gagne('O', 5));
	}
	
	@Test
	@DisplayName("test de la victoire diagonale descendante")
	void testVictoireDiagonaleDescendante() {
		Grille g=new Grille();
		g.ajoutePion('O', 5);
		g.ajoutePion('X', 4);
		g.ajoutePion('O', 4);
		g.ajoutePion('X', 3);
		g.ajoutePion('X', 3);
		g.ajoutePion('O', 3);
		g.ajoutePion('X', 2);
		g.ajoutePion('X', 2);
		g.ajoutePion('X', 2);
		assertEquals(false, g.gagne('O', 2));
		g.ajoutePion('O', 2);
		System.out.println(g);
		assertEquals(true, g.gagne('O', 2));
	}
	
	@Test
	@DisplayName("test du remplissage des colonnes")
	void testEstPleine() {
		Grille g=new Grille();
		g.ajoutePion('O', 5);
		g.ajoutePion('X', 4);
		g.ajoutePion('O', 4);
		g.ajoutePion('X', 3);
		g.ajoutePion('X', 3);
		g.ajoutePion('O', 3);
		g.ajoutePion('X', 2);
		g.ajoutePion('X', 2);
		g.ajoutePion('X', 2);
		g.ajoutePion('X', 2);
		assertEquals(false, g.colonnePleine(2));
		System.out.println("estpleine \n" + g);
		g.ajoutePion('X', 2);
		assertEquals(false, g.colonnePleine(2));
		g.ajoutePion('X', 2);
		assertEquals(true, g.colonnePleine(2));
		System.out.println("estpleine \n" + g);



	}

}
