package core.cerveau;

import core.trace.GenereTrace;
import outils.listeChaine.ListeChaine;

/**
 * cette classe va simuler un cerveau avec des entrées, 
 * des sorties et un réseau de neurone entre deux.
 * @author jrl
 *
 */
public class Cerveau extends GenereTrace{
	
	//-------------------------------------------------------------------------------
	//variables
	
	/**
	 * le nombre de neurones d'entrées
	 */
	private int nbInput;
	
	/**
	 * le nombre de neurones de sortie
	 */
	private int nbOutput;
	
	/**
	 * le nombre de neurones internes
	 */
	private int nbNeurones;

	/**
	 * un tableau fixe des neurones d'origines
	 */
	private Neurone[] listeInput;

	/**
	 * un tableau fixe des neurones de sortie
	 */
	private Neurone[] listeOutput;

	/**
	 * un tableau fixe de neurones
	 */
	private Neurone[] listeNeurones;
	
	/**
	 * une linkedList de connexions
	 */
	private ListeChaine<Connexion> listeConnexions=new ListeChaine<>();

	//------------------------------------------------------------------------------
	//constructeur
	
	/**
	 * génère un cerveau vierge sans connexions
	 * @param nbInput
	 * @param nbOutput
	 * @param nbNeurones
	 */
	public Cerveau(int nbInput, int nbOutput, int nbNeurones) {
		//initialisation des quantitées
		this.nbInput=nbInput;
		this.nbOutput=nbOutput;
		this.nbNeurones=nbNeurones;
		//initialisation des listes
		listeInput=initListN(nbInput, "input");
		listeOutput=initListN(nbOutput, "output");
		listeNeurones=initListN(nbNeurones, "interne");		
	}
	
	//-----------------------------------------------------------------------------
	//fonction d'erreur
	
	/**
	 * fonction d'erreur en cas de type incorrect
	 * @param type
	 */
	private void exeptionType(String type) {
		if (type=="input" || type=="output" || type=="interne") {
			return;
		}//je sais que l'exeption n'est pas la bonne mais flemme de creer
		throw new ArithmeticException("le type de la neurone est inconnu");
	}
	
	//------------------------------------------------------------------------------
	//fonction d'initialisation

	/**
	 * fonction privée qui remplis une liste de neronnes
	 * @param nb le nombre de neurones
	 * @param type le type de la neurone
	 * @return
	 */
	private Neurone[] initListN(int nb, String type) {
		Neurone[] n=new Neurone[nb];
		for (int i=0; i<nb; i++) {
			n[i]=new Neurone(type, i);
		}
		return n;
	}
	
	
	//---------------------------------------------------------------------
	//getteurs
	
	/**
	 * getteur pour nbInput
	 * @return
	 */
	public int getNbInput() {
		return nbInput;
	}

	/**
	 * getteur pour nbOutput
	 * @return
	 */
	public int getNbOutput() {
		return nbOutput;
	}

	/**
	 * getteur pour nbNeurones
	 * @return
	 */
	public int getNbNeurones() {
		return nbNeurones;
	}
	
	/**
	 * getteur pour listeInput
	 * @return
	 */
	public Neurone[] getListeInput() {
		return listeInput;
	}

	/**
	 * getteur pour listeOutPut
	 * @return
	 */
	public Neurone[] getListeOutput() {
		return listeOutput;
	}

	/**
	 * getteur pour listeNeurones
	 * @return
	 */
	public Neurone[] getListeNeurones() {
		return listeNeurones;
	}

	/**
	 * getteur pour listeConnexions
	 * @return
	 */
	public ListeChaine<Connexion> getListeConnexions() {
		return listeConnexions;
	}
	
	/**
	 * un getteur pour obtenir une neurone a partir de son type et de sa position
	 * @param type
	 * @param position
	 * @return la neurone recherchee
	 */
	private Neurone getNeurone(String type, int position) {
		exeptionType(type);
		if (type=="input") {
			return listeInput[position];
		}
		else if (type=="output") {
			return listeOutput[position];
		}
		else if (type=="interne") {
			return listeNeurones[position];
		}
		else {
			System.err.println("j'ai merde : " + type + "a passe les mailles du filet");
			return null;
		}
		
	}
	
	
	//------------------------------------------------------------------------------
	//fonctions lies au fonctionnement dynamique du cerveau

