(ns aoc2024.day25
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (let [ss (->> (for [p (str/split input #"\R\R")]
                  (str/split-lines p))
                (group-by #(= "#####" (first %))))]
    {:locks (->> (ss true))
     :keys (ss false)}))

(defn transpose [v]
  (apply mapv vector v))

(defn lock->heights [lock]
  (->> (drop 1 lock)
       (transpose)
       (map #(filter #{\#} %))
       (map count)))

(defn key->heights [k]
  (->> (butlast k)
       (transpose)
       (map #(filter #{\#} %))
       (map count)))

(defn solve-part1 [{:keys [keys locks]}]
  (count
    (for [l locks
          k keys
          :let [lh (lock->heights l)
                kh (key->heights k)
                m (apply max (mapv + lh kh))]
          :when (<= m 5)]
      m)))
