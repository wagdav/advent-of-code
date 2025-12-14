(ns aoc2025.day12
  (:require [clojure.string :as str]))

(def example-input "0:
###
##.
##.

1:
###
##.
.##

2:
.##
###
##.

3:
##.
###
##.

4:
###
#..
###

5:
###
.#.
###

4x4: 0 0 0 0 2 0
12x5: 1 0 1 0 2 2
12x5: 1 0 1 0 3 2")

(defn parse-input [input]
  (let [lines (str/split input #"\R\R")
        shapes (->> (butlast lines)
                    (mapv #(->> (str/split-lines %)
                                (drop 1)
                                (mapv vec))))
        regions (->> (last lines)
                     (str/split-lines)
                     (map #(->> (re-seq #"\d+" %)
                                (map parse-long)
                                (split-at 2))))]
    {:shapes shapes
     :regions regions}))

(defn rotate [s]
  (->> s
       (apply map vector)
       (mapv (comp vec reverse))))

(defn show! [s]
  (doseq [l s]
    (prn (apply str l))))

(->> example-input
     parse-input
     :shapes
     first
     (show!))

(->> example-input
     parse-input)

(defn solve-part1 [input])

(defn solve-part2 [input])

(comment
  (->> (clojure.java.io/resource "day12.txt")
       slurp
       parse-input))
