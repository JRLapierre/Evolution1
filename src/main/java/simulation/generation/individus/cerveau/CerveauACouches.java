package simulation.generation.individus.cerveau;

public class CerveauACouches extends Cerveau{
	
	
	//-----------------------------------------------------------------------------------------
	//attributs
	
	/**
	 * une liste a deux dimentions contenant les neurones internes
	 */
	private Neurone[][] couchesInternes;
	
	
	//-----------------------------------------------------------------------------------------
	//constructeurs

	/**
	 * constructeur prive pour pouvoir utiliser la methode replique
	 * @param nbInput	le nombre de neurones d'entree du cerveau
	 * @param nbOutput	le nombre de neurones de sortie du cerveau
	 * @param nbInterne	le nombre de neurones internes du cerveau
	 */
	private CerveauACouches(int nbInput, int nbOutput, int nbInterne) {
		super(nbInput, nbOutput, nbInterne);
	}

	
	/**
	 * construit un cerveau contenant des couches de neurones internes
	 * @param nbInput	le nombre de neurones d'entree du cerveau
	 * @param nbOutput	le nombre de neurones de sortie du cerveau
	 * @param nbParCouche le nombre de neurones par couches internes
	 * @param nbCouches le nombre de couches de neurones internes
	 */
	public CerveauACouches(int nbInput, int nbOutput, int nbParCouche, int nbCouches) {
		super(nbInput, nbOutput, nbParCouche*nbCouches);
		//securite
		if(nbParCouche!=0 && nbCouches!=0) {
			remplisCouchesInternes(nbParCouche, nbCouches);
		}
		relieCouches();
	}
	
	/**
	 * constructeur pour un cerveau qui ne contient pas de couches internes
	 * @param nbInput	le nombre de neurones d'entree du cerveau
	 * @param nbOutput	le nombre de neurones de sortie du cerveau
	 */
	public CerveauACouches(int nbInput, int nbOutput) {
		super(nbInput, nbOutput, 0);
		relieCouches();
	}
	
