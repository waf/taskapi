(ns taskapi.api.task
  (:use compojure.core)
  (:use ring.util.response)
  (:use korma.core)
  (:use korma.db)
  (:use taskapi.entity.core)
  (:use taskapi.common.http)
  (:use taskapi.common.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.json :as json]))

;TODO: once we add more resources (more than just task) this can be massively generalized

(defn get-all-tasks []
  (select tasks))

(defn get-task [id]
  (select tasks
          (where {:id id})))

(defn add-task 
  ; by default, don't allow inserts to specify the id
  ([task] (insert tasks 
          (values (dissoc task :id))))
  ; if they specifically provide an id, use it
  ([task id] (insert tasks 
          (values (into task {:id id})))))

(defn update-task [task]
  (update tasks 
          (set-fields (dissoc task :id))
          (where {:id (:id task)})))

(defn add-or-update-with-id [task]
  (transaction
    (if (empty? (get-task (:id task)))
      (add-task task (:id task))
      (update-task task))))

(defn delete-task [id]
  (delete tasks (where {:id id})))

(defn parse-task [task]
  (map-selected-vals task #(Integer/parseInt %) :id :projects_id))

(defroutes task-routes
  (GET "/" [] 
       (response (get-all-tasks)))
  (GET ["/:id", :id #"[0-9]+"] [id] 
       (response-or-not-found (get-task (read-string id))))
  (POST "/" {task :params} 
       (response (add-task (parse-task task))))
  (PUT ["/:id", :id #"[0-9]+"] {task :params} 
       (response (add-or-update-with-id (parse-task task))))
  (DELETE ["/:id", :id #"[0-9]+"] [id]
       (response (delete-task (Integer/parseInt id)))))

