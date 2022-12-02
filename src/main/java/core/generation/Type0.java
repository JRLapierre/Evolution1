package core.generation;

import core.generation.individus.Individu;

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
	public Type0(Individu originel, int nbClonesParfaits, int nbClonesMutes, int nbEnfantsSexe,
			int nbTics, String nomSimulation) {
		super(originel, nbClonesParfaits, nbClonesMutes, nbEnfantsSexe, nbTics, nomSimulation);
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
