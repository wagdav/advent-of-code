(ns aoc2025.day03
  (:require [clojure.string :as str]))

(defn parse-line [line]
  (map #(parse-long (str %)) line))

(defn parse-input [input]
  (->> input
       (str/split-lines)
       (map parse-line)))

(defn max-joltage [n js]
  (loop [n n
         js js
         res []]
    (if (zero? n)
      (parse-long (apply str res))
      (let [[pos digit] (->> (drop-last (dec n) js)
                             (map-indexed vector)
                             reverse
                             (apply max-key second))]
        (recur (dec n)
               (drop (inc pos) js)
               (conj res digit))))))

(defn solve-part1 [input]
  (reduce + (map (partial max-joltage 2) input)))

(defn solve-part2 [input]
  (reduce + (map (partial max-joltage 12) input)))
