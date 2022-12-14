package core.generation.individus;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import core.generation.individus.cerveau.Cerveau;
import core.generation.individus.cerveau.Connexion;
import core.generation.individus.mutations.Mutation;
import outils.ListeChaine;

class TestIndividu {

	//--------------------------------------------------------------------------------
	//fonctions pratiques
	
	Cerveau c=new Cerveau(2,3,20);
	
	Connexion con1=new Connexion(1, c.getListeInput()[0], c.getListeNeurones()[0]);
	Connexion con2=new Connexion(-1, c.getListeNeurones()[0], c.getListeOutput()[0]);
	Connexion con3=new Connexion(0.5f, c.getListeInput()[1], c.getListeOutput()[1]);
	
	private Cerveau c1(Cerveau c) {
		ListeChaine<Connexion> liste=new ListeChaine<>();
		liste.ajout(con1);
		liste.ajout(con2);
		c.setListeConnextions(liste);
		c.addConnexion(con3);
		return c;
	}
	

	
	//--------------------------------------------------------------------------------
	//tests
	
	@Test
	@DisplayName("test des fonctions de base")
	void testFonctionsBase() {
		Individu i=new Original(c1(c), 0, null);
		
		//assertEquals(0, i.getId());
		assertEquals(0, i.getScore());
		assertEquals(c1(c), i.getCerveau());
		assertEquals(0, i.getGeneration());
		
		i.updateScore(2);
		i.updateScore(3);
		
		assertEquals(5, i.getScore());

	}

	@Test   
	@DisplayName("test d'un clone parfait")
	void testCloneParfait() {
		Individu i=new Original(c1(c), 0, null);
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
		//les mutations
		Mutation m=new Mutation(0, -456, 0, 0, 0, 0);
		
		Individu i=new Original(c1(c), 0, m);

		Individu iMute=new CloneMute(i);
		
		//assertEquals(3, i.getId());
		//assertEquals(4, iMute.getId());
		assertEquals(0, iMute.getScore());
		assertEquals(c1(c), iMute.getCerveau());
		assertEquals(1, iMute.getGeneration());
	}

	@Test
	@DisplayName("test d'un clone mute mais les mutations sont folles")
	void testCloneMute2() {
		//les mutations
		Mutation m=new Mutation(0, 50, 50, 100, 100, 100);
		
		Individu i=new Original(c1(c), 0, m);

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
		Mutation m=new Mutation(0, 50, 50, 100, 100, 100);
		
		Individu i=new Original(c1(c), 0, m);

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
		Mutation m=new Mutation(0, 0, 0, 0, 0, 0);
		
		Individu i=new Original(c1(c), 0, m);
		Individu iSexe=new EnfantSexe(i, i);
		assertEquals(0, iSexe.getScore());
		assertEquals(i.getCerveau(), iSexe.getCerveau());
		assertEquals(1, iSexe.getGeneration());
	}
	
	@Test
	@DisplayName("test des toStringJson")
	void testToStringJson() {
		Mutation m=new Mutation(0, 15, 15, 50, 0.5f, 15);
		
		Individu i0=new Original(c1(c), 0, m);
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
		Mutation m=new Mutation(0, 15, 15, 50, 0.5f, 15);
		
		Individu i0=new Original(c1(c), 0, m);
		Individu i1=new CloneMute(i0);
		Individu i2=new CloneParfait(i0);
		Individu i3=new EnfantSexe(i1, i2);
		
		System.out.println(i0.toStringJson());
		System.out.println(i1.toStringJson());
		System.out.println(i2.toStringJson());
		System.out.println(i3.toStringJson());
	}
	
	
	@Test
	@DisplayName("test de creation d'individu a partir d'un string")
	void testCreationFromString() {
		//{"individu102":{"type":"CloneParfait","parent":1,"id":102,"generation":2,"score":50.0,"cerveau":{"inputs":{"Neurone0":{}},"interne":{"Neurone0":{},"Neurone1":{},"Neurone2":{},"Neurone3":{"connexion0":{"id":0,"facteur":-1.3322587,"origine":{"type":"interne","numero":3},"cible":{"type":"input","numero":0}}},"Neurone4":{}},"outputs":{"Neurone0":{}}}}}
		Individu individu=new Sauvegarde("{\"individu102\":{\"type\":\"CloneParfait\",\"parent\":1,\"id\":102,\"generation\":2,\"score\":50.0,\"cerveau\":{\"inputs\":{\"Neurone0\":{}},\"interne\":{\"Neurone0\":{},\"Neurone1\":{},\"Neurone2\":{},\"Neurone3\":{\"connexion0\":{\"id\":0,\"facteur\":-1.3322587,\"origine\":{\"type\":\"interne\",\"numero\":3},\"cible\":{\"type\":\"input\",\"numero\":0}}},\"Neurone4\":{}},\"outputs\":{\"Neurone0\":{}}}}}", 0, null);
		
		assertEquals(102, individu.getId());
		assertEquals(2, individu.getGeneration());
		assertEquals(50, individu.getScore());
		System.out.println(individu.toStringJson());
	}

}
