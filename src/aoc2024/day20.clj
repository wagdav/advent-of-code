(ns aoc2024.day20
  (:require [clojure.string :as str]
            [aoc2024.search :as search]))

(defn parse-input [input]
  (apply merge-with into
    (for [[i row] (map-indexed vector (str/split-lines input))
          [j c]   (map-indexed vector row)
          :when (not= c \#)]
      (case c
        \. {:free #{[i j]}}
        \S {:free #{[i j]}
            :pos [i j]}
        \E {:free #{[i j]}
            :end [i j]}))))

(defn fastest-path [input]
  (mapv :pos
    (:path
      (search/uniform-cost
        (reify search/Problem
          (initial-state [_] input)
          (goal? [_ {:keys [pos end]}] (= pos end))
          (actions [_ {:keys [free pos]}]
            (for [d [[0 1] [1 0] [0 -1] [-1 0]] :when (free (mapv + pos d))] d))
          (result [this {:keys [pos] :as state} action]
            (assoc state :pos (mapv + pos action)))
          (step-cost [this state action]
            1))))))

(defn manhattan [[a1 a2] [b1 b2]]
  (+ (abs (- a1 b1)) (abs (- a2 b2))))

(defn cheats [at-least max-cheats input]
  (let [path (fastest-path input)]
    (for [i (range (count path))
          j (range i (count path))
          :let [distance (manhattan (path i) (path j))
                saving (- j i distance)]
          :when (and (<= at-least saving)
                     (<= 2 distance max-cheats))]
      saving)))

(defn solve-part1 [input]
   (count (cheats 100 2 input)))

(defn solve-part2 [input]
   (count (cheats 100 20 input)))

(comment
  (time (solve-part1 (parse-input (slurp "resources/day20.txt"))))
  (time (solve-part2 (parse-input (slurp "resources/day20.txt")))))
