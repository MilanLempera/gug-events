(ns gug-events.event.transform
  (:require [gug-events.event.filter :as filter]))

(defn map-by-interval
  [events interval]
  (map
    (fn [from to]
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

(defn map-by-day
  [events]
  (map
    (fn [day]
      (hash-map
        day
        (count
          (filter
            (fn [event]
              (= (get event :created-day) day))
            events))))
    (range 1 32)))


