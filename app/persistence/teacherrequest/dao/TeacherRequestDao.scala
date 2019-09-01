/*
 * This file is part of the Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package persistence.teacherrequest.dao

import java.time.LocalDateTime

import persistence.category.model.Category

import scala.concurrent.Future
import slick.jdbc.JdbcProfile
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import persistence.geo.model.Location
import persistence.request.model.Request
import persistence.teacherrequest.model.TeacherRequest

// DAO: 施設情報
//~~~~~~~~~~~~~~~~~~
class TeacherRequestDao @javax.inject.Inject()(
  val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  // --[ リソース定義 ] --------------------------------------------------------
  lazy val slick = TableQuery[TeacherRequestTable]

  // --[ データ処理定義 ] ------------------------------------------------------
  def get(id: TeacherRequest.Id): Future[Option[TeacherRequest]] =
    db.run {
      slick
        .filter(_.id === id)
        .result.headOption
    }

  def findAll: Future[Seq[TeacherRequest]] =
    db.run {
      slick.result
    }

  /**
    * locationIdに該当するrequestSeq
    */
  def filterByLocationIds(locationId: Seq[Location.Id]): Future[Seq[TeacherRequest]] =
    db.run {
      slick
        .filter(_.locationId inSet locationId)
        .result
    }

  /**
    * categoryIdに該当するrequestSeq
    */
  def filterByCategoryIds(categoryId: Category.Id): Future[Seq[TeacherRequest]] =
    db.run {
      slick
        .filter(_.categoryId === categoryId)
        .result
    }

  /**
    * locationId & categoryIdに該当するrequestSeq
    */
  def filterByLocationAndCategoryIds(locationId: Seq[Location.Id], categoryId: Category.Id): Future[Seq[TeacherRequest]] =
    db.run {
      slick
        .filter(x => (x.locationId inSet locationId) && (x.categoryId === categoryId))
        .result
    }

  // --[ テーブル定義 ] --------------------------------------------------------
  class TeacherRequestTable(tag: Tag) extends Table[TeacherRequest](tag, "teacher_request") {


    // Table's columns
    /* @ */ def id           = column[TeacherRequest.Id]   ("teacher_request_id", O.PrimaryKey, O.AutoInc)
    /* @ */ def requestId    = column[Request.Id]          ("user_request_id")
    /* @ */ def locationId   = column[Location.Id]         ("location_id")
    /* @ */ def categoryId   = column[String]              ("category_id")
    /* @ */ def name         = column[String]              ("teacher_request_name")
    /* @ */ def detail       = column[String]              ("teacher_request_detail")
    /* @ */ def maxPeople    = column[Int]                 ("teacher_request_maximum_people")
    /* @ */ def minPeople    = column[Int]                 ("teacher_request_minimum_people")
    /* @ */ def fee          = column[Int]                 ("teacher_request_study_fee")
    /* @ */ def createDate   = column[LocalDateTime]       ("teacher_request_date")
    /* @ */ def deadlineDate = column[LocalDateTime]       ("teacher_request_deadline")
    /* @ */ def scheduleDate = column[LocalDateTime]       ("teacher_request_scheduled_date")
    /* @ */ def updatedAt    = column[LocalDateTime]       ("updated_at")
    /* @ */ def createdAt    = column[LocalDateTime]       ("created_at")

    // The * projection of the table
    def * = (
      id.?,
      requestId,
      locationId,
      categoryId,
      name,
      detail,
      maxPeople,
      minPeople,
      fee,
      createDate,
      deadlineDate,
      scheduleDate,
      updatedAt,
      createdAt
    ) <> (
      /** The bidirectional mappings : Tuple(table) => Model */
      (TeacherRequest.apply _).tupled,
      /** The bidirectional mappings : Model => Tuple(table) */
      (v: TableElementType) => TeacherRequest.unapply(v).map(_.copy(
        _13 = LocalDateTime.now
      ))
    )
  }
}
