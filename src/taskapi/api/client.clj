(ns taskapi.api.client
  (:use compojure.core)
  (:use ring.util.response)
  (:use korma.core)
  (:use korma.db)
  (:use taskapi.entity.core)
  (:use taskapi.common.http)
  (:use taskapi.common.core)
  (:use taskapi.common.api)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.json :as json]))

(defn parse-client [client]
  (map-selected-vals client #(Integer/parseInt %) :id))

(defroutes client-routes
  (GET "/" []
       (response (get-all clients)))
  (GET ["/:id", :id #"[0-9]+"] [id]
       (response-or-not-found (get-by-id clients (read-string id))))
  (POST "/" {client :params}
       (response (add-entity clients (parse-client client))))
  (PUT ["/:id", :id #"[0-9]+"] {client :params}
       (response (add-or-update clients (parse-client client))))
  (DELETE ["/:id", :id #"[0-9]+"] [id]
       (response (delete-entity clients (Integer/parseInt id)))))
