(ns aoc2024.day11)

(defn parse-input [input]
  (->> (re-seq #"-?\d+" input)
       (mapv parse-long)))

(defn splitable? [n]
  (even? (count (str n))))

(defn split [n]
  (let [as-string (str n)
        length (count as-string)
        half (quot length 2)]
    (map parse-long [(.substring as-string 0 half)
                     (.substring as-string half length)])))

(defn blink [stones]
  (mapcat
    (fn [stone]
        (cond
          (= stone 0)        [1]
          (splitable? stone) (split stone)
          :else              [(* stone 2024)]))
    stones))

(defn solve-part1 [input]
   (count (nth (iterate blink input) 25)))

(defn blink2 [stones]
  (merge-with +
    {1 (get stones 0 0)}
    (apply merge-with +
      (for [[stone amount] stones
            :when (splitable? stone)
            :let [[a b] (split stone)]]
        (merge-with + {a amount} {b amount})))
    (into {}
      (for [[stone amount] stones
            :when (and (not= stone 0)
                       (not (splitable? stone)))]
        [(* 2024 stone) amount]))))

(defn solve-part2 [input]
  (let [stone-map (into {} (map vector input (repeat 1)))
        blinks 75]
    (->> (nth (iterate blink2 stone-map) blinks)
         (vals)
         (apply +))))

(comment
  (time (solve-part1 (parse-input (slurp "resources/day11.txt"))))
  (time (solve-part2 (parse-input (slurp "resources/day11.txt")))))
