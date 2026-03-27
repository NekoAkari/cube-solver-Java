package solver.model;

import java.util.Arrays;

/**
 * Immutable 2×2 cube state using corner permutation (cp) and corner orientation (co).
 *
 * <p>Corner indices (standard cubie model):
 * 0 URF, 1 UFL, 2 ULB, 3 UBR, 4 DFR, 5 DLF, 6 DBL, 7 DRB
 *
 * <p>Invariants:
 * <ul>
 *   <li>cp is a permutation of {0..7}</li>
 *   <li>co values are in {0,1,2} and sum(co) % 3 == 0 for valid states</li>
 * </ul>
 *
 * <p>This class is <b>immutable</b>: {@link #apply(Move)} returns a new state and never mutates the original.
 */
public final class CubeState {

    // cp[pos] = which corner cubie (0..7) is currently located at position pos
    private final byte[] cp; // length 8

    // co[pos] = orientation (0..2) of the corner cubie currently located at position pos
    private final byte[] co; // length 8

    private CubeState(byte[] cp, byte[] co) {
        this.cp = cp;
        this.co = co;
    }

    /**
     * @return the solved 2×2 cube state (identity permutation, all orientations = 0)
     */
    public static CubeState solved() {
        byte[] cp = new byte[8];
        byte[] co = new byte[8];
        for (byte i = 0; i < 8; i++) {
            cp[i] = i;
            co[i] = 0;
        }
        return new CubeState(cp, co);
    }

    /**
     * Applies a move and returns the resulting state.
     *
     * <p>A move may be a quarter-turn, half-turn, or prime turn depending on {@link Move#turns()}.
     *
     * @param move move to apply
     * @return a new {@link CubeState} after applying {@code move}
     */
    public CubeState apply(Move move) {
        CubeState s = this;
        for (int i = 0; i < move.turns(); i++) {
            s = s.applyQuarter(move.face());
        }
        return s;
    }

    /**
     * @return true if this state is the solved state (identity permutation and all orientations = 0)
     */
    public boolean isSolved() {
        for (byte i = 0; i < 8; i++) {
            if (cp[i] != i) return false;
            if (co[i] != 0) return false;
        }
        return true;
    }

    /**
     * Applies one <b>clockwise</b> quarter-turn of the given face.
     *
     * @param face face to turn
     * @return the state after one clockwise quarter-turn
     * @throws UnsupportedOperationException if the face is not implemented yet
     */
    private CubeState applyQuarter(Move.Face face) {
        return switch (face) {
            case U -> applyUQuarter();
            case R -> applyRQuarter();
            case F -> throw new UnsupportedOperationException(
                    "applyQuarter(" + face + ") not implemented yet");
        };
    }

    /**
     * U clockwise: cycles the top-layer corners (0,1,2,3) and leaves orientations unchanged.
     */
    private CubeState applyUQuarter() {
        // new[pos] = old[permU[pos]]
        final int[] permU = {1, 2, 3, 0, 4, 5, 6, 7};

        byte[] newCp = new byte[8];
        byte[] newCo = new byte[8];

        for (int pos = 0; pos < 8; pos++) {
            int src = permU[pos];
            newCp[pos] = cp[src];
            newCo[pos] = co[src];
        }

        return new CubeState(newCp, newCo);
    }

    /**
     * R clockwise: cycles the right-layer corners (0,3,7,4) and twists the moved corners.
     */
    private CubeState applyRQuarter() {
        // new[pos] = old[permR[pos]]
        final int[] permR = {3, 1, 2, 7, 0, 5, 6, 4};
        // Corner orientation deltas in standard cubie coordinates.
        final int[] deltaR = {2, 0, 0, 1, 1, 0, 0, 2};

        byte[] newCp = new byte[8];
        byte[] newCo = new byte[8];

        for (int pos = 0; pos < 8; pos++) {
            int src = permR[pos];
            newCp[pos] = cp[src];
            newCo[pos] = (byte) ((co[src] + deltaR[pos]) % 3);
        }

        return new CubeState(newCp, newCo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CubeState other)) return false;
        return Arrays.equals(cp, other.cp) && Arrays.equals(co, other.co);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(cp);
        result = 31 * result + Arrays.hashCode(co);
        return result;
    }
}
