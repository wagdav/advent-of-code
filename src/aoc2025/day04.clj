(ns aoc2025.day04
  (:require [clojure.string :as str]
            [clojure.set :refer [difference]]))

(defn parse-input [input]
  (set (for [[i row] (map-indexed vector (str/split-lines input))
             [j v]   (map-indexed vector row)
             :when (and (= v \@))]
         [i j])))

(def d8 [[-1 -1] [-1  0] [-1  1]
         [ 0 -1]         [ 0  1]
         [ 1 -1] [ 1  0] [ 1  1]])

(defn count-neigbours [pos grid]
  (->> d8
       (keep #(grid (mapv + pos %)))
       (count)))

(defn accessible-rolls [grid]
  (set (filter #(< (count-neigbours % grid) 4) grid)))

(defn solve-part1 [input]
  (count (accessible-rolls input)))

(defn solve-part2 [input]
  (loop [total-removed 0
         grid input]
    (let [accessible (accessible-rolls grid)]
      (if (empty? accessible)
        total-removed
        (recur
          (+ total-removed (count accessible))
          (difference grid accessible))))))
