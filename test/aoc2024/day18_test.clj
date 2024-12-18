(ns aoc2024.day18-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2024.day18 :refer [parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example-input "5,4
4,2
4,5
3,0
2,1
6,3
2,4
1,5
0,6
3,3
2,6
5,1
1,2
5,5
2,5
6,5
1,4
0,4
6,4
1,1
6,1
1,0
0,5
1,6
2,0")

(deftest works
  (testing "with example input"
    (is (= 22 (solve-part1 (parse-input example-input) 6 12)))
    (is (= "6,1" (solve-part2 (parse-input example-input) 6))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day18.txt")))]
      (is (= 294 (solve-part1 input)))
      (is (= "31,22" (solve-part2 input))))))
