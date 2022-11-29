package core.cerveau;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import outils.listeChaine.ListeChaine;

class TestCerveau {

	
	//--------------------------------------------------------------------------------
	//fonctions utiles
	
	private Cerveau c1() {
		return new Cerveau(2,3,20);
	}
	
	private Cerveau c2() {
		Cerveau c=c1();
		ListeChaine<Connexion> liste=new ListeChaine<>();
		Connexion con=new Connexion(1.2f, c.getListeInput()[1], c.getListeNeurones()[1]);
		liste.ajout(new Connexion(1, c.getListeInput()[0], c.getListeNeurones()[0]));
		liste.ajout(con);
		liste.ajout(new Connexion(-1, c.getListeNeurones()[0], c.getListeOutput()[0]));
		c.setListeConnextions(liste);
		c.addConnexion(new Connexion(0.5f, c.getListeInput()[1], c.getListeOutput()[1]));
		c.delConnextion(con);
		return c;
	}
	
	private String sc2() {
		return "{\"inputs\":{\"Neurone0\":{\"connexion1\":{\"id\":1,\"facteur\":1.0,\"origine\":{\"type\":\"input\",\"numero\":0},\"cible\":{\"type\":\"interne\",\"numero\":0}}},\"Neurone1\":{\"connexion3\":{\"id\":3,\"facteur\":0.5,\"origine\":{\"type\":\"input\",\"numero\":1},\"cible\":{\"type\":\"output\",\"numero\":1}}}},\"interne\":{\"Neurone0\":{\"connexion2\":{\"id\":2,\"facteur\":-1.0,\"origine\":{\"type\":\"interne\",\"numero\":0},\"cible\":{\"type\":\"output\",\"numero\":0}}},\"Neurone1\":{},\"Neurone2\":{},\"Neurone3\":{},\"Neurone4\":{},\"Neurone5\":{},\"Neurone6\":{},\"Neurone7\":{},\"Neurone8\":{},\"Neurone9\":{},\"Neurone10\":{},\"Neurone11\":{},\"Neurone12\":{},\"Neurone13\":{},\"Neurone14\":{},\"Neurone15\":{},\"Neurone16\":{},\"Neurone17\":{},\"Neurone18\":{},\"Neurone19\":{}},\"outputs\":{\"Neurone0\":{},\"Neurone1\":{},\"Neurone2\":{}}}";
	}
	
	//--------------------------------------------------------------------------------
	//tests
	
	
	@Test
	@DisplayName("test des fonctions de base")
	void testFonctionsBase() {
		Cerveau c1=c1();
		
		assertEquals(2, c1.getNbInput());
		assertEquals(3, c1.getNbOutput());
		assertEquals(20, c1.getNbNeurones());
		assertEquals(2, c1.getListeInput().length);
		assertEquals(3, c1.getListeOutput().length);
		assertEquals(20, c1.getListeNeurones().length);
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
		
		c.getListeInput()[0].setPuissance(1);
		c.getListeInput()[1].setPuissance(1);
		
		c.next();
		
		assertEquals(0, c.getListeInput()[0].getPuissance());
		assertEquals(0, c.getListeInput()[1].getPuissance());
		assertEquals(1, c.getListeNeurones()[0].getPuissance());
		assertEquals(0 , c.getListeOutput()[0].getPuissance());
		assertEquals(0.5 , c.getListeOutput()[1].getPuissance());
		assertEquals(0, c.getListeOutput()[2].getPuissance());
		
		c.next();
		
		assertEquals(0, c.getListeInput()[0].getPuissance());
		assertEquals(0, c.getListeInput()[1].getPuissance());
		assertEquals(0, c.getListeNeurones()[0].getPuissance());
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
		
		cBis.addConnexion(new Connexion(1.2f, c.getListeInput()[1], c.getListeNeurones()[1]));
		
		assertEquals(false, c.equals(cBis));
	}
	
	
	@Test
	@DisplayName("test du toStringJson")
	void testToStringJson() {
		//System.out.println(c2().toStringJson());
		
		assertEquals(sc2(), c2().toStringJson());
	}
	
	@Test
	@DisplayName("test du constructeur a partir d'un string")
	void testFromToString() {
		//{"individu391":{"type":"EnfantSexe","parent1":230,"parent2":231,"id":391,"generation":4,"score":50.0,"cerveau":{"inputs":{"Neurone0":{}},"interne":{"Neurone0":{},"Neurone1":{"connexion141":{"id":141,"facteur":-1.3898041,"origine":{"type":"interne","numero":1},"cible":{"type":"interne","numero":3}}},"Neurone2":{},"Neurone3":{},"Neurone4":{"connexion140":{"id":140,"facteur":-1.7281322,"origine":{"type":"interne","numero":4},"cible":{"type":"input","numero":0}}}},"outputs":{"Neurone0":{"connexion2":{"id":2,"facteur":-0.5347695,"origine":{"type":"output","numero":0},"cible":{"type":"interne","numero":4}}}}}}}
		Cerveau c=new Cerveau("{\"individu391\":{\"type\":\"EnfantSexe\",\"parent1\":230,\"parent2\":231,\"id\":391,\"generation\":4,\"score\":50.0,\"cerveau\":{\"inputs\":{\"Neurone0\":{}},\"interne\":{\"Neurone0\":{},\"Neurone1\":{\"connexion141\":{\"id\":141,\"facteur\":-1.3898041,\"origine\":{\"type\":\"interne\",\"numero\":1},\"cible\":{\"type\":\"interne\",\"numero\":3}}},\"Neurone2\":{},\"Neurone3\":{},\"Neurone4\":{\"connexion140\":{\"id\":140,\"facteur\":-1.7281322,\"origine\":{\"type\":\"interne\",\"numero\":4},\"cible\":{\"type\":\"input\",\"numero\":0}}}},\"outputs\":{\"Neurone0\":{\"connexion2\":{\"id\":2,\"facteur\":-0.5347695,\"origine\":{\"type\":\"output\",\"numero\":0},\"cible\":{\"type\":\"interne\",\"numero\":4}}}}}}}");
		assertEquals(1, c.getNbInput());
		assertEquals(5, c.getNbNeurones());
		assertEquals(1, c.getNbOutput());
		assertEquals("{\"inputs\":{\"Neurone0\":{}},\"interne\":{\"Neurone0\":{},\"Neurone1\":{\"connexion141\":{\"id\":141,\"facteur\":-1.3898041,\"origine\":{\"type\":\"interne\",\"numero\":1},\"cible\":{\"type\":\"interne\",\"numero\":3}}},\"Neurone2\":{},\"Neurone3\":{},\"Neurone4\":{\"connexion140\":{\"id\":140,\"facteur\":-1.7281322,\"origine\":{\"type\":\"interne\",\"numero\":4},\"cible\":{\"type\":\"input\",\"numero\":0}}}},\"outputs\":{\"Neurone0\":{\"connexion2\":{\"id\":2,\"facteur\":-0.5347695,\"origine\":{\"type\":\"output\",\"numero\":0},\"cible\":{\"type\":\"interne\",\"numero\":4}}}}}", c.toStringJson());
	}

}
