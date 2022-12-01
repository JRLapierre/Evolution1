package core.generation;

import core.generation.individus.Individu;
import core.generation.individus.mutations.Mutation;

/**
 * cette classe sert pour les tests.
 * J'ai gardé tous les parametres accessibles pour avoir de la souplesse.
 * 
 * @author jrl
 *
 */
public class Type0 extends Generation{

	/**
	 * constructeur pour la premiere generation
	 * @param originel
	 * @param mutation
	 * @param nbClonesParfaits
	 * @param nbClonesMutes
	 * @param nbEnfantsSexe
	 * @param nbTics
	 * @param nomSimulation
	 */
	public Type0(Individu originel, Mutation mutation, int nbClonesParfaits, int nbClonesMutes, int nbEnfantsSexe,
			int nbTics, String nomSimulation) {
		super(originel, mutation, nbClonesParfaits, nbClonesMutes, nbEnfantsSexe, nbTics, nomSimulation);
	}
	
	/**
	 * constructeur pour la generation suivante
	 * @param g
	 */
	public Type0(Generation g) {
		super(g);
	}

	@Override
	protected void epreuve(Individu individu) {//mmeme chose que pour Type1
		for(int i=0; i<super.nbTics; i++) {
			individu.getCerveau().getListeInput()[0].setPuissance(1);
			individu.getCerveau().next();
			//je vais selectionner ceux qui ont un output s'approchant le plus de 5
			float score=individu.getCerveau().getListeOutput()[0].getPuissance();
			individu.updateScore(5+score);
		}
		
	}
	
}
