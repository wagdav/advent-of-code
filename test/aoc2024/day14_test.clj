(ns aoc2024.day14-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2024.day14 :refer [parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example-input ")
p=0,4 v=3,-3
p=6,3 v=-1,-3
p=10,3 v=-1,2
p=2,0 v=2,-1
p=0,0 v=1,3
p=3,0 v=-2,-2
p=7,6 v=-1,-3
p=3,0 v=-1,-2
p=9,3 v=2,3
p=7,3 v=-1,2
p=2,4 v=2,-3
p=9,5 v=-3,-3")

(deftest works
  (testing "with example input"
    (is (= 21 (solve-part1 (parse-input example-input)))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day14.txt")))]
      (is (= 228410028 (solve-part1 input)))
      (is (= 8258 (solve-part2 input))))))
