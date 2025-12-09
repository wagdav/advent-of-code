(ns aoc2025.day09
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (->> input
       (re-seq #"\d+")
       (map parse-long)
       (partition 2)))

(defn area [[x1 y1] [x2 y2]]
  (* (inc (abs (- x1 x2)))
     (inc (abs (- y1 y2)))))

(defn edge-outside-rectangle? [[[r1 c1] [r2 c2]] [[x1 y1] [x2 y2]]]
  (let [[x1 x2] (sort [x1 x2])
        [y1 y2] (sort [y1 y2])
        [r1 r2] (sort [r1 r2])
        [c1 c2] (sort [c1 c2])]
    (if (= r1 r2)
      ; horizontal edge
      (or
        (or (<= r1 x1)  ; row outside the rectangle
            (<= x2 r2))
        (or (<= c1 c2 y1)
            (<= y2 c1 c2)))
      ; vertical dege
      (or
        (or (<= c1 y1)  ; col outside the rectangle
            (<= y2 c2))
        (or (<= r1 r2 x1)
            (<= x2 r1 r2))))))

(defn solve-part1 [input]
  (apply max
    (for [[i a] (map-indexed vector input)
          [j b] (map-indexed vector input)
          :when (< i j)]
      (area a b))))

(defn solve-part2 [input]
  (let [edges (partitionv 2 1 input input)]
    (apply max
      (for [[i a] (map-indexed vector input)
            [j b] (map-indexed vector input)
            :when (and (< i j)
                       (every? #(edge-outside-rectangle? % [a b]) edges))]
        (area a b)))))
