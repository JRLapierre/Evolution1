package core.individus;

import java.io.File;
import java.io.PrintWriter;

import core.cerveau.Cerveau;
import outils.aleatoire.Aleatoire;

/**
 * cette classe repr�sente un individu.
 * l'invidu peut etre de quatre types : 
 * - original, cr�� depuis la racine pour servir de base � tout le reste
 * - les clones parfait, copies parfaites de leur parents
 * - les clones mut�s, copies avec quelques mutations de leur parents
 * - les enfants de reproduction sexu�e, qui m�lange les carract�ristiques
 * de leurs parents et qui ont en plus quelques mutations
 * 
 * 
 * @author jrl
 *
 */
public abstract class Individu {	
	
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
	private int id;
	
	/**
	 * le nombre d'individus
	 */
	private static int nbIndividus=0;
	
	/**
	 * le generateur de nombres aleatoires pour la reproduction sexuee
	 */
	protected static Aleatoire alea;
	
	/**
	 * le score pour evaluer la
	 */
	private float score=0;
	
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
	
	//----------------------------------------------------------------------
	//getteurs
	
	/**
	 * getteur pour l'id
	 * @return
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * getteur pour le score
	 * @return
	 */
	public float getScore() {
		return score;
	}
	
	/**
	 * getteur pour le cerveau
	 * @return
	 */
	public Cerveau getCerveau() {
		return cerveau;
	}
	
	/**
	 * getteur pour la generation de l'individu
	 * @return
	 */
	public int getGeneration() {
		return generation;
	}
	
	//----------------------------------------------------------------------
	//fonctions lies au fonctionnement actif
	
	/**
	 * met � jour le score pour l'evaluation et la selection
	 * @param score
	 */
	public void updateScore(float score) {
		this.score +=score;
	}
	
	//-------------------------------------------------------------------------------
	//fonction d'affichage
	
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
	
	//------------------------------------------------------------------------------------------
	//fonction d'enregistrement
	
	/**
	 * une methode qui enregistre les resultats de chaque individu
	 * @param numSimulation le numero de la simulation servant d'identifiant au dossier
	 * @param sousDossier le sous dossier voulu pour mettre plus d'ordre
	 * @param nomFic le nom du ficher � enregistrer
	 */
	public void creeEnregistrementJson(String nomSimulation) {
        try {
        	//si le dossier n'existe pas on le cr��
        	File f=new File("enregistrements\\simulation" + nomSimulation
        			+ "\\generation" + generation + "\\");
        	f.mkdirs();
            PrintWriter writer = new PrintWriter(
            		"enregistrements\\simulation" + nomSimulation
            		+ "\\generation" + generation
            		+ "\\individu" + id + ".json");
            writer.write(this.toStringJson());
            
            writer.flush();
            writer.close();
            System.out.println("individu enregistre");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	
	
	
	
}
