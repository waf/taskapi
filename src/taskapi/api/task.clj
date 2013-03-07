(ns taskapi.api.task                                                                                         
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
                                                                                                             
(defn parse-task [task]                                                                                      
  (map-selected-vals task #(Integer/parseInt %) :id :projects_id))                                           
                                                                                                             
(defroutes task-routes                                                                                       
  (GET "/" []                                                                                                
       (response (get-all tasks)))                                                                           
  (GET ["/:id", :id #"[0-9]+"] [id]                                                                          
       (response-or-not-found (get-by-id tasks (read-string id))))                                           
  (POST "/" {task :params}                                                                                   
       (response (add-entity tasks (parse-task task))))                                                      
  (PUT ["/:id", :id #"[0-9]+"] {task :params}                                                                
       (response (add-or-update tasks (parse-task task))))                                                   
  (DELETE ["/:id", :id #"[0-9]+"] [id]                                                                       
       (response (delete-entity tasks (Integer/parseInt id)))))
