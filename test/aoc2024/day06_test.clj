(ns aoc2024.day06-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2024.day06 :refer [parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example-input "....#.....
.........#
..........
..#.......
.......#..
..........
.#..^.....
........#.
#.........
......#...")

(deftest works
  (testing "with example input"
    (is (= 41 (solve-part1 (parse-input example-input))))
    (is (= 6 (solve-part2 (parse-input example-input)))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day06.txt")))]
      (is (= 4819 (solve-part1 input))))))
      ;(is (= 1796 (solve-part2 input)))))) ; slow
