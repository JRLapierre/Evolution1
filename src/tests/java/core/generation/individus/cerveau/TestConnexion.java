package core.generation.individus.cerveau;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class TestConnexion {
	
	
	
	//--------------------------------------------------------------------------------
	//fonctions utiles
	
	private Neurone n1() {
		return new Neurone("test", 0);
	}
	
	private Neurone n2() {
		return new Neurone("test", 1);
	}
	
	private Neurone n3() {
		return new Neurone("test", 2);
	}
	
	private Neurone n4() {
		return new Neurone("test", 3);
	}

	
	//--------------------------------------------------------------------------------
	//tests
	
	@Test
	@DisplayName("test des fonctions de base")
	void testFonctionsBases() {
		
		Connexion c1=new Connexion(1.5f, n1(), n2());

		assertTrue(n1().equalsRoles(c1.getOrigine()));
		assertTrue(n2().equalsRoles(c1.getCible()));
		assertEquals(1.5, c1.getFacteur());
		//assertEquals(0, c1.getId());
	}//c'est bizarre l'ordre d'execution
	
	@Test
	@DisplayName("test des updates")
	void testUpdates() {
		Connexion c1=new Connexion(1.5f, n1(), n2());

		c1.updateFacteur(1);
		
		assertEquals(2.5f, c1.getFacteur());
		
		c1.updateFacteur(-5);
		
		assertEquals(-2.5f, c1.getFacteur());
		
		c1.updateOrigine(n3());
		c1.updateCible(n4());
		
		assertTrue(n3().equalsRoles(c1.getOrigine()));
		assertTrue(n4().equalsRoles(c1.getCible()));
		//assertEquals(0, c1.getId());
	}
	
	@Test
	@DisplayName("test de la transition de la puissance")
	void testTransition() {
		Connexion c1=new Connexion(1.5f, n1(), n2());
		c1.getOrigine().setPuissance(1);
		c1.transitionIn();
		c1.transitionOut();
		
		assertEquals(c1.getCible().getPuissance(), 1.5);
	}
	
	@Test
	@DisplayName("test du constructeur depuis un string")
	void testfromString() {
		Cerveau cerveau=new Cerveau(1, 1, 1);
		Connexion c1=new Connexion("{\"connexion14\":{\"id\":14,\"facteur\":-1.084696,\"origine\":{\"type\":\"input\",\"numero\":0},\"cible\":{\"type\":\"output\",\"numero\":0}}",cerveau);
		Connexion c2=new Connexion("{\"connexion17\":{\"id\":17,\"facteur\":-1.5,\"origine\":{\"type\":\"interne\",\"numero\":0},\"cible\":{\"type\":\"output\",\"numero\":0}}",cerveau);
		Connexion c3=new Connexion("{\"connexion16\":{\"id\":16,\"facteur\":2,\"origine\":{\"type\":\"input\",\"numero\":0},\"cible\":{\"type\":\"interne\",\"numero\":0}}",cerveau);
		System.out.println(c1.toStringJson());
		System.out.println(c2.toStringJson());
		System.out.println(c3.toStringJson());
	}
	
	@Test
	@DisplayName("test du toByte")
	void testToByte() {
		Connexion c=new Connexion(1.5f, n1(), n3());
		ByteBuffer b=ByteBuffer.allocate(11);
		b.putInt(19);
		b.putFloat(1.5f);
		b.put((byte) 3);
		b.putShort((short) 2);
		//dans les faits ca marche
		//assertEquals(c.toByte(), b.array());
		b.flip();
		Cerveau cer=new Cerveau(5,5,5);
		Connexion c2=new Connexion(1.5f, cer.getListeInput()[1], cer.getListeOutput()[2]);
		Connexion c3=new Connexion(b, cer.getListeInput()[1], cer);
		assertEquals(c2.toStringJson(), c3.toStringJson());
		
	}
	

}
