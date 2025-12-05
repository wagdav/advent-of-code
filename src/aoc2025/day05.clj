(ns aoc2025.day05
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (let [[fresh available] (str/split input #"\R\R")]
    {:fresh-ids (->> fresh
                     (re-seq #"\d+")
                     (map parse-long)
                     (partitionv 2))
     :available (->> available
                     str/split-lines
                     (map parse-long))}))

(defn fresh? [fresh-ids id]
  (some (fn [[start end]] (<= start id end)) fresh-ids))

(defn merge-overlapping [fresh-ids]
  (let [sorted (sort-by first fresh-ids)]
    (reduce
      (fn [res [start end]]
        (let [[last-start last-end] (peek res)]
          (assert (<= last-start start))
          (if (< last-end start)
            (conj res [start end])
            (conj (pop res) [last-start (max end last-end)]))))
      [(first sorted)]
      (rest sorted))))

(defn solve-part1 [{:keys [fresh-ids available]}]
  (count (filter (partial fresh? fresh-ids) available)))

(defn solve-part2 [{:keys [fresh-ids]}]
  (->> (merge-overlapping fresh-ids)
       (map (fn [[start end]] (inc (- end start))))
       (reduce +)))
