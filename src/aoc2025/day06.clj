(ns aoc2025.day06
  (:require [clojure.string :as str]))

(defn transpose [v]
  (apply mapv vector v))

(defn parse-input [input]
  (let [lines (str/split-lines input)]
    {:numbers (->> (butlast lines)
                   (map #(re-seq #"\d+" %))
                   (map #(map parse-long %))
                   transpose)
     :numbers2 (->> (butlast lines)
                    transpose
                    (map #(remove #{\space} %))
                    (reduce
                      (fn [res numbers]
                        (if (seq numbers)
                          (conj (pop res) (conj (peek res) numbers))
                          (conj res [])))
                      [[]])
                    (map (fn [ns] (map #(parse-long (apply str %)) ns))))
     :ops (->> (last lines)
               (re-seq #"[\*+]")
               (map {"*" * "+" +}))}))

(defn solve-part1 [{:keys [ops numbers]}]
  (reduce + (map apply ops numbers)))

(defn solve-part2 [{:keys [ops numbers2]}]
  (reduce + (map apply ops numbers2)))
