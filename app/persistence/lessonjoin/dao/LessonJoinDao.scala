package persistence.lessonjoin.dao

import java.time.LocalDateTime

import scala.concurrent.Future
import slick.jdbc.JdbcProfile
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import persistence.udb.model.User
import persistence.teacherrequest.model.TeacherRequest
import persistence.lessonjoin.model.LessonJoin

//---- DAO ----//
class LessonJoinDAO @javax.inject.Inject()(
  val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  //-- リソース --//
  lazy val slick = TableQuery[LessonJoinTable]

  //-- 処理 --//

  /**
  * userIdに該当列取得
  */
  def filterByUserId(id: User.Id): Future[Seq[LessonJoin]] =
    db.run {
      slick
        .filter(_.userId === id)
        .result
    }
  
  /**
  * lessonIdに該当列取得
  */
  def filterByLessonId(id: TeacherRequest.Id): Future[Seq[LessonJoin]] =
    db.run {
      slick
        .filter(_.lessonId === id)
        .result
    }

  /**
  * 全取得
  */
  def findAll: Future[Seq[LessonJoin]] =
    db.run {
      slick.result
    }

  /**
  * lessonjoinの追加
  */
  def insert(insertData: LessonJoin) = {
    db.run {
      slick returning slick.map(_.id) += insertData
    }
  }

  //-- テーブル定義 --//
  class LessonJoinTable(tag: Tag) extends Table[LessonJoin](tag, "participation_status_relationship") {
    
    // Table's columns
    /* @1 */ def id        = column[LessonJoin.Id]     ("id", O.PrimaryKey, O.AutoInc)
    /* @2 */ def userId    = column[User.Id]           ("user_id")
    /* @3 */ def lessonId  = column[TeacherRequest.Id] ("teacher_request_id")
    /* @4 */ def updatedAt = column[LocalDateTime]     ("updated_at")
    /* @5 */ def createdAt = column[LocalDateTime]     ("created_at")

    // The * projection of the table
    def * = (
      id.?,
      userId,
      lessonId,
      updatedAt,
      createdAt,
    ) <> (
      /** The bidirectional mappings : Tuple(table) => Model */
      (LessonJoin.apply _).tupled,
      /** The bidirectional mappings : Model => Tuple(table) */
      (v: TableElementType) => LessonJoin.unapply(v).map(_.copy(
        _4 = LocalDateTime.now
      ))
    )
  }
}

