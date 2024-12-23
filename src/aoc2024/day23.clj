(ns aoc2024.day23
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (let [pairs (for [line (str/split-lines input)]
                (str/split line #"-"))]
    (merge-with into
                (update-vals (group-by first pairs)
                             #(set (map second %)))
                (update-vals (group-by first (for [[a b] pairs] [b a]))
                             #(set (map second %))))))

(defn connected? [g a b]
  (contains? (g a #{}) b))

(defn triples [g]
  (set (for [a (keys g)
             b (keys g)
             c (keys g)
             :when (and (or (str/starts-with? a "t")
                            (str/starts-with? b "t")
                            (str/starts-with? c "t"))
                        (and (connected? g a b)
                             (connected? g b c)
                             (connected? g c a)))]

         (set [a b c]))))

(defn filter-connected [g ps]
  (reduce
    (fn [res p]
      (if (every? #(connected? g p %) res)
        (conj res p)
        res))
    []
    ps))

(defn network [g start]
  (loop [frontier #{start}, visited #{start}]
    (if (seq frontier)
      (let [frontier' (->> (for [f frontier
                                 p (g f)
                                 :when (and (not (visited p))
                                            (every? #(connected? g p %) visited))]
                             p)
                           (filter-connected g))]
        (recur (set frontier') (into visited frontier')))
      visited)))

(defn solve-part1 [input]
  (count (triples input)))

(defn solve-part2 [input]
  (->> (for [n (keys input)]
         (network input n))
       (apply max-key count)
       (str/join ",")))

(comment
  (time (solve-part1 (parse-input (slurp "resources/day23.txt"))))
  (solve-part2 (parse-input (slurp "resources/day23.txt"))))
