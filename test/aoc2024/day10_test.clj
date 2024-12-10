(ns aoc2024.day10-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2024.day10 :refer [parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example-input "89010123
78121874
87430965
96549874
45678903
32019012
01329801
10456732")

(deftest works
  (testing "with example input"
    (is (= 36 (solve-part1 (parse-input example-input))))
    (is (= 81 (solve-part2 (parse-input example-input)))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day10.txt")))]
      (is (= 574 (solve-part1 input)))
      (is (= 1238 (solve-part2 input))))))
