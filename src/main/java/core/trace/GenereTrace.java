package core.trace;

import java.io.File;
import java.io.PrintWriter;

/**
 * cette classe abstraite est la pour generer les fichiers des individus dans
 * les bons dossiers.
 * @author jrl
 *
 */
public abstract class GenereTrace {

	//-------------------------------------------------------------------------------
	//fonctions d'enregistrement

	
	/**
	 * une methode qui enregistre les resultats de chaque individu
	 * @param numSimulation le numero de la simulation servant d'identifiant au dossier
	 * @param sousDossier le sous dossier voulu pour mettre plus d'ordre
	 * @param nomFic le nom du ficher à enregistrer
	 */
	public void creeEnregistrementJson(String nomSimulation, String sousDossier, String nomFic) {
        try {
        	//si le dossier n'existe pas on le créé
        	File f=new File("enregistrements\\simulation" + nomSimulation
        			+ "\\" + sousDossier + "\\");
        	f.mkdirs();
            PrintWriter writer = new PrintWriter(
            		"enregistrements\\simulation" + nomSimulation
            		+ "\\" + sousDossier
            		+ "\\" + nomFic + ".json");
            writer.write(this.toStringJson());
            
            writer.flush();
            writer.close();
            System.out.println("fichier cree");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
		
	/**
	 * fonction remplacant le toString pour generer un String correspondant au format
	 * json
	 * @return
	 */
	public abstract String toStringJson();
	
	
	
}
