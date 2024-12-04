(ns aoc2024.day04
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (mapv #(apply vector %)
    (for [r (str/split-lines input)]
      (for [c r]
        c))))

(defn word
  ([len]
   (word len 0))
  ([len offset]
   (fn [p [r c] [dr dc]]
     (apply str
       (for [i (range offset (+ offset len))
             :let [l (get-in p [(+ r (* i dr))
                                (+ c (* i dc))])]
             :while l]
         l)))))

(defn words [puzzle word-fn]
  (let [directions [[0 1] [0 -1] [1 0] [-1 0] [1 1] [-1 -1] [-1 1] [1 -1]]]
    (for [r (range (count puzzle))
          c (range (count (first puzzle)))
          d directions]
      (word-fn puzzle [r c] d))))

(defn crosses [puzzle word-fn]
  (let [directions [[1 1] [-1 -1] [1 -1] [-1 1]]]
    (for [r (range (count puzzle))
          c (range (count (first puzzle)))]
      (for [d directions]
        (word-fn puzzle [r c] d)))))

(defn solve-part1 [input]
  (count (filter #(= "XMAS" %) (words input (word 4)))))

(defn solve-part2 [input]
  (->> (crosses input (word 3 -1))
       (map frequencies)
       (filter #(= 2 (get % "MAS")))
       (count)))
