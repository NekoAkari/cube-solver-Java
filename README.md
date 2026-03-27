# Cube Solver (Java)

![Java](https://img.shields.io/badge/Java-17-informational)
![Maven](https://img.shields.io/badge/Build-Maven-informational)
![JUnit](https://img.shields.io/badge/Tests-JUnit%205-informational)
![JaCoCo](https://img.shields.io/badge/Coverage-JaCoCo-informational)
![Coverage](https://img.shields.io/badge/Coverage-90%25-brightgreen)

A Rubik’s Cube solver written in Java. This project starts with a **2×2 (Pocket Cube)** solver and is designed to be extended.

## Why this project
I’m rebuilding my earlier cube-solver attempt with a stronger focus on:
- Correctness (move validation + tests)
- Clean architecture (model / search / CLI)
- Reproducibility (seedable scrambles)
- Engineering quality (CI, documentation, benchmarks)

## Current status
- ✓ Maven project (Java 17) with JUnit 5
- ✓ JaCoCo coverage report
- ✓ 2×2 cube model (corner permutation/orientation), **immutable** state
- ✓ Move utilities: ``from(...)``, ``inverse()``, ``parse(...)`` (U/R/F notation)
- ✓ Implemented faces: **U, R, F** quarter-turns
- ✓ Tests:
  - Property tests (identity/inverse) that automatically skip unimplemented moves
  - Boundary/negative tests for invalid inputs and branch coverage

## Planned features
- Full move set: U, D, L, R, F, B (+ inverse and double moves)
- Scramble generator (seedable)
- Solver:
  - Baseline: IDDFS
  - Stretch: A* with heuristics / pattern database
- CLI: scramble / solve / verify
- JUnit tests for move correctness and solver validity
- CI (planned): GitHub Actions (build + test + coverage)

## Project structure (planned)
```text
src/main/java/
  solver/model/   # CubeState, Move
  solver/search/  # IDDFS, (A* later)
  solver/cli/     # Main entry point
src/test/java/
  solver/model/   # MovePropertiesTest, BoundaryTest, MoveTest, etc.
docs/
```

## Getting started
### Requirements
- Java 17+
- Maven 3+

### Build & run tests
```bash
mvn test
```

### Coverage report (JaCoCo)
```bash
mvn test
open target/site/jacoco/index.html
```

## Usage (coming soon)
For now the CLI prints a simple initialization message.
The next milestone is to accept a scramble string (e.g., "R U R' U'") and run the solver.

## Roadmap
- [x] Repository initialized + GitHub sync
- [x] Maven project skeleton
- [x] Add JUnit tests + JaCoCo coverage
- [x] Implement U move (2×2)
- [x] Implement R move (2×2)
- [x] Implement F move (2×2)
- [ ] Scramble parser/formatter + seedable scrambler
- [ ] IDDFS solver (baseline)
- [ ] CLI: solve from scramble input
- [ ] Stretch: A* + heuristics / pattern database
- [ ] CI: GitHub Actions

## License
TBD
