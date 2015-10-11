(ns gug-events.event.transform
  (:require [gug-events.event.filter :as filter]))

(defn map-by-interval
  [events interval]
  (map (fn [from to]
         (def filter-function
           (filter/create-interval-filter from to))

         (hash-map
           (list from to)
           (count
             (filter
               (fn [event]
                 (filter-function (get event :interval)))
               events))))
       (first interval) (second interval)))
