(ns taskapi.common.http
  (:use ring.util.response)
  (:require [compojure.route :as route]))

(defn response-or-not-found [body]
  (if (empty? body) 
    (route/not-found "404 not found") 
    (response body)))
