package outils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TestListeChaine {
	
	//-----------------------------------------------------------------------------
	//fonctions de création d'éléments utiles
	
	private ListeChaine<Integer> liste1(){
		ListeChaine<Integer> liste=new ListeChaine<>();
		liste.ajout(0, 5);
		liste.ajout(0, 4);
		liste.ajout(0, 7);
		return liste;
	}
	
	private ListeChaine<Integer> liste2(){
		ListeChaine<Integer> liste=new ListeChaine<>();
		liste.ajout(7);
		liste.ajout(4);
		liste.ajout(5);
		return liste;
	}
	
	private ListeChaine<Integer> liste3(){
		ListeChaine<Integer> liste=new ListeChaine<>(5);
		liste.ajout(0, 7);//ajoute en premier
		liste.ajout(2, 4);//ajoute en dernier
		liste.ajout(1, 3);//ajoute en deuxième position
		liste.ajout(3, 6);//ajoute en 4e position
		//la liste : 7 3 5 6 4
		return liste;
	}
	
	private ListeChaine<Integer> liste4(){
		return new ListeChaine<>(1,2,3,4,5,6,7,8,9,10);
	}
	
	private ListeChaine<Integer> liste5(){
		ListeChaine<Integer> liste=new ListeChaine<>();
		liste.ajout(0, 1,2,3,4,5);
		return liste;
	}
	
	private ListeChaine<Integer> liste6(){
		return new ListeChaine<Integer>(4,5,6,8,2,1,3,1,4,9);
	}
	
	private ListeChaine<Integer> liste6trie(){
		return new ListeChaine<Integer>(1,1,2,3,4,4,5,6,8,9);
	}
	
	//-----------------------------------------------------------------------------
	//tests
	

	
	//--------------------------------------------------------------------------------
	//tests d'erreurs
	
	@Test
	@DisplayName("test de la methode ajoutIndex dans le cas d'une erreur")
	void testAjoutIndexErreur() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			ListeChaine<Integer> liste=new ListeChaine<>(5);
			liste.ajout(2, 7);
		});
		assertThrows(IndexOutOfBoundsException.class, () -> {
			ListeChaine<Integer> liste=new ListeChaine<>(5);
			liste.ajout(-1, 7);
		});
		
	}
	
	//------------------------------------------------------------------------------
	//tests ajout d'éléments
	
	@Test
	@DisplayName("test de la méthode ajoutPremier")
	void testAjoutPremier() {
		ListeChaine<Integer> liste=liste1();
		
		assertEquals(3, liste.getLongueur());
		assertEquals(7, liste.getPremier());
		assertEquals(5, liste.getDernier());
	}
	
	@Test
	@DisplayName("test de la méthode ajoutDernier")
	void testAjoutDernier() {
		ListeChaine<Integer> liste=liste2();
		
		assertEquals(3, liste.getLongueur());
		assertEquals(7, liste.getPremier());
		assertEquals(5, liste.getDernier());
	}

	
	@Test
	@DisplayName("test de la methode ajoutIndex")
	void testAjoutIndex1() {
		ListeChaine<Integer> liste=liste3();
		
		assertEquals(5, liste.getLongueur());
		assertEquals(7, liste.getPremier());
		assertEquals(4, liste.getDernier());
		assertEquals(3, liste.getElement(1));
		assertEquals(5, liste.getElement(2));
		assertEquals(6, liste.getElement(3));
	}
	
	@Test
	@DisplayName("autre test de la methode ajoutIndex")
	void testAjoutIndex2() {
		ListeChaine<Integer> liste=liste4();
		
		liste.ajout(7, 11);
		assertEquals(1, liste.getElement(0));
		assertEquals(2, liste.getElement(1));
		assertEquals(3, liste.getElement(2));
		assertEquals(4, liste.getElement(3));
		assertEquals(5, liste.getElement(4));
		assertEquals(6, liste.getElement(5));
		assertEquals(7, liste.getElement(6));
		assertEquals(11, liste.getElement(7));
		assertEquals(8, liste.getElement(8));
		assertEquals(9, liste.getElement(9));
		assertEquals(10, liste.getElement(10));
	}
	
	@Test
	@DisplayName("test de concatenation 1")
	void testConcatene1() {
		ListeChaine<Integer> liste1=liste1();
		ListeChaine<Integer> liste2=liste3();
		
		liste1.concatene(liste2);
		ListeChaine<Integer> liste3;
		liste3=new ListeChaine<>(7, 4, 5, 7, 3, 5, 6, 4);
		//7 4 5 7 3 5 6 4
		
		assertEquals(liste3, liste1);
	}
	
	@Test
	@DisplayName("test de concatenation 2")
	void testConcatene2() {
		ListeChaine<Integer> liste1=liste1();
		ListeChaine<Integer> liste2=new ListeChaine<>();

		liste1.concatene(liste2);
		
		assertEquals(liste1, liste1());
	}
	
	//------------------------------------------------------------------------------
	//tests de fonctions de changement d'éléments dans la liste
	
	@Test
	@DisplayName("test des fonctions de changement d'élément")
	void testFonctionsChangeElt() {
		ListeChaine<Integer> liste=liste3();
		
		//altération de la liste
		liste.replaceElt(0, 1);
		liste.echange(1, 3);
		//la liste : 1 6 5 3 4
		
		//assert
		assertEquals(5, liste.getLongueur());
		assertEquals(1, liste.getPremier());
		assertEquals(4, liste.getDernier());
		assertEquals(6, liste.getElement(1));
		assertEquals(5, liste.getElement(2));
		assertEquals(3, liste.getElement(3));
	}
	
	//------------------------------------------------------------------------------
	//tests getteurs
	
	@Test
	@DisplayName("test de la fonction getIndex")
	void testGetIndex() {
		ListeChaine<Integer> liste=liste3();
		
		assertEquals(3, liste.getIndex(6));
		assertEquals(-1, liste.getIndex(10));
	}
	
	@Test
	@DisplayName("test de getPremier et getDernier")
	void testGetPremierGetDernier() {
		ListeChaine<Integer> liste=new ListeChaine<>();
		
		assertEquals(null, liste.getDernier());
		assertEquals(null, liste.getPremier());
	}
	
	@Test
	@DisplayName("test des fonctions de parcours d'une liste")
	void testGetSuivantGetPrecedent() {
		ListeChaine<Integer> liste1=liste1();//7 4 5
		
		assertEquals(liste1.getSuivant(), 7);
		assertEquals(liste1.getSuivant(), 4);
		assertEquals(liste1.getSuivant(), 5);
		assertEquals(liste1.getPrecedent(), 4);
		assertEquals(liste1.getSuivant(), 5);
		assertEquals(liste1.getSuivant(), null);
		
		assertEquals(liste1.getPrecedent(), 5);
		assertEquals(liste1.getPrecedent(), 4);
		assertEquals(liste1.getPrecedent(), 7);
		assertEquals(liste1.getPrecedent(), null);
	}
	
	@Test
	@DisplayName("test du tri rapide")
	void testTriRapide() {
		
		Carracteristique<Integer> car = (elt) -> elt;
		
		ListeChaine<Integer> liste=liste6();
		liste.triRapide(car);
		assertEquals(liste6trie(), liste);

	}
	
	@Test
	@DisplayName("dest du getSousChaine")
	void testGetSousChaine() {
		ListeChaine<Integer> liste=liste6();
		//4,5,6,8,2,1,3,1,4,9
		ListeChaine<Integer> sousListe=liste.getSousChaine(0, 5);
		assertEquals(new ListeChaine<Integer>(4,5,6,8,2), sousListe);
		assertEquals(new ListeChaine<Integer>(3,1,4,9), liste.getSousChaine(6, 10));
	}
	
	//------------------------------------------------------------------------------
	//tests de suppressions
	
	@Test
	@DisplayName("test de delIndex")
	void testDelIndex() {
		ListeChaine<Integer> liste=liste3();
		
		ListeChaine<Integer> liste2=new ListeChaine<>(7);

		//suppressions
		liste.delIndex(1);
		liste.delIndex(0);
		liste.delIndex(2);
		//la liste : 5, 6
		
		assertEquals(5, liste.getPremier());
		assertEquals(6, liste.getDernier());
		
		liste2.delIndex(0);

		assertEquals(0, liste2.getLongueur());
		assertThrows(IndexOutOfBoundsException.class, () -> {
			liste2.delIndex(0);
		});
	}
	
	@Test
	@DisplayName("test de delElt")
	void testDelElt() {
		ListeChaine<Integer> liste=liste5();
		
		liste.delElt(5);
		liste.delElt(3);
		liste.delElt(6);
		ListeChaine<Integer> liste2=new ListeChaine<>(1, 2, 4);
		
		assertEquals(liste, liste2);
	}
	
	@Test
	@DisplayName("test de delElts")
	void testDelElts() {
		ListeChaine<Integer> liste=liste5();
		liste.ajout(liste.getLongueur(), 1,2,3);
		
		liste.delElts(3);
		
		assertEquals(liste, new ListeChaine<Integer>(1,2,4,5,1,2));
	}
	
	//------------------------------------------------------------------------------
	//tests d'affichage
	

	

}
