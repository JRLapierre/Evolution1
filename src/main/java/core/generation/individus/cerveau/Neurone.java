package core.generation.individus.cerveau;

/**
 * cette classe simule une neuronne, qui recoit des infos et qui en renvoie.
 * Les neurones recoivent un signal soit par impulsion exterieure par les 
 * neurones de type input, soit par une connexion.
 * @author jrl
 *
 */
public class Neurone {

	//-------------------------------------------------------------------------------
	//

	/**
	 * le type de la neurone (pour le toString)
	 */
	private String type;
	
	/**
	 * le numero dans la liste respective
	 */
	private int numero;
	
	/**
	 * la somme des entr�es recues par les connections
	 * sa valeur est entre -5 et 5
	 */
	private float puissance=0;
	
	//--------------------------------------------------------------------------------
	//constructeur
	
	/**
	 * constructeur
	 * @param type peut �tre input, output ou interne
	 * @param numero l'emplacement dans la liste
	 */
	public Neurone(String type, int numero) {
		this.type=type;
		this.numero=numero;
	}

	//---------------------------------------------------------------------
	//fonction de controle
	
	/**
	 * cette fonction permet de limiter la puissance contenue par une Neurone.
	 * Si la puissance exc�de 5, elle est ramen� � 5
	 * Si la puissance est en dessous de -5, elle est ramen�e � -5
	 * @param puissance
	 * @return les pertes
	 */
	private float limitePuissance(float puissance) {
		float pertes;
		if (this.puissance+puissance<-5) {
			pertes=-(5+this.puissance+puissance);
			this.puissance=-5;
		}
		else if (this.puissance+puissance>5) {
			pertes=(-5+this.puissance+puissance);
			this.puissance=5;
		}
		else {
			this.puissance+=puissance;
			pertes=0;
		}
		return pertes;
	}
	
	//-------------------------------------------------------------------------------
	//setteurs
	
	/**
	 * setter pour enregistrer la valeur (neurone d'entr�e du cerveau)
	 * cette fonction est utilis�e pour les input
	 * @param puissance
	 * @return les pertes
	 */
	public float setPuissance(float puissance) {
		this.puissance=0;
		return limitePuissance(puissance);
	}
	
	//-------------------------------------------------------------------------------
	//getteurs
	
	/**
	 * getter pour obtenir la valeur(neurone de sortie du cerveau)
	 * @return la valeur stock�e dans la neurone
	 */
	public float getPuissance() {
		return puissance;
	}
	
	/**
	 * getteur pour le type
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * getteur pour le numero
	 * @return
	 */
	public int getNumero() {
		return numero;
	}
	
	//--------------------------------------------------------------------------------
	//en lien avec le fonctionnement dynamique du cerveau


	/**
	 * cette fonction permet de mettre � jour la puissance pour cumuler celles 
	 * venant des connexions
	 * @param puissance un signal recu d'une connexion
	 * @return les pertes
	 */
	public float updatePuissance(float puissance) {
		return setPuissance(this.puissance+puissance);
	}
	
	/**
	 * remet � 0 la puissance (pour apr�s le passage d'un signal)
	 */
	public void resetPuissance() {
		this.puissance=0;
	}
	
	//--------------------------------------------------------------------
	//fonction d'affichage
	
	/**
	 * fonction a appeler sans probleme dans la connexion
	 * @return un morceau de code json contenant le type et le numero de la Neurone.
	 */
	public String toStringJson() {
		String str="{";
		str += "\"type\":\"" + type + "\",";
		str += "\"numero\":" + numero + "}";
		return str;
	}


	//---------------------------------------------------------------------
	//autres fonctions
	
	/**
	 * fonction qui existe pour comparer des neurones de cerveau diff�rents, 
	 * m�me si la liste de Connexion associ�s n'est pas la m�me
	 * @param obj l'objet � comparer
	 * @return true si les deux neurones ont le m�me type et le m�me numero
	 */
	public boolean equalsRoles(Neurone other) {
		if (other == null)
			return false;
		return numero == other.numero 
				&& type.equals(other.type);
	}
	
	
}
