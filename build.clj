(ns build
  (:refer-clojure :exclude [test])
  (:require [clojure.tools.build.api :as b]))

(defn test
  "Run all the tests."
  [opts]
  (let [basis (b/create-basis {:aliases [:test]})
        cmds  (b/java-command
                {:basis      basis
                 :main      'clojure.main
                 :main-args ["-m" "cognitect.test-runner"]})
        {:keys [exit]} (b/process cmds)]
    (when-not (zero? exit) (throw (ex-info "Tests failed" {}))
      opts)))

(defn lint
  "Lint source files using clj-kondo."
  [opts]
  (let [lint-options (merge {:lint ["src" "test"]
                             :config
                             {:linters
                               {:unresolved-symbol
                                 {:exclude '[(clojure.test/is [match?])]}}}}
                            opts)
        kondo-run!   (requiring-resolve 'clj-kondo.core/run!)
        kondo-print! (requiring-resolve 'clj-kondo.core/print!)
        results      (kondo-run! lint-options)]
      (kondo-print! results)
      (when (pos? (get-in results [:summary :errors] 0))
        (println ": clj-kondo found errors 😢")
        (System/exit -1))
      (println "clj-kondo approves ☺️")))
