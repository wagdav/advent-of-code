(ns aoc2024.day15
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (let [[a b] (str/split input #"\R\R")]
    (assoc
      (apply merge-with into
        (for [[i row] (map-indexed vector (str/split-lines a))
              [j c]   (map-indexed vector row)
              :when (not= c \.)]
          (case c
            \@ {:pos [i j]}
            \O {:boxes #{[i j]}}
            \# {:walls #{[i j]}})))
      :moves (map first (re-seq #"\S" b)))))

(def directions {\^ [-1 0] \v [1 0] \> [0 1] \< [0 -1]})

(def ^:dynamic *width* 1)

(defn wall? [{:keys [walls]} [px py]]
  (case *width*
    1 (walls [px py])
    2 (or (walls [px py])
          (walls [px (dec py)]))))

(defn box? [{:keys [boxes]} [px py]]
  (case *width*
    1 (boxes [px py])
    2 (or (boxes [px py])
          (boxes [px (dec py)]))))

(defn box-coords [warehouse p]
  (let [[bx by] (box? warehouse p)]
    (case *width*
      1 [[bx by]]
      2 [[bx by] [bx (inc by)]])))

(defn free? [warehouse pos]
  (and (not (wall? warehouse pos))
       (not (box? warehouse pos))))

(defn behind-box [warehouse box d]
  (loop [p box]
    (let [p' (mapv + p d)]
      (if (or (free? warehouse p')
              (wall? warehouse p')
              (not= (box? warehouse p') (box? warehouse p)))
        p'
        (recur p')))))

(defn pushable-boxes [warehouse pos d]
  (loop [res #{}, q (box-coords warehouse pos)]
    (if (seq q)
      (let [p (peek q)
            q' (pop q)
            p' (behind-box warehouse p d)]
        (cond
          (wall? warehouse p')
          #{}

          (free? warehouse p')
          (recur (conj res (box? warehouse p)) q')

          :else
          (recur (conj res (box? warehouse p))
                 (into q' (box-coords warehouse p')))))
      res)))

(defn step [{:keys [pos] :as state} move]
  (let [d (directions move)
        ahead (mapv + pos d)]
    (cond
      (wall? state ahead)
      state

      (free? state ahead)
      (assoc state :pos ahead)

      :else
      (let [bs (pushable-boxes state ahead d)]
        (if (empty? bs)
          state
          (-> state
              (update :boxes #(apply disj % bs))
              (update :boxes into (map #(mapv + d %) bs))
              (assoc :pos ahead)))))))

(defn gps [{:keys [boxes]}]
  (apply +
    (for [[r c] boxes]
      (+ (* 100 r) c))))

(defn scale [[r c]]
  [r (* *width* c)])

(defn solve-part1 [{:keys [moves] :as state}]
  (gps (reduce step state moves)))

(defn solve-part2 [{:keys [moves] :as state}]
  (binding [*width* 2]
    (let [scaled (-> state
                     (update :boxes #(set (map scale %)))
                     (update :walls #(set (map scale %)))
                     (update :pos scale))]
      (gps (reduce step scaled moves)))))
