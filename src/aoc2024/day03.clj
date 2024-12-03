(ns aoc2024.day03
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (flatten
    (interpose :do
      (for [m (str/split input #"do\(\)")]
        (interpose :dont (str/split m #"don't\(\)"))))))

(defn muls [p]
  (for [[_ a b] (re-seq #"mul\((\d+),(\d+)\)" p)]
    (* (parse-long a) (parse-long b))))

(defn solve-part1 [input]
  (apply + (mapcat muls (filter string? input))))

(defn solve-part2 [input]
  (apply +
    (second
      (reduce
        (fn [[enabled res] p]
          (case p
            :do   [true res]
            :dont [false res]
            [enabled (if enabled
                       (into res (muls p))
                       res)]))
        [true []]
        input))))
