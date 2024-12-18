(ns aoc2024.day18
  (:require [clojure.string :as str]
            [aoc2024.search :as search]))

(defn parse-input [input]
  (->> (re-seq #"-?\d+" input)
       (mapv parse-long)
       (partitionv 2)))

(defn steps [input size fallen]
  (let [corrupted (into #{} (take fallen input))]
    (:path-cost
      (search/uniform-cost
        (reify search/Problem
          (actions [_ state]
            (for [d [[-1 0] [1 0] [0 -1] [0 1]]
                  :let [[x y] (mapv + state d)]
                  :when (and (<= 0 x size)
                             (<= 0 y size)
                             (not (corrupted [x y])))]
              [x y]))
          (goal? [_ state]
            (= state [size size]))
          (initial-state [_]
            [0 0])
          (result [_ state action]
            action)
          (step-cost [_ state action]
            1))))))

(defn blocker-byte [input size]
  (loop [i (count input)]
    (if (steps input size i)
      (nth input i)
      (recur (dec i)))))

(defn solve-part1
  ([input]
   (solve-part1 input 70 1024))
  ([input size fallen]
   (steps input size fallen)))

(defn solve-part2
  ([input]
   (solve-part2 input 70))
  ([input size]
   (str/join "," (blocker-byte input size))))
