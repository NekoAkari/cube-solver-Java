package solver.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class CubeStateEqualityTest {

    @Test
    void equalsHandlesReferenceAndNullAndOtherType() {
        CubeState s = CubeState.solved();

        assertEquals(s, s); // same reference
        assertNotEquals(s, null); // null
        assertNotEquals(s, "nope"); // other type
    }

    @Test
    void equalStatesHaveSameHashCode() {
        CubeState a = CubeState.solved();
        CubeState b = CubeState.solved();

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void differentPermutationNotEqual() {
        CubeState solved = CubeState.solved();
        CubeState permChanged = CubeState.solved().apply(Move.U); // changes cp, keeps co at 0

        // Forces equals() to compute Arrays.equals(cp, ...) == false (short-circuit on &&).
        assertNotEquals(solved, permChanged);
    }

    @Test
    void worksInHashSet() {
        CubeState a = CubeState.solved();
        CubeState b = CubeState.solved(); // equal to a

        HashSet<CubeState> set = new HashSet<>();
        set.add(a);

        assertTrue(set.contains(b)); // requires equals+hashCode consistency
    }
}
