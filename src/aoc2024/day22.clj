(ns aoc2024.day22
  (:require [clojure.string :as str]
            [clojure.math :refer [round]]))

(defn parse-input [input]
  (->> (re-seq #"-?\d+" input)
       (mapv parse-long)))

(defn mix [n v] (bit-xor n v))
(defn prune [n] (mod n 16777216)) ; 2^24

(defn step [n]
  (let [p1 (prune (mix n  (* 64 n)))
        p2 (prune (mix p1 (quot p1 32)))
        p3 (prune (mix p2 (* p2 2048)))]
    p3))

(defn solve-part1 [input]
  (apply + (for [n input] (nth (iterate step n) 2000))))

(defn prices [start]
  (for [n (iterate step start)]
    (rem n 10)))

(defn changes [start]
  (for [[a b] (partition 2 1 (prices start))]
    (- b a)))

(defn patterns [start]
  (partition 4 1 (changes start)))

(defn signal [start]
  (map vector (patterns start)
              (drop 4 (prices start))))

(defn winning-by-sequence [start]
  (update-vals (group-by first (take 2000 (signal start)))
               (comp second first)))

(defn solve-part2 [input]
  (let [sws (map winning-by-sequence input)
        all-seqs (reduce
                   (fn [res s]
                     (into res (keys s)))
                   #{}
                   sws)]
    (apply max
      (for [s all-seqs]
        (apply + (for [sw sws] (get sw s 0)))))))

(comment
  (time (solve-part1 (parse-input (slurp "resources/day22.txt"))))
  (time (solve-part2 (parse-input (slurp "resources/day22.txt")))))

