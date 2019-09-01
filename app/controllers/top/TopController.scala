package controllers.top

import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, MessagesControllerComponents}

class TopController @javax.inject.Inject()(
  cc: MessagesControllerComponents
) extends AbstractController(cc) with I18nSupport {
  implicit lazy val executionContext = defaultExecutionContext
  
  /**
  * 検索
  */
  def show = Action { implicit r =>
    Ok(views.html.site.Main())
  }

}