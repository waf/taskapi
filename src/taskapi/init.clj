(ns taskapi.init
  (:use compojure.core)
  (:use taskapi.route)
  (:use korma.db)
  (:require [compojure.handler :as handler]
            [taskapi.setup :as setup]
            [ring.middleware.json :as json]))

; database connection
(def db-info {:classname "org.postgresql.Driver"
              :subprotocol "postgresql"
              :user "task"
              :password "taskpass"
              :subname "//localhost:5432/task"})

; db data access library (korma) setup
(defdb db db-info)

; db schema creation (via lobos)
;(setup/schema-setup db-info)

; set up ring handler pipeline
(def app
  (-> (handler/api app-routes)
    (json/wrap-json-body)
    (json/wrap-json-response)))
