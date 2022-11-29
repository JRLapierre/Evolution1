package core.mutation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import core.mutations.Mutation;

class TestMutation {

	@Test
	@DisplayName("test du constructeur de decodage")
	void testDecodage() {
		String s="{\"graine\":46,\"tauxCreation\":5,\"tauxSuppression\":5,\"tauxMutationFacteur\":5,\"maxChangementFacteur\":2.0,\"tauxMutationNeurone\":5}}";
		Mutation m=new Mutation(s);
		System.out.println(m.toStringJson());
	}

}
