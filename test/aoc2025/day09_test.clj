(ns aoc2025.day09-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2025.day09 :refer [parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example-input "7,1
11,1
11,7
9,7
9,5
2,5
2,3
7,3")

(deftest works
  (testing "with example input"
    (is (= 50 (solve-part1 (parse-input example-input))))
    (is (= 24 (solve-part2 (parse-input example-input)))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day09.txt")))]
      (is (= 4755429952 (solve-part1 input)))
      (is (= 1429596008 (solve-part2 input))))))
