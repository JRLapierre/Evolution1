package puissance4.grille;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestPion {

	@Test
	void test() {
		Pion p1=new PionX();
		Pion p2=new PionO();
		Pion p3=new PionX();
		Pion p4=new PionO();
		assertEquals('X', p1.getCouleur());
		assertEquals('O', p2.getCouleur());
		assertEquals("X", p3.toString());
		assertEquals("O", p4.toString());
		assertTrue(p1.equalsPion(p3));
		assertTrue(p2.equalsPion(p4));
		assertFalse(p1.equalsPion(p2));
		assertFalse(p3.equalsPion(p4));

		
	}

}
