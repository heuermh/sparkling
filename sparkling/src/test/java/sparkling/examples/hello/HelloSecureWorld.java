package sparkling.examples.hello;

import sparkling.Request;
import sparkling.Response;
import sparkling.Route;

import static sparkling.Sparkling.get;
import static sparkling.Sparkling.setSecure;

/**
 * You'll need to provide a JKS keystore as arg 0 and its password as arg 1.
 */
public class HelloSecureWorld {
    public static void main(String[] args) {

        setSecure(args[0], args[1], null, null);
        get(new Route("/hello") {
            @Override
            public Object handle(Request request, Response response) {
                return "Hello Secure World!";
            }
        });

    }
}
