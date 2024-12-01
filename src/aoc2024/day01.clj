(ns aoc2024.day01
  (:require [clojure.string :as str]))

(def example "3   4
4   3
2   5
1   3
3   9
3   3")

(defn transpose [v]
  (apply mapv vector v))

(defn parse-input [input]
  (->> (re-seq #"-?\d+" input)
       (mapv parse-long)
       (partition 2)
       (transpose)))

(defn solve-part1 [[left right]]
  (apply + (for [[l r] (map vector (sort left) (sort right))]
             (abs (- l r)))))

(defn solve-part2 [[left right]]
  (let [f (frequencies right)]
    (apply + (for [l left] (* l (get f l 0))))))
