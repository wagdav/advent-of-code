(ns aoc2024.day13
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (for [block (str/split input #"\R\R")]
    (let [[a b prize] (->> (re-seq #"\d+" block)
                           (mapv parse-long)
                           (partition 2))]
      {:a a, :b b, :prize prize})))

; To reach the prize means: alpha * a + beta * b = p, where a and b are the
; button vectors, p is the location of the prize. These form two linear
; equations for alpha and beta.
(defn tokens
  ([machine]
   (tokens 0 machine))
  ([unit {:keys [a b prize]}]
   (let [[a1 a2] a
         [b1 b2] b
         [p1 p2] (mapv (partial + unit) prize)
         alpha (/ (- p1 (* p2 (/ b1 b2)))
                  (- a1 (* a2 (/ b1 b2))))
         beta (/ (- p2 (* alpha a2)) b2)]
     (if (and (integer? alpha) (integer? beta))
       (+ (* 3 alpha) beta)
       0))))

(defn solve-part1 [input]
  (apply + (map tokens input)))

(defn solve-part2 [input]
  (apply + (map (partial tokens 10000000000000) input)))
