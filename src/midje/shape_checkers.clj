(ns midje.shape-checkers
  "Checkers that use structural-typing"
  (:use commons.clojure.core)
  (:require [such.immigration :as immigrate]
            [midje.checking.core :as checking.core]
            [midje.checking.checkers.defining :refer [checker defchecker]]
            [structural-typing.type :as type]
            [structural-typing.assist.oopsie :as oopsie]))

(def type-repo (-> type/empty-type-repo
                   (type/replace-success-handler (constantly true))
                   (type/replace-error-handler
                    #(checking.core/as-data-laden-falsehood {:notes (oopsie/explanations %)}))))

(immigrate/import-vars [structural-typing.type
                        ALL RANGE required-path requires])

(defchecker built-like [type-shorthand]
  (checker [actual]
    (type/built-like type-repo type-shorthand actual)))

(defchecker all-built-like [type-shorthand]
  (checker [actual]
    (type/all-built-like type-repo type-shorthand actual)))
