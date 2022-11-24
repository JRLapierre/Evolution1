package core.generation;

import core.cerveau.Cerveau;
import core.individus.Individu;
import core.individus.Original;
import core.mutations.Mutation;

/**
 * Cette classe represente le premier type de generation.
 * Ce type de generation a les carracteristiques suivantes : 
 * Individu originel : 
 * Cerveau : 
 * nombre de neurones d'entrées : 1,
 * nombre de neurones de sortie : 1,
 * nombre de neurones internes : 5;
 * graine d'aleatoire des individus : 46;
 * Mutation : 
 * graine d'aleatoire : 46,
 * taux de creation d'une connexion : 5,
 * taux de suppression d'une connexion : 5,
 * taux de mutation du facteur : 5,
 * maximum de changement du facteur : 2,
 * taux de changement d'une extremité du connexion : 5;
 * nombre de clones parfaits : 25;
 * nombre de clones mutés : 50;
 * nombre d'enfants de reproduction sexuee : 25;
 * nombre de tics d'evaluation : 10;
 * nom de la simulation : 1;
 * 
 * 
 * @author jrl
 *
 */
public class Type1 extends Generation{

	/**
	 * constructeur pour initialiser la premiere generation de ce type.
	 * initialise tous les parametres cités dans la description de la classe.
	 */
	public Type1() {
		super(new Original(new Cerveau(1,1,5), 46), 
				new Mutation(46, 5, 5, 5, 2, 5), 
				25, 50, 25, 10, "1");
	}
	
	/**
	 * Constructeur pour generer une nouvelle generation à partir de la precedente.
	 * @param precedent la generation precedente
	 */
	public Type1(Generation precedent) {
		super(precedent);
	}

	@Override
	protected void epreuve(Individu individu) {
		for(int i=0; i<super.nbTics; i++) {
			individu.getCerveau().getListeInput()[0].setPuissance(1);
			individu.getCerveau().next();
			//je vais selectionner ceux qui ont un output s'approchant le plus de 5
			float score=individu.getCerveau().getListeOutput()[0].getPuissance();
			individu.updateScore(5+score);
		}
		
	}

}
