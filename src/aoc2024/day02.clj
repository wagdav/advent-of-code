(ns aoc2024.day02
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (for [line (str/split-lines input)]
    (map parse-long (re-seq #"-?\d+" line))))

(defn safe? [report]
  (and (or (apply < report)
           (apply > report))
       (every?
         (fn [[a b]]
           (<= 1 (abs (- b a)) 3))
         (partition 2 1 report))))

(defn solve-part1 [input]
  (count (filter safe? input)))

(defn dampen [report]
  (for [i (range (count report))]
    (concat (take i report) (drop (inc i) report))))

(defn solve-part2 [input]
  (count (filter #(or (safe? %)
                      (some safe? (dampen %)))
                 input)))
