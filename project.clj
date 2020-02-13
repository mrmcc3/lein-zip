(defproject gorillalabs/lein-zip "0.1.1"
  :description "Zips files"
  :url "https://github.com/gorillalabs/lein-zip"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :eval-in-leiningen true
  :plugins [[com.roomkey/lein-v "7.0.0"]]

  :middleware [leiningen.v/version-from-scm
               leiningen.v/dependency-version-from-scm
               leiningen.v/add-workspace-data]
  :scm {:name "git"
        :url  "https://github.com/gorillalabs/lein-zip"}

  ;; make sure you have your ~/.lein/credentials.clj.gpg setup correctly

  :deploy-repositories [["releases" :clojars]]
  :release-tasks [["vcs" "assert-committed"]
                  ["v" "update"]
                  ["deploy" "clojars"]
                  ["vcs" "push"]])
