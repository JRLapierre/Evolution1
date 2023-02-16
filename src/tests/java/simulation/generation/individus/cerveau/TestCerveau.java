package simulation.generation.individus.cerveau;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//@FixMethodOrder(MethodSorters.NAME_ASCENDING)//ordre alphabetique
class TestCerveau {

	
	//--------------------------------------------------------------------------------
	//fonctions utiles
	
	private Cerveau c1() {
		return new Cerveau(2,3,20);
	}
	
	private Cerveau c2() {
		Cerveau c=c1();
		Connexion con=new Connexion(1.2f, c.getListeInput()[1], c.getListeInterne()[1]);
		c.getListeConnexions().ajout(new Connexion(1, c.getListeInput()[0], c.getListeInterne()[0]));
		c.getListeConnexions().ajout(con);
		c.getListeConnexions().ajout(new Connexion(-1, c.getListeInterne()[0], c.getListeOutput()[0]));
		c.addConnexion(new Connexion(0.5f, c.getListeInput()[1], c.getListeOutput()[1]));
		c.getListeConnexions().delElt(con);
		return c;
	}

	
	//--------------------------------------------------------------------------------
	//tests
	
	
	@Test
	@DisplayName("test des fonctions de base")
	void testFonctionsBase() {
		Cerveau c1=c1();
		
		assertEquals(2, c1.getListeInput().length);
		assertEquals(3, c1.getListeOutput().length);
		assertEquals(20, c1.getListeInterne().length);
		assertEquals(0, c1.getListeConnexions().getLongueur());
	}
	
	@Test
	@DisplayName("test des fonctions d'evolution")
	void testEvolution() {
		Cerveau c=c2();
		
		assertEquals(3, c.getListeConnexions().getLongueur());
		
	}
	
	@Test
	@DisplayName("test des fonctions du fonctionnement dynamique du cerveau")
	void testFonctionnement() {
		Cerveau c=c2();
		float[] input=new float[2];

		input[0]=1;
		input[1]=1;
		c.analyse(input);
		
		assertEquals(0, c.getListeInput()[0].getPuissance());
		assertEquals(0, c.getListeInput()[1].getPuissance());
		assertEquals(1, c.getListeInterne()[0].getPuissance());
		//assertEquals(0 , c.getListeOutput()[0].getPuissance());
		assertEquals(0.5 , c.getListeOutput()[1].getPuissance());
		assertEquals(0, c.getListeOutput()[2].getPuissance());
		
		input[0]=0;
		input[1]=0;
		c.analyse(input);
		
		assertEquals(0, c.getListeInput()[0].getPuissance());
		assertEquals(0, c.getListeInput()[1].getPuissance());
		assertEquals(0, c.getListeInterne()[0].getPuissance());
		assertEquals(-1 , c.getListeOutput()[0].getPuissance());
		assertEquals(0 , c.getListeOutput()[1].getPuissance());
		assertEquals(0, c.getListeOutput()[2].getPuissance());

	}
	
	@Test
	@DisplayName("test de replique et de equals")
	void testRepliqueEquals() {
		Cerveau c=c2();
		Cerveau cBis=c.replique();
		
		assertEquals(c, cBis);
		
		cBis.addConnexion(new Connexion(1.2f, c.getListeInput()[1], c.getListeInterne()[1]));
		
		assertEquals(false, c.equals(cBis));
	}
	
	
	@Test
	@DisplayName("test du toStringJson et du decodeur")
	void testToStringJson() {
		//System.out.println(c2().toStringJson());
		Cerveau c0=c2();
		Cerveau c1=new Cerveau(c0.toStringJson());
		assertEquals(c0, c1);
	}
	
	@Test
	@DisplayName("test des pertes")
	void testPertes() {
		Cerveau c=new Cerveau(1,1,1);
		c.addConnexion(new Connexion(2, c.getListeInput()[0], c.getListeOutput()[0]));
		c.addConnexion(new Connexion(2, c.getListeInput()[0], c.getListeOutput()[0]));
		c.addConnexion(new Connexion(2, c.getListeInput()[0], c.getListeOutput()[0]));
		
		float[] inputs=new float[] {1};
		c.analyse(inputs);
		assertEquals(1, c.getPertes());
		inputs[0]=-1;
		c.analyse(inputs);
		assertEquals(2, c.getPertes());
	}
	
	@Test
	@DisplayName("test du tri des connexions")
	void testTri() {
		Cerveau c=new Cerveau(2,2,2);
		c.addConnexion(new Connexion(2, c.getListeInput()[1], c.getListeInterne()[0]));
		c.addConnexion(new Connexion(2, c.getListeInterne()[1], c.getListeOutput()[0]));
		c.addConnexion(new Connexion(2, c.getListeOutput()[0], c.getListeOutput()[0]));
		c.addConnexion(new Connexion(2, c.getListeOutput()[1], c.getListeOutput()[0]));
		c.addConnexion(new Connexion(2, c.getListeInput()[0], c.getListeInterne()[1]));
		c.addConnexion(new Connexion(2, c.getListeInterne()[0], c.getListeInterne()[0]));
		
		System.out.println(c.toStringJson());

	}
	@Test
	@DisplayName("test du toByte")
	void testToByte() {
		Cerveau c=c2();
		ByteBuffer bb=ByteBuffer.allocate(c.toByteLongueur());
		bb.put(c.toByte());
		bb.flip();
		Cerveau c2=new Cerveau(bb);
		assertEquals(c, c2);
	}

}
