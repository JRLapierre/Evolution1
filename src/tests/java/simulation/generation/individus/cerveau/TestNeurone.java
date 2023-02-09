package simulation.generation.individus.cerveau;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TestNeurone {
	
	
	//-------------------------------------------------------------------------------
	//fonctions utiles

	
	private Neurone n1() {
		return new Neurone("test", 0);
	}
	
	
	private String s1() {
		return "{\"type\":\"input\",\"numero\":1}";
	}
	
	//--------------------------------------------------------------------------------
	//tests
	
	@Test
	@DisplayName("test des fonctions de bases")
	void testBases() {
		Neurone n=n1();
		
		assertEquals("test", n.getType());
		assertEquals(0, n.getNumero());
	}
	
	@Test
	@DisplayName("test de la puissance")
	void testPuissance() {
		Neurone n=n1();
		assertEquals(0, n.getPuissance());
		n.setPuissance(2);
		assertEquals(2, n.getPuissance());
		n.setPuissance(-6);
		assertEquals(-5, n.getPuissance());
		n.updatePuissance(6);
		assertEquals(1, n.getPuissance());
		n.updatePuissance(5);
		assertEquals(5, n.getPuissance());
		n.resetPuissance();
		assertEquals(0, n.getPuissance());
	}
	
	@Test
	@DisplayName("test des methode d'enregistrable")
	void testEnregistrable() {
		Neurone n=new Neurone("input", 1);
		assertEquals(n.toStringJson(), s1());
		ByteBuffer b=ByteBuffer.allocate(3);
		b.put((byte) 1);
		b.putShort((short) 1);
		byte[] b2=n.toByte();
		for(int i=0; i<b2.length; i++) {
			assertEquals(b2[i], b.array()[i]);
		}
	}


}
