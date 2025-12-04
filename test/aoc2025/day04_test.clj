(ns aoc2025.day04-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2025.day04 :refer [parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example-input "..@@.@@@@.
@@@.@.@.@@
@@@@@.@.@@
@.@@@@..@.
@@.@@@@.@@
.@@@@@@@.@
.@.@.@.@@@
@.@@@.@@@@
.@@@@@@@@.
@.@.@@@.@.")

(deftest works
  (testing "with example input"
    (is (= 13 (solve-part1 (parse-input example-input))))
    (is (= 43 (solve-part2 (parse-input example-input)))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day04.txt")))]
      (is (= 1478 (solve-part1 input)))
      (is (= 9120 (solve-part2 input))))))
