(ns aoc2025.day11-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2025.day11 :refer [parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example-input "aaa: you hhh
you: bbb ccc
bbb: ddd eee
ccc: ddd eee fff
ddd: ggg
eee: out
fff: out
ggg: out
hhh: ccc fff iii
iii: out")

(def example-input2 "svr: aaa bbb
aaa: fft
fft: ccc
bbb: tty
tty: ccc
ccc: ddd eee
ddd: hub
hub: fff
eee: dac
dac: fff
fff: ggg hhh
ggg: out
hhh: out")

(deftest works
  (testing "with example input"
    (is (= 5 (solve-part1 (parse-input example-input))))
    (is (= 2 (solve-part2 (parse-input example-input2)))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day11.txt")))]
      (time (is (= 566 (solve-part1 input))))
      (time (is (= 331837854931968 (solve-part2 input)))))))
