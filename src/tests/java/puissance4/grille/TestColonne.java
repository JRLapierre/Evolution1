package puissance4.grille;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestColonne {

	@Test
	void test() {
		Colonne c=new Colonne();
		assertEquals(' ', c.getListe()[0]);
		c.ajoutePion('O');
		assertEquals('O', c.getListe()[0]);
		assertEquals(false, c.estPleine());
		for(int i=0; i<5; i++) {
			assertEquals(true, c.ajoutePion('X'));
		}
		assertEquals(false, c.ajoutePion('O'));
		assertEquals(true, c.estPleine());
		c.vide();
		assertEquals(false, c.estPleine());
	}

}
