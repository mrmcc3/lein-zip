# lein-zip

Leiningen plugin that zips files

## Example Usage

[![Clojars Project](http://clojars.org/gorillalabs/lein-zip/latest-version.svg)](http://clojars.org/gorillalabs/lein-zip)

Add the following to project.clj

```clojure
:zip [^:replace-path ["target/uber.jar" "lib/"]
      "resources/file2"
      "dir1"
      ["dir2/" "dir3/"]
      ^:replace-path["dir2/" "dir4/"]]
```

You can give a vector of files or source/destination tuples. The files will be added to the
zip with their path. Directories will include all files (but not recursively). If given a
src/dest-tuple, the dest will be set or prepended to the path dependent on the :replace-with metadata. If replace-with is set, the original path will be replaced with the new path, else the destination-path will prepend the original path.

Then

    $ lein zip

This will produce `target/{{project}}-{{version}}.zip`
