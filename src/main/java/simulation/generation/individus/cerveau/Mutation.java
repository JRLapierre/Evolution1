package simulation.generation.individus.cerveau;

import java.nio.ByteBuffer;

import outils.Aleatoire;
import outils.ListeChaine;
import outils.interfaces.Representable;

/**
 * classe pour les main.mutations d'une génération à l'autre.
 * Initialise en debut de simulation
 * @author jrl
 *
 */
public class Mutation implements Representable{
	
	//-------------------------------------------------------------------------------
	//variables
	
	/**
	 * generateur de nombres aleatoires
	 */
	private Aleatoire aleatoire;
	
	/**
	 * taux de chance de creation d'une connexion allant de 0 à 100
	 * valeur par defaut : 0
	 */
	private int tauxCreation=0;
	
	/**
	 * taux de chance de suppression d'une connexion allant de 0 à 100
	 * valeur par defaut : 0
	 */
	private int tauxSuppression=0;
	
	/**
	 * taux de chance de changement de facteur d'une connexion allant de 0 à 100
	 * valeur par defaut : 0
	 */
	private int tauxMutationFacteur=0;
	
	/**
	 * taux de changement du facteur d'une connexion
	 * le taux sera aléatoire entre -maxChangementFacteur et maxChangementFacteur
	 * valeur par defaut : 0
	 */
	private float maxChangementFacteur=0;
	
	/**
	 * taux de chance de mutation de la source ou de la fin de la connexion
	 * allant de 0 a 100
	 * valeur par defaut : 0
	 */
	private int tauxMutationNeurone=0;
	
	//-------------------------------------------------------------------------------
	//constructeur
	
	
	/**
	 * constructeur partiel pour faciliter l'initialisation.
	 * les setteurs devront être utilisés pour completer les mutations.
	 * @param graine la graine de generation de nombres aleatoires
	 */
	public Mutation(int graine) {
		this.aleatoire=new Aleatoire(graine);
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
	
	
	/**
	 * constructeur a partir d'un enregistrement binaire
	 * @param bb le ByteBuffer contenant les infos
	 */
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
	
	
	//----------------------------------------------------------------------------------------
	//setteurs
	
	
	/**
	 * setteur pour changer la graine
	 * @param graine la graine de generation de nombres aleatoires
	 * @return this l'objet modifié
	 */
	public Mutation setGraine(int graine) {
		this.aleatoire=new Aleatoire(graine);
		return this;
	}
	
	/**
	 * setteur permettant de mettre en place la creation et suppression des connexions
	 * @param tauxCreation 		le taux de creation de nouvelle connexions
	 * @param tauxSuppression 	le taux de suppresssion de connexions
	 * @return this l'objet modifié
	 */
	public Mutation setTauxCreationSuppression(int tauxCreation, int tauxSuppression) {
		this.tauxCreation=corrigeTaux(tauxCreation);
		this.tauxSuppression=corrigeTaux(tauxSuppression);
		return this;
	}
	
	/**
	 * setteur permettant de mettre en place la mutation de la puissance des connexions.
	 * @param tauxMutationFacteur	le taux de mutation du facteur des connexions
	 * @param maxChangementFacteur	le taux maximal de changement en cas de mutation du facteur
	 * @return this l'objet modifié
	 */
	public Mutation setMutationFacteur(int tauxMutationFacteur, float maxChangementFacteur) {
		this.tauxMutationFacteur=corrigeTaux(tauxMutationFacteur);
		this.maxChangementFacteur=maxChangementFacteur;
		return this;
	}
	
	
	/**
	 * setteur permettant de mettre en place le changement d'extremites des connexions.
	 * @param tauxMutationNeurone	le taux de changement de neurone de l'extremite d'une connexion
	 * @return this l'objet simplifié
	 */
	public Mutation setTauxMutationNeurone(int tauxMutationNeurone) {
		this.tauxMutationNeurone=corrigeTaux(tauxMutationNeurone);
		return this;
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
				cerveau.getListeInput().length +
				cerveau.getListeInterne().length +
				cerveau.getListeOutput().length-1);
		if (a<cerveau.getListeInput().length) {
			return cerveau.getListeInput()[a];
		}
		a-=cerveau.getListeInput().length;
		if (a<cerveau.getListeInterne().length) {
			return cerveau.getListeInterne()[a];
		}
		else {
			a-=cerveau.getListeInterne().length;
			return cerveau.getListeOutput()[a];
		}
	}
	
