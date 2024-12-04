(ns aoc2024.day04
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (mapv vec (str/split-lines input)))

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
  (for [r (range (count puzzle))
        c (range (count (first puzzle)))
        dr [-1 0 1]
        dc [-1 0 1]
        :when (not= dr dc 0)]
    (word-fn puzzle [r c] [dr dc])))

(defn crosses [puzzle word-fn]
  (for [r (range (count puzzle))
        c (range (count (first puzzle)))]
    (for [dr [-1 1]
          dc [-1 1]]
      (word-fn puzzle [r c] [dr dc]))))

(defn solve-part1 [input]
  (count (filter #(= "XMAS" %) (words input (word 4)))))

(defn solve-part2 [input]
  (->> (crosses input (word 3 -1))
       (map frequencies)
       (filter #(= 2 (get % "MAS")))
       (count)))
