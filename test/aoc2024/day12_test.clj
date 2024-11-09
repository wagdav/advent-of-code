(ns aoc2024.day12-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2024.day12 :refer [parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example-input "")

(deftest works
  (testing "with example input"
    (is (nil? (solve-part1 (parse-input example-input))))
    (is (nil? (solve-part2 (parse-input example-input)))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day12.txt")))]
      (is (nil? (solve-part1 input)))
      (is (nil? (solve-part2 input))))))
