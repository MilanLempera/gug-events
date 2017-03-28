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
;; =>
;;; {"type":"vega","content":{"axes":[{"scale":"x","type":"x"},{"scale":"y","type":"y"}],"scales":[{"name":"x","type":"ordinal","range":"width","domain":{"data":"7a30f77f-f772-4c09-8b29-213b79b078c6","field":"data.x"}},{"name":"y","range":"height","nice":true,"domain":{"data":"7a30f77f-f772-4c09-8b29-213b79b078c6","field":"data.y"}}],"marks":[{"type":"rect","from":{"data":"7a30f77f-f772-4c09-8b29-213b79b078c6"},"properties":{"enter":{"y":{"scale":"y","field":"data.y"},"width":{"offset":-1,"scale":"x","band":true},"x":{"scale":"x","field":"data.x"},"y2":{"scale":"y","value":0}},"update":{"fill":{"value":"steelblue"},"opacity":{"value":1}},"hover":{"fill":{"value":"#FF29D2"}}}}],"data":[{"name":"7a30f77f-f772-4c09-8b29-213b79b078c6","values":[{"x":"GBG Brno","y":5},{"x":"GDG Prague","y":5},{"x":"GDG ČVUT","y":11},{"x":"GBG Prague","y":5},{"x":"GEG Sušice","y":4},{"x":"GEG Praha","y":11}]}],"width":400,"height":247.2187957763672,"padding":{"bottom":40,"top":10,"right":10,"left":55}},"value":"#gorilla_repl.vega.VegaView{:content {:axes [{:scale \"x\", :type \"x\"} {:scale \"y\", :type \"y\"}], :scales [{:name \"x\", :type \"ordinal\", :range \"width\", :domain {:data \"7a30f77f-f772-4c09-8b29-213b79b078c6\", :field \"data.x\"}} {:name \"y\", :range \"height\", :nice true, :domain {:data \"7a30f77f-f772-4c09-8b29-213b79b078c6\", :field \"data.y\"}}], :marks [{:type \"rect\", :from {:data \"7a30f77f-f772-4c09-8b29-213b79b078c6\"}, :properties {:enter {:y {:scale \"y\", :field \"data.y\"}, :width {:offset -1, :scale \"x\", :band true}, :x {:scale \"x\", :field \"data.x\"}, :y2 {:scale \"y\", :value 0}}, :update {:fill {:value \"steelblue\"}, :opacity {:value 1}}, :hover {:fill {:value \"#FF29D2\"}}}}], :data [{:name \"7a30f77f-f772-4c09-8b29-213b79b078c6\", :values ({:x \"GBG Brno\", :y 5} {:x \"GDG Prague\", :y 5} {:x \"GDG ČVUT\", :y 11} {:x \"GBG Prague\", :y 5} {:x \"GEG Sušice\", :y 4} {:x \"GEG Praha\", :y 11})}], :width 400, :height 247.2188, :padding {:bottom 40, :top 10, :right 10, :left 55}}}"}
;; <=

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
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-unkown'>18</span>","value":"18"}],"value":"[1 18]"}],"value":"{1 18}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-unkown'>19</span>","value":"19"}],"value":"[2 19]"}],"value":"{2 19}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"},{"type":"html","content":"<span class='clj-unkown'>37</span>","value":"37"}],"value":"[3 37]"}],"value":"{3 37}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>4</span>","value":"4"},{"type":"html","content":"<span class='clj-unkown'>21</span>","value":"21"}],"value":"[4 21]"}],"value":"{4 21}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>5</span>","value":"5"},{"type":"html","content":"<span class='clj-unkown'>31</span>","value":"31"}],"value":"[5 31]"}],"value":"{5 31}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>6</span>","value":"6"},{"type":"html","content":"<span class='clj-unkown'>24</span>","value":"24"}],"value":"[6 24]"}],"value":"{6 24}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>7</span>","value":"7"},{"type":"html","content":"<span class='clj-unkown'>11</span>","value":"11"}],"value":"[7 11]"}],"value":"{7 11}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>8</span>","value":"8"},{"type":"html","content":"<span class='clj-unkown'>3</span>","value":"3"}],"value":"[8 3]"}],"value":"{8 3}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>9</span>","value":"9"},{"type":"html","content":"<span class='clj-unkown'>17</span>","value":"17"}],"value":"[9 17]"}],"value":"{9 17}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>10</span>","value":"10"},{"type":"html","content":"<span class='clj-unkown'>23</span>","value":"23"}],"value":"[10 23]"}],"value":"{10 23}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>11</span>","value":"11"},{"type":"html","content":"<span class='clj-unkown'>25</span>","value":"25"}],"value":"[11 25]"}],"value":"{11 25}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>12</span>","value":"12"},{"type":"html","content":"<span class='clj-unkown'>19</span>","value":"19"}],"value":"[12 19]"}],"value":"{12 19}"}],"value":"({1 18} {2 19} {3 37} {4 21} {5 31} {6 24} {7 11} {8 3} {9 17} {10 23} {11 25} {12 19})"}
;; <=

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
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-unkown'>8</span>","value":"8"}],"value":"[1 8]"}],"value":"{1 8}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-unkown'>8</span>","value":"8"}],"value":"[2 8]"}],"value":"{2 8}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"},{"type":"html","content":"<span class='clj-unkown'>13</span>","value":"13"}],"value":"[3 13]"}],"value":"{3 13}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>4</span>","value":"4"},{"type":"html","content":"<span class='clj-unkown'>12</span>","value":"12"}],"value":"[4 12]"}],"value":"{4 12}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>5</span>","value":"5"},{"type":"html","content":"<span class='clj-unkown'>11</span>","value":"11"}],"value":"[5 11]"}],"value":"{5 11}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>6</span>","value":"6"},{"type":"html","content":"<span class='clj-unkown'>16</span>","value":"16"}],"value":"[6 16]"}],"value":"{6 16}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>7</span>","value":"7"},{"type":"html","content":"<span class='clj-unkown'>6</span>","value":"6"}],"value":"[7 6]"}],"value":"{7 6}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>8</span>","value":"8"},{"type":"html","content":"<span class='clj-unkown'>4</span>","value":"4"}],"value":"[8 4]"}],"value":"{8 4}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>9</span>","value":"9"},{"type":"html","content":"<span class='clj-unkown'>10</span>","value":"10"}],"value":"[9 10]"}],"value":"{9 10}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>10</span>","value":"10"},{"type":"html","content":"<span class='clj-unkown'>19</span>","value":"19"}],"value":"[10 19]"}],"value":"{10 19}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>11</span>","value":"11"},{"type":"html","content":"<span class='clj-unkown'>15</span>","value":"15"}],"value":"[11 15]"}],"value":"{11 15}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>12</span>","value":"12"},{"type":"html","content":"<span class='clj-unkown'>12</span>","value":"12"}],"value":"[12 12]"}],"value":"{12 12}"}],"value":"({1 8} {2 8} {3 13} {4 12} {5 11} {6 16} {7 6} {8 4} {9 10} {10 19} {11 15} {12 12})"}
;; <=

