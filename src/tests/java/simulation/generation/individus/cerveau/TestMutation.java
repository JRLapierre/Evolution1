package simulation.generation.individus.cerveau;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TestMutation {

	@Test
	@DisplayName("test du constructeur de decodage")
	void testDecodage() {
		String s="{\"graine\":46,\"tauxCreation\":5,\"tauxSuppression\":5,\"tauxMutationFacteur\":5,\"maxChangementFacteur\":2.0,\"tauxMutationNeurone\":5}}";
		Mutation m=new Mutation(s);
		System.out.println(m.toStringJson());
	}
	
	@Test
	@DisplayName("test de l'encodage et du decodage binaire")
	void testToByte() {
		Mutation m1=new Mutation(1)
				.setTauxCreationSuppression(2, 3)
				.setMutationFacteur(4, 5)
				.setTauxMutationNeurone(6);
		ByteBuffer bb=ByteBuffer.allocate(m1.toByteLongueur());
		bb.put(m1.toByte());
		bb.flip();
		Mutation m3=new Mutation(bb);
		m3.setGraine(1);
		assertEquals(m1.toStringJson(), m3.toStringJson());
		
	}

	

}
