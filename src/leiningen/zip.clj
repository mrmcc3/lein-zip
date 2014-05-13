(ns leiningen.zip
  (:require [clojure.java.io :refer [input-stream copy]]
            [leiningen.core.main :refer [info]])
  (:import [java.io FileOutputStream]
           [java.util.zip ZipOutputStream ZipEntry]))

(defn zip
  "Zips files from :zip in project.clj
  to target/project-version.zip"
  [{v :version n :name fs :zip} & args]
  (with-open [out (-> (str "target/" n "-" v ".zip")
                      (FileOutputStream.)
                      (ZipOutputStream.))]
    (doseq [f fs]
      (with-open [in (input-stream f)]
        (.putNextEntry out (ZipEntry. f))
        (copy in out)
        (.closeEntry out)
        (info "zipped file" f)))))
