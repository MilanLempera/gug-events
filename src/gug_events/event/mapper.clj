(ns gug-events.event.mapper
  (:require [clj-time.core :as time]
            [clj-time.format :as time-format]))

(def iso-formater (time-format/formatters :date-time-no-ms))

(defn- get-date
  "return date object getter by key"
  [key]
  (fn [object]
    (time-format/parse iso-formater (get object key))))

(defn- get-create-date
  "return create date as date object"
  [event]
  ((get-date :created_at) event))

(defn- get-start-date
  "return create date as date object"
  [event]
  ((get-date :date_from) event))

(defn create-interval
  "Count days between publication a start"
  [event]
  (let [negative (time/after? (get-create-date event) (get-start-date event))
        interval (time/in-days
                   (time/interval
                     (if negative
                       (get-start-date event)
                       (get-create-date event))
                     (if negative
                       (get-create-date event)
                       (get-start-date event))))]

    ((if negative - +) interval)))

(defn event->map
  "Count days between publication a start"
  [event]
  (let [event-name (if (contains? event :event_name)
                     (:event_name event )
                     (get-in event [:event :event_name]))]

    (hash-map
      :name (or event-name "")
      :groups (map :name (get event :groups))
      :interval (create-interval event)
      :created-day (time/day (get-create-date event))
      :created-month (time/month (get-create-date event))
      :created (.toString (get-create-date event))
      :start-month (time/month (get-start-date event))
      :start (.toString (get-start-date event)))))




