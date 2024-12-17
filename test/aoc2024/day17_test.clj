(ns aoc2024.day17-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2024.day17 :refer [parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example-input "Register A: 729
Register B: 0
Register C: 0

Program: 0,1,5,4,3,0")

(def example-input-quine "Register A: 2024
Register B: 0
Register C: 0

Program: 0,3,5,4,3,0")

(deftest works
  (testing "with example input"
    (is (= "4,6,3,5,6,3,5,2,1,0" (solve-part1 (parse-input example-input))))
    (is (= 117440 (solve-part2 (parse-input example-input-quine)))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day17.txt")))]
      (is (= "4,1,7,6,4,1,0,2,7" (solve-part1 input)))
      (is (nil? (solve-part2 input))))))
