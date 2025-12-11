(ns aoc2025.day11
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (->> input
       str/split-lines
       (map #(str/split % #"[ :]+"))
       (reduce #(assoc %1 (first %2) (rest %2)) {})))

(defn paths
  ([input from to]
   (paths input from to nil))
  ([input from to via]
   (loop [paths #{}, q [[from]], explored #{}]
     (if (empty? q)
       (count paths)
       (let [cur (peek q)
             q' (into (pop q)
                      (->> (input (last cur))
                           (remove explored)
                           (map #(conj cur %))))]
         (recur
           (if (and (= to (last cur))
                    (if via ((set cur) via) true))
             (conj paths cur)
             paths)
           q'
           (conj explored cur)))))))

(defn solve-part1 [input]
  (paths input "you" "out"))

(defn solve-part2 [input]
  (let [start "svr"
        end "out"
        ; I plotted the input data with Graphviz which revealed that the
        ; devices are connected through layers of "hubs".  All paths must go
        ; through through one hub in each layer. I identified the hubs
        ; visually.
        hubs [["yad" "caw" "jub" "aek" "xvt"]   ;0
              ["xxt" "bmm" "nxq" "nhk"]         ;1  via "fft"
              ["arh" "sxr", "els" "tku" "zgf"]  ;2
              ["xdu" "fbt" "tak" "xvf"]         ;3
              ["sus" "tgd" "you"]]]             ;4  via "dac"
   (apply +
     (for [h0 (hubs 0)
           h1 (hubs 1)
           h2 (hubs 2)
           h3 (hubs 3)
           h4 (hubs 4)]
       (*
         (paths (apply dissoc input (hubs 0)) start h0)
         (paths (apply dissoc input (hubs 1)) h0 h1 "fft")
         (paths (apply dissoc input (hubs 2)) h1 h2)
         (paths (apply dissoc input (hubs 3)) h2 h3)
         (paths (apply dissoc input (hubs 4)) h3 h4 "dac")
         (paths input h4 end))))))

(defn dot! [input]
  (println "digraph G {")
  (doseq [line (into (for [[from tos] input
                           to tos]
                      (str " " from " -> " to ";")))]
    (println line))
  (println "}"))

(defn run [opts]
  (prn (solve-part2 (parse-input (slurp (clojure.java.io/resource "day11.txt"))))))

; clj -X aoc2025.day11/dot
(defn dot [opts]
  (->> (parse-input (slurp (clojure.java.io/resource "day11.txt")))
       dot!))
