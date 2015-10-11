(ns gug-events.core
  (:require [clojure.data.json :as json]
            [gug-events.mapper :as event]))

(defn loadEvents
  "Load events from file"
  [fileName]
  )

(clojure.pprint/pprint
  (map (fn [from to]
         (hash-map
           (list from to)
           (count
             (filter
               (fn [event]
                 (and
                   (>= (get event :interval) from)
                   (< (get event :interval) to)
                   (not (re-matches #".*Extended.*" (get event :name)))
                   ))
               (map event/event-to-map
                    (loadEvents "resources/events.json"))))))
       (list -500 0 0 0 0 0 0 14) (list 0 1 3 5 7 10 14 500)))


(clojure.pprint/pprint
  (map (fn [day]
         (hash-map
           day
           (count
             (filter
               (fn [event]
                 (= (get event :created-day) day))
               (map event/event-to-map
                    (loadEvents "resources/events.json"))))))
       (range 0 31)))


