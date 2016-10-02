(ns system.components.statsd
  (:require
    [clj-statsd :as statsd]
    [com.stuartsierra.component :as component]))

(defrecord StatsD [host port prefix cfg]
  component/Lifecycle
  (start [component]
    (if cfg
      component
      (let [cfg (statsd/setup host port :prefix prefix)]
        ;; stats starts to be collected when statsd/cfg atom is present
        (assoc component :cfg cfg))))
  (stop [component]
    (let [cfg (reset! statsd/cfg nil)]
      (assoc component :cfg cfg))))

(defn new-statsd
  ([host port]
   (new-statsd host port nil))
  ([host port prefix]
   (map->StatsD {:port port :host host :prefix prefix})))