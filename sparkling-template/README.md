sparkling-template
==============

Template routes for Sparkling web framework:

```java
    get(new VelocityTemplateRoute("/hello/:name") {
      @Override
      public Object handle(final Request request, final Response response) {
        Person person = Person.find(request.params("name"));
        return template("hello.wm").render("person", person);
      }
    });
```


#### Hacking sparking-template

To build

```bash
$ mvn install
```

To build velocity example

```bash
$ cd velocity-example
$ mvn assembly:assembly
```

To run velocity example

```bash
$ java -jar target/sparkling-template-velocity-example-1.1-SNAPSHOT-jar-with-dependencies.jar
== Sparkling has ignited ...
>> Listening on 0.0.0.0:4567
```

Then open

http://localhost:4567/hello/foo

in a browser.


#### Extending sparkling-template

To add a new Template engine

 - Create a new module
 - Add dependency to sparkling-template
 - Add dependency to new template engine
 - Extend [TemplateRoute.java](https://github.com/heuermh/sparkling/blob/master/sparkling-template/api/src/main/java/sparkling/template/TemplateRoute.java)
 - (optional) Create a new example module
