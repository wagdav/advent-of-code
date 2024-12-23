(ns aoc2024.day23-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc2024.day23 :refer [parse-input solve-part1 solve-part2]]
            [clojure.java.io :as io]))

(def example-input "kh-tc
qp-kh
de-cg
ka-co
yn-aq
qp-ub
cg-tb
vc-aq
tb-ka
wh-tc
yn-cg
kh-ub
ta-co
de-co
tc-td
tb-wq
wh-td
ta-ka
td-qp
aq-cg
wq-ub
ub-vc
de-ta
wq-aq
wq-vc
wh-yn
ka-de
kh-ta
co-tc
wh-qp
tb-vc
td-yn")

(deftest works
  (testing "with example input"
    (is (= 7 (solve-part1 (parse-input example-input))))
    (is (= "co,de,ka,ta" (solve-part2 (parse-input example-input)))))

  (testing "with real input"
    (let [input (parse-input (slurp (io/resource "day23.txt")))]
      ;(is (= 1485 (solve-part1 input))) ; SLOW
      (= "cc,dz,ea,hj,if,it,kf,qo,sk,ug,ut,uv,wh" (solve-part2 input)))))
