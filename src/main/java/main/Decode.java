package main;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import simulation.generation.individus.Individu;

/**
 * permet de decoder le fichier binaire d'un individu.
 * affiche dans le terminal sa representation json.
 * @author jrl
 *
 */
public class Decode {
	
	private static final String nomSimulation="2P4";
	
	private static final int numeroGeneration=51012;
	
	private static final int idIndividu=100160;
	

	public static void main(String[] args) throws IOException {
		String path="enregistrements/simulation" + nomSimulation
				+ "/generation" + numeroGeneration + 
				"/individu" + idIndividu + ".bin";
		//fichier de la generation
		byte[] sim = Files.readAllBytes(Paths.get(path));
		ByteBuffer b = ByteBuffer.allocate(sim.length);
		b.put(sim);
		b.flip();
		//recreer l'individu
		Individu i=Individu.regenereIndividu(b);
		System.out.println(i.toStringJson());
	}
	
}
