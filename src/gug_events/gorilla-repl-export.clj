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
            [gug-events.core :as m]
            [dk.ative.docjure.spreadsheet :as spreadsheet]))


(def events-raw (json/read-str (slurp "resources/event_occurence_2017.json")
                                :key-fn keyword))

;; @@

;; @@
(defn prepare-user [user]
  { :email (:email user)
    :name (str (:first_name user) " " (:last_name user))})

(defn prepare-event [event]
  {
  	:name (get-in event [:event :event_name])
    :date	(get-in event [:event :date_from])
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
(def events-table-data 
  (->> events
     (map #(select-keys % [:date :quest-count  :name :groups]))
     (sort-by :quest-count)
     reverse
     (map #(list (:date %) 
                 (:name %) 
                 (clojure.string/join ", " (:groups %)) 
                 (:quest-count %)))))


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

;; Create a spreadsheet and save it
(let [wb (spreadsheet/create-workbook "events"
                          events-table-data)]
  (spreadsheet/save-workbook! "events.xlsx" wb))
;; @@

;; @@

;; @@
