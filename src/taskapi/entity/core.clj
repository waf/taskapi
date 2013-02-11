(ns taskapi.entity.core
  (:use korma.core)
  (:use korma.db)
  )

(defentity users)
(defentity roles)
(defentity activities)

(defentity tasks
           (has-many activities))
(defentity projects
           (has-many tasks))
(defentity clients
           (has-many projects))
