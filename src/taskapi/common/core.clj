(ns taskapi.common.core)

(defn map-selected-vals [m f & ks]
  (let [submap (select-keys m ks)
        transformed (into {} (for [[k v] submap] [k (f v)]))]
    (merge m transformed)))

