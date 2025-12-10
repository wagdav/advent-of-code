(ns aoc2024.search
  (:require [clojure.data.priority-map :refer [priority-map priority-map-keyfn]]))

(defprotocol Problem
  (actions [this state])
  (goal? [this state])
  (initial-state [this])
  (result [this state action])
  (step-cost [this state action]))

; Russel&Norvig AI (Section 3.1.1 p 78.)
(defrecord Node [state actions path path-cost])

(defn make-node [state]
  (map->Node {:state state :actions [] :path [state] :path-cost 0}))

(defn child-node [problem parent action]
  (let [state (result problem (:state parent) action)]
    (map->Node
      {:state state
       :actions (conj (:actions parent) action)
       :path (conj (:path parent) state)
       :path-cost (+ (:path-cost parent)
                     (step-cost problem (:state parent) action))})))

(defn- expand [problem node]
  (for [action (actions problem (:state node))]
    (child-node problem node action)))

(defn uniform-cost-all [problem]
  (let [start (make-node (initial-state problem))]
    (loop [reached {}
           frontier (priority-map start (:path-cost start))]
      (when-let [[node cost] (peek frontier)]
        (if (goal? problem (:state node))
          (->> frontier
               (filter (fn [[n c]] (and (= cost c) (goal? problem (:state n)))))
               (map first))
          (let [children (for [child (expand problem node)
                               :let [s (:state child)
                                     c (:path-cost child)]
                               :when (or (not (reached s)) (<= c (reached s)))]
                           child)]
            (recur
              (into reached (for [c children] [(:state c) (:path-cost c)]))
              (into (pop frontier) (for [c children] [c (:path-cost c)])))))))))

(defn best-first [problem f]
  (let [start (make-node (initial-state problem))]
    (loop [explored #{}
           frontier (priority-map start (f start))]
      (when-let [[node cost] (peek frontier)]
        (if (goal? problem (:state node))
          node
          (recur
            (conj explored (:state node))
            (into (pop frontier) (for [child (expand problem node)
                                       :when (not (explored (:state child)))]
                                   [child (f child)]))))))))

(defn uniform-cost [problem]
  (best-first problem :path-cost))

(defn A* [problem h]
  (best-first problem (fn [^Node node] (+ (:path-cost node) (h node)))))

(defn breadth-first [problem]
  (let [start-state (initial-state problem)
        start-node  (map->Node {:state start-state :actions [] :path [start-state] :path-cost 0})]
    (loop [best nil, explored #{}, frontier [[start-state start-node]]]
      (if-let [[state node] (first frontier)]
        (if (goal? problem state)
          (recur
            (if best
              (min-key :path-cost best node)
              node)
            (conj explored state)
            (rest frontier))
          (let [children (for [action (actions problem state)
                               :let [c (child-node problem node action)
                                     s (:state c)]
                               :when (not (explored s))]
                           [s c])]
            (recur best (conj explored state) (into (rest frontier) children))))
        best))))

(defn depth-first [problem]
  (let [start-state (initial-state problem)
        start-node  (map->Node {:state start-state :actions [] :path [start-state] :path-cost 0})]
    (loop [best nil, explored #{}, frontier [[start-state start-node]]]
      (if-let [[state node] (peek frontier)]
        (if (goal? problem state)
          (recur
            (if best
              (min-key :path-cost best node)
              node)
            (conj explored state)
            (pop frontier))
          (let [children (for [action (actions problem state)
                               :let [c (child-node problem node action)
                                     s (:state c)]
                               :when (not (explored s))]
                           [s c])]
            (recur best (conj explored state) (into (pop frontier) children))))
        best))))
