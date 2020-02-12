(ns leiningen.zip
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [leiningen.zip :as zip]))

#_(def $project {:version     "1.2.3"
               :name        "somename"
               :target-path "target/"
               :zip         ["test/leiningen/zip.clj"
                             ["test/leiningen/zip.clj" "dingdong.clj"]
                             ["test/leiningen/zip.clj" "dingdong/"]
                             ^{:replace-path false} ["test/leiningen/zip.clj" "razonk/"]
                             ^:replace-path ["src/leiningen/zip.clj" "razonk/"]
                             ^:replace-path ["src/leiningen/zip.clj" "razonk.clj"]]})

(deftest test-out-path
  (let [f (io/file "test/leiningen/zip.clj")]
    (is (= "test/leiningen/zip.clj"
           (zip/out-path "test/leiningen/zip.clj"
                     f)))
    (is (= "dingdong.clj"
           (zip/out-path ["test/leiningen/zip.clj" "dingdong.clj"]
                     f)))
    (is (= "dingdong/test/leiningen/zip.clj"
           (zip/out-path ["test/leiningen/zip.clj" "dingdong/"]
                     f)))
    (is (= "dingdong/test/leiningen/zip.clj"
           (zip/out-path ^{:replace-path false} ["test/leiningen/zip.clj" "dingdong/"]
                     f)))
    (is (= "dingdong/zip.clj"
           (zip/out-path ^:replace-path ["src/leiningen/zip.clj" "dingdong/"]
                     f)))))
