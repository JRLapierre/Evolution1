package core.generation;

import java.io.IOException;

import core.individus.Individu;

/**
 * cette classe permet de recuperer une generation depuis une sauvegarde.
 * @author jrl
 *
 */
public class FromSave extends Generation{
	
	/**
	 * constructeur pour recuperer une geneation depuis une sauvegarde.
	 * @param nomSimulation le nom de la simulation
	 * @param generation la generation à laquelle reprendre
	 * @throws IOException si les fichiers recherches n'existent pas
	 */
	public FromSave(String nomSimulation, int generation) throws IOException {
		super(nomSimulation, generation);
	}

	/**
	 * ca ne devais pas exister. de la restructuration seras a refaire.
	 */
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
