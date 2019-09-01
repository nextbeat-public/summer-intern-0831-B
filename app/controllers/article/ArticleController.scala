package controllers.article

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

import model.site.search.SiteViewValueSearch
import model.site.request.SiteViewValueRequest
import model.site.lesson.SiteViewValueLesson

import model.component.util.ViewValuePageLayout
import mvc.action.AuthenticationAction

class ArticleController @javax.inject.Inject()(
  val requestDao: RequestDAO,
  val lessonDao: LessonDAO,
  val daoLocation: LocationDAO,
  val daoCategory: CategoryDAO,
  cc: MessagesControllerComponents
) extends AbstractController(cc) with I18nSupport {
  implicit lazy val executionContext = defaultExecutionContext

    /**
    * 検索
    */
    def search(locationId: Option[String], categoryId: Option[String]) = (Action andThen AuthenticationAction()).async {
      for {
        locSeq     <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
        catSeq     <- daoCategory.findAll
        requestSeq <- (locationId, categoryId) match {
          case (Some(locId), Some(catId)) => {
            for {
              locations  <- daoLocation.filterByPrefId(locId)
              requestSeq <- requestDao.filterByLocationAndCategoryIds(locations.map(_.id), catId)
            } yield requestSeq 
          }
          case (Some(locId), None)        => {
            for {
              locations  <- daoLocation.filterByPrefId(locId)
              requestSeq <- requestDao.filterByLocationIds(locations.map(_.id))
            } yield requestSeq
          }
          case (None, Some(catId))        => requestDao.filterByCategoryIds(catId)
          case (None, None)               => requestDao.findAll
        }
        lessonSeq  <- (locationId, category) match {
          case (Some(locId), Some(catId)) => {
            for {
              locations  <- daoLocation.filterByPrefId(locId)
              lessonSeq  <- lessonDao.filterByLocationAndCategoryIds(locations.map(_.id), catId)
            } yield lessonSeq 
          }
          case (Some(locId), None)        => {
            for {
              locations <- daoLocation.filterByPrefId(locId)
              lessonSeq <- lessonDao.filterByLocationIds(locations.map(_.id))
            } yield lessonSeq
          }
          case (None, Some(catId))        => lessonDao.filterByCategoryIds(catId)
          case (None, None)               => lessonDao.findAll
        }
      } yield {
        val vv = SiteViewValueSearch(
          location = locSeq,
          category = catSeq,
          requests = requestSeq,
          lessons  = lessonSeq,
        )
        Ok(views.html.site.article.search.Main(vv))
      }
    }

    /**
    * ユーザ提案
    */
    def showRequest(requestId: Int) = Action.async {
      for {
        locSeq  <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
        catSeq  <- daoCategory.findAll
        request <- requestDao.get(requestId)
      } yield {
        val vv = SiteViewValueRequest(
          location = locSeq,
          category = catSeq,
          request  = request,
        )
        Ok(views.html.site.article.request.Main(vv))
      }
    }

    /**
    * 授業提案
    */
    def showLesson(lessonId: Int) = Action.async {
      for {
        locSeq <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
        catSeq <- daoCategory.findAll
        lesson <- lessonDao.get(lessonId)
      } yield {
        val vv = SiteViewValueLesson(
          location = locSeq,
          category = catSeq,
          lesson   = lesson,
        )
        Ok(views.html.site.article.lesson.Main(vv))
      }
    }
}
