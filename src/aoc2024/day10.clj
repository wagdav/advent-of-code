(ns aoc2024.day10
  (:require [clojure.string :as str]))

(def example-input "89010123
78121874
87430965
96549874
45678903
32019012
01329801
10456732")

(defn parse-input [input]
  (vec
    (for [line (str/split-lines input)]
      (mapv parse-long (re-seq #"\d" line)))))

(defn trailheads [m]
  (for [[i row] (map-indexed vector m)
        [j h] (map-indexed vector row)
        :when (= h 0)]
    [i j]))

(def ex (parse-input example-input)) ; ([0 2] [0 4] [2 4] [4 6] [5 2] [5 5] [6 0] [6 6] [7 1])

(defn actions [m p]
  (let [c (get-in m p)]
    (for [d [[-1 0] [1 0] [0 1] [0 -1]]
          :let [n (mapv + p d)]
          :when (= (inc c) (get-in m n))]
      n)))

(actions ex [0 2])

(defn hike [m start]
  (loop [ends 0, q [start], explored #{}]
    (if (seq q)
      (let [])
      :go
      ends)))

(defn solve-part1 [input])

(defn solve-part2 [input])
