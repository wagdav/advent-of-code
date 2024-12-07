(ns aoc2024.day07-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2024.day07 :refer [parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example-input "190: 10 19
3267: 81 40 27
83: 17 5
156: 15 6
7290: 6 8 6 15
161011: 16 10 13
192: 17 8 14
21037: 9 7 18 13
292: 11 6 16 20
")

(deftest works
  (testing "with example input"
    (is (= 3749 (solve-part1 (parse-input example-input))))
    (is (= 11387 (solve-part2 (parse-input example-input)))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day07.txt")))]
      (is (= 14711933466277 (solve-part1 input)))
      (is (= 286580387663654 (solve-part2 input))))))
