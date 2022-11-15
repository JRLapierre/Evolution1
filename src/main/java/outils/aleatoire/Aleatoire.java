package outils.aleatoire;

/**
 * j'essaie de recréer l'algorithme Mersenne Twister pour générer
 * des nombres aléatoires sans importer random
 * Ce generateur de nombres pseudo-aléatoire a plusieurs avantages : 
 * - les nombres sont imprédictibles
 * - les nombres générés sont les mêmes si la graine est la même
 * cela siginifie que je pourrais générer des mutations qui semblent
 * aléatoire tout en étant capable de reproduire exactement la simulation.
 * Je n'aurais qu'à changer la graine à chaque simulation
 * @author jrl
 *
 */
public class Aleatoire {

	/*
	w: word size (in number of bits)
	n: degree of recurrence
	m: middle word, an offset used in the recurrence relation defining the series
	r: separation point of one word, or the number of bits of the lower bitmask,
	a: coefficients of the rational normal form twist matrix
	b, c: TGFSR(R) tempering bitmasks
	s, t: TGFSR(R) tempering bit shifts
	u, d, l: additional Mersenne Twister tempering bit shifts/masks
	 */
	
	
	//----------------------------------------------------------------------
	//coeur de l'algorithme
	
	
	//coefficients for MT19937
	private int w=32;
	private int n=624;
	private int m=397;
	private int r=31;
	
	private int a=0x9908B0DF;
	
	private int u=11;
	private int d=0xFFFFFFFF;
	
	private int s=7;
	private int b=0x9D2C5680;
	
	private int t=15;
	private int c=0xEFC60000;
	
	private int l=18;
	private int f=1812433253;
	
	
	//cree un tableau de longeur n pour stocker l'état du générateur
	private int[] mt=new int[n];
	private int index=n+1;
	private final int LOWERMASK=(1<<r)-1;//le nombre binaire de 1 dans r
	private final int UPPERMASK=0x80000000;

	/**
	 * initialise le générateur à partir d'une graine
	 * @param seed la graine
	 */
	private void mtSeed(int seed) {
		mt[0]=seed;
		for (int i=1; i<n; i++) {
			int temp=f*(mt[i-1] ^ (mt[i-1] >> (w-2))) + i;
			mt[i]=temp & 0xffffffff;
		}
	}
	
	/**
	 * permet d'obtenir le prochain nombre pseudo-aléatoire
	 * @return
	 */
	private int extractNumber() {
		if (index >= n) {
			twist();
			index=0;
		}
		int y = mt[index];
		y = y ^ ((y >> u) & d);
		y = y ^ ((y << s) & b);
		y = y ^ ((y << t) & c);
		y = y ^ (y >> l);
		
		index++;
		
		return y & 0xffffffff;
				
	}
	
	/**
	 * genere les n prochaines favleurs de la serie x_i
	 */
	private void twist() {
		for (int i=0; i<n; i++) {
			int x=(mt[i] & UPPERMASK) + (mt[(i+1) % n] & LOWERMASK);
			int xa = x >> 1;
			if ((x%2)!=0) {
				xa = xa ^ a;
			}
			mt[i]=mt[(i+m)%n] ^ xa;
		}
	}
	
	//----------------------------------------------------------------------
	//fonctions et autres
	
	
	//---------------------------------------------------------------------
	//constructeur
	
	
	/**
	 * constructeur permettant d'initialiser les nombres aléatoires
	 * @param graine la graine qui va déterminer les éléments de la liste
	 */
	public Aleatoire(int graine) {
		mtSeed(graine);
	}
	
	//---------------------------------------------------------------------
	//methodes
	
	//---------------------------------------------------------------------
	//erreur en cas d'entrees incorrectes
	private void parametresIncorrect() {
		throw new IllegalArgumentException("arguments incorrects");
	}
	
	//---------------------------------------------------------------------
	//obtention d'un nombre aléatoire

	/**
	 * une methode qui genere un signe aléatoire
	 * @return 1 si le nombre doit être positif, 
	 * -1 si le nombre doit être negatif
	 */
	public int aleatSigne() {
		if (extractNumber()>1073741823) {
			return 1;
		}
		else {
			return -1;
		}
	}
	
	/**
	 * une fonction qui genere un nombre entre 0 et 1
	 * @return un double entre 0 et 1
	 */
	public double aleatPos() {
		return (double) extractNumber()/ (double) 2147483647;
	}
	
	/**
	 * une methode qui renvoie un nombre entre -1 et 1
	 * @return
	 */
	public double aleat() {
		return (double) aleatInt()/ (double) 2147483647;
	}
	
	/**
	 * une methode qui renvoie un int aleatoire
	 * @return un entier aléatoire selon la graine
	 */
	public int aleatInt() {
		return extractNumber()*aleatSigne();
	}
	
	/**
	 * une methode qui renvoie un int entre 0 et max
	 * @param max le nombre maximal qu'on puisse obtenir
	 * @return un int entre 0 et max
	 */
	public int aleatInt(int max) {
		if (max<0) {
			parametresIncorrect();
		}
		return (int) (aleatPos()*(max+1));
	}
	
	/**
	 * une methode qui renvoie un int entre min et max
	 * @param min le nombre minimal qu'on souhaite obtenir
	 * @param max le nombre maximal qu'on souhaite obtenir
	 * @return un int entre min et max
	 */
	public int aleatInt(int min, int max) {
		if (max<min) {
			parametresIncorrect();
		}
		return min + (int) (aleatPos() * (max-min+1));
	}

	/**
	 * une methode qui renvoie un float entre 0 et max
	 * @param max le nombre maximal qu'on souhaite obtenir
	 * @return un double entre 0 et max
	 */
	public double aleatDouble(double max) {
		return aleatPos()*(max);
	}
	
	/**
	 * une methode qui renvoie un float entre min et max
	 * @param min le nombre minimal qu'on souhaite obtenir
	 * @param max le nombre maximal qu'on souhaite obtenir
	 * @return un double entre min et max
	 */
	public double aleatDouble(double min, double max) {
		return min + (aleatPos() * (max-min));
	}

}
