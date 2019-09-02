package persistence.request.dao

import java.time.LocalDateTime

import scala.concurrent.Future
import slick.jdbc.JdbcProfile
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import persistence.request.model._
import persistence.geo.model.Location
import persistence.category.model.Category

//---- DAO ----//
class RequestDAO @javax.inject.Inject()(
  val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  //-- リソース --//
  lazy val slick = TableQuery[RequestTable]

  //-- 処理 --//

  /**
  * idから単request取得
  */
  def get(id: Request.Id): Future[Option[Request]] =
    db.run {
      slick
        .filter(_.id === id)
        .result.headOption
    }
  
  /**
  * 全request取得
  */
  def findAll: Future[Seq[Request]] =
    db.run {
      slick.result
    }

  /**
  * locationIdに該当するrequestSeq
  */
  def filterByLocationIds(locationId: Seq[Location.Id]): Future[Seq[Request]] =
    db.run {
      slick
        .filter(_.locationId inSet locationId)
        .result
    }

  /**
  * categoryIdに該当するrequestSeq
  */
  def filterByCategoryIds(categoryId: Category.Id): Future[Seq[Request]] =
    db.run {
      slick
        .filter(_.categoryId === categoryId)
        .result
    }

  /**
  * locationId & categoryIdに該当するrequestSeq
  */
  def filterByLocationAndCategoryIds(locationId: Seq[Location.Id], categoryId: Category.Id): Future[Seq[Request]] =
    db.run {
      slick
        .filter(x => (x.locationId inSet locationId) && (x.categoryId === categoryId))
        .result
    }

  /**
  * requestの追加
  */
  def insert(request: Request) = {
    val entWithNoId = request.copy(id = None)
    db.run {
      slick returning slick.map(_.id) += entWithNoId
    }
  }

  //-- テーブル定義 --//
  class RequestTable(tag: Tag) extends Table[Request](tag, "user_request") {
    
    // Table's columns
    /* @1 */ def id         = column[Request.Id]    ("user_request_id", O.PrimaryKey, O.AutoInc)
    /* @2 */ def name       = column[String]        ("user_request_name")
    /* @3 */ def detail     = column[String]        ("user_request_detail")
    /* @4 */ def date       = column[LocalDateTime] ("user_request_date")
    /* @5 */ def numGood    = column[Int]           ("user_request_good")
    /* @6 */ def categoryId = column[Category.Id]   ("category_id")
    /* @7 */ def locationId = column[Location.Id]   ("location_id")
    /* @8 */ def updatedAt  = column[LocalDateTime] ("updated_at")
    /* @9 */ def createdAt  = column[LocalDateTime] ("created_at")

    // The * projection of the table
    def * = (
      id.?,
      name,
      detail,
      date,
      numGood,
      locationId,
      categoryId,
      updatedAt,
      createdAt
    ) <> (
      /** The bidirectional mappings : Tuple(table) => Model */
      (Request.apply _).tupled,
      /** The bidirectional mappings : Model => Tuple(table) */
      (v: TableElementType) => Request.unapply(v).map(_.copy(
        _8 = LocalDateTime.now
      ))
    )
  }
}

