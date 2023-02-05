package puissance4.joueurs;

import outils.ListeChaine;
import puissance4.grille.Grille;
import simulation.generation.individus.cerveau.Cerveau;

/**
 * cette classe represente un joueur issu de la simulation qui joue avec son cerveau.
 * @author jrl
 *
 */
public class JoueurIndividu extends Joueur{
	
	/**
	 * un mutex pour permettre le multithreading
	 */
	private Object mutex=new Object();

	/**
	 * le cerveau de l'individu : il possede 42 neurones d'entree, soit autant que de 
	 * cases et 7 neurones de sortie soit autant que de possiblites
	 */
	private Cerveau cerveau;
	
	/**
	 * le nombre de tics que l'individu aura pour reflechir
	 */
	private int nbTics;
	
	/**
	 * constructeur d'un joueur de la classe Individu
	 * @param cerveau le cerveau de l'individu
	 * @param nbTics le temps de choix
	 */
	public JoueurIndividu(Cerveau cerveau, int nbTics) {
		super();
		this.cerveau=cerveau;
		this.nbTics=nbTics;
	}

	@Override
	public int choix(Grille grille) {
		//un seul choix a la fois
		synchronized(mutex){
			//on genere une liste a partir des output
			float[] liste=genereListe(grille);
			float[] choix={0,0,0,0,0,0,0};
			for(int i=0; i<nbTics; i++) {
				for(int j=0; j<43; j++) {
					cerveau.getListeInput()[j].setPuissance(liste[j]);
				}
				cerveau.next();
				for(int k=0; k<7; k++) {
					choix[k]+=cerveau.getListeOutput()[k].getPuissance();
				}
			}
			
			//chercher le max des choix. En cas d'egalite, on garde les plus eleves
			float max=maxVal(choix, nbTics * 5 + 1);
			ListeChaine<Integer> idMax=new ListeChaine<>();
			while(idMax.getLongueur()==0) {
				idMax=maxCustom(choix, max, grille);
				max=maxVal(choix, max);
			}//on fait confiance au jeu pour qu'il y ait au moins 1 possibilite
			//en cas d'egalites entre plusieurs choix, on prends au hasard parmis ces choix
			return idMax.getElement(random.aleatInt(idMax.getLongueur()-1))+1;
		}
	}
	
	
	/**
	 * genere une liste de float pour les input du cerveau
	 * @param grille la grille du jeu a interpreter
	 * @return une liste convertissant le contenu de la grille en input pour le cerveau
	 */
	private float[] genereListe(Grille grille) {
		String str=grille.toString();
		float[] liste=new float[43];
		int i=17;
		int j=0;
		while(i<str.length()) {
			if(str.charAt(i)==' ' || str.charAt(i)=='O' || str.charAt(i)=='X') {
				if (str.charAt(i)==' ' ) liste[j]=0;
				else if (str.charAt(i)==this.pion) liste[j]=1;
				else liste[j]=-1;
				j++;
			}
			i++;
		}
		liste[42]=1;//une constante pour permettre des actions meme sans input
		return liste;
	}
	
	/**
	 * une fonction max custom pour trouver les id des valeurs maximales
	 * @param choix la liste de valeurs
	 * @param maximum la valeur a partir de laquelle les valeurs sont exclues
	 * @return la liste des id des valeurs max
	 */
	private ListeChaine<Integer> maxCustom(float[] choix, float max, Grille grille){
		//chercher le max des choix. En cas d'egalite, on garde les plus eleves
		ListeChaine<Integer> idMax=new ListeChaine<>();
		for(int i=0; i<choix.length; i++) {
			if(choix[i]==max && !grille.colonnePleine(i+1)) {
				idMax.ajout(i);
			}
		}
		return idMax;
	}
	
	/**
	 * renvoie la valeur maximale de la liste sous un butoir donne
	 * @param choix la liste des choix du cerveau
	 * @param max la valeur a partir de laquelle les valeurs sont exclues
	 * @return la nouvelle valeur maximale autaurisee inferieure a max
	 */
	private float maxVal(float[] choix, float max) {
		float localMax=-nbTics * 5 - 1;
		for(int i=0; i<choix.length; i++) {
			if(choix[i]>localMax && choix[i]<max) {
				localMax=choix[i];
			}
		}
		return localMax;
	}
}
