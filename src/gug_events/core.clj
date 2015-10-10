(ns gug-events.core
  (:require [clojure.data.json :as json]
            [gug-events.event :as event]))

(defn -main [& args]

  (defn get-interval-filter
    [from, to]
    (fn [interval]
      (and
        (>= interval from)
        (< interval to)
        )))

  (def events
    (map event/transform-event
         (json/read-str
           (slurp (first args))
           :key-fn keyword)))

  (def events-without-extended
    (filter
      (fn [event]
        (not (re-matches #".*Extended.*" (get event :name)))
        )
      events))

  (defn map-by-interval
    [events interval]
    (map (fn [from to]
           (def filter-function
             (get-interval-filter from to))

           (hash-map
             (list from to)
             (count
               (filter
                 (fn [event]
                   (filter-function (get event :interval)))
                 events))))
         (first interval) (second interval))
    )

  (clojure.pprint/pprint
    (count events))


  (clojure.pprint/pprint
    (count events-without-extended))

  (clojure.pprint/pprint
    (map-by-interval
      events
      (list
        (list -500 0 0 0 0 0 0 14) (list 0 1 3 5 7 10 14 500))))

  (clojure.pprint/pprint
    (map-by-interval
      events-without-extended
      (list
        (list -500 0 0 0 0 0 0 14) (list 0 1 3 5 7 10 14 500)))))
