(ns leiningen.zip
  (:require [clojure.java.io :refer [input-stream copy file]]
            [leiningen.core.main :refer [info]])
  (:import [java.io FileOutputStream]
           [java.util.zip ZipOutputStream ZipEntry]))


(defn- copy-and-get-new-file [ {:keys [file-name file-folder] }   ]
  (let [old-file (file (str file-folder "/" file-name) )
        new-file (file file-name)  ]
    (copy old-file new-file)
    file-name))

(defn- find-file-name [f]
  (if (map? f)
    (copy-and-get-new-file f)
    f))


(defn zip
  "Zips files from :zip in project.clj
  to target/project-version.zip"
  [{v :version n :name fs :zip} & args]
  (with-open [out (-> (str "target/" n "-" v ".zip")
                      (FileOutputStream.)
                      (ZipOutputStream.))]
    (doseq [f fs]
      (let [file-to-zip (find-file-name f)]
        (with-open [in (input-stream file-to-zip)]
          (.putNextEntry out (ZipEntry. file-to-zip))
          (copy in out)
          (.closeEntry out)
          (info "zipped file" file-to-zip))))))
