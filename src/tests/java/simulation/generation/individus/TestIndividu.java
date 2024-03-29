package simulation.generation.individus;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import simulation.generation.individus.cerveau.Cerveau;
import simulation.generation.individus.cerveau.Mutation;

class TestIndividu {

	//--------------------------------------------------------------------------------
	//fonctions pratiques
	
	Cerveau c=new Cerveau(2,3,20);
	
	private Cerveau c1(Cerveau c) {
		Cerveau c2=c.replique();
		c2.addNewConnexion(1, "input", 0, "interne", 0);
		c2.addNewConnexion(-1, "interne", 0, "output", 0);
		c2.addNewConnexion(0.5f, "input", 0, "output", 1);
		return c2;
	}
	

	
	//--------------------------------------------------------------------------------
	//tests
	
	@Test
	@DisplayName("test des fonctions de base")
	void testFonctionsBase() {
		Cerveau localC=c1(c);
		Individu i=new Original(localC, null);
		
		//assertEquals(0, i.getId());
		assertEquals(0, i.getScore());
		assertEquals(localC, i.getCerveau());
		assertEquals(0, i.getGeneration());
		
		i.updateScore(2);
		i.updateScore(3);
		
		assertEquals(5, i.getScore());

	}

	@Test   
	@DisplayName("test d'un clone parfait")
	void testCloneParfait() {
		Individu i=new Original(c1(c), null);
		Individu iParfait=new CloneParfait(i);
		
		//assertEquals(1, i.getId());
		//assertEquals(2, iParfait.getId());
		assertEquals(0, iParfait.getScore());
		assertEquals(i.getCerveau(), iParfait.getCerveau());
		assertEquals(1, iParfait.getGeneration());
	}
	
	@Test
	@DisplayName("test d'un clone mute mais des mutations quasi nulles")
	void testCloneMute1() {
		Cerveau localC=c1(c);
		//les mutations
		Mutation m=new Mutation(0).setTauxCreationSuppression(-450, 0);
		
		Individu i=new Original(localC, m);

		Individu iMute=new CloneMute(i);
		
		//assertEquals(3, i.getId());
		//assertEquals(4, iMute.getId());
		assertEquals(0, iMute.getScore());
		assertEquals(localC.toStringJson(), iMute.getCerveau().toStringJson());
		assertEquals(1, iMute.getGeneration());
	}

	@Test
	@DisplayName("test d'un clone mute mais les mutations sont folles")
	void testCloneMute2() {
		//les mutations
		Mutation m=new Mutation(0).setTauxCreationSuppression(50, 50)
				.setMutationFacteur(100, 100).setTauxMutationNeurone(100);
		
		Individu i=new Original(c1(c), m);

		Individu iMute=new CloneMute(i);
		
		//assertEquals(5, i.getId());
		//assertEquals(6, iMute.getId());
		assertEquals(0, iMute.getScore());
		assertFalse(c1(c).equals(iMute.getCerveau()));
		assertEquals(1, iMute.getGeneration());

	}
	
	@Test
	@DisplayName("test d'un enfant issu de reproduction sexuelle et les mutations sont fortes")
	void testEnfantSexe1() {
		//initialisation
		Mutation m=new Mutation(0).setTauxCreationSuppression(50, 50)
				.setMutationFacteur(100, 100).setTauxMutationNeurone(100);
		
		Individu i=new Original(c1(c), m);

		Individu iMute=new CloneMute(i);
		
		Individu iParfait=new CloneParfait(i);
		Individu iSexe=new EnfantSexe(iMute, iParfait);
		
		//assertEquals(7, i.getId());
		//assertEquals(8, iMute.getId());
		//assertEquals(9, iParfait.getId());
		//assertEquals(10, iSexe.getId());
		assertEquals(0, iSexe.getScore());
		assertFalse(iMute.getCerveau().equals(iSexe.getCerveau()));
		assertEquals(2, iSexe.getGeneration());
	}
	
	@Test
	@DisplayName("enfant d'une reproduction sexuelle mais les mutations sont inexistantes")
	void testEnfantSexe2() {
		Mutation m=new Mutation(0);
		
		Individu i=new Original(c1(c), m);
		Individu iSexe=new EnfantSexe(i, i);
		assertEquals(0, iSexe.getScore());
		assertEquals(i.getCerveau(), iSexe.getCerveau());
		assertEquals(1, iSexe.getGeneration());
	}
	
	@Test
	@DisplayName("test des toStringJson")
	void testToStringJson() {
		Mutation m=new Mutation(0).setTauxCreationSuppression(10, 15)
				.setMutationFacteur(50, 0.5f).setTauxMutationNeurone(15);
		
		Individu i0=new Original(c1(c), m);
		Individu i1=new CloneMute(i0);
		Individu i2=new CloneParfait(i0);
		Individu i3=new EnfantSexe(i1, i2);
		
		System.out.println(i0.toStringJson());
		System.out.println(i1.toStringJson());
		System.out.println(i2.toStringJson());
		System.out.println(i3.toStringJson());
		System.out.println(m.toStringJson());
		
	}
	
