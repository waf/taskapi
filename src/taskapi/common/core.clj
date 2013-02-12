(ns taskapi.common.core)

; given a map m, a function f, and keys ks
; apply f to the values in m that correspond with ks
(defn map-selected-vals [m f & ks]
  (let [submap (select-keys m ks)
        transformed (into {} (for [[k v] submap] [k (f v)]))]
    (merge m transformed)))

