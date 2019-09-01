package controllers.input

import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, MessagesControllerComponents}
import persistence.geo.model.Location
import persistence.geo.dao.LocationDAO
import persistence.request.model.Request
import persistence.request.dao.RequestDAO
import model.component.util.ViewValuePageLayout
import model.site.input.request.SiteViewValueInputRequest
import model.site.input.lesson.SiteViewValueInputLesson
import mvc.action.AuthenticationAction
import persistence.category.dao.CategoryDao
import persistence.teacherrequest.dao.TeacherRequestDao

class InputController @javax.inject.Inject()(
  val requestDao: RequestDAO,
  val teacherRequestDao: TeacherRequestDao,
  val daoLocation: LocationDAO,
  val daoCategory: CategoryDao,
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
    } yield {
      val vv = SiteViewValueInputRequest(
        layout     = ViewValuePageLayout(id = r.uri),
        locations  = locSeq,
        categories = catSeq,
      )
      Ok(views.html.site.input.request.Main(vv))
    }
  }

  /**
  * 住民提案の追加
  */
//  def requestInput = Action.async { implicit r =>
//    formForRequest.bindFromRequest.fold(
//      errors  => {
//        for {
//          locSeq <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
//          catSeq <- daoCategory.findAll
//        } yield {
//          Redirect(routes.TopController.show)
//        }
//      },
//      request => {
//        requestDao.insert(request)
//      }
//    )
//    Redirect(routes.TopController.show)
//  }

  /**
  * 講師提案の入力ページ
  */
  def lessonPage(requestId: Long) = (Action andThen AuthenticationAction()).async { implicit r =>
    for {
      locSeq <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
      catSeq <- daoCategory.findAll
      Some(request) <- requestDao.get(requestId)
    } yield {
      val vv = SiteViewValueInputLesson(
        layout     = ViewValuePageLayout(id = r.uri),
        locations  = locSeq,
        categories = catSeq,
        request    = request,
      )
      Ok(views.html.site.input.lesson.Main(vv))
    }
  }

  /**
  * 講師提案の追加
  */
//  def lessonInput(requestId: Int) = Action.async { implicit r =>
//    formForLesson.bindFromRequest.fold(
//      errors => {
//        for {
//          locSeq <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
//          catSeq <- daoCategory.findAll
//        } yield {
//          Redirect(routes.TopController.show)
//        }
//      },
//      lessonFromInput => {
//        for {
//          requestLocationId <- requestDao.getLocationIdById(requestId)
//          requestCategoryId <- requestDao.getCategoryIdById(requestId)
//          lesson <- lessonFromInput.toLesson(requestId, requestCategoryId, requestLocationId)
//        } yield lessonDao.insert(lesson)
//      }
//    )
//    Redirect(routes.TopController.show)
//  }

}
