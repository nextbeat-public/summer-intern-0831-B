package persistence.request.dao

import java.time.LocalDateTime
import scala.concurrent.Future

import slick.jdbc.JdbcProfile
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import persistence.Lesson.model._
import persistence.geo.model.Location
import persistence.category.model.Category
import persistence.request.model.Request


//---- DAO ----//
class LessonDAO @javax.inject.Inject()(
  val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._
  
  //-- リソース --//
  lazy val slick = TableQuery[LessonTable]

  //-- 処理 --//

  /**
  * idから単lesson取得
  */
  def get(id: Lesson.Id): Future[Option[Lesson]] =
    db.run {
      slick
        .filter(_.id === id)
        .result.headOption
    }

  /**
  * 全lesson取得
  */
  def findAll: Future[Seq[Lesson]] =
    db.run {
      slick.result
    }

  /**
  * locationIdに該当するlessonSeq
  */
  def filterByLocationIds(locationId: Seq[Location.Id]): Future[Seq[Lesson]] =
    db.run {
      slick
        .filter(_.locationId inSet locationId)
        .result
    }

  /**
  * categoryIdに該当するlessonSeq
  */
  def filterByCategoryIds(categoryId: Category.Id): Future[Seq[Lesson]] =
    db.run {
      slick
        .filter(_.categoryId === categoryIds)
        .result
    }

  /**
  * locationId & categoryIdに該当するrequestSeq
  */
  def filterByLocationAndCategoryIds(locationId: Seq[Location.Id], categoryId: Category.Id): Future[Seq[Lesson]] =
    db.run {
      slick
        .filter(_.locationId inSet locationId && _.categoryId === categoryIds)
        .result
    }

  /**
  * lessonの追加
  */
  def insert(lesson: Lesson) = {
    val insertData: Lesson = Lesson(None, lesson.name, lesson.detail, LocalDateTime.now, LocalDateTime.now, lesson.maxPeople, lesson.minPeople, LocalDateTime.now, lesson.fee, lesson.categoryId.get, lesson.locationId.get, lesson.requestId.get, lesson.userId.get)
    db.run {
      slick returning slick.map(_.id) += insertData
    }
  }

  //-- テーブル定義 --//
  class LessonTable(tag: Tag) extends Table[Lesson](tag, "teacher_request") {
    
    // Table's columns
    /* @1  */ def id            = column[Request.Id]    ("teacher_request_id", O.PrimaryKey, O.AutoInc)
    /* @2  */ def name          = column[String]        ("teacher_request_name")
    /* @3  */ def detail        = column[String]        ("teacher_request_detail")
    /* @4  */ def date          = column[LocalDateTime] ("teacher_request_date")
    /* @5  */ def deadline      = column[LocalDateTime] ("teacher_request_deadline")
    /* @6  */ def maxPeople     = column[Int]           ("teacher_request_maximum_people")
    /* @7  */ def minPeople     = column[Int]           ("teacher_request_minimum_people")
    /* @8  */ def scheduledDate = column[LocalDateTime] ("teacher_request_scheduled_date")
    /* @9  */ def fee           = column[Int]           ("teacher_request_study_fee")
    /* @10 */ def categoryId    = column[Category.Id]   ("category_id")
    /* @11 */ def locationId    = column[Location.Id]   ("location_id")
    /* @12 */ def requestId     = column[Request.Id]    ("user_request_id")
    /* @13 */ def userId        = column[User.Id]       ("id")
    /* @14 */ def updatedAt     = column[LocalDateTime] ("updated_at")
    /* @15 */ def createdAt     = column[LocalDateTime] ("created_at")

    // The * projection of the table
    def * = (
      id.?, name, detail, date, deadline, maxPeople, minPeople, scheduledDate, fee, categoryId, locationId, requestId, userId,
      updatedAt, createdAt
    ) <> (
      /** The bidirectional mappings : Tuple(table) => Model */
      (Lesson.apply _).tupled,
      /** The bidirectional mappings : Model => Tuple(table) */
      (v: TableElementType) => Request.unapply(v).map(_.copy(
        _8 = LocalDateTime.now
      ))
    )
  }

}