	/**
	 * constructeur depuis un enregistrement JSON
	 * @param sub la chaine de carractere contenant toutes les infos
	 */
	public CerveauACouches(String sub) {
		super(decodeNbNeurones(INPUT, sub),
				decodeNbNeurones(OUTPUT, sub),
				decodeNbNeurones(INTERNE, sub));
		//reconnaitre le nombre de couches internes
		int nbCouches=nbOccurence(sub, "}},\""+INTERNE);

		if(getListeInterne().length!=0 && nbCouches!=0) {
			remplisCouchesInternes(getListeInterne().length/nbCouches, nbCouches);
		}
		//replacer fidelement les connexions
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
	
	//TODO constructeur depuis un enregistrement BIN
	
	//-----------------------------------------------------------------------------------------
	//methodes d'initialisation
	
	/**
	 * methode pour initialiser les connexions entre deux couches de neurones
	 * @param listeOrigine 	la liste d'origine des connexions
	 * @param listeCible	la liste cible des connexions
	 * @param facteur		le facteur initial
	 */
	private void relieCouche(Neurone[] listeOrigine, Neurone[] listeCible, float facteur) {
		for(int i=0; i<listeOrigine.length; i++) {
			for(int j=0; j<listeCible.length; j++) {
				addNewConnexion(facteur, 
						listeOrigine[i].getType(), listeOrigine[i].getNumero(), 
						listeCible[j].getType(), listeCible[j].getNumero());
			}
		}
	}
	
	/**
	 * methode qui met les connexions dans le cerveau
	 */
	private void relieCouches() {
		if(couchesInternes==null) {
			relieCouche(getListeInput(), getListeOutput(), 1.0f / getListeInput().length);
		}
		else {
			float facteur = 1.0f / couchesInternes[0].length;
			relieCouche(getListeInput(), couchesInternes[0], 1.0f/getListeInput().length);
			for(int i=0; i<couchesInternes.length-1; i++) {
				relieCouche(couchesInternes[i], couchesInternes[i+1], facteur);
			}
			relieCouche(couchesInternes[couchesInternes.length-1], getListeOutput(), facteur);
		}
	}
	
	/**
	 * methode qui assigne les neurones dans les couches internes
	 * @param nbParCouche le nombre de neurones par couche interne
	 * @param nbCouches le nombre de couches de neurones internes
	 */
	private void remplisCouchesInternes(int nbParCouche, int nbCouches) {
		couchesInternes = new Neurone[nbCouches][nbParCouche];
		int couche=-1; //on commence a -1 pour pouvoir incrementer
		for (int i=0; i<getListeInterne().length; i++) {
			if(i%nbParCouche==0) couche++;
			couchesInternes[couche][i%nbParCouche]=getListeInterne()[i];
		}
	}
	
	/**
	 * decode le nombre de neurones de chaque type
	 * @param type le type dont on veut savoir le nombre de couches
	 * @param sub  la chaine de carractere representant le cerveau
	 * @return le nombre de Neurones de type == type
	 */
	private static int decodeNbNeurones(String type, String sub) {
		String subsub; //la sous sous chaine de carractere a etudier
		if(type.equals(OUTPUT)) {
			subsub=sub.substring(sub.lastIndexOf("Neurone"));
			return nbOccurence(subsub, "connexion");
		}
		if(sub.indexOf("}},\""+INTERNE)==-1) {//si il n'y a pas de couches internes
			if(type.equals(INPUT)) return nbOccurence(sub, "Neurone");
			else return 0;//cas interne
		}
		if (type.equals(INPUT)) 
			subsub=sub.substring(25, sub.indexOf("}},\""+INTERNE));
		else subsub=sub.substring(sub.indexOf("}},\""+INTERNE));
		return nbOccurence(subsub, "Neurone");
	}
	
	/**
	 * permet de connaitre le nombre d'occurence dans une chaine de carracteres
	 * @param total la chaine de carracteres originale
	 * @param motCle le mot cle dont on veut savoir le nombre d'occurence
	 * @return le nombre d'apparition de motCle dans total
	 */
	private static int nbOccurence(String total, String motCle) {
		int i=0;
		int lastPos=-1;
		while(true) {
			lastPos = total.indexOf(motCle, lastPos + 1);
			if (lastPos < 0) return i;
			++i;
		}
	}
	
	//-----------------------------------------------------------------------------------------
	//methodes
	
	/**
	 * reecriture de next()
	 * Cette version permet d'obtenir directement le resultat, 
	 * peu importe le nombre de couches a parcourir.
	 */
	@Override
	protected void next() {
		getListeConnexions().resetParcours();
		//reset de la derniere couche
		super.resetPuissance(getListeOutput());
		if (couchesInternes==null) {
			transfertCouche(getListeInput(), getListeOutput());
		}
		else {
			//des inputs a la couche suivante
			transfertCouche(getListeInput(), couchesInternes[0]);
			//de chaque couche interne a la suivante
			for(int i=0; i<couchesInternes.length-1; i++) {
				transfertCouche(couchesInternes[i], couchesInternes[i+1]);
			}
			//de la derniere couche interne aux output
			transfertCouche(couchesInternes[couchesInternes.length-1], getListeOutput());
		}
	}
	
	/**
	 * effectue la transition du signal d'une couche a l'autre
	 * @param listeOrigine	la liste d'ou vient le signal
	 * @param listeCible	la liste qui va recevoir le signal
	 */
	private void transfertCouche(Neurone[] listeOrigine, Neurone[] listeCible) {
		//transitionIn et transitionOut
		int longueur=listeOrigine.length*listeCible.length;
		for(int i=0; i<longueur; i++) {
			getListeConnexions().getSuivant().transitionIn();
			getListeConnexions().getActuel().transitionOut();
		}
		//nettoyage des connexions
		super.resetPuissance(listeOrigine);
	}
	
	
	@Override
	public CerveauACouches replique() {
		//reprise du code de Cerveau
		//creation de la structure principale
		CerveauACouches cerveau=new CerveauACouches(
				this.getListeInput().length, 
				this.getListeOutput().length, 
				this.getListeInterne().length);
		//ajout des connexions
		this.getListeConnexions().resetParcours();
		this.triConnexions();
		while (this.getListeConnexions().getSuivant()!=null) {
			cerveau.addConnexion(this.getListeConnexions().getActuel());
		}
		//ajout des mutations
		cerveau.mutation=this.mutation;
		//reconstruction des couches
		if(couchesInternes!=null) {
			cerveau.remplisCouchesInternes(couchesInternes[0].length, couchesInternes.length);
		}
		return cerveau;
	}
	
	/**
	 * assiste le toStringJsonPartiel en determinant la couche de la liste de 
	 * neurones si c'est une liste interne. Sinon, chaine vide.
	 * @param liste	la liste de Neurones a localiser
	 * @param type	le type de la liste (input ou interne)
	 * @return chaine vide si c'est input, le positionnement de la couche si c'est interne
	 */
	private String determineCouche(Neurone[] liste, String type){
		if(type.equals(Cerveau.INPUT)) return "";
		//le type restant est interne
		for(int i=0; i<couchesInternes.length; i++) {
			if(liste==couchesInternes[i]) {
				return ""+(i+1);
			}
		}
		return null;//ne devrait jamais arriver
	}
	
	/**
	 * assiste le ToStringJsonPartiel en determinant le nombre de neurones 
	 * de la prochaine couche du cerveau
	 * @param couche la couche actuelle (chaine vide si c'est input, 
	 * le numero de la couche sinon)
	 * @return le nombre de neurones de la couches suivante
	 */
	private int determineNbCibles(String couche) {
		if((couche.equals("") && couchesInternes==null) || 
				(!couche.equals("") && Integer.parseInt(couche)==couchesInternes.length)) {
			return getListeOutput().length;
		}
		else {
			return couchesInternes[0].length;
		}
	}
	
	@Override
	protected void toStringJsonPartiel(StringBuilder build,  Neurone[] liste, String type) {
		Connexion c=getListeConnexions().getActuel();
		int nbOrigines=liste.length;
		String couche=determineCouche(liste, type);
		int nbCibles=determineNbCibles(couche);
		build.append("\""+type+couche+"\":{");
		//les connexions venant des input
		for (int i=0; i<nbOrigines; i++) {
			build.append("\"Neurone" + i + "\":{");
			for(int j=0; j<nbCibles-1; j++) {
				build.append(c.toStringJson());
				c=getListeConnexions().getSuivant();
				build.append(",");
			}
			build.append(c.toStringJson());
			c=getListeConnexions().getSuivant();
			build.append("}");
			if(i!=nbOrigines-1) {
				build.append(",");
			}
		}
		build.append("}");
	}
	
	@Override
	public String toStringJson() {
		//on trie la liste
		triConnexions();
		//on parcours sagement la liste
		getListeConnexions().resetParcours();
		getListeConnexions().getSuivant();
		StringBuilder build=new StringBuilder(getListeConnexions().getLongueur()*100);
		build.append("{");
		//le type
		build.append("\"type\":\"couches\",");//couches pour le cerveau a couches
		//les connexions venant des input
		toStringJsonPartiel(build, getListeInput(), Cerveau.INPUT);
		//les connexions venant de l'interieur
		if(couchesInternes!=null) {
			build.append(",");
			for(int i=0; i<couchesInternes.length; i++) {
				toStringJsonPartiel(build, couchesInternes[i], Cerveau.INTERNE);
				if(i!=couchesInternes.length-1) {
					build.append(",");
				}
			}
		}
		build.append("}");
		return build.toString();
	}
	
	//regarder github.com/kpodlaski/introductionToAspectJ
	
	//TODO reecrire toByte()

	
	/**
	 * adaptation du equals pour un cerveau a couches
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CerveauACouches other = (CerveauACouches) obj;
		if (getListeInput().length!=other.getListeInput().length 
				|| getListeOutput().length!=other.getListeOutput().length 
				|| getListeInterne().length!=other.getListeInterne().length) {
			return false;
		}
		if((couchesInternes==null && other.couchesInternes!=null)
				|| (couchesInternes!=null && other.couchesInternes==null)) {
			return false;
		}
		if(couchesInternes!=null 
				&& (couchesInternes[0].length!=other.couchesInternes[0].length 
				|| couchesInternes.length!=other.couchesInternes.length)) {
			return false;
		}
		return getListeConnexions().equals(other.getListeConnexions());
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	
	
}
