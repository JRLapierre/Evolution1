package outils;

/**
 * cette classe contient des outils pratiques qui n'ont pas 
 * leur place dans d'autres classes du projet
 * @author jrl
 *
 */
public class Outils {
	
	/**
	 * cette methode compare deux listes d'octets
	 * @param liste1 la premiere liste
	 * @param liste2 la deuxieme liste
	 * @return true si le contenu des listes est le même
	 */
	public static boolean compareListeByte(byte[] liste1, byte[] liste2) {
		if(liste1.length!=liste2.length) return false;
		for(int i=0; i<liste1.length; i++) {
			if(liste1[i]!=liste2[i]) return false;
		}
		return true;
	}

}
