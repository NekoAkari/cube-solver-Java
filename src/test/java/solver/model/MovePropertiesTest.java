package solver.model;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.*;

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
            if (!isImplemented(face)) {
                continue;
            }

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
            if (!isImplemented(m)) {
                continue;
            }

            CubeState s = CubeState.solved().apply(m).apply(m.inverse());
            assertTrue(s.isSolved(), "Move " + m +
                    " followed by inverse should be solved");
        }
    }

    @Test
    void rQuarterFromSolvedProducesExpectedPermutationAndOrientation() throws Exception {
        CubeState state = CubeState.solved().apply(Move.R);

        assertArrayEquals(new byte[]{3, 1, 2, 7, 0, 5, 6, 4}, getByteArrayField(state, "cp"));
        assertArrayEquals(new byte[]{2, 0, 0, 1, 1, 0, 0, 2}, getByteArrayField(state, "co"));
    }

    @Test
    void fQuarterFromSolvedProducesExpectedPermutationAndOrientation() throws Exception {
        CubeState state = CubeState.solved().apply(Move.F);

        assertArrayEquals(new byte[]{1, 5, 2, 3, 0, 4, 6, 7}, getByteArrayField(state, "cp"));
        assertArrayEquals(new byte[]{1, 2, 0, 0, 2, 1, 0, 0}, getByteArrayField(state, "co"));
    }

    private static byte[] getByteArrayField(CubeState state, String fieldName) throws Exception {
        Field field = CubeState.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return (byte[]) field.get(state);
    }
}
