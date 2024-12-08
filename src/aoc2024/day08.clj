(ns aoc2024.day08
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (let [rows (str/split-lines input)]
    {:size (count rows)
     :antennas (apply merge-with into
                 (for [[i row] (map-indexed vector rows)
                       [j a]   (map-indexed vector row)
                       :when (not= a \.)]
                   {a [[i j]]}))}))

(defn in-bounds [size]
  (fn [[x y]]
    (and (< -1 x size) (< -1 y size))))

(defn antinodes1 [size]
  (fn [a b]
    (filter (in-bounds size)
      (let [diff (mapv - b a)]
        [(mapv - a diff) (mapv + b diff)]))))

(defn antinodes2 [size]
  (fn [a b]
    (let [diff (mapv - b a)]
      (concat
        (take-while (in-bounds size) (iterate #(mapv + % diff) a))
        (take-while (in-bounds size) (iterate #(mapv - % diff) b))))))

(defn search [antennas antinode-fn]
  (into #{}
    (for [[_ ps] antennas
          p1 ps
          p2 ps
          :when (not= p1 p2)
          an (antinode-fn p1 p2)]
      an)))

(defn solve-part1 [{:keys [size antennas]}]
  (count (search antennas (antinodes1 size))))

(defn solve-part2 [{:keys [size antennas]}]
  (count (search antennas (antinodes2 size))))
