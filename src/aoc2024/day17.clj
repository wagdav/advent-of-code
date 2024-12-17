(ns aoc2024.day17
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (let [[registers ops] (->> (re-seq #"-?\d+" input)
                             (mapv parse-long)
                             (split-at 3))]
    {:reg (into {} (map vector [:a :b :c] registers))
     :program (into [] ops)
     :out []
     :pc 0}))

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

(defn run
  ([input reg-a]
   (run (assoc-in input [:reg :a] reg-a)))
  ([input]
   (loop [state input]
     (let [state' (step state)]
       (if (= state' :halt)
         (:out state)
         (recur state'))))))

(defn solve-part1 [input]
  (str/join "," (run input)))

(defn match-output [prog a expected]
  (first
    ; one output is determined by (7 + 3) bits
    (for [i (range (bit-shift-left 1 10))
          :let [a' (bit-or (bit-shift-left a 3) i)
                out' (prog a')
                diff (- (count expected) (count out'))]
          :when (= out' (drop diff expected))]
     [a' out'])))

(defn solve-part2 [input]
  (let [prog #(run input %)
        expected (:program input)]
    (loop [a 0]
      (let [[a' out'] (match-output prog a expected)]
        (if (= out' expected)
          a'
          (recur a'))))))

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

; My input program decompiled
(defn round [a]
  (mod (->> (mod a 8)   ;-----|
            (bit-xor 1) ;     v   shift by a 3 bit number, that is by maximum 7
            (bit-shift-right a)
            (bit-xor a)         ; the 3-bit result is influenced by 10-bits
            (+ 4))              ;            aa aaaa aaaa
       8))                      ;                     ***
