(ns aoc2024.prepare
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn aoc-namespace [year day]
  (format "aoc%04d.day%02d" year day))

(def src-template
  (str
    "(ns %s\n"
    "  (:require [clojure.string :as str]))\n"
    "\n"
    "(defn parse-input [input]\n"
    "  (->> (re-seq #\"-?\\d+\" input)\n"
    "       (mapv parse-long)))\n"
    "\n"
    "(defn solve-part1 [input])\n"
    "\n"
    "(defn solve-part2 [input])\n"))

(def test-template
  (str
    "(ns %s-test\n"
    "  (:require [clojure.test :refer [deftest is testing]]\n"
    "            [%s :refer [parse-input solve-part1 solve-part2]]\n"
    "            [clojure.java.io :as io]))\n"
    "\n"
    "(def example-input \"\")\n"
    "\n"
    "(deftest works\n"
    "  (testing \"with example input\"\n"
    "    (is (nil? (solve-part1 (parse-input example-input))))\n"
    "    (is (nil? (solve-part2 (parse-input example-input)))))\n"
    "\n"
    "  (testing \"with real input\"\n"
    "    (let [input (parse-input (slurp (io/resource \"day%02d.txt\")))]\n"
    "      (is (nil? (solve-part1 input)))\n"
    "      (is (nil? (solve-part2 input))))))\n"))

(defn src-str [year day]
  (format src-template (aoc-namespace year day)))

(defn test-str [year day]
  (let [ns-name (aoc-namespace year day)]
    (format test-template ns-name ns-name day)))

(defn project-file [& parts]
  (str/join "/" parts))

(defn make-project
  ([year]
   (make-project year (range 1 26)))

  ([year days]
   (let [aoc (format "aoc%04d" year)]
    (partition 2
      (flatten
        (for [day days]
          [(project-file "src" aoc (format "day%02d.clj" day))
           (src-str year day)
           (project-file "test" aoc (format "day%02d_test.clj" day))
           (test-str year day)
           (project-file "resources" (format "day%02d.txt" day))
           ""]))))))

(defn write-project [project base-dir]
  (doseq [[path content] project]
    (let [full-path (str/join "/" [base-dir path])]
      (println "Writing" full-path)
      (with-open [out (io/writer full-path)]
        (.write out content)))))

(comment
  (print (src-str 2022 1))
  (print (test-str 2022 5))
  (take 3 (make-project 2022))
  (write-project (make-project 2025 [9 10 11 12]) "/home/dwagner/projects/advent-of-code/"))
