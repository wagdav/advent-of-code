(ns aoc2025.day01-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2025.day01 :refer [parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example-input "L68
L30
R48
L5
R60
L55
L1
L99
R14
L82")

(deftest works
  (testing "with example input"
    (is (= 3 (solve-part1 (parse-input example-input))))
    (is (= 6 (solve-part2 (parse-input example-input)))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day01.txt")))]
      (is (= 1180 (solve-part1 input)))
      (is (= 6892 (solve-part2 input))))))