	/**
	 * fonction qui met le cerveau dans l'état suivant(après 1 tic),
	 * c'est à dire que les signeaux se transmettent d'une neurone à l'autre
	 */
	public void next() {
		//on parcours les connextions
		for (int i=0; i<listeConnexions.getLongueur(); i++) {
			//on enregistre dans les conenxtions la valeur
			listeConnexions.getElement(i).transitionIn();
		}
		//on vide les neurones
		for (int i=0; i<nbNeurones; i++) {
			listeNeurones[i].resetPuissance();
		}
		for (int i=0; i<nbInput; i++) {
			listeInput[i].resetPuissance();
		}		
		for (int i=0; i<nbOutput; i++) {
			listeOutput[i].resetPuissance();
		}
		//on reparcours les connextions
		for (int i=0; i<listeConnexions.getLongueur(); i++) {
			//on enregistre la valeur des connextions dans les neurones
			listeConnexions.getElement(i).transitionOut();
		}
	}
	
	
	//-------------------------------------------------------------------------------
	//fonctions lies a l'evolution du cerveau
	
	/**
	 * fonction d'ajout d'une connexion.
	 * copie la connexion recue en parametres et lui met les neurones 
	 * correspondantes du cerveau
	 * 
	 * @param c la connexion à copier et integrer dans le cerveau
	 */
	public void addConnexion(Connexion c) {
		Connexion c2=new Connexion(
				c.getFacteur(), 
				getNeurone(c.getOrigine().getType(), c.getOrigine().getNumero()), 
				getNeurone(c.getCible().getType(), c.getCible().getNumero()), 
				c.getId());
		this.listeConnexions.ajout(c2);
		c2.getOrigine().addConnexion(c2);
		
	}
	
	/**
	 * fonction de suppression d'une connexion
	 * @param c la connexion à supprimer
	 */
	public void delConnextion(Connexion c) {
		listeConnexions.delElt(c);
		c.getOrigine().delConnexion(c);
	}
	
	/**
	 * setter pour inclure une liste déja faite
	 * @param listeConnextions
	 */
	public void setListeConnextions(ListeChaine<Connexion> listeConnexions) {
		this.listeConnexions = listeConnexions;
		Connexion c=listeConnexions.getSuivant();
		while (c!=null) {
			c.getOrigine().addConnexion(c);
			c=listeConnexions.getSuivant();
		}
	}

	
	/**
	 * fonction qui cree un nouveau cerveau identique au precedent
	 * @return un nouveau cerveau
	 */
	public Cerveau replique() {
		Cerveau c=new Cerveau(
				this.nbInput, 
				this.nbOutput, 
				this.nbNeurones);
		Connexion i=this.listeConnexions.getSuivant();
		while (i!=null) {
			c.addConnexion(i);
			i=this.listeConnexions.getSuivant();
		}
		return c;
	}
	
	//---------------------------------------------------------------------
	//fonction d'affichage
	
	/**
	 * fonction toStringJson qui affiche un cerveau au format json
	 * affiche successivement les connexions tries par les neurones
	 * d'origine.
	 */
	public String toStringJson() {
		String str="{";
		//les connexions venant des input
		str +="\"inputs\":{";
		for (int i=0; i<nbInput; i++) {
			str += "\"Neurone" + i + "\":";
			str += listeInput[i].toStringJson();
			if(i!=nbInput-1) {
				str += ",";
			}
		}
		str +="},";
		//les connexions venant de l'interieur
		str +="\"interne\":{";
		for (int i=0; i<nbNeurones; i++) {
			str += "\"Neurone" + i + "\":";
			str += listeNeurones[i].toStringJson();
			if(i!=nbNeurones-1) {
				str += ",";
			}
		}
		str +="},";
		//les connexions venant des outputs
		str +="\"outputs\":{";
		for (int i=0; i<nbOutput; i++) {
			str += "\"Neurone" + i + "\":";
			str += listeOutput[i].toStringJson();
			if(i!=nbOutput-1) {
				str += ",";
			}
		}
		str +="}";
		str+="}";
		return str;
	}


	//---------------------------------------------------------------------
	//autres fonctions
	
	@Override
	/**
	 * adaptation du equals pour un cerveau
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cerveau other = (Cerveau) obj;
		if (nbInput!=other.nbInput 
				|| nbOutput!=other.nbOutput 
				|| nbNeurones!=other.nbNeurones) {
			return false;
		}
		return listeConnexions.equals(other.listeConnexions);
	}
	

	
	
}
