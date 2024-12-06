(ns aoc2024.day06
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (mapv vec (str/split-lines input)))

(def directions [[-1 0] [0 1] [1 0] [0 -1]]) ; up, right, down, left

(defn start-state [m]
  {:facing 0
   :pos (first (for [[i row] (map-indexed vector m)
                     [j c] (map-indexed vector row)
                     :when (= c \^)]
                 [i j]))})

(defn obstacle-ahead? [m {:keys [facing pos]}]
  (let [ahead (mapv + pos (directions facing))]
    (= (get-in m ahead) \#)))

(defn turn-right [state]
  (update state :facing #(-> % inc (mod 4))))

(defn step-forward [{:keys [pos facing] :as state}]
  (update state :pos #(mapv + % (directions facing))))

(defn patrol-protocol [m state]
  (if (obstacle-ahead? m state)
    (turn-right state)
    (step-forward state)))

(defn patrol [m start]
  (loop [state start, visited #{(start :pos)}]
    (let [new-state (patrol-protocol m state)]
      (if (nil? (get-in m (:pos new-state)))
        visited
        (recur new-state (conj visited (new-state :pos)))))))

(defn loop? [m start]
  (loop [state start, visited #{start}]
    (let [new-state (patrol-protocol m state)]
      (cond
        (nil? (get-in m (:pos new-state))) false
        (contains? visited new-state)      true
        :else
        (recur new-state (conj visited new-state))))))

(defn solve-part1 [input]
  (count (patrol input (start-state input))))

(defn solve-part2 [input]
  (let [start (start-state input)
        path (patrol input start)]
    (count (filter true?
             (for [obstacle-pos (disj path (start :pos))]
               (loop? (assoc-in input obstacle-pos \#) start))))))
