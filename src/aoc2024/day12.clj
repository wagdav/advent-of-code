(ns aoc2024.day12
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (into {}
    (for [[i line] (map-indexed vector (str/split-lines input))
          [j c] (map-indexed vector line)]
     [[i j] c])))

(defn garden [m start]
  (let [plant (get m start)]
    (loop [frontier #{start}, visited #{start}]
      (if (seq frontier)
        (let [frontier' (for [f frontier
                              d [[-1 0] [1 0] [0 -1] [0 1]]
                              :let [pos' (mapv + f d)
                                    plant' (get m pos')]
                              :when (and (= plant plant')
                                         (not (visited pos')))]
                          pos')]
          (recur (set frontier') (into visited frontier)))
        visited))))

(defn perimeter [garden]
  (for [g garden
        d [[-1 0] [1 0] [0 -1] [0 1]]
        :when (nil? (garden (mapv + g d)))]
    g))

(defn gardens [input]
  (loop [gs [], coords input]
    (if (seq coords)
      (let [g (garden input (ffirst coords))]
        (recur
          (conj gs g)
          (apply dissoc coords g)))
      gs)))

(defn border [garden]
  (for [g garden
        d [[-1 0] [1 0] [0 -1] [0 1]]
        :let [pos' (mapv + g d)]
        :when (not (contains? garden pos'))]
    [pos' d]))

(defn collapse [border d]
  (loop [border border]
    (let [border' (remove
                    (fn [[b n]]
                      (let [moved (mapv + b d)]
                        (contains? (set border) [moved n])))
                    border)]
      (if (= border border')
        border
        (recur border')))))

(defn sides [garden]
  (-> (border garden)
      (collapse [0 1])
      (collapse [1 0])
      (count)))

(defn solve-part1 [input]
  (apply +
    (for [g (gardens input)]
      (* (count g) (count (perimeter g))))))

(defn solve-part2 [input]
  (apply +
    (for [g (gardens input)]
      (* (count g) (sides g)))))

(comment
  (time (solve-part1 (parse-input (slurp "resources/day12.txt")))) ; 1477924
  (time (solve-part2 (parse-input (slurp "resources/day12.txt"))))) ; 841934

