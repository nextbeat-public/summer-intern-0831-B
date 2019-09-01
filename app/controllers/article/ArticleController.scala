package controllers.article

import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, MessagesControllerComponents}

import persistence.geo.model.Location
import persistence.geo.dao.LocationDAO

import model.component.util.ViewValuePageLayout
import mvc.action.AuthenticationAction

class ArticleController @javax.inject.Inject()(
  val ArticleDao: ArticleDAO,
  val daoLocation: LocationDAO,
  cc: MessagesControllerComponents
) extends AbstractController(cc) with I18nSupport {
  implicit lazy val executionContext = defaultExecutionContext

    def search = (Action andThen AuthenticationAction()).async {
      
    }