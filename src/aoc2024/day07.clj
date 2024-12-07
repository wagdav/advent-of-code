(ns aoc2024.day07
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (for [line (str/split-lines input)]
    (let [xs (->> (re-seq #"-?\d+" line)
                  (map parse-long))]
      [(first xs) (rest xs)])))

(defn eval-with [xs ops]
  (reduce
    (fn [result x]
      (for [r result
            op ops]
        (op r x)))
    (take 1 xs)
    (drop 1 xs)))

(defn conkat [a b] (parse-long (str a b)))

(defn calibrate [ops [res xs]]
  (if (some #(= res %) (eval-with xs ops)) res 0))

(defn solve-part1 [input]
  (->> input
       (map (partial calibrate [+ *]))
       (apply +)))

(defn solve-part2 [input]
  (->> input
       (map (partial calibrate [+ * conkat]))
       (apply +)))
