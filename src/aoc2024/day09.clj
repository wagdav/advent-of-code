(ns aoc2024.day09
  (:require [clojure.string :as str]))

(def example-input "2333133121414131402")

(defn parse-input [input]
  (->> (re-seq #"\d" input)
       (mapv parse-long)
       (partition 2 2 [0])
       (map-indexed (fn [i [n f]] [i n f]))))

(defn seek [disk]
  (some (fn [[i [id sz f]]]
          (when (pos? f) i))
        (map-indexed vector disk)))

(defn defrag-step [disk]
  (let [done (seek disk)]
    (if (nil? done)
      disk
      (let [[dd wk] (split-at done disk)
            [i n f] (first wk)
            [j m _] (last wk)]
        (cond
          (>= f m)
          (concat dd
                  [[i n 0]] [[j m (- f m)]]
                  (drop 1 (butlast wk)))

          (< f m)
          (concat dd
                  [[i n 0]] [[j f 0]]
                  (drop 1 (butlast wk))
                  [[j (- m f) 0]]))))))

(defn defrag [disk]
  (loop [i 0, d disk]
    (if (> i (count disk))
      d
      (recur (inc i) (defrag-step d)))))

(defn make-fs [disk]
  (reduce
    (fn [{:keys [pos] :as res} [id size free]]
      (cond-> res
       true        (update :files assoc id [pos size])
       (pos? free) (update :free  assoc (+ pos size) free)
       true        (update :pos + free size)))
    {:free (sorted-map)
     :files (sorted-map)
     :pos 0}
    disk))

(defn mv [{:keys [files free]} file-id new-addr]
  (let [[old-addr file-size] (get files file-id)]
    {:files
     (assoc files file-id [new-addr file-size])
     :free
     (-> free
         (assoc old-addr file-size)
         (dissoc new-addr)
         (assoc (+ new-addr file-size)
                (- (free new-addr) file-size)))}))

(defn seek-free-space [{:keys [files free]} file-id]
  (let [[file-addr file-size] (files file-id)]
    (some
      (fn [[addr size]]
        (when (and (< addr file-addr)
                   (<= file-size size)) addr))
      free)))

(defn defrag2 [disk]
  (let [fs (make-fs disk)]
    (reduce
      (fn [fs file-id]
        (if-let [addr (seek-free-space fs file-id)]
           (mv fs file-id addr)
           fs))
      fs
      (reverse (keys (fs :files))))))

(defn checksum [disk]
  (apply +
    (map #(apply * %)
      (map-indexed vector
        (for [[id sz _] disk
              v (repeat sz id)]
          v)))))

(defn checksum-fs [{:keys [files]}]
  (apply +
    (for [[id [addr length]] files
          pos (range addr (+ addr length))]
      (* id pos))))

(defn solve-part1 [input]
  (checksum (defrag input)))

(defn solve-part2 [input]
  (checksum-fs (defrag2 input)))

(comment
  (def real-input (parse-input (slurp "resources/day09.txt")))
  (count "0099811188827773336446555566")
  (left real-input)
  (apply + (map second ex))
  (apply + (map second real-input))
  (last real-input)
  (time (solve-part1 real-input))
  (time (solve-part2 real-input)))
