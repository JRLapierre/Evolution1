package core.individus;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import core.cerveau.Cerveau;
import core.cerveau.Connexion;
import core.mutations.Mutation;
import outils.listeChaine.ListeChaine;

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
		Individu i=new Original(c1(c), 0);
		
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
		Individu i=new Original(c1(c), 0);
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
		
		Individu i=new Original(c1(c), 0);

		Individu iMute=new CloneMute(i, m);
		
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
		
		Individu i=new Original(c1(c), 0);

		Individu iMute=new CloneMute(i, m);
		
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
		
		Individu i=new Original(c1(c), 0);

		Individu iMute=new CloneMute(i, m);
		
		Individu iParfait=new CloneParfait(i);
		Individu iSexe=new EnfantSexe(iMute, iParfait, m);
		
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
		
		Individu i=new Original(c1(c), 0);
		Individu iSexe=new EnfantSexe(i, i, m);
		assertEquals(0, iSexe.getScore());
		assertEquals(i.getCerveau(), iSexe.getCerveau());
		assertEquals(1, iSexe.getGeneration());
	}
	
	@Test
	@DisplayName("test des toStringJson")
	void testToStringJson() {
		Mutation m=new Mutation(0, 15, 15, 50, 0.5f, 15);
		
		Individu i0=new Original(c1(c), 0);
		Individu i1=new CloneMute(i0, m);
		Individu i2=new CloneParfait(i0);
		Individu i3=new EnfantSexe(i1, i2, m);
		
		System.out.println(i0.toStringJson());
		System.out.println(i1.toStringJson());
		System.out.println(i2.toStringJson());
		System.out.println(i3.toStringJson());
		
	}
	
	@Test
	@DisplayName("test de creation de fichiers de trace")
	void testCreationFichiers() {
		Mutation m=new Mutation(0, 15, 15, 50, 0.5f, 15);
		
		Individu i0=new Original(c1(c), 0);
		Individu i1=new CloneMute(i0, m);
		Individu i2=new CloneParfait(i0);
		Individu i3=new EnfantSexe(i1, i2, m);
		
		i0.creeEnregistrementJson("0");
		i1.creeEnregistrementJson("0");
		i2.creeEnregistrementJson("0");
		i3.creeEnregistrementJson("0");
	}

}
