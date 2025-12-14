(ns aoc2025.day12
  (:require [clojure.string :as str]))

(def example-input "0:
###
##.
##.

1:
###
##.
.##

2:
.##
###
##.

3:
##.
###
##.

4:
###
#..
###

5:
###
.#.
###

4x4: 0 0 0 0 2 0
12x5: 1 0 1 0 2 2
12x5: 1 0 1 0 3 2")

(defn parse-input [input]
  (let [lines (str/split input #"\R\R")
        shapes (->> (butlast lines)
                    (mapv #(->> (str/split-lines %)
                                (drop 1)
                                (mapv vec))))
        regions (->> (last lines)
                     (str/split-lines)
                     (map #(->> (re-seq #"\d+" %)
                                (map parse-long)
                                (split-at 2))))]
    {:shapes shapes
     :regions regions}))

(defn rotate [present]
  (->> present
       (apply map vector)
       (mapv (comp vec reverse))))

(defn flip [present]
  (mapv #(vec (reverse %)) present))

(defn present-coords [present]
  (for [[r row] (map-indexed vector present)
        [c v]   (map-indexed vector row)
        :when (= v \#)]
    [r c]))

(defn make-region [[w l]]
  (vec (repeat l (vec (repeat w \.)))))

(defn place-present [region delta present]
  (reduce
    (fn [region coord]
      (let [target (mapv + delta coord)
            v (get-in region target)]
        (prn target v)
        (if (= v \.)
          (assoc-in region target \#)
          (reduced nil))))
    region
    (present-coords present)))

(defn show! [s]
  (doseq [l s]
    (prn (apply str l))))

(defn show-region! [region]
  (run! prn region))

(let [present (->> example-input
                   parse-input
                   :shapes)]
  (-> (make-region [4 4])
      (place-present [0 0] (rotate (present 4)))
      (show-region!)))

(def presents (->> example-input
                   parse-input
                   :shapes))

(flip (presents 0))

(defn solve-part1 [input])

(defn solve-part2 [input])

(comment
  (->> (clojure.java.io/resource "day12.txt")
       slurp
       parse-input))
