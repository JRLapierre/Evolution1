package core.individus;

public class Sauvegarde extends Individu {

	public Sauvegarde(String sub, int seed) {
		super(sub, seed);
	}
	
	
	@Override
	public String toStringJson() {
		return "{\"individu" + this.getId() + "\":{"
		+ "\"type\":\"sauvegarde\","
		+ super.toStringJson();
	}
	
}
