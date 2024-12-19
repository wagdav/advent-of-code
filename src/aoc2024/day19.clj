(ns aoc2024.day19
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (let [[a b] (str/split input #"\R\R")]
    [(str/split a #", ")
     (str/split-lines b)]))

(defn prefix-matches [patterns design]
  (for [p patterns
        :when (str/starts-with? design p)]
    (.substring design (count p))))

(defn possible? [patterns design]
  (loop [q (list design)]
    (if (empty? q)
      false
      (let [d (peek q)]
        (if (empty? d)
          true
          (recur (into (pop q) (prefix-matches patterns d))))))))

(def arrangements
  (memoize
    (fn [patterns design]
      (let [match (group-by empty? (prefix-matches patterns design))]
         (+ (count (match true))
            (apply +
              (for [g (match false)]
                (arrangements patterns g))))))))

(defn solve-part1 [[patterns designs]]
  (count (filter (partial possible? patterns) designs)))

(defn solve-part2 [[patterns designs]]
  (apply + (map (partial arrangements patterns) designs)))
