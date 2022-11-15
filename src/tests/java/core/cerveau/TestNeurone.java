package core.cerveau;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import outils.listeChaine.ListeChaine;

class TestNeurone {

	//-------------------------------------------------------------------------------
	//mock necessiare
	
	@Mock
	private Connexion c1=new Connexion(1, n1(), n2());
	
	@Mock
	private Connexion c2=new Connexion(1, n1(), new Neurone("test", 3));
	
	@Mock
	private Connexion c3=new Connexion(1.5f, n1(), new Neurone("test", 4));
	
	
	
	//-------------------------------------------------------------------------------
	//fonctions utiles

	
	private Neurone n1() {
		return new Neurone("test", 0);
	}
	
	private Neurone n2() {
		return new Neurone("test", 1);
	}
	
	private String s1() {
		return "{\"connexion0\":{\"id\":0,\"facteur\":1.0,\"origine\":{\"type\":\"test\",\"numero\":0},\"cible\":{\"type\":\"test\",\"numero\":1}},\"connexion1\":{\"id\":1,\"facteur\":1.0,\"origine\":{\"type\":\"test\",\"numero\":0},\"cible\":{\"type\":\"test\",\"numero\":3}},\"connexion2\":{\"id\":2,\"facteur\":1.5,\"origine\":{\"type\":\"test\",\"numero\":0},\"cible\":{\"type\":\"test\",\"numero\":4}}}";
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
	@DisplayName("test de la liste de connexions")
	void testConnexions() {//attention a l'id des neurones
		Neurone n=n1();
		assertEquals(new ListeChaine<Connexion>(), n.getConnexions());

		n.addConnexion(c1);
		n.addConnexion(c2);
		n.addConnexion(c3);
		n.delConnexion(c2);
		
		ListeChaine<Connexion>liste=new ListeChaine<Connexion>(c1,c3);
		
		assertEquals(liste, n.getConnexions());
		
		Neurone n5=new Neurone("test", 0);
		n.getConnexions().vide();
		n.addConnexion(c1);
		n.addConnexion(c2);
		n.addConnexion(c3);

		//System.out.println(n.toStringJson());
		
		assertEquals(false, n.equalsRoles(n2()));
		assertEquals(true, n.equalsRoles(n5));
		assertEquals(n.toStringJson(), s1());
	}
	/*
	@Test
	@DisplayName("test de equalsRole")
	void testEqualsRoles() {
		Neurone n=n1();
		assertEquals(new ListeChaine<Connexion>(), n.getConnexions());
		
		//Connexion c1=new Connexion(1, n, n2());
		//Connexion c2=new Connexion(1, n, new Neurone("test", 3));
		//Connexion c3=new Connexion(1.5f, n, new Neurone("test", 4));
		
		n.addConnexion(c1);
		n.addConnexion(c2);
		n.addConnexion(c3);
		
		ListeChaine<Connexion>liste=new ListeChaine<Connexion>(c1,c3);
		
		Neurone n5=new Neurone("test", 0);
				
		assertEquals(false, n.equalsRoles(n2()));
		assertEquals(true, n.equalsRoles(n5));
		assertEquals(n.toStringJson(), s1());
	}*/

}
