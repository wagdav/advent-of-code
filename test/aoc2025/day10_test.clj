(ns aoc2025.day10-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2025.day10 :refer [parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example-input "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}")

(deftest works
  (testing "with example input"
    (is (= 7 (solve-part1 (parse-input example-input))))
    (is (= 33 (solve-part2 (parse-input example-input)))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day10.txt")))]
      (time (is (= 512 (solve-part1 input))))
      (time (is (nil? (solve-part2 input)))))))
