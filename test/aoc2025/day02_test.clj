(ns aoc2025.day02-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2025.day02 :refer [parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example-input "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124")

(deftest works
  (testing "with example input"
    (is (= 1227775554 (solve-part1 (parse-input example-input))))
    (is (= 4174379265 (solve-part2 (parse-input example-input)))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day02.txt")))]
      (is (= 28844599675 (solve-part1 input)))
      (is (= 48778605167 (solve-part2 input))))))
