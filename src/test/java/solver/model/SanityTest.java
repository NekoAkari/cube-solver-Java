package solver.model;

import org.junit.jupiter.api.Test;
import static  org.junit.jupiter.api.Assertions.*;

public class SanityTest {
    @Test
    void sanity() {
        // Project-level sanity: solved cube should report solved
        assertTrue(CubeState.solved().isSolved());
    }
}
