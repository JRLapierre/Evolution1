package puissance4.joueurs;

import core.generation.individus.cerveau.Cerveau;
import outils.ListeChaine;
import puissance4.grille.Grille;

/**
 * cette classe represente un joueur issu de la simulation qui joue avec son cerveau.
 * @author jrl
 *
 */
public class JoueurIndividu extends Joueur{

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
	 * @param pion
	 * @param cerveau
	 * @param nbTics le temps de choix
	 */
	public JoueurIndividu(char pion, Cerveau cerveau, int nbTics) {
		super(pion);
		this.cerveau=cerveau;
		this.nbTics=nbTics;
	}

	@Override
	public int choix(Grille grille) {
		float[] liste=genereListe(grille);
		float[] choix={0,0,0,0,0,0,0};
		for(int i=0; i<nbTics; i++) {
			for(int j=0; j<49; j++) {
				cerveau.getListeInput()[j].setPuissance(liste[j]);
			}
			cerveau.next();
			for(int k=0; k<7; k++) {
				choix[k]+=cerveau.getListeOutput()[k].getPuissance();
			}
		}
		//chercher le max des choix. En cas d'egalite, on garde les plus eleves
		float max=choix[0];
		ListeChaine<Integer> idMax=new ListeChaine<>(0);
		for(int i=0; i<7; i++) {
			if(choix[i]>max) {
				max=choix[i];
				idMax.vide();
				idMax.ajout(i);
			}
			else if(choix[i]==max) {
				idMax.ajout(i);
			}
		}
		//en cas d'egalites entre plusieurs choix, on prends au hasard parmis ces choix
		return idMax.getElement(random.aleatInt(idMax.getLongueur()-1));
	}
	
	
	/**
	 * genere une liste de float pour les input du cerveau
	 * @param grille la grille du jeu a interpreter
	 * @return une liste convertissant le contenu de la grille en input pour le cerveau
	 */
	private float[] genereListe(Grille grille) {
		String str=grille.toString();
		float[] liste=new float[49];
		int i=17;
		int j=0;
		while(i<str.length()) {
			if(str.charAt(i)==' ' || str.charAt(i)=='O' || str.charAt(i)=='X') {
				if (str.charAt(i)==' ') liste[j]=0;
				if (str.charAt(i)==this.pion) liste[j]=1;
				else liste[j]=-1;
				j++;
			}
			i++;
		}
		return liste;
	}

}
