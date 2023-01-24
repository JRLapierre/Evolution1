package core.generation.individus.mutations;

import java.nio.ByteBuffer;

import core.Enregistrable;
import core.generation.individus.cerveau.Cerveau;
import core.generation.individus.cerveau.Connexion;
import core.generation.individus.cerveau.Neurone;
import outils.Aleatoire;
import outils.ListeChaine;

/**
 * classe pour les main.mutations d'une g�n�ration � l'autre.
 * Initialise en debut de simulation
 * @author jrl
 *
 */
public class Mutation implements Enregistrable{
	
	//-------------------------------------------------------------------------------
	//variables
	
	/**
	 * generateur de nombres aleatoires
	 */
	private Aleatoire aleatoire;
	
	/**
	 * taux de chance de creation d'une connexion allant de 0 � 100
	 */
	private int tauxCreation;
	
	/**
	 * taux de chance de suppression d'une connexion allant de 0 � 100
	 */
	private int tauxSuppression;
	
	/**
	 * taux de chance de changement de facteur d'une connexion allant de 0 � 100
	 */
	private int tauxMutationFacteur;
	
	/**
	 * taux de changement du facteur d'une connexion
	 * le taux sera al�atoire entre -maxChangementFacteur et maxChangementFacteur
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
	 * initialisation des param�tres de mutation
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
	
	
	//constructeur a partir d'un enregistrement binaire
	public Mutation(ByteBuffer bb) {
		this.aleatoire=new Aleatoire(bb.getInt()+1%2147483647);
		this.tauxCreation=bb.get();
		this.tauxSuppression=bb.get();
		this.tauxMutationFacteur=bb.get();
		this.maxChangementFacteur=bb.getFloat();
		this.tauxMutationNeurone=bb.get();
	}
	
	//-------------------------------------------------------------------------------
	//fonctions d'initialisation
	
	/**
	 * fonction priv�e qui s'assure que le taux de changement soit entre 0 et 100
	 * @param taux
	 * @return 0 si le taux est inf�rieur � 0, 100 si le taux est sup�rieur � 100
	 *  et taux lui m�me sinon
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
				cerveau.getNbInterne() +
				cerveau.getNbOutput()-1);
		if (a<cerveau.getNbInput()) {
			return cerveau.getListeInput()[a];
		}
		a-=cerveau.getNbInput();
		if (a<cerveau.getNbInterne()) {
			return cerveau.getListeInterne()[a];
		}
		else {
			a-=cerveau.getNbInterne();
			return cerveau.getListeOutput()[a];
		}
	}
	
	/**
	 * fonction d'ajout de conexions
	 * @param cerveau
	 */
	private void ajoutConnexion(Cerveau cerveau) {
		for (int i=0; i < cerveau.getNbInput() + cerveau.getNbInterne() +
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
	
	
	
	/**
	 * fonction de fusion de deux cerveaux
	 * @param c1 le cerveau du pere
	 * @param c2 le cerveau de la mere
	 * @return un cerveau issu de la fusion des deux cerveaux precents
	 */
	public Cerveau evolution(Cerveau c1, Cerveau c2) {
		Cerveau c=new Cerveau(c1.getNbInput(), c1.getNbOutput(), c1.getNbInterne());
		//fonction locale de clonage pour dupliquer sans risque les listes
		ListeChaine<Connexion> l1=duplique(c1.getListeConnexions());
		ListeChaine<Connexion> l2=duplique(c2.getListeConnexions());
		
		//gestion des cas ou l'ID est le m�me
		fusionMemeID(c, l1, l2);
		
		//gestion des connexions restantes
		fusionListesConnexion(c, l1, l2);
		
		return evolution(c);
	}
	
	
	/**
	 * fonction qui duplique de maniere securise une liste de connexion
	 * @param liste la liste de connexions a dupliquer
	 * @return la liste duplique
	 */
	private ListeChaine<Connexion> duplique(ListeChaine<Connexion> liste) {
		ListeChaine<Connexion> duplicat=new ListeChaine<>();
		Connexion c=liste.getSuivant();
		while (c!=null) {
			duplicat.ajout(c);
			c=liste.getSuivant();
		}
		return duplicat;
	}

	
	/**
	 * fonction de fusion des connexions avec le m�me id.
	 * des parents.
	 * @param c le cerveau a affecter
	 * @param liste1 la liste de neurones du cerveau du pere
	 * @param liste2 la liste de neurones du cerveau de la mere
	 */
	private void fusionMemeID(Cerveau c, 
			ListeChaine<Connexion> liste1, 
			ListeChaine<Connexion> liste2) {
		//on declare les connexions
		Connexion c1;
		Connexion c2;
		//premiere boucle
		c1=liste1.getSuivant();
		while (c1!=null) {
			//parcours de liste2
			c2=liste2.getSuivant();
			while (c2!=null) {
				//si l'id est le m�me
				if (c1.getId()==c2.getId()) {
					//fusion et ajout
					c.addConnexion(fusionConnexion(c1, c2));
					//suppression des listes
					liste1.delElt(c1);
					liste2.delElt(c2);
				}
				//iteration
				c2=liste2.getSuivant();
			}
			//iteration
			c1=liste1.getSuivant();
		}
		
	}
	
	
	
		
	/**
	 * fonction de fusion de deux connexions.
	 * Choisis aleatoirement les neurones d'origine et de cible entre celles
	 * des parents et choisis une puissance aleatoire entre les valeurs
	 * Supprime au passage les elements d�ja trait�s dans les listes des parents
	 * Est utilis� dans fusionMemeID
	 * @param c1
	 * @param c2
	 * @return une connexion fusionn�e
	 */
	private Connexion fusionConnexion(Connexion c1, Connexion c2) {
		Neurone origine;
		Neurone cible;
		float facteur=(float) aleatoire.aleatDouble(c1.getFacteur(), c2.getFacteur());
		int n=aleatoire.aleatInt(1);
		if (n==0) {
			origine=c1.getOrigine();
		} else {
			origine=c2.getOrigine();
		}
		n=aleatoire.aleatInt(1);
		if (n==0) {
			cible=c1.getCible();
		} else {
			cible=c2.getCible();
		}
		return new Connexion(facteur, origine, cible, c1.getId());
	}
	
	/**
	 * fonction d'insertion aleatoire de neurones dans le cerveau
	 * @param c
	 * @param liste1
	 * @param liste2
	 * @return
	 */
	private void fusionListesConnexion(Cerveau c, 
			ListeChaine<Connexion> liste1, ListeChaine<Connexion> liste2) {
		//on fait une liste contenant les elements des deux listes
		ListeChaine<Connexion> liste=new ListeChaine<>();
		liste.concatene(liste1);
		liste.concatene(liste2);
		int n;
		Connexion con=liste.getSuivant();
		while (con!=null) {
			n=aleatoire.aleatInt(1);
			if (n==1) {
				c.addConnexion(con);
			}
			con=liste.getSuivant();
		}
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
	
	
	public byte[] toByte() {
		ByteBuffer bb=ByteBuffer.allocate(toByteLongueur());
		bb.putInt(aleatoire.getGraine());
		bb.put((byte) tauxCreation);
		bb.put((byte) tauxSuppression);
		bb.put((byte) tauxMutationFacteur);
		bb.putFloat(maxChangementFacteur);
		bb.put((byte) tauxMutationNeurone);
		return bb.array();
	}
	
	public int toByteLongueur() {
		return 12;
	}
}
