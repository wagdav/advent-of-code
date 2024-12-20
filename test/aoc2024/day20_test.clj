(ns aoc2024.day20-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2024.day20 :refer [cheats parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example-input "###############
#...#...#.....#
#.#.#.#.#.###.#
#S#...#.#.#...#
#######.#.#.###
#######.#.#...#
#######.#.###.#
###..E#...#...#
###.#######.###
#...###...#...#
#.#####.#.###.#
#.#...#.#.#...#
#.#.#.#.#.#.###
#...#...#...###
###############")

(deftest works
  (testing "with example input"
    (is (= 44 (count (cheats 1 2 (parse-input example-input)))))
    (is (= 285 (count (cheats 50 20 (parse-input example-input))))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day20.txt")))]
      (is (= 1384 (solve-part1 input)))
      (is (= 1008542 (solve-part2 input))))))
