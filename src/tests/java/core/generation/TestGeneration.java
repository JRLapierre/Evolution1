package core.generation;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import core.generation.individus.Individu;
import core.generation.individus.Original;
import core.generation.individus.cerveau.Cerveau;
import core.generation.individus.cerveau.Connexion;
import core.generation.individus.mutations.Mutation;

class TestGeneration {
	
	//-------------------------------------------------------------------------------------------
	//fonctions pratiques
	
	private Epreuve e1() {
		Epreuve epreuve=population -> {
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
		return epreuve;
	}
	
	private Generation type01() {
		Cerveau c=new Cerveau(1, 1, 0);
		c.addConnexion(new Connexion(2, c.getListeInput()[0], c.getListeOutput()[0]));
		Mutation m=new Mutation(0, 0, 0, 0, 0, 0);
		Individu i=new Original(c, 0, m);
		return new Generation(i, 0, 0, 1, 100, e1(), "0");
	}
	
	private Generation type02() {
		Cerveau c=new Cerveau(1, 1, 5);
		c.addConnexion(new Connexion(2, c.getListeInput()[0], c.getListeOutput()[0]));
		Mutation m=new Mutation(0, 100, 0, 50, 100, 0);
		Individu i=new Original(c, 0, m);
		return new Generation(i, 1, 1, 1, 100, e1(), "0");//devrait yavoir 3 individus
	}
	
	//-------------------------------------------------------------------------------------------
	//fonctions de test
	
	@Test
	@DisplayName("test des fonctions de bases")
	void testBases() {
		Generation g1=type01();
		g1.enregistreGeneration();
		
		assertEquals(70, g1.getPopulation()[0].getScore());
		
	}
	
	@Test
	@DisplayName("test de plusieurs generations")
	void testGenerations() {
		Generation g2=type02();
		g2.enregistreGeneration();
		System.out.println( g2.toStringJson());
		g2.nextGen();
		g2.enregistreGeneration();
		System.out.println( g2.toStringJson());
	}
	
	@Test
	@DisplayName("test du decodeur")
	void testDecodeur() {
		try {
			Generation g=new Generation("1", 100, e1());
			g.nextGen();
			for(int i=0; i<10; i++) {
				g.enregistreGeneration();
				g.nextGen();
			}
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}

	}

}
