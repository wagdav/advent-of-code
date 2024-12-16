(ns aoc2024.day16
  (:require [clojure.string :as str]
            [aoc2024.search :as search]))

(def example-input "###############
#.......#....E#
#.#.###.#.###.#
#.....#.#...#.#
#.###.#####.#.#
#.#.#.......#.#
#.#.#####.###.#
#...........#.#
###.#.#####.#.#
#...#.....#.#.#
#.#.#.###.#.#.#
#.....#...#.#.#
#.###.#.#.#.#.#
#S..#.....#...#
###############")

(defn parse-input [input]
  (apply merge-with into
    (for [[i row] (map-indexed vector (str/split-lines input))
          [j c]   (map-indexed vector row)]
      (case c
        \. {:free #{[i j]}}
        \S {:free #{[i j]}
            :pos [i j]}
        \E {:free #{[i j]}
            :end [i j]}
        \# {}))))

(def directions {0 [0 1] 1 [1 0] 2 [0 -1] 3 [-1 0]})

(defn make-problem [input]
  (reify search/Problem
    (actions [this {:keys [free facing pos]}]
      (if (free (mapv + pos (directions facing)))
        [0 -1 1]
        [-1 1]))
    (goal? [this {:keys [pos end]}]
      (= pos end))
    (initial-state [this]
      (assoc input :facing 0))
    (result [this {:keys [facing] :as state} action]
      (if (zero? action)
        (update state :pos #(mapv + % (directions facing)))
        (update state :facing #(-> % (+ action) (mod 4)))))
    (step-cost [this state action]
      ({0 1, -1 1000, 1 1000} action))))

(defn solve-part1 [input]
  (:path-cost (search/uniform-cost (make-problem input))))

(defn dfs [problem limit]
  (let [start (search/initial-state problem)]
    (loop [res #{}
           explored {}
           frontier [(search/map->Node {:state start :actions [] :path [start] :path-cost 0})]]
      (if (seq frontier)
        (let [node (peek frontier)]
          (recur
            (if (search/goal? problem (:state node))
              (into res (map :pos (:path node)))
              res)
            (conj explored [(select-keys (:state node) [:pos :facing]) (:path-cost node)])
            (into (pop frontier)
                  (for [action (search/actions problem (:state node))
                        :let [c (search/child-node problem node action)
                              s (:state c)]
                        :when (and (<= (:path-cost c)
                                       (get explored (select-keys s [:pos :facing]) limit)))]
                    c))))
        (count res)))))

(defn solve-part2 [input]
  (let [p (make-problem input)
        min-cost (:path-cost (search/uniform-cost p))]
    (println min-cost)
    (dfs p min-cost)))

(comment
  (solve-part1 (parse-input example-input))
  (solve-part2 (parse-input example-input))
  (solve-part1 (parse-input (slurp "resources/day16.txt")))
  (time (solve-part2 (parse-input (slurp "resources/day16.txt")))))
