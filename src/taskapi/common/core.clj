(ns taskapi.common.core)

; given a map m, a function f, and keys ks
; apply f to the values in m that correspond with ks
(defn map-selected-vals [m f & ks]
  (reduce #(update-in %1 [%2] f) m ks))

