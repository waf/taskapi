(defproject taskapi "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [compojure "1.1.5"]
                 [korma "0.3.0-RC3"]
                 [lobos "1.0.0-beta1"]
                 [postgresql "9.0-801.jdbc4"]
                 [ring/ring-json "0.1.2"]]
  :plugins [[lein-ring "0.8.2"]]
  :ring {:handler taskapi.init/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.3"]]}})
