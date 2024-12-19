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

(defn arrangements' [patterns design]
  (loop [cnt 0, q (vector design)]
    (if (empty? q)
      cnt
      (let [d (peek q)]
        (if (empty? d)
          (recur (inc cnt) (pop q))
          (recur cnt (into (pop q) (for [p patterns
                                         :when (str/starts-with? d p)]
                                     (.substring d (count p))))))))))

(def arrangements
  (memoize (fn [patterns design]
             (let [good (keep (fn [p] (when (str/starts-with? design p)
                                        (.substring design (count p))))
                              patterns)]
               (+ (count (filter empty? good))
                  (* (count (filter #(seq %) good))
                     (count (map (partial arrangements patterns)
                                 (filter #(seq %) good)))))))))

(defn solve-part1 [[patterns designs]]
  (count (filter (partial possible? patterns) designs)))

(defn solve-part2 [[patterns designs]]
  (apply +
    (for [d designs
          :when (possible? patterns d)]
      (arrangements patterns d))))

