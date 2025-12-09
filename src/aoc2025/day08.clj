(ns aoc2025.day08
  (:require [clojure.string :as str]))

(def example-input "162,817,812
57,618,57
906,360,560
592,479,940
352,342,300
466,668,158
542,29,236
431,825,988
739,650,466
52,470,668
216,146,977
819,987,18
117,168,530
805,96,715
346,949,466
970,615,88
941,993,340
862,61,35
984,92,344
425,690,689")

(defn parse-input [input]
  (->> input
       (re-seq #"\d+")
       (map parse-long)
       (partitionv 3)))

(defn distance [[a1 a2 a3] [b1 b2 b3]]
  (+ (* (- a1 b1) (- a1 b1))
     (* (- a2 b2) (- a2 b2))
     (* (- a3 b3) (- a3 b3))))

(defn circuit [start explored connections]
  (loop [explored explored
         frontier [start]]
    (if (empty? frontier)
      explored
      (let [f (peek frontier)
            to (->> connections
                    (filter #(= f (first %)))
                    (map second))
            from (->> connections
                      (filter #(= f (second %)))
                      (map first))]
        (recur
          (-> explored
              (into to)
              (into from))
          (-> (pop frontier)
              (into (remove explored from))
              (into (remove explored to))))))))

(defn circuit2 [start connections size]
  (loop [explored #{start}
         frontier [start]
         connections connections]
    (if (= (count explored) size)
      (last connections)
      (let [f (peek frontier)]
        (recur
          (into explored (first connections))
          (into (pop frontier) (remove explored connections))
          (rest connections))))))

(defn solve-part1 [n-pairs input]
  (let [by-distance (sort-by first
                     (for [[i a] (map-indexed vector input)
                           [j b] (map-indexed vector input)
                           :when (< i j)]
                       [(distance a b) a b]))
        connections (->> (take n-pairs by-distance)
                         (mapv #(drop 1 %)))]
    (loop [circuits []
           junctions (set input)]
      (if (empty? junctions)
        (apply * (take 3 (sort > (map count circuits))))
        (let [new-circuit (circuit (first junctions) #{(first junctions)} connections)]
           (recur
             (conj circuits new-circuit)
             (remove new-circuit (rest junctions))))))))

(defn solve-part2* [input]
  (let [by-distance (sort-by first
                     (for [[i a] (map-indexed vector input)
                           [j b] (map-indexed vector input)
                           :when (< i j)]
                       [(distance a b) a b]))]
    (loop [n 10
           crt #{}]
        (prn n)
        (let [connections (->> (take n by-distance)
                               (mapv #(drop 1 %)))
              new-circuit (circuit (second (first by-distance)) #{} connections)]
          #_(prn (last connections) (count new-circuit))
          (if (= (count new-circuit) (count input))
            (->> (last connections)
                 (map first)
                 (apply *))
            (recur (inc n) new-circuit))))))

(defn solve-part2** [input]
  (let [by-distance (sort-by first
                     (for [[i a] (map-indexed vector input)
                           [j b] (map-indexed vector input)
                           :when (< i j)]
                       [(distance a b) a b]))
        connections (mapv #(drop 1 %) by-distance)]
    (->> (circuit2 (ffirst connections) connections (count input))
         (map first)
         (apply *))))

(defn solve-part2 [input]
  (let [by-distance (sort-by first
                     (for [[i a] (map-indexed vector input)
                           [j b] (map-indexed vector input)
                           :when (< i j)]
                       [(distance a b) a b]))
        connections (mapv #(drop 1 %) by-distance)]
    (reduce
      (fn [circuit [a b]]
        (let [new-circuit (conj circuit a b)]
          (if (= (count new-circuit) (count input))
            (reduced (* (first a) (first b)))
            (conj circuit a b))))
      #{}
      connections)
    (->> (circuit2 (ffirst connections) connections (count input))
         (map first)
         (apply *))))

(->> example-input
     parse-input
     (solve-part1 10))

(defn run [opts]
  (let [input (parse-input (slurp (clojure.java.io/resource "day08.txt")))]
     (prn (solve-part2 input))))

(defn run-ex [opts]
  (prn (->> example-input
            parse-input
            solve-part2)))

(comment
  (time (->> example-input
             parse-input
             solve-part2)))
