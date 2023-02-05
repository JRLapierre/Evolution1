package puissance4.grille;

/**
 * la grille de jeu.
 * Une grille est une liste de colonnes de pions
 * @author jrl
 *
 */
public class Grille {

	/**
	 * la grille de jeu
	 */
	private Colonne[] grille=new Colonne[7];
	
	/**
	 * constructeur qui initalise la grille
	 */
	public Grille() {
		for(int i=0; i<7; i++) {
			grille[i]=new Colonne();
		}
	}
	
	/**
	 * une methode pour ajouter un pion
	 * @param pion le pion a ajouter
	 * @param position le numero de la colonne dans laquelle on veut mettre le pion
	 * @return true si le pion a pu etre ajoute, false sinon
	 */
	public boolean ajoutePion(char pion, int position) {
		if(position>7 || position<1) return false;
		return grille[position-1].ajoutePion(pion);
	}
	
	/**
	 * methode qui dit si la grille est pleine
	 * @return true si la grille est pleine
	 */
	public boolean estPleine() {
		for (int i=0; i<7; i++) {
			if(!grille[i].estPleine()) return false;
		}
		return true;
	}
	
	/**
	 * dit si une colone est pleine
	 * @param col la colonne choisie
	 * @return true la colonne est pleine
	 */
	public boolean colonnePleine(int col) {
		return grille[col-1].estPleine();
	}
	
	/**
	 * savoir si un joueur a gagne
	 * @param pion le pion du dernier joueur
	 * @param position la colonne dans laquelle le pion a ete joue
	 * @return true en cas de victoire, false sinon
	 */
	public boolean gagne(char pion, int position) {
		if(position>7) return false;
		int x=position-1;
		int y=-1;
		//determiner la position du pion dans la colonne
		for(int i=0; i<6; i++) {
			if(grille[x].getListe()[i]==' ') {
				y=i-1;
				break;
			}
		}
		if(y==-1) y=5;
		//regarder dans les 4 sens possibles
		return (aligneVertical(pion, x) || aligneHorisontal(pion, y) ||
				aligneDiagonaleMontante(pion, x, y) || 
				aligneDiagonaleDescendante(pion, x, y));
	}
	
	//4 fonctions d'alignements :
	
	/**
	 * diagonale descendante
	 * @param pion le pion a inspecter
	 * @param x la coordonnee x du pion
	 * @param y la coordonnee y du pion
	 * @return true si il y a un alignement vertical de 4 pions
	 */
	private boolean aligneVertical(char pion, int x) {
		int suite=0;
		for(int i=0; i<6; i++) {
			if(grille[x].getListe()[i]==' ') {
				return false;
			} else if(grille[x].getListe()[i]==pion) {
				suite++;
				if (suite==4) {
					return true;
				}
			}
			else {
				suite=0;
			}
		}
		return false;
	}
	
	/**
	 * diagonale descendante
	 * @param pion le pion a inspecter
	 * @param x la coordonnee x du pion
	 * @param y la coordonnee y du pion
	 * @return true si il y a un alignement horisontal de 4 pions
	 */
	private boolean aligneHorisontal(char pion, int y) {
		int suite=0;
		for(int i=0; i<7; i++) {
			if(grille[i].getListe()[y]!=' ' && grille[i].getListe()[y]==pion) {
				suite++;
				if (suite==4) return true;
			}
			else suite=0;
		}
		return false;
	}
	
	/**
	 * diagonale descendante
	 * @param pion le pion a inspecter
	 * @param x la coordonnee x du pion
	 * @param y la coordonnee y du pion
	 * @return true si il y a un alignement diagonal montant de 4 pions
	 */
	private boolean aligneDiagonaleMontante(char pion, int x, int y) {
		//determiner la base de la diagonale
		if(x>y) {
			x-=y;
			y=0;
		} else if(x<y) {
			y-=x;
			x=0;
		} else {
			x=0;
			y=0;
		}
		//on remonte la diagonale en verifiant qu'on ne sorte pas des limites
		int suite=0;
		while(x<7 && y<6) {
			if(grille[x].getListe()[y]!=' ' && grille[x].getListe()[y]==pion) {
				suite++;
				if (suite==4) return true;
			} else suite=0;
			x++;
			y++;
		}
		return false;
	}
	
	/**
	 * diagonale descendante
	 * @param pion le pion a inspecter
	 * @param x la coordonnee x du pion
	 * @param y la coordonnee y du pion
	 * @return true si il y a un alignement diagonal descendant de 4 pions
	 */
	private boolean aligneDiagonaleDescendante(char pion, int x, int y) {
		//determiner la base de la diagonale //on commence en haut
		while(x!=0 && y!=5) {
			x--;
			y++;
		}
		//on descend la diagonale en verifiant qu'on ne sorte pas des limites
		int suite=0;
		while(x<7 && y>=0) {
			if(grille[x].getListe()[y]!=' ' && grille[x].getListe()[y]==pion) {
				suite++;
				if (suite==4) return true;
			} else suite=0;
			x++;
			y--;
		}
		return false;
	}
	
	/***
	 * une methode de copie de la grille
	 * @return une grille identique
	 */
	public Grille copie() {
		Grille g=new Grille();
		for(int i=0; i<7; i++) {
			for(int j=0; j<6; j++) {
				g.grille[i].getListe()[j]=this.grille[i].getListe()[j];
			}
		}
		return g;
	}
	
	
	
	@Override
	public String toString() {
		StringBuilder str=new StringBuilder(133);
		//ligne superieure
		str.append(" ");
		for(int i=1; i<=7; i++) {
			str.append(i + " ");
		}
		str.append("\r\n");
		//grille
		for(int i=0; i<6; i++) {
			str.append("|");
			for(int j=0; j<7; j++) {
				str.append(grille[j].getListe()[5-i]);
				str.append("|");
			}
			str.append("\r\n");
		}
		
		return str.toString();
	}
	
}
