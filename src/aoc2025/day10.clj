(ns aoc2025.day10
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (->> (re-seq #"-?\d+" input)
       (mapv parse-long)))

(defn solve-part1 [input])

(defn solve-part2 [input])
