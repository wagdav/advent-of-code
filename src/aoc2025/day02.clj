(ns aoc2025.day02
  (:require [clojure.string :as str]))

(def example-input "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124")

(defn parse-input [input]
  (->> input
       (re-seq #"\d+")
       (map parse-long)
       (partition 2)))

(defn doubled? [n]
  (let [as-str (str n)
        mid (quot (count as-str) 2)]
    (= (.substring as-str 0 mid)
       (.substring as-str mid))))

(defn repeated? [number k]
  (let [ps (partition k k (cycle [:pad]) (str number))]
    (and
      (< 1 (count ps))
      (apply = ps))))

(defn at-least-doubled? [n]
  (some (partial repeated? n) (range 1 (count (str n)))))

(defn check-range [filter-fn [start end]]
  (->> (range start (inc end))
       (filter filter-fn)
       (reduce +)))

(defn solve-part1 [id-ranges]
  (reduce + (map (partial check-range doubled?) id-ranges)))

(defn solve-part2 [id-ranges]
  (reduce + (map (partial check-range at-least-doubled?) id-ranges)))