	@Test
	@DisplayName("test de creation de fichiers de trace")
	void testCreationFichiers() {
		Mutation m=new Mutation(0).setTauxCreationSuppression(10, 15)
				.setMutationFacteur(50, 0.5f).setTauxMutationNeurone(15);
		
		Individu i0=new Original(c1(c), m);
		Individu i1=new CloneMute(i0);
		Individu i2=new CloneParfait(i0);
		Individu i3=new EnfantSexe(i1, i2);
		
		i0.enregistre("enregistrements/individus/", "json");
		i1.enregistre("enregistrements/individus/", "json");
		i2.enregistre("enregistrements/individus/", "json");
		i3.enregistre("enregistrements/individus/", "json");
		i0.enregistre("enregistrements/individus/", "bin");
		i1.enregistre("enregistrements/individus/", "bin");
		i2.enregistre("enregistrements/individus/", "bin");
		i3.enregistre("enregistrements/individus/", "bin");
	}
	
	
	@Test
	@DisplayName("test de creation d'individu a partir d'un string")
	void testCreationFromString() {
		//{"individu102":{"type":"CloneParfait","parent":1,"id":102,"generation":2,"score":50.0,"cerveau":{"inputs":{"Neurone0":{}},"interne":{"Neurone0":{},"Neurone1":{},"Neurone2":{},"Neurone3":{"connexion0":{"id":0,"facteur":-1.3322587,"origine":{"type":"interne","numero":3},"cible":{"type":"input","numero":0}}},"Neurone4":{}},"outputs":{"Neurone0":{}}}}}
		Individu individu=new Sauvegarde("{\"individu102\":{\"type\":\"CloneParfait\",\"parent\":1,\"id\":102,\"generation\":2,\"score\":50.0,\"cerveau\":{\"input\":{\"Neurone0\":{}},\"interne\":{\"Neurone0\":{},\"Neurone1\":{},\"Neurone2\":{},\"Neurone3\":{\"connexion0\":{\"id\":0,\"facteur\":-1.3322587,\"origine\":{\"type\":\"interne\",\"numero\":3},\"cible\":{\"type\":\"input\",\"numero\":0}}},\"Neurone4\":{}},\"output\":{\"Neurone0\":{}}}}}", null);
		
		assertEquals(102, individu.getId());
		assertEquals(2, individu.getGeneration());
		assertEquals(50, individu.getScore());
		System.out.println(individu.toStringJson());
	}
	
	@Test
	@DisplayName("test du toByte et de la reconstruction")
	void testToByte() {
		Individu individu0=new Original(c, new Mutation(0).setTauxCreationSuppression(10, 15)
				.setMutationFacteur(50, 0.5f).setTauxMutationNeurone(15));
		Individu individu1=new CloneParfait(individu0);
		Individu individu2=new CloneMute(individu0);
		Individu individu3=new EnfantSexe(individu1, individu2);
		Individu individu4=new Sauvegarde("{\"individu102\":{\"type\":\"CloneParfait\",\"parent\":1,\"id\":102,\"generation\":2,\"score\":50.0,\"cerveau\":{\"input\":{\"Neurone0\":{}},\"interne\":{\"Neurone0\":{},\"Neurone1\":{},\"Neurone2\":{},\"Neurone3\":{\"connexion0\":{\"id\":0,\"facteur\":-1.3322587,\"origine\":{\"type\":\"interne\",\"numero\":3},\"cible\":{\"type\":\"input\",\"numero\":0}}},\"Neurone4\":{}},\"output\":{\"Neurone0\":{}}}}}", null);
		
		ByteBuffer bb0=ByteBuffer.allocate(individu0.toByteLongueur());
		bb0.put(individu0.toByte());
		bb0.flip();
		ByteBuffer bb1=ByteBuffer.allocate(individu1.toByteLongueur());
		bb1.put(individu1.toByte());
		bb1.flip();
		ByteBuffer bb2=ByteBuffer.allocate(individu2.toByteLongueur());
		bb2.put(individu2.toByte());
		bb2.flip();
		ByteBuffer bb3=ByteBuffer.allocate(individu3.toByteLongueur());
		bb3.put(individu3.toByte());
		bb3.flip();
		ByteBuffer bb4=ByteBuffer.allocate(individu4.toByteLongueur());
		bb4.put(individu4.toByte());
		bb4.flip();
		
		assertEquals(Individu.regenereIndividu(bb0).toStringJson(), individu0.toStringJson());
		assertEquals(Individu.regenereIndividu(bb1).toStringJson(), individu1.toStringJson());
		assertEquals(Individu.regenereIndividu(bb2).toStringJson(), individu2.toStringJson());
		assertEquals(Individu.regenereIndividu(bb3).toStringJson(), individu3.toStringJson());
		assertEquals(Individu.regenereIndividu(bb4).toStringJson(), individu4.toStringJson());
	}

}
