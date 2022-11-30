package core.generation;

import java.io.IOException;

import core.individus.Individu;

public class FromSave extends Generation{
	
	public FromSave(String nomSimulation, int generation) throws IOException {
		super(nomSimulation, generation);
	}

	@Override
	//des changements structurels devront être faits
	//j'ai repris la même epreuve que dans les autres fichiers Type
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
