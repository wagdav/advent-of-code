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
                (assoc-in [:reg :a] (bit-shift-right (reg :a) (combo state arg)))
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
                (assoc-in [:reg :b] (bit-shift-right (reg :a) (combo state arg)))
                (assoc :pc (+ pc 2)))
          ;cdv
          7 (-> state
                (assoc-in [:reg :c] (bit-shift-right (reg :a) (combo state arg)))
                (assoc :pc (+ pc 2)))))))

(defn asm [{:keys [program]}]
  (let [ops [:adv :bxl :bst :jnz :bxc :out :bdv :cdv]
        combo #(if (<= 0 % 3) % ([:a :b :c] (- % 4)))
        comment [":a = :a / 2^c%d"
                 ":b = xor(:b, %d)"
                 ":b = mod(c%d, 8)"
                 "jnz(:a, %d)"
                 ":b = xor(:b, :c)"
                 "out(c%d mod 8)"
                 ":b = :a / 2^c%d"
                 ":c = :a / 2^c%d"]]
    (doseq [[i [op arg]] (map-indexed vector (partition 2 program))]
      (println i " " (ops op) arg ";" (format (get comment op "") arg)))))

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

(defn round [a]
  (mod (->> (mod a 8)
            (bit-xor 1)
            (bit-shift-right a)
            (bit-xor a)
            (+ 4))
       8))

(defn prog [a]
  (loop [out [], a a]
    (if (zero? a)
      out
      (recur (conj out (round a))
             (quot a 8)))))

(defn prog-asm [input a]
  (:out (run (assoc-in input [:reg :a] a))))

(defn prog' [a]
  (prog-asm real a))

(for [i (range 8)]
  (prog' i))

(for [i (range 8)]
  (prog' (bit-or (bit-shift-left 4 3) (bit-shift-left i 0))))

(for [i (range 8)]
  (prog' (bit-or (bit-shift-left 4 6) (bit-shift-left 5 3) (bit-shift-left i 0))))

(for [i (range 8)]
  (prog' (bit-or (bit-shift-left 4 9) (bit-shift-left 5 6) (bit-shift-left 3 2) (bit-shift-left i 0))))

(prog' 6)
(def expected-output [2 4 1 1 7 5 1 5 4 0 5 5 0 3 3 0])

(prog 64854237)
; Program: 2,4,1,1,7,5,1,5,4,0,5,5,0,3,3,0

(defn run-part2 [opts] ; > 23620000
  (solve-part2 (parse-input (slurp "resources/day17.txt"))))

(comment
  (:out (run (parse-input (slurp "resources/day17.txt")))) ; [4 1 7 6 4 1 0 2 7]
  (:program (parse-input (slurp "resources/day17.txt")))

  (def real (parse-input (slurp "resources/day17.txt")))

  (let [real (parse-input (slurp "resources/day17.txt"))
        reg-a 5000000000000000
        out1 (:out (run (assoc-in real [:reg :a] reg-a)))
        out2 (prog reg-a)]
    (= out1 out2))

  (dotimes [n 8]
    (println (bit-xor n 5 1))))
