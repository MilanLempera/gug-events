(ns gug-events.event.filter)

(defn create-interval-filter
  [from, to]
  (fn [interval]
    (and
      (>= interval from)
      (< interval to)
      )))

(defn containsExtended? [event]
  (re-matches #".*Extended.*" (get event :name)))
