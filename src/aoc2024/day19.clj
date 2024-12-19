(ns aoc2024.day19
  (:require [clojure.string :as str]))

(def example-input "r, wr, b, g, bwu, rb, gb, br

brwrr
bggr
gbbr
rrbgbr
ubwu
bwurrg
brgr
bbrgwb")

(defn parse-input [input]
  (let [[a b] (str/split input #"\R\R")]
    [(str/split a #", ")
     (str/split-lines b)]))

(def ex (parse-input example-input))

(defn possible? [patterns design]
  (loop [q (list design)]
    (if (empty? q)
      false
      (let [d (peek q)]
        (if (empty? d)
          true
          (recur (into (pop q) (for [p patterns
                                     :when (str/starts-with? d p)]
                                 (.substring d (count p))))))))))

(defn solve-part1 [[patterns designs]]
  (count (filter (partial possible? patterns) designs)))

(solve-part1 ex)

(defn solve-part2 [input])
