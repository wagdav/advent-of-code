(ns aoc2025.day10
  (:require [clojure.string :as str]
            [aoc2024.search :as search]))

(def example-input "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}")

(defn parse-input [input]
  (->> (str/split-lines input)
       (map (fn [line]
              (let [parts (str/split line #" ")]
                {:indicators (->> (first parts)
                                  (keep #{\. \#})
                                  (mapv {\. false \# true}))
                 :buttons (->> (butlast parts)
                               (drop 1)
                               (map #(re-seq #"\d+" %))
                               (map #(mapv parse-long %))
                               (map set))
                 :joltages (->> (last parts)
                                (re-seq #"\d+")
                                (mapv parse-long))})))))

(defn toggle [state buttons]
  (map-indexed (fn [i s]
                 (if (buttons i)
                   (not s)
                   s))
               state))

(defn increase-joltage [state buttons]
  (vec
    (map-indexed (fn [i s]
                   (if (buttons i)
                     (inc s)
                     s))
                 state)))

(defn fewest-presses [{:keys [indicators buttons]}]
  (:path-cost
    (search/uniform-cost
      (reify search/Problem
        (actions [this state]
          buttons)
        (goal? [this state]
          (= state indicators))
        (initial-state [this]
          (repeat (count indicators) false))
        (result [this state action]
          (toggle state action))
        (step-cost [this state action]
          1)))))

(defn fewest-presses2 [{:keys [joltages buttons]}]
  (:path-cost
    (search/uniform-cost
      (reify search/Problem
        (actions [this state]
          (for [bs buttons
                :when (every? #(< (state %) (joltages %)) bs)]
            bs))
        (goal? [this state]
          (= state joltages))
        (initial-state [this]
          (vec (repeat (count joltages) 0)))
        (result [this state action]
          (increase-joltage state action))
        (step-cost [this state action]
          1)))))

(defn solve-part1 [input]
  (apply + (map fewest-presses input)))

(->> example-input
     parse-input
     solve-part1)

(defn solve-part2 [input]
  (apply + (map fewest-presses2 input)))

(->> example-input
   parse-input
   solve-part2)
