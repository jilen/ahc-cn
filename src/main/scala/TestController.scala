import play.api.mvc._
import play.api.data._
import play.api.data.Forms._


object TestController extends Controller {

  val f = Form(single("name" -> nonEmptyText))

  def post = Action(parse.form(f)) { r =>
    println(r.body)
    Ok(r.body)
  }

}
