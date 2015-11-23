# lein-zip

minimal leiningen plugin that zips files

## Example Usage

[![Clojars Project](http://clojars.org/lein-zip/latest-version.svg)](http://clojars.org/lein-zip)

Add the following to project.clj

```clojure
:zip ["file1" "resources/file2" "target/production.jar" "dir1"]
```

Then

    $ lein zip

This will produce `target/{{project}}-{{version}}.zip`
