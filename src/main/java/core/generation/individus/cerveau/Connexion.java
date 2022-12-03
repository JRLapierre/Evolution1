package core.generation.individus.cerveau;

/**
 * cette classe simule une connexion entre deux neurones.
 * Elle amplifie ou affaiblit le signal d'une neurone pour l'envoyer vers une autre.
 * les connextions sont suceptibles de muter, d'ou l'existance des fonctions update.
 * @author jrl
 *
 */
public class Connexion {
	
	//-------------------------------------------------------------------------------
	//variables
	
	/**
	 * variable statique mise pour generer les identifiants
	 */
	private static int nbConnexions=0;
	
	/**
	 * l'identifient d'une connexion pour mieux traquer
	 * est generalement gener� en lui attribuant la valeur de nbNeurones
	 */
	private int id;
	
	/**
	 * le facteur de changement de puissance du signal d'une neurone
	 *  vers l'autre. est entre -2 et 2.
	 */
	private float facteur;
	
	/**
	 * la neurone de source de la connexion
	 */
	private Neurone origine;
	
	/**
	 * la cible de la connexion
	 */
	private Neurone cible;
	
	/**
	 * la valeur transport�e par la connextion
	 * utilis� pour stocker temporairementle signal entre 2 neurones
	 */
	private float force=0;

	//-------------------------------------------------------------------------------
	//constructeur
	
	/**
	 * le constructeur pour une nouvelle Connexion unique
	 * @param puissance le facteur multiplicateur
	 * @param origine la neurone d'origine
	 * @param cible la neurone cible de la connexion
	 */
	public Connexion(float facteur, Neurone origine, Neurone cible) {
		this.facteur = corrigeFacteur(facteur);
		this.origine = origine;
		this.cible = cible;
		this.id=nbConnexions;
		nbConnexions++;
	}
	
	/**
	 * un constructeur pour faire une copie d'une neurone.
	 * Limiter l'usage pour le clonage
	 * @param facteur
	 * @param origine
	 * @param cible
	 * @param id
	 */
	public Connexion(float facteur, Neurone origine, Neurone cible, int id) {
		this.facteur = corrigeFacteur(facteur);
		this.origine = origine;
		this.cible = cible;
		this.id=id;
	}
	
	//constructeur pour creer depuis un enregistrement json
	public Connexion(String sub, Cerveau cerveau) {
		//faire de la sous decoupe
		this.id=Integer.parseInt(sub.substring(
				sub.indexOf("\"id\":")+5, 
				sub.indexOf(",\"facteur\":")));
		this.facteur=Float.valueOf(sub.substring(
				sub.indexOf(",\"facteur\":")+11, 
				sub.indexOf(",\"origine\":{")));
		//neurones
		this.origine=trouveNeurone(sub.substring(
				sub.indexOf("\"origine\":")+10, 
				sub.indexOf(",\"cible\":")), cerveau);
		this.cible=trouveNeurone(sub.substring(
				sub.indexOf("\"cible\":")+8, 
				sub.indexOf("}}")+1), cerveau);
		//pour remettre le compte des neurones assez haut
		if (this.id>nbConnexions) {
			nbConnexions=this.id;
		}
	}
	
	//---------------------------------------------------------------------
	//fonction de controle
	
	/**
	 * fonction qui limite le facteur d'une connexion en cas d'evolution
	 * @param facteur l'ancien facteur, potentiellement incorrect.
	 * @return le facteur corrig�
	 */
	private float corrigeFacteur(float facteur) {
		if(facteur<-2) return -2;
		if(facteur>2) return 2;
		return facteur;
	}
	
	/**
	 * fonction priv�e pour determiner une neurone a partir d'un string
	 * @param sub la sous chaine de carractere correspondant a la neurone
	 * @param cerveau le cerveau contenant la neurone
	 * @return la neurone correspondant a cette chaine de carracteres
	 */
	private Neurone trouveNeurone(String sub, Cerveau cerveau) {
		String type=sub.substring(9, 14);
		if (type.equals("input")) {
			return cerveau.getListeInput()[Integer.parseInt(sub.substring(25, 26))];
		}
		else if (type.equals("outpu")) {
			return cerveau.getListeOutput()[Integer.parseInt(sub.substring(26, 27))];
		}
		else if (type.equals("inter")) {
			return cerveau.getListeNeurones()[Integer.parseInt(sub.substring(27, 28))];
		}
		else {//flemme de faire une vraie erreur
			System.err.println("erreur : type de neurone inconnu");
			return null;
		}
		
	}
	
	//-------------------------------------------------------------------------------
	//getteurs
	
	/**
	 * getteur pour obtenir la neurone d'origine
	 * @return
	 */
	public Neurone getOrigine() {
		return this.origine;
	}
	
	/**
	 * getteur pour obtenir la neurone de cible
	 * @return
	 */
	public Neurone getCible() {
		return this.cible;
	}
	
	/**
	 * getteur pour l'id
	 * @return
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * getteur pour l'id
	 * @return
	 */
	public float getFacteur() {
		return this.facteur;
	}
	
	//-------------------------------------------------------------------------------
	//fonctions lies au fonctionnement dynamique du cerveau
	
	/**
	 * fait la premi�re partie de la transmission d'un signal
	 * d'une neurone � l'autre : la valeur de la neurone se fait
	 * transf�rer dans la connextion
	 */
	public void transitionIn() {
		force=origine.getPuissance()*facteur;
	}
	
	/**
	 * deuxi�me partie de la transmission du signal :
	 * transmet sa force � la neurone suivante puis
	 * r�initialise sa force
	 * @return les pertes
	 */
	public float transitionOut() {
		float pertes=cible.updatePuissance(force);
		force=0;
		return pertes;
	}
	
	//-------------------------------------------------------------------------------
	//fonctions lies a l'evolution du cerveau
	
	/**
	 * cette fonction met � jour la puissance suite � une mutation
	 * @param update l'amplification ou l'affaiblissement du facteur
	 */
	public void updateFacteur(float update) {
		this.facteur=corrigeFacteur(facteur+update);
	}
	
	/**
	 * cette fonction change la neurone d'origine suite � une mutation
	 * @param origine
	 */
	public void updateOrigine(Neurone origine) {
		this.origine.delConnexion(this);
		this.origine=origine;
		this.origine.addConnexion(this);
	}
	
	/**
	 * cette fonction change la neurone de cible suite � une mutations
	 * @param cible
	 */
	public void updateCible(Neurone cible) {
		this.cible.delConnexion(this);
		this.cible=cible;
		this.cible.addConnexion(this);
	}
		
	//---------------------------------------------------------------------
	//fonction d'affichage

	/**
	 * renvoie des infos sur la neurone pour un format json
	 */
	public String toStringJson() {
		return "\"connexion" + id + "\":{"
		+ "\"id\":" + id + ","
		+ "\"facteur\":" + facteur + ","
		+ "\"origine\":" + origine.toStringJson2() + ","
		+ "\"cible\":" + cible.toStringJson2()
		+ "}";
	}
	
	//---------------------------------------------------------------------
	//autres fonctions
	
	@Override
	/**
	 * adapte la fonction equals aux specificit�s du role
	 * @param obj
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Connexion other = (Connexion) obj;
		return origine.equalsRoles(other.origine)
				&& Float.floatToIntBits(facteur) == Float.floatToIntBits(other.facteur)
				&& cible.equalsRoles(other.cible)
				&& id==other.id;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
		
}
