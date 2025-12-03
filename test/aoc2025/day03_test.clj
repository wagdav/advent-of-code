(ns aoc2025.day03-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2025.day03 :refer [parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example-input "987654321111111
811111111111119
234234234234278
818181911112111")

(deftest works
  (testing "with example input"
    (is (= 357 (solve-part1 (parse-input example-input))))
    (is (= 3121910778619 (solve-part2 (parse-input example-input)))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day03.txt")))]
      (is (= 17408 (solve-part1 input)))
      (is (= 172740584266849 (solve-part2 input))))))
