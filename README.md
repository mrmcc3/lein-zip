# lein-zip

Zips files

## Usage

Add the following to project.clj

```clojure
:zip ["file1" "resources/file2" "target/production.jar"]
```

    $ lein zip

This will produce target/project-version.zip
