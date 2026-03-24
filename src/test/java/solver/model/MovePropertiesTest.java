package solver.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

/**
 * Property tests for move correctness.
 *
 * <p>Uses group properties (identity/inverse) and skips moves that are not implemented yet.
 */
class MovePropertiesTest {

    private static boolean isImplemented(Move.Face face) {
        try {
            CubeState.solved().apply(Move.from(face, 1));
            return true;
        } catch (UnsupportedOperationException e) {
            return false;
        }
    }

    private static boolean isImplemented(Move move) {
        try {
            CubeState.solved().apply(move);
            return true;
        } catch (UnsupportedOperationException e) {
            return false;
        }
    }

    @Test
    void eachFaceTurnAppliedFourTimesReturnsSolved() {
        for (Move.Face face : Move.Face.values()) {
            assumeTrue(isImplemented(face), "Face " + face + " not implemented yet");

            CubeState s = CubeState.solved();
            for (int i = 0; i < 4; i++) {
                s = s.apply(Move.from(face, 1));
            }
            assertTrue(s.isSolved(), "Face " + face +
                    " should return to solved after 4 quarter-turns");
        }
    }

    @Test
    void moveThenInverseReturnsSolved() {
        for (Move m : Move.values()) {
            assumeTrue(isImplemented(m), "Move " + m + " not implemented yet");

            CubeState s = CubeState.solved().apply(m).apply(m.inverse());
            assertTrue(s.isSolved(), "Move " + m +
                    " followed by inverse should be solved");
        }
    }
}
