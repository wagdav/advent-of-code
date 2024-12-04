(ns aoc2024.day04-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2024.day04 :refer [parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example-input "MMMSXXMASM
MSAMXMSMSA
AMXSXMAAMM
MSAMASMSMX
XMASAMXAMM
XXAMMXXAMA
SMSMSASXSS
SAXAMASAAA
MAMMMXMMMM
MXMXAXMASX")

(deftest works
  (testing "with example input"
    (is (= 18 (solve-part1 (parse-input example-input))))
    (is (= 9 (solve-part2 (parse-input example-input)))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day04.txt")))]
      (is (= 2462 (solve-part1 input)))
      (is (= 1877 (solve-part2 input))))))
