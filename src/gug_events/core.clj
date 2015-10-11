(ns gug-events.core
  (:require [clojure.data.json :as json]
            [gug-events.event.mapper :refer [event-to-map]]
            [gug-events.event.filter :as filter]
            [gug-events.event.transform :as transform]
            [clojure.tools.cli :as cli])
  (:gen-class))

(def cli-options
  ;; An option with a required argument
  [["-f" "--file inputFile.json" "input file"
    :default "resources/events_2014-15.json"
    :validate [#(.exists (clojure.java.io/as-file %)) "Must be a file"]
    ]
   ;; A boolean option defaulting to nil
   ["-h" "--help"]])

(defn print-stats
  [events]
  (clojure.pprint/pprint
    (count events))


  (clojure.pprint/pprint
    (transform/map-by-interval
      events
      (list
        (list -500 0 0 0 0 0 0 14) (list 0 1 3 5 7 10 14 500))))

  (clojure.pprint/pprint
    (transform/map-by-day
      events)))

(defn -main [& args]

  (def options
    (cli/parse-opts args cli-options))

  (def events
    (map event-to-map
         (json/read-str
           (slurp (get-in options [:options :file]))
           :key-fn keyword)))

  (def events-without-extended
    (filter
      (fn [event]
        (not
          (filter/containsExtended? event)))
      events))

  (print-stats events)
  (print-stats events-without-extended))
