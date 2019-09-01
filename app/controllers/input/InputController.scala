package controllers.input

import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, MessagesControllerComponents}

import persistence.geo.model.Location
import persistence.geo.dao.LocationDAO
import persistence.category.model.category
import persistence.category.dao.CategoryDAO
import persistence.request.model.Request
import persistence.request.dao.RequestDAO
import persistence.lesson.model.Lesson
import persistence.lesson.dao.LessonDAO

import model.component.util.ViewValuePageLayout
import mvc.action.AuthenticationAction

class InputController @javax.inject.Inject()(
  val requestDao: RequestDAO,
  val lessonDao: LessonDAO,
  val daoLocation: LocationDAO,
  val daoCategory: CategoryDAO,
  cc: MessagesControllerComponents
) extends AbstractController(cc) with I18nSupport {
  implicit lazy val executionContext = defaultExecutionContext


}