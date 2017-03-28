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


(def events-raw (json/read-str (slurp "resources/event_occurence_2017-q1.json")
                                :key-fn keyword))


;; @@

;; @@
(defn prepare-user [user]
  { :email (:email user)
    :name (str (:first_name user) " " (:last_name user))})

(defn prepare-event [event]
  {
  	:name (get-in event [:event :event_name])
    :quest-count (get-in event [:event :guests_count])
    :garant (prepare-user (:user event))
    :groups (map :name (:groups event))
    :people (map prepare-user (:users event))
    
  })

(prepare-event (first events-raw))
;; @@

;; @@
(def events (map prepare-event events-raw))
;; @@

;; @@
(count events)
;; @@

;; @@
(defn average
  [numbers]
    (/ (apply + numbers) (count numbers)))

(def counts 
  (map :quest-count 
  (filter #(number? (:quest-count %)) 
          events)))

(frequencies  counts)
(apply min counts)
(apply max counts)
(average counts)

counts

(plot/histogram (filter #(< % 70) counts))
;; @@

;; @@
(->> events
     (filter #(number? (:quest-count %)))
     (filter #(> (:quest-count %) 25))
     (map #(select-keys % [:quest-count  :name :groups]))
     (sort-by :quest-count)
     reverse
     (map #(str (clojure.string/join ", " (:groups %)) " " (:name %) " - " (:quest-count %))))

;; @@

;; @@
(->> events
     (map #(get-in % [:garant :name]))
     frequencies
     (sort-by val)
     reverse)
;; @@

;; @@
(->> events
     (mapcat #(:people % ))
     (map :name)
     frequencies
     (sort-by val)
     reverse)
;; @@

;; @@

;; @@
