package core.generation.individus.cerveau;

import outils.listeChaine.ListeChaine;

/**
 * cette classe va simuler un cerveau avec des entrées, 
 * des sorties et un réseau de neurone entre deux.
 * @author jrl
 *
 */
public class Cerveau {
	
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
	
	/**
	 * constructeur pour generer un cerveau depuis un string Json
	 * @param sub les infos venant de l'individu
	 */
	public Cerveau(String sub) {
		this.nbInput=decodeNbNeurones("inputs", sub);
		this.nbOutput=decodeNbNeurones("outputs", sub);
		this.nbNeurones=decodeNbNeurones("interne", sub);
		//initialisation des listes
		listeInput=initListN(nbInput, "input");
		listeOutput=initListN(nbOutput, "output");
		listeNeurones=initListN(nbNeurones, "interne");		
		//chercher les connexions et les ajouter
		int i=0;
		int begin=sub.indexOf("\"connexion", i);
		int end=sub.indexOf("}}", begin)+2;
		while(begin!=-1) {
			addConnexion(new Connexion(sub.substring(begin, end),this));
			i=end;
			begin=sub.indexOf("\"connexion", i);
			end=sub.indexOf("}}", begin)+2;
		}
		
	}
	
	//-----------------------------------------------------------------------------
	//fonction d'erreur
	
	/**
	 * fonction d'erreur en cas de type incorrect
	 * @param type
	 */
	private void exeptionType(String type) {
		if (type.equals("input") || type.equals("output") || type.equals("interne")) {
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
	
	/**
	 * fonction privee pour determiner le nombre de neurones d'un type
	 * @param type le type de neurones dont on veut connaitre le nombre
	 * @param sub la chaine de carractere sur laquelle se trouvent les infos
	 * @return le nombre de neurones de ce type
	 */
	private int decodeNbNeurones(String type, String sub) {
		String subsub; //la sous sous chaine de carractere a etudier
		int i=0;
		if(type.equals("inputs")) {
			subsub=sub.substring(
					sub.indexOf(type + "\":{"), 
					sub.indexOf(",\"interne\":{"));
		}
		else if(type.equals("outputs")) {
			subsub=sub.substring(sub.indexOf(type + "\":{"));
		}
		else if(type.equals("interne")) {
			subsub=sub.substring(
					sub.indexOf(",\"interne\":{"), 
					sub.indexOf(",\"outputs\":{"));
		}
		else {
			System.err.println("le type " + type + " n'est pas bon");
			return 0;
		}
		//conmpter le nombre de neurones
		while(subsub.indexOf("Neurone"+i)!=-1) {
			i++;
		}
		
		return i;
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
		if (type.equals("input")) {
			return listeInput[position];
		}
		else if (type.equals("output")) {
			return listeOutput[position];
		}
		else if (type.equals("interne")) {
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
		StringBuilder build=new StringBuilder(nbInput*100+nbNeurones*100+nbOutput*100);
		build.append("{\"inputs\":{");
		//les connexions venant des input
		for (int i=0; i<nbInput; i++) {
			build.append("\"Neurone" + i + "\":" 
					+ listeInput[i].toStringJson());
			if(i!=nbInput-1) {
				build.append(",");
			}
		}
		build.append("},");
		//les connexions venant de l'interieur
		build.append("\"interne\":{");
		for (int i=0; i<nbNeurones; i++) {
			build.append("\"Neurone" + i + "\":" 
					+ listeNeurones[i].toStringJson());
			if(i!=nbNeurones-1) {
				build.append(",");
			}
		}
		build.append("},");
		//les connexions venant des outputs
		build.append("\"outputs\":{");
		for (int i=0; i<nbOutput; i++) {
			build.append("\"Neurone" + i + "\":" 
					+ listeOutput[i].toStringJson());
			if(i!=nbOutput-1) {
				build.append(",");
			}
		}
		build.append("}}");
		return build.toString();
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

	@Override
	public int hashCode() {
		return super.hashCode();
	}
	

	
	
}
