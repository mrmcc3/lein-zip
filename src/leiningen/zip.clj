(ns leiningen.zip
  (:require [clojure.java.io :as io]
            [leiningen.core.main :refer [info warn]]
            [leiningen.core.utils :as utils]
            [clojure.string :as s])
  (:import [java.io File]
           [java.util.zip ZipOutputStream ZipEntry]))

;; from Meikel Brandmeyer,
;; https://stackoverflow.com/questions/17965763/zip-a-file-in-clojure
(defmacro ^:private with-entry
  [zip entry-name & body]
  `(let [^ZipOutputStream zip# ~zip]
     (.putNextEntry zip# (ZipEntry. ~entry-name))
     ~@body
     (flush)
     (.closeEntry zip#)))

(defn- output-filename [n v]
  (str n "-" v ".zip"))

(defn out-path
  "Given a path p and a file, returns the entry path inside the ZIP archive.
  Path might be a filename (which will be identical to the filename for f).
  Things become more interesting if p is a tuple (i.e. a two-value vector): Here,
  the first value is the path to the file, but the second value is the destination
  inside the ZIP archive.

  If that second value (destintion) denotes a file, i.e. destination does not
  end with an /, that name will be the entry path, i.e. the file will be copied
  into the ZIP starting at top level.

  If the seconde item is a directory, behaviour is defined by the metadata to
  the tuple: If no meta data is given or :replace-path is false, the destination
  is just prepended to the filepath, so nesting is deeper. If :replace-path is true,
  the file is inserted directly under the destination with no further folders created."
  [p ^File f]
  (if-not (coll? p)
    (.getPath f)
    (let [entry (second p)]
      (if (s/ends-with? entry "/")
        (if (:replace-path (meta p))
          (str entry (.getName f))
          (str entry (.getPath f)))
        entry))))

(defn zip
  "Zips files from :zip in project.clj to target/project-version.zip"
  [{v :version n :name target-path :target-path paths :zip}]
  (let [target (doto (io/file target-path) utils/mkdirs)
        target-zip-file (io/file target (output-filename n v))]
    (info "About to create " (.getCanonicalPath target-zip-file))
    (with-open [intermediate-output-stream (io/output-stream target-zip-file)
                out (ZipOutputStream. intermediate-output-stream)]
      (doseq [p paths]
        (doseq [^File f (file-seq (io/file (if (coll? p) (first p) p)))]
          (if-not (.exists f)
            (warn (.getPath f) "does not exist")
            (if-not (.canRead f)
              (warn "Cannot read" (.getPath f))
              (when-not (.isDirectory f)
                (with-open [in (io/input-stream f)]
                  (let [entry (out-path p f)]
                    (with-entry out entry
                                (io/copy in out))
                    (info "zipped file" (.getPath f) "to" entry)))))))))))