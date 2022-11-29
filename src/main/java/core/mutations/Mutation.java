package core.mutations;

import core.cerveau.Cerveau;
import core.cerveau.Connexion;
import core.cerveau.Neurone;
import outils.aleatoire.Aleatoire;

/**
 * classe pour les main.mutations d'une génération à l'autre.
 * Initialise en debut de simulation
 * @author jrl
 *
 */
public class Mutation {
	
	//-------------------------------------------------------------------------------
	//variables
	
	/**
	 * generateur de nombres aleatoires
	 */
	private Aleatoire aleatoire;
	
	/**
	 * taux de chance de creation d'une connexion allant de 0 à 100
	 */
	private int tauxCreation;
	
	/**
	 * taux de chance de suppression d'une connexion allant de 0 à 100
	 */
	private int tauxSuppression;
	
	/**
	 * taux de chance de changement de facteur d'une connexion allant de 0 à 100
	 */
	private int tauxMutationFacteur;
	
	/**
	 * taux de changement du facteur d'une connexion
	 * le taux sera aléatoire entre -maxChangementFacteur et maxChangementFacteur
	 */
	private float maxChangementFacteur;
	
	/**
	 * taux de chance de mutation de la source ou de la fin de la connexion
	 * allant de 0 a 100
	 */
	private int tauxMutationNeurone;
	
	//-------------------------------------------------------------------------------
	//constructeur
	
	//rajouter des garde-fou pour les valeurs
	
	/**
	 * initialisation des paramètres de mutation
	 * @param graine
	 * @param tauxCreation
	 * @param tauxSuppressionCreation
	 * @param tauxMutationFacteur
	 * @param maxChangementFacteur
	 * @param tauxMutationNeurone
	 */
	public Mutation(
			int graine,
			int tauxCreation,
			int tauxSuppression, 
			int tauxMutationFacteur, 
			float maxChangementFacteur,
			int tauxMutationNeurone
			) {
		this.aleatoire=new Aleatoire(graine);
		this.tauxCreation=corrigeTaux(tauxCreation);
		this.tauxSuppression=corrigeTaux(tauxSuppression);
		this.tauxMutationFacteur=corrigeTaux(tauxMutationFacteur);
		this.maxChangementFacteur=maxChangementFacteur;
		this.tauxMutationNeurone=corrigeTaux(tauxMutationNeurone);
	}
	
	/**
	 * constructeur a partir d'un string format json
	 * @param sub le substring correspondant aux carracteristiques
	 */
	public Mutation(String sub) {
		this.aleatoire=new Aleatoire(Integer.parseInt(sub.substring(10, 
				sub.indexOf(",\"tauxCreation\"")))+1%2147483647);
		this.tauxCreation=Integer.parseInt(sub.substring(
				sub.indexOf("\"tauxCreation\"")+15, 
				sub.indexOf(",\"tauxSuppression\"")));
		this.tauxSuppression=Integer.parseInt(sub.substring(
				sub.indexOf("\"tauxSuppression\"")+18, 
				sub.indexOf(",\"tauxMutationFacteur\"")));
		this.tauxMutationFacteur=Integer.parseInt(sub.substring(
				sub.indexOf("\"tauxMutationFacteur\":")+22, 
				sub.indexOf(",\"maxChangementFacteur\"")));
		this.maxChangementFacteur=Float.parseFloat(sub.substring(
				sub.indexOf("\"maxChangementFacteur\":")+23, 
				sub.indexOf(",\"tauxMutationNeurone\"")));
		this.tauxMutationNeurone=Integer.parseInt(sub.substring(
				sub.indexOf("\"tauxMutationNeurone\":")+22, 
				sub.indexOf("}}")));
	}
	
	//-------------------------------------------------------------------------------
	//fonctions d'initialisation
	
	/**
	 * fonction privée qui s'assure que le taux de changement soit entre 0 et 100
	 * @param taux
	 * @return 0 si le taux est inférieur à 0, 100 si le taux est supérieur à 100
	 *  et taux lui même sinon
	 */
	private int corrigeTaux(int taux) {
		if (taux<0) {
			taux=0;
		}
		else if(taux>100){
			taux=100;
		}
		return taux;
	}
	
