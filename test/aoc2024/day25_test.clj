(ns aoc2024.day25-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2024.day25 :refer [parse-input solve-part1]]
            [clojure.java.io :as io]))

(def example-input "#####
.####
.####
.####
.#.#.
.#...
.....

#####
##.##
.#.##
...##
...#.
...#.
.....

.....
#....
#....
#...#
#.#.#
#.###
#####

.....
.....
#.#..
###..
###.#
###.#
#####

.....
.....
.....
#....
#.#..
#.#.#
#####")


(deftest works
  (testing "with example input"
    (is (= 3 (solve-part1 (parse-input example-input)))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day25.txt")))]
      (is (= 3508 (solve-part1 input))))))
