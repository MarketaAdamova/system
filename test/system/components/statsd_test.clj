(ns system.components.statsd-test
  (:require
    [clj-statsd :as statsd]
    [system.components.statsd :refer [new-statsd]]
    [com.stuartsierra.component :as component]
    [clojure.test :refer [deftest is]]))

(def statsd-cmp (new-statsd "localhost" 8125 "my-system"))

(deftest start-statsd
  (alter-var-root #'statsd-cmp component/start)
  (is (= (:cfg statsd-cmp) (deref statsd/cfg)))
  (is (= "my-system" (:prefix (:cfg statsd-cmp))))
  (is (= 8125 (:port (:cfg statsd-cmp))))
  (alter-var-root #'statsd-cmp component/stop))

(deftest stop-statsd
  (alter-var-root #'statsd-cmp component/start)
  (alter-var-root #'statsd-cmp component/stop)
  (is (nil? (:cfg statsd-cmp))))
