(ns aoc2025.day05-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2025.day05 :refer [parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example-input "3-5
10-14
16-20
12-18

1
5
8
11
17
32")

(deftest works
  (testing "with example input"
    (is (= 3 (solve-part1 (parse-input example-input))))
    (is (= 14 (solve-part2 (parse-input example-input)))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day05.txt")))]
      (is (= 701 (solve-part1 input)))
      (is (= 352340558684863 (solve-part2 input))))))

