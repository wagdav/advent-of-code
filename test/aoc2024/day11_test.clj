(ns aoc2024.day11-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2024.day11 :refer [parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example-input "125 17")

(deftest works
  (testing "with example input"
    (is (= 55312 (solve-part1 (parse-input example-input))))
    (is (= 65601038650482 (solve-part2 (parse-input example-input)))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day11.txt")))]
      (is (= 220999 (solve-part1 input)))
      (is (= 261936432123724 (solve-part2 input))))))
