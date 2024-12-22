(ns aoc2024.day22-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2024.day22 :refer [parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example-input "1
10
100
2024")

(def example-input2 "1
2
3
2024")

(deftest works
  (testing "with example input"
    (is (= 37327623 (solve-part1 (parse-input example-input))))
    (is (= 23 (solve-part2 (parse-input example-input2)))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day22.txt")))]
      (is (= 14082561342 (solve-part1 input))))))
      ;(is (= 1568 (solve-part2 input)))))) ; SLOW
