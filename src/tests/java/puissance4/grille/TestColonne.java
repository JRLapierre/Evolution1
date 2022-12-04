package puissance4.grille;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestColonne {

	@Test
	void test() {
		Colonne c=new Colonne();
		assertEquals(null, c.getListe()[0]);
		c.ajoutePion(new PionO());
		assertEquals('O', c.getListe()[0].getCouleur());
		assertEquals(false, c.estPleine());
		for(int i=0; i<5; i++) {
			c.ajoutePion(new PionX());
		}
		assertEquals(true, c.estPleine());
		c.vide();
		assertEquals(false, c.estPleine());
	}

}
