package simulation.generation.individus.cerveau;

import java.nio.ByteBuffer;

import outils.Carracteristique;
import outils.ListeChaine;
import simulation.Enregistrable;
import simulation.generation.individus.mutations.Mutation;

/**
 * cette classe va simuler un cerveau avec des entrées, 
 * des sorties et un réseau de neurone entre deux.
 * @author jrl
 *
 */
public class Cerveau implements Enregistrable {
	
	//-------------------------------------------------------------------------------
	//variables

	/**
	 * un tableau fixe des neurones d'origines
	 */
	private Neurone[] listeInput;

	/**
	 * un tableau fixe des neurones de sortie
	 */
	private Neurone[] listeOutput;

	/**
	 * un tableau fixe de neurones internes
	 */
	private Neurone[] listeInterne;
	
	/**
	 * une linkedList de connexions
	 */
	private ListeChaine<Connexion> listeConnexions=new ListeChaine<>();
	
	/**
	 * les pertes d'energie dans le cerveau
	 */
	private float pertes=0;
	
	/**
	 * les mutations auquelles le cerveau peut être confronté.
	 * Si rien n'est fait, les mutations n'affecterons pas le cerveau.
	 */
	public static Mutation mutation=new Mutation(0);

	//------------------------------------------------------------------------------
	//constructeur
	
	/**
	 * génère un cerveau vierge sans connexions
	 * @param nbInput		le nombre de neurones d'entrees du cerveau
	 * @param nbOutput		le nombre de neurones de sortie du cerveau
	 * @param nbInterne		le nombre de neurones internes du cerveau
	 */
	public Cerveau(int nbInput, int nbOutput, int nbInterne) {
		//initialisation des listes
		listeInput=initListN(nbInput, "input");
		listeOutput=initListN(nbOutput, "output");
		listeInterne=initListN(nbInterne, "interne");		
	}
	
