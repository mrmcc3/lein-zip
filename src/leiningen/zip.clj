(ns leiningen.zip
  (:require [clojure.java.io :as io]
            [leiningen.core.main :refer [info]])
  (:import [java.io FileOutputStream]
           [java.util.zip ZipOutputStream ZipEntry]))

(defn zip
  "Zips files from :zip in project.clj
  to target/project-version.zip"
  [{v :version n :name paths :zip}]
  (with-open [out (-> (str "target/" n "-" v ".zip")
                      (FileOutputStream.)
                      (ZipOutputStream.))]
    (doseq [p paths
            f (file-seq (io/file p))]
        (when-not (.isDirectory f)
          (with-open [in (io/input-stream f)]
            (.putNextEntry out (ZipEntry. (.getPath f)))
            (io/copy in out)
            (.closeEntry out)
            (info "zipped file" (.getPath f)))))))
