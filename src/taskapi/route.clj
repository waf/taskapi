(ns taskapi.route
  (:use compojure.core)
  (:use ring.util.response)
  (:use taskapi.task.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.json :as json]))

(defroutes app-routes
           (context "/tasks" [] task-routes)
           (GET "/" [] "Index")
           (route/not-found "Not Found"))
