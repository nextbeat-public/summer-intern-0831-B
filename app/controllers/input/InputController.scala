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

import model.site.request.SiteViewValueRequest
import model.site.lesson.SiteViewValueLesson

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

  /**
  * 住民提案の入力ページ
  */
  def requestPage = (Action andThen AuthenticationAction()).async { implicit r =>
    for {
      locSeq  <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
      catSeq  <- daoCategory.findAll
      request <- scala.concurrent.Future(None)
    } yield {
      val vv = SiteViewValueRequest(
        layout   = ViewValuePageLayout(id = r.uri),
        location = locSeq,
        category = catSeq,
        request  = request,
      )
      Ok(views.html.site.input.request.Main(vv, formForRequest))
    }
  }

  /**
  * 住民提案の追加
  */
  def requestInput = Action.async { implicit r =>
    formForRequest.bindFromRequest.fold(
      errors  => {
        for {
          locSeq <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
          catSeq <- daoCategory.findAll
        } yield {
          Redirect(routes.TopController.show)
        }
      },
      request => {
        requestDao.insert(request)
      }
    )
    Redirect(routes.TopController.show)
  }

  /**
  * 講師提案の入力ページ
  */
  def lessonPage(requestId: Int) = (Action andThen AuthenticationAction()).async { implicit r =>
    for {
      locSeq <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
      catSeq <- daoCategory.findAll
      lesson <- scala.concurrent.Future(None)
    } yield {
      val vv = SiteViewValueLesson(
        layout   = ViewValuePageLayout(id = r.uri),
        location = locSeq,
        category = catSeq,
        lesson   = lesson,
      )
      Ok(views.html.site.input.lesson.Main(vv, formForLesson))
    }
  }

  /**
  * 講師提案の追加
  */
  def lessonInput(requestId: Int) = Action.async { implicit r =>
    formForRequest.bindFromRequest.fold(
      errors => {
        for {
          locSeq <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
          catSeq <- daoCategory.findAll
        } yield {
          Redirect(routes.TopController.show)
        }
      },
      lesson => {
        for {
          lesson.locationId <- requestDao.getLocationIdById(requestId)
          lesson.categoryId <- requestDao.getCategoryIdById(requestId)
          lesson.requestId  <- requestId
        } yield lessonDao.insert(lesson)
      }
    )
    Redirect(routes.TopController.show)
  }

}