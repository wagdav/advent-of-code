(ns aoc2024.day01-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2024.day01 :refer [parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example-input "3   4
4   3
2   5
1   3
3   9
3   3")

(deftest works
  (testing "with example input"
    (is (= 11 (solve-part1 (parse-input example-input))))
    (is (= 31 (solve-part2 (parse-input example-input)))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day01.txt")))]
      (is (= 2164381 (solve-part1 input)))
      (is (= 20719933 (solve-part2 input))))))
