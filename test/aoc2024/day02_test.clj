(ns aoc2024.day02-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2024.day02 :refer [parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example-input "7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9
")

(deftest works
  (testing "with example input"
    (is (= 2 (solve-part1 (parse-input example-input))))
    (is (= 4 (solve-part2 (parse-input example-input)))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day02.txt")))]
      (is (= 526 (solve-part1 input)))
      (is (= 566 (solve-part2 input))))))
