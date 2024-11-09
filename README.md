My solutions to the  [Advent of Code](https://adventofcode.com) programming puzzles in Clojure.

It contains my solutions for year 2024. For other years see:

* [2020](https://github.com/wagdav/advent-of-code-2020)
* [2021](https://github.com/wagdav/advent-of-code-2021)
* [2022](https://github.com/wagdav/advent-of-code-2022)
* [2023](https://github.com/wagdav/advent-of-code-2023)

Eventually, I'll collect all solutions here.

# Build and run

Install the Nix package manager then

```
nix build
```

# Develop

Run all tests:

```
clojure -M:test
```

Run tests and watch for changes:

```
clojure -M:test --watch
```

Filter tests for a specific namespace:

```
clojure -M:test --watch --focus aoc2024.day01-test
```

Run linting:

```
nix develop --command clj-kondo --lint .
clojure -T:build lint
```
