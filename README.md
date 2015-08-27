# lein-zip

minimal leiningen plugin that zips files

## Example Usage

```clojure
[lein-zip "0.1.0"]
```

Add the following to project.clj

```clojure
:zip ["file1" "resources/file2" "target/production.jar"]
```

Or for a single file out of a folder

```clojure
:zip ["file1" "resources/file2"  {:file-name "production.jar" :file-folder "target" }]
```


Then

    $ lein zip

This will produce target/project-version.zip
