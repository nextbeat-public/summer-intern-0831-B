package controllers.article

import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, MessagesControllerComponents}
import persistence.geo.model.Location
import persistence.geo.dao.LocationDAO
import persistence.category.dao.CategoryDao
import persistence.request.dao.RequestDAO
import persistence.teacherrequest.dao.TeacherRequestDao
import persistence.lessonjoin.dao.LessonJoinDAO
import persistence.lessonjoin.model.LessonJoin
import persistence.requestgood.dao.RequestGoodDAO
import persistence.requestgood.model.RequestGood
import model.site.article.{SiteViewValueLesson, SiteViewValueRequest, SiteViewValueSearch}
import model.component.util.ViewValuePageLayout
import model.site.article.FormValueForSiteSearch.formForSearch
import mvc.action.AuthenticationAction

class ArticleController @javax.inject.Inject()(
  val requestDao: RequestDAO,
  val teacherRequestDao: TeacherRequestDao,
  val lessonJoinDao: LessonJoinDAO,
  val requestGoodDao: RequestGoodDAO,
  val daoLocation: LocationDAO,
  val daoCategory: CategoryDao,
  cc: MessagesControllerComponents
) extends AbstractController(cc) with I18nSupport {
  implicit lazy val executionContext = defaultExecutionContext

    /**
    * 検索
    */
    def search = (Action).async { implicit r =>
      val locationId = r.getQueryString("locationId")
      val categoryId = r.getQueryString("categoryId")

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
        lessonSeq  <- (locationId, categoryId) match {
          case (Some(locId), Some(catId)) => {
            for {
              locations  <- daoLocation.filterByPrefId(locId)
              lessonSeq  <- teacherRequestDao.filterByLocationAndCategoryIds(locations.map(_.id), catId)
            } yield lessonSeq 
          }
          case (Some(locId), None)        => {
            for {
              locations <- daoLocation.filterByPrefId(locId)
              lessonSeq <- teacherRequestDao.filterByLocationIds(locations.map(_.id))
            } yield lessonSeq
          }
          case (None, Some(catId))        => teacherRequestDao.filterByCategoryIds(catId)
          case (None, None)               => teacherRequestDao.findAll
        }
      } yield {
        val vv = SiteViewValueSearch(
          layout     = ViewValuePageLayout(id = r.uri),
          locations  = locSeq,
          categories = catSeq,
          requests   = requestSeq,
          lessons    = lessonSeq,
        )
        Ok(views.html.site.article.search.Main(vv, formForSearch))
      }
    }

    /**
    * ユーザ提案
    */
    def showRequest(requestId: Long) = (Action andThen AuthenticationAction()).async { implicit r =>
      for {
        locSeq        <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
        catSeq        <- daoCategory.findAll
        Some(request) <- requestDao.get(requestId)
        requestGoods  <- requestGoodDao.filterByRequestId(requestId)
        alreadyGood   <- requestGoodDao.filterByUserAndRequestId(r.userId, requestId)
      } yield {
        val vv = SiteViewValueRequest(
          layout     = ViewValuePageLayout(id = r.uri),
          locations  = locSeq,
          categories = catSeq,
          request    = request,
        )
        Ok(views.html.site.article.request.Main(vv, requestGoods.size, alreadyGood.size))
      }
    }
    // いいね
    def insertRequestGood(requestId: Long) = (Action andThen AuthenticationAction()) { implicit r =>
      val insertData = RequestGood(None, r.userId, requestId)
      requestGoodDao.insert(insertData)
      Redirect(routes.ArticleController.showRequest(requestId))
    }

    /**
    * 授業提案
    */
    def showLesson(lessonId: Long) = (Action andThen AuthenticationAction()).async { implicit r =>
      for {
        locSeq       <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
        catSeq       <- daoCategory.findAll
        Some(lesson) <- teacherRequestDao.get(lessonId)
        lessonJoins  <- lessonJoinDao.filterByLessonId(lessonId)
        alreadyJoin  <- lessonJoinDao.filterByUserAndLessonId(r.userId, lessonId)
      } yield {
        val vv = SiteViewValueLesson(
          layout     = ViewValuePageLayout(id = r.uri),
          locations  = locSeq,
          categories = catSeq,
          lesson     = lesson,
        )
        Ok(views.html.site.article.lesson.Main(vv, lessonJoins.size, alreadyJoin.size))
      }
    }
    // 参加追加
    def insertLessonJoin(lessonId: Long) = (Action andThen AuthenticationAction()) { implicit r =>
      val insertData = LessonJoin(None, r.userId, lessonId)
      lessonJoinDao.insert(insertData)
      Redirect(routes.ArticleController.showLesson(lessonId))
    }
}
