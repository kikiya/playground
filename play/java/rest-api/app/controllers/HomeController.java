package controllers;

import play.libs.Json;
import play.mvc.*;

import java.util.concurrent.atomic.AtomicLong;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    final AtomicLong counter = new AtomicLong();

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */

    public Result greeting(String guest) {

        return ok(Json.toJson(new Greeting(counter.incrementAndGet(), guest)));

    }




    public static class Greeting {
        public final Long count;
        public final String greeting;

        Greeting(Long count, String guest){
            this.count = count;
            this.greeting = String.format("Hello, %s!", guest);
        }
    }

}
