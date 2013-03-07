(ns taskapi.common.api
  (:use korma.core)
  (:use korma.db))

(defn get-all [ents]
  (select ents))

(defn get-by-id [ents id]
  (select ents
          (where {:id id})))

(defn add-entity
  ; by default, don't allow inserts to specify the id
  ([ents e] (insert ents
              (values (dissoc e :id))))
  ; if they specifically provide an id, use it
  ([ents e id] (insert ents
              (values (into e {:id id})))))

(defn update-entity [ents e]
  (update ents
          (set-fields (dissoc e :id))
          (where {:id (:id e)})))

(defn add-or-update [ents e]
  (transaction
    (if (empty? (get-by-id ents (:id e)))
      (add-entity ents e (:id e))
      (update-entity ents e))))

(defn delete-entity [ents id]
  (delete ents (where {:id id})))
