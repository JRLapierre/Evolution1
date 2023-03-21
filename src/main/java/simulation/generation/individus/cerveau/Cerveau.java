package simulation.generation.individus.cerveau;

import java.nio.ByteBuffer;

import outils.Carracteristique;
import outils.ListeChaine;
import outils.interfaces.Repliquable;
import outils.interfaces.Representable;

/**
 * cette classe va simuler un cerveau avec des entrées, 
 * des sorties et un réseau de neurone entre deux.
 * @author jrl
 *
 */
public class Cerveau implements Representable, Repliquable {
	
	//-------------------------------------------------------------------------------
	//constantes
	
	/**
	 * le nom des neurones receptrices du cerveau
	 */
	protected static final String INPUT="input";
	
	/**
	 * le nom des neurones internes au cerveau
	 */
	protected static final String INTERNE="interne";
	
	/**
	 * le nom des neurone emmetrices du cerveau
	 */
	protected static final String OUTPUT="output";
	
	
	
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
	public Mutation mutation=new Mutation(0);

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
		listeInput=initListN(nbInput, INPUT);
		listeOutput=initListN(nbOutput, OUTPUT);
		listeInterne=initListN(nbInterne, INTERNE);		
	}
	
	/**
	 * constructeur pour generer un cerveau depuis un string Json
	 * @param sub les infos venant de l'individu
	 */
	public Cerveau(String sub) {
		//initialisation des listes
		listeInput=initListN(decodeNbNeurones(INPUT, sub), INPUT);
		listeOutput=initListN(decodeNbNeurones(OUTPUT, sub), OUTPUT);
		listeInterne=initListN(decodeNbNeurones(INTERNE, sub), INTERNE);		
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
	protected Cerveau(ByteBuffer bb) {
		this.listeInput=initListN(bb.getShort(), INPUT);
		this.listeInterne=initListN(bb.getShort(), INTERNE);
		this.listeOutput=initListN(bb.getShort(), OUTPUT);
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
	//"Constructeur"
	
	/**
	 * permet de determiner le type du cerveau et renvoie le type adapte
	 * @param bb le ByteBuffer qui contient les informations
	 * @return le cerveau correspondant a l'enregistrement
	 */
	public static Cerveau regenereCerveau(ByteBuffer bb) {
		if(bb.get()==0) return new Cerveau(bb);
		return new CerveauACouches(bb);
	}
	
	//-----------------------------------------------------------------------------
	//fonction d'erreur
	
	/**
	 * fonction d'erreur en cas de type incorrect
	 * @param type
	 */
	private void exeptionType(String type) {
		if (type.equals(INPUT) || type.equals(OUTPUT) || type.equals(INTERNE)) {
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
		exeptionType(type);
		String subsub; //la sous sous chaine de carractere a etudier
		int i=0;
		if(type.equals(INPUT)) {
			subsub=sub.substring(
					sub.indexOf(type + "\":{"), 
					sub.indexOf(",\""+INTERNE+"\":{"));
		}
		else if(type.equals(OUTPUT)) {
			subsub=sub.substring(sub.indexOf(type + "\":{"));
		}
		else {
			subsub=sub.substring(
					sub.indexOf(",\""+INTERNE+"\":{"), 
					sub.indexOf(",\""+OUTPUT+"\":{"));
		}
		//compter le nombre de neurones
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
	protected Neurone[] getListeInput() {
		return listeInput;
	}

	/**
	 * getteur pour listeOutPut
	 * @return la liste des neurones de sortie du cerveau
	 */
	protected Neurone[] getListeOutput() {
		return listeOutput;
	}

	/**
	 * getteur pour listeInterneNeurones
	 * @return la liste des neurones internes au cerveau
	 */
	protected Neurone[] getListeInterne() {
		return listeInterne;
	}

	/**
	 * getteur pour listeConnexions
	 * @return la liste des connexions contenues dans ce cerveau
	 */
	protected ListeChaine<Connexion> getListeConnexions() {
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
		if (type.equals(INPUT)) {
			return listeInput[position];
		}
		else if (type.equals(OUTPUT)) {
			return listeOutput[position];
		}
		else {
			return listeInterne[position];
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
	 * met les valeurs souhaités dans les neurones d'entrée du cerveau.
	 * @param inputs un tableau de float contenant les valeurs voulues
	 */
	private void setInputs(float[] inputs) {
		for(int i=0; i<inputs.length; i++) {
			listeInput[i].setPuissance(inputs[i]);
		}
	}
	
	
	/**
	 * renvoie une liste des sorties du cerveau
	 * @return une liste de float qui contient 
	 * la puissance des neurones en sortie du cerveau
	 */
	private float[] getOutputs() {
		float[] outputs=new float[listeOutput.length];
		for(int i=0; i<outputs.length; i++) {
			outputs[i]=listeOutput[i].getPuissance();
		}
		return outputs;
	}
	
	
	/**
	 * fonction qui met le cerveau dans l'état suivant(après 1 tic),
	 * c'est à dire que les signeaux se transmettent d'une neurone à l'autre
	 */
	protected void next() {
		//on parcours les connextions
		listeConnexions.resetParcours();
		while(listeConnexions.getSuivant()!=null) {
			listeConnexions.getActuel().transitionIn();
		}
		//on vide les neurones
		resetPuissance(listeInput);
		resetPuissance(listeInterne);
		resetPuissance(listeOutput);
		//on reparcours les connextions
		listeConnexions.resetParcours();
		while(listeConnexions.getSuivant()!=null) {
			pertes += listeConnexions.getActuel().transitionOut();
		}
	}
	
	/**
	 * methode qui permet de reinitialiser la puissance dans une liste de Neurones
	 * @param liste la liste de neurone a reinitialiser
	 */
	protected void resetPuissance(Neurone[] liste) {
		for (int i=0; i<liste.length; i++) {
			liste[i].resetPuissance();
		}
	}
	
	
	/**
	 * fonction qui analyse les entrees donnees et renvoie le resultat de l'analyse
	 * @param inputs un tableau de float contenant les valeurs voulues
	 * @return une liste qui contient la puissance des neurones de sortie
	 */
	public float[] analyse(float[] inputs) {
		setInputs(inputs);
		next();
		return getOutputs();
	}
	
	
	//-------------------------------------------------------------------------------
	//fonctions lies a l'evolution du cerveau
	
	/**
	 * fonction d'ajout d'une connexion.
	 * copie la connexion recue en parametres et lui met les neurones 
	 * correspondantes du cerveau
	 * 
	 * @param connexion la connexion à copier et integrer dans le cerveau
	 */
	protected void addConnexion(Connexion connexion) {
		Connexion copie=connexion.replique();
		copie.updateOrigine(getNeurone(
				connexion.getOrigine().getType(), 
				connexion.getOrigine().getNumero()));
		copie.updateCible(getNeurone(
				connexion.getCible().getType(), 
				connexion.getCible().getNumero()));
		listeConnexions.ajout(copie);
	}
	
	
	/**
	 * fonction d'ajout d'une nouvelle connexion.
	 * Plus rapide que addConnexion si il s'agit de creer 
	 * une nouvelle connexion de toutes pieces.
	 * typeOrigine et type cible peuvent être "input", "interne" ou "output"
	 * @param facteur			le facteur de la connexion
	 * @param typeOrigine		le type de la neurone d'origine
	 * @param numeroOrigine		le numero de la neurone d'origine
	 * @param typeCible			le type de la neurone cible
	 * @param numeroCible		le numero de la neurone cible
	 */
	public void addNewConnexion(float facteur, 
			String typeOrigine, int numeroOrigine, 
			String typeCible, int numeroCible) {
		
		listeConnexions.ajout(new Connexion(
				facteur, 
				getNeurone(typeOrigine, numeroOrigine), 
				getNeurone(typeCible, numeroCible)));
	}
	
	
	/**
	 * fonction qui cree un nouveau cerveau identique au precedent
	 * @return un nouveau cerveau
	 */
	@Override
	public Cerveau replique() {
		//creation de la structure principale
		Cerveau cerveau=new Cerveau(
				this.listeInput.length, 
				this.listeOutput.length, 
				this.listeInterne.length);
		//ajout des connexions
		this.listeConnexions.resetParcours();
		while (this.listeConnexions.getSuivant()!=null) {
			cerveau.addConnexion(this.listeConnexions.getActuel());
		}
		//ajout des mutations
		cerveau.mutation=this.mutation;
		return cerveau;
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
		if (type.equals(INPUT)) {
			return listeInput.length;
		}
		else if (type.equals(INTERNE)) {
			return listeInterne.length;
		}
		else {
			return listeOutput.length;
		}
	}
	
	/**
	 * cree la partie du toStringJson pour une liste de neurones
	 * @param build le stringbuilder contenant le reste du toStringJson()
	 * @param liste la liste a afficher
	 * @param type	le type de la liste (input, interne, output)
	 */
	private void toStringJsonPartiel(StringBuilder build,  Neurone[] liste, String type) {
		Connexion c=listeConnexions.getActuel();
		int longueur=liste.length;
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
		build.append("}");
	}
	
	/**
	 * fonction toStringJson qui affiche un cerveau au format json
	 * affiche successivement les connexions tries par les neurones
	 * d'origine.
	 */
	@Override
	public String toStringJson() {
		//on trie la liste
		triConnexions();
		//on parcours sagement la liste
		listeConnexions.getSuivant();
		StringBuilder build=new StringBuilder(listeConnexions.getLongueur()*100);
		build.append("{");
		//le type
		build.append("\"type\":\"base\",");//base pour le cerveau de base
		//les connexions venant des input
		toStringJsonPartiel(build, listeInput,  INPUT);
		build.append(",");
		//les connexions venant de l'interieur
		toStringJsonPartiel(build, listeInterne, INTERNE);
		build.append(",");
		//les connexions venant des outputs
		toStringJsonPartiel(build, listeOutput, OUTPUT);
		build.append("}");
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
		bb.put((byte) 0);//type du cerveau
		//les nombres
		bb.putShort((short) listeInput.length);
		bb.putShort((short) listeInterne.length);
		bb.putShort((short) listeOutput.length);
		//les input
		bb.put((byte) 1);
		toBytePartiel(INPUT, bb);
		//les interne
		bb.put((byte) 2);
		toBytePartiel(INTERNE, bb);
		//les output
		bb.put((byte) 3);
		toBytePartiel(OUTPUT, bb);
		//return
		return bb.array();
	}
	
	/**
	 * une fonction qui dit la longueur de toByte
	 * @return 10 + 2*(nbInput+nbOutput+nbInterne) + 11*listeConnexions.getLongueur()
	 */
	public int toByteLongueur() {
		return 10 + 4*(listeInput.length + listeOutput.length + listeInterne.length)
				+ 11*listeConnexions.getLongueur();
	}


	//---------------------------------------------------------------------
	//autres fonctions
	
	/**
	 * fonction de tri de la liste de connexion
	 */
	protected void triConnexions() {
		Carracteristique<Connexion> car = elt -> {
			if(elt.getOrigine().getType().equals(INPUT)) {
				return elt.getOrigine().getNumero();
			}
			else if(elt.getOrigine().getType().equals(INTERNE)) {
				return listeInput.length + elt.getOrigine().getNumero();
			}
			else {
				return listeInput.length + listeInterne.length + elt.getOrigine().getNumero();
			}
		};
		listeConnexions.triRapide(car);
	}
	
	
	/**
	 * adaptation du equals pour un cerveau
	 */
	@Override
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
