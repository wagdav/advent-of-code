(ns aoc2025.day01
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (->> (str/split-lines input)
       (map (fn [[direction & amount]]
              [direction (parse-long (apply str amount))]))))

(defn rotate [pos [d n]]
  (case d
    \L (- pos n)
    \R (+ pos n)))

(defn password [pos new-pos]
  (if (zero? (mod new-pos 100))
    1
    0))

(defn password-click [pos new-pos] ; method 0x434C49434B
  (cond
    (zero? new-pos)
    1

    (neg? (* pos new-pos))
    (inc (abs (quot new-pos 100)))

    :else
    (abs (quot new-pos 100))))

(defn make-step [password-method]
  (fn [[pos clicks] code]
    (let [new-pos (rotate pos code)
          new-clicks (password-method pos new-pos)]
      [(mod new-pos 100) (+ clicks new-clicks)])))

(defn solve-part1 [rs]
  (second (reduce (make-step password) [50 0] rs)))

(defn solve-part2 [rs]
  (second (reduce (make-step password-click) [50 0] rs)))
