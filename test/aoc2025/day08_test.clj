(ns aoc2025.day08-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2025.day08 :refer [parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example-input "162,817,812
57,618,57
906,360,560
592,479,940
352,342,300
466,668,158
542,29,236
431,825,988
739,650,466
52,470,668
216,146,977
819,987,18
117,168,530
805,96,715
346,949,466
970,615,88
941,993,340
862,61,35
984,92,344
425,690,689")

(deftest works
  (testing "with example input"
    (is (= 40 (solve-part1 10 (parse-input example-input))))
    (is (= 32103 (solve-part2 (parse-input example-input)))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day08.txt")))]
      (is (= 32103 (solve-part1 1000 input)))
      (is (= 8133642976 (solve-part2 input))))))

