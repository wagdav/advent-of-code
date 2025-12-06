(ns aoc2025.day06-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2025.day06 :refer [parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example-input "123 328  51 64
 45 64  387 23
  6 98  215 314
*   +   *   +
")

(deftest works
  (testing "with example input"
    (is (= 4277556 (solve-part1 (parse-input example-input))))
    (is (= nil (solve-part2 (parse-input example-input)))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day06.txt")))]
      (is (= 4412382293768 (solve-part1 input)))
      (is (= 7858808482092 (solve-part2 input))))))
