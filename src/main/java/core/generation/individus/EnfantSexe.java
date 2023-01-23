package core.generation.individus;

import java.nio.ByteBuffer;

import core.generation.individus.cerveau.Cerveau;
import core.generation.individus.cerveau.Connexion;
import core.generation.individus.cerveau.Neurone;
import outils.ListeChaine;

/**
 * classe qui représente un individu issu de reproduction sexuée
 * @author jrl
 *
 */
public class EnfantSexe extends Individu{

	//------------------------------------------------------------------------------
	//variables
	
	/**
	 * l'id du père de l'individu, de la génération précédente
	 */
	private int idParent1;
	
	/**
	 * l'id de la mere de l'individu, de la generation precedente
	 */
	private int idParent2;

	//-------------------------------------------------------------------------------
	//constructeur
	
	/**
	 * constructeur pour un enfant issu de reproduciton sexuee, 
	 * avec des mutations par rapport a ses parent
	 * @param cerveau
	 * @param generation
	 * @param parent
	 */
	public EnfantSexe(Individu parent1, Individu parent2) {
		super();
		this.idParent1 = parent1.getId();
		this.idParent2 = parent2.getId();
		this.generation=parent1.generation+1;
		this.cerveau=mutation.evolution(fusion(parent1.cerveau, parent2.cerveau));
	}
	
	
	//constructeur pour un enfant a partir d'un fichier binaire
	public EnfantSexe(ByteBuffer bb) {
		this.id=bb.getInt();
		this.idParent1=bb.getInt();
		this.idParent2=bb.getInt();
		this.generation=bb.getInt();
		this.score=bb.getFloat();
		this.cerveau=new Cerveau(bb);
	}
	
	//----------------------------------------------------------------------
	//fonction d'initialisation
	
	
	
	/**
	 * fonction de fusion de deux cerveaux
	 * @param c1 le cerveau du pere
	 * @param c2 le cerveau de la mere
	 * @return un cerveau issu de la fusion des deux cerveaux precents
	 */
	private Cerveau fusion(Cerveau c1, Cerveau c2) {
		Cerveau c=new Cerveau(c1.getNbInput(), c1.getNbOutput(), c1.getNbInterne());
		//fonction locale de clonage pour dupliquer sans risque les listes
		ListeChaine<Connexion> l1=duplique(c1.getListeConnexions());
		ListeChaine<Connexion> l2=duplique(c2.getListeConnexions());
		
		//gestion des cas ou l'ID est le même
		fusionMemeID(c, l1, l2);
		
		//gestion des connexions restantes
		fusionListesConnexion(c, l1, l2);
		
		return c;
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
	 * @param c1
	 * @param c2
	 * @return une connexion fusionnée
	 */
	private Connexion fusionConnexion(Connexion c1, Connexion c2) {
		Neurone origine;
		Neurone cible;
		float facteur=(float) alea.aleatDouble(c1.getFacteur(), c2.getFacteur());
		int n=alea.aleatInt(1);
		if (n==0) {
			origine=c1.getOrigine();
		} else {
			origine=c2.getOrigine();
		}
		n=alea.aleatInt(1);
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
			n=alea.aleatInt(1);
			if (n==1) {
				c.addConnexion(con);
			}
			con=liste.getSuivant();
		}
	}
	
	//-------------------------------------------------------------------------------
	//fonctions d'affichage
	
	/**
	 * toStringJson decrivant un enfant de repodution sexuée
	 */
	@Override
	public String toStringJson() {
		return "{\"individu" + this.getId() + "\":{"
		+ "\"type\":\"EnfantSexe\"" + ","
		+ "\"parent1\":" + idParent1 + ","
		+ "\"parent2\":" + idParent2 + ","
		+ super.toStringJson();
	}

	@Override
	public byte[] toByte() {
		ByteBuffer bb=ByteBuffer.allocate(toByteLongueur());
		bb.put((byte) 3);//3 pour cloneMute
		bb.putInt(id);
		bb.putInt(idParent1);
		bb.putInt(idParent2);
		bb.putInt(generation);
		bb.putFloat(score);
		bb.put(cerveau.toByte());
		return bb.array();
	}

	@Override
	public int toByteLongueur() {
		return 21 + cerveau.toByteLongueur();
	}
	
}
