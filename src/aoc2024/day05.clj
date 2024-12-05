(ns aoc2024.day05
  (:require [clojure.string :as str]
            [clojure.set :as s]))

(defn parse-input [input]
  (let [[rules updates] (str/split input #"\n\n")]
    {:rules
     (update-vals
       (->> (re-seq #"\d+" rules)
            (map parse-long)
            (partition 2)
            (group-by first))
       (fn [v] (set (map second v))))
     :updates (->> (for [line (str/split-lines updates)]
                     (map parse-long (re-seq #"\d+" line))))}))

(defn correct? [rules pages]
  (let [p (first pages)
        r (rest pages)]
    (if (seq r)
      (if (empty? (s/difference (set r) (rules p)))
        (recur rules (rest pages))
        false)
      true)))

(defn middle [s]
  (first (drop (quot (count s) 2) s)))

(defn reorder [rules pages]
  (sort #(contains? (rules %1) %2) pages))

(defn solve-part1 [{:keys [rules updates]}]
  (->> updates
       (filter (partial correct? rules))
       (map middle)
       (apply +)))

(defn solve-part2 [{:keys [rules updates]}]
  (->> updates
       (remove (partial correct? rules))
       (map (partial reorder rules))
       (map middle)
       (apply +)))
