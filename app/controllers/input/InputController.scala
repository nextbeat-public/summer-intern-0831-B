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
import model.site.input.request.SiteViewValueInputRequest.formForRequest
import persistence.teacherrequest.model.TeacherRequest.formForTeacherRequestInput
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
        form       = formForRequest,
      )
      Ok(views.html.site.input.request.Main(vv))
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
          val vv = SiteViewValueInputRequest(
            layout     = ViewValuePageLayout(id = r.uri),
            locations  = locSeq,
            categories = catSeq,
            form       = errors,
          )
          BadRequest(views.html.site.input.request.Main(vv))
        }
      },
      requestForm => {
        for {
          _      <- requestDao.insert(requestForm.toRequest)
          madeRequest <- requestDao.getLast
        } yield {
          Redirect(controllers.article.routes.ArticleController.showRequest(madeRequest.get.id.get))
        }
      }
    )
  }

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
        form       = formForTeacherRequestInput
      )
      Ok(views.html.site.input.lesson.Main(vv))
    }
  }

  /**
  * 講師提案の追加
  */
  def lessonInput(requestId: Long) = Action.async { implicit r =>
    formForTeacherRequestInput.bindFromRequest.fold(
      errors => {
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
            form       = errors
          )
        println(errors)
          BadRequest(views.html.site.input.lesson.Main(vv))
        }
      },
      lessonFromInput => {
        for {
          Some(request) <- requestDao.get(requestId)
          _            <- teacherRequestDao.insert(lessonFromInput.toTeacherRequest(requestId, request.categoryId, request.locationId))
          madeLesson   <- teacherRequestDao.getLast
        } yield {
          Redirect(controllers.article.routes.ArticleController.showLesson(madeLesson.get.id.get))
        }
      }
    )
  }

}
