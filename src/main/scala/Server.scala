import play.api.test._
import play.api.routing._
import play.api.mvc._
import org.asynchttpclient._
import play.api.inject.guice.GuiceApplicationBuilder

object Server {

  implicit def app = GuiceApplicationBuilder().additionalRouter (Router.from({
    case h: RequestHeader if h.path == "/post" => TestController.post
  })).build()

  val port = 12345
  lazy val instance =  TestServer(port, app, None, None)

  def main(args: Array[String]): Unit = {
    instance.start()
    println("Server started")
    val ahc = new DefaultAsyncHttpClient();
    val resp = ahc
      .preparePost(s"http://127.0.0.1:${port}/post")
      .setHeader("Content-Type", "application/x-www-form-urlencoded")
      .addFormParam("name", "呵呵")
      .execute()
      .get()
    println(resp.getResponseBody())

    val resp2 = ahc.preparePost(s"http://127.0.0.1:${port}/post")
      .setHeader("Content-Type", "application/x-www-form-urlencoded")
      .setBody(s"name=${java.net.URLEncoder.encode("呵呵", "UTF-8")}")
      .execute()
      .get()
    println(resp2.getResponseBody())

    instance.stop()
    println("Server stopped")
  }
}
