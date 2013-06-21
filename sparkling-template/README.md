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



To build

    $ mvn install


To build velocity example

    $ cd velocity-example
    $ mvn assembly:assembly


To run velocity example

    $ java -jar target/sparkling-template-velocity-example-1.0-SNAPSHOT-jar-with-dependencies.jar 
    == Sparkling has ignited ...
    >> Listening on 0.0.0.0:4567

Then open

    http://localhost:4567/hello/foo

in a browser.



To add a new Template engine

 - Create a new module
 - Add dependency to sparkling-template
 - Add dependency to new template engine
 - Extend https://github.com/heuermh/sparkling/blob/master/sparkling-template/api/src/main/java/sparkling/template/TemplateRoute.java
 - (optional) Create a new example module