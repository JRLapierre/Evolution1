package core.generation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import core.cerveau.Cerveau;
import core.cerveau.Connexion;
import core.individus.Individu;
import core.individus.Original;
import core.mutations.Mutation;

class TestGeneration {
	
	//-------------------------------------------------------------------------------------------
	//fonctions pratiques
	
	private Generation type01() {
		Cerveau c=new Cerveau(1, 1, 0);
		c.addConnexion(new Connexion(2, c.getListeInput()[0], c.getListeOutput()[0]));
		Individu i=new Original(c, 0);
		Mutation m=new Mutation(0, 0, 0, 0, 0, 0);
		return new Type0(i, m, 0, 0, 1, 1, "0");
	}
	
	private Generation type02() {
		Cerveau c=new Cerveau(1, 1, 5);
		c.addConnexion(new Connexion(2, c.getListeInput()[0], c.getListeOutput()[0]));
		Individu i=new Original(c, 0);
		Mutation m=new Mutation(0, 100, 0, 50, 100, 0);
		return new Type0(i, m, 1, 1, 1, 1, "0");//devrait yavoir 3 individus
	}
	
	//-------------------------------------------------------------------------------------------
	//fonctions de test
	
	@Test
	@DisplayName("test des fonctions de bases")
	void testBases() {
		Generation g1=type01();
		g1.evaluation();
		g1.enregistre();
		
		assertEquals(7, g1.getPopulation()[0].getScore());
		
	}
	
	@Test
	@DisplayName("test de plusieurs generations")
	void testGenerations() {
		Generation g2=type02();
		g2.evaluation();
		g2.enregistre();
		Generation g3=new Type0(g2);
		g3.evaluation();
		g3.enregistre();
		System.out.println( g2.toStringJson());
		System.out.println( g3.toStringJson());
	}

}
