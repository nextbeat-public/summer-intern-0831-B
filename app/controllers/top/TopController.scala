package controllers.top

import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, MessagesControllerComponents}
import model.component.util.ViewValuePageLayout
import model.site.top.SiteViewValueTop

class TopController @javax.inject.Inject()(
  cc: MessagesControllerComponents
) extends AbstractController(cc) with I18nSupport {
  implicit lazy val executionContext = defaultExecutionContext

  def show = Action{ implicit r =>
    val vv = SiteViewValueTop(layout = ViewValuePageLayout(id = "/"))
    Ok(views.html.site.Main(vv))
  }

}
