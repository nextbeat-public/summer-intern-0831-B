package persistence.category.dao

import java.time.LocalDateTime
import scala.concurrent.Future

import slick.jdbc.JdbcProfile
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider

import persistence.category.model.Location

class LocationDAO @javax.inject.Inject()(
  val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  // --[ リソース定義 ] --------------------------------------------------------
  lazy val slick = TableQuery[LocationTable]

  // --[ データ処理定義 ] ------------------------------------------------------
  /**
   * 地域情報の取得
   * 検索業件: ロケーションID (全国地方公共団体コード)
   */
  def get(id: Category.Id): Future[Option[Category]] =
    db.run {
      slick
        .filter(_.id === id)
        .result.headOption
    }

  /**
   * 全カテゴリ取得
   */
  def findAll: Future[Seq[Category]] =
    db.run {
      slick
        .result
    }

  // --[ テーブル定義 ] --------------------------------------------------------
  class CategoryTable(tag: Tag) extends Table[Category](tag, "category") {

    // Table's columns
    /* @1 */ def id        = column[Location.Id]     ("id", O.PrimaryKey)
    /* @2 */ def level     = column[String]          ("name")
    /* @3 */ def updatedAt = column[LocalDateTime]   ("updated_at")
    /* @4 */ def createdAt = column[LocalDateTime]   ("created_at")

    // The * projection of the table
    def * = (
      id, name, updatedAt, createdAt
    ) <> (
      /** The bidirectional mappings : Tuple(table) => Model */
      (Category.apply _).tupled,
      /** The bidirectional mappings : Model => Tuple(table) */
      (v: TableElementType) => Category.unapply(v).map(_.copy(
        _3 = LocalDateTime.now
      ))
    )
  }
}
