package core.generation.individus.mutation;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import core.generation.individus.mutations.Mutation;

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
		Mutation m1=new Mutation(1,2,3,4,5,6);
		Mutation m2=new Mutation(2,2,3,4,5,6);
		ByteBuffer bb=ByteBuffer.allocate(m1.toByteLongueur());
		bb.put(m1.toByte());
		bb.flip();
		Mutation m3=new Mutation(bb);
		assertEquals(m2.toStringJson(), m3.toStringJson());
		
	}

}
