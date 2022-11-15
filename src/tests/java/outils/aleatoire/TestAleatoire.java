package outils.aleatoire;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TestAleatoire {

	/*
	valeur max d'un int : 2,147,483,647
	avec 46 comme graine : 
	
	2015229212
	117454934
	358490867
	1444463685
	292268429
	1661003491
	715870051
	737728927
	1574445156
	784300376
	1215963960
	1338677342
	260887579
	89312105
	1344093749
	1059562462
	362197680
	788883485
	2119484738
	25175263
	 */
	
	//----------------------------------------------------------------------
	//fonction d'initialisation utiles
	
	/**
	 * fonction qui initialise la graine de l'aleatroire a 46
	 * @return
	 */
	private Aleatoire aleat46() {
		return new Aleatoire(46);
	}
	
	//----------------------------------------------------------------------
	//tests des fonctions pseudo aleatoire

	@Test
	@DisplayName("test de aleatSigne")
	void testAleatSigne() {
		Aleatoire aleat=aleat46();
		
		assertEquals(aleat.aleatSigne(), 1);
		assertEquals(aleat.aleatSigne(), -1);
		assertEquals(aleat.aleatSigne(), -1);
		assertEquals(aleat.aleatSigne(), 1);
		assertEquals(aleat.aleatSigne(), -1);		
		assertEquals(aleat.aleatSigne(), 1);
		assertEquals(aleat.aleatSigne(), -1);
		assertEquals(aleat.aleatSigne(), -1);
		assertEquals(aleat.aleatSigne(), 1);
		assertEquals(aleat.aleatSigne(), -1);
	}
	
	@Test
	@DisplayName("test de aleatPos")
	void testAleatPos() {
		Aleatoire aleat=aleat46();
		
		assertTrue(aleat.aleatPos()>0.5);
		assertTrue(aleat.aleatPos()<0.5);
		assertTrue(aleat.aleatPos()<0.5);
		assertTrue(aleat.aleatPos()>0.5);
		assertTrue(aleat.aleatPos()<0.5);
		assertTrue(aleat.aleatPos()>0.5);
		assertTrue(aleat.aleatPos()<0.5);
		assertTrue(aleat.aleatPos()<0.5);
		assertTrue(aleat.aleatPos()>0.5);
		assertTrue(aleat.aleatPos()<0.5);
	}
	
	@Test
	@DisplayName("test de aleatInt()")
	void testAleatInt1() {
		Aleatoire aleat=aleat46();
		
		assertEquals(aleat.aleatInt(), -2015229212);
		assertEquals(aleat.aleatInt(), 358490867);
		assertEquals(aleat.aleatInt(), 292268429);
		assertEquals(aleat.aleatInt(), -715870051);
		assertEquals(aleat.aleatInt(), -1574445156);
	}
	
	//apres les resulats deviennent trop durs a anticiper

}
