(ns aoc2024.day19-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2024.day19 :refer [parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example-input "r, wr, b, g, bwu, rb, gb, br

brwrr
bggr
gbbr
rrbgbr
ubwu
bwurrg
brgr
bbrgwb")

(deftest works
  (testing "with example input"
    (is (= 6 (solve-part1 (parse-input example-input))))
    (is (= 16 (solve-part2 (parse-input example-input)))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day19.txt")))]
      (is (= 315 (solve-part1 input)))
      (is (= 625108891232249 (solve-part2 input))))))
