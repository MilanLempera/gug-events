;; gorilla-repl.fileformat = 1

;; **
;;; # Gorilla REPL
;;; 
;;; Welcome to gorilla :-)
;;; 
;;; Shift + enter evaluates code. Hit ctrl+g twice in quick succession or click the menu icon (upper-right corner) for more commands ...
;;; 
;;; It's a good habit to run each worksheet in its own namespace: feel free to use the declaration we've provided below if you'd like.
;; **

;; @@
(ns thriving-fern
  (:require [gorilla-plot.core :as plot]
            [gorilla-repl.table :as table]
            [clojure.data.json :as json]
            [gug-events.event.mapper :refer [event->map]]
            [gug-events.event.filter :as filter]
            [gug-events.event.transform :as transform]
            [gug-events.core :as m]))

(defn get-stats
  [events]

  {:count       (count events)
   :by-interval (transform/map-by-interval
                  events
                  (list
                    (list -500 0 7 14 21 28) (list 0 7 14 21 28 500)))
   :by-day      (transform/map-by-day
                  events)
   :by-month    (transform/map-by-month
                  events)
   })


(def events (map event->map
                 (json/read-str (slurp "resources/event_occurence_2016.json")
                                :key-fn keyword)))

(def events-without-extended (filter (complement filter/containsExtended?)
                                     events))
(def events-stats (get-stats events))
(def daily (:by-day events-stats))
(def by-interval (:by-interval events-stats))
(def by-month (:by-month events-stats))
(def events-count (:count events-stats))

(table/table-view (->> events
  (filter #(> 1 (:interval %)))))

2016
events-count
(plot/bar-chart (mapcat keys daily) (mapcat vals daily))
(plot/bar-chart (mapcat keys by-interval) (mapcat vals by-interval))
(plot/bar-chart (mapcat keys by-month) (mapcat vals by-month))

by-month




(def late-created (->> events
  (filter #(= (:start-month %) (:created-month %)))
  (filter #(not= 1 (:created-day %)))
  (map :groups)
     flatten))
  
(def go (frequencies late-created))

(count late-created)

(table/table-view (->> events
  (filter #(= (:start-month %) (:created-month %)))
  (filter #(not= 1 (:created-day %)))))

(def late-created-top (select-keys late-created-frequencies 
             (for [[k v] late-created-frequencies 
                   :when (> v 3)]
               k)))

(plot/bar-chart (keys late-created-top) (vals late-created-top))



;; @@

;; @@
(def events (map event->map
                 (json/read-str (slurp "resources/event_occurence_2015.json")
                                :key-fn keyword)))

(def events-without-extended (filter (complement filter/containsExtended?)
                                     events))
(def events-stats (get-stats events))
(def daily (:by-day events-stats))
(def by-interval (:by-interval events-stats))
(def by-month (:by-month events-stats))
(def events-count (:count events-stats))

2015
events-count
(plot/bar-chart (mapcat keys daily) (mapcat vals daily))
(plot/bar-chart (mapcat keys by-interval) (mapcat vals by-interval))
(plot/bar-chart (mapcat keys by-month) (mapcat vals by-month))

by-month



;; @@

;; @@
(def events (map event->map
                 (json/read-str (slurp "resources/event_occurence_2014.json")
                                :key-fn keyword)))

(def events-without-extended (filter (complement filter/containsExtended?)
                                     events))
(def events-stats (get-stats events))
(def daily (:by-day events-stats))
(def by-interval (:by-interval events-stats))
(def by-month (:by-month events-stats))
(def events-count (:count events-stats))

2014
events-count
(plot/bar-chart (mapcat keys daily) (mapcat vals daily))
(plot/bar-chart (mapcat keys by-interval) (mapcat vals by-interval))
(plot/bar-chart (mapcat keys by-month) (mapcat vals by-month))

by-month

;; @@

;; @@
(plot/bar-chart [2014 2015 2016] [134 248 298])
;; @@

;; @@



;; @@

;; @@

;; @@
