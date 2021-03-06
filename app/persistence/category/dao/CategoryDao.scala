package persistence.category.dao

import java.time.LocalDateTime

import persistence.category.model.Category

import scala.concurrent.Future
import slick.jdbc.JdbcProfile
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider


class CategoryDAO @javax.inject.Inject()(
  val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  // --[ リソース定義 ] --------------------------------------------------------
  lazy val slick = TableQuery[CategoryTable]

  // --[ データ処理定義 ] ------------------------------------------------------
  /**
   * カテゴリIDからカテゴリの取得
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
    /* @1 */ def id        = column[Category.Id]     ("id", O.PrimaryKey)
    /* @2 */ def name      = column[String]          ("name")
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
