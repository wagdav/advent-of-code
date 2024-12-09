(ns aoc2024.day09-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2024.day09 :refer [parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example-input "2333133121414131402")

(deftest works
  (testing "with example input"
    (is (= 1928 (solve-part1 (parse-input example-input))))
    (is (= 2858 (solve-part2 (parse-input example-input)))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day09.txt")))]
      (is (= 6421128769094 (solve-part1 input)))
      (is (= 6448168620520 (solve-part2 input))))))
