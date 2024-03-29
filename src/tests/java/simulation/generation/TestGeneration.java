package simulation.generation;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import simulation.generation.individus.Individu;
import simulation.generation.individus.Original;
import simulation.generation.individus.cerveau.Cerveau;
import simulation.generation.individus.cerveau.Mutation;

class TestGeneration {
	
	//-------------------------------------------------------------------------------------------
	//fonctions pratiques
	
	private Epreuve e1() {
		return population -> {
			float[] tab=new float[] {1};
			for (int j=0; j<population.length; j++) {
				for(int i=0; i<10; i++) {
					float score=population[j].getCerveau().analyse(tab)[0];
					population[j].updateScore(5+score);
				}
				population[j].updateScore(-population[j].getCerveau().getPertes());
			}
		};
	}
	
	private Generation type01() {
		Cerveau c=new Cerveau(1, 1, 0);
		c.addNewConnexion(2, "input", 0, "output", 0);
		Mutation m=new Mutation(0);
		Individu i=new Original(c, m);
		return new Generation(i, 0, 0, 1, 100, e1(), "0.1");
	}
	
	private Generation type02() {
		Cerveau c=new Cerveau(1, 1, 5);
		c.addNewConnexion(2, "input", 0, "output", 0);
		Mutation m=new Mutation(0).setTauxCreationSuppression(100, 0)
				.setMutationFacteur(50, 100);
		Individu i=new Original(c, m);
		return new Generation(i, 1, 1, 1, 100, e1(), "0.2");//devrait yavoir 3 individus
	}
	
	private Generation type03() {
		Cerveau c=new Cerveau(1, 1, 5);
		c.addNewConnexion(2, "input", 0, "output", 0);
		Mutation m=new Mutation(0).setTauxCreationSuppression(100, 0)
				.setMutationFacteur(50, 100);
		Individu i=new Original(c, m);
		return new Generation(i, 1, 1, 1, 100, e1(), "0.3");//devrait yavoir 3 individus
	}
	
	private Generation type04() {
		Cerveau c=new Cerveau(1, 1, 5);
		c.addNewConnexion(2, "input", 0, "output", 0);
		Mutation m=new Mutation(0).setTauxCreationSuppression(100, 0)
				.setMutationFacteur(50, 100);
		Individu i=new Original(c, m);
		return new Generation(i, 1, 1, 1, 100, e1(), "0.2");//devrait yavoir 3 individus
	}

	static void deleteFolder(File file){
		for (File subFile : file.listFiles()) {
			if(subFile.isDirectory()) {
				deleteFolder(subFile);
			} else {
				subFile.delete();
			}
		}
		file.delete();
	}
	
	//-------------------------------------------------------------------------------------------
	//fonctions de test
	
	
	@BeforeAll
	@DisplayName("suppression des dossiers si ils existent")
	static void supprimeFichiers() {
		File f=new File("enregistrements/simulation0.1/");
		if(f.exists()) deleteFolder(f);
		f=new File("enregistrements/simulation0.2/");
		if(f.exists()) deleteFolder(f);
		f=new File("enregistrements/simulation0.3/");
		if(f.exists()) deleteFolder(f);
	}
	
	
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
