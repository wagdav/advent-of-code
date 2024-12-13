(ns aoc2024.day13-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2024.day13 :refer [parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example-input "Button A: X+94, Y+34
Button B: X+22, Y+67
Prize: X=8400, Y=5400

Button A: X+26, Y+66
Button B: X+67, Y+21
Prize: X=12748, Y=12176

Button A: X+17, Y+86
Button B: X+84, Y+37
Prize: X=7870, Y=6450

Button A: X+69, Y+23
Button B: X+27, Y+71
Prize: X=18641, Y=10279")

(deftest works
  (testing "with example input"
    (is (= 480 (solve-part1 (parse-input example-input))))
    (is (= 875318608908 (solve-part2 (parse-input example-input)))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day13.txt")))]
      (is (= 29201 (solve-part1 input)))
      (is (= 104140871044942 (solve-part2 input))))))
