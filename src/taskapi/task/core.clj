(ns taskapi.task.core
  (:use compojure.core)
  (:use ring.util.response)
  (:use korma.core)
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

(defn add-task [task]
  (insert tasks 
          (values (dissoc task :id))))

(defn update-task [task]
  (update tasks 
          (set-fields task)
          (where {:id (:id task)})))

(defn parse-task [task]
  (map-selected-vals task #(Integer/parseInt %) :id :projects_id))

(defroutes task-routes
  (GET "/" [] 
       (response (get-all-tasks)))
  (GET ["/:id", :id #"[0-9]+"] [id] 
       (response-or-not-found (get-task (read-string id))))
  (POST "/" {task :params} 
       (response (add-task (parse-task task))))
  (PUT "/" {task :params} 
       (response (update-task (parse-task task)))))

