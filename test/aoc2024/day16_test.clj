(ns aoc2024.day16-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2024.day16 :refer [parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example-input "###############
#.......#....E#
#.#.###.#.###.#
#.....#.#...#.#
#.###.#####.#.#
#.#.#.......#.#
#.#.#####.###.#
#...........#.#
###.#.#####.#.#
#...#.....#.#.#
#.#.#.###.#.#.#
#.....#...#.#.#
#.###.#.#.#.#.#
#S..#.....#...#
###############")

(deftest works
  (testing "with example input"
    (is (= 7036 (solve-part1 (parse-input example-input))))
    (is (= 45 (solve-part2 (parse-input example-input)))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day16.txt")))]
      (is (= 111480 (solve-part1 input)))
      (is (= 529 (solve-part2 input)))))) ; SLOW