	//-------------------------------------------------------------------------------
	//fonctions de mutation
	
	/**
	 * fonction de selection d'une neurone aleatoire
	 * @param cerveau
	 * @return une nerone parmi toutes
	 */
	private Neurone neuroneAleat(Cerveau cerveau) {
		int a=aleatoire.aleatInt(0,
				cerveau.getNbInput() +
				cerveau.getNbNeurones() +
				cerveau.getNbOutput()-1);
		if (a<cerveau.getNbInput()) {
			return cerveau.getListeInput()[a];
		}
		a-=cerveau.getNbInput();
		if (a<cerveau.getNbNeurones()) {
			return cerveau.getListeNeurones()[a];
		}
		else {
			a-=cerveau.getNbNeurones();
			return cerveau.getListeOutput()[a];
		}
	}
	
	/**
	 * fonction d'ajout de conexions
	 * @param cerveau
	 */
	private void ajoutConnexion(Cerveau cerveau) {
		for (int i=0; i < cerveau.getNbInput() + cerveau.getNbNeurones() +
				cerveau.getNbOutput(); i++) {
			if (aleatoire.aleatInt(0,100)<=tauxCreation) {
				cerveau.addConnexion(new Connexion(
						(float) aleatoire.aleatDouble(
								-maxChangementFacteur, 
								maxChangementFacteur), 
						neuroneAleat(cerveau), 
						neuroneAleat(cerveau)));
			}
		}
	}
	
	/**
	 * fonction de suppression de connexions
	 * parcours la liste de connexion et en supprime quelques unes
	 * @param cerveau
	 */
	private void supprConnexion(Cerveau cerveau) {
		Connexion c=cerveau.getListeConnexions().getSuivant();
		while (c!=null) {
			if (aleatoire.aleatInt(0,100)<=tauxSuppression) {
				cerveau.delConnextion(c);
			}
			c=cerveau.getListeConnexions().getSuivant();
		}
	}
	
	
	/**
	 * fonction de changement des attributs des connexions
	 * @param cerveau
	 */
	private void changeConnexion(Cerveau cerveau) {
		Connexion c=cerveau.getListeConnexions().getSuivant();
		int i=0;
		while (c!=null) {
			if (aleatoire.aleatInt(0,100)<=tauxMutationNeurone) {
				cerveau.getListeConnexions().getElement(i)
				.updateOrigine(neuroneAleat(cerveau));
			}
			if (aleatoire.aleatInt(0,100)<=tauxMutationNeurone) {
				cerveau.getListeConnexions().getElement(i)
				.updateCible(neuroneAleat(cerveau));
			}
			if (aleatoire.aleatInt(0,100)<=tauxMutationFacteur) {
				cerveau.getListeConnexions().getElement(i)
				.updateFacteur((float) aleatoire.aleatDouble(
						-maxChangementFacteur, maxChangementFacteur));
			}
			c=cerveau.getListeConnexions().getSuivant();
			i++;
		}
	}
	
	
	/**
	 * grosse fonction de mutation du main.cerveau
	 * @param cerveau le cerveau a changer
	 */
	public Cerveau evolution(Cerveau cerveau) {
		//suppression de connexion
		supprConnexion(cerveau);
		//changement des extremites d'une connexion ainsi que sa valeur
		changeConnexion(cerveau);
		//ajout de connexion
		ajoutConnexion(cerveau);
		
		return cerveau;
	}
	
	//-------------------------------------------------------------------------------
	//fonction d'affichage
	
	/**
	 * fonction toStringJson qui genere un string compatible au JSon
	 * @return un string au JSon
	 */
	public String toStringJson() {
		return "{"
		+ "\"graine\":" + aleatoire.getGraine() + ","
		+ "\"tauxCreation\":" + tauxCreation + ","
		+ "\"tauxSuppression\":" + tauxSuppression + ","
		+ "\"tauxMutationFacteur\":" + tauxMutationFacteur + ","
		+ "\"maxChangementFacteur\":" + maxChangementFacteur + ","
		+ "\"tauxMutationNeurone\":" + tauxMutationNeurone
		+ "}";
	}
}
