
# Assignment 2: Shell Sort Implementation (Student A)

This repository contains the implementation of the Shell Sort algorithm, featuring various gap sequences (Shell's, Knuth's, and Sedgewick's), as part of Assignment 2.

## Features
- Implementation of Shell Sort.
- Support for Shell's, Knuth's, and Sedgewick's gap sequences.
- **Optimizations:** 
    - All sequences are implemented to achieve O(1) auxiliary space complexity.
    - Sedgewick's sequence uses a pre-computed static array to eliminate runtime calculation overhead.
- Performance tracking (comparisons, moves, array accesses, time).
- Comprehensive JUnit 5 test suite.
- Flexible CLI interface for benchmarking.

## Project Structure
```
├── src/main/java/
│   ├── algorithms/ShellSort.java
│   ├── metrics/PerformanceTracker.java
│   └── cli/BenchmarkRunner.java
├── src/test/java/
│   └── algorithms/ShellSortTest.java
├── docs/
│   └── analysis-report-heapsort.md (Peer review of Heap Sort)
├── README.md
└── pom.xml
```

## Building and Running

### Prerequisites
- Java JDK 11+
- Maven

### Compiling
```bash
mvn compile
```

### Running Tests
```bash
mvn test
```

### Running the Benchmark CLI
To run the benchmark CLI (after compiling), use the `BenchmarkRunner`.

Example Usage (assuming running from project root via `java -cp target/classes`):
```bash
# ShellSort, 10000 elements, SEDGEWICK sequence (default), RANDOM input
java -cp target/classes cli.BenchmarkRunner ShellSort 10000 

# ShellSort, 5000 elements, KNUTH sequence, REVERSE input
java -cp target/classes cli.BenchmarkRunner ShellSort 5000 KNUTH REVERSE

# ShellSort, 1000 elements, SHELL sequence, SORTED input
java -cp target/classes cli.BenchmarkRunner ShellSort 1000 SHELL SORTED
```

## Complexity Overview
- **Worst Case:** Varies from O(N^2) (Shell) to O(N^(4/3)) (Sedgewick).
- **Space Complexity:** O(1) auxiliary (optimized implementation).
