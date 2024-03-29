package outils;

import outils.interfaces.Repliquable;

/**
 * cette classe correspond � une liste doublement chain�e de taille variable.
 * Je l'ai cr�e parce que je le pouvais et je ne voulais pas utiliser le
 * bazooka pour tuer la mouche. J'ai fini par cr�er un bazooka moins fiable
 * que les LinkedList que j'aurais pu importer
 * @author jrl
 *
 * @param <T> le type en param�tre
 */
public class ListeChaine<T> implements Repliquable{

	//-----------------------------------------------------------------------------
	//la classe Noeud
	
		/**
		 * un noeud de la liste chain�e. Il poss�de deux extr�mit�es.
		 * @author jrl
		 *
		 */
		protected class Noeud{
			
			/**
			 * l'�l�ment contenu dans le noeud.
			 */
			private T elt;
			
			/**
			 * le noeud pr�c�dent dans la chaine
			 */
			private Noeud precedent;
			
			/**
			 * le noeud suivant dans la chaine
			 */
			private Noeud suivant;

			/**
			 * le constructeur du noeud.
			 * @param elt
			 * @param precedent
			 * @param suivant
			 */
			protected Noeud(T elt, Noeud precedent, Noeud suivant) {
				this.elt = elt;
				this.precedent = precedent;
				this.suivant = suivant;
			}

		}
	
		
		
	//------------------------------------------------------------------------------
	//variables
	
	/**
	 * le premier noeud de la liste
	 */
	private Noeud premier;
	
	/**
	 * le dernier noeud de la liste
	 */
	private Noeud dernier;

	/**
	 * la longueur de la liste
	 */
	private int longueur;
	
	/**
	 * noeud qui sert de reference pour getSuivant et getPrecedent
	 */
	private Noeud local;
	
	//------------------------------------------------------------------------------
	//constructeurs
	
	/**
	 * constructeur qui initialise les premiers et derniers noeuds � null
	 */
	public ListeChaine() {
		premier=null;
		dernier=null;
		longueur=0;
	}
	
	/**
	 * constructeur qui initialise une liste avec 1 element des le d�part
	 * @param elt l'element de depart
	 */
	public ListeChaine(T elt) {
		premier=new Noeud(elt, null, null);
		dernier=premier;
		longueur=1;
	}
	
	/**
	 * constructeur qui prend en parametres plusieurs arguments et les integres
	 * dans la liste.
	 * @param elts les elements a ajouter
	 */
	@SafeVarargs
	public ListeChaine(T... elts) {
		for (T elt:elts) {
			ajout(elt);
		}
	}
	

	
	//-----------------------------------------------------------------------------
	//fonction d'erreur
	
	/**
	 * factorisation du code : v�rification en cas d'index trop important
	 * ou d'index negatif
	 * @param index
	 */
	private void exeptionIndex(int index) {
		if (index>=longueur) {
			throw new IndexOutOfBoundsException(
					"l'index est hors du tableau : l'index est "
					+ index +
					", ce qui est sup�rieur ou �gal � "
					+ longueur);
		}
		else if (index<0) {
			throw new IndexOutOfBoundsException(
					"l'index est n�gatif : l'index est " + index);		
		}
	}
	
	//-----------------------------------------------------------------------------
	//fonctions d'ajout d'�l�ments
	
	/**
	 * ajout d'un element en premi�re place
	 * @param elt
	 */
	private void ajoutPremier(T elt) {
		if (longueur==0) {
			premier=new Noeud(elt, null, null);
			dernier=premier;
		} else if (longueur==1) {
			premier=new Noeud(elt, null, dernier);
			dernier.precedent=premier;
		} else {
			Noeud n=premier;
			premier=new Noeud(elt, null, n);
			n.precedent=premier;
		}
		longueur++;
	}
	
	/**
	 * ajout d'un element en queue de liste
	 * @param elt l'element a ajouter
	 */
	public void ajout(T elt) {
		if (longueur==0) {
			premier=new Noeud(elt, null, null);
			dernier=premier;
		} else if (longueur==1) {
			dernier=new Noeud(elt, premier, null);
			premier.suivant=dernier;
		} else {
			Noeud n=dernier;
			dernier=new Noeud(elt, n, null);
			n.suivant=dernier;
		}
		longueur++;
	}
	
