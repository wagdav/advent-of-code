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
                     (map (fn [line]
                            (let [[size required] (->> line
                                                       (re-seq #"\d+")
                                                       (map parse-long)
                                                       (split-at 2))]
                             [size (vec required)]))))]
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

(defn free-coords [region]
  (let [occupied (for [[r row] (map-indexed vector region)
                       [c v]   (map-indexed vector row)
                       :when (= v \#)]
                   [r c])]

    (if (seq occupied)
      (for [[r c] occupied
            dr [-1 0 1]
            dc [-1 0 1]
            :let [rr (+ r dr)
                  cc (+ c dc)]
            :when (and (not= dr dc 0)
                       (= \. (get-in region [rr cc])))]
        [rr cc])
      [[0 0]])))

(defn make-region [[w l]]
  (vec (repeat l (vec (repeat w \.)))))

(defn place-present [region delta present]
  (reduce
    (fn [region coord]
      (let [target (mapv + delta coord)
            v (get-in region target)]
        (if (= v \.)
          (assoc-in region target \#)
          (reduced nil))))
    region
    (present-coords present)))

(defn show! [s]
  (doseq [l s]
    (prn (apply str l))))

(defn all-orientations [present]
  (for [rotated [present
                 (-> present rotate)
                 (-> present rotate rotate)
                 (-> present rotate rotate rotate)]
        present' [rotated (flip rotated)]]
    present'))

(def all-fit?
  (memoize
    (fn
      ([presents [region required]]
       (all-fit? presents [(make-region region) required] 0))

      ([presents [region required] p]
       (cond
         (= p (count presents))
         (every? zero? required) ; checked everything and all

         (zero? (required p))
         (all-fit? presents [region required] (inc p))

         :else
         (some
           true?
           (for [orientation (all-orientations (presents p))
                 free-spot (free-coords region)
                 :let [new-region (place-present region free-spot orientation)]
                 :when new-region]
             (all-fit? presents [new-region (update required p dec)] p))))))))

(def presents (->> example-input
                   parse-input
                   :shapes))

(show! (presents 0))
(show! (flip (presents 0)))

(defn solve-part1 [{:keys [shapes regions]}]
  (count (filter #(all-fit? shapes %) regions)))

(defn solve-part2 [input])

(defn run [opts]
  (->> example-input
       parse-input
       solve-part1
       prn))

(defn run-real [opts]
  (->> (clojure.java.io/resource "day12.txt")
       slurp
       parse-input
       solve-part1
       prn))

(comment
  (let [present (->> example-input
                   parse-input
                   :shapes)]
   (-> (make-region [4 4])
     (place-present [0 0] (rotate (present 4)))))

  (->> (clojure.java.io/resource "day12.txt")
       slurp
       parse-input)

  (->> example-input
       parse-input
       solve-part1))