	/**
	 * constructeur pour generer un cerveau depuis un string Json
	 * @param sub les infos venant de l'individu
	 */
	public Cerveau(String sub) {
		//initialisation des listes
		listeInput=initListN(decodeNbNeurones("input", sub), "input");
		listeOutput=initListN(decodeNbNeurones("output", sub), "output");
		listeInterne=initListN(decodeNbNeurones("interne", sub), "interne");		
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
	
	
	/**
	 * constructeur pour generer un cerveau a partir de binaire
	 * @param bb le ByteBuffer duquel on extrait les informations
	 */
	public Cerveau(ByteBuffer bb) {
		this.listeInput=initListN(bb.getShort(), "input");
		this.listeInterne=initListN(bb.getShort(), "interne");
		this.listeOutput=initListN(bb.getShort(), "output");
		byte type;
		short numero;
		short nbConnexions;
		Neurone[] liste;
		while (bb.hasRemaining()) {
			type=bb.get();
			//selon le type
			if (type==1) liste=listeInput;
			else if(type==2) liste=listeInterne;
			else liste=listeOutput;
			//on met les connexions
			for(int i=0; i<liste.length; i++) {
				numero=bb.getShort();
				nbConnexions=bb.getShort();
				for(int j=0; j<nbConnexions; j++) {
					listeConnexions.ajout(new Connexion(bb, liste[numero], this));
				}
			}
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
		if(type.equals("input")) {
			subsub=sub.substring(
					sub.indexOf(type + "\":{"), 
					sub.indexOf(",\"interne\":{"));
		}
		else if(type.equals("output")) {
			subsub=sub.substring(sub.indexOf(type + "\":{"));
		}
		else if(type.equals("interne")) {
			subsub=sub.substring(
					sub.indexOf(",\"interne\":{"), 
					sub.indexOf(",\"output\":{"));
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
	 * getteur pour listeInput
	 * @return la liste des neurones d'entree du cerveau
	 */
	public Neurone[] getListeInput() {
		return listeInput;
	}

	/**
	 * getteur pour listeOutPut
	 * @return la liste des neurones de sortie du cerveau
	 */
	public Neurone[] getListeOutput() {
		return listeOutput;
	}

	/**
	 * getteur pour listeInterneNeurones
	 * @return la liste des neurones internes au cerveau
	 */
	public Neurone[] getListeInterne() {
		return listeInterne;
	}

	/**
	 * getteur pour listeConnexions
	 * @return la liste des connexions contenues dans ce cerveau
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
			return listeInterne[position];
		}
		else {
			System.err.println("j'ai merde : " + type + "a passe les mailles du filet");
			return null;
		}
		
	}
	
	/**
	 * getteur pour obtenir les pertes du cerveau
	 * @return pertes
	 */
	public float getPertes() {
		return pertes;
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
		for (int i=0; i<listeInterne.length; i++) {
			listeInterne[i].resetPuissance();
		}
		for (int i=0; i<listeInput.length; i++) {
			listeInput[i].resetPuissance();
		}		
		for (int i=0; i<listeOutput.length; i++) {
			listeOutput[i].resetPuissance();
		}
		//on reparcours les connextions
		for (int i=0; i<listeConnexions.getLongueur(); i++) {
			//on enregistre la valeur des connextions dans les neurones
			pertes += listeConnexions.getElement(i).transitionOut();
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
		
	}
	
	/**
	 * fonction qui cree un nouveau cerveau identique au precedent
	 * @return un nouveau cerveau
	 */
	public Cerveau replique() {
		Cerveau c=new Cerveau(
				this.listeInput.length, 
				this.listeOutput.length, 
				this.listeInterne.length);
		Connexion i=this.listeConnexions.getSuivant();
		while (i!=null) {
			c.addConnexion(i);
			i=this.listeConnexions.getSuivant();
		}
		return c;
	}
	
	/**
	 * fais muter le cerveau
	 */
	public void mute() {
		mutation.evolution(this);
	}
	

	/**
	 * une fonction qui cree un cerveau issu de la fusion de deux cerveaux.
	 * Les connexions identiques seront gardes, 
	 * les connexions uniques a chaque cerveau auront une chance sur 2 de rester, 
	 * les connexions differentes mais au même id seront combines aleatoirement.
	 * @param autre l'autre cerveau avec qui la fusion sera effectue
	 * @return le cerveau issu de la fusion de this et de autre
	 */
	public Cerveau fusionne(Cerveau autre) {
		return mutation.evolution(this, autre);
	}
	
	//---------------------------------------------------------------------
	//fonction d'affichage
	
	/**
	 * fonction de factorisation de code.
	 * @param type le type de neurones dont on veut connaitre le nombre
	 * @return la longueur de la liste de neurone voulu
	 */
	private int longueurListe(String type) {
		if (type.equals("input")) {
			return listeInput.length;
		}
		else if (type.equals("interne")) {
			return listeInterne.length;
		}
		else {
			return listeOutput.length;
		}
	}
	
	
	private void toStringJsonPartiel(StringBuilder build, String type) {
		Connexion c=listeConnexions.getActuel();
		int longueur=longueurListe(type);
		build.append("\""+type+"\":{");
		//les connexions venant des input
		for (int i=0; i<longueur; i++) {
			build.append("\"Neurone" + i + "\":{");
			while(c!=null && 
					c.getOrigine().getType().equals(type) && 
					c.getOrigine().getNumero()==i) {
				build.append(c.toStringJson());
				c=listeConnexions.getSuivant();
				if(c!=null && 
						c.getOrigine().getType().equals(type) && 
						c.getOrigine().getNumero()==i)
					build.append(",");
			}
			build.append("}");
			if(i!=longueur-1) {
				build.append(",");
			}
		}
	}
	
	/**
	 * fonction toStringJson qui affiche un cerveau au format json
	 * affiche successivement les connexions tries par les neurones
	 * d'origine.
	 */
	public String toStringJson() {
		//on trie la liste
		triConnexions();
		//on parcours sagement la liste
		listeConnexions.getSuivant();
		StringBuilder build=new StringBuilder(listeConnexions.getLongueur()*100);
		build.append("{");
		//les connexions venant des input
		toStringJsonPartiel(build, "input");
		build.append("},");
		//les connexions venant de l'interieur
		toStringJsonPartiel(build, "interne");
		build.append("},");
		//les connexions venant des outputs
		toStringJsonPartiel(build, "output");
		build.append("}}");
		return build.toString();
	}
	
	
	/**
	 * fonction pour aider au toByte
	 * @param type
	 * @param connexion
	 * @param bb
	 */
	private void toBytePartiel(String type, ByteBuffer bb) {
		Connexion connexion=listeConnexions.getActuel();
		int longueur=longueurListe(type);
		short nbConnexions;
		//pour chaque neurone
		for(int i=0; i<longueur; i++) {
			bb.putShort((short) i);
			//determiner le nombre de connexions partant de cette neurone
			nbConnexions=0;
			while( connexion!=null 
					&& connexion.getOrigine().getType().equals(type) 
					&& connexion.getOrigine().getNumero()==i) {
				nbConnexions++;
				connexion=listeConnexions.getSuivant();
			}
			//on se repositionne bien dans la liste
			for(int j=0; j<nbConnexions; j++) {
				connexion=listeConnexions.getPrecedent();
			}
			bb.putShort(nbConnexions);
			//si il y a des connexions partant de cette neurone
			while( connexion!=null 
					&& connexion.getOrigine().getType().equals(type) 
					&& connexion.getOrigine().getNumero()==i) {
				bb.put(connexion.toByte());
				connexion=listeConnexions.getSuivant();
			}
		}
	}
	
	/**
	 * toByte();
	 */
	public byte[] toByte() {
		//le ByteBuffer dans toute sa longueur
		ByteBuffer bb=ByteBuffer.allocate(toByteLongueur());
		//tri de la liste
		triConnexions();
		//preparation du parcours
		listeConnexions.resetParcours();
		listeConnexions.getSuivant();
		//remplissage du ByteBuffer
		//les nombres
		bb.putShort((short) listeInput.length);
		bb.putShort((short) listeInterne.length);
		bb.putShort((short) listeOutput.length);
		//les input
		bb.put((byte) 1);
		toBytePartiel("input", bb);
		//les interne
		bb.put((byte) 2);
		toBytePartiel("interne", bb);
		//les output
		bb.put((byte) 3);
		toBytePartiel("output", bb);
		//return
		return bb.array();
	}
	
	/**
	 * une fonction qui dit la longueur de toByte
	 * @return 9 + 2*(nbInput+nbOutput+nbInterne) + 11*listeConnexions.getLongueur()
	 */
	public int toByteLongueur() {
		return 9 + 4*(listeInput.length + listeOutput.length + listeInterne.length)
				+ 11*listeConnexions.getLongueur();
	}


	//---------------------------------------------------------------------
	//autres fonctions
	
	/**
	 * fonction de tri de la liste de connexion
	 */
	public void triConnexions() {
		Carracteristique<Connexion> car = elt -> {
			if(elt.getOrigine().getType().equals("input")) {
				return elt.getOrigine().getNumero();
			}
			else if(elt.getOrigine().getType().equals("interne")) {
				return listeInput.length + elt.getOrigine().getNumero();
			}
			else {
				return listeInput.length + listeInterne.length + elt.getOrigine().getNumero();
			}
		};
		listeConnexions.triRapide(car);
	}
	
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
		if (listeInput.length!=other.listeInput.length 
				|| listeOutput.length!=other.listeOutput.length 
				|| listeInterne.length!=other.listeInterne.length) {
			return false;
		}
		return listeConnexions.equals(other.listeConnexions);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
	

	
	
}