	/**
	 * fonction priv�e qui traite les cas particulier d'ajout(int index, T elt)
	 * @param elt
	 * @param index
	 */
	private void ajoutIndexParticulier(T elt, int index) {
		//ins�rer � l'index 0 revient � ajouter en premi�re place
		if (index==0) {
			ajoutPremier(elt);
		}
		//cela revient � ajouter en derniere place
		else if (index==longueur) {
			ajout(elt);
		}
		//insertion � l'avant derni�re place
		else if (index==longueur-1) {
			Noeud n=new Noeud(elt, dernier.precedent, dernier);
			dernier.precedent.suivant=n;
			dernier.precedent=n;
			longueur++;
		}
	}
	
	/**
	 * fonction cr�e pour contenir le traitement g�n�ral de l'insertion dans les listes.
	 * @param elt
	 * @param index
	 */
	private void ajoutIndexGeneral(T elt, int index) {
		//une erreur si index est sup�rieur � longueur
		exeptionIndex(index-1);
		//tous les autres cas
		Noeud n;
		Noeud n2;
		if (2*index>longueur) {
			//on commence par la fin
			n=dernier;
			for (int i=0; i<longueur-index-1; i++) {
				n=n.precedent;
			}
			n2=new Noeud(elt, n.precedent,n);
			n.precedent.suivant=n2;
			n.precedent=n2;
		}else {
			//on commence par le d�but
			n=premier;
			for (int i=0; i<index; i++) {
				n=n.suivant;
			}
			n2=new Noeud(elt, n.precedent,n);
			n.precedent.suivant=n2;
			n.precedent=n2;
		}
		longueur++;	
	}
	
	/**
	 * fonction d'ajout � un index donn�
	 * @param index l'index auquel on va inserer l'element
	 * @param elt l'element a inserer
	 */
	public void ajout(int index, T elt) {
		//cas particuliers
		if(index==0||index==longueur||index==longueur-1) {
			ajoutIndexParticulier(elt, index);
		}
		//cas g�n�ral
		else {
			ajoutIndexGeneral(elt, index);
		}
	}
	
	/**
	 * fonction d'ajout de plusieurs elements
	 * @param index l'index auquel on commence � ins�rer les elements
	 * @param elts les �l�ments � ajouter
	 */
	public void ajout(int index, T... elts) {
		//risque d'erreurs cons�quent
		if (index>longueur||index<0) {
			exeptionIndex(index);
		}
		int i=0;
		for (T elt : elts) {
			this.ajout(index+i, elt);
			i++;
		}
	}
	
	/**
	 * fonction de concatenation
	 * @param liste la liste qu'on ajoute au bout de la precedente
	 */
	public void concatene(ListeChaine<T> liste) {
		//cas de liste vide
		if (liste.longueur!=0) {
			ListeChaine<T>.Noeud n=liste.premier;
			while (n!=null) {
				this.ajout(n.elt);
				n=n.suivant;
			}
		}
	}
	
	/**
	 * fonction de copie de la liste (mais pas des objets contenus)
	 * @return une liste identique a la precedente contenant les m�mes objets
	 */
	@Override
	public ListeChaine<T> replique() {
		ListeChaine<T> copie=new ListeChaine<>();
		this.resetParcours();
		while(this.getSuivant()!=null) {
			copie.ajout(this.getActuel());
		}
		return copie;
	}
	
	//------------------------------------------------------------------------------
	//fonctions d'alteration d'elements
	
	/**
	 * fonction qui remplace un element par un autre
	 * @param index l'index de l'element a remplacer
	 * @param newElt l'element a mettre a la place
	 */
	public void replaceElt(int index, T newElt) {
		this.getNoeud(index).elt=newElt;
	}
	
