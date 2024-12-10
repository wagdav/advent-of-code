(ns aoc2024.day10
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (vec
    (for [line (str/split-lines input)]
      (mapv parse-long (re-seq #"\d" line)))))

(defn trailheads [m]
  (for [[i row] (map-indexed vector m)
        [j h] (map-indexed vector row)
        :when (= h 0)]
    [i j]))

(defn actions [m p]
  (let [c (get-in m p)]
    (for [d [[-1 0] [1 0] [0 1] [0 -1]]
          :let [n (mapv + p d)]
          :when (= (inc c) (get-in m n))]
      n)))

(defn score1 [m start]
  (loop [ends 0, q (list start), explored #{}]
    (if (seq q)
      (let [cur (peek q)
            q' (into (pop q)
                     (remove explored (actions m cur)))]
        (recur
          (if (= 9 (get-in m cur)) (inc ends) ends)
          q' (conj explored cur)))
      ends)))

(defn score2 [m start]
  (loop [paths #{}, q (list [start]), explored #{}]
    (if (seq q)
      (let [cur (peek q)
            q' (into (pop q)
                     (->> (actions m (last cur))
                          (remove explored)
                          (map #(conj cur %))))]
        (recur
          (if (= 9 (get-in m (last cur))) (conj paths cur) paths)
          q'
          (conj explored cur)))
     (count paths))))

(defn solve-part1 [input]
  (apply +
    (for [start (trailheads input)]
      (score1 input start))))

(defn solve-part2 [input]
  (apply +
    (for [start (trailheads input)]
      (score2 input start))))
