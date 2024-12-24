(ns aoc2024.day24
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (let [[env ops] (str/split input #"\R\R")]
    {:env (into {} (for [[wire value] (map #(str/split % #": ") (str/split-lines env))]
                     [wire (if (= value "1") true false)]))
     :ops (for [[a op b _ res] (->> ops (str/split-lines) (map #(str/split % #" ")))]
            [a op b res])}))

(defn z-wires [ops]
  (->> ops
       (mapcat (fn [[a op b res]] [a b res]))
       set
       (filter #(str/starts-with? % "z"))))

(defn pass [{:keys [env ops]}]
  (reduce
    (fn [env [a op b dst]]
      (if (and (some? (env a)) (some? (env b)))
         (assoc env dst (case op
                          "AND" (and (env a) (env b))
                          "OR"  (or (env a) (env b))
                          "XOR" (distinct? (env a) (env b))))
         env))
    env
    ops))

(defn run [{:keys [env ops]}]
  (let [zs (z-wires ops)]
    (loop [env env]
      (if (every? #(some? (env %)) zs)
        env
        (recur (pass {:env env :ops ops}))))))

(defn to-decimal [bits]
  (Long/parseLong (str/join bits) 2))

(defn solve-part1 [input]
  (let [env (run input)]
    (to-decimal (for [k (reverse (sort (keys env)))
                      :when (str/starts-with? k "z")]
                  (if (env k) 1 0)))))

(defn graphviz [{:keys [ops]}]
  (println "digraph D {")
  (println " ordering=out;")
  (println "  subgraph cluster_z  {" (str/join "; " (for [i (range 45)] (format "z%02d" i))) "}")
  (doseq [[a op b res] ops]
    (let [res' (case res
                  "z27" "kcd"
                  "kcd" "z27"

                  "z23" "pfn"
                  "pfn" "z23"

                  "wkb" "tpk"
                  "tpk" "wkb"

                  "z07" "shj"
                  "shj" "z07"
                  res)]
      (when-not (str/starts-with? res' "z")
          (println " " res' (format "[label=%s%s];" res', op)))
      (println " " a "-> " res')
      (println " " b "-> " res')))
  (println "}"))


(defn solve-part2 [input])
; Solved visually. I generated a Graphviz diagram, displayed it with
;
;   xdot -f circo day24.dot
;
; Then, I looked for nodes which caused irregularities.

(comment
  (do
    (binding [*out* (clojure.java.io/writer "day24.dot")]
      (graphviz (parse-input (slurp "resources/day24.txt"))))))
