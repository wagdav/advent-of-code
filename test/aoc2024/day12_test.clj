(ns aoc2024.day12-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2024.day12 :refer [parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example-input "RRRRIICCFF
RRRRIICCCF
VVRRRCCFFF
VVRCCCJFFF
VVVVCJJCFE
VVIVCCJJEE
VVIIICJJEE
MIIIIIJJEE
MIIISIJEEE
MMMISSJEEE")

(deftest works
  (testing "with example input"
    (is (= 1930 (solve-part1 (parse-input example-input))))
    (is (= 1206 (solve-part2 (parse-input example-input)))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day12.txt")))]
      (is (= 1477924 (solve-part1 input)))
      (is (= 841934 (solve-part2 input))))))
