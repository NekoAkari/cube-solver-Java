# Progress Log

## 2026-03-26
- Implemented `R` quarter-turn in `solver.model.CubeState`
- Implemented `F` quarter-turn in `solver.model.CubeState`
- Improved move property tests so implemented moves are still checked even when later moves remain unimplemented
- Added direct state assertions for `R` and `F` from the solved cube
- Verified with `mvn test`

## 2026-02-06
- Initialized repo and Maven project (`pom.xml`) with Java 17 and JUnit 5
- Added `solver.cli.Main` and basic JUnit sanity test (mvn test: BUILD SUCCESS)
- Added documentation and updated: Big-O complexity notes for initial move set (U/R/F)
- Added model skeleton:
  - `solver.model.Move` enum (U/R/F variants)
  - `solver.model.CubeState` (immutable) with `isSolved()` implemented
  - `applyQuarter()` currently stubbed (throws UnsupportedOperationException)
- Pushed commits to GitHub (`main` up to date)

### Next
- [ ] Add a simple scramble parser/formatter (optional)
- [ ] Start solver search package (`solver.search`) with a baseline IDDFS skeleton
- [ ] Add remaining face turns (D/L/B) or decide to keep the solver scoped to the current U/R/F generator set
