package puissance4.grille;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TestGrille {

	@Test
	void test() {
		Grille g=new Grille();
		System.out.println(g);
		assertEquals(true, g.ajoutePion('O', 1));
		System.out.println(g);
		assertEquals(false, g.ajoutePion('O', 0));
		assertEquals(true, g.ajoutePion('O', 7));
		assertEquals(false, g.ajoutePion('O', 8));

	}
	
	@Test
	@DisplayName("test de victoire verticale")
	void testVictoireVertical() {
		Grille g=new Grille();
		g.ajoutePion('O', 1);
		g.ajoutePion('X', 1);
		g.ajoutePion('X', 1);
		g.ajoutePion('X', 1);
		System.out.println("verticale");
		assertEquals(false, g.gagne('X', 1));
		g.ajoutePion('X', 1);
		System.out.println(g);
		assertEquals(true, g.gagne('X', 1));
	}
	
	@Test
	@DisplayName("Test de la victoire horisontal")
	void testVictoireHorisontal() {
		Grille g=new Grille();
		g.ajoutePion('O', 1);
		g.ajoutePion('X', 2);
		g.ajoutePion('X', 3);
		g.ajoutePion('X', 4);
		System.out.println("horisontal");
		assertEquals(false, g.gagne('X', 4));
		g.ajoutePion('X', 5);
		System.out.println(g);
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
		System.out.println("diag mont");
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
		System.out.println("diag desc");
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
		assertEquals(false, g.ajoutePion('X', 2));
		assertEquals(false, g.ajoutePion('O', 0));
		
		Grille g2=new Grille();
		for(int i=1; i<=42; i++) {
			assertEquals(false, g2.estPleine());
			assertEquals(true, g2.ajoutePion('O', (i%7)+1));
		}
		assertEquals(true, g2.estPleine());
	}
	
	@Test
	@DisplayName("test de replique")
	void testReplique() {
		Grille g=new Grille();
		assertEquals(g, g.replique());
		g.ajoutePion('O', 5);
		g.ajoutePion('X', 4);
		g.ajoutePion('O', 4);
		g.ajoutePion('X', 3);
		assertEquals(g, g.replique());
	}

}
