package controllers

import java.util.concurrent.atomic.AtomicLong
import javax.inject._

import play.api.mvc._
import play.api.libs.json._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  val counter: AtomicLong = new AtomicLong()

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  case class Greeting(count: Long, greeting: String)
  implicit val greetingWrites = Json.writes[Greeting]

  def index(guest: String) = Action {
    Ok(Json.toJson(Greeting(counter.incrementAndGet(), s"Hey, $guest")))
  }

}
