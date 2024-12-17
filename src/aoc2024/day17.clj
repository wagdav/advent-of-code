(ns aoc2024.day17
  (:require [clojure.string :as str]
            [clojure.math :refer [pow]]))

(def example-input "Register A: 729
Register B: 0
Register C: 0

Program: 0,1,5,4,3,0")

(defn parse-input [input]
  (let [[registers ops] (->> (re-seq #"-?\d+" input)
                             (mapv parse-long)
                             (split-at 3))]
    {:reg (into {} (map vector [:a :b :c] registers))
     :program (into [] ops)
     :out []
     :pc 0}))

(def ex (parse-input example-input))

(defn combo [{:keys [reg] :as state} v]
  (if (<= 0 v 3)
    v
    (case v
      4 (reg :a)
      5 (reg :b)
      6 (reg :c))))

(defn step [{:keys [pc reg program out] :as state}]
    (if (>= pc (count program))
      :halt
      (let [op (program pc)
            arg (program (inc pc))]
        (case op
          ;adv
          0 (-> state
                (assoc-in [:reg :a] (quot (reg :a) (int (pow 2 (combo state arg)))))
                (assoc :pc (+ pc 2)))
          ;bxl
          1 (-> state
                (assoc-in [:reg :b] (bit-xor (reg :b) arg))
                (assoc :pc (+ pc 2)))
          ;bst
          2 (-> state
                (assoc-in [:reg :b] (mod (combo state arg) 8))
                (assoc :pc (+ pc 2)))
          ;jnz
          3 (if (zero? (reg :a))
              (assoc state :pc (+ pc 2))
              (assoc state :pc arg))
          ; bxc
          4 (-> state
                (assoc-in [:reg :b] (bit-xor (reg :b) (reg :c)))
                (assoc :pc (+ pc 2)))
          ; out
          5 (-> state
                (update :out conj (mod (combo state arg) 8))
                (assoc :pc (+ pc 2)))
          ;bdv
          6 (-> state
                (assoc-in [:reg :b] (quot (reg :a) (int (pow 2 (combo state arg)))))
                (assoc :pc (+ pc 2)))
          ;cdv
          7 (-> state
                (assoc-in [:reg :c] (quot (reg :a) (int (pow 2 (combo state arg)))))
                (assoc :pc (+ pc 2)))))))

(defn run [input]
  (loop [state input]
     (let [state' (step state)]
       (if (= state' :halt)
         state
         (recur state')))))

(defn solve-part1 [input]
  (str/join "," (:out (run input))))

(defn solve-part2 [{:keys [program] :as input}]
  (loop [reg-a 33392450000004]
    (let [out (:out (run (assoc-in input [:reg :a] reg-a)))]
      (when (zero? (mod reg-a 10000))
        (println reg-a (count out)))
      (if (= program out)
        (do
          (println "part2" reg-a)
          reg-a)
        (recur (+ 10000000 reg-a))))))

(def quine "Register A: 2024
Register B: 0
Register C: 0

Program: 0,3,5,4,3,0")

(:out (run (assoc-in (parse-input quine) [:reg :a] 117440)))

(defn run-part2 [opts] ; > 23620000
  (solve-part2 (parse-input (slurp "resources/day17.txt"))))

(defn run-part2-ex [opts]
  (solve-part2 (parse-input quine)))
