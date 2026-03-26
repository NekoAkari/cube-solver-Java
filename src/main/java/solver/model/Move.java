package solver.model;

/**
 * Initial move set: U / R / F with { (clockwise), ' (counter-clockwise), 2 (half-turn) } variants.
 *
 * <p>Notation examples:
 * <ul>
 *   <li>{@code U}  : quarter turn clockwise</li>
 *   <li>{@code U2} : half turn (180°)</li>
 *   <li>{@code U'} : quarter turn counter-clockwise</li>
 * </ul>
 */
public enum Move {
    U(Face.U, 1), U2(Face.U, 2), UP(Face.U, 3),
    R(Face.R, 1), R2(Face.R, 2), RP(Face.R, 3),
    F(Face.F, 1), F2(Face.F, 2), FP(Face.F, 3);

    /** The face being turned (currently U, R, F only). */
    public enum Face { U, R, F }

    private final Face face;
    /** Number of clockwise quarter-turns: 1=90°, 2=180°, 3=270° (i.e., counter-clockwise). */
    private final int turns;

    Move(Face face, int turns) {
        this.face = face;
        this.turns = turns;
    }

    /** @return the face this move turns (U/R/F). */
    public Face face() {
        return face;
    }

    /**
     * @return number of clockwise quarter-turns: 1 = 90°, 2 = 180°, 3 = 270° (i.e., counter-clockwise)
     */
    public int turns() {
        return turns;
    }

    /**
     * Returns the inverse of this move.
     *
     * <p>Examples:
     * <ul>
     *   <li>{@code U}  ↔ {@code U'}</li>
     *   <li>{@code U2} ↔ {@code U2}</li>
     * </ul>
     *
     * @return the inverse move
     */
    public Move inverse() {
        if (turns == 2) return this;
        int invTurns = 4 - turns; // 1->3, 3->1
        return from(face, invTurns);
    }

    /**
     * Factory for creating a {@link Move} from a face and number of clockwise quarter-turns.
     *
     * @param face  the face to turn
     * @param turns number of clockwise quarter-turns (1, 2, or 3)
     * @return the corresponding {@link Move}
     * @throws IllegalArgumentException if {@code turns} is not 1, 2, or 3
     */
    public static Move from(Face face, int turns) {
        if (turns < 1 || turns > 3) {
            throw new IllegalArgumentException("turns must be 1, 2, or 3 (got " + turns + ")");
        }
        return switch (face) {
            case U -> (turns == 1) ? U : (turns == 2) ? U2 : UP;
            case R -> (turns == 1) ? R : (turns == 2) ? R2 : RP;
            case F -> (turns == 1) ? F : (turns == 2) ? F2 : FP;
        };
    }

    /**
     * Parses standard cube notation tokens such as: {@code U}, {@code U2}, {@code U'}, {@code R}, {@code R2}, {@code R'},
     * {@code F}, {@code F2}, {@code F'}.
     *
     * <p>Whitespace is ignored and the face letter is case-insensitive.
     *
     * @param token move token (e.g., "R", "U2", "F'")
     * @return parsed {@link Move}
     * @throws IllegalArgumentException if the token is null/empty or has invalid format
     */
    public static Move parse(String token) {
        if (token == null) {
            throw new IllegalArgumentException("token cannot be null");
        }
        String t = token.trim();
        if (t.isEmpty()) {
            throw new IllegalArgumentException("token cannot be empty");
        }

        // Face letter
        char faceChar = Character.toUpperCase(t.charAt(0));
        Face face = switch (faceChar) {
            case 'U' -> Face.U;
            case 'R' -> Face.R;
            case 'F' -> Face.F;
            default -> throw new IllegalArgumentException("Unknown face: " + faceChar);
        };
        // Optional suffix: 2 or '
        int turns = getTurns(t);
        return from(face, turns);
    }

    /**
     * Parses the optional suffix of a token to determine the number of quarter-turns.
     *
     * @param t trimmed token string
     * @return 1 for no suffix, 2 for '2', 3 for prime (')
     * @throws IllegalArgumentException if the suffix/length is invalid
     */
    private static int getTurns(String t) {
        int turns;
        if (t.length() == 1) {
            turns = 1;
        } else if (t.length() == 2) {
            char suffix = t.charAt(1);
            if (suffix == '2') {
                turns = 2;
            } else if (suffix == '\'' || suffix == '’') {
                turns = 3;
            } else {
                throw new IllegalArgumentException("Invalid move suffix: " + suffix);
            }
        } else {
            throw new IllegalArgumentException("Invalid move token length: " + t);
        }
        return turns;
    }

    /** @return standard cube notation for this move (e.g., "U", "R2", "F'"). */
    @Override
    public String toString() {
        return switch (this) {
            case U -> "U"; case U2 -> "U2"; case UP -> "U'";
            case R -> "R"; case R2 -> "R2"; case RP -> "R'";
            case F -> "F"; case F2 -> "F2"; case FP -> "F'";
        };
    }
}
