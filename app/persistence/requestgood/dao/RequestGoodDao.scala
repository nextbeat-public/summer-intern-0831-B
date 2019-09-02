package persistence.requestgood.dao

import java.time.LocalDateTime

import scala.concurrent.Future
import slick.jdbc.JdbcProfile
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import persistence.udb.model.User
import persistence.request.model.Request
import persistence.lessonjoin.model.RequestGood

//---- DAO ----//
class RequestGoodDAO @javax.inject.Inject()(
  val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  //-- リソース --//
  lazy val slick = TableQuery[RequestGoodTable]

  //-- 処理 --//

  /**
  * userIdに該当列取得
  */
  def filterByUserId(id: User.Id): Future[Seq[RequestGood]] =
    db.run {
      slick
        .filter(_.userId === id)
        .result
    }
  
  /**
  * requestIdに該当列取得
  */
  def filterByRequestId(id: Request.Id): Future[Seq[RequestGood]] =
    db.run {
      slick
        .filter(_.requestId === id)
        .result
    }

  /**
  * requestIdとuserIdに該当列取得
  */
  def filterByUserAndRequestId(userId : User.Id, requestId: Request.Id): Future[Seq[RequestGood]] =
    db.run {
      slick
        .filter(row => ((row.userId === userId) && (row.requestId === requestId) ))
        .result
    }

  /**
  * 全取得
  */
  def findAll: Future[Seq[RequestGood]] =
    db.run {
      slick.result
    }

  /**
  * requestgoodの追加
  */
  def insert(insertData: RequestGood) = {
    db.run {
      slick returning slick.map(_.id) += insertData
    }
  }

  //-- テーブル定義 --//
  class RequestGoodTable(tag: Tag) extends Table[RequestGood](tag, "request_good_relationship") {
    
    // Table's columns
    /* @1 */ def id        = column[LessonJoin.Id]     ("id", O.PrimaryKey, O.AutoInc)
    /* @2 */ def userId    = column[User.Id]           ("user_id")
    /* @3 */ def requestId = column[TeacherRequest.Id] ("user_request_id")
    /* @4 */ def updatedAt = column[LocalDateTime]     ("updated_at")
    /* @5 */ def createdAt = column[LocalDateTime]     ("created_at")

    // The * projection of the table
    def * = (
      id.?,
      userId,
      requestId,
      updatedAt,
      createdAt,
    ) <> (
      /** The bidirectional mappings : Tuple(table) => Model */
      (RequestGood.apply _).tupled,
      /** The bidirectional mappings : Model => Tuple(table) */
      (v: TableElementType) => RequestGood.unapply(v).map(_.copy(
        _4 = LocalDateTime.now
      ))
    )
  }
}

