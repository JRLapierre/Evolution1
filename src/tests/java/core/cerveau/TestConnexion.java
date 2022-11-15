package core.cerveau;

import static org.junit.jupiter.api.Assertions.*;

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
		
		assertEquals(2, c1.getFacteur());
		
		c1.updateFacteur(-5);
		
		assertEquals(-2, c1.getFacteur());
		
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
	
	//ya juste le equals que j'ai la flemme de tester
	

}
