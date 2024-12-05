(ns aoc2024.day05-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2024.day05 :refer [parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example-input "47|53
97|13
97|61
97|47
75|29
61|13
75|53
29|13
97|29
53|29
61|53
97|53
61|29
47|13
75|47
97|75
47|61
75|61
47|29
75|13
53|13

75,47,61,53,29
97,61,53,29,13
75,29,13
75,97,47,61,53
61,13,29
97,13,75,29,47
")

(deftest works
  (testing "with example input"
    (is (= 143 (solve-part1 (parse-input example-input))))
    (is (= 123 (solve-part2 (parse-input example-input)))))

  (testing "with real input"
    (let [input (parse-input (slurp "resources/day05.txt"))]
      (is (= 5991 (solve-part1 input)))
      (is (= 5479 (solve-part2 input))))))
