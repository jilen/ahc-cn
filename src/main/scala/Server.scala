import play.api.test._
import org.asynchttpclient._

object Server {

  implicit def app = FakeApplication(
    withRoutes = {
      case ("POST", "/post") => TestController.post
    }
  )
  val port = 12345
  lazy val instance =  new TestServer(port, app)

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
    instance.stop()
    println("Server stopped")
    System.exit(0)
  }
}
