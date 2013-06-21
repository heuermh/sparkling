package sparkling.servlet;

import static sparkling.Sparkling.after;
import static sparkling.Sparkling.before;
import static sparkling.Sparkling.get;
import static sparkling.Sparkling.post;
import sparkling.Filter;
import sparkling.Request;
import sparkling.Response;
import sparkling.Route;
import sparkling.servlet.SparklingApplication;

public class MyApp implements SparklingApplication {

    @Override
    public void init() {
        System.out.println("HELLO!!!");
        before(new Filter("/protected/*") {

            @Override
            public void handle(Request request, Response response) {
                halt(401, "Go Away!");
            }
        });

        get(new Route("/hi") {

            @Override
            public Object handle(Request request, Response response) {
                return "Hello World!";
            }
        });

        get(new Route("/:param") {

            @Override
            public Object handle(Request request, Response response) {
                return "echo: " + request.params(":param");
            }
        });

        get(new Route("/") {

            @Override
            public Object handle(Request request, Response response) {
                return "Hello Root!";
            }
        });

        post(new Route("/poster") {

            @Override
            public Object handle(Request request, Response response) {
                String body = request.body();
                response.status(201); // created
                return "Body was: " + body;
            }
        });

        after(new Filter("/hi") {

            @Override
            public void handle(Request request, Response response) {
                response.header("after", "foobar");
            }
        });

        try {
            Thread.sleep(500);
        } catch (Exception e) {
        }
    }

}
