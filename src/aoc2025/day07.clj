(ns aoc2025.day07
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn parse-row [row]
  (->> (map-indexed vector row)
       (keep (fn [[i v]] (when (#{\S \^} v) i)))))

(defn parse-input [input]
  (let [[start-line & splitters] (str/split-lines input)]
    {:beams (into #{} (parse-row start-line))
     :splitters (->> splitters
                     (map parse-row)
                     (keep seq))}))

(defn run-beams [{:keys [beams splitters]}]
 (reduce
   (fn [{:keys [beams splits timelines]} scols]
     (let [forming (set (mapcat (fn [s] [(dec s) (inc s)]) scols))
           splitting (set/intersection beams (set scols))
           new-beams (into (set/difference beams splitting) forming)]
       {:beams new-beams
        :splits (+ splits (count splitting))
        :timelines (reduce
                     (fn [res pos]
                       (if-let [beams (res pos)]
                         (-> res
                             (assoc pos 0)
                             (update (inc pos) (fnil #(+ % beams) 0))
                             (update (dec pos) (fnil #(+ % beams) 0)))
                         res))
                     timelines
                     scols)}))
   {:beams beams
    :splits 0
    :timelines { (first beams) 1}}
   splitters))

(defn solve-part1 [input]
  (:splits (run-beams input)))

(defn solve-part2 [input]
  (reduce + (vals (:timelines (run-beams input)))))