	/**
	 * fonction pour intervertir 2 elements
	 * @param index1 l'index du premier element
	 * @param index2 l'index du deuxi�me element
	 */
	public void echange(int index1, int index2) {
		T tmp=this.getElement(index1);
		this.getNoeud(index1).elt=this.getElement(index2);
		this.getNoeud(index2).elt=tmp;
	}
	
	
	/**
	 * fonction qui melange la liste
	 * @param graineAleatoire la graine qui va generer une liste de nombres aleatoires
	 */
	public void melange(int graineAleatoire) {
		Aleatoire alea=new Aleatoire(graineAleatoire);
		for(int i=0; i<longueur*5; i++) {
			echange(alea.aleatInt(longueur-1), alea.aleatInt(longueur-1));
		}
	}
	
	/**
	 * cette fonction fait un tri rapide d'une listeChaine.
	 * @param car une expression labda qui indique sur quel carracteristique de l'objet
	 * on doit trier
	 */
	public void triRapide(Carracteristique<T> car){
		if (this.getLongueur()<=1) {
			return;
		}
		ListeChaine<T> petit=new ListeChaine<>();
		ListeChaine<T> pareil=new ListeChaine<>();
		ListeChaine<T> grand=new ListeChaine<>();
		T pivot=this.getPremier();
		//on met les differents elements dans les trois listes
		T elt=this.getSuivant();
		while (elt!=null) {
			if (car.carracteristique(elt)==car.carracteristique(pivot)) {
				pareil.ajout(elt);
			}
			else if (car.carracteristique(elt)>car.carracteristique(pivot)) {
				grand.ajout(elt);
			}
			else {
				petit.ajout(elt);
			}
			elt=this.getSuivant();
		}
		//partie recursive
		if (petit.getLongueur()>1) {
			petit.triRapide(car);
		}
		if (grand.getLongueur()>1) {
			grand.triRapide(car);
		}
		this.vide();
		//concatenation et return
		this.concatene(petit);
		this.concatene(pareil);
		this.concatene(grand);
	}
	
	//------------------------------------------------------------------------------
	//getteurs
	
	/**
	 * getter pour obtenir la longueur de la liste
	 * @return la longueur de la liste
	 */
	public int getLongueur() {
		return longueur;
	}
	
	/**
	 * faux getter qui renvoie la valeur du premier noeud
	 * @return le contenu du premier noeud
	 */
	public T getPremier() {
		if (premier!=null) return premier.elt;
		return null;
	}
	
	/**
	 * faux getter qui renvoie la valeur du dernier noeud
	 * @return le contenu du dernier noeud
	 */
	public T getDernier() {
		if (dernier!=null) return dernier.elt;
		return null;
	}
	
	/**
	 * fonction qui renvoie l'index de la premiere occurence d'un element
	 * @param elt l'element recherche
	 * @return -1 si l'�l�ment n'est pas trouv�, l'index sinon
	 */
	public int getIndex(T elt) {
		int index=0;
		Noeud n=premier;
		//parcours de la liste en incr�mentant index
		for (int i=0;i<longueur; i++) {
			if (n.elt==elt) {
				return index;
			}
			else {
				index++;
				n=n.suivant;
			}
		}
		//en cas d'�chec
		return -1;
	}
	
	/**
	 * fonction priv�e qui renvoie le noeud � un index donn�
	 * @param index
	 * @return
	 */
	private Noeud getNoeud(int index) {
		//une erreur si index est sup�rieur ou �gal � longueur
		exeptionIndex(index);
		Noeud n;
		//cas ou l'index est petit
		if (2*index<longueur) {
			n=premier;
			for (int i=0; i<index; i++ ) {
				n=n.suivant;
			}
		}
		//cas ou l'index est grand
		else {
			n=dernier;
			for (int i=0; i<longueur-index-1; i++ ) {
				n=n.precedent;
			}	
		}
		return n;
	}
	
	/**
	 * fonction de recherche d'un element selon l'index
	 * @param index l'index recherche
	 * @return le contenu du noeud
	 */
	public T getElement(int index) {
		return this.getNoeud(index).elt;
	}
	
	/**
	 * fonction qui cree une sous chaine
	 * @param debut l'index de l'element de debut de la sous liste
	 * @param fin l'index de l'element de fin de la sous liste
	 * @return une sous liste
	 */
	public ListeChaine<T> getSousChaine(int debut, int fin){
		//si debut et fin sont invalides
		this.exeptionIndex(debut);
		this.exeptionIndex(fin-1);
		if(debut>fin) throw new ArithmeticException("debut est plus grand que fin");
		//creation de la sous chaine
		ListeChaine<T> liste=new ListeChaine<>();
		Noeud n=this.getNoeud(debut);
		for(int i=0; i<fin-debut; i++) {
			liste.ajout(n.elt);
			n=n.suivant;
		}
		return liste;
	}
	