	/**
	 * fonction d'ajout de connexions
	 * @param cerveau
	 */
	private void ajoutConnexion(Cerveau cerveau) {
		Neurone origine;
		Neurone cible;
		for (int i=0; i < cerveau.getListeInput().length + cerveau.getListeInterne().length +
				cerveau.getListeOutput().length; i++) {
			if (aleatoire.aleatInt(0,100)<=tauxCreation) {
				origine=neuroneAleat(cerveau);
				cible=neuroneAleat(cerveau);
				cerveau.addNewConnexion(
						(float) aleatoire.aleatDouble(
								-maxChangementFacteur, 
								maxChangementFacteur), 
						origine.getType(), origine.getNumero(), 
						cible.getType(), cible.getNumero());
			}
		}
	}
	
	/**
	 * fonction de suppression de connexions
	 * parcours la liste de connexion et en supprime quelques unes
	 * @param listeConnexions la liste de connexions a changer
	 */
	private void supprConnexion(ListeChaine<Connexion> listeConnexions) {
		Connexion c=listeConnexions.getSuivant();
		while (c!=null) {
			if (aleatoire.aleatInt(0,100)<=tauxSuppression) {
				listeConnexions.delElt(c);
			}
			c=listeConnexions.getSuivant();
		}
	}
	
	
	/**
	 * fonction de changement du facteur des connexions
	 * @param listeConnexions la liste des connexions a changer
	 */
	private void changeFacteur(ListeChaine<Connexion> listeConnexions) {
		Connexion c=listeConnexions.getSuivant();
		while (c!=null) {
			if (aleatoire.aleatInt(0,100)<=tauxMutationFacteur) {
				listeConnexions.getActuel()
				.updateFacteur((float) aleatoire.aleatDouble(
						-maxChangementFacteur, maxChangementFacteur));
			}
			c=listeConnexions.getSuivant();
		}
	}
	
	/**
	 * fonction qui change aleatoirement les extrêmités de connexions
	 * @param cerveau le cerveau a changer
	 */
	private void changeExtremites(Cerveau cerveau) {
		while (cerveau.getListeConnexions().getSuivant()!=null) {
			if (aleatoire.aleatInt(0,100)<=tauxMutationNeurone) {
				cerveau.getListeConnexions().getActuel()
				.updateOrigine(neuroneAleat(cerveau));
			}
			if (aleatoire.aleatInt(0,100)<=tauxMutationNeurone) {
				cerveau.getListeConnexions().getActuel()
				.updateCible(neuroneAleat(cerveau));
			}
		}
	}
	
	
	/**
	 * grosse fonction de mutation du cerveau
	 * @param cerveau le cerveau a changer
	 */
	protected void evolution(Cerveau cerveau) {
		//prudence
		cerveau.getListeConnexions().resetParcours();
		//suppression de connexion
		if(tauxSuppression!=0) supprConnexion(cerveau.getListeConnexions());
		//changement des extremites d'une connexion
		if(tauxMutationNeurone!=0) changeExtremites(cerveau);
		//change le facteur d'une connexion
		if(tauxMutationFacteur!=0) changeFacteur(cerveau.getListeConnexions());
		//ajout de connexion
		if(tauxSuppression!=0) ajoutConnexion(cerveau);
	}
	
	
	
	/**
	 * fonction de fusion de deux cerveaux
	 * @param c1 le cerveau du pere
	 * @param c2 le cerveau de la mere
	 * @return un cerveau issu de la fusion des deux cerveaux precents
	 */
	protected Cerveau evolution(Cerveau c1, Cerveau c2) {
		Cerveau c=new Cerveau(c1.getListeInput().length, 
				c1.getListeOutput().length, c1.getListeInterne().length);
		//fonction locale de clonage pour dupliquer sans risque les listes
		ListeChaine<Connexion> l1=c1.getListeConnexions().replique();
		ListeChaine<Connexion> l2=c2.getListeConnexions().replique();
		
		//gestion des cas ou l'ID est le même
		fusionMemeID(c, l1, l2);
		
		//gestion des connexions restantes
		fusionListesConnexion(c, l1, l2);
		
		return c;
	}

	
	/**
	 * fonction de fusion des connexions avec le même id.
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
				//si l'id est le même
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
	 * Supprime au passage les elements déja traités dans les listes des parents
	 * Est utilisé dans fusionMemeID
	 * @param c1 la premiere connexion avec un id donne
	 * @param c2 la deuxieme connexion avec le même id
	 * @return une connexion fusionnée
	 */
	private Connexion fusionConnexion(Connexion c1, Connexion c2) {
		Connexion c3=c1.replique();
		c3.updateFacteur(
				(float) aleatoire.aleatDouble(c1.getFacteur(), c2.getFacteur())
				- c3.getFacteur());
		int n=aleatoire.aleatInt(1);
		if (n==0) {
			c3.updateOrigine(c1.getOrigine());
		} else {
			c3.updateOrigine(c2.getOrigine());
		}
		n=aleatoire.aleatInt(1);
		if (n==0) {
			c3.updateCible(c1.getCible());
		} else {
			c3.updateCible(c2.getCible());
		}
		return c3;
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