;; @@
(plot/bar-chart [2014 2015 2016] [134 248 298])
;; @@
;; =>
;;; {"type":"vega","content":{"axes":[{"scale":"x","type":"x"},{"scale":"y","type":"y"}],"scales":[{"name":"x","type":"ordinal","range":"width","domain":{"data":"de8e75e9-2e11-4a34-af88-1b0a66a4fe3a","field":"data.x"}},{"name":"y","range":"height","nice":true,"domain":{"data":"de8e75e9-2e11-4a34-af88-1b0a66a4fe3a","field":"data.y"}}],"marks":[{"type":"rect","from":{"data":"de8e75e9-2e11-4a34-af88-1b0a66a4fe3a"},"properties":{"enter":{"y":{"scale":"y","field":"data.y"},"width":{"offset":-1,"scale":"x","band":true},"x":{"scale":"x","field":"data.x"},"y2":{"scale":"y","value":0}},"update":{"fill":{"value":"steelblue"},"opacity":{"value":1}},"hover":{"fill":{"value":"#FF29D2"}}}}],"data":[{"name":"de8e75e9-2e11-4a34-af88-1b0a66a4fe3a","values":[{"x":2014,"y":134},{"x":2015,"y":248},{"x":2016,"y":298}]}],"width":400,"height":247.2187957763672,"padding":{"bottom":40,"top":10,"right":10,"left":55}},"value":"#gorilla_repl.vega.VegaView{:content {:axes [{:scale \"x\", :type \"x\"} {:scale \"y\", :type \"y\"}], :scales [{:name \"x\", :type \"ordinal\", :range \"width\", :domain {:data \"de8e75e9-2e11-4a34-af88-1b0a66a4fe3a\", :field \"data.x\"}} {:name \"y\", :range \"height\", :nice true, :domain {:data \"de8e75e9-2e11-4a34-af88-1b0a66a4fe3a\", :field \"data.y\"}}], :marks [{:type \"rect\", :from {:data \"de8e75e9-2e11-4a34-af88-1b0a66a4fe3a\"}, :properties {:enter {:y {:scale \"y\", :field \"data.y\"}, :width {:offset -1, :scale \"x\", :band true}, :x {:scale \"x\", :field \"data.x\"}, :y2 {:scale \"y\", :value 0}}, :update {:fill {:value \"steelblue\"}, :opacity {:value 1}}, :hover {:fill {:value \"#FF29D2\"}}}}], :data [{:name \"de8e75e9-2e11-4a34-af88-1b0a66a4fe3a\", :values ({:x 2014, :y 134} {:x 2015, :y 248} {:x 2016, :y 298})}], :width 400, :height 247.2188, :padding {:bottom 40, :top 10, :right 10, :left 55}}}"}
;; <=

;; @@



;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>72</span>","value":"72"}
;; <=

;; @@

;; @@
