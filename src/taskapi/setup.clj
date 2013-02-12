(ns taskapi.setup
  (:use lobos.core)
  (:use lobos.schema)
  (:use lobos.connectivity))

(def users
  (table :users
         (integer :id :unique :auto-inc :primary-key)
         (varchar :username 255 :not-null)))

(def activities
  (table :activities
         (integer :id :unique :auto-inc :primary-key)
         (varchar :name 255 :not-null)
         (integer :tasks_id :not-null [:refer :tasks :id])))

(def tasks
  (table :tasks
         (integer :id :unique :auto-inc :primary-key)
         (varchar :name 255 :not-null)
         (integer :projects_id :not-null [:refer :projects :id])))

(def projects
  (table :projects
         (integer :id :unique :auto-inc :primary-key)
         (varchar :name 255 :not-null)
         (integer :clients_id :not-null [:refer :clients :id])))

(def clients
  (table :clients
         (integer :id :unique :auto-inc :primary-key)
         (varchar :name 255 :not-null)))

;TODO add role lookup table/column
(def roles
  (table :roles
         (integer :users_id :not-null [:refer :users :id])
         (integer :projects_id [:refer :projects :id])))

(defn schema-setup [db-info]
  (with-connection db-info
                   (do 
                     (create users)
                     (create clients)
                     (create projects)
                     (create tasks)
                     (create activities)
                     (create roles))))
