(ns aoc2024.day03-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2024.day03 :refer [parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example1 "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")
(def example2 "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")

(deftest works
  (testing "with example input"
    (is (= 161 (solve-part1 (parse-input example1))))
    (is (= 48 (solve-part2 (parse-input example2)))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day03.txt")))]
      (is (= 168539636 (solve-part1 input)))
      (is (= 97529391 (solve-part2 input))))))
