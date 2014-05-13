# lein-zip

Zips files

## Example Usage

Add the following to project.clj

```clojure
:zip ["file1" "resources/file2" "target/production.jar"]
```

Then

    $ lein zip

This will produce target/project-version.zip
