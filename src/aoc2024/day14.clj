(ns aoc2024.day14
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (->> (re-seq #"-?\d+" input)
       (mapv parse-long)
       (partition 4)))

(defn move [[sx sy] [x y vx vy]]
  [(-> x (+ vx) (mod sx)) (-> y (+ vy) (mod sy)) vx vy])

(defn move-all [size robots]
  (map (partial move size) robots))

(defn safety-factor [[sx sy] robots]
  (apply *
    (for [op1 [< >]
          op2 [< >]]
      (->> robots
           (filter (fn [[x y _ _]] (and (op1 x (quot sx 2))
                                        (op2 y (quot sy 2)))))
           (count)))))

(defn solve-part1
 ([input]
  (solve-part1 input [101 103]))
 ([input size]
  (safety-factor
    size
    (nth
      (iterate (partial move-all size) input)
      100))))

(defn 🎄 [input]
  (loop [i 1, robots input]
    (let [robots' (move-all [101 103] robots)
          m (->> robots'
                 (map #(take 2 %))
                 (into #{}))]
      (if (= (count m) (count robots'))
        [i m]
        (recur (inc i) robots')))))

(defn solve-part2 [input]
  (first (🎄 input)))

(defn viz [[sx sy] m]
  (doseq [y (range sy)]
    (doseq [x (range sx)]
      (if (m [x y])
        (print "#")
        (print " ")))
    (println)))

; clj -X aoc2024.day14/run
(defn run [opts]
  (let [[i p] (🎄 (parse-input (slurp "resources/day14.txt")))]
    (viz [101 103] p)))

(comment
  (time (solve-part1 (parse-input (slurp "resources/day14.txt")))))
