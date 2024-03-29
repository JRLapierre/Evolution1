package simulation.generation.individus;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.ByteBuffer;

import outils.interfaces.Representable;
import simulation.generation.individus.cerveau.Cerveau;

/**
 * cette classe repr�sente un individu.
 * l'invidu peut etre de cinq types : 
 * - original, cr�� depuis la racine pour servir de base � tout le reste
 * - les clones parfait, copies parfaites de leur parents
 * - les clones mut�s, copies avec quelques mutations de leur parents
 * - les enfants de reproduction sexu�e, qui m�lange les carract�ristiques
 * de leurs parents et qui ont en plus quelques mutations
 * - les sauvegarde, qui sont des individus simplifies pour reprendre depuis une sauvegarde.
 * 
 * 
 * @author jrl
 *
 */
public abstract class Individu implements Representable {
	
	//-------------------------------------------------------------------------------
	//variables
	
	/**
	 * le cerveau de l'individu
	 */
	protected Cerveau cerveau;
	
	/**
	 * le nombre de generations precedant l'individu
	 */
	protected int generation;
	
	/**
	 * l'identifiant de l'individu
	 */
	protected int id;
	
	/**
	 * le nombre d'individus
	 */
	private static int nbIndividus=0;
	
	/**
	 * le score pour evaluer la performance
	 */
	protected float score=0;
	
	//-------------------------------------------------------------------------------
	//constructeurs
	
	/**
	 * constructeur g�n�ral pour un individu, sans pr�cisions
	 * 
	 */
	protected Individu() {
		this.id=nbIndividus;
		nbIndividus++;
	}
	
	/**
	 * constructeur pour recreer des individus depuis une sauvegarde.
	 * @param sub le string qui decrit un individu
	 */
	protected Individu(String sub) {
		this.id=Integer.parseInt(sub.substring(
				sub.indexOf("\"id\":")+5, 
				sub.indexOf(",\"generation\"")));
		this.generation=Integer.parseInt(sub.substring(
				sub.indexOf("\"generation\":")+13, 
				sub.indexOf(",\"score\"")));
		this.score=Float.parseFloat(sub.substring(
				sub.indexOf("\"score\":")+8, 
				sub.indexOf(",\"cerveau\"")));
		this.cerveau=new Cerveau(sub);
		updateId();
	}
	
	//----------------------------------------------------------------------
	//"constructeur"
	
	/**
	 * fonction statique qui genere un individu
	 * @param bb le ByteBuffer contenant les inforamtions de l'individu
	 * @return l'individu decrit en binaire
	 */
	public static Individu regenereIndividu(ByteBuffer bb) {
		byte type=bb.get();
		switch(type) {
		case(0):
			return new Original(bb);
		case(1):
			return new CloneParfait(bb);
		case(2):
			return new CloneMute(bb);
		case(3):
			return new EnfantSexe(bb);
		case(4):
			return new Sauvegarde(bb);
		default:
			System.err.println("type inconu");
			return null;
		}
	}
	
	//----------------------------------------------------------------------
	//getteurs
	
	/**
	 * getteur pour l'id
	 * @return l'identifiant de l'individu
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * getteur pour le score
	 * @return le score de l'individu
	 */
	public float getScore() {
		return score;
	}
	
	/**
	 * getteur pour le cerveau
	 * @return le cerveau de l'individu
	 */
	public Cerveau getCerveau() {
		return cerveau;
	}
	
	/**
	 * getteur pour la generation de l'individu
	 * @return le numero de generation
	 */
	public int getGeneration() {
		return generation;
	}

	
	//----------------------------------------------------------------------
	//fonctions lies au fonctionnement actif
	
	/**
	 * met � jour le score pour l'evaluation et la selection
	 * @param changement le changement a apporter au score de l'individu
	 */
	public void updateScore(float changement) {
		this.score +=changement;
	}
	
	
	/**
	 * met a jour l'id maximal des individus
	 */
	protected void updateId() {
		if(this.id+1>nbIndividus) {
			Individu.nbIndividus=id+1;
		}
	}
	
	//-------------------------------------------------------------------------------
	//fonction de l'interface Representable
	
	/**
	 * fonction toStringJson incomplete qui reclamme une sous classe
	 * pour �tre compl�t�
	 */
	public String toStringJson() {
		//je laisse le d�but aux sous classes
		return ""
		+ "\"id\":" + id +","
		+ "\"generation\":" + generation + ","
		+ "\"score\":" + score + ","
		+ "\"cerveau\":"
		+ cerveau.toStringJson()
		//je ferme cette partie la pour l'instant
		+ "}}";
	}
	
	//les fonctions toByte() et toByteLongueur() 
	//sont integralement definies dans les sous classes
	
	/**
	 * fonction qui enregistre
	 * @param dossier le dossier dans lequel enregistrer
	 * @param format le format d'enregistrement (bin ou json)
	 */
	public void enregistre(String dossier, String format) {
		try {
	    	switch(format) {
	    	case("bin"):
            	FileOutputStream fos = new FileOutputStream(
            			dossier + "individu" + id + "." + format);
            	fos.write(this.toByte()); 
            	fos.flush();
            	fos.close();
	    		break;
	    	case("json"):
    		PrintWriter writer;
            	writer = new PrintWriter(dossier + "individu" + id + "." + format);
                writer.write(this.toStringJson());
                writer.flush();
                writer.close();
            break;
	        default:
	        	System.err.println("type de fichier inconnu");
	    	}
		} catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	
}
