(ns portal
  (:require [portal.api :as p]))

; Portal Visual inspector
(def p (p/open)) ; Open a new inspector
(add-tap #'p/submit)
