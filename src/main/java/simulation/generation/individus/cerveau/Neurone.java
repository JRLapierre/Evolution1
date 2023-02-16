package simulation.generation.individus.cerveau;

import java.nio.ByteBuffer;

import simulation.Enregistrable;

/**
 * cette classe simule une neuronne, qui recoit des infos et qui en renvoie.
 * Les neurones recoivent un signal soit par impulsion exterieure par les 
 * neurones de type input, soit par une connexion.
 * @author jrl
 *
 */
public class Neurone implements Enregistrable {

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
	 * la somme des entrées recues par les connections
	 * sa valeur est entre -5 et 5
	 */
	private float puissance=0;
	
	//--------------------------------------------------------------------------------
	//constructeur
	
	/**
	 * constructeur
	 * @param type peut être input, output ou interne
	 * @param numero l'emplacement dans la liste
	 */
	protected Neurone(String type, int numero) {
		this.type=type;
		this.numero=numero;
	}

	//---------------------------------------------------------------------
	//fonction de controle
	
	/**
	 * cette fonction permet de limiter la puissance contenue par une Neurone.
	 * Si la puissance excède 5, elle est ramené à 5
	 * Si la puissance est en dessous de -5, elle est ramenée à -5
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
	 * setter pour enregistrer la valeur (neurone d'entrée du cerveau)
	 * cette fonction est utilisée pour les input
	 * @param puissance la puissance qu'on souhaite mettre
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
	 * @return la valeur stockée dans la neurone
	 */
	public float getPuissance() {
		return puissance;
	}
	
	/**
	 * getteur pour le type
	 * @return le type
	 */
	protected String getType() {
		return type;
	}

	/**
	 * getteur pour le numero
	 * @return le numero
	 */
	protected int getNumero() {
		return numero;
	}
	
	//--------------------------------------------------------------------------------
	//en lien avec le fonctionnement dynamique du cerveau


	/**
	 * cette fonction permet de mettre à jour la puissance pour cumuler celles 
	 * venant des connexions
	 * @param puissance un signal recu d'une connexion
	 * @return les pertes
	 */
	protected float updatePuissance(float puissance) {
		return setPuissance(this.puissance+puissance);
	}
	
	/**
	 * remet à 0 la puissance (pour après le passage d'un signal)
	 */
	protected void resetPuissance() {
		this.puissance=0;
	}
	
	//--------------------------------------------------------------------
	//methodes d'enregistrable
	
	/**
	 * fonction a appeler sans probleme dans la connexion
	 * @return un morceau de code json contenant le type et le numero de la Neurone.
	 */
	public String toStringJson() {
		return "{"
		+ "\"type\":\"" + type + "\","
		+ "\"numero\":" + numero + "}";
	}
	
	/**
	 * fonction qui renvoie un tableau de byte correspondant au neuronne actuel
	 * fait toujours 3 byte de long
	 */
	public byte[] toByte() {
		ByteBuffer b=ByteBuffer.allocate(3);
		if (type.equals("input")) b.put((byte) 1);
		else if (type.equals("interne")) b.put((byte) 2);
		else if (type.equals("output")) b.put((byte) 3);
		else b.put((byte) 0);
		b.putShort((short) numero);
		return b.array();
	}
	
	/**
	 * une fonction qui dit la longueur de toByte
	 * @return 3
	 */
	public int toByteLongueur() {
		return 3;
	}


	//---------------------------------------------------------------------
	//autres fonctions
	
	/**
	 * fonction qui existe pour comparer des neurones de cerveau différents, 
	 * même si la liste de Connexion associés n'est pas la même
	 * @param other l'objet à comparer
	 * @return true si les deux neurones ont le même type et le même numero
	 */
	protected boolean equalsRoles(Neurone other) {
		if (other == null)
			return false;
		return numero == other.numero 
				&& type.equals(other.type);
	}
	
	
}