	/**
	 * fonction qui permet de parcourir la liste elt par elt en avant
	 * @return null si l'element n'existe pas, l'element suivant sinon
	 */
	public T getSuivant() {
		if (local==null) local=premier;
		else local=local.suivant;
		return getActuel();
	}
	
	/**
	 * fonction qui permet de parcourir la liste en alant en arriere
	 * @return null si l'element n'existe pas, sa valeur sinon
	 */
	public T getPrecedent() {
		if (local==null) local=dernier;
		else local=local.precedent;
		return getActuel();
	}
	
	/**
	 * fonction qui permet de s'arreter dans le parcours de la liste
	 * @return null si l'element n'existe pas, sa valeur sinon
	 */
	public T getActuel() {
		if (local==null) return null;
		else return local.elt;
	}
	
	/**
	 * fonction qui permet de remettre le parcours de la liste a 0
	 */
	public void resetParcours() {
		local=null;
	}
	
	//-----------------------------------------------------------------------------
	//fonctions de suppression

	/**
	 * fonction privee qui enleve de la liste le noeud en parametre
	 * @param n le noeud a supprimer
	 */
	private void delNoeud(Noeud n) {
		//n n'existe pas
		if (n==null) {
			return;
		}
		//n est le seul element de la liste
		else if (n==premier && n==dernier) {
			premier=null;
			dernier=null;
		}
		//si l'�l�ment c'est le premier de la liste
		else if (n==premier) {
			premier=n.suivant;
			n.suivant.precedent=null;
		}
		//si l'�l�ment est � la fin de la liste
		else if (n==dernier) {
			dernier=n.precedent;
			n.precedent.suivant=null;
		}
		//les autres cas
		else {
			n.precedent.suivant=n.suivant;
			n.suivant.precedent=n.precedent;
		}
		longueur--;

	}
	
	
	/**
	 * fonction de suppression d'un element selon l'index
	 * @param index l'index de l'�l�ment qu'on veut supprimer
	 */
	public void delIndex(int index) {
		delNoeud(this.getNoeud(index));
	}
	
	/**
	 * supprime la premi�re occurence d'un element
	 * @param elt l'element recherche
	 */
	public void delElt(T elt) {
		int index=getIndex(elt);
		if (index>=0) {
			delIndex(index);
		}
	}
	
	/**
	 * fonction de suppression d'un element selon l'�l�ment en question
	 * @param elt l'�l�ment dont on va supprimer toutes les it�rations
	 */
	public void delElts(T elt) {
		Noeud n=premier;
		while(n!=null) {
			if(n.elt==elt) {
				delNoeud(n);
			}
			n=n.suivant;
		}

	}
	
	/**
	 * fonction qui vide la liste
	 */
	public void vide() {
		this.premier=null;
		this.dernier=null;
		this.local=null;
		this.longueur=0;
	}
	
	//-----------------------------------------------------------------------------
	//fonction d'affichage
	
	/**
	 * fontion d'affichage des elements.
	 * affiche tous les elements de la liste les uns apr�s les autres
	 */
	public void printElts() {
		Noeud n=premier;
		System.out.println("liste");
		for (int i=0; i<longueur; i++) {
			System.out.println(n.elt);
			n=n.suivant;
		}
	}
	
	
	//-------------------------------------------------------------------------------
	//fonctions generales

	@Override
	public int hashCode() {
		return super.hashCode();
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ListeChaine other = (ListeChaine) obj;
		Noeud n1=this.premier;
		Noeud n2=other.premier;
		while (n1!=null ||n2!=null) {
		    if (n1==null || n2==null) {
		        return false;
		    }
		    else if (!n1.elt.equals(n2.elt)) {
		        return false;
		    }
		    else {
		        n1=n1.suivant;
		        n2=n2.suivant;
		    }
		}
		return true;
	}


	
}
