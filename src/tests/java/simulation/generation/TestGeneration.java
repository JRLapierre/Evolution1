package simulation.generation;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import simulation.generation.individus.Individu;
import simulation.generation.individus.Original;
import simulation.generation.individus.cerveau.Cerveau;
import simulation.generation.individus.cerveau.Connexion;
import simulation.generation.individus.mutations.Mutation;

class TestGeneration {
	
	//-------------------------------------------------------------------------------------------
	//fonctions pratiques
	
	private Epreuve e1() {
		return population -> {
			for (int j=0; j<population.length; j++) {
				for(int i=0; i<10; i++) {
					population[j].getCerveau().getListeInput()[0].setPuissance(1);
					population[j].getCerveau().next();
					float score=population[j].getCerveau().getListeOutput()[0].getPuissance();
					population[j].updateScore(5+score);
				}
				population[j].updateScore(-population[j].getCerveau().getPertes());
			}
		};
	}
	
	private Generation type01() {
		Cerveau c=new Cerveau(1, 1, 0);
		c.addConnexion(new Connexion(2, c.getListeInput()[0], c.getListeOutput()[0]));
		Mutation m=new Mutation(0, 0, 0, 0, 0, 0);
		Individu i=new Original(c, m);
		return new Generation(i, 0, 0, 1, 100, e1(), "0.1");
	}
	
	private Generation type02() {
		Cerveau c=new Cerveau(1, 1, 5);
		c.addConnexion(new Connexion(2, c.getListeInput()[0], c.getListeOutput()[0]));
		Mutation m=new Mutation(0, 100, 0, 50, 100, 0);
		Individu i=new Original(c, m);
		return new Generation(i, 1, 1, 1, 100, e1(), "0.2");//devrait yavoir 3 individus
	}
	
	private Generation type03() {
		Cerveau c=new Cerveau(1, 1, 5);
		c.addConnexion(new Connexion(2, c.getListeInput()[0], c.getListeOutput()[0]));
		Mutation m=new Mutation(0, 100, 0, 50, 100, 0);
		Individu i=new Original(c, m);
		return new Generation(i, 1, 1, 1, 100, e1(), "0.3");//devrait yavoir 3 individus
	}
	
	private Generation type04() {
		Cerveau c=new Cerveau(1, 1, 5);
		c.addConnexion(new Connexion(2, c.getListeInput()[0], c.getListeOutput()[0]));
		Mutation m=new Mutation(0, 100, 0, 50, 100, 0);
		Individu i=new Original(c, m);
		return new Generation(i, 1, 1, 1, 100, e1(), "0.2");//devrait yavoir 3 individus
	}
	
	//-------------------------------------------------------------------------------------------
	//fonctions de test
	
	//TODO faire des fonctions de suppresion des dossiers crees durant les tests
	
	
	@Test
	@DisplayName("test des fonctions de bases")
	void testBases() {
		Generation g1=type01();
		g1.evaluation();
		g1.enregistreGeneration("json");
		
		assertEquals(70, g1.getPopulation()[0].getScore());
		
	}
	
	@Test
	@DisplayName("test de plusieurs generations")
	void testGenerations() {
		Generation g2=type04();
		g2.evaluation();
		g2.enregistreInfos("json");
		g2.enregistreGeneration("json");
		System.out.println( g2.toStringJson());
		g2.nextGen();
		g2.evaluation();
		g2.enregistreGeneration("json");
		System.out.println( g2.toStringJson());
	}
	
	@Test
	@DisplayName("test du decodeur json")
	void testDecodeurJson() {
		try {
			Generation g2=type02();
			g2.evaluation();
			g2.enregistreInfos("json");
			g2.enregistreGeneration("json");
			g2.nextGen();
			g2.evaluation();
			g2.enregistreGeneration("json");
			Generation g=new Generation("0.2", 2, "json", e1());
			g2.evaluation();
			g.enregistreInfos("json");
			g.nextGen();
			for(int i=0; i<10; i++) {
				g.enregistreGeneration("json");
				g.nextGen();
			}
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}

	}
	
	@Test
	@DisplayName("test du decodeur binaire")
	void testDecodeurBin() {
		Generation g1=type03();
		g1.enregistreGeneration("bin");
		g1.nextGen();
		g1.enregistreInfos("bin");
		try {
			Generation g2=new Generation("0.3", 1, "bin", e1());
			g2.nextGen();
			g2.enregistreGeneration("bin");
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}

}
