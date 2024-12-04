(ns aoc2024.day04
  (:require [clojure.string :as str]))

(def example-input "MMMSXXMASM
MSAMXMSMSA
AMXSXMAAMM
MSAMASMSMX
XMASAMXAMM
XXAMMXXAMA
SMSMSASXSS
SAXAMASAAA
MAMMMXMMMM
MXMXAXMASX")

(defn parse-input [input]
  (mapv #(apply vector %)
    (for [r (str/split-lines input)]
      (for [c r]
        c))))

(def directions [[0 1] [0 -1] [1 0] [-1 0] [1 1] [-1 -1] [-1 1] [1 -1]])

(defn word [p [r c] [dr dc]]
  (apply str
    (for [i (range 4)
          :let [l (get-in p [(+ r (* i dr))
                             (+ c (* i dc))])]
          :while l]
      l)))

(defn words [puzzle]
  (let [rows (count puzzle)
        cols (count (first puzzle))]
    (for [r (range rows)
          c (range cols)
          d directions]
      (word puzzle [r c] d))))

(defn cross [p [r c] [dr dc]]
  (apply str
    (for [pos [[(- r dr) (- c dc)]
               [   r        c]
               [(+ r dr) (+ c dc)]]
          :let [l (get-in p pos)]
          :while l]
      l)))

(defn crosses [puzzle]
  (let [rows (count puzzle)
        cols (count (first puzzle))]
    (for [r (range rows)
          c (range cols)]
      (for [d [[1 1] [-1 -1] [1 -1] [-1 1]]]
        (cross puzzle [r c] d)))))

(defn solve-part1 [input]
  (count (filter #(= "XMAS" %) (words input))))

(defn solve-part2 [input]
  (->> (crosses input)
       (map frequencies)
       (filter #(= 2 (get % "MAS")))
       (count)))
