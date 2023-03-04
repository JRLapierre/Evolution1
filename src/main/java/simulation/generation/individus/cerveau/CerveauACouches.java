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
	
	//TODO constructeur depuis un enregistrement JSON
	
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
						listeOrigine[i].getType(), i, 
						listeCible[j].getType(), j);
			}
		}
	}
	
	/**
	 * methode qui met les connexions dans le cerveau
	 */
	private void relieCouches() {
		if(couchesInternes==null) {
			relieCouche(getListeInput(), getListeOutput(), 1.0f / getListeOutput().length);
		}
		else {
			float facteur = 1.0f / couchesInternes[0].length;
			relieCouche(getListeInput(), couchesInternes[0], 1.0f/getListeInput().length);
			for(int i=0; i<couchesInternes.length-1; i++) {
				relieCouche(couchesInternes[i], couchesInternes[i+1], facteur);
			}
			relieCouche(couchesInternes[couchesInternes.length-1], getListeOutput(),facteur);
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
	
	//-----------------------------------------------------------------------------------------
	//methodes
	
	//TODO reecrire analyse pour optimiser
	
	//TODO reecrire replique()
	
	//TODO reecrire toStringJson()
	
	//TODO reecrire toByte()

	
	@Override
	/**
	 * adaptation du equals pour un cerveau a couches
	 */
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
		return getListeConnexions().equals(other.getListeConnexions())
				&& couchesInternes == other.couchesInternes;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	
	
}